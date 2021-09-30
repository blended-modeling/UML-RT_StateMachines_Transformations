
#ifndef A_HH
#define A_HH

#include "Protocol1.hh"
#include "umlrtcapsule.hh"
#include "umlrtcapsuleclass.hh"
#include "umlrtlogprotocol.hh"
#include "umlrtmessage.hh"
struct UMLRTCommsPort;
struct UMLRTSlot;

class Capsule_A : public UMLRTCapsule
{
public:
    Capsule_A( const UMLRTCapsuleClass * cd, UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat );
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
        State4,
        State1,
        State2,
        State3,
        State4__State1,
        State4__State2,
        State4__State3,
        State4__boundary,
        SPECIAL_INTERNAL_STATE_TOP,
        SPECIAL_INTERNAL_STATE_UNVISITED
    };
    const char * stateNames[10];
    State currentState;
    State history[1];
    void save_history( State compositeState, State subState );
    bool check_history( State compositeState, State subState );
    void update_state( State newState );
    bool guard_____State4__ta41( const UMLRTMessage * msg );
    bool guard_____State4__ta42( const UMLRTMessage * msg );
    void actionchain_____State4__new_transition_1( const UMLRTMessage * msg );
    void actionchain_____State4__new_transition_2( const UMLRTMessage * msg );
    void actionchain_____State4__new_transition_3( const UMLRTMessage * msg );
    void actionchain_____State4__new_transition_4_to_unvisited_boundary( const UMLRTMessage * msg );
    void actionchain_____State4__new_transition_5_to_visited_boundary( const UMLRTMessage * msg );
    void actionchain_____State4__ta41( const UMLRTMessage * msg );
    void actionchain_____State4__ta42( const UMLRTMessage * msg );
    void actionchain_____State4__ta43( const UMLRTMessage * msg );
    void actionchain_____ta0( const UMLRTMessage * msg );
    void actionchain_____ta1( const UMLRTMessage * msg );
    void actionchain_____ta2( const UMLRTMessage * msg );
    void actionchain_____ta3( const UMLRTMessage * msg );
    State junction_____State4__as4enp1( const UMLRTMessage * msg );
    State choice_____State4__as4cp1( const UMLRTMessage * msg );
    State choice_____State4__deephistory( const UMLRTMessage * msg );
    State state_____State1( const UMLRTMessage * msg );
    State state_____State2( const UMLRTMessage * msg );
    State state_____State3( const UMLRTMessage * msg );
    State state_____State4__State1( const UMLRTMessage * msg );
    State state_____State4__State2( const UMLRTMessage * msg );
    State state_____State4__State3( const UMLRTMessage * msg );
    State state_____State4__boundary( const UMLRTMessage * msg );
};
extern const UMLRTCapsuleClass A;

#endif

