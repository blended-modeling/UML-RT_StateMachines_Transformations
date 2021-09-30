
#ifndef RECEIVER_HH
#define RECEIVER_HH

#include "Protocol1.hh"
#include "umlrtcapsule.hh"
#include "umlrtcapsuleclass.hh"
#include "umlrtmessage.hh"
struct UMLRTCommsPort;
struct UMLRTSlot;

#include <iostream>
using namespace std;

class Capsule_Receiver : public UMLRTCapsule
{
public:
    Capsule_Receiver( const UMLRTCapsuleClass * cd, UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat );
protected:
    Protocol1::Conj r_inp;
public:
    enum BorderPortId
    {
        borderport_r_inp
    };
    enum PartId
    {
    };
    enum PortId
    {
        port_r_inp
    };
    virtual void bindPort( bool isBorder, int portId, int index );
    virtual void unbindPort( bool isBorder, int portId, int index );
    virtual void inject( const UMLRTMessage & message );
    virtual void initialize( const UMLRTMessage & message );
    const char * getCurrentStateString() const;
private:
    enum State
    {
        Listening,
        MessageReceived,
        SPECIAL_INTERNAL_STATE_TOP,
        SPECIAL_INTERNAL_STATE_UNVISITED
    };
    const char * stateNames[4];
    State currentState;
    void update_state( State newState );
    void entryaction_____Listening( const UMLRTMessage * msg );
    void entryaction_____MessageReceived( const UMLRTMessage * msg );
    void actionchain_____t0( const UMLRTMessage * msg );
    void actionchain_____t1( const UMLRTMessage * msg );
    State state_____Listening( const UMLRTMessage * msg );
    State state_____MessageReceived( const UMLRTMessage * msg );
};
extern const UMLRTCapsuleClass Receiver;

#endif

