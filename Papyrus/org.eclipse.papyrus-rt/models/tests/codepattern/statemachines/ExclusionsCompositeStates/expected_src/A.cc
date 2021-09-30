
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

Capsule_A::Capsule_A( const UMLRTCapsuleClass * cd, UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat )
: UMLRTCapsule( NULL, cd, st, border, internal, isStat )
, p( borderPorts[borderport_p] )
, currentState( SPECIAL_INTERNAL_STATE_UNVISITED )
{
    stateNames[State4] = "State4";
    stateNames[State1] = "State1";
    stateNames[State2] = "State2";
    stateNames[State3] = "State3";
    stateNames[State4__State1] = "State4__State1";
    stateNames[State4__State2] = "State4__State2";
    stateNames[State4__State3] = "State4__State3";
    stateNames[State4__boundary] = "State4__boundary";
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
    case State1:
        currentState = state_____State1( &message );
        break;
    case State2:
        currentState = state_____State2( &message );
        break;
    case State3:
        currentState = state_____State3( &message );
        break;
    case State4__State1:
        currentState = state_____State4__State1( &message );
        break;
    case State4__State2:
        currentState = state_____State4__State2( &message );
        break;
    case State4__State3:
        currentState = state_____State4__State3( &message );
        break;
    case State4__boundary:
        currentState = state_____State4__boundary( &message );
        break;
    default:
        break;
    }
}

void Capsule_A::initialize( const UMLRTMessage & message )
{
    msg = &message;
    actionchain_____ta0( &message );
    currentState = State1;
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

bool Capsule_A::guard_____State4__ta41( const UMLRTMessage * msg )
{
    #define data ( *(const int64_t * )msg->getParam( 0 ) )
    #define rtdata ( (const int64_t * )msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/ExclusionsCompositeStates/ExclusionsCompositeStates.uml ExclusionsCompositeStates::A::State4 guard State4::as4cp1,State4::State1 */
    return data > 0 && data < 10;
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
    #undef data
}

bool Capsule_A::guard_____State4__ta42( const UMLRTMessage * msg )
{
    #define data ( *(const int64_t * )msg->getParam( 0 ) )
    #define rtdata ( (const int64_t * )msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/ExclusionsCompositeStates/ExclusionsCompositeStates.uml ExclusionsCompositeStates::A::State4 guard State4::as4cp1,State4::State2 */
    return data >= 10;
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
    #undef data
}

void Capsule_A::actionchain_____State4__new_transition_1( const UMLRTMessage * msg )
{
    update_state( State4__State1 );
}

void Capsule_A::actionchain_____State4__new_transition_2( const UMLRTMessage * msg )
{
    update_state( State4__State2 );
}

void Capsule_A::actionchain_____State4__new_transition_3( const UMLRTMessage * msg )
{
    update_state( State4__State3 );
}

void Capsule_A::actionchain_____State4__new_transition_4_to_unvisited_boundary( const UMLRTMessage * msg )
{
    update_state( State4__boundary );
}

void Capsule_A::actionchain_____State4__new_transition_5_to_visited_boundary( const UMLRTMessage * msg )
{
    update_state( State4__boundary );
}

void Capsule_A::actionchain_____State4__ta41( const UMLRTMessage * msg )
{
    update_state( State4__State1 );
}

void Capsule_A::actionchain_____State4__ta42( const UMLRTMessage * msg )
{
    update_state( State4__State2 );
}

void Capsule_A::actionchain_____State4__ta43( const UMLRTMessage * msg )
{
    update_state( State4__State3 );
}

void Capsule_A::actionchain_____ta0( const UMLRTMessage * msg )
{
    update_state( State1 );
}

void Capsule_A::actionchain_____ta1( const UMLRTMessage * msg )
{
    update_state( SPECIAL_INTERNAL_STATE_TOP );
    update_state( State2 );
}

void Capsule_A::actionchain_____ta2( const UMLRTMessage * msg )
{
    update_state( SPECIAL_INTERNAL_STATE_TOP );
    update_state( State3 );
}

void Capsule_A::actionchain_____ta3( const UMLRTMessage * msg )
{
    update_state( SPECIAL_INTERNAL_STATE_TOP );
    update_state( State4 );
}

Capsule_A::State Capsule_A::junction_____State4__as4enp1( const UMLRTMessage * msg )
{
    return choice_____State4__as4cp1( msg );
}

Capsule_A::State Capsule_A::choice_____State4__as4cp1( const UMLRTMessage * msg )
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
    else
    {
        actionchain_____State4__ta43( msg );
        return State4__State3;
    }
    return currentState;
}

Capsule_A::State Capsule_A::choice_____State4__deephistory( const UMLRTMessage * msg )
{
    if( check_history( State4, State4__State1 ) )
    {
        actionchain_____State4__new_transition_1( msg );
        return State4__State1;
    }
    else if( check_history( State4, State4__State2 ) )
    {
        actionchain_____State4__new_transition_2( msg );
        return State4__State2;
    }
    else if( check_history( State4, State4__State3 ) )
    {
        actionchain_____State4__new_transition_3( msg );
        return State4__State3;
    }
    else if( check_history( State4, SPECIAL_INTERNAL_STATE_UNVISITED ) )
    {
        actionchain_____State4__new_transition_4_to_unvisited_boundary( msg );
        return State4__boundary;
    }
    else if( check_history( State4, State4__boundary ) )
    {
        actionchain_____State4__new_transition_5_to_visited_boundary( msg );
        return State4__boundary;
    }
    return currentState;
}

Capsule_A::State Capsule_A::state_____State1( const UMLRTMessage * msg )
{
    switch( msg->destPort->role()->id )
    {
    case port_p:
        switch( msg->getSignalId() )
        {
        case Protocol1::signal_m1:
            actionchain_____ta1( msg );
            return State2;
        case Protocol1::signal_m2:
            actionchain_____ta2( msg );
            return State3;
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

Capsule_A::State Capsule_A::state_____State3( const UMLRTMessage * msg )
{
    switch( msg->destPort->role()->id )
    {
    default:
        this->unexpectedMessage();
        break;
    }
    return currentState;
}

Capsule_A::State Capsule_A::state_____State4__State1( const UMLRTMessage * msg )
{
    switch( msg->destPort->role()->id )
    {
    default:
        this->unexpectedMessage();
        break;
    }
    return currentState;
}

Capsule_A::State Capsule_A::state_____State4__State2( const UMLRTMessage * msg )
{
    switch( msg->destPort->role()->id )
    {
    default:
        this->unexpectedMessage();
        break;
    }
    return currentState;
}

Capsule_A::State Capsule_A::state_____State4__State3( const UMLRTMessage * msg )
{
    switch( msg->destPort->role()->id )
    {
    default:
        this->unexpectedMessage();
        break;
    }
    return currentState;
}

Capsule_A::State Capsule_A::state_____State4__boundary( const UMLRTMessage * msg )
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

