
#include "Top.hh"

#include "Capsule1.hh"
#include "Control.hh"
#include "umlrtcapsulepart.hh"
#include "umlrtcommsportrole.hh"
#include "umlrtmessage.hh"
#include "umlrtslot.hh"
#include "umlrttimerprotocol.hh"
#include <cstddef>
#include "umlrtcapsuleclass.hh"
#include "umlrtcapsulerole.hh"
#include "umlrtcommsport.hh"
#include "umlrtframeservice.hh"
class UMLRTRtsInterface;

Capsule_Top::Capsule_Top( const UMLRTCapsuleClass * cd, UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat )
: UMLRTCapsule( NULL, cd, st, border, internal, isStat )
, control( internalPorts[internalport_control] )
, frame( internalPorts[internalport_frame] )
, timing( internalPorts[internalport_timing] )
, capsule1( &slot->parts[part_capsule1] )
, currentState( SPECIAL_INTERNAL_STATE_UNVISITED )
{
    stateNames[Finish] = "Finish";
    stateNames[PartReady] = "PartReady";
    stateNames[Stopping] = "Stopping";
    stateNames[WaitingForPartToBeReady] = "WaitingForPartToBeReady";
    stateNames[SPECIAL_INTERNAL_STATE_TOP] = "<top>";
    stateNames[SPECIAL_INTERNAL_STATE_UNVISITED] = "<uninitialized>";
}









void Capsule_Top::bindPort( bool isBorder, int portId, int index )
{
    if( isBorder )
        switch( portId )
        {
        }
    else
        UMLRTFrameService::sendBoundUnbound( internalPorts, internalport_control, index, true );
}

void Capsule_Top::unbindPort( bool isBorder, int portId, int index )
{
    if( isBorder )
        switch( portId )
        {
        }
    else
    {
        UMLRTFrameService::sendBoundUnbound( internalPorts, internalport_control, index, false );
        UMLRTFrameService::disconnectPort( internalPorts[internalport_control], index );
    }
}


void Capsule_Top::inject( const UMLRTMessage & message )
{
    msg = &message;
    switch( currentState )
    {
    case WaitingForPartToBeReady:
        currentState = state_____WaitingForPartToBeReady( &message );
        break;
    case PartReady:
        currentState = state_____PartReady( &message );
        break;
    case Stopping:
        currentState = state_____Stopping( &message );
        break;
    case Finish:
        currentState = state_____Finish( &message );
        break;
    default:
        break;
    }
}

void Capsule_Top::initialize( const UMLRTMessage & message )
{
    msg = &message;
    actionchain_____Initial( &message );
    currentState = WaitingForPartToBeReady;
}

const char * Capsule_Top::getCurrentStateString() const
{
    return stateNames[currentState];
}




void Capsule_Top::update_state( Capsule_Top::State newState )
{
    currentState = newState;
}

void Capsule_Top::entryaction_____Finish( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/ServicePorts/ServicePorts.uml ServicePorts::Top::Finish entry  */
    log.log(TLFCAPINST_STATE, "received done message on control port finish");
    exit(0);
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_Top::entryaction_____PartReady( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/ServicePorts/ServicePorts.uml ServicePorts::Top::PartReady entry  */
    log.log(TLFCAPINST_STATE, "received ready message on control port; sending start message through control port");
    control.start().send();
    long delay = TimingUtil::getDelayFromCmdLine(0, "Top", "PartReady");
    timerId = timing.informIn(UMLRTTimespec(delay,0));
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_Top::entryaction_____Stopping( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/ServicePorts/ServicePorts.uml ServicePorts::Top::Stopping entry  */
    log.log(TLFCAPINST_STATE, "timed-out; sending stop message through control port");
    control.stop().send();
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_Top::entryaction_____WaitingForPartToBeReady( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/ServicePorts/ServicePorts.uml ServicePorts::Top::WaitingForPartToBeReady entry  */
    log.log(TLFCAPINST_STATE, "incarnating capsule1");
    UMLRTCapsuleId id = frame.incarnate(capsule1);
    if (id.isValid()) {
    log.log(TLFCAPINST_STATE, "incarnating capsule1 succeeded");
    } else {
    log.log(TLFCAPINST_STATE, "incarnating capsule1 failed");
    exit(0);
    }
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_Top::actionchain_____Initial( const UMLRTMessage * msg )
{
    update_state( WaitingForPartToBeReady );
    entryaction_____WaitingForPartToBeReady( msg );
}

void Capsule_Top::actionchain_____transition1( const UMLRTMessage * msg )
{
    update_state( SPECIAL_INTERNAL_STATE_TOP );
    update_state( PartReady );
    entryaction_____PartReady( msg );
}

void Capsule_Top::actionchain_____transition2( const UMLRTMessage * msg )
{
    update_state( SPECIAL_INTERNAL_STATE_TOP );
    update_state( Finish );
    entryaction_____Finish( msg );
}

void Capsule_Top::actionchain_____transition3( const UMLRTMessage * msg )
{
    update_state( SPECIAL_INTERNAL_STATE_TOP );
    update_state( Stopping );
    entryaction_____Stopping( msg );
}

Capsule_Top::State Capsule_Top::state_____Finish( const UMLRTMessage * msg )
{
    switch( msg->destPort->role()->id )
    {
    default:
        this->unexpectedMessage();
        break;
    }
    return currentState;
}

Capsule_Top::State Capsule_Top::state_____PartReady( const UMLRTMessage * msg )
{
    switch( msg->destPort->role()->id )
    {
    case port_timing:
        switch( msg->getSignalId() )
        {
        case UMLRTTimerProtocol::signal_timeout:
            actionchain_____transition3( msg );
            return Stopping;
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

Capsule_Top::State Capsule_Top::state_____Stopping( const UMLRTMessage * msg )
{
    switch( msg->destPort->role()->id )
    {
    case port_control:
        switch( msg->getSignalId() )
        {
        case Control::signal_done:
            actionchain_____transition2( msg );
            return Finish;
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

Capsule_Top::State Capsule_Top::state_____WaitingForPartToBeReady( const UMLRTMessage * msg )
{
    switch( msg->destPort->role()->id )
    {
    case port_control:
        switch( msg->getSignalId() )
        {
        case Control::signal_ready:
            actionchain_____transition1( msg );
            return PartReady;
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


static const UMLRTCapsuleRole roles[] = 
{
    {
        "capsule1",
        &Capsule1,
        0,
        1,
        true,
        false
    }
};

static const UMLRTCommsPortRole portroles_internal[] = 
{
    {
        Capsule_Top::port_control,
        "Control",
        "control",
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
        Capsule_Top::port_frame,
        "Frame",
        "frame",
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
        Capsule_Top::port_timing,
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
        Capsule_Top::port_log,
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

static void instantiate_Top( const UMLRTRtsInterface * rts, UMLRTSlot * slot, const UMLRTCommsPort * * borderPorts )
{
    const UMLRTCommsPort * * internalPorts = UMLRTFrameService::createInternalPorts( slot, &Top );
    UMLRTFrameService::connectPorts( internalPorts[Capsule_Top::internalport_control], 0, &slot->parts[Capsule_Top::part_capsule1].slots[0]->ports[Capsule_Capsule1::borderport_control], 0 );
    slot->capsule = new Capsule_Top( &Top, slot, borderPorts, internalPorts, false );
}

const UMLRTCapsuleClass Top = 
{
    "Top",
    NULL,
    instantiate_Top,
    1,
    roles,
    0,
    NULL,
    4,
    portroles_internal
};

