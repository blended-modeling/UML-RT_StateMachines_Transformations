/*******************************************************************************
 * Copyright (c) 2014-2015 Zeligsoft (2009) Limited and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.papyrusrt.codegen.cpp.structure;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.papyrusrt.codegen.cpp.AbstractCppGenerator;
import org.eclipse.papyrusrt.codegen.cpp.CppCodePattern;
import org.eclipse.papyrusrt.codegen.cpp.rts.UMLRTRuntime;
import org.eclipse.papyrusrt.codegen.cpp.structure.model.Controller;
import org.eclipse.papyrusrt.codegen.cpp.structure.model.ControllerAllocations;
import org.eclipse.papyrusrt.codegen.cpp.structure.model.Deployment;
import org.eclipse.papyrusrt.codegen.instance.model.ICapsuleInstance;
import org.eclipse.papyrusrt.codegen.instance.model.IPortInstance;
import org.eclipse.papyrusrt.codegen.lang.cpp.Expression;
import org.eclipse.papyrusrt.codegen.lang.cpp.element.CppEnum;
import org.eclipse.papyrusrt.codegen.lang.cpp.element.ElementList;
import org.eclipse.papyrusrt.codegen.lang.cpp.element.Enumerator;
import org.eclipse.papyrusrt.codegen.lang.cpp.element.LinkageSpec;
import org.eclipse.papyrusrt.codegen.lang.cpp.element.Variable;
import org.eclipse.papyrusrt.codegen.lang.cpp.expr.AddressOfExpr;
import org.eclipse.papyrusrt.codegen.lang.cpp.expr.BlockInitializer;
import org.eclipse.papyrusrt.codegen.lang.cpp.expr.BooleanLiteral;
import org.eclipse.papyrusrt.codegen.lang.cpp.expr.ConstructorCall;
import org.eclipse.papyrusrt.codegen.lang.cpp.expr.CppEnumOrderedInitializer;
import org.eclipse.papyrusrt.codegen.lang.cpp.expr.ElementAccess;
import org.eclipse.papyrusrt.codegen.lang.cpp.expr.IndexExpr;
import org.eclipse.papyrusrt.codegen.lang.cpp.expr.IntegralLiteral;
import org.eclipse.papyrusrt.codegen.lang.cpp.expr.StringLiteral;
import org.eclipse.papyrusrt.codegen.lang.cpp.external.StandardLibrary;
import org.eclipse.papyrusrt.codegen.utils.XTUMLRTUtil;
import org.eclipse.papyrusrt.xtumlrt.common.CapsulePart;

public class CompositionGenerator extends AbstractCppGenerator
{
    private final org.eclipse.papyrusrt.xtumlrt.common.Capsule topCapsule;
    private final ControllerAllocations controllerAllocations;

    private Variable slotsVar;
    private final Map<ICapsuleInstance, Enumerator> capsuleEnums = new HashMap<ICapsuleInstance, Enumerator>();
    private final Map<ICapsuleInstance, Variable> capsuleTypeInstances = new HashMap<ICapsuleInstance, Variable>();
    private final Map<ICapsuleInstance, Variable[]> capsulePortArrays = new HashMap<ICapsuleInstance, Variable[]>();
    private final Map<ICapsuleInstance, VarWrapper[]> portArrays = new HashMap<ICapsuleInstance, VarWrapper[]>();

    private static class VarWrapper
    {
        public final Variable variable;
        public VarWrapper( Variable variable ) { this.variable = variable; }
    }

    private Deployment deployment;

    public static class Factory implements AbstractCppGenerator.Factory<org.eclipse.papyrusrt.xtumlrt.common.Capsule, org.eclipse.papyrusrt.xtumlrt.common.Package>
    {
        @Override
        public AbstractCppGenerator create( CppCodePattern cpp, org.eclipse.papyrusrt.xtumlrt.common.Capsule topCapsule, org.eclipse.papyrusrt.xtumlrt.common.Package context )
        {
            ControllerAllocations controllerAllocations = null;
            File file = cpp.getControllerAllocations( topCapsule );
            if( file != null )
                controllerAllocations = ControllerAllocations.load( file );

            // If there isn't an allocations file, then create one that will put everything onto
            // the same controller.
            if( controllerAllocations == null )
                controllerAllocations = ControllerAllocations.Default;

            return new CompositionGenerator( cpp, topCapsule, controllerAllocations );
        }
    }

    private CompositionGenerator( CppCodePattern cpp, org.eclipse.papyrusrt.xtumlrt.common.Capsule topCapsule, ControllerAllocations controllerAllocations )
    {
        super( cpp );
        this.topCapsule = topCapsule;
        this.controllerAllocations = controllerAllocations;
    }

    @Override
    public String getLabel()
    {
        return super.getLabel() + ' ' + topCapsule.getName();
    }

    @Override
    public boolean generate()
    {
        deployment = Deployment.build( cpp, topCapsule, controllerAllocations );
        if( deployment == null )
            return false;

        ElementList elements = cpp.getElementList( CppCodePattern.Output.Deployment, topCapsule );
        if( elements == null
         || ! cpp.markWritable( elements ) )
            return false;

        CppEnum capsuleIdEnum = new CppEnum( "CapsuleInstanceId" );
        elements.addElement( capsuleIdEnum );

        // Populate the full CapsuleId enum so that enumerators are assigned in a
        // consistent order.  When generating connections the far end capsule instance's
        // enumerator is used.  That access could change the generation order based
        // on how the connectors are traversed.
        for( Controller controller : deployment.getControllers() )
            for( ICapsuleInstance capsule : controller.getCapsules() )
            {
                Enumerator enumerator = new Enumerator( "InstId_" + capsule.getQualifiedName( '_' ) );
                capsuleIdEnum.add( enumerator );
                capsuleEnums.put( capsule, enumerator );
            }

        // Create an enumerator for each capsule instance.
        BlockInitializer arrayInit = new BlockInitializer( UMLRTRuntime.UMLRTSlot.getType().arrayOf( null ) );
        for( Controller controller : deployment.getControllers() )
        {
            Variable controllerVar
                = new Variable(
                        LinkageSpec.EXTERN,
                        UMLRTRuntime.UMLRTController.getType(),
                        controller.getName(),
                        UMLRTRuntime.UMLRTController.Ctor( new StringLiteral( controller.getName() ) ) );
            elements.addElement( controllerVar );

            for( ICapsuleInstance capsule : controller.getCapsules() )
            {
                Variable borderPortArray = getPortArray( elements, capsule, true );

                // The user capsule class is only created for statically allocated capsules
                if( ! capsule.isDynamic() )
                {
                    Variable borderPortPtrArray = null;
                    if( borderPortArray != null )
                    {
                        borderPortPtrArray = createCapsulePortPointerArray( borderPortArray );
                        if( borderPortPtrArray != null )
                            elements.addElement( borderPortPtrArray );
                    }
                    Variable internalPortArray = getPortArray( elements, capsule, false );

                    ConstructorCall ctorCall = new ConstructorCall( cpp.getConstructor( CppCodePattern.Output.CapsuleClass, capsule.getType() ) );
                    ctorCall.addArgument(
                        new AddressOfExpr(
                            new ElementAccess( cpp.getVariable( CppCodePattern.Output.UMLRTCapsuleClass, capsule.getType() ) ) ) );
                    ctorCall.addArgument( new AddressOfExpr( getSlotAccess( capsule ) ) );
                    ctorCall.addArgument( borderPortPtrArray == null ? StandardLibrary.NULL() : new ElementAccess( borderPortPtrArray ) );
                    ctorCall.addArgument( internalPortArray == null ? StandardLibrary.NULL() : new ElementAccess( internalPortArray ) );
                    ctorCall.addArgument( new BooleanLiteral( ! capsule.isDynamic() ) );

                    char[] instNameChars = capsule.getQualifiedName( '_' ).toCharArray();
                    String instName = null;
                    if( instNameChars.length <= 0 )
                        throw new RuntimeException( "invalid attempt to generate code for unnamed Capsule" );
                    else if( Character.isLowerCase( instNameChars[0] ) )
                        instName = new String( instNameChars ) + '_';
                    else
                    {
                        instNameChars[0] = Character.toLowerCase( instNameChars[0] );
                        instName = new String( instNameChars );
                    }

                    Variable instVar
                        = new Variable(
                                LinkageSpec.STATIC,
                                cpp.getCppClass( CppCodePattern.Output.CapsuleClass, capsule.getType() ).getType(),
                                instName,
                                ctorCall );
                    elements.addElement( instVar );
                    capsuleTypeInstances.put( capsule, instVar );
                }

                Variable partArray = createCapsulePartArray( elements, capsule );
                if( partArray != null )
                    elements.addElement( partArray );

                Variable userClassVar = capsuleTypeInstances.get( capsule );
                arrayInit.addExpression(
                    new BlockInitializer(
                        UMLRTRuntime.UMLRTSlot.getType(),
                        new StringLiteral( capsule.getQualifiedName( '.' ) ),
                        new IntegralLiteral( capsule.getIndex() ),
                        new AddressOfExpr( new ElementAccess( cpp.getVariable( CppCodePattern.Output.UMLRTCapsuleClass, capsule.getType() ) ) ),
                        capsule.getContainer() == null
                            ? StandardLibrary.NULL()
                            : new AddressOfExpr(
                                new ElementAccess(
                                    cpp.getVariable( CppCodePattern.Output.UMLRTCapsuleClass, capsule.getContainer().getType() ) ) ),
                        capsule.getCapsulePart() == null
                            ? new IntegralLiteral( 0 )
                            : cpp.getEnumeratorAccess(
                                    CppCodePattern.Output.PartId,
                                    capsule.getCapsulePart(),
                                    capsule.getContainer() == null ? null : capsule.getContainer().getType() ),
                        userClassVar == null ? StandardLibrary.NULL() : new AddressOfExpr( new ElementAccess( userClassVar ) ),
                        new AddressOfExpr( new ElementAccess( controllerVar ) ),
                        new IntegralLiteral( partArray == null ? 0 : partArray.getNumInitializedInstances() ),
                        partArray == null ? StandardLibrary.NULL() : new ElementAccess( partArray ),
                        new IntegralLiteral( borderPortArray == null ? 0 : borderPortArray.getNumInitializedInstances() ),
                        borderPortArray == null ? StandardLibrary.NULL() : new ElementAccess( borderPortArray ),
                        StandardLibrary.NULL(),
                        BooleanLiteral.TRUE(),
                        BooleanLiteral.FALSE() ) );
            }
        }

        if( slotsVar != null )
            slotsVar.setInitializer( arrayInit );
        else
            slotsVar
                = new Variable(
                        LinkageSpec.EXTERN,
                        UMLRTRuntime.UMLRTSlot.getType().arrayOf( null ),
                        topCapsule.getName() + "_slots",
                        arrayInit );
        elements.addElement( slotsVar );

        new CppMainGenerator().generate( cpp.getOutputFolder().getAbsolutePath() + "/main.cc", deployment, slotsVar );

        return true;
    }

    private Expression getSlotAccess( ICapsuleInstance capsule )
    {
        if( slotsVar == null )
            slotsVar
                = new Variable(
                        LinkageSpec.EXTERN,
                        UMLRTRuntime.UMLRTSlot.getType().arrayOf( null ),
                        topCapsule.getName() + "_slots" );

        return new IndexExpr( new ElementAccess( slotsVar ), new ElementAccess( capsuleEnums.get( capsule ) ) );
    }

    private Variable createCapsulePartArray( ElementList elements, ICapsuleInstance capsule )
    {
        BlockInitializer slotArrayInit = new BlockInitializer( UMLRTRuntime.UMLRTSlot.getType().ptr().arrayOf( null ) );
        Variable slotArrayVar
            = new Variable(
                    LinkageSpec.STATIC,
                    slotArrayInit.getType(),
                    "slots_" + capsule.getQualifiedName( '_' ),
                    slotArrayInit );

        BlockInitializer init = new BlockInitializer( UMLRTRuntime.UMLRTCapsulePart.getType().arrayOf( null ) );
        Variable var
            = new Variable(
                    LinkageSpec.STATIC,
                    UMLRTRuntime.UMLRTCapsulePart.getType().arrayOf( null ),
                    "parts_" + capsule.getQualifiedName( '_' ),
                    init );

        Variable capsuleClassVar = cpp.getVariable( CppCodePattern.Output.UMLRTCapsuleClass, capsule.getType() );

        for( CapsulePart part : XTUMLRTUtil.getAllCapsuleParts( capsule.getType() ) )
        {
            // The part points to an array of slots.  If the part is replicated at most one time,
            // then we avoid creating the array and instead point to a single slot.
            Expression slotAccess = null;
            List<? extends ICapsuleInstance> contained = capsule.getContained( part );
            if( contained == null
             || contained.size() <= 0 )
                slotAccess = StandardLibrary.NULL();
            else
            {
                slotAccess
                    = new AddressOfExpr(
                        new IndexExpr(
                            new ElementAccess( slotArrayVar ),
                            new IntegralLiteral( slotArrayVar.getNumInitializedInstances() ) ) );
                for( ICapsuleInstance sub : contained )
                    slotArrayInit.addExpression( new AddressOfExpr( getSlotAccess( sub ) ) );
            }

            init.addExpression(
                new BlockInitializer(
                    UMLRTRuntime.UMLRTCapsulePart.getType(),
                    new AddressOfExpr( new ElementAccess( capsuleClassVar ) ),
                    cpp.getEnumeratorAccess( CppCodePattern.Output.PartId, part, capsule.getType() ),
                    new IntegralLiteral( contained == null ? 0 : contained.size() ),
                    slotAccess ) );
        }

        if( slotArrayVar.getNumInitializedInstances() > 0 )
            elements.addElement( slotArrayVar );

        return var.getNumInitializedInstances() <= 0 ? null : var;
    }

    private Variable createCapsulePortArray( ICapsuleInstance capsule, boolean border )
    {
        Variable[] vars = capsulePortArrays.get( capsule );
        if( vars == null )
        {
            vars = new Variable[2];
            capsulePortArrays.put( capsule, vars );
        }

        Variable var = vars[border ? 0 : 1];
        if( var == null )
        {
            var = new Variable(
                    LinkageSpec.EXTERN, // extern so port farEnds can reference each other
                    UMLRTRuntime.UMLRTCommsPort.getType().arrayOf( null ),
                    ( border ? "border" : "internal" ) + "ports_" + capsule.getQualifiedName( '_' ) );
            vars[border ? 0 : 1] = var;
        }

        return var;
    }

    private Variable getPortArray( ElementList elements, ICapsuleInstance capsule, boolean border )
    {
        VarWrapper[] vars = portArrays.get( capsule );
        if( vars == null )
        {
            vars = new VarWrapper[2];
            portArrays.put( capsule, vars );
        }

        VarWrapper var = vars[border ? 0 : 1];
        if( var == null )
        {
            var = new VarWrapper( createPortArray( elements, capsule, border ) );
            vars[border ? 0 : 1] = var;

            if( var.variable != null )
                elements.addElement( var.variable );
        }

        return var.variable;
    }

    private Variable createPortArray( ElementList elements, ICapsuleInstance capsule, boolean border )
    {
        // The port array must be initialized in the same order as the corresponding PortId enum.
        CppEnumOrderedInitializer arrayInit = null;

        CppCodePattern.Output CppCodePattern_Output_portId
            = border ? CppCodePattern.Output.BorderPortId : CppCodePattern.Output.InternalPortId;

        // This generates two arrays.  One has an element for each port, the other is the array of
        // farEnd ports instances.  The farEnd array has an entry for each instance, the port array
        // has an entry for each port.
        // E.g., if there are two ports, one not replicated and the other replicated twice, then the
        //       port array will have two elements and the farEnd array will have three.
        // Further, the port array is initialized using slices of the farEnd array.  E.g.,
        //     farEndArray[3] = { farEndA0, farEndA1, farEndB };
        //     portArray[2]   = { &farEndArray[0], &farEndArray[2] );

        BlockInitializer farEndInit = new BlockInitializer( UMLRTRuntime.UMLRTCommsPortFarEnd.getType().arrayOf( null ) );
        Variable farEndPorts
            = new Variable(
                    LinkageSpec.STATIC,
                    farEndInit.getType(),
                    ( border ? "border" : "internal" ) + "farEndList_" + capsule.getQualifiedName( '_' ),
                    farEndInit );

        for( IPortInstance port : capsule.getPorts() )
        {
            // If we're processing border ports, then ignore the internal ones, and
            // vice versa.
            if( border == XTUMLRTUtil.isInternalPort( port.getType() ) )
                continue;

            // This generates an expression to access the start of the farEnd array.
            // For index 0 this is the array "farEndList_capsulename".  For other indexes it
            // is the address of the corresponding element "&farEndList_capsulename[n]".
            int farEndCount = 0;
            Expression farEndAccess
                = farEndPorts.getNumInitializedInstances() == 0
                    ? new ElementAccess( farEndPorts )
                    : new AddressOfExpr(
                          new IndexExpr(
                              new ElementAccess( farEndPorts ),
                              new IntegralLiteral( farEndPorts.getNumInitializedInstances() ) ) );
            for( IPortInstance.IFarEnd farEnd : port.getFarEnds() )
            {
                ++farEndCount;
                ICapsuleInstance farEndCapsule = farEnd.getContainer();
                farEndInit.addExpression(
                    new BlockInitializer(
                        UMLRTRuntime.UMLRTCommsPortFarEnd.getType(),
                        new IntegralLiteral( farEnd.getIndex() ),
                        new AddressOfExpr(
                            new IndexExpr(
                                new ElementAccess( createCapsulePortArray( farEndCapsule, ! XTUMLRTUtil.isInternalPort( farEnd.getType() ) ) ),
                                cpp.getEnumeratorAccess(
                                    XTUMLRTUtil.isInternalPort( farEnd.getType() ) ? CppCodePattern.Output.InternalPortId : CppCodePattern.Output.BorderPortId,
                                    farEnd.getType(),
                                    farEndCapsule.getType() ) ) ) ) );
            }

            // Bug 469882: Generate empty elements for all unconnected port instances.  The RTS
            //             requires this farEnd list to be the length that is declared in
            //             UMLRTCommsPortRole.numFarEnd (in the UMLRTCapsuleClass).
            // This numFarEnd is initialized with the lower bound of the port type.
            for( ; farEndCount < XTUMLRTUtil.getLowerBound( port.getType() ); ++farEndCount )
                farEndInit.addExpression(
                    new BlockInitializer(
                        UMLRTRuntime.UMLRTCommsPortFarEnd.getType(),
                        new IntegralLiteral( 0 ),
                        StandardLibrary.NULL() ) );

            BlockInitializer portInit = new BlockInitializer( UMLRTRuntime.UMLRTCommsPort.getType() );
            portInit.addExpression(
                new AddressOfExpr(
                    new ElementAccess(
                        cpp.getVariable( CppCodePattern.Output.UMLRTCapsuleClass, port.getContainer().getType() ) ) ) );
            portInit.addExpression(
                    cpp.getEnumeratorAccess( CppCodePattern_Output_portId, port.getType(), capsule.getType() ) );
            portInit.addExpression( new AddressOfExpr( getSlotAccess( port.getContainer() ) ) );
            portInit.addExpression( new IntegralLiteral( farEndCount ) );
            portInit.addExpression( farEndCount <= 0 ? StandardLibrary.NULL() : farEndAccess );

            // TODO some fields need the actual values
            portInit.addExpression( StandardLibrary.NULL() ); // mutable UMLRTMessageQueue * deferQueue; // Deferred messages on this port.
            portInit.addExpression( StandardLibrary.NULL() ); // mutable char * registeredName;
            portInit.addExpression( BooleanLiteral.from( XTUMLRTUtil.isAutomatic( port.getType() ) ) );
            portInit.addExpression( BooleanLiteral.from( border ) );
            portInit.addExpression( BooleanLiteral.TRUE() );  // unsigned generated : 1; // True for code-generated ports (registeredName is not from heap).
            portInit.addExpression( BooleanLiteral.from( XTUMLRTUtil.isApplicationLocked( port.getType() ) ) );
            portInit.addExpression( BooleanLiteral.from( XTUMLRTUtil.isNotification( port.getType() ) ) );
            portInit.addExpression( BooleanLiteral.FALSE() ); // unsigned proxy : 1; // True for proxy border ports created if the slot port replication is less than the capsule border port replication.
            portInit.addExpression( BooleanLiteral.from( port.isRelay() ) );
            portInit.addExpression( BooleanLiteral.FALSE() ); // unsigned sap : 1; // True if the port is an SAP.
            portInit.addExpression( BooleanLiteral.FALSE() ); // unsigned spp : 1; // True if the port is an SPP.
            portInit.addExpression( BooleanLiteral.FALSE() ); // unsigned unbound : 1; // True to represent the unbound port. Has no far-end instances and is replaced when binding.
            portInit.addExpression( BooleanLiteral.from( XTUMLRTUtil.isWired( port.getType() ) ) );

            if( arrayInit == null )
                arrayInit
                    = new CppEnumOrderedInitializer(
                        cpp.getIdEnum( CppCodePattern_Output_portId, port.getContainer().getType() ),
                        UMLRTRuntime.UMLRTCommsPortFarEnd.getType().arrayOf( null ) );
            arrayInit.putExpression( cpp.getEnumerator( CppCodePattern_Output_portId, port.getType(), capsule.getType() ), portInit );
        }

        if( arrayInit == null
         || arrayInit.getNumInitializers() <= 0 )
            return null;

        if( farEndPorts.getNumInitializedInstances() > 0 )
            elements.addElement( farEndPorts );

        Variable portArray = createCapsulePortArray( capsule, border );
        portArray.setInitializer( arrayInit );
        return portArray;
    }

    private Variable createCapsulePortPointerArray( Variable var )
    {
        if( var == null )
            return null;

        BlockInitializer init = new BlockInitializer( UMLRTRuntime.UMLRTCommsPort.getType().const_().ptr().arrayOf( null ) );
        for( int i = 0; i < var.getNumInitializedInstances(); ++i )
            init.addExpression(
                new AddressOfExpr(
                    new IndexExpr(
                        new ElementAccess( var ),
                        new IntegralLiteral( i ) ) ) );
        return new Variable( LinkageSpec.STATIC, init.getType(), var.getName().getIdentifier() + "_ptrs", init );
    }
}
