
#include "Receiver.hh"

#include "Protocol1.hh"
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

int Capsule_Receiver::messagesReceived = 0;
Capsule_Receiver::Capsule_Receiver( const UMLRTCapsuleClass * cd, UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat )
: UMLRTCapsule( NULL, cd, st, border, internal, isStat )
, inp( borderPorts[borderport_inp] )
, currentState( SPECIAL_INTERNAL_STATE_UNVISITED )
{
    stateNames[State1] = "State1";
    stateNames[State2] = "State2";
    stateNames[SPECIAL_INTERNAL_STATE_TOP] = "<top>";
    stateNames[SPECIAL_INTERNAL_STATE_UNVISITED] = "<uninitialized>";
}







void Capsule_Receiver::bindPort( bool isBorder, int portId, int index )
{
    if( isBorder )
        switch( portId )
        {
        case borderport_inp:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_inp, index, true );
            break;
        }
}

void Capsule_Receiver::unbindPort( bool isBorder, int portId, int index )
{
    if( isBorder )
        switch( portId )
        {
        case borderport_inp:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_inp, index, false );
            UMLRTFrameService::disconnectPort( borderPorts[borderport_inp], index );
            break;
        }
}


void Capsule_Receiver::inject( const UMLRTMessage & message )
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

void Capsule_Receiver::initialize( const UMLRTMessage & message )
{
    msg = &message;
    actionchain_____t0( &message );
    currentState = State1;
}

const char * Capsule_Receiver::getCurrentStateString() const
{
    return stateNames[currentState];
}




void Capsule_Receiver::update_state( Capsule_Receiver::State newState )
{
    currentState = newState;
}

void Capsule_Receiver::entryaction_____State1( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/RelayPortOptionalMixedPassThrough/RelayPortOptionalMixedPassThrough.uml RootElement::Receiver::State1 entry  */
    log.log(LFCAPINST_STATE, "receiver waiting for message");
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_Receiver::entryaction_____State2( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/RelayPortOptionalMixedPassThrough/RelayPortOptionalMixedPassThrough.uml RootElement::Receiver::State2 entry  */
    log.log(LFCAPINST_STATE, "receiver received message");
    if (messagesReceived >= 2) exit(0);
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_Receiver::transitionaction_____t1( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/RelayPortOptionalMixedPassThrough/RelayPortOptionalMixedPassThrough.uml RootElement::Receiver transition State1,State2,msg1:inp */
    messagesReceived++;
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_Receiver::actionchain_____t0( const UMLRTMessage * msg )
{
    update_state( State1 );
    entryaction_____State1( msg );
}

void Capsule_Receiver::actionchain_____t1( const UMLRTMessage * msg )
{
    update_state( SPECIAL_INTERNAL_STATE_TOP );
    transitionaction_____t1( msg );
    update_state( State2 );
    entryaction_____State2( msg );
}

Capsule_Receiver::State Capsule_Receiver::state_____State1( const UMLRTMessage * msg )
{
    switch( msg->destPort->role()->id )
    {
    case port_inp:
        switch( msg->getSignalId() )
        {
        case Protocol1::signal_msg1:
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

Capsule_Receiver::State Capsule_Receiver::state_____State2( const UMLRTMessage * msg )
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
        Capsule_Receiver::port_inp,
        "Protocol1",
        "inp",
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
        Capsule_Receiver::port_log,
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

static void instantiate_Receiver( const UMLRTRtsInterface * rts, UMLRTSlot * slot, const UMLRTCommsPort * * borderPorts )
{
    const UMLRTCommsPort * * internalPorts = UMLRTFrameService::createInternalPorts( slot, &Receiver );
    slot->capsule = new Capsule_Receiver( &Receiver, slot, borderPorts, internalPorts, false );
}

const UMLRTCapsuleClass Receiver = 
{
    "Receiver",
    NULL,
    instantiate_Receiver,
    0,
    NULL,
    1,
    portroles_border,
    1,
    portroles_internal
};

