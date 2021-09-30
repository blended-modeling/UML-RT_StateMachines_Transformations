/*******************************************************************************
 * Copyright (c) 2014-2015 Zeligsoft (2009) Limited and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.papyrusrt.codegen.cpp.structure.model;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.papyrusrt.codegen.instance.model.ICapsuleInstance;

public class Controller
{
    private final String name;
    private final List<ICapsuleInstance> capsules = new ArrayList<ICapsuleInstance>();

    public Controller( String name )
    {
        this.name = name;
    }

    public void add( ICapsuleInstance capsule ) { capsules.add( capsule ); }

    public String getName() { return name; }
    public Iterable<ICapsuleInstance> getCapsules() { return capsules; }
    public int getNumCapsules() { return capsules.size(); }
}
