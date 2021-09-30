/*****************************************************************************
 * Copyright (c) 2016 CEA LIST and others.
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

package org.eclipse.papyrusrt.umlrt.tooling.diagram.statemachine.internal.providers;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.gmf.runtime.common.core.service.IOperation;
import org.eclipse.gmf.runtime.common.ui.services.parser.GetParserOperation;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParser;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParserProvider;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.TransitionNameEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.providers.UMLParserProvider;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.statemachine.internal.editparts.RTStateMachineDiagramVisualID;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.statemachine.internal.parsers.TransitionLabelParser;

/**
 * ParserProvider for the RT state Machine diagram.
 */
public class RTStateMachineParserProvider extends UMLParserProvider implements IParserProvider {

	/** Instance of the Parser for Internal Transition label representation. */
	private TransitionLabelParser internalTransitionParser;

	/** Instance of the Parser for Transition label representation. */
	private TransitionLabelParser transitionParser;

	/**
	 * Constructor.
	 */
	public RTStateMachineParserProvider() {
		// empty
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean provides(IOperation operation) {
		if (operation instanceof GetParserOperation) {
			IAdaptable hint = ((GetParserOperation) operation).getHint();
			String vid = hint.getAdapter(String.class);
			if (RTStateMachineDiagramVisualID.INTERNAL_TRANSITION_LABEL.equals(vid)) {
				return true;
			} else if (TransitionNameEditPart.VISUAL_ID.equals(vid)) {
				return true;
			}
		}
		return super.provides(operation);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IParser getParser(IAdaptable hint) {
		String vid = hint.getAdapter(String.class);
		if (RTStateMachineDiagramVisualID.INTERNAL_TRANSITION_LABEL.equals(vid)) {
			if (internalTransitionParser == null) {
				internalTransitionParser = new TransitionLabelParser();
			}
			return internalTransitionParser;
		} else if (TransitionNameEditPart.VISUAL_ID.equals(vid)) {
			if (transitionParser == null) {
				transitionParser = new TransitionLabelParser();
			}
			return transitionParser;
		}
		return super.getParser(hint);
	}

}
