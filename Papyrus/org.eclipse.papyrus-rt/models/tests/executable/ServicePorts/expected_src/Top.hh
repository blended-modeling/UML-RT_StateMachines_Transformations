
#ifndef TOP_HH
#define TOP_HH

#include "Control.hh"
#include "umlrtcapsule.hh"
#include "umlrtcapsuleclass.hh"
#include "umlrtframeprotocol.hh"
#include "umlrtlogprotocol.hh"
#include "umlrtmessage.hh"
#include "umlrttimerid.hh"
#include "umlrttimerprotocol.hh"
struct UMLRTCapsulePart;
struct UMLRTCommsPort;
struct UMLRTSlot;

#include "UtilityMacros.hh"

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
        internalport_frame,
        internalport_timing,
        internalport_log
    };
protected:
    UMLRTFrameProtocol_baserole frame;
    UMLRTLogProtocol_baserole log;
    UMLRTTimerProtocol_baserole timing;
public:
    enum PartId
    {
        part_capsule1
    };
protected:
    const UMLRTCapsulePart * const capsule1;
public:
    enum PortId
    {
        port_control,
        port_frame,
        port_log,
        port_timing
    };
    virtual void bindPort( bool isBorder, int portId, int index );
    virtual void unbindPort( bool isBorder, int portId, int index );
    UMLRTTimerId timerId;
    virtual void inject( const UMLRTMessage & message );
    virtual void initialize( const UMLRTMessage & message );
    const char * getCurrentStateString() const;
private:
    enum State
    {
        Finish,
        PartReady,
        Stopping,
        WaitingForPartToBeReady,
        SPECIAL_INTERNAL_STATE_TOP,
        SPECIAL_INTERNAL_STATE_UNVISITED
    };
    const char * stateNames[6];
    State currentState;
    void update_state( State newState );
    void entryaction_____Finish( const UMLRTMessage * msg );
    void entryaction_____PartReady( const UMLRTMessage * msg );
    void entryaction_____Stopping( const UMLRTMessage * msg );
    void entryaction_____WaitingForPartToBeReady( const UMLRTMessage * msg );
    void actionchain_____Initial( const UMLRTMessage * msg );
    void actionchain_____transition1( const UMLRTMessage * msg );
    void actionchain_____transition2( const UMLRTMessage * msg );
    void actionchain_____transition3( const UMLRTMessage * msg );
    State state_____Finish( const UMLRTMessage * msg );
    State state_____PartReady( const UMLRTMessage * msg );
    State state_____Stopping( const UMLRTMessage * msg );
    State state_____WaitingForPartToBeReady( const UMLRTMessage * msg );
};
extern const UMLRTCapsuleClass Top;

#endif

