/*****************************************************************************
 * Copyright (c) 2014 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  CEA LIST - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrusrt.umlrt.core.tests.creation;

import static org.junit.Assert.assertEquals;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrusrt.umlrt.core.tests.utils.IValidationRule;
import org.eclipse.papyrusrt.umlrt.core.types.ElementTypeUtils;
import org.eclipse.papyrusrt.umlrt.core.types.IUMLRTElementTypes;
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.Capsule;
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.CapsulePart;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.VisibilityKind;
import org.junit.Assert;

/**
 * Validation Rule for {@link CapsulePart} creation in a {@link Capsule}
 */
public class CreateCapsulePartInCapsuleValidation extends IValidationRule.Stub {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void validatePostEdition(Element targetElement, Object[] commandResults) throws Exception {
		Assert.assertEquals("comand result should not be empty", 1, commandResults.length);
		Object result = commandResults[0];
		Assert.assertTrue("new element should be a Property", result instanceof Property);
		Assert.assertTrue("new element should be a capsulePart", ElementTypeUtils.matches((EObject) result, IUMLRTElementTypes.CAPSULE_PART_ID));

		Property capsulePart = (Property) result;
		assertEquals(VisibilityKind.PROTECTED_LITERAL, capsulePart.getVisibility()); // see Bug 474486

		// see Bug 479350
		Assert.assertFalse("CapsulePart Multiplicities should be left unset ", capsulePart.eIsSet(UMLPackage.eINSTANCE.getMultiplicityElement().getEStructuralFeature("lowerValue")));
		Assert.assertFalse("CapsulePart Multiplicities should be left unset ", capsulePart.eIsSet(UMLPackage.eINSTANCE.getMultiplicityElement().getEStructuralFeature("upperValue")));
	}

}
