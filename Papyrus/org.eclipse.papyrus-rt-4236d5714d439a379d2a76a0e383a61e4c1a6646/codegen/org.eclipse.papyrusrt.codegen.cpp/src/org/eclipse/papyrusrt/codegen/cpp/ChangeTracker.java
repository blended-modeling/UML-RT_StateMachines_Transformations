/*******************************************************************************
* Copyright (c) 2015 Zeligsoft (2009) Limited  and others.
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v10.html
*******************************************************************************/
package org.eclipse.papyrusrt.codegen.cpp;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.papyrus.codegen.base.codesync.ChangeObject;
import org.eclipse.papyrusrt.codegen.cpp.CppCodeGenerator.GeneratorKey;

import org.eclipse.papyrusrt.codegen.cpp.AbstractCppGenerator;

public interface ChangeTracker
{
    public void prune( Map<GeneratorKey, AbstractCppGenerator> generators );
    public void consumeChanges( Map<GeneratorKey, AbstractCppGenerator> generators );
    public void addChanges( List<ChangeObject> changeList );
    public Collection<EObject> getAllChanged();
    public void closeResource( Resource resource );
    public void resetAll();
}
