
#include "A.hh"

#include "P.hh"
#include "umlrtcommsportrole.hh"
#include "umlrtmessage.hh"
#include "umlrtslot.hh"
#include <cstddef>
#include "umlrtcapsuleclass.hh"
#include "umlrtframeservice.hh"
class UMLRTRtsInterface;
struct UMLRTCommsPort;

Capsule_A::Capsule_A( const UMLRTCapsuleClass * cd, UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat )
: UMLRTCapsule( NULL, cd, st, border, internal, isStat )
, p( borderPorts[borderport_p] )
, currentState( SPECIAL_INTERNAL_STATE_UNVISITED )
{
    stateNames[State1] = "State1";
    stateNames[State1__State1] = "State1__State1";
    stateNames[State1__State2] = "State1__State2";
    stateNames[State2] = "State2";
    stateNames[SPECIAL_INTERNAL_STATE_TOP] = "<top>";
    stateNames[SPECIAL_INTERNAL_STATE_UNVISITED] = "<uninitialized>";
    int i = 0;
    while( i < 1 )
        history[i++] = SPECIAL_INTERNAL_STATE_UNVISITED;
}







void Capsule_A::bindPort( bool isBorder, int portId, int index )
{
    if( isBorder )
        switch( portId )
        {
        case borderport_p:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_p, index, true );
            break;
        }
}

void Capsule_A::unbindPort( bool isBorder, int portId, int index )
{
    if( isBorder )
        switch( portId )
        {
        case borderport_p:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_p, index, false );
            UMLRTFrameService::disconnectPort( borderPorts[borderport_p], index );
            break;
        }
}

void Capsule_A::inject( const UMLRTMessage & message )
{
    msg = &message;
    switch( currentState )
    {
    case State2:
        currentState = state_____State2( &message );
        break;
    case State1__State1:
        currentState = state_____State1__State1( &message );
        break;
    case State1__State2:
        currentState = state_____State1__State2( &message );
        break;
    default:
        break;
    }
}

void Capsule_A::initialize( const UMLRTMessage & message )
{
    msg = &message;
    actionchain_____Initial( &message );
    currentState = choice_____State1__deephistory( &message );
}

const char * Capsule_A::getCurrentStateString() const
{
    return stateNames[currentState];
}





void Capsule_A::save_history( Capsule_A::State compositeState, Capsule_A::State subState )
{
    history[compositeState] = subState;
}

bool Capsule_A::check_history( Capsule_A::State compositeState, Capsule_A::State subState )
{
    return history[compositeState] == subState;
}

void Capsule_A::update_state( Capsule_A::State newState )
{
    currentState = newState;
}

void Capsule_A::entryaction_____State1( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/ExclusionsActions/ExclusionsActions.uml ExclusionsActions::A::State1 entry  */
    log.log(LFCAPINST_STATE, "entry action 1");
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_A::entryaction_____State1__State1( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/ExclusionsActions/ExclusionsActions.uml ExclusionsActions::A::State1::State1 entry  */
    log.log(LFCAPINST_STATE, "entry action 3");
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_A::entryaction_____State1__State2( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/ExclusionsActions/ExclusionsActions.uml ExclusionsActions::A::State1::State2 entry  */
    log.log(LFCAPINST_STATE, "entry action 4");
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_A::entryaction_____State2( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/ExclusionsActions/ExclusionsActions.uml ExclusionsActions::A::State2 entry  */
    log.log(LFCAPINST_STATE, "entry action 2");
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_A::exitaction_____State1( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/ExclusionsActions/ExclusionsActions.uml ExclusionsActions::A::State1 exit  */
    log.log(LFCAPINST_STATE, "exit action 1");
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_A::exitaction_____State1__State1( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/ExclusionsActions/ExclusionsActions.uml ExclusionsActions::A::State1::State1 exit  */
    log.log(LFCAPINST_STATE, "exit action 3");
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_A::exitaction_____State1__State2( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/ExclusionsActions/ExclusionsActions.uml ExclusionsActions::A::State1::State2 exit  */
    log.log(LFCAPINST_STATE, "exit action 4");
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_A::transitionaction_____State1__t11( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/ExclusionsActions/ExclusionsActions.uml ExclusionsActions::A::State1 transition State1::State1,State1::State2,m1:p */
    log.log(LFCAPINST_STATE, "transition action 3");
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_A::transitionaction_____State1__t12( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/ExclusionsActions/ExclusionsActions.uml ExclusionsActions::A::State1 transition State1::State1,State1::State2,m2:p */
    log.log(LFCAPINST_STATE, "transition action 4");
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_A::transitionaction_____State1__t13( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/ExclusionsActions/ExclusionsActions.uml ExclusionsActions::A::State1 transition State1::State1,State1::State2,m3:p */
    log.log(LFCAPINST_STATE, "transition action 5");
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_A::transitionaction_____t1( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/ExclusionsActions/ExclusionsActions.uml ExclusionsActions::A transition State1::connectionPoint1,State2 */
    log.log(LFCAPINST_STATE, "transition action 1");
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_A::transitionaction_____t2( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/ExclusionsActions/ExclusionsActions.uml ExclusionsActions::A transition State1::connectionPoint2,State2 */
    log.log(LFCAPINST_STATE, "transition action 2");
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_A::actionchain_____Initial( const UMLRTMessage * msg )
{
    update_state( State1 );
    entryaction_____State1( msg );
}

void Capsule_A::actionchain_____State1__new_transition_1( const UMLRTMessage * msg )
{
    update_state( State1__State1 );
    entryaction_____State1__State1( msg );
}

void Capsule_A::actionchain_____State1__new_transition_2( const UMLRTMessage * msg )
{
    update_state( State1__State2 );
    entryaction_____State1__State2( msg );
}

void Capsule_A::actionchain_____State1__new_transition_3( const UMLRTMessage * msg )
{
    update_state( State1__State1 );
    entryaction_____State1__State1( msg );
}

void Capsule_A::actionchain_____State1__new_transition_4( const UMLRTMessage * msg )
{
    exitaction_____State1__State1( msg );
    update_state( State1 );
    save_history( State1, State1__State1 );
}

void Capsule_A::actionchain_____State1__new_transition_5( const UMLRTMessage * msg )
{
    exitaction_____State1__State2( msg );
    update_state( State1 );
    save_history( State1, State1__State2 );
}

void Capsule_A::actionchain_____State1__new_transition_6( const UMLRTMessage * msg )
{
    exitaction_____State1__State1( msg );
    update_state( State1 );
    save_history( State1, State1__State1 );
}

void Capsule_A::actionchain_____State1__new_transition_7( const UMLRTMessage * msg )
{
    exitaction_____State1__State2( msg );
    update_state( State1 );
    save_history( State1, State1__State2 );
}

void Capsule_A::actionchain_____State1__t11( const UMLRTMessage * msg )
{
    exitaction_____State1__State1( msg );
    update_state( State1 );
    transitionaction_____State1__t11( msg );
    update_state( State1__State2 );
    entryaction_____State1__State2( msg );
}

void Capsule_A::actionchain_____State1__t12( const UMLRTMessage * msg )
{
    exitaction_____State1__State1( msg );
    update_state( State1 );
    transitionaction_____State1__t12( msg );
    update_state( State1__State2 );
    entryaction_____State1__State2( msg );
}

void Capsule_A::actionchain_____State1__t13( const UMLRTMessage * msg )
{
    exitaction_____State1__State1( msg );
    update_state( State1 );
    transitionaction_____State1__t13( msg );
    update_state( State1__State2 );
    entryaction_____State1__State2( msg );
}

void Capsule_A::actionchain_____t1( const UMLRTMessage * msg )
{
    exitaction_____State1( msg );
    update_state( SPECIAL_INTERNAL_STATE_TOP );
    transitionaction_____t1( msg );
    update_state( State2 );
    entryaction_____State2( msg );
}

void Capsule_A::actionchain_____t2( const UMLRTMessage * msg )
{
    exitaction_____State1( msg );
    update_state( SPECIAL_INTERNAL_STATE_TOP );
    transitionaction_____t2( msg );
    update_state( State2 );
    entryaction_____State2( msg );
}

Capsule_A::State Capsule_A::junction_____State1__connectionPoint1( const UMLRTMessage * msg )
{
    actionchain_____t1( msg );
    return State2;
}

Capsule_A::State Capsule_A::junction_____State1__connectionPoint2( const UMLRTMessage * msg )
{
    actionchain_____t2( msg );
    return State2;
}

Capsule_A::State Capsule_A::choice_____State1__deephistory( const UMLRTMessage * msg )
{
    if( check_history( State1, State1__State1 ) )
    {
        actionchain_____State1__new_transition_1( msg );
        return State1__State1;
    }
    else if( check_history( State1, State1__State2 ) )
    {
        actionchain_____State1__new_transition_2( msg );
        return State1__State2;
    }
    else if( check_history( State1, SPECIAL_INTERNAL_STATE_UNVISITED ) )
    {
        actionchain_____State1__new_transition_3( msg );
        return State1__State1;
    }
    return currentState;
}

Capsule_A::State Capsule_A::state_____State1__State1( const UMLRTMessage * msg )
{
    switch( msg->destPort->role()->id )
    {
    case port_p:
        switch( msg->getSignalId() )
        {
        case P::signal_m1:
            actionchain_____State1__t11( msg );
            return State1__State2;
        case P::signal_m2:
            actionchain_____State1__t12( msg );
            return State1__State2;
        case P::signal_m3:
            actionchain_____State1__t13( msg );
            return State1__State2;
        default:
            this->unexpectedMessage();
            break;
        }
        return currentState;
    default:
        this->unexpectedMessage();
        break;
    }
    return currentState;
}

Capsule_A::State Capsule_A::state_____State1__State2( const UMLRTMessage * msg )
{
    switch( msg->destPort->role()->id )
    {
    case port_p:
        switch( msg->getSignalId() )
        {
        case P::signal_m1:
            actionchain_____State1__new_transition_5( msg );
            return junction_____State1__connectionPoint1( msg );
        case P::signal_m2:
            actionchain_____State1__new_transition_7( msg );
            return junction_____State1__connectionPoint2( msg );
        default:
            this->unexpectedMessage();
            break;
        }
        return currentState;
    default:
        this->unexpectedMessage();
        break;
    }
    return currentState;
}

Capsule_A::State Capsule_A::state_____State2( const UMLRTMessage * msg )
{
    switch( msg->destPort->role()->id )
    {
    default:
        this->unexpectedMessage();
        break;
    }
    return currentState;
}


static const UMLRTCommsPortRole portroles_border[] = 
{
    {
        Capsule_A::port_p,
        "P",
        "p",
        "",
        1,
        true,
        false,
        false,
        false,
        false,
        false,
        true
    }
};

static const UMLRTCommsPortRole portroles_internal[] = 
{
    {
        Capsule_A::port_log,
        "Log",
        "log",
        "",
        0,
        false,
        false,
        false,
        false,
        true,
        false,
        false
    }
};

static void instantiate_A( const UMLRTRtsInterface * rts, UMLRTSlot * slot, const UMLRTCommsPort * * borderPorts )
{
    const UMLRTCommsPort * * internalPorts = UMLRTFrameService::createInternalPorts( slot, &A );
    slot->capsule = new Capsule_A( &A, slot, borderPorts, internalPorts, false );
}

const UMLRTCapsuleClass A = 
{
    "A",
    NULL,
    instantiate_A,
    0,
    NULL,
    1,
    portroles_border,
    1,
    portroles_internal
};

