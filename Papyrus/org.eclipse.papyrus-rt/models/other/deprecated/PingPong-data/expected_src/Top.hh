
#ifndef TOP_HH
#define TOP_HH

#include "umlrtcapsule.hh"
#include "umlrtcapsuleclass.hh"
#include "umlrtframeprotocol.hh"
#include "umlrtmessage.hh"
struct UMLRTCapsulePart;
struct UMLRTCommsPort;
struct UMLRTSlot;

#include <iostream>
#include <stdio.h>
#include "umlrtcontroller.hh"

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
        top__State1,
        SPECIAL_INTERNAL_STATE_UNVISITED
    };
    const char * stateNames[2];
    State currentState;
    void transitionaction_____PingPong_data__Top__StateMachine1__Region1__initial__onInit( const UMLRTMessage * msg );
    void actionchain_____action_____PingPong_data__Top__StateMachine1__Region1__initial( const UMLRTMessage * msg );
    State state_____top__State1( const UMLRTMessage * msg );
protected:
    UMLRTFrameProtocol_baserole frame;
public:
    enum BorderPortId
    {
        borderport_frame
    };
    enum PartId
    {
        part_pinger,
        part_ponger
    };
protected:
    const UMLRTCapsulePart * const pinger;
    const UMLRTCapsulePart * const ponger;
public:
    enum PortId
    {
        port_frame
    };
    virtual void bindPort( bool isBorder, int portId, int index );
    virtual void unbindPort( bool isBorder, int portId, int index );
};
extern const UMLRTCapsuleClass Top;

#endif

