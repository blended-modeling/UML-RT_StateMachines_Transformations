
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
    PingPongProtocol::Base ping;
public:
    enum BorderPortId
    {
        borderport_ping,
        borderport_timer
    };
protected:
    UMLRTTimerProtocol_baserole timer;
public:
    enum PartId
    {
    };
    enum PortId
    {
        port_ping,
        port_timer
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
    void transitionaction_____PingPong_data__Pinger__Pinger_SM__Region1__initialise__onInit( const UMLRTMessage * msg );
    void transitionaction_____PingPong_data__Pinger__Pinger_SM__Region1__onPong__onPong( const UMLRTMessage * msg );
    void transitionaction_____PingPong_data__Pinger__Pinger_SM__Region1__onTimeout__onTimeout( const UMLRTMessage * msg );
    void actionchain_____action_____PingPong_data__Pinger__Pinger_SM__Region1__initialise( const UMLRTMessage * msg );
    void actionchain_____action_____PingPong_data__Pinger__Pinger_SM__Region1__onPong( const UMLRTMessage * msg );
    void actionchain_____action_____PingPong_data__Pinger__Pinger_SM__Region1__onTimeout( const UMLRTMessage * msg );
    State state_____top__Running( const UMLRTMessage * msg );
};
extern const UMLRTCapsuleClass Pinger;

#endif

