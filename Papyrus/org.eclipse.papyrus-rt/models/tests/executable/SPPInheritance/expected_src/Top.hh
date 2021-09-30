
#ifndef TOP_HH
#define TOP_HH

#include "umlrtcapsule.hh"
#include "umlrtcapsuleclass.hh"
#include "umlrtframeprotocol.hh"
#include "umlrtlogprotocol.hh"
#include "umlrtmessage.hh"
#include "umlrttimerprotocol.hh"
struct UMLRTCapsulePart;
struct UMLRTCommsPort;
struct UMLRTSlot;

#include "Server1.hh"
#include "TimingUtil.hh"
#include "UtilityMacros.hh"

class Capsule_Top : public UMLRTCapsule
{
public:
    Capsule_Top( const UMLRTCapsuleClass * cd, UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat );
    enum PortId
    {
        port_frame,
        port_log,
        port_timing
    };
    virtual void inject( const UMLRTMessage & message );
    virtual void initialize( const UMLRTMessage & message );
    const char * getCurrentStateString() const;
private:
    enum State
    {
        Starting,
        Working,
        SPECIAL_INTERNAL_STATE_TOP,
        SPECIAL_INTERNAL_STATE_UNVISITED
    };
    const char * stateNames[4];
    State currentState;
    void update_state( State newState );
    void entryaction_____Starting( const UMLRTMessage * msg );
    void entryaction_____Working( const UMLRTMessage * msg );
    void actionchain_____Initial( const UMLRTMessage * msg );
    void actionchain_____transition1( const UMLRTMessage * msg );
    State state_____Starting( const UMLRTMessage * msg );
    State state_____Working( const UMLRTMessage * msg );
protected:
    UMLRTFrameProtocol_baserole frame;
public:
    enum InternalPortId
    {
        internalport_frame,
        internalport_timing,
        internalport_log
    };
protected:
    UMLRTLogProtocol_baserole log;
    UMLRTTimerProtocol_baserole timing;
public:
    enum PartId
    {
        part_client,
        part_server
    };
protected:
    const UMLRTCapsulePart * const client;
    const UMLRTCapsulePart * const server;
public:
    virtual void bindPort( bool isBorder, int portId, int index );
    virtual void unbindPort( bool isBorder, int portId, int index );
    const long DEFAULT_DELAY;
};
extern const UMLRTCapsuleClass Top;

#endif

