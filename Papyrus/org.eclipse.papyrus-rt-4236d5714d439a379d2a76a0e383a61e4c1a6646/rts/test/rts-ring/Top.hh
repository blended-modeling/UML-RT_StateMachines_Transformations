
#ifndef TOP_HH
#define TOP_HH

#include "umlrtcapsule.hh"
#include "umlrtcapsuleclass.hh"
#include "umlrtinmessage.hh"
struct UMLRTCapsulePart;
struct UMLRTCommsPort;
struct UMLRTSlot;

class Capsule_Top : public UMLRTCapsule
{
public:
    Capsule_Top( const UMLRTCapsuleClass * cd, UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * internal, bool isStat );
    enum PartId
    {
        part_part1,
        part_part2,
        part_part3,
        part_part4
    };
protected:
    const UMLRTCapsulePart * part1() const;
    const UMLRTCapsulePart * part2() const;
    const UMLRTCapsulePart * part3() const;
    const UMLRTCapsulePart * part4() const;
public:
    virtual void bindPort( bool isBorder, int portId, int index );
    virtual void unbindPort( bool isBorder, int portId, int index );
    virtual void initialize( const UMLRTInMessage & msg );
    virtual void inject( const UMLRTInMessage & msg );
};
extern const UMLRTCapsuleClass Top;

#endif

