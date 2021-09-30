/*****************************************************************************
 * Copyright (c) 2016, 2017 CEA LIST, Christian W. Damus, and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Celine JANSSENS (ALL4TEC) celine.janssens@all4tec.net - Initial API and implementation
 *   Christian W. Damus - bugs 476984, 514413
 *   
 *****************************************************************************/

package org.eclipse.papyrusrt.umlrt.tooling.ui.providers;

import java.util.stream.Stream;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.infra.widgets.providers.EncapsulatedContentProvider;
import org.eclipse.papyrus.infra.widgets.providers.IStaticContentProvider;
import org.eclipse.papyrusrt.umlrt.core.utils.CapsuleUtils;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.ElementImport;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.PackageImport;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.util.UMLSwitch;

/**
 * 
 * Content Provider For Parameter Type
 * It filters the basic content to keep only the rootModel and add the list of default language primitives
 * Required when adding a new parameter to a Protocol Message
 * 
 * @author Céline JANSSENS
 *
 */
public class ParameterTypeContentProvider extends EncapsulatedContentProvider {

	/**
	 * Constructor
	 * 
	 * @param provider
	 *            The encapsulated Content Provider
	 *
	 */
	public ParameterTypeContentProvider(final IStaticContentProvider provider) {
		super(provider);
	}


	/**
	 * @see org.eclipse.papyrus.infra.widgets.providers.EncapsulatedContentProvider#isValidValue(java.lang.Object)
	 *
	 * @param element
	 * @return true if the current element is Valid
	 */
	@Override
	public boolean isValidValue(final Object element) {
		boolean result = super.isValidValue(element);

		if (result) {
			EObject eObject = EMFHelper.getEObject(element);
			result = (eObject instanceof Classifier) && isValidMessageParameterType((Classifier) eObject);
		}

		return result;
	}

	/**
	 * @see org.eclipse.papyrus.infra.widgets.providers.EncapsulatedContentProvider#getElements()
	 *
	 * @return List of root Element
	 */
	@Override
	public Object[] getElements() {
		return Stream.of(super.getElements())
				.filter(this::isNavigable)
				.toArray();
	}


	/**
	 * @see org.eclipse.papyrus.infra.widgets.providers.EncapsulatedContentProvider#getElements(java.lang.Object)
	 *
	 * @param inputElement
	 * @return List of Root Elements
	 */
	@Override
	public Object[] getElements(final Object inputElement) {
		return getElements();
	}

	/**
	 * @see org.eclipse.papyrus.infra.widgets.providers.EncapsulatedContentProvider#getChildren(java.lang.Object)
	 *
	 * @param parentElement
	 * @return The list of Children
	 */
	@Override
	public Object[] getChildren(final Object parentElement) {
		return Stream.of(super.getChildren(parentElement))
				.filter(this::isNavigable)
				.toArray();
	}

	private boolean isNavigable(Object object) {
		EObject element = EMFHelper.getEObject(object);
		return (element != null) &&
				((element instanceof EReference) // Explorer advanced mode
						|| NavigableElementSwitch.NAVIGABLE_ELEMENT.doSwitch(element));
	}

	/**
	 * Queries whether a {@code classifier} is a valid protocol message parameter type.
	 * 
	 * @param classifier
	 *            a classifier
	 * @return whether it is a valid message-parameter type
	 */
	public static boolean isValidMessageParameterType(Classifier classifier) {
		return ValidMessageParameterSwitch.VALID_PARAMETER_TYPE.doSwitch(classifier);
	}

	//
	// Nested types
	//

	private static class ValidMessageParameterSwitch extends UMLSwitch<Boolean> {
		static final ValidMessageParameterSwitch VALID_PARAMETER_TYPE = new ValidMessageParameterSwitch();

		@Override
		public Boolean defaultCase(EObject object) {
			return false;
		}

		@Override
		public Boolean caseClassifier(Classifier object) {
			// Short-circuit before we try more super-cases
			return false;
		}

		@Override
		public Boolean caseClass(Class object) {
			// Must be exactly a class (not some Behavior)
			return (object.eClass() == UMLPackage.Literals.CLASS) && !CapsuleUtils.isCapsule(object);
		}

		@Override
		public Boolean caseEnumeration(Enumeration object) {
			return true;
		}

		@Override
		public Boolean casePrimitiveType(PrimitiveType object) {
			return true;
		}
	}

	private static class NavigableElementSwitch extends ValidMessageParameterSwitch {
		static final NavigableElementSwitch NAVIGABLE_ELEMENT = new NavigableElementSwitch();

		@Override
		public Boolean casePackage(Package object) {
			return true;
		}

		@Override
		public Boolean casePackageImport(PackageImport object) {
			return true;
		}

		@Override
		public Boolean caseElementImport(ElementImport object) {
			return true;
		}
	}
}


