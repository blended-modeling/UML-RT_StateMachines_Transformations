
#include "A.hh"

#include "umlrtslot.hh"
#include <cstddef>
#include "umlrtcapsuleclass.hh"
class UMLRTRtsInterface;
struct UMLRTCommsPort;

Capsule_A::Capsule_A( const UMLRTCapsuleClass * cd, UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat )
: UMLRTCapsule( NULL, cd, st, border, internal, isStat )
{
}


void Capsule_A::bindPort( bool isBorder, int portId, int index )
{
}

void Capsule_A::unbindPort( bool isBorder, int portId, int index )
{
}




void Capsule_A::initialize( const UMLRTMessage & msg )
{
}

void Capsule_A::inject( const UMLRTMessage & msg )
{
}


static void instantiate_A( const UMLRTRtsInterface * rts, UMLRTSlot * slot, const UMLRTCommsPort * * borderPorts )
{
    slot->capsule = new Capsule_A( &A, slot, borderPorts, NULL, false );
}

const UMLRTCapsuleClass A = 
{
    "A",
    NULL,
    instantiate_A,
    0,
    NULL,
    0,
    NULL,
    0,
    NULL
};

