/*****************************************************************************
 * Copyright (c) 2017 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrusrt.umlrt.tooling.diagram.common.copypaste.tests;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.papyrus.infra.gmfdiag.common.strategy.IStrategy;
import org.eclipse.papyrus.infra.gmfdiag.common.strategy.paste.PasteStrategyManager;
import org.eclipse.papyrus.junit.utils.HandlerUtils;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.junit.utils.tests.AbstractEditorTest;
import org.eclipse.papyrus.views.modelexplorer.ModelExplorerView;
import org.eclipse.papyrus.views.modelexplorer.matching.IMatchingItem;
import org.eclipse.papyrus.views.modelexplorer.matching.ModelElementItemMatchingItem;
import org.eclipse.papyrusrt.junit.rules.UIThreadRule;
import org.eclipse.papyrusrt.umlrt.core.utils.ProtocolUtils;
import org.eclipse.papyrusrt.umlrt.core.utils.RTMessageUtils;
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.RTMessageKind;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.copy.UmlRTPasteStrategy;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.navigator.CommonViewer;
import org.eclipse.uml2.uml.CallEvent;
import org.eclipse.uml2.uml.Collaboration;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.PackageableElement;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.TestRule;

/**
 * @author AS247872
 *
 */
@PluginResource("resource/protocolMessages/model.di")
public class ProtocolMessageCopyPasteTest extends AbstractEditorTest {

	@ClassRule
	public static final TestRule uiThread = new UIThreadRule();


	public static final String RESOURCES_PATH = "resource/protocolMessages/"; //$NON-NLS-1$

	public static final String MODEL_NAME = "model"; //$NON-NLS-1$

	public static final String PROJECT_NAME = "ProtocolMessagesTests"; //$NON-NLS-1$


	protected static Model rootModel;

	protected static ModelExplorerView modelExplorerView;

	protected static ISelectionService selectionService;

	@Before
	public void initModelForCutTest() {
		try {
			initModel(PROJECT_NAME, MODEL_NAME, Platform.getBundle("org.eclipse.papyrusrt.umlrt.tooling.diagram.common.tests"));
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}




	@Test
	public void copyPasteOfProtocolMessageTest() throws Exception {

		List<Object> elements = new ArrayList<>();

		UmlRTPasteStrategy protocolMsgPasteStrategy = getProtocolMsgPasteStrategy();
		Assert.assertNotNull("ProtocolMsgPasteStrategy not present", protocolMsgPasteStrategy);
		Assert.assertTrue("ProtocolMsgPasteStrategy not active", PasteStrategyManager.getInstance().isActive(protocolMsgPasteStrategy));

		// get the model explorer view
		modelExplorerView = getModelExplorerView();
		// selectionService = getSelectionService();
		IWorkbenchWindow activeWorkbenchWindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		selectionService = activeWorkbenchWindow.getSelectionService();
		// Select the ProtocolMsg to be copied
		rootModel = (Model) getRootUMLModel();
		Assert.assertNotNull("RootModel is null", rootModel);
		Package container = rootModel.getNestedPackage("Protocol1");
		Collaboration protocol = (Collaboration) container.getOwnedType("Protocol1");
		elements.add(protocol);
		modelExplorerView.revealSemanticElement(elements);
		// need to expand the item manually see Bug 480403
		IMatchingItem itemToExpand = new ModelElementItemMatchingItem(protocol);
		CommonViewer commonViewer = modelExplorerView.getCommonViewer();
		commonViewer.expandToLevel(itemToExpand, 1);

		Interface messageSetIn = ProtocolUtils.getMessageSetIn(protocol);
		Operation protocolMessageIn = messageSetIn.getOperation("InProtocolMessage1", null, null);
		elements.clear();
		elements.add(protocolMessageIn);
		modelExplorerView.revealSemanticElement(elements);
		Object operationTreeObject = ((IStructuredSelection) selectionService.getSelection()).getFirstElement();
		Assert.assertNotNull("InProtocolMessage1 TreeElement is null", operationTreeObject);

		// copy

		IHandler copyHandler = HandlerUtils.getActiveHandlerFor(ActionFactory.COPY.getCommandId());
		Assert.assertTrue("Copy not available", copyHandler.isEnabled());
		copyHandler.execute(new ExecutionEvent());

		// select tree item where to paste
		Package container2 = rootModel.getNestedPackage("Protocol2");
		Collaboration protocol2 = (Collaboration) container2.getOwnedType("Protocol2");
		elements.clear();
		elements.add(protocol2);
		modelExplorerView.revealSemanticElement(elements);
		Object protocol2TreeObject = ((IStructuredSelection) selectionService.getSelection()).getFirstElement();
		Assert.assertNotNull("Protocol2 TreeElement is null", protocol2TreeObject);


		// paste
		IHandler pasteHandler = HandlerUtils.getActiveHandlerFor(ActionFactory.PASTE.getCommandId());
		Assert.assertTrue("Paste not available", pasteHandler.isEnabled());
		pasteHandler.execute(new ExecutionEvent());


		// check that new protocol message is copyed
		Interface messageSetIn2 = ProtocolUtils.getMessageSetIn(protocol2);
		Operation protocolMessageIn2 = messageSetIn2.getOperation("InProtocolMessage1", null, null);
		Assert.assertNotNull("The copy is missing", protocolMessageIn2);

		// check the right direction
		RTMessageKind kind = RTMessageUtils.getMessageKind(protocolMessageIn);
		assertEquals("Error in the copied operation kind", kind, RTMessageUtils.getMessageKind(protocolMessageIn2));

		// check the creation of an associated call event
		CallEvent operationCallEvent = null;
		for (PackageableElement packageableElement : container2.getPackagedElements()) {
			if (packageableElement instanceof CallEvent) {
				// check the operation
				if (protocolMessageIn2.equals(((CallEvent) packageableElement).getOperation())) {
					// assert there is not already a operation call event found
					operationCallEvent = (CallEvent) packageableElement;
				}
			}
		}
		Assert.assertNotNull("No CallEvent was found for the new message set", operationCallEvent);

	}

	private UmlRTPasteStrategy getProtocolMsgPasteStrategy() {
		List<IStrategy> allStrategies = PasteStrategyManager.getInstance().getAllActiveStrategies();
		for (IStrategy iStrategy : allStrategies) {
			if (iStrategy instanceof UmlRTPasteStrategy) {
				return (UmlRTPasteStrategy) iStrategy;
			}
		}
		return null;
	}


	/**
	 * @see org.eclipse.papyrus.junit.utils.tests.AbstractEditorTest#getSourcePath()
	 *
	 * @return
	 */
	@Override
	protected String getSourcePath() {
		return RESOURCES_PATH;
	}

}
