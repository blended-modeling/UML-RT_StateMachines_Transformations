/*******************************************************************************
 * Copyright (c) 2014-2015 Zeligsoft (2009) Limited and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.papyrusrt.codegen.cpp;

import org.eclipse.papyrusrt.codegen.cpp.AbstractCppGenerator;
import org.eclipse.papyrusrt.codegen.cpp.CppCodePattern;
import org.eclipse.papyrusrt.xtumlrt.common.NamedElement;

public abstract class AbstractCppGenerator
{
    protected final CppCodePattern cpp;

    protected AbstractCppGenerator( CppCodePattern cpp )
    {
        this.cpp = cpp;
    }

    /**
     * Subclasses should override this implement to provide an appropriate label
     * in the codegen results.
     */
    public String getLabel()
    {
        return getClass().getSimpleName();
    }

    public abstract boolean generate();

    public static interface Factory<T extends NamedElement, U extends NamedElement>
    {
        public AbstractCppGenerator create( CppCodePattern cpp, T t, U context );
    }
}
