
#include "A.hh"

#include "Protocol1.hh"
#include "Protocol2.hh"
#include "umlrtcapsuleclass.hh"
#include "umlrtcommsportrole.hh"
#include "umlrtmessage.hh"
#include "umlrtslot.hh"
#include <cstddef>
#include "umlrtframeservice.hh"
class UMLRTRtsInterface;
struct UMLRTCommsPort;

Capsule_A::Capsule_A( const UMLRTCapsuleClass * cd, UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat )
: UMLRTCapsule( NULL, cd, st, border, internal, isStat )
, p( borderPorts[borderport_p] )
, q( borderPorts[borderport_q] )
, currentState( SPECIAL_INTERNAL_STATE_UNVISITED )
{
    stateNames[State1] = "State1";
    stateNames[State2] = "State2";
    stateNames[State3] = "State3";
    stateNames[State4] = "State4";
    stateNames[State5] = "State5";
    stateNames[SPECIAL_INTERNAL_STATE_TOP] = "<top>";
    stateNames[SPECIAL_INTERNAL_STATE_UNVISITED] = "<uninitialized>";
}






void Capsule_A::bindPort( bool isBorder, int portId, int index )
{
    if( isBorder )
        switch( portId )
        {
        case borderport_p:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_p, index, true );
            break;
        case borderport_q:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_q, index, true );
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
        case borderport_q:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_q, index, false );
            UMLRTFrameService::disconnectPort( borderPorts[borderport_q], index );
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
    case State4:
        currentState = state_____State4( &message );
        break;
    case State5:
        currentState = state_____State5( &message );
        break;
    default:
        break;
    }
}

void Capsule_A::initialize( const UMLRTMessage & message )
{
    msg = &message;
    actionchain_____transition0( &message );
    currentState = State1;
}

const char * Capsule_A::getCurrentStateString() const
{
    return stateNames[currentState];
}




void Capsule_A::update_state( Capsule_A::State newState )
{
    currentState = newState;
}

void Capsule_A::actionchain_____transition0( const UMLRTMessage * msg )
{
    update_state( State1 );
}

void Capsule_A::actionchain_____transition1( const UMLRTMessage * msg )
{
    update_state( SPECIAL_INTERNAL_STATE_TOP );
    update_state( State2 );
}

void Capsule_A::actionchain_____transition2( const UMLRTMessage * msg )
{
    update_state( SPECIAL_INTERNAL_STATE_TOP );
    update_state( State3 );
}

void Capsule_A::actionchain_____transition3( const UMLRTMessage * msg )
{
    update_state( SPECIAL_INTERNAL_STATE_TOP );
    update_state( State4 );
}

void Capsule_A::actionchain_____transition4( const UMLRTMessage * msg )
{
    update_state( SPECIAL_INTERNAL_STATE_TOP );
    update_state( State5 );
}

void Capsule_A::actionchain_____transition5( const UMLRTMessage * msg )
{
    update_state( SPECIAL_INTERNAL_STATE_TOP );
    update_state( State5 );
}

void Capsule_A::actionchain_____transition6( const UMLRTMessage * msg )
{
    update_state( SPECIAL_INTERNAL_STATE_TOP );
    update_state( State5 );
}

Capsule_A::State Capsule_A::state_____State1( const UMLRTMessage * msg )
{
    switch( msg->destPort->role()->id )
    {
    case port_p:
        switch( msg->getSignalId() )
        {
        case Protocol1::signal_m1:
            actionchain_____transition1( msg );
            return State2;
        case Protocol1::signal_m2:
            actionchain_____transition2( msg );
            return State3;
        case Protocol1::signal_m3:
            actionchain_____transition3( msg );
            return State4;
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
    case port_q:
        switch( msg->getSignalId() )
        {
        case Protocol2::signal_om1:
            actionchain_____transition4( msg );
            return State5;
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

Capsule_A::State Capsule_A::state_____State3( const UMLRTMessage * msg )
{
    switch( msg->destPort->role()->id )
    {
    case port_q:
        switch( msg->getSignalId() )
        {
        case Protocol2::signal_om2:
            actionchain_____transition5( msg );
            return State5;
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

Capsule_A::State Capsule_A::state_____State4( const UMLRTMessage * msg )
{
    switch( msg->destPort->role()->id )
    {
    case port_q:
        switch( msg->getSignalId() )
        {
        case Protocol2::signal_om3:
            actionchain_____transition6( msg );
            return State5;
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

Capsule_A::State Capsule_A::state_____State5( const UMLRTMessage * msg )
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
    },
    {
        Capsule_A::port_q,
        "Protocol2",
        "q",
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

static void instantiate_A( const UMLRTRtsInterface * rts, UMLRTSlot * slot, const UMLRTCommsPort * * borderPorts )
{
    slot->capsule = new Capsule_A( &A, slot, borderPorts, NULL, false );
}

const UMLRTCapsuleClass A = 
{
    "A",
    NULL,
    instantiate_A,
    0,
    NULL,
    2,
    portroles_border,
    0,
    NULL
};

