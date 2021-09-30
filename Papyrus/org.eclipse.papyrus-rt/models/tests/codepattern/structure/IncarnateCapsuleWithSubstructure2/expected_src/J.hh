
#ifndef J_HH
#define J_HH

#include "Protocol1.hh"
#include "Protocol2.hh"
#include "umlrtcapsule.hh"
#include "umlrtcapsuleclass.hh"
#include "umlrtmessage.hh"
struct UMLRTCommsPort;
struct UMLRTSlot;

class Capsule_J : public UMLRTCapsule
{
public:
    Capsule_J( const UMLRTCapsuleClass * cd, UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat );
    enum BorderPortId
    {
        borderport_protocol1,
        borderport_protocol2
    };
protected:
    Protocol1::Base protocol1;
    Protocol2::Conj protocol2;
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
extern const UMLRTCapsuleClass J;

#endif

