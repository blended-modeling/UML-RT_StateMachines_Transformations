/*******************************************************************************
* Copyright (c) 2014-2015 Zeligsoft (2009) Limited  and others.
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v10.html
*******************************************************************************/

package org.eclipse.papyrusrt.codegen.statemachines.transformations

import org.eclipse.papyrusrt.xtumlrt.statemach.StateMachine

/**
 * This is the interface for transformations on StateMachines that perform their
 * task in a functional way, i.e., do not modify the input state machine in-place,
 * but create a new one from the given input.
 */
abstract class FunctionalTransformation extends Transformation
{
    abstract def StateMachine transform
    (
        StateMachine stateMachine,
        TransformationContext context
    )
}