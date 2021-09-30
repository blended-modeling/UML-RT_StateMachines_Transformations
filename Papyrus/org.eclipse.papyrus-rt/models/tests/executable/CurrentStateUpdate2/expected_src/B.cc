
#include "B.hh"

#include "P.hh"
#include "umlrtcommsportrole.hh"
#include "umlrtmessage.hh"
#include "umlrtslot.hh"
#include <cstddef>
#include "umlrtcapsuleclass.hh"
#include "umlrtframeservice.hh"
class UMLRTRtsInterface;
struct UMLRTCommsPort;

Capsule_B::Capsule_B( const UMLRTCapsuleClass * cd, UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat )
: UMLRTCapsule( NULL, cd, st, border, internal, isStat )
, p( borderPorts[borderport_p] )
, currentState( SPECIAL_INTERNAL_STATE_UNVISITED )
{
    stateNames[State1] = "State1";
    stateNames[State2] = "State2";
    stateNames[State1__State1] = "State1__State1";
    stateNames[State1__boundary] = "State1__boundary";
    stateNames[State2__State1] = "State2__State1";
    stateNames[State2__boundary] = "State2__boundary";
    stateNames[SPECIAL_INTERNAL_STATE_TOP] = "<top>";
    stateNames[SPECIAL_INTERNAL_STATE_UNVISITED] = "<uninitialized>";
    int i = 0;
    while( i < 2 )
        history[i++] = SPECIAL_INTERNAL_STATE_UNVISITED;
}







void Capsule_B::bindPort( bool isBorder, int portId, int index )
{
    if( isBorder )
        switch( portId )
        {
        case borderport_p:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_p, index, true );
            break;
        }
}

void Capsule_B::unbindPort( bool isBorder, int portId, int index )
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

void Capsule_B::inject( const UMLRTMessage & message )
{
    msg = &message;
    switch( currentState )
    {
    case State1__State1:
        currentState = state_____State1__State1( &message );
        break;
    case State1__boundary:
        currentState = state_____State1__boundary( &message );
        break;
    case State2__State1:
        currentState = state_____State2__State1( &message );
        break;
    case State2__boundary:
        currentState = state_____State2__boundary( &message );
        break;
    default:
        break;
    }
}

void Capsule_B::initialize( const UMLRTMessage & message )
{
    msg = &message;
    actionchain_____t0( &message );
    currentState = junction_____State1__State1EntryPoint( &message );
}

const char * Capsule_B::getCurrentStateString() const
{
    return stateNames[currentState];
}





void Capsule_B::save_history( Capsule_B::State compositeState, Capsule_B::State subState )
{
    history[compositeState] = subState;
}

bool Capsule_B::check_history( Capsule_B::State compositeState, Capsule_B::State subState )
{
    return history[compositeState] == subState;
}

void Capsule_B::update_state( Capsule_B::State newState )
{
    currentState = newState;
}

void Capsule_B::entryaction_____State1( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/CurrentStateUpdate2/CurrentStateUpdate2.uml CurrentStateUpdate2::B::State1 entry  */
    log.log("[%s %s - %s](%s) B::State1::entry", getTypeName(), getName(), name(), getCurrentStateString());
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_B::entryaction_____State1__State1( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/CurrentStateUpdate2/CurrentStateUpdate2.uml CurrentStateUpdate2::B::State1::State1 entry  */
    log.log("[%s %s - %s](%s) B::State1::State1::entry", getTypeName(), getName(), name(), getCurrentStateString());
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_B::entryaction_____State2( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/CurrentStateUpdate2/CurrentStateUpdate2.uml CurrentStateUpdate2::B::State2 entry  */
    log.log("[%s %s - %s](%s) B::State2::entry", getTypeName(), getName(), name(), getCurrentStateString());
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_B::entryaction_____State2__State1( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/CurrentStateUpdate2/CurrentStateUpdate2.uml CurrentStateUpdate2::B::State2::State1 entry  */
    log.log("[%s %s - %s](%s) B::State2::State1::entry", getTypeName(), getName(), name(), getCurrentStateString());
    exit(0);
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_B::exitaction_____State1( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/CurrentStateUpdate2/CurrentStateUpdate2.uml CurrentStateUpdate2::B::State1 exit  */
    log.log("[%s %s - %s](%s) B::State1::exit", getTypeName(), getName(), name(), getCurrentStateString());
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_B::exitaction_____State1__State1( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/CurrentStateUpdate2/CurrentStateUpdate2.uml CurrentStateUpdate2::B::State1::State1 exit  */
    log.log("[%s %s - %s](%s) B::State1::State1::exit", getTypeName(), getName(), name(), getCurrentStateString());
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_B::transitionaction_____State1__t10( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/CurrentStateUpdate2/CurrentStateUpdate2.uml CurrentStateUpdate2::B::State1 transition State1::State1EntryPoint,State1::State1 */
    log.log("[%s %s - %s](%s) B::State1::t10", getTypeName(), getName(), name(), getCurrentStateString());
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_B::transitionaction_____State1__t11( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/CurrentStateUpdate2/CurrentStateUpdate2.uml CurrentStateUpdate2::B::State1 transition State1::State1,State1::State1ExitPoint1,m:p */
    log.log("[%s %s - %s](%s) B::State1::t11", getTypeName(), getName(), name(), getCurrentStateString());
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_B::transitionaction_____State2__t20( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/CurrentStateUpdate2/CurrentStateUpdate2.uml CurrentStateUpdate2::B::State2 transition State2::State2EntryPoint1,State2::State1 */
    log.log("[%s %s - %s](%s) B::State2::t20", getTypeName(), getName(), name(), getCurrentStateString());
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_B::transitionaction_____t0( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/CurrentStateUpdate2/CurrentStateUpdate2.uml CurrentStateUpdate2::B transition subvertex0,State1::State1EntryPoint */
    log.log("[%s %s - %s](%s) B::t0", getTypeName(), getName(), name(), getCurrentStateString());
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_B::transitionaction_____t1( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/CurrentStateUpdate2/CurrentStateUpdate2.uml CurrentStateUpdate2::B transition State1::State1ExitPoint1,State2::State2EntryPoint1 */
    log.log("[%s %s - %s](%s) B::t1", getTypeName(), getName(), name(), getCurrentStateString());
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_B::actionchain_____State1__new_transition_1( const UMLRTMessage * msg )
{
    update_state( State1__State1 );
    entryaction_____State1__State1( msg );
}

void Capsule_B::actionchain_____State1__new_transition_2_to_unvisited_boundary( const UMLRTMessage * msg )
{
    update_state( State1__boundary );
}

void Capsule_B::actionchain_____State1__new_transition_3_to_visited_boundary( const UMLRTMessage * msg )
{
    update_state( State1__boundary );
}

void Capsule_B::actionchain_____State1__t10( const UMLRTMessage * msg )
{
    transitionaction_____State1__t10( msg );
    update_state( State1__State1 );
    entryaction_____State1__State1( msg );
}

void Capsule_B::actionchain_____State1__t11( const UMLRTMessage * msg )
{
    exitaction_____State1__State1( msg );
    update_state( State1 );
    transitionaction_____State1__t11( msg );
    save_history( State1, State1__State1 );
}

void Capsule_B::actionchain_____State2__new_transition_4( const UMLRTMessage * msg )
{
    update_state( State2__State1 );
    entryaction_____State2__State1( msg );
}

void Capsule_B::actionchain_____State2__new_transition_5_to_unvisited_boundary( const UMLRTMessage * msg )
{
    update_state( State2__boundary );
}

void Capsule_B::actionchain_____State2__new_transition_6_to_visited_boundary( const UMLRTMessage * msg )
{
    update_state( State2__boundary );
}

void Capsule_B::actionchain_____State2__t20( const UMLRTMessage * msg )
{
    transitionaction_____State2__t20( msg );
    update_state( State2__State1 );
    entryaction_____State2__State1( msg );
}

void Capsule_B::actionchain_____t0( const UMLRTMessage * msg )
{
    transitionaction_____t0( msg );
    update_state( State1 );
    entryaction_____State1( msg );
}

void Capsule_B::actionchain_____t1( const UMLRTMessage * msg )
{
    exitaction_____State1( msg );
    update_state( SPECIAL_INTERNAL_STATE_TOP );
    transitionaction_____t1( msg );
    update_state( State2 );
    entryaction_____State2( msg );
}

Capsule_B::State Capsule_B::junction_____State1__State1EntryPoint( const UMLRTMessage * msg )
{
    actionchain_____State1__t10( msg );
    return State1__State1;
}

Capsule_B::State Capsule_B::junction_____State1__State1ExitPoint1( const UMLRTMessage * msg )
{
    actionchain_____t1( msg );
    return junction_____State2__State2EntryPoint1( msg );
}

Capsule_B::State Capsule_B::junction_____State2__State2EntryPoint1( const UMLRTMessage * msg )
{
    actionchain_____State2__t20( msg );
    return State2__State1;
}

Capsule_B::State Capsule_B::choice_____State1__deephistory( const UMLRTMessage * msg )
{
    if( check_history( State1, State1__State1 ) )
    {
        actionchain_____State1__new_transition_1( msg );
        return State1__State1;
    }
    else if( check_history( State1, SPECIAL_INTERNAL_STATE_UNVISITED ) )
    {
        actionchain_____State1__new_transition_2_to_unvisited_boundary( msg );
        return State1__boundary;
    }
    else if( check_history( State1, State1__boundary ) )
    {
        actionchain_____State1__new_transition_3_to_visited_boundary( msg );
        return State1__boundary;
    }
    return currentState;
}

Capsule_B::State Capsule_B::choice_____State2__deephistory( const UMLRTMessage * msg )
{
    if( check_history( State2, State2__State1 ) )
    {
        actionchain_____State2__new_transition_4( msg );
        return State2__State1;
    }
    else if( check_history( State2, SPECIAL_INTERNAL_STATE_UNVISITED ) )
    {
        actionchain_____State2__new_transition_5_to_unvisited_boundary( msg );
        return State2__boundary;
    }
    else if( check_history( State2, State2__boundary ) )
    {
        actionchain_____State2__new_transition_6_to_visited_boundary( msg );
        return State2__boundary;
    }
    return currentState;
}

Capsule_B::State Capsule_B::state_____State1__State1( const UMLRTMessage * msg )
{
    switch( msg->destPort->role()->id )
    {
    case port_p:
        switch( msg->getSignalId() )
        {
        case P::signal_m:
            actionchain_____State1__t11( msg );
            return junction_____State1__State1ExitPoint1( msg );
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

Capsule_B::State Capsule_B::state_____State1__boundary( const UMLRTMessage * msg )
{
    switch( msg->destPort->role()->id )
    {
    default:
        this->unexpectedMessage();
        break;
    }
    return currentState;
}

Capsule_B::State Capsule_B::state_____State2__State1( const UMLRTMessage * msg )
{
    switch( msg->destPort->role()->id )
    {
    default:
        this->unexpectedMessage();
        break;
    }
    return currentState;
}

Capsule_B::State Capsule_B::state_____State2__boundary( const UMLRTMessage * msg )
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
        Capsule_B::port_p,
        "P",
        "p",
        "",
        1,
        true,
        true,
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
        Capsule_B::port_log,
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

static void instantiate_B( const UMLRTRtsInterface * rts, UMLRTSlot * slot, const UMLRTCommsPort * * borderPorts )
{
    const UMLRTCommsPort * * internalPorts = UMLRTFrameService::createInternalPorts( slot, &B );
    slot->capsule = new Capsule_B( &B, slot, borderPorts, internalPorts, false );
}

const UMLRTCapsuleClass B = 
{
    "B",
    NULL,
    instantiate_B,
    0,
    NULL,
    1,
    portroles_border,
    1,
    portroles_internal
};

