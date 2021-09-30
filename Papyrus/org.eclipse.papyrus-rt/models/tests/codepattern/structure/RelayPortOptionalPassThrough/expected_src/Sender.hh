
#ifndef SENDER_HH
#define SENDER_HH

#include "Protocol1.hh"
#include "Start.hh"
#include "umlrtcapsule.hh"
#include "umlrtcapsuleclass.hh"
#include "umlrtmessage.hh"
struct UMLRTCommsPort;
struct UMLRTSlot;

#include <iostream>
using namespace std;

class Capsule_Sender : public UMLRTCapsule
{
public:
    Capsule_Sender( const UMLRTCapsuleClass * cd, UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat );
protected:
    Protocol1::Base s_out;
public:
    enum BorderPortId
    {
        borderport_s_out,
        borderport_start
    };
protected:
    Start::Conj start;
public:
    enum PartId
    {
    };
    enum PortId
    {
        port_s_out,
        port_start
    };
    virtual void bindPort( bool isBorder, int portId, int index );
    virtual void unbindPort( bool isBorder, int portId, int index );
    virtual void inject( const UMLRTMessage & message );
    virtual void initialize( const UMLRTMessage & message );
    const char * getCurrentStateString() const;
private:
    enum State
    {
        MessageSent,
        WaitingToStart,
        SPECIAL_INTERNAL_STATE_TOP,
        SPECIAL_INTERNAL_STATE_UNVISITED
    };
    const char * stateNames[4];
    State currentState;
    void update_state( State newState );
    void entryaction_____MessageSent( const UMLRTMessage * msg );
    void entryaction_____WaitingToStart( const UMLRTMessage * msg );
    void transitionaction_____t1( const UMLRTMessage * msg );
    void actionchain_____t0( const UMLRTMessage * msg );
    void actionchain_____t1( const UMLRTMessage * msg );
    State state_____MessageSent( const UMLRTMessage * msg );
    State state_____WaitingToStart( const UMLRTMessage * msg );
};
extern const UMLRTCapsuleClass Sender;

#endif

