
#ifndef E_HH
#define E_HH

#include "P.hh"
#include "umlrtcapsule.hh"
#include "umlrtcapsuleclass.hh"
#include "umlrtmessage.hh"
struct UMLRTCapsulePart;
struct UMLRTCommsPort;
struct UMLRTSlot;

class Capsule_E : public UMLRTCapsule
{
public:
    Capsule_E( const UMLRTCapsuleClass * cd, UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat );
    enum BorderPortId
    {
        borderport_p
    };
protected:
    P::Base p;
public:
    enum PartId
    {
        part_f
    };
protected:
    const UMLRTCapsulePart * const f;
public:
    enum PortId
    {
        port_p
    };
    virtual void bindPort( bool isBorder, int portId, int index );
    virtual void unbindPort( bool isBorder, int portId, int index );
    virtual void initialize( const UMLRTMessage & msg );
    virtual void inject( const UMLRTMessage & msg );
};
extern const UMLRTCapsuleClass E;

#endif

