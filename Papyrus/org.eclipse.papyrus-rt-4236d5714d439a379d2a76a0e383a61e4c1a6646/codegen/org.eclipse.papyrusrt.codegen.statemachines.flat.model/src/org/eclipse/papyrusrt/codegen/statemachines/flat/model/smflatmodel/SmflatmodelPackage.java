/**
 * Copyright (c) 2014-2015 Zeligsoft (2009) Limited  and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 */
package org.eclipse.papyrusrt.codegen.statemachines.flat.model.smflatmodel;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.papyrusrt.xtumlrt.common.CommonPackage;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each operation of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see org.eclipse.papyrusrt.codegen.statemachines.flat.model.smflatmodel.SmflatmodelFactory
 * @model kind="package"
 * @generated
 */
public interface SmflatmodelPackage extends EPackage
{
    /**
     * The package name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String eNAME = "smflatmodel";

    /**
     * The package namespace URI.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String eNS_URI = "http://www.zeligsoft.com/smflatmodel";

    /**
     * The package namespace name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String eNS_PREFIX = "smflatmodel";

    /**
     * The singleton instance of the package.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    SmflatmodelPackage eINSTANCE = org.eclipse.papyrusrt.codegen.statemachines.flat.model.smflatmodel.impl.SmflatmodelPackageImpl.init();

    /**
     * The meta object id for the '{@link org.eclipse.papyrusrt.codegen.statemachines.flat.model.smflatmodel.impl.SaveHistoryImpl <em>Save History</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.papyrusrt.codegen.statemachines.flat.model.smflatmodel.impl.SaveHistoryImpl
     * @see org.eclipse.papyrusrt.codegen.statemachines.flat.model.smflatmodel.impl.SmflatmodelPackageImpl#getSaveHistory()
     * @generated
     */
    int SAVE_HISTORY = 0;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SAVE_HISTORY__NAME = CommonPackage.ACTION_CODE__NAME;

    /**
     * The feature id for the '<em><b>Description</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SAVE_HISTORY__DESCRIPTION = CommonPackage.ACTION_CODE__DESCRIPTION;

    /**
     * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SAVE_HISTORY__ANNOTATIONS = CommonPackage.ACTION_CODE__ANNOTATIONS;

    /**
     * The feature id for the '<em><b>Source</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SAVE_HISTORY__SOURCE = CommonPackage.ACTION_CODE__SOURCE;

    /**
     * The feature id for the '<em><b>Composite State</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SAVE_HISTORY__COMPOSITE_STATE = CommonPackage.ACTION_CODE_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Sub State</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SAVE_HISTORY__SUB_STATE = CommonPackage.ACTION_CODE_FEATURE_COUNT + 1;

    /**
     * The number of structural features of the '<em>Save History</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SAVE_HISTORY_FEATURE_COUNT = CommonPackage.ACTION_CODE_FEATURE_COUNT + 2;

    /**
     * The number of operations of the '<em>Save History</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SAVE_HISTORY_OPERATION_COUNT = CommonPackage.ACTION_CODE_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link org.eclipse.papyrusrt.codegen.statemachines.flat.model.smflatmodel.impl.CheckHistoryImpl <em>Check History</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.papyrusrt.codegen.statemachines.flat.model.smflatmodel.impl.CheckHistoryImpl
     * @see org.eclipse.papyrusrt.codegen.statemachines.flat.model.smflatmodel.impl.SmflatmodelPackageImpl#getCheckHistory()
     * @generated
     */
    int CHECK_HISTORY = 1;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CHECK_HISTORY__NAME = CommonPackage.ACTION_CODE__NAME;

    /**
     * The feature id for the '<em><b>Description</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CHECK_HISTORY__DESCRIPTION = CommonPackage.ACTION_CODE__DESCRIPTION;

    /**
     * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CHECK_HISTORY__ANNOTATIONS = CommonPackage.ACTION_CODE__ANNOTATIONS;

    /**
     * The feature id for the '<em><b>Source</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CHECK_HISTORY__SOURCE = CommonPackage.ACTION_CODE__SOURCE;

    /**
     * The feature id for the '<em><b>Composite State</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CHECK_HISTORY__COMPOSITE_STATE = CommonPackage.ACTION_CODE_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Sub State</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CHECK_HISTORY__SUB_STATE = CommonPackage.ACTION_CODE_FEATURE_COUNT + 1;

    /**
     * The number of structural features of the '<em>Check History</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CHECK_HISTORY_FEATURE_COUNT = CommonPackage.ACTION_CODE_FEATURE_COUNT + 2;

    /**
     * The number of operations of the '<em>Check History</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CHECK_HISTORY_OPERATION_COUNT = CommonPackage.ACTION_CODE_OPERATION_COUNT + 0;


    /**
     * The meta object id for the '{@link org.eclipse.papyrusrt.codegen.statemachines.flat.model.smflatmodel.impl.EntryActionImpl <em>Entry Action</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.papyrusrt.codegen.statemachines.flat.model.smflatmodel.impl.EntryActionImpl
     * @see org.eclipse.papyrusrt.codegen.statemachines.flat.model.smflatmodel.impl.SmflatmodelPackageImpl#getEntryAction()
     * @generated
     */
    int ENTRY_ACTION = 2;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ENTRY_ACTION__NAME = CommonPackage.ACTION_CODE__NAME;

    /**
     * The feature id for the '<em><b>Description</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ENTRY_ACTION__DESCRIPTION = CommonPackage.ACTION_CODE__DESCRIPTION;

    /**
     * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ENTRY_ACTION__ANNOTATIONS = CommonPackage.ACTION_CODE__ANNOTATIONS;

    /**
     * The feature id for the '<em><b>Source</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ENTRY_ACTION__SOURCE = CommonPackage.ACTION_CODE__SOURCE;

    /**
     * The number of structural features of the '<em>Entry Action</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ENTRY_ACTION_FEATURE_COUNT = CommonPackage.ACTION_CODE_FEATURE_COUNT + 0;

    /**
     * The number of operations of the '<em>Entry Action</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ENTRY_ACTION_OPERATION_COUNT = CommonPackage.ACTION_CODE_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link org.eclipse.papyrusrt.codegen.statemachines.flat.model.smflatmodel.impl.ExitActionImpl <em>Exit Action</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.papyrusrt.codegen.statemachines.flat.model.smflatmodel.impl.ExitActionImpl
     * @see org.eclipse.papyrusrt.codegen.statemachines.flat.model.smflatmodel.impl.SmflatmodelPackageImpl#getExitAction()
     * @generated
     */
    int EXIT_ACTION = 3;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int EXIT_ACTION__NAME = CommonPackage.ACTION_CODE__NAME;

    /**
     * The feature id for the '<em><b>Description</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int EXIT_ACTION__DESCRIPTION = CommonPackage.ACTION_CODE__DESCRIPTION;

    /**
     * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int EXIT_ACTION__ANNOTATIONS = CommonPackage.ACTION_CODE__ANNOTATIONS;

    /**
     * The feature id for the '<em><b>Source</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int EXIT_ACTION__SOURCE = CommonPackage.ACTION_CODE__SOURCE;

    /**
     * The number of structural features of the '<em>Exit Action</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int EXIT_ACTION_FEATURE_COUNT = CommonPackage.ACTION_CODE_FEATURE_COUNT + 0;

    /**
     * The number of operations of the '<em>Exit Action</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int EXIT_ACTION_OPERATION_COUNT = CommonPackage.ACTION_CODE_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link org.eclipse.papyrusrt.codegen.statemachines.flat.model.smflatmodel.impl.TransitionActionImpl <em>Transition Action</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.papyrusrt.codegen.statemachines.flat.model.smflatmodel.impl.TransitionActionImpl
     * @see org.eclipse.papyrusrt.codegen.statemachines.flat.model.smflatmodel.impl.SmflatmodelPackageImpl#getTransitionAction()
     * @generated
     */
    int TRANSITION_ACTION = 4;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TRANSITION_ACTION__NAME = CommonPackage.ACTION_CODE__NAME;

    /**
     * The feature id for the '<em><b>Description</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TRANSITION_ACTION__DESCRIPTION = CommonPackage.ACTION_CODE__DESCRIPTION;

    /**
     * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TRANSITION_ACTION__ANNOTATIONS = CommonPackage.ACTION_CODE__ANNOTATIONS;

    /**
     * The feature id for the '<em><b>Source</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TRANSITION_ACTION__SOURCE = CommonPackage.ACTION_CODE__SOURCE;

    /**
     * The number of structural features of the '<em>Transition Action</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TRANSITION_ACTION_FEATURE_COUNT = CommonPackage.ACTION_CODE_FEATURE_COUNT + 0;

    /**
     * The number of operations of the '<em>Transition Action</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TRANSITION_ACTION_OPERATION_COUNT = CommonPackage.ACTION_CODE_OPERATION_COUNT + 0;


    /**
     * Returns the meta object for class '{@link org.eclipse.papyrusrt.codegen.statemachines.flat.model.smflatmodel.SaveHistory <em>Save History</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Save History</em>'.
     * @see org.eclipse.papyrusrt.codegen.statemachines.flat.model.smflatmodel.SaveHistory
     * @generated
     */
    EClass getSaveHistory();

    /**
     * Returns the meta object for the reference '{@link org.eclipse.papyrusrt.codegen.statemachines.flat.model.smflatmodel.SaveHistory#getCompositeState <em>Composite State</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the reference '<em>Composite State</em>'.
     * @see org.eclipse.papyrusrt.codegen.statemachines.flat.model.smflatmodel.SaveHistory#getCompositeState()
     * @see #getSaveHistory()
     * @generated
     */
    EReference getSaveHistory_CompositeState();

    /**
     * Returns the meta object for the reference '{@link org.eclipse.papyrusrt.codegen.statemachines.flat.model.smflatmodel.SaveHistory#getSubState <em>Sub State</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the reference '<em>Sub State</em>'.
     * @see org.eclipse.papyrusrt.codegen.statemachines.flat.model.smflatmodel.SaveHistory#getSubState()
     * @see #getSaveHistory()
     * @generated
     */
    EReference getSaveHistory_SubState();

    /**
     * Returns the meta object for class '{@link org.eclipse.papyrusrt.codegen.statemachines.flat.model.smflatmodel.CheckHistory <em>Check History</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Check History</em>'.
     * @see org.eclipse.papyrusrt.codegen.statemachines.flat.model.smflatmodel.CheckHistory
     * @generated
     */
    EClass getCheckHistory();

    /**
     * Returns the meta object for the reference '{@link org.eclipse.papyrusrt.codegen.statemachines.flat.model.smflatmodel.CheckHistory#getCompositeState <em>Composite State</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the reference '<em>Composite State</em>'.
     * @see org.eclipse.papyrusrt.codegen.statemachines.flat.model.smflatmodel.CheckHistory#getCompositeState()
     * @see #getCheckHistory()
     * @generated
     */
    EReference getCheckHistory_CompositeState();

    /**
     * Returns the meta object for the reference '{@link org.eclipse.papyrusrt.codegen.statemachines.flat.model.smflatmodel.CheckHistory#getSubState <em>Sub State</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the reference '<em>Sub State</em>'.
     * @see org.eclipse.papyrusrt.codegen.statemachines.flat.model.smflatmodel.CheckHistory#getSubState()
     * @see #getCheckHistory()
     * @generated
     */
    EReference getCheckHistory_SubState();

    /**
     * Returns the meta object for class '{@link org.eclipse.papyrusrt.codegen.statemachines.flat.model.smflatmodel.EntryAction <em>Entry Action</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Entry Action</em>'.
     * @see org.eclipse.papyrusrt.codegen.statemachines.flat.model.smflatmodel.EntryAction
     * @generated
     */
    EClass getEntryAction();

    /**
     * Returns the meta object for class '{@link org.eclipse.papyrusrt.codegen.statemachines.flat.model.smflatmodel.ExitAction <em>Exit Action</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Exit Action</em>'.
     * @see org.eclipse.papyrusrt.codegen.statemachines.flat.model.smflatmodel.ExitAction
     * @generated
     */
    EClass getExitAction();

    /**
     * Returns the meta object for class '{@link org.eclipse.papyrusrt.codegen.statemachines.flat.model.smflatmodel.TransitionAction <em>Transition Action</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Transition Action</em>'.
     * @see org.eclipse.papyrusrt.codegen.statemachines.flat.model.smflatmodel.TransitionAction
     * @generated
     */
    EClass getTransitionAction();

    /**
     * Returns the factory that creates the instances of the model.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the factory that creates the instances of the model.
     * @generated
     */
    SmflatmodelFactory getSmflatmodelFactory();

    /**
     * <!-- begin-user-doc -->
     * Defines literals for the meta objects that represent
     * <ul>
     *   <li>each class,</li>
     *   <li>each feature of each class,</li>
     *   <li>each operation of each class,</li>
     *   <li>each enum,</li>
     *   <li>and each data type</li>
     * </ul>
     * <!-- end-user-doc -->
     * @generated
     */
    interface Literals
    {
        /**
         * The meta object literal for the '{@link org.eclipse.papyrusrt.codegen.statemachines.flat.model.smflatmodel.impl.SaveHistoryImpl <em>Save History</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see org.eclipse.papyrusrt.codegen.statemachines.flat.model.smflatmodel.impl.SaveHistoryImpl
         * @see org.eclipse.papyrusrt.codegen.statemachines.flat.model.smflatmodel.impl.SmflatmodelPackageImpl#getSaveHistory()
         * @generated
         */
        EClass SAVE_HISTORY = eINSTANCE.getSaveHistory();

        /**
         * The meta object literal for the '<em><b>Composite State</b></em>' reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference SAVE_HISTORY__COMPOSITE_STATE = eINSTANCE.getSaveHistory_CompositeState();

        /**
         * The meta object literal for the '<em><b>Sub State</b></em>' reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference SAVE_HISTORY__SUB_STATE = eINSTANCE.getSaveHistory_SubState();

        /**
         * The meta object literal for the '{@link org.eclipse.papyrusrt.codegen.statemachines.flat.model.smflatmodel.impl.CheckHistoryImpl <em>Check History</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see org.eclipse.papyrusrt.codegen.statemachines.flat.model.smflatmodel.impl.CheckHistoryImpl
         * @see org.eclipse.papyrusrt.codegen.statemachines.flat.model.smflatmodel.impl.SmflatmodelPackageImpl#getCheckHistory()
         * @generated
         */
        EClass CHECK_HISTORY = eINSTANCE.getCheckHistory();

        /**
         * The meta object literal for the '<em><b>Composite State</b></em>' reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference CHECK_HISTORY__COMPOSITE_STATE = eINSTANCE.getCheckHistory_CompositeState();

        /**
         * The meta object literal for the '<em><b>Sub State</b></em>' reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference CHECK_HISTORY__SUB_STATE = eINSTANCE.getCheckHistory_SubState();

        /**
         * The meta object literal for the '{@link org.eclipse.papyrusrt.codegen.statemachines.flat.model.smflatmodel.impl.EntryActionImpl <em>Entry Action</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see org.eclipse.papyrusrt.codegen.statemachines.flat.model.smflatmodel.impl.EntryActionImpl
         * @see org.eclipse.papyrusrt.codegen.statemachines.flat.model.smflatmodel.impl.SmflatmodelPackageImpl#getEntryAction()
         * @generated
         */
        EClass ENTRY_ACTION = eINSTANCE.getEntryAction();

        /**
         * The meta object literal for the '{@link org.eclipse.papyrusrt.codegen.statemachines.flat.model.smflatmodel.impl.ExitActionImpl <em>Exit Action</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see org.eclipse.papyrusrt.codegen.statemachines.flat.model.smflatmodel.impl.ExitActionImpl
         * @see org.eclipse.papyrusrt.codegen.statemachines.flat.model.smflatmodel.impl.SmflatmodelPackageImpl#getExitAction()
         * @generated
         */
        EClass EXIT_ACTION = eINSTANCE.getExitAction();

        /**
         * The meta object literal for the '{@link org.eclipse.papyrusrt.codegen.statemachines.flat.model.smflatmodel.impl.TransitionActionImpl <em>Transition Action</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see org.eclipse.papyrusrt.codegen.statemachines.flat.model.smflatmodel.impl.TransitionActionImpl
         * @see org.eclipse.papyrusrt.codegen.statemachines.flat.model.smflatmodel.impl.SmflatmodelPackageImpl#getTransitionAction()
         * @generated
         */
        EClass TRANSITION_ACTION = eINSTANCE.getTransitionAction();

    }

} //SmflatmodelPackage
