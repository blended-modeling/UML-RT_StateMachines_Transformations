
#ifndef COMPUTER_HH
#define COMPUTER_HH

#include "AppControl.hh"
#include "Resource.hh"
#include "USBProtocol.hh"
#include "umlrtcapsule.hh"
#include "umlrtcapsuleclass.hh"
#include "umlrtcapsuleid.hh"
#include "umlrtframeprotocol.hh"
#include "umlrtmessage.hh"
#include "umlrttimerid.hh"
#include "umlrttimerprotocol.hh"
struct UMLRTCapsulePart;
struct UMLRTCommsPort;
struct UMLRTSlot;

#include "ResourceType.hh"
#include "umlrttimerid.hh"
#include "umlrttimespec.hh"

#include <iostream>
#include <iomanip>
#include <ctime>
#include "ResourceType.hh"

class Capsule_Computer : public UMLRTCapsule
{
public:
    Capsule_Computer( const UMLRTCapsuleClass * cd, UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat );
protected:
    UMLRTFrameProtocol_baserole frame;
public:
    enum BorderPortId
    {
        borderport_frame,
        borderport_timer,
        borderport_usbPort,
        borderport_userPort
    };
protected:
    Resource::Conj resMgr;
public:
    enum InternalPortId
    {
        internalport_resMgr
    };
protected:
    UMLRTTimerProtocol_baserole timer;
    USBProtocol::Conj usbPort;
    AppControl::Base userPort;
public:
    enum PartId
    {
        part_application,
        part_driverManager,
        part_usbBus
    };
protected:
    const UMLRTCapsulePart * const application;
    const UMLRTCapsulePart * const driverManager;
    const UMLRTCapsulePart * const usbBus;
public:
    enum PortId
    {
        port_frame,
        port_resMgr,
        port_timer,
        port_usbPort,
        port_userPort
    };
    virtual void bindPort( bool isBorder, int portId, int index );
    virtual void unbindPort( bool isBorder, int portId, int index );
protected:
    UMLRTTimerId timerId;
    UMLRTCapsuleId printerID;
    UMLRTCapsuleId storageID;
    int numSec;
public:
    int dummy;
    virtual void inject( const UMLRTMessage & message );
    virtual void initialize( const UMLRTMessage & message );
    const char * getCurrentStateString() const;
private:
    enum State
    {
        InitUSBBus,
        InitUSBBus__WaitForPrinter,
        InitUSBBus__WaitForStorage,
        InitUSBBus__boundary,
        Running,
        WaitForResourceManager,
        SPECIAL_INTERNAL_STATE_TOP,
        SPECIAL_INTERNAL_STATE_UNVISITED
    };
    const char * stateNames[8];
    State currentState;
    State history[1];
    void save_history( State compositeState, State subState );
    bool check_history( State compositeState, State subState );
    void update_state( State newState );
    void entryaction_____InitUSBBus( const UMLRTMessage * msg );
    void entryaction_____InitUSBBus__WaitForPrinter( const UMLRTMessage * msg );
    void entryaction_____InitUSBBus__WaitForStorage( const UMLRTMessage * msg );
    void entryaction_____Running( const UMLRTMessage * msg );
    void entryaction_____WaitForResourceManager( const UMLRTMessage * msg );
    void transitionaction_____InitUSBBus__onPrintDriverTimeout( const UMLRTMessage * msg );
    void transitionaction_____InitUSBBus__onPrintResourceID( const UMLRTMessage * msg );
    void transitionaction_____InitUSBBus__onStorageResourceID( const UMLRTMessage * msg );
    void transitionaction_____InitUSBBus__onStorageTimeout( const UMLRTMessage * msg );
    void transitionaction_____Initialise( const UMLRTMessage * msg );
    void transitionaction_____onResMgrRunning( const UMLRTMessage * msg );
    void actionchain_____EX_InitUSB_Continuation( const UMLRTMessage * msg );
    void actionchain_____InitUSBBus__EN_initUSB_Continuation( const UMLRTMessage * msg );
    void actionchain_____InitUSBBus__new_transition_1( const UMLRTMessage * msg );
    void actionchain_____InitUSBBus__new_transition_2( const UMLRTMessage * msg );
    void actionchain_____InitUSBBus__new_transition_3_to_unvisited_boundary( const UMLRTMessage * msg );
    void actionchain_____InitUSBBus__new_transition_4_to_visited_boundary( const UMLRTMessage * msg );
    void actionchain_____InitUSBBus__onPrintDriverTimeout( const UMLRTMessage * msg );
    void actionchain_____InitUSBBus__onPrintResourceID( const UMLRTMessage * msg );
    void actionchain_____InitUSBBus__onStorageResourceID( const UMLRTMessage * msg );
    void actionchain_____InitUSBBus__onStorageTimeout( const UMLRTMessage * msg );
    void actionchain_____Initialise( const UMLRTMessage * msg );
    void actionchain_____onResMgrRunning( const UMLRTMessage * msg );
    State junction_____InitUSBBus__EN_initUSB( const UMLRTMessage * msg );
    State junction_____InitUSBBus__EX_initUSB( const UMLRTMessage * msg );
    State choice_____InitUSBBus__deephistory( const UMLRTMessage * msg );
    State state_____InitUSBBus__WaitForPrinter( const UMLRTMessage * msg );
    State state_____InitUSBBus__WaitForStorage( const UMLRTMessage * msg );
    State state_____InitUSBBus__boundary( const UMLRTMessage * msg );
    State state_____Running( const UMLRTMessage * msg );
    State state_____WaitForResourceManager( const UMLRTMessage * msg );
};
extern const UMLRTCapsuleClass Computer;

#endif

