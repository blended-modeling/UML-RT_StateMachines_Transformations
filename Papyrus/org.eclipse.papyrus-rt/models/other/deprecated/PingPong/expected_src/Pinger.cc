
#include "Pinger.hh"

#include "PingPongProtocol.hh"
#include "umlrtcommsportrole.hh"
#include "umlrtmessage.hh"
#include "umlrtslot.hh"
#include "umlrttimerprotocol.hh"
#include <cstddef>
#include "umlrtcapsuleclass.hh"
#include "umlrtframeservice.hh"
class UMLRTRtsInterface;
struct UMLRTCommsPort;

Capsule_Pinger::Capsule_Pinger( const UMLRTCapsuleClass * cd, UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat )
: UMLRTCapsule( NULL, cd, st, border, internal, isStat )
, PingPort( borderPorts[borderport_PingPort] )
, timerPort( borderPorts[borderport_timerPort] )
, currentState( SPECIAL_INTERNAL_STATE_UNVISITED )
{
    stateNames[top__Running] = "top__Running";
    stateNames[SPECIAL_INTERNAL_STATE_UNVISITED] = "<uninitialized>";
}






void Capsule_Pinger::bindPort( bool isBorder, int portId, int index )
{
    if( isBorder )
        switch( portId )
        {
        case borderport_PingPort:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_PingPort, index, true );
            break;
        case borderport_timerPort:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_timerPort, index, true );
            break;
        }
}

void Capsule_Pinger::unbindPort( bool isBorder, int portId, int index )
{
    if( isBorder )
        switch( portId )
        {
        case borderport_PingPort:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_PingPort, index, false );
            UMLRTFrameService::disconnectPort( borderPorts[borderport_PingPort], index );
            break;
        case borderport_timerPort:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_timerPort, index, false );
            UMLRTFrameService::disconnectPort( borderPorts[borderport_timerPort], index );
            break;
        }
}

void Capsule_Pinger::inject( const UMLRTMessage & message )
{
    msg = &message;
    switch( currentState )
    {
    case top__Running:
        currentState = state_____top__Running( &message );
        break;
    default:
        break;
    }
}

void Capsule_Pinger::initialize( const UMLRTMessage & message )
{
    msg = &message;
    actionchain_____action_____PingPong__Pinger__Pinger_SM__Region1__initial( &message );
    currentState = top__Running;
}

const char * Capsule_Pinger::getCurrentStateString() const
{
    return stateNames[currentState];
}




void Capsule_Pinger::entryaction_____PingPong__Pinger__Pinger_SM__Region1__Running__onEntry( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/PingPong/PingPong.uml PingPong::Pinger::Running entry  */
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_Pinger::transitionaction_____PingPong__Pinger__Pinger_SM__Region1__initial__onInit( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/PingPong/PingPong.uml PingPong::Pinger transition Initial,Running */
    std::cout << getName() << ": timer started" << std::endl;
    timerPort.informIn( UMLRTTimespec( 1, 0 ) );
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_Pinger::transitionaction_____PingPong__Pinger__Pinger_SM__Region1__onPong__onPong( const UMLRTMessage * msg )
{
    #define umlrtparam_param ( *(int *)msg->getParam( 0 ) )
    #define rtdata ( (int *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/PingPong/PingPong.uml PingPong::Pinger transition Running,Running,pong:PingPort */
    std::cout << getName() << ": pong( " << * rtdata << " ) received, sending ping" << std::endl;
    PingPort.ping( * rtdata + 1 ).send(); 
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
    #undef umlrtparam_param
}

void Capsule_Pinger::transitionaction_____PingPong__Pinger__Pinger_SM__Region1__onTimeout__onTimeout( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/PingPong/PingPong.uml PingPong::Pinger transition Running,Running,timeout:timerPort */
    std::cout << getName() << ": timeout, sending ping( 0 )" << std::endl;
    PingPort.ping( 0 ).send();
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_Pinger::actionchain_____action_____PingPong__Pinger__Pinger_SM__Region1__initial( const UMLRTMessage * msg )
{
    transitionaction_____PingPong__Pinger__Pinger_SM__Region1__initial__onInit( msg );
    entryaction_____PingPong__Pinger__Pinger_SM__Region1__Running__onEntry( msg );
}

void Capsule_Pinger::actionchain_____action_____PingPong__Pinger__Pinger_SM__Region1__onPong( const UMLRTMessage * msg )
{
    transitionaction_____PingPong__Pinger__Pinger_SM__Region1__onPong__onPong( msg );
    entryaction_____PingPong__Pinger__Pinger_SM__Region1__Running__onEntry( msg );
}

void Capsule_Pinger::actionchain_____action_____PingPong__Pinger__Pinger_SM__Region1__onTimeout( const UMLRTMessage * msg )
{
    transitionaction_____PingPong__Pinger__Pinger_SM__Region1__onTimeout__onTimeout( msg );
    entryaction_____PingPong__Pinger__Pinger_SM__Region1__Running__onEntry( msg );
}

Capsule_Pinger::State Capsule_Pinger::state_____top__Running( const UMLRTMessage * msg )
{
    switch( msg->destPort->role()->id )
    {
    case port_PingPort:
        switch( msg->getSignalId() )
        {
        case PingPongProtocol::signal_pong:
            actionchain_____action_____PingPong__Pinger__Pinger_SM__Region1__onPong( msg );
            return top__Running;
        default:
            this->unexpectedMessage();
            break;
        }
        return currentState;
    case port_timerPort:
        switch( msg->getSignalId() )
        {
        case UMLRTTimerProtocol::signal_timeout:
            actionchain_____action_____PingPong__Pinger__Pinger_SM__Region1__onTimeout( msg );
            return top__Running;
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
        Capsule_Pinger::port_PingPort,
        "PingPongProtocol",
        "PingPort",
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
        Capsule_Pinger::port_timerPort,
        "Timing",
        "timerPort",
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

static void instantiate_Pinger( const UMLRTRtsInterface * rts, UMLRTSlot * slot, const UMLRTCommsPort * * borderPorts )
{
    slot->capsule = new Capsule_Pinger( &Pinger, slot, borderPorts, NULL, false );
}

const UMLRTCapsuleClass Pinger = 
{
    "Pinger",
    NULL,
    instantiate_Pinger,
    0,
    NULL,
    2,
    portroles_border,
    0,
    NULL
};

