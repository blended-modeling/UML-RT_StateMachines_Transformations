
#ifndef SENDER_HH
#define SENDER_HH

#include "Protocol1.hh"
#include "Start.hh"
#include "umlrtcapsule.hh"
#include "umlrtcapsuleclass.hh"
#include "umlrtmessage.hh"
struct UMLRTCommsPort;
struct UMLRTSlot;

#include <iostream>
using namespace std;

class Capsule_Sender : public UMLRTCapsule
{
public:
    Capsule_Sender( const UMLRTCapsuleClass * cd, UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat );
protected:
    Protocol1::Base s_out;
public:
    enum BorderPortId
    {
        borderport_s_out,
        borderport_start
    };
protected:
    Start::Conj start;
public:
    enum PartId
    {
    };
    enum PortId
    {
        port_s_out,
        port_start
    };
    virtual void bindPort( bool isBorder, int portId, int index );
    virtual void unbindPort( bool isBorder, int portId, int index );
    virtual void inject( const UMLRTMessage & message );
    virtual void initialize( const UMLRTMessage & message );
    const char * getCurrentStateString() const;
private:
    enum State
    {
        top__MessageSent,
        top__WaitingToStart,
        SPECIAL_INTERNAL_STATE_UNVISITED
    };
    const char * stateNames[3];
    State currentState;
    void entryaction_____RootElement__Sender__StateMachine__Region__MessageSent__OpaqueBehavior( const UMLRTMessage * msg );
    void entryaction_____RootElement__Sender__StateMachine__Region__WaitingToStart__OpaqueBehavior( const UMLRTMessage * msg );
    void transitionaction_____RootElement__Sender__StateMachine__Region__t1__OpaqueBehavior0( const UMLRTMessage * msg );
    void actionchain_____action_____RootElement__Sender__StateMachine__Region__t0( const UMLRTMessage * msg );
    void actionchain_____action_____RootElement__Sender__StateMachine__Region__t1( const UMLRTMessage * msg );
    State state_____top__MessageSent( const UMLRTMessage * msg );
    State state_____top__WaitingToStart( const UMLRTMessage * msg );
};
extern const UMLRTCapsuleClass Sender;

#endif

