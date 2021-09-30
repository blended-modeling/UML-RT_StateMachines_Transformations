
#ifndef PINGER_HH
#define PINGER_HH

#include "PingPongProtocol.hh"
#include "umlrtcapsule.hh"
#include "umlrtcapsuleclass.hh"
#include "umlrtinmessage.hh"
#include "umlrttimerprotocol.hh"
struct UMLRTCommsPort;
struct UMLRTSlot;

#include "DataType1.hh"

class Capsule_Pinger : public UMLRTCapsule
{
public:
    Capsule_Pinger( UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * internal, bool isStat );
    enum BorderPortId
    {
        borderport_PingPort,
        borderport_timerPort
    };
protected:
    PingPongProtocol_baserole PingPort() const;
    UMLRTTimerProtocol_baserole timerPort() const;
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
    virtual void inject( const UMLRTInMessage & msg );
    virtual void initialize( const UMLRTInMessage & msg );
private:
    enum State
    {
        top__Running,
        SPECIAL_INTERNAL_STATE_UNVISITED
    };
    State currentState;
    void entryaction_____top__initialise__ActionChain3__onEntry( const UMLRTInMessage & msg );
    void transitionaction_____top__initialise__ActionChain3__onInit( const UMLRTInMessage & msg );
    void transitionaction_____top__onPong__ActionChain5__onPong( const UMLRTInMessage & msg );
    void transitionaction_____top__onTimeout__ActionChain7__onTimeout( const UMLRTInMessage & msg );
    void actionchain_____top__initialise__ActionChain3( const UMLRTInMessage & msg );
    void actionchain_____top__onPong__ActionChain5( const UMLRTInMessage & msg );
    void actionchain_____top__onTimeout__ActionChain7( const UMLRTInMessage & msg );
    State state_____top__Running( const UMLRTInMessage & msg );

    int timeoutCount;
};
extern const UMLRTCapsuleClass Pinger;

#endif

