/**
 * generated by Xtext 2.21.0
 */
package org.xtext.example.hclscope.hclScope.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.xtext.example.hclscope.hclScope.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class HclScopeFactoryImpl extends EFactoryImpl implements HclScopeFactory
{
  /**
   * Creates the default factory implementation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static HclScopeFactory init()
  {
    try
    {
      HclScopeFactory theHclScopeFactory = (HclScopeFactory)EPackage.Registry.INSTANCE.getEFactory(HclScopePackage.eNS_URI);
      if (theHclScopeFactory != null)
      {
        return theHclScopeFactory;
      }
    }
    catch (Exception exception)
    {
      EcorePlugin.INSTANCE.log(exception);
    }
    return new HclScopeFactoryImpl();
  }

  /**
   * Creates an instance of the factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public HclScopeFactoryImpl()
  {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EObject create(EClass eClass)
  {
    switch (eClass.getClassifierID())
    {
      case HclScopePackage.STATE_MACHINE: return createStateMachine();
      case HclScopePackage.STATE: return createState();
      case HclScopePackage.ENTRY_ACTION: return createEntryAction();
      case HclScopePackage.EXIT_ACTION: return createExitAction();
      case HclScopePackage.INITIAL_STATE: return createInitialState();
      case HclScopePackage.JUNCTION: return createJunction();
      case HclScopePackage.CHOICE: return createChoice();
      case HclScopePackage.ENTRY_POINT: return createEntryPoint();
      case HclScopePackage.EXIT_POINT: return createExitPoint();
      case HclScopePackage.DEEP_HISTORY: return createDeepHistory();
      case HclScopePackage.INITIAL_TRANSITION: return createInitialTransition();
      case HclScopePackage.TRANSITION: return createTransition();
      case HclScopePackage.INTERNAL_TRANSITION: return createInternalTransition();
      case HclScopePackage.HISTORY_TRANSITION: return createHistoryTransition();
      case HclScopePackage.TRANSITION_BODY: return createTransitionBody();
      case HclScopePackage.TRANSITION_GUARD: return createTransitionGuard();
      case HclScopePackage.TRANSITION_OPERATION: return createTransitionOperation();
      case HclScopePackage.TRIGGER: return createTrigger();
      case HclScopePackage.METHOD: return createMethod();
      case HclScopePackage.PARAMETER: return createParameter();
      case HclScopePackage.METHOD_PARAMETER_TRIGGER: return createMethodParameterTrigger();
      case HclScopePackage.PORT: return createPort();
      case HclScopePackage.EVENT: return createEvent();
      case HclScopePackage.PORT_EVENT_TRIGGER: return createPortEventTrigger();
      case HclScopePackage.VERTEX: return createVertex();
      case HclScopePackage.TRANSITIONS: return createTransitions();
      default:
        throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
    }
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public StateMachine createStateMachine()
  {
    StateMachineImpl stateMachine = new StateMachineImpl();
    return stateMachine;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public State createState()
  {
    StateImpl state = new StateImpl();
    return state;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EntryAction createEntryAction()
  {
    EntryActionImpl entryAction = new EntryActionImpl();
    return entryAction;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public ExitAction createExitAction()
  {
    ExitActionImpl exitAction = new ExitActionImpl();
    return exitAction;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public InitialState createInitialState()
  {
    InitialStateImpl initialState = new InitialStateImpl();
    return initialState;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Junction createJunction()
  {
    JunctionImpl junction = new JunctionImpl();
    return junction;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Choice createChoice()
  {
    ChoiceImpl choice = new ChoiceImpl();
    return choice;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EntryPoint createEntryPoint()
  {
    EntryPointImpl entryPoint = new EntryPointImpl();
    return entryPoint;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public ExitPoint createExitPoint()
  {
    ExitPointImpl exitPoint = new ExitPointImpl();
    return exitPoint;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public DeepHistory createDeepHistory()
  {
    DeepHistoryImpl deepHistory = new DeepHistoryImpl();
    return deepHistory;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public InitialTransition createInitialTransition()
  {
    InitialTransitionImpl initialTransition = new InitialTransitionImpl();
    return initialTransition;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Transition createTransition()
  {
    TransitionImpl transition = new TransitionImpl();
    return transition;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public InternalTransition createInternalTransition()
  {
    InternalTransitionImpl internalTransition = new InternalTransitionImpl();
    return internalTransition;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public HistoryTransition createHistoryTransition()
  {
    HistoryTransitionImpl historyTransition = new HistoryTransitionImpl();
    return historyTransition;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public TransitionBody createTransitionBody()
  {
    TransitionBodyImpl transitionBody = new TransitionBodyImpl();
    return transitionBody;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public TransitionGuard createTransitionGuard()
  {
    TransitionGuardImpl transitionGuard = new TransitionGuardImpl();
    return transitionGuard;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public TransitionOperation createTransitionOperation()
  {
    TransitionOperationImpl transitionOperation = new TransitionOperationImpl();
    return transitionOperation;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Trigger createTrigger()
  {
    TriggerImpl trigger = new TriggerImpl();
    return trigger;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Method createMethod()
  {
    MethodImpl method = new MethodImpl();
    return method;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Parameter createParameter()
  {
    ParameterImpl parameter = new ParameterImpl();
    return parameter;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public MethodParameterTrigger createMethodParameterTrigger()
  {
    MethodParameterTriggerImpl methodParameterTrigger = new MethodParameterTriggerImpl();
    return methodParameterTrigger;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Port createPort()
  {
    PortImpl port = new PortImpl();
    return port;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Event createEvent()
  {
    EventImpl event = new EventImpl();
    return event;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public PortEventTrigger createPortEventTrigger()
  {
    PortEventTriggerImpl portEventTrigger = new PortEventTriggerImpl();
    return portEventTrigger;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Vertex createVertex()
  {
    VertexImpl vertex = new VertexImpl();
    return vertex;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Transitions createTransitions()
  {
    TransitionsImpl transitions = new TransitionsImpl();
    return transitions;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public HclScopePackage getHclScopePackage()
  {
    return (HclScopePackage)getEPackage();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @deprecated
   * @generated
   */
  @Deprecated
  public static HclScopePackage getPackage()
  {
    return HclScopePackage.eINSTANCE;
  }

} //HclScopeFactoryImpl
