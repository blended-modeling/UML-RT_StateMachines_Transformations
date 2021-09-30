
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
, r_inp( borderPorts[borderport_r_inp] )
, currentState( SPECIAL_INTERNAL_STATE_UNVISITED )
{
    stateNames[Listening] = "Listening";
    stateNames[MessageReceived] = "MessageReceived";
    stateNames[SPECIAL_INTERNAL_STATE_TOP] = "<top>";
    stateNames[SPECIAL_INTERNAL_STATE_UNVISITED] = "<uninitialized>";
}





void Capsule_Receiver::bindPort( bool isBorder, int portId, int index )
{
    if( isBorder )
        switch( portId )
        {
        case borderport_r_inp:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_r_inp, index, true );
            break;
        }
}

void Capsule_Receiver::unbindPort( bool isBorder, int portId, int index )
{
    if( isBorder )
        switch( portId )
        {
        case borderport_r_inp:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_r_inp, index, false );
            UMLRTFrameService::disconnectPort( borderPorts[borderport_r_inp], index );
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
    case MessageReceived:
        currentState = state_____MessageReceived( &message );
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
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/RelayPortOptionalPassThrough/RelayPortOptionalPassThrough.uml RootElement::Receiver::Listening entry  */
    cout << "[Receiver](Listening)" << endl;
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_Receiver::entryaction_____MessageReceived( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/RelayPortOptionalPassThrough/RelayPortOptionalPassThrough.uml RootElement::Receiver::MessageReceived entry  */
    cout << "[Receiver](MessageReceived)" << endl;
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
    update_state( MessageReceived );
    entryaction_____MessageReceived( msg );
}

Capsule_Receiver::State Capsule_Receiver::state_____Listening( const UMLRTMessage * msg )
{
    switch( msg->destPort->role()->id )
    {
    case port_r_inp:
        switch( msg->getSignalId() )
        {
        case Protocol1::signal_msg1:
            actionchain_____t1( msg );
            return MessageReceived;
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

Capsule_Receiver::State Capsule_Receiver::state_____MessageReceived( const UMLRTMessage * msg )
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
        Capsule_Receiver::port_r_inp,
        "Protocol1",
        "r_inp",
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

