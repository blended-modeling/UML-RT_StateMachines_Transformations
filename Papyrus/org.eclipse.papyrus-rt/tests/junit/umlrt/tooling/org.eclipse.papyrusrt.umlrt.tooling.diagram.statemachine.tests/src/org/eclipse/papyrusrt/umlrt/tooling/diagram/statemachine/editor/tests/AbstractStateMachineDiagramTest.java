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

package org.eclipse.papyrusrt.umlrt.tooling.diagram.statemachine.editor.tests;

import static com.google.common.base.Predicates.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Iterator;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.LabelEditPart;
import org.eclipse.papyrus.infra.emf.utils.TreeIterators;
import org.eclipse.papyrus.infra.gmfdiag.common.utils.DiagramEditPartsUtil;
import org.eclipse.papyrus.junit.framework.classification.tests.AbstractPapyrusTest;
import org.eclipse.papyrus.junit.utils.rules.AbstractHouseKeeperRule.CleanUp;
import org.eclipse.papyrus.junit.utils.rules.HouseKeeper;
import org.eclipse.papyrusrt.junit.rules.ElementTypesRule;
import org.eclipse.papyrusrt.junit.rules.PapyrusRTEditorFixture.DiagramNaming;
import org.eclipse.papyrusrt.junit.rules.UIThreadRule;
import org.eclipse.papyrusrt.junit.rules.UMLRTViewpointRule;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.statemachine.canonical.tests.StateMachineDiagramNameStrategy;
import org.eclipse.papyrusrt.umlrt.uml.util.UMLRTExtensionUtil;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.util.UMLUtil;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.rules.TestRule;

/**
 * Test cases for modeling assistants in the RT state machine diagram.
 */
@DiagramNaming(StateMachineDiagramNameStrategy.class)
public abstract class AbstractStateMachineDiagramTest extends AbstractPapyrusTest {

	@ClassRule
	public static final TestRule uiThread = new UIThreadRule();

	@ClassRule
	public static final TestRule viewpoint = new UMLRTViewpointRule();

	@ClassRule
	public static final TestRule elementTypesRule = new ElementTypesRule();

	@Rule
	public final HouseKeeper housekeeper = new HouseKeeper();

	@CleanUp
	private ComposedAdapterFactory adapterFactory;

	public AbstractStateMachineDiagramTest() {
		super();
	}

	protected IGraphicalEditPart requireEditPart(EObject element) {
		IGraphicalEditPart result = getEditPart(element, getDiagramEditPart());
		assertThat("No edit part for " + label(element), result, notNullValue());
		return result;
	}

	protected abstract DiagramEditPart getDiagramEditPart();

	protected IGraphicalEditPart getEditPart(EObject element, IGraphicalEditPart scope) {
		IGraphicalEditPart result = null;

		com.google.common.base.Predicate<EditPart> isLabel = LabelEditPart.class::isInstance;
		TreeIterator<IGraphicalEditPart> nonLabels = TreeIterators.filter(allContents(scope, true), not(isLabel));

		for (Iterator<IGraphicalEditPart> iter = nonLabels; iter.hasNext();) {
			IGraphicalEditPart next = iter.next();
			EObject semantic = next.resolveSemanticElement();

			// We don't want to get the label edit-parts by this means (we search for them as
			// a specific child of a shape edit-part). We especially don't want applied-stereotype labels
			if (!(next instanceof LabelEditPart) && ((semantic == element) || redefines(semantic, element))) {
				result = next;
				break;
			}
		}

		return result;
	}

	protected TreeIterator<IGraphicalEditPart> allContents(EditPart root, boolean includeRoot) {
		return TreeIterators.filter(DiagramEditPartsUtil.getAllContents(root, includeRoot), IGraphicalEditPart.class);
	}

	protected boolean redefines(EObject element, EObject redefined) {
		return (element instanceof Element) && (redefined instanceof Element)
				&& UMLRTExtensionUtil.redefines((Element) element, (Element) redefined);
	}

	@Before
	public void createAdapterFactory() {
		adapterFactory = new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE);
	}

	protected String label(EObject object) {
		String result;

		if (object instanceof ENamedElement) {
			result = ((UMLUtil.getQualifiedName((ENamedElement) object, NamedElement.SEPARATOR)));
		} else {
			IItemLabelProvider labels = (IItemLabelProvider) adapterFactory.adapt(object, IItemLabelProvider.class);
			result = (labels == null) ? String.valueOf(object) : labels.getText(object);
		}

		return result;
	}
}
