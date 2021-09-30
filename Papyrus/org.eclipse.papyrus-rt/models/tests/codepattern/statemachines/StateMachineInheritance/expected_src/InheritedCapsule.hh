
#ifndef INHERITEDCAPSULE_HH
#define INHERITEDCAPSULE_HH

#include "PingPongProtocol.hh"
#include "Pinger.hh"
#include "umlrtcapsuleclass.hh"
#include "umlrtlogprotocol.hh"
#include "umlrtmessage.hh"
struct UMLRTCommsPort;
struct UMLRTSlot;

class Capsule_InheritedCapsule : public Capsule_Pinger
{
public:
    Capsule_InheritedCapsule( const UMLRTCapsuleClass * cd, UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat );
protected:
    UMLRTLogProtocol_baserole log;
    PingPongProtocol::Base pingPort;
public:
    enum BorderPortId
    {
        borderport_pingPort
    };
    enum PartId
    {
    };
    enum PortId
    {
        port_log,
        port_pingPort
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
        Playing,
        State1,
        SPECIAL_INTERNAL_STATE_TOP,
        SPECIAL_INTERNAL_STATE_UNVISITED
    };
    const char * stateNames[4];
    State currentState;
    void update_state( State newState );
    void entryaction_____Playing( const UMLRTMessage * msg );
    void transitionaction_____transition1( const UMLRTMessage * msg );
    bool guard_____onPong( const UMLRTMessage * msg );
    bool guard_____transition1( const UMLRTMessage * msg );
    void actionchain_____Initial( const UMLRTMessage * msg );
    void actionchain_____onPong( const UMLRTMessage * msg );
    void actionchain_____transition1( const UMLRTMessage * msg );
    State state_____Playing( const UMLRTMessage * msg );
    State state_____State1( const UMLRTMessage * msg );
};
extern const UMLRTCapsuleClass InheritedCapsule;

#endif

