
#include "Capsule2.hh"

#include "umlrtcommsportrole.hh"
#include "umlrtmessage.hh"
#include "umlrtslot.hh"
#include <cstddef>
#include "umlrtcapsuleclass.hh"
#include "umlrtframeservice.hh"
class UMLRTRtsInterface;
struct UMLRTCommsPort;

Capsule_Capsule2::Capsule_Capsule2( const UMLRTCapsuleClass * cd, UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat )
: UMLRTCapsule( NULL, cd, st, border, internal, isStat )
, port1( borderPorts[borderport_port1] )
, currentState( SPECIAL_INTERNAL_STATE_UNVISITED )
{
    stateNames[State1] = "State1";
    stateNames[SPECIAL_INTERNAL_STATE_TOP] = "<top>";
    stateNames[SPECIAL_INTERNAL_STATE_UNVISITED] = "<uninitialized>";
}







void Capsule_Capsule2::bindPort( bool isBorder, int portId, int index )
{
    if( isBorder )
        switch( portId )
        {
        case borderport_port1:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_port1, index, true );
            break;
        }
}

void Capsule_Capsule2::unbindPort( bool isBorder, int portId, int index )
{
    if( isBorder )
        switch( portId )
        {
        case borderport_port1:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_port1, index, false );
            UMLRTFrameService::disconnectPort( borderPorts[borderport_port1], index );
            break;
        }
}

void Capsule_Capsule2::inject( const UMLRTMessage & message )
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

void Capsule_Capsule2::initialize( const UMLRTMessage & message )
{
    msg = &message;
    actionchain_____t0( &message );
    currentState = State1;
}

const char * Capsule_Capsule2::getCurrentStateString() const
{
    return stateNames[currentState];
}




void Capsule_Capsule2::update_state( Capsule_Capsule2::State newState )
{
    currentState = newState;
}

void Capsule_Capsule2::entryaction_____State1( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/AnyEvent1/AnyEvent1.uml AnyEvent1::Capsule2::State1 entry  */
    log.log(LFCAPINST_STATE, "entry");
    port1.msg1().send();
    log.log(LFCAPINST_STATE, "sent msg1");
    port1.step().send();
    log.log(LFCAPINST_STATE, "sent step");
    port1.msg2().send();
    log.log(LFCAPINST_STATE, "sent msg2");
    port1.step().send();
    log.log(LFCAPINST_STATE, "sent step");
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_Capsule2::actionchain_____t0( const UMLRTMessage * msg )
{
    update_state( State1 );
    entryaction_____State1( msg );
}

Capsule_Capsule2::State Capsule_Capsule2::state_____State1( const UMLRTMessage * msg )
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
        Capsule_Capsule2::port_port1,
        "Protocol1",
        "port1",
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
        Capsule_Capsule2::port_log,
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

static void instantiate_Capsule2( const UMLRTRtsInterface * rts, UMLRTSlot * slot, const UMLRTCommsPort * * borderPorts )
{
    const UMLRTCommsPort * * internalPorts = UMLRTFrameService::createInternalPorts( slot, &Capsule2 );
    slot->capsule = new Capsule_Capsule2( &Capsule2, slot, borderPorts, internalPorts, false );
}

const UMLRTCapsuleClass Capsule2 = 
{
    "Capsule2",
    NULL,
    instantiate_Capsule2,
    0,
    NULL,
    1,
    portroles_border,
    1,
    portroles_internal
};

