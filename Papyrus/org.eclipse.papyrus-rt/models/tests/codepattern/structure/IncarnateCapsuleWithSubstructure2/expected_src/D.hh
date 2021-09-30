
#ifndef D_HH
#define D_HH

#include "Protocol1.hh"
#include "Protocol2.hh"
#include "umlrtcapsule.hh"
#include "umlrtcapsuleclass.hh"
#include "umlrtmessage.hh"
struct UMLRTCapsulePart;
struct UMLRTCommsPort;
struct UMLRTSlot;

class Capsule_D : public UMLRTCapsule
{
public:
    Capsule_D( const UMLRTCapsuleClass * cd, UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat );
protected:
    Protocol1::Base protocol1;
public:
    enum BorderPortId
    {
        borderport_protocol1,
        borderport_protocol2
    };
protected:
    Protocol2::Conj protocol2;
public:
    enum PartId
    {
        part_j
    };
protected:
    const UMLRTCapsulePart * const j;
public:
    enum PortId
    {
        port_protocol1,
        port_protocol2
    };
    virtual void bindPort( bool isBorder, int portId, int index );
    virtual void unbindPort( bool isBorder, int portId, int index );
    virtual void initialize( const UMLRTMessage & msg );
    virtual void inject( const UMLRTMessage & msg );
};
extern const UMLRTCapsuleClass D;

#endif

