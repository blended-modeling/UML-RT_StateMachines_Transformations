
#include "InheritedCapsule.hh"

#include "PingPongProtocol.hh"
#include "Pinger.hh"
#include "umlrtcommsportrole.hh"
#include "umlrtmessage.hh"
#include "umlrtslot.hh"
#include <cstddef>
#include "umlrtcapsuleclass.hh"
#include "umlrtframeservice.hh"
class UMLRTRtsInterface;
struct UMLRTCommsPort;

Capsule_InheritedCapsule::Capsule_InheritedCapsule( const UMLRTCapsuleClass * cd, UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat )
: Capsule_Pinger( cd, st, border, internal, isStat )
, pingPort( borderPorts[borderport_pingPort] )
, currentState( SPECIAL_INTERNAL_STATE_UNVISITED )
{
    stateNames[Playing] = "Playing";
    stateNames[State1] = "State1";
    stateNames[SPECIAL_INTERNAL_STATE_TOP] = "<top>";
    stateNames[SPECIAL_INTERNAL_STATE_UNVISITED] = "<uninitialized>";
}







void Capsule_InheritedCapsule::bindPort( bool isBorder, int portId, int index )
{
    if( isBorder )
        switch( portId )
        {
        case borderport_pingPort:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_pingPort, index, true );
            break;
        }
}

void Capsule_InheritedCapsule::unbindPort( bool isBorder, int portId, int index )
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

void Capsule_InheritedCapsule::inject( const UMLRTMessage & message )
{
    msg = &message;
    switch( currentState )
    {
    case State1:
        currentState = state_____State1( &message );
        break;
    case Playing:
        currentState = state_____Playing( &message );
        break;
    default:
        break;
    }
}

void Capsule_InheritedCapsule::initialize( const UMLRTMessage & message )
{
    msg = &message;
    actionchain_____Initial( &message );
    currentState = Playing;
}

const char * Capsule_InheritedCapsule::getCurrentStateString() const
{
    return stateNames[currentState];
}




void Capsule_InheritedCapsule::update_state( Capsule_InheritedCapsule::State newState )
{
    currentState = newState;
}

void Capsule_InheritedCapsule::entryaction_____Playing( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/StateMachineInheritance/StateMachineInheritance.uml PingPong::InheritedCapsule::Playing entry  */
    log.log(LCAPINST, "(enter Playing) sending ping");
    pingPort.ping().send();111
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_InheritedCapsule::transitionaction_____transition1( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/StateMachineInheritance/StateMachineInheritance.uml PingPong::InheritedCapsule transition Playing,State1 */
    This is effect111
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

bool Capsule_InheritedCapsule::guard_____onPong( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/StateMachineInheritance/StateMachineInheritance.uml PingPong::InheritedCapsule guard Playing,Playing,pong:pingPort */
    printf("This is onPong redefined guard");
    111
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

bool Capsule_InheritedCapsule::guard_____transition1( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/StateMachineInheritance/StateMachineInheritance.uml PingPong::InheritedCapsule guard Playing,State1 */
    This is guard
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_InheritedCapsule::actionchain_____Initial( const UMLRTMessage * msg )
{
    update_state( Playing );
    entryaction_____Playing( msg );
}

void Capsule_InheritedCapsule::actionchain_____onPong( const UMLRTMessage * msg )
{
    update_state( SPECIAL_INTERNAL_STATE_TOP );
    update_state( Playing );
    entryaction_____Playing( msg );
}

void Capsule_InheritedCapsule::actionchain_____transition1( const UMLRTMessage * msg )
{
    update_state( SPECIAL_INTERNAL_STATE_TOP );
    transitionaction_____transition1( msg );
    update_state( State1 );
}

Capsule_InheritedCapsule::State Capsule_InheritedCapsule::state_____Playing( const UMLRTMessage * msg )
{
    switch( msg->destPort->role()->id )
    {
    case port_pingPort:
        switch( msg->getSignalId() )
        {
        case PingPongProtocol::signal_pong:
            if( guard_____onPong( msg ) )
            {
                actionchain_____onPong( msg );
                return Playing;
            }
            break;
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

Capsule_InheritedCapsule::State Capsule_InheritedCapsule::state_____State1( const UMLRTMessage * msg )
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
        Capsule_InheritedCapsule::port_pingPort,
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
        Capsule_InheritedCapsule::port_log,
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

static void instantiate_InheritedCapsule( const UMLRTRtsInterface * rts, UMLRTSlot * slot, const UMLRTCommsPort * * borderPorts )
{
    const UMLRTCommsPort * * internalPorts = UMLRTFrameService::createInternalPorts( slot, &InheritedCapsule );
    slot->capsule = new Capsule_InheritedCapsule( &InheritedCapsule, slot, borderPorts, internalPorts, false );
}

const UMLRTCapsuleClass InheritedCapsule = 
{
    "InheritedCapsule",
    &Pinger,
    instantiate_InheritedCapsule,
    0,
    NULL,
    1,
    portroles_border,
    1,
    portroles_internal
};

