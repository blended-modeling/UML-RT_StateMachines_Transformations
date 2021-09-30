/*****************************************************************************
 * Copyright (c) 2016, 2017 CEA LIST, Christian W. Damus, and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *   Christian W. Damus - bugs 467545, 507552, 507282
 *   
 *****************************************************************************/

package org.eclipse.papyrusrt.umlrt.tooling.ui.internal.modelelement.properties;

import static org.eclipse.papyrus.uml.tools.utils.NamedElementUtil.getDefaultNameWithIncrementFromBase;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.papyrus.infra.properties.contexts.Context;
import org.eclipse.papyrus.infra.properties.contexts.View;
import org.eclipse.papyrus.infra.properties.ui.creation.CreationContext;
import org.eclipse.papyrus.views.properties.runtime.ConfigurationManager;
import org.eclipse.papyrusrt.umlrt.core.utils.ProtocolUtils;
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.RTMessageKind;
import org.eclipse.papyrusrt.umlrt.tooling.ui.Messages;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocol;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTProtocolMessage;
import org.eclipse.swt.widgets.Control;
import org.eclipse.uml2.uml.Collaboration;
import org.eclipse.uml2.uml.Operation;

/**
 * Property-editor factory for one of the message kinds of a protocol.
 */
public class UMLRTProtocolMessageEditorFactory extends FacadeListPropertyEditorFactory<UMLRTProtocolMessage> {

	public static final String SINGLE_PROTOCOL_MESSAGE = "Single ProtocolMessage"; //$NON-NLS-1$

	public static final String UML_RT = "uml-rt"; //$NON-NLS-1$

	private final RTMessageKind kind;

	public UMLRTProtocolMessageEditorFactory(EReference referenceIn, RTMessageKind kind, IObservableList<UMLRTProtocolMessage> modelProperty) {
		super(referenceIn, modelProperty);

		this.kind = kind;
	}

	@Override
	protected CreationContext basicGetCreationContext(Object element) {
		if (element instanceof UMLRTProtocol) {
			element = basicGetCreationContext(((UMLRTProtocol) element).toUML());
		} else if (element instanceof Collaboration) {
			element = ProtocolUtils.getMessageSet((Collaboration) element, kind);
		}

		return super.basicGetCreationContext(element);
	}

	@Override
	public String getCreationDialogTitle() {
		switch (kind) {
		case OUT:
			return Messages.MessageSetOwnedProtocolMessageValueFactory_CreateOutProtocolMessageDialogTitle;

		case IN:
			return Messages.MessageSetOwnedProtocolMessageValueFactory_CreateInProtocolMessageDialogTitle;

		case IN_OUT:
			return Messages.MessageSetOwnedProtocolMessageValueFactory_CreateInOutProtocolMessageDialogTitle;

		default:
			return Messages.MessageSetOwnedProtocolMessageValueFactory_CreateDialogTitle;
		}
	}

	@Override
	public String getEditionDialogTitle(Object objectToEdit) {
		switch (kind) {
		case OUT:
			return Messages.MessageSetOwnedProtocolMessageValueFactory_EditOutProtocolMessageDialogTitle;

		case IN:
			return Messages.MessageSetOwnedProtocolMessageValueFactory_EditInProtocolMessageDialogTitle;

		case IN_OUT:
			return Messages.MessageSetOwnedProtocolMessageValueFactory_EditInOutProtocolMessageDialogTitle;

		default:
			return Messages.MessageSetOwnedProtocolMessageValueFactory_EditDialogTitle;
		}
	}

	@Override
	protected Object doCreateObject(Control widget, Object context) {
		UMLRTProtocol protocol = (UMLRTProtocol) asUMLRT(context);
		Set<Operation> others = protocol.getMessages(kind, true).stream()
				.map(UMLRTProtocolMessage::toUML)
				.collect(Collectors.toSet());

		StringBuilder baseName = new StringBuilder(15);
		baseName.append(kind.getLiteral()).append("ProtocolMessage");
		baseName.setCharAt(0, Character.toUpperCase(baseName.charAt(0)));
		String autoName = getDefaultNameWithIncrementFromBase(baseName.toString(), others);

		UMLRTProtocolMessage result = protocol.createMessage(kind, autoName);
		return createObject(widget, context, asUML(result));
	}

	@Override
	protected Set<View> getCreationDialogViews() {
		String contextName = UML_RT;
		String viewName = SINGLE_PROTOCOL_MESSAGE;
		Context context = ConfigurationManager.getInstance().getContext(contextName);
		for (View view : context.getViews()) {
			if (viewName.equals(view.getName())) {
				return Collections.singleton(view);
			}
		}
		return Collections.emptySet();
	}
}
