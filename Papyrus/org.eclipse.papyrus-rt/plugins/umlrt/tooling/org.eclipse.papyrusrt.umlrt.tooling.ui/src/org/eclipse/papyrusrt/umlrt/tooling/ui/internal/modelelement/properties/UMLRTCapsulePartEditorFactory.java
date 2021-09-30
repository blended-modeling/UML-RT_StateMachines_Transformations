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
import org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsulePart;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.util.UMLUtil.StereotypeApplicationHelper;

/**
 * Property-editor factory for the capsule-parts of a capsule.
 */
public class UMLRTCapsulePartEditorFactory extends FacadeListPropertyEditorFactory<UMLRTCapsulePart> {

	private static final String CAPSULE_PART_DIALOG = "CapsulePart Creation Dialog"; //$NON-NLS-1$

	public UMLRTCapsulePartEditorFactory(EReference referenceIn, IObservableList<UMLRTCapsulePart> modelProperty) {
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

		IElementType partCreationType = UMLRTUIElementTypesEnumerator.CAPSULE_PART_CREATION_WITH_UI;
		if (partCreationType != null) {
			result = new CreateElementRequest(container, partCreationType, UMLPackage.Literals.STRUCTURED_CLASSIFIER__OWNED_ATTRIBUTE);
		}

		return result;
	}

	@Override
	protected void applyStereotypes(Element newElement) {
		super.applyStereotypes(newElement);

		if (newElement instanceof Property) {
			StereotypeApplicationHelper.getInstance(newElement).applyStereotype(newElement,
					UMLRealTimePackage.Literals.CAPSULE_PART);
		}
	}

	@Override
	public String getCreationDialogTitle() {
		return "Create New Capsule Part";
	}

	@Override
	public String getEditionDialogTitle(Object objectToEdit) {
		return "Edit Capsule Part";
	}

	@Override
	protected Set<View> getCreationDialogViews() {
		return loadDialogViews(CAPSULE_PART_DIALOG);
	}
}
