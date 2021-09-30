
#include "E.hh"

#include "F.hh"
#include "umlrtcapsuleclass.hh"
#include "umlrtcapsulepart.hh"
#include "umlrtslot.hh"
#include <cstddef>
#include "umlrtcapsulerole.hh"
#include "umlrtcommsport.hh"
#include "umlrtcommsportrole.hh"
#include "umlrtframeservice.hh"
class UMLRTRtsInterface;

Capsule_E::Capsule_E( const UMLRTCapsuleClass * cd, UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat )
: UMLRTCapsule( NULL, cd, st, border, internal, isStat )
, p( borderPorts[borderport_p] )
, f( &slot->parts[part_f] )
{
}






void Capsule_E::bindPort( bool isBorder, int portId, int index )
{
    if( isBorder )
        switch( portId )
        {
        case borderport_p:
            switch( index )
            {
            case 0:
                UMLRTFrameService::connectRelayPort( borderPorts[borderport_p], index, &slot->parts[part_f].slots[0]->ports[Capsule_F::borderport_p], 0 );
                UMLRTFrameService::bindSubcapsulePort( true, slot->parts[part_f].slots[0]->capsule, portId, index );
                break;
            }
            break;
        }
}

void Capsule_E::unbindPort( bool isBorder, int portId, int index )
{
    if( isBorder )
        switch( portId )
        {
        case borderport_p:
            switch( index )
            {
            case 0:
                UMLRTFrameService::unbindSubcapsulePort( true, slot->parts[part_f].slots[0]->capsule, portId, index );
                break;
            }
            break;
        }
}

void Capsule_E::initialize( const UMLRTMessage & msg )
{
}

void Capsule_E::inject( const UMLRTMessage & msg )
{
}


static const UMLRTCapsuleRole roles[] = 
{
    {
        "f",
        &F,
        1,
        1,
        false,
        false
    }
};

static const UMLRTCommsPortRole portroles_border[] = 
{
    {
        Capsule_E::port_p,
        "P",
        "p",
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

static void instantiate_E( const UMLRTRtsInterface * rts, UMLRTSlot * slot, const UMLRTCommsPort * * borderPorts )
{
    UMLRTFrameService::connectRelayPort( borderPorts[Capsule_E::borderport_p], 0, &slot->parts[Capsule_E::part_f].slots[0]->ports[Capsule_F::borderport_p], 0 );
    F.instantiate( NULL, slot->parts[Capsule_E::part_f].slots[0], UMLRTFrameService::createBorderPorts( slot->parts[Capsule_E::part_f].slots[0], F.numPortRolesBorder ) );
    slot->capsule = new Capsule_E( &E, slot, borderPorts, NULL, false );
}

const UMLRTCapsuleClass E = 
{
    "E",
    NULL,
    instantiate_E,
    1,
    roles,
    1,
    portroles_border,
    0,
    NULL
};

