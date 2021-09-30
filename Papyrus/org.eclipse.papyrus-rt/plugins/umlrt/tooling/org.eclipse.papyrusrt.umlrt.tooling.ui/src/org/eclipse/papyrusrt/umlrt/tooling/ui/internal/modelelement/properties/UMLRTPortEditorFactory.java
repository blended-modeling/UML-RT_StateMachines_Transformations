/*****************************************************************************
 * Copyright (c) 2016, 2017 Christian W. Damus and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *   Christian W. Damus - bugs 467545, 507552
 *   
 *****************************************************************************/

package org.eclipse.papyrusrt.umlrt.tooling.ui.internal.modelelement.properties;

import java.util.Set;

import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest;
import org.eclipse.papyrus.infra.properties.contexts.View;
import org.eclipse.papyrus.infra.properties.ui.creation.CreationContext;
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.UMLRealTimePackage;
import org.eclipse.papyrusrt.umlrt.tooling.ui.internal.types.UMLRTUIElementTypesEnumerator;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsule;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTPort;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Port;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.util.UMLUtil.StereotypeApplicationHelper;

/**
 * Property-editor factory for the ports of a capsule.
 */
public class UMLRTPortEditorFactory extends FacadeListPropertyEditorFactory<UMLRTPort> {

	private static final String PORT_DIALOG = "RTPort Creation Dialog"; //$NON-NLS-1$

	public UMLRTPortEditorFactory(EReference referenceIn) {
		this(referenceIn, null);
	}

	public UMLRTPortEditorFactory(EReference referenceIn, IObservableList<UMLRTPort> modelProperty) {
		super(referenceIn, modelProperty);
	}

	@Override
	protected CreationContext basicGetCreationContext(Object element) {
		if (element instanceof UMLRTCapsule) {
			element = ((UMLRTCapsule) element).toUML();
		}

		return super.basicGetCreationContext(element);
	}

	@Override
	protected IEditCommandRequest getCustomCreationRequest(EObject container) {
		CreateElementRequest result = null;

		IElementType portCreationType = UMLRTUIElementTypesEnumerator.PORT_CREATION_WITH_UI;
		if (portCreationType != null) {
			result = new CreateElementRequest(container, portCreationType, UMLPackage.Literals.STRUCTURED_CLASSIFIER__OWNED_ATTRIBUTE);
		}

		return result;
	}

	@Override
	protected void applyStereotypes(Element newElement) {
		super.applyStereotypes(newElement);

		if (newElement instanceof Port) {
			StereotypeApplicationHelper.getInstance(newElement).applyStereotype(newElement,
					UMLRealTimePackage.Literals.RT_PORT);
		}
	}

	@Override
	public String getCreationDialogTitle() {
		return "Create New Port";
	}

	@Override
	public String getEditionDialogTitle(Object objectToEdit) {
		return "Edit Port";
	}

	@Override
	protected Set<View> getCreationDialogViews() {
		return loadDialogViews(PORT_DIALOG);
	}
}
