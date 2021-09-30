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

package org.eclipse.papyrusrt.umlrt.tooling.diagram.common.internal.editpolicies.stereotype;

import static org.eclipse.papyrusrt.umlrt.uml.util.UMLRTExtensionUtil.isVirtualElement;

import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.papyrus.infra.tools.util.TypeUtils;
import org.eclipse.papyrus.uml.diagram.common.editpolicies.AbstractAppliedStereotypeDisplayEditPolicy;
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.UMLRealTimePackage;
import org.eclipse.papyrusrt.umlrt.profile.statemachine.UMLRTStateMachines.UMLRTStateMachinesPackage;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.util.UMLUtil;

/**
 * Helper to which the RT-specific applied-stereotype edit-policies delegate
 * their customizations of the behaviour inherited from Papyrus. This
 * implements a composition pattern to account for the necessity of extending
 * multiple Papyrus classes.
 */
class RTAppliedStereotypeEditPolicyHelper implements Adapter {
	private static final Class<?>[] GET_VALUE_SIGNATURE = { Stereotype.class, String.class };
	private static final Class<?>[] SET_VALUE_SIGNATURE = { Stereotype.class, String.class, Object.class };

	private final Element realHostElement;
	private Element reducedHostElement;

	private RTAppliedStereotypeEditPolicyHelper(Element element) {
		super();

		this.realHostElement = element;
	}

	static RTAppliedStereotypeEditPolicyHelper getInstance(Element element) {
		RTAppliedStereotypeEditPolicyHelper result = (RTAppliedStereotypeEditPolicyHelper) EcoreUtil.getExistingAdapter(
				element, RTAppliedStereotypeEditPolicyHelper.class);

		if (result == null) {
			result = new RTAppliedStereotypeEditPolicyHelper(element);
			element.eAdapters().add(result);
		}

		return result;
	}

	/**
	 * Helper for the {@link AbstractAppliedStereotypeDisplayEditPolicy#getUMLElement} override,
	 * obtaining the {@code host} edit-part's semantic element, possible dynamically proxied to
	 * hide its applied canonical UML-RT stereotypes.
	 * 
	 * @param host
	 *            the host edit-part of the edit-policy that I help
	 * @return the {@code host}'s UML element or a dynamic proxy thereof
	 * 
	 * @see AbstractAppliedStereotypeDisplayEditPolicy#getUMLElement
	 */
	static Element getUMLElement(IGraphicalEditPart host) {
		Element result = TypeUtils.as(host.resolveSemanticElement(), Element.class);

		if (result instanceof NamedElement) {
			NamedElement element = (NamedElement) result;
			if (hasRTStereotypes(element) || isVirtualElement(element)) {
				result = getInstance(element).getReducedUMLElement();
			}
		}

		return result;
	}

	Element getReducedUMLElement() {
		// Provide a view of the element that has no or a reduced set of stereotypes
		if (reducedHostElement == null) {
			createReducedHostElement(isVirtualElement(realHostElement));
		}

		return reducedHostElement;
	}

	private void createReducedHostElement(boolean excludeAllStereotypes) {
		Class<?>[] interfaces = getAllInterfaces(realHostElement.getClass());
		reducedHostElement = (Element) Proxy.newProxyInstance(getClass().getClassLoader(), interfaces,
				(proxy, method, args) -> {
					Object result;

					switch (method.getName()) {
					case "getAppliedStereotypes": //$NON-NLS-1$
						result = excludeAllStereotypes
								? ECollections.EMPTY_ELIST
								: filterStereotypes(realHostElement.getAppliedStereotypes());
						break;
					case "getStereotypeApplications": //$NON-NLS-1$
						result = excludeAllStereotypes
								? ECollections.EMPTY_ELIST
								: filterStereotypeApplications(realHostElement.getStereotypeApplications());
						break;
					case "getValue": //$NON-NLS-1$
						if (Arrays.equals(method.getParameterTypes(), GET_VALUE_SIGNATURE)) {
							Stereotype stereotype = (Stereotype) args[0];
							if (excludeAllStereotypes) {
								throw new IllegalArgumentException(stereotype.getName());
							}
							String propertyName = (String) args[1];
							result = getFilteredValue(realHostElement, stereotype, propertyName);
						} else {
							result = method.invoke(realHostElement, args);
						}
						break;
					case "setValue": //$NON-NLS-1$
						if (Arrays.equals(method.getParameterTypes(), SET_VALUE_SIGNATURE)) {
							Stereotype stereotype = (Stereotype) args[0];
							if (excludeAllStereotypes) {
								throw new IllegalArgumentException(stereotype.getName());
							}
							String propertyName = (String) args[1];
							Object value = args[2];
							result = setFilteredValue(realHostElement, stereotype, propertyName, value);
						} else {
							result = method.invoke(realHostElement, args);
						}
						break;
					default:
						result = method.invoke(realHostElement, args);
						break;
					}

					return result;
				});
	}

	static Class<?>[] getAllInterfaces(Class<?> class_) {
		Set<Class<?>> result = new LinkedHashSet<>();

		for (; (class_ != Object.class) && (class_ != null); class_ = class_.getSuperclass()) {
			result.addAll(Arrays.asList(class_.getInterfaces()));
		}

		return result.toArray(new Class<?>[result.size()]);
	}

	static boolean hasRTStereotypes(Element element) {
		List<Stereotype> applied = element.getAppliedStereotypes();
		return !applied.isEmpty()
				&& applied.stream().anyMatch(RTAppliedStereotypeEditPolicyHelper::isRTStereotype);
	}

	static boolean isRTStereotype(Stereotype stereotype) {
		Profile profile = stereotype.getProfile();
		String nsURI = (profile == null) ? null : profile.getURI();
		return UMLRealTimePackage.eNS_URI.equals(nsURI)
				|| UMLRTStateMachinesPackage.eNS_URI.equals(nsURI);
	}

	static boolean isNotRTStereotype(Stereotype stereotype) {
		return !isRTStereotype(stereotype);
	}

	static EList<Stereotype> filterStereotypes(EList<Stereotype> stereotypes) {
		EList<Stereotype> result;

		if (stereotypes.isEmpty()) {
			result = stereotypes;
		} else {
			result = new BasicEList<>(stereotypes.size());
			stereotypes.stream()
					.filter(RTAppliedStereotypeEditPolicyHelper::isNotRTStereotype)
					.forEach(result::add);
		}

		return result;
	}

	static boolean isRTStereotypeApplication(EObject stereotypeApplication) {
		Stereotype stereotype = UMLUtil.getStereotype(stereotypeApplication);
		return (stereotype != null) && isRTStereotype(stereotype);
	}

	static boolean isNotRTStereotypeApplication(EObject object) {
		return !isRTStereotypeApplication(object);
	}

	static EList<EObject> filterStereotypeApplications(EList<EObject> stereotypeApplications) {
		EList<EObject> result;

		if (stereotypeApplications.isEmpty()) {
			result = stereotypeApplications;
		} else {
			result = new BasicEList<>(stereotypeApplications.size());
			stereotypeApplications.stream()
					.filter(RTAppliedStereotypeEditPolicyHelper::isNotRTStereotypeApplication)
					.forEach(result::add);
		}

		return result;
	}

	static Object getFilteredValue(Element element, Stereotype stereotype, String propertyName) {
		if (isRTStereotype(stereotype)) {
			throw new IllegalArgumentException(stereotype.getName());
		}

		return element.getValue(stereotype, propertyName);
	}

	static Void setFilteredValue(Element element, Stereotype stereotype, String propertyName, Object value) {
		if (isRTStereotype(stereotype)) {
			throw new IllegalArgumentException(stereotype.getName());
		}

		element.setValue(stereotype, propertyName, value);
		return null;
	}

	//
	// EMF Adapter protocol
	//

	@Override
	public Notifier getTarget() {
		return realHostElement;
	}

	@Override
	public void setTarget(Notifier newTarget) {
		if (newTarget == null) {
			reducedHostElement = null;
		} else if (newTarget != realHostElement) {
			throw new IllegalStateException("attempt to reuse helper for another element"); //$NON-NLS-1$
		}
	}

	@Override
	public boolean isAdapterForType(Object type) {
		return type == RTAppliedStereotypeEditPolicyHelper.class;
	}

	@Override
	public void notifyChanged(Notification notification) {
		// In case it is no longer valid
		reducedHostElement = null;
	}
}
