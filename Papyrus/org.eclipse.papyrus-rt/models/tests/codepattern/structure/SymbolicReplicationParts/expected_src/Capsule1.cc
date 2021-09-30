
#include "Capsule1.hh"

#include "umlrtslot.hh"
#include <cstddef>
#include "umlrtcapsuleclass.hh"
class UMLRTRtsInterface;
struct UMLRTCommsPort;

Capsule_Capsule1::Capsule_Capsule1( const UMLRTCapsuleClass * cd, UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat )
: UMLRTCapsule( NULL, cd, st, border, internal, isStat )
{
}


void Capsule_Capsule1::bindPort( bool isBorder, int portId, int index )
{
}

void Capsule_Capsule1::unbindPort( bool isBorder, int portId, int index )
{
}

void Capsule_Capsule1::initialize( const UMLRTMessage & msg )
{
}

void Capsule_Capsule1::inject( const UMLRTMessage & msg )
{
}


static void instantiate_Capsule1( const UMLRTRtsInterface * rts, UMLRTSlot * slot, const UMLRTCommsPort * * borderPorts )
{
    slot->capsule = new Capsule_Capsule1( &Capsule1, slot, borderPorts, NULL, false );
}

const UMLRTCapsuleClass Capsule1 = 
{
    "Capsule1",
    NULL,
    instantiate_Capsule1,
    0,
    NULL,
    0,
    NULL,
    0,
    NULL
};

