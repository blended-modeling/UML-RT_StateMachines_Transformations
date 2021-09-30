
#include "Sender.hh"

#include "Start.hh"
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
, s_out( borderPorts[borderport_s_out] )
, start( borderPorts[borderport_start] )
, currentState( SPECIAL_INTERNAL_STATE_UNVISITED )
{
    stateNames[MessageSent] = "MessageSent";
    stateNames[WaitingToStart] = "WaitingToStart";
    stateNames[SPECIAL_INTERNAL_STATE_TOP] = "<top>";
    stateNames[SPECIAL_INTERNAL_STATE_UNVISITED] = "<uninitialized>";
}






void Capsule_Sender::bindPort( bool isBorder, int portId, int index )
{
    if( isBorder )
        switch( portId )
        {
        case borderport_s_out:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_s_out, index, true );
            break;
        case borderport_start:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_start, index, true );
            break;
        }
}

void Capsule_Sender::unbindPort( bool isBorder, int portId, int index )
{
    if( isBorder )
        switch( portId )
        {
        case borderport_s_out:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_s_out, index, false );
            UMLRTFrameService::disconnectPort( borderPorts[borderport_s_out], index );
            break;
        case borderport_start:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_start, index, false );
            UMLRTFrameService::disconnectPort( borderPorts[borderport_start], index );
            break;
        }
}

void Capsule_Sender::inject( const UMLRTMessage & message )
{
    msg = &message;
    switch( currentState )
    {
    case WaitingToStart:
        currentState = state_____WaitingToStart( &message );
        break;
    case MessageSent:
        currentState = state_____MessageSent( &message );
        break;
    default:
        break;
    }
}

void Capsule_Sender::initialize( const UMLRTMessage & message )
{
    msg = &message;
    actionchain_____t0( &message );
    currentState = WaitingToStart;
}

const char * Capsule_Sender::getCurrentStateString() const
{
    return stateNames[currentState];
}




void Capsule_Sender::update_state( Capsule_Sender::State newState )
{
    currentState = newState;
}

void Capsule_Sender::entryaction_____MessageSent( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/RelayPortOptionalPassThrough/RelayPortOptionalPassThrough.uml RootElement::Sender::MessageSent entry  */
    cout << "[Sender](MessageSent)" << endl;
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_Sender::entryaction_____WaitingToStart( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/RelayPortOptionalPassThrough/RelayPortOptionalPassThrough.uml RootElement::Sender::WaitingToStart entry  */
    cout << "[Sender](WaitingToStart)" << endl;
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_Sender::transitionaction_____t1( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/RelayPortOptionalPassThrough/RelayPortOptionalPassThrough.uml RootElement::Sender transition WaitingToStart,MessageSent,begin:start */
    cout << "[Sender](t1) sending message" << endl;
    s_out.msg1().send();
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_Sender::actionchain_____t0( const UMLRTMessage * msg )
{
    update_state( WaitingToStart );
    entryaction_____WaitingToStart( msg );
}

void Capsule_Sender::actionchain_____t1( const UMLRTMessage * msg )
{
    update_state( SPECIAL_INTERNAL_STATE_TOP );
    transitionaction_____t1( msg );
    update_state( MessageSent );
    entryaction_____MessageSent( msg );
}

Capsule_Sender::State Capsule_Sender::state_____MessageSent( const UMLRTMessage * msg )
{
    switch( msg->destPort->role()->id )
    {
    default:
        this->unexpectedMessage();
        break;
    }
    return currentState;
}

Capsule_Sender::State Capsule_Sender::state_____WaitingToStart( const UMLRTMessage * msg )
{
    switch( msg->destPort->role()->id )
    {
    case port_start:
        switch( msg->getSignalId() )
        {
        case Start::signal_begin:
            actionchain_____t1( msg );
            return MessageSent;
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
        Capsule_Sender::port_s_out,
        "Protocol1",
        "s_out",
        "",
        1,
        true,
        false,
        false,
        false,
        false,
        false,
        true
    },
    {
        Capsule_Sender::port_start,
        "Start",
        "start",
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
    2,
    portroles_border,
    0,
    NULL
};

