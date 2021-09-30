
#include "ResourceManager.hh"

#include "Resource.hh"
#include "USBDeviceDriver.hh"
#include "umlrtcommsportrole.hh"
#include "umlrtmessage.hh"
#include "umlrtslot.hh"
#include "umlrttimerprotocol.hh"
#include <cstddef>
#include "umlrtcapsuleclass.hh"
#include "umlrtcapsulepart.hh"
#include "umlrtcapsulerole.hh"
#include "umlrtframeservice.hh"
class UMLRTRtsInterface;
struct UMLRTCommsPort;

Capsule_ResourceManager::Capsule_ResourceManager( const UMLRTCapsuleClass * cd, UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat )
: UMLRTCapsule( NULL, cd, st, border, internal, isStat )
, appPort( borderPorts[borderport_appPort] )
, frame( borderPorts[borderport_frame] )
, resMgr( borderPorts[borderport_resMgr] )
, timer( borderPorts[borderport_timer] )
, usbPrinterDriver( &slot->parts[part_usbPrinterDriver] )
, usbStorageDriver( &slot->parts[part_usbStorageDriver] )
, printerRequestCount( 0 )
, storageRequestCount( 0 )
, currentState( SPECIAL_INTERNAL_STATE_UNVISITED )
{
    stateNames[InitFail] = "InitFail";
    stateNames[WaitInitTimeout] = "WaitInitTimeout";
    stateNames[WaitingForRequest] = "WaitingForRequest";
    stateNames[SPECIAL_INTERNAL_STATE_TOP] = "<top>";
    stateNames[SPECIAL_INTERNAL_STATE_UNVISITED] = "<uninitialized>";
}










void Capsule_ResourceManager::bindPort( bool isBorder, int portId, int index )
{
    if( isBorder )
        switch( portId )
        {
        case borderport_appPort:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_appPort, index, true );
            break;
        case borderport_resMgr:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_resMgr, index, true );
            break;
        }
}

void Capsule_ResourceManager::unbindPort( bool isBorder, int portId, int index )
{
    if( isBorder )
        switch( portId )
        {
        case borderport_appPort:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_appPort, index, false );
            UMLRTFrameService::disconnectPort( borderPorts[borderport_appPort], index );
            break;
        case borderport_resMgr:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_resMgr, index, false );
            UMLRTFrameService::disconnectPort( borderPorts[borderport_resMgr], index );
            break;
        }
}









void Capsule_ResourceManager::inject( const UMLRTMessage & message )
{
    msg = &message;
    switch( currentState )
    {
    case WaitingForRequest:
        currentState = state_____WaitingForRequest( &message );
        break;
    case InitFail:
        currentState = state_____InitFail( &message );
        break;
    case WaitInitTimeout:
        currentState = state_____WaitInitTimeout( &message );
        break;
    default:
        break;
    }
}

void Capsule_ResourceManager::initialize( const UMLRTMessage & message )
{
    msg = &message;
    actionchain_____initialize( &message );
    currentState = choice_____instantiateOK( &message );
}

const char * Capsule_ResourceManager::getCurrentStateString() const
{
    return stateNames[currentState];
}




void Capsule_ResourceManager::update_state( Capsule_ResourceManager::State newState )
{
    currentState = newState;
}

void Capsule_ResourceManager::entryaction_____InitFail( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/ComputerSystem/ComputerSystem.uml ComputerSystem::Computer::ResourceManager::InitFail entry  */
    std::cout << "[" << this->getName() << "] is in state {InitFail}" << std::endl;
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_ResourceManager::entryaction_____WaitInitTimeout( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/ComputerSystem/ComputerSystem.uml ComputerSystem::Computer::ResourceManager::WaitInitTimeout entry  */
    time_t currentTime;
    struct tm *localTime;
    time( &currentTime );
    localTime = localtime( &currentTime );
    std::cout << std::setfill('0') << "[" << std::setw(2) << localTime->tm_hour << ":" << std::setw(2) << localTime->tm_min << ":" << std::setw(2) << localTime->tm_sec << "] ";
    std::cout << "[" << this->getName() << "] is in state {WaitInitTimeout}" << std::endl;
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_ResourceManager::entryaction_____WaitingForRequest( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/ComputerSystem/ComputerSystem.uml ComputerSystem::Computer::ResourceManager::WaitingForRequest entry  */
    time_t currentTime;
    struct tm *localTime;
    time( &currentTime );
    localTime = localtime( &currentTime );
    std::cout << std::setfill('0') << "[" << std::setw(2) << localTime->tm_hour << ":" << std::setw(2) << localTime->tm_min << ":" << std::setw(2) << localTime->tm_sec << "] ";
    std::cout << "[" << this->getName() << "] is in state {WaitingForRequest},  waiting for a request" << std::endl;
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_ResourceManager::exitaction_____WaitingForRequest( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/ComputerSystem/ComputerSystem.uml ComputerSystem::Computer::ResourceManager::WaitingForRequest exit  */
    time_t currentTime;
    struct tm *localTime;
    time( &currentTime );
    localTime = localtime( &currentTime );
    std::cout << std::setfill('0') << "[" << std::setw(2) << localTime->tm_hour << ":" << std::setw(2) << localTime->tm_min << ":" << std::setw(2) << localTime->tm_sec << "] ";
    std::cout << "[" << this->getName() << "] received a request!" << std::endl;
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_ResourceManager::transitionaction_____NOK( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/ComputerSystem/ComputerSystem.uml ComputerSystem::Computer::ResourceManager transition instantiateOK,InitFail */
    time_t currentTime;
    struct tm *localTime;
    time( &currentTime );
    localTime = localtime( &currentTime );
    std::cout << std::setfill('0') << "[" << std::setw(2) << localTime->tm_hour << ":" << std::setw(2) << localTime->tm_min << ":" << std::setw(2) << localTime->tm_sec << "] ";
    std::cout << "[" << this->getName() << "] FAILed to initialize!" << std::endl;
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_ResourceManager::transitionaction_____OK( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/ComputerSystem/ComputerSystem.uml ComputerSystem::Computer::ResourceManager transition instantiateOK,WaitInitTimeout */
    time_t currentTime;
    struct tm *localTime;
    time( &currentTime );
    localTime = localtime( &currentTime );
    std::cout << std::setfill('0') << "[" << std::setw(2) << localTime->tm_hour << ":" << std::setw(2) << localTime->tm_min << ":" << std::setw(2) << localTime->tm_sec << "] ";
    std::cout << "[" << this->getName() << "] drivers instantiated." << std::endl;
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_ResourceManager::transitionaction_____initialize( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/ComputerSystem/ComputerSystem.uml ComputerSystem::Computer::ResourceManager transition Initial1,instantiateOK */
    time_t currentTime;
    struct tm *localTime;
    time( &currentTime );
    localTime = localtime( &currentTime );
    std::cout << std::setfill('0') << "[" << std::setw(2) << localTime->tm_hour << ":" << std::setw(2) << localTime->tm_min << ":" << std::setw(2) << localTime->tm_sec << "] ";
    std::cout << "[" << this->getName() << "] initializing";
    /**
    * In this version, we will initialize just the resources that are required for the demo.
    * This capsule could be made more intelligent by implemented managed pools.
    */
    bool localPrinterStatus;
    bool localStorageStatus;
    bool localTimerStatus;
    printerID = frame.incarnate(usbPrinterDriver, USBPrinterDriver);
    localPrinterStatus = printerID.isValid();
    if ( localPrinterStatus ) {
    std::cout << " - printer driver ready" << std::endl;
    } else {
    std::cout << " - ERROR instantiating printer driver" << std::endl;
    }
    time( &currentTime );
    localTime = localtime( &currentTime );
    std::cout << std::setfill('0') << "[" << std::setw(2) << localTime->tm_hour << ":" << std::setw(2) << localTime->tm_min << ":" << std::setw(2) << localTime->tm_sec << "] ";
    std::cout << "[" << this->getName() << "] initializing";
    storageID = frame.incarnate(usbStorageDriver, USBStorageDriver);
    localStorageStatus = storageID.isValid();
    if ( localStorageStatus ) {
    std::cout << " - storage driver ready" << std::endl;
    } else {
    std::cout << " - ERROR instantiating storage driver" << std::endl;
    }
    status = ( localPrinterStatus && localStorageStatus);
    if (status) {
    time( &currentTime );
    localTime = localtime( &currentTime );
    std::cout << std::setfill('0') << "[" << std::setw(2) << localTime->tm_hour << ":" << std::setw(2) << localTime->tm_min << ":" << std::setw(2) << localTime->tm_sec << "] ";
    std::cout << "[" << this->getName() << "] initializing - ";
    numSec = 10;
    timerID = timer.informIn(UMLRTTimespec(numSec,0));
    localTimerStatus = timerID.isValid();
    if ( ! localTimerStatus ) {
    std::cout << "FAILED to ";
    }
    std::cout << "set a " << numSec << " seconds timer for drivers initialization." << std::endl;
    }
    status = ( localPrinterStatus && localStorageStatus && localTimerStatus);
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_ResourceManager::transitionaction_____onAppPortStorageRequest( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/ComputerSystem/ComputerSystem.uml ComputerSystem::Computer::ResourceManager transition WaitingForRequest,WaitingForRequest,requestStorageDriver:appPort */
    time_t currentTime;
    struct tm *localTime;
    time( &currentTime );
    localTime = localtime( &currentTime );
    std::cout << std::setfill('0') << "[" << std::setw(2) << localTime->tm_hour << ":" << std::setw(2) << localTime->tm_min << ":" << std::setw(2) << localTime->tm_sec << "] ";
    std::cout << "[" << this->getName() << "] storage resource requested by App,";
    if ( storageID.isValid() ) {
    if ( appPort.resourceID( storageID ).send() ) {
    std::cout << " and sent." << std::endl;
    } else {
    std::cout << " but ERROR sending storage resource!" << std::endl;
    }
    } else {
    if ( appPort.resNotAvail().send() ) {
    std::cout << " but storage resource not available sent." << std::endl;
    } else {
    std::cout << "but ERROR sending storage resource not available!" << std::endl;
    }
    }
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_ResourceManager::transitionaction_____onAppPrinterRequest( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/ComputerSystem/ComputerSystem.uml ComputerSystem::Computer::ResourceManager transition WaitingForRequest,WaitingForRequest,requestPrinterDriver:appPort */
    time_t currentTime;
    struct tm *localTime;
    time( &currentTime );
    localTime = localtime( &currentTime );
    std::cout << std::setfill('0') << "[" << std::setw(2) << localTime->tm_hour << ":" << std::setw(2) << localTime->tm_min << ":" << std::setw(2) << localTime->tm_sec << "] ";
    std::cout << "[" << this->getName() << "] printer resource requested by App,";
    if ( printerID.isValid() ) {
    if ( resMgr.resourceID( printerID ).send() ) {
    std::cout << " and sent." << std::endl;
    } else {
    std::cout << " but got an ERROR sending!" << std::endl;
    }
    } else {
    if ( resMgr.resNotAvail().send() ) {
    std::cout << "[ but printer resource is not available sent" << std::endl;
    } else {
    std::cout << "but ERROR sending printer resource not available" << std::endl;
    }
    }
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_ResourceManager::transitionaction_____onResMgrPrinterRequest( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/ComputerSystem/ComputerSystem.uml ComputerSystem::Computer::ResourceManager transition WaitingForRequest,WaitingForRequest,requestPrinterDriver:resMgr */
    time_t currentTime;
    struct tm *localTime;
    time( &currentTime );
    localTime = localtime( &currentTime );
    std::cout << std::setfill('0') << "[" << std::setw(2) << localTime->tm_hour << ":" << std::setw(2) << localTime->tm_min << ":" << std::setw(2) << localTime->tm_sec << "] ";
    std::cout << "[" << this->getName() << "] printer resource requested by computer,";
    if ( printerID.isValid() ) {
    if ( resMgr.resourceID( printerID ).send() ) {
    std::cout << " and sent." << std::endl;
    } else {
    std::cout << " but ERROR sending printer resource!" << std::endl;
    }
    } else {
    if ( resMgr.resNotAvail().send() ) {
    std::cout << " but printer resource not available sent." << std::endl;
    } else {
    std::cout << " but ERROR sending printer resource not available!" << std::endl;
    }
    }
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_ResourceManager::transitionaction_____onResMgrStorageRequest( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/ComputerSystem/ComputerSystem.uml ComputerSystem::Computer::ResourceManager transition WaitingForRequest,WaitingForRequest,requestStorageDriver:resMgr */
    time_t currentTime;
    struct tm *localTime;
    time( &currentTime );
    localTime = localtime( &currentTime );
    std::cout << std::setfill('0') << "[" << std::setw(2) << localTime->tm_hour << ":" << std::setw(2) << localTime->tm_min << ":" << std::setw(2) << localTime->tm_sec << "] ";
    std::cout << "[" << this->getName() << "] storage resource requested by Computer,";
    if ( storageID.isValid() ) {
    if ( resMgr.resourceID( storageID ).send() ) {
    std::cout << " and sent." << std::endl;
    } else {
    std::cout << " but ERROR sending storage resource!" << std::endl;
    }
    } else {
    if ( resMgr.resNotAvail().send() ) {
    std::cout << " but storage resource not available sent." << std::endl;
    } else {
    std::cout << " but ERROR sending storage resource not available" << std::endl;
    }
    }
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_ResourceManager::transitionaction_____onTimeout( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/ComputerSystem/ComputerSystem.uml ComputerSystem::Computer::ResourceManager transition WaitInitTimeout,WaitingForRequest,timeout:timer */
    time_t currentTime;
    struct tm *localTime;
    time( &currentTime );
    localTime = localtime( &currentTime );
    std::cout << std::setfill('0') << "[" << std::setw(2) << localTime->tm_hour << ":" << std::setw(2) << localTime->tm_min << ":" << std::setw(2) << localTime->tm_sec << "] ";
    /**
    * Telling the world that the ResourceManager is taking requests!
    */
    std::cout << "[" << this->getName() << "] ";
    if ( resMgr.resMgrRunning().send() ) {
    std::cout << "told ";
    } else {
    std::cout << "FAILED to tell ";
    }
    std::cout << "its clients that it is taking requests." << std::endl;
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

bool Capsule_ResourceManager::guard_____NOK( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/ComputerSystem/ComputerSystem.uml ComputerSystem::Computer::ResourceManager guard instantiateOK,InitFail */
    return ( ! status );
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

bool Capsule_ResourceManager::guard_____OK( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/ComputerSystem/ComputerSystem.uml ComputerSystem::Computer::ResourceManager guard instantiateOK,WaitInitTimeout */
    return( status );
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_ResourceManager::actionchain_____NOK( const UMLRTMessage * msg )
{
    transitionaction_____NOK( msg );
    update_state( InitFail );
    entryaction_____InitFail( msg );
}

void Capsule_ResourceManager::actionchain_____OK( const UMLRTMessage * msg )
{
    transitionaction_____OK( msg );
    update_state( WaitInitTimeout );
    entryaction_____WaitInitTimeout( msg );
}

void Capsule_ResourceManager::actionchain_____initialize( const UMLRTMessage * msg )
{
    transitionaction_____initialize( msg );
}

void Capsule_ResourceManager::actionchain_____onAppPortStorageRequest( const UMLRTMessage * msg )
{
    exitaction_____WaitingForRequest( msg );
    update_state( SPECIAL_INTERNAL_STATE_TOP );
    transitionaction_____onAppPortStorageRequest( msg );
    update_state( WaitingForRequest );
    entryaction_____WaitingForRequest( msg );
}

void Capsule_ResourceManager::actionchain_____onAppPrinterRequest( const UMLRTMessage * msg )
{
    exitaction_____WaitingForRequest( msg );
    update_state( SPECIAL_INTERNAL_STATE_TOP );
    transitionaction_____onAppPrinterRequest( msg );
    update_state( WaitingForRequest );
    entryaction_____WaitingForRequest( msg );
}

void Capsule_ResourceManager::actionchain_____onResMgrPrinterRequest( const UMLRTMessage * msg )
{
    exitaction_____WaitingForRequest( msg );
    update_state( SPECIAL_INTERNAL_STATE_TOP );
    transitionaction_____onResMgrPrinterRequest( msg );
    update_state( WaitingForRequest );
    entryaction_____WaitingForRequest( msg );
}

void Capsule_ResourceManager::actionchain_____onResMgrStorageRequest( const UMLRTMessage * msg )
{
    exitaction_____WaitingForRequest( msg );
    update_state( SPECIAL_INTERNAL_STATE_TOP );
    transitionaction_____onResMgrStorageRequest( msg );
    update_state( WaitingForRequest );
    entryaction_____WaitingForRequest( msg );
}

void Capsule_ResourceManager::actionchain_____onTimeout( const UMLRTMessage * msg )
{
    update_state( SPECIAL_INTERNAL_STATE_TOP );
    transitionaction_____onTimeout( msg );
    update_state( WaitingForRequest );
    entryaction_____WaitingForRequest( msg );
}

Capsule_ResourceManager::State Capsule_ResourceManager::choice_____instantiateOK( const UMLRTMessage * msg )
{
    if( guard_____OK( msg ) )
    {
        actionchain_____OK( msg );
        return WaitInitTimeout;
    }
    else if( guard_____NOK( msg ) )
    {
        actionchain_____NOK( msg );
        return InitFail;
    }
    return currentState;
}

Capsule_ResourceManager::State Capsule_ResourceManager::state_____InitFail( const UMLRTMessage * msg )
{
    switch( msg->destPort->role()->id )
    {
    default:
        this->unexpectedMessage();
        break;
    }
    return currentState;
}

Capsule_ResourceManager::State Capsule_ResourceManager::state_____WaitInitTimeout( const UMLRTMessage * msg )
{
    switch( msg->destPort->role()->id )
    {
    case port_timer:
        switch( msg->getSignalId() )
        {
        case UMLRTTimerProtocol::signal_timeout:
            actionchain_____onTimeout( msg );
            return WaitingForRequest;
        default:
            this->unexpectedMessage();
            break;
        }
        return currentState;
    default:
        this->unexpectedMessage();
        break;
    }
    return currentState;
}

Capsule_ResourceManager::State Capsule_ResourceManager::state_____WaitingForRequest( const UMLRTMessage * msg )
{
    switch( msg->destPort->role()->id )
    {
    case port_resMgr:
        switch( msg->getSignalId() )
        {
        case Resource::signal_requestPrinterDriver:
            actionchain_____onResMgrPrinterRequest( msg );
            return WaitingForRequest;
        case Resource::signal_requestStorageDriver:
            actionchain_____onResMgrStorageRequest( msg );
            return WaitingForRequest;
        default:
            this->unexpectedMessage();
            break;
        }
        return currentState;
    case port_appPort:
        switch( msg->getSignalId() )
        {
        case Resource::signal_requestPrinterDriver:
            actionchain_____onAppPrinterRequest( msg );
            return WaitingForRequest;
        case Resource::signal_requestStorageDriver:
            actionchain_____onAppPortStorageRequest( msg );
            return WaitingForRequest;
        default:
            this->unexpectedMessage();
            break;
        }
        return currentState;
    default:
        this->unexpectedMessage();
        break;
    }
    return currentState;
}


static const UMLRTCapsuleRole roles[] = 
{
    {
        "usbPrinterDriver",
        &USBDeviceDriver,
        0,
        1,
        true,
        false
    },
    {
        "usbStorageDriver",
        &USBDeviceDriver,
        0,
        1,
        true,
        false
    }
};

static const UMLRTCommsPortRole portroles_border[] = 
{
    {
        Capsule_ResourceManager::port_resMgr,
        "Resource",
        "resMgr",
        "",
        1,
        true,
        false,
        false,
        false,
        false,
        false,
        true
    },
    {
        Capsule_ResourceManager::port_appPort,
        "Resource",
        "appPort",
        "",
        1,
        true,
        false,
        false,
        false,
        false,
        false,
        true
    },
    {
        Capsule_ResourceManager::port_frame,
        "Frame",
        "frame",
        "",
        0,
        false,
        false,
        false,
        false,
        false,
        false,
        false
    },
    {
        Capsule_ResourceManager::port_timer,
        "Timing",
        "timer",
        "",
        0,
        false,
        false,
        false,
        false,
        false,
        false,
        false
    }
};

static void instantiate_ResourceManager( const UMLRTRtsInterface * rts, UMLRTSlot * slot, const UMLRTCommsPort * * borderPorts )
{
    slot->capsule = new Capsule_ResourceManager( &ResourceManager, slot, borderPorts, NULL, false );
}

const UMLRTCapsuleClass ResourceManager = 
{
    "ResourceManager",
    NULL,
    instantiate_ResourceManager,
    2,
    roles,
    4,
    portroles_border,
    0,
    NULL
};

