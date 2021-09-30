/*******************************************************************************
 * Copyright (c) 2015 Zeligsoft (2009) Limited and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.papyrusrt.codegen.instance.model;

import java.util.List;

import org.eclipse.papyrusrt.xtumlrt.common.Capsule;
import org.eclipse.papyrusrt.xtumlrt.common.CapsulePart;
import org.eclipse.papyrusrt.xtumlrt.common.Port;

public interface ICapsuleInstance
{
    public Capsule getType();
    public CapsulePart getCapsulePart();
    public ICapsuleInstance getContainer();
    public int getIndex();
    public String getQualifiedName( char sep );
    public List<IPortInstance> getPorts();
    public IPortInstance getPort( Port port );

    public boolean isDynamic();
    public List<ICapsuleInstance> getContained();
    public List<? extends ICapsuleInstance> getContained( CapsulePart part );
}
