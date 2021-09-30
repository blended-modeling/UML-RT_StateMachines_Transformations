
#include "A.hh"

#include "B.hh"
#include "C.hh"
#include "umlrtcapsuleclass.hh"
#include "umlrtcapsulepart.hh"
#include "umlrtcommsportrole.hh"
#include "umlrtmessage.hh"
#include "umlrtsignal.hh"
#include "umlrtslot.hh"
#include <cstddef>
#include "umlrtcapsulerole.hh"
#include "umlrtcommsport.hh"
#include "umlrtframeservice.hh"
class UMLRTRtsInterface;

Capsule_A::Capsule_A( const UMLRTCapsuleClass * cd, UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat )
: UMLRTCapsule( NULL, cd, st, border, internal, isStat )
, p( borderPorts[borderport_p] )
, r( borderPorts[borderport_r] )
, b( &slot->parts[part_b] )
, c( &slot->parts[part_c] )
, currentState( SPECIAL_INTERNAL_STATE_UNVISITED )
{
    stateNames[State1] = "State1";
    stateNames[State2] = "State2";
    stateNames[State3] = "State3";
    stateNames[SPECIAL_INTERNAL_STATE_TOP] = "<top>";
    stateNames[SPECIAL_INTERNAL_STATE_UNVISITED] = "<uninitialized>";
}










void Capsule_A::bindPort( bool isBorder, int portId, int index )
{
    if( isBorder )
        switch( portId )
        {
        case borderport_p:
            switch( index )
            {
            case 0:
                UMLRTFrameService::connectRelayPort( borderPorts[borderport_p], index, &slot->parts[part_b].slots[0]->ports[Capsule_B::borderport_p], 0 );
                UMLRTFrameService::bindSubcapsulePort( true, slot->parts[part_b].slots[0]->capsule, portId, index );
                break;
            }
            break;
        case borderport_r:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_r, index, true );
            break;
        }
}

void Capsule_A::unbindPort( bool isBorder, int portId, int index )
{
    if( isBorder )
        switch( portId )
        {
        case borderport_p:
            switch( index )
            {
            case 0:
                UMLRTFrameService::unbindSubcapsulePort( true, slot->parts[part_b].slots[0]->capsule, portId, index );
                break;
            }
            break;
        case borderport_r:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_r, index, false );
            UMLRTFrameService::disconnectPort( borderPorts[borderport_r], index );
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
    case State2:
        currentState = state_____State2( &message );
        break;
    case State3:
        currentState = state_____State3( &message );
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
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/IncarnateCapsuleWithSubstructure/IncarnateCapsuleWithSubstructure.uml IncarnateCapsuleWithSubstructure::A::State1 entry  */
    log.log(LFCAPINST_STATE, "entry");
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_A::entryaction_____State2( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/IncarnateCapsuleWithSubstructure/IncarnateCapsuleWithSubstructure.uml IncarnateCapsuleWithSubstructure::A::State2 entry  */
    log.log(LFCAPINST_STATE, "entry");
    context()->debugOutputModel();
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_A::entryaction_____State3( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/IncarnateCapsuleWithSubstructure/IncarnateCapsuleWithSubstructure.uml IncarnateCapsuleWithSubstructure::A::State3 entry  */
    log.log(LFCAPINST_STATE, "entry");
    context()->debugOutputModel();
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_A::transitionaction_____transition1( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/IncarnateCapsuleWithSubstructure/IncarnateCapsuleWithSubstructure.uml IncarnateCapsuleWithSubstructure::A transition State1,State2,rtBound:r */
    log.log(LFCAPINST_STATE, "rtBound received at r");
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_A::transitionaction_____transition2( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/IncarnateCapsuleWithSubstructure/IncarnateCapsuleWithSubstructure.uml IncarnateCapsuleWithSubstructure::A transition State2,State3,rtUnbound:r */
    log.log(LFCAPINST_STATE, "rtUnbound received at r");
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_A::actionchain_____Initial( const UMLRTMessage * msg )
{
    update_state( State1 );
    entryaction_____State1( msg );
}

void Capsule_A::actionchain_____transition1( const UMLRTMessage * msg )
{
    update_state( SPECIAL_INTERNAL_STATE_TOP );
    transitionaction_____transition1( msg );
    update_state( State2 );
    entryaction_____State2( msg );
}

void Capsule_A::actionchain_____transition2( const UMLRTMessage * msg )
{
    update_state( SPECIAL_INTERNAL_STATE_TOP );
    transitionaction_____transition2( msg );
    update_state( State3 );
    entryaction_____State3( msg );
}

Capsule_A::State Capsule_A::state_____State1( const UMLRTMessage * msg )
{
    switch( msg->destPort->role()->id )
    {
    case port_r:
        switch( msg->getSignalId() )
        {
        case UMLRTSignal::rtBound:
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

Capsule_A::State Capsule_A::state_____State2( const UMLRTMessage * msg )
{
    switch( msg->destPort->role()->id )
    {
    case port_r:
        switch( msg->getSignalId() )
        {
        case UMLRTSignal::rtUnbound:
            actionchain_____transition2( msg );
            return State3;
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

Capsule_A::State Capsule_A::state_____State3( const UMLRTMessage * msg )
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
        "b",
        &B,
        1,
        1,
        false,
        false
    },
    {
        "c",
        &C,
        1,
        1,
        false,
        false
    }
};

static const UMLRTCommsPortRole portroles_border[] = 
{
    {
        Capsule_A::port_p,
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
        Capsule_A::port_r,
        "R",
        "r",
        "",
        1,
        true,
        false,
        false,
        true,
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
    UMLRTFrameService::connectRelayPort( borderPorts[Capsule_A::borderport_p], 0, &slot->parts[Capsule_A::part_b].slots[0]->ports[Capsule_B::borderport_p], 0 );
    UMLRTFrameService::connectPorts( &slot->parts[Capsule_A::part_b].slots[0]->ports[Capsule_B::borderport_q], 0, &slot->parts[Capsule_A::part_c].slots[0]->ports[Capsule_C::borderport_q], 0 );
    B.instantiate( NULL, slot->parts[Capsule_A::part_b].slots[0], UMLRTFrameService::createBorderPorts( slot->parts[Capsule_A::part_b].slots[0], B.numPortRolesBorder ) );
    C.instantiate( NULL, slot->parts[Capsule_A::part_c].slots[0], UMLRTFrameService::createBorderPorts( slot->parts[Capsule_A::part_c].slots[0], C.numPortRolesBorder ) );
    slot->capsule = new Capsule_A( &A, slot, borderPorts, internalPorts, false );
}

const UMLRTCapsuleClass A = 
{
    "A",
    NULL,
    instantiate_A,
    2,
    roles,
    2,
    portroles_border,
    1,
    portroles_internal
};

