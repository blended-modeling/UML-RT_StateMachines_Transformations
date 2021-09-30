
#include "A.hh"

#include "umlrtcommsportrole.hh"
#include "umlrtmessage.hh"
#include "umlrtslot.hh"
#include <cstddef>
#include "umlrtcapsuleclass.hh"
#include "umlrtframeservice.hh"
class UMLRTRtsInterface;
struct UMLRTCommsPort;

Capsule_A::Capsule_A( const UMLRTCapsuleClass * cd, UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat )
: UMLRTCapsule( NULL, cd, st, border, internal, isStat )
, control( borderPorts[borderport_control] )
, p1( borderPorts[borderport_p1] )
, p2( borderPorts[borderport_p2] )
, currentState( SPECIAL_INTERNAL_STATE_UNVISITED )
{
    stateNames[State1] = "State1";
    stateNames[SPECIAL_INTERNAL_STATE_TOP] = "<top>";
    stateNames[SPECIAL_INTERNAL_STATE_UNVISITED] = "<uninitialized>";
}









void Capsule_A::bindPort( bool isBorder, int portId, int index )
{
    if( isBorder )
        switch( portId )
        {
        case borderport_control:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_control, index, true );
            break;
        case borderport_p1:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_p1, index, true );
            break;
        case borderport_p2:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_p2, index, true );
            break;
        }
}

void Capsule_A::unbindPort( bool isBorder, int portId, int index )
{
    if( isBorder )
        switch( portId )
        {
        case borderport_control:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_control, index, false );
            UMLRTFrameService::disconnectPort( borderPorts[borderport_control], index );
            break;
        case borderport_p1:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_p1, index, false );
            UMLRTFrameService::disconnectPort( borderPorts[borderport_p1], index );
            break;
        case borderport_p2:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_p2, index, false );
            UMLRTFrameService::disconnectPort( borderPorts[borderport_p2], index );
            break;
        }
}

void Capsule_A::inject( const UMLRTMessage & message )
{
    msg = &message;
    switch( currentState )
    {
    case State1:
        currentState = state_____State1( &message );
        break;
    default:
        break;
    }
}

void Capsule_A::initialize( const UMLRTMessage & message )
{
    msg = &message;
    actionchain_____Initial( &message );
    currentState = State1;
}

const char * Capsule_A::getCurrentStateString() const
{
    return stateNames[currentState];
}




void Capsule_A::update_state( Capsule_A::State newState )
{
    currentState = newState;
}

void Capsule_A::entryaction_____State1( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/IncarnateSubclass/IncarnateSubclass.uml IncarnateSubclass::A::State1 entry  */
    log.log("[A](s:State1) A instance ready: '%s:%s'", this->name(),this->getTypeName());
    control.done().send();
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_A::actionchain_____Initial( const UMLRTMessage * msg )
{
    update_state( State1 );
    entryaction_____State1( msg );
}

Capsule_A::State Capsule_A::state_____State1( const UMLRTMessage * msg )
{
    switch( msg->destPort->role()->id )
    {
    default:
        this->unexpectedMessage();
        break;
    }
    return currentState;
}


static const UMLRTCommsPortRole portroles_border[] = 
{
    {
        Capsule_A::port_control,
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
        Capsule_A::port_p1,
        "P1",
        "p1",
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
        Capsule_A::port_p2,
        "P2",
        "p2",
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

static const UMLRTCommsPortRole portroles_internal[] = 
{
    {
        Capsule_A::port_log,
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

static void instantiate_A( const UMLRTRtsInterface * rts, UMLRTSlot * slot, const UMLRTCommsPort * * borderPorts )
{
    const UMLRTCommsPort * * internalPorts = UMLRTFrameService::createInternalPorts( slot, &A );
    slot->capsule = new Capsule_A( &A, slot, borderPorts, internalPorts, false );
}

const UMLRTCapsuleClass A = 
{
    "A",
    NULL,
    instantiate_A,
    0,
    NULL,
    3,
    portroles_border,
    1,
    portroles_internal
};

