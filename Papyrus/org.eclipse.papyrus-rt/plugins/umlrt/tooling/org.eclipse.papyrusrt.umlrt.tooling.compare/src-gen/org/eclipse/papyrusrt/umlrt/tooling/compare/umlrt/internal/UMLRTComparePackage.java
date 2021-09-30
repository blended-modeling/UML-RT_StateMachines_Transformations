/**
 * Copyright (c) 2016 EclipseSource Services GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Philip Langer (EclipseSource) - Initial API and implementation
 */
package org.eclipse.papyrusrt.umlrt.tooling.compare.umlrt.internal;

import org.eclipse.emf.compare.diagram.internal.extensions.ExtensionsPackage;
import org.eclipse.emf.compare.uml2.internal.UMLComparePackage;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;

/**
 * <!-- begin-user-doc --> The <b>Package</b> for the model. It contains
 * accessors for the meta objects to represent
 * <ul>
 * <li>each class,</li>
 * <li>each feature of each class,</li>
 * <li>each enum,</li>
 * <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see org.eclipse.papyrusrt.umlrt.tooling.compare.umlrt.internal.UMLRTCompareFactory
 * @model kind="package"
 * @generated
 */
public interface UMLRTComparePackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "umlrt"; //$NON-NLS-1$

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.eclipse.org/papyrus/umlrt/compare/1.0"; //$NON-NLS-1$

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "umlrt"; //$NON-NLS-1$

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @generated
	 */
	UMLRTComparePackage eINSTANCE = org.eclipse.papyrusrt.umlrt.tooling.compare.umlrt.internal.impl.UMLRTComparePackageImpl.init();

	/**
	 * The meta object id for the '
	 * {@link org.eclipse.papyrusrt.umlrt.tooling.compare.umlrt.internal.impl.UMLRTDiffImpl
	 * <em>UMLRT Diff</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @see org.eclipse.papyrusrt.umlrt.tooling.compare.umlrt.internal.impl.UMLRTDiffImpl
	 * @see org.eclipse.papyrusrt.umlrt.tooling.compare.umlrt.internal.impl.UMLRTComparePackageImpl#getUMLRTDiff()
	 * @generated
	 */
	int UMLRT_DIFF = 0;

	/**
	 * The feature id for the '<em><b>Match</b></em>' container reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int UMLRT_DIFF__MATCH = UMLComparePackage.UML_DIFF__MATCH;

	/**
	 * The feature id for the '<em><b>Requires</b></em>' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int UMLRT_DIFF__REQUIRES = UMLComparePackage.UML_DIFF__REQUIRES;

	/**
	 * The feature id for the '<em><b>Required By</b></em>' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int UMLRT_DIFF__REQUIRED_BY = UMLComparePackage.UML_DIFF__REQUIRED_BY;

	/**
	 * The feature id for the '<em><b>Implies</b></em>' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int UMLRT_DIFF__IMPLIES = UMLComparePackage.UML_DIFF__IMPLIES;

	/**
	 * The feature id for the '<em><b>Implied By</b></em>' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int UMLRT_DIFF__IMPLIED_BY = UMLComparePackage.UML_DIFF__IMPLIED_BY;

	/**
	 * The feature id for the '<em><b>Refines</b></em>' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int UMLRT_DIFF__REFINES = UMLComparePackage.UML_DIFF__REFINES;

	/**
	 * The feature id for the '<em><b>Refined By</b></em>' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int UMLRT_DIFF__REFINED_BY = UMLComparePackage.UML_DIFF__REFINED_BY;

	/**
	 * The feature id for the '<em><b>Prime Refining</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int UMLRT_DIFF__PRIME_REFINING = UMLComparePackage.UML_DIFF__PRIME_REFINING;

	/**
	 * The feature id for the '<em><b>Kind</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int UMLRT_DIFF__KIND = UMLComparePackage.UML_DIFF__KIND;

	/**
	 * The feature id for the '<em><b>Source</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int UMLRT_DIFF__SOURCE = UMLComparePackage.UML_DIFF__SOURCE;

	/**
	 * The feature id for the '<em><b>State</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int UMLRT_DIFF__STATE = UMLComparePackage.UML_DIFF__STATE;

	/**
	 * The feature id for the '<em><b>Equivalence</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int UMLRT_DIFF__EQUIVALENCE = UMLComparePackage.UML_DIFF__EQUIVALENCE;

	/**
	 * The feature id for the '<em><b>Conflict</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int UMLRT_DIFF__CONFLICT = UMLComparePackage.UML_DIFF__CONFLICT;

	/**
	 * The feature id for the '<em><b>Discriminant</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int UMLRT_DIFF__DISCRIMINANT = UMLComparePackage.UML_DIFF__DISCRIMINANT;

	/**
	 * The feature id for the '<em><b>EReference</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int UMLRT_DIFF__EREFERENCE = UMLComparePackage.UML_DIFF__EREFERENCE;

	/**
	 * The number of structural features of the '<em>UMLRT Diff</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UMLRT_DIFF_FEATURE_COUNT = UMLComparePackage.UML_DIFF_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrusrt.umlrt.tooling.compare.umlrt.internal.impl.ProtocolChangeImpl <em>Protocol Change</em>}' class.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @see org.eclipse.papyrusrt.umlrt.tooling.compare.umlrt.internal.impl.ProtocolChangeImpl
	 * @see org.eclipse.papyrusrt.umlrt.tooling.compare.umlrt.internal.impl.UMLRTComparePackageImpl#getProtocolChange()
	 * @generated
	 */
	int PROTOCOL_CHANGE = 1;

	/**
	 * The feature id for the '<em><b>Match</b></em>' container reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PROTOCOL_CHANGE__MATCH = UMLRT_DIFF__MATCH;

	/**
	 * The feature id for the '<em><b>Requires</b></em>' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PROTOCOL_CHANGE__REQUIRES = UMLRT_DIFF__REQUIRES;

	/**
	 * The feature id for the '<em><b>Required By</b></em>' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PROTOCOL_CHANGE__REQUIRED_BY = UMLRT_DIFF__REQUIRED_BY;

	/**
	 * The feature id for the '<em><b>Implies</b></em>' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PROTOCOL_CHANGE__IMPLIES = UMLRT_DIFF__IMPLIES;

	/**
	 * The feature id for the '<em><b>Implied By</b></em>' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PROTOCOL_CHANGE__IMPLIED_BY = UMLRT_DIFF__IMPLIED_BY;

	/**
	 * The feature id for the '<em><b>Refines</b></em>' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PROTOCOL_CHANGE__REFINES = UMLRT_DIFF__REFINES;

	/**
	 * The feature id for the '<em><b>Refined By</b></em>' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PROTOCOL_CHANGE__REFINED_BY = UMLRT_DIFF__REFINED_BY;

	/**
	 * The feature id for the '<em><b>Prime Refining</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PROTOCOL_CHANGE__PRIME_REFINING = UMLRT_DIFF__PRIME_REFINING;

	/**
	 * The feature id for the '<em><b>Kind</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PROTOCOL_CHANGE__KIND = UMLRT_DIFF__KIND;

	/**
	 * The feature id for the '<em><b>Source</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PROTOCOL_CHANGE__SOURCE = UMLRT_DIFF__SOURCE;

	/**
	 * The feature id for the '<em><b>State</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PROTOCOL_CHANGE__STATE = UMLRT_DIFF__STATE;

	/**
	 * The feature id for the '<em><b>Equivalence</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PROTOCOL_CHANGE__EQUIVALENCE = UMLRT_DIFF__EQUIVALENCE;

	/**
	 * The feature id for the '<em><b>Conflict</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PROTOCOL_CHANGE__CONFLICT = UMLRT_DIFF__CONFLICT;

	/**
	 * The feature id for the '<em><b>Discriminant</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PROTOCOL_CHANGE__DISCRIMINANT = UMLRT_DIFF__DISCRIMINANT;

	/**
	 * The feature id for the '<em><b>EReference</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PROTOCOL_CHANGE__EREFERENCE = UMLRT_DIFF__EREFERENCE;

	/**
	 * The number of structural features of the '<em>Protocol Change</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROTOCOL_CHANGE_FEATURE_COUNT = UMLRT_DIFF_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrusrt.umlrt.tooling.compare.umlrt.internal.impl.ProtocolMessageChangeImpl <em>Protocol Message Change</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrusrt.umlrt.tooling.compare.umlrt.internal.impl.ProtocolMessageChangeImpl
	 * @see org.eclipse.papyrusrt.umlrt.tooling.compare.umlrt.internal.impl.UMLRTComparePackageImpl#getProtocolMessageChange()
	 * @generated
	 */
	int PROTOCOL_MESSAGE_CHANGE = 2;

	/**
	 * The feature id for the '<em><b>Match</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROTOCOL_MESSAGE_CHANGE__MATCH = UMLRT_DIFF__MATCH;

	/**
	 * The feature id for the '<em><b>Requires</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROTOCOL_MESSAGE_CHANGE__REQUIRES = UMLRT_DIFF__REQUIRES;

	/**
	 * The feature id for the '<em><b>Required By</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROTOCOL_MESSAGE_CHANGE__REQUIRED_BY = UMLRT_DIFF__REQUIRED_BY;

	/**
	 * The feature id for the '<em><b>Implies</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROTOCOL_MESSAGE_CHANGE__IMPLIES = UMLRT_DIFF__IMPLIES;

	/**
	 * The feature id for the '<em><b>Implied By</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROTOCOL_MESSAGE_CHANGE__IMPLIED_BY = UMLRT_DIFF__IMPLIED_BY;

	/**
	 * The feature id for the '<em><b>Refines</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROTOCOL_MESSAGE_CHANGE__REFINES = UMLRT_DIFF__REFINES;

	/**
	 * The feature id for the '<em><b>Refined By</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROTOCOL_MESSAGE_CHANGE__REFINED_BY = UMLRT_DIFF__REFINED_BY;

	/**
	 * The feature id for the '<em><b>Prime Refining</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROTOCOL_MESSAGE_CHANGE__PRIME_REFINING = UMLRT_DIFF__PRIME_REFINING;

	/**
	 * The feature id for the '<em><b>Kind</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROTOCOL_MESSAGE_CHANGE__KIND = UMLRT_DIFF__KIND;

	/**
	 * The feature id for the '<em><b>Source</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROTOCOL_MESSAGE_CHANGE__SOURCE = UMLRT_DIFF__SOURCE;

	/**
	 * The feature id for the '<em><b>State</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROTOCOL_MESSAGE_CHANGE__STATE = UMLRT_DIFF__STATE;

	/**
	 * The feature id for the '<em><b>Equivalence</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROTOCOL_MESSAGE_CHANGE__EQUIVALENCE = UMLRT_DIFF__EQUIVALENCE;

	/**
	 * The feature id for the '<em><b>Conflict</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROTOCOL_MESSAGE_CHANGE__CONFLICT = UMLRT_DIFF__CONFLICT;

	/**
	 * The feature id for the '<em><b>Discriminant</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROTOCOL_MESSAGE_CHANGE__DISCRIMINANT = UMLRT_DIFF__DISCRIMINANT;

	/**
	 * The feature id for the '<em><b>EReference</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROTOCOL_MESSAGE_CHANGE__EREFERENCE = UMLRT_DIFF__EREFERENCE;

	/**
	 * The number of structural features of the '<em>Protocol Message Change</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROTOCOL_MESSAGE_CHANGE_FEATURE_COUNT = UMLRT_DIFF_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrusrt.umlrt.tooling.compare.umlrt.internal.impl.ProtocolMessageParameterChangeImpl <em>Protocol Message Parameter Change</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrusrt.umlrt.tooling.compare.umlrt.internal.impl.ProtocolMessageParameterChangeImpl
	 * @see org.eclipse.papyrusrt.umlrt.tooling.compare.umlrt.internal.impl.UMLRTComparePackageImpl#getProtocolMessageParameterChange()
	 * @generated
	 */
	int PROTOCOL_MESSAGE_PARAMETER_CHANGE = 3;

	/**
	 * The feature id for the '<em><b>Match</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROTOCOL_MESSAGE_PARAMETER_CHANGE__MATCH = UMLRT_DIFF__MATCH;

	/**
	 * The feature id for the '<em><b>Requires</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROTOCOL_MESSAGE_PARAMETER_CHANGE__REQUIRES = UMLRT_DIFF__REQUIRES;

	/**
	 * The feature id for the '<em><b>Required By</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROTOCOL_MESSAGE_PARAMETER_CHANGE__REQUIRED_BY = UMLRT_DIFF__REQUIRED_BY;

	/**
	 * The feature id for the '<em><b>Implies</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROTOCOL_MESSAGE_PARAMETER_CHANGE__IMPLIES = UMLRT_DIFF__IMPLIES;

	/**
	 * The feature id for the '<em><b>Implied By</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROTOCOL_MESSAGE_PARAMETER_CHANGE__IMPLIED_BY = UMLRT_DIFF__IMPLIED_BY;

	/**
	 * The feature id for the '<em><b>Refines</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROTOCOL_MESSAGE_PARAMETER_CHANGE__REFINES = UMLRT_DIFF__REFINES;

	/**
	 * The feature id for the '<em><b>Refined By</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROTOCOL_MESSAGE_PARAMETER_CHANGE__REFINED_BY = UMLRT_DIFF__REFINED_BY;

	/**
	 * The feature id for the '<em><b>Prime Refining</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROTOCOL_MESSAGE_PARAMETER_CHANGE__PRIME_REFINING = UMLRT_DIFF__PRIME_REFINING;

	/**
	 * The feature id for the '<em><b>Kind</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROTOCOL_MESSAGE_PARAMETER_CHANGE__KIND = UMLRT_DIFF__KIND;

	/**
	 * The feature id for the '<em><b>Source</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROTOCOL_MESSAGE_PARAMETER_CHANGE__SOURCE = UMLRT_DIFF__SOURCE;

	/**
	 * The feature id for the '<em><b>State</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROTOCOL_MESSAGE_PARAMETER_CHANGE__STATE = UMLRT_DIFF__STATE;

	/**
	 * The feature id for the '<em><b>Equivalence</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROTOCOL_MESSAGE_PARAMETER_CHANGE__EQUIVALENCE = UMLRT_DIFF__EQUIVALENCE;

	/**
	 * The feature id for the '<em><b>Conflict</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROTOCOL_MESSAGE_PARAMETER_CHANGE__CONFLICT = UMLRT_DIFF__CONFLICT;

	/**
	 * The feature id for the '<em><b>Discriminant</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROTOCOL_MESSAGE_PARAMETER_CHANGE__DISCRIMINANT = UMLRT_DIFF__DISCRIMINANT;

	/**
	 * The feature id for the '<em><b>EReference</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROTOCOL_MESSAGE_PARAMETER_CHANGE__EREFERENCE = UMLRT_DIFF__EREFERENCE;

	/**
	 * The number of structural features of the '<em>Protocol Message Parameter Change</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROTOCOL_MESSAGE_PARAMETER_CHANGE_FEATURE_COUNT = UMLRT_DIFF_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrusrt.umlrt.tooling.compare.umlrt.internal.impl.UMLRTDiagramChangeImpl <em>UMLRT Diagram Change</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrusrt.umlrt.tooling.compare.umlrt.internal.impl.UMLRTDiagramChangeImpl
	 * @see org.eclipse.papyrusrt.umlrt.tooling.compare.umlrt.internal.impl.UMLRTComparePackageImpl#getUMLRTDiagramChange()
	 * @generated
	 */
	int UMLRT_DIAGRAM_CHANGE = 4;

	/**
	 * The feature id for the '<em><b>Match</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UMLRT_DIAGRAM_CHANGE__MATCH = ExtensionsPackage.DIAGRAM_CHANGE__MATCH;

	/**
	 * The feature id for the '<em><b>Requires</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UMLRT_DIAGRAM_CHANGE__REQUIRES = ExtensionsPackage.DIAGRAM_CHANGE__REQUIRES;

	/**
	 * The feature id for the '<em><b>Required By</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UMLRT_DIAGRAM_CHANGE__REQUIRED_BY = ExtensionsPackage.DIAGRAM_CHANGE__REQUIRED_BY;

	/**
	 * The feature id for the '<em><b>Implies</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UMLRT_DIAGRAM_CHANGE__IMPLIES = ExtensionsPackage.DIAGRAM_CHANGE__IMPLIES;

	/**
	 * The feature id for the '<em><b>Implied By</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UMLRT_DIAGRAM_CHANGE__IMPLIED_BY = ExtensionsPackage.DIAGRAM_CHANGE__IMPLIED_BY;

	/**
	 * The feature id for the '<em><b>Refines</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UMLRT_DIAGRAM_CHANGE__REFINES = ExtensionsPackage.DIAGRAM_CHANGE__REFINES;

	/**
	 * The feature id for the '<em><b>Refined By</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UMLRT_DIAGRAM_CHANGE__REFINED_BY = ExtensionsPackage.DIAGRAM_CHANGE__REFINED_BY;

	/**
	 * The feature id for the '<em><b>Prime Refining</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UMLRT_DIAGRAM_CHANGE__PRIME_REFINING = ExtensionsPackage.DIAGRAM_CHANGE__PRIME_REFINING;

	/**
	 * The feature id for the '<em><b>Kind</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UMLRT_DIAGRAM_CHANGE__KIND = ExtensionsPackage.DIAGRAM_CHANGE__KIND;

	/**
	 * The feature id for the '<em><b>Source</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UMLRT_DIAGRAM_CHANGE__SOURCE = ExtensionsPackage.DIAGRAM_CHANGE__SOURCE;

	/**
	 * The feature id for the '<em><b>State</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UMLRT_DIAGRAM_CHANGE__STATE = ExtensionsPackage.DIAGRAM_CHANGE__STATE;

	/**
	 * The feature id for the '<em><b>Equivalence</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UMLRT_DIAGRAM_CHANGE__EQUIVALENCE = ExtensionsPackage.DIAGRAM_CHANGE__EQUIVALENCE;

	/**
	 * The feature id for the '<em><b>Conflict</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UMLRT_DIAGRAM_CHANGE__CONFLICT = ExtensionsPackage.DIAGRAM_CHANGE__CONFLICT;

	/**
	 * The feature id for the '<em><b>Semantic Diff</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UMLRT_DIAGRAM_CHANGE__SEMANTIC_DIFF = ExtensionsPackage.DIAGRAM_CHANGE__SEMANTIC_DIFF;

	/**
	 * The feature id for the '<em><b>View</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UMLRT_DIAGRAM_CHANGE__VIEW = ExtensionsPackage.DIAGRAM_CHANGE__VIEW;

	/**
	 * The number of structural features of the '<em>UMLRT Diagram Change</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UMLRT_DIAGRAM_CHANGE_FEATURE_COUNT = ExtensionsPackage.DIAGRAM_CHANGE_FEATURE_COUNT + 0;

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrusrt.umlrt.tooling.compare.umlrt.internal.UMLRTDiff <em>UMLRT Diff</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>UMLRT Diff</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.tooling.compare.umlrt.internal.UMLRTDiff
	 * @generated
	 */
	EClass getUMLRTDiff();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrusrt.umlrt.tooling.compare.umlrt.internal.ProtocolChange <em>Protocol Change</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Protocol Change</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.tooling.compare.umlrt.internal.ProtocolChange
	 * @generated
	 */
	EClass getProtocolChange();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrusrt.umlrt.tooling.compare.umlrt.internal.ProtocolMessageChange <em>Protocol Message Change</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Protocol Message Change</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.tooling.compare.umlrt.internal.ProtocolMessageChange
	 * @generated
	 */
	EClass getProtocolMessageChange();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrusrt.umlrt.tooling.compare.umlrt.internal.ProtocolMessageParameterChange <em>Protocol Message Parameter Change</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Protocol Message Parameter Change</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.tooling.compare.umlrt.internal.ProtocolMessageParameterChange
	 * @generated
	 */
	EClass getProtocolMessageParameterChange();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrusrt.umlrt.tooling.compare.umlrt.internal.UMLRTDiagramChange <em>UMLRT Diagram Change</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>UMLRT Diagram Change</em>'.
	 * @see org.eclipse.papyrusrt.umlrt.tooling.compare.umlrt.internal.UMLRTDiagramChange
	 * @generated
	 */
	EClass getUMLRTDiagramChange();

	/**
	 * Returns the factory that creates the instances of the model. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	UMLRTCompareFactory getUMLRTCompareFactory();

	/**
	 * <!-- begin-user-doc --> Defines literals for the meta objects that
	 * represent
	 * <ul>
	 * <li>each class,</li>
	 * <li>each feature of each class,</li>
	 * <li>each enum,</li>
	 * <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link org.eclipse.papyrusrt.umlrt.tooling.compare.umlrt.internal.impl.UMLRTDiffImpl <em>UMLRT Diff</em>}' class.
		 * <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * @see org.eclipse.papyrusrt.umlrt.tooling.compare.umlrt.internal.impl.UMLRTDiffImpl
		 * @see org.eclipse.papyrusrt.umlrt.tooling.compare.umlrt.internal.impl.UMLRTComparePackageImpl#getUMLRTDiff()
		 * @generated
		 */
		EClass UMLRT_DIFF = eINSTANCE.getUMLRTDiff();
		/**
		 * The meta object literal for the '{@link org.eclipse.papyrusrt.umlrt.tooling.compare.umlrt.internal.impl.ProtocolChangeImpl <em>Protocol Change</em>}' class.
		 * <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * @see org.eclipse.papyrusrt.umlrt.tooling.compare.umlrt.internal.impl.ProtocolChangeImpl
		 * @see org.eclipse.papyrusrt.umlrt.tooling.compare.umlrt.internal.impl.UMLRTComparePackageImpl#getProtocolChange()
		 * @generated
		 */
		EClass PROTOCOL_CHANGE = eINSTANCE.getProtocolChange();
		/**
		 * The meta object literal for the '{@link org.eclipse.papyrusrt.umlrt.tooling.compare.umlrt.internal.impl.ProtocolMessageChangeImpl <em>Protocol Message Change</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrusrt.umlrt.tooling.compare.umlrt.internal.impl.ProtocolMessageChangeImpl
		 * @see org.eclipse.papyrusrt.umlrt.tooling.compare.umlrt.internal.impl.UMLRTComparePackageImpl#getProtocolMessageChange()
		 * @generated
		 */
		EClass PROTOCOL_MESSAGE_CHANGE = eINSTANCE.getProtocolMessageChange();
		/**
		 * The meta object literal for the '{@link org.eclipse.papyrusrt.umlrt.tooling.compare.umlrt.internal.impl.ProtocolMessageParameterChangeImpl <em>Protocol Message Parameter Change</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrusrt.umlrt.tooling.compare.umlrt.internal.impl.ProtocolMessageParameterChangeImpl
		 * @see org.eclipse.papyrusrt.umlrt.tooling.compare.umlrt.internal.impl.UMLRTComparePackageImpl#getProtocolMessageParameterChange()
		 * @generated
		 */
		EClass PROTOCOL_MESSAGE_PARAMETER_CHANGE = eINSTANCE.getProtocolMessageParameterChange();
		/**
		 * The meta object literal for the '{@link org.eclipse.papyrusrt.umlrt.tooling.compare.umlrt.internal.impl.UMLRTDiagramChangeImpl <em>UMLRT Diagram Change</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrusrt.umlrt.tooling.compare.umlrt.internal.impl.UMLRTDiagramChangeImpl
		 * @see org.eclipse.papyrusrt.umlrt.tooling.compare.umlrt.internal.impl.UMLRTComparePackageImpl#getUMLRTDiagramChange()
		 * @generated
		 */
		EClass UMLRT_DIAGRAM_CHANGE = eINSTANCE.getUMLRTDiagramChange();

	}

} // UMLRTComparePackage
