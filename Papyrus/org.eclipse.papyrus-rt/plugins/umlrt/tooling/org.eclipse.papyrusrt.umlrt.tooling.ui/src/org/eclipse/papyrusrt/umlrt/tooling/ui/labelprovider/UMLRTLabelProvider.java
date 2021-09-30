/*****************************************************************************
 * Copyright (c) 2017 CEA LIST, Christian W. Damus, and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *   Christian W. Damus - bugs 510315, 507282, 513066, 513793
 *   
 *****************************************************************************/
package org.eclipse.papyrusrt.umlrt.tooling.ui.labelprovider;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.provider.ComposedImage;
import org.eclipse.emf.edit.provider.IChangeNotifier;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.INotifyChangedListener;
import org.eclipse.emf.edit.provider.ViewerNotification;
import org.eclipse.emf.edit.ui.provider.ExtendedImageRegistry;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.ISpecializationType;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProviderChangedEvent;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.uml.tools.providers.DelegatingItemLabelProvider;
import org.eclipse.papyrus.uml.tools.providers.UMLFilteredLabelProvider;
import org.eclipse.papyrusrt.umlrt.core.types.IUMLRTElementTypes;
import org.eclipse.papyrusrt.umlrt.core.types.UMLRTElementTypesEnumerator;
import org.eclipse.papyrusrt.umlrt.core.utils.MessageSetUtils;
import org.eclipse.papyrusrt.umlrt.core.utils.TransitionUtils;
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.RTMessageKind;
import org.eclipse.papyrusrt.umlrt.tooling.ui.Activator;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTInheritanceKind;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTTrigger;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.MultiplicityElement;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Transition;
import org.eclipse.uml2.uml.Trigger;

import com.google.common.collect.Lists;

/**
 * Generic label provider for graphical representation of UMLRT elements.
 */
public class UMLRTLabelProvider extends UMLFilteredLabelProvider {

	private static final String UI_PLUGIN_ID = "org.eclipse.papyrusrt.umlrt.tooling.ui";
	private static final String OVERLAY_PATH = "$nl$/icons/full/ovr16/";
	private static ImageDescriptor EXCLUDED_OVERLAY = AbstractUIPlugin.imageDescriptorFromPlugin(
			UI_PLUGIN_ID, OVERLAY_PATH + "excluded_ovr.png");
	private static ImageDescriptor INHERITED_OVERLAY = AbstractUIPlugin.imageDescriptorFromPlugin(
			UI_PLUGIN_ID, OVERLAY_PATH + "inherited_ovr.png");
	private static ImageDescriptor REDEFINITION_OVERLAY = AbstractUIPlugin.imageDescriptorFromPlugin(
			UI_PLUGIN_ID, OVERLAY_PATH + "redefinition_ovr.png");
	private static ImageDescriptor WARNING_OVERLAY = AbstractUIPlugin.imageDescriptorFromPlugin(
			UI_PLUGIN_ID, OVERLAY_PATH + "warning_ovr.png");
	public final Map<String, String> typeIdtoIconPath;
	/** path to the icons in the plugin */
	protected static String ICON_PATH = "/icons/";
	protected static final String RT_PSEUDO_STATE_CHOICE_ICON = ICON_PATH + "rt_pseudostate_choice.gif";
	protected static final String RT_PSEUDO_STATE_DEEP_HISTORY_ICON = ICON_PATH + "rt_pseudostate_deepHistory.gif";
	protected static final String RT_PSEUDO_STATE_ENTRY_POINT_ICON = ICON_PATH + "rt_pseudostate_entryPoint.gif";
	protected static final String RT_PSEUDO_STATE_EXIT_POINT_ICON = ICON_PATH + "rt_pseudostate_exitPoint.gif";
	protected static final String RT_PSEUDO_STATE_FORK_ICON = ICON_PATH + "rt_pseudostate_fork.gif";
	protected static final String RT_PSEUDO_STATE_INITIAL_ICON = ICON_PATH + "rt_pseudostate_initial.gif";
	protected static final String RT_PSEUDO_STATE_JOIN_ICON = ICON_PATH + "rt_pseudostate_join.gif";
	protected static final String RT_PSEUDO_STATE_JUNCTION_ICON = ICON_PATH + "rt_pseudostate_junction.gif";
	protected static final String RT_PSEUDO_STATE_SHALLOW_HISTORY_ICON = ICON_PATH + "rt_pseudostate_shallowHistory.gif";
	protected static final String RT_PSEUDO_STATE_TERMINATE_ICON = ICON_PATH + "rt_pseudostate_terminate.gif";
	protected static final String RT_TRANSITION_ICON = ICON_PATH + "rt_transition.gif";
	protected static final String RT_TRANSITION_INTERNAL_ICON = ICON_PATH + "rt_transition_internal.gif";
	protected static String RT_MESSAGE_IN_ICON = ICON_PATH + "protocolmessage_in.gif";
	protected static String RT_MESSAGE_IN_OUT_ICON = ICON_PATH + "protocolmessage_inout.gif";
	protected static String RT_MESSAGE_OUT_ICON = ICON_PATH + "protocolmessage_out.gif";
	protected static String RT_MESSAGE_UNDEFINED_ICON = ICON_PATH + "protocolmessage_undefined.gif";
	protected static final IItemLabelProvider labelProvider = new DelegatingItemLabelProvider();
	private final CopyOnWriteArrayList<ILabelProviderListener> listeners = new CopyOnWriteArrayList<>();
	protected final INotifyChangedListener changeForwarder = this::forwardChange;

	/**
	 * Constructor.
	 *
	 */
	public UMLRTLabelProvider() {
		typeIdtoIconPath = new HashMap<>();
		typeIdtoIconPath.put(IUMLRTElementTypes.CAPSULE_ID, ICON_PATH + "capsule.png"); //$NON-NLS-1$
		typeIdtoIconPath.put(IUMLRTElementTypes.CAPSULE_PART_ID, ICON_PATH + "capsule_part.png");//$NON-NLS-1$
		typeIdtoIconPath.put(IUMLRTElementTypes.PROTOCOL_CONTAINER_ID, ICON_PATH + "protocol_container.png");//$NON-NLS-1$
		typeIdtoIconPath.put(IUMLRTElementTypes.PROTOCOL_ID, ICON_PATH + "protocol.png");//$NON-NLS-1$
		typeIdtoIconPath.put(IUMLRTElementTypes.RT_CONNECTOR_ID, ICON_PATH + "rt_connector.gif");//$NON-NLS-1$
		typeIdtoIconPath.put(IUMLRTElementTypes.RT_EXCLUDED_ELEMENT_ID, ICON_PATH + "rt_excludedElement.gif");//$NON-NLS-1$
		typeIdtoIconPath.put(IUMLRTElementTypes.RT_MESSAGE_SET_ID, ICON_PATH + "rt_messageset.gif");//$NON-NLS-1$
		typeIdtoIconPath.put(IUMLRTElementTypes.RT_PORT_ID, ICON_PATH + "rt_port.gif");//$NON-NLS-1$
		typeIdtoIconPath.put(IUMLRTElementTypes.PROTOCOL_MESSAGE_IN_ID, RT_MESSAGE_IN_ICON);
		typeIdtoIconPath.put(IUMLRTElementTypes.PROTOCOL_MESSAGE_INOUT_ID, RT_MESSAGE_IN_OUT_ICON);
		typeIdtoIconPath.put(IUMLRTElementTypes.PROTOCOL_MESSAGE_OUT_ID, RT_MESSAGE_OUT_ICON);
		typeIdtoIconPath.put(IUMLRTElementTypes.PROTOCOL_MESSAGE_ID, RT_MESSAGE_UNDEFINED_ICON);

		// state machine
		typeIdtoIconPath.put(IUMLRTElementTypes.RT_STATE_MACHINE_ID, ICON_PATH + "rt_statemachine.gif");
		typeIdtoIconPath.put(IUMLRTElementTypes.RT_REGION_ID, ICON_PATH + "rt_region.gif");
		typeIdtoIconPath.put(IUMLRTElementTypes.RT_STATE_ID, ICON_PATH + "rt_state.gif");

		typeIdtoIconPath.put(IUMLRTElementTypes.RT_PSEUDO_STATE_ID, ICON_PATH + "rt_pseudostate.gif");
		typeIdtoIconPath.put(IUMLRTElementTypes.RT_PSEUDO_STATE_CHOICE_ID, RT_PSEUDO_STATE_CHOICE_ICON);
		typeIdtoIconPath.put(IUMLRTElementTypes.RT_PSEUDO_STATE_DEEP_HISTORY_ID, RT_PSEUDO_STATE_DEEP_HISTORY_ICON);
		typeIdtoIconPath.put(IUMLRTElementTypes.RT_PSEUDO_STATE_ENTRY_POINT_ID, RT_PSEUDO_STATE_ENTRY_POINT_ICON);
		typeIdtoIconPath.put(IUMLRTElementTypes.RT_PSEUDO_STATE_EXIT_POINT_ID, RT_PSEUDO_STATE_EXIT_POINT_ICON);
		typeIdtoIconPath.put(IUMLRTElementTypes.RT_PSEUDO_STATE_FORK_ID, RT_PSEUDO_STATE_FORK_ICON);
		typeIdtoIconPath.put(IUMLRTElementTypes.RT_PSEUDO_STATE_INITIAL_ID, RT_PSEUDO_STATE_INITIAL_ICON);
		typeIdtoIconPath.put(IUMLRTElementTypes.RT_PSEUDO_STATE_JOIN_ID, RT_PSEUDO_STATE_JOIN_ICON);
		typeIdtoIconPath.put(IUMLRTElementTypes.RT_PSEUDO_STATE_JUNCTION_ID, RT_PSEUDO_STATE_JUNCTION_ICON);
		typeIdtoIconPath.put(IUMLRTElementTypes.RT_PSEUDO_STATE_SHALLOW_HISTORY_ID, RT_PSEUDO_STATE_SHALLOW_HISTORY_ICON);
		typeIdtoIconPath.put(IUMLRTElementTypes.RT_PSEUDO_STATE_TERMINATE_ID, RT_PSEUDO_STATE_TERMINATE_ICON);
		typeIdtoIconPath.put(IUMLRTElementTypes.TRANSITION_INTERNAL_ID, RT_TRANSITION_INTERNAL_ICON);

		if (labelProvider instanceof IChangeNotifier) {
			((IChangeNotifier) labelProvider).addListener(changeForwarder);
		}
	}

	@Override
	public void dispose() {
		if (labelProvider instanceof IChangeNotifier) {
			((IChangeNotifier) labelProvider).removeListener(changeForwarder);
		}

		super.dispose();
	}

	@Override
	public Image getImage(Object element) {
		Image result;

		if (element instanceof IStructuredSelection) {
			// Handle the Tabbed Properties View
			result = getImage((IStructuredSelection) element);
		} else {
			EObject eObject = EMFHelper.getEObject(element);
			if (eObject != null) {
				result = getImage(eObject);
			} else {
				result = super.getImage(element);
			}
		}

		return result;
	}

	@Override
	protected Image getImage(EObject element) {
		Image result = doGetImage(element);

		if (result != null) {
			// Look for overlays
			List<?> overlays = getOverlays(element, null);
			if ((overlays != null) && !overlays.isEmpty()) {
				// Left to right, top to bottom, up to four
				ComposedImage composed = new UMLRTComposedImage(Lists.asList(result, overlays.toArray()));
				result = ExtendedImageRegistry.getInstance().getImage(composed);
			}
		}

		return result;
	}

	protected Image doGetImage(EObject element) {
		if (!(element instanceof Element)) {
			Activator.log.debug("Trying to display an UMLRT image for a non UML-RT element");
			return null;
		}

		// depending on the element type that matches, return a different icon
		String matchingTypeMatcher = getMatchingType(element);

		Image image = null;
		// a match was done. give a different icon given the value
		switch (matchingTypeMatcher) {
		case IUMLRTElementTypes.PROTOCOL_MESSAGE_IN_ID:
			image = org.eclipse.papyrus.infra.widgets.Activator.getDefault().getImage(org.eclipse.papyrusrt.umlrt.tooling.ui.Activator.PLUGIN_ID, RT_MESSAGE_IN_ICON);
			break;
		case IUMLRTElementTypes.PROTOCOL_MESSAGE_OUT_ID:
			image = org.eclipse.papyrus.infra.widgets.Activator.getDefault().getImage(org.eclipse.papyrusrt.umlrt.tooling.ui.Activator.PLUGIN_ID, RT_MESSAGE_OUT_ICON);
			break;
		case IUMLRTElementTypes.PROTOCOL_MESSAGE_INOUT_ID:
			image = org.eclipse.papyrus.infra.widgets.Activator.getDefault().getImage(org.eclipse.papyrusrt.umlrt.tooling.ui.Activator.PLUGIN_ID, RT_MESSAGE_IN_OUT_ICON);
			break;

		case IUMLRTElementTypes.RT_PSEUDO_STATE_CHOICE_ID:
			image = org.eclipse.papyrus.infra.widgets.Activator.getDefault().getImage(org.eclipse.papyrusrt.umlrt.tooling.ui.Activator.PLUGIN_ID, RT_PSEUDO_STATE_CHOICE_ICON);
			break;
		case IUMLRTElementTypes.RT_PSEUDO_STATE_DEEP_HISTORY_ID:
			image = org.eclipse.papyrus.infra.widgets.Activator.getDefault().getImage(org.eclipse.papyrusrt.umlrt.tooling.ui.Activator.PLUGIN_ID, RT_PSEUDO_STATE_DEEP_HISTORY_ICON);
			break;
		case IUMLRTElementTypes.RT_PSEUDO_STATE_ENTRY_POINT_ID:
			image = org.eclipse.papyrus.infra.widgets.Activator.getDefault().getImage(org.eclipse.papyrusrt.umlrt.tooling.ui.Activator.PLUGIN_ID, RT_PSEUDO_STATE_ENTRY_POINT_ICON);
			break;
		case IUMLRTElementTypes.RT_PSEUDO_STATE_EXIT_POINT_ID:
			image = org.eclipse.papyrus.infra.widgets.Activator.getDefault().getImage(org.eclipse.papyrusrt.umlrt.tooling.ui.Activator.PLUGIN_ID, RT_PSEUDO_STATE_EXIT_POINT_ICON);
			break;
		case IUMLRTElementTypes.RT_PSEUDO_STATE_FORK_ID:
			image = org.eclipse.papyrus.infra.widgets.Activator.getDefault().getImage(org.eclipse.papyrusrt.umlrt.tooling.ui.Activator.PLUGIN_ID, RT_PSEUDO_STATE_FORK_ICON);
			break;
		case IUMLRTElementTypes.RT_PSEUDO_STATE_INITIAL_ID:
			image = org.eclipse.papyrus.infra.widgets.Activator.getDefault().getImage(org.eclipse.papyrusrt.umlrt.tooling.ui.Activator.PLUGIN_ID, RT_PSEUDO_STATE_INITIAL_ICON);
			break;
		case IUMLRTElementTypes.RT_PSEUDO_STATE_JOIN_ID:
			image = org.eclipse.papyrus.infra.widgets.Activator.getDefault().getImage(org.eclipse.papyrusrt.umlrt.tooling.ui.Activator.PLUGIN_ID, RT_PSEUDO_STATE_JOIN_ICON);
			break;
		case IUMLRTElementTypes.RT_PSEUDO_STATE_JUNCTION_ID:
			image = org.eclipse.papyrus.infra.widgets.Activator.getDefault().getImage(org.eclipse.papyrusrt.umlrt.tooling.ui.Activator.PLUGIN_ID, RT_PSEUDO_STATE_JUNCTION_ICON);
			break;
		case IUMLRTElementTypes.RT_PSEUDO_STATE_SHALLOW_HISTORY_ID:
			image = org.eclipse.papyrus.infra.widgets.Activator.getDefault().getImage(org.eclipse.papyrusrt.umlrt.tooling.ui.Activator.PLUGIN_ID, RT_PSEUDO_STATE_SHALLOW_HISTORY_ICON);
			break;
		case IUMLRTElementTypes.RT_PSEUDO_STATE_TERMINATE_ID:
			image = org.eclipse.papyrus.infra.widgets.Activator.getDefault().getImage(org.eclipse.papyrusrt.umlrt.tooling.ui.Activator.PLUGIN_ID, RT_PSEUDO_STATE_TERMINATE_ICON);
			break;
		case IUMLRTElementTypes.RT_MESSAGE_SET_ID:
			RTMessageKind kind = MessageSetUtils.getMessageKind(element);
			if (kind == RTMessageKind.IN) {
				image = org.eclipse.papyrus.infra.widgets.Activator.getDefault().getImage(org.eclipse.papyrusrt.umlrt.tooling.ui.Activator.PLUGIN_ID, ICON_PATH + "rt_messageset_in.gif");
			} else if (kind == RTMessageKind.OUT) {
				image = org.eclipse.papyrus.infra.widgets.Activator.getDefault().getImage(org.eclipse.papyrusrt.umlrt.tooling.ui.Activator.PLUGIN_ID, ICON_PATH + "rt_messageset_out.gif");
			} else if (kind == RTMessageKind.IN_OUT) {
				image = org.eclipse.papyrus.infra.widgets.Activator.getDefault().getImage(org.eclipse.papyrusrt.umlrt.tooling.ui.Activator.PLUGIN_ID, ICON_PATH + "rt_messageset_inout.gif");
			} else {
				image = org.eclipse.papyrus.infra.widgets.Activator.getDefault().getImage(org.eclipse.papyrusrt.umlrt.tooling.ui.Activator.PLUGIN_ID, typeIdtoIconPath.get(IUMLRTElementTypes.RT_MESSAGE_SET_ID));
			}
			break;
		default:
			image = getElementImage(matchingTypeMatcher, element);
			break;
		}

		return image;
	}

	protected List<Object> getOverlays(EObject element, List<Object> images) {
		List<Object> result = images;

		if (element instanceof NamedElement) {
			Image overlay = getInheritanceOverlay((NamedElement) element);
			if (overlay != null) {
				if (result == null) {
					result = new ArrayList<>(3); // Anticipate few overlays
				}
				result.add(overlay);
			}
		}

		// FIXME: According to bug 515492 the trigger warning decorator is temporary disabled until bug 511102
		// is fixed which should differentiate whether a transition actually should have a trigger or not.
		if (element instanceof Transition && false && TransitionUtils.hasNameAndNoTriggers(((Transition) element))) {
			Image overlay = getWarningOverlay();
			if (overlay != null) {
				if (result == null) {
					result = new ArrayList<>(3); // Anticipate few overlays
				}
				result.add(overlay);
			}
		}

		return result;
	}

	protected Image getWarningOverlay() {
		return ExtendedImageRegistry.getInstance().getImage(WARNING_OVERLAY);
	}

	protected Image getInheritanceOverlay(NamedElement element) {
		Image result;

		UMLRTInheritanceKind inheritance;
		if (element instanceof Trigger) {
			// Special case for these, to account for the guard
			UMLRTTrigger trigger = UMLRTTrigger.getInstance((Trigger) element);
			inheritance = (trigger != null)
					? trigger.getInheritanceKind()
					: UMLRTInheritanceKind.of(element);
		} else {
			inheritance = UMLRTInheritanceKind.of(element);
		}

		switch (inheritance) {
		case INHERITED:
			result = ExtendedImageRegistry.getInstance().getImage(INHERITED_OVERLAY);
			break;
		case REDEFINED:
			result = ExtendedImageRegistry.getInstance().getImage(REDEFINITION_OVERLAY);
			break;
		case EXCLUDED:
			result = ExtendedImageRegistry.getInstance().getImage(EXCLUDED_OVERLAY);
			break;
		default:
			result = null;
			break;
		}

		return result;
	}

	@Override
	public String getText(Object element) {
		String result;

		if (element instanceof IStructuredSelection) {
			// Handle the Tabbed Properties View
			result = getText((IStructuredSelection) element);
		} else {
			EObject eObject = EMFHelper.getEObject(element);
			if (eObject != null) {
				result = getText(eObject);
			} else {
				result = super.getText(element);
			}
		}

		return result;
	}

	@Override
	protected String getText(EObject element) {
		if (element instanceof MultiplicityElement) {
			// Ensure that the bounds values (if any) have the item-provider attached
			// so that they will fire viewer notifications when their values change.
			MultiplicityElement mult = (MultiplicityElement) element;
			if (mult.getLowerValue() != null) {
				getText(mult.getLowerValue());
			}
			if (mult.getUpperValue() != null) {
				getText(mult.getUpperValue());
			}
		}
		if (element instanceof Operation) {
			// Ensure that the parameters have the item-provider attached so that
			// they will fire viewer notifications when they change
			Operation operation = (Operation) element;
			operation.getOwnedParameters().forEach(this::getText);
		}

		return labelProvider.getText(element);
	}

	/**
	 * Return the element type identifier for the given semantic EObject, given the predefined UML-RT list
	 * 
	 * @param semanticObject
	 *            the element to display
	 * @return the unique UML-RT element type identifier or <code>null</code>
	 */
	protected String getMatchingType(EObject semanticObject) {
		for (IElementType type : UMLRTElementTypesEnumerator.getAllRTTypes()) {
			if (type instanceof ISpecializationType) {
				if (((ISpecializationType) type).getMatcher().matches(semanticObject)) {
					return type.getId();
				}
			}
		}
		return null;
	}

	/**
	 * Obtains the icon image for an element type.
	 * 
	 * @param id
	 *            the element type identifier
	 * @param semanticObject
	 *            the instance of the element type for which the icon is requested
	 * 
	 * @return the icon image, or {@code null} if none can be determined by element type
	 */
	protected Image getElementImage(String id, EObject semanticObject) {
		String iconPath = typeIdtoIconPath.get(id);
		if (iconPath != null) {
			return org.eclipse.papyrus.infra.widgets.Activator.getDefault().getImage(org.eclipse.papyrusrt.umlrt.tooling.ui.Activator.PLUGIN_ID, iconPath);
		}
		IElementType type = ElementTypeRegistry.getInstance().getType(id);
		if (type != null) {
			URL iconURL = type.getIconURL();
			if (iconURL != null) {
				return org.eclipse.papyrus.infra.widgets.Activator.getDefault().getImage(ImageDescriptor.createFromURL(iconURL));
			}
		}
		return null;

	}

	@Override
	public void addListener(ILabelProviderListener listener) {
		// The superclass's handling of listeners is useless to us
		listeners.addIfAbsent(listener);
	}

	@Override
	public void removeListener(ILabelProviderListener listener) {
		// The superclass's handling of listeners is useless to us
		listeners.remove(listener);
	}

	private void forwardChange(Notification notification) {
		if (notification instanceof ViewerNotification) {
			ViewerNotification vnot = (ViewerNotification) notification;
			if (vnot.isLabelUpdate() && !listeners.isEmpty()) {
				LabelProviderChangedEvent event = new LabelProviderChangedEvent(this, vnot.getElement());
				listeners.forEach(l -> {
					try {
						l.labelProviderChanged(event);
					} catch (Exception e) {
						Activator.log.error("Uncaught exception in label provider listener", e); //$NON-NLS-1$
					}
				});
			}
		}
	}

	//
	// Nested types
	//

	private static class UMLRTComposedImage extends ComposedImage {

		UMLRTComposedImage(Collection<?> images) {
			super(images);
		}

		@Override
		public List<Point> getDrawPoints(Size size) {
			List<Point> result = super.getDrawPoints(size);

			// Support up to four decorations, from left to right and top bottom
			if (result.size() > 2) {
				// Position 0 is TL, 1 = TR, 2 = BL, 3 = BR
				int position = 1;
				for (int i = 2; i < result.size(); i++) {
					Point next = result.get(i);
					Size deco = imageSizes.get(i);

					if ((position & 1) != 0) {
						next.x = size.width - deco.width;
					}
					if ((position & 2) != 0) {
						next.y = size.height - deco.height;
					}
					position = (position + 1) % 3;
				}
			}

			return result;
		}
	}
}
