
#ifndef RECEIVER_HH
#define RECEIVER_HH

#include "Protocol1.hh"
#include "umlrtcapsule.hh"
#include "umlrtcapsuleclass.hh"
#include "umlrtmessage.hh"
struct UMLRTCommsPort;
struct UMLRTSlot;

#include <iostream>
using namespace std;

class Capsule_Receiver : public UMLRTCapsule
{
public:
    Capsule_Receiver( const UMLRTCapsuleClass * cd, UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat );
protected:
    Protocol1::Conj r_inp;
public:
    enum BorderPortId
    {
        borderport_r_inp
    };
    enum PartId
    {
    };
    enum PortId
    {
        port_r_inp
    };
    virtual void bindPort( bool isBorder, int portId, int index );
    virtual void unbindPort( bool isBorder, int portId, int index );
    virtual void inject( const UMLRTMessage & message );
    virtual void initialize( const UMLRTMessage & message );
    const char * getCurrentStateString() const;
private:
    enum State
    {
        top__MessageReceived,
        top__Listening,
        SPECIAL_INTERNAL_STATE_UNVISITED
    };
    const char * stateNames[3];
    State currentState;
    void entryaction_____RootElement__Receiver__StateMachine__Region__Listening__OpaqueBehavior( const UMLRTMessage * msg );
    void entryaction_____RootElement__Receiver__StateMachine__Region__MessageReceived__OpaqueBehavior( const UMLRTMessage * msg );
    void actionchain_____action_____RootElement__Receiver__StateMachine__Region__t0( const UMLRTMessage * msg );
    void actionchain_____action_____RootElement__Receiver__StateMachine__Region__t1( const UMLRTMessage * msg );
    State state_____top__Listening( const UMLRTMessage * msg );
    State state_____top__MessageReceived( const UMLRTMessage * msg );
};
extern const UMLRTCapsuleClass Receiver;

#endif

