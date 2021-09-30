/*****************************************************************************
 * Copyright (c) 2015 CEA LIST and others.
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

package org.eclipse.papyrusrt.umlrt.tooling.modelexplorer.provider;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.ISpecializationType;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.uml.tools.providers.DelegatingItemLabelProvider;
import org.eclipse.papyrus.uml.tools.providers.UMLFilteredLabelProvider;
import org.eclipse.papyrusrt.umlrt.core.types.IUMLRTElementTypes;
import org.eclipse.papyrusrt.umlrt.core.types.UMLRTElementTypesEnumerator;
import org.eclipse.papyrusrt.umlrt.core.utils.MessageSetUtils;
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.RTMessageKind;
import org.eclipse.papyrusrt.umlrt.tooling.modelexplorer.Activator;
import org.eclipse.swt.graphics.Image;
import org.eclipse.uml2.uml.Element;

/**
 * UML-RT specific label provider.
 */
public class UMLRTLabelProvider extends UMLFilteredLabelProvider {

	public final Map<String, String> typeIdtoIconPath;

	/** path to the icons in the plugin */
	protected static String ICON_PATH = "/icons/";

	protected static String RT_MESSAGE_IN_ICON = ICON_PATH + "protocolmessage_in.gif";//$NON-NLS-1$
	protected static String RT_MESSAGE_IN_OUT_ICON = ICON_PATH + "protocolmessage_inout.gif";//$NON-NLS-1$
	protected static String RT_MESSAGE_OUT_ICON = ICON_PATH + "protocolmessage_out.gif";//$NON-NLS-1$
	protected static String RT_MESSAGE_UNDEFINED_ICON = ICON_PATH + "protocolmessage_undefined.gif";//$NON-NLS-1$

	private static final IItemLabelProvider labelProvider = new DelegatingItemLabelProvider();

	/**
	 * Default constructor
	 */
	public UMLRTLabelProvider() {
		typeIdtoIconPath = new HashMap<String, String>();
		typeIdtoIconPath.put(IUMLRTElementTypes.CAPSULE_ID, ICON_PATH + "capsule.png"); //$NON-NLS-1$
		typeIdtoIconPath.put(IUMLRTElementTypes.CAPSULE_PART_ID, ICON_PATH + "capsule_part.png");//$NON-NLS-1$
		typeIdtoIconPath.put(IUMLRTElementTypes.PROTOCOL_CONTAINER_ID, ICON_PATH + "protocol_container.png");//$NON-NLS-1$
		typeIdtoIconPath.put(IUMLRTElementTypes.PROTOCOL_ID, ICON_PATH + "protocol.png");//$NON-NLS-1$
		typeIdtoIconPath.put(IUMLRTElementTypes.RT_CONNECTOR_ID, ICON_PATH + "rt_connector.png");//$NON-NLS-1$
		typeIdtoIconPath.put(IUMLRTElementTypes.RT_EXCLUDED_ELEMENT_ID, ICON_PATH + "rt_excludedElement.gif");//$NON-NLS-1$
		typeIdtoIconPath.put(IUMLRTElementTypes.RT_MESSAGE_SET_ID, ICON_PATH + "rt_messageset.gif");//$NON-NLS-1$
		typeIdtoIconPath.put(IUMLRTElementTypes.RT_PORT_ID, ICON_PATH + "rt_port.png");//$NON-NLS-1$
		typeIdtoIconPath.put(IUMLRTElementTypes.PROTOCOL_MESSAGE_IN_ID, RT_MESSAGE_IN_ICON);
		typeIdtoIconPath.put(IUMLRTElementTypes.PROTOCOL_MESSAGE_INOUT_ID, RT_MESSAGE_IN_OUT_ICON);
		typeIdtoIconPath.put(IUMLRTElementTypes.PROTOCOL_MESSAGE_OUT_ID, RT_MESSAGE_OUT_ICON);
		typeIdtoIconPath.put(IUMLRTElementTypes.PROTOCOL_MESSAGE_ID, RT_MESSAGE_UNDEFINED_ICON);
	}

	/**
	 * @see org.eclipse.papyrus.uml.tools.providers.UMLLabelProvider#getImage(org.eclipse.emf.ecore.EObject)
	 *
	 * @param element
	 * @return
	 */
	@Override
	public Image getImage(Object element) {
		EObject semanticObject = EMFHelper.getEObject(element);

		if (!(semanticObject instanceof Element)) {
			Activator.log.debug("Trying to display an UMLRT image for a non UML-RT element");
			return null;
		}

		// depending on the element type that matches, return a different icon
		String matchingTypeMatcher = getMatchingType(semanticObject);

		if (matchingTypeMatcher == null) {
			return null;
		}

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

		case IUMLRTElementTypes.RT_MESSAGE_SET_ID:
			RTMessageKind kind = MessageSetUtils.getMessageKind(semanticObject);
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
			image = getElementImage(matchingTypeMatcher, semanticObject);
			break;
		}

		return image;
	}


	/**
	 * @see org.eclipse.papyrus.uml.tools.providers.UMLLabelProvider#getText(java.lang.Object)
	 *
	 * @param element
	 * @return
	 */
	@Override
	public String getText(Object element) {
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
	 * @param type
	 * @param element
	 * @return
	 */
	protected Image getElementImage(String id, EObject semanticObject) {
		String iconPath = typeIdtoIconPath.get(id);
		if (iconPath != null) {
			return org.eclipse.papyrus.infra.widgets.Activator.getDefault().getImage(org.eclipse.papyrusrt.umlrt.tooling.ui.Activator.PLUGIN_ID, iconPath);
		}
		return null;

	}

}
