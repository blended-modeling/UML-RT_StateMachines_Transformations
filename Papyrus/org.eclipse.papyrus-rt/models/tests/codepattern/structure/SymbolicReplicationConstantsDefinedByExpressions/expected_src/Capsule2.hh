
#ifndef CAPSULE2_HH
#define CAPSULE2_HH

#include "Protocol1.hh"
#include "umlrtcapsule.hh"
#include "umlrtcapsuleclass.hh"
#include "umlrtmessage.hh"
struct UMLRTCommsPort;
struct UMLRTSlot;

class Capsule_Capsule2 : public UMLRTCapsule
{
public:
    Capsule_Capsule2( const UMLRTCapsuleClass * cd, UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat );
protected:
    Protocol1::Conj protocol1;
public:
    enum BorderPortId
    {
        borderport_protocol1
    };
    enum PartId
    {
    };
    enum PortId
    {
        port_protocol1
    };
    virtual void bindPort( bool isBorder, int portId, int index );
    virtual void unbindPort( bool isBorder, int portId, int index );
    int M;
    int L;
    virtual void initialize( const UMLRTMessage & msg );
    virtual void inject( const UMLRTMessage & msg );
};
extern const UMLRTCapsuleClass Capsule2;

#endif

