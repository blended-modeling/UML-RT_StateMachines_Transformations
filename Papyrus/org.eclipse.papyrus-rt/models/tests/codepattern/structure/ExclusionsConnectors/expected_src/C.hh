
#ifndef C_HH
#define C_HH

#include "Protocol1.hh"
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
    Protocol1::Base p;
public:
    enum BorderPortId
    {
        borderport_p,
        borderport_q
    };
protected:
    Protocol1::Base q;
public:
    enum PartId
    {
    };
    enum PortId
    {
        port_p,
        port_q
    };
    virtual void bindPort( bool isBorder, int portId, int index );
    virtual void unbindPort( bool isBorder, int portId, int index );
    virtual void initialize( const UMLRTMessage & msg );
    virtual void inject( const UMLRTMessage & msg );
};
extern const UMLRTCapsuleClass C;

#endif

