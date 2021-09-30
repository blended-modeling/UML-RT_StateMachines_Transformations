
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
        State1,
        State1__State1,
        State1__State2,
        State1__boundary,
        SPECIAL_INTERNAL_STATE_TOP,
        SPECIAL_INTERNAL_STATE_UNVISITED
    };
    const char * stateNames[6];
    State currentState;
    State history[1];
    void save_history( State compositeState, State subState );
    bool check_history( State compositeState, State subState );
    void update_state( State newState );
    void transitionaction_____State1__it1( const UMLRTMessage * msg );
    void transitionaction_____State1__it2( const UMLRTMessage * msg );
    void actionchain_____Initial( const UMLRTMessage * msg );
    void actionchain_____State1__it1( const UMLRTMessage * msg );
    void actionchain_____State1__it1( const UMLRTMessage * msg );
    void actionchain_____State1__it1( const UMLRTMessage * msg );
    void actionchain_____State1__it2( const UMLRTMessage * msg );
    void actionchain_____State1__it2( const UMLRTMessage * msg );
    void actionchain_____State1__it2( const UMLRTMessage * msg );
    void actionchain_____State1__new_transition_1( const UMLRTMessage * msg );
    void actionchain_____State1__new_transition_2( const UMLRTMessage * msg );
    void actionchain_____State1__new_transition_3_to_unvisited_boundary( const UMLRTMessage * msg );
    void actionchain_____State1__new_transition_4_to_visited_boundary( const UMLRTMessage * msg );
    void actionchain_____State1__t1( const UMLRTMessage * msg );
    State choice_____State1__deephistory( const UMLRTMessage * msg );
    State state_____State1__State1( const UMLRTMessage * msg );
    State state_____State1__State2( const UMLRTMessage * msg );
    State state_____State1__boundary( const UMLRTMessage * msg );
};
extern const UMLRTCapsuleClass A;

#endif

