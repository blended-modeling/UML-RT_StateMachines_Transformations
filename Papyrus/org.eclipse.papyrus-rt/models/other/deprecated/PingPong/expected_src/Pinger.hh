
#ifndef PINGER_HH
#define PINGER_HH

#include "PingPongProtocol.hh"
#include "umlrtcapsule.hh"
#include "umlrtcapsuleclass.hh"
#include "umlrtmessage.hh"
#include "umlrttimerprotocol.hh"
struct UMLRTCommsPort;
struct UMLRTSlot;

#include <iostream>

class Capsule_Pinger : public UMLRTCapsule
{
public:
    Capsule_Pinger( const UMLRTCapsuleClass * cd, UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat );
protected:
    PingPongProtocol::Base PingPort;
public:
    enum BorderPortId
    {
        borderport_PingPort,
        borderport_timerPort
    };
protected:
    UMLRTTimerProtocol_baserole timerPort;
public:
    enum PartId
    {
    };
    enum PortId
    {
        port_PingPort,
        port_timerPort
    };
    virtual void bindPort( bool isBorder, int portId, int index );
    virtual void unbindPort( bool isBorder, int portId, int index );
    virtual void inject( const UMLRTMessage & message );
    virtual void initialize( const UMLRTMessage & message );
    const char * getCurrentStateString() const;
private:
    enum State
    {
        top__Running,
        SPECIAL_INTERNAL_STATE_UNVISITED
    };
    const char * stateNames[2];
    State currentState;
    void entryaction_____PingPong__Pinger__Pinger_SM__Region1__Running__onEntry( const UMLRTMessage * msg );
    void transitionaction_____PingPong__Pinger__Pinger_SM__Region1__initial__onInit( const UMLRTMessage * msg );
    void transitionaction_____PingPong__Pinger__Pinger_SM__Region1__onPong__onPong( const UMLRTMessage * msg );
    void transitionaction_____PingPong__Pinger__Pinger_SM__Region1__onTimeout__onTimeout( const UMLRTMessage * msg );
    void actionchain_____action_____PingPong__Pinger__Pinger_SM__Region1__initial( const UMLRTMessage * msg );
    void actionchain_____action_____PingPong__Pinger__Pinger_SM__Region1__onPong( const UMLRTMessage * msg );
    void actionchain_____action_____PingPong__Pinger__Pinger_SM__Region1__onTimeout( const UMLRTMessage * msg );
    State state_____top__Running( const UMLRTMessage * msg );
};
extern const UMLRTCapsuleClass Pinger;

#endif

