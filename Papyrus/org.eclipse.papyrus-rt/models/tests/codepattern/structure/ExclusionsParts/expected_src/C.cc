
#include "C.hh"

#include "umlrtslot.hh"
#include <cstddef>
#include "umlrtcapsuleclass.hh"
class UMLRTRtsInterface;
struct UMLRTCommsPort;

Capsule_C::Capsule_C( const UMLRTCapsuleClass * cd, UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat )
: UMLRTCapsule( NULL, cd, st, border, internal, isStat )
{
}


void Capsule_C::bindPort( bool isBorder, int portId, int index )
{
}

void Capsule_C::unbindPort( bool isBorder, int portId, int index )
{
}

void Capsule_C::initialize( const UMLRTMessage & msg )
{
}

void Capsule_C::inject( const UMLRTMessage & msg )
{
}


static void instantiate_C( const UMLRTRtsInterface * rts, UMLRTSlot * slot, const UMLRTCommsPort * * borderPorts )
{
    slot->capsule = new Capsule_C( &C, slot, borderPorts, NULL, false );
}

const UMLRTCapsuleClass C = 
{
    "C",
    NULL,
    instantiate_C,
    0,
    NULL,
    0,
    NULL,
    0,
    NULL
};

