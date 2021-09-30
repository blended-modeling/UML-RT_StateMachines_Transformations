/*******************************************************************************
 * Copyright (c) 2014-2015 Zeligsoft (2009) Limited and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.papyrusrt.codegen.cpp;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.papyrusrt.codegen.cpp.rts.UMLRTRuntime;
import org.eclipse.papyrusrt.codegen.lang.cpp.CppWriter;
import org.eclipse.papyrusrt.codegen.lang.cpp.Expression;
import org.eclipse.papyrusrt.codegen.lang.cpp.HeaderFile;
import org.eclipse.papyrusrt.codegen.lang.cpp.dep.DependencyBlob;
import org.eclipse.papyrusrt.codegen.lang.cpp.element.Constructor;
import org.eclipse.papyrusrt.codegen.lang.cpp.element.CppClass;
import org.eclipse.papyrusrt.codegen.lang.cpp.element.CppEnum;
import org.eclipse.papyrusrt.codegen.lang.cpp.element.ElementList;
import org.eclipse.papyrusrt.codegen.lang.cpp.element.Enumerator;
import org.eclipse.papyrusrt.codegen.lang.cpp.element.LinkageSpec;
import org.eclipse.papyrusrt.codegen.lang.cpp.element.Variable;
import org.eclipse.papyrusrt.codegen.lang.cpp.expr.MemberAccess;
import org.eclipse.papyrusrt.codegen.lang.cpp.name.FileName;
import org.eclipse.papyrusrt.codegen.utils.XTUMLRTUtil;
import org.eclipse.papyrusrt.codegen.xtumlrt.trans.UML2xtumlrtCppProfileTranslator;
import org.eclipse.papyrusrt.xtumlrt.common.Capsule;
import org.eclipse.papyrusrt.xtumlrt.common.CapsulePart;
import org.eclipse.papyrusrt.xtumlrt.common.NamedElement;
import org.eclipse.papyrusrt.xtumlrt.common.Port;
import org.eclipse.papyrusrt.xtumlrt.common.Protocol;
import org.eclipse.papyrusrt.xtumlrt.common.RedefinableElement;
import org.eclipse.papyrusrt.xtumlrt.common.Signal;
import org.eclipse.papyrusrt.xtumlrt.platformcppmodel.CppInclude;

/**
 * The Code Pattern implements the top-level of the documented code pattern.  This is
 * used for sharing language model elements between different parts of the generator's
 * implementation.
 */
public class CppCodePattern
{
    private File outputFolder;
    private File modelFolder;
    private final Map<Key, ElementList> elementLists = new HashMap<CppCodePattern.Key, ElementList>();
    private final Map<Key, CppClass> cppClasses = new HashMap<CppCodePattern.Key, CppClass>();
    private final Map<Key, Variable> variables = new HashMap<CppCodePattern.Key, Variable>();
    private final Map<Key, CppEnum> cppEnums = new HashMap<CppCodePattern.Key, CppEnum>();
    private final Map<Key, Enumerator> enumerators = new HashMap<CppCodePattern.Key, Enumerator>();
    private final Map<Key, Constructor> constructors = new HashMap<CppCodePattern.Key, Constructor>();
    private final List<ElementList> outputs = new ArrayList<ElementList>();

    /**
     * Describes the meaning of each type of "file" that can be produced by the generator.
     */
    public static enum Output
    {
        CapsuleClass,
        ProtocolClass,
        BasicClass,
        UserEnum,
        ProtocolBaseRole,
        ProtocolConjugateRole,
        SignalId,
        OutSignals,
        InSignals,
        PortId,
        BorderPortId,
        InternalPortId,
        PartId,
        Deployment,
        UMLRTCapsuleClass,
        UMLRTTypeDescriptor;
    }

    public CppCodePattern()
    {
    }

    public File getOutputFolder()
    {
        return this.outputFolder;
    }

    public void setOutputFolder( File outputFolder )
    {
        this.outputFolder = outputFolder;
    }

    public void setModelFolder( File modelFolder )
    {
        this.modelFolder = modelFolder;
    }

    /**
     * Mark the given element list as writable.  Files that are not marked as writable
     * can be referenced but output should not be produced.
     */
    public boolean markWritable( ElementList elements )
    {
        if( ! elementLists.containsValue( elements ) )
            return false;

        outputs.add( elements );
        return true;
    }

    public File getControllerAllocations( NamedElement topCapsule )
    {
        File allocationsFile = new File( modelFolder, topCapsule.getName() + ".controllers" );
        return allocationsFile.exists() ? allocationsFile : null;
    }

    public ElementList getElementList( Output output, NamedElement element )
    {
        Key k = new Key( output, element, null );
        switch( output )
        {
        case Deployment:
            ElementList elementList = elementLists.get( k );
            if( elementList == null )
            {
                elementList = new ElementList( new FileName( "Controllers" ) );
                elementLists.put( k, elementList );
            }
            return elementList;
        default:
            return getElementList( k, element );
        }
    }

    public CppClass getWritableCppClass( Output output, NamedElement element )
    {
        CppClass cls = getCppClass( output, element );
        HeaderFile header = cls.getDefinedIn();
        if( header instanceof ElementList )
            markWritable( (ElementList)header );
        return cls;
    }

    public CppEnum getWritableCppEnum( Output output, NamedElement element )
    {
        CppEnum enm = getCppEnum( output, element );
        HeaderFile header = enm.getDefinedIn();
        if( header instanceof ElementList )
            markWritable( (ElementList)header );
        return enm;
    }

    public CppClass getCppClass( Output output, NamedElement element )
    {
        Key k = new Key( output, element, null );
        CppClass cls = cppClasses.get( k );
        if( cls == null )
        {
            switch( output )
            {
            case CapsuleClass:
            case ProtocolClass:
            case BasicClass:
                cls = new CppClass( getName( output, element ) );
                ElementList elements = getElementList( k, element );
                elements.addElement( cls );
                apply_C_Cpp_Include_Stereotypes( elements, element );
                break;
            case OutSignals:
                cls = new CppClass( "OutSignals" );
                getCppClass( Output.ProtocolClass, element ).addMember( CppClass.Visibility.PUBLIC, cls );
                break;
            case InSignals:
                cls = new CppClass( "InSignals" );
                getCppClass( Output.ProtocolClass, element ).addMember( CppClass.Visibility.PUBLIC, cls );
                break;
            case ProtocolBaseRole:
            case ProtocolConjugateRole:
                // TODO fix ordering in the language model, until then force the protocol class to
                //      be generated before the role.
                getCppClass( Output.ProtocolClass, element );
                cls = new CppClass( getName( output, element ) );
                getElementList( new Key( Output.ProtocolClass, element, null ), element ).addElement( cls );
                break;
            default:
                throw new RuntimeException( "code pattern does not contain a CppClass for " + output.toString() );
            }

            cppClasses.put( k, cls );
        }

        return cls;
    }

    public Constructor getConstructor( Output output, NamedElement element )
    {
        Key k = new Key( output, element, null );
        Constructor ctor = constructors.get( k );
        if( ctor == null )
        {
            switch( output )
            {
            case CapsuleClass:
            case ProtocolBaseRole:
            case ProtocolConjugateRole:
                ctor = new Constructor();
                getCppClass( output, element ).addMember( CppClass.Visibility.PUBLIC, ctor );
                break;
            default:
                throw new RuntimeException( "code pattern does not contain a Constructor for " + output.toString() );
            }            

            constructors.put( k, ctor );
        }

        return ctor;
    }

    public Variable getVariable( Output output, NamedElement element )
    {
        Key k = new Key( output, element, null );
        Variable var = variables.get( k );
        if( var == null )
        {
            switch( output )
            {
            case UMLRTCapsuleClass:
                var = new Variable( LinkageSpec.EXTERN, UMLRTRuntime.UMLRTCapsuleClass.getType().const_(), getName( output, element ) );
                break;
            case UMLRTTypeDescriptor:
                var = new Variable( LinkageSpec.EXTERN, UMLRTRuntime.UMLRTObject.getType().const_().constPtr(), getName( output, element ) );
                break;
            default:
                throw new RuntimeException( "code pattern does not contain a Variable for " + output.toString() );
            }

            variables.put( k, var );
        }

        return var;
    }

    public CppEnum getCppEnum( Output output, NamedElement element )
    {
        Key k = new Key( output, element, null );
        CppEnum enm = cppEnums.get( k );
        if( enm == null )
        {
            switch( output )
            {
            case UserEnum:
                enm = new CppEnum( element.getName() );
                break;
            default:
                throw new RuntimeException( "code pattern does not contain a CppEnum for " + output.toString() );
            }

            ElementList elements = getElementList( k, element );
            elements.addElement( enm );
            enm.setDefinedIn( elements );

            cppEnums.put( k, enm );
        }

        return enm;
    }

    private static Map<String, NamedElement> getSortedSignals( Protocol protocol )
    {
        // If the base element has 2 features, A and C, and the derived has feature B, then
        // the IDs must be sorted A, C, B.  The parents are first and then the children,
        // each group is sorted by name.  In cases where the derived element redefines
        // feature A, the IDs must continue to be sorted A, C, B.
        //
        // This is needed because the IDs are built into the code.  Derived elements must
        // use the same number as the parent.
        //
        // The implementation finds the root Element, then starts a LinkedHashMap that is
        // indexed by the feature element's name.  LinkedHashMaps will replace existing
        // values without changing the order.

        RedefinableElement parent = protocol.getRedefines();
        Map<String, NamedElement> elements
            = parent instanceof Protocol
                ? getSortedSignals( (Protocol)parent )
                : new LinkedHashMap<String, NamedElement>();
        for( NamedElement element : XTUMLRTUtil.getUserSignals( protocol ) )
            elements.put( element.getName(), element );

        return elements;
    }

    private static Map<String, NamedElement> getSortedPorts( Capsule capsule )
    {
        // See implementation comment in #getSortedSignals.

        RedefinableElement parent = capsule.getRedefines();
        Map<String, NamedElement> elements
            = parent instanceof Capsule
                ? getSortedPorts( (Capsule)parent )
                : new LinkedHashMap<String, NamedElement>();
        for( Port element : XTUMLRTUtil.getRTPorts( capsule ) )
            elements.put( element.getName(), element );

        return elements;
    }

    private static Map<String, NamedElement> getSortedParts( Capsule capsule )
    {
        // See implementation comment in #getSortedSignals.

        RedefinableElement parent = capsule.getRedefines();
        Map<String, NamedElement> elements
            = parent instanceof Capsule
                ? getSortedParts( (Capsule)parent )
                : new LinkedHashMap<String, NamedElement>();
        for( CapsulePart element : XTUMLRTUtil.getCapsuleParts( capsule ) )
            elements.put( element.getName(), element );

        return elements;
    }

    public CppEnum getIdEnum( Output output, NamedElement element )
    {
        Key k = new Key( output, element, null );
        CppEnum enm = cppEnums.get( k );
        if( enm == null )
        {
            Map<String, NamedElement> enumeratorElements = null;

            Output clsKind = null;
            String enumName = null;
            Expression firstLiteral = null;
            switch( output )
            {
            case SignalId:
                clsKind = Output.ProtocolClass;
                enumName = "SignalId";
                enumeratorElements = getSortedSignals( (Protocol)element );
                firstLiteral = UMLRTRuntime.UMLRTSignal.FIRST_PROTOCOL_SIGNAL_ID();
                break;
            case PortId:
                clsKind = Output.CapsuleClass;
                enumName = "PortId";
                enumeratorElements = getSortedPorts( (Capsule)element );
                break;
            case PartId:
                clsKind = Output.CapsuleClass;
                enumName = "PartId";
                enumeratorElements = getSortedParts( (Capsule)element );
                break;
            case BorderPortId:
                clsKind = Output.CapsuleClass;
                enumName = "BorderPortId";
                break;
            case InternalPortId:
                clsKind = Output.CapsuleClass;
                enumName = "InternalPortId";
                break;
            default:
                throw new RuntimeException( "code pattern does not contain a CppEnum for " + output.toString() );
            }

            CppClass cls = getCppClass( clsKind, element );
            enm = new CppEnum( cls, enumName, firstLiteral );
            cls.addMember( CppClass.Visibility.PUBLIC, enm );

            cppEnums.put( k, enm );

            if( enumeratorElements != null )
                for( NamedElement enumeratorElement : enumeratorElements.values() )
                    getEnumerator( output, enumeratorElement, element );
        }

        return enm;
    }

    public Enumerator getEnumerator( Output output, NamedElement element, NamedElement context )
    {
        Key k = new Key( output, element, context );
        Enumerator enumerator = enumerators.get( k );
        if( enumerator == null )
        {
            switch( output )
            {
            case SignalId:
            {
                if( ! ( element instanceof Signal ) )
                    throw new RuntimeException( "code pattern requires Signal for " + output.toString() + " but got " + element.getClass().getCanonicalName() );

                if( ! ( context instanceof NamedElement ) )
                    throw new RuntimeException( "code pattern requires NamedElement as owner for " + output.toString() + " but got " + context.getClass().getCanonicalName() );

                break;
            }
            case PortId:
            case PartId:
            case BorderPortId:
            case InternalPortId:
            {
                if( ! ( context instanceof NamedElement ) )
                    throw new RuntimeException( "code pattern requires NamedElement as owner for " + output.toString() + " but got " + context.getClass().getCanonicalName() );

                break;
            }
            default:
                throw new RuntimeException( "code pattern does not contain an Enumerator for " + output.toString() );
            }

            // Accessing the IdEnum could trigger addition of all enumerators.  Check for a value
            // after accessing the enum.  Create a new instance only when it does not already
            // exist.
            CppEnum enm = getIdEnum( output, context );
            enumerator = enumerators.get( k );
            if( enumerator == null )
            {
                enumerator = enm.add( getName( output, element ) );
                enumerators.put( k, enumerator );
            }
        }

        return enumerator;
    }

    public Expression getEnumeratorAccess( Output output, NamedElement element, NamedElement context )
    {
        CppClass cls = null;
        switch( output )
        {
        case SignalId:
        {
            if( ! ( element instanceof Signal ) )
                throw new RuntimeException( "code pattern requires Signal for " + output.toString() + " but got " + element.getClass().getCanonicalName() );

            Signal signal = (Signal) element;
            if( context == null)
            {
                NamedElement owner = XTUMLRTUtil.getOwner( signal );
                if (owner == null || !(owner instanceof Protocol))
                    throw new RuntimeException( "code pattern: the owner of signal " + signal.getName() + " is not a protocol" );
                Protocol protocol = (Protocol) owner;
                if( XTUMLRTUtil.isSystemElement( protocol ) )
                    return UMLRTRuntime.getSystemProtocolSignalAccess( signal );
                context = protocol;
            }
            cls = getCppClass( Output.ProtocolClass, context );
            break;
        }
        case PartId:
        {
            if( ! ( element instanceof CapsulePart ) )
                throw new RuntimeException( "code pattern requires Property for " + output.toString() + " but got " + element.getClass().getCanonicalName() );

            if( context == null )
            {
                CapsulePart part = (CapsulePart)element;
                NamedElement capsule = XTUMLRTUtil.getOwner( part );
                context = capsule;
            }
            cls = getCppClass( Output.CapsuleClass, context );
            break;
        }
        case PortId:
        case BorderPortId:
        case InternalPortId:
        {
            if( ! ( element instanceof Port ) )
                throw new RuntimeException( "code pattern requires Port for " + output.toString() + " but got " + element.getClass().getCanonicalName() );

            if( context == null )
            {
                Port port = (Port)element;
                NamedElement capsule = XTUMLRTUtil.getOwner( port );
                context = capsule;
            }
            cls = getCppClass( Output.CapsuleClass, context );
            break;
        }
        default:
            throw new RuntimeException( "no enumerator access expression defined for " + output );
        }

        Enumerator enumerator = getEnumerator( output, element, context );
        return new MemberAccess( cls, enumerator );
    }

    private String getName( Output output, NamedElement element )
    {
        switch( output )
        {
        case CapsuleClass:          return "Capsule_"        + element.getName();
        case ProtocolBaseRole:      return element.getName() + "_baserole";
        case ProtocolConjugateRole: return element.getName() + "_conjrole";
        case SignalId:              return "signal_"         + element.getName();
        case PortId:                return "port_"           + element.getName();
        case BorderPortId:          return "borderport_"     + element.getName();
        case InternalPortId:        return "internalport_"   + element.getName();
        case PartId:                return "part_"           + element.getName();
        case UMLRTTypeDescriptor:   return "UMLRTType_"      + element.getName();
        default:                    return                     element.getName();
        }
    }

    private ElementList getElementList( Key k, NamedElement element )
    {
        ElementList elementList = elementLists.get( k );
        if( elementList == null )
        {
            switch( k.output )
            {
            case UMLRTCapsuleClass:
                return getElementList( Output.CapsuleClass, element );
            default:
                elementList = new ElementList( new FileName( element.getName() ) );
                break;
            }
            elementLists.put( k, elementList );
        }

        return elementList;
    }

    private static class Key
    {
        public final Output output;
        public final NamedElement element;
        public final NamedElement context;
        public Key( Output output, NamedElement element, NamedElement context )
        {
            this.output = output;
            this.element = element;
            this.context = context;
        }

        @Override
        public boolean equals( Object obj )
        {
            if( ! ( obj instanceof Key ) )
                return false;
            Key other = (Key)obj;
            if (context == null)
                return output == other.output
                && element.equals( other.element )
                && other.context == null;
            return output == other.output
                && element.equals( other.element )
                && context.equals( other.context );
        }

        @Override
        public int hashCode()
        {
            return output.ordinal() ^ element.hashCode();
        }
    }

    // TODO Accessors for common parts of the top-level of the code pattern.  E.g., the
    //      SignalId is needed by several generators; there should be an accessor here.

    public boolean write( )
    {
        String baseFolder = outputFolder.getAbsolutePath();

        boolean ret = true;
        for( ElementList output : outputs )
        {
            CppWriter out = CppWriter.create( baseFolder, output );
            try
            {
                if( ! output.write( out ) )
                {
                    ret = false;
                    System.err.println( "Failure while writing '" + output.getName().getAbsolutePath() + "' to disk" );
                }
            }
            finally
            {
                out.close();
            }
        }

        // Generate makefile
        new CppMakefileGenerator().generate(outputFolder.getAbsolutePath() + "/Makefile");

        return ret;
    }

    private void apply_C_Cpp_Include_Stereotypes( ElementList elements, NamedElement element )
    {
        CppInclude include = UML2xtumlrtCppProfileTranslator.getCppInclude( element );
        if( include == null )
            return;

        String text = include.getHeader();
        if( text != null
         && ! text.trim().isEmpty() )
             elements.addDeclDependency( new DependencyBlob( text ) );

        text = include.getBody();
        if( text != null
         && ! text.trim().isEmpty() )
             elements.addDefnDependency( new DependencyBlob( text ) );
    }
}
