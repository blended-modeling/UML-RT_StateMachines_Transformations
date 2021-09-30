/*****************************************************************************
 * Copyright (c) 2015 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrusrt.umlrt.core.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.ISpecializationType;
import org.eclipse.gmf.runtime.emf.type.core.commands.MoveElementsCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.MoveRequest;
import org.eclipse.papyrusrt.umlrt.core.types.IUMLRTElementTypes;
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.RTMessageKind;
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.RTMessageSet;
import org.eclipse.uml2.uml.Collaboration;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.util.UMLUtil;

/**
 * Utility class for PackageContainers
 */
public class ProtocolContainerUtils {

	/**
	 * @param protocolContainer
	 * @param direction
	 * @param showInherited
	 * @return
	 */
	public static List<Operation> getRTMessages(Package protocolContainer, RTMessageKind direction, boolean showInherited) {
		Interface messageSet = getMessageSet(protocolContainer, direction);
		if (messageSet != null) {
			
			if (showInherited) {
				return messageSet.getAllOperations(); // this will also display inherited operations
			}
			return messageSet.getOwnedOperations(); // only contained
		}
		return Collections.emptyList();
	}

	/**
	 * @param protocolContainer
	 * @param direction
	 * @return
	 */
	public static List<Operation> getAllRTMessages(Package protocolContainer, RTMessageKind direction) {
		return getRTMessages(protocolContainer, direction, true);
	}
	
	/**
	 * Returns all the In Operations attached to the protocol in this protocol container
	 * 
	 * @return all the In Operations attached to the protocol in this protocol container or an empty list if there was no protocol/interfacein.
	 */
	public static List<Operation> getAllInRTMessages(Package protocolContainer) {
		return getAllRTMessages(protocolContainer, RTMessageKind.IN);
	}

	/**
	 * Returns all the Out Operations attached to the protocol in this protocol container
	 * 
	 * @return all the Out Operations attached to the protocol in this protocol container or <code>null</code> if there was no protocol/interfacein.
	 */
	public static Collection<Operation> getAllOutRTMessages(Package protocolContainer) {
		return getAllRTMessages(protocolContainer, RTMessageKind.OUT);
	}

	/**
	 * Returns all the InOut Operations attached to the protocol in this protocol container
	 * 
	 * @return all the InOut Operations attached to the protocol in this protocol container or <code>null</code> if there was no protocol/interfacein.
	 */
	public static Collection<Operation> getAllInOutRTMessages(Package protocolContainer) {
		return getAllRTMessages(protocolContainer, RTMessageKind.IN_OUT);
	}



	/**
	 * @param protocolContainer
	 * @param direction
	 * @param showInherited
	 * @return
	 */
	public static List<Operation> getRTMessages(Package protocolContainer, RTMessageKind direction) {
		return getRTMessages(protocolContainer, direction, false);
	}

	/**
	 * Returns all the In Operations attached to the protocol in this protocol container
	 * 
	 * @return all the In Operations attached to the protocol in this protocol container or an empty list if there was no protocol/interfacein.
	 */
	public static List<Operation> getInRTMessages(Package protocolContainer) {
		return getRTMessages(protocolContainer, RTMessageKind.IN);
	}

	/**
	 * Returns all the Out Operations attached to the protocol in this protocol container
	 * 
	 * @return all the Out Operations attached to the protocol in this protocol container or <code>null</code> if there was no protocol/interfacein.
	 */
	public static Collection<Operation> geOutRTMessages(Package protocolContainer) {
		return getRTMessages(protocolContainer, RTMessageKind.OUT);
	}

	/**
	 * Returns all the InOut Operations attached to the protocol in this protocol container
	 * 
	 * @return all the InOut Operations attached to the protocol in this protocol container or <code>null</code> if there was no protocol/interfacein.
	 */
	public static Collection<Operation> getInOutRTMessages(Package protocolContainer) {
		return getRTMessages(protocolContainer, RTMessageKind.IN_OUT);
	}

	public static Interface getMessageSet(Package protocolContainer, RTMessageKind messageKind) {
		for (PackageableElement packageableElement : protocolContainer.getPackagedElements()) {
			// look each interface to find the right one with the stereotype message set
			if (packageableElement instanceof Interface) {
				RTMessageSet messageSet = UMLUtil.getStereotypeApplication(packageableElement, RTMessageSet.class);
				if (messageSet != null && messageKind != null && messageKind.equals(messageSet.getRtMsgKind())) {
					return (Interface) packageableElement;
				}
			}
		}
		return null;
	}


	public static Interface getMessageSetIn(Package protocolContainer) {
		return getMessageSet(protocolContainer, RTMessageKind.IN);
	}

	public static Interface getMessageSetOut(Package protocolContainer) {
		return getMessageSet(protocolContainer, RTMessageKind.OUT);
	}

	public static Interface getMessageSetInOut(Package protocolContainer) {
		return getMessageSet(protocolContainer, RTMessageKind.IN_OUT);
	}

	public static Collaboration getCollaboration(Package protocolContainer) {
		for (PackageableElement packageableElement : protocolContainer.getPackagedElements()) {
			// look each interface to find the right one with the stereotype message set
			if (packageableElement instanceof Collaboration) {
				return (Collaboration) packageableElement;
			}
		}
		return null;
	}

	public static boolean containsProtocolContainer(Package container) {
		Collection<Package> packages = EcoreUtil.getObjectsByType(container.getPackagedElements(), UMLPackage.eINSTANCE.getPackage());

		if (packages != null && !packages.isEmpty()) {
			IElementType type = ElementTypeRegistry.getInstance().getType(IUMLRTElementTypes.PROTOCOL_CONTAINER_ID);
			if (!(type instanceof ISpecializationType)) { // check at the same time UMLRT element types are correctly loaded
				return false;
			}

			for (Package pkg : packages) {
				// check the package is a protocol container
				if (((ISpecializationType) type).getMatcher().matches(pkg)) {
					return true;
				}
			}
		}
		return false;
	}

	public static List<Package> getContainedProtocolContainers(Package container) {
		Collection<Package> packages = EcoreUtil.getObjectsByType(container.getPackagedElements(), UMLPackage.eINSTANCE.getPackage());

		if (packages != null && !packages.isEmpty()) {
			IElementType type = ElementTypeRegistry.getInstance().getType(IUMLRTElementTypes.PROTOCOL_CONTAINER_ID);
			if (!(type instanceof ISpecializationType)) { // check at the same time UMLRT element types are correctly loaded
				return Collections.emptyList();
			}

			List<Package> protocolContainers = null;
			for (Package pkg : packages) {
				// check the package is a protocol container
				if (((ISpecializationType) type).getMatcher().matches(pkg)) {
					if (protocolContainers == null) {
						protocolContainers = new ArrayList<Package>();
					}
					protocolContainers.add(pkg);
				}
			}
		}
		return Collections.emptyList();
	}


	public static boolean isProtocolContainer(EObject package_) {
		IElementType type = ElementTypeRegistry.getInstance().getType(IUMLRTElementTypes.PROTOCOL_CONTAINER_ID);
		if (!(type instanceof ISpecializationType)) { // check at the same time UMLRT element types are correctly loaded
			return false;
		}
		if (((ISpecializationType) type).getMatcher().matches(package_)) {
			return true;
		}
		return false;
	}

	/**
	 * REturns the packageElements, but with package protocolcontainers replaced by protocol
	 * 
	 * @param source
	 * @return
	 */
	public static List<PackageableElement> getFilteredPackagedElements(Element source) {
		if (!(source instanceof Package)) {
			return Collections.emptyList();
		}

		if (!containsProtocolContainer((Package) source)) {
			return ((Package) source).getPackagedElements();
		}

		List<PackageableElement> returnList = new ArrayList<PackageableElement>(((Package) source).getPackagedElements());

		for (PackageableElement packageableElement : ((Package) source).getPackagedElements()) {
			if (packageableElement instanceof Package) {
				if ((isProtocolContainer(packageableElement))) {
					// replace element in return list
					int i = returnList.indexOf(packageableElement);
					returnList.remove(packageableElement);
					returnList.add(i, ProtocolContainerUtils.getCollaboration((Package) packageableElement));
				}
			}
		}
		return returnList;
	}

	/**
	 * Returns ProtocolContainer of a given UML::PackagableElement.
	 * @param element
	 * @return
	 */
	public static Package getProtocolContainer(PackageableElement element) {
		return element.getNearestPackage();
	}
	
	/**
	 * Returns ProtocolContainer of a given EObject.
	 * @param eObject
	 * @return
	 */
	public static EObject getProtocolContainer(EObject eObject) {
		EObject result = null;
		
		if (eObject instanceof Package) {
			result = eObject;
		} else {
			result = getProtocolContainer(eObject.eContainer());
		}		
		
		return result;
	}
	
	public static MoveElementsCommand createMoveProtocolContainerCommand(final MoveRequest request, final EObject protocolContainerToMove) {
		EObject targetContainer = ProtocolContainerUtils.getProtocolContainer(request.getTargetContainer());
		MoveRequest moveRequest = new MoveRequest(targetContainer, protocolContainerToMove);
		MoveElementsCommand command = new MoveElementsCommand(moveRequest);
		return command;		
	}

}
