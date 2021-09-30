/*******************************************************************************
* Copyright (c) 2014-2015 Zeligsoft (2009) Limited  and others.
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v10.html
*******************************************************************************/

package org.eclipse.papyrusrt.codegen.cpp.statemachines.flat

import org.eclipse.papyrusrt.codegen.cpp.CppCodePattern
import org.eclipse.papyrusrt.codegen.cpp.TypesUtil
import org.eclipse.papyrusrt.codegen.lang.cpp.Expression
import org.eclipse.papyrusrt.codegen.lang.cpp.Statement
//import org.eclipse.papyrusrt.codegen.lang.cpp.Type
import org.eclipse.papyrusrt.codegen.lang.cpp.element.CppClass
import org.eclipse.papyrusrt.codegen.lang.cpp.element.CppEnum
import org.eclipse.papyrusrt.codegen.lang.cpp.element.Enumerator
import org.eclipse.papyrusrt.codegen.lang.cpp.element.LinkageSpec
import org.eclipse.papyrusrt.codegen.lang.cpp.element.MemberField
import org.eclipse.papyrusrt.codegen.lang.cpp.element.MemberFunction
import org.eclipse.papyrusrt.codegen.lang.cpp.element.Parameter
import org.eclipse.papyrusrt.codegen.lang.cpp.element.PrimitiveType
import org.eclipse.papyrusrt.codegen.lang.cpp.element.Variable
import org.eclipse.papyrusrt.codegen.lang.cpp.expr.BinaryOperation
import org.eclipse.papyrusrt.codegen.lang.cpp.expr.ElementAccess
import org.eclipse.papyrusrt.codegen.lang.cpp.expr.FunctionCall
import org.eclipse.papyrusrt.codegen.lang.cpp.expr.IndexExpr
import org.eclipse.papyrusrt.codegen.lang.cpp.expr.IntegralLiteral
import org.eclipse.papyrusrt.codegen.lang.cpp.expr.LogicalComparison
import org.eclipse.papyrusrt.codegen.lang.cpp.expr.MemberAccess
import org.eclipse.papyrusrt.codegen.lang.cpp.expr.UnaryOperation
import org.eclipse.papyrusrt.codegen.lang.cpp.stmt.BreakStatement
import org.eclipse.papyrusrt.codegen.lang.cpp.stmt.ConditionalStatement
import org.eclipse.papyrusrt.codegen.lang.cpp.stmt.ExpressionStatement
import org.eclipse.papyrusrt.codegen.lang.cpp.stmt.ReturnStatement
import org.eclipse.papyrusrt.codegen.lang.cpp.stmt.SwitchClause
import org.eclipse.papyrusrt.codegen.lang.cpp.stmt.SwitchStatement
import org.eclipse.papyrusrt.codegen.lang.cpp.stmt.VariableDeclarationStatement
import org.eclipse.papyrusrt.codegen.lang.cpp.stmt.WhileStatement
import org.eclipse.papyrusrt.codegen.statemachines.flat.model.smflatmodel.CheckHistory
import org.eclipse.papyrusrt.codegen.statemachines.flat.model.smflatmodel.SaveHistory
import org.eclipse.papyrusrt.xtumlrt.common.ActionChain
import org.eclipse.papyrusrt.xtumlrt.common.ActionCode
import org.eclipse.papyrusrt.xtumlrt.common.ChoicePoint
import org.eclipse.papyrusrt.xtumlrt.common.Entity
import org.eclipse.papyrusrt.xtumlrt.common.Guard
import org.eclipse.papyrusrt.xtumlrt.common.JunctionPoint
import org.eclipse.papyrusrt.xtumlrt.common.NamedElement
import org.eclipse.papyrusrt.xtumlrt.common.Port
import org.eclipse.papyrusrt.xtumlrt.common.Pseudostate
import org.eclipse.papyrusrt.xtumlrt.common.Signal
import org.eclipse.papyrusrt.xtumlrt.common.State
import org.eclipse.papyrusrt.xtumlrt.common.StateMachine
import org.eclipse.papyrusrt.xtumlrt.common.Transition
import org.eclipse.papyrusrt.xtumlrt.common.Type
import org.eclipse.papyrusrt.xtumlrt.common.Vertex
import org.eclipse.papyrusrt.xtumlrt.umlrt.RTTrigger
import org.eclipse.papyrusrt.codegen.cpp.rts.UMLRTRuntime
import org.eclipse.papyrusrt.codegen.cpp.rts.UMLRTRuntime.UMLRTCommsPort
import org.eclipse.papyrusrt.codegen.cpp.rts.UMLRTRuntime.UMLRTCommsPortRole
import org.eclipse.papyrusrt.codegen.cpp.rts.UMLRTRuntime.UMLRTMessage
import org.eclipse.papyrusrt.codegen.CodeGenPlugin
import static extension org.eclipse.papyrusrt.codegen.cpp.statemachines.flat.CppNamesUtil.*
import static extension org.eclipse.papyrusrt.codegen.utils.QualifiedNames.*
import static extension org.eclipse.papyrusrt.codegen.utils.XTUMLRTSMVirtualInheritanceExtensions.*
import static extension org.eclipse.papyrusrt.codegen.utils.XTUMLRTStateMachineUtil.*
import java.util.ArrayList
import java.util.Arrays
import java.util.Collection
import java.util.HashMap
import java.util.HashSet
import java.util.LinkedHashSet
import java.util.Map

import org.eclipse.xtend.lib.annotations.Data
import org.eclipse.papyrusrt.codegen.statemachines.transformations.GlobalConstants
import org.eclipse.papyrusrt.codegen.statemachines.transformations.InPlaceTransformation
import org.eclipse.papyrusrt.codegen.statemachines.transformations.StateNestingFlattener
import org.eclipse.papyrusrt.codegen.statemachines.transformations.TransitionDepthComparator
import org.eclipse.papyrusrt.codegen.statemachines.transformations.TransformationContext
import org.eclipse.papyrusrt.codegen.lang.cpp.external.StandardLibrary
import org.eclipse.papyrusrt.codegen.lang.cpp.expr.BlockInitializer
import org.eclipse.papyrusrt.codegen.lang.cpp.expr.StringLiteral
import org.eclipse.papyrusrt.codegen.lang.cpp.Type.CVQualifier
import org.eclipse.papyrusrt.codegen.lang.cpp.expr.AddressOfExpr
import org.eclipse.papyrusrt.codegen.lang.cpp.stmt.CodeBlock

/**
 * This class contains the transformation from flat UML-RT state machines to the
 * C/C++ language model.
 *
 * It implements the algorithms described in the technical report
 *
 * E. Posse. "Transforming flat UML-RT State Machines to a C/C++ language model".
 * Technical Report ZTR-2014-EP-002, Version 2, Zeligsoft, Sep 2014.
 *
 * @author eposse
 */
class FlatModel2Cpp implements InPlaceTransformation
{

    /**
     * The CppCodePattern that is being used for this transformation operation.
     */
    @Data static class CppGenerationTansformationContext implements TransformationContext
    {
        CppCodePattern      cpp
        Entity              capsuleContext
        Collection<State>   discardedStates
    }

    CppCodePattern cpp
    /** The class/capsule context where the state machine occurs (maybe the owner or a subclass of the SM owner */
    Entity              capsuleContext
    /** (Composite) States discarded by the flattener */
    Collection<State>   discardedStates
    /** The source state machine to transform */
    StateMachine        machine

    /** Elements that go into the generated model. */
    CppEnum                             statesDeclaration
    MemberField                         stateNamesTableDeclaration
    MemberField                         currentStateField
    Map<State, Enumerator>              stateEnumerators
    MemberField                         historyTableDeclaration
    MemberFunction                      saveHistoryFunction
    MemberFunction                      checkHistoryFunction
    Map<ActionCode, MemberFunction>     userActionFunctions
    Map<Guard, MemberFunction>          userGuardFunctions
    Map<ActionChain, MemberFunction>    actionChainFunctions
    Map<ChoicePoint, MemberFunction>    choicePointFunctions
    Map<JunctionPoint, MemberFunction>  junctionPointFunctions
    Map<State, MemberFunction>          stateFunctions
    MemberFunction                      injectFunc
    Parameter                           injectFuncParam
    MemberFunction                      initializeFunc
    Parameter                           initializeFuncParam
    MemberFunction                      getCurrentStateStringFunc
    CppClass                            cppCapsuleClass

    /** Objects used for the generation */
    val actionDeclarationGenerator = new ActionDeclarationGenerator
    val actionInvocationGenerator  = new ActionInvocationGenerator
    val guardDeclarationGenerator  = new GuardDeclarationGenerator
    val guardInvocationGenerator   = new GuardInvocationGenerator

    Map<State, String> stateNames
    val states = new ArrayList<State>

    new ()
    {
        stateEnumerators = newHashMap
        userActionFunctions = newHashMap
        userGuardFunctions = newHashMap
        actionChainFunctions = newHashMap
        choicePointFunctions = newHashMap
        junctionPointFunctions = newHashMap
        stateFunctions = newHashMap
        stateNames = newHashMap
    }

    /**
     * This is the main method of the transformation. It performs the
     * transformation by invoking methods that generate each part of the
     * target language model.
     */
    override boolean transformInPlace
    (
        StateMachine stateMachine,
        TransformationContext context
    )
    {
        if (stateMachine === null
            || context === null
            || !(context instanceof CppGenerationTansformationContext))
            return false
        if (stateMachine.top === null) return true
        this.discardedStates = (context as CppGenerationTansformationContext).discardedStates
        this.cpp = (context as CppGenerationTansformationContext).cpp
        this.capsuleContext = (context as CppGenerationTansformationContext).capsuleContext
        machine = stateMachine
        try
        {
            cppCapsuleClass =
                cpp.getCppClass( CppCodePattern.Output.CapsuleClass, capsuleContext )
            generateStatesDeclaration
            generateStateNamesTableDeclaration
            generateCurrentStateField
            generateHistoryTableDeclaration
            saveHistoryFunction = getSaveHistoryFunction
            checkHistoryFunction = getCheckHistoryFunction
            generateAllUserActionFunctions
            generateAllUserGuardFunctions
            generateAllActionChainFunctions
            generateAllChoicePointFunctions
            generateAllJunctionFunctions
            generateAllStateFunctions
            generateInjectFunc
            generateInitializeFunc
            generateGetCurrentStateStrFunc
            generateCompilationUnit
            return true
        }
        catch (Exception e)
        {
            CodeGenPlugin.error("[FlatModel2Cpp] error generating C++ code from flat state machine", e)
            return false
        }
    }

    protected def
    create new Variable( cppCapsuleClass.getType().ptr(), "this" )
    getThisRef()
    {
    }

    /**
     * Builds an enum type for the states of the state machine.
     *
     * The generated code would be something like:
     *
     * <p><pre>
     * <code>enum State { s0, s1, s1_s0, s1_s1, s2, ..., UNVISITED };</code>
     * </pre>
     *
     * The enumerators for former composite states go first so that they can be used as indices
     * to the history table.
     */
    protected def generateStatesDeclaration()
    {
        val numSubStates = machine.allSubstates.size
        statesDeclaration = new CppEnum( GlobalConstants.STATE_TYPE_NAME )

        val comparator = new VertexNameComparator
        // Add enumerators for each of the former composite states
        var State[] formerCompositeStates = newArrayOfSize( discardedStates.size )
        discardedStates.toArray( formerCompositeStates )
        Arrays.sort( formerCompositeStates, comparator )
        for (s : formerCompositeStates)
        {
            val name = s.cachedFullSMName.makeValidCName
            val stateEnum = new Enumerator( name )
            statesDeclaration.add( stateEnum )
            stateEnumerators.put( s, stateEnum )
            stateNames.put( s, name )
            states.add( s )
        }
        // Add enumerators for the remaining states
        var State[] otherStates = newArrayOfSize( numSubStates )
        machine.allSubstates.toList.toArray( otherStates )
        Arrays.sort( otherStates, comparator )
        for (s : otherStates)
        {
            val name = s.cachedFullSMName.makeValidCName
            val stateEnum = new Enumerator( name )
            statesDeclaration.add( stateEnum )
            stateEnumerators.put( s, stateEnum )
            stateNames.put( s, name )
            states.add( s )
        }
        // Add an enumerator for the dummy UNVISITED state last
        val unvisitedStateEnum = new Enumerator( GlobalConstants.UNVISITED )
        statesDeclaration.add( unvisitedStateEnum )
        stateEnumerators.put( StateNestingFlattener.UNVISITED, unvisitedStateEnum )
    }

    protected def generateStateNamesTableDeclaration()
    {
        val numStates = stateNames.size

        val stateNamesType = PrimitiveType.CHAR.ptr().const_().arrayOf( new IntegralLiteral( numStates ))
        val stateNamesInitializer = new BlockInitializer( stateNamesType )

        for (name : stateNames.values)
            stateNamesInitializer.addExpression( new StringLiteral( name ) )
        stateNamesTableDeclaration =
            new MemberField
            (
                stateNamesType,
                GlobalConstants.STATE_NAMES_TABLE_NAME,
                stateNamesInitializer
            )
        val ctor =
            cpp.getConstructor
            (
                CppCodePattern.Output.CapsuleClass,
                capsuleContext
            )
        for (s : states)
        {
            val stateName = stateNames.get( s )
            val stateEnum = stateEnumerators.get( s )
            val stateNameInitStmt =
                new ExpressionStatement
                (
                    new BinaryOperation
                    (
                        new IndexExpr
                        (
                            new ElementAccess( stateNamesTableDeclaration ),
                            new ElementAccess( stateEnum )
                        ),
                        BinaryOperation.Operator.ASSIGN,
                        new StringLiteral( stateName )
                    )
                )
            ctor.add( stateNameInitStmt )
        }
    }

    /**
     * Generates a field to hold the current state.
     *
     * The generated code would be something like:
     *
     * <p><pre>
     * <code>
     * State currentState;
     * </code>
     * </pre>
     */
    protected def generateCurrentStateField()
    {
        currentStateField =
            new MemberField
            (
                statesDeclaration.getType(),
                GlobalConstants.CURRENT_STATE_FIELD_NAME
            )
    }

    /**
     * Generates a declaration for the history table for the state machine.
     *
     * The generated code would be something like:
     *
     * <p>
     * <code>State[] history = { UNDEFINED, ..., UNDEFINED };
     */
    protected def generateHistoryTableDeclaration()
    {
        val numStates = discardedStates.size
        if (numStates > 0)
        {
            val historyTableType =
                statesDeclaration.getType().arrayOf( new IntegralLiteral(numStates) )
            val tableDecl =
                new MemberField
                (
                    historyTableType,
                    GlobalConstants.HISTORY_TABLE_NAME// , initializer
                )
            val counter =
                new Variable
                (
                    LinkageSpec.UNSPECIFIED,
                    PrimitiveType.INT,
                    "i",
                    new IntegralLiteral(0)
                )
            val counterDecl = new VariableDeclarationStatement( counter )
            val historyInitLoopStmt =
                new WhileStatement
                (
                    new LogicalComparison(
                        new ElementAccess( counter ),
                        LogicalComparison.Operator.LESS_THAN,
                        new IntegralLiteral( numStates )
                    )
                )
            val historyEntryInitStmt =
                new ExpressionStatement
                (
                    new BinaryOperation
                    (
                        new IndexExpr
                        (
                            new ElementAccess( tableDecl ),
                            new UnaryOperation
                            (
                                UnaryOperation.Operator.POST_INCREMENT,
                                new ElementAccess( counter )
                            )
                        ),
                        BinaryOperation.Operator.ASSIGN,
                        new ElementAccess
                        (
                            stateEnumerators.get( StateNestingFlattener.UNVISITED )
                        )
                    )
                )
            historyInitLoopStmt.add( historyEntryInitStmt )
            val ctor =
                cpp.getConstructor
                (
                    CppCodePattern.Output.CapsuleClass,
                    capsuleContext
                )
            ctor.add( counterDecl )
            ctor.add( historyInitLoopStmt )
            historyTableDeclaration = tableDecl
        }
    }

    /**
     * Generates a function that saves history.
     *
     * The code generated is as follows:
     *
     * <p><pre>
     * <code>
     * void saveHistory(State compositeState, State subState) {
     *     history[compositeState] = subState;
     * }
     * <code>
     * </pre>
     *
     * where <code>State</code> is the capsule's state type (an enum) and
     * <code>history</code> is the capsule's history table.
     *
     * <p><b>Note:</b> The current implementation generates this as a normal
     * function but it should be either a macro or an inline function.
     * However the C/C++ language model does not currently support these.
     *
     * @see
     *  #generateStatesDeclaration
     *  #generateHistoryTableDeclaration
     */
    protected def create
        if (historyTableDeclaration !== null)
            new MemberFunction
            (
                PrimitiveType.VOID,
                GlobalConstants.SAVE_HISTORY_FUNC_NAME
            )
    getSaveHistoryFunction()
    {
        if (it == null) return
        val param1 = new Parameter( stateType , "compositeState" )
        val param2 = new Parameter( stateType , "subState" )
        // Body is "history[compositeState] = subState;"
        val body =
            new ExpressionStatement
            (
                new BinaryOperation
                (
                    new IndexExpr
                    (
                        new ElementAccess( historyTableDeclaration ),
                        new ElementAccess( param1 )
                    ),
                    BinaryOperation.Operator.ASSIGN,
                    new ElementAccess( param2 )
                )
            )
        add( param1 )
        add( param2 )
        add( body )
    }

    /**
     * Generates a function that checks history.
     *
     * The code generated is as follows:
     *
     * <p><pre>
     * <code>
     * void checkHistory(State compositeState, State subState) {
     *     return history[compositeState] == subState;
     * }
     * <code>
     * </pre>
     *
     * where <code>State</code> is the capsule's state type (an enum) and
     * <code>history</code> is the capsule's history table.
     *
     * <p><b>Note:</b> The current implementation generates this as a normal
     * function but it should be either a macro or an inline function.
     * However the C/C++ language model does not currently support these.
     *
     * @see
     *  #generateStatesDeclaration
     *  #generateHistoryTableDeclaration
     */
    protected def create
        if (historyTableDeclaration !== null)
            new MemberFunction
            (
                PrimitiveType.BOOL,
                GlobalConstants.CHECK_HISTORY_FUNC_NAME
            )
    getCheckHistoryFunction()
    {
        if (it == null) return
        val param1 = new Parameter( stateType, "compositeState" )
        val param2 = new Parameter( stateType, "subState" )
        // Body is "history[compositeState] = subState;"
        val body =
            new ReturnStatement
            (
                new LogicalComparison
                (
                    new IndexExpr
                    (
                        new ElementAccess( historyTableDeclaration ),
                        new ElementAccess( param1 )
                    ),
                    LogicalComparison.Operator.EQUIVALENT,
                    new ElementAccess( param2 )
                )
            )
        add( param1 )
        add( param2 )
        add( body )
    }

    /**
     * Generates declarations for action functions for actions occurring in
     * a transition's chain.
     *
     * <p>This generation includes not only transition actions from the original
     * model, but also state entry and exit actions, since the flattening
     * transformation moves entry and exit actions to transition's action chains.
     *
     * <p><b>Notes:</b>
     * <p>The "save history" action is not generated by this function, as
     * there is only one declaration for the save history.
     */
     protected def generateAllUserActionFunctions()
     {
        for (t : machine.allTransitions)
        {
            if (t.actionChain !== null && t.actionChain.actions !== null)
            {
                for (a : t.actionChain.actions)
                {
                    // The same action may occur in several transition chains but
                    // the function declaration is generated only once, as the
                    // visitor that performs the generation memoizes its results,
                    // so that subsequent invocations of the following method will
                    // yield the same function declaration object.
                    generateActionFunc( a, t )
                }
            }
        }
     }

    /**
     * Generates the function declaration for an action occurring in
     * a transition's chain.
     */
    protected def dispatch generateActionFunc( SaveHistory a, Transition t )
    {
        // This is superfluous since this function is generated by
        // {@link generateSaveHistoryFunction}, but we need to have a function
        // that accepts {@link SaveHistory} actions when processing chains.
        actionDeclarationGenerator.visit( a, null )
    }

    protected def dispatch generateActionFunc(ActionCode a, Transition t) {
        val ctx =
            new ActionDeclarationGenerator.UserActionContext
            (
                RTMessageType,
                getRTDataType( t )
            )
            as ActionDeclarationGenerator.Context
        val f = actionDeclarationGenerator.visit( a, ctx )
        userActionFunctions.put( a, f )
    }

    /**
     * Generates declarations for guard functions for guards occurring in
     * a transition's chain.
     *
     * <p><b>Notes:</b>
     * <p>The "check history" action is not generated by this function, as
     * there is only one declaration for the check history guard.
     */
     protected def generateAllUserGuardFunctions()
     {
        for (t : machine.allTransitions)
        {
            if (t.guard !== null)
            {
                generateGuardFunc( t.guard, t )
            }
        }
     }

    /**
     * Generates the function declaration for an action occurring in
     * a transition's chain.
     */
    protected def dispatch generateGuardFunc( CheckHistory g, Transition t )
    {
        // This is superfluous since this function is generated by
        // {@link generateSaveHistoryFunction}, but we need to have a function
        // that accepts {@link CheckHistory} actions when processing chains.
        // guardDeclarationGenerator.visit( g, null )
    }

    protected def dispatch generateGuardFunc( Guard g, Transition t )
    {
        if (g.body !== null && !(g.body instanceof CheckHistory))
        {
            var sourceTransition = getTransitionChainUniqueSource( t )
            sourceTransition = if (sourceTransition === null) t else sourceTransition
            val ctxt =
                new GuardDeclarationGenerator.UserGuardContext
                (
                    RTMessageType,
                    getRTDataType( sourceTransition )
                )
                as GuardDeclarationGenerator.Context
            val f = guardDeclarationGenerator.visit( g, ctxt )
            userGuardFunctions.put( g, f )
        }
    }


    /**
     * Generate function declarations for transition action chains.
     *
     * <p>Each function generated will have a sequence of calls, invoking either
     * the action functions generated by {@link generateActionFunc} for
     * transition, state entry and state exit actions, as well as invoking
     * "save history" actions produced by the flattening transformation.
     */
    protected def generateAllActionChainFunctions()
    {
        for (t : machine.allTransitions)
        {
            if (t.actionChain !== null && !t.actionChain.actions.empty)
            {
                val f = generateActionChainFunc( t )
                actionChainFunctions.put( t.actionChain, f )
            }
        }
    }

    /**
     * Generates the function declaration for a single action chain.
     */
    protected def
    create
        new MemberFunction( PrimitiveType.VOID, t.actionChain.funcName.toString )
    generateActionChainFunc( Transition t )
    {
        val param = new Parameter( RTMessageType, GlobalConstants.CHAIN_FUNC_PARAM )
        add( param )
        if (t.actionChain !== null)
        {
            for (ActionCode a : t.actionChain.actions)
            {
                val call = getActionInvocation( a, param )
                if (call !== null) add( call as ExpressionStatement )
            }
        }
    }

    /**
     * Generates a call to an action, either user action or action generated by
     * the transformation.
     */
    protected def dispatch getActionInvocation( SaveHistory action, Parameter param )
    {
        val ctx =
            new ActionInvocationGenerator.SaveHistoryActionContext
            (
                saveHistoryFunction,
                stateEnumerators
            )
            as ActionInvocationGenerator.Context
        actionInvocationGenerator.visit( action, ctx )
    }

    protected def dispatch getActionInvocation( ActionCode action, Parameter param )
    {
        val funcDecl = userActionFunctions.get( action )
        val ctx =
            new ActionInvocationGenerator.UserActionContext
            (
                funcDecl,
                new ElementAccess( param )
            )
            as ActionInvocationGenerator.Context
        actionInvocationGenerator.visit( action, ctx )
    }

    /**
     * Generates all functions corresponding to choice points.
     */
    protected def generateAllChoicePointFunctions()
    {
        for (c : machine.allChoicePoints)
        {
            val f = getChoicePointFunction( c )
            choicePointFunctions.put( c, f )
        }
    }

    /**
     * Generates the function corresponding to a given choice point.
     */
    protected def
    create
        new MemberFunction( statesDeclaration.type, p.funcName.toString )
    getChoicePointFunction( ChoicePoint p )
    {
        val param = new Parameter( RTMessageType, GlobalConstants.CHAIN_FUNC_PARAM )
        val defRet = new ReturnStatement( new ElementAccess(currentStateField) )
        val cond = new ConditionalStatement()
        var elseBranch = false
        var ignoreBranch = false
        for (t : p.directOutgoingTransitions)
        {
            var CodeBlock newBranch = null
            ignoreBranch = false
            if (t.guard !== null)
            {
                var guard = getGuardInvocation( t.guard, param )
                newBranch = cond.add( guard )
            }
            else if (!elseBranch)
            {
                // If there is a transition without a guard, we chose it as
                // the default branch
                newBranch = cond.defaultBlock
                elseBranch = true
            }
            else
                // If there is more than one transition without a branch, we
                // chose the first and ignore the rest
                ignoreBranch = true
            if (!ignoreBranch)
            {
                if (t.actionChain !== null && !t.actionChain.actions.empty)
                {
                    val actFunc = actionChainFunctions.get( t.actionChain )
                    val arg = new ElementAccess( param )
                    val call = new FunctionCall( actFunc, arg )
                    newBranch.add( new ExpressionStatement( call ) )
                }
                var destStmt = getDestination( t, false, new ElementAccess(param) )
                newBranch.add( destStmt )
            }
        }
        add( param )
        add( cond )
        add( defRet )   // If no guard is chosen, we remain the current state
    }

    protected def getGuardInvocation( Guard guard, Parameter param )
    {
        if (guard.body !== null && guard.body instanceof CheckHistory)
            getCheckHistoryGuardInvocation( guard.body as CheckHistory, param )
        else
            getOtherGuardInvocation( guard, param )
    }

    protected def getCheckHistoryGuardInvocation( CheckHistory guard, Parameter param )
    {
        val ctx =
            new GuardInvocationGenerator.CheckHistoryGuardContext
            (
                checkHistoryFunction,
                stateEnumerators
            )
            as GuardInvocationGenerator.Context
        guardInvocationGenerator.visit( guard, ctx )
    }

    protected def getOtherGuardInvocation( Guard guard, Parameter param )
    {
        val funcDecl = userGuardFunctions.get( guard )
        val ctx =
            new GuardInvocationGenerator.UserGuardContext
            (
                funcDecl,
                new ElementAccess(param)
            )
            as GuardInvocationGenerator.Context
        guardInvocationGenerator.visit( guard, ctx )
    }

    /**
     * Generates all functions corresponding to junction points.
     */
    protected def generateAllJunctionFunctions()
    {
        for (j : machine.allJunctionPoints)
        {
            val f = getJunctionPointFunction( j )
            junctionPointFunctions.put( j, f )
        }
    }

    /**
     * Generates the function corresponding to a specific junction point.
     */
    protected def
    create
        new MemberFunction( statesDeclaration.type, p.funcName.toString )
    getJunctionPointFunction( JunctionPoint p )
    {
        val param = new Parameter( RTMessageType, GlobalConstants.JUNC_FUNC_PARAM )
        val t = p.directOutgoingTransitions.get(0) // There should be only one. // TODO: add warning if not
        val block = new ArrayList<Statement>
        if (t.actionChain !== null && !t.actionChain.actions.empty)
        {
            val actFunc = actionChainFunctions.get( t.actionChain )
            val arg = new ElementAccess( param )
            val call = new FunctionCall( actFunc, arg )
            block.add( new ExpressionStatement(call) )
        }
        val d = getDestination( t, false, new ElementAccess(param) )
        block.add( d )
        add( param )
        add( block )
    }

    /**
     * Generates all functions corresponding to states.
     */
    protected def generateAllStateFunctions()
    {
        for (s : machine.allSubstates)
        {
            val f = generateStateFunc( s )
            stateFunctions.put( s, f )
        }
    }

    /**
     * Generates the function corresponding to a given state.
     */
    protected def
    create
        new MemberFunction( statesDeclaration.type, state.funcName.toString )
    generateStateFunc( State state )
    {
        val param = new Parameter( RTMessageType, GlobalConstants.STATE_FUNC_PARAM )
        val table = getPortSignalTransitionsTable( state )
        val defRet = new ReturnStatement( new ElementAccess(currentStateField) )
        val portSwitch = new SwitchStatement( getPortCond(param) )
        for (port : table.keySet)
        {
            val sigSwitch = new SwitchStatement( getSigCond( param ) )
            val portSignals = table.get( port )
            for (signal : portSignals.keySet)
            {
                val transitions = portSignals.get( signal )
                val sigCase = generatePortSignalCase( transitions, signal, param, defRet )
                sigSwitch.add( sigCase )
            }
            val defaultCase = generateDefaultMessageCase()
            sigSwitch.add( defaultCase )
            val portCase = new SwitchClause( enumeratorFor( port ) )
            portCase.add( sigSwitch )
            portCase.add( defRet )
            portSwitch.add( portCase )
        }
        val defaultCase = generateDefaultMessageCase()
        portSwitch.add( defaultCase )
        val body = portSwitch
        add( param )
        add( body )
        add( defRet )
    }

    protected def generateDefaultMessageCase()
    {
        val defaultClause = new SwitchClause()
        val callStmt =
            new ExpressionStatement
            (
                UMLRTRuntime.UMLRTCapsule.unexpectedMessage( new ElementAccess(thisRef) )
            )
        defaultClause.add( callStmt )
        defaultClause
    }

    /**
     * Builds a table that contains for each port and signal, all the outgoing
     * transitions of a given state whose trigger has that port and signal.
     *
     * @param s     a {@link State}
     * @return a table T indexed by {@link Port}s such that for each port p,
     *         the entry T[p] is a table T' indexed by {@link Signal}s such that
     *         for each signal e, T'[e] contains all outgoing {@link Transition}s
     *         from s whose trigger has port p and signal e. In other words,
     *         T[p][e] contains all transitions from s whose trigger has port p
     *         and signal e.
     */
    protected def getPortSignalTransitionsTable( State s )
    {
        val table = newHashMap
        for (transition : s.allOutgoingTransitions)
        {
            for (trigger : transition.triggers)
            {
                if (trigger instanceof RTTrigger)
                {
                    val signal = trigger.signal
                    for (port : trigger.ports)
                    {
                        if (!table.containsKey(port))
                        {
                            table.put( port, new HashMap<Signal, LinkedHashSet<Transition>> )
                        }
                        val portSignals = table.get( port )
                        if (!portSignals.containsKey( signal ))
                        {
                            portSignals.put( signal, newLinkedHashSet )
                        }
                        portSignals.get( signal ).add( transition )
                    }
                }
            }
        }
        return table
    }

    protected def generateMessageDecodeInit( Parameter msg, org.eclipse.papyrusrt.xtumlrt.common.Parameter modelParam )
    {
        if( modelParam == null )
            return UMLRTRuntime.UMLRTInMessage.decodeInit(
                    new ElementAccess( msg ),
                    StandardLibrary.NULL() );

        return UMLRTRuntime.UMLRTInMessage.decodeInit(
                new ElementAccess( msg ),
                TypesUtil.createRTTypeAccess( cpp, modelParam, modelParam.type ) )
    }

    /**
     * Generates the body of the case block for a particular port/signal pair.
     *
     * <p>It sorts the transitions by nesting depth, and filters out those
     * which have no guard except for the first one.
     *
     * <p>If only one transition is left, the generated case contains the code
     * for it, possibly with a conditional if it had a guard.
     *
     * <p>If there is more than one transition left, then they must have guards
     * except possibly for the last one. In this case we generate a conditional.
     */
    protected def generatePortSignalCase
    (
        LinkedHashSet<Transition> transitions,
        Signal signal,
        Parameter param,
        ReturnStatement defRet
    )
    {
        val sigCase = new SwitchClause( enumeratorFor(signal) )
        val sortedTransitions = sortTransitions( transitions )
        if (sortedTransitions.length == 1)
        {
            val t = sortedTransitions.get(0)
            val branch = new ArrayList<Statement>
            if (t.actionChain !== null && !t.actionChain.actions.empty)
            {
                val actFunc = actionChainFunctions.get( t.actionChain )
                val arg = new ElementAccess( param )
                val call = new FunctionCall( actFunc, arg )
                val callStmt = new ExpressionStatement( call )
                branch.add( callStmt )
            }
            val d = getDestination( t, false, new ElementAccess(param) )
            branch.add( d )

            if( signal.parameters.size > 0 )
                sigCase.add( new ExpressionStatement( generateMessageDecodeInit( param, signal.parameters.get( 0 ) ) ) )
            else
                sigCase.add( new ExpressionStatement( generateMessageDecodeInit( param, null ) ) )

            if (t.guard === null)
            {
                for (stmt : branch) sigCase.add( stmt )
            }
            else
            {
                // TODO: add a warning if there is a condition and only one transition
                val cond = new ConditionalStatement()
                val guard = getGuardInvocation( t.guard, param )
                cond.add( guard ).add( branch )
                sigCase.add( cond )
                sigCase.add( defRet )
            }
        }
        else
        {
            val cond = new ConditionalStatement()
            for (t : sortedTransitions)
            {
                val branch = new ArrayList<Statement>
                if (t.actionChain !== null && !t.actionChain.actions.empty)
                {
                    val actFunc = actionChainFunctions.get( t.actionChain )
                    val arg = new ElementAccess( param )
                    val call = new FunctionCall( actFunc, arg )
                    val callStmt = new ExpressionStatement( call )
                    branch.add( callStmt )
                }
                val d = getDestination( t, false, new ElementAccess(param) )
                branch.add( d )
                if (t.guard === null)
                { // Only the last one should have no guard
                    for (stmt : branch) cond.defaultBlock.add( stmt )
                }
                else
                {
                    val guard = getGuardInvocation( t.guard, param )
                    cond.add( guard ).add( branch )
                    sigCase.add( cond )
                    sigCase.add( defRet )
                }
            }
        }
        return sigCase
    }

    /**
     * Sorts transitions for the same port/signal pair by order of nesting
     * depth, marking the first non-guarded transition as the default transition
     * and filters-out all other non-guarded transitions.
     */
    protected def sortTransitions( LinkedHashSet<Transition> transitions )
    {
        var Transition[] transitionsArray = newArrayOfSize( transitions.size )
        transitions.toArray( transitionsArray )
        transitionsArray = transitionsArray.sortWith( new TransitionDepthComparator )
        val sortedList = newArrayList
        var Transition defaultTransition = null
        var i = 0
        while (defaultTransition === null && i < transitionsArray.length)
        {
            val t = transitionsArray.get( i )
            val hasNonEmptyGuard = t.hasNonEmptyGuard
            if (hasNonEmptyGuard)
            {
                sortedList.add( t )
            }
            else
            {
                defaultTransition = t
            }
            i++
        }
        sortedList.add( defaultTransition )
        return sortedList
    }

    private static def boolean hasNonEmptyGuard( Transition t )
    {
        t.guard !== null
        && t.guard instanceof Guard
        && (t.guard as Guard).body.source != ""
    }

    /**
     * Obtains the function call corresponding to a transition's destination.
     *
     * @param t - The {@link Transition}
     * @param init - Whether we are looking for the destination to be obtained
     *               in the initialized method or in the inject method.
     * @param destArg - The argument to pass to the destination if it is a
     *                  function.
     */
    protected def getDestination( Transition t, boolean init, Expression destArg )
    {
        var Expression retVal
        if (t.targetVertex instanceof ChoicePoint)
        {
            val c = t.targetVertex as ChoicePoint
            val func = getChoicePointFunction( c )
            retVal = new FunctionCall( func, destArg )
        }
        else if (t.targetVertex instanceof JunctionPoint)
        {
            val j = t.targetVertex as JunctionPoint
            val func = getJunctionPointFunction( j )
            retVal = new FunctionCall(func, destArg)
        }
        else
        {
            val s = t.targetVertex as State
            retVal = enumeratorFor(s)
        }
        if (init)
        {
            new ExpressionStatement
            (
                new BinaryOperation
                (
                    new ElementAccess( currentStateField ),
                    BinaryOperation.Operator.ASSIGN,
                    retVal
                )
            )
        }
        else
        {
            new ReturnStatement( retVal )
        }
    }

    protected def generateMsgFieldInit( Parameter param, MemberFunction func )
    {
        val msgAssn =
            new ExpressionStatement
            (
                new BinaryOperation
                (
                    UMLRTRuntime.UMLRTCapsule.msg,
                    BinaryOperation.Operator.ASSIGN,
                    new AddressOfExpr( new ElementAccess( param ) )
                )
            )
        func.add( msgAssn )
    }

    /**
     * Generates the parameter for the main 'inject' function
     */
    protected  def getInjectFuncParam()
    {
        if (injectFuncParam === null)
            injectFuncParam =
                new Parameter
                (
                    RTMessageType,
                    GlobalConstants.INJECT_FUNC_PARAM
                )
        return injectFuncParam
    }

    /**
     * Generates the main 'inject' function that receives and handles events.
     */
    protected def generateInjectFunc()
    {
        injectFunc =
            new MemberFunction
            (
                PrimitiveType.VOID,
                GlobalConstants.INJECT_FUNC_NAME
            )
        injectFunc.setVirtual()
        injectFunc.add( getInjectFuncParam )
        generateMsgFieldInit( injectFuncParam, injectFunc )
        val stateSwitch = new SwitchStatement( stateCond )
        for (s : machine.allSubstates)
        {
            val stateCase = new SwitchClause( enumeratorFor( s ) )
            val func = stateFunctions.get( s )
            val arg = new ElementAccess( getInjectFuncParam() )
            val call = new FunctionCall( func, arg )
            val update =
                new BinaryOperation
                (
                    new ElementAccess( currentStateField ),
                    BinaryOperation.Operator.ASSIGN,
                    call
                )
            stateCase.add( new ExpressionStatement(update) )
            stateCase.add( new BreakStatement )
            stateSwitch.add( stateCase )
        }
        val defaultCase = generateDefaultStateCase
        stateSwitch.add( defaultCase )
        injectFunc.add( stateSwitch )
    }

    protected def generateDefaultStateCase()
    {
        val defaultClause = new SwitchClause()
        val defStmt = new BreakStatement()
        defaultClause.add( defStmt )
        defaultClause
    }

    protected def getInitializeFuncParam()
    {
        if (initializeFuncParam === null)
            initializeFuncParam =
                new Parameter
                (
                    RTMessageType,
                    GlobalConstants.INITIALIZE_FUNC_PARAM
                )
        return initializeFuncParam
    }

    /**
     * Build the initialize function which performs the initial transition.
     *
     * <p>This assumes that the top level of the state machine must have an
     * initial pseudo-state, and that there is exactly one outgoing transition
     * from such initial point.
     *
     * <p> If there is no initial point, the body of the initialize method is
     * empty.
     */
    protected def generateInitializeFunc()
    {
        initializeFunc =
            new MemberFunction
            (
                PrimitiveType.VOID,
                GlobalConstants.INITIALIZE_FUNC_NAME
            )
        initializeFunc.setVirtual()
        val param = getInitializeFuncParam
        initializeFunc.add( param )
        generateMsgFieldInit( param, initializeFunc )
        if (machine.top.initial !== null
            && !machine.top.initial.allDirectOutgoingTransitions.empty)
        {
            val initialTransition = machine.top.initial.directOutgoingTransitions.get(0)
            if (initialTransition.actionChain !== null
                && !initialTransition.actionChain.actions.empty)
            {
                val actFunc = actionChainFunctions.get( initialTransition.actionChain )
                val arg = new ElementAccess( getInitializeFuncParam )
                val call = new FunctionCall( actFunc, arg )
                initializeFunc.add( call )
            }
            val d = getDestination( initialTransition, true, new ElementAccess(param) )
            initializeFunc.add( d )
        }
    }

    protected def generateGetCurrentStateStrFunc()
    {
        getCurrentStateStringFunc =
            new MemberFunction
            (
                PrimitiveType.CHAR.ptr().const_(),
                GlobalConstants.GET_CURR_STATE_NAME_FUNC_NAME,
                CVQualifier.CONST
            )
        val body =
            new ReturnStatement
            (
                new IndexExpr
                    (
                        new ElementAccess( stateNamesTableDeclaration ),
                        new ElementAccess( currentStateField )
                    )
            )
        getCurrentStateStringFunc.add( body )
    }

    /**
     * Generates the compilation unit for the state machine (*)
     *
     * <p><b>Notes:</b> This implementation generates only a list of elements
     * to be consumed by the capsule generator which is assumed to be
     * responsible for putting together the full compilation unit.
     */
    protected def generateCompilationUnit()
    {
        cppCapsuleClass.addMember( CppClass.Visibility.PUBLIC, injectFunc )
        cppCapsuleClass.addMember( CppClass.Visibility.PUBLIC, initializeFunc )
        cppCapsuleClass.addMember( CppClass.Visibility.PUBLIC, getCurrentStateStringFunc )
        cppCapsuleClass.addMember( CppClass.Visibility.PRIVATE, statesDeclaration )
        cppCapsuleClass.addMember( CppClass.Visibility.PRIVATE, stateNamesTableDeclaration )
        cppCapsuleClass.addMember( CppClass.Visibility.PRIVATE, currentStateField )

        var ctor = cpp.getConstructor( CppCodePattern.Output.CapsuleClass, capsuleContext );
        if( ctor != null )
            ctor.addFieldInitializer(
                currentStateField,
                new ElementAccess( stateEnumerators.get( StateNestingFlattener.UNVISITED ) )
            )

        if (historyTableDeclaration !== null)
        {
            cppCapsuleClass.addMember( CppClass.Visibility.PRIVATE, historyTableDeclaration )
            cppCapsuleClass.addMember( CppClass.Visibility.PRIVATE, saveHistoryFunction )
            cppCapsuleClass.addMember( CppClass.Visibility.PRIVATE, checkHistoryFunction )
        }
        var ActionCode[] userActionFunctionArray = newArrayOfSize( userActionFunctions.size )
        userActionFunctions.keySet.toArray( userActionFunctionArray )
        userActionFunctionArray = userActionFunctionArray.sortWith( new ActionNameComparator )
        for (action : userActionFunctionArray)
        {
            cppCapsuleClass.addMember( CppClass.Visibility.PRIVATE, userActionFunctions.get( action ) )
        }
        var Guard[] userGuardFunctionArray = newArrayOfSize( userGuardFunctions.size )
        userGuardFunctions.keySet.toArray( userGuardFunctionArray )
        userGuardFunctionArray = userGuardFunctionArray.sortWith( new GuardNameComparator )
        for (guard : userGuardFunctionArray)
        {
            cppCapsuleClass.addMember( CppClass.Visibility.PRIVATE, userGuardFunctions.get( guard ) )
        }
        var ActionChain[] actionChainFunctionArray = newArrayOfSize( actionChainFunctions.size )
        actionChainFunctions.keySet.toArray( actionChainFunctionArray )
        actionChainFunctionArray = actionChainFunctionArray.sortWith( new ChainNameComparator )
        for (chain : actionChainFunctionArray)
        {
            cppCapsuleClass.addMember( CppClass.Visibility.PRIVATE, actionChainFunctions.get( chain ) )
        }
        var JunctionPoint[] junctionPointFunctionArray = newArrayOfSize( junctionPointFunctions.size )
        junctionPointFunctions.keySet.toArray( junctionPointFunctionArray )
        junctionPointFunctionArray = junctionPointFunctionArray.sortWith( new VertexNameComparator )
        for (junction : junctionPointFunctionArray)
        {
            cppCapsuleClass.addMember( CppClass.Visibility.PRIVATE, junctionPointFunctions.get( junction ) )
        }
        var ChoicePoint[] choicePointFunctionArray = newArrayOfSize( choicePointFunctions.size )
        choicePointFunctions.keySet.toArray( choicePointFunctionArray )
        choicePointFunctionArray = choicePointFunctionArray.sortWith( new VertexNameComparator )
        for (choice : choicePointFunctionArray)
        {
            cppCapsuleClass.addMember( CppClass.Visibility.PRIVATE, choicePointFunctions.get( choice ) )
        }
        var State[] stateFunctionArray = newArrayOfSize( stateFunctions.size )
        stateFunctions.keySet.toArray( stateFunctionArray )
        stateFunctionArray = stateFunctionArray.sortWith( new VertexNameComparator )
        for (state : stateFunctionArray)
        {
            cppCapsuleClass.addMember( CppClass.Visibility.PRIVATE, stateFunctions.get( state ) )
        }
    }

    /**
     * Auxiliary methods
     */

    private dispatch def enumeratorFor( State s )
    {
        new ElementAccess( stateEnumerators.get(s) )
    }

    private dispatch def enumeratorFor( Port p )
    {
        val enumerator =
            cpp.getEnumerator( CppCodePattern.Output.PortId, p, capsuleContext )
        new ElementAccess( enumerator )
    }

    private dispatch def enumeratorFor( Signal s )
    {
        cpp.getEnumeratorAccess( CppCodePattern.Output.SignalId, s, null )
    }

    private def getStateType()
    {
        statesDeclaration.getType()
    }

    private def getRTMessageType()
    {
        UMLRTRuntime.UMLRTInMessage.Element.getType().ref().const_()
    }

    private def getRTDataType( Transition t )
    {
        var org.eclipse.papyrusrt.codegen.lang.cpp.Type rtdataType = null
        var NamedElement typeOwner = null
        val Collection<Type> types = newLinkedHashSet
        for (trigger : t.triggers)
        {
            if (trigger instanceof RTTrigger)
            {
                val params = trigger.signal.parameters
                if (!params.empty)
                {
                    val first = params.get(0)
                    types.add( first.type )
                    typeOwner = first
                }
            }
        }
        if (types.size == 1)
        {
            val type = types.get(0)
            rtdataType = TypesUtil.createCppType( cpp, typeOwner, type )
        }
        rtdataType
    }

    /**
     * Builds an expression to obtain the port enum id for the switch.
     *
     * <p>It assumes that the message parameter to the inject function is a
     * pointer to the RTMessage type, as returned by {@link #getRTMessageType},
     * this is, the signature of the inject function must be:
     *
     * <p><pre>
     * <code>void inject(UMLRTMessage * msg)</code>
     * </pre>
     *
     * <p>It also assumes that the port id and signal id are accessible from
     * this type. Basically the assumption is that the relevant definitions are
     * as follows:
     *
     * <p>
     * <pre>
     * <code>
     * class UMLRTMessage : ... {
     * public:
     *     UMLRTPort * destPort;
     *     UMLRTSignal * signal;
     * }
     *
     * struct UMLRTPort {
     *     int id;
     *     // ...
     * }
     *
     * class UMLRTSignal {
     * public:
     *     int id;
     *     // ...
     * }
     * </code>
     * </pre>
     *
     * <p>... where the typed <code>UMLRTPortId</code> and
     * <code>UMLRTSignalId</code> can be cast to the <code>Port</code> and
     * <code>Signal</code> enum types generated in the state machine's class.
     *
     * <p>Given this assumptions, the port condition generated has the form:
     *
     * <p><pre><code>(Port)(msg->destPort)->id</code></pre>
     *
     * <p>and the signal condition is:
     *
     * <p><pre><code>(ProtocolX::Signal)(msg->signal)->getId()</code></pre>
     *
     * <p>where <code>ProtocolX</code> is the name of the port's protocol
     */
    private def getPortCond( Parameter param )
    {
        val messagePortField = UMLRTMessage.destPort
        val roleIdField = UMLRTCommsPortRole.id
        new MemberAccess
        (
            UMLRTCommsPort.role
            (
                new MemberAccess
                (
                    new ElementAccess( param ),
                    messagePortField
                )
            ),
            roleIdField
        )
    }

    private def getSigCond( Parameter param )
    {
        UMLRTRuntime.UMLRTInMessage.getSignalId(
            new ElementAccess( param )
        )
    }

    private def getStateCond()
    {
        new ElementAccess( currentStateField )
    }

    /**
     * Looks for the source stable state which of the transition chain that contains the given
     * transition t, if it is unique, this is, if there is a path
     *
     * <p>s --t1--> p_1 --t2--> p_2 --t3--> ... --t--> v
     *
     * <p>where t is the given transition, s is a state, each p_i is a pseudo-state with exactly
     * one incoming transition. If any p_i has more than one incoming transition, then this
     * method returns null, otherwise it returns t1.
     */
    private def getTransitionChainUniqueSource( Transition t )
    {
        val visited = new HashSet<Vertex>
        var vertex = t.sourceVertex
        var sourceTransition = t
        while (vertex instanceof Pseudostate
                && !(vertex as Pseudostate).directIncomingTransitions.isEmpty
                && !visited.contains( vertex ))
        {
            visited.add( vertex )
            sourceTransition = vertex.directIncomingTransitions.get(0)
            vertex = sourceTransition.sourceVertex
        }
        if (vertex instanceof State)
            return sourceTransition
        return null
    }

}
