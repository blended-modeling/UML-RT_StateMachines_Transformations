
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

C Capsule_A::op1( char * x, D y )
{
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/ExclusionsOperations/ExclusionsOperations.uml ExclusionsOperations::A operation op1 */
    // op1
    /* UMLRTGEN-USERREGION-END */
}

C Capsule_A::op2( char * x, D y )
{
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/ExclusionsOperations/ExclusionsOperations.uml ExclusionsOperations::A operation op2 */
    /* UMLRTGEN-USERREGION-END */
}

C Capsule_A::op3( char * x, D y )
{
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/ExclusionsOperations/ExclusionsOperations.uml ExclusionsOperations::A operation op3 */
    // op3
    /* UMLRTGEN-USERREGION-END */
}

C Capsule_A::op4( char * x, D y )
{
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/ExclusionsOperations/ExclusionsOperations.uml ExclusionsOperations::A operation op4 */
    /* UMLRTGEN-USERREGION-END */
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

