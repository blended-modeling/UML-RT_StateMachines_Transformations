
#include "C.hh"

#include "Q.hh"
#include "umlrtcommsportrole.hh"
#include "umlrtmessage.hh"
#include "umlrtslot.hh"
#include <cstddef>
#include "umlrtcapsuleclass.hh"
#include "umlrtframeservice.hh"
class UMLRTRtsInterface;
struct UMLRTCommsPort;

Capsule_C::Capsule_C( const UMLRTCapsuleClass * cd, UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat )
: UMLRTCapsule( NULL, cd, st, border, internal, isStat )
, q( borderPorts[borderport_q] )
, currentState( SPECIAL_INTERNAL_STATE_UNVISITED )
{
    stateNames[State1] = "State1";
    stateNames[State2] = "State2";
    stateNames[SPECIAL_INTERNAL_STATE_TOP] = "<top>";
    stateNames[SPECIAL_INTERNAL_STATE_UNVISITED] = "<uninitialized>";
}







void Capsule_C::bindPort( bool isBorder, int portId, int index )
{
    if( isBorder )
        switch( portId )
        {
        case borderport_q:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_q, index, true );
            break;
        }
}

void Capsule_C::unbindPort( bool isBorder, int portId, int index )
{
    if( isBorder )
        switch( portId )
        {
        case borderport_q:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_q, index, false );
            UMLRTFrameService::disconnectPort( borderPorts[borderport_q], index );
            break;
        }
}

void Capsule_C::inject( const UMLRTMessage & message )
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

void Capsule_C::initialize( const UMLRTMessage & message )
{
    msg = &message;
    actionchain_____Initial( &message );
    currentState = State1;
}

const char * Capsule_C::getCurrentStateString() const
{
    return stateNames[currentState];
}




void Capsule_C::update_state( Capsule_C::State newState )
{
    currentState = newState;
}

void Capsule_C::entryaction_____State1( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/IncarnateCapsuleWithSubstructure/IncarnateCapsuleWithSubstructure.uml IncarnateCapsuleWithSubstructure::C::State1 entry  */
    log.log(LFCAPINST_STATE, "entry");
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_C::entryaction_____State2( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/IncarnateCapsuleWithSubstructure/IncarnateCapsuleWithSubstructure.uml IncarnateCapsuleWithSubstructure::C::State2 entry  */
    log.log(LFCAPINST_STATE, "entry");
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_C::transitionaction_____transition1( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/IncarnateCapsuleWithSubstructure/IncarnateCapsuleWithSubstructure.uml IncarnateCapsuleWithSubstructure::C transition State1,State2,n:q */
    log.log(LFCAPINST_STATE, "n received at q");
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_C::actionchain_____Initial( const UMLRTMessage * msg )
{
    update_state( State1 );
    entryaction_____State1( msg );
}

void Capsule_C::actionchain_____transition1( const UMLRTMessage * msg )
{
    update_state( SPECIAL_INTERNAL_STATE_TOP );
    transitionaction_____transition1( msg );
    update_state( State2 );
    entryaction_____State2( msg );
}

Capsule_C::State Capsule_C::state_____State1( const UMLRTMessage * msg )
{
    switch( msg->destPort->role()->id )
    {
    case port_q:
        switch( msg->getSignalId() )
        {
        case Q::signal_n:
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

Capsule_C::State Capsule_C::state_____State2( const UMLRTMessage * msg )
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
        Capsule_C::port_q,
        "Q",
        "q",
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
        Capsule_C::port_log,
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

static void instantiate_C( const UMLRTRtsInterface * rts, UMLRTSlot * slot, const UMLRTCommsPort * * borderPorts )
{
    const UMLRTCommsPort * * internalPorts = UMLRTFrameService::createInternalPorts( slot, &C );
    slot->capsule = new Capsule_C( &C, slot, borderPorts, internalPorts, false );
}

const UMLRTCapsuleClass C = 
{
    "C",
    NULL,
    instantiate_C,
    0,
    NULL,
    1,
    portroles_border,
    1,
    portroles_internal
};

