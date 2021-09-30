
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
, currentState( SPECIAL_INTERNAL_STATE_UNVISITED )
, p( borderPorts[borderport_p] )
{
    stateNames[State1] = "State1";
    stateNames[SPECIAL_INTERNAL_STATE_TOP] = "<top>";
    stateNames[SPECIAL_INTERNAL_STATE_UNVISITED] = "<uninitialized>";
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
    actionchain_____transition0( &message );
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

void Capsule_A::transitionaction_____transition0( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/CurrentStateUpdate1/CurrentStateUpdate1.uml CurrentStateUpdate::A transition subvertex0,State1 */
    p.m().send();
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_A::actionchain_____transition0( const UMLRTMessage * msg )
{
    transitionaction_____transition0( msg );
    update_state( State1 );
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





void Capsule_A::bindPort( bool isBorder, int portId, int index )
{
    if( isBorder )
        switch( portId )
        {
        case borderport_p:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_p, index, true );
            break;
        }
}

void Capsule_A::unbindPort( bool isBorder, int portId, int index )
{
    if( isBorder )
        switch( portId )
        {
        case borderport_p:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_p, index, false );
            UMLRTFrameService::disconnectPort( borderPorts[borderport_p], index );
            break;
        }
}


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
    }
};

static void instantiate_A( const UMLRTRtsInterface * rts, UMLRTSlot * slot, const UMLRTCommsPort * * borderPorts )
{
    slot->capsule = new Capsule_A( &A, slot, borderPorts, NULL, false );
}

const UMLRTCapsuleClass A = 
{
    "A",
    NULL,
    instantiate_A,
    0,
    NULL,
    1,
    portroles_border,
    0,
    NULL
};

