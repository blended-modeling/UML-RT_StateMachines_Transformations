/*****************************************************************************
 * Copyright (c) 2015, 2017 CEA LIST, Christian W. Damus, and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *   Christian W. Damus - bugs 494367, 467545, 510189
 *   
 *****************************************************************************/

package org.eclipse.papyrusrt.umlrt.core.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.common.core.command.UnexecutableCommand;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.ISpecializationType;
import org.eclipse.gmf.runtime.emf.type.core.requests.MoveRequest;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.papyrus.infra.services.edit.service.IElementEditService;
import org.eclipse.papyrusrt.umlrt.core.types.IUMLRTElementTypes;
import org.eclipse.uml2.uml.Collaboration;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * Utility class for PackageContainers
 */
public class ProtocolContainerUtils {


	public static boolean containsProtocolContainer(Package container) {
		Collection<Package> packages = EcoreUtil.getObjectsByType(container.getPackagedElements(), UMLPackage.eINSTANCE.getPackage());

		if (packages != null && !packages.isEmpty()) {
			IElementType type = ElementTypeRegistry.getInstance().getType(IUMLRTElementTypes.PROTOCOL_CONTAINER_ID);
			if (!(type instanceof ISpecializationType)) { // check at the same time UMLRT element types are correctly loaded
				return false;
			}

			for (Package pkg : packages) {
				// check the package is a protocol container
				if (((ISpecializationType) type).getMatcher().matches(pkg)) {
					return true;
				}
			}
		}
		return false;
	}

	public static boolean isProtocolContainer(EObject package_) {
		IElementType type = ElementTypeRegistry.getInstance().getType(IUMLRTElementTypes.PROTOCOL_CONTAINER_ID);
		if (!(type instanceof ISpecializationType)) { // check at the same time UMLRT element types are correctly loaded
			return false;
		}
		if (((ISpecializationType) type).getMatcher().matches(package_)) {
			return true;
		}
		return false;
	}

	/**
	 * Returns the packageElements, but with protocol container package replaced by protocol
	 * 
	 * @param source
	 * @return
	 */
	public static List<PackageableElement> getFilteredPackagedElements(Element source) {
		if (!(source instanceof Package)) {
			return Collections.emptyList();
		}

		if (!containsProtocolContainer((Package) source)) {
			return ((Package) source).getPackagedElements();
		}

		List<PackageableElement> result = new ArrayList<>(((Package) source).getPackagedElements());

		for (ListIterator<PackageableElement> iter = result.listIterator(); iter.hasNext();) {
			PackageableElement next = iter.next();

			if ((next instanceof Package) && isProtocolContainer(next)) {
				// replace it with its contained protocol
				Collaboration protocol = ProtocolContainerUtils.getProtocol((Package) next);
				if (protocol == null) {
					iter.remove();
				} else {
					iter.set(protocol);
				}
			}
		}

		return result;
	}

	public static ICommand createMoveProtocolContainerCommand(final MoveRequest request, final EObject protocolContainerToMove) {
		ICommand result;

		EObject targetContainer = PackageUtils.getNearestPackage(request.getTargetContainer());
		MoveRequest moveRequest = new MoveRequest(targetContainer, protocolContainerToMove);

		// We need to account for advice on the moving of the protocol container
		IElementEditService edit = ElementEditServiceUtils.getCommandProvider(targetContainer);
		if (edit == null) {
			// Don't move anything, then
			result = UnexecutableCommand.INSTANCE;
		} else {
			result = edit.getEditCommand(moveRequest);
			if (result == null) {
				// Don't move anything, then
				result = UnexecutableCommand.INSTANCE;
			}
		}

		return result;
	}

	/**
	 * Obtains the protocol from a protocol container.
	 * 
	 * @param protocolContainer
	 *            the protocol container in which protocol should be searched
	 * @return the protocol found (collaboration stereotyped as {@literal <<protocol>>}),
	 *         or {@code null} if none was found
	 */
	public static Collaboration getProtocol(Package protocolContainer) {
		if (protocolContainer == null) {
			return null;
		}
		for (PackageableElement packageableElement : protocolContainer.getPackagedElements()) {
			if (ProtocolUtils.isProtocol(packageableElement)) {
				return (Collaboration) packageableElement;
			}
		}
		return null;
	}

}
