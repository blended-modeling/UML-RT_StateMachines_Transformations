
#include "Top.hh"

#include "Capsule1.hh"
#include "umlrtcapsuleclass.hh"
#include "umlrtcapsulepart.hh"
#include "umlrtslot.hh"
#include <cstddef>
#include "umlrtcapsulerole.hh"
#include "umlrtcommsport.hh"
#include "umlrtcommsportrole.hh"
#include "umlrtframeservice.hh"
class UMLRTRtsInterface;

Capsule_Top::Capsule_Top( const UMLRTCapsuleClass * cd, UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat )
: UMLRTCapsule( NULL, cd, st, border, internal, isStat )
, protocol1( internalPorts[internalport_protocol1] )
, protocol2( internalPorts[internalport_protocol2] )
, protocol3( internalPorts[internalport_protocol3] )
, capsule1( &slot->parts[part_capsule1] )
{
}








void Capsule_Top::bindPort( bool isBorder, int portId, int index )
{
    if( isBorder )
        switch( portId )
        {
        }
    else
    {
        UMLRTFrameService::sendBoundUnbound( internalPorts, internalport_protocol1, index, true );
        UMLRTFrameService::sendBoundUnbound( internalPorts, internalport_protocol2, index, true );
        UMLRTFrameService::sendBoundUnbound( internalPorts, internalport_protocol3, index, true );
    }
}

void Capsule_Top::unbindPort( bool isBorder, int portId, int index )
{
    if( isBorder )
        switch( portId )
        {
        }
    else
    {
        UMLRTFrameService::sendBoundUnbound( internalPorts, internalport_protocol1, index, false );
        UMLRTFrameService::disconnectPort( internalPorts[internalport_protocol1], index );
        UMLRTFrameService::sendBoundUnbound( internalPorts, internalport_protocol2, index, false );
        UMLRTFrameService::disconnectPort( internalPorts[internalport_protocol2], index );
        UMLRTFrameService::sendBoundUnbound( internalPorts, internalport_protocol3, index, false );
        UMLRTFrameService::disconnectPort( internalPorts[internalport_protocol3], index );
    }
}

void Capsule_Top::initialize( const UMLRTMessage & msg )
{
}

void Capsule_Top::inject( const UMLRTMessage & msg )
{
}


static const UMLRTCapsuleRole roles[] = 
{
    {
        "capsule1",
        &Capsule1,
        1,
        1,
        false,
        false
    }
};

static const UMLRTCommsPortRole portroles_internal[] = 
{
    {
        Capsule_Top::port_protocol1,
        "Protocol1",
        "protocol1",
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
        Capsule_Top::port_protocol2,
        "Protocol2",
        "protocol2",
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
        Capsule_Top::port_protocol3,
        "Protocol3",
        "protocol3",
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

static void instantiate_Top( const UMLRTRtsInterface * rts, UMLRTSlot * slot, const UMLRTCommsPort * * borderPorts )
{
    const UMLRTCommsPort * * internalPorts = UMLRTFrameService::createInternalPorts( slot, &Top );
    UMLRTFrameService::connectPorts( internalPorts[Capsule_Top::internalport_protocol1], 0, &slot->parts[Capsule_Top::part_capsule1].slots[0]->ports[Capsule_Capsule1::borderport_protocol1], 0 );
    UMLRTFrameService::connectPorts( internalPorts[Capsule_Top::internalport_protocol2], 0, &slot->parts[Capsule_Top::part_capsule1].slots[0]->ports[Capsule_Capsule1::borderport_protocol2], 0 );
    UMLRTFrameService::connectPorts( internalPorts[Capsule_Top::internalport_protocol3], 0, &slot->parts[Capsule_Top::part_capsule1].slots[0]->ports[Capsule_Capsule1::borderport_protocol3], 0 );
    Capsule1.instantiate( NULL, slot->parts[Capsule_Top::part_capsule1].slots[0], UMLRTFrameService::createBorderPorts( slot->parts[Capsule_Top::part_capsule1].slots[0], Capsule1.numPortRolesBorder ) );
    slot->capsule = new Capsule_Top( &Top, slot, borderPorts, internalPorts, false );
}

const UMLRTCapsuleClass Top = 
{
    "Top",
    NULL,
    instantiate_Top,
    1,
    roles,
    0,
    NULL,
    3,
    portroles_internal
};

