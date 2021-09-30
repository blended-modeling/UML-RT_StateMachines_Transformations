/******************************************************************************
 * Copyright (c) 2010, 2017 IBM Corporation, Christian W. Damus, and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    IBM Corporation - initial API and implementation 
 *    Christian W. Damus - bugs 500743, 507552
 *    
 ****************************************************************************/
package org.eclipse.papyrusrt.umlrt.tooling.diagram.common.internal.commands;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.common.core.util.StringStatics;
import org.eclipse.gmf.runtime.diagram.ui.commands.ArrangeCommand;
import org.eclipse.gmf.runtime.diagram.ui.commands.CommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.internal.services.layout.IInternalLayoutRunnable;
import org.eclipse.gmf.runtime.diagram.ui.internal.services.layout.LayoutNode;
import org.eclipse.gmf.runtime.diagram.ui.services.layout.ILayoutNodeProvider;
import org.eclipse.gmf.runtime.diagram.ui.services.layout.LayoutService;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.gmf.runtime.emf.commands.core.command.CompositeTransactionalCommand;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.editparts.IInheritableEditPart;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.internal.layout.PrivateLayoutService;

/**
 * An analogue of the GMF {@link ArrangeCommand} that does not delegate to
 * the {@link LayoutService} but instead a specific list of layout providers
 * encapsulated in a private layout service.
 * 
 * @see PrivateLayoutService
 */
@SuppressWarnings("restriction")
public class RestrictedArrangeCommand extends AbstractTransactionalCommand {

	private final ILayoutNodeProvider layoutService;

	/**
	 * Nodes to lay out.
	 */
	private List<LayoutNode> nodes;

	/**
	 * Whether only a selected subset of the graph is to be laid out.
	 */
	private boolean selectionArrange;

	/**
	 * The layout hint parameter
	 */
	private IAdaptable layoutHint;

	/**
	 * Initializes me with my private layout service.
	 * 
	 * @param domain
	 *            the contextual editing domain
	 * @param label
	 *            command label
	 * @param layoutService
	 *            my layout service
	 * @param affectedFiles
	 *            list of affected files
	 * @param editparts
	 *            edit-parts to be arranged
	 * @param layoutHint
	 *            the layout hint
	 * @param selectionArrange
	 *            whether only a selected subset of the graph is to be laid out
	 */
	public RestrictedArrangeCommand(TransactionalEditingDomain domain, String label,
			ILayoutNodeProvider layoutService,
			List<? extends IFile> affectedFiles, Collection<IGraphicalEditPart> editparts,
			IAdaptable layoutHint, boolean selectionArrange) {

		super(domain, label, affectedFiles);

		this.layoutService = layoutService;

		this.layoutHint = layoutHint;
		this.selectionArrange = selectionArrange;

		initLayoutNodes(editparts);
	}

	@Override
	protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		ICommand cmd = getCommandForExecution();
		if (cmd.canExecute()) {
			cmd.execute(monitor, info);
		}
		return CommandResult.newOKCommandResult();
	}

	/**
	 * Creates the list of nodes to layout from the editparts required to be arranged
	 * 
	 * @param editparts
	 *            the editparts required to be arranged
	 */
	private void initLayoutNodes(Collection<IGraphicalEditPart> editparts) {
		nodes = new ArrayList<>(editparts.size());
		Iterator<IGraphicalEditPart> li = editparts.iterator();
		while (li.hasNext()) {
			IGraphicalEditPart ep = li.next();
			View view = ep.getNotationView();
			if (ep.isActive() && (view != null) && (view instanceof Node) && (ep != layoutHint.getAdapter(EditPart.class))) {
				if (!(ep instanceof IInheritableEditPart) || !((IInheritableEditPart) ep).isInherited()) {
					Rectangle bounds = ep.getFigure().getBounds();
					nodes.add(new LayoutNode((Node) view, bounds.width, bounds.height));
				}
			}
		}
	}

	/**
	 * Creates the actual layout command to execute.
	 * 
	 * @return the actual graph layout command
	 */
	private ICommand getCommandForExecution() {
		final Runnable layoutRun = layoutService.layoutLayoutNodes(nodes, selectionArrange, layoutHint);

		TransactionalEditingDomain editingDomain = getEditingDomain();
		CompositeTransactionalCommand ctc = new CompositeTransactionalCommand(editingDomain, StringStatics.BLANK);
		if (layoutRun instanceof IInternalLayoutRunnable) {
			Command cmd = ((IInternalLayoutRunnable) layoutRun).getCommand();
			if (cmd != null) {
				ctc.add(new CommandProxy(cmd));
			}
		} else {
			ctc.add(new AbstractTransactionalCommand(editingDomain, StringStatics.BLANK, null) {
				@Override
				protected CommandResult doExecuteWithResult(
						IProgressMonitor progressMonitor, IAdaptable info)
						throws ExecutionException {
					layoutRun.run();
					return CommandResult.newOKCommandResult();
				}
			});
		}
		return ctc;
	}

	@Override
	public boolean canExecute() {
		return layoutService.canLayoutNodes(nodes, selectionArrange, layoutHint);
	}

}
