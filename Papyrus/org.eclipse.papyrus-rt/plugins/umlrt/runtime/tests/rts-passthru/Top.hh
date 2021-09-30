
#ifndef TOP_HH
#define TOP_HH

#include "Start.hh"
#include "umlrtcapsule.hh"
#include "umlrtcapsuleid.hh"
#include "umlrtcapsuleclass.hh"
#include "umlrtframeprotocol.hh"
#include "umlrttimerprotocol.hh"
#include "umlrtmessage.hh"
struct UMLRTCapsulePart;
struct UMLRTCommsPort;
struct UMLRTSlot;

#include <iostream>
using namespace std;

class Capsule_Top : public UMLRTCapsule
{
public:
    Capsule_Top( const UMLRTCapsuleClass * cd, UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat );
    virtual void inject( const UMLRTMessage & message );
    virtual void initialize( const UMLRTMessage & message );
    const char * getCurrentStateString() const;
private:
    enum State
    {
        top__Started,
        SPECIAL_INTERNAL_STATE_UNVISITED
    };
    const char * stateNames[2];
    State currentState;
    void entryaction_____RootElement__Top__StateMachine__Region__Started__OpaqueBehavior( const UMLRTMessage * msg );
    void transitionaction_____RootElement__Top__StateMachine__Region__t0__OpaqueBehavior( const UMLRTMessage * msg );
    void actionchain_____action_____RootElement__Top__StateMachine__Region__t0( const UMLRTMessage * msg );
    void action_timeout_msg( const UMLRTMessage * msg );
    State state_____top__Started( const UMLRTMessage * msg );
    UMLRTCapsuleId mediatorId;
    int timeoutCount;
protected:
    UMLRTFrameProtocol_baserole frame;
    UMLRTTimerProtocol_baserole timer;
public:
    enum InternalPortId
    {
        internalport_frame,
        internalport_timer
    };
protected:
    Start::Base start;
public:
    enum BorderPortId
    {
        borderport_start
    };
    enum PartId
    {
        part_mediator,
        part_receiver,
        part_sender
    };
protected:
    const UMLRTCapsulePart * const mediator;
    const UMLRTCapsulePart * const receiver;
    const UMLRTCapsulePart * const sender;
public:
    enum PortId
    {
        port_frame,
        port_timer,
        port_start
    };
    virtual void bindPort( bool isBorder, int portId, int index );
    virtual void unbindPort( bool isBorder, int portId, int index );
};
extern const UMLRTCapsuleClass Top;

#endif

