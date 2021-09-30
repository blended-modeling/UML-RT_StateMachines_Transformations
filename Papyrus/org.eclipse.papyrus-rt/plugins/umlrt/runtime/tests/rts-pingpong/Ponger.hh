
#ifndef PONGER_HH
#define PONGER_HH

#include "PingPongProtocol.hh"
#include "umlrtcapsule.hh"
#include "umlrtcapsuleclass.hh"

struct UMLRTCommsPort;
struct UMLRTSlot;
class UMLRTMessage;

class Capsule_Ponger : public UMLRTCapsule
{
public:
    Capsule_Ponger( const UMLRTCapsuleClass * cd, UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat );
protected:
    PingPongProtocol::Conj PongPort;
public:
    enum BorderPortId
    {
        borderport_PongPort
    };
    enum PartId
    {
    };
    enum PortId
    {
        port_PongPort
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
    int messageLimit;
    void transitionaction_____top__initial__ActionChain3__onInit( const UMLRTMessage & msg );
    void transitionaction_____top__onPing__ActionChain5__onPing( const UMLRTMessage & msg );
    void actionchain_____top__initial__ActionChain3( const UMLRTMessage & msg );
    void actionchain_____top__onPing__ActionChain5( const UMLRTMessage & msg );
    State state_____top__Running( const UMLRTMessage & msg );
};
extern const UMLRTCapsuleClass Ponger;

#endif

