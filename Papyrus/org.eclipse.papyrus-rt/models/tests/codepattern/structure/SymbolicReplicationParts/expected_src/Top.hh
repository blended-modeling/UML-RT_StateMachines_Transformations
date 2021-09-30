
#ifndef TOP_HH
#define TOP_HH

#include "umlrtcapsule.hh"
#include "umlrtcapsuleclass.hh"
#include "umlrtmessage.hh"
struct UMLRTCapsulePart;
struct UMLRTCommsPort;
struct UMLRTSlot;

class Capsule_Top : public UMLRTCapsule
{
public:
    Capsule_Top( const UMLRTCapsuleClass * cd, UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat );
    enum PartId
    {
        part_capsule1
    };
protected:
    const UMLRTCapsulePart * const capsule1;
public:
    virtual void bindPort( bool isBorder, int portId, int index );
    virtual void unbindPort( bool isBorder, int portId, int index );
    int SIZE;
    virtual void initialize( const UMLRTMessage & msg );
    virtual void inject( const UMLRTMessage & msg );
};
extern const UMLRTCapsuleClass Top;

#endif

