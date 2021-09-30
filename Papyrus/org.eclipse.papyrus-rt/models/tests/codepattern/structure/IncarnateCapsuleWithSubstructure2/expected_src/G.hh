
#ifndef G_HH
#define G_HH

#include "Protocol1.hh"
#include "Protocol2.hh"
#include "umlrtcapsule.hh"
#include "umlrtcapsuleclass.hh"
#include "umlrtmessage.hh"
struct UMLRTCommsPort;
struct UMLRTSlot;

class Capsule_G : public UMLRTCapsule
{
public:
    Capsule_G( const UMLRTCapsuleClass * cd, UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat );
protected:
    Protocol1::Conj protocol1;
public:
    enum BorderPortId
    {
        borderport_protocol1,
        borderport_protocol2
    };
protected:
    Protocol2::Base protocol2;
public:
    enum PartId
    {
    };
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
extern const UMLRTCapsuleClass G;

#endif

