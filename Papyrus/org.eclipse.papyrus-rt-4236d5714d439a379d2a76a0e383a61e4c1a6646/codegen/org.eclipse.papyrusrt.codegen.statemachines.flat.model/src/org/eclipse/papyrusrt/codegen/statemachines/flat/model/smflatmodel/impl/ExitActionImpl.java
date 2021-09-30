/**
 * Copyright (c) 2014-2015 Zeligsoft (2009) Limited  and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 */
package org.eclipse.papyrusrt.codegen.statemachines.flat.model.smflatmodel.impl;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.papyrusrt.codegen.statemachines.flat.model.smflatmodel.ExitAction;
import org.eclipse.papyrusrt.codegen.statemachines.flat.model.smflatmodel.SmflatmodelPackage;

import org.eclipse.papyrusrt.xtumlrt.common.impl.ActionCodeImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Exit Action</b></em>'.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class ExitActionImpl extends ActionCodeImpl implements ExitAction
{
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected ExitActionImpl()
    {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass()
    {
        return SmflatmodelPackage.Literals.EXIT_ACTION;
    }

} //ExitActionImpl
