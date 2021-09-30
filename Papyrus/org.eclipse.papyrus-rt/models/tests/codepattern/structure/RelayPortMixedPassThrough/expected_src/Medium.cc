
#include "Medium.hh"

#include "Receiver.hh"
#include "umlrtcapsuleclass.hh"
#include "umlrtcapsulepart.hh"
#include "umlrtslot.hh"
#include <cstddef>
#include "umlrtcapsulerole.hh"
#include "umlrtcommsport.hh"
#include "umlrtcommsportrole.hh"
#include "umlrtframeservice.hh"
class UMLRTRtsInterface;

Capsule_Medium::Capsule_Medium( const UMLRTCapsuleClass * cd, UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat )
: UMLRTCapsule( NULL, cd, st, border, internal, isStat )
, m_inp( borderPorts[borderport_m_inp] )
, m_out( borderPorts[borderport_m_out] )
, eavesdropper( &slot->parts[part_eavesdropper] )
{
}







void Capsule_Medium::bindPort( bool isBorder, int portId, int index )
{
    if( isBorder )
        switch( portId )
        {
        case borderport_m_inp:
            switch( index )
            {
            case 0:
                UMLRTFrameService::connectFarEnds( borderPorts[borderport_m_inp], index, borderPorts[borderport_m_out], 0 );
                UMLRTFrameService::sendBoundUnboundFarEnd( borderPorts[borderport_m_inp], index, true );
                UMLRTFrameService::sendBoundUnboundFarEnd( borderPorts[borderport_m_out], 0, true );
                break;
            case 1:
                UMLRTFrameService::connectRelayPort( borderPorts[borderport_m_inp], index, &slot->parts[part_eavesdropper].slots[0]->ports[Capsule_Receiver::borderport_inp], 0 );
                UMLRTFrameService::bindSubcapsulePort( true, slot->parts[part_eavesdropper].slots[0]->capsule, portId, index );
                break;
            }
            break;
        case borderport_m_out:
            switch( index )
            {
            }
            break;
        }
}

void Capsule_Medium::unbindPort( bool isBorder, int portId, int index )
{
    if( isBorder )
        switch( portId )
        {
        case borderport_m_inp:
            switch( index )
            {
            case 0:
                UMLRTFrameService::sendBoundUnboundForPortIndex( borderPorts[borderport_m_inp], index, false );
                UMLRTFrameService::disconnectPort( borderPorts[borderport_m_inp], index );
                break;
            case 1:
                UMLRTFrameService::unbindSubcapsulePort( true, slot->parts[part_eavesdropper].slots[0]->capsule, portId, index );
                break;
            }
            break;
        case borderport_m_out:
            switch( index )
            {
            case 0:
                UMLRTFrameService::sendBoundUnboundForPortIndex( borderPorts[borderport_m_out], index, false );
                UMLRTFrameService::disconnectPort( borderPorts[borderport_m_out], index );
                break;
            }
            break;
        }
}

void Capsule_Medium::initialize( const UMLRTMessage & msg )
{
}

void Capsule_Medium::inject( const UMLRTMessage & msg )
{
}


static const UMLRTCapsuleRole roles[] = 
{
    {
        "eavesdropper",
        &Receiver,
        1,
        1,
        false,
        false
    }
};

static const UMLRTCommsPortRole portroles_border[] = 
{
    {
        Capsule_Medium::port_m_inp,
        "Protocol1",
        "m_inp",
        "",
        2,
        true,
        true,
        false,
        false,
        false,
        false,
        true
    },
    {
        Capsule_Medium::port_m_out,
        "Protocol1",
        "m_out",
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

static void instantiate_Medium( const UMLRTRtsInterface * rts, UMLRTSlot * slot, const UMLRTCommsPort * * borderPorts )
{
    UMLRTFrameService::connectFarEnds( borderPorts[Capsule_Medium::borderport_m_inp], 0, borderPorts[Capsule_Medium::borderport_m_out], 0 );
    UMLRTFrameService::connectRelayPort( borderPorts[Capsule_Medium::borderport_m_inp], 1, &slot->parts[Capsule_Medium::part_eavesdropper].slots[0]->ports[Capsule_Receiver::borderport_inp], 0 );
    Receiver.instantiate( NULL, slot->parts[Capsule_Medium::part_eavesdropper].slots[0], UMLRTFrameService::createBorderPorts( slot->parts[Capsule_Medium::part_eavesdropper].slots[0], Receiver.numPortRolesBorder ) );
    slot->capsule = new Capsule_Medium( &Medium, slot, borderPorts, NULL, false );
}

const UMLRTCapsuleClass Medium = 
{
    "Medium",
    NULL,
    instantiate_Medium,
    1,
    roles,
    2,
    portroles_border,
    0,
    NULL
};

