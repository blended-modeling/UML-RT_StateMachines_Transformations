
#ifndef H_HH
#define H_HH

#include "Protocol2.hh"
#include "Protocol3.hh"
#include "umlrtcapsule.hh"
#include "umlrtcapsuleclass.hh"
#include "umlrtmessage.hh"
struct UMLRTCapsulePart;
struct UMLRTCommsPort;
struct UMLRTSlot;

class Capsule_H : public UMLRTCapsule
{
public:
    Capsule_H( const UMLRTCapsuleClass * cd, UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat );
protected:
    Protocol2::Conj protocol2;
public:
    enum BorderPortId
    {
        borderport_protocol2,
        borderport_protocol3
    };
protected:
    Protocol3::Base protocol3;
public:
    enum PartId
    {
        part_d,
        part_i
    };
protected:
    const UMLRTCapsulePart * const d;
    const UMLRTCapsulePart * const i;
public:
    enum PortId
    {
        port_protocol2,
        port_protocol3
    };
    virtual void bindPort( bool isBorder, int portId, int index );
    virtual void unbindPort( bool isBorder, int portId, int index );
    virtual void initialize( const UMLRTMessage & msg );
    virtual void inject( const UMLRTMessage & msg );
};
extern const UMLRTCapsuleClass H;

#endif

