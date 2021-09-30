
#ifndef B_HH
#define B_HH

#include "A.hh"
#include "Control.hh"
#include "P1.hh"
#include "P2.hh"
#include "umlrtcapsuleclass.hh"
#include "umlrtlogprotocol.hh"
#include "umlrtmessage.hh"
struct UMLRTCommsPort;
struct UMLRTSlot;

class Capsule_B : public Capsule_A
{
public:
    Capsule_B( const UMLRTCapsuleClass * cd, UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat );
protected:
    Control::Base control;
public:
    enum BorderPortId
    {
        borderport_control,
        borderport_p1,
        borderport_p2
    };
protected:
    UMLRTLogProtocol_baserole log;
    P1::Base p1;
    P2::Base p2;
public:
    enum PartId
    {
    };
    enum PortId
    {
        port_control,
        port_log,
        port_p1,
        port_p2
    };
    enum InternalPortId
    {
        internalport_log
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
        SPECIAL_INTERNAL_STATE_TOP,
        SPECIAL_INTERNAL_STATE_UNVISITED
    };
    const char * stateNames[3];
    State currentState;
    void update_state( State newState );
    void entryaction_____State1( const UMLRTMessage * msg );
    void actionchain_____Initial( const UMLRTMessage * msg );
    State state_____State1( const UMLRTMessage * msg );
};
extern const UMLRTCapsuleClass B;

#endif

