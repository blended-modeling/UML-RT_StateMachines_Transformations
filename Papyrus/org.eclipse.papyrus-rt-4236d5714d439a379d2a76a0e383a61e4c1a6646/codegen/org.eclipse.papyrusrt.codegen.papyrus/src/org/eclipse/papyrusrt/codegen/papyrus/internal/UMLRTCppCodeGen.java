/*******************************************************************************
 * Copyright (c) 2014-2015 Zeligsoft (2009) Limited and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.papyrusrt.codegen.papyrus.internal;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrusrt.codegen.papyrus.UMLRTGenerator;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.handlers.HandlerUtil;

public class UMLRTCppCodeGen extends AbstractHandler
{
    @SuppressWarnings( "rawtypes" )
    @Override
    public Object execute( ExecutionEvent event ) throws ExecutionException
    {
        ISelection sel = HandlerUtil.getCurrentSelection( event );
        if( ! ( sel instanceof IStructuredSelection ) )
            return null;

        List<EObject> targets = new ArrayList<EObject>();

        IStructuredSelection selection = (IStructuredSelection)sel;
        Iterator i = selection.iterator();
        while( i.hasNext() )
        {
            Object obj = i.next();
            EObject eobj = EMFHelper.getEObject( obj );
            if( eobj == null )
                throw new IllegalArgumentException( obj.getClass().getCanonicalName() + " is not an EObject" );

            targets.add( eobj );
        }

        final IStatus status = UMLRTGenerator.generate( targets, "Top" );  // "Top" should be replaced by some user-given property
        Display.getDefault().syncExec( new Runnable()
        {
            @Override
            public void run()
            {
                ErrorDialog.openError( Display.getCurrent().getActiveShell(), "UML-RT Code Generator", null, status );
            }
        } );

        return null;
    }
}
