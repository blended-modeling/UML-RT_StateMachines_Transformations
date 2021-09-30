
#ifndef PONGER_HH
#define PONGER_HH

#include "PingPongProtocol.hh"
#include "umlrtcapsule.hh"
#include "umlrtcapsuleclass.hh"
#include "umlrtinmessage.hh"
struct UMLRTCommsPort;
struct UMLRTSlot;

class Capsule_Ponger : public UMLRTCapsule
{
public:
    Capsule_Ponger( UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * internal, bool isStat );
    enum BorderPortId
    {
        borderport_PongPort,
        borderport__bind
    };
protected:
    PingPongProtocol_conjrole PongPort() const;
public:
    enum PartId
    {
    };
    enum PortId
    {
        port_PongPort,
        port__bind
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
    void transitionaction_____top__initial__ActionChain3__onInit( const UMLRTInMessage & msg );
    void transitionaction_____top__onBound__ActionChain6__onBound( const UMLRTInMessage & msg );
    void transitionaction_____top__onPing__ActionChain5__onPing( const UMLRTInMessage & msg );
    void actionchain_____top__initial__ActionChain3( const UMLRTInMessage & msg );
    void actionchain_____top__onBound__ActionChain6( const UMLRTInMessage & msg );
    void actionchain_____top__onPing__ActionChain5( const UMLRTInMessage & msg );
    State state_____top__Running( const UMLRTInMessage & msg );
};
extern const UMLRTCapsuleClass Ponger;

#endif

