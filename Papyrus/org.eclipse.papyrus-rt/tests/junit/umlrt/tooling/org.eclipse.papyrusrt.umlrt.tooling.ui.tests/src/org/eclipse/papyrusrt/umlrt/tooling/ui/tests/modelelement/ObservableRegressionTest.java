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

package org.eclipse.papyrusrt.umlrt.tooling.ui.tests.modelelement;

import static org.eclipse.papyrus.junit.matchers.MoreMatchers.lessThan;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.HashSet;
import java.util.Set;

import javax.inject.Named;

import org.eclipse.core.databinding.observable.IObservable;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.infra.core.sasheditor.di.contentprovider.utils.TransactionHelper;
import org.eclipse.papyrus.junit.utils.rules.ModelSetFixture;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrusrt.junit.rules.FixtureElementRule;
import org.eclipse.papyrusrt.junit.rules.UIThreadRule;
import org.eclipse.papyrusrt.junit.rules.UMLRTModelSetFixture;
import org.eclipse.papyrusrt.umlrt.tooling.ui.widgets.CapsulePartKindObservableValue;
import org.eclipse.papyrusrt.umlrt.tooling.ui.widgets.PortRTKindObservableValue;
import org.eclipse.uml2.uml.Port;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.ValueSpecification;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;

/**
 * Regression tests for specific bugs in the <em>Observables</em>
 * support the UML-RT property sheet.
 */
@PluginResource("resource/modelelement/model.di")
public class ObservableRegressionTest {

	@Rule
	public final ModelSetFixture model = new UMLRTModelSetFixture();

	@Rule
	public final FixtureElementRule elements = new FixtureElementRule();

	// The observables tested herein don't provide constructors accepting
	// a realm, so make sure they can find the default realm
	@Rule
	public final TestRule uiThread = new UIThreadRule();

	@Named("model::Capsule::sap")
	private Port sap;
	@Named("model::Capsule::spp")
	private Port spp;

	@Named("model::ConnectedCapsule::capsule")
	private Property capsule;
	@Named("model::ConnectedCapsule::libraryCapsule")
	private Property libraryCapsule;

	public ObservableRegressionTest() {
		super();
	}

	/**
	 * <p>
	 * Check that disposal of an RTPortKind observable removes any and
	 * all adapters that it added to the port.
	 * </p>
	 * <p>
	 * Regression test for bug 489434.
	 * </p>
	 * 
	 * @see https://bugs.eclipse.org/bugs/show_bug.cgi?id=489434
	 */
	@Test
	public void adaptersOnRTPortCleanedUp() {
		EObject sapStereo = sap.getStereotypeApplications().get(0);
		EObject sppStereo = spp.getStereotypeApplications().get(0);
		IObservable obs1 = new PortRTKindObservableValue(sapStereo, model.getEditingDomain());
		IObservable obs2 = new PortRTKindObservableValue(sppStereo, model.getEditingDomain());

		assertUniqueAdapters(sap, spp);
		assertUniqueAdapters(sapStereo, sppStereo);

		obs1.dispose();
		obs2.dispose();
		obs1 = new PortRTKindObservableValue(sapStereo, model.getEditingDomain());
		obs2 = new PortRTKindObservableValue(sppStereo, model.getEditingDomain());

		// The previous observables' listeners were removed
		assertUniqueAdapters(sap, spp);
		assertUniqueAdapters(sapStereo, sppStereo);
	}

	/**
	 * <p>
	 * Check that disposal of a CapsulePartKind observable removes any and
	 * all adapters that it added to the port.
	 * </p>
	 * <p>
	 * Regression test for bug 489434.
	 * </p>
	 * 
	 * @see https://bugs.eclipse.org/bugs/show_bug.cgi?id=489434
	 */
	@Test
	public void adaptersOnCapsulePartCleanedUp() {
		EObject capsuleStereo = capsule.getStereotypeApplications().get(0);
		EObject libraryCapsuleStereo = libraryCapsule.getStereotypeApplications().get(0);
		IObservable obs1 = new CapsulePartKindObservableValue(capsuleStereo, model.getEditingDomain());
		IObservable obs2 = new CapsulePartKindObservableValue(libraryCapsuleStereo, model.getEditingDomain());

		assertUniqueAdapters(capsule, libraryCapsule);
		assertUniqueAdapters(capsule.getLowerValue(), capsule.getUpperValue());
		assertUniqueAdapters(capsuleStereo, libraryCapsuleStereo);

		obs1.dispose();
		obs2.dispose();
		obs1 = new CapsulePartKindObservableValue(capsuleStereo, model.getEditingDomain());
		obs2 = new CapsulePartKindObservableValue(libraryCapsuleStereo, model.getEditingDomain());

		// The previous observables' listeners were removed
		assertUniqueAdapters(capsule, libraryCapsule);
		assertUniqueAdapters(capsule.getLowerValue(), capsule.getUpperValue());
		assertUniqueAdapters(capsuleStereo, libraryCapsuleStereo);
	}

	/**
	 * Tests that that adapter(s) attached to a capsule part's
	 * lower/upper bound elements doesn't leak when those elements
	 * are replaced.
	 */
	@Test
	public void adapterDoesntLeakOnCapsulePartBounds() throws Exception {
		EObject capsuleStereo = capsule.getStereotypeApplications().get(0);
		new CapsulePartKindObservableValue(capsuleStereo, model.getEditingDomain());

		ValueSpecification lower = capsule.getLowerValue();
		final int baselineLower = lower.eAdapters().size();

		ValueSpecification upper = capsule.getUpperValue();
		final int baselineUpper = upper.eAdapters().size();

		// Replace the lower and upper bounds
		TransactionHelper.run(model.getEditingDomain(), new Runnable() {
			@Override
			public void run() {
				capsule.createLowerValue(null, null, UMLPackage.Literals.LITERAL_INTEGER);
				capsule.createUpperValue(null, null, UMLPackage.Literals.LITERAL_UNLIMITED_NATURAL);
			}
		});

		assertThat(lower.eAdapters().size(), lessThan(baselineLower));
		assertThat(upper.eAdapters().size(), lessThan(baselineUpper));
	}

	//
	// Nested types
	//

	void assertUniqueAdapters(Notifier... notifiers) {
		for (Notifier next : notifiers) {
			Set<Class<?>> adapterClasses = new HashSet<>();
			for (Object adapter : next.eAdapters()) {
				assertThat("Duplicate adapter " + adapter, adapterClasses.add(adapter.getClass()), is(true));
			}
		}
	}
}
