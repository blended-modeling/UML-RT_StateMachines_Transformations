
#ifndef TOP_HH
#define TOP_HH

#include "Protocol1.hh"
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
        borderport_protocol1
    };
    enum PartId
    {
    };
    enum PortId
    {
        port_protocol1
    };
    virtual void bindPort( bool isBorder, int portId, int index );
    virtual void unbindPort( bool isBorder, int portId, int index );
    virtual void inject( const UMLRTMessage & message );
    virtual void initialize( const UMLRTMessage & message );
    const char * getCurrentStateString() const;
private:
    enum State
    {
        State2,
        State1,
        State2__State1,
        State2__State2,
        State2__State3,
        State2__State4,
        State2__boundary,
        State3,
        State4,
        SPECIAL_INTERNAL_STATE_TOP,
        SPECIAL_INTERNAL_STATE_UNVISITED
    };
    const char * stateNames[11];
    State currentState;
    State history[1];
    void save_history( State compositeState, State subState );
    bool check_history( State compositeState, State subState );
    void update_state( State newState );
    bool guard_____State2__transition4( const UMLRTMessage * msg );
    bool guard_____State2__tt( const UMLRTMessage * msg );
    void actionchain_____Initial( const UMLRTMessage * msg );
    void actionchain_____State2__new_transition_1( const UMLRTMessage * msg );
    void actionchain_____State2__new_transition_2( const UMLRTMessage * msg );
    void actionchain_____State2__new_transition_3( const UMLRTMessage * msg );
    void actionchain_____State2__new_transition_4( const UMLRTMessage * msg );
    void actionchain_____State2__new_transition_5_to_unvisited_boundary( const UMLRTMessage * msg );
    void actionchain_____State2__new_transition_6_to_visited_boundary( const UMLRTMessage * msg );
    void actionchain_____State2__transition0( const UMLRTMessage * msg );
    void actionchain_____State2__transition1( const UMLRTMessage * msg );
    void actionchain_____State2__transition3( const UMLRTMessage * msg );
    void actionchain_____State2__transition4( const UMLRTMessage * msg );
    void actionchain_____State2__transition5( const UMLRTMessage * msg );
    void actionchain_____State2__transition6( const UMLRTMessage * msg );
    void actionchain_____State2__transition7( const UMLRTMessage * msg );
    void actionchain_____State2__transition8( const UMLRTMessage * msg );
    void actionchain_____State2__tt( const UMLRTMessage * msg );
    void actionchain_____transition1( const UMLRTMessage * msg );
    void actionchain_____transition2( const UMLRTMessage * msg );
    void actionchain_____transition3( const UMLRTMessage * msg );
    void actionchain_____transition4( const UMLRTMessage * msg );
    State junction_____State2__connectionPoint0( const UMLRTMessage * msg );
    State junction_____State2__connectionPoint1( const UMLRTMessage * msg );
    State junction_____State2__connectionPoint2( const UMLRTMessage * msg );
    State junction_____State2__connectionPoint3( const UMLRTMessage * msg );
    State choice_____State2__c1( const UMLRTMessage * msg );
    State choice_____State2__deephistory( const UMLRTMessage * msg );
    State choice_____State2__subvertex4( const UMLRTMessage * msg );
    State state_____State1( const UMLRTMessage * msg );
    State state_____State2__State1( const UMLRTMessage * msg );
    State state_____State2__State2( const UMLRTMessage * msg );
    State state_____State2__State3( const UMLRTMessage * msg );
    State state_____State2__State4( const UMLRTMessage * msg );
    State state_____State2__boundary( const UMLRTMessage * msg );
    State state_____State3( const UMLRTMessage * msg );
    State state_____State4( const UMLRTMessage * msg );
};
extern const UMLRTCapsuleClass Top;

#endif

