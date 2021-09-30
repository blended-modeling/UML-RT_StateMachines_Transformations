
#ifndef B_HH
#define B_HH

#include "A.hh"
#include "Protocol1.hh"
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
    Protocol1::Base p;
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
        State3,
        State4,
        State1,
        State2,
        State3__State1,
        State3__boundary,
        State4__State1,
        State4__State2,
        State4__boundary,
        State5,
        SPECIAL_INTERNAL_STATE_TOP,
        SPECIAL_INTERNAL_STATE_UNVISITED
    };
    const char * stateNames[12];
    State currentState;
    State history[2];
    void save_history( State compositeState, State subState );
    bool check_history( State compositeState, State subState );
    void update_state( State newState );
    void entryaction_____State4__State2( const UMLRTMessage * msg );
    bool guard_____State4__ta41( const UMLRTMessage * msg );
    bool guard_____State4__ta42( const UMLRTMessage * msg );
    void actionchain_____State3__new_transition_1( const UMLRTMessage * msg );
    void actionchain_____State3__new_transition_2_to_unvisited_boundary( const UMLRTMessage * msg );
    void actionchain_____State3__new_transition_3_to_visited_boundary( const UMLRTMessage * msg );
    void actionchain_____State3__tb30( const UMLRTMessage * msg );
    void actionchain_____State4__new_transition_4( const UMLRTMessage * msg );
    void actionchain_____State4__new_transition_5( const UMLRTMessage * msg );
    void actionchain_____State4__new_transition_6_to_unvisited_boundary( const UMLRTMessage * msg );
    void actionchain_____State4__new_transition_7_to_visited_boundary( const UMLRTMessage * msg );
    void actionchain_____State4__ta41( const UMLRTMessage * msg );
    void actionchain_____State4__ta42( const UMLRTMessage * msg );
    void actionchain_____State4__tb41( const UMLRTMessage * msg );
    void actionchain_____State4__tb42( const UMLRTMessage * msg );
    void actionchain_____ta0( const UMLRTMessage * msg );
    void actionchain_____ta1( const UMLRTMessage * msg );
    void actionchain_____ta2( const UMLRTMessage * msg );
    void actionchain_____ta3( const UMLRTMessage * msg );
    void actionchain_____tb4( const UMLRTMessage * msg );
    State junction_____State3__bs3ep1( const UMLRTMessage * msg );
    State junction_____State4__as4enp1( const UMLRTMessage * msg );
    State junction_____State4__bs4exp1( const UMLRTMessage * msg );
    State choice_____State3__deephistory( const UMLRTMessage * msg );
    State choice_____State4__as4cp1( const UMLRTMessage * msg );
    State choice_____State4__deephistory( const UMLRTMessage * msg );
    State state_____State1( const UMLRTMessage * msg );
    State state_____State2( const UMLRTMessage * msg );
    State state_____State3__State1( const UMLRTMessage * msg );
    State state_____State3__boundary( const UMLRTMessage * msg );
    State state_____State4__State1( const UMLRTMessage * msg );
    State state_____State4__State2( const UMLRTMessage * msg );
    State state_____State4__boundary( const UMLRTMessage * msg );
    State state_____State5( const UMLRTMessage * msg );
};
extern const UMLRTCapsuleClass B;

#endif

