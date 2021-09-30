
#ifndef COMPUTERSYSTEM_HH
#define COMPUTERSYSTEM_HH

#include "umlrtcapsule.hh"
#include "umlrtcapsuleclass.hh"
#include "umlrtframeprotocol.hh"
#include "umlrtmessage.hh"
#include "umlrttimerid.hh"
#include "umlrttimerprotocol.hh"
struct UMLRTCapsulePart;
struct UMLRTCommsPort;
struct UMLRTSlot;

class Capsule_ComputerSystem : public UMLRTCapsule
{
public:
    Capsule_ComputerSystem( const UMLRTCapsuleClass * cd, UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat );
    enum PortId
    {
        port_frame,
        port_timer
    };
    virtual void inject( const UMLRTMessage & message );
    virtual void initialize( const UMLRTMessage & message );
    const char * getCurrentStateString() const;
private:
    enum State
    {
        Failure,
        Running,
        WaitingForComputerInit,
        WaitingForDeviceInit,
        SPECIAL_INTERNAL_STATE_TOP,
        SPECIAL_INTERNAL_STATE_UNVISITED
    };
    const char * stateNames[6];
    State currentState;
    void update_state( State newState );
    void entryaction_____Failure( const UMLRTMessage * msg );
    void entryaction_____Running( const UMLRTMessage * msg );
    void entryaction_____WaitingForComputerInit( const UMLRTMessage * msg );
    void entryaction_____WaitingForDeviceInit( const UMLRTMessage * msg );
    void transitionaction_____Failed( const UMLRTMessage * msg );
    void transitionaction_____Success( const UMLRTMessage * msg );
    void transitionaction_____compTimerFail( const UMLRTMessage * msg );
    void transitionaction_____incarnateDevices( const UMLRTMessage * msg );
    void transitionaction_____incarnateUser( const UMLRTMessage * msg );
    void transitionaction_____setComputerInitTimer( const UMLRTMessage * msg );
    void transitionaction_____timerOK( const UMLRTMessage * msg );
    void transitionaction_____userNOK( const UMLRTMessage * msg );
    void transitionaction_____userOK( const UMLRTMessage * msg );
    bool guard_____Failed( const UMLRTMessage * msg );
    bool guard_____Success( const UMLRTMessage * msg );
    bool guard_____compTimerFail( const UMLRTMessage * msg );
    bool guard_____timerOK( const UMLRTMessage * msg );
    bool guard_____userNOK( const UMLRTMessage * msg );
    bool guard_____userOK( const UMLRTMessage * msg );
    void actionchain_____Failed( const UMLRTMessage * msg );
    void actionchain_____Success( const UMLRTMessage * msg );
    void actionchain_____compTimerFail( const UMLRTMessage * msg );
    void actionchain_____incarnateDevices( const UMLRTMessage * msg );
    void actionchain_____incarnateUser( const UMLRTMessage * msg );
    void actionchain_____setComputerInitTimer( const UMLRTMessage * msg );
    void actionchain_____timerOK( const UMLRTMessage * msg );
    void actionchain_____userNOK( const UMLRTMessage * msg );
    void actionchain_____userOK( const UMLRTMessage * msg );
    State choice_____initOK_( const UMLRTMessage * msg );
    State choice_____timerOK_( const UMLRTMessage * msg );
    State choice_____userOK_( const UMLRTMessage * msg );
    State state_____Failure( const UMLRTMessage * msg );
    State state_____Running( const UMLRTMessage * msg );
    State state_____WaitingForComputerInit( const UMLRTMessage * msg );
    State state_____WaitingForDeviceInit( const UMLRTMessage * msg );
protected:
    UMLRTFrameProtocol_baserole frame;
public:
    enum BorderPortId
    {
        borderport_frame,
        borderport_timer
    };
protected:
    UMLRTTimerProtocol_baserole timer;
public:
    enum PartId
    {
        part_computer,
        part_massStorage,
        part_printer,
        part_user
    };
protected:
    const UMLRTCapsulePart * const computer;
    const UMLRTCapsulePart * const massStorage;
    const UMLRTCapsulePart * const printer;
    const UMLRTCapsulePart * const user;
public:
    virtual void bindPort( bool isBorder, int portId, int index );
    virtual void unbindPort( bool isBorder, int portId, int index );
protected:
    bool initStatus;
    UMLRTTimerId timerID;
    bool _dummiest;
    int numSec;
};
extern const UMLRTCapsuleClass ComputerSystem;

#endif

