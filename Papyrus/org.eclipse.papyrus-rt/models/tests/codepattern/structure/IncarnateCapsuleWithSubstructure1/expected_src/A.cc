
#include "A.hh"

#include "E.hh"
#include "umlrtcapsulepart.hh"
#include "umlrtslot.hh"
#include <cstddef>
#include "umlrtcapsuleclass.hh"
#include "umlrtcapsulerole.hh"
#include "umlrtcommsport.hh"
#include "umlrtcommsportrole.hh"
#include "umlrtframeservice.hh"
class UMLRTRtsInterface;

Capsule_A::Capsule_A( const UMLRTCapsuleClass * cd, UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat )
: UMLRTCapsule( NULL, cd, st, border, internal, isStat )
, p( borderPorts[borderport_p] )
, e( &slot->parts[part_e] )
{
}






void Capsule_A::bindPort( bool isBorder, int portId, int index )
{
    if( isBorder )
        switch( portId )
        {
        case borderport_p:
            switch( index )
            {
            case 0:
                UMLRTFrameService::connectRelayPort( borderPorts[borderport_p], index, &slot->parts[part_e].slots[0]->ports[Capsule_E::borderport_p], 0 );
                UMLRTFrameService::bindSubcapsulePort( true, slot->parts[part_e].slots[0]->capsule, portId, index );
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
        case borderport_p:
            switch( index )
            {
            case 0:
                UMLRTFrameService::unbindSubcapsulePort( true, slot->parts[part_e].slots[0]->capsule, portId, index );
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
        "e",
        &E,
        0,
        1,
        true,
        false
    }
};

static const UMLRTCommsPortRole portroles_border[] = 
{
    {
        Capsule_A::port_p,
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

static void instantiate_A( const UMLRTRtsInterface * rts, UMLRTSlot * slot, const UMLRTCommsPort * * borderPorts )
{
    UMLRTFrameService::connectRelayPort( borderPorts[Capsule_A::borderport_p], 0, &slot->parts[Capsule_A::part_e].slots[0]->ports[Capsule_E::borderport_p], 0 );
    slot->capsule = new Capsule_A( &A, slot, borderPorts, NULL, false );
}

const UMLRTCapsuleClass A = 
{
    "A",
    NULL,
    instantiate_A,
    1,
    roles,
    1,
    portroles_border,
    0,
    NULL
};

