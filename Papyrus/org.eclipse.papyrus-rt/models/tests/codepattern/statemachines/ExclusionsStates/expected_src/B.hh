
#ifndef B_HH
#define B_HH

#include "A.hh"
#include "Protocol1.hh"
#include "Protocol2.hh"
#include "umlrtcapsuleclass.hh"
#include "umlrtmessage.hh"
struct UMLRTCommsPort;
struct UMLRTSlot;

class Capsule_B : public Capsule_A
{
public:
    Capsule_B( const UMLRTCapsuleClass * cd, UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat );
protected:
    Protocol1::Base p;
public:
    enum BorderPortId
    {
        borderport_p,
        borderport_q
    };
protected:
    Protocol2::Conj q;
public:
    enum PartId
    {
    };
    enum PortId
    {
        port_p,
        port_q
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
        State5,
        SPECIAL_INTERNAL_STATE_TOP,
        SPECIAL_INTERNAL_STATE_UNVISITED
    };
    const char * stateNames[6];
    State currentState;
    void update_state( State newState );
    void entryaction_____State3( const UMLRTMessage * msg );
    void actionchain_____implicitTransition0( const UMLRTMessage * msg );
    void actionchain_____implicitTransition1( const UMLRTMessage * msg );
    void actionchain_____implicitTransition2( const UMLRTMessage * msg );
    void actionchain_____implicitTransition3( const UMLRTMessage * msg );
    void actionchain_____implicitTransition4( const UMLRTMessage * msg );
    State state_____State1( const UMLRTMessage * msg );
    State state_____State2( const UMLRTMessage * msg );
    State state_____State3( const UMLRTMessage * msg );
    State state_____State5( const UMLRTMessage * msg );
};
extern const UMLRTCapsuleClass B;

#endif

