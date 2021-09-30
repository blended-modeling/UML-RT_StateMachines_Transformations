/*******************************************************************************
 * Copyright (c) 2016, 2017 EclipseSource Muenchen GmbH, Christian W. Damus, and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Alexandra Buzila - initial API and implementation
 *     Philip Langer - generalizing this group extender for protocol messages
 *     Christian W. Damus - bug 510189
 *******************************************************************************/

package org.eclipse.papyrusrt.umlrt.tooling.compare.ui.internal.differenceGroup;

import static com.google.common.collect.Lists.newArrayList;
import static org.eclipse.papyrusrt.umlrt.tooling.compare.internal.UMLRTCompareUtil.getAnyMatchValue;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.compare.Comparison;
import org.eclipse.emf.compare.Match;
import org.eclipse.emf.compare.rcp.ui.internal.structuremergeviewer.nodes.ConflictNode;
import org.eclipse.emf.compare.rcp.ui.structuremergeviewer.groups.extender.IDifferenceGroupExtender;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.tree.TreeNode;
import org.eclipse.papyrusrt.umlrt.core.utils.PackageUtils;
import org.eclipse.papyrusrt.umlrt.core.utils.ProtocolContainerUtils;
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.Protocol;
import org.eclipse.uml2.uml.Collaboration;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Package;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;


/**
 * {@link IDifferenceGroupExtender} for handling tree nodes representing protocol messages.
 * <p>
 * This group extender moves {@link Operation operations} that represent protocol messages
 * into the tree node that represents the protocol.
 * </p>
 * 
 * @author Alexandra Buzila
 * @author Philip Langer
 */
@SuppressWarnings("restriction")
public class ProtocolMessageGroupExtender implements IDifferenceGroupExtender {

	@Override
	public boolean handle(TreeNode treeNode) {
		// we handle protocol matches (matches of UML collaborations)
		// and we handle operation matches
		if (isNonConflictingCollaborationOrOperationMatch(treeNode)) {
			final Match match = (Match) treeNode.getData();
			return getProtocolFromMatch(match).isPresent();
		}
		return false;
	}

	/**
	 * Specifies whether the given <code>treeNode</code> represents a collaboration or operation of a protocol.
	 * 
	 * @param treeNode
	 *            {@link TreeNode} to check.
	 * @return <code>true</code> if <code>treeNode</code> represents a collaboration or operation of a protocol,
	 *         <code>false</code> otherwise.
	 */
	private boolean isNonConflictingCollaborationOrOperationMatch(TreeNode treeNode) {
		final EObject data = treeNode.getData();
		return (isCollaborationMatch(data) || isOperationMatch(data)) && !isChildOfConflictNode(treeNode);
	}

	/**
	 * Specifies whether the given <code>eObject</code> is an instance of a {@link Collaboration}.
	 * 
	 * @param eObject
	 *            The {@link EObject} to check.
	 * @return <code>true</code> if the given <code>eObject</code> is a {@link Match} of a {@link Collaboration},
	 *         <code>false</code> otherwise.
	 */
	private boolean isCollaborationMatch(EObject eObject) {
		return eObject instanceof Match && getAnyMatchValue((Match) eObject) instanceof Collaboration;
	}

	/**
	 * Specifies whether the given <code>eObject</code> is an instance of a {@link Operation}.
	 * 
	 * @param eObject
	 *            The {@link EObject} to check.
	 * @return <code>true</code> if the given <code>eObject</code> is a {@link Match} of a {@link Operation},
	 *         <code>false</code> otherwise.
	 */
	private boolean isOperationMatch(EObject eObject) {
		return eObject instanceof Match && getAnyMatchValue((Match) eObject) instanceof Operation;
	}

	/**
	 * Specifies whether the given <code>treeNode</code> is a child of a conflict node.
	 * 
	 * @param treeNode
	 *            The {@link TreeNode} to check.
	 * @return <code>true</code> if <code>treeNode</code> is a child of a conflict node,
	 *         <code>false</code> otherwise.
	 */
	private boolean isChildOfConflictNode(TreeNode treeNode) {
		TreeNode parent = treeNode;
		while ((parent = parent.getParent()) != null) {
			if (parent instanceof ConflictNode) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Returns the protocol for the given <code>match</code>.
	 * 
	 * @param match
	 *            The match from which we want to obtain the protocol.
	 * @return The optional protocol or {@link Optional#absent()}.
	 */
	private Optional<Collaboration> getProtocolFromMatch(Match match) {
		return getProtocol(getAnyMatchValue(match));
	}

	/**
	 * Returns the {@link Collaboration} representing the {@link Protocol} for the given <code>eObject</code>.
	 * 
	 * @param eObject
	 *            The {@link EObject} to get the protocol for.
	 * @return An optional protocol.
	 */
	private Optional<Collaboration> getProtocol(EObject eObject) {
		final Package protocolContainer = PackageUtils.getNearestPackage(eObject);
		final Collaboration protocol = ProtocolContainerUtils.getProtocol(protocolContainer);
		return Optional.fromNullable(protocol);
	}

	@Override
	public void addChildren(TreeNode treeNode) {
		if (treeNode.getParent() == null) {
			return;
		}

		final EObject data = treeNode.getData();
		final Match match = (Match) data;
		final Comparison comparison = match.getComparison();
		final Optional<Collaboration> protocol = getProtocolFromMatch(match);

		if (isOperationMatch(match)) {
			final Optional<TreeNode> existingProtocolTreeNode = getProtocolTreeNode(protocol, treeNode);
			if (!existingProtocolTreeNode.isPresent() && protocol.isPresent()) {
				// no existing tree node for protocol match
				// set match of the operation's parent to the protocol
				final TreeNode parentOfOperationTreeNode = treeNode.getParent();
				final Match protocolMatch = comparison.getMatch(protocol.get());
				parentOfOperationTreeNode.setData(protocolMatch);
			}
			// if there is an existing protocol match, we'll handle the collaboration match
		} else if (isCollaborationMatch(match) && protocol.isPresent()) {
			// find operation matches in siblings that represent protocol messages
			// and move them as child of the collaboration match
			final TreeNode parentTreeNode = treeNode.getParent();
			final List<TreeNode> operationTreeNodes;
			operationTreeNodes = getProtocolMessageTreeNodes(protocol.get(), newArrayList(parentTreeNode));
			for (TreeNode operationTreeNode : operationTreeNodes) {
				treeNode.getChildren().add(operationTreeNode);
			}
		}
	}

	/**
	 * Returns the {@link TreeNode} representing the given <code>protocol</code> within the tree
	 * in which the given <code>treeNode</code> is contained.
	 * 
	 * @param protocol
	 *            The protocol to be represented by the {@link TreeNode} to be found.
	 * @param treeNode
	 *            The {@link TreeNode} that is contained by the tree to search in.
	 * @return The {@link TreeNode} representing the given <code>protocol</code>.
	 */
	private Optional<TreeNode> getProtocolTreeNode(Optional<Collaboration> protocol, TreeNode treeNode) {
		if (!protocol.isPresent()) {
			return Optional.absent();
		}

		final Set<TreeNode> checkedTreeNodes = new HashSet<>();
		TreeNode parent = treeNode;
		while ((parent = parent.getParent()) != null) {
			final Optional<TreeNode> protocolTreeNode = getProtocolTreeNodeFromChildren(protocol, parent, checkedTreeNodes);
			if (protocolTreeNode.isPresent()) {
				return protocolTreeNode;
			}
			checkedTreeNodes.add(parent);
		}
		return Optional.absent();
	}

	/**
	 * Returns the {@link TreeNode} representing the given <code>protocol</code> within the tree
	 * in which the given <code>treeNode</code> is contained.
	 * 
	 * @param protocol
	 *            The protocol to be represented by the {@link TreeNode} to be found.
	 * @param parent
	 *            The {@link TreeNode} that is contained by the tree to search in.
	 * @param checkedTreeNodes
	 *            The set of nodes that already have been checked.
	 * @return The {@link TreeNode} representing the given <code>protocol</code>.
	 */
	private Optional<TreeNode> getProtocolTreeNodeFromChildren(Optional<Collaboration> protocol, TreeNode parent, Set<TreeNode> checkedTreeNodes) {
		if (protocol.isPresent() && !checkedTreeNodes.contains(parent)) {
			for (final TreeNode childTreeNode : parent.getChildren()) {
				// check if childTreeNode is tree node for protocol match
				if (isCollaborationMatch(childTreeNode)) {
					final Match currentMatch = (Match) childTreeNode.getData();
					final Optional<Collaboration> currentProtocol = getProtocolFromMatch(currentMatch);
					if (currentProtocol.isPresent() && protocol.get().equals(currentProtocol.get())) {
						return Optional.of(childTreeNode);
					}
				}
				// check children recursively
				final Optional<TreeNode> treeNodeFromChildren;
				treeNodeFromChildren = getProtocolTreeNodeFromChildren(protocol, childTreeNode, checkedTreeNodes);
				if (treeNodeFromChildren.isPresent()) {
					return treeNodeFromChildren;
				}
				checkedTreeNodes.add(childTreeNode);
			}
		}
		return Optional.absent();
	}

	/**
	 * Specifies whether the given <code>treeNode</code> represents a {@link Match} of a {@link Collaboration}.
	 * 
	 * @param treeNode
	 *            The {@link TreeNode} to check.
	 * @return <code>true</code> if <code>treeNode</code> represents a {@link Match} of a {@link Collaboration},
	 *         <code>false</code> otherwise.
	 */
	private boolean isCollaborationMatch(TreeNode treeNode) {
		return treeNode.getData() instanceof Match &&
				getAnyMatchValue((Match) treeNode.getData()) instanceof Collaboration;
	}

	/**
	 * Returns a list of {@link TreeNode tree nodes} that represent protocol messages of the given <code>protocol</code>.
	 * <p>
	 * This method searches for tree nodes with a {@link TreeNode#getData() data} that is an {@link Operation}
	 * representing a protocol messages for the given <code>protocol</code> inside the given <code>treeNode</code>.
	 * </p>
	 * 
	 * @param protocol
	 *            {@link Collaboration} of the protocol for which the protocol messages shall be searched.
	 * @param treeNodes
	 *            List of {@link TreeNode tree nodes} to search through including their children.
	 * @return The list of found {@link TreeNode tree nodes} that represent protocol messages for the
	 *         given <code>protocol</code>.
	 */
	private List<TreeNode> getProtocolMessageTreeNodes(Collaboration protocol, List<TreeNode> treeNodes) {
		final Builder<TreeNode> list = ImmutableList.builder();
		for (TreeNode currentTreeNode : treeNodes) {
			final EObject data = currentTreeNode.getData();
			if (isOperationMatch(data)) {
				final Match operationMatch = (Match) data;
				final Optional<Collaboration> currentProtocol = getProtocolFromMatch(operationMatch);
				if (currentProtocol.isPresent() && protocol.equals(currentProtocol.get())) {
					list.add(currentTreeNode);
				}
			}
			list.addAll(getProtocolMessageTreeNodes(protocol, currentTreeNode.getChildren()));
		}
		return list.build();
	}

}
