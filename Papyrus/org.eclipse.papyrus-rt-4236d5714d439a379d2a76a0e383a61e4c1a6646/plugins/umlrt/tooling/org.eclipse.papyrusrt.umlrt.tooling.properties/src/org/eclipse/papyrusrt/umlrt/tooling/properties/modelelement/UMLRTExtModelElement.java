/*****************************************************************************
 * Copyright (c) 2010, 2015 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Onder GURCAN (CEA LIST) onder.gurcan@cea.fr - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrusrt.umlrt.tooling.properties.modelelement;

import java.util.Hashtable;
import java.util.Iterator;

import org.eclipse.core.databinding.observable.IObservable;
import org.eclipse.emf.databinding.FeaturePath;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.papyrus.infra.widgets.creation.ReferenceValueFactory;
import org.eclipse.papyrus.infra.widgets.providers.IStaticContentProvider;
import org.eclipse.papyrus.uml.properties.modelelement.UMLModelElement;
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.RTMessageKind;
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.RTMessageSet;
import org.eclipse.uml2.uml.Collaboration;
import org.eclipse.uml2.uml.Dependency;
import org.eclipse.uml2.uml.DirectedRelationship;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.util.UMLUtil;

/**
 * A UMLRTModelElement provider. In particular, it will take care of UMLRT protocols which reference provided, required and prov/required interfaces.
 * These can not be specified by means of a property path, since they depend on implemented or used interfaces which are not directly provided.
 * The idea of this class is to delegate to UMLModelElement belonging to these interfaces
 */
public class UMLRTExtModelElement extends UMLModelElement {

	private Hashtable<Element, UMLModelElement> delegationModelElements;

	private final String ownedOp = "ownedOperation"; //$NON-NLS-1$

	public UMLRTExtModelElement(EObject source) {
		super(source, TransactionUtil.getEditingDomain(source));
		delegationModelElements = new Hashtable<Element, UMLModelElement>();
	}

	/**
	 * Get the delegating model element
	 * 
	 * @param element
	 * @return
	 */
	public UMLModelElement getDelegationModelElement(Element element) {
		UMLModelElement delegationModelElement = delegationModelElements.get(element);
		if (delegationModelElement == null) {
			delegationModelElement = new UMLModelElement(element, this.getDomain());
			delegationModelElements.put(element, delegationModelElement);
		}
		return delegationModelElement;
	}

	@Override
	public IStaticContentProvider getContentProvider(String propertyPath) {
		Interface intf = getProvidedOrRequiredInterface(propertyPath);
		if (intf != null) {
			return getDelegationModelElement(intf).getContentProvider(ownedOp);
		}
		return super.getContentProvider(propertyPath);
	}

	@Override
	public boolean isOrdered(String propertyPath) {
		Interface intf = getProvidedOrRequiredInterface(propertyPath);
		if (intf != null) {
			return getDelegationModelElement(intf).isOrdered(ownedOp);
		}
		return super.isOrdered(propertyPath);
	}

	@Override
	public boolean isMandatory(String propertyPath) {
		Interface intf = getProvidedOrRequiredInterface(propertyPath);
		if (intf != null) {
			return getDelegationModelElement(intf).isMandatory(ownedOp);
		}
		return super.isMandatory(propertyPath);
	}

	@Override
	public ILabelProvider getLabelProvider(String propertyPath) {
		Interface intf = getProvidedOrRequiredInterface(propertyPath);
		if (intf != null) {
			return getDelegationModelElement(intf).getLabelProvider(ownedOp);
		}
		return super.getLabelProvider(propertyPath);
	}

	@Override
	public EStructuralFeature getFeature(String propertyPath) {
		Interface intf = getProvidedOrRequiredInterface(propertyPath);
		if (intf != null) {
			return getDelegationModelElement(intf).getFeature(ownedOp);
		}
		return super.getFeature(propertyPath);
	}

	@Override
	public FeaturePath getFeaturePath(String propertyPath) {
		Interface intf = getProvidedOrRequiredInterface(propertyPath);
		if (intf != null) {
			return getDelegationModelElement(intf).getFeaturePath(ownedOp);
		}
		return super.getFeaturePath(propertyPath);
	}

	@Override
	public IObservable doGetObservable(String propertyPath) {
		Interface intf = getProvidedOrRequiredInterface(propertyPath);
		if (intf != null) {
			return getDelegationModelElement(intf).doGetObservable(ownedOp);
		}
		return super.doGetObservable(propertyPath);
	}

	@Override
	public ReferenceValueFactory getValueFactory(String propertyPath) {
		Interface intf = getProvidedOrRequiredInterface(propertyPath);
		if (intf != null) {
			return getDelegationModelElement(intf).getValueFactory(ownedOp);
		}
		return super.getValueFactory(propertyPath);
	}

	/**
	 * return the interface that is required or provides, depending on propertyPath
	 * 
	 * @param propertyPath
	 * @return provided or required interface
	 */
	protected Interface getProvidedOrRequiredInterface(String propertyPath) {
		Interface result = null;
		if (source instanceof Collaboration) {
			if (propertyPath.endsWith("Incoming")) { //$NON-NLS-1$
				result = getInterface(RTMessageKind.IN);
			} else if (propertyPath.endsWith("Outgoing")) { //$NON-NLS-1$
				result = getInterface(RTMessageKind.OUT);
			} else if (propertyPath.endsWith("InOut")) { //$NON-NLS-1$
				result = getInterface(RTMessageKind.IN_OUT);
			}
		}
		return result;
	}

	/**
	 * Get the incmoing interfaces. Don't use getImplementedInterfaces, since it only captures
	 * the interface realization and not the realization relationship.
	 * 
	 * @return list of required interfaces
	 */
	protected Interface getInterface(RTMessageKind rtMessageKind) {
		Interface result = null;
		
		Collaboration protocol = (Collaboration) source;
		Iterator<DirectedRelationship> relationshipIterator = protocol.getSourceDirectedRelationships().iterator();
		while (relationshipIterator.hasNext() && (result == null)) {
			DirectedRelationship directedRelation = relationshipIterator.next();
			if (directedRelation instanceof Dependency) { // Realization or Usage
				Dependency dependency = (Dependency) directedRelation;
				Iterator<NamedElement> dependencyIterator = dependency.getSuppliers().iterator();
				while (dependencyIterator.hasNext() && (result == null)) {
					NamedElement supplier = dependencyIterator.next();
					if (supplier instanceof Interface) {
						Interface interfaceImpl = (Interface) supplier;
						RTMessageSet rtMessageSet = UMLUtil.getStereotypeApplication(interfaceImpl, RTMessageSet.class);
						if (rtMessageSet != null) {
							if (rtMessageSet.getRtMsgKind() == rtMessageKind) {
								result = (Interface) supplier;
							} // if
						} // if
					} // if
				} // while
			} // if
		} // while

		return result;
	}


}
