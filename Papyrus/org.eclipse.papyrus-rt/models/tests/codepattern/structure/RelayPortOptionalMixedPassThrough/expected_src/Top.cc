
#include "Top.hh"

#include "Medium.hh"
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

#include <iostream>
#include "UtilityMacros.hh"
using namespace std;

Capsule_Top::Capsule_Top( const UMLRTCapsuleClass * cd, UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat )
: UMLRTCapsule( NULL, cd, st, border, internal, isStat )
, control( internalPorts[internalport_control] )
, frame( borderPorts[borderport_frame] )
, medium( &slot->parts[part_medium] )
, receiver( &slot->parts[part_receiver] )
, sender( &slot->parts[part_sender] )
, currentState( SPECIAL_INTERNAL_STATE_UNVISITED )
{
    stateNames[Begin] = "Begin";
    stateNames[SPECIAL_INTERNAL_STATE_TOP] = "<top>";
    stateNames[SPECIAL_INTERNAL_STATE_UNVISITED] = "<uninitialized>";
}











void Capsule_Top::bindPort( bool isBorder, int portId, int index )
{
    if( isBorder )
        switch( portId )
        {
        }
    else
        UMLRTFrameService::sendBoundUnbound( internalPorts, internalport_control, index, true );
}

void Capsule_Top::unbindPort( bool isBorder, int portId, int index )
{
    if( isBorder )
        switch( portId )
        {
        }
    else
    {
        UMLRTFrameService::sendBoundUnbound( internalPorts, internalport_control, index, false );
        UMLRTFrameService::disconnectPort( internalPorts[internalport_control], index );
    }
}


void Capsule_Top::inject( const UMLRTMessage & message )
{
    msg = &message;
    switch( currentState )
    {
    case Begin:
        currentState = state_____Begin( &message );
        break;
    default:
        break;
    }
}

void Capsule_Top::initialize( const UMLRTMessage & message )
{
    msg = &message;
    actionchain_____t0( &message );
    currentState = Begin;
}

const char * Capsule_Top::getCurrentStateString() const
{
    return stateNames[currentState];
}




void Capsule_Top::update_state( Capsule_Top::State newState )
{
    currentState = newState;
}

void Capsule_Top::entryaction_____Begin( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/RelayPortOptionalMixedPassThrough/RelayPortOptionalMixedPassThrough.uml RootElement::Top::Begin entry  */
    log.log(LFCAPINST_STATE, "starting");
    log.log(LFCAPINST_STATE, "incarnating medium");
    mediumId = frame.incarnate(medium, Medium);
    if (!mediumId.isValid()) {
    log.log(LFCAPINST_STATE, "failed to incarnate medium");
    exit(0);
    }
    log.log(LFCAPINST_STATE, "starting sender");
    control.begin().send();
    log.log(LFCAPINST_STATE, "begin message sent");
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_Top::actionchain_____t0( const UMLRTMessage * msg )
{
    update_state( Begin );
    entryaction_____Begin( msg );
}

Capsule_Top::State Capsule_Top::state_____Begin( const UMLRTMessage * msg )
{
    switch( msg->destPort->role()->id )
    {
    default:
        this->unexpectedMessage();
        break;
    }
    return currentState;
}


static const UMLRTCapsuleRole roles[] = 
{
    {
        "medium",
        &Medium,
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
        Capsule_Top::port_control,
        "Control",
        "control",
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
        Capsule_Top::port_log,
        "Log",
        "log",
        "",
        0,
        false,
        false,
        false,
        false,
        true,
        false,
        false
    }
};

static void instantiate_Top( const UMLRTRtsInterface * rts, UMLRTSlot * slot, const UMLRTCommsPort * * borderPorts )
{
    const UMLRTCommsPort * * internalPorts = UMLRTFrameService::createInternalPorts( slot, &Top );
    UMLRTFrameService::connectPorts( internalPorts[Capsule_Top::internalport_control], 0, &slot->parts[Capsule_Top::part_sender].slots[0]->ports[Capsule_Sender::borderport_control], 0 );
    UMLRTFrameService::connectPorts( &slot->parts[Capsule_Top::part_medium].slots[0]->ports[Capsule_Medium::borderport_m_inp], 0, &slot->parts[Capsule_Top::part_sender].slots[0]->ports[Capsule_Sender::borderport_out], 0 );
    UMLRTFrameService::connectPorts( &slot->parts[Capsule_Top::part_medium].slots[0]->ports[Capsule_Medium::borderport_m_inp], 1, &slot->parts[Capsule_Top::part_sender].slots[0]->ports[Capsule_Sender::borderport_out], 1 );
    UMLRTFrameService::connectPorts( &slot->parts[Capsule_Top::part_medium].slots[0]->ports[Capsule_Medium::borderport_m_out], 0, &slot->parts[Capsule_Top::part_receiver].slots[0]->ports[Capsule_Receiver::borderport_inp], 0 );
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

