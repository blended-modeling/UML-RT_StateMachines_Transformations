
#ifndef MEDIATOR_HH
#define MEDIATOR_HH

#include "Protocol1.hh"
#include "umlrtcapsule.hh"
#include "umlrtcapsuleclass.hh"
#include "umlrtmessage.hh"
struct UMLRTCommsPort;
struct UMLRTSlot;

#include <iostream>

using namespace std;

class Capsule_Mediator : public UMLRTCapsule
{
public:
    Capsule_Mediator( const UMLRTCapsuleClass * cd, UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat );
protected:
    Protocol1::Conj minp;
public:
    enum BorderPortId
    {
        borderport_minp,
        borderport_mout
    };
protected:
    Protocol1::Base mout;
public:
    enum PartId
    {
    };
    enum PortId
    {
        port_minp,
        port_mout
    };
    virtual void bindPort( bool isBorder, int portId, int index );
    virtual void unbindPort( bool isBorder, int portId, int index );
    virtual void initialize( const UMLRTMessage & msg );
    virtual void inject( const UMLRTMessage & msg );
};
extern const UMLRTCapsuleClass Mediator;

#endif

