
#include "D.hh"

#include "C.hh"
#include "umlrtslot.hh"
#include <cstddef>
#include "umlrtcapsuleclass.hh"
class UMLRTRtsInterface;
struct UMLRTCommsPort;

Capsule_D::Capsule_D( const UMLRTCapsuleClass * cd, UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat )
: Capsule_C( cd, st, border, internal, isStat )
{
}


void Capsule_D::bindPort( bool isBorder, int portId, int index )
{
}

void Capsule_D::unbindPort( bool isBorder, int portId, int index )
{
}

void Capsule_D::initialize( const UMLRTMessage & msg )
{
}

void Capsule_D::inject( const UMLRTMessage & msg )
{
}


static void instantiate_D( const UMLRTRtsInterface * rts, UMLRTSlot * slot, const UMLRTCommsPort * * borderPorts )
{
    slot->capsule = new Capsule_D( &D, slot, borderPorts, NULL, false );
}

const UMLRTCapsuleClass D = 
{
    "D",
    &C,
    instantiate_D,
    0,
    NULL,
    0,
    NULL,
    0,
    NULL
};

