
#ifndef A_HH
#define A_HH

#include "P.hh"
#include "umlrtcapsule.hh"
#include "umlrtcapsuleclass.hh"
#include "umlrtlogprotocol.hh"
#include "umlrtmessage.hh"
struct UMLRTCommsPort;
struct UMLRTSlot;

#include "UtilityMacros.hh"

class Capsule_A : public UMLRTCapsule
{
public:
    Capsule_A( const UMLRTCapsuleClass * cd, UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat );
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
        State2,
        SPECIAL_INTERNAL_STATE_TOP,
        SPECIAL_INTERNAL_STATE_UNVISITED
    };
    const char * stateNames[4];
    State currentState;
    void update_state( State newState );
    void entryaction_____State1( const UMLRTMessage * msg );
    void entryaction_____State2( const UMLRTMessage * msg );
    void transitionaction_____transition1( const UMLRTMessage * msg );
    void actionchain_____Initial( const UMLRTMessage * msg );
    void actionchain_____transition1( const UMLRTMessage * msg );
    State state_____State1( const UMLRTMessage * msg );
    State state_____State2( const UMLRTMessage * msg );
};
extern const UMLRTCapsuleClass A;

#endif

