
#ifndef PINGER_HH
#define PINGER_HH

#include "PingPongProtocol.hh"
#include "umlrtcapsule.hh"
#include "umlrtcapsuleclass.hh"
#include "umlrtlogprotocol.hh"
#include "umlrtmessage.hh"
struct UMLRTCommsPort;
struct UMLRTSlot;

class Capsule_Pinger : public UMLRTCapsule
{
public:
    Capsule_Pinger( const UMLRTCapsuleClass * cd, UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat );
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
        SPECIAL_INTERNAL_STATE_TOP,
        SPECIAL_INTERNAL_STATE_UNVISITED
    };
    const char * stateNames[3];
    State currentState;
    void update_state( State newState );
    void entryaction_____Playing( const UMLRTMessage * msg );
    void transitionaction_____onPong( const UMLRTMessage * msg );
    void actionchain_____Initial( const UMLRTMessage * msg );
    void actionchain_____onPong( const UMLRTMessage * msg );
    State state_____Playing( const UMLRTMessage * msg );
};
extern const UMLRTCapsuleClass Pinger;

#endif

