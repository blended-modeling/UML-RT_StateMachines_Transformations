
#ifndef TOP_HH
#define TOP_HH

#include "umlrtcapsule.hh"
#include "umlrtcapsuleclass.hh"
#include "umlrtlogprotocol.hh"
#include "umlrtmessage.hh"
struct UMLRTCapsulePart;
struct UMLRTCommsPort;
struct UMLRTSlot;

#include "DBG.hh"

#include "umlrtmain.hh"

class Capsule_Top : public UMLRTCapsule
{
public:
    Capsule_Top( const UMLRTCapsuleClass * cd, UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat );
protected:
    UMLRTLogProtocol_baserole log;
public:
    enum PartId
    {
        part_below1
    };
protected:
    const UMLRTCapsulePart * const below1;
public:
    enum PortId
    {
        port_log
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
        top__Running,
        SPECIAL_INTERNAL_STATE_UNVISITED
    };
    const char * stateNames[2];
    State currentState;
    void entryaction_____GetArguments__Top__StateMachine__Region__Running__topRunning( const UMLRTMessage * msg );
    void transitionaction_____GetArguments__Top__StateMachine__Region__TopInitial__initializeTop( const UMLRTMessage * msg );
    void actionchain_____top__TopInitial__ActionChain3( const UMLRTMessage * msg );
    State state_____top__Running( const UMLRTMessage * msg );
};
extern const UMLRTCapsuleClass Top;

#endif

