
#include "Top.hh"

#include "Pinger.hh"
#include "Ponger.hh"
#include "umlrtcapsuleclass.hh"
#include "umlrtcapsulepart.hh"
#include "umlrtcapsulerole.hh"
#include "umlrtcommsport.hh"
#include "umlrtcommsportfarend.hh"
#include "umlrtcommsportrole.hh"
#include "umlrtframeservice.hh"
#include "umlrtinmessage.hh"
#include "umlrtslot.hh"
#include <cstddef>
class UMLRTRtsInterface;

#include <iostream>
#include <stdio.h>
#include "umlrtcontroller.hh"

Capsule_Top::Capsule_Top( UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * internal, bool isStat )
: UMLRTCapsule( NULL, &Top, st, border, internal, isStat )
{
}


UMLRTFrameProtocol_baserole Capsule_Top::FramePort() const
{
    return UMLRTFrameProtocol_baserole( borderPorts[borderport_FramePort] );
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
    if( isBorder )
        switch( portId )
        {
        case borderport_FramePort:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_FramePort, index, true );
            break;
        }
    else
    {
    }
}

void Capsule_Top::unbindPort( bool isBorder, int portId, int index )
{
    if( isBorder )
        switch( portId )
        {
        case borderport_FramePort:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_FramePort, index, true );
            UMLRTFrameService::disconnectPort( borderPorts[borderport_FramePort], index );
            break;
        }
    else
    {
    }
}

void Capsule_Top::inject( const UMLRTInMessage & msg )
{
    switch( currentState )
    {
    case top__State1:
        currentState = state_____top__State1( msg );
        break;
    }
}

void Capsule_Top::initialize( const UMLRTInMessage & msg )
{
    actionchain_____top__Transition2__ActionChain3( msg );
    currentState = top__State1;
}



void Capsule_Top::transitionaction_____top__Transition2__ActionChain3__onInit( const UMLRTInMessage & msg )
{
    void * rtdata = *(void * *)msg.signal.getPayload();
    std::cout << "Top initialised!\n";

    UMLRTCapsuleId pongerId = FramePort().incarnate( ponger(), Ponger );
    if( ! pongerId.isValid() )
        context()->perror("ponger incarnate failed.");
    printf( "incarnated ponger valid:%d @%p %s\n",
               pongerId.isValid(), pongerId.getCapsule(), pongerId.getCapsule()->getName());
}

void Capsule_Top::actionchain_____top__Transition2__ActionChain3( const UMLRTInMessage & msg )
{
    transitionaction_____top__Transition2__ActionChain3__onInit( msg );
}

Capsule_Top::State Capsule_Top::state_____top__State1( const UMLRTInMessage & msg )
{
    switch( msg.destPort->role()->id )
    {
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
        0,
        1,
        true,
        false
    }
};
static const UMLRTCommsPortRole portroles_border[] = 
{
    {
        Capsule_Top::port_FramePort,
        "UMLRTFrameProtocol",
        "FramePort",
        NULL,
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
    UMLRTFrameService::connectPorts( &slot->parts[Capsule_Top::part_ponger].slots[0]->ports[Capsule_Ponger::borderport_PongPort], 0, &slot->parts[Capsule_Top::part_pinger].slots[0]->ports[Capsule_Pinger::borderport_PingPort], 0 );
    Pinger.instantiate( NULL, slot->parts[Capsule_Top::part_pinger].slots[0], UMLRTFrameService::createBorderPorts( slot->parts[Capsule_Top::part_pinger].slots[0], Pinger.numPortRolesBorder ) );
    slot->capsule = new Capsule_Top( slot, borderPorts, NULL, false );
}
const UMLRTCapsuleClass Top = 
{
    "Top",
    NULL,
    instantiate_Top,
    2,
    roles,
    1,
    portroles_border,
    0,
    NULL
};
