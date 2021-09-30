
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

Capsule_Receiver::Capsule_Receiver( const UMLRTCapsuleClass * cd, UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat )
: UMLRTCapsule( NULL, cd, st, border, internal, isStat )
, inp( borderPorts[borderport_inp] )
, currentState( SPECIAL_INTERNAL_STATE_UNVISITED )
{
    stateNames[Listening] = "Listening";
    stateNames[Received] = "Received";
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
    case Listening:
        currentState = state_____Listening( &message );
        break;
    case Received:
        currentState = state_____Received( &message );
        break;
    default:
        break;
    }
}

void Capsule_Receiver::initialize( const UMLRTMessage & message )
{
    msg = &message;
    actionchain_____t0( &message );
    currentState = Listening;
}

const char * Capsule_Receiver::getCurrentStateString() const
{
    return stateNames[currentState];
}




void Capsule_Receiver::update_state( Capsule_Receiver::State newState )
{
    currentState = newState;
}

void Capsule_Receiver::entryaction_____Listening( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/RelayPortPassThrough/RelayPortPassThrough.uml RootElement::Receiver::Listening entry  */
    cout << "[Receiver.Listening] waiting for message" << endl;
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_Receiver::entryaction_____Received( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/RelayPortPassThrough/RelayPortPassThrough.uml RootElement::Receiver::Received entry  */
    cout << "[Receiver.Received] message arrived" << endl;
    exit(0);
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_Receiver::actionchain_____t0( const UMLRTMessage * msg )
{
    update_state( Listening );
    entryaction_____Listening( msg );
}

void Capsule_Receiver::actionchain_____t1( const UMLRTMessage * msg )
{
    update_state( SPECIAL_INTERNAL_STATE_TOP );
    update_state( Received );
    entryaction_____Received( msg );
}

Capsule_Receiver::State Capsule_Receiver::state_____Listening( const UMLRTMessage * msg )
{
    switch( msg->destPort->role()->id )
    {
    case port_inp:
        switch( msg->getSignalId() )
        {
        case Protocol1::signal_msg1:
            actionchain_____t1( msg );
            return Received;
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

Capsule_Receiver::State Capsule_Receiver::state_____Received( const UMLRTMessage * msg )
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

static void instantiate_Receiver( const UMLRTRtsInterface * rts, UMLRTSlot * slot, const UMLRTCommsPort * * borderPorts )
{
    slot->capsule = new Capsule_Receiver( &Receiver, slot, borderPorts, NULL, false );
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
    0,
    NULL
};

