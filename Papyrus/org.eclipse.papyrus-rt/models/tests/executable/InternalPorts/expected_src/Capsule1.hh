
#ifndef CAPSULE1_HH
#define CAPSULE1_HH

#include "Protocol1.hh"
#include "umlrtcapsule.hh"
#include "umlrtcapsuleclass.hh"
#include "umlrtlogprotocol.hh"
#include "umlrtmessage.hh"
#include "umlrttimerprotocol.hh"
struct UMLRTCommsPort;
struct UMLRTSlot;

class Capsule_Capsule1 : public UMLRTCapsule
{
public:
    Capsule_Capsule1( const UMLRTCapsuleClass * cd, UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat );
    enum BorderPortId
    {
        borderport_protocol1
    };
    enum InternalPortId
    {
        internalport_log,
        internalport_timing
    };
protected:
    UMLRTLogProtocol_baserole log;
    Protocol1::Conj protocol1;
    UMLRTTimerProtocol_baserole timing;
public:
    enum PartId
    {
    };
    enum PortId
    {
        port_log,
        port_protocol1,
        port_timing
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
        State3,
        SPECIAL_INTERNAL_STATE_TOP,
        SPECIAL_INTERNAL_STATE_UNVISITED
    };
    const char * stateNames[5];
    State currentState;
    void update_state( State newState );
    void entryaction_____State1( const UMLRTMessage * msg );
    void entryaction_____State2( const UMLRTMessage * msg );
    void entryaction_____State3( const UMLRTMessage * msg );
    void actionchain_____Initial( const UMLRTMessage * msg );
    void actionchain_____start( const UMLRTMessage * msg );
    void actionchain_____timeout( const UMLRTMessage * msg );
    State state_____State1( const UMLRTMessage * msg );
    State state_____State2( const UMLRTMessage * msg );
    State state_____State3( const UMLRTMessage * msg );
};
extern const UMLRTCapsuleClass Capsule1;

#endif

