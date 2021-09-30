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

package org.eclipse.papyrusrt.umlrt.tooling.ui.internal.handlers;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Stream;

import javax.xml.parsers.DocumentBuilderFactory;

import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.expressions.Expression;
import org.eclipse.core.expressions.ExpressionConverter;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrusrt.umlrt.uml.util.UMLRTExtensionUtil;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.IWindowListener;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.handlers.IHandlerActivation;
import org.eclipse.ui.handlers.IHandlerService;
import org.eclipse.ui.services.IDisposable;
import org.eclipse.uml2.uml.Element;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

/**
 * Utility for installation of custom source providers in the Eclipse Workbench.
 */
public class WorkbenchSourceProviders {

	/**
	 * Not instantiable by clients.
	 */
	private WorkbenchSourceProviders() {
		super();
	}

	/**
	 * Asynchronously install the custom source providers in the workbench on the UI thread.
	 */
	public static void asyncInstall() {
		Display.getDefault().asyncExec(() -> {
			IWorkbench workbench = PlatformUI.getWorkbench();

			// Do our installations for the discrete services in each
			// workbench window opened by the user
			IWindowListener windowListener = createWindowListener();
			workbench.addWindowListener(windowListener);

			// We already missed the opening of these windows
			Stream.of(workbench.getWorkbenchWindows()).forEach(windowListener::windowOpened);
		});
	}

	/**
	 * A listener that reacts to window openings by installing our source providers
	 * and closings by uninstalling them.
	 */
	private static IWindowListener createWindowListener() {
		return new IWindowListener() {
			private Map<IWorkbenchWindow, IDisposable> activations = new HashMap<>();

			@Override
			public void windowOpened(IWorkbenchWindow window) {
				if (!activations.containsKey(window)) {
					activations.put(window, activateSources(window));
				}
			}

			@Override
			public void windowClosed(IWorkbenchWindow window) {
				IDisposable activation = activations.remove(window);
				if (activation != null) {
					activation.dispose();
				}
			}

			@Override
			public void windowDeactivated(IWorkbenchWindow window) {
				// Pass
			}

			@Override
			public void windowActivated(IWorkbenchWindow window) {
				// Pass
			}
		};
	}

	/**
	 * Activate our custom source providers in the given {@code window}.
	 * 
	 * @param window
	 *            a workbench window
	 * 
	 * @return a tokn that can be disposed to deactivate our source providers and anything else
	 *         installed/activated along with them
	 */
	private static IDisposable activateSources(IWorkbenchWindow window) {
		// We will have zero or more things to dispose
		List<IDisposable> tokens = new ArrayList<>(3); // Anticipate very few

		// We need to interact with some handler-related services
		ISelectionService selections = window.getService((ISelectionService.class));
		ICommandService commands = window.getService(ICommandService.class);
		IHandlerService handlers = window.getService(IHandlerService.class);

		Command delete = commands.getCommand(ActionFactory.DELETE.getCommandId());
		if (delete != null) {
			// Create our exclusion handler
			IHandler exclusionHandler = new ExclusionHandler(true);

			// Give the exclusion handler an activation expression that trumps Model Explorer's
			// delete handler and the diagram's, too
			Expression activeWhen;
			try {
				// Include the activeMenuSelection for highest priority
				Document xml = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new InputSource(
						new StringReader("<and>"
								+ "<with variable=\"papyrusrt.override.delete\">"
								+ "   <equals value=\"true\"/>"
								+ "</with>"
								+ "<with variable=\"activeMenuSelection\">"
								+ "   <iterate ifEmpty=\"true\" operation=\"or\">"
								+ "      <instanceof value=\"java.lang.Object\"/>"
								+ "   </iterate>"
								+ "</with>"
								+ "</and>")));
				activeWhen = ExpressionConverter.getDefault().perform(xml.getDocumentElement());
			} catch (Exception e) {
				throw new WrappedException(e);
			}

			// Register the exlusion handler against the delete action with our activation criteria
			IHandlerActivation excludeActivation = handlers.activateHandler(delete.getId(), exclusionHandler, activeWhen);

			selections.addSelectionListener(new ISelectionListener() {
				@Override
				public void selectionChanged(IWorkbenchPart part, ISelection selection) {
					Predicate<Element> excludeable = UMLRTExtensionUtil::isInherited;
					excludeable = excludeable.or(UMLRTExtensionUtil::isRedefinition);

					IStructuredSelection ssel = (selection instanceof IStructuredSelection)
							? (IStructuredSelection) selection
							: StructuredSelection.EMPTY;

					// is all of the selection comprised of excludeable inherited elements?
					boolean activate = !ssel.isEmpty()
							&& (((List<?>) ssel.toList()).stream()
									.map(EMFHelper::getEObject)
									.filter(Objects::nonNull)
									.filter(Element.class::isInstance).map(Element.class::cast)
									.filter(excludeable)
									.count() == ssel.size());

					if (OverrideDeleteSourceProvider.setEnabled(activate, selection)) {
						// Update the menu action
						commands.refreshElements(delete.getId(), null);
					}
				}
			});

			tokens.add(() -> handlers.deactivateHandler(excludeActivation));
		}

		return () -> tokens.forEach(IDisposable::dispose);
	}

}
