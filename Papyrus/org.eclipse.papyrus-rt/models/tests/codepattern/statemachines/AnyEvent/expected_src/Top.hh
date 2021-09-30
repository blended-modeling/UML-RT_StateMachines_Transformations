
#ifndef TOP_HH
#define TOP_HH

#include "Protocol1.hh"
#include "Protocol2.hh"
#include "Protocol3.hh"
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
protected:
    Protocol1::Base protocol1;
public:
    enum InternalPortId
    {
        internalport_protocol1,
        internalport_protocol2,
        internalport_protocol3
    };
protected:
    Protocol2::Base protocol2;
    Protocol3::Conj protocol3;
public:
    enum PartId
    {
        part_capsule1
    };
protected:
    const UMLRTCapsulePart * const capsule1;
public:
    enum PortId
    {
        port_protocol1,
        port_protocol2,
        port_protocol3
    };
    virtual void bindPort( bool isBorder, int portId, int index );
    virtual void unbindPort( bool isBorder, int portId, int index );
    virtual void initialize( const UMLRTMessage & msg );
    virtual void inject( const UMLRTMessage & msg );
};
extern const UMLRTCapsuleClass Top;

#endif

