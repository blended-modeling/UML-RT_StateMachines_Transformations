
#include "Application.hh"

#include "umlrtslot.hh"
#include <cstddef>
#include "umlrtcapsuleclass.hh"
class UMLRTRtsInterface;
struct UMLRTCommsPort;

Capsule_Application::Capsule_Application( const UMLRTCapsuleClass * cd, UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat )
: UMLRTCapsule( NULL, cd, st, border, internal, isStat )
{
}


void Capsule_Application::bindPort( bool isBorder, int portId, int index )
{
}

void Capsule_Application::unbindPort( bool isBorder, int portId, int index )
{
}


void Capsule_Application::initialize( const UMLRTMessage & msg )
{
}

void Capsule_Application::inject( const UMLRTMessage & msg )
{
}


static void instantiate_Application( const UMLRTRtsInterface * rts, UMLRTSlot * slot, const UMLRTCommsPort * * borderPorts )
{
    slot->capsule = new Capsule_Application( &Application, slot, borderPorts, NULL, false );
}

const UMLRTCapsuleClass Application = 
{
    "Application",
    NULL,
    instantiate_Application,
    0,
    NULL,
    0,
    NULL,
    0,
    NULL
};

