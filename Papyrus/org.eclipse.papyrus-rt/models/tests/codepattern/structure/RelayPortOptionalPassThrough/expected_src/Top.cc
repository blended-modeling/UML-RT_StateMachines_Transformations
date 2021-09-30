
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
, frame( borderPorts[borderport_frame] )
, start( internalPorts[internalport_start] )
, mediator( &slot->parts[part_mediator] )
, receiver( &slot->parts[part_receiver] )
, sender( &slot->parts[part_sender] )
{
    stateNames[Started] = "Started";
    stateNames[SPECIAL_INTERNAL_STATE_TOP] = "<top>";
    stateNames[SPECIAL_INTERNAL_STATE_UNVISITED] = "<uninitialized>";
}

void Capsule_Top::inject( const UMLRTMessage & message )
{
    msg = &message;
    switch( currentState )
    {
    case Started:
        currentState = state_____Started( &message );
        break;
    default:
        break;
    }
}

void Capsule_Top::initialize( const UMLRTMessage & message )
{
    msg = &message;
    actionchain_____t0( &message );
    currentState = Started;
}

const char * Capsule_Top::getCurrentStateString() const
{
    return stateNames[currentState];
}




void Capsule_Top::update_state( Capsule_Top::State newState )
{
    currentState = newState;
}

void Capsule_Top::entryaction_____Started( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/RelayPortOptionalPassThrough/RelayPortOptionalPassThrough.uml RootElement::Top::Started entry  */
    cout << "[Top](Started)" << endl;
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_Top::transitionaction_____t0( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/RelayPortOptionalPassThrough/RelayPortOptionalPassThrough.uml RootElement::Top transition Init,Started */
    cout << "[Top](t0) starting" << endl;
    cout << "[Top](t0) incarnating mediator" << endl;
    mediatorId = frame.incarnate(mediator, Mediator);
    if (!mediatorId.isValid()) {
    cout << "[Top](t0) failed to incarnate mediator" << endl;
    return;
    }
    cout << "[Top](t0) starting sender" << endl;
    start.begin().send();
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_Top::actionchain_____t0( const UMLRTMessage * msg )
{
    transitionaction_____t0( msg );
    update_state( Started );
    entryaction_____Started( msg );
}

Capsule_Top::State Capsule_Top::state_____Started( const UMLRTMessage * msg )
{
    switch( msg->destPort->role()->id )
    {
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
        }
    else
        UMLRTFrameService::sendBoundUnbound( internalPorts, internalport_start, index, true );
}

void Capsule_Top::unbindPort( bool isBorder, int portId, int index )
{
    if( isBorder )
        switch( portId )
        {
        }
    else
    {
        UMLRTFrameService::sendBoundUnbound( internalPorts, internalport_start, index, false );
        UMLRTFrameService::disconnectPort( internalPorts[internalport_start], index );
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
        Capsule_Top::port_frame,
        "Frame",
        "frame",
        "",
        0,
        false,
        false,
        false,
        false,
        false,
        false,
        false
    }
};

static const UMLRTCommsPortRole portroles_internal[] = 
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

static void instantiate_Top( const UMLRTRtsInterface * rts, UMLRTSlot * slot, const UMLRTCommsPort * * borderPorts )
{
    const UMLRTCommsPort * * internalPorts = UMLRTFrameService::createInternalPorts( slot, &Top );
    UMLRTFrameService::connectPorts( internalPorts[Capsule_Top::internalport_start], 0, &slot->parts[Capsule_Top::part_sender].slots[0]->ports[Capsule_Sender::borderport_start], 0 );
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
    1,
    portroles_internal
};

