
#ifndef TOP_HH
#define TOP_HH

#include "Control.hh"
#include "umlrtcapsule.hh"
#include "umlrtcapsuleclass.hh"
#include "umlrtcapsuleid.hh"
#include "umlrtframeprotocol.hh"
#include "umlrtlogprotocol.hh"
#include "umlrtmessage.hh"
struct UMLRTCapsulePart;
struct UMLRTCommsPort;
struct UMLRTSlot;

class Capsule_Top : public UMLRTCapsule
{
public:
    Capsule_Top( const UMLRTCapsuleClass * cd, UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat );
protected:
    Control::Base control;
public:
    enum InternalPortId
    {
        internalport_control,
        internalport_log
    };
protected:
    UMLRTFrameProtocol_baserole frame;
public:
    enum BorderPortId
    {
        borderport_frame
    };
protected:
    UMLRTLogProtocol_baserole log;
public:
    enum PartId
    {
        part_medium,
        part_receiver,
        part_sender
    };
protected:
    const UMLRTCapsulePart * const medium;
    const UMLRTCapsulePart * const receiver;
    const UMLRTCapsulePart * const sender;
public:
    enum PortId
    {
        port_control,
        port_frame,
        port_log
    };
    virtual void bindPort( bool isBorder, int portId, int index );
    virtual void unbindPort( bool isBorder, int portId, int index );
protected:
    UMLRTCapsuleId mediumId;
public:
    virtual void inject( const UMLRTMessage & message );
    virtual void initialize( const UMLRTMessage & message );
    const char * getCurrentStateString() const;
private:
    enum State
    {
        Begin,
        SPECIAL_INTERNAL_STATE_TOP,
        SPECIAL_INTERNAL_STATE_UNVISITED
    };
    const char * stateNames[3];
    State currentState;
    void update_state( State newState );
    void entryaction_____Begin( const UMLRTMessage * msg );
    void actionchain_____t0( const UMLRTMessage * msg );
    State state_____Begin( const UMLRTMessage * msg );
};
extern const UMLRTCapsuleClass Top;

#endif

