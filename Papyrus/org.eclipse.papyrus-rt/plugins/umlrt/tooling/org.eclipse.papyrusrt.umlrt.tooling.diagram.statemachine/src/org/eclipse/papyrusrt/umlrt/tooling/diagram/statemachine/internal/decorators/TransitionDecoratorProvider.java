/*****************************************************************************
 * Copyright (c) 2017 EclipseSource, Christian W. Damus, and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   EclipseSource - Initial API and implementation
 *   Christian W. Damus - bugs 510315, 512953, 517078, 517468
 *   
 *****************************************************************************/

package org.eclipse.papyrusrt.umlrt.tooling.diagram.statemachine.internal.decorators;

import java.util.Comparator;
import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.common.core.service.AbstractProvider;
import org.eclipse.gmf.runtime.common.core.service.IOperation;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.services.decorator.CreateDecoratorsOperation;
import org.eclipse.gmf.runtime.diagram.ui.services.decorator.IDecoratorProvider;
import org.eclipse.gmf.runtime.diagram.ui.services.decorator.IDecoratorTarget;
import org.eclipse.gmf.runtime.draw2d.ui.mapmode.IMapMode;
import org.eclipse.gmf.runtime.draw2d.ui.mapmode.MapModeUtil;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.tools.util.TypeUtils;
import org.eclipse.papyrus.uml.diagram.common.figure.node.ScaledImageFigure;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.TransitionEditPart;
import org.eclipse.papyrusrt.umlrt.core.utils.StateMachineUtils;
import org.eclipse.papyrusrt.umlrt.core.utils.TransitionUtils;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.decorator.AbstractUMLRTDecorator;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.internal.editpolicies.DecorationEditPolicyEx.TransitionDecoratorTarget;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.statemachine.Activator;
import org.eclipse.papyrusrt.umlrt.tooling.ui.internal.labelprovider.LabelUtils;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTInheritanceKind;
import org.eclipse.uml2.uml.Behavior;
import org.eclipse.uml2.uml.Constraint;
import org.eclipse.uml2.uml.StateMachine;
import org.eclipse.uml2.uml.Transition;

/**
 * Decorator provider for transitions represented as edges.
 */
public class TransitionDecoratorProvider extends AbstractProvider implements IDecoratorProvider {

	/**
	 * Constructor.
	 */
	public TransitionDecoratorProvider() {
		super();
	}

	@Override
	public boolean provides(IOperation operation) {
		IDecoratorTarget target = ((CreateDecoratorsOperation) operation).getDecoratorTarget();
		TransitionEditPart editPart = TypeUtils.as(target.getAdapter(EditPart.class), TransitionEditPart.class);
		Transition transition = TypeUtils.as(target.getAdapter(EObject.class), Transition.class);
		StateMachine stateMachine = (transition == null) ? null : transition.containingStateMachine();

		return (editPart != null)
				&& (stateMachine != null)
				&& StateMachineUtils.isRTStateMachine(stateMachine);
	}

	@Override
	public void createDecorators(IDecoratorTarget decoratorTarget) {
		final View node = decoratorTarget.getAdapter(View.class);
		if (node != null) {
			// Install the decorator
			decoratorTarget.installDecorator(TransitionDecorator.KEY, new TransitionDecorator(decoratorTarget));
		}
	}

	/**
	 * Decorator for transition, displaying a warning in case of absence of triggers while specific name is set.
	 */
	private static final class TransitionDecorator extends AbstractUMLRTDecorator<TransitionDecoratorTarget> {

		/** key for the decorator. */
		public static final String KEY = "transition_decoration";

		/** The source image size. */
		private static final int IMAGE_SIZE = 32;

		/** The rendered size of the decoration image. */
		private static final int DECORATION_SIZE = 16;

		/** Offset of the decoration. */
		private static final double DISTANCE_FROM_SOURCE = 10;

		/** space between each decoration. */
		private static final double SPACE = 20;

		/** max number of lines in the tooltips. */
		private static final int MAX_LINES = 20;

		/** Ellipsis added to the tooltip when truncated. */
		private static final String ELLIPSIS = "[...]";

		/**
		 * Constructor.
		 *
		 * @param decoratorTarget
		 *            the object to be decorated
		 */
		TransitionDecorator(IDecoratorTarget decoratorTarget) {
			super(TransitionDecoratorTarget.class, decoratorTarget);
		}

		@Override
		protected boolean matchesTarget(EObject semanticElement) {
			return (semanticElement instanceof Transition);
		}

		@Override
		protected void addDecorations(IGraphicalEditPart editPart, EObject semanticElement) {
			double position = DISTANCE_FROM_SOURCE;

			// Get the figure of the editpart
			final IFigure editPartFigure = editPart.getFigure();
			Transition transition = (Transition) semanticElement;

			// FIXME: According to bug 515492 the trigger warning decorator is temporary disabled until bug 511102
			// is fixed which should differentiate whether a transition actually should have a trigger or not.
			if (false && TransitionUtils.hasNameAndNoTriggers(transition)) {
				ScaledImageFigure imageFigure = new ScaledImageFigure() {
					@Override
					public IFigure getToolTip() {
						// Builds tooltip before getting it, so it will be refreshed each time before display.
						// This avoids usage of listeners for refreshing a tooltip
						Label tooltip = new Label("This transition has a specific name set, and no triggers.");
						tooltip.setIconAlignment(PositionConstants.LEFT);
						tooltip.setIcon(Activator.getImage(Activator.IMG_OBJ16_WARNING));
						setToolTip(tooltip);
						return super.getToolTip();
					}
				};
				imageFigure.setImage(Activator.getImage(Activator.IMG_OBJ_WARNING));

				// Get MapMode for size and scaling
				final IMapMode mm = MapModeUtil.getMapMode(editPartFigure);
				// Set the size of the decorator figure
				imageFigure.setSize(mm.DPtoLP(DECORATION_SIZE), mm.DPtoLP(DECORATION_SIZE));
				imageFigure.setScale((double) DECORATION_SIZE / (double) IMAGE_SIZE);

				// Set the decoration with the custom locator.
				addDecoration(getDecoratorTarget().addFixedConnectionDecoration(imageFigure, position, false));
				position += SPACE;
			}

			List<Constraint> guards = TransitionUtils.getGuards(transition);
			if (!guards.isEmpty()) {
				// Is any guard inherited or redefined?
				UMLRTInheritanceKind inheritance = guards.stream().map(UMLRTInheritanceKind::of)
						.max(Comparator.naturalOrder())
						.orElse(UMLRTInheritanceKind.NONE);

				final IMapMode mm = MapModeUtil.getMapMode(editPartFigure);
				ScaledImageFigure imageFigure = new ScaledImageFigure() {
					@Override
					public IFigure getToolTip() {
						// Builds tooltip before getting it, so it will be refreshed each time before display.
						// This avoids usage of listeners for refreshing a tooltip
						String tooltipText = LabelUtils.abbreviateLines(LabelUtils.getGuardTooltip(transition), MAX_LINES, ELLIPSIS);
						Label tooltip = new Label(tooltipText);
						tooltip.setIcon(Activator.getImage(Activator.getGuardSmallKey(inheritance)));
						tooltip.setSize(mm.DPtoLP(DECORATION_SIZE), mm.DPtoLP(DECORATION_SIZE));
						tooltip.setIconAlignment(PositionConstants.LEFT);
						setToolTip(tooltip);
						return super.getToolTip();
					}
				};
				imageFigure.setImage(Activator.getImage(Activator.getGuardKey(inheritance)));

				// Set the size of the decorator figure
				imageFigure.setSize(mm.DPtoLP(DECORATION_SIZE), mm.DPtoLP(DECORATION_SIZE));
				imageFigure.setScale((double) DECORATION_SIZE / (double) IMAGE_SIZE);

				// Set the decoration with the custom locator.
				addDecoration(getDecoratorTarget().addFixedConnectionDecoration(imageFigure, position, false));
				position += SPACE;
			}

			Behavior effect = transition.getEffect();
			if (effect != null) {
				// Is the effect inherited or redefined?
				UMLRTInheritanceKind inheritance = UMLRTInheritanceKind.of(effect);

				ScaledImageFigure imageFigure = new ScaledImageFigure() {
					@Override
					public IFigure getToolTip() {
						// Builds tooltip before getting it, so it will be refreshed each time before display.
						// This avoids usage of listeners for refreshing a tooltip
						String effectText = LabelUtils.abbreviateLines(LabelUtils.getEffectTooltip(effect), MAX_LINES, ELLIPSIS);
						Label tooltip = new Label(effectText);
						tooltip.setIcon(Activator.getImage(Activator.getEffectSmallKey(inheritance)));
						tooltip.setIconAlignment(PositionConstants.LEFT);
						setToolTip(tooltip);
						return super.getToolTip();
					}
				};
				imageFigure.setImage(Activator.getImage(Activator.getEffectKey(inheritance)));

				// Get MapMode
				final IMapMode mm = MapModeUtil.getMapMode(editPartFigure);
				// Set the size of the decorator figure
				imageFigure.setSize(mm.DPtoLP(DECORATION_SIZE), mm.DPtoLP(DECORATION_SIZE));
				imageFigure.setScale((double) DECORATION_SIZE / (double) IMAGE_SIZE);

				// Set the decoration with the custom locator.
				addDecoration(getDecoratorTarget().addFixedConnectionDecoration(imageFigure, position, false));
				position += SPACE;
			}
		}
	}
}
