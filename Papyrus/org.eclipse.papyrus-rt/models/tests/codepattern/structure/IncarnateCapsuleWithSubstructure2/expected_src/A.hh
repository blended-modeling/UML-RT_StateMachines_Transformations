
#ifndef A_HH
#define A_HH

#include "Protocol1.hh"
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
protected:
    Protocol1::Base protocol1;
public:
    enum BorderPortId
    {
        borderport_protocol1
    };
    enum PartId
    {
        part_c,
        part_d
    };
protected:
    const UMLRTCapsulePart * const c;
    const UMLRTCapsulePart * const d;
public:
    enum PortId
    {
        port_protocol1
    };
    virtual void bindPort( bool isBorder, int portId, int index );
    virtual void unbindPort( bool isBorder, int portId, int index );
    virtual void initialize( const UMLRTMessage & msg );
    virtual void inject( const UMLRTMessage & msg );
};
extern const UMLRTCapsuleClass A;

#endif

