
#include "Server1.hh"

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

Capsule_Server1::Capsule_Server1( const UMLRTCapsuleClass * cd, UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat )
: Capsule_Server( cd, st, border, internal, isStat )
, service( internalPorts[internalport_service] )
, timing( internalPorts[internalport_timing] )
, currentState( SPECIAL_INTERNAL_STATE_UNVISITED )
{
    stateNames[Responding] = "Responding";
    stateNames[WaitingForRequests] = "WaitingForRequests";
    stateNames[SPECIAL_INTERNAL_STATE_TOP] = "<top>";
    stateNames[SPECIAL_INTERNAL_STATE_UNVISITED] = "<uninitialized>";
}







void Capsule_Server1::bindPort( bool isBorder, int portId, int index )
{
}

void Capsule_Server1::unbindPort( bool isBorder, int portId, int index )
{
}

void Capsule_Server1::inject( const UMLRTMessage & message )
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

void Capsule_Server1::initialize( const UMLRTMessage & message )
{
    msg = &message;
    actionchain_____Initial( &message );
    currentState = WaitingForRequests;
}

const char * Capsule_Server1::getCurrentStateString() const
{
    return stateNames[currentState];
}




void Capsule_Server1::update_state( Capsule_Server1::State newState )
{
    currentState = newState;
}

void Capsule_Server1::entryaction_____Responding( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/SPPInheritance/SPPInheritance.uml SPPInheritance::Server1::Responding entry  */
    log.log(TLFCAPINST_STATE, "specialized processing request");
    long delay = TimingUtil::getDelayFromCmdLine(3, "Top", "Starting");
    timing.informIn(UMLRTTimespec(delay,0));
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_Server1::entryaction_____WaitingForRequests( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/SPPInheritance/SPPInheritance.uml SPPInheritance::Server1::WaitingForRequests entry  */
    log.log(TLFCAPINST_STATE, "specialized listening...");
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_Server1::transitionaction_____implicitTransition1( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/SPPInheritance/SPPInheritance.uml SPPInheritance::Server1 transition WaitingForRequests,Responding,request:service */
    log.log(TLFCAPINST_STATE, "received request on service port");
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_Server1::transitionaction_____implicitTransition2( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/SPPInheritance/SPPInheritance.uml SPPInheritance::Server1 transition Responding,WaitingForRequests,timeout:timing */
    service.response().send();
    log.log(TLFCAPINST_STATE, "sent response on service port");
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_Server1::actionchain_____Initial( const UMLRTMessage * msg )
{
    update_state( WaitingForRequests );
    entryaction_____WaitingForRequests( msg );
}

void Capsule_Server1::actionchain_____implicitTransition1( const UMLRTMessage * msg )
{
    update_state( SPECIAL_INTERNAL_STATE_TOP );
    transitionaction_____implicitTransition1( msg );
    update_state( Responding );
    entryaction_____Responding( msg );
}

void Capsule_Server1::actionchain_____implicitTransition2( const UMLRTMessage * msg )
{
    update_state( SPECIAL_INTERNAL_STATE_TOP );
    transitionaction_____implicitTransition2( msg );
    update_state( WaitingForRequests );
    entryaction_____WaitingForRequests( msg );
}

Capsule_Server1::State Capsule_Server1::state_____Responding( const UMLRTMessage * msg )
{
    switch( msg->destPort->role()->id )
    {
    case port_timing:
        switch( msg->getSignalId() )
        {
        case UMLRTTimerProtocol::signal_timeout:
            actionchain_____implicitTransition2( msg );
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

Capsule_Server1::State Capsule_Server1::state_____WaitingForRequests( const UMLRTMessage * msg )
{
    switch( msg->destPort->role()->id )
    {
    case port_service:
        switch( msg->getSignalId() )
        {
        case Service::signal_request:
            actionchain_____implicitTransition1( msg );
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
        Capsule_Server1::port_service,
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
        Capsule_Server1::port_timing,
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
        Capsule_Server1::port_log,
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

static void instantiate_Server1( const UMLRTRtsInterface * rts, UMLRTSlot * slot, const UMLRTCommsPort * * borderPorts )
{
    const UMLRTCommsPort * * internalPorts = UMLRTFrameService::createInternalPorts( slot, &Server1 );
    slot->capsule = new Capsule_Server1( &Server1, slot, borderPorts, internalPorts, false );
}

const UMLRTCapsuleClass Server1 = 
{
    "Server1",
    &Server,
    instantiate_Server1,
    0,
    NULL,
    0,
    NULL,
    3,
    portroles_internal
};

