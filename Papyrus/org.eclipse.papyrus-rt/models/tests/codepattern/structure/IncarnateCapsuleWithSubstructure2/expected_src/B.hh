
#ifndef B_HH
#define B_HH

#include "Protocol1.hh"
#include "umlrtcapsule.hh"
#include "umlrtcapsuleclass.hh"
#include "umlrtmessage.hh"
struct UMLRTCapsulePart;
struct UMLRTCommsPort;
struct UMLRTSlot;

class Capsule_B : public UMLRTCapsule
{
public:
    Capsule_B( const UMLRTCapsuleClass * cd, UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat );
protected:
    Protocol1::Conj protocol1;
public:
    enum BorderPortId
    {
        borderport_protocol1
    };
    enum PartId
    {
        part_e,
        part_f
    };
protected:
    const UMLRTCapsulePart * const e;
    const UMLRTCapsulePart * const f;
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
extern const UMLRTCapsuleClass B;

#endif

