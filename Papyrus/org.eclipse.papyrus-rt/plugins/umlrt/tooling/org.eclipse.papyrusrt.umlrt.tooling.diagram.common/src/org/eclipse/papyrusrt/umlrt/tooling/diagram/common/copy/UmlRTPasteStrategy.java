/*****************************************************************************
 * Copyright (c) 2015, 2017 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Benoit Maggi (CEA LIST) benoit.maggi@cea.fr - Initial API and implementation
 *  Celine Janssens (ALL4TEC) celine.janssens@all4tec.net - 
 *  Christian W. Damus - bugs 510323, 510189
 *****************************************************************************/
package org.eclipse.papyrusrt.umlrt.tooling.diagram.common.copy;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.IClientContext;
import org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.MoveRequest;
import org.eclipse.papyrus.infra.core.clipboard.IClipboardAdditionalData;
import org.eclipse.papyrus.infra.core.clipboard.PapyrusClipboard;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.emf.gmf.command.GMFtoEMFCommandWrapper;
import org.eclipse.papyrus.infra.gmfdiag.common.strategy.paste.AbstractPasteStrategy;
import org.eclipse.papyrus.infra.gmfdiag.common.strategy.paste.DefaultPasteStrategy;
import org.eclipse.papyrus.infra.gmfdiag.common.strategy.paste.IPasteStrategy;
import org.eclipse.papyrus.infra.services.edit.context.TypeContext;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.papyrus.infra.services.edit.service.IElementEditService;
import org.eclipse.papyrusrt.umlrt.core.utils.MessageUtils;
import org.eclipse.papyrusrt.umlrt.core.utils.ProtocolUtils;
import org.eclipse.papyrusrt.umlrt.core.utils.RTMessageUtils;
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.RTMessageKind;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.Activator;
import org.eclipse.uml2.uml.CallEvent;
import org.eclipse.uml2.uml.Collaboration;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Package;

/**
 * Offer a copy/paste strategy for operation in model explorer. It will
 * automatically duplicate the associated call event
 */
public class UmlRTPasteStrategy extends AbstractPasteStrategy implements IPasteStrategy {

	/** The instance. */
	private static IPasteStrategy instance = new UmlRTPasteStrategy();

	private PapyrusClipboard<Object> papyrusClipboard = null;

	/**
	 * Gets the single instance of UmlRTPasteStrategy.
	 *
	 * @return single instance of UmlRTPasteStrategy
	 */
	public static IPasteStrategy getInstance() {
		return instance;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.eclipse.papyrus.infra.gmfdiag.common.strategy.paste.IPasteStrategy#
	 * getLabel()
	 */
	@Override
	public String getLabel() {
		return "Uml RT Paste Strategy"; //$NON-NLS-1$
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.eclipse.papyrus.infra.gmfdiag.common.strategy.paste.IPasteStrategy#
	 * getID()
	 */
	@Override
	public String getID() {
		return Activator.PLUGIN_ID + ".UMLRTPasteStrategy"; //$NON-NLS-1$
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.eclipse.papyrus.infra.gmfdiag.common.strategy.paste.IPasteStrategy#
	 * getDescription()
	 */
	@Override
	public String getDescription() {
		return "Copy Call event in model explorer"; //$NON-NLS-1$
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.eclipse.papyrus.infra.gmfdiag.common.strategy.paste.IPasteStrategy#
	 * getSemanticCommand(org.eclipse.emf.edit.domain.EditingDomain,
	 * org.eclipse.emf.ecore.EObject,
	 * org.eclipse.papyrus.infra.core.clipboard.PapyrusClipboard)
	 */
	@Override
	public org.eclipse.emf.common.command.Command getSemanticCommand(final EditingDomain domain, final EObject targetOwner, PapyrusClipboard<Object> papyrusClipboard) {

		CompoundCommand compoundCommand = new CompoundCommand(); // $NON-NLS-1$
		this.papyrusClipboard = papyrusClipboard;
		if (papyrusClipboard.size() > 0) {
			if (ProtocolUtils.isProtocol(targetOwner)) {
				compoundCommand.setLabel("UML-RT : Paste into a Protocol"); //$NON-NLS-1$
				Command protocolTargetCommand = getSemanticCommandForProtocolTarget((Collaboration) targetOwner);
				if (null != protocolTargetCommand) {
					compoundCommand.append(getSemanticCommandForProtocolTarget((Collaboration) targetOwner));
				}
			}
		}

		if (compoundCommand.isEmpty()) {
			compoundCommand = null;
		}
		return compoundCommand;

	}


	/**
	 * Get the command to paste on the Protocol Target
	 * 
	 * @param targetOwner
	 *            The Protocol on which the paste is done
	 * @return the command that paste object on the Protocol
	 */
	protected Command getSemanticCommandForProtocolTarget(final Collaboration targetOwner) {

		// For each element into the ClipBoard copy, get the associated Command
		Iterator<?> iter = papyrusClipboard.iterateOnSource();
		CompoundCommand compoundCommand = new CompoundCommand("Paste on Protocol");
		while (iter.hasNext()) {
			Object object = iter.next();
			if ((object instanceof EObject) && (RTMessageUtils.isRTMessage((EObject) object))) {
				compoundCommand.append(getOperationSemanticCommand(targetOwner, (Operation) object));
			}
		}

		// An empty compound Command can't be executed
		if (compoundCommand.isEmpty()) {
			compoundCommand = null;
		}

		return compoundCommand;
	}

	/**
	 * Get the semantic command in case of Operation Duplication
	 * 
	 * @param targetOwner
	 *            The Target of the paste
	 * @param object
	 *            The Operation to duplicate
	 * @return the Command that duplicate the Operation and that creates the associated Call Event.
	 */
	protected Command getOperationSemanticCommand(final EObject targetOwner, Operation initialOperation) {
		CompoundCommand command = new CompoundCommand();

		// the source Operation
		Operation originalOperation = initialOperation;
		// the internal copy
		Operation internalOperation = (Operation) papyrusClipboard.getCopyFromSource(originalOperation);
		// The target Copy
		Operation newOperation = (Operation) papyrusClipboard.getInternalClipboardToTargetCopy().get(internalOperation);

		// The kind of the original Operation
		RTMessageKind kind = getRTAdditionalData(internalOperation).getMessageKind();

		// 1) Get the paste Command for the duplicated Operation
		Command pasteOperationCommand = getPasteOperationCommand(targetOwner, newOperation, kind);

		// 2) Get the paste command for the duplicated CallEvent
		Command createCallEventCommand = getCreateCallEventCommand(targetOwner, internalOperation);

		command.append(pasteOperationCommand);
		command.append(createCallEventCommand);

		return command;


	}

	/**
	 * Get the Command that duplicates the Operation and add it into the
	 * corresponding RTMessageSet.
	 * 
	 * @param targetOwner
	 *            The initial target (here we expected type is a Collaboration as a Protocol )
	 * @param operation
	 *            The Clipboard where the duplicated object are stored.
	 * @param kind
	 * @return The command that Paste the duplicated Operation
	 */
	protected Command getPasteOperationCommand(final EObject targetOwner, final Operation newOperation, RTMessageKind kind) {
		CompoundCommand pasteCommand = new CompoundCommand("Paste Operation Command");

		// The targetOwner is a Protocol , but the real target should be the RTMessageSet related to the kind of the initial Operation
		EObject realTarget = getRealTarget(kind, targetOwner);

		// Build of the move request
		MoveRequest moveRequest = new MoveRequest(realTarget, newOperation);
		pasteCommand.append(getEditCommand(moveRequest, realTarget));

		return pasteCommand;

	}


	/**
	 * Get the Command that duplicates and add the Call Event into the
	 * ProtocolContainer
	 * 
	 * @param domain
	 * @param targetOwner
	 *            The Target of the Paste
	 * @param papyrusClipboard
	 *            The ClipBoard initiated during the copy/paste
	 * @return The Command that duplicates and add the Call Event into the
	 *         ProtocolContainer
	 */
	protected Command getCreateCallEventCommand(final EObject targetOwner, Operation internalOperation) {
		CompoundCommand compoundCommand = new CompoundCommand();

		UmlRTClipboardAdditionalData additionalData = null;

		Package nearestPackage = ((Collaboration) targetOwner).getNearestPackage();

		// get affiliate additional data
		additionalData = getRTAdditionalData(internalOperation);
		if (null != additionalData) {
			CallEvent callEvent = additionalData.getDuplicateCallEvent();
			org.eclipse.emf.common.command.Command command = getMoveCallEventCommand(callEvent, nearestPackage, (Operation) papyrusClipboard.getTragetCopyFromInternalClipboardCopy(internalOperation));
			compoundCommand.append(command);
		}

		return compoundCommand;

	}


	/**
	 * Get the Real Container of the paste Operation
	 * 
	 * @param originalKind
	 *            the Kind of the duplicate Operation
	 * @param targetOwner
	 *            The initial TargetOwner of the Operation
	 * @return the associated MessageSet
	 */
	protected EObject getRealTarget(final RTMessageKind originalKind, final EObject targetOwner) {
		EObject messageSet = null;
		if (targetOwner instanceof Collaboration && null != originalKind) {
			messageSet = ProtocolUtils.getMessageSet(((Collaboration) targetOwner), originalKind);
		}
		return messageSet;
	}

	/**
	 * Get the Additional Data related to the duplicated operation.
	 * 
	 * @param papyrusClipboard
	 *            The ClipBorad where is stored all the additional data.
	 * @param operation
	 *            The Object key required to retrieve the associated Additional Data (here it is the internal copy of the Operation )
	 * @return The associated UmlRTClipboardAdditionalData.
	 */
	protected UmlRTClipboardAdditionalData getRTAdditionalData(final Object operation) {
		UmlRTClipboardAdditionalData additional = null;
		Map<?, ?> additionnalData = papyrusClipboard.getAdditionalDataForStrategy(getID());
		Object umlRTAdditionnalData = additionnalData.get(operation);
		if (umlRTAdditionnalData instanceof UmlRTClipboardAdditionalData) {
			additional = (UmlRTClipboardAdditionalData) umlRTAdditionnalData;
		}

		return additional;
	}

	/**
	 * Set the Operation and Build the command to Paste the CallEvent into the Container.
	 * 
	 * @param targetCallEvent
	 *            The Call Event to be set and moved
	 * @param container
	 *            The Container that should owned the CallEvent (ProtocolContainer)
	 * @param operation
	 *            The operation which is referred by the CallEvent as ownedOperations
	 * @return the Command that move the Call Event into the Container
	 */
	protected Command getMoveCallEventCommand(final CallEvent targetCallEvent, final Package container, Operation operation) {
		Command callEventMoveCommand = null;
		if (null != targetCallEvent) {
			targetCallEvent.setOperation(operation);
			// Add the Call Event into the PrtotocolContainer
			MoveRequest moveRequest = new MoveRequest(container, targetCallEvent);
			callEventMoveCommand = getEditCommand(moveRequest, container);
		}

		if ((callEventMoveCommand != null) && !callEventMoveCommand.canExecute()) {
			callEventMoveCommand = null;
		}

		return callEventMoveCommand;
	}


	/**
	 * Get Edit Command related to the request and the passed object.
	 * 
	 * @param request
	 *            the Edit Request
	 * @param objectProvider
	 *            the object of which the provider is retrieved to get the EditCommand
	 * @return The Edit Command wrapped into EMF command.
	 */
	protected Command getEditCommand(IEditCommandRequest request, Object objectProvider) {
		Command command = null;
		try {
			IClientContext context = TypeContext.getContext(request.getEditingDomain());
			IElementEditService provider = ElementEditServiceUtils.getCommandProvider(objectProvider, context);
			if (null != provider) {
				ICommand editCommand = provider.getEditCommand(request);
				if (editCommand.canExecute()) {
					command = GMFtoEMFCommandWrapper.wrap(editCommand);
				}
			}
		} catch (ServiceException e) {
			Activator.log.error(e);
		}
		return command;

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.eclipse.papyrus.infra.gmfdiag.common.strategy.paste.IPasteStrategy#
	 * dependsOn()
	 */
	@Override
	public IPasteStrategy dependsOn() {
		return DefaultPasteStrategy.getInstance();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.eclipse.papyrus.infra.gmfdiag.common.strategy.paste.IPasteStrategy#
	 * prepare(org.eclipse.papyrus.infra.core.clipboard.PapyrusClipboard)
	 */
	@Override
	public void prepare(final PapyrusClipboard<Object> papyrusClipboard, final Collection<EObject> selection) {
		Map<Object, IClipboardAdditionalData> mapCopyToClipboardAdditionalData = new HashMap<>();

		for (Iterator<EObject> iterator = papyrusClipboard.iterateOnSource(); iterator.hasNext();) {
			EObject eObjectSource = iterator.next();

			if (eObjectSource instanceof Operation) {

				CallEvent callEvent = MessageUtils.getCallEvent((Operation) eObjectSource);

				if (null != callEvent) {
					UmlRTClipboardAdditionalData umlRTClipboardAdditionalData = new UmlRTClipboardAdditionalData(callEvent, RTMessageUtils.getMessageKind(eObjectSource));
					Object copy = papyrusClipboard.getCopyFromSource(eObjectSource);
					mapCopyToClipboardAdditionalData.put(copy, umlRTClipboardAdditionalData);
				}
			}
		}
		papyrusClipboard.pushAdditionalData(getID(), mapCopyToClipboardAdditionalData);
	}


	protected class UmlRTClipboardAdditionalData implements IClipboardAdditionalData {

		/** The callEvent. */
		protected CallEvent callEvent;

		/** The Message Direction. */
		protected RTMessageKind messagekind;

		/**
		 * @param callEvent
		 *            the call event to copy
		 */
		public UmlRTClipboardAdditionalData(CallEvent callEvent, RTMessageKind kind) {
			this.callEvent = callEvent;
			this.messagekind = kind;
		}

		/**
		 * @return the duplicated call event
		 */
		public CallEvent getDuplicateCallEvent() {
			return duplicateCallEvent(callEvent);
		}

		/**
		 * @return the duplicated call event
		 */
		public RTMessageKind getMessageKind() {
			return messagekind;
		}

		/**
		 * @param callEvent
		 *            to duplicate
		 * @return duplicated callEvent
		 */
		protected CallEvent duplicateCallEvent(CallEvent callEvent) {
			EcoreUtil.Copier copier = new EcoreUtil.Copier();

			copier.copy(callEvent);
			copier.copyReferences();

			EObject copy = copier.get(callEvent);

			if (copy instanceof CallEvent) {
				return (CallEvent) copy;
			}

			return null;
		}
	}
}
