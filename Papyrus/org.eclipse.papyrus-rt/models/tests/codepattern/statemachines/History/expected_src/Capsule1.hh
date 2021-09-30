
#ifndef CAPSULE1_HH
#define CAPSULE1_HH

#include "Protocol1.hh"
#include "umlrtcapsule.hh"
#include "umlrtcapsuleclass.hh"
#include "umlrtlogprotocol.hh"
#include "umlrtmessage.hh"
struct UMLRTCommsPort;
struct UMLRTSlot;

class Capsule_Capsule1 : public UMLRTCapsule
{
public:
    Capsule_Capsule1( const UMLRTCapsuleClass * cd, UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat );
protected:
    UMLRTLogProtocol_baserole log;
    Protocol1::Base port1;
public:
    enum BorderPortId
    {
        borderport_port1
    };
    enum PartId
    {
    };
    enum PortId
    {
        port_log,
        port_port1
    };
    enum InternalPortId
    {
        internalport_log
    };
    virtual void bindPort( bool isBorder, int portId, int index );
    virtual void unbindPort( bool isBorder, int portId, int index );
    virtual void inject( const UMLRTMessage & message );
    virtual void initialize( const UMLRTMessage & message );
    const char * getCurrentStateString() const;
private:
    enum State
    {
        s3,
        s1,
        s2,
        s3__s31,
        s3__s32,
        SPECIAL_INTERNAL_STATE_TOP,
        SPECIAL_INTERNAL_STATE_UNVISITED
    };
    const char * stateNames[7];
    State currentState;
    State history[1];
    void save_history( State compositeState, State subState );
    bool check_history( State compositeState, State subState );
    void update_state( State newState );
    void actionchain_____s3__new_transition_1( const UMLRTMessage * msg );
    void actionchain_____s3__new_transition_2( const UMLRTMessage * msg );
    void actionchain_____s3__new_transition_3( const UMLRTMessage * msg );
    void actionchain_____s3__new_transition_4( const UMLRTMessage * msg );
    void actionchain_____s3__new_transition_5( const UMLRTMessage * msg );
    void actionchain_____s3__new_transition_6( const UMLRTMessage * msg );
    void actionchain_____s3__new_transition_7( const UMLRTMessage * msg );
    void actionchain_____s3__t32( const UMLRTMessage * msg );
    void actionchain_____s3__t33( const UMLRTMessage * msg );
    void actionchain_____s3__t34( const UMLRTMessage * msg );
    void actionchain_____t1( const UMLRTMessage * msg );
    void actionchain_____t2( const UMLRTMessage * msg );
    void actionchain_____t3( const UMLRTMessage * msg );
    void actionchain_____t4( const UMLRTMessage * msg );
    void actionchain_____t5( const UMLRTMessage * msg );
    void actionchain_____t6( const UMLRTMessage * msg );
    State junction_____s3__connectionPoint1( const UMLRTMessage * msg );
    State junction_____s3__new_exitpoint_1( const UMLRTMessage * msg );
    State choice_____s3__deephistory( const UMLRTMessage * msg );
    State state_____s1( const UMLRTMessage * msg );
    State state_____s2( const UMLRTMessage * msg );
    State state_____s3__s31( const UMLRTMessage * msg );
    State state_____s3__s32( const UMLRTMessage * msg );
};
extern const UMLRTCapsuleClass Capsule1;

#endif

