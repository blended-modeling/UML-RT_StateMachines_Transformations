
#include "Capsule1.hh"

#include "Protocol1.hh"
#include "umlrtcommsportrole.hh"
#include "umlrtmessage.hh"
#include "umlrtslot.hh"
#include "umlrttimerprotocol.hh"
#include <cstddef>
#include "umlrtcapsuleclass.hh"
#include "umlrtframeservice.hh"
class UMLRTRtsInterface;
struct UMLRTCommsPort;

Capsule_Capsule1::Capsule_Capsule1( const UMLRTCapsuleClass * cd, UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat )
: UMLRTCapsule( NULL, cd, st, border, internal, isStat )
, protocol1( borderPorts[borderport_protocol1] )
, timing( internalPorts[internalport_timing] )
, currentState( SPECIAL_INTERNAL_STATE_UNVISITED )
{
    stateNames[State1] = "State1";
    stateNames[State2] = "State2";
    stateNames[State3] = "State3";
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
    case State3:
        currentState = state_____State3( &message );
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

void Capsule_Capsule1::entryaction_____State1( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/InternalPorts/InternalPorts.uml RootElement::Capsule1::State1 entry  */
    log.log("[Capsule1](s:State1) sending 'ready' to Top; waiting for 'start' from Top");
    protocol1.ready().send();
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_Capsule1::entryaction_____State2( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/InternalPorts/InternalPorts.uml RootElement::Capsule1::State2 entry  */
    log.log("[Capsule1](s:State2) received 'start' from Top; setting timer");
    timing.informIn(UMLRTTimespec(1,0));
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_Capsule1::entryaction_____State3( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/InternalPorts/InternalPorts.uml RootElement::Capsule1::State3 entry  */
    log.log("[Capsule1](s:State3) received 'timeout'; sending 'done' to Top");
    protocol1.done().send();
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_Capsule1::actionchain_____Initial( const UMLRTMessage * msg )
{
    update_state( State1 );
    entryaction_____State1( msg );
}

void Capsule_Capsule1::actionchain_____start( const UMLRTMessage * msg )
{
    update_state( SPECIAL_INTERNAL_STATE_TOP );
    update_state( State2 );
    entryaction_____State2( msg );
}

void Capsule_Capsule1::actionchain_____timeout( const UMLRTMessage * msg )
{
    update_state( SPECIAL_INTERNAL_STATE_TOP );
    update_state( State3 );
    entryaction_____State3( msg );
}

Capsule_Capsule1::State Capsule_Capsule1::state_____State1( const UMLRTMessage * msg )
{
    switch( msg->destPort->role()->id )
    {
    case port_protocol1:
        switch( msg->getSignalId() )
        {
        case Protocol1::signal_start:
            actionchain_____start( msg );
            return State2;
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

Capsule_Capsule1::State Capsule_Capsule1::state_____State2( const UMLRTMessage * msg )
{
    switch( msg->destPort->role()->id )
    {
    case port_timing:
        switch( msg->getSignalId() )
        {
        case UMLRTTimerProtocol::signal_timeout:
            actionchain_____timeout( msg );
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

Capsule_Capsule1::State Capsule_Capsule1::state_____State3( const UMLRTMessage * msg )
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
    }
};

static const UMLRTCommsPortRole portroles_internal[] = 
{
    {
        Capsule_Capsule1::port_log,
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
    },
    {
        Capsule_Capsule1::port_timing,
        "Timing",
        "timing",
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

static void instantiate_Capsule1( const UMLRTRtsInterface * rts, UMLRTSlot * slot, const UMLRTCommsPort * * borderPorts )
{
    const UMLRTCommsPort * * internalPorts = UMLRTFrameService::createInternalPorts( slot, &Capsule1 );
    slot->capsule = new Capsule_Capsule1( &Capsule1, slot, borderPorts, internalPorts, false );
}

const UMLRTCapsuleClass Capsule1 = 
{
    "Capsule1",
    NULL,
    instantiate_Capsule1,
    0,
    NULL,
    1,
    portroles_border,
    2,
    portroles_internal
};

