
#ifndef CAPSULE1_HH
#define CAPSULE1_HH

#include "Enumeration1.hh"
#include "umlrtcapsule.hh"
#include "umlrtcapsuleclass.hh"
#include "umlrtmessage.hh"
struct UMLRTCommsPort;
struct UMLRTSlot;

class Capsule_Capsule1 : public UMLRTCapsule
{
public:
    Capsule_Capsule1( const UMLRTCapsuleClass * cd, UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat );
    enum PartId
    {
    };
    virtual void bindPort( bool isBorder, int portId, int index );
    virtual void unbindPort( bool isBorder, int portId, int index );
    char * Attribute1;
private:
    char * Attribute2;
protected:
    char * Attribute3;
public:
    char * Attribute4;
    Enumeration1 Attribute5;
private:
    Enumeration1 Attribute6;
protected:
    Enumeration1 Attribute7;
public:
    Enumeration1 Attribute8;
    virtual void initialize( const UMLRTMessage & msg );
    virtual void inject( const UMLRTMessage & msg );
};
extern const UMLRTCapsuleClass Capsule1;

#endif

