
#ifndef PONGER_HH
#define PONGER_HH

#include "PingPongProtocol.hh"
#include "umlrtcapsule.hh"
#include "umlrtcapsuleclass.hh"
#include "umlrtlogprotocol.hh"
#include "umlrtmessage.hh"
struct UMLRTCommsPort;
struct UMLRTSlot;

class Capsule_Ponger : public UMLRTCapsule
{
public:
    Capsule_Ponger( const UMLRTCapsuleClass * cd, UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat );
protected:
    UMLRTLogProtocol_baserole log;
    PingPongProtocol::Conj pongPort;
public:
    enum BorderPortId
    {
        borderport_pongPort
    };
    enum PartId
    {
    };
    enum PortId
    {
        port_log,
        port_pongPort
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
        SPECIAL_INTERNAL_STATE_TOP,
        SPECIAL_INTERNAL_STATE_UNVISITED
    };
    const char * stateNames[3];
    State currentState;
    void update_state( State newState );
    void entryaction_____Playing( const UMLRTMessage * msg );
    void transitionaction_____onPing( const UMLRTMessage * msg );
    void actionchain_____Initial( const UMLRTMessage * msg );
    void actionchain_____onPing( const UMLRTMessage * msg );
    State state_____Playing( const UMLRTMessage * msg );
};
extern const UMLRTCapsuleClass Ponger;

#endif

