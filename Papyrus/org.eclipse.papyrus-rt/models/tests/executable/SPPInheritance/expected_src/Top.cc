
#include "Top.hh"

#include "Client.hh"
#include "Server.hh"
#include "umlrtcapsuleclass.hh"
#include "umlrtcapsulepart.hh"
#include "umlrtcommsportrole.hh"
#include "umlrtmessage.hh"
#include "umlrtslot.hh"
#include "umlrttimerprotocol.hh"
#include <cstddef>
#include "umlrtcapsulerole.hh"
#include "umlrtframeservice.hh"
class UMLRTRtsInterface;
struct UMLRTCommsPort;

Capsule_Top::Capsule_Top( const UMLRTCapsuleClass * cd, UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat )
: UMLRTCapsule( NULL, cd, st, border, internal, isStat )
, currentState( SPECIAL_INTERNAL_STATE_UNVISITED )
, frame( internalPorts[internalport_frame] )
, timing( internalPorts[internalport_timing] )
, client( &slot->parts[part_client] )
, server( &slot->parts[part_server] )
, DEFAULT_DELAY( 1 )
{
    stateNames[Starting] = "Starting";
    stateNames[Working] = "Working";
    stateNames[SPECIAL_INTERNAL_STATE_TOP] = "<top>";
    stateNames[SPECIAL_INTERNAL_STATE_UNVISITED] = "<uninitialized>";
}


void Capsule_Top::inject( const UMLRTMessage & message )
{
    msg = &message;
    switch( currentState )
    {
    case Working:
        currentState = state_____Working( &message );
        break;
    case Starting:
        currentState = state_____Starting( &message );
        break;
    default:
        break;
    }
}

void Capsule_Top::initialize( const UMLRTMessage & message )
{
    msg = &message;
    actionchain_____Initial( &message );
    currentState = Starting;
}

const char * Capsule_Top::getCurrentStateString() const
{
    return stateNames[currentState];
}




void Capsule_Top::update_state( Capsule_Top::State newState )
{
    currentState = newState;
}

void Capsule_Top::entryaction_____Starting( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/SPPInheritance/SPPInheritance.uml SPPInheritance::Top::Starting entry  */
    log.log(TLFCAPINST_STATE, "setting timer");
    long delay = TimingUtil::getDelayFromCmdLine(0, "Top", "Starting");
    timing.informIn(UMLRTTimespec(delay,0));
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_Top::entryaction_____Working( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/SPPInheritance/SPPInheritance.uml SPPInheritance::Top::Working entry  */
    log.log(TLFCAPINST_STATE, "incarnating Server");
    UMLRTCapsuleId id = frame.incarnate(server, Server1);
    if (id.isValid())
    {
    log.log(TLFCAPINST_STATE, "Server incarnated successfully");
    }
    else
    {
    log.log(TLFCAPINST_STATE, "failed to incarnate Server");
    exit(1);
    }
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_Top::actionchain_____Initial( const UMLRTMessage * msg )
{
    update_state( Starting );
    entryaction_____Starting( msg );
}

void Capsule_Top::actionchain_____transition1( const UMLRTMessage * msg )
{
    update_state( SPECIAL_INTERNAL_STATE_TOP );
    update_state( Working );
    entryaction_____Working( msg );
}

Capsule_Top::State Capsule_Top::state_____Starting( const UMLRTMessage * msg )
{
    switch( msg->destPort->role()->id )
    {
    case port_timing:
        switch( msg->getSignalId() )
        {
        case UMLRTTimerProtocol::signal_timeout:
            actionchain_____transition1( msg );
            return Working;
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

Capsule_Top::State Capsule_Top::state_____Working( const UMLRTMessage * msg )
{
    switch( msg->destPort->role()->id )
    {
    default:
        this->unexpectedMessage();
        break;
    }
    return currentState;
}








void Capsule_Top::bindPort( bool isBorder, int portId, int index )
{
}

void Capsule_Top::unbindPort( bool isBorder, int portId, int index )
{
}



static const UMLRTCapsuleRole roles[] = 
{
    {
        "client",
        &Client,
        1,
        1,
        false,
        false
    },
    {
        "server",
        &Server,
        0,
        1,
        true,
        false
    }
};

static const UMLRTCommsPortRole portroles_internal[] = 
{
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
    Client.instantiate( NULL, slot->parts[Capsule_Top::part_client].slots[0], UMLRTFrameService::createBorderPorts( slot->parts[Capsule_Top::part_client].slots[0], Client.numPortRolesBorder ) );
    slot->capsule = new Capsule_Top( &Top, slot, borderPorts, internalPorts, false );
}

const UMLRTCapsuleClass Top = 
{
    "Top",
    NULL,
    instantiate_Top,
    2,
    roles,
    0,
    NULL,
    3,
    portroles_internal
};

