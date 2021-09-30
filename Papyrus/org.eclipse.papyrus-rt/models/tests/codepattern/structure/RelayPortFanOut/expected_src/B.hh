
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
    Protocol1::Conj q;
public:
    enum BorderPortId
    {
        borderport_q
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
        port_q
    };
    virtual void bindPort( bool isBorder, int portId, int index );
    virtual void unbindPort( bool isBorder, int portId, int index );
    virtual void initialize( const UMLRTMessage & msg );
    virtual void inject( const UMLRTMessage & msg );
};
extern const UMLRTCapsuleClass B;

#endif

