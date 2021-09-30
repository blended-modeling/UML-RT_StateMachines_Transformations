
#ifndef PONGER_HH
#define PONGER_HH

#include "PingPongProtocol.hh"
#include "umlrtcapsule.hh"
#include "umlrtcapsuleclass.hh"
#include "umlrtmessage.hh"
struct UMLRTCommsPort;
struct UMLRTSlot;

#include <iostream>

class Capsule_Ponger : public UMLRTCapsule
{
public:
    Capsule_Ponger( const UMLRTCapsuleClass * cd, UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat );
protected:
    PingPongProtocol::Conj pong;
public:
    enum BorderPortId
    {
        borderport_pong
    };
    enum PartId
    {
    };
    enum PortId
    {
        port_pong
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
    void transitionaction_____PingPong_data__Ponger__Ponger_SM__Region1__initial__onInit( const UMLRTMessage * msg );
    void transitionaction_____PingPong_data__Ponger__Ponger_SM__Region1__onBound__onBound( const UMLRTMessage * msg );
    void transitionaction_____PingPong_data__Ponger__Ponger_SM__Region1__onPing__onPing( const UMLRTMessage * msg );
    void actionchain_____action_____PingPong_data__Ponger__Ponger_SM__Region1__initial( const UMLRTMessage * msg );
    void actionchain_____action_____PingPong_data__Ponger__Ponger_SM__Region1__onBound( const UMLRTMessage * msg );
    void actionchain_____action_____PingPong_data__Ponger__Ponger_SM__Region1__onPing( const UMLRTMessage * msg );
    State state_____top__Running( const UMLRTMessage * msg );
};
extern const UMLRTCapsuleClass Ponger;

#endif

