
#ifndef C_HH
#define C_HH

#include "Control.hh"
#include "P1.hh"
#include "P2.hh"
#include "umlrtcapsule.hh"
#include "umlrtcapsuleclass.hh"
#include "umlrtmessage.hh"
struct UMLRTCommsPort;
struct UMLRTSlot;

class Capsule_C : public UMLRTCapsule
{
public:
    Capsule_C( const UMLRTCapsuleClass * cd, UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat );
    enum BorderPortId
    {
        borderport_control,
        borderport_p1,
        borderport_p2
    };
protected:
    Control::Base control;
    P1::Conj p1;
    P2::Conj p2;
public:
    enum PartId
    {
    };
    enum PortId
    {
        port_control,
        port_p1,
        port_p2
    };
    virtual void bindPort( bool isBorder, int portId, int index );
    virtual void unbindPort( bool isBorder, int portId, int index );
    virtual void initialize( const UMLRTMessage & msg );
    virtual void inject( const UMLRTMessage & msg );
};
extern const UMLRTCapsuleClass C;

#endif

