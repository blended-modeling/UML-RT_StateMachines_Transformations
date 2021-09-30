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

package org.eclipse.papyrusrt.umlrt.tooling.diagram.common.inheritance.tests;

import static org.eclipse.papyrus.junit.matchers.MoreMatchers.greaterThan;
import static org.eclipse.papyrusrt.junit.matchers.FunctionalMatchers.compose;
import static org.hamcrest.CoreMatchers.both;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;

import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.BendpointRequest;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.LabelEditPart;
import org.eclipse.gmf.runtime.notation.BooleanValueStyle;
import org.eclipse.gmf.runtime.notation.Bounds;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.Location;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.RelativeBendpoints;
import org.eclipse.gmf.runtime.notation.Size;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.gmfdiag.common.editpart.NodeEditPart;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.papyrus.junit.utils.rules.JavaResource;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.uml.diagram.common.editparts.BorderNodeEditPart;
import org.eclipse.papyrusrt.umlrt.core.commands.ExcludeRequest;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.editparts.IInheritableEditPart;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.tests.AbstractPapyrusRTDiagramTest;
import org.eclipse.papyrusrt.umlrt.uml.FacadeObject;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsule;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsulePart;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTConnector;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTFactory;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTPackage;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTPort;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTReplicatedElement;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTStateMachine;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTTransition;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTVertex;
import org.eclipse.papyrusrt.umlrt.uml.util.UMLRTExtensionUtil;
import org.eclipse.papyrusrt.umlrt.uml.util.UMLRTSwitch;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Namespace;
import org.junit.runners.Parameterized;

import com.google.common.primitives.Primitives;

/**
 * Abstract superclass of diagram inheritance tests, providing various test framework
 * services to help the authoring of test cases.
 */
public abstract class AbstractInheritanceTest extends AbstractPapyrusRTDiagramTest {

	public AbstractInheritanceTest() {
		super();
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
	public AbstractInheritanceTest(String modelPath) {
		super(modelPath);
	}

	protected void activateDiagram(String capsuleName) {
		editor.activateDiagram(capsuleName);
	}

	/**
	 * Activates the diagram that visualizes the capsule contet of the given {@code element}.
	 * 
	 * @param element
	 *            an element in a capsule (or, itself, a capsule)
	 */
	protected void activateDiagram(UMLRTNamedElement element) {
		UMLRTCapsule capsule = new UMLRTSwitch<UMLRTCapsule>() {
			@Override
			public UMLRTCapsule caseReplicatedElement(UMLRTReplicatedElement object) {
				return object.getCapsule();
			}

			@Override
			public UMLRTCapsule caseConnector(UMLRTConnector object) {
				return object.getCapsule();
			}

			@Override
			public UMLRTCapsule caseCapsule(UMLRTCapsule object) {
				return object;
			}

			@Override
			public UMLRTCapsule caseStateMachine(UMLRTStateMachine object) {
				return object.getCapsule();
			}

			@Override
			public UMLRTCapsule caseVertex(UMLRTVertex object) {
				return doSwitch(object.containingStateMachine());
			}

			@Override
			public UMLRTCapsule caseTransition(UMLRTTransition object) {
				return doSwitch(object.containingStateMachine());
			}
		}.doSwitch(element);

		assertThat("No capsule context inferred from " + element, capsule, notNullValue());

		activateDiagram(capsule.getName());
	}

	protected UMLRTPackage getRoot() {
		return UMLRTPackage.getInstance(editor.getModel());
	}

	protected UMLRTCapsule getCapsule(String name) {
		return getRoot().getCapsule(name);
	}

	/**
	 * Obtains the {@code name}d port of <b>Capsule1</b>, for the common use case
	 * of accessing the inherited element (root definition). For ports of any
	 * other capsule, {@linkplain #getCapsule(String) get} that capsule, first,
	 * and {@linkplain UMLRTCapsule#getPort(String) get} the port from it.
	 * 
	 * @param name
	 *            the port name to get from Capsule1
	 * 
	 * @return the port
	 */
	protected UMLRTPort getPort(String name) {
		return getCapsule("Capsule1").getPort(name);
	}

	/**
	 * Obtains the {@code name}d capsule-part of <b>Capsule1</b>, for the common use case
	 * of accessing the inherited element (root definition). For capsule-parts of any
	 * other capsule, {@linkplain #getCapsule(String) get} that capsule, first,
	 * and {@linkplain UMLRTCapsule#getCapsulePart(String) get} the capsule-part from it.
	 * 
	 * @param name
	 *            the capsule-part name to get from Capsule1
	 * 
	 * @return the capsule-part
	 */
	protected UMLRTCapsulePart getCapsulePart(String name) {
		return getCapsule("Capsule1").getCapsulePart(name);
	}

	protected UMLRTPort getPortOnPart(String capsulePartName, String portName) {
		return getCapsulePart(capsulePartName).getType().getPort(portName);
	}

	/**
	 * Obtains the {@code name}d connector in <b>Capsule1</b>, for the common use case
	 * of accessing the inherited element (root definition). For connectors in any
	 * other capsule, {@linkplain #getCapsule(String) get} that capsule, first,
	 * and {@linkplain UMLRTCapsule#getConnector(String) get} the connector from it.
	 * 
	 * @param name
	 *            the connector name to get from Capsule1
	 * 
	 * @return the connector
	 */
	protected UMLRTConnector getConnector(String name) {
		return getCapsule("Capsule1").getConnector(name);
	}

	/**
	 * Obtains the redefinition of an {@code element} in the current diagram context.
	 * 
	 * @param element
	 *            an element in the superclass diagram
	 * 
	 * @return the redefinition of it in the context of the currently active diagram
	 */
	protected <T extends UMLRTNamedElement> T getRedefinition(T element) {
		T result = null;

		EObject context = editor.getActiveDiagram().resolveSemanticElement();
		if (context instanceof NamedElement) {
			UMLRTNamedElement rtContext = UMLRTFactory.create((NamedElement) context);
			result = rtContext.getRedefinitionOf(element);
		}

		if (result == null) {
			// Perhaps it's a port on a part? In which case, it had better be unique,
			// otherwise the test probably won't work
			result = element;
		}

		return result;
	}

	/**
	 * Obtains the redefinition of an {@code element} in the current diagram context.
	 * 
	 * @param element
	 *            an element in the superclass diagram
	 * 
	 * @return the redefinition of it in the context of the currently active diagram
	 */
	protected <T extends NamedElement> T getRedefinition(T element) {
		T result = null;

		EObject context = editor.getActiveDiagram().resolveSemanticElement();
		if (context instanceof NamedElement) {
			Namespace rtContext = ((NamedElement) context).getNamespace();
			@SuppressWarnings("unchecked")
			T redefinition = (T) rtContext.allOwnedElements().stream()
					.filter(element.getClass()::isInstance)
					.filter(e -> UMLRTExtensionUtil.redefines(e, element))
					.findFirst()
					.orElse(null);
			result = redefinition;
		}

		if (result == null) {
			// Perhaps it's a port on a part? In which case, it had better be unique,
			// otherwise the test probably won't work
			result = element;
		}

		return result;
	}

	protected View requireView(FacadeObject element) {
		return requireView(element.toUML());
	}

	protected IGraphicalEditPart requireEditPart(FacadeObject element) {
		return requireEditPart(element.toUML());
	}

	protected IGraphicalEditPart getEditPart(FacadeObject element) {
		return getEditPart(element.toUML());
	}

	protected IGraphicalEditPart getEditPart(FacadeObject element, IGraphicalEditPart scope) {
		return super.getEditPart(element.toUML(), scope);
	}

	protected IGraphicalEditPart requireConnectionEditPart(FacadeObject element) {
		return requireConnectionEditPart(element.toUML());
	}

	protected IGraphicalEditPart getConnectionEditPart(FacadeObject element) {
		return getConnectionEditPart(element.toUML());
	}

	protected IFigure getCoreFigure(IGraphicalEditPart editPart) {
		IFigure result;
		if (editPart instanceof BorderNodeEditPart) {
			result = ((BorderNodeEditPart) editPart).getPrimaryShape();
		} else if (editPart instanceof NodeEditPart) {
			result = ((NodeEditPart) editPart).getPrimaryShape();
		} else {
			result = editPart.getFigure();
		}
		return result;
	}

	protected IGraphicalEditPart requireLabel(UMLRTNamedElement element) {
		IGraphicalEditPart mainEP = requireEditPart(element);
		Optional<? extends IGraphicalEditPart> result = ((List<?>) mainEP.getChildren()).stream()
				.filter(LabelEditPart.class::isInstance)
				.map(LabelEditPart.class::cast)
				.findFirst();

		return result.orElseThrow(() -> new AssertionError("No label found for edit-part of " + element));
	}

	protected void move(IGraphicalEditPart editPart, int deltaX, int deltaY) {
		ChangeBoundsRequest request = new ChangeBoundsRequest(RequestConstants.REQ_MOVE);
		request.setMoveDelta(new Point(deltaX, deltaY));
		request.setEditParts(editPart);

		Command move = editPart.getCommand(request);
		assertThat(move, notNullValue());
		assertThat(move.canExecute(), is(true));

		// If we're moving it, it must be a node with a location
		Node node = (Node) editPart.getNotationView();
		Location location = (Location) node.getLayoutConstraint();
		Point oldLocation = new Point(location.getX(), location.getY());

		execute(move);

		location = (Location) node.getLayoutConstraint(); // In case it was created anew
		Point newLocation = new Point(location.getX(), location.getY());

		assertThat("Edit-part was not moved", newLocation, not(oldLocation));
	}

	protected void resize(IGraphicalEditPart editPart, int deltaWidth, int deltaHeight) {
		ChangeBoundsRequest request = new ChangeBoundsRequest(RequestConstants.REQ_RESIZE);
		request.setSizeDelta(new Dimension(deltaWidth, deltaHeight));
		request.setCenteredResize(true);
		request.setEditParts(editPart);

		Command resize = editPart.getCommand(request);
		assertThat(resize, notNullValue());
		assertThat(resize.canExecute(), is(true));

		// If we're resizing and moving it, it must be a node with a bounds
		Node node = (Node) editPart.getNotationView();
		Bounds bounds = (Bounds) node.getLayoutConstraint();
		Rectangle oldBounds = new Rectangle(bounds.getX(), bounds.getY(), bounds.getWidth(), bounds.getHeight());

		execute(resize);

		bounds = (Bounds) node.getLayoutConstraint(); // In case it was created anew
		Rectangle newBounds = new Rectangle(bounds.getX(), bounds.getY(), bounds.getWidth(), bounds.getHeight());

		assertThat("Edit-part was not resized", newBounds, not(oldBounds));
	}

	protected void bend(IGraphicalEditPart editPart, int x, int y) {
		BendpointRequest request = new BendpointRequest();
		request.setType(RequestConstants.REQ_CREATE_BENDPOINT);
		request.setSource((ConnectionEditPart) editPart);
		request.setLocation(new Point(x, y));

		Connection connection = (Connection) editPart.getFigure();
		connection.getPoints().insertPoint(new Point(x, y), 1);

		Command bend = editPart.getCommand(request);
		assertThat(bend, notNullValue());
		assertThat(bend.canExecute(), is(true));

		// If we're adding a bendpoint, it must be an edge
		Edge edge = (Edge) editPart.getNotationView();
		RelativeBendpoints bendpoints = (RelativeBendpoints) edge.getBendpoints();
		List<?> oldPoints = bendpoints.getPoints();

		execute(bend);

		bendpoints = (RelativeBendpoints) edge.getBendpoints(); // In case it was created anew
		List<?> newPoints = bendpoints.getPoints();

		assertThat("No bend-point was added", newPoints.size(), greaterThan(oldPoints.size()));
	}

	protected void unfollow(IGraphicalEditPart editPart) {
		View view = editPart.getNotationView();
		Optional<BooleanValueStyle> style = IInheritableEditPart.StyleUtil.getInheritedStyle(view);
		if (!style.isPresent()) {
			execute(IInheritableEditPart.StyleUtil.getCreateInheritedStyle(view));
		} else if (style.get().isBooleanValue()) {
			execute(SetCommand.create(editor.getEditingDomain(), style.get(),
					NotationPackage.Literals.BOOLEAN_VALUE_STYLE__BOOLEAN_VALUE, false));
		}
	}

	protected void exclude(UMLRTNamedElement element) {
		exclude(element.toUML());
	}

	protected void exclude(Element element) {
		ExcludeRequest request = new ExcludeRequest(element, true);
		ICommand exclude = ElementEditServiceUtils.getCommandProvider(element).getEditCommand(request);

		assertThat("No exclude command provided", exclude, notNullValue());
		editor.execute(exclude);
	}

	/**
	 * Verifies that an edit-part in the {@link local} diagram context followed the corresponding
	 * edit-part {@link inherited} from the supertype context in the specified detail.
	 * 
	 * @param inherited
	 *            an edit-part in the inherited diagram context
	 * @param local
	 *            the corresponding edit-part in the inheriting/redefining diagram context
	 * @param detailType
	 *            the kind of detail of the notation view to verify
	 * 
	 * @throws IllegalArgumentException
	 *             if the {@code detailType} is not a class of the Notation package
	 */
	protected void assertFollowed(IGraphicalEditPart inherited, IGraphicalEditPart local, EClass detailType) {
		if (detailType.getEPackage() != NotationPackage.eINSTANCE) {
			throw new IllegalArgumentException("not a Notation class: " + detailType.getName());
		}

		switch (detailType.getClassifierID()) {
		case NotationPackage.LOCATION:
			assertDetail(inherited, local, detailType, Location.class, this::assertEquals);
			break;
		case NotationPackage.SIZE:
			assertDetail(inherited, local, detailType, Size.class, this::assertEquals);
			break;
		case NotationPackage.BOUNDS:
			assertDetail(inherited, local, detailType, Bounds.class, this::assertEquals);
			break;
		case NotationPackage.RELATIVE_BENDPOINTS:
			assertDetail(inherited, local, detailType, RelativeBendpoints.class, this::assertEquals);
			break;
		}
	}

	/**
	 * Verifies that an edit-part in the {@link local} diagram context followed the corresponding
	 * edit-part {@link inherited} from the supertype context in the specified detail.
	 * 
	 * @param inherited
	 *            an edit-part in the inherited diagram context
	 * @param local
	 *            the corresponding edit-part in the inheriting/redefining diagram context
	 * @param detailFeature
	 *            a specific feature of the local view or some part of it to verify
	 * 
	 * @throws IllegalArgumentException
	 *             if the {@code detailType} is not a class of the Notation package
	 */
	protected void assertFollowed(IGraphicalEditPart inherited, IGraphicalEditPart local, EStructuralFeature detailFeature) {
		EClass detailType = detailFeature.getEContainingClass();

		if (detailType.getEPackage() != NotationPackage.eINSTANCE) {
			throw new IllegalArgumentException("not a Notation class: " + detailType.getName());
		}

		assertDetail(inherited, local, detailType, detailFeature,
				(actual, expected) -> assertThat("wrong " + detailFeature.getName(), actual, is(expected)));
	}

	private void assertEquals(Location expected, Location actual) {
		assertThat("wrong x coördinate", actual.getX(), is(expected.getX()));
		assertThat("wrong y coördinate", actual.getY(), is(expected.getY()));
	}

	private void assertEquals(Size expected, Size actual) {
		assertThat("wrong width", actual.getWidth(), is(expected.getWidth()));
		assertThat("wrong height", actual.getHeight(), is(expected.getHeight()));
	}

	private void assertEquals(Bounds expected, Bounds actual) {
		assertEquals((Location) expected, (Location) actual);
		assertEquals((Size) expected, (Size) actual);
	}

	private void assertEquals(RelativeBendpoints expected, RelativeBendpoints actual) {
		assertThat("wrong bendpoints", actual.getPoints(), is(expected.getPoints()));
	}

	/**
	 * Verifies that an edit-part in the {@link local} diagram context followed the corresponding
	 * edit-part {@link inherited} from the supertype context in the specified detail.
	 * 
	 * @param inherited
	 *            an edit-part in the inherited diagram context
	 * @param local
	 *            the corresponding edit-part in the inheriting/redefining diagram context
	 * @param detailEClass
	 *            the metaclass of the detail of the notation view to verify
	 * @param detailType
	 *            the kind of detail of the notation view to verify
	 * 
	 * @throws IllegalArgumentException
	 *             if the {@code detailType} is not a class of the Notation package
	 */
	private <T extends EObject> void assertDetail(IGraphicalEditPart inherited, IGraphicalEditPart local,
			EClass detailEClass, Class<T> detailType, BiConsumer<? super T, ? super T> assertion) {

		View inheritedView = inherited.getNotationView();
		View localView = local.getNotationView();

		// First, make sure that we aren't comparing an object to itself
		assertThat(localView, not(sameInstance(inheritedView)));

		EStructuralFeature detail;

		switch (detailEClass.getClassifierID()) {
		case NotationPackage.LOCATION:
		case NotationPackage.SIZE:
		case NotationPackage.BOUNDS:
			detail = NotationPackage.Literals.NODE__LAYOUT_CONSTRAINT;
			break;
		case NotationPackage.RELATIVE_BENDPOINTS:
			detail = NotationPackage.Literals.EDGE__BENDPOINTS;
			break;
		default:
			throw new IllegalArgumentException("Unsupported assertion detail: " + detailEClass);
		}

		Object inheritedDetail = inheritedView.eGet(detail);
		Object localDetail = localView.eGet(detail);

		assertion.accept(detailType.cast(localDetail), detailType.cast(inheritedDetail));
	}

	/**
	 * Verifies that an edit-part in the {@link local} diagram context followed the corresponding
	 * edit-part {@link inherited} from the supertype context in the specified detail.
	 * 
	 * @param inherited
	 *            an edit-part in the inherited diagram context
	 * @param local
	 *            the corresponding edit-part in the inheriting/redefining diagram context
	 * @param detailEClass
	 *            the metaclass of the detail of the notation view to verify
	 * @param detailType
	 *            the kind of detail of the notation view to verify
	 * 
	 * @throws IllegalArgumentException
	 *             if the {@code detailType} is not a class of the Notation package
	 */
	private <T> void assertDetail(IGraphicalEditPart inherited, IGraphicalEditPart local,
			EClass detailEClass, EStructuralFeature detailFeature, BiConsumer<? super T, ? super T> assertion) {

		View inheritedView = inherited.getNotationView();
		View localView = local.getNotationView();

		// First, make sure that we aren't comparing an object to itself
		assertThat(localView, not(sameInstance(inheritedView)));

		EStructuralFeature detail;

		switch (detailEClass.getClassifierID()) {
		case NotationPackage.LOCATION:
		case NotationPackage.SIZE:
		case NotationPackage.BOUNDS:
			detail = NotationPackage.Literals.NODE__LAYOUT_CONSTRAINT;
			break;
		case NotationPackage.RELATIVE_BENDPOINTS:
			detail = NotationPackage.Literals.EDGE__BENDPOINTS;
			break;
		default:
			throw new IllegalArgumentException("Unsupported assertion detail: " + detailEClass);
		}

		Object inheritedDetail = ((EObject) inheritedView.eGet(detail)).eGet(detailFeature);
		Object localDetail = ((EObject) localView.eGet(detail)).eGet(detailFeature);

		@SuppressWarnings("unchecked")
		Class<T> detailType = (Class<T>) Primitives.wrap(detailFeature.getEType().getInstanceClass());
		assertion.accept(detailType.cast(localDetail), detailType.cast(inheritedDetail));
	}

	/**
	 * Verifies that an edit-part in the {@link local} diagram context did not follow the corresponding
	 * edit-part {@link inherited} from the supertype context in the specified detail.
	 * 
	 * @param inherited
	 *            an edit-part in the inherited diagram context
	 * @param local
	 *            the corresponding edit-part in the inheriting/redefining diagram context
	 * @param detailType
	 *            the kind of detail of the notation view to verify
	 * 
	 * @throws IllegalArgumentException
	 *             if the {@code detailType} is not a class of the Notation package
	 */
	protected void assertNotFollowed(IGraphicalEditPart inherited, IGraphicalEditPart local, EClass detailType) {
		if (detailType.getEPackage() != NotationPackage.eINSTANCE) {
			throw new IllegalArgumentException("not a Notation class: " + detailType.getName());
		}

		switch (detailType.getClassifierID()) {
		case NotationPackage.LOCATION:
			assertDetail(inherited, local, detailType, Location.class, this::assertNotEquals);
			break;
		case NotationPackage.SIZE:
			assertDetail(inherited, local, detailType, Size.class, this::assertNotEquals);
			break;
		case NotationPackage.BOUNDS:
			assertDetail(inherited, local, detailType, Bounds.class, this::assertNotEquals);
			break;
		case NotationPackage.RELATIVE_BENDPOINTS:
			assertDetail(inherited, local, detailType, RelativeBendpoints.class, this::assertNotEquals);
			break;
		}
	}

	private void assertNotEquals(Location expected, Location actual) {
		assertThat("same location", actual, not(both(compose(Location::getX, is(expected.getX()))).and(
				compose(Location::getY, is(expected.getY())))));
	}

	private void assertNotEquals(Size expected, Size actual) {
		assertThat("same size", actual, not(both(compose(Size::getWidth, is(expected.getWidth()))).and(
				compose(Size::getHeight, is(expected.getHeight())))));
	}

	private void assertNotEquals(Bounds expected, Bounds actual) {
		assertThat("same bounds", actual, not(both(compose(Bounds::getWidth, is(expected.getWidth()))).and(
				compose(Bounds::getHeight, is(expected.getHeight()))).and(
						compose(Location::getX, is(expected.getX())))
				.and(compose(Location::getY, is(expected.getY())))));
	}

	private void assertNotEquals(RelativeBendpoints expected, RelativeBendpoints actual) {
		assertThat("same bendpoints", actual.getPoints(), not(expected.getPoints()));
	}
}
