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

package org.eclipse.papyrusrt.junit.rules;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.gmf.runtime.emf.core.resources.ResourceHelperImpl;
import org.eclipse.papyrus.junit.utils.rules.AbstractModelFixture;
import org.eclipse.papyrus.junit.utils.rules.ResourceSetFixture;
import org.eclipse.papyrusrt.umlrt.core.utils.ModelUtils;
import org.eclipse.papyrusrt.umlrt.uml.util.UMLRTResourcesUtil;

/**
 * A specialized {@link ResourceSetFixture} that ensures that the resource set
 * is configured for the UML-RT language.
 */
public class UMLRTResourceSetFixture extends ResourceSetFixture {

	public UMLRTResourceSetFixture() {
		super();
	}

	@Override
	protected Resource initModelResource(String targetPath, AbstractModelFixture.ResourceKind resourceKind, String resourcePath) {
		Resource result;

		if (getResourceSet() == null) {
			// Bootstrap mode. We use a temporary resource set to copy resources
			// to the workspace, so language doesn't matter
			result = super.initModelResource(targetPath, resourceKind, resourcePath);
		} else {
			ResourceSet rset = getResourceSet();

			UMLRTResourcesUtil.installUMLRTFactory(rset);
			UMLRTResourcesUtil.initLocalStereotypeApplicationHelper(rset);
			installResourceHelper(rset);

			result = super.initModelResource(targetPath, resourceKind, resourcePath);
		}

		return result;
	}

	private void installResourceHelper(ResourceSet rset) {
		rset.eAdapters().add(new ResourceHelperImpl() {

			@Override
			public void notifyChanged(Notification msg) {
				if (msg.getNotifier() instanceof ResourceSet) {
					if (msg.getFeatureID(ResourceSet.class) == ResourceSet.RESOURCE_SET__RESOURCES) {
						switch (msg.getEventType()) {
						case Notification.ADD:
							((Resource) msg.getNewValue()).eAdapters().add(this);
							break;
						case Notification.ADD_MANY:
							((Collection<?>) msg.getNewValue()).forEach(
									r -> ((Resource) r).eAdapters().add(this));
							break;
						case Notification.REMOVE:
							((Resource) msg.getOldValue()).eAdapters().remove(this);
							break;
						case Notification.REMOVE_MANY:
							((Collection<?>) msg.getOldValue()).forEach(
									r -> ((Resource) r).eAdapters().remove(this));
							break;
						case Notification.SET:
							((Resource) msg.getNewValue()).eAdapters().add(this);
							((Resource) msg.getOldValue()).eAdapters().remove(this);
							break;
						}
					}
				}
			}

			@Override
			public EObject create(EClass eClass) {
				return ModelUtils.create(rset, eClass);
			}
		});
	}

}
