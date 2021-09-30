
#include "B.hh"

#include "A.hh"
#include "Protocol1.hh"
#include "Protocol2.hh"
#include "umlrtcommsportrole.hh"
#include "umlrtmessage.hh"
#include "umlrtslot.hh"
#include <cstddef>
#include "umlrtcapsuleclass.hh"
#include "umlrtframeservice.hh"
class UMLRTRtsInterface;
struct UMLRTCommsPort;

Capsule_B::Capsule_B( const UMLRTCapsuleClass * cd, UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat )
: Capsule_A( cd, st, border, internal, isStat )
, p( borderPorts[borderport_p] )
, q( borderPorts[borderport_q] )
, currentState( SPECIAL_INTERNAL_STATE_UNVISITED )
{
    stateNames[State1] = "State1";
    stateNames[State2] = "State2";
    stateNames[State3] = "State3";
    stateNames[State5] = "State5";
    stateNames[SPECIAL_INTERNAL_STATE_TOP] = "<top>";
    stateNames[SPECIAL_INTERNAL_STATE_UNVISITED] = "<uninitialized>";
}






void Capsule_B::bindPort( bool isBorder, int portId, int index )
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

void Capsule_B::unbindPort( bool isBorder, int portId, int index )
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

void Capsule_B::inject( const UMLRTMessage & message )
{
    msg = &message;
    switch( currentState )
    {
    case State3:
        currentState = state_____State3( &message );
        break;
    case State1:
        currentState = state_____State1( &message );
        break;
    case State2:
        currentState = state_____State2( &message );
        break;
    case State5:
        currentState = state_____State5( &message );
        break;
    default:
        break;
    }
}

void Capsule_B::initialize( const UMLRTMessage & message )
{
    msg = &message;
    actionchain_____implicitTransition0( &message );
    currentState = State1;
}

const char * Capsule_B::getCurrentStateString() const
{
    return stateNames[currentState];
}




void Capsule_B::update_state( Capsule_B::State newState )
{
    currentState = newState;
}

void Capsule_B::entryaction_____State3( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/ExclusionsStates/ExclusionsStates.uml ExclusionsStates::B::State3 entry  */
    // Adding a new entry action
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_B::actionchain_____implicitTransition0( const UMLRTMessage * msg )
{
    update_state( State1 );
}

void Capsule_B::actionchain_____implicitTransition1( const UMLRTMessage * msg )
{
    update_state( SPECIAL_INTERNAL_STATE_TOP );
    update_state( State2 );
}

void Capsule_B::actionchain_____implicitTransition2( const UMLRTMessage * msg )
{
    update_state( SPECIAL_INTERNAL_STATE_TOP );
    update_state( State3 );
    entryaction_____State3( msg );
}

void Capsule_B::actionchain_____implicitTransition3( const UMLRTMessage * msg )
{
    update_state( SPECIAL_INTERNAL_STATE_TOP );
    update_state( State5 );
}

void Capsule_B::actionchain_____implicitTransition4( const UMLRTMessage * msg )
{
    update_state( SPECIAL_INTERNAL_STATE_TOP );
    update_state( State5 );
}

Capsule_B::State Capsule_B::state_____State1( const UMLRTMessage * msg )
{
    switch( msg->destPort->role()->id )
    {
    case port_p:
        switch( msg->getSignalId() )
        {
        case Protocol1::signal_m1:
            actionchain_____implicitTransition1( msg );
            return State2;
        case Protocol1::signal_m2:
            actionchain_____implicitTransition2( msg );
            return State3;
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
    case port_q:
        switch( msg->getSignalId() )
        {
        case Protocol2::signal_om1:
            actionchain_____implicitTransition3( msg );
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

Capsule_B::State Capsule_B::state_____State3( const UMLRTMessage * msg )
{
    switch( msg->destPort->role()->id )
    {
    case port_q:
        switch( msg->getSignalId() )
        {
        case Protocol2::signal_om2:
            actionchain_____implicitTransition4( msg );
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
    },
    {
        Capsule_B::port_q,
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

static void instantiate_B( const UMLRTRtsInterface * rts, UMLRTSlot * slot, const UMLRTCommsPort * * borderPorts )
{
    slot->capsule = new Capsule_B( &B, slot, borderPorts, NULL, false );
}

const UMLRTCapsuleClass B = 
{
    "B",
    &A,
    instantiate_B,
    0,
    NULL,
    2,
    portroles_border,
    0,
    NULL
};

