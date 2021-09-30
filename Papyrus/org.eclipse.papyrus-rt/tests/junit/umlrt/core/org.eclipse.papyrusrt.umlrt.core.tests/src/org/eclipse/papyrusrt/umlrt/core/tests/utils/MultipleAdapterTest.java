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

import static org.hamcrest.CoreMatchers.both;
import static org.hamcrest.CoreMatchers.either;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assume.assumeThat;

import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.papyrusrt.umlrt.core.utils.MultipleAdapter;
import org.junit.Test;

/**
 * Test cases for the {@link MultipleAdapter} API.
 */
public class MultipleAdapterTest {

	public MultipleAdapterTest() {
		super();
	}

	@Test
	public void setTarget() {
		MultipleAdapter adapter = new MultipleAdapter();
		Notifier o1 = EcoreFactory.eINSTANCE.createEObject();
		o1.eAdapters().add(adapter);
		assertThat(adapter.getTarget(), is(o1));
	}

	@Test
	public void unsetTarget() {
		MultipleAdapter adapter = new MultipleAdapter();
		Notifier o1 = EcoreFactory.eINSTANCE.createEObject();
		o1.eAdapters().add(adapter);
		assumeThat(adapter.getTarget(), is(o1));

		o1.eAdapters().remove(adapter);
		assertThat(adapter.getTarget(), nullValue());
	}

	@Test
	public void getTargets() {
		MultipleAdapter adapter = new MultipleAdapter();
		Notifier o1 = EcoreFactory.eINSTANCE.createEObject();
		Notifier o2 = EcoreFactory.eINSTANCE.createEObject();
		o1.eAdapters().add(adapter);
		o2.eAdapters().add(adapter);

		assertThat(adapter.getTargets().size(), is(2));
		assertThat(adapter.getTargets(), both(hasItem(o1)).and(hasItem(o2)));
		assertThat(adapter.getTarget(), either(is(o1)).or(is(o2)));
	}

	@Test
	public void dispose() {
		MultipleAdapter adapter = new MultipleAdapter();
		Notifier o1 = EcoreFactory.eINSTANCE.createEObject();
		Notifier o2 = EcoreFactory.eINSTANCE.createEObject();
		o1.eAdapters().add(adapter);
		o2.eAdapters().add(adapter);

		adapter.dispose();

		assertThat(adapter.getTargets().size(), is(0));
		assertThat(adapter.getTarget(), nullValue());
		assertThat(o1.eAdapters(), not(hasItem(adapter)));
		assertThat(o2.eAdapters(), not(hasItem(adapter)));
	}
}
