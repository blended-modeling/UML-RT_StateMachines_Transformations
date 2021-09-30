/*****************************************************************************
 * Copyright (c) 2016 Christian W. Damus and others.
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

package org.eclipse.papyrusrt.umlrt.core.tests.utils;

import static org.junit.Assert.fail;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.papyrus.junit.utils.rules.HouseKeeper;
import org.eclipse.papyrusrt.umlrt.core.utils.RTPropertyUtils;
import org.eclipse.uml2.uml.LiteralInteger;
import org.eclipse.uml2.uml.LiteralReal;
import org.eclipse.uml2.uml.LiteralString;
import org.eclipse.uml2.uml.LiteralUnlimitedNatural;
import org.eclipse.uml2.uml.OpaqueExpression;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.ValueSpecification;
import org.junit.Rule;
import org.junit.Test;

/**
 * Unit tests covering the {@link RTPropertyUtils} class.
 */
public class RTPropertyUtilsTest {

	@Rule
	public final HouseKeeper houseKeeper = new HouseKeeper();

	private Property property = UMLFactory.eINSTANCE.createProperty();

	@Test
	public void isReplicated_implicit() {
		assertUnreplicated(property);
	}

	@Test
	public void isReplicated_integers() {
		property.setLowerValue(createValue(LiteralInteger.class, 1));
		property.setUpperValue(createValue(LiteralInteger.class, 1));
		assertUnreplicated(property); // explicit

		((LiteralInteger) property.getUpperValue()).setValue(4);
		assertReplicated(property);
	}

	@Test
	public void isReplicated_naturals() {
		property.setLowerValue(createValue(LiteralUnlimitedNatural.class, 1));
		property.setUpperValue(createValue(LiteralUnlimitedNatural.class, 1));
		assertUnreplicated(property); // explicit

		((LiteralUnlimitedNatural) property.getUpperValue()).setValue(4);
		assertReplicated(property);
	}

	@Test
	public void isReplicated_reals() {
		property.setLowerValue(createValue(LiteralReal.class, 1.0));
		property.setUpperValue(createValue(LiteralReal.class, 1.0));
		assertUnreplicated(property); // explicit

		((LiteralReal) property.getUpperValue()).setValue(4.0);
		assertReplicated(property);
	}

	@Test
	public void isReplicated_strings() {
		property.setLowerValue(createValue(LiteralString.class, "1"));
		property.setUpperValue(createValue(LiteralString.class, "1"));
		assertUnreplicated(property); // explicit

		((LiteralString) property.getUpperValue()).setValue("4");
		assertReplicated(property);
	}

	@Test
	public void isReplicated_expressions() {
		property.setLowerValue(createValue(OpaqueExpression.class, "1"));
		property.setUpperValue(createValue(OpaqueExpression.class, "1"));
		assertUnreplicated(property); // explicit

		((OpaqueExpression) property.getUpperValue()).getBodies().set(0, "4");
		assertReplicated(property);
	}

	@Test
	public void isOptional_implicit() {
		assertRequired(property);
	}

	@Test
	public void isOptional_integers() {
		property.setLowerValue(createValue(LiteralInteger.class, 1));
		property.setUpperValue(createValue(LiteralInteger.class, 1));
		assertRequired(property); // explicit

		((LiteralInteger) property.getLowerValue()).setValue(0);
		assertOptional(property);
	}

	@Test
	public void isOptional_naturals() {
		property.setLowerValue(createValue(LiteralUnlimitedNatural.class, 1));
		property.setUpperValue(createValue(LiteralUnlimitedNatural.class, 1));
		assertRequired(property); // explicit

		((LiteralUnlimitedNatural) property.getLowerValue()).setValue(0);
		assertOptional(property);
	}

	@Test
	public void isOptional_reals() {
		property.setLowerValue(createValue(LiteralReal.class, 1.0));
		property.setUpperValue(createValue(LiteralReal.class, 1.0));
		assertRequired(property); // explicit

		((LiteralReal) property.getLowerValue()).setValue(0.0);
		assertOptional(property);
	}

	@Test
	public void isOptional_strings() {
		property.setLowerValue(createValue(LiteralString.class, "1"));
		property.setUpperValue(createValue(LiteralString.class, "1"));
		assertRequired(property); // explicit

		((LiteralString) property.getLowerValue()).setValue("0");
		assertOptional(property);
	}

	@Test
	public void isOptional_expressions() {
		property.setLowerValue(createValue(OpaqueExpression.class, "1"));
		property.setUpperValue(createValue(OpaqueExpression.class, "1"));
		assertRequired(property); // explicit

		((OpaqueExpression) property.getLowerValue()).getBodies().set(0, "0");
		assertOptional(property);
	}

	//
	// Test framework
	//

	void assertReplicated(Property property) {
		if (!RTPropertyUtils.isReplicated(property)) {
			fail(String.format("Unreplicated: lower=%s, upper=%s",
					property.getLowerValue(), property.getUpperValue()));
		}
	}

	void assertUnreplicated(Property property) {
		if (RTPropertyUtils.isReplicated(property)) {
			fail(String.format("Replicated: lower=%s, upper=%s",
					property.getLowerValue(), property.getUpperValue()));
		}
	}

	void assertOptional(Property property) {
		if (!RTPropertyUtils.isOptional(property)) {
			fail(String.format("Required: lower=%s, upper=%s",
					property.getLowerValue(), property.getUpperValue()));
		}
	}

	void assertRequired(Property property) {
		if (RTPropertyUtils.isOptional(property)) {
			fail(String.format("Optional: lower=%s, upper=%s",
					property.getLowerValue(), property.getUpperValue()));
		}
	}

	<V extends ValueSpecification> V createValue(Class<V> valueType, Object value) {
		V result = valueType.cast(EcoreUtil.create((EClass) UMLPackage.eINSTANCE.getEClassifier(valueType.getSimpleName())));

		switch (result.eClass().getClassifierID()) {
		case UMLPackage.LITERAL_INTEGER:
			((LiteralInteger) result).setValue((Integer) value);
			break;
		case UMLPackage.LITERAL_UNLIMITED_NATURAL:
			((LiteralUnlimitedNatural) result).setValue((Integer) value);
			break;
		case UMLPackage.LITERAL_REAL:
			((LiteralReal) result).setValue((Double) value);
			break;
		case UMLPackage.LITERAL_STRING:
			((LiteralString) result).setValue((String) value);
			break;
		case UMLPackage.OPAQUE_EXPRESSION:
			((OpaqueExpression) result).getLanguages().add("OCL");
			((OpaqueExpression) result).getBodies().add((String) value);
			break;
		default:
			fail("Unsupported value type: " + valueType);
		}

		return result;
	}
}
