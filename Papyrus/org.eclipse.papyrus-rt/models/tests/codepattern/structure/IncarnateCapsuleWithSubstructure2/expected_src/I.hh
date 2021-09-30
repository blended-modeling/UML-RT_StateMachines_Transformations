
#ifndef I_HH
#define I_HH

#include "Protocol1.hh"
#include "Protocol3.hh"
#include "umlrtcapsule.hh"
#include "umlrtcapsuleclass.hh"
#include "umlrtmessage.hh"
struct UMLRTCommsPort;
struct UMLRTSlot;

class Capsule_I : public UMLRTCapsule
{
public:
    Capsule_I( const UMLRTCapsuleClass * cd, UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat );
    enum BorderPortId
    {
        borderport_protocol3,
        borderport_protocol1
    };
protected:
    Protocol1::Conj protocol1;
    Protocol3::Base protocol3;
public:
    enum PartId
    {
    };
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
extern const UMLRTCapsuleClass I;

#endif

