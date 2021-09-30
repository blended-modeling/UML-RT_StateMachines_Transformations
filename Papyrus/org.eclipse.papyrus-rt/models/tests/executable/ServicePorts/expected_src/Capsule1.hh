
#ifndef CAPSULE1_HH
#define CAPSULE1_HH

#include "Control.hh"
#include "umlrtcapsule.hh"
#include "umlrtcapsuleclass.hh"
#include "umlrtlogprotocol.hh"
#include "umlrtmessage.hh"
#include "umlrttimerid.hh"
#include "umlrttimerprotocol.hh"
struct UMLRTCommsPort;
struct UMLRTSlot;

#include "UtilityMacros.hh"

class Capsule_Capsule1 : public UMLRTCapsule
{
public:
    Capsule_Capsule1( const UMLRTCapsuleClass * cd, UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat );
    enum BorderPortId
    {
        borderport_control
    };
protected:
    Control::Conj control;
    UMLRTLogProtocol_baserole log;
    UMLRTTimerProtocol_baserole timing;
public:
    enum InternalPortId
    {
        internalport_timing,
        internalport_log
    };
    enum PartId
    {
    };
    enum PortId
    {
        port_control,
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
        Done,
        Ended,
        Ready,
        Started,
        SPECIAL_INTERNAL_STATE_TOP,
        SPECIAL_INTERNAL_STATE_UNVISITED
    };
    const char * stateNames[6];
    State currentState;
    void update_state( State newState );
    void entryaction_____Done( const UMLRTMessage * msg );
    void entryaction_____Ended( const UMLRTMessage * msg );
    void entryaction_____Ready( const UMLRTMessage * msg );
    void entryaction_____Started( const UMLRTMessage * msg );
    void actionchain_____Initial( const UMLRTMessage * msg );
    void actionchain_____stop0( const UMLRTMessage * msg );
    void actionchain_____stop1( const UMLRTMessage * msg );
    void actionchain_____transition1( const UMLRTMessage * msg );
    void actionchain_____transition2( const UMLRTMessage * msg );
    State state_____Done( const UMLRTMessage * msg );
    State state_____Ended( const UMLRTMessage * msg );
    State state_____Ready( const UMLRTMessage * msg );
    State state_____Started( const UMLRTMessage * msg );
};
extern const UMLRTCapsuleClass Capsule1;

#endif

