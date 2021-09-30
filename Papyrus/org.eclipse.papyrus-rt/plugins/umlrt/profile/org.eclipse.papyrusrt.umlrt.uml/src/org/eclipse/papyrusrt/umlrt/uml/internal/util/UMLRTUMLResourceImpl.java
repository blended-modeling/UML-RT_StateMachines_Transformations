/*****************************************************************************
 * Copyright (c) 2016 Christian W. Damus and others.
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

package org.eclipse.papyrusrt.umlrt.uml.internal.util;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.ExtendedMetaData;
import org.eclipse.emf.ecore.xmi.XMLLoad;
import org.eclipse.uml2.uml.internal.resource.UMLHandler;
import org.eclipse.uml2.uml.internal.resource.UMLLoadImpl;
import org.eclipse.uml2.uml.internal.resource.UMLResourceImpl;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Custom UML resource that ensures usage of {@link EFactory} instances
 * registered locally in the resource set when instantiating elements
 * from the feature type in case that that XMI Type is implied.
 */
class UMLRTUMLResourceImpl extends UMLResourceImpl {
	UMLRTUMLResourceImpl(URI uri) {
		super(uri);

		InheritanceAdapter.getInstance(this).adapt(this);
	}

	@Override
	protected XMLLoad createXMLLoad() {
		return new UMLLoadImpl(createXMLHelper()) {
			@Override
			protected DefaultHandler makeDefaultHandler() {
				return new UMLHandler(resource, helper, options) {
					@Override
					protected EObject createObjectFromFeatureType(EObject peekObject, EStructuralFeature feature) {
						String typeName = null;
						EFactory factory = null;
						EClassifier eType = (feature == null) ? null : feature.getEType();
						EObject obj = null;

						if (eType != null) {
							boolean isUnspecifiedFeature = (extendedMetaData != null)
									&& (eType == EcorePackage.Literals.EOBJECT)
									&& (extendedMetaData.getFeatureKind(feature) != ExtendedMetaData.UNSPECIFIED_FEATURE);

							if (useNewMethods) {
								if (isUnspecifiedFeature) {
									eType = anyType;
									typeName = extendedMetaData.getName(anyType);
									factory = getEFactory(anyType);
								} else {
									factory = getEFactory(eType);
									typeName = (extendedMetaData == null) ? eType.getName() : extendedMetaData.getName(eType);
								}

								obj = createObject(factory, eType, false);
							} else {
								if (isUnspecifiedFeature) {
									typeName = extendedMetaData.getName(anyType);
									factory = getEFactory(anyType);
								} else {
									EClass eClass = (EClass) eType;
									typeName = extendedMetaData == null ? eClass.getName() : extendedMetaData.getName(eClass);
									factory = getEFactory(eClass);
								}
								obj = createObjectFromFactory(factory, typeName);
							}
						}

						obj = validateCreateObjectFromFactory(factory, typeName, obj, feature);

						if (obj != null) {
							setFeatureValue(peekObject, feature, obj);
						}

						processObject(obj);
						return obj;
					}
				};
			}
		};
	}

	EFactory getEFactory(EClassifier eClassifier) {
		EFactory result;

		ResourceSet rset = getResourceSet();
		if (rset != null) {
			EPackage ePackage = eClassifier.getEPackage();
			result = rset.getPackageRegistry().getEFactory(ePackage.getNsURI());
			if (result == null) {
				result = ePackage.getEFactoryInstance();
			}
		} else {
			result = eClassifier.getEPackage().getEFactoryInstance();
		}

		return result;
	}
}
