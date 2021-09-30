
#ifndef TOP_HH
#define TOP_HH

#include "UTILS.hh"
#include "umlrtcapsule.hh"
#include "umlrtcapsuleclass.hh"
#include "umlrtlogprotocol.hh"
#include "umlrtmessage.hh"
struct UMLRTCommsPort;
struct UMLRTSlot;

#include "UTILS.hh"
#include "UtilsPrintKinds.hh"

class Capsule_Top : public UMLRTCapsule
{
public:
    Capsule_Top( const UMLRTCapsuleClass * cd, UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat );
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
    enum InternalPortId
    {
        internalport_log
    };
    virtual void bindPort( bool isBorder, int portId, int index );
    virtual void unbindPort( bool isBorder, int portId, int index );
protected:
    UTILS utils;
public:
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
    void entryaction_____ClassUtility__Top__StateMachine__Region__Running__topRunning( const UMLRTMessage * msg );
    void transitionaction_____ClassUtility__Top__StateMachine__Region__Transition0__topInItializing( const UMLRTMessage * msg );
    void actionchain_____top__Transition3__ActionChain4( const UMLRTMessage * msg );
    State state_____top__Running( const UMLRTMessage * msg );
};
extern const UMLRTCapsuleClass Top;

#endif

