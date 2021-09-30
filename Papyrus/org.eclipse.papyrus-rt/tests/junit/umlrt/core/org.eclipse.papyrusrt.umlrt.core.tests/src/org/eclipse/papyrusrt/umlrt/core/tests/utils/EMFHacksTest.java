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

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage.Literals;
import org.eclipse.papyrusrt.umlrt.core.utils.EMFHacks;
import org.junit.Test;

import com.google.common.collect.Lists;

/**
 * Test cases for the {@link EMFHacks} class.
 */
public class EMFHacksTest {

	private final EClass eclass = EcoreFactory.eINSTANCE.createEClass();

	/**
	 * Initializes me.
	 */
	public EMFHacksTest() {
		super();
	}

	@Test
	public void silently() {
		WhatChanged.test(eclass, o -> {
			EMFHacks.silently((EClass)o, _o -> o.setName("Foo"))
					.setAbstract(true);
		}).assertChanged(Literals.ECLASS__ABSTRACT);
	}

	@Test
	public void silentlyPresent() {
		WhatChanged.test(eclass, o -> {
			EMFHacks.silently(Optional.of(o), _o -> o.setName("Foo"))
					.get().setAbstract(true);
		}).assertChanged(Literals.ECLASS__ABSTRACT);
	}

	@Test
	public void silentlyAbsent() {
		WhatChanged.test(eclass, o -> {
			EMFHacks.silently(Optional.<EClass> empty(), _o -> o.setName("Foo"))
					.ifPresent(_o -> o.setAbstract(true));
		}).assertChanged(); // Nothing changed, of course
	}

	@Test
	public void coneOfSilence() {
		EPackage epackage = EcoreFactory.eINSTANCE.createEPackage();

		WhatChanged.test(eclass, c -> {
			WhatChanged.test(epackage, p -> {
				EMFHacks.coneOfSilence(c)
						.with(p)
						.execute(() -> {
							p.setName("pkg");
							c.setName("Foo");
							c.setAbstract(true);
							p.getEClassifiers().add(c);
						});
			}).assertChanged();
		}).assertChanged();
	}

	//
	// Test framework
	//

	private static class WhatChanged extends AdapterImpl {
		private List<EStructuralFeature> featuresChanged = Lists.newArrayListWithExpectedSize(1);

		private WhatChanged() {
			super();
		}

		@Override
		public void notifyChanged(Notification msg) {
			if (msg.getFeature() instanceof EStructuralFeature) {
				featuresChanged.add((EStructuralFeature) msg.getFeature());
			}
		}

		static <T extends Notifier> WhatChanged test(T subject, Consumer<? super T> test) {
			WhatChanged result = new WhatChanged();

			subject.eAdapters().add(result);
			try {
				test.accept(subject);
			} finally {
				subject.eAdapters().remove(result);
			}

			return result;
		}

		void assertChanged(EStructuralFeature... features) {
			assertThat("Wrong sequence of features changed",
					featuresChanged,
					is(Arrays.asList(features)));
		}
	}
}
