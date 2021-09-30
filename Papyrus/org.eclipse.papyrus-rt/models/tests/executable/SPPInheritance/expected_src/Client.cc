
#include "Client.hh"

#include "Service.hh"
#include "umlrtcommsportrole.hh"
#include "umlrtmessage.hh"
#include "umlrtsignal.hh"
#include "umlrtslot.hh"
#include "umlrttimerprotocol.hh"
#include <cstddef>
#include "umlrtcapsuleclass.hh"
#include "umlrtframeservice.hh"
class UMLRTRtsInterface;
struct UMLRTCommsPort;

Capsule_Client::Capsule_Client( const UMLRTCapsuleClass * cd, UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat )
: UMLRTCapsule( NULL, cd, st, border, internal, isStat )
, service( internalPorts[internalport_service] )
, timing( internalPorts[internalport_timing] )
, currentState( SPECIAL_INTERNAL_STATE_UNVISITED )
{
    stateNames[Done] = "Done";
    stateNames[Ready] = "Ready";
    stateNames[TimedOut] = "TimedOut";
    stateNames[WaitingForServiceToBeReady] = "WaitingForServiceToBeReady";
    stateNames[SPECIAL_INTERNAL_STATE_TOP] = "<top>";
    stateNames[SPECIAL_INTERNAL_STATE_UNVISITED] = "<uninitialized>";
}







void Capsule_Client::bindPort( bool isBorder, int portId, int index )
{
}

void Capsule_Client::unbindPort( bool isBorder, int portId, int index )
{
}


void Capsule_Client::inject( const UMLRTMessage & message )
{
    msg = &message;
    switch( currentState )
    {
    case WaitingForServiceToBeReady:
        currentState = state_____WaitingForServiceToBeReady( &message );
        break;
    case TimedOut:
        currentState = state_____TimedOut( &message );
        break;
    case Ready:
        currentState = state_____Ready( &message );
        break;
    case Done:
        currentState = state_____Done( &message );
        break;
    default:
        break;
    }
}

void Capsule_Client::initialize( const UMLRTMessage & message )
{
    msg = &message;
    actionchain_____Initial( &message );
    currentState = WaitingForServiceToBeReady;
}

const char * Capsule_Client::getCurrentStateString() const
{
    return stateNames[currentState];
}




void Capsule_Client::update_state( Capsule_Client::State newState )
{
    currentState = newState;
}

void Capsule_Client::entryaction_____Done( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/SPPInheritance/SPPInheritance.uml SPPInheritance::Client::Done entry  */
    log.log(TLFCAPINST_STATE, "response received");
    exit(0);
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_Client::entryaction_____Ready( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/SPPInheritance/SPPInheritance.uml SPPInheritance::Client::Ready entry  */
    log.log(TLFCAPINST_STATE, "service ready; sending request and setting timer to maximum service response waiting time");
    long delay = TimingUtil::getDelayFromCmdLine(2, "Client", "Ready");
    timing.informIn(UMLRTTimespec(delay,0));
    service.request().send();
    log.log(TLFCAPINST_STATE, "request sent; waiting for response");
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_Client::entryaction_____TimedOut( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/SPPInheritance/SPPInheritance.uml SPPInheritance::Client::TimedOut entry  */
    log.log(TLFCAPINST_STATE, "no service response");
    exit(0);
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_Client::entryaction_____WaitingForServiceToBeReady( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/SPPInheritance/SPPInheritance.uml SPPInheritance::Client::WaitingForServiceToBeReady entry  */
    log.log(TLFCAPINST_STATE, "setting timer to maximum server readiness waiting time");
    long delay = TimingUtil::getDelayFromCmdLine(1, "Client", "WaitingForServiceToBeReady");
    timerId = timing.informIn(UMLRTTimespec(delay,0));
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_Client::transitionaction_____timeout1( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/SPPInheritance/SPPInheritance.uml SPPInheritance::Client transition WaitingForServiceToBeReady,TimedOut,timeout:timing */
    log.log(TLFCAPINST_STATE, "service isn't ready");
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_Client::transitionaction_____timeout2( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/SPPInheritance/SPPInheritance.uml SPPInheritance::Client transition Ready,TimedOut,timeout:timing */
    log.log(TLFCAPINST_STATE, "service didn't respond to request");
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_Client::transitionaction_____transition2( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/SPPInheritance/SPPInheritance.uml SPPInheritance::Client transition WaitingForServiceToBeReady,Ready,rtBound:service */
    log.log(TLFCAPINST_STATE, "received rtBound on service port; cancelling previous timer");
    timing.cancelTimer(timerId);
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_Client::actionchain_____Initial( const UMLRTMessage * msg )
{
    update_state( WaitingForServiceToBeReady );
    entryaction_____WaitingForServiceToBeReady( msg );
}

void Capsule_Client::actionchain_____timeout1( const UMLRTMessage * msg )
{
    update_state( SPECIAL_INTERNAL_STATE_TOP );
    transitionaction_____timeout1( msg );
    update_state( TimedOut );
    entryaction_____TimedOut( msg );
}

void Capsule_Client::actionchain_____timeout2( const UMLRTMessage * msg )
{
    update_state( SPECIAL_INTERNAL_STATE_TOP );
    transitionaction_____timeout2( msg );
    update_state( TimedOut );
    entryaction_____TimedOut( msg );
}

void Capsule_Client::actionchain_____transition2( const UMLRTMessage * msg )
{
    update_state( SPECIAL_INTERNAL_STATE_TOP );
    transitionaction_____transition2( msg );
    update_state( Ready );
    entryaction_____Ready( msg );
}

void Capsule_Client::actionchain_____transition3( const UMLRTMessage * msg )
{
    update_state( SPECIAL_INTERNAL_STATE_TOP );
    update_state( Done );
    entryaction_____Done( msg );
}

Capsule_Client::State Capsule_Client::state_____Done( const UMLRTMessage * msg )
{
    switch( msg->destPort->role()->id )
    {
    default:
        this->unexpectedMessage();
        break;
    }
    return currentState;
}

Capsule_Client::State Capsule_Client::state_____Ready( const UMLRTMessage * msg )
{
    switch( msg->destPort->role()->id )
    {
    case port_service:
        switch( msg->getSignalId() )
        {
        case Service::signal_response:
            actionchain_____transition3( msg );
            return Done;
        default:
            this->unexpectedMessage();
            break;
        }
        return currentState;
    case port_timing:
        switch( msg->getSignalId() )
        {
        case UMLRTTimerProtocol::signal_timeout:
            actionchain_____timeout2( msg );
            return TimedOut;
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

Capsule_Client::State Capsule_Client::state_____TimedOut( const UMLRTMessage * msg )
{
    switch( msg->destPort->role()->id )
    {
    default:
        this->unexpectedMessage();
        break;
    }
    return currentState;
}

Capsule_Client::State Capsule_Client::state_____WaitingForServiceToBeReady( const UMLRTMessage * msg )
{
    switch( msg->destPort->role()->id )
    {
    case port_timing:
        switch( msg->getSignalId() )
        {
        case UMLRTTimerProtocol::signal_timeout:
            actionchain_____timeout1( msg );
            return TimedOut;
        default:
            this->unexpectedMessage();
            break;
        }
        return currentState;
    case port_service:
        switch( msg->getSignalId() )
        {
        case UMLRTSignal::rtBound:
            actionchain_____transition2( msg );
            return Ready;
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
        Capsule_Client::port_service,
        "Service",
        "service",
        "",
        1,
        true,
        true,
        false,
        true,
        true,
        false,
        false
    },
    {
        Capsule_Client::port_timing,
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
        Capsule_Client::port_log,
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

static void instantiate_Client( const UMLRTRtsInterface * rts, UMLRTSlot * slot, const UMLRTCommsPort * * borderPorts )
{
    const UMLRTCommsPort * * internalPorts = UMLRTFrameService::createInternalPorts( slot, &Client );
    slot->capsule = new Capsule_Client( &Client, slot, borderPorts, internalPorts, false );
}

const UMLRTCapsuleClass Client = 
{
    "Client",
    NULL,
    instantiate_Client,
    0,
    NULL,
    0,
    NULL,
    3,
    portroles_internal
};

