
#include "D.hh"

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

Capsule_D::Capsule_D( const UMLRTCapsuleClass * cd, UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat )
: UMLRTCapsule( NULL, cd, st, border, internal, isStat )
, s( borderPorts[borderport_s] )
, currentState( SPECIAL_INTERNAL_STATE_UNVISITED )
{
    stateNames[State1] = "State1";
    stateNames[SPECIAL_INTERNAL_STATE_TOP] = "<top>";
    stateNames[SPECIAL_INTERNAL_STATE_UNVISITED] = "<uninitialized>";
}





void Capsule_D::bindPort( bool isBorder, int portId, int index )
{
    if( isBorder )
        switch( portId )
        {
        case borderport_s:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_s, index, true );
            break;
        }
}

void Capsule_D::unbindPort( bool isBorder, int portId, int index )
{
    if( isBorder )
        switch( portId )
        {
        case borderport_s:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_s, index, false );
            UMLRTFrameService::disconnectPort( borderPorts[borderport_s], index );
            break;
        }
}

void Capsule_D::inject( const UMLRTMessage & message )
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

void Capsule_D::initialize( const UMLRTMessage & message )
{
    msg = &message;
    actionchain_____t0( &message );
    currentState = State1;
}

const char * Capsule_D::getCurrentStateString() const
{
    return stateNames[currentState];
}




void Capsule_D::update_state( Capsule_D::State newState )
{
    currentState = newState;
}

void Capsule_D::transitionaction_____t1( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/RelayPortFanOut/RelayPortFanOut.uml RootElement::D transition State1,State1,msg1:s */
    cout << "[D.t1] received msg1" << endl;
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_D::transitionaction_____t2( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/RelayPortFanOut/RelayPortFanOut.uml RootElement::D transition State1,State1,msg2:s */
    cout << "[D.t2] received msg2" << endl;
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_D::actionchain_____t0( const UMLRTMessage * msg )
{
    update_state( State1 );
}

void Capsule_D::actionchain_____t1( const UMLRTMessage * msg )
{
    update_state( SPECIAL_INTERNAL_STATE_TOP );
    transitionaction_____t1( msg );
    update_state( State1 );
}

void Capsule_D::actionchain_____t2( const UMLRTMessage * msg )
{
    update_state( SPECIAL_INTERNAL_STATE_TOP );
    transitionaction_____t2( msg );
    update_state( State1 );
}

Capsule_D::State Capsule_D::state_____State1( const UMLRTMessage * msg )
{
    switch( msg->destPort->role()->id )
    {
    case port_s:
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
        Capsule_D::port_s,
        "Protocol1",
        "s",
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

static void instantiate_D( const UMLRTRtsInterface * rts, UMLRTSlot * slot, const UMLRTCommsPort * * borderPorts )
{
    slot->capsule = new Capsule_D( &D, slot, borderPorts, NULL, false );
}

const UMLRTCapsuleClass D = 
{
    "D",
    NULL,
    instantiate_D,
    0,
    NULL,
    1,
    portroles_border,
    0,
    NULL
};

