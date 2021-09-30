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

package org.eclipse.papyrusrt.umlrt.tooling.diagram.common.tests;

import static com.google.common.base.Predicates.not;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assume.assumeThat;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.eclipse.core.commands.operations.IOperationHistory;
import org.eclipse.core.commands.operations.IUndoContext;
import org.eclipse.core.commands.operations.IUndoableOperation;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.common.command.CommandStack;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.workspace.IWorkspaceCommandStack;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.diagram.core.edithelpers.CreateElementRequestAdapter;
import org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ITextAwareEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.LabelEditPart;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewAndElementRequest;
import org.eclipse.gmf.runtime.diagram.ui.services.decorator.Decoration;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.IHintedType;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.commands.wrappers.GEFtoEMFCommandWrapper;
import org.eclipse.papyrus.infra.emf.gmf.command.GMFtoEMFCommandWrapper;
import org.eclipse.papyrus.infra.emf.utils.TreeIterators;
import org.eclipse.papyrus.infra.gmfdiag.common.utils.DiagramEditPartsUtil;
import org.eclipse.papyrus.junit.framework.classification.tests.AbstractPapyrusTest;
import org.eclipse.papyrus.junit.utils.JUnitUtils;
import org.eclipse.papyrus.junit.utils.rules.AbstractHouseKeeperRule.CleanUp;
import org.eclipse.papyrus.junit.utils.rules.AnnotationRule;
import org.eclipse.papyrus.junit.utils.rules.HouseKeeper;
import org.eclipse.papyrus.junit.utils.rules.JavaResource;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrusrt.junit.rules.PapyrusRTEditorFixture;
import org.eclipse.papyrusrt.junit.rules.PapyrusRTEditorFixture.DiagramNaming;
import org.eclipse.papyrusrt.junit.rules.UIThreadRule;
import org.eclipse.papyrusrt.junit.rules.UMLRTViewpointRule;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.editparts.IInheritableEditPart;
import org.eclipse.papyrusrt.umlrt.uml.util.UMLRTExtensionUtil;
import org.eclipse.uml2.uml.DirectedRelationship;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.util.UMLUtil;
import org.hamcrest.CustomTypeSafeMatcher;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.rules.TestRule;
import org.junit.runners.Parameterized;

import com.google.common.collect.Iterables;
import com.google.common.collect.Iterators;

/**
 * Abstract superclass suitable for most Papyrus-RT diagram tests.
 */
@DiagramNaming(CapsuleStructureDiagramNameStrategy.class)
public abstract class AbstractPapyrusRTDiagramTest extends AbstractPapyrusTest {

	@ClassRule
	public static final TestRule VIEWPOINT_RULE = new UMLRTViewpointRule();

	/**
	 * Tests using the diagram-editor fixture have to run in the UI thread.
	 */
	@Rule
	public final TestRule uiThread = new UIThreadRule();

	@Rule
	public final HouseKeeper houseKeeper = new HouseKeeper();

	@Rule
	public final AnnotationRule<Boolean> needUIEvents = AnnotationRule.createExists(NeedsUIEvents.class);

	@Rule
	public final PapyrusRTEditorFixture editor;

	@CleanUp
	private ComposedAdapterFactory adapterFactory;

	@CleanUp
	private IUndoableOperation undoMark;

	public AbstractPapyrusRTDiagramTest() {
		super();

		this.editor = new PapyrusRTEditorFixture();
	}

	/**
	 * Initializes me with a test resource path to inject not via an annotation.
	 * This is useful especially for {@link Parameterized parameterized} test suites.
	 *
	 * @param modelPath
	 *            the model path to load instead of any that might be supplied by an annotaiton
	 * 
	 * @see Parameterized
	 * @see PluginResource
	 * @see JavaResource
	 */
	public AbstractPapyrusRTDiagramTest(String modelPath) {
		super();

		this.editor = new PapyrusRTEditorFixture(modelPath);
	}

	@Before
	public void createAdapterFactory() {
		adapterFactory = new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE);
	}

	protected DiagramEditPart getDiagramEditPart() {
		return editor.getActiveDiagramEditor().getDiagramEditPart();
	}

	protected TreeIterator<IGraphicalEditPart> allContents(EditPart root, boolean includeRoot) {
		return TreeIterators.filter(DiagramEditPartsUtil.getAllContents(root, includeRoot), IGraphicalEditPart.class);
	}

	protected IGraphicalEditPart getEditPart(EObject element) {
		return getEditPart(element, getDiagramEditPart());
	}

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

	protected boolean redefines(EObject element, EObject redefined) {
		return (element instanceof Element) && (redefined instanceof Element)
				&& UMLRTExtensionUtil.redefines((Element) element, (Element) redefined);
	}

	protected IGraphicalEditPart requireEditPart(EObject element) {
		IGraphicalEditPart result = getEditPart(element, getDiagramEditPart());
		assertThat("No edit part for " + label(element), result, notNullValue());
		return result;
	}

	protected IGraphicalEditPart getConnectionEditPart(EObject element) {
		IGraphicalEditPart result = null;

		for (Iterator<IGraphicalEditPart> iter = Iterators.filter(getDiagramEditPart().getConnections().iterator(), IGraphicalEditPart.class); iter.hasNext();) {
			IGraphicalEditPart next = iter.next();
			EObject semantic = next.resolveSemanticElement();
			if (((semantic == element) || redefines(semantic, element))) {
				result = next;
				break;
			}
		}

		return result;
	}

	protected IGraphicalEditPart requireConnectionEditPart(EObject element) {
		IGraphicalEditPart result = getConnectionEditPart(element);
		assertThat("No connection edit part for " + label(element), result, notNullValue());
		return result;
	}

	protected View getView(EObject element, View scope) {
		View result = null;

		for (Iterator<View> iter = Iterators.filter(scope.eAllContents(), View.class); (result == null) && iter.hasNext();) {
			View next = iter.next();
			if (next.getElement() == element) {
				result = next;
			}
		}

		return result;
	}

	protected View requireView(EObject element, View scope) {
		View result = getView(element, scope);
		assertThat("View not found: " + label(element), result, notNullValue());
		return result;
	}

	protected View assumeView(EObject element, View scope) {
		View result = getView(element, scope);
		assumeThat("View not found: " + label(element), result, notNullValue());
		return result;
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

	protected View getView(EObject object) {
		IGraphicalEditPart editPart = getEditPart(object);
		if (editPart == null) {
			// Maybe it's an edge
			editPart = getConnectionEditPart(object);
		}
		return (editPart == null) ? null : editPart.getNotationView();
	}

	protected View requireView(EObject object) {
		View result = getView(object);
		assertThat("No view for " + label(object), result, notNullValue());
		return result;
	}

	protected View assumeView(EObject object) {
		View result = getView(object);
		assumeThat("No view for " + label(object), result, notNullValue());
		return result;
	}

	protected Edge requireEdge(View oneEnd, View otherEnd) {
		Edge result = getEdge(oneEnd, otherEnd);
		assertThat("No edge between " + label(oneEnd) + " and " + label(otherEnd), result, notNullValue());
		return result;
	}

	protected Edge getEdge(View oneEnd, View otherEnd) {
		Edge result = null;

		for (Edge next : Iterables.filter(oneEnd.getSourceEdges(), Edge.class)) {
			if (next.getTarget() == otherEnd) {
				result = next;
				break;
			}
		}

		if (result == null) {
			// Try the other way around
			for (Edge next : Iterables.filter(oneEnd.getTargetEdges(), Edge.class)) {
				if (next.getSource() == otherEnd) {
					result = next;
					break;
				}
			}
		}

		return result;
	}

	protected <T extends DirectedRelationship> T getRelationship(NamedElement from, NamedElement to, Class<T> type) {
		T result = null;

		for (T next : Iterables.filter(from.getSourceDirectedRelationships(), type)) {
			if (next.getTargets().contains(to)) {
				result = next;
				break;
			}
		}

		return result;
	}

	protected void execute(ICommand command) {
		execute(GMFtoEMFCommandWrapper.wrap(command));
	}

	protected void execute(Command command) {
		execute(GEFtoEMFCommandWrapper.wrap(command));
	}

	protected void execute(org.eclipse.emf.common.command.Command command) {
		assertThat("Cannot execute command", command.canExecute(), is(true));
		editor.getEditingDomain().getCommandStack().execute(command);
		waitForUIEvents();
	}

	protected final void waitForUIEvents() {
		// If we're running the tests in the IDE, we should see what's happening. Or, it could be that a
		// particular test actually needs UI events to be processed before proceeding
		if (!JUnitUtils.isAutomatedBuildExecution() || isNeedUIEvents()) {
			editor.flushDisplayEvents();
		}
	}

	protected final boolean isNeedUIEvents() {
		return needUIEvents.get();
	}

	protected void execute(final Runnable writeOperation) {
		execute(new RecordingCommand(editor.getEditingDomain()) {

			@Override
			protected void doExecute() {
				writeOperation.run();
			}
		});
	}

	protected <T> T execute(Supplier<T> supplier) {
		AtomicReference<T> result = new AtomicReference<>(null);

		execute(new RecordingCommand(editor.getEditingDomain()) {

			@Override
			protected void doExecute() {
				result.set(supplier.get());
			}
		});

		return result.get();
	}

	protected void undo() {
		CommandStack stack = editor.getEditingDomain().getCommandStack();
		assertThat("Cannot undo", stack.canUndo(), is(true));
		stack.undo();
		waitForUIEvents();
	}

	/**
	 * Marks the current place in the undo history for a subsequent {@link #undoToMark()}
	 * to unwind the history back to that same state. This simplifies test cases that
	 * need to execute a sequence of commands and then undo them all or, as happens
	 * sometimes with canonical view creation, have asynchronous layout commands executed
	 * on their behalf without their knowledge, depending on the configuration of
	 * installed plug-ins.
	 * 
	 * @see #undoToMark()
	 */
	protected void markForUndo() {
		IWorkspaceCommandStack stack = (IWorkspaceCommandStack) editor.getEditingDomain().getCommandStack();
		IOperationHistory history = stack.getOperationHistory();
		undoMark = history.getUndoOperation(stack.getDefaultUndoContext());
	}

	/**
	 * Returns the current place in the undo history.
	 * 
	 * @return the undoMark
	 */
	public IUndoableOperation getUndoMark() {
		return undoMark;
	}

	/**
	 * Undoes the top command in the undo history until the previously established
	 * {@linkplain #markForUndo() mark} is reached.
	 * 
	 * @see #markForUndo()
	 */
	protected void undoToMark() {
		IWorkspaceCommandStack stack = (IWorkspaceCommandStack) editor.getEditingDomain().getCommandStack();
		IOperationHistory history = stack.getOperationHistory();
		IUndoContext ctx = stack.getDefaultUndoContext();

		try {
			IUndoableOperation lastOperation;

			do {
				IUndoableOperation topOperation = history.getUndoOperation(ctx);
				assertThat("Cannot undo", stack.canUndo(), is(true));
				stack.undo();
				lastOperation = history.getUndoOperation(ctx);
				assertThat("Undo failed; check log for an exception", lastOperation, not(topOperation));
			} while ((lastOperation != null) && (lastOperation != undoMark));

			waitForUIEvents();
		} finally {
			undoMark = null;
		}
	}

	protected void redo() {
		CommandStack stack = editor.getEditingDomain().getCommandStack();
		assertThat("Cannot redo", stack.canRedo(), is(true));
		stack.redo();
		waitForUIEvents();
	}

	public Command getCreationToolCommand(IGraphicalEditPart targetEditPart, Element targetElement, Point mouse, String elementTypeID) {

		IHintedType typeToCreate = (IHintedType) ElementTypeRegistry.getInstance().getType(elementTypeID);
		CreateElementRequest createElement = new CreateElementRequest(targetElement, typeToCreate);
		CreateElementRequestAdapter createAdapter = new CreateElementRequestAdapter(createElement);
		CreateViewAndElementRequest request = new CreateViewAndElementRequest(
				new CreateViewAndElementRequest.ViewAndElementDescriptor(
						createAdapter, Node.class, typeToCreate.getSemanticHint(), editor.getPreferencesHint()));
		request.setType(RequestConstants.REQ_CREATE);
		request.setLocation(mouse);

		return targetEditPart.getCommand(request);
	}

	protected EditPart getTargetEditPart(EditPart editPart, Request request) {
		EditPart result = editPart;

		// Drill through text compartments
		while (result instanceof ITextAwareEditPart) {
			result = result.getParent();
		}

		result = result.getTargetEditPart(request);

		return result;
	}

	protected List<Decoration> retrieveDecorations(IGraphicalEditPart editpart) {
		@SuppressWarnings("unchecked")
		List<Decoration> visuals = (List<Decoration>) editpart.getViewer().getVisualPartMap().keySet().stream()
				.filter(key -> editpart.getViewer().getVisualPartMap().get(key).equals(editpart))
				.filter(Decoration.class::isInstance)
				.map(Decoration.class::cast)
				.collect(Collectors.toList());
	
		return visuals;
	}

	/**
	 * Assertion that an edit-part is or is not inherited from a parent diagram.
	 * Visually speaking, that it appears as a local definition, not washed-out.
	 * 
	 * @param expected
	 *            whether the edit-part is expected to be inherited
	 * 
	 * @return the edit-part matcher
	 */
	public static Matcher<EditPart> inherited(boolean expected) {
		return new CustomTypeSafeMatcher<EditPart>(expected ? "is inherited" : "not inherited") {
			@Override
			protected boolean matchesSafely(EditPart item) {
				boolean result = false;

				if (item instanceof IInheritableEditPart) {
					IInheritableEditPart iep = (IInheritableEditPart) item;

					result = expected
							? iep.isInherited() && (iep.getRedefinedView() != null)
							: !iep.isInherited() || (iep.getRedefinedView() == null);
				}

				return result;
			}
		};
	}

	/**
	 * Assertion that an edit-part is inherited from a parent diagram.
	 * 
	 * @param expected
	 *            whether the edit-part is expected to be inherited
	 * 
	 * @return the edit-part matcher
	 */
	public static Matcher<EditPart> isInherited() {
		return inherited(true);
	}

	/**
	 * Assertion that an edit-part is not inherited from a parent diagram.
	 * 
	 * @param expected
	 *            whether the edit-part is expected to be inherited
	 * 
	 * @return the edit-part matcher
	 */
	public static Matcher<EditPart> notInherited() {
		return inherited(false);
	}

	//
	// Nested types
	//

	@Retention(RetentionPolicy.RUNTIME)
	@Target({ ElementType.TYPE, ElementType.METHOD })
	protected @interface NeedsUIEvents {
		// Empty
	}

}
