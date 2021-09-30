/*****************************************************************************
 * Copyright (c) 2016 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *   
 *****************************************************************************/

package org.eclipse.papyrusrt.umlrt.tooling.ui.widgets;

import java.util.stream.Stream;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.uml.tools.providers.UMLContentProvider;
import org.eclipse.papyrusrt.umlrt.core.utils.CapsulePartUtils;
import org.eclipse.papyrusrt.umlrt.core.utils.RTPortUtils;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.ElementImport;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.PackageImport;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.util.UMLSwitch;

public class ConstantContentProvider extends UMLContentProvider {


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

	@Override
	public boolean isValidValue(Object object) {
		EObject element = EMFHelper.getEObject(object);
		// only constant could be selected: constant is represented as class property with default value
		if (!(element instanceof Property)) {
			return false;
		} else {
			// do not allow to select Capsule Part or RT Port
			if (CapsulePartUtils.isCapsulePart((Property) element) || (RTPortUtils.isRTPort(element))) {
				return false;
			} else {
				return ((Property) element).eIsSet(UMLPackage.eINSTANCE.getProperty_DefaultValue());
			}
		}

	}


	private boolean isNavigable(Object object) {
		EObject element = EMFHelper.getEObject(object);

		return element != null && NavigableElementSwitch.NAVIGABLE_ELEMENT.doSwitch(element);
	}

	private static class NavigableElementSwitch extends UMLSwitch<Boolean> {
		static final NavigableElementSwitch NAVIGABLE_ELEMENT = new NavigableElementSwitch();

		@Override
		public Boolean defaultCase(EObject object) {
			return false;
		}

		@Override
		public Boolean caseProperty(Property object) {
			return true;
		}

		@Override
		public Boolean casePackage(Package object) {
			return true;
		}

		@Override
		public Boolean caseClassifier(Classifier object) {
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