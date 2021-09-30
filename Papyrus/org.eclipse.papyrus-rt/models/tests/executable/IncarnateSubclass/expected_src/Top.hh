
#ifndef TOP_HH
#define TOP_HH

#include "Control.hh"
#include "umlrtcapsule.hh"
#include "umlrtcapsuleclass.hh"
#include "umlrtframeprotocol.hh"
#include "umlrtlogprotocol.hh"
#include "umlrtmessage.hh"
struct UMLRTCapsulePart;
struct UMLRTCommsPort;
struct UMLRTSlot;

#include "B.hh"

class Capsule_Top : public UMLRTCapsule
{
public:
    Capsule_Top( const UMLRTCapsuleClass * cd, UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat );
protected:
    Control::Conj control;
public:
    enum InternalPortId
    {
        internalport_control,
        internalport_frame,
        internalport_log
    };
protected:
    UMLRTFrameProtocol_baserole frame;
    UMLRTLogProtocol_baserole log;
public:
    enum PartId
    {
        part_a,
        part_c
    };
protected:
    const UMLRTCapsulePart * const a;
    const UMLRTCapsulePart * const c;
public:
    enum PortId
    {
        port_control,
        port_frame,
        port_log
    };
    virtual void bindPort( bool isBorder, int portId, int index );
    virtual void unbindPort( bool isBorder, int portId, int index );
    virtual void inject( const UMLRTMessage & message );
    virtual void initialize( const UMLRTMessage & message );
    const char * getCurrentStateString() const;
private:
    enum State
    {
        State1,
        State2,
        SPECIAL_INTERNAL_STATE_TOP,
        SPECIAL_INTERNAL_STATE_UNVISITED
    };
    const char * stateNames[4];
    State currentState;
    void update_state( State newState );
    void entryaction_____State1( const UMLRTMessage * msg );
    void entryaction_____State2( const UMLRTMessage * msg );
    void actionchain_____Initial( const UMLRTMessage * msg );
    void actionchain_____transition1( const UMLRTMessage * msg );
    State state_____State1( const UMLRTMessage * msg );
    State state_____State2( const UMLRTMessage * msg );
};
extern const UMLRTCapsuleClass Top;

#endif

