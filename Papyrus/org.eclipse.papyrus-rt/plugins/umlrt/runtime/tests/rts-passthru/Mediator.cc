
#include "Mediator.hh"

#include <cstddef>
#include "umlrtcapsuleclass.hh"
#include "umlrtcommsportrole.hh"
#include "umlrtframeservice.hh"
#include "umlrtslot.hh"
class UMLRTRtsInterface;
struct UMLRTCommsPort;

Capsule_Mediator::Capsule_Mediator( const UMLRTCapsuleClass * cd, UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat )
: UMLRTCapsule( NULL, cd, st, border, internal, isStat )
, m_inp( borderPorts[borderport_m_inp] )
, m_out( borderPorts[borderport_m_out] )
{
}






void Capsule_Mediator::bindPort( bool isBorder, int portId, int index )
{
    if( isBorder )
        switch( portId )
        {
        case borderport_m_inp:
            // Handle binds for both far-ends of the pass-through ports while binding a single port of the pair.
            UMLRTFrameService::connectFarEnds( borderPorts[borderport_m_inp/*aka portId*/], index, borderPorts[borderport_m_out], 0/*must be known by generator*/);
            UMLRTFrameService::sendBoundUnboundFarEnd( borderPorts[borderport_m_inp/*aka portId*/], index, true );
            UMLRTFrameService::sendBoundUnboundFarEnd( borderPorts[borderport_m_out], 0/*must be known by generator*/, true );
            break;
        case borderport_m_out:
            // Do nothing when binding this half of the pass-through port. The other half handles the connection and rtBound/rtUnbound.*/
            break;
        }
}

void Capsule_Mediator::unbindPort( bool isBorder, int portId, int index )
{
    if( isBorder )
        switch( portId )
        {
        case borderport_m_inp:
            UMLRTFrameService::sendBoundUnboundForPortIndex( borderPorts[borderport_m_inp/*aka portId*/], index, false );
            UMLRTFrameService::disconnectPort( borderPorts[borderport_m_inp], index );
            break;
        case borderport_m_out:
            UMLRTFrameService::sendBoundUnboundForPortIndex( borderPorts[borderport_m_out/*aka portId*/], index, false );
            UMLRTFrameService::disconnectPort( borderPorts[borderport_m_out], index );
            break;
        }
}

void Capsule_Mediator::initialize( const UMLRTMessage & msg )
{
}

void Capsule_Mediator::inject( const UMLRTMessage & msg )
{
}


static const UMLRTCommsPortRole portroles_border[] = 
{
    {
        Capsule_Mediator::port_m_inp,
        "Protocol1",
        "m_inp",
        "",
        1,
        true, // automatic
        true, // conjugated
        false, // locked
        false, // notification
        false, // sap
        false, // spp
        true // wired
    },
    {
        Capsule_Mediator::port_m_out,
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

static void instantiate_Mediator( const UMLRTRtsInterface * rts, UMLRTSlot * slot, const UMLRTCommsPort * * borderPorts )
{
    UMLRTFrameService::connectFarEnds( borderPorts[Capsule_Mediator::borderport_m_inp], 0, borderPorts[Capsule_Mediator::borderport_m_out], 0 );
    slot->capsule = new Capsule_Mediator( &Mediator, slot, borderPorts, NULL, false );
}

const UMLRTCapsuleClass Mediator = 
{
    "Mediator",
    NULL,
    instantiate_Mediator,
    0,
    NULL,
    2,
    portroles_border,
    0,
    NULL
};

