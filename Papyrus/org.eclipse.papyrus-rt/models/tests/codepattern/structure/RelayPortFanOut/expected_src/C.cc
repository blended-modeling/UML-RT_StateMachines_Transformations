
#include "C.hh"

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
using namespace std;

Capsule_C::Capsule_C( const UMLRTCapsuleClass * cd, UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat )
: UMLRTCapsule( NULL, cd, st, border, internal, isStat )
, r( borderPorts[borderport_r] )
, currentState( SPECIAL_INTERNAL_STATE_UNVISITED )
{
    stateNames[State1] = "State1";
    stateNames[SPECIAL_INTERNAL_STATE_TOP] = "<top>";
    stateNames[SPECIAL_INTERNAL_STATE_UNVISITED] = "<uninitialized>";
}





void Capsule_C::bindPort( bool isBorder, int portId, int index )
{
    if( isBorder )
        switch( portId )
        {
        case borderport_r:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_r, index, true );
            break;
        }
}

void Capsule_C::unbindPort( bool isBorder, int portId, int index )
{
    if( isBorder )
        switch( portId )
        {
        case borderport_r:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_r, index, false );
            UMLRTFrameService::disconnectPort( borderPorts[borderport_r], index );
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
    default:
        break;
    }
}

void Capsule_C::initialize( const UMLRTMessage & message )
{
    msg = &message;
    actionchain_____t0( &message );
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

void Capsule_C::transitionaction_____t1( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/RelayPortFanOut/RelayPortFanOut.uml RootElement::C transition State1,State1,msg1:r */
    cout << "[C.t1] received msg1" << endl;
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_C::transitionaction_____t2( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/RelayPortFanOut/RelayPortFanOut.uml RootElement::C transition State1,State1,msg2:r */
    cout << "[C.t2] received msg2" << endl;
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_C::actionchain_____t0( const UMLRTMessage * msg )
{
    update_state( State1 );
}

void Capsule_C::actionchain_____t1( const UMLRTMessage * msg )
{
    update_state( SPECIAL_INTERNAL_STATE_TOP );
    transitionaction_____t1( msg );
    update_state( State1 );
}

void Capsule_C::actionchain_____t2( const UMLRTMessage * msg )
{
    update_state( SPECIAL_INTERNAL_STATE_TOP );
    transitionaction_____t2( msg );
    update_state( State1 );
}

Capsule_C::State Capsule_C::state_____State1( const UMLRTMessage * msg )
{
    switch( msg->destPort->role()->id )
    {
    case port_r:
        switch( msg->getSignalId() )
        {
        case Protocol1::signal_msg1:
            actionchain_____t1( msg );
            return State1;
        case Protocol1::signal_msg2:
            actionchain_____t2( msg );
            return State1;
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


static const UMLRTCommsPortRole portroles_border[] = 
{
    {
        Capsule_C::port_r,
        "Protocol1",
        "r",
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

static void instantiate_C( const UMLRTRtsInterface * rts, UMLRTSlot * slot, const UMLRTCommsPort * * borderPorts )
{
    slot->capsule = new Capsule_C( &C, slot, borderPorts, NULL, false );
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
    0,
    NULL
};

