
#include "Sender.hh"

#include "Control.hh"
#include "umlrtcommsportrole.hh"
#include "umlrtmessage.hh"
#include "umlrtslot.hh"
#include <cstddef>
#include "umlrtcapsuleclass.hh"
#include "umlrtframeservice.hh"
class UMLRTRtsInterface;
struct UMLRTCommsPort;

#include <iostream>
#include "UtilityMacros.hh"
using namespace std;

Capsule_Sender::Capsule_Sender( const UMLRTCapsuleClass * cd, UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat )
: UMLRTCapsule( NULL, cd, st, border, internal, isStat )
, control( borderPorts[borderport_control] )
, out( borderPorts[borderport_out] )
, currentState( SPECIAL_INTERNAL_STATE_UNVISITED )
{
    stateNames[State1] = "State1";
    stateNames[State2] = "State2";
    stateNames[SPECIAL_INTERNAL_STATE_TOP] = "<top>";
    stateNames[SPECIAL_INTERNAL_STATE_UNVISITED] = "<uninitialized>";
}








void Capsule_Sender::bindPort( bool isBorder, int portId, int index )
{
    if( isBorder )
        switch( portId )
        {
        case borderport_control:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_control, index, true );
            break;
        case borderport_out:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_out, index, true );
            break;
        }
}

void Capsule_Sender::unbindPort( bool isBorder, int portId, int index )
{
    if( isBorder )
        switch( portId )
        {
        case borderport_control:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_control, index, false );
            UMLRTFrameService::disconnectPort( borderPorts[borderport_control], index );
            break;
        case borderport_out:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_out, index, false );
            UMLRTFrameService::disconnectPort( borderPorts[borderport_out], index );
            break;
        }
}

void Capsule_Sender::inject( const UMLRTMessage & message )
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

void Capsule_Sender::initialize( const UMLRTMessage & message )
{
    msg = &message;
    actionchain_____t0( &message );
    currentState = State1;
}

const char * Capsule_Sender::getCurrentStateString() const
{
    return stateNames[currentState];
}




void Capsule_Sender::update_state( Capsule_Sender::State newState )
{
    currentState = newState;
}

void Capsule_Sender::entryaction_____State1( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/RelayPortOptionalMixedPassThrough/RelayPortOptionalMixedPassThrough.uml RootElement::Sender::State1 entry  */
    log.log(LFCAPINST_STATE, "sender waiting to start");
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_Sender::entryaction_____State2( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/RelayPortOptionalMixedPassThrough/RelayPortOptionalMixedPassThrough.uml RootElement::Sender::State2 entry  */
    log.log(LFCAPINST_STATE, "sender sending message");
    out.msg1().send();
    log.log(LFCAPINST_STATE, "sender sent message");
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_Sender::transitionaction_____t1( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/RelayPortOptionalMixedPassThrough/RelayPortOptionalMixedPassThrough.uml RootElement::Sender transition State1,State2,begin:control */
    log.log(LFCAPINST_STATE, "sender received begin message");
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_Sender::actionchain_____t0( const UMLRTMessage * msg )
{
    update_state( State1 );
    entryaction_____State1( msg );
}

void Capsule_Sender::actionchain_____t1( const UMLRTMessage * msg )
{
    update_state( SPECIAL_INTERNAL_STATE_TOP );
    transitionaction_____t1( msg );
    update_state( State2 );
    entryaction_____State2( msg );
}

Capsule_Sender::State Capsule_Sender::state_____State1( const UMLRTMessage * msg )
{
    switch( msg->destPort->role()->id )
    {
    case port_control:
        switch( msg->getSignalId() )
        {
        case Control::signal_begin:
            actionchain_____t1( msg );
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

Capsule_Sender::State Capsule_Sender::state_____State2( const UMLRTMessage * msg )
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
        Capsule_Sender::port_control,
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
    },
    {
        Capsule_Sender::port_out,
        "Protocol1",
        "out",
        "",
        2,
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
        Capsule_Sender::port_log,
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

static void instantiate_Sender( const UMLRTRtsInterface * rts, UMLRTSlot * slot, const UMLRTCommsPort * * borderPorts )
{
    const UMLRTCommsPort * * internalPorts = UMLRTFrameService::createInternalPorts( slot, &Sender );
    slot->capsule = new Capsule_Sender( &Sender, slot, borderPorts, internalPorts, false );
}

const UMLRTCapsuleClass Sender = 
{
    "Sender",
    NULL,
    instantiate_Sender,
    0,
    NULL,
    2,
    portroles_border,
    1,
    portroles_internal
};

