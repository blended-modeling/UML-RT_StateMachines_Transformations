
#include "Sender.hh"

#include "umlrtcommsportrole.hh"
#include "umlrtmessage.hh"
#include "umlrtslot.hh"
#include <cstddef>
#include "umlrtcapsuleclass.hh"
#include "umlrtframeservice.hh"
class UMLRTRtsInterface;
struct UMLRTCommsPort;

Capsule_Sender::Capsule_Sender( const UMLRTCapsuleClass * cd, UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat )
: UMLRTCapsule( NULL, cd, st, border, internal, isStat )
, out( borderPorts[borderport_out] )
, currentState( SPECIAL_INTERNAL_STATE_UNVISITED )
{
    stateNames[Sending] = "Sending";
    stateNames[SPECIAL_INTERNAL_STATE_TOP] = "<top>";
    stateNames[SPECIAL_INTERNAL_STATE_UNVISITED] = "<uninitialized>";
}





void Capsule_Sender::bindPort( bool isBorder, int portId, int index )
{
    if( isBorder )
        switch( portId )
        {
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
    case Sending:
        currentState = state_____Sending( &message );
        break;
    default:
        break;
    }
}

void Capsule_Sender::initialize( const UMLRTMessage & message )
{
    msg = &message;
    actionchain_____t0( &message );
    currentState = Sending;
}

const char * Capsule_Sender::getCurrentStateString() const
{
    return stateNames[currentState];
}




void Capsule_Sender::update_state( Capsule_Sender::State newState )
{
    currentState = newState;
}

void Capsule_Sender::entryaction_____Sending( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/RelayPortPassThrough/RelayPortPassThrough.uml RootElement::Sender::Sending entry  */
    cout << "[Sender.Sending] sending message" << endl;
    out.msg1().send();
    cout << "[Sender.Sending] message sent" << endl;
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_Sender::actionchain_____t0( const UMLRTMessage * msg )
{
    update_state( Sending );
    entryaction_____Sending( msg );
}

Capsule_Sender::State Capsule_Sender::state_____Sending( const UMLRTMessage * msg )
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
        Capsule_Sender::port_out,
        "Protocol1",
        "out",
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

static void instantiate_Sender( const UMLRTRtsInterface * rts, UMLRTSlot * slot, const UMLRTCommsPort * * borderPorts )
{
    slot->capsule = new Capsule_Sender( &Sender, slot, borderPorts, NULL, false );
}

const UMLRTCapsuleClass Sender = 
{
    "Sender",
    NULL,
    instantiate_Sender,
    0,
    NULL,
    1,
    portroles_border,
    0,
    NULL
};

