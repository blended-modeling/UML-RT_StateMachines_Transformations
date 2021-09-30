
#ifndef USER_HH
#define USER_HH

#include "AppControl.hh"
#include "umlrtcapsule.hh"
#include "umlrtcapsuleclass.hh"
#include "umlrtmessage.hh"
#include "umlrttimerid.hh"
#include "umlrttimerprotocol.hh"
struct UMLRTCommsPort;
struct UMLRTSlot;

#include <iostream>
#include <iomanip>
#include <cstdlib>

class Capsule_User : public UMLRTCapsule
{
public:
    Capsule_User( const UMLRTCapsuleClass * cd, UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat );
protected:
    AppControl::Conj computerPort;
public:
    enum BorderPortId
    {
        borderport_computerPort,
        borderport_timer
    };
protected:
    UMLRTTimerProtocol_baserole timer;
public:
    enum PartId
    {
    };
    enum PortId
    {
        port_computerPort,
        port_timer
    };
    virtual void bindPort( bool isBorder, int portId, int index );
    virtual void unbindPort( bool isBorder, int portId, int index );
protected:
    int numSec;
    UMLRTTimerId timerID;
    bool status;
public:
    virtual void inject( const UMLRTMessage & message );
    virtual void initialize( const UMLRTMessage & message );
    const char * getCurrentStateString() const;
private:
    enum State
    {
        Alive,
        FAILED,
        TestDone,
        Waiting4DocPrint,
        Waiting4DocSave,
        SPECIAL_INTERNAL_STATE_TOP,
        SPECIAL_INTERNAL_STATE_UNVISITED
    };
    const char * stateNames[7];
    State currentState;
    void update_state( State newState );
    void entryaction_____Alive( const UMLRTMessage * msg );
    void entryaction_____FAILED( const UMLRTMessage * msg );
    void entryaction_____TestDone( const UMLRTMessage * msg );
    void entryaction_____Waiting4DocPrint( const UMLRTMessage * msg );
    void entryaction_____Waiting4DocSave( const UMLRTMessage * msg );
    void transitionaction_____initTimerFail( const UMLRTMessage * msg );
    void transitionaction_____initTimerSet( const UMLRTMessage * msg );
    void transitionaction_____initialize( const UMLRTMessage * msg );
    void transitionaction_____isFalsePrintDoc( const UMLRTMessage * msg );
    void transitionaction_____isTrueDone( const UMLRTMessage * msg );
    void transitionaction_____onTimeout1( const UMLRTMessage * msg );
    void transitionaction_____onTimeout2( const UMLRTMessage * msg );
    void transitionaction_____transition6( const UMLRTMessage * msg );
    void transitionaction_____transition7( const UMLRTMessage * msg );
    void transitionaction_____transition8( const UMLRTMessage * msg );
    void transitionaction_____transition9( const UMLRTMessage * msg );
    bool guard_____initTimerFail( const UMLRTMessage * msg );
    bool guard_____initTimerSet( const UMLRTMessage * msg );
    bool guard_____isFalsePrintDoc( const UMLRTMessage * msg );
    bool guard_____isTrueDone( const UMLRTMessage * msg );
    bool guard_____transition6( const UMLRTMessage * msg );
    bool guard_____transition7( const UMLRTMessage * msg );
    bool guard_____transition8( const UMLRTMessage * msg );
    bool guard_____transition9( const UMLRTMessage * msg );
    void actionchain_____initTimerFail( const UMLRTMessage * msg );
    void actionchain_____initTimerSet( const UMLRTMessage * msg );
    void actionchain_____initialize( const UMLRTMessage * msg );
    void actionchain_____isFalsePrintDoc( const UMLRTMessage * msg );
    void actionchain_____isTrueDone( const UMLRTMessage * msg );
    void actionchain_____onTimeout1( const UMLRTMessage * msg );
    void actionchain_____onTimeout2( const UMLRTMessage * msg );
    void actionchain_____onTimeout3( const UMLRTMessage * msg );
    void actionchain_____transition10( const UMLRTMessage * msg );
    void actionchain_____transition6( const UMLRTMessage * msg );
    void actionchain_____transition7( const UMLRTMessage * msg );
    void actionchain_____transition8( const UMLRTMessage * msg );
    void actionchain_____transition9( const UMLRTMessage * msg );
    State choice_____TesterChoice( const UMLRTMessage * msg );
    State choice_____tid1_( const UMLRTMessage * msg );
    State choice_____tid2_( const UMLRTMessage * msg );
    State choice_____tid3_( const UMLRTMessage * msg );
    State state_____Alive( const UMLRTMessage * msg );
    State state_____FAILED( const UMLRTMessage * msg );
    State state_____TestDone( const UMLRTMessage * msg );
    State state_____Waiting4DocPrint( const UMLRTMessage * msg );
    State state_____Waiting4DocSave( const UMLRTMessage * msg );
};
extern const UMLRTCapsuleClass User;

#endif

