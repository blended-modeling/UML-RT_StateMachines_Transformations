/*******************************************************************************
 * Copyright (c) 2014-2015 Zeligsoft (2009) Limited and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.papyrusrt.codegen.cpp;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrusrt.codegen.CodeGenPlugin;
import org.eclipse.papyrusrt.codegen.utils.XTUMLRTUtil;
import org.eclipse.papyrusrt.codegen.xtumlrt.trans.UML2xtumlrtCppProfileTranslator;
import org.eclipse.papyrusrt.codegen.xtumlrt.trans.UML2xtumlrtModelTranslator;
import org.eclipse.papyrusrt.xtumlrt.common.Capsule;
import org.eclipse.papyrusrt.xtumlrt.common.Entity;
import org.eclipse.papyrusrt.xtumlrt.common.Enumeration;
import org.eclipse.papyrusrt.xtumlrt.common.Model;
import org.eclipse.papyrusrt.xtumlrt.common.NamedElement;
import org.eclipse.papyrusrt.xtumlrt.common.Package;
import org.eclipse.papyrusrt.xtumlrt.common.Parameter;
import org.eclipse.papyrusrt.xtumlrt.common.Port;
import org.eclipse.papyrusrt.xtumlrt.common.Protocol;
import org.eclipse.papyrusrt.xtumlrt.common.Signal;
import org.eclipse.papyrusrt.xtumlrt.common.StateMachine;
import org.eclipse.papyrusrt.xtumlrt.common.StructuredType;
import org.eclipse.papyrusrt.xtumlrt.common.TypeDefinition;
import org.eclipse.papyrusrt.xtumlrt.common.util.CommonSwitch;
import org.eclipse.papyrusrt.xtumlrt.platformcppmodel.GenerationProperties;

/**
 * The top-level interface to the CodeGenerator. This will be used by the UI
 * action to generate code for a specific set of model elements.
 * <p>
 * The generator is responsible for examining the input model and invoking the
 * generators needed to create code for the model elements.
 */
public class CppCodeGenerator
{
    private final CppCodePattern    cpp;
    private final List<EObject>     targets = new ArrayList<EObject>();
    private String                  top;
    private ChangeTracker           changeTracker;

    /**
     * This works with the org.eclipse.papyrusrt.codegen.cpp.generator extension
     * point to provide a generator for StateMachine.
     */
    public static enum Kind
    {
        BasicClass("ClassGenerator"),
        Enum("EnumGenerator"),
        Protocol("ProtocolGenerator"),
        Capsule("CapsuleGenerator "),
        StateMachine("StateMachineGenerator"),
        EmptyStateMachine("EmptyStateMachineGenerator"),
        Structural("StructuralGenerator");

        public final String id;

        private Kind( String id )
        {
            this.id = id;
        }
    }

    public CppCodeGenerator( CppCodePattern cpp,
                             UML2xtumlrtModelTranslator translator )
    {
        this.cpp = cpp;
        this.changeTracker = new UML2ChangeTracker( translator, top );
        UML2ChangeTracker.setActiveInstance( (UML2ChangeTracker)this.changeTracker );
    }

    public CppCodeGenerator( CppCodePattern cpp,
                             UML2xtumlrtModelTranslator translator,
                             String top )
    {
        this( cpp, translator );
        this.top = top;
    }

    public CppCodeGenerator( CppCodePattern cpp,
                             UML2xtumlrtModelTranslator translator,
                             String top, EObject target )
    {
        this( cpp, translator, top );
        this.targets.add( target );
    }

    public CppCodeGenerator( CppCodePattern cpp,
                             UML2xtumlrtModelTranslator translator,
                             String top,
                             List<EObject> targets )
    {
        this( cpp, translator, top );
        this.targets.addAll( targets );
    }

    public ChangeTracker getChangeTracker()
    {
        return this.changeTracker;
    }

    public void setTop( String top )
    {
        this.top = top;
    }

    public void setTarget( EObject target )
    {
        this.targets.add( target );
    }

    public void setTargets( List<EObject> targets )
    {
        this.targets.addAll( targets );
    }

    protected static class GeneratorKey
    {
        public final Kind    kind;
        public final EObject object;
        public final EObject context;

        public GeneratorKey( Kind kind, EObject object, EObject context )
        {
            this.kind = kind;
            this.object = object;
            this.context = context;
        }

        @Override
        public int hashCode()
        {
            return kind.hashCode() ^ object.hashCode();
        }

        @Override
        public boolean equals( Object obj )
        {
            if (!(obj instanceof GeneratorKey)) return false;

            GeneratorKey other = (GeneratorKey) obj;
            if (context == null)
                return kind == other.kind
                    && object.equals( other.object )
                    && other.context == null;
            return kind == other.kind
                    && object.equals( other.object )
                    && context.equals( other.context );
        }
    }

    public IStatus generate()
    {
        long start = System.currentTimeMillis();
        MultiStatus status = new MultiStatus( CodeGenPlugin.ID, IStatus.OK,
                "UML-RT Code Generation", null );

        Map<GeneratorKey, AbstractCppGenerator> generators = new LinkedHashMap<GeneratorKey, AbstractCppGenerator>();
        Collector collector = new Collector( generators );
        for (EObject target : targets)
        {
            if (!toBeGenerated( target ))
                continue;
            if (!collector.doSwitch( target ))
                status.add( CodeGenPlugin.error( "Error while examining model changes" ) );
        }
        status.add( CodeGenPlugin.info( "Examined model changes "
                + (System.currentTimeMillis() - start) + "ms" ) );

        start = System.currentTimeMillis();
        changeTracker.prune( generators );
        status.add( CodeGenPlugin.info( "Prune unchanged elements "
                + (System.currentTimeMillis() - start) + "ms" ) );

        for (AbstractCppGenerator generator : generators.values())
        {
            try
            {
                start = System.currentTimeMillis();
                if (generator.generate())
                    status.add( CodeGenPlugin.info( generator.getLabel() + ' '
                                + (System.currentTimeMillis() - start) + "ms" ) );
                else status.add( CodeGenPlugin.error( "Error while generating "
                        + generator.getLabel() ) );
            }
            catch (Exception e)
            {
                status.add( CodeGenPlugin.error( e ) );
            }
        }

        start = System.currentTimeMillis();
        changeTracker.consumeChanges( generators );
        status.add( CodeGenPlugin.info( "Consume changes to elements "
                + (System.currentTimeMillis() - start) + "ms" ) );

        return status;
    }

    private boolean toBeGenerated( EObject target )
    {
        if (!(target instanceof NamedElement)) return false;
        NamedElement element = (NamedElement)target;
        if (XTUMLRTUtil.isSystemElement( element )) return false;
        GenerationProperties genProperties = UML2xtumlrtCppProfileTranslator.getGenerationProperties( element );
        if (genProperties == null) return true;
        return genProperties.isGenerate();
    }

    private class Collector extends CommonSwitch<Boolean>
    {
        private final GeneratorManager genManager = GeneratorManager.getInstance();
        public final Map<GeneratorKey, AbstractCppGenerator> generators;

        public Collector( Map<GeneratorKey, AbstractCppGenerator> generators )
        {
            this.generators = generators;
        }

        /**
         * Return true if a generator was created and inserted and false
         * otherwise.
         */
        private <T extends NamedElement> boolean createGenerator( Kind kind, T object, T context )
        {
            GeneratorKey key = new GeneratorKey( kind, object, context );
            if (generators.get( key ) != null) return false;

            AbstractCppGenerator generator = genManager.getGenerator( kind, cpp, object, context );
            if (generator == null) return false;

            generators.put( key, generator );
            return true;
        }

        // Unhandled kinds of elements are ignored.
        @Override
        public Boolean defaultCase( EObject object )
        {
            return true;
        }

        @Override
        public Boolean caseModel( Model model )
        {
            if (!toBeGenerated( model )) return true;
            for (Entity element : model.getEntities())
                doSwitch( element );
            for (Protocol element : model.getProtocols())
                doSwitch( element );
            for (TypeDefinition element : model.getTypeDefinitions())
                doSwitch( element );
            for (Package element : model.getPackages())
                doSwitch( element );

            return true;
        }

        @Override
        public Boolean casePackage( Package packge )
        {
            if (!toBeGenerated( packge )) return true;
            for (Entity element : packge.getEntities())
                doSwitch( element );
            for (Protocol element : packge.getProtocols())
                doSwitch( element );
            for (TypeDefinition element : packge.getTypeDefinitions())
                doSwitch( element );
            for (Package element : packge.getPackages())
                doSwitch( element );

            return true;
        }

        @Override
        public Boolean caseProtocol( Protocol protocol )
        {
            if (!toBeGenerated( protocol )) return true;
            // If this is a Protocol then create a Protocol generator for it and
            // examine all capsules, select the ones that have a port that uses this protocol.
            // The context for the new generator is null because we generate the protocol in its own class, not within another class.
            if (!XTUMLRTUtil.isSystemElement( protocol )
                    && createGenerator( Kind.Protocol, protocol, null ))
            {
                // Look at the data type for all signals and generate the data class (if any)
                // for each.
                for( Signal signal : XTUMLRTUtil.getSignals( protocol ) )
                    for( Parameter p : signal.getParameters() )
                        if( p.getType() != null ) doSwitch( p.getType() );

                Model model = XTUMLRTUtil.getModel( protocol );
                if (model == null) return true;
                for (Capsule capsule : XTUMLRTUtil.getAllCapsules( model ))
                {
                    for (Port port : XTUMLRTUtil.getAllRTPorts( capsule ))
                        if (port.getType() == protocol) doSwitch( capsule );
                }
            }
            return true;
        }

        @Override
        public Boolean caseTypeDefinition( TypeDefinition typedef )
        {
            if (!toBeGenerated( typedef)) return true;
            return doSwitch( typedef.getType() );
        }

        @Override
        public Boolean caseStructuredType( StructuredType struct )
        {
            if (!toBeGenerated( struct )) return true;
            // The context for the new generator is null because we generate the struct in its own class, not within another class.
            createGenerator( Kind.BasicClass, struct, null );
            return true;
        }

        @Override
        public Boolean caseCapsule( Capsule capsule )
        {
            if (!toBeGenerated( capsule )) return true;
            // The context for the new generator is null because we generate the capsule in its own class, not within another class.
            createGenerator( Kind.Capsule, capsule, null );

            StateMachine behaviour = XTUMLRTUtil.getActualBehaviour( capsule );
            if( behaviour != null && toBeGenerated( behaviour ) )
                createGenerator( Kind.StateMachine, behaviour, capsule );
            else
                createGenerator( Kind.EmptyStateMachine, capsule, null );

            // TODO Until there is a way to allocate capsules to threads, we treat the
            //      CapsuleType called "Top" specially.
            // The context for the new generator is null because we don't generate the Controllers.cc/.hh nested within another class.
            if( top.equals( capsule.getName() ) )
                createGenerator( Kind.Structural, capsule, null );

            return true;
        }

        @Override
        public Boolean caseEntity( Entity object )
        {
            if( ! toBeGenerated( object ) )
                return true;

            // The user provided classes are not nested within a class so there is no
            // reason to provide context.
            createGenerator( Kind.BasicClass, object, null );
            return true;
        }

        @Override
        public Boolean caseEnumeration( Enumeration object )
        {
            if( ! toBeGenerated( object ) )
                return true;

            // The user provided enumerations are not nested within a class so there is no
            // reason to provide context.
            createGenerator( Kind.Enum, object, null );
            return true;
        }
    };
}
