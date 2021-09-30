/*****************************************************************************
 * Copyright (c) 2016, 2017 Christian W. Damus and others.
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

package org.eclipse.papyrusrt.umlrt.tooling.diagram.common.internal.utils;

import static org.eclipse.papyrusrt.umlrt.uml.util.UMLRTExtensionUtil.redefines;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.UnexecutableCommand;
import org.eclipse.gmf.runtime.common.core.command.CompositeCommand;
import org.eclipse.gmf.runtime.diagram.core.edithelpers.CreateElementRequestAdapter;
import org.eclipse.gmf.runtime.diagram.core.util.ViewUtil;
import org.eclipse.gmf.runtime.diagram.ui.commands.CommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.commands.CreateCommand;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.commands.SemanticCreateCommand;
import org.eclipse.gmf.runtime.diagram.ui.editparts.CompartmentEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.l10n.DiagramUIMessages;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewAndElementRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.EditCommandRequestWrapper;
import org.eclipse.gmf.runtime.diagram.ui.requests.RefreshConnectionsRequest;
import org.eclipse.gmf.runtime.emf.commands.core.command.CompositeTransactionalCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.tools.util.TypeUtils;
import org.eclipse.papyrusrt.umlrt.core.utils.Either;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.editparts.IInheritableEditPart;
import org.eclipse.papyrusrt.umlrt.uml.FacadeObject;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsule;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsulePart;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTConnectionPoint;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTConnector;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTFactory;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTPort;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTReplicatedElement;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTState;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTStateMachine;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTTransition;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTVertex;
import org.eclipse.papyrusrt.umlrt.uml.util.UMLRTExtensionUtil;
import org.eclipse.papyrusrt.umlrt.uml.util.UMLRTSwitch;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Connector;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Namespace;
import org.eclipse.uml2.uml.Port;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Pseudostate;
import org.eclipse.uml2.uml.Region;
import org.eclipse.uml2.uml.State;
import org.eclipse.uml2.uml.StateMachine;
import org.eclipse.uml2.uml.Transition;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.Vertex;
import org.eclipse.uml2.uml.util.UMLSwitch;

/**
 * Static utilities for externalization of common behaviour of edit-parts
 * that support inheritance of views from other diagrams.
 * 
 * @see IInheritableEditPart
 */
public class EditPartInheritanceUtils {

	/**
	 * Not instantiable by clients
	 */
	private EditPartInheritanceUtils() {
		super();
	}

	public static <T extends EObject> T resolveSemanticElement(EditPart editPart, java.lang.Class<T> type) {
		EObject result = (editPart instanceof IGraphicalEditPart)
				? ((IGraphicalEditPart) editPart).resolveSemanticElement()
				: (editPart.getModel() instanceof View)
						? resolveSemanticElement((View) editPart.getModel())
						: null;

		return TypeUtils.as(result, type);
	}

	/**
	 * Resolves the semantic element in an inheritable edit-part, accounting for inheritance of the element
	 * to compute the possible inherited/redefined element in the context of the inheriting diagram.
	 * 
	 * @param editPart
	 *            an inheritable edit-part
	 * @param result
	 *            the result of the default resolution algorithm, which usually should just be
	 *            {@code super.resolveSemanticElement()} to access the default GMF/Papyrus algorithm
	 * @return the resolved semantic element
	 */
	public static EObject resolveSemanticElement(IInheritableEditPart editPart, EObject result) {
		if (result == null) {
			// Can't resolve a null
			return result;
		}

		return new UMLSwitch<EObject>() {
			@Override
			public EObject caseProperty(Property property) {
				Class context = editPart.resolveContext(Class.class);
				if ((context != null) && (context != property.getClass_())) {
					// It's inherited
					Property redefiningProperty = resolveRedefiningProperty(property, context);
					if (redefiningProperty != null) {
						return redefiningProperty;
					}
				}

				return property;
			}

			@Override
			public EObject caseConnector(Connector connector) {
				UMLRTConnector facade = UMLRTConnector.getInstance(connector);
				if (facade != null) {
					Class context = editPart.resolveContext(Class.class);
					UMLRTCapsule capsule = (context == null) ? null : UMLRTCapsule.getInstance(context);
					if (context != null) {
						facade = capsule.getRedefinitionOf(facade);
						if (facade != null) {
							return facade.toUML();
						}
					}
				}

				return connector;
			}

			@Override
			public EObject caseRegion(Region region) {
				Either<State, StateMachine> context = Either.or(
						editPart.resolveContext(State.class),
						() -> editPart.resolveContext(StateMachine.class));

				return context.flatMapEither(Namespace.class, namespace -> {
					return namespace.getOwnedMembers().stream()
							.filter(Region.class::isInstance).map(Region.class::cast)
							.filter(r -> UMLRTExtensionUtil.redefines(r, region))
							.findFirst();
				}).orElse(region);
			}

			@Override
			public EObject casePseudostate(Pseudostate pseudostate) {
				// Handle state connection points
				if (pseudostate.getState() != null) {
					UMLRTConnectionPoint facade = UMLRTConnectionPoint.getInstance(pseudostate);
					if (facade != null) {
						UMLRTState context = UMLRTState.getInstance(editPart.resolveContext(State.class));
						return Optional.ofNullable(context)
								.map(state -> state.getRedefinitionOf(facade))
								.map(UMLRTConnectionPoint::toUML)
								.orElse(pseudostate);
					}
				}

				// Go to the more general vertex case
				return null;
			}

			@Override
			public EObject caseVertex(Vertex vertex) {
				UMLRTVertex facade = UMLRTVertex.getInstance(vertex);
				if (facade != null) {
					Either<State, StateMachine> context = Either.or(
							editPart.resolveContext(State.class),
							() -> editPart.resolveContext(StateMachine.class));

					return context.mapEither(Namespace.class, namespace -> {
						UMLRTNamedElement stateOrSM = UMLRTFactory.create(namespace);
						return (stateOrSM != null)
								? stateOrSM.getRedefinitionOf(facade)
								: null;
					}).map(UMLRTNamedElement::toUML)
							.orElse(vertex);
				}

				return vertex;
			}

			@Override
			public EObject caseTransition(Transition transition) {
				UMLRTTransition facade = UMLRTTransition.getInstance(transition);
				if (facade != null) {
					Either<State, StateMachine> context = Either.or(
							editPart.resolveContext(State.class),
							() -> editPart.resolveContext(StateMachine.class));

					return context.mapEither(Namespace.class, namespace -> {
						UMLRTNamedElement stateOrSM = UMLRTFactory.create(namespace);
						return (stateOrSM != null)
								? stateOrSM.getRedefinitionOf(facade)
								: null;
					}).map(UMLRTNamedElement::toUML)
							.orElse(transition);
				}

				return transition;
			}

			@Override
			public EObject defaultCase(EObject object) {
				return object;
			}
		}.doSwitch(result);
	}

	static Property resolveRedefiningProperty(Property inheritedProperty, Class context) {
		return resolveRedefiningProperty(inheritedProperty, UMLRTCapsule.getInstance(context));
	}

	@SuppressWarnings("unchecked")
	static <T extends Property> T resolveRedefiningProperty(T inheritedProperty, UMLRTCapsule capsule) {
		T result = null;

		UMLRTReplicatedElement redefined = (UMLRTReplicatedElement) UMLRTFactory.create(inheritedProperty);

		if ((capsule != null) && (redefined != null)) {
			result = (T) ((redefined instanceof UMLRTPort) ? capsule.getPorts() : capsule.getCapsuleParts()).stream()
					.filter(p -> p.redefines(redefined))
					.findFirst()
					.map(UMLRTReplicatedElement::toUML)
					.orElse(null);
		}

		return result;
	}

	public static UMLRTCapsule getContextCapsule(View view) {
		UMLRTCapsule result = null;

		for (View next = view; (result == null) && (next != null); next = ViewUtil.getContainerView(next)) {
			EObject semantic = ViewUtil.resolveSemanticElement(next);
			if (semantic instanceof org.eclipse.uml2.uml.Class) {
				result = UMLRTCapsule.getInstance((org.eclipse.uml2.uml.Class) semantic);
			}
		}

		return result;
	}

	public static UMLRTStateMachine getContextStateMachine(View view) {
		UMLRTStateMachine result = null;

		for (View next = view; (result == null) && (next != null); next = ViewUtil.getContainerView(next)) {
			EObject semantic = ViewUtil.resolveSemanticElement(next);
			if (semantic instanceof StateMachine) {
				result = UMLRTStateMachine.getInstance((StateMachine) semantic);
			} else if ((semantic instanceof State) && (ViewUtil.getContainerView(next) == null)) {
				// Top state. It's a redefinition, so get its state macine
				result = UMLRTStateMachine.getInstance(((State) semantic).containingStateMachine());
			}
		}

		return result;
	}

	public static EObject resolveSemanticElement(View view) {
		EObject result = ViewUtil.resolveSemanticElement(view);

		UMLRTCapsule capsule = getContextCapsule(view);
		if (capsule != null) {
			FacadeObject facade = UMLRTFactory.create(result);
			if (facade != null) {
				result = resolve(capsule, facade, view).toUML();
			}
		} else {
			// Look for a state machine context
			UMLRTStateMachine stateMachine = getContextStateMachine(view);
			if (stateMachine != null) {
				if (result instanceof Region) {
					// Is it the region of a composite state?
					Either<UMLRTState, UMLRTStateMachine> container = Either.or(
							Optional.ofNullable(((Region) result).getState()).map(UMLRTState::getInstance),
							() -> Optional.of(stateMachine));

					// Resolve the state (if any) in the context of the state machine
					container = container.map(state -> stateMachine.getRedefinitionOf(state));

					// Resolve to the state or state machine's region (we know we
					// at least have a state machine)
					@SuppressWarnings("unchecked")
					List<Region> regions = (List<Region>) container.map(
							state -> UMLRTExtensionUtil.<Region> getUMLRTContents(state.toUML(), UMLPackage.Literals.STATE__REGION),
							sm -> UMLRTExtensionUtil.<Region> getUMLRTContents(sm.toUML(), UMLPackage.Literals.STATE_MACHINE__REGION))
							.getEither();
					if (!regions.isEmpty()) {
						result = regions.get(0);
					}
				} else {
					FacadeObject facade = UMLRTFactory.create(result);
					if (facade != null) {
						result = resolve(stateMachine, facade).toUML();
					}
				}
			}
		}

		return result;
	}

	static <T extends FacadeObject> T resolve(UMLRTCapsule semanticContext, T element, View view) {
		@SuppressWarnings("unchecked")
		UMLRTSwitch<T> switch_ = new UMLRTSwitch<T>() {
			@Override
			public T casePort(UMLRTPort port) {
				return (T) resolvePort(port.toUML(), semanticContext, view);
			}

			@Override
			public T caseCapsulePart(UMLRTCapsulePart capsulePart) {
				return (T) resolveCapsulePart(capsulePart.toUML(), semanticContext, view);
			}

			@Override
			public T defaultCase(EObject object) {
				return (T) object;
			};
		};
		return switch_.doSwitch(element);
	}

	public static UMLRTPort resolvePort(Port uml, UMLRTCapsule capsule, View portView) {
		UMLRTPort result = null;

		View portParentView = ViewUtil.getContainerView(portView);
		EObject portParent = ViewUtil.resolveSemanticElement(portParentView);
		if (portParent instanceof Property) {
			// It could be inherited
			UMLRTCapsulePart part = resolveCapsulePart((Property) portParent, capsule, portParentView);
			if (part != null) {
				// Resolve the port in the part's capsule type
				UMLRTCapsule type = part.getType();
				if (type != null) {
					result = UMLRTPort.getInstance(resolveRedefiningProperty(uml, type));
				}
			}
		} else {
			// It's a port on the capsule
			result = UMLRTPort.getInstance(resolveRedefiningProperty(uml, capsule));
		}

		return result;
	}

	public static UMLRTCapsulePart resolveCapsulePart(Property uml, UMLRTCapsule capsule, View partView) {
		Property result = resolveRedefiningProperty(uml, capsule);

		return (result == null)
				? UMLRTCapsulePart.getInstance(uml)
				: UMLRTCapsulePart.getInstance(result);
	}

	static <T extends FacadeObject> T resolve(UMLRTStateMachine semanticContext, T element) {
		@SuppressWarnings("unchecked")
		UMLRTSwitch<T> switch_ = new UMLRTSwitch<T>() {
			@Override
			public T caseConnectionPoint(UMLRTConnectionPoint connectionPoint) {
				UMLRTState state = resolve(semanticContext, connectionPoint.getState());
				return (T) state.getRedefinitionOf(connectionPoint);
			}

			@Override
			public T caseVertex(UMLRTVertex vertex) {
				Either<UMLRTState, UMLRTStateMachine> container = Either.or(
						vertex.getState(), () -> semanticContext);

				// Resolve the state if that is our container
				container = container.map(state -> resolve(semanticContext, state));

				return (T) container.mapEither(UMLRTNamedElement.class, c -> c.getRedefinitionOf(vertex))
						.orElse(null);
			}

			@Override
			public T caseTransition(UMLRTTransition transition) {
				Either<UMLRTState, UMLRTStateMachine> container = Either.or(
						transition.getState(), () -> semanticContext);

				// Resolve the state if that is our container
				container = container.map(state -> resolve(semanticContext, state));

				return (T) container.mapEither(UMLRTNamedElement.class, c -> c.getRedefinitionOf(transition))
						.orElse(null);
			}

			@Override
			public T defaultCase(EObject object) {
				return (T) object;
			};
		};

		return switch_.doSwitch(element);
	}

	/**
	 * Obtains an adapter for an inheritable edit-part, accounting for inheritance by delegating
	 * the {@link EObject} adapter case for the semantic element to the edit-part's
	 * {@linkplain IGraphicalEditPart#resolveSemanticElement() intrinsic resolution} method.
	 * 
	 * @param editPart
	 *            an edit-part for which to get an adapter
	 * @param key
	 *            the adapter type
	 * @param result
	 *            the default adapter (usually obtain via {@code super.getAdapter(key)}
	 * 
	 * @return the adapter, accounting for inheritance in the case of the semantic element adapter
	 */
	public static Object getAdapter(IGraphicalEditPart editPart, @SuppressWarnings("rawtypes") java.lang.Class key, Object result) {
		if (result instanceof EObject) {
			// Don't attempt to resolve an unloaded view's element because that will lead
			// to an NPE in GMF when the edit-part tries to commit a read-only transaction
			// in an editing-domain that has been disposed
			View view = editPart.getNotationView();
			if ((view != null) && (result == view.getElement()) && !view.eIsProxy()) {
				// Let the edit-part resolve the semantic element properly
				result = editPart.resolveSemanticElement();
			}
		}

		return result;
	}

	public static <T extends UMLRTNamedElement> Optional<T> getGraphicalRedefinitionContext(
			UMLRTNamedElement rtElement, java.lang.Class<T> contextType) {

		T result = null;

		NamedElement element = (rtElement == null) ? null : rtElement.toUML();
		if (element != null) {
			for (Element owner = element.getOwner(); (result == null) && (owner != null); owner = owner.getOwner()) {
				// The graphical redefinition context is some kind of namespace
				// (classifier is too specific because states are not classifiers)
				if (owner instanceof Namespace) {
					Namespace namespace = (Namespace) owner;
					result = TypeUtils.as(UMLRTFactory.create(namespace), contextType);
				}
			}
		}

		return Optional.ofNullable(result);
	}

	public static UMLRTCapsule getContextCapsule(UMLRTNamedElement rtElement) {
		return getGraphicalRedefinitionContext(rtElement, UMLRTCapsule.class).orElse(null);
	}

	public static Either<UMLRTState, UMLRTStateMachine> getStateMachineContext(UMLRTNamedElement rtElement) {
		// A state would be encountered before a state machine, so short-circuit on that
		return Either.or(getGraphicalRedefinitionContext(rtElement, UMLRTState.class),
				() -> getGraphicalRedefinitionContext(rtElement, UMLRTStateMachine.class));
	}

	/**
	 * Obtains the state or state machine that is the context of the diagram containing
	 * the given {@code view}. The context may be an inherited (redefined) element or
	 * it may be a root definition.
	 * 
	 * @param view
	 *            a view in some state machine diagram
	 * @return the state or state machine that is its context
	 */
	public static Either<UMLRTState, UMLRTStateMachine> getStateMachineContext(View view) {
		Diagram diagram = view.getDiagram();
		return (diagram == null)
				? Either.empty()
				: Either.cast(diagram.getElement(), State.class, StateMachine.class)
						.map(UMLRTState::getInstance, UMLRTStateMachine::getInstance);
	}

	public static Command getCreateElementAndViewCommand(IGraphicalEditPart editPart, CreateViewAndElementRequest request,
			Function<? super CreateViewAndElementRequest, ? extends Command> createView) {

		CreateElementRequestAdapter requestAdapter = request.getViewAndElementDescriptor().getCreateElementRequestAdapter();
		CreateElementRequest createElementRequest = (CreateElementRequest) requestAdapter.getAdapter(CreateElementRequest.class);

		// complete the semantic request by filling in the host's semantic
		// element as the context
		View view = editPart.getNotationView();
		EObject hostElement = editPart.resolveSemanticElement();

		if ((hostElement == null) && (view.getElement() == null)) {
			hostElement = view;
		}

		// Returns null if host is unresolvable so that trying to create a
		// new element in an unresolved shape will not be allowed.
		if (hostElement == null) {
			return null;
		}
		createElementRequest.setContainer(hostElement);

		// get the create element command based on the element descriptor's
		// request
		Command createElementCommand = editPart.getCommand(
				new EditCommandRequestWrapper(createElementRequest, request.getExtendedData()));

		if (createElementCommand == null) {
			return UnexecutableCommand.INSTANCE;
		}
		if (!createElementCommand.canExecute()) {
			return createElementCommand;
		}

		// create the semantic create wrapper command
		SemanticCreateCommand semanticCommand = new SemanticCreateCommand(requestAdapter, createElementCommand);
		Command viewCommand = createView.apply(request);

		Command refreshConnectionsCommand = editPart.getCommand(
				new RefreshConnectionsRequest(((List<?>) request.getNewObject())));

		// form the compound command and return
		CompositeCommand cc = new CompositeCommand(semanticCommand.getLabel());
		cc.compose(semanticCommand);
		cc.compose(new CommandProxy(viewCommand));
		if (refreshConnectionsCommand != null) {
			cc.compose(new CommandProxy(refreshConnectionsCommand));
		}

		return new ICommandProxy(cc);
	}

	public static Command getCreateCommand(IGraphicalEditPart editPart, CreateViewRequest request) {
		TransactionalEditingDomain editingDomain = editPart.getEditingDomain();
		CompositeTransactionalCommand cc = new CompositeTransactionalCommand(
				editingDomain, DiagramUIMessages.AddCommand_Label);

		Iterator<? extends CreateViewRequest.ViewDescriptor> descriptors = request.getViewDescriptors().iterator();

		while (descriptors.hasNext()) {
			CreateViewRequest.ViewDescriptor descriptor = descriptors.next();

			CreateCommand createCommand = new CreateCommand(editingDomain, descriptor, editPart.getNotationView());
			cc.compose(createCommand);
		}

		return new ICommandProxy(cc.reduce());
	}

	/**
	 * Externalizes the graphical edit-part {@code handleNotificationEvent} method to account
	 * for changes in the notation view's semantic element reference that don't actually
	 * change the resolved semantic element from an inheritance perspective.
	 * 
	 * @param editPart
	 *            an edit-part
	 * @param notification
	 *            a notification from some object related to it
	 * @param defaultHandler
	 *            the default notification handler, which usually should be
	 *            {@code super::handleNotificationEvent} in the caller's context
	 */
	public static void handleNotificationEvent(IGraphicalEditPart editPart, Notification notification,
			Consumer<? super Notification> defaultHandler) {

		// Is it a change in the semantic element reference of the notation view
		// pertinent to the edit-part?
		Object feature = notification.getFeature();
		Object notifier = notification.getNotifier();

		if ((feature == NotationPackage.Literals.VIEW__ELEMENT)
				&& ((notifier == editPart.getModel())
						|| ((editPart instanceof CompartmentEditPart) && (editPart.getParent() != null) && (notifier == editPart.getParent().getModel())))) {

			// It is not a major semantic change if we adjust our view's element
			// for redefinition because the resolved semantic element remains the
			// same as before
			switch (notification.getEventType()) {
			case Notification.SET:
			case Notification.UNSET:
				EObject oldSemantic = (EObject) notification.getOldValue();
				EObject newSemantic = (EObject) notification.getNewValue();
				if (!(oldSemantic instanceof Region) || !(newSemantic instanceof Region)) {
					// Yeah, that's pretty major. Default handler will handle it
					defaultHandler.accept(notification);
				} else {
					Region oldRegion = (Region) oldSemantic;
					Region newRegion = (Region) newSemantic;
					if ((oldRegion != newRegion) && !(redefines(oldRegion, newRegion) || redefines(newRegion, oldRegion))) {
						// This also is major
						defaultHandler.accept(notification);
					}
				}
			}
		} else {
			defaultHandler.accept(notification);
		}
	}
}
