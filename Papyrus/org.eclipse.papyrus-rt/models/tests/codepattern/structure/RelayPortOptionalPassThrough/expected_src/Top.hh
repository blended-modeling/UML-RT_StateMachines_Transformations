
#ifndef TOP_HH
#define TOP_HH

#include "Start.hh"
#include "umlrtcapsule.hh"
#include "umlrtcapsuleclass.hh"
#include "umlrtcapsuleid.hh"
#include "umlrtframeprotocol.hh"
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
        Started,
        SPECIAL_INTERNAL_STATE_TOP,
        SPECIAL_INTERNAL_STATE_UNVISITED
    };
    const char * stateNames[3];
    State currentState;
    void update_state( State newState );
    void entryaction_____Started( const UMLRTMessage * msg );
    void transitionaction_____t0( const UMLRTMessage * msg );
    void actionchain_____t0( const UMLRTMessage * msg );
    State state_____Started( const UMLRTMessage * msg );
protected:
    UMLRTFrameProtocol_baserole frame;
public:
    enum BorderPortId
    {
        borderport_frame
    };
protected:
    Start::Base start;
public:
    enum InternalPortId
    {
        internalport_start
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
        port_start
    };
    virtual void bindPort( bool isBorder, int portId, int index );
    virtual void unbindPort( bool isBorder, int portId, int index );
protected:
    UMLRTCapsuleId mediatorId;
};
extern const UMLRTCapsuleClass Top;

#endif

