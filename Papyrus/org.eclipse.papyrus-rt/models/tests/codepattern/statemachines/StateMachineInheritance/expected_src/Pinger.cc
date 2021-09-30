
#include "Pinger.hh"

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

Capsule_Pinger::Capsule_Pinger( const UMLRTCapsuleClass * cd, UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat )
: UMLRTCapsule( NULL, cd, st, border, internal, isStat )
, pingPort( borderPorts[borderport_pingPort] )
, currentState( SPECIAL_INTERNAL_STATE_UNVISITED )
{
    stateNames[Playing] = "Playing";
    stateNames[SPECIAL_INTERNAL_STATE_TOP] = "<top>";
    stateNames[SPECIAL_INTERNAL_STATE_UNVISITED] = "<uninitialized>";
}







void Capsule_Pinger::bindPort( bool isBorder, int portId, int index )
{
    if( isBorder )
        switch( portId )
        {
        case borderport_pingPort:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_pingPort, index, true );
            break;
        }
}

void Capsule_Pinger::unbindPort( bool isBorder, int portId, int index )
{
    if( isBorder )
        switch( portId )
        {
        case borderport_pingPort:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_pingPort, index, false );
            UMLRTFrameService::disconnectPort( borderPorts[borderport_pingPort], index );
            break;
        }
}

void Capsule_Pinger::inject( const UMLRTMessage & message )
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

void Capsule_Pinger::initialize( const UMLRTMessage & message )
{
    msg = &message;
    actionchain_____Initial( &message );
    currentState = Playing;
}

const char * Capsule_Pinger::getCurrentStateString() const
{
    return stateNames[currentState];
}




void Capsule_Pinger::update_state( Capsule_Pinger::State newState )
{
    currentState = newState;
}

void Capsule_Pinger::entryaction_____Playing( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/StateMachineInheritance/StateMachineInheritance.uml PingPong::Pinger::Playing entry  */
    log.log(LCAPINST, "(enter Playing) sending ping");
    pingPort.ping().send();
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_Pinger::transitionaction_____onPong( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/StateMachineInheritance/StateMachineInheritance.uml PingPong::Pinger transition Playing,Playing,pong:pingPort */
    log.log(LCAPINST, "(onPong)");
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_Pinger::actionchain_____Initial( const UMLRTMessage * msg )
{
    update_state( Playing );
    entryaction_____Playing( msg );
}

void Capsule_Pinger::actionchain_____onPong( const UMLRTMessage * msg )
{
    update_state( SPECIAL_INTERNAL_STATE_TOP );
    transitionaction_____onPong( msg );
    update_state( Playing );
    entryaction_____Playing( msg );
}

Capsule_Pinger::State Capsule_Pinger::state_____Playing( const UMLRTMessage * msg )
{
    switch( msg->destPort->role()->id )
    {
    case port_pingPort:
        switch( msg->getSignalId() )
        {
        case PingPongProtocol::signal_pong:
            actionchain_____onPong( msg );
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
        Capsule_Pinger::port_pingPort,
        "PingPongProtocol",
        "pingPort",
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
        Capsule_Pinger::port_log,
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

static void instantiate_Pinger( const UMLRTRtsInterface * rts, UMLRTSlot * slot, const UMLRTCommsPort * * borderPorts )
{
    const UMLRTCommsPort * * internalPorts = UMLRTFrameService::createInternalPorts( slot, &Pinger );
    slot->capsule = new Capsule_Pinger( &Pinger, slot, borderPorts, internalPorts, false );
}

const UMLRTCapsuleClass Pinger = 
{
    "Pinger",
    NULL,
    instantiate_Pinger,
    0,
    NULL,
    1,
    portroles_border,
    1,
    portroles_internal
};

