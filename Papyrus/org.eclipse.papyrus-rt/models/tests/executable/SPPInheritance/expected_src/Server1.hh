
#ifndef SERVER1_HH
#define SERVER1_HH

#include "Server.hh"
#include "Service.hh"
#include "umlrtcapsuleclass.hh"
#include "umlrtlogprotocol.hh"
#include "umlrtmessage.hh"
#include "umlrttimerprotocol.hh"
struct UMLRTCommsPort;
struct UMLRTSlot;

#include "UtilityMacros.hh"

class Capsule_Server1 : public Capsule_Server
{
public:
    Capsule_Server1( const UMLRTCapsuleClass * cd, UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat );
protected:
    UMLRTLogProtocol_baserole log;
    Service::Base service;
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
    virtual void inject( const UMLRTMessage & message );
    virtual void initialize( const UMLRTMessage & message );
    const char * getCurrentStateString() const;
private:
    enum State
    {
        Responding,
        WaitingForRequests,
        SPECIAL_INTERNAL_STATE_TOP,
        SPECIAL_INTERNAL_STATE_UNVISITED
    };
    const char * stateNames[4];
    State currentState;
    void update_state( State newState );
    void entryaction_____Responding( const UMLRTMessage * msg );
    void entryaction_____WaitingForRequests( const UMLRTMessage * msg );
    void transitionaction_____implicitTransition1( const UMLRTMessage * msg );
    void transitionaction_____implicitTransition2( const UMLRTMessage * msg );
    void actionchain_____Initial( const UMLRTMessage * msg );
    void actionchain_____implicitTransition1( const UMLRTMessage * msg );
    void actionchain_____implicitTransition2( const UMLRTMessage * msg );
    State state_____Responding( const UMLRTMessage * msg );
    State state_____WaitingForRequests( const UMLRTMessage * msg );
};
extern const UMLRTCapsuleClass Server1;

#endif

