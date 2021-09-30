
#ifndef B_HH
#define B_HH

#include "A.hh"
#include "Protocol1.hh"
#include "umlrtcapsuleclass.hh"
#include "umlrtmessage.hh"
struct UMLRTCommsPort;
struct UMLRTSlot;

class Capsule_B : public Capsule_A
{
public:
    Capsule_B( const UMLRTCapsuleClass * cd, UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat );
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
        port_q,
        port_r
    };
    virtual void bindPort( bool isBorder, int portId, int index );
    virtual void unbindPort( bool isBorder, int portId, int index );
    virtual void initialize( const UMLRTMessage & msg );
    virtual void inject( const UMLRTMessage & msg );
};
extern const UMLRTCapsuleClass B;

#endif

