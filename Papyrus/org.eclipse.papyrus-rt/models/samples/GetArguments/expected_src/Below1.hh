
#ifndef BELOW1_HH
#define BELOW1_HH

#include "umlrtcapsule.hh"
#include "umlrtcapsuleclass.hh"
#include "umlrtlogprotocol.hh"
#include "umlrtmessage.hh"
struct UMLRTCommsPort;
struct UMLRTSlot;

#include "DBG.hh"

#include "umlrtmain.hh"

class Capsule_Below1 : public UMLRTCapsule
{
public:
    Capsule_Below1( const UMLRTCapsuleClass * cd, UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat );
    enum InternalPortId
    {
        internalport_log
    };
protected:
    UMLRTLogProtocol_baserole log;
public:
    enum PartId
    {
    };
    enum PortId
    {
        port_log
    };
    virtual void bindPort( bool isBorder, int portId, int index );
    virtual void unbindPort( bool isBorder, int portId, int index );
    virtual void inject( const UMLRTMessage & message );
    virtual void initialize( const UMLRTMessage & message );
    const char * getCurrentStateString() const;
private:
    enum State
    {
        top__Running,
        SPECIAL_INTERNAL_STATE_UNVISITED
    };
    const char * stateNames[2];
    State currentState;
    void entryaction_____GetArguments__Below1__StateMachine__Region__Running__Running_EN( const UMLRTMessage * msg );
    void transitionaction_____GetArguments__Below1__StateMachine__Region__belowOneInitial__below1Init( const UMLRTMessage * msg );
    void actionchain_____top__belowOneInitial__ActionChain3( const UMLRTMessage * msg );
    State state_____top__Running( const UMLRTMessage * msg );
};
extern const UMLRTCapsuleClass Below1;

#endif

