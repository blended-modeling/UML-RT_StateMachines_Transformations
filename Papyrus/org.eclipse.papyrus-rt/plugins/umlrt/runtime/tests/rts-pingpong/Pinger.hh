
#ifndef PINGER_HH
#define PINGER_HH

#include "PingPongProtocol.hh"
#include "umlrtcapsule.hh"
#include "umlrtcapsuleclass.hh"
#include "umlrttimerprotocol.hh"
struct UMLRTCommsPort;
struct UMLRTSlot;

#include "DataType1.hh"

class Capsule_Pinger : public UMLRTCapsule
{
public:
    Capsule_Pinger( const UMLRTCapsuleClass * cd, UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat );
    enum BorderPortId
    {
        borderport_PingPort
    };
    enum InternalPortId
    {
        internalport_timerPort
    };
protected:
    PingPongProtocol::Base PingPort;
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
    const char * stateNames[1];
    State currentState;
    void entryaction_____top__initialise__ActionChain3__onEntry( const UMLRTMessage & msg );
    void transitionaction_____top__initialise__ActionChain3__onInit( const UMLRTMessage & msg );
    void transitionaction_____top__onPong__ActionChain5__onPong( const UMLRTMessage & msg );
    void transitionaction_____top__onTimeout__ActionChain7__onTimeout( const UMLRTMessage & msg );
    void actionchain_____top__initialise__ActionChain3( const UMLRTMessage & msg );
    void actionchain_____top__onPong__ActionChain5( const UMLRTMessage & msg );
    void actionchain_____top__onTimeout__ActionChain7( const UMLRTMessage & msg );
    State state_____top__Running( const UMLRTMessage & msg );

    int timeoutCount;
};
extern const UMLRTCapsuleClass Pinger;

#endif

