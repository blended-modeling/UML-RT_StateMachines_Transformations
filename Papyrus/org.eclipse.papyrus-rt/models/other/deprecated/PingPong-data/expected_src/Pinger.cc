
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
, ping( borderPorts[borderport_ping] )
, timer( borderPorts[borderport_timer] )
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
        case borderport_ping:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_ping, index, true );
            break;
        case borderport_timer:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_timer, index, true );
            break;
        }
}

void Capsule_Pinger::unbindPort( bool isBorder, int portId, int index )
{
    if( isBorder )
        switch( portId )
        {
        case borderport_ping:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_ping, index, false );
            UMLRTFrameService::disconnectPort( borderPorts[borderport_ping], index );
            break;
        case borderport_timer:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_timer, index, false );
            UMLRTFrameService::disconnectPort( borderPorts[borderport_timer], index );
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
    actionchain_____action_____PingPong_data__Pinger__Pinger_SM__Region1__initialise( &message );
    currentState = top__Running;
}

const char * Capsule_Pinger::getCurrentStateString() const
{
    return stateNames[currentState];
}




void Capsule_Pinger::transitionaction_____PingPong_data__Pinger__Pinger_SM__Region1__initialise__onInit( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/PingPong-data/PingPong-data.uml PingPong-data::Pinger transition Initial,Running */
    std::cout << "Pinger initialised" << std::endl;
    timer.informIn( UMLRTTimespec( 2, 0 ) );
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_Pinger::transitionaction_____PingPong_data__Pinger__Pinger_SM__Region1__onPong__onPong( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/PingPong-data/PingPong-data.uml PingPong-data::Pinger transition Running,Running,pong:ping */
    std::cout << "Pong received" << std::endl;
    timer.informIn( UMLRTTimespec( 2, 0 ) );
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_Pinger::transitionaction_____PingPong_data__Pinger__Pinger_SM__Region1__onTimeout__onTimeout( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/PingPong-data/PingPong-data.uml PingPong-data::Pinger transition Running,Running,timeout:timer */
    static int * ptr = NULL;
    if( ! ptr )
    ptr = new int( 0 );
    std::cout << "Sending Ping from timeout transition action" << std::endl;
    ++*ptr;
    DataType1 dt = { one, 0, true, 3., ptr, 1.618f, "hello" };
    ping.ping( dt ).send();
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_Pinger::actionchain_____action_____PingPong_data__Pinger__Pinger_SM__Region1__initialise( const UMLRTMessage * msg )
{
    transitionaction_____PingPong_data__Pinger__Pinger_SM__Region1__initialise__onInit( msg );
}

void Capsule_Pinger::actionchain_____action_____PingPong_data__Pinger__Pinger_SM__Region1__onPong( const UMLRTMessage * msg )
{
    transitionaction_____PingPong_data__Pinger__Pinger_SM__Region1__onPong__onPong( msg );
}

void Capsule_Pinger::actionchain_____action_____PingPong_data__Pinger__Pinger_SM__Region1__onTimeout( const UMLRTMessage * msg )
{
    transitionaction_____PingPong_data__Pinger__Pinger_SM__Region1__onTimeout__onTimeout( msg );
}

Capsule_Pinger::State Capsule_Pinger::state_____top__Running( const UMLRTMessage * msg )
{
    switch( msg->destPort->role()->id )
    {
    case port_ping:
        switch( msg->getSignalId() )
        {
        case PingPongProtocol::signal_pong:
            actionchain_____action_____PingPong_data__Pinger__Pinger_SM__Region1__onPong( msg );
            return top__Running;
        default:
            this->unexpectedMessage();
            break;
        }
        return currentState;
    case port_timer:
        switch( msg->getSignalId() )
        {
        case UMLRTTimerProtocol::signal_timeout:
            actionchain_____action_____PingPong_data__Pinger__Pinger_SM__Region1__onTimeout( msg );
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
        Capsule_Pinger::port_ping,
        "PingPongProtocol",
        "ping",
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
        Capsule_Pinger::port_timer,
        "Timing",
        "timer",
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

