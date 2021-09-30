
#ifndef RESOURCEMANAGER_HH
#define RESOURCEMANAGER_HH

#include "Resource.hh"
#include "ResourceType.hh"
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

#include "USBPrinterDriver.hh"
#include "USBStorageDriver.hh"

#include <iostream>
#include <iomanip>
#include <ctime>
#include "ResourceType.hh"
#include "USBPrinterDriver.hh"
#include "USBStorageDriver.hh"

class Capsule_ResourceManager : public UMLRTCapsule
{
public:
    Capsule_ResourceManager( const UMLRTCapsuleClass * cd, UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat );
    enum BorderPortId
    {
        borderport_resMgr,
        borderport_appPort,
        borderport_frame,
        borderport_timer
    };
protected:
    Resource::Base appPort;
    UMLRTFrameProtocol_baserole frame;
    Resource::Base resMgr;
    UMLRTTimerProtocol_baserole timer;
public:
    enum PartId
    {
        part_usbPrinterDriver,
        part_usbStorageDriver
    };
protected:
    const UMLRTCapsulePart * const usbPrinterDriver;
    const UMLRTCapsulePart * const usbStorageDriver;
public:
    enum PortId
    {
        port_appPort,
        port_frame,
        port_resMgr,
        port_timer
    };
    virtual void bindPort( bool isBorder, int portId, int index );
    virtual void unbindPort( bool isBorder, int portId, int index );
protected:
    UMLRTCapsuleId printerID;
    UMLRTCapsuleId storageID;
    int printerRequestCount;
    int storageRequestCount;
    bool status;
    ResourceType resourceType;
    UMLRTTimerId timerID;
    int numSec;
public:
    virtual void inject( const UMLRTMessage & message );
    virtual void initialize( const UMLRTMessage & message );
    const char * getCurrentStateString() const;
private:
    enum State
    {
        InitFail,
        WaitInitTimeout,
        WaitingForRequest,
        SPECIAL_INTERNAL_STATE_TOP,
        SPECIAL_INTERNAL_STATE_UNVISITED
    };
    const char * stateNames[5];
    State currentState;
    void update_state( State newState );
    void entryaction_____InitFail( const UMLRTMessage * msg );
    void entryaction_____WaitInitTimeout( const UMLRTMessage * msg );
    void entryaction_____WaitingForRequest( const UMLRTMessage * msg );
    void exitaction_____WaitingForRequest( const UMLRTMessage * msg );
    void transitionaction_____NOK( const UMLRTMessage * msg );
    void transitionaction_____OK( const UMLRTMessage * msg );
    void transitionaction_____initialize( const UMLRTMessage * msg );
    void transitionaction_____onAppPortStorageRequest( const UMLRTMessage * msg );
    void transitionaction_____onAppPrinterRequest( const UMLRTMessage * msg );
    void transitionaction_____onResMgrPrinterRequest( const UMLRTMessage * msg );
    void transitionaction_____onResMgrStorageRequest( const UMLRTMessage * msg );
    void transitionaction_____onTimeout( const UMLRTMessage * msg );
    bool guard_____NOK( const UMLRTMessage * msg );
    bool guard_____OK( const UMLRTMessage * msg );
    void actionchain_____NOK( const UMLRTMessage * msg );
    void actionchain_____OK( const UMLRTMessage * msg );
    void actionchain_____initialize( const UMLRTMessage * msg );
    void actionchain_____onAppPortStorageRequest( const UMLRTMessage * msg );
    void actionchain_____onAppPrinterRequest( const UMLRTMessage * msg );
    void actionchain_____onResMgrPrinterRequest( const UMLRTMessage * msg );
    void actionchain_____onResMgrStorageRequest( const UMLRTMessage * msg );
    void actionchain_____onTimeout( const UMLRTMessage * msg );
    State choice_____instantiateOK( const UMLRTMessage * msg );
    State state_____InitFail( const UMLRTMessage * msg );
    State state_____WaitInitTimeout( const UMLRTMessage * msg );
    State state_____WaitingForRequest( const UMLRTMessage * msg );
};
extern const UMLRTCapsuleClass ResourceManager;

#endif

