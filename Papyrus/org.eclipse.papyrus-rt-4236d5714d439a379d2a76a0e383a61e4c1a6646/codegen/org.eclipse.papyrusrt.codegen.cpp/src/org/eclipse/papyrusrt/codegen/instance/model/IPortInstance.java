/*******************************************************************************
 * Copyright (c) 2015 Zeligsoft (2009) Limited and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.papyrusrt.codegen.instance.model;

import org.eclipse.papyrusrt.xtumlrt.common.Port;

public interface IPortInstance
{
    public ICapsuleInstance getContainer();
    public Port getType();
    public Iterable<? extends IPortInstance.IFarEnd> getFarEnds();
    public String getName();
    public boolean isRelay();

    public interface IFarEnd
    {
        public int getIndex();
        public ICapsuleInstance getContainer();
        public Port getType();
        public IPortInstance getOwner();

        public void connectWith( IFarEnd other );
    }
}
