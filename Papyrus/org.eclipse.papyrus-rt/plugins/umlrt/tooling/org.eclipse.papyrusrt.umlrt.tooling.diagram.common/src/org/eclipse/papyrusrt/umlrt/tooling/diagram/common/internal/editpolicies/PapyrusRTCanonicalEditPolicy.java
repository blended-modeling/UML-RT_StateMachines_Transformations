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

package org.eclipse.papyrusrt.umlrt.tooling.diagram.common.internal.editpolicies;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ShapeCompartmentEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest.ViewDescriptor;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.gmfdiag.canonical.editpolicy.PapyrusCanonicalEditPolicy;
import org.eclipse.papyrus.uml.diagram.common.util.CommandUtil;
import org.eclipse.papyrusrt.umlrt.uml.util.UMLRTExtensionUtil;
import org.eclipse.uml2.uml.Element;

/**
 * A specialized <em>Canonical Edit-Policy</em> that accounts for inheritance semantics
 * of UML-RT.
 */
public class PapyrusRTCanonicalEditPolicy extends PapyrusCanonicalEditPolicy {

	/**
	 * Commands initiated by canonical edit-policy that are currently executing
	 * (multiplicity accounts for possible recursive command execution).
	 */
	private static final Set<Command> pendingExecution = Collections.newSetFromMap(new ConcurrentHashMap<>());

	/**
	 * Instances currently computing semantic refresh..
	 */
	private static final Set<PapyrusRTCanonicalEditPolicy> pendingSemanticRefresh = Collections.newSetFromMap(new ConcurrentHashMap<>());

	public PapyrusRTCanonicalEditPolicy() {
		super();
	}

	/**
	 * Resolves the edit-part to the local inherited or overriding definition
	 * of an the notation view's element in the case that such element is
	 * the root definition, not the definition in context.
	 */
	@Override
	public EObject getSemanticHost() {
		IGraphicalEditPart host = host();

		if (host instanceof ShapeCompartmentEditPart) {
			// Resolve from the parent because it should be inheritable
			if (host.getParent() instanceof IGraphicalEditPart) {
				host = (IGraphicalEditPart) host.getParent();
			}
		}

		return host.resolveSemanticElement();
	}

	@Override
	protected CreateRequest createCreateRequest(ViewDescriptor viewDescriptor) {
		CreateRequest result = super.createCreateRequest(viewDescriptor);

		// Mark this create request as originating from Canonical Edit Policy
		@SuppressWarnings("unchecked")
		Map<Object, Object> extendedData = result.getExtendedData();
		extendedData.put(EditPolicyRoles.CANONICAL_ROLE, true);

		return result;
	}

	/**
	 * Extends the inherited method by first finding child views that are
	 * referencing a redefinition, that should be referencing the root
	 * definition instead and updating them.
	 */
	@Override
	protected List<IAdaptable> refreshSemanticChildren(ChildrenKind kind) {
		if ((resolveSemanticElement() != null) && isInState(State.ACTIVE)) {
			// Only attempt to correct view semantic-element references if
			// we are actually active
			List<View> viewChildren = getViewChildren(kind);
			List<EObject> semanticChildren = getSemanticChildrenList(kind);

			// There cannot be anything to adjust if there is no possibility
			// of an intersection between the visual and semantic children
			if (!viewChildren.isEmpty() && !semanticChildren.isEmpty()) {
				adjustSemanticElements(viewChildren, semanticChildren);
			}
		}

		return super.refreshSemanticChildren(kind);
	}

	/**
	 * For any views that do not reference the root definition of their semantic element
	 * but should do so, update them quietly to do just that.
	 * 
	 * @param visualChildren
	 *            the visual children of my host
	 * @param semanticChildren
	 *            the canonical semantic children of my host
	 */
	protected void adjustSemanticElements(List<? extends View> visualChildren, List<? extends EObject> semanticChildren) {
		// First, map the view that have inherited elements by the root definition
		Map<Element, View> problems = new HashMap<>();
		for (View next : visualChildren) {
			if (next.isSetElement()) {
				EObject semantic = next.getElement();
				if (semantic instanceof Element) {
					Element element = (Element) semantic;
					if (UMLRTExtensionUtil.isInherited(element) && !isDiagramElement(element, next)) {
						problems.put(UMLRTExtensionUtil.getRootDefinition(element), next);
					}
				}
			}
		}

		// Do we have any problems that pertain to our semantic children? If so, fix them
		if (!problems.isEmpty()) {
			Map<View, Element> fixup = new HashMap<>();

			for (EObject semantic : semanticChildren) {
				if (semantic instanceof Element) {
					Element element = (Element) semantic;
					View toFix = problems.remove(element);
					if (toFix != null) {
						fixup.put(toFix, element);
					}
				}
			}

			if (!fixup.isEmpty()) {
				CommandUtil.executeUnsafeCommand(() -> {
					fixup.entrySet().forEach(e -> e.getKey().setElement(e.getValue()));
				}, host());
			}
		}
	}

	/**
	 * Is an element the one that the diagram visualizes? If so, it needs to be
	 * allowed to be a redefinition because the root definition may already
	 * have a diagram associated with it and only the one diagram is allowed.
	 * 
	 * @param semantic
	 *            an element in the model
	 * @param view
	 *            a view of the element
	 * @return whether the {@code view} is in a diagram that is a diagram for the {@code element}
	 */
	private boolean isDiagramElement(EObject semantic, View view) {
		Diagram diagram = view.getDiagram();
		return (diagram != null) && (diagram.getElement() == semantic);
	}

	@Override
	protected void refreshSemantic() {
		boolean original = pendingSemanticRefresh.add(this);

		try {
			super.refreshSemantic();
		} finally {
			if (original) {
				pendingSemanticRefresh.remove(this);
			}
		}
	}

	@Override
	protected void executeCommand(Command cmd) {
		boolean original = pendingExecution.add(cmd);

		try {
			super.executeCommand(cmd);
		} finally {
			if (original) {
				pendingExecution.remove(cmd);
			}
		}
	}

	/**
	 * Queries whether a {@code request} is received in the context of computing a
	 * canonical refresh or execution of a command implementing (possibly
	 * asynchronously) a canonical refresh.
	 * 
	 * @param request
	 *            a GEF edit request
	 * @return whether it originates, at a best guess, from the <em>Canonical Edit Policy</em>
	 *         to request canonical synchronization of the diagram
	 */
	public static boolean isCanonicalEditPolicyRequest(Request request) {
		return !pendingSemanticRefresh.isEmpty() || !pendingExecution.isEmpty();
	}
}
