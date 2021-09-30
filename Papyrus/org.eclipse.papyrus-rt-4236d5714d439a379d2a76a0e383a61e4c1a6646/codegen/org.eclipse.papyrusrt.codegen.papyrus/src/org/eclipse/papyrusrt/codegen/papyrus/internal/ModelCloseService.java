/*******************************************************************************
 * Copyright (c) 2014-2015 Zeligsoft (2009) Limited and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.papyrusrt.codegen.papyrus.internal;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.papyrus.infra.core.editor.IMultiDiagramEditor;
import org.eclipse.papyrus.infra.core.resource.ModelSet;
import org.eclipse.papyrus.infra.core.services.EditorLifecycleEventListener;
import org.eclipse.papyrus.infra.core.services.EditorLifecycleManager;
import org.eclipse.papyrus.infra.core.services.IService;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.core.services.ServicesRegistry;
import org.eclipse.papyrusrt.codegen.cpp.UML2ChangeTracker;


public class ModelCloseService implements IService
{
    private ServicesRegistry registry;

    public ModelCloseService()
    {
    }

    @Override
    public void disposeService() throws ServiceException
    {
    }

    @Override
    public void init( ServicesRegistry registry ) throws ServiceException
    {
        this.registry = registry;
    }

    @Override
    public void startService() throws ServiceException
    {
        ModelSet modelSet = registry.getService( ModelSet.class ); // Get the ModelSet service from the ServicesRegistry
        TransactionalEditingDomain editingDomain = modelSet
                .getTransactionalEditingDomain();
        EditorLifecycleManager lifecycleManager = registry
                .getService( EditorLifecycleManager.class );
        lifecycleManager
                .addEditorLifecycleEventsListener( new EditorCycleListener(
                        editingDomain ) );
    }

    /**
     * An internal listener for life cycle events of Papyrus
     */
    protected static class EditorCycleListener implements
            EditorLifecycleEventListener
    {

        TransactionalEditingDomain domain;

        EditorCycleListener( TransactionalEditingDomain domain )
        {
            this.domain = domain;
        }

        public void postInit( IMultiDiagramEditor editor )
        {
        }

        public void postDisplay( IMultiDiagramEditor editor )
        {
        }

        /**
         * Executed before an editor will close => stop recording for this
         * editing domain
         */
        public void beforeClose( IMultiDiagramEditor editor )
        {
            for (Resource res : domain.getResourceSet().getResources())
            {
                UML2ChangeTracker activeInstance = UML2ChangeTracker.getActiveInstance();
                if (activeInstance != null) activeInstance.closeResource( res );
            }
        }
    }
}
