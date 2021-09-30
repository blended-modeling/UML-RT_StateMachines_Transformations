
#include "Top.hh"

#include "Mediator.hh"
#include "Receiver.hh"
#include "Sender.hh"
#include "umlrtcapsuleclass.hh"
#include "umlrtcapsulepart.hh"
#include "umlrtcommsportrole.hh"
#include "umlrtmessage.hh"
#include "umlrtslot.hh"
#include <cstddef>
#include "umlrtcapsulerole.hh"
#include "umlrtcommsport.hh"
#include "umlrtframeservice.hh"
class UMLRTRtsInterface;

Capsule_Top::Capsule_Top( const UMLRTCapsuleClass * cd, UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat )
: UMLRTCapsule( NULL, cd, st, border, internal, isStat )
, currentState( SPECIAL_INTERNAL_STATE_UNVISITED )
, timeoutCount(0)
, frame( internalPorts[internalport_frame] )
, timer( internalPorts[internalport_timer] )
, start( borderPorts[borderport_start] )
, mediator( &slot->parts[part_mediator] )
, receiver( &slot->parts[part_receiver] )
, sender( &slot->parts[part_sender] )
{
    stateNames[top__Started] = "top__Started";
    stateNames[SPECIAL_INTERNAL_STATE_UNVISITED] = "<uninitialized>";
}

void Capsule_Top::inject( const UMLRTMessage & message )
{
    msg = &message;
    switch( currentState )
    {
    case top__Started:
        currentState = state_____top__Started( &message );
        break;
    default:
        break;
    }
}

void Capsule_Top::initialize( const UMLRTMessage & message )
{
    msg = &message;
    actionchain_____action_____RootElement__Top__StateMachine__Region__t0( &message );
    currentState = top__Started;
}

const char * Capsule_Top::getCurrentStateString() const
{
    return stateNames[currentState];
}




void Capsule_Top::entryaction_____RootElement__Top__StateMachine__Region__Started__OpaqueBehavior( const UMLRTMessage * msg )
{
    // the following code has been generated
    /* UMLRT-CODEGEN:platform:/resource/RelayPortOptionalPassThrough/RelayPortOptionalPassThrough.uml#_86jT8Mt4EeWdNb6-Lc-hxQ */
    #define rtdata ( (void *)msg->getParam( 0 ) )
    // generated code ends
    cout << "[Top.Started]" << endl;
    // the following code has been generated
    #undef rtdata
    // generated code ends
}

void Capsule_Top::transitionaction_____RootElement__Top__StateMachine__Region__t0__OpaqueBehavior( const UMLRTMessage * msg )
{
    // the following code has been generated
    /* UMLRT-CODEGEN:platform:/resource/RelayPortOptionalPassThrough/RelayPortOptionalPassThrough.uml#_QZvQgMt5EeWdNb6-Lc-hxQ */
    #define rtdata ( (void *)msg->getParam( 0 ) )
    // generated code ends
    cout << "[Top.t0] starting" << endl;
    cout << "[Top.t0] incarnating mediator" << endl;
    mediatorId = frame.incarnate(mediator, Mediator);
    if (!mediatorId.isValid()) {
    cout << "[Top.t0] failed to incarnate mediator" << endl;
    return;
    }
    context()->debugOutputModel("after incarnate mediator");
    cout << "[Top.t0] starting sender" << endl;
    start.begin().send();

    timer.informEvery(UMLRTTimespec(2,0));


    // the following code has been generated
    #undef rtdata
    // generated code ends
}

void Capsule_Top::actionchain_____action_____RootElement__Top__StateMachine__Region__t0( const UMLRTMessage * msg )
{
    transitionaction_____RootElement__Top__StateMachine__Region__t0__OpaqueBehavior( msg );
    entryaction_____RootElement__Top__StateMachine__Region__Started__OpaqueBehavior( msg );
}

void Capsule_Top::action_timeout_msg( const UMLRTMessage * msg )
{
    if (!timeoutCount)
    {
        timeoutCount++;
        if (!frame.destroy(mediatorId))
        {
            cout << "[Top.t0] destruction of mediator failed " << context()->strerror() << endl;;
        }
        context()->debugOutputModel("after destroying mediator");
        // wait one 'tick' before abort.
    }
    else
    {
        context()->abort();
    }
}

Capsule_Top::State Capsule_Top::state_____top__Started( const UMLRTMessage * msg )
{
    switch( msg->destPort->role()->id )
    {
    case port_timer:
        action_timeout_msg(msg);
        break;
    default:
        this->unexpectedMessage();
        break;
    }
    return currentState;
}










void Capsule_Top::bindPort( bool isBorder, int portId, int index )
{
    if( isBorder )
        switch( portId )
        {
        case borderport_start:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_start, index, true );
            break;
        }
}

void Capsule_Top::unbindPort( bool isBorder, int portId, int index )
{
    if( isBorder )
        switch( portId )
        {
        case borderport_start:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_start, index, false );
            UMLRTFrameService::disconnectPort( borderPorts[borderport_start], index );
            break;
        }
}


static const UMLRTCapsuleRole roles[] = 
{
    {
        "mediator",
        &Mediator,
        0,
        1,
        true,
        false
    },
    {
        "receiver",
        &Receiver,
        1,
        1,
        false,
        false
    },
    {
        "sender",
        &Sender,
        1,
        1,
        false,
        false
    }
};

static const UMLRTCommsPortRole portroles_border[] = 
{
    {
        Capsule_Top::port_start,
        "Start",
        "start",
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

static const UMLRTCommsPortRole portroles_internal[] = 
{
    {
        Capsule_Top::port_frame,
        "Frame",
        "frame",
        "",
        1,
        true,
        false,
        false,
        false,
        true,
        false,
        false
    },
    {
        Capsule_Top::port_timer,
        "UMLRTTimerProtocol",
        "timer",
        "", // registrationOverride
        1,
        false, // automatic
        false, // conjugated
        false, // locked
        false, // notification
        false, // sap
        false, // spp
        false, // wired
    },
};

static void instantiate_Top( const UMLRTRtsInterface * rts, UMLRTSlot * slot, const UMLRTCommsPort * * borderPorts )
{
    const UMLRTCommsPort * * internalPorts = UMLRTFrameService::createInternalPorts( slot, &Top );
    UMLRTFrameService::connectPorts( borderPorts[Capsule_Top::borderport_start], 0, &slot->parts[Capsule_Top::part_sender].slots[0]->ports[Capsule_Sender::borderport_start], 0 );
    UMLRTFrameService::connectPorts( &slot->parts[Capsule_Top::part_mediator].slots[0]->ports[Capsule_Mediator::borderport_m_inp], 0, &slot->parts[Capsule_Top::part_sender].slots[0]->ports[Capsule_Sender::borderport_s_out], 0 );
    UMLRTFrameService::connectPorts( &slot->parts[Capsule_Top::part_mediator].slots[0]->ports[Capsule_Mediator::borderport_m_out], 0, &slot->parts[Capsule_Top::part_receiver].slots[0]->ports[Capsule_Receiver::borderport_r_inp], 0 );
    Receiver.instantiate( NULL, slot->parts[Capsule_Top::part_receiver].slots[0], UMLRTFrameService::createBorderPorts( slot->parts[Capsule_Top::part_receiver].slots[0], Receiver.numPortRolesBorder ) );
    Sender.instantiate( NULL, slot->parts[Capsule_Top::part_sender].slots[0], UMLRTFrameService::createBorderPorts( slot->parts[Capsule_Top::part_sender].slots[0], Sender.numPortRolesBorder ) );
    slot->capsule = new Capsule_Top( &Top, slot, borderPorts, internalPorts, false );
}

const UMLRTCapsuleClass Top = 
{
    "Top",
    NULL,
    instantiate_Top,
    3,
    roles,
    1,
    portroles_border,
    2,
    portroles_internal
};

