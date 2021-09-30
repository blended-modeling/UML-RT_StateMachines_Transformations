
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
    stateNames[top__MessageReceived] = "top__MessageReceived";
    stateNames[top__Listening] = "top__Listening";
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
    case top__Listening:
        currentState = state_____top__Listening( &message );
        break;
    case top__MessageReceived:
        currentState = state_____top__MessageReceived( &message );
        break;
    default:
        break;
    }
}

void Capsule_Receiver::initialize( const UMLRTMessage & message )
{
    msg = &message;
    actionchain_____action_____RootElement__Receiver__StateMachine__Region__t0( &message );
    currentState = top__Listening;
}

const char * Capsule_Receiver::getCurrentStateString() const
{
    return stateNames[currentState];
}




void Capsule_Receiver::entryaction_____RootElement__Receiver__StateMachine__Region__Listening__OpaqueBehavior( const UMLRTMessage * msg )
{
    // the following code has been generated
    /* UMLRT-CODEGEN:platform:/resource/RelayPortOptionalPassThrough/RelayPortOptionalPassThrough.uml#_Sjv74Mt3EeWdNb6-Lc-hxQ */
    #define rtdata ( (void *)msg->getParam( 0 ) )
    // generated code ends
    cout << "[Receiver.Listening]" << endl;
    // the following code has been generated
    #undef rtdata
    // generated code ends
}

void Capsule_Receiver::entryaction_____RootElement__Receiver__StateMachine__Region__MessageReceived__OpaqueBehavior( const UMLRTMessage * msg )
{
    // the following code has been generated
    /* UMLRT-CODEGEN:platform:/resource/RelayPortOptionalPassThrough/RelayPortOptionalPassThrough.uml#_Y6IPIMt3EeWdNb6-Lc-hxQ */
    #define rtdata ( (void *)msg->getParam( 0 ) )
    // generated code ends
    cout << "[Receiver.MessageReceived]" << endl;
    // the following code has been generated
    #undef rtdata
    // generated code ends
}

void Capsule_Receiver::actionchain_____action_____RootElement__Receiver__StateMachine__Region__t0( const UMLRTMessage * msg )
{
    entryaction_____RootElement__Receiver__StateMachine__Region__Listening__OpaqueBehavior( msg );
}

void Capsule_Receiver::actionchain_____action_____RootElement__Receiver__StateMachine__Region__t1( const UMLRTMessage * msg )
{
    entryaction_____RootElement__Receiver__StateMachine__Region__MessageReceived__OpaqueBehavior( msg );
}

Capsule_Receiver::State Capsule_Receiver::state_____top__Listening( const UMLRTMessage * msg )
{
    switch( msg->destPort->role()->id )
    {
    case port_r_inp:
        switch( msg->getSignalId() )
        {
        case Protocol1::signal_msg1:
            actionchain_____action_____RootElement__Receiver__StateMachine__Region__t1( msg );
            return top__MessageReceived;
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

Capsule_Receiver::State Capsule_Receiver::state_____top__MessageReceived( const UMLRTMessage * msg )
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

