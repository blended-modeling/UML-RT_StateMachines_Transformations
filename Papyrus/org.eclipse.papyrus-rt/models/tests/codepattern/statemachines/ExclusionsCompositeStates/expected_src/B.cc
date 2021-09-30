
#include "B.hh"

#include "A.hh"
#include "Protocol1.hh"
#include "umlrtcommsportrole.hh"
#include "umlrtmessage.hh"
#include "umlrtslot.hh"
#include <cstddef>
#include "umlrtcapsuleclass.hh"
#include "umlrtframeservice.hh"
#include <stdint.h>
class UMLRTRtsInterface;
struct UMLRTCommsPort;

Capsule_B::Capsule_B( const UMLRTCapsuleClass * cd, UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat )
: Capsule_A( cd, st, border, internal, isStat )
, p( borderPorts[borderport_p] )
, currentState( SPECIAL_INTERNAL_STATE_UNVISITED )
{
    stateNames[State3] = "State3";
    stateNames[State4] = "State4";
    stateNames[State1] = "State1";
    stateNames[State2] = "State2";
    stateNames[State3__State1] = "State3__State1";
    stateNames[State3__boundary] = "State3__boundary";
    stateNames[State4__State1] = "State4__State1";
    stateNames[State4__State2] = "State4__State2";
    stateNames[State4__boundary] = "State4__boundary";
    stateNames[State5] = "State5";
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
    case State5:
        currentState = state_____State5( &message );
        break;
    case State1:
        currentState = state_____State1( &message );
        break;
    case State2:
        currentState = state_____State2( &message );
        break;
    case State3__State1:
        currentState = state_____State3__State1( &message );
        break;
    case State3__boundary:
        currentState = state_____State3__boundary( &message );
        break;
    case State4__State2:
        currentState = state_____State4__State2( &message );
        break;
    case State4__State1:
        currentState = state_____State4__State1( &message );
        break;
    case State4__boundary:
        currentState = state_____State4__boundary( &message );
        break;
    default:
        break;
    }
}

void Capsule_B::initialize( const UMLRTMessage & message )
{
    msg = &message;
    actionchain_____ta0( &message );
    currentState = State1;
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

void Capsule_B::entryaction_____State4__State2( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/ExclusionsCompositeStates/ExclusionsCompositeStates.uml ExclusionsCompositeStates::B::State4::State2 entry  */
    log.log("State4.State2 entry");
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

bool Capsule_B::guard_____State4__ta41( const UMLRTMessage * msg )
{
    #define data ( *(const int64_t * )msg->getParam( 0 ) )
    #define rtdata ( (const int64_t * )msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/ExclusionsCompositeStates/ExclusionsCompositeStates.uml ExclusionsCompositeStates::B::State4 guard State4::as4cp1,State4::State1 */
    return data > 0 && data < 10;
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
    #undef data
}

bool Capsule_B::guard_____State4__ta42( const UMLRTMessage * msg )
{
    #define data ( *(const int64_t * )msg->getParam( 0 ) )
    #define rtdata ( (const int64_t * )msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/ExclusionsCompositeStates/ExclusionsCompositeStates.uml ExclusionsCompositeStates::B::State4 guard State4::as4cp1,State4::State2 */
    return data >= 10;
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
    #undef data
}

void Capsule_B::actionchain_____State3__new_transition_1( const UMLRTMessage * msg )
{
    update_state( State3__State1 );
}

void Capsule_B::actionchain_____State3__new_transition_2_to_unvisited_boundary( const UMLRTMessage * msg )
{
    update_state( State3__boundary );
}

void Capsule_B::actionchain_____State3__new_transition_3_to_visited_boundary( const UMLRTMessage * msg )
{
    update_state( State3__boundary );
}

void Capsule_B::actionchain_____State3__tb30( const UMLRTMessage * msg )
{
    update_state( State3__State1 );
}

void Capsule_B::actionchain_____State4__new_transition_4( const UMLRTMessage * msg )
{
    update_state( State4__State2 );
    entryaction_____State4__State2( msg );
}

void Capsule_B::actionchain_____State4__new_transition_5( const UMLRTMessage * msg )
{
    update_state( State4__State1 );
}

void Capsule_B::actionchain_____State4__new_transition_6_to_unvisited_boundary( const UMLRTMessage * msg )
{
    update_state( State4__boundary );
}

void Capsule_B::actionchain_____State4__new_transition_7_to_visited_boundary( const UMLRTMessage * msg )
{
    update_state( State4__boundary );
}

void Capsule_B::actionchain_____State4__ta41( const UMLRTMessage * msg )
{
    update_state( State4__State1 );
}

void Capsule_B::actionchain_____State4__ta42( const UMLRTMessage * msg )
{
    update_state( State4__State2 );
    entryaction_____State4__State2( msg );
}

void Capsule_B::actionchain_____State4__tb41( const UMLRTMessage * msg )
{
    update_state( State4 );
}

void Capsule_B::actionchain_____State4__tb42( const UMLRTMessage * msg )
{
    update_state( State4 );
    save_history( State4, State4__State2 );
}

void Capsule_B::actionchain_____ta0( const UMLRTMessage * msg )
{
    update_state( State1 );
}

void Capsule_B::actionchain_____ta1( const UMLRTMessage * msg )
{
    update_state( SPECIAL_INTERNAL_STATE_TOP );
    update_state( State2 );
}

void Capsule_B::actionchain_____ta2( const UMLRTMessage * msg )
{
    update_state( SPECIAL_INTERNAL_STATE_TOP );
    update_state( State3 );
}

void Capsule_B::actionchain_____ta3( const UMLRTMessage * msg )
{
    update_state( SPECIAL_INTERNAL_STATE_TOP );
    update_state( State4 );
}

void Capsule_B::actionchain_____tb4( const UMLRTMessage * msg )
{
    update_state( SPECIAL_INTERNAL_STATE_TOP );
    update_state( State5 );
}

Capsule_B::State Capsule_B::junction_____State3__bs3ep1( const UMLRTMessage * msg )
{
    actionchain_____State3__tb30( msg );
    return State3__State1;
}

Capsule_B::State Capsule_B::junction_____State4__as4enp1( const UMLRTMessage * msg )
{
    return choice_____State4__as4cp1( msg );
}

Capsule_B::State Capsule_B::junction_____State4__bs4exp1( const UMLRTMessage * msg )
{
    actionchain_____tb4( msg );
    return State5;
}

Capsule_B::State Capsule_B::choice_____State3__deephistory( const UMLRTMessage * msg )
{
    if( check_history( State3, State3__State1 ) )
    {
        actionchain_____State3__new_transition_1( msg );
        return State3__State1;
    }
    else if( check_history( State3, SPECIAL_INTERNAL_STATE_UNVISITED ) )
    {
        actionchain_____State3__new_transition_2_to_unvisited_boundary( msg );
        return State3__boundary;
    }
    else if( check_history( State3, State3__boundary ) )
    {
        actionchain_____State3__new_transition_3_to_visited_boundary( msg );
        return State3__boundary;
    }
    return currentState;
}

Capsule_B::State Capsule_B::choice_____State4__as4cp1( const UMLRTMessage * msg )
{
    if( guard_____State4__ta41( msg ) )
    {
        actionchain_____State4__ta41( msg );
        return State4__State1;
    }
    else if( guard_____State4__ta42( msg ) )
    {
        actionchain_____State4__ta42( msg );
        return State4__State2;
    }
    return currentState;
}

Capsule_B::State Capsule_B::choice_____State4__deephistory( const UMLRTMessage * msg )
{
    if( check_history( State4, State4__State2 ) )
    {
        actionchain_____State4__new_transition_4( msg );
        return State4__State2;
    }
    else if( check_history( State4, State4__State1 ) )
    {
        actionchain_____State4__new_transition_5( msg );
        return State4__State1;
    }
    else if( check_history( State4, SPECIAL_INTERNAL_STATE_UNVISITED ) )
    {
        actionchain_____State4__new_transition_6_to_unvisited_boundary( msg );
        return State4__boundary;
    }
    else if( check_history( State4, State4__boundary ) )
    {
        actionchain_____State4__new_transition_7_to_visited_boundary( msg );
        return State4__boundary;
    }
    return currentState;
}

Capsule_B::State Capsule_B::state_____State1( const UMLRTMessage * msg )
{
    switch( msg->destPort->role()->id )
    {
    case port_p:
        switch( msg->getSignalId() )
        {
        case Protocol1::signal_m2:
            actionchain_____ta2( msg );
            return junction_____State3__bs3ep1( msg );
        case Protocol1::signal_m1:
            actionchain_____ta1( msg );
            return State2;
        case Protocol1::signal_m3:
            actionchain_____ta3( msg );
            return junction_____State4__as4enp1( msg );
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

Capsule_B::State Capsule_B::state_____State2( const UMLRTMessage * msg )
{
    switch( msg->destPort->role()->id )
    {
    default:
        this->unexpectedMessage();
        break;
    }
    return currentState;
}

Capsule_B::State Capsule_B::state_____State3__State1( const UMLRTMessage * msg )
{
    switch( msg->destPort->role()->id )
    {
    default:
        this->unexpectedMessage();
        break;
    }
    return currentState;
}

Capsule_B::State Capsule_B::state_____State3__boundary( const UMLRTMessage * msg )
{
    switch( msg->destPort->role()->id )
    {
    default:
        this->unexpectedMessage();
        break;
    }
    return currentState;
}

Capsule_B::State Capsule_B::state_____State4__State1( const UMLRTMessage * msg )
{
    switch( msg->destPort->role()->id )
    {
    case port_p:
        switch( msg->getSignalId() )
        {
        case Protocol1::signal_m4:
            actionchain_____State4__tb41( msg );
            return choice_____State4__deephistory( msg );
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

Capsule_B::State Capsule_B::state_____State4__State2( const UMLRTMessage * msg )
{
    switch( msg->destPort->role()->id )
    {
    case port_p:
        switch( msg->getSignalId() )
        {
        case Protocol1::signal_m5:
            actionchain_____State4__tb42( msg );
            return junction_____State4__bs4exp1( msg );
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

Capsule_B::State Capsule_B::state_____State4__boundary( const UMLRTMessage * msg )
{
    switch( msg->destPort->role()->id )
    {
    default:
        this->unexpectedMessage();
        break;
    }
    return currentState;
}

Capsule_B::State Capsule_B::state_____State5( const UMLRTMessage * msg )
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
        "Protocol1",
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
    &A,
    instantiate_B,
    0,
    NULL,
    1,
    portroles_border,
    1,
    portroles_internal
};

