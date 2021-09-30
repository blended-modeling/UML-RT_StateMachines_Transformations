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

package org.eclipse.papyrusrt.umlrt.tooling.diagram.common.utils;

import static org.eclipse.papyrusrt.umlrt.tooling.diagram.common.utils.DrawFigureUtils.getLighterColor;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.gef.ui.figures.NodeFigure;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.papyrus.infra.gmfdiag.common.helper.DiagramHelper;
import org.eclipse.papyrus.infra.gmfdiag.css.notation.CSSAnnotations;
import org.eclipse.papyrus.infra.tools.util.TypeUtils;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.editparts.IInheritableEditPart;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.internal.editpolicies.PapyrusRTCanonicalEditPolicy;
import org.eclipse.swt.graphics.Color;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

/**
 * Convenience utility for working with edit-parts in the UML-RT diagrams.
 */
public class UMLRTEditPartUtils {
	private static final AtomicReference<Runnable> pendingDirectEditRequest = new AtomicReference<>();

	/**
	 * Not instantiable by clients.
	 */
	private UMLRTEditPartUtils() {
		super();
	}

	/**
	 * Schedule invocation of direct edit in the given {@code viewer} on an
	 * edit-part that will exist by the time the diagram refresh and whatever
	 * else currently pending on the display thread has come to pass.
	 * 
	 * @param viewer
	 *            the contextual diagram viewer
	 * @param onEditPart
	 *            a supplier that will be able to provide the edit-part when needed,
	 *            but is permitted to return {@code null} if the edit-part does not exist
	 *            (in which case there simply will not be any in-line editing initiated)
	 */
	public static void scheduleDirectEdit(EditPartViewer viewer, Supplier<? extends EditPart> onEditPart) {
		DiagramHelper.asyncExec(viewer.getRootEditPart(), () -> {

			EditPart toEdit = onEditPart.get();
			if ((toEdit != null) && toEdit.isActive()) {
				Runnable inlineEdit = new Runnable() {
					// Do this asynchronously later on the same display thread to ensure that
					// the direct-edit cell editor isn't cropped by the figure's bounds
					// (this would happen, e.g., on drop of a capsule to create a part)

					@Override
					public void run() {
						// Ensure that I am no longer pending (if I ever was)
						pendingDirectEditRequest.compareAndSet(this, null);

						// check that this request is still valid
						if (toEdit.isActive()) {
							// And start the in-line editor
							toEdit.performRequest(new Request(RequestConstants.REQ_DIRECT_EDIT));
							viewer.reveal(toEdit);
						}
					}
				};

				// Don't stack direct-edit requests in case of a multiple drop
				// because otherwise we get a silly animation of direct editors
				// from element to element until the last one remains in edit mode
				if (pendingDirectEditRequest.compareAndSet(null, inlineEdit)) {
					// If this viewer is not in the active workbench part (we may
					// be dropping from another view such as Model Explorer) then
					// activate it now
					ensureWorkbenchPartActive(viewer);

					// Select the new port edit-part
					viewer.setSelection(new StructuredSelection(toEdit));

					viewer.getControl().getDisplay().asyncExec(inlineEdit);
				}
			}
		});
	}

	/**
	 * Schedule invocation of direct edit on an edit-part
	 * contained within some {@code ancestor} that will exist by
	 * the time the diagram refresh and whatever else currently
	 * pending on the display thread has come to pass.
	 * 
	 * @param ancestor
	 *            the contextual edit-part in which tree an {@code element} is to be presented
	 * @param element
	 *            a model element that is expected to be presented within the tree
	 *            of the {@code ancestor} edit-part
	 */
	public static void scheduleDirectEdit(EditPart ancestor, EObject element) {
		// Don't stack direct-edit requests in case of a multiple drop
		// because otherwise we get a silly animation of direct editors
		// from element to element until the last one remains in edit mode
		if (pendingDirectEditRequest.get() == null) {
			IGraphicalEditPart graphicalAncestor = TypeUtils.as(ancestor, IGraphicalEditPart.class);
			if (graphicalAncestor != null) {
				EditPartViewer viewer = graphicalAncestor.getViewer();
				if ((viewer != null)
						&& (viewer.getControl() != null)
						&& !viewer.getControl().isDisposed()) {

					// Note that, because of asynchronous execution, the contextual
					// edit-part may no longer be active for some reason
					scheduleDirectEdit(viewer, () -> graphicalAncestor.isActive()
							? graphicalAncestor.findEditPart(graphicalAncestor, element)
							: null);
				}
			}
		}
	}

	private static void ensureWorkbenchPartActive(EditPartViewer viewer) {
		Optional.ofNullable(PlatformUI.getWorkbench())
				.map(IWorkbench::getActiveWorkbenchWindow)
				.map(IWorkbenchWindow::getActivePage)
				.ifPresent(page -> {
					IEditorPart editor = page.getActiveEditor();
					if ((editor != null) && (editor.getAdapter(GraphicalViewer.class) == viewer)) {
						// This is our editor. Make sure it's active
						page.activate(editor);
					}
				});
	}

	/**
	 * Finds the direct child of an edit-part that visualizes the given {@code element}.
	 * 
	 * @param parentEditPart
	 *            an edit-part
	 * @param element
	 *            a model element
	 * 
	 * @return the first child of the parent that visualizes the {@code element}
	 */
	public static Optional<IGraphicalEditPart> getChild(IGraphicalEditPart parentEditPart, EObject element) {
		@SuppressWarnings("unchecked")
		List<EditPart> children = parentEditPart.getChildren();

		return children.stream()
				.filter(IGraphicalEditPart.class::isInstance)
				.map(IGraphicalEditPart.class::cast)
				.filter(child -> child.resolveSemanticElement() == element)
				.findFirst();
	}

	/**
	 * Finds a child at the specified {@code location} in the coordinate space of
	 * an edit-part.
	 * 
	 * @param parentEditPart
	 *            an edit-part to search
	 * @param location
	 *            a location in the parent's coordinates
	 * 
	 * @return the child edit-part at that {@code location}
	 */
	public static Optional<IGraphicalEditPart> findChildAt(IGraphicalEditPart parentEditPart, Point location) {
		location = location.getTranslated(parentEditPart.getFigure().getBounds().getLocation());
		parentEditPart.getFigure().translateToAbsolute(location);
		EditPart result = parentEditPart.getViewer().findObjectAtExcluding(location, Collections.emptySet(),
				ep -> ep.getParent() == parentEditPart);
		return Optional.ofNullable(result)
				.filter(IGraphicalEditPart.class::isInstance)
				.map(IGraphicalEditPart.class::cast);
	}

	public static void updateBackgroundColor(IInheritableEditPart editPart, NodeFigure figure) {
		if (editPart.isSemanticInherited()) {
			// We present inherited elements in a lighter colour
			if (figure.isUsingGradient()) {
				figure.setGradientData(getLighterColor(figure.getGradientColor1(), false),
						getLighterColor(figure.getGradientColor2(), false),
						figure.getGradientStyle());
			} else {
				Color bg = figure.getBackgroundColor();
				if (bg != null) {
					bg = getLighterColor(bg, false);
					figure.setBackgroundColor(bg);
					figure.setIsUsingGradient(false);
					figure.setGradientData(-1, -1, 0);
				}
			}
		}
	}

	public static void updateForegroundColor(IInheritableEditPart editPart, Figure figure) {
		Color fg = figure.getForegroundColor();
		if ((fg != null) && editPart.isSemanticInherited()) {
			// We present inherited elements in a lighter colour
			fg = getLighterColor(fg, true);
			figure.setForegroundColor(fg);
		}
	}


	public static View findView(View root, String type, EObject semantic) {
		View result = null;

		if (Objects.equals(root.getType(), type) && (root.getElement() == semantic)) {
			result = root;
		} else {
			for (TreeIterator<EObject> iter = root.eAllContents(); (result == null) && iter.hasNext();) {
				EObject next = iter.next();
				if (!(next instanceof View)) {
					iter.prune();
				} else {
					View view = (View) next;
					if (Objects.equals(view.getType(), type) && (view.getElement() == semantic)) {
						result = view;
						break;
					}
				}
			}
		}

		return result;
	}

	/**
	 * Queries whether the user has explicitly set visibility of a view.
	 * 
	 * @param view
	 *            the view to check
	 * 
	 * @return <code>true</code> if the user has set explicitly the visibility, <code>false</code> otherwise
	 */
	public static boolean isVisibilityForced(View view) {
		EAnnotation eAnnotation = view.getEAnnotation(CSSAnnotations.CSS_FORCE_VALUE);
		if (eAnnotation == null) {
			return false;
		}
		return eAnnotation.getDetails().containsKey(NotationPackage.Literals.VIEW__VISIBLE.getName());
	}

	/**
	 * Queries whether a view creation {@code request} originates from the <em>Canonical Edit Policy</em>.
	 * A client might use this information, for example, to take the request literally and not as a
	 * user gesture that perhaps should be intercepted and modified.
	 * 
	 * @param request
	 *            a request
	 * @return whether it is from the <em>Canonical Edit Policy</em>
	 * 
	 * @see PapyrusRTCanonicalEditPolicy
	 */
	public static boolean isCanonicalRequest(CreateRequest request) {
		return request.getExtendedData().containsKey(EditPolicyRoles.CANONICAL_ROLE);
	}
}
