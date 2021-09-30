
#ifndef CAPSULE1_HH
#define CAPSULE1_HH

#include "Protocol1.hh"
#include "umlrtcapsule.hh"
#include "umlrtcapsuleclass.hh"
#include "umlrtlogprotocol.hh"
#include "umlrtmessage.hh"
struct UMLRTCommsPort;
struct UMLRTSlot;

#include "UtilityMacros.hh"

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
        State1,
        State2,
        State3,
        SPECIAL_INTERNAL_STATE_TOP,
        SPECIAL_INTERNAL_STATE_UNVISITED
    };
    const char * stateNames[5];
    State currentState;
    void update_state( State newState );
    void entryaction_____State1( const UMLRTMessage * msg );
    void entryaction_____State2( const UMLRTMessage * msg );
    void entryaction_____State3( const UMLRTMessage * msg );
    void transitionaction_____t1( const UMLRTMessage * msg );
    void transitionaction_____t2( const UMLRTMessage * msg );
    void transitionaction_____t3( const UMLRTMessage * msg );
    void transitionaction_____t4( const UMLRTMessage * msg );
    void actionchain_____t0( const UMLRTMessage * msg );
    void actionchain_____t1( const UMLRTMessage * msg );
    void actionchain_____t2( const UMLRTMessage * msg );
    void actionchain_____t3( const UMLRTMessage * msg );
    void actionchain_____t4( const UMLRTMessage * msg );
    State state_____State1( const UMLRTMessage * msg );
    State state_____State2( const UMLRTMessage * msg );
    State state_____State3( const UMLRTMessage * msg );
};
extern const UMLRTCapsuleClass Capsule1;

#endif

