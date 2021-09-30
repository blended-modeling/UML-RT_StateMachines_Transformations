/*******************************************************************************
 * Copyright (c) 2014-2015 Zeligsoft (2009) Limited and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.papyrusrt.codegen.lang.cpp.element;

import org.eclipse.papyrusrt.codegen.lang.cpp.Element;
import org.eclipse.papyrusrt.codegen.lang.cpp.HeaderFile;
import org.eclipse.papyrusrt.codegen.lang.cpp.Type;
import org.eclipse.papyrusrt.codegen.lang.cpp.name.FileName;

public abstract class UserElement extends Element
{
    private final Kind kind;
    private final FileName defn;

    public static enum Kind
    {
        DEFAULT,
        HEADER_ONLY,
        IMPL_ONLY;
    }

    // TODO remove this
    protected UserElement() { kind = Kind.DEFAULT; defn = null; }
    protected UserElement( HeaderFile header ) { super( header ); kind = Kind.DEFAULT; defn = null; }
    public UserElement( Kind kind, FileName defn )
    {
        this.kind = kind;
        this.defn = defn;
    }

    @Override public Type getType() { return new Type( this ); }
}
