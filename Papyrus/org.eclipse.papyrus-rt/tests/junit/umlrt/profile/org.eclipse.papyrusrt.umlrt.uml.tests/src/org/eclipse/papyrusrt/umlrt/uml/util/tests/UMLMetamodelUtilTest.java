/*****************************************************************************
 * Copyright (c) 2017 Christian W. Damus and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrusrt.umlrt.uml.util.tests;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

import org.eclipse.emf.ecore.EReference;
import org.eclipse.papyrusrt.umlrt.uml.util.UMLMetamodelUtil;
import org.eclipse.uml2.uml.UMLPackage;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Test;

/**
 * Test cases for the {@link UMLMetamodelUtil} class.
 */
public class UMLMetamodelUtilTest {

	public UMLMetamodelUtilTest() {
		super();
	}

	@Test
	public void isSubsetOf() {
		assertThat(UMLPackage.Literals.CLASSIFIER__FEATURE,
				subsets(UMLPackage.Literals.NAMESPACE__MEMBER));

		assertThat(UMLPackage.Literals.TRANSITION__REDEFINED_TRANSITION,
				subsets(UMLPackage.Literals.REDEFINABLE_ELEMENT__REDEFINED_ELEMENT));
	}

	@Test
	public void isSubsetOf_negative() {
		assertThat(UMLPackage.Literals.CLASSIFIER__FEATURE,
				not(subsets(UMLPackage.Literals.NAMESPACE__OWNED_MEMBER)));

		assertThat(UMLPackage.Literals.STATE_MACHINE__CONNECTION_POINT,
				not(subsets(UMLPackage.Literals.REDEFINABLE_ELEMENT__REDEFINED_ELEMENT)));
	}

	//
	// Test framework
	//

	Matcher<EReference> subsets(EReference other) {
		return new BaseMatcher<EReference>() {
			@Override
			public void describeTo(Description description) {
				description.appendText(String.format("subsets %s::%s",
						other.getEContainingClass().getName(), other.getName()));
			}

			@Override
			public boolean matches(Object item) {
				return (item instanceof EReference) && UMLMetamodelUtil.isSubsetOf((EReference) item, other);
			}
		};
	}
}
