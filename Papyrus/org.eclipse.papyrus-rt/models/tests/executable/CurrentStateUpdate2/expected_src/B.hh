
#ifndef B_HH
#define B_HH

#include "P.hh"
#include "umlrtcapsule.hh"
#include "umlrtcapsuleclass.hh"
#include "umlrtlogprotocol.hh"
#include "umlrtmessage.hh"
struct UMLRTCommsPort;
struct UMLRTSlot;

class Capsule_B : public UMLRTCapsule
{
public:
    Capsule_B( const UMLRTCapsuleClass * cd, UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat );
protected:
    UMLRTLogProtocol_baserole log;
    P::Conj p;
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
        State2,
        State1__State1,
        State1__boundary,
        State2__State1,
        State2__boundary,
        SPECIAL_INTERNAL_STATE_TOP,
        SPECIAL_INTERNAL_STATE_UNVISITED
    };
    const char * stateNames[8];
    State currentState;
    State history[2];
    void save_history( State compositeState, State subState );
    bool check_history( State compositeState, State subState );
    void update_state( State newState );
    void entryaction_____State1( const UMLRTMessage * msg );
    void entryaction_____State1__State1( const UMLRTMessage * msg );
    void entryaction_____State2( const UMLRTMessage * msg );
    void entryaction_____State2__State1( const UMLRTMessage * msg );
    void exitaction_____State1( const UMLRTMessage * msg );
    void exitaction_____State1__State1( const UMLRTMessage * msg );
    void transitionaction_____State1__t10( const UMLRTMessage * msg );
    void transitionaction_____State1__t11( const UMLRTMessage * msg );
    void transitionaction_____State2__t20( const UMLRTMessage * msg );
    void transitionaction_____t0( const UMLRTMessage * msg );
    void transitionaction_____t1( const UMLRTMessage * msg );
    void actionchain_____State1__new_transition_1( const UMLRTMessage * msg );
    void actionchain_____State1__new_transition_2_to_unvisited_boundary( const UMLRTMessage * msg );
    void actionchain_____State1__new_transition_3_to_visited_boundary( const UMLRTMessage * msg );
    void actionchain_____State1__t10( const UMLRTMessage * msg );
    void actionchain_____State1__t11( const UMLRTMessage * msg );
    void actionchain_____State2__new_transition_4( const UMLRTMessage * msg );
    void actionchain_____State2__new_transition_5_to_unvisited_boundary( const UMLRTMessage * msg );
    void actionchain_____State2__new_transition_6_to_visited_boundary( const UMLRTMessage * msg );
    void actionchain_____State2__t20( const UMLRTMessage * msg );
    void actionchain_____t0( const UMLRTMessage * msg );
    void actionchain_____t1( const UMLRTMessage * msg );
    State junction_____State1__State1EntryPoint( const UMLRTMessage * msg );
    State junction_____State1__State1ExitPoint1( const UMLRTMessage * msg );
    State junction_____State2__State2EntryPoint1( const UMLRTMessage * msg );
    State choice_____State1__deephistory( const UMLRTMessage * msg );
    State choice_____State2__deephistory( const UMLRTMessage * msg );
    State state_____State1__State1( const UMLRTMessage * msg );
    State state_____State1__boundary( const UMLRTMessage * msg );
    State state_____State2__State1( const UMLRTMessage * msg );
    State state_____State2__boundary( const UMLRTMessage * msg );
};
extern const UMLRTCapsuleClass B;

#endif

