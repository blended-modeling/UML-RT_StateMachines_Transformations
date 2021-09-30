
#include "B.hh"

#include "A.hh"
#include "umlrtslot.hh"
#include <cstddef>
#include "umlrtcapsuleclass.hh"
class UMLRTRtsInterface;
struct UMLRTCommsPort;

Capsule_B::Capsule_B( const UMLRTCapsuleClass * cd, UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat )
: Capsule_A( cd, st, border, internal, isStat )
{
}


void Capsule_B::bindPort( bool isBorder, int portId, int index )
{
}

void Capsule_B::unbindPort( bool isBorder, int portId, int index )
{
}



void Capsule_B::initialize( const UMLRTMessage & msg )
{
}

void Capsule_B::inject( const UMLRTMessage & msg )
{
}


static void instantiate_B( const UMLRTRtsInterface * rts, UMLRTSlot * slot, const UMLRTCommsPort * * borderPorts )
{
    slot->capsule = new Capsule_B( &B, slot, borderPorts, NULL, false );
}

const UMLRTCapsuleClass B = 
{
    "B",
    &A,
    instantiate_B,
    0,
    NULL,
    0,
    NULL,
    0,
    NULL
};

