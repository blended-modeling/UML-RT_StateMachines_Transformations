
#include "Top.hh"

#include "Pinger.hh"
#include "Ponger.hh"
#include "umlrtcapsuleclass.hh"
#include "umlrtcapsulepart.hh"
#include "umlrtcommsportrole.hh"
#include "umlrtslot.hh"
#include <cstddef>
#include "umlrtcapsuleclass.hh"
#include "umlrtcapsulepart.hh"
#include "umlrtcapsulerole.hh"
#include "umlrtcommsport.hh"
#include "umlrtframeservice.hh"
#include "umlrtmessage.hh"
#include <cstddef>
#include <stdint.h>
class UMLRTRtsInterface;

#include <iostream>

Capsule_Top::Capsule_Top( const UMLRTCapsuleClass * cd, UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat )
: UMLRTCapsule( NULL, cd, st, border, internal, isStat )
, currentState( SPECIAL_INTERNAL_STATE_UNVISITED )
{
    stateNames[top__State1] = "top__State1";
}


const UMLRTCapsulePart * Capsule_Top::pinger() const
{
    return &slot->parts[part_pinger];
}

const UMLRTCapsulePart * Capsule_Top::ponger() const
{
    return &slot->parts[part_ponger];
}

void Capsule_Top::bindPort( bool isBorder, int portId, int index )
{
}

void Capsule_Top::unbindPort( bool isBorder, int portId, int index )
{
}

void Capsule_Top::inject( const UMLRTMessage & message )
{
    msg = &message;
    switch( currentState )
    {
    case top__State1:
        currentState = state_____top__State1( message );
        break;
    default:
        break;
    }
}

void Capsule_Top::initialize( const UMLRTMessage & message )
{
    msg = &message;
    actionchain_____top__Transition2__ActionChain3( message );
    currentState = top__State1;
}

const char * Capsule_Top::getCurrentStateString() const
{
    return stateNames[currentState];
}




void Capsule_Top::transitionaction_____top__Transition2__ActionChain3__onInit( const UMLRTMessage & msg )
{
   // UMLRT-CODEGEN:platform:/resource/PingPong/PingPong.uml#_DjVVEFYMEeSmi4Fyw18d0w
    std::cout << "Top initialised!\n";
}

void Capsule_Top::actionchain_____top__Transition2__ActionChain3( const UMLRTMessage & msg )
{
    transitionaction_____top__Transition2__ActionChain3__onInit( msg );
}

Capsule_Top::State Capsule_Top::state_____top__State1( const UMLRTMessage & msg )
{
    switch( msg.destPort->role()->id )
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
        "pinger",
        &Pinger,
        1,
        1,
        false,
        false
    },
    {
        "ponger",
        &Ponger,
        1,
        1,
        false,
        false
    }
};

static void instantiate_Top( const UMLRTRtsInterface * rts, UMLRTSlot * slot, const UMLRTCommsPort * * borderPorts )
{
    UMLRTFrameService::connectPorts( &slot->parts[Capsule_Top::part_pinger].slots[0]->ports[Capsule_Pinger::borderport_PingPort], 0, &slot->parts[Capsule_Top::part_ponger].slots[0]->ports[Capsule_Ponger::borderport_PongPort], 0 );
    Pinger.instantiate( NULL, slot->parts[Capsule_Top::part_pinger].slots[0], UMLRTFrameService::createBorderPorts( slot->parts[Capsule_Top::part_pinger].slots[0], Pinger.numPortRolesBorder ) );
    Ponger.instantiate( NULL, slot->parts[Capsule_Top::part_ponger].slots[0], UMLRTFrameService::createBorderPorts( slot->parts[Capsule_Top::part_ponger].slots[0], Ponger.numPortRolesBorder ) );
    slot->capsule = new Capsule_Top( &Top, slot, borderPorts, NULL, false );
}

const UMLRTCapsuleClass Top = 
{
    "Top",
    NULL,
    instantiate_Top,
    2,
    roles,
    0,
    NULL,
    0,
    NULL
};

