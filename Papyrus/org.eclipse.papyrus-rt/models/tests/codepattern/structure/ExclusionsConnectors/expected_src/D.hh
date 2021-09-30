
#ifndef D_HH
#define D_HH

#include "Protocol1.hh"
#include "umlrtcapsule.hh"
#include "umlrtcapsuleclass.hh"
#include "umlrtmessage.hh"
struct UMLRTCommsPort;
struct UMLRTSlot;

class Capsule_D : public UMLRTCapsule
{
public:
    Capsule_D( const UMLRTCapsuleClass * cd, UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat );
protected:
    Protocol1::Conj p;
public:
    enum BorderPortId
    {
        borderport_p,
        borderport_q
    };
protected:
    Protocol1::Conj q;
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
extern const UMLRTCapsuleClass D;

#endif

