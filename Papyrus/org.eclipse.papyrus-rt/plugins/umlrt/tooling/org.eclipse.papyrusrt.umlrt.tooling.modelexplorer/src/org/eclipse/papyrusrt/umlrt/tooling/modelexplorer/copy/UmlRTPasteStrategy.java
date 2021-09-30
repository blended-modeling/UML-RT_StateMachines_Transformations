/*****************************************************************************
 * Copyright (c) 2015 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Benoit Maggi (CEA LIST) benoit.maggi@cea.fr - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrusrt.umlrt.tooling.modelexplorer.copy;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.papyrus.infra.core.clipboard.IClipboardAdditionalData;
import org.eclipse.papyrus.infra.core.clipboard.PapyrusClipboard;
import org.eclipse.papyrus.infra.gmfdiag.common.strategy.paste.AbstractPasteStrategy;
import org.eclipse.papyrus.infra.gmfdiag.common.strategy.paste.DefaultPasteStrategy;
import org.eclipse.papyrus.infra.gmfdiag.common.strategy.paste.IPasteStrategy;
import org.eclipse.papyrusrt.umlrt.core.utils.MessageUtils;
import org.eclipse.uml2.uml.CallEvent;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Package;

/**
 * Offer a copy/paste strategy for operation in model explorer.
 * It will automatically duplicate the associated call event
 */
public class UmlRTPasteStrategy extends AbstractPasteStrategy implements IPasteStrategy {

	/** The instance. */
	private static IPasteStrategy instance = new UmlRTPasteStrategy();

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
	 * @see org.eclipse.papyrus.infra.gmfdiag.common.strategy.paste.IPasteStrategy#getLabel()
	 */
	@Override
	public String getLabel() {
		return "Uml RT Paste Strategy"; //$NON-NLS-1$
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.papyrus.infra.gmfdiag.common.strategy.paste.IPasteStrategy#getID()
	 */
	@Override
	public String getID() {
		return org.eclipse.papyrusrt.umlrt.core.Activator.PLUGIN_ID + ".UMLRTPasteStrategy"; //$NON-NLS-1$
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.papyrus.infra.gmfdiag.common.strategy.paste.IPasteStrategy#getDescription()
	 */
	@Override
	public String getDescription() {
		return "Copy Call event in model explorer"; //$NON-NLS-1$
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.papyrus.infra.gmfdiag.common.strategy.paste.IPasteStrategy#getSemanticCommand(org.eclipse.emf.edit.domain.EditingDomain,
	 * org.eclipse.emf.ecore.EObject, org.eclipse.papyrus.infra.core.clipboard.PapyrusClipboard)
	 */
	@Override
	public org.eclipse.emf.common.command.Command getSemanticCommand(final EditingDomain domain, final EObject targetOwner, PapyrusClipboard<Object> papyrusClipboard) {
		CompoundCommand compoundCommand = new CompoundCommand("UML-RT : Duplicate Call event when copy/paste an operationl"); //$NON-NLS-1$
		
		Map<Object, ?> additionalDataMap = papyrusClipboard.getAdditionalDataForStrategy(getID());
		if (additionalDataMap != null) {
			Object additionalData = null;


			for (Iterator<Object> iterator = papyrusClipboard.iterator(); iterator.hasNext();) {
				Object object = iterator.next();
				// get target Element
				EObject target = papyrusClipboard.getTragetCopyFromInternalClipboardCopy(object);
				if (target != null && target instanceof Operation) {


					Package nearestPackage = ((org.eclipse.uml2.uml.Element) targetOwner).getNearestPackage();

					// get affiliate additional data
					additionalData = additionalDataMap.get(object);
					if (additionalData instanceof UmlRTClipboardAdditionalData) {
						UmlRTClipboardAdditionalData umlRTClipboardAdditionalData = (UmlRTClipboardAdditionalData) additionalData;
						CallEvent callEvent = umlRTClipboardAdditionalData.getDuplicateCallEvent();
						org.eclipse.emf.common.command.Command command = buildSemanticCommand(domain, callEvent, nearestPackage, (Operation) target);
						compoundCommand.append(command);

					}
				}
			}
		}

		// An empty compound Command can't be executed
		if (compoundCommand.getCommandList().isEmpty()) {
			return null;
		}
		return compoundCommand;
	}


	protected Command buildSemanticCommand(final EditingDomain domain, final CallEvent targetCallEvent, final Package nearestPackage, Operation operation) {
		targetCallEvent.setOperation(operation);
		Command command = AddCommand.create(domain, nearestPackage, null, targetCallEvent);
		
		return command;
	}


	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.papyrus.infra.gmfdiag.common.strategy.paste.IPasteStrategy#dependsOn()
	 */
	@Override
	public IPasteStrategy dependsOn() {
		return DefaultPasteStrategy.getInstance();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.papyrus.infra.gmfdiag.common.strategy.paste.IPasteStrategy#prepare(org.eclipse.papyrus.infra.core.clipboard.PapyrusClipboard)
	 */
	@Override
	public void prepare(PapyrusClipboard<Object> papyrusClipboard, Collection<EObject> selection) {
		Map<Object, IClipboardAdditionalData> mapCopyToClipboardAdditionalData = new HashMap<Object, IClipboardAdditionalData>();		

		for (Iterator<EObject> iterator = papyrusClipboard.iterateOnSource(); iterator.hasNext();) {
			EObject eObjectSource = iterator.next();			

			if (eObjectSource instanceof Operation) {

				CallEvent callEvent = MessageUtils.getCallEvent((Operation) eObjectSource);

				if (callEvent != null) {
					UmlRTClipboardAdditionalData umlRTClipboardAdditionalData = new UmlRTClipboardAdditionalData(callEvent);
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

		/**
		 * @param callEvent the call event to copy
		 */
		public UmlRTClipboardAdditionalData(CallEvent callEvent) {
			this.callEvent = duplicateCallEvent(callEvent);
		}

		/**
		 * @return the duplicated call event 
		 */
		public CallEvent getDuplicateCallEvent() {
			return duplicateCallEvent(this.callEvent);
		}

		/**
		 * @param callEvent to duplicate
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
