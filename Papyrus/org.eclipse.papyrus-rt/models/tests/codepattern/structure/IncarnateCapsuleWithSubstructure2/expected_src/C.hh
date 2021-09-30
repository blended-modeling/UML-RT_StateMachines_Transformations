
#ifndef C_HH
#define C_HH

#include "Protocol2.hh"
#include "umlrtcapsule.hh"
#include "umlrtcapsuleclass.hh"
#include "umlrtmessage.hh"
struct UMLRTCommsPort;
struct UMLRTSlot;

class Capsule_C : public UMLRTCapsule
{
public:
    Capsule_C( const UMLRTCapsuleClass * cd, UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat );
protected:
    Protocol2::Base protocol2;
public:
    enum BorderPortId
    {
        borderport_protocol2
    };
    enum PartId
    {
    };
    enum PortId
    {
        port_protocol2
    };
    virtual void bindPort( bool isBorder, int portId, int index );
    virtual void unbindPort( bool isBorder, int portId, int index );
    virtual void initialize( const UMLRTMessage & msg );
    virtual void inject( const UMLRTMessage & msg );
};
extern const UMLRTCapsuleClass C;

#endif

