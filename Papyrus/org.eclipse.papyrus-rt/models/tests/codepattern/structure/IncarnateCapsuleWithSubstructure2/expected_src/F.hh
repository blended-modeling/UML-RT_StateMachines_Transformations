
#ifndef F_HH
#define F_HH

#include "Protocol3.hh"
#include "umlrtcapsule.hh"
#include "umlrtcapsuleclass.hh"
#include "umlrtmessage.hh"
struct UMLRTCommsPort;
struct UMLRTSlot;

class Capsule_F : public UMLRTCapsule
{
public:
    Capsule_F( const UMLRTCapsuleClass * cd, UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat );
    enum BorderPortId
    {
        borderport_protocol3
    };
protected:
    Protocol3::Conj protocol3;
public:
    enum PartId
    {
    };
    enum PortId
    {
        port_protocol3
    };
    virtual void bindPort( bool isBorder, int portId, int index );
    virtual void unbindPort( bool isBorder, int portId, int index );
    virtual void initialize( const UMLRTMessage & msg );
    virtual void inject( const UMLRTMessage & msg );
};
extern const UMLRTCapsuleClass F;

#endif

