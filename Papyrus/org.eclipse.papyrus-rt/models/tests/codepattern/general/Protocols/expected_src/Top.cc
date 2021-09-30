
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
, protocol10( internalPorts[internalport_protocol10] )
, protocol102( internalPorts[internalport_protocol102] )
, protocol12( internalPorts[internalport_protocol12] )
, protocol2( internalPorts[internalport_protocol2] )
, protocol22( internalPorts[internalport_protocol22] )
, protocol3( internalPorts[internalport_protocol3] )
, protocol32( internalPorts[internalport_protocol32] )
, protocol4( internalPorts[internalport_protocol4] )
, protocol42( internalPorts[internalport_protocol42] )
, protocol5( internalPorts[internalport_protocol5] )
, protocol52( internalPorts[internalport_protocol52] )
, protocol6( internalPorts[internalport_protocol6] )
, protocol62( internalPorts[internalport_protocol62] )
, protocol7( internalPorts[internalport_protocol7] )
, protocol72( internalPorts[internalport_protocol72] )
, protocol8( internalPorts[internalport_protocol8] )
, protocol82( internalPorts[internalport_protocol82] )
, protocol9( internalPorts[internalport_protocol9] )
, protocol92( internalPorts[internalport_protocol92] )
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
        UMLRTFrameService::sendBoundUnbound( internalPorts, internalport_protocol10, index, true );
        UMLRTFrameService::sendBoundUnbound( internalPorts, internalport_protocol102, index, true );
        UMLRTFrameService::sendBoundUnbound( internalPorts, internalport_protocol12, index, true );
        UMLRTFrameService::sendBoundUnbound( internalPorts, internalport_protocol2, index, true );
        UMLRTFrameService::sendBoundUnbound( internalPorts, internalport_protocol22, index, true );
        UMLRTFrameService::sendBoundUnbound( internalPorts, internalport_protocol3, index, true );
        UMLRTFrameService::sendBoundUnbound( internalPorts, internalport_protocol32, index, true );
        UMLRTFrameService::sendBoundUnbound( internalPorts, internalport_protocol4, index, true );
        UMLRTFrameService::sendBoundUnbound( internalPorts, internalport_protocol42, index, true );
        UMLRTFrameService::sendBoundUnbound( internalPorts, internalport_protocol5, index, true );
        UMLRTFrameService::sendBoundUnbound( internalPorts, internalport_protocol52, index, true );
        UMLRTFrameService::sendBoundUnbound( internalPorts, internalport_protocol6, index, true );
        UMLRTFrameService::sendBoundUnbound( internalPorts, internalport_protocol62, index, true );
        UMLRTFrameService::sendBoundUnbound( internalPorts, internalport_protocol7, index, true );
        UMLRTFrameService::sendBoundUnbound( internalPorts, internalport_protocol72, index, true );
        UMLRTFrameService::sendBoundUnbound( internalPorts, internalport_protocol8, index, true );
        UMLRTFrameService::sendBoundUnbound( internalPorts, internalport_protocol82, index, true );
        UMLRTFrameService::sendBoundUnbound( internalPorts, internalport_protocol9, index, true );
        UMLRTFrameService::sendBoundUnbound( internalPorts, internalport_protocol92, index, true );
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
        UMLRTFrameService::sendBoundUnbound( internalPorts, internalport_protocol10, index, false );
        UMLRTFrameService::disconnectPort( internalPorts[internalport_protocol10], index );
        UMLRTFrameService::sendBoundUnbound( internalPorts, internalport_protocol102, index, false );
        UMLRTFrameService::disconnectPort( internalPorts[internalport_protocol102], index );
        UMLRTFrameService::sendBoundUnbound( internalPorts, internalport_protocol12, index, false );
        UMLRTFrameService::disconnectPort( internalPorts[internalport_protocol12], index );
        UMLRTFrameService::sendBoundUnbound( internalPorts, internalport_protocol2, index, false );
        UMLRTFrameService::disconnectPort( internalPorts[internalport_protocol2], index );
        UMLRTFrameService::sendBoundUnbound( internalPorts, internalport_protocol22, index, false );
        UMLRTFrameService::disconnectPort( internalPorts[internalport_protocol22], index );
        UMLRTFrameService::sendBoundUnbound( internalPorts, internalport_protocol3, index, false );
        UMLRTFrameService::disconnectPort( internalPorts[internalport_protocol3], index );
        UMLRTFrameService::sendBoundUnbound( internalPorts, internalport_protocol32, index, false );
        UMLRTFrameService::disconnectPort( internalPorts[internalport_protocol32], index );
        UMLRTFrameService::sendBoundUnbound( internalPorts, internalport_protocol4, index, false );
        UMLRTFrameService::disconnectPort( internalPorts[internalport_protocol4], index );
        UMLRTFrameService::sendBoundUnbound( internalPorts, internalport_protocol42, index, false );
        UMLRTFrameService::disconnectPort( internalPorts[internalport_protocol42], index );
        UMLRTFrameService::sendBoundUnbound( internalPorts, internalport_protocol5, index, false );
        UMLRTFrameService::disconnectPort( internalPorts[internalport_protocol5], index );
        UMLRTFrameService::sendBoundUnbound( internalPorts, internalport_protocol52, index, false );
        UMLRTFrameService::disconnectPort( internalPorts[internalport_protocol52], index );
        UMLRTFrameService::sendBoundUnbound( internalPorts, internalport_protocol6, index, false );
        UMLRTFrameService::disconnectPort( internalPorts[internalport_protocol6], index );
        UMLRTFrameService::sendBoundUnbound( internalPorts, internalport_protocol62, index, false );
        UMLRTFrameService::disconnectPort( internalPorts[internalport_protocol62], index );
        UMLRTFrameService::sendBoundUnbound( internalPorts, internalport_protocol7, index, false );
        UMLRTFrameService::disconnectPort( internalPorts[internalport_protocol7], index );
        UMLRTFrameService::sendBoundUnbound( internalPorts, internalport_protocol72, index, false );
        UMLRTFrameService::disconnectPort( internalPorts[internalport_protocol72], index );
        UMLRTFrameService::sendBoundUnbound( internalPorts, internalport_protocol8, index, false );
        UMLRTFrameService::disconnectPort( internalPorts[internalport_protocol8], index );
        UMLRTFrameService::sendBoundUnbound( internalPorts, internalport_protocol82, index, false );
        UMLRTFrameService::disconnectPort( internalPorts[internalport_protocol82], index );
        UMLRTFrameService::sendBoundUnbound( internalPorts, internalport_protocol9, index, false );
        UMLRTFrameService::disconnectPort( internalPorts[internalport_protocol9], index );
        UMLRTFrameService::sendBoundUnbound( internalPorts, internalport_protocol92, index, false );
        UMLRTFrameService::disconnectPort( internalPorts[internalport_protocol92], index );
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
        Capsule_Top::port_protocol10,
        "Protocol10",
        "protocol10",
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
        Capsule_Top::port_protocol102,
        "Protocol10",
        "protocol102",
        "",
        1,
        true,
        true,
        false,
        false,
        false,
        false,
        true
    },
    {
        Capsule_Top::port_protocol12,
        "Protocol1",
        "protocol12",
        "",
        1,
        true,
        true,
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
        Capsule_Top::port_protocol22,
        "Protocol2",
        "protocol22",
        "",
        1,
        true,
        true,
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
        false,
        false,
        false,
        false,
        false,
        true
    },
    {
        Capsule_Top::port_protocol32,
        "Protocol3",
        "protocol32",
        "",
        1,
        true,
        true,
        false,
        false,
        false,
        false,
        true
    },
    {
        Capsule_Top::port_protocol4,
        "Protocol4",
        "protocol4",
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
        Capsule_Top::port_protocol42,
        "Protocol4",
        "protocol42",
        "",
        1,
        true,
        true,
        false,
        false,
        false,
        false,
        true
    },
    {
        Capsule_Top::port_protocol5,
        "Protocol5",
        "protocol5",
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
        Capsule_Top::port_protocol52,
        "Protocol5",
        "protocol52",
        "",
        1,
        true,
        true,
        false,
        false,
        false,
        false,
        true
    },
    {
        Capsule_Top::port_protocol6,
        "Protocol6",
        "protocol6",
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
        Capsule_Top::port_protocol62,
        "Protocol6",
        "protocol62",
        "",
        1,
        true,
        true,
        false,
        false,
        false,
        false,
        true
    },
    {
        Capsule_Top::port_protocol7,
        "Protocol7",
        "protocol7",
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
        Capsule_Top::port_protocol72,
        "Protocol7",
        "protocol72",
        "",
        1,
        true,
        true,
        false,
        false,
        false,
        false,
        true
    },
    {
        Capsule_Top::port_protocol8,
        "Protocol8",
        "protocol8",
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
        Capsule_Top::port_protocol82,
        "Protocol8",
        "protocol82",
        "",
        1,
        true,
        true,
        false,
        false,
        false,
        false,
        true
    },
    {
        Capsule_Top::port_protocol9,
        "Protocol9",
        "protocol9",
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
        Capsule_Top::port_protocol92,
        "Protocol9",
        "protocol92",
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
    UMLRTFrameService::connectPorts( internalPorts[Capsule_Top::internalport_protocol1], 0, &slot->parts[Capsule_Top::part_capsule1].slots[0]->ports[Capsule_Capsule1::borderport_p1a], 0 );
    UMLRTFrameService::connectPorts( internalPorts[Capsule_Top::internalport_protocol10], 0, &slot->parts[Capsule_Top::part_capsule1].slots[0]->ports[Capsule_Capsule1::borderport_p10a], 0 );
    UMLRTFrameService::connectPorts( internalPorts[Capsule_Top::internalport_protocol102], 0, &slot->parts[Capsule_Top::part_capsule1].slots[0]->ports[Capsule_Capsule1::borderport_p10b], 0 );
    UMLRTFrameService::connectPorts( internalPorts[Capsule_Top::internalport_protocol12], 0, &slot->parts[Capsule_Top::part_capsule1].slots[0]->ports[Capsule_Capsule1::borderport_p1b], 0 );
    UMLRTFrameService::connectPorts( internalPorts[Capsule_Top::internalport_protocol2], 0, &slot->parts[Capsule_Top::part_capsule1].slots[0]->ports[Capsule_Capsule1::borderport_p2a], 0 );
    UMLRTFrameService::connectPorts( internalPorts[Capsule_Top::internalport_protocol22], 0, &slot->parts[Capsule_Top::part_capsule1].slots[0]->ports[Capsule_Capsule1::borderport_p2b], 0 );
    UMLRTFrameService::connectPorts( internalPorts[Capsule_Top::internalport_protocol3], 0, &slot->parts[Capsule_Top::part_capsule1].slots[0]->ports[Capsule_Capsule1::borderport_p3a], 0 );
    UMLRTFrameService::connectPorts( internalPorts[Capsule_Top::internalport_protocol32], 0, &slot->parts[Capsule_Top::part_capsule1].slots[0]->ports[Capsule_Capsule1::borderport_p3b], 0 );
    UMLRTFrameService::connectPorts( internalPorts[Capsule_Top::internalport_protocol4], 0, &slot->parts[Capsule_Top::part_capsule1].slots[0]->ports[Capsule_Capsule1::borderport_p4a], 0 );
    UMLRTFrameService::connectPorts( internalPorts[Capsule_Top::internalport_protocol42], 0, &slot->parts[Capsule_Top::part_capsule1].slots[0]->ports[Capsule_Capsule1::borderport_p4b], 0 );
    UMLRTFrameService::connectPorts( internalPorts[Capsule_Top::internalport_protocol5], 0, &slot->parts[Capsule_Top::part_capsule1].slots[0]->ports[Capsule_Capsule1::borderport_p5a], 0 );
    UMLRTFrameService::connectPorts( internalPorts[Capsule_Top::internalport_protocol52], 0, &slot->parts[Capsule_Top::part_capsule1].slots[0]->ports[Capsule_Capsule1::borderport_p5b], 0 );
    UMLRTFrameService::connectPorts( internalPorts[Capsule_Top::internalport_protocol6], 0, &slot->parts[Capsule_Top::part_capsule1].slots[0]->ports[Capsule_Capsule1::borderport_p6a], 0 );
    UMLRTFrameService::connectPorts( internalPorts[Capsule_Top::internalport_protocol62], 0, &slot->parts[Capsule_Top::part_capsule1].slots[0]->ports[Capsule_Capsule1::borderport_p6b], 0 );
    UMLRTFrameService::connectPorts( internalPorts[Capsule_Top::internalport_protocol7], 0, &slot->parts[Capsule_Top::part_capsule1].slots[0]->ports[Capsule_Capsule1::borderport_p7a], 0 );
    UMLRTFrameService::connectPorts( internalPorts[Capsule_Top::internalport_protocol72], 0, &slot->parts[Capsule_Top::part_capsule1].slots[0]->ports[Capsule_Capsule1::borderport_p7b], 0 );
    UMLRTFrameService::connectPorts( internalPorts[Capsule_Top::internalport_protocol8], 0, &slot->parts[Capsule_Top::part_capsule1].slots[0]->ports[Capsule_Capsule1::borderport_p8a], 0 );
    UMLRTFrameService::connectPorts( internalPorts[Capsule_Top::internalport_protocol82], 0, &slot->parts[Capsule_Top::part_capsule1].slots[0]->ports[Capsule_Capsule1::borderport_p8b], 0 );
    UMLRTFrameService::connectPorts( internalPorts[Capsule_Top::internalport_protocol9], 0, &slot->parts[Capsule_Top::part_capsule1].slots[0]->ports[Capsule_Capsule1::borderport_p9a], 0 );
    UMLRTFrameService::connectPorts( internalPorts[Capsule_Top::internalport_protocol92], 0, &slot->parts[Capsule_Top::part_capsule1].slots[0]->ports[Capsule_Capsule1::borderport_p9b], 0 );
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
    20,
    portroles_internal
};

