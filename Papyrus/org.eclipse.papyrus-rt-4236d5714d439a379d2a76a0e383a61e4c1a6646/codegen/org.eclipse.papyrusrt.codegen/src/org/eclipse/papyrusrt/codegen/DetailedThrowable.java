/*******************************************************************************
 * Copyright (c) 2014-2015 Zeligsoft (2009) Limited and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.papyrusrt.codegen;

/**
 * This class is used to identify Exceptions and Errors which should be reported
 * as MultiStatus, rather than simple (single-line) Status.
 *
 * @author epp
 */
public class DetailedThrowable extends Throwable
{

    public DetailedThrowable()
    {
        super();
    }

    public DetailedThrowable(String message)
    {
        super( message );
    }

    public DetailedThrowable(Throwable cause)
    {
        super( cause );
    }

    public DetailedThrowable(String message, Throwable cause)
    {
        super( message, cause );
    }

    public DetailedThrowable(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
    {
        super( message, cause, enableSuppression, writableStackTrace );
    }

}
