
#ifndef B_HH
#define B_HH

#include "A.hh"
#include "P.hh"
#include "umlrtcapsuleclass.hh"
#include "umlrtlogprotocol.hh"
#include "umlrtmessage.hh"
struct UMLRTCommsPort;
struct UMLRTSlot;

class Capsule_B : public Capsule_A
{
public:
    Capsule_B( const UMLRTCapsuleClass * cd, UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat );
protected:
    UMLRTLogProtocol_baserole log;
    P::Base p;
public:
    enum BorderPortId
    {
        borderport_p
    };
    enum PartId
    {
    };
    enum PortId
    {
        port_log,
        port_p
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
        State1__State1,
        State1__State2,
        State2,
        SPECIAL_INTERNAL_STATE_TOP,
        SPECIAL_INTERNAL_STATE_UNVISITED
    };
    const char * stateNames[6];
    State currentState;
    State history[1];
    void save_history( State compositeState, State subState );
    bool check_history( State compositeState, State subState );
    void update_state( State newState );
    void entryaction_____State1__State1( const UMLRTMessage * msg );
    void entryaction_____State1__State2( const UMLRTMessage * msg );
    void entryaction_____State2( const UMLRTMessage * msg );
    void exitaction_____State1( const UMLRTMessage * msg );
    void exitaction_____State1__State2( const UMLRTMessage * msg );
    void transitionaction_____State1__t12( const UMLRTMessage * msg );
    void transitionaction_____t1( const UMLRTMessage * msg );
    void actionchain_____Initial( const UMLRTMessage * msg );
    void actionchain_____State1__new_transition_1( const UMLRTMessage * msg );
    void actionchain_____State1__new_transition_2( const UMLRTMessage * msg );
    void actionchain_____State1__new_transition_3( const UMLRTMessage * msg );
    void actionchain_____State1__new_transition_4( const UMLRTMessage * msg );
    void actionchain_____State1__new_transition_5( const UMLRTMessage * msg );
    void actionchain_____State1__new_transition_6( const UMLRTMessage * msg );
    void actionchain_____State1__new_transition_7( const UMLRTMessage * msg );
    void actionchain_____State1__t12( const UMLRTMessage * msg );
    void actionchain_____State1__t13( const UMLRTMessage * msg );
    void actionchain_____State1__transition0( const UMLRTMessage * msg );
    void actionchain_____t1( const UMLRTMessage * msg );
    void actionchain_____transition0( const UMLRTMessage * msg );
    State junction_____State1__implicitConnectionPoint1( const UMLRTMessage * msg );
    State junction_____State1__implicitConnectionPoint2( const UMLRTMessage * msg );
    State choice_____State1__deephistory( const UMLRTMessage * msg );
    State state_____State1__State1( const UMLRTMessage * msg );
    State state_____State1__State2( const UMLRTMessage * msg );
    State state_____State2( const UMLRTMessage * msg );
};
extern const UMLRTCapsuleClass B;

#endif

