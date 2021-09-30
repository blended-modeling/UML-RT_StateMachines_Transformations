
#ifndef TOP_HH
#define TOP_HH

#include "P.hh"
#include "R.hh"
#include "umlrtcapsule.hh"
#include "umlrtcapsuleclass.hh"
#include "umlrtframeprotocol.hh"
#include "umlrtlogprotocol.hh"
#include "umlrtmessage.hh"
struct UMLRTCapsulePart;
struct UMLRTCommsPort;
struct UMLRTSlot;

#include "UtilityMacros.hh"

class Capsule_Top : public UMLRTCapsule
{
public:
    Capsule_Top( const UMLRTCapsuleClass * cd, UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat );
    virtual void inject( const UMLRTMessage & message );
    virtual void initialize( const UMLRTMessage & message );
    const char * getCurrentStateString() const;
private:
    enum State
    {
        State1,
        SPECIAL_INTERNAL_STATE_TOP,
        SPECIAL_INTERNAL_STATE_UNVISITED
    };
    const char * stateNames[3];
    State currentState;
    void update_state( State newState );
    void entryaction_____State1( const UMLRTMessage * msg );
    void actionchain_____Initial( const UMLRTMessage * msg );
    State state_____State1( const UMLRTMessage * msg );
protected:
    UMLRTFrameProtocol_baserole frame;
public:
    enum InternalPortId
    {
        internalport_frame,
        internalport_p,
        internalport_r,
        internalport_log
    };
protected:
    UMLRTLogProtocol_baserole log;
    P::Conj p;
    R::Conj r;
public:
    enum PartId
    {
        part_a
    };
protected:
    const UMLRTCapsulePart * const a;
public:
    enum PortId
    {
        port_frame,
        port_log,
        port_p,
        port_r
    };
    virtual void bindPort( bool isBorder, int portId, int index );
    virtual void unbindPort( bool isBorder, int portId, int index );
};
extern const UMLRTCapsuleClass Top;

#endif

