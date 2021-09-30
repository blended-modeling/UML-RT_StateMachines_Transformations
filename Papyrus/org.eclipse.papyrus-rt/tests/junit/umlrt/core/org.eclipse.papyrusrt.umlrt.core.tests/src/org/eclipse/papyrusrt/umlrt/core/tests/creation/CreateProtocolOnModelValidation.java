/*****************************************************************************
 * Copyright (c) 2014, 2017 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  CEA LIST - Initial API and implementation
 *  Christian W. Damus - bug 510189
 *****************************************************************************/
package org.eclipse.papyrusrt.umlrt.core.tests.creation;

import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.papyrusrt.umlrt.core.tests.utils.IValidationRule;
import org.eclipse.papyrusrt.umlrt.core.types.ElementTypeUtils;
import org.eclipse.papyrusrt.umlrt.core.types.IUMLRTElementTypes;
import org.eclipse.papyrusrt.umlrt.core.utils.ProtocolUtils;
import org.eclipse.uml2.uml.Collaboration;
import org.eclipse.uml2.uml.Dependency;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.Assert;

/**
 * Additional validation rules for Protocol created on a model
 */
public class CreateProtocolOnModelValidation implements IValidationRule {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void validatePostEdition(Element targetContainer, Object[] commandResults) throws Exception {
		Assert.assertEquals("comand result should not be empty", 1, commandResults.length);
		Object result = commandResults[0];
		Assert.assertTrue("new element should be an EObject", result instanceof EObject);
		Assert.assertTrue("new element should be a protocol", ElementTypeUtils.matches((EObject) result, IUMLRTElementTypes.PROTOCOL_ID));

		Collaboration protocol = (Collaboration) result;
		/* checkProtocol */
		// name
		Assert.assertEquals("Protocol1", protocol.getName());
		// other?

		/* check protocolcontainer */
		Element owner = protocol.getOwner();
		Assert.assertTrue("Container of the protcol should be a ProtocolContainer", ElementTypeUtils.matches(owner, IUMLRTElementTypes.PROTOCOL_CONTAINER_ID));
		Package protocolContainer = (Package) owner;
		// check name
		Assert.assertEquals("Protocol1", protocolContainer.getName());

		Interface messageSetIn = ProtocolUtils.getMessageSetIn(protocol);
		Assert.assertNotNull(messageSetIn);
		Assert.assertEquals("Protocol1", messageSetIn.getName());

		Interface messageSetOut = ProtocolUtils.getMessageSetOut(protocol);
		Assert.assertNotNull(messageSetOut);
		Assert.assertEquals("Protocol1~", messageSetOut.getName());

		Interface messageSetInOut = ProtocolUtils.getMessageSetInOut(protocol);
		Assert.assertNotNull(messageSetInOut);
		Assert.assertEquals("Protocol1IO", messageSetInOut.getName());

		// check that all protocol container dependencies names are unset
		List<Dependency> allDependencies = getProtocolDependencies(protocolContainer);
		if (allDependencies != null && !allDependencies.isEmpty()) {
			for (Dependency dependency : allDependencies) {
				Assert.assertFalse(dependency.eIsSet(UMLPackage.eINSTANCE.getNamedElement().getEStructuralFeature("name")));
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void validatePostUndo(Element targetContainer, Object[] commandResults) throws Exception {
		// targetcontainer should be a package
		Assert.assertTrue(targetContainer instanceof Package);
		Assert.assertNull(((Package) targetContainer).getPackagedElement("Protocol1", false, UMLPackage.eINSTANCE.getPackage(), false));
	}

	static List<Dependency> getProtocolDependencies(Package protocolContainer) {
		return EcoreUtil.getObjectsByType(protocolContainer.allOwnedElements(), UMLPackage.Literals.DEPENDENCY)
				.stream().map(Dependency.class::cast).collect(Collectors.toList());
	}
}
