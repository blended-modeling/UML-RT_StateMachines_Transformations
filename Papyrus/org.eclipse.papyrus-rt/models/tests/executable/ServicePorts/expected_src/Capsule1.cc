
#include "Capsule1.hh"

#include "Control.hh"
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
, control( borderPorts[borderport_control] )
, timing( internalPorts[internalport_timing] )
, currentState( SPECIAL_INTERNAL_STATE_UNVISITED )
{
    stateNames[Done] = "Done";
    stateNames[Ended] = "Ended";
    stateNames[Ready] = "Ready";
    stateNames[Started] = "Started";
    stateNames[SPECIAL_INTERNAL_STATE_TOP] = "<top>";
    stateNames[SPECIAL_INTERNAL_STATE_UNVISITED] = "<uninitialized>";
}








void Capsule_Capsule1::bindPort( bool isBorder, int portId, int index )
{
    if( isBorder )
        switch( portId )
        {
        case borderport_control:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_control, index, true );
            break;
        }
}

void Capsule_Capsule1::unbindPort( bool isBorder, int portId, int index )
{
    if( isBorder )
        switch( portId )
        {
        case borderport_control:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_control, index, false );
            UMLRTFrameService::disconnectPort( borderPorts[borderport_control], index );
            break;
        }
}


void Capsule_Capsule1::inject( const UMLRTMessage & message )
{
    msg = &message;
    switch( currentState )
    {
    case Ready:
        currentState = state_____Ready( &message );
        break;
    case Started:
        currentState = state_____Started( &message );
        break;
    case Done:
        currentState = state_____Done( &message );
        break;
    case Ended:
        currentState = state_____Ended( &message );
        break;
    default:
        break;
    }
}

void Capsule_Capsule1::initialize( const UMLRTMessage & message )
{
    msg = &message;
    actionchain_____Initial( &message );
    currentState = Ready;
}

const char * Capsule_Capsule1::getCurrentStateString() const
{
    return stateNames[currentState];
}




void Capsule_Capsule1::update_state( Capsule_Capsule1::State newState )
{
    currentState = newState;
}

void Capsule_Capsule1::entryaction_____Done( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/ServicePorts/ServicePorts.uml ServicePorts::Capsule1::Done entry  */
    log.log(TLFCAPINST_STATE, "received stop message on control port; sending done message through control port");
    control.done().send();
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_Capsule1::entryaction_____Ended( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/ServicePorts/ServicePorts.uml ServicePorts::Capsule1::Ended entry  */
    log.log(TLFCAPINST_STATE, "timed-out; ended task");
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_Capsule1::entryaction_____Ready( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/ServicePorts/ServicePorts.uml ServicePorts::Capsule1::Ready entry  */
    log.log(TLFCAPINST_STATE, "sending ready message through control port");
    control.ready().send();
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_Capsule1::entryaction_____Started( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/ServicePorts/ServicePorts.uml ServicePorts::Capsule1::Started entry  */
    log.log(TLFCAPINST_STATE, "received start message on control port");
    long delay = TimingUtil::getDelayFromCmdLine(1, "Capsule1", "Started");
    timerId = timing.informIn(UMLRTTimespec(delay,0));
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_Capsule1::actionchain_____Initial( const UMLRTMessage * msg )
{
    update_state( Ready );
    entryaction_____Ready( msg );
}

void Capsule_Capsule1::actionchain_____stop0( const UMLRTMessage * msg )
{
    update_state( SPECIAL_INTERNAL_STATE_TOP );
    update_state( Done );
    entryaction_____Done( msg );
}

void Capsule_Capsule1::actionchain_____stop1( const UMLRTMessage * msg )
{
    update_state( SPECIAL_INTERNAL_STATE_TOP );
    update_state( Done );
    entryaction_____Done( msg );
}

void Capsule_Capsule1::actionchain_____transition1( const UMLRTMessage * msg )
{
    update_state( SPECIAL_INTERNAL_STATE_TOP );
    update_state( Started );
    entryaction_____Started( msg );
}

void Capsule_Capsule1::actionchain_____transition2( const UMLRTMessage * msg )
{
    update_state( SPECIAL_INTERNAL_STATE_TOP );
    update_state( Ended );
    entryaction_____Ended( msg );
}

Capsule_Capsule1::State Capsule_Capsule1::state_____Done( const UMLRTMessage * msg )
{
    switch( msg->destPort->role()->id )
    {
    default:
        this->unexpectedMessage();
        break;
    }
    return currentState;
}

Capsule_Capsule1::State Capsule_Capsule1::state_____Ended( const UMLRTMessage * msg )
{
    switch( msg->destPort->role()->id )
    {
    case port_control:
        switch( msg->getSignalId() )
        {
        case Control::signal_stop:
            actionchain_____stop1( msg );
            return Done;
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

Capsule_Capsule1::State Capsule_Capsule1::state_____Ready( const UMLRTMessage * msg )
{
    switch( msg->destPort->role()->id )
    {
    case port_control:
        switch( msg->getSignalId() )
        {
        case Control::signal_start:
            actionchain_____transition1( msg );
            return Started;
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

Capsule_Capsule1::State Capsule_Capsule1::state_____Started( const UMLRTMessage * msg )
{
    switch( msg->destPort->role()->id )
    {
    case port_timing:
        switch( msg->getSignalId() )
        {
        case UMLRTTimerProtocol::signal_timeout:
            actionchain_____transition2( msg );
            return Ended;
        default:
            this->unexpectedMessage();
            break;
        }
        return currentState;
    case port_control:
        switch( msg->getSignalId() )
        {
        case Control::signal_stop:
            actionchain_____stop0( msg );
            return Done;
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
        Capsule_Capsule1::port_control,
        "Control",
        "control",
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
    },
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

