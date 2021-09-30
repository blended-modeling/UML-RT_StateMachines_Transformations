
#include "Server.hh"

#include "Service.hh"
#include "umlrtcommsportrole.hh"
#include "umlrtmessage.hh"
#include "umlrtslot.hh"
#include "umlrttimerprotocol.hh"
#include <cstddef>
#include "umlrtcapsuleclass.hh"
#include "umlrtframeservice.hh"
class UMLRTRtsInterface;
struct UMLRTCommsPort;

Capsule_Server::Capsule_Server( const UMLRTCapsuleClass * cd, UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat )
: UMLRTCapsule( NULL, cd, st, border, internal, isStat )
, service( internalPorts[internalport_service] )
, timing( internalPorts[internalport_timing] )
, currentState( SPECIAL_INTERNAL_STATE_UNVISITED )
{
    stateNames[Responding] = "Responding";
    stateNames[WaitingForRequests] = "WaitingForRequests";
    stateNames[SPECIAL_INTERNAL_STATE_TOP] = "<top>";
    stateNames[SPECIAL_INTERNAL_STATE_UNVISITED] = "<uninitialized>";
}







void Capsule_Server::bindPort( bool isBorder, int portId, int index )
{
}

void Capsule_Server::unbindPort( bool isBorder, int portId, int index )
{
}

void Capsule_Server::inject( const UMLRTMessage & message )
{
    msg = &message;
    switch( currentState )
    {
    case WaitingForRequests:
        currentState = state_____WaitingForRequests( &message );
        break;
    case Responding:
        currentState = state_____Responding( &message );
        break;
    default:
        break;
    }
}

void Capsule_Server::initialize( const UMLRTMessage & message )
{
    msg = &message;
    actionchain_____Initial( &message );
    currentState = WaitingForRequests;
}

const char * Capsule_Server::getCurrentStateString() const
{
    return stateNames[currentState];
}




void Capsule_Server::update_state( Capsule_Server::State newState )
{
    currentState = newState;
}

void Capsule_Server::entryaction_____Responding( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/SPPInheritance/SPPInheritance.uml SPPInheritance::Server::Responding entry  */
    log.log(TLFCAPINST_STATE, "processing request");
    long delay = TimingUtil::getDelayFromCmdLine(3, "Top", "Starting");
    timing.informIn(UMLRTTimespec(delay,0));
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_Server::entryaction_____WaitingForRequests( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/SPPInheritance/SPPInheritance.uml SPPInheritance::Server::WaitingForRequests entry  */
    log.log(TLFCAPINST_STATE, "listening...");
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_Server::transitionaction_____transition1( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/SPPInheritance/SPPInheritance.uml SPPInheritance::Server transition WaitingForRequests,Responding,request:service */
    log.log(TLFCAPINST_STATE, "received request on service port");
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_Server::transitionaction_____transition2( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/SPPInheritance/SPPInheritance.uml SPPInheritance::Server transition Responding,WaitingForRequests,timeout:timing */
    service.response().send();
    log.log(TLFCAPINST_STATE, "sent response on service port");
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_Server::actionchain_____Initial( const UMLRTMessage * msg )
{
    update_state( WaitingForRequests );
    entryaction_____WaitingForRequests( msg );
}

void Capsule_Server::actionchain_____transition1( const UMLRTMessage * msg )
{
    update_state( SPECIAL_INTERNAL_STATE_TOP );
    transitionaction_____transition1( msg );
    update_state( Responding );
    entryaction_____Responding( msg );
}

void Capsule_Server::actionchain_____transition2( const UMLRTMessage * msg )
{
    update_state( SPECIAL_INTERNAL_STATE_TOP );
    transitionaction_____transition2( msg );
    update_state( WaitingForRequests );
    entryaction_____WaitingForRequests( msg );
}

Capsule_Server::State Capsule_Server::state_____Responding( const UMLRTMessage * msg )
{
    switch( msg->destPort->role()->id )
    {
    case port_timing:
        switch( msg->getSignalId() )
        {
        case UMLRTTimerProtocol::signal_timeout:
            actionchain_____transition2( msg );
            return WaitingForRequests;
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

Capsule_Server::State Capsule_Server::state_____WaitingForRequests( const UMLRTMessage * msg )
{
    switch( msg->destPort->role()->id )
    {
    case port_service:
        switch( msg->getSignalId() )
        {
        case Service::signal_request:
            actionchain_____transition1( msg );
            return Responding;
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


static const UMLRTCommsPortRole portroles_internal[] = 
{
    {
        Capsule_Server::port_service,
        "Service",
        "service",
        "",
        1,
        true,
        false,
        false,
        false,
        false,
        true,
        false
    },
    {
        Capsule_Server::port_timing,
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
    },
    {
        Capsule_Server::port_log,
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

static void instantiate_Server( const UMLRTRtsInterface * rts, UMLRTSlot * slot, const UMLRTCommsPort * * borderPorts )
{
    const UMLRTCommsPort * * internalPorts = UMLRTFrameService::createInternalPorts( slot, &Server );
    slot->capsule = new Capsule_Server( &Server, slot, borderPorts, internalPorts, false );
}

const UMLRTCapsuleClass Server = 
{
    "Server",
    NULL,
    instantiate_Server,
    0,
    NULL,
    0,
    NULL,
    3,
    portroles_internal
};

