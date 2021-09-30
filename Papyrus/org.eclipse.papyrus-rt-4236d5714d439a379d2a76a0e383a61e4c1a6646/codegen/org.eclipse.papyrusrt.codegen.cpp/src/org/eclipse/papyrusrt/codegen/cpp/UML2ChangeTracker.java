/*******************************************************************************
 * Copyright (c) 2015 Zeligsoft (2009) Limited and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.papyrusrt.codegen.cpp;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.papyrus.codegen.base.codesync.ChangeObject;
import org.eclipse.papyrusrt.codegen.CodeGenPlugin;
import org.eclipse.papyrusrt.codegen.cpp.CppCodeGenerator.GeneratorKey;
import org.eclipse.papyrusrt.codegen.cpp.CppCodeGenerator.Kind;
import org.eclipse.papyrusrt.codegen.utils.UMLRealTimeProfileUtil;
import org.eclipse.papyrusrt.codegen.utils.XTUMLRTUtil;
import org.eclipse.papyrusrt.codegen.xtumlrt.trans.UML2xtumlrtModelTranslator;
import org.eclipse.papyrusrt.xtumlrt.common.NamedElement;
import org.eclipse.papyrusrt.xtumlrt.common.StateMachine;
import org.eclipse.uml2.uml.Behavior;
import org.eclipse.uml2.uml.DataType;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.util.UMLSwitch;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

@SuppressWarnings( "deprecation" )
public class UML2ChangeTracker implements ChangeTracker
{
    private static UML2ChangeTracker ACTIVE_INSTANCE = null;
    private static Map<Resource, Multimap<Kind, EObject>> alreadyGenerated = new HashMap<Resource, Multimap<Kind, EObject>>(); // HashMultimap.create();
    private static Map<Resource, Multimap<Kind, EObject>> changed          = new HashMap<Resource, Multimap<Kind, EObject>>(); // HashMultimap.create();

    private UML2xtumlrtModelTranslator translator;
    private String top;

    public UML2ChangeTracker( UML2xtumlrtModelTranslator translator )
    {
        this.translator = translator;
    }

    public UML2ChangeTracker( UML2xtumlrtModelTranslator translator, String top )
    {
        this.translator = translator;
        this.top = top;
    }

    public void setTop( String top )
    {
        this.top = top;
    }

    public static void setActiveInstance( UML2ChangeTracker trackerInstance )
    {
        ACTIVE_INSTANCE = trackerInstance;
    }

    public static UML2ChangeTracker getActiveInstance()
    {
        return ACTIVE_INSTANCE;
    }

    @Override
    public void prune( Map<GeneratorKey, AbstractCppGenerator> generators )
    {
        Iterator<Entry<GeneratorKey, AbstractCppGenerator>> iterator = generators
                .entrySet().iterator();
        while (iterator.hasNext())
        {
            Entry<GeneratorKey, AbstractCppGenerator> next = iterator.next();
            GeneratorKey key = next.getKey();
            EObject umlElement = translator.getSource( (NamedElement) key.object );

            if (alreadyGeneratedContains( key.kind, umlElement )
                    && !changedContains( key.kind, umlElement ))
            {
                if (key.kind == Kind.Capsule)
                {
                    boolean portWithChangedProtocol = false;
                    for (org.eclipse.uml2.uml.Port umlPort
                            : UMLRealTimeProfileUtil.getAllRTPorts( (org.eclipse.uml2.uml.Class) umlElement ))
                    {
                        org.eclipse.uml2.uml.Package protocol = UMLRealTimeProfileUtil.getProtocol( umlPort );
                        if (changedContains( Kind.Protocol, protocol ))
                        {
                            portWithChangedProtocol = true;
                            break;
                        }
                    }

                    if (!portWithChangedProtocol)
                    {
                        CodeGenPlugin.getLogger()
                                .log( Level.INFO,
                                      "Pruning "
                                              + ((org.eclipse.uml2.uml.NamedElement) umlElement)
                                                      .getQualifiedName()
                                              + " from generation." );
                        iterator.remove();
                    }
                    else
                    {
                        addAlreadyGenerated( key.kind, umlElement );
                    }
                }
                else
                {
                    CodeGenPlugin.getLogger()
                            .log( Level.INFO,
                                  "Pruning "
                                          + ((org.eclipse.uml2.uml.NamedElement) umlElement)
                                                  .getQualifiedName()
                                          + " from generation." );
                    iterator.remove();
                }
            }
            else
            {
                addAlreadyGenerated( key.kind, umlElement );
            }
        }
    }

    @Override
    public void consumeChanges( Map<GeneratorKey, AbstractCppGenerator> generators )
    {
        for (GeneratorKey gk : generators.keySet())
        {
            EObject umlElement = translator.getSource( (NamedElement) gk.object );
            removeChanged( gk.kind, umlElement );
        }
    }

    private boolean alreadyGeneratedContains( Kind kind, EObject object )
    {
        if (object != null)
        {
            Resource resource = object.eResource();
            if (resource != null)
            {
                Multimap<Kind, EObject> multimap = alreadyGenerated.get( resource );
                if (multimap != null)
                {
                    return multimap.containsEntry( kind, object );
                }
            }
        }
        return false;
    }

    private void addAlreadyGenerated( Kind kind, EObject object )
    {
        if (object != null)
        {
            Resource resource = object.eResource();
            if (resource != null)
            {
                Multimap<Kind, EObject> multimap = alreadyGenerated.get( resource );
                if (multimap == null)
                {
                    multimap = HashMultimap.create();
                    alreadyGenerated.put( resource, multimap );
                }
                multimap.put( kind, object );
            }
        }
    }

    private boolean changedContains( Kind kind, EObject object )
    {
        if (object != null)
        {
            Resource resource = object.eResource();
            if (resource != null)
            {
                Multimap<Kind, EObject> multimap = changed.get( resource );
                if (multimap != null)
                {
                    return multimap.containsEntry( kind, object );
                }
            }
        }
        return false;
    }

    public Collection<EObject> getAllChanged()
    {
        Collection<EObject> all = new ArrayList<EObject>();
        if (changed != null)
        {
            Collection<Multimap<Kind, EObject>> allMultiMaps = changed.values();
            if (allMultiMaps != null)
            {
                for (Multimap<Kind, EObject> map : allMultiMaps)
                {
                    all.addAll( map.values() );
                }
            }
        }
        return all;
    }

    private void removeChanged( Kind kind, EObject object )
    {
        if (object != null)
        {
            Resource resource = object.eResource();
            if (resource != null)
            {
                Multimap<Kind, EObject> multimap = changed.get( resource );
                if (multimap != null)
                {
                    multimap.remove( kind, object );
                }
            }
        }
    }

    @Override
    public void addChanges( List<ChangeObject> changeList )
    {
        if (changeList != null)
        {
            ChangeCollector changeCollector = new ChangeCollector( changed );
            for (ChangeObject change : changeList)
            {
                EObject target = change.eObject;
                // There might be a bug in Papyrus: updates to OpaqueBehaviour
                // seem to be reported as REMOVE. However, the following would
                // still work, as the container gets regenerated.
                if (change.eventType == Notification.REMOVE)
                    target = target.eContainer();
                changeCollector.doSwitch( target );
            }
        }
    }

    @Override
    public void closeResource( Resource resource )
    {
        if (changed.remove( resource ) != null)
        {
            CodeGenPlugin.getLogger().log( Level.INFO,
                                           "Cleaning up changed map for resource: "
                                                   + resource.getURI()
                                                           .toString() );
        }

        if (alreadyGenerated.remove( resource ) != null)
        {
            CodeGenPlugin.getLogger().log( Level.INFO,
                                           "Cleaning up already generated map for resource: "
                                                   + resource.getURI()
                                                           .toString() );
        }
    }

    @Override
    public void resetAll()
    {
        UML2ChangeTracker.alreadyGenerated = new HashMap<Resource, Multimap<Kind, EObject>>();
        UML2ChangeTracker.changed          = new HashMap<Resource, Multimap<Kind, EObject>>();
    }

    private class ChangeCollector extends UMLSwitch<Boolean>
    {
        final Map<Resource, Multimap<Kind, EObject>> changed;

        public ChangeCollector( Map<Resource, Multimap<Kind, EObject>> changed )
        {
            this.changed = changed;
        }

        // Unhandled kinds of elements are ignored.
        @Override
        public Boolean defaultCase( EObject object )
        {
            EObject container = object.eContainer();
            if (container != null)
            {
                doSwitch( container );
            }

            return Boolean.TRUE;
        }

        @Override
        public Boolean casePackage( org.eclipse.uml2.uml.Package object )
        {
            // If this is a <<ProtocolContainer>> then create a Protocol
            // generator for it and
            // examine all capsules, select the ones that have a port that uses
            // this protocol.
            if (UMLRealTimeProfileUtil.isProtocolContainer( object ))
            {
                org.eclipse.papyrusrt.xtumlrt.common.CommonElement xtumlrtElement = translator.getGenerated( object );
                if (xtumlrtElement != null && xtumlrtElement instanceof NamedElement && !XTUMLRTUtil.isSystemElement( (NamedElement) xtumlrtElement ))
                {
                    createChange( Kind.Protocol, object );
                    for (Element element : object.getModel().getOwnedElements())
                    {
                        if (UMLRealTimeProfileUtil.isCapsule( element ))
                            for (org.eclipse.uml2.uml.Port umlPort
                                    : UMLRealTimeProfileUtil.getAllRTPorts( (org.eclipse.uml2.uml.Class) element ))
                                if (UMLRealTimeProfileUtil.getProtocol( umlPort ) == object)
                                    doSwitch( element );
                    }
                }
            }

            return Boolean.TRUE;
        }

        @Override
        public Boolean caseDataType( DataType object )
        {
            createChange( Kind.BasicClass, object );
            return Boolean.TRUE;
        }

        @Override
        public Boolean caseBehavior( org.eclipse.uml2.uml.Behavior object )
        {
            // We need to have a separate clause for state machines as in UML
            // Behavior  is a sub-type of Class so we don't want to confuse
            // this case with the Class case, otherwise the caseClass method
            // will create a BasicClass generator even if the behavior
            // belongs to a capsule
            EObject container = object.eContainer();
            if (container != null)
            {
                doSwitch( container );
            }

            return Boolean.TRUE;
        }

        @Override
        public Boolean caseClass( org.eclipse.uml2.uml.Class object )
        {
            if (!UMLRealTimeProfileUtil.isCapsule( object ))
            {
                createChange( Kind.BasicClass, object );
                return Boolean.TRUE;
            }

            // If the Class has been stereotyped with "Capsule" then the
            // structural as well
            // as state machine elements must be created.
            createChange( Kind.Capsule, object );

            Behavior behaviour = object.getClassifierBehavior();
            if (behaviour == null)
            {
                behaviour = object.getOwnedBehavior( null );
            }

            // Must leave the fully qualified name of UML StateMachine, otherwise
            // it gets confused with xtumlrt StateMachine
            if (behaviour instanceof org.eclipse.uml2.uml.StateMachine)
            {
                createChange( Kind.StateMachine, behaviour );
            }
            else
            {
                createChange( Kind.EmptyStateMachine, object );
            }

            // TODO Until there is a way to allocate capsules to threads, we
            //      treat the CapsuleType called "Top" specially.
            if (top == null
             || top.equals( object.getName() ))
            {
                createChange( Kind.Structural, object );
            }

            return true;
        }

        private void createChange( Kind kind, EObject object )
        {
            if (object != null)
            {
                Resource resource = object.eResource();
                if (resource != null)
                {
                    Multimap<Kind, EObject> multimap = changed.get( resource );
                    if (multimap == null)
                    {
                        multimap = HashMultimap.create();
                        changed.put( resource, multimap );
                    }
                    multimap.put( kind, object );
                }
            }
        }
    }


}
