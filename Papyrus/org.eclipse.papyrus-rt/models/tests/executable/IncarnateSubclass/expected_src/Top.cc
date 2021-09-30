
#include "Top.hh"

#include "A.hh"
#include "C.hh"
#include "Control.hh"
#include "umlrtcapsulepart.hh"
#include "umlrtcommsportrole.hh"
#include "umlrtmessage.hh"
#include "umlrtslot.hh"
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
, a( &slot->parts[part_a] )
, c( &slot->parts[part_c] )
, currentState( SPECIAL_INTERNAL_STATE_UNVISITED )
{
    stateNames[State1] = "State1";
    stateNames[State2] = "State2";
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
    case State1:
        currentState = state_____State1( &message );
        break;
    case State2:
        currentState = state_____State2( &message );
        break;
    default:
        break;
    }
}

void Capsule_Top::initialize( const UMLRTMessage & message )
{
    msg = &message;
    actionchain_____Initial( &message );
    currentState = State1;
}

const char * Capsule_Top::getCurrentStateString() const
{
    return stateNames[currentState];
}




void Capsule_Top::update_state( Capsule_Top::State newState )
{
    currentState = newState;
}

void Capsule_Top::entryaction_____State1( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/IncarnateSubclass/IncarnateSubclass.uml IncarnateSubclass::Top::State1 entry  */
    log.log("[Top](s:State1) incarnating part 'a'");
    UMLRTCapsuleId id = frame.incarnate(a, B);
    if (id.isValid())
    {
    log.log("[Top](s:State1) incarnation successful: '%s:%s'", id.getCapsule()->name(),id.getCapsule()->getTypeName());
    }
    else
    {
    log.log("[Top](s:State1) incarnation failed: '%s:%s'", id.getCapsule()->name(),id.getCapsule()->getTypeName());
    }
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_Top::entryaction_____State2( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/IncarnateSubclass/IncarnateSubclass.uml IncarnateSubclass::Top::State2 entry  */
    log.log("[Top](s:State2) done");
    exit(0);
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_Top::actionchain_____Initial( const UMLRTMessage * msg )
{
    update_state( State1 );
    entryaction_____State1( msg );
}

void Capsule_Top::actionchain_____transition1( const UMLRTMessage * msg )
{
    update_state( SPECIAL_INTERNAL_STATE_TOP );
    update_state( State2 );
    entryaction_____State2( msg );
}

Capsule_Top::State Capsule_Top::state_____State1( const UMLRTMessage * msg )
{
    switch( msg->destPort->role()->id )
    {
    case port_control:
        switch( msg->getSignalId() )
        {
        case Control::signal_done:
            actionchain_____transition1( msg );
            return State2;
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

Capsule_Top::State Capsule_Top::state_____State2( const UMLRTMessage * msg )
{
    switch( msg->destPort->role()->id )
    {
    default:
        this->unexpectedMessage();
        break;
    }
    return currentState;
}


static const UMLRTCapsuleRole roles[] = 
{
    {
        "a",
        &A,
        0,
        1,
        true,
        false
    },
    {
        "c",
        &C,
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
        2,
        true,
        true,
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
    UMLRTFrameService::connectPorts( internalPorts[Capsule_Top::internalport_control], 0, &slot->parts[Capsule_Top::part_a].slots[0]->ports[Capsule_A::borderport_control], 0 );
    UMLRTFrameService::connectPorts( internalPorts[Capsule_Top::internalport_control], 1, &slot->parts[Capsule_Top::part_c].slots[0]->ports[Capsule_C::borderport_control], 0 );
    UMLRTFrameService::connectPorts( &slot->parts[Capsule_Top::part_a].slots[0]->ports[Capsule_A::borderport_p1], 0, &slot->parts[Capsule_Top::part_c].slots[0]->ports[Capsule_C::borderport_p1], 0 );
    UMLRTFrameService::connectPorts( &slot->parts[Capsule_Top::part_a].slots[0]->ports[Capsule_A::borderport_p2], 0, &slot->parts[Capsule_Top::part_c].slots[0]->ports[Capsule_C::borderport_p2], 0 );
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

