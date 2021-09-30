
#ifndef TOP_HH
#define TOP_HH

#include "Protocol1.hh"
#include "Protocol10.hh"
#include "Protocol2.hh"
#include "Protocol3.hh"
#include "Protocol4.hh"
#include "Protocol5.hh"
#include "Protocol6.hh"
#include "Protocol7.hh"
#include "Protocol8.hh"
#include "Protocol9.hh"
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
        internalport_protocol10,
        internalport_protocol102,
        internalport_protocol12,
        internalport_protocol2,
        internalport_protocol22,
        internalport_protocol3,
        internalport_protocol32,
        internalport_protocol4,
        internalport_protocol42,
        internalport_protocol5,
        internalport_protocol52,
        internalport_protocol6,
        internalport_protocol62,
        internalport_protocol7,
        internalport_protocol72,
        internalport_protocol8,
        internalport_protocol82,
        internalport_protocol9,
        internalport_protocol92
    };
protected:
    Protocol10::Base protocol10;
    Protocol10::Conj protocol102;
    Protocol1::Conj protocol12;
    Protocol2::Base protocol2;
    Protocol2::Conj protocol22;
    Protocol3::Base protocol3;
    Protocol3::Conj protocol32;
    Protocol4::Base protocol4;
    Protocol4::Conj protocol42;
    Protocol5::Base protocol5;
    Protocol5::Conj protocol52;
    Protocol6::Base protocol6;
    Protocol6::Conj protocol62;
    Protocol7::Base protocol7;
    Protocol7::Conj protocol72;
    Protocol8::Base protocol8;
    Protocol8::Conj protocol82;
    Protocol9::Base protocol9;
    Protocol9::Conj protocol92;
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
        port_protocol10,
        port_protocol102,
        port_protocol12,
        port_protocol2,
        port_protocol22,
        port_protocol3,
        port_protocol32,
        port_protocol4,
        port_protocol42,
        port_protocol5,
        port_protocol52,
        port_protocol6,
        port_protocol62,
        port_protocol7,
        port_protocol72,
        port_protocol8,
        port_protocol82,
        port_protocol9,
        port_protocol92
    };
    virtual void bindPort( bool isBorder, int portId, int index );
    virtual void unbindPort( bool isBorder, int portId, int index );
    virtual void initialize( const UMLRTMessage & msg );
    virtual void inject( const UMLRTMessage & msg );
};
extern const UMLRTCapsuleClass Top;

#endif

