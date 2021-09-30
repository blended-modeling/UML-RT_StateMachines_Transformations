
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
    stateNames[top__MessageSent] = "top__MessageSent";
    stateNames[top__WaitingToStart] = "top__WaitingToStart";
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
    case top__WaitingToStart:
        currentState = state_____top__WaitingToStart( &message );
        break;
    case top__MessageSent:
        currentState = state_____top__MessageSent( &message );
        break;
    default:
        break;
    }
}

void Capsule_Sender::initialize( const UMLRTMessage & message )
{
    msg = &message;
    actionchain_____action_____RootElement__Sender__StateMachine__Region__t0( &message );
    currentState = top__WaitingToStart;
}

const char * Capsule_Sender::getCurrentStateString() const
{
    return stateNames[currentState];
}




void Capsule_Sender::entryaction_____RootElement__Sender__StateMachine__Region__MessageSent__OpaqueBehavior( const UMLRTMessage * msg )
{
    // the following code has been generated
    /* UMLRT-CODEGEN:platform:/resource/RelayPortOptionalPassThrough/RelayPortOptionalPassThrough.uml#_81lJ0Mt3EeWdNb6-Lc-hxQ */
    #define rtdata ( (void *)msg->getParam( 0 ) )
    // generated code ends
    cout << "[Sender.MessageSent]" << endl;
    // the following code has been generated
    #undef rtdata
    // generated code ends
}

void Capsule_Sender::entryaction_____RootElement__Sender__StateMachine__Region__WaitingToStart__OpaqueBehavior( const UMLRTMessage * msg )
{
    // the following code has been generated
    /* UMLRT-CODEGEN:platform:/resource/RelayPortOptionalPassThrough/RelayPortOptionalPassThrough.uml#_tj8LwMt3EeWdNb6-Lc-hxQ */
    #define rtdata ( (void *)msg->getParam( 0 ) )
    // generated code ends
    cout << "[Sender.WaitingToStart]" << endl;
    // the following code has been generated
    #undef rtdata
    // generated code ends
}

void Capsule_Sender::transitionaction_____RootElement__Sender__StateMachine__Region__t1__OpaqueBehavior0( const UMLRTMessage * msg )
{
    // the following code has been generated
    /* UMLRT-CODEGEN:platform:/resource/RelayPortOptionalPassThrough/RelayPortOptionalPassThrough.uml#_oe2EwMt4EeWdNb6-Lc-hxQ */
    #define rtdata ( (void *)msg->getParam( 0 ) )
    // generated code ends
    cout << "[Sender.t1] sending message" << endl;
    s_out.msg1().send();
    // the following code has been generated
    #undef rtdata
    // generated code ends
}

void Capsule_Sender::actionchain_____action_____RootElement__Sender__StateMachine__Region__t0( const UMLRTMessage * msg )
{
    entryaction_____RootElement__Sender__StateMachine__Region__WaitingToStart__OpaqueBehavior( msg );
}

void Capsule_Sender::actionchain_____action_____RootElement__Sender__StateMachine__Region__t1( const UMLRTMessage * msg )
{
    transitionaction_____RootElement__Sender__StateMachine__Region__t1__OpaqueBehavior0( msg );
    entryaction_____RootElement__Sender__StateMachine__Region__MessageSent__OpaqueBehavior( msg );
}

Capsule_Sender::State Capsule_Sender::state_____top__MessageSent( const UMLRTMessage * msg )
{
    switch( msg->destPort->role()->id )
    {
    default:
        this->unexpectedMessage();
        break;
    }
    return currentState;
}

Capsule_Sender::State Capsule_Sender::state_____top__WaitingToStart( const UMLRTMessage * msg )
{
    switch( msg->destPort->role()->id )
    {
    case port_start:
        switch( msg->getSignalId() )
        {
        case Start::signal_begin:
            actionchain_____action_____RootElement__Sender__StateMachine__Region__t1( msg );
            return top__MessageSent;
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

