/*******************************************************************************
 * Copyright (c) 2014-2015 Zeligsoft (2009) Limited and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package org.eclipse.papyrusrt.codegen.statemachines.transformations

import org.eclipse.papyrusrt.xtumlrt.common.StateMachine

import org.eclipse.xtend.lib.annotations.Data
import org.eclipse.papyrusrt.xtumlrt.common.State
import java.util.Collection

@Data class TransformationResult
{
    boolean             success
    StateMachine        stateMachine
    Collection<State>   discardedStates
}