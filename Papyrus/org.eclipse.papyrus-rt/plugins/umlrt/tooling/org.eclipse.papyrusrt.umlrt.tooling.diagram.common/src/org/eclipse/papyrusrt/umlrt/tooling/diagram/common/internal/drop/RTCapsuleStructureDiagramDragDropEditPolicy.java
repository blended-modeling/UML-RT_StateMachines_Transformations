/*****************************************************************************
 * Copyright (c) 2016, 2017 CEA LIST, Christian W. Damus, and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *   Christian W. Damus - bugs 472885, 483537, 494295, 497755, 510188
 *****************************************************************************/

package org.eclipse.papyrusrt.umlrt.tooling.diagram.common.internal.drop;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.stream.Stream;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.requests.DropObjectsRequest;
import org.eclipse.papyrus.infra.gmfdiag.dnd.policy.CustomizableDropEditPolicy;
import org.eclipse.papyrus.infra.gmfdiag.dnd.strategy.DropStrategy;
import org.eclipse.papyrus.infra.tools.util.PlatformHelper;
import org.eclipse.papyrus.uml.diagram.composite.custom.edit.policies.CustomDiagramDragDropEditPolicy;
import org.eclipse.papyrusrt.umlrt.core.utils.CapsulePartUtils;
import org.eclipse.papyrusrt.umlrt.core.utils.CapsuleUtils;
import org.eclipse.papyrusrt.umlrt.core.utils.ProtocolUtils;
import org.eclipse.papyrusrt.umlrt.core.utils.RTPortUtils;
import org.eclipse.papyrusrt.umlrt.system.profile.util.SystemElementsUtils;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.editparts.RTClassCompositeCompartmentEditPart;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.editparts.RTClassCompositeEditPart;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTPortKind;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Collaboration;
import org.eclipse.uml2.uml.Port;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;

/**
 * Edit policy to override {@link CustomDiagramDragDropEditPolicy} for Capsule structure compartment edit part.
 * Workaround for the Bug 472885: [tooling] Papyrus-RT shall support CapsulePart creation using drag n drop from model explorer
 */
public class RTCapsuleStructureDiagramDragDropEditPolicy extends CustomizableDropEditPolicy {

	/** key for the specific edit policy that creates a property while dragging a classifier onto the structure compartment of a classifier. */
	private static final String ORG_ECLIPSE_PAPYRUS_UML_DIAGRAM_DND_CLASSIFIER_TO_STRUCTURE_COMP_AS_PROPERTY_DROP = "org.eclipse.papyrus.uml.diagram.dnd.ClassifierToStructureCompAsPropertyDrop";

	/** Prefix matching strategy IDs that we prefer over other contributions. */
	private static final String PREFERRED_STRATEGY_PREFIX = "org.eclipse.papyrusrt."; //$NON-NLS-1$

	/**
	 * Constructor.
	 *
	 * @param defaultDropEditPolicy
	 * @param defaultCreationEditPolicy
	 */
	public RTCapsuleStructureDiagramDragDropEditPolicy(EditPolicy defaultDropEditPolicy, EditPolicy defaultCreationEditPolicy) {
		super(filter(defaultDropEditPolicy), defaultCreationEditPolicy);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Map<DropStrategy, List<Command>> findExtendedStrategies(Request request) {
		Map<DropStrategy, List<Command>> result = super.findExtendedStrategies(request);

		// Only filter out the strategies that we don't want if there are strategies
		// in play that we prefer. This ensures that we don't prevent the correct
		// drop behaviour in a delegation scenario, such as when a protocol-as-port
		// drop converts the protocol drop into the dropping of a port, for which we
		// don't have a preferred strategy
		if (hasPreferredDropStrategies(result.keySet())) {
			result = removeStrategies(result,
					ORG_ECLIPSE_PAPYRUS_UML_DIAGRAM_DND_CLASSIFIER_TO_STRUCTURE_COMP_AS_PROPERTY_DROP,
					"default"); //$NON-NLS-1$
		} else {
			// But, if we are dropping an invalid kind of classifier onto the structure
			// compartment, then we don't want that dropped anyways
			if (request instanceof DropObjectsRequest) {
				DropObjectsRequest drop = (DropObjectsRequest) request;
				Stream<EObject> dropped = ((List<?>) drop.getObjects()).stream()
						.filter(EObject.class::isInstance).map(EObject.class::cast);

				if (dropped.anyMatch(o -> isInvalidTypeOnCapsule(o, getHost()))) {
					result = removeStrategies(result,
							ORG_ECLIPSE_PAPYRUS_UML_DIAGRAM_DND_CLASSIFIER_TO_STRUCTURE_COMP_AS_PROPERTY_DROP);
				}
			}
		}

		return result;
	}

	/**
	 * Removes strategies from a map according to the given IDs.
	 * 
	 * @param strategies
	 *            a map of strategies to pare down
	 * @param identifiers
	 *            the IDs of strategies to remove from the map
	 * 
	 * @return the reduced map
	 */
	private <T> Map<DropStrategy, T> removeStrategies(Map<DropStrategy, T> strategies, String... identifiers) {
		Set<String> forbidden = new HashSet<>(Arrays.asList(identifiers));

		Map<DropStrategy, T> result = new HashMap<>(strategies);
		result.keySet().removeIf(k -> forbidden.contains(k.getID()));

		return result;
	}

	/**
	 * Determines whether a group of drop strategies includes any that are preferred
	 * over others. These are typically those that are contributed by the UML-RT
	 * bundles.
	 * 
	 * @param strategies
	 *            a group of contributed drop strategies
	 * @return whether any of them are preferred over others
	 */
	protected boolean hasPreferredDropStrategies(Collection<? extends DropStrategy> strategies) {
		return strategies.stream()
				.map(DropStrategy::getID)
				.anyMatch(id -> id.startsWith(PREFERRED_STRATEGY_PREFIX));
	}

	private static FilteredDropEditPolicy filter(EditPolicy delegate) {
		BiPredicate<EObject, EditPart> baseFilter = FilteredDropEditPolicy::hasView;
		BiPredicate<EObject, EditPart> invalidPortOnPart = RTCapsuleStructureDiagramDragDropEditPolicy::isInvalidPortOnCapsulePart;
		BiPredicate<EObject, EditPart> invalidTypeOnCapsule = RTCapsuleStructureDiagramDragDropEditPolicy::isInvalidTypeOnCapsule;
		BiPredicate<EObject, EditPart> invalidPortOnCapsule = RTCapsuleStructureDiagramDragDropEditPolicy::isInvalidPortOnCapsule;

		return new FilteredDropEditPolicy(delegate,
				baseFilter.or(invalidPortOnPart).or(invalidTypeOnCapsule).or(invalidPortOnCapsule));
	}

	// External ports of a capsule-part's capsule type may be dropped onto it to
	// visualize the port-on-part, but none else
	private static boolean isInvalidPortOnCapsulePart(EObject element, EditPart editPart) {
		boolean result = false;

		if (element instanceof Port) {
			Port port = (Port) element;
			UMLRTPortKind kind = RTPortUtils.getPortKind(port);

			if ((port.getClass_() != null) && (kind != null)) {
				// It's an external port of a capsule
				EObject dropOn = PlatformHelper.getAdapter(editPart, EObject.class);
				if (dropOn instanceof Property) {
					Property property = (Property) dropOn;
					if (CapsulePartUtils.isCapsulePart(property) && (property.getType() instanceof org.eclipse.uml2.uml.Class)) {
						org.eclipse.uml2.uml.Class capsule = (org.eclipse.uml2.uml.Class) property.getType();

						// Okay if the port is an external port of the capsule that
						// types the part
						result = !kind.isExternal() || !capsule.conformsTo(port.getClass_());
					}
				}
			}
		}

		return result;
	}

	private static boolean isCapsule(EObject object) {
		return (object instanceof Classifier) && CapsuleUtils.isCapsule((Classifier) object);
	}

	// Non-capsules should not be dropped on a capsule structure diagram to create parts
	// because we only visualize capsule-parts. Do allow dropping protocols, though,
	// because those create external or internal ports according to where they are dropped
	private static boolean isInvalidTypeOnCapsule(EObject element, EditPart editPart) {
		boolean result = false;

		// Are we dropping a type on a capsule?
		if ((element instanceof Type) && (editPart instanceof IGraphicalEditPart)
				&& isCapsule(((IGraphicalEditPart) editPart).resolveSemanticElement())) {

			// Then we can only drop capsules and protocols
			result = !isCapsule(element) && !ProtocolUtils.isProtocol(element);
		}

		return result;
	}



	// Can not drop System and Base Protocols to a border of a capsule: without this filter, the default drop strategy that drop a collaboration use is executed
	// see https://bugs.eclipse.org/bugs/show_bug.cgi?id=477723 for more details
	private static boolean isInvalidPortOnCapsule(EObject element, EditPart editPart) {
		boolean result = false;

		if (element instanceof Collaboration) {

			Collaboration collaboration = (Collaboration) element;
			// do not allow to drop a base or a system protocol on the capsule border
			if (editPart instanceof RTClassCompositeEditPart) {
				if (SystemElementsUtils.isSystemProtocol(collaboration)) {
					result = true;
				} else if (SystemElementsUtils.isBaseProtocol(collaboration)) {
					result = true;
				}
				// do not allow to drop a base protocol in side a capsule
			} else if (editPart instanceof RTClassCompositeCompartmentEditPart) {
				if (SystemElementsUtils.isBaseProtocol(collaboration)) {
					result = true;
				}
			}

		}

		return result;
	}
}
