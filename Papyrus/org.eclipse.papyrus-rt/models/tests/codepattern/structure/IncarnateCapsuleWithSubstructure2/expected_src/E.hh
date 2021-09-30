
#ifndef E_HH
#define E_HH

#include "Protocol1.hh"
#include "Protocol3.hh"
#include "umlrtcapsule.hh"
#include "umlrtcapsuleclass.hh"
#include "umlrtmessage.hh"
struct UMLRTCapsulePart;
struct UMLRTCommsPort;
struct UMLRTSlot;

class Capsule_E : public UMLRTCapsule
{
public:
    Capsule_E( const UMLRTCapsuleClass * cd, UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat );
    enum BorderPortId
    {
        borderport_protocol1,
        borderport_protocol3
    };
protected:
    Protocol1::Conj protocol1;
    Protocol3::Base protocol3;
public:
    enum PartId
    {
        part_g,
        part_h
    };
protected:
    const UMLRTCapsulePart * const g;
    const UMLRTCapsulePart * const h;
public:
    enum PortId
    {
        port_protocol1,
        port_protocol3
    };
    virtual void bindPort( bool isBorder, int portId, int index );
    virtual void unbindPort( bool isBorder, int portId, int index );
    virtual void initialize( const UMLRTMessage & msg );
    virtual void inject( const UMLRTMessage & msg );
};
extern const UMLRTCapsuleClass E;

#endif

