
#include "Capsule1.hh"

#include "umlrtcommsportrole.hh"
#include "umlrtmessage.hh"
#include "umlrtslot.hh"
#include <cstddef>
#include "umlrtcapsuleclass.hh"
#include "umlrtframeservice.hh"
class UMLRTRtsInterface;
struct UMLRTCommsPort;

Capsule_Capsule1::Capsule_Capsule1( const UMLRTCapsuleClass * cd, UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat )
: UMLRTCapsule( NULL, cd, st, border, internal, isStat )
, protocol1( borderPorts[borderport_protocol1] )
, protocol2( borderPorts[borderport_protocol2] )
, protocol3( borderPorts[borderport_protocol3] )
, currentState( SPECIAL_INTERNAL_STATE_UNVISITED )
{
    stateNames[State1] = "State1";
    stateNames[State2] = "State2";
    stateNames[SPECIAL_INTERNAL_STATE_TOP] = "<top>";
    stateNames[SPECIAL_INTERNAL_STATE_UNVISITED] = "<uninitialized>";
}







void Capsule_Capsule1::bindPort( bool isBorder, int portId, int index )
{
    if( isBorder )
        switch( portId )
        {
        case borderport_protocol1:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_protocol1, index, true );
            break;
        case borderport_protocol2:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_protocol2, index, true );
            break;
        case borderport_protocol3:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_protocol3, index, true );
            break;
        }
}

void Capsule_Capsule1::unbindPort( bool isBorder, int portId, int index )
{
    if( isBorder )
        switch( portId )
        {
        case borderport_protocol1:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_protocol1, index, false );
            UMLRTFrameService::disconnectPort( borderPorts[borderport_protocol1], index );
            break;
        case borderport_protocol2:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_protocol2, index, false );
            UMLRTFrameService::disconnectPort( borderPorts[borderport_protocol2], index );
            break;
        case borderport_protocol3:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_protocol3, index, false );
            UMLRTFrameService::disconnectPort( borderPorts[borderport_protocol3], index );
            break;
        }
}

void Capsule_Capsule1::inject( const UMLRTMessage & message )
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
    default:
        break;
    }
}

void Capsule_Capsule1::initialize( const UMLRTMessage & message )
{
    msg = &message;
    actionchain_____Initial( &message );
    currentState = State1;
}

const char * Capsule_Capsule1::getCurrentStateString() const
{
    return stateNames[currentState];
}




void Capsule_Capsule1::update_state( Capsule_Capsule1::State newState )
{
    currentState = newState;
}

void Capsule_Capsule1::actionchain_____Initial( const UMLRTMessage * msg )
{
    update_state( State1 );
}

void Capsule_Capsule1::actionchain_____transition1( const UMLRTMessage * msg )
{
    update_state( SPECIAL_INTERNAL_STATE_TOP );
    update_state( State2 );
}

Capsule_Capsule1::State Capsule_Capsule1::state_____State1( const UMLRTMessage * msg )
{
    switch( msg->destPort->role()->id )
    {
    case port_protocol1:
        switch( msg->getSignalId() )
        {
        default:
            actionchain_____transition1( msg );
            return State2;
        }
        return currentState;
    case port_protocol2:
        switch( msg->getSignalId() )
        {
        default:
            actionchain_____transition1( msg );
            return State2;
        }
        return currentState;
    case port_protocol3:
        switch( msg->getSignalId() )
        {
        default:
            actionchain_____transition1( msg );
            return State2;
        }
        return currentState;
    default:
        this->unexpectedMessage();
        break;
    }
    return currentState;
}

Capsule_Capsule1::State Capsule_Capsule1::state_____State2( const UMLRTMessage * msg )
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
        Capsule_Capsule1::port_protocol1,
        "Protocol1",
        "protocol1",
        "",
        1,
        true,
        true,
        false,
        false,
        false,
        false,
        true
    },
    {
        Capsule_Capsule1::port_protocol2,
        "Protocol2",
        "protocol2",
        "",
        1,
        true,
        true,
        false,
        false,
        false,
        false,
        true
    },
    {
        Capsule_Capsule1::port_protocol3,
        "Protocol3",
        "protocol3",
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

static void instantiate_Capsule1( const UMLRTRtsInterface * rts, UMLRTSlot * slot, const UMLRTCommsPort * * borderPorts )
{
    slot->capsule = new Capsule_Capsule1( &Capsule1, slot, borderPorts, NULL, false );
}

const UMLRTCapsuleClass Capsule1 = 
{
    "Capsule1",
    NULL,
    instantiate_Capsule1,
    0,
    NULL,
    3,
    portroles_border,
    0,
    NULL
};

