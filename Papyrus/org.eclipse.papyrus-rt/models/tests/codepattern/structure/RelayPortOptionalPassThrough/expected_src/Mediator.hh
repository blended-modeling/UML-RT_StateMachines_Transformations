
#ifndef MEDIATOR_HH
#define MEDIATOR_HH

#include "Protocol1.hh"
#include "umlrtcapsule.hh"
#include "umlrtcapsuleclass.hh"
#include "umlrtmessage.hh"
struct UMLRTCommsPort;
struct UMLRTSlot;

class Capsule_Mediator : public UMLRTCapsule
{
public:
    Capsule_Mediator( const UMLRTCapsuleClass * cd, UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat );
protected:
    Protocol1::Conj m_inp;
public:
    enum BorderPortId
    {
        borderport_m_inp,
        borderport_m_out
    };
protected:
    Protocol1::Base m_out;
public:
    enum PartId
    {
    };
    enum PortId
    {
        port_m_inp,
        port_m_out
    };
    virtual void bindPort( bool isBorder, int portId, int index );
    virtual void unbindPort( bool isBorder, int portId, int index );
    virtual void initialize( const UMLRTMessage & msg );
    virtual void inject( const UMLRTMessage & msg );
};
extern const UMLRTCapsuleClass Mediator;

#endif

