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

package org.eclipse.papyrusrt.umlrt.tooling.diagram.common.editparts;

import java.util.Optional;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.editparts.CompartmentEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramRootEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.emf.commands.core.command.CompositeTransactionalCommand;
import org.eclipse.gmf.runtime.notation.BooleanValueStyle;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.NamedStyle;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.emf.gmf.command.EMFtoGMFCommandWrapper;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.infra.tools.util.TypeUtils;
import org.eclipse.papyrus.uml.diagram.common.editparts.FloatingLabelEditPart;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.internal.utils.EditPartInheritanceUtils;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.utils.UMLRTCapsuleStructureDiagramUtils;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.utils.UMLRTEditPartUtils;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsule;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsulePart;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTFactory;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement;
import org.eclipse.papyrusrt.umlrt.uml.util.UMLRTExtensionUtil;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.NamedElement;

/**
 * Protocol implemented by edit-parts that can visualize elements inherited
 * from another diagram.
 */
public interface IInheritableEditPart extends IGraphicalEditPart {
	default Optional<UMLRTNamedElement> getUMLRTElement() {
		EObject semantic = null;

		View view = getNotationView();
		if ((view == null) || !view.eIsProxy()) {
			// Don't attempt to resolve an unloaded view's element because that will lead
			// to an NPE in GMF when the edit-part tries to commit a read-only transaction
			// in an editing-domain that has been disposed
			semantic = resolveSemanticElement();
		}

		return (semantic instanceof NamedElement)
				? Optional.ofNullable(UMLRTFactory.create((NamedElement) semantic))
				: Optional.empty();
	}

	/**
	 * Queries whether I track the visual state (position, routing, and more) of a
	 * corresponding view in the superclass diagram.
	 * 
	 * @return whether I visually inherit the superclass view
	 */
	default boolean isInherited() {
		boolean result;

		if (isDependentChild()) {
			// I do not own my inheritance state
			result = ((IInheritableEditPart) getParent()).isInherited();
		} else {
			// First, check whether I have the style that overrides tracking the redefined view
			result = StyleUtil.getInheritedStyle(getNotationView())
					.map(BooleanValueStyle::isBooleanValue)
					// And if not barred by the style, can I actually inherit anything?
					.orElseGet(this::canInherit);
		}

		return result;
	}

	/**
	 * Queries whether there is a view in the superclass diagram corresponding to
	 * me that I may track its position, routing, etc.
	 * 
	 * @return whether I have a corresponding superclass view
	 */
	default boolean canInherit() {
		boolean result;

		EditPart parent = getParent();
		IInheritableEditPart inheritableParent = TypeUtils.as(parent, IInheritableEditPart.class);

		// I can be visually inherited if either my semantic element is a redefinition
		// or I am visualized on a view of a redefinition that, recursively, is inherited
		if ((inheritableParent != null) && inheritableParent.isSemanticInherited()) {
			// I present the inheritance of my parent because it is my parent that
			// is inherited or redefined in the context of the diagram, not me
			// (for example, the case of a port on a part)
			result = inheritableParent.isInherited();
		} else {
			// I can only be considered inherited if my element is a redefinition
			result = isSemanticInherited();
		}

		return result;
	}

	default boolean isSemanticInherited() {
		return getUMLRTElement()
				.map(UMLRTNamedElement::isInherited)
				.orElse(false);
	}

	/**
	 * In the case that I visualize an {@linkplain #isSemanticInherited() inherited element},
	 * obtains the view in the diagram of the parent context that my view redefines.
	 * 
	 * @return my redefined view, or {@code null} if my element is not inherited
	 * 
	 * @see #isSemanticInherited()
	 */
	default View getRedefinedView() {
		View result = null;

		if (isInherited()) {
			View view = getNotationView();
			Optional<UMLRTNamedElement> redefinition;
			View parentView;

			if (getParent() instanceof IInheritableEditPart) {
				IInheritableEditPart part = (IInheritableEditPart) getParent();
				redefinition = part.getUMLRTElement();
				parentView = part.getRedefinedView();
			} else {
				redefinition = getUMLRTElement();
				parentView = redefinition
						.map(UMLRTNamedElement::getRedefinedElement)
						.map(this::getRedefinitionContext)
						.map(this::getPrimaryDiagram)
						.orElse(null);
			}

			if (parentView != null) {
				// Use the view's element directly because we always reference the
				// root definition, at every level of inheritance, except in the top node
				// which must reference the redefinition because of the viewpoint
				// restriction on the number of diagrams for any given element
				Element lookFor = (Element) view.getElement();
				if (UMLRTExtensionUtil.isInherited(lookFor)) {
					lookFor = UMLRTExtensionUtil.getRedefinedElement(lookFor);
				}
				result = UMLRTEditPartUtils.findView(parentView, view.getType(), lookFor);
			}
		}

		return result;
	}

	/**
	 * Gets the redefinition context, such as a capsule or a state machine, of the
	 * given {@code element} visualized within the diagram of such context.
	 * 
	 * @param element
	 *            an element that is redefinable in some context
	 * @return the redefinition context, for the purposes of diagram inheritance and not
	 *         UML-RT or UML inheritance, of the {@code element}
	 */
	default UMLRTNamedElement getRedefinitionContext(UMLRTNamedElement element) {
		return EditPartInheritanceUtils.getContextCapsule(element);
	}

	/**
	 * Obtains the primary diagram of the given redefinition {@code context},
	 * where redefinition here is graphical (diagram) redefinition, not the
	 * UML-RT or UML redefinition in the semantic model.
	 * 
	 * @param context
	 *            a graphical redefinition context
	 * @return its primary UML-RT diagram
	 */
	default Diagram getPrimaryDiagram(UMLRTNamedElement context) {
		return UMLRTCapsuleStructureDiagramUtils.getCapsuleStructureDiagram(
				(org.eclipse.uml2.uml.Class) context.toUML());
	}

	/**
	 * Queries whether I am a dependent child of an inheritable edit-part.
	 * Dependent children do not manage their visual inheritance on their own, but
	 * inherit strictly according to their parent edit-part. Floating labels
	 * are a good example of dependents.
	 * 
	 * @return whether I am a dependent child edit-part
	 */
	default boolean isDependentChild() {
		// Compartment edit-parts include also items in a list compartment, but
		// we don't position/size/colour/etc. them according to their inheritance
		// because they have icons that show the inheritance in a decorator
		return (this instanceof FloatingLabelEditPart)
				|| (this instanceof CompartmentEditPart);
	}

	default Command getReifyViewCommand() {
		return isDependentChild()
				// A dependent child does not maintain its own inheritance state
				? ((IInheritableEditPart) getParent()).getReifyViewCommand()
				: getUMLRTElement().map(rt -> {
					// An EMFtoGMFCommandWrapper doesn't, itself, create a transaction
					// so we need to put it in a transactional composite
					CompositeTransactionalCommand cc = new CompositeTransactionalCommand(getEditingDomain(), "Redefine View");
					View view = getNotationView();

					if (isInherited()) {
						cc.add(EMFtoGMFCommandWrapper.wrap(StyleUtil.getCreateInheritedStyle(view)));
					}

					return cc.isEmpty() ? null : new ICommandProxy(cc.reduce());
				}).orElse(null);
	}

	default <T extends NamedElement> T resolveContext(Class<T> contextType) {
		T result = null;
		for (EditPart parent = getParent(); (result == null) && (parent != null); parent = parent.getParent()) {
			EObject context = resolveContext(parent);

			if (contextType.isInstance(context)) {
				result = contextType.cast(context);
			}
		}

		return result;
	}

	default EObject resolveContext(EditPart editPart) {
		EObject result = null;

		if (editPart instanceof RTPropertyPartEditPart) {
			result = ((RTPropertyPartEditPart) editPart).getUMLRTElement()
					.map(UMLRTCapsulePart.class::cast)
					.map(UMLRTCapsulePart::getType)
					.map(UMLRTCapsule::toUML)
					.orElse(null);
		} else if (editPart instanceof IGraphicalEditPart) {
			result = ((IGraphicalEditPart) editPart).resolveSemanticElement();
		} else if (editPart instanceof DiagramEditPart) {
			DiagramEditPart diagram = (DiagramEditPart) editPart;
			result = EditPartInheritanceUtils.resolveSemanticElement(diagram.getDiagramView());
		} else if (editPart instanceof DiagramRootEditPart) {
			// We're looking up a connection edit part, probably
			DiagramEditPart diagram = (DiagramEditPart) editPart.getChildren().get(0);
			result = EditPartInheritanceUtils.resolveSemanticElement(diagram.getDiagramView());
		}

		return result;
	}

	//
	// Nested types
	//

	class StyleUtil {
		static final String INHERITED_STYLE = "papyrusrt.inherited"; //$NON-NLS-1$

		protected static <T extends NamedStyle> Optional<T> getStyle(View view, String name, Class<T> type) {
			return Optional.ofNullable(view.getNamedStyle(eClass(type), name))
					.map(type::cast);
		}

		protected static EClass eClass(Class<?> interface_) {
			return (EClass) NotationPackage.eINSTANCE.getEClassifier(interface_.getSimpleName());
		}

		protected static <T extends NamedStyle> T demandStyle(View view, String name, Class<T> type) {
			return getStyle(view, name, type).orElseGet(() -> createStyle(view, name, type));
		}

		protected static <T extends NamedStyle> T createStyle(View view, String name, Class<T> type) {
			T result = type.cast(view.createStyle(eClass(type)));
			result.setName(name);
			return result;
		}

		protected static <T extends NamedStyle> org.eclipse.emf.common.command.Command getCreateStyle(View view, String name, Class<T> type) {
			T style = type.cast(EcoreUtil.create(eClass(type)));
			style.setName(name);

			return AddCommand.create(EMFHelper.resolveEditingDomain(view),
					view, NotationPackage.Literals.VIEW__STYLES, style);
		}

		public static Optional<BooleanValueStyle> getInheritedStyle(View view) {
			return Optional.ofNullable((BooleanValueStyle) view.getNamedStyle(
					NotationPackage.Literals.BOOLEAN_VALUE_STYLE,
					INHERITED_STYLE));
		}

		public static BooleanValueStyle demandInheritedStyle(View view) {
			return getInheritedStyle(view).orElseGet(() -> {
				BooleanValueStyle result = (BooleanValueStyle) view.createStyle(NotationPackage.Literals.BOOLEAN_VALUE_STYLE);
				result.setName(INHERITED_STYLE);
				return result;
			});
		}

		public static org.eclipse.emf.common.command.Command getCreateInheritedStyle(View view) {
			return getCreateStyle(view, INHERITED_STYLE, BooleanValueStyle.class);
		}

		public static org.eclipse.emf.common.command.Command getDeleteStyle(View view, String name, Class<? extends NamedStyle> type) {
			return getStyle(view, name, type)
					.map(s -> RemoveCommand.create(EMFHelper.resolveEditingDomain(s), s))
					.orElse(null);
		}

		public static org.eclipse.emf.common.command.Command getDeleteInheritedStyle(View view) {
			return getDeleteStyle(view, INHERITED_STYLE, BooleanValueStyle.class);
		}
	}
}
