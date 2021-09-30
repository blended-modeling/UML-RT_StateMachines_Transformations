
#ifndef A_HH
#define A_HH

#include "Protocol1.hh"
#include "umlrtcapsule.hh"
#include "umlrtcapsuleclass.hh"
#include "umlrtmessage.hh"
struct UMLRTCommsPort;
struct UMLRTSlot;

class Capsule_A : public UMLRTCapsule
{
public:
    Capsule_A( const UMLRTCapsuleClass * cd, UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat );
protected:
    Protocol1::Base p;
public:
    enum BorderPortId
    {
        borderport_p,
        borderport_q,
        borderport_r
    };
protected:
    Protocol1::Base q;
    Protocol1::Base r;
public:
    enum PartId
    {
    };
    enum PortId
    {
        port_p,
        port_q,
        port_r
    };
    virtual void bindPort( bool isBorder, int portId, int index );
    virtual void unbindPort( bool isBorder, int portId, int index );
    virtual void initialize( const UMLRTMessage & msg );
    virtual void inject( const UMLRTMessage & msg );
};
extern const UMLRTCapsuleClass A;

#endif

