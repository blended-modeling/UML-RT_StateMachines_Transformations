
#ifndef A_HH
#define A_HH

#include "umlrtcapsule.hh"
#include "umlrtcapsuleclass.hh"
#include "umlrtmessage.hh"
struct UMLRTCapsulePart;
struct UMLRTCommsPort;
struct UMLRTSlot;

class Capsule_A : public UMLRTCapsule
{
public:
    Capsule_A( const UMLRTCapsuleClass * cd, UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat );
    enum PartId
    {
        part_c1,
        part_c2,
        part_c3
    };
protected:
    const UMLRTCapsulePart * const c1;
    const UMLRTCapsulePart * const c2;
    const UMLRTCapsulePart * const c3;
public:
    virtual void bindPort( bool isBorder, int portId, int index );
    virtual void unbindPort( bool isBorder, int portId, int index );
    virtual void initialize( const UMLRTMessage & msg );
    virtual void inject( const UMLRTMessage & msg );
};
extern const UMLRTCapsuleClass A;

#endif

