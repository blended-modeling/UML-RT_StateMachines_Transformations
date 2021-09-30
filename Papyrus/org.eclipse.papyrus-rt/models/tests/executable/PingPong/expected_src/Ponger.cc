
#include "Ponger.hh"

#include "PingPongProtocol.hh"
#include "umlrtcommsportrole.hh"
#include "umlrtmessage.hh"
#include "umlrtslot.hh"
#include <cstddef>
#include "umlrtcapsuleclass.hh"
#include "umlrtframeservice.hh"
class UMLRTRtsInterface;
struct UMLRTCommsPort;

#include "UtilityMacros.hh"

Capsule_Ponger::Capsule_Ponger( const UMLRTCapsuleClass * cd, UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat )
: UMLRTCapsule( NULL, cd, st, border, internal, isStat )
, pongPort( borderPorts[borderport_pongPort] )
, currentState( SPECIAL_INTERNAL_STATE_UNVISITED )
{
    stateNames[Playing] = "Playing";
    stateNames[SPECIAL_INTERNAL_STATE_TOP] = "<top>";
    stateNames[SPECIAL_INTERNAL_STATE_UNVISITED] = "<uninitialized>";
}







void Capsule_Ponger::bindPort( bool isBorder, int portId, int index )
{
    if( isBorder )
        switch( portId )
        {
        case borderport_pongPort:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_pongPort, index, true );
            break;
        }
}

void Capsule_Ponger::unbindPort( bool isBorder, int portId, int index )
{
    if( isBorder )
        switch( portId )
        {
        case borderport_pongPort:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_pongPort, index, false );
            UMLRTFrameService::disconnectPort( borderPorts[borderport_pongPort], index );
            break;
        }
}

void Capsule_Ponger::inject( const UMLRTMessage & message )
{
    msg = &message;
    switch( currentState )
    {
    case Playing:
        currentState = state_____Playing( &message );
        break;
    default:
        break;
    }
}

void Capsule_Ponger::initialize( const UMLRTMessage & message )
{
    msg = &message;
    actionchain_____Initial( &message );
    currentState = Playing;
}

const char * Capsule_Ponger::getCurrentStateString() const
{
    return stateNames[currentState];
}




void Capsule_Ponger::update_state( Capsule_Ponger::State newState )
{
    currentState = newState;
}

void Capsule_Ponger::entryaction_____Playing( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/PingPong/PingPong.uml PingPong::Ponger::Playing entry  */
    log.log(LCAPINST, "(enter Playing)");
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_Ponger::transitionaction_____onPing( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/PingPong/PingPong.uml PingPong::Ponger transition Playing,Playing,ping:pongPort */
    log.log(LCAPINST, "(onPing) sending pong");
    pongPort.pong().send();
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_Ponger::actionchain_____Initial( const UMLRTMessage * msg )
{
    update_state( Playing );
    entryaction_____Playing( msg );
}

void Capsule_Ponger::actionchain_____onPing( const UMLRTMessage * msg )
{
    update_state( SPECIAL_INTERNAL_STATE_TOP );
    transitionaction_____onPing( msg );
    update_state( Playing );
    entryaction_____Playing( msg );
}

Capsule_Ponger::State Capsule_Ponger::state_____Playing( const UMLRTMessage * msg )
{
    switch( msg->destPort->role()->id )
    {
    case port_pongPort:
        switch( msg->getSignalId() )
        {
        case PingPongProtocol::signal_ping:
            actionchain_____onPing( msg );
            return Playing;
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


static const UMLRTCommsPortRole portroles_border[] = 
{
    {
        Capsule_Ponger::port_pongPort,
        "PingPongProtocol",
        "pongPort",
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
        Capsule_Ponger::port_log,
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

static void instantiate_Ponger( const UMLRTRtsInterface * rts, UMLRTSlot * slot, const UMLRTCommsPort * * borderPorts )
{
    const UMLRTCommsPort * * internalPorts = UMLRTFrameService::createInternalPorts( slot, &Ponger );
    slot->capsule = new Capsule_Ponger( &Ponger, slot, borderPorts, internalPorts, false );
}

const UMLRTCapsuleClass Ponger = 
{
    "Ponger",
    NULL,
    instantiate_Ponger,
    0,
    NULL,
    1,
    portroles_border,
    1,
    portroles_internal
};

