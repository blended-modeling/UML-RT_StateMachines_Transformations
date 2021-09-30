/*****************************************************************************
 * Copyright (c) 2015 CEA LIST.
 * *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Celine Janssens (ALL4TEC) celine.janssens@all4tec.net
 *
 *****************************************************************************/
package org.eclipse.papyrusrt.umlrt.core.types.advice;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.commands.ConfigureElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.commands.CreateElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.ConfigureRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.papyrus.infra.services.edit.utils.RequestParameterConstants;
import org.eclipse.papyrus.uml.service.types.element.UMLElementTypes;
import org.eclipse.papyrusrt.umlrt.core.types.UMLRTElementTypesEnumerator;
import org.eclipse.papyrusrt.umlrt.core.utils.MessageSetUtils;
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.RTMessageKind;
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.RTMessageSet;
import org.eclipse.uml2.uml.Collaboration;
import org.eclipse.uml2.uml.Dependency;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.InterfaceRealization;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.Relationship;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.Usage;
import org.eclipse.uml2.uml.util.UMLUtil;

/**
 * This Class Create the Protocol Context for Papyrus Real Time:
 * In a Protocol Container:
 * <ul>
 * <li>Collaboration as Protocol</li>
 * <li>MessageSet IN</li>
 * <li>MessageSet OUT</li>
 * <li>MessageSet INOUT</li>
 * <li>Interface Realization</li>
 * <li>Usage</li>
 * </ul>
 * 
 * @author Céline JANSSENS
 *
 */
public class ProtocolContainerConfigurationCommand extends ConfigureElementCommand {

	/**
	 * The enumeration of the relation between referenceElement and created Element
	 * 
	 * @author Céline JANSSENS
	 *
	 */
	private enum Relation {
		CHILD, SIBLING, PARENT;
	}

	private final String name;

	private final Collaboration protocol;

	private IProgressMonitor progressMonitor;

	private IAdaptable info;

	public ProtocolContainerConfigurationCommand(ConfigureRequest request, String name, Collaboration protocol) {
		super(request);
		this.name = name;
		this.protocol = protocol;
	}

	/**
	 * Creates a UML::Usage relation between protocol and rtMessageSet with given name.
	 *
	 * @param protocol
	 * @param name
	 * @param rtMessageSet
	 * @throws ExecutionException
	 */
	private void createUsage(final Collaboration protocol, final String name, Interface rtMessageSet) throws ExecutionException {
		Usage usageOut = (Usage) createElement(protocol, name, UMLElementTypes.USAGE, Relation.SIBLING);
		usageOut.getClients().add(protocol);
		usageOut.getSuppliers().add(rtMessageSet);
	}

	/**
	 * Creates an UML::InterfaceRealization relation between protocol and rtMessageSet with given name.
	 *
	 * @param protocol
	 * @param name
	 * @param rtMessageSet
	 * @throws ExecutionException
	 */
	private void createInterfaceRealization(final Collaboration protocol, final String name, Interface rtMessageSetInt) throws ExecutionException {
		InterfaceRealization realization = (InterfaceRealization) createElement(protocol, name, UMLElementTypes.INTERFACE_REALIZATION, Relation.CHILD);
		realization.setContract(rtMessageSetInt);
		realization.setImplementingClassifier(protocol);
	}

	/**
	 * Create the Element of type elementType associated with the referencedEleemnt
	 * 
	 * @param referenceElement
	 *            The element that is the reference
	 * @param name
	 *            Name of the newlyy created Element
	 * @param elementType
	 *            Element type to be created
	 * @param relation
	 *            the Relation with the referenceElement
	 * @return created element as EObject
	 * @throws ExecutionException
	 */
	protected EObject createElement(Collaboration referenceElement, String name, IElementType elementType, Relation relation) throws ExecutionException {
		if ((referenceElement == null) /* @noname || (name == null) */) {
			throw new ExecutionException("Either the referenceElement or the name parameter is null. ");
		}

		EObject newElement = null;

		CreateElementRequest createElementRequest = new CreateElementRequest(referenceElement.getNearestPackage(), elementType);
		
		// change the update name field to false in the ApplyStereotype advice configuration to keep always the name set in the RequestParameterConstants: 
		// no more need to explicitly call the setName() method as in the previous commit
		// for dependencies, setParameter is useless see https://bugs.eclipse.org/bugs/show_bug.cgi?id=440263: no default name will be set for dependencies if name==null
		createElementRequest.setParameter(RequestParameterConstants.NAME_TO_SET, name); // force name
		
		CreateElementCommand command = new CreateElementCommand(createElementRequest);
		command.execute(progressMonitor, info);
		newElement = command.getNewElement();

		if (newElement == null) {
			throw new ExecutionException("Element creation problem for " + elementType.getDisplayName() + ".");
		}

		if (relation == Relation.CHILD) { // if newElement is an owned element of protocol
			if (elementType == UMLElementTypes.INTERFACE_REALIZATION) {
				referenceElement.getInterfaceRealizations().add((InterfaceRealization) newElement);
			} else {
				referenceElement.createOwnedAttribute(name, (Type) newElement);
			}
		} else if (relation == Relation.SIBLING) { // if newElement is a sibling of protocol
			Package nearestPackage = referenceElement.getNearestPackage();
			nearestPackage.getPackagedElements().add((PackageableElement) newElement);
		} else if (relation == Relation.PARENT) { // otherwise newElement is a container element of protocol
			Package container = (Package) newElement;
			EList<PackageableElement> packagedElements = container.getPackagedElements();
			packagedElements.add(referenceElement);
		}

		return newElement;
	}

	/**
	 * Set the Kind of the Message
	 * 
	 * @param rtMessageSetInt
	 *            The message Set
	 * @param kind
	 *            the Kind to be set
	 */
	private void setRtMsgKind(Interface rtMessageSetInt, RTMessageKind kind) {
		RTMessageSet rtMessageSet = UMLUtil.getStereotypeApplication(rtMessageSetInt, RTMessageSet.class);
		rtMessageSet.setRtMsgKind(kind);
	}

	/**
	 * @see org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand#doExecuteWithResult(org.eclipse.core.runtime.IProgressMonitor, org.eclipse.core.runtime.IAdaptable)
	 */
	@Override
	protected CommandResult doExecuteWithResult(IProgressMonitor progressMonitor, IAdaptable info) throws ExecutionException {
		this.progressMonitor = progressMonitor;
		this.info = info;

		// Create the UMLRealTime::ProtocolContainer package
		createElement(protocol, name, UMLRTElementTypesEnumerator.PROTOCOL_CONTAINER, Relation.PARENT);

		// Create the incoming UMLRealTime::RTMessageSet interface
		String nameIn = MessageSetUtils.computeInterfaceInName(name);
		Interface rtMessageSetInt = (Interface) createElement(protocol, nameIn, UMLRTElementTypesEnumerator.RT_MESSAGE_SET, Relation.SIBLING);
		setRtMsgKind(rtMessageSetInt, RTMessageKind.IN);
		createInterfaceRealization(protocol, /* @noname nameIn */null, rtMessageSetInt);

		// Create the outgoing UMLRealTime::RTMessageSet interface
		String nameOut = MessageSetUtils.computeInterfaceOutName(name);
		Interface rtMessageSetOutInt = (Interface) createElement(protocol, nameOut, UMLRTElementTypesEnumerator.RT_MESSAGE_SET, Relation.SIBLING);
		setRtMsgKind(rtMessageSetOutInt, RTMessageKind.OUT);
		createUsage(protocol, /* @noname nameOut */ null, rtMessageSetOutInt);

		createElement(protocol, "*", UMLElementTypes.ANY_RECEIVE_EVENT, Relation.SIBLING); //$NON-NLS-1$

		// Create the in-out UMLRealTime::RTMessageSet interface
		String nameInOut = MessageSetUtils.computeInterfaceInOutName(name);
		Interface rtMessageSetInOutInt = (Interface) createElement(protocol, nameInOut, UMLRTElementTypesEnumerator.RT_MESSAGE_SET, Relation.SIBLING);
		setRtMsgKind(rtMessageSetInOutInt, RTMessageKind.IN_OUT);
		createInterfaceRealization(protocol, /* @noname nameInOut */null, rtMessageSetInOutInt);
		createUsage(protocol, /* @noname nameInOut */ null, rtMessageSetInOutInt);

		protocol.setName(name);

		return CommandResult.newOKCommandResult(protocol);
	}


}

