
#ifndef TOP_HH
#define TOP_HH

#include "Protocol1.hh"
#include "Protocol2.hh"
#include "umlrtcapsule.hh"
#include "umlrtcapsuleclass.hh"
#include "umlrtmessage.hh"
struct UMLRTCommsPort;
struct UMLRTSlot;

class Capsule_Top : public UMLRTCapsule
{
public:
    Capsule_Top( const UMLRTCapsuleClass * cd, UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat );
protected:
    Protocol1::Base protocol1;
public:
    enum BorderPortId
    {
        borderport_protocol1,
        borderport_protocol2
    };
protected:
    Protocol2::Base protocol2;
public:
    enum PartId
    {
    };
    enum PortId
    {
        port_protocol1,
        port_protocol2
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
        State1__boundary,
        State2,
        State3,
        State4,
        SPECIAL_INTERNAL_STATE_TOP,
        SPECIAL_INTERNAL_STATE_UNVISITED
    };
    const char * stateNames[9];
    State currentState;
    State history[1];
    void save_history( State compositeState, State subState );
    bool check_history( State compositeState, State subState );
    void update_state( State newState );
    void actionchain_____Initial( const UMLRTMessage * msg );
    void actionchain_____State1__new_transition_1( const UMLRTMessage * msg );
    void actionchain_____State1__new_transition_10( const UMLRTMessage * msg );
    void actionchain_____State1__new_transition_2( const UMLRTMessage * msg );
    void actionchain_____State1__new_transition_3_to_unvisited_boundary( const UMLRTMessage * msg );
    void actionchain_____State1__new_transition_4_to_visited_boundary( const UMLRTMessage * msg );
    void actionchain_____State1__new_transition_5( const UMLRTMessage * msg );
    void actionchain_____State1__new_transition_6( const UMLRTMessage * msg );
    void actionchain_____State1__new_transition_7( const UMLRTMessage * msg );
    void actionchain_____State1__new_transition_8( const UMLRTMessage * msg );
    void actionchain_____State1__new_transition_9( const UMLRTMessage * msg );
    void actionchain_____State1__transition0( const UMLRTMessage * msg );
    void actionchain_____State1__transition1( const UMLRTMessage * msg );
    void actionchain_____State1__transition2( const UMLRTMessage * msg );
    void actionchain_____transition1( const UMLRTMessage * msg );
    void actionchain_____transition2( const UMLRTMessage * msg );
    void actionchain_____transition3( const UMLRTMessage * msg );
    State junction_____State1__connectionPoint0( const UMLRTMessage * msg );
    State junction_____State1__exp1( const UMLRTMessage * msg );
    State junction_____State1__exp2( const UMLRTMessage * msg );
    State junction_____State1__exp3( const UMLRTMessage * msg );
    State choice_____State1__deephistory( const UMLRTMessage * msg );
    State state_____State1__State1( const UMLRTMessage * msg );
    State state_____State1__State2( const UMLRTMessage * msg );
    State state_____State1__boundary( const UMLRTMessage * msg );
    State state_____State2( const UMLRTMessage * msg );
    State state_____State3( const UMLRTMessage * msg );
    State state_____State4( const UMLRTMessage * msg );
};
extern const UMLRTCapsuleClass Top;

#endif

