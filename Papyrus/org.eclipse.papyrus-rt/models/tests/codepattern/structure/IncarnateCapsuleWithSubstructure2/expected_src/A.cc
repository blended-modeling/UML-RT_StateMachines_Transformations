
#include "A.hh"

#include "C.hh"
#include "D.hh"
#include "umlrtcapsuleclass.hh"
#include "umlrtcapsulepart.hh"
#include "umlrtslot.hh"
#include <cstddef>
#include "umlrtcapsulerole.hh"
#include "umlrtcommsport.hh"
#include "umlrtcommsportrole.hh"
#include "umlrtframeservice.hh"
class UMLRTRtsInterface;

Capsule_A::Capsule_A( const UMLRTCapsuleClass * cd, UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat )
: UMLRTCapsule( NULL, cd, st, border, internal, isStat )
, protocol1( borderPorts[borderport_protocol1] )
, c( &slot->parts[part_c] )
, d( &slot->parts[part_d] )
{
}







void Capsule_A::bindPort( bool isBorder, int portId, int index )
{
    if( isBorder )
        switch( portId )
        {
        case borderport_protocol1:
            switch( index )
            {
            case 0:
                UMLRTFrameService::connectRelayPort( borderPorts[borderport_protocol1], index, &slot->parts[part_d].slots[0]->ports[Capsule_D::borderport_protocol1], 0 );
                UMLRTFrameService::bindSubcapsulePort( true, slot->parts[part_d].slots[0]->capsule, portId, index );
                break;
            }
            break;
        }
}

void Capsule_A::unbindPort( bool isBorder, int portId, int index )
{
    if( isBorder )
        switch( portId )
        {
        case borderport_protocol1:
            switch( index )
            {
            case 0:
                UMLRTFrameService::unbindSubcapsulePort( true, slot->parts[part_d].slots[0]->capsule, portId, index );
                break;
            }
            break;
        }
}

void Capsule_A::initialize( const UMLRTMessage & msg )
{
}

void Capsule_A::inject( const UMLRTMessage & msg )
{
}


static const UMLRTCapsuleRole roles[] = 
{
    {
        "c",
        &C,
        1,
        1,
        false,
        false
    },
    {
        "d",
        &D,
        0,
        1,
        true,
        false
    }
};

static const UMLRTCommsPortRole portroles_border[] = 
{
    {
        Capsule_A::port_protocol1,
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
    }
};

static void instantiate_A( const UMLRTRtsInterface * rts, UMLRTSlot * slot, const UMLRTCommsPort * * borderPorts )
{
    UMLRTFrameService::connectRelayPort( borderPorts[Capsule_A::borderport_protocol1], 0, &slot->parts[Capsule_A::part_d].slots[0]->ports[Capsule_D::borderport_protocol1], 0 );
    UMLRTFrameService::connectPorts( &slot->parts[Capsule_A::part_c].slots[0]->ports[Capsule_C::borderport_protocol2], 0, &slot->parts[Capsule_A::part_d].slots[0]->ports[Capsule_D::borderport_protocol2], 0 );
    C.instantiate( NULL, slot->parts[Capsule_A::part_c].slots[0], UMLRTFrameService::createBorderPorts( slot->parts[Capsule_A::part_c].slots[0], C.numPortRolesBorder ) );
    slot->capsule = new Capsule_A( &A, slot, borderPorts, NULL, false );
}

const UMLRTCapsuleClass A = 
{
    "A",
    NULL,
    instantiate_A,
    2,
    roles,
    1,
    portroles_border,
    0,
    NULL
};

