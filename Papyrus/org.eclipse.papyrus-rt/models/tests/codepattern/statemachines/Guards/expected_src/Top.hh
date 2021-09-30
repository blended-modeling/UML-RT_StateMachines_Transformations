
#ifndef TOP_HH
#define TOP_HH

#include "P.hh"
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
        port_p
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
        State4,
        SPECIAL_INTERNAL_STATE_TOP,
        SPECIAL_INTERNAL_STATE_UNVISITED
    };
    const char * stateNames[6];
    State currentState;
    void update_state( State newState );
    void transitionaction_____t1( const UMLRTMessage * msg );
    void transitionaction_____t2( const UMLRTMessage * msg );
    void transitionaction_____t3( const UMLRTMessage * msg );
    bool guard_____t1( const UMLRTMessage * msg );
    bool guard_____t1__trigger0( const UMLRTMessage * msg );
    bool guard_____t1__trigger1( const UMLRTMessage * msg );
    bool guard_____t3__trigger0( const UMLRTMessage * msg );
    bool guard_____t3__trigger1( const UMLRTMessage * msg );
    void actionchain_____Initial( const UMLRTMessage * msg );
    void actionchain_____t1( const UMLRTMessage * msg );
    void actionchain_____t2( const UMLRTMessage * msg );
    void actionchain_____t3( const UMLRTMessage * msg );
    State state_____State1( const UMLRTMessage * msg );
    State state_____State2( const UMLRTMessage * msg );
    State state_____State3( const UMLRTMessage * msg );
    State state_____State4( const UMLRTMessage * msg );
};
extern const UMLRTCapsuleClass Top;

#endif

