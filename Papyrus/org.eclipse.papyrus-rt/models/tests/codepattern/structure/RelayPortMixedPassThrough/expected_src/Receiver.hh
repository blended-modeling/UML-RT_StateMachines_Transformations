
#ifndef RECEIVER_HH
#define RECEIVER_HH

#include "Protocol1.hh"
#include "umlrtcapsule.hh"
#include "umlrtcapsuleclass.hh"
#include "umlrtmessage.hh"
struct UMLRTCommsPort;
struct UMLRTSlot;

class Capsule_Receiver : public UMLRTCapsule
{
public:
    Capsule_Receiver( const UMLRTCapsuleClass * cd, UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat );
protected:
    Protocol1::Conj inp;
public:
    enum BorderPortId
    {
        borderport_inp
    };
    enum PartId
    {
    };
    enum PortId
    {
        port_inp
    };
    virtual void bindPort( bool isBorder, int portId, int index );
    virtual void unbindPort( bool isBorder, int portId, int index );
    static int messagesReceived;
    virtual void inject( const UMLRTMessage & message );
    virtual void initialize( const UMLRTMessage & message );
    const char * getCurrentStateString() const;
private:
    enum State
    {
        State1,
        SPECIAL_INTERNAL_STATE_TOP,
        SPECIAL_INTERNAL_STATE_UNVISITED
    };
    const char * stateNames[3];
    State currentState;
    void update_state( State newState );
    void transitionaction_____t1( const UMLRTMessage * msg );
    void actionchain_____t0( const UMLRTMessage * msg );
    void actionchain_____t1( const UMLRTMessage * msg );
    State state_____State1( const UMLRTMessage * msg );
};
extern const UMLRTCapsuleClass Receiver;

#endif

