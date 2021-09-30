
#include "B.hh"

#include "A.hh"
#include "P.hh"
#include "umlrtcommsportrole.hh"
#include "umlrtmessage.hh"
#include "umlrtslot.hh"
#include <cstddef>
#include "umlrtcapsuleclass.hh"
#include "umlrtframeservice.hh"
class UMLRTRtsInterface;
struct UMLRTCommsPort;

Capsule_B::Capsule_B( const UMLRTCapsuleClass * cd, UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat )
: Capsule_A( cd, st, border, internal, isStat )
, p( borderPorts[borderport_p] )
, q( borderPorts[borderport_q] )
, currentState( SPECIAL_INTERNAL_STATE_UNVISITED )
{
    stateNames[State1] = "State1";
    stateNames[State2] = "State2";
    stateNames[SPECIAL_INTERNAL_STATE_TOP] = "<top>";
    stateNames[SPECIAL_INTERNAL_STATE_UNVISITED] = "<uninitialized>";
}








void Capsule_B::bindPort( bool isBorder, int portId, int index )
{
    if( isBorder )
        switch( portId )
        {
        case borderport_p:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_p, index, true );
            break;
        case borderport_q:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_q, index, true );
            break;
        }
}

void Capsule_B::unbindPort( bool isBorder, int portId, int index )
{
    if( isBorder )
        switch( portId )
        {
        case borderport_p:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_p, index, false );
            UMLRTFrameService::disconnectPort( borderPorts[borderport_p], index );
            break;
        case borderport_q:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_q, index, false );
            UMLRTFrameService::disconnectPort( borderPorts[borderport_q], index );
            break;
        }
}

void Capsule_B::inject( const UMLRTMessage & message )
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

void Capsule_B::initialize( const UMLRTMessage & message )
{
    msg = &message;
    actionchain_____Initial( &message );
    currentState = State1;
}

const char * Capsule_B::getCurrentStateString() const
{
    return stateNames[currentState];
}




void Capsule_B::update_state( Capsule_B::State newState )
{
    currentState = newState;
}

void Capsule_B::entryaction_____State1( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/PortInheritance/PortInheritance.uml PortInheritance::B::State1 entry  */
    log.log(LFCAPINST_STATE, "entry");
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_B::entryaction_____State2( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/PortInheritance/PortInheritance.uml PortInheritance::B::State2 entry  */
    log.log(LFCAPINST_STATE, "entry");
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_B::transitionaction_____implicitTransition1( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/PortInheritance/PortInheritance.uml PortInheritance::B transition State1,State2,m:p */
    log.log(LFCAPINST_STATE, "transition m");
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_B::actionchain_____Initial( const UMLRTMessage * msg )
{
    update_state( State1 );
    entryaction_____State1( msg );
}

void Capsule_B::actionchain_____implicitTransition1( const UMLRTMessage * msg )
{
    update_state( SPECIAL_INTERNAL_STATE_TOP );
    transitionaction_____implicitTransition1( msg );
    update_state( State2 );
    entryaction_____State2( msg );
}

Capsule_B::State Capsule_B::state_____State1( const UMLRTMessage * msg )
{
    switch( msg->destPort->role()->id )
    {
    case port_p:
        switch( msg->getSignalId() )
        {
        case P::signal_m:
            actionchain_____implicitTransition1( msg );
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

Capsule_B::State Capsule_B::state_____State2( const UMLRTMessage * msg )
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
        Capsule_B::port_p,
        "P",
        "p",
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
        Capsule_B::port_q,
        "Q",
        "q",
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
        Capsule_B::port_log,
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

static void instantiate_B( const UMLRTRtsInterface * rts, UMLRTSlot * slot, const UMLRTCommsPort * * borderPorts )
{
    const UMLRTCommsPort * * internalPorts = UMLRTFrameService::createInternalPorts( slot, &B );
    slot->capsule = new Capsule_B( &B, slot, borderPorts, internalPorts, false );
}

const UMLRTCapsuleClass B = 
{
    "B",
    &A,
    instantiate_B,
    0,
    NULL,
    2,
    portroles_border,
    1,
    portroles_internal
};

