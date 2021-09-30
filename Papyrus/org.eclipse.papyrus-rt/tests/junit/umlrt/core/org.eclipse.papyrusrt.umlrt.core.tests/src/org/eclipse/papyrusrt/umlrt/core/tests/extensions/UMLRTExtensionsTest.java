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

package org.eclipse.papyrusrt.umlrt.core.tests.extensions;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNotNull;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import javax.inject.Named;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.gmf.runtime.emf.commands.core.command.CompositeTransactionalCommand;
import org.eclipse.gmf.runtime.emf.type.core.ClientContextManager;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.IClientContext;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.IElementTypeDescriptor;
import org.eclipse.gmf.runtime.emf.type.core.IHintedType;
import org.eclipse.gmf.runtime.emf.type.core.ISpecializationType;
import org.eclipse.gmf.runtime.emf.type.core.internal.impl.SpecializationTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.requests.SetRequest;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.services.edit.context.TypeContext;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.papyrus.junit.framework.classification.tests.AbstractPapyrusTest;
import org.eclipse.papyrus.junit.utils.rules.HouseKeeper;
import org.eclipse.papyrus.junit.utils.rules.ModelSetFixture;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.junit.utils.rules.ServiceRegistryModelSetFixture;
import org.eclipse.papyrus.junit.utils.rules.UIThreadRule;
import org.eclipse.papyrusrt.junit.rules.ElementTypesRule;
import org.eclipse.papyrusrt.junit.rules.FixtureElementRule;
import org.eclipse.papyrusrt.umlrt.core.types.UMLRTElementTypesEnumerator;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;

/**
 * Test suite for ...
 */
@PluginResource("resource/umlrt-extensionsmodel.di")
public class UMLRTExtensionsTest extends AbstractPapyrusTest {
	@ClassRule
	public static final ElementTypesRule elementTypes = new ElementTypesRule();

	@Rule
	public HouseKeeper houseKeeper = new HouseKeeper();

	@Rule
	public ModelSetFixture modelSet = new ServiceRegistryModelSetFixture();

	@Rule
	public FixtureElementRule fixtureRule = new FixtureElementRule();

	@Rule
	public TestRule uiThread = new UIThreadRule(true);

	@Named("umlrt-extensionsmodel::TopCapsule")
	private Class topCapsule;

	@Named("umlrt-extensionsmodel::Capsule1")
	private Class capsule1;

	@Named("umlrt-extensionsmodel::Capsule2")
	private Class capsule2;

	private ElementTypeRegistry reg;

	/**
	 * Initializes me.
	 */
	public UMLRTExtensionsTest() {
		super();
	}

	@Before
	public void initRegistry() throws Exception {
		reg = ElementTypeRegistry.getInstance();
	}

	@Test
	public void testElementTypeRegistry() {
		Assert.assertNotNull("Could not get elementTypeRegistry", reg);
		IElementType type = reg.getType("org.eclipse.papyrus.uml.Class");
		Assert.assertNotNull("Could not get uml Class from registry", type);
		System.err.println(type);

		Method method;
		try {
			method = ElementTypeRegistry.class.getDeclaredMethod("getTypeDescriptor", String.class);
			method.setAccessible(true);
			IElementTypeDescriptor descriptor = (IElementTypeDescriptor) method.invoke(reg, type.getId());
			System.err.println(descriptor);
			IClientContext clientContext = ClientContextManager.getInstance().getBinding(descriptor);
			System.err.println(clientContext);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		ISpecializationType[] s1 = reg.getSpecializationsOf("org.eclipse.papyrus.uml.Class");

		Field field;
		try {
			field = ElementTypeRegistry.class.getDeclaredField("specializationTypeRegistry");
			field.setAccessible(true);

			SpecializationTypeRegistry value = (SpecializationTypeRegistry) field.get(reg);
			System.err.println(value);
			System.err.println("=== descriptors === ");
			value.getSpecializationTypeDescriptors().stream().forEach(d -> System.err.println(d));

			System.err.println("=== end of descriptors ===");
			System.err.println("=== descriptors for context: archi=== ");
			IClientContext context = ClientContextManager.getInstance().getClientContext("org.eclipse.papyrusrt.umlrt.architecture");
			System.err.println(context);
			value.getSpecializationTypeDescriptors(context).stream().forEach(d -> System.out.println(d));
			System.err.println("=== end descriptors for context: archi=== ");
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}



		Assert.assertTrue("Could not get type from elementTypeRegistry", s1.length > 0);
	}

	@Test
	public void testClientContext() throws ServiceException {
		IClientContext context = TypeContext.getContext(capsule1);
		assertNotNull(context);
		assertThat(context.getId(), equalTo("org.eclipse.papyrusrt.umlrt.architecture"));
	}

	@Test
	public void testCapsulesFound() throws ServiceException {
		Assert.assertNotNull("Can't find TopCapsule", topCapsule);

		Assert.assertNotNull("Can't find Capsule1", capsule1);

		Assert.assertNotNull("Can't find Capsule2", capsule2);
	}

	@Test
	public void testStereotypesApplied() throws ServiceException {
		Stereotype subCapsuleA = capsule1.getAppliedStereotype("UML-RT Extension Profile::SubCapsuleA");
		Assert.assertNotNull("Capsule1 does not have the subCapsuleA stereotype", subCapsuleA);

		Stereotype subCapsuleB = capsule2.getAppliedStereotype("UML-RT Extension Profile::SubCapsuleB");
		Assert.assertNotNull("Capsule2 does not have the subCapsuleB stereotype", subCapsuleB);
	}

	@Test
	public void testElementTypes() throws ServiceException {
		List<IElementType> typesForCapsule1 = Arrays.asList(reg.getAllTypesMatching(capsule1, TypeContext.getContext(capsule1)));
		IElementType typeForCapsule1 = reg.getType("org.eclipse.papyrusrt.umlrt.core.tests.umlrtext.types.subCapsuleA");
		assertThat(typeForCapsule1, notNullValue());
		Assert.assertTrue("Could not get subCapsuleA type for capsule1", typesForCapsule1.contains(typeForCapsule1));

		IElementType typeForCapsule2b = reg.getType("org.eclipse.papyrusrt.umlrt.core.tests.umlrtext.types.subCapsuleB");
		Assert.assertFalse("Could get subCapsuleB type for capsule1 but shouldn't", typesForCapsule1.contains(typeForCapsule2b));


		List<IElementType> typesForCapsule2 = Arrays.asList(reg.getAllTypesMatching(capsule2, TypeContext.getContext(capsule2)));
		IElementType typeForCapsule2 = reg.getType("org.eclipse.papyrusrt.umlrt.core.tests.umlrtext.types.subCapsuleB");
		Assert.assertTrue("Could not get subCapsuleB type for capsule2", typesForCapsule2.contains(typeForCapsule2));

		IElementType typeForCapsule1b = reg.getType("org.eclipse.papyrusrt.umlrt.core.tests.umlrtext.types.subCapsuleA");
		Assert.assertFalse("Could get subCapsuleA type for capsule2 but shouldn't", typesForCapsule2.contains(typeForCapsule1b));
	}

	@Test
	public void testSubCapsuleAPart() throws Exception {
		Property part = createCapsulePartFromType(topCapsule, capsule1);
		Assert.assertNotNull("Couldn't create capsule part", part);

		List<IElementType> types = Arrays.asList(reg.getAllTypesMatching(part.getType(), TypeContext.getContext(part)));
		IElementType type = reg.getType("org.eclipse.papyrusrt.umlrt.core.tests.umlrtext.types.subCapsuleA");
		Assert.assertTrue("Couldn't create capusle part of type SubCapsuleA", types.contains(type));
	}

	@Test
	public void testSubCapsuleBPart() throws Exception {
		Property part = createCapsulePartFromType(topCapsule, capsule2);
		Assert.assertNotNull("Couldn't create capsule part", part);

		List<IElementType> types = Arrays.asList(reg.getAllTypesMatching(part.getType(), TypeContext.getContext(part)));
		IElementType type = reg.getType("org.eclipse.papyrusrt.umlrt.core.tests.umlrtext.types.subCapsuleB");
		Assert.assertTrue("Couldn't create capusle part of type SubCapsuleB", types.contains(type));
	}

	Property createCapsulePartFromType(Class capsule, Class partType) throws ServiceException {
		IHintedType typeToCreate = UMLRTElementTypesEnumerator.CAPSULE_PART;
		CompositeTransactionalCommand command = new CompositeTransactionalCommand(modelSet.getEditingDomain(), "Create Capsule Part");
		ICommand createChildCommandWithContext = ElementEditServiceUtils.getCreateChildCommandWithContext(capsule, typeToCreate);
		command.add(createChildCommandWithContext);

		ICommand setType = new AbstractTransactionalCommand(modelSet.getEditingDomain(), "Set Type", null) {

			@Override
			protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
				// The new part is the last one added
				Property newPart = capsule.getOwnedAttributes().get(capsule.getOwnedAttributes().size() - 1);

				SetRequest request = new SetRequest(getEditingDomain(), newPart, UMLPackage.Literals.TYPED_ELEMENT__TYPE, partType);
				ICommand delegate = ElementEditServiceUtils.getCommandProvider(newPart).getEditCommand(request);
				delegate.execute(monitor, info);

				return CommandResult.newOKCommandResult(newPart);
			}
		};
		command.add(setType);
		modelSet.execute(command);

		Property result = (Property) setType.getCommandResult().getReturnValue();
		Assert.assertNotNull("Capsule Part creation failed", result);
		return result;
	}
}
