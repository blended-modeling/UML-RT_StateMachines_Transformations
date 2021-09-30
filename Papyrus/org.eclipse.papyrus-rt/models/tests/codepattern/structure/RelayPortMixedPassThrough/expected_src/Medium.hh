
#ifndef MEDIUM_HH
#define MEDIUM_HH

#include "Protocol1.hh"
#include "umlrtcapsule.hh"
#include "umlrtcapsuleclass.hh"
#include "umlrtmessage.hh"
struct UMLRTCapsulePart;
struct UMLRTCommsPort;
struct UMLRTSlot;

class Capsule_Medium : public UMLRTCapsule
{
public:
    Capsule_Medium( const UMLRTCapsuleClass * cd, UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat );
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
        part_eavesdropper
    };
protected:
    const UMLRTCapsulePart * const eavesdropper;
public:
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
extern const UMLRTCapsuleClass Medium;

#endif

