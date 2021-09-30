
#ifndef CLIENT_HH
#define CLIENT_HH

#include "Service.hh"
#include "umlrtcapsule.hh"
#include "umlrtcapsuleclass.hh"
#include "umlrtlogprotocol.hh"
#include "umlrtmessage.hh"
#include "umlrttimerid.hh"
#include "umlrttimerprotocol.hh"
struct UMLRTCommsPort;
struct UMLRTSlot;

#include "TimingUtil.hh"
#include "UtilityMacros.hh"

class Capsule_Client : public UMLRTCapsule
{
public:
    Capsule_Client( const UMLRTCapsuleClass * cd, UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat );
protected:
    UMLRTLogProtocol_baserole log;
    Service::Conj service;
public:
    enum InternalPortId
    {
        internalport_service,
        internalport_timing,
        internalport_log
    };
protected:
    UMLRTTimerProtocol_baserole timing;
public:
    enum PartId
    {
    };
    enum PortId
    {
        port_log,
        port_service,
        port_timing
    };
    virtual void bindPort( bool isBorder, int portId, int index );
    virtual void unbindPort( bool isBorder, int portId, int index );
    UMLRTTimerId timerId;
    virtual void inject( const UMLRTMessage & message );
    virtual void initialize( const UMLRTMessage & message );
    const char * getCurrentStateString() const;
private:
    enum State
    {
        Done,
        Ready,
        TimedOut,
        WaitingForServiceToBeReady,
        SPECIAL_INTERNAL_STATE_TOP,
        SPECIAL_INTERNAL_STATE_UNVISITED
    };
    const char * stateNames[6];
    State currentState;
    void update_state( State newState );
    void entryaction_____Done( const UMLRTMessage * msg );
    void entryaction_____Ready( const UMLRTMessage * msg );
    void entryaction_____TimedOut( const UMLRTMessage * msg );
    void entryaction_____WaitingForServiceToBeReady( const UMLRTMessage * msg );
    void transitionaction_____timeout1( const UMLRTMessage * msg );
    void transitionaction_____timeout2( const UMLRTMessage * msg );
    void transitionaction_____transition2( const UMLRTMessage * msg );
    void actionchain_____Initial( const UMLRTMessage * msg );
    void actionchain_____timeout1( const UMLRTMessage * msg );
    void actionchain_____timeout2( const UMLRTMessage * msg );
    void actionchain_____transition2( const UMLRTMessage * msg );
    void actionchain_____transition3( const UMLRTMessage * msg );
    State state_____Done( const UMLRTMessage * msg );
    State state_____Ready( const UMLRTMessage * msg );
    State state_____TimedOut( const UMLRTMessage * msg );
    State state_____WaitingForServiceToBeReady( const UMLRTMessage * msg );
};
extern const UMLRTCapsuleClass Client;

#endif

