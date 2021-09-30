
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

Capsule_Ponger::Capsule_Ponger( const UMLRTCapsuleClass * cd, UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat )
: UMLRTCapsule( NULL, cd, st, border, internal, isStat )
, PongPort( borderPorts[borderport_PongPort] )
, currentState( SPECIAL_INTERNAL_STATE_UNVISITED )
{
    stateNames[top__Running] = "top__Running";
    stateNames[SPECIAL_INTERNAL_STATE_UNVISITED] = "<uninitialized>";
}





void Capsule_Ponger::bindPort( bool isBorder, int portId, int index )
{
    if( isBorder )
        switch( portId )
        {
        case borderport_PongPort:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_PongPort, index, true );
            break;
        }
}

void Capsule_Ponger::unbindPort( bool isBorder, int portId, int index )
{
    if( isBorder )
        switch( portId )
        {
        case borderport_PongPort:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_PongPort, index, false );
            UMLRTFrameService::disconnectPort( borderPorts[borderport_PongPort], index );
            break;
        }
}


void Capsule_Ponger::inject( const UMLRTMessage & message )
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

void Capsule_Ponger::initialize( const UMLRTMessage & message )
{
    msg = &message;
    actionchain_____action_____PingPong__Ponger__Ponger_SM__Region1__initial( &message );
    currentState = top__Running;
}

const char * Capsule_Ponger::getCurrentStateString() const
{
    return stateNames[currentState];
}




void Capsule_Ponger::transitionaction_____PingPong__Ponger__Ponger_SM__Region1__initial__onInit( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/PingPong/PingPong.uml PingPong::Ponger transition Initial,Running */
    int limit = 0;
    if( UMLRTMain::getArgCount() > 0 )
    {
    std::cout << "parsing arg '" << UMLRTMain::getArg( 0 ) << '\'' << std::endl;
    limit = atoi( UMLRTMain::getArg( 0 ) );
    }
    if( limit <= 0 )
    limit = 15;
    messageLimit = limit;
    std::cout << getName() << ": initialized with message limit " << messageLimit << std::endl;
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_Ponger::transitionaction_____PingPong__Ponger__Ponger_SM__Region1__onPing__onPing( const UMLRTMessage * msg )
{
    #define umlrtparam_param ( *(int *)msg->getParam( 0 ) )
    #define rtdata ( (int *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/PingPong/PingPong.uml PingPong::Ponger transition Running,Running,ping:PongPort */
    if(* rtdata < messageLimit )
    {
    std::cout << getName() << ": ping( " << * rtdata << " ) received, sending Pong" << std::endl;
    PongPort.pong( * rtdata ).send(); 
    }
    else
    {
    std::cout << getName() << ": ping( " << * rtdata << " ) received, done" << std::endl;
    exit( 0 );
    }
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
    #undef umlrtparam_param
}

void Capsule_Ponger::actionchain_____action_____PingPong__Ponger__Ponger_SM__Region1__initial( const UMLRTMessage * msg )
{
    transitionaction_____PingPong__Ponger__Ponger_SM__Region1__initial__onInit( msg );
}

void Capsule_Ponger::actionchain_____action_____PingPong__Ponger__Ponger_SM__Region1__onPing( const UMLRTMessage * msg )
{
    transitionaction_____PingPong__Ponger__Ponger_SM__Region1__onPing__onPing( msg );
}

Capsule_Ponger::State Capsule_Ponger::state_____top__Running( const UMLRTMessage * msg )
{
    switch( msg->destPort->role()->id )
    {
    case port_PongPort:
        switch( msg->getSignalId() )
        {
        case PingPongProtocol::signal_ping:
            actionchain_____action_____PingPong__Ponger__Ponger_SM__Region1__onPing( msg );
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
        Capsule_Ponger::port_PongPort,
        "PingPongProtocol",
        "PongPort",
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

static void instantiate_Ponger( const UMLRTRtsInterface * rts, UMLRTSlot * slot, const UMLRTCommsPort * * borderPorts )
{
    slot->capsule = new Capsule_Ponger( &Ponger, slot, borderPorts, NULL, false );
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
    0,
    NULL
};

