
#include "Computer.hh"

#include "Resource.hh"
#include "ResourceManager.hh"
#include "USBDeviceDriver.hh"
#include "WordProcessorApp.hh"
#include "umlrtcapsuleclass.hh"
#include "umlrtcapsulepart.hh"
#include "umlrtcommsportrole.hh"
#include "umlrtmessage.hh"
#include "umlrtslot.hh"
#include "umlrttimerprotocol.hh"
#include <cstddef>
#include "umlrtcapsuleid.hh"
#include "umlrtcapsulerole.hh"
#include "umlrtcommsport.hh"
#include "umlrtframeservice.hh"
class UMLRTRtsInterface;

Capsule_Computer::Capsule_Computer( const UMLRTCapsuleClass * cd, UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat )
: UMLRTCapsule( NULL, cd, st, border, internal, isStat )
, frame( borderPorts[borderport_frame] )
, resMgr( internalPorts[internalport_resMgr] )
, timer( borderPorts[borderport_timer] )
, usbPort( borderPorts[borderport_usbPort] )
, userPort( borderPorts[borderport_userPort] )
, application( &slot->parts[part_application] )
, driverManager( &slot->parts[part_driverManager] )
, usbBus( &slot->parts[part_usbBus] )
, currentState( SPECIAL_INTERNAL_STATE_UNVISITED )
{
    stateNames[InitUSBBus] = "InitUSBBus";
    stateNames[InitUSBBus__WaitForPrinter] = "InitUSBBus__WaitForPrinter";
    stateNames[InitUSBBus__WaitForStorage] = "InitUSBBus__WaitForStorage";
    stateNames[InitUSBBus__boundary] = "InitUSBBus__boundary";
    stateNames[Running] = "Running";
    stateNames[WaitForResourceManager] = "WaitForResourceManager";
    stateNames[SPECIAL_INTERNAL_STATE_TOP] = "<top>";
    stateNames[SPECIAL_INTERNAL_STATE_UNVISITED] = "<uninitialized>";
    int i = 0;
    while( i < 1 )
        history[i++] = SPECIAL_INTERNAL_STATE_UNVISITED;
}













void Capsule_Computer::bindPort( bool isBorder, int portId, int index )
{
    if( isBorder )
        switch( portId )
        {
        case borderport_usbPort:
            switch( index )
            {
            case 0:
                UMLRTFrameService::connectRelayPort( borderPorts[borderport_usbPort], index, &slot->parts[part_usbBus].slots[0]->ports[Capsule_USBDeviceDriver::borderport_usbExtPort], 0 );
                UMLRTFrameService::bindSubcapsulePort( true, slot->parts[part_usbBus].slots[0]->capsule, portId, index );
                break;
            case 1:
                UMLRTFrameService::connectRelayPort( borderPorts[borderport_usbPort], index, &slot->parts[part_usbBus].slots[1]->ports[Capsule_USBDeviceDriver::borderport_usbExtPort], 0 );
                UMLRTFrameService::bindSubcapsulePort( true, slot->parts[part_usbBus].slots[1]->capsule, portId, index );
                break;
            }
            break;
        case borderport_userPort:
            switch( index )
            {
            case 0:
                UMLRTFrameService::connectRelayPort( borderPorts[borderport_userPort], index, &slot->parts[part_application].slots[0]->ports[Capsule_WordProcessorApp::borderport_UserControlePort], 0 );
                UMLRTFrameService::bindSubcapsulePort( true, slot->parts[part_application].slots[0]->capsule, portId, index );
                break;
            }
            break;
        }
    else
        UMLRTFrameService::sendBoundUnbound( internalPorts, internalport_resMgr, index, true );
}

void Capsule_Computer::unbindPort( bool isBorder, int portId, int index )
{
    if( isBorder )
        switch( portId )
        {
        case borderport_usbPort:
            switch( index )
            {
            case 0:
                UMLRTFrameService::unbindSubcapsulePort( true, slot->parts[part_usbBus].slots[0]->capsule, portId, index );
                break;
            case 1:
                UMLRTFrameService::unbindSubcapsulePort( true, slot->parts[part_usbBus].slots[1]->capsule, portId, index );
                break;
            }
            break;
        case borderport_userPort:
            switch( index )
            {
            case 0:
                UMLRTFrameService::unbindSubcapsulePort( true, slot->parts[part_application].slots[0]->capsule, portId, index );
                break;
            }
            break;
        }
    else
    {
        UMLRTFrameService::sendBoundUnbound( internalPorts, internalport_resMgr, index, false );
        UMLRTFrameService::disconnectPort( internalPorts[internalport_resMgr], index );
    }
}






void Capsule_Computer::inject( const UMLRTMessage & message )
{
    msg = &message;
    switch( currentState )
    {
    case WaitForResourceManager:
        currentState = state_____WaitForResourceManager( &message );
        break;
    case Running:
        currentState = state_____Running( &message );
        break;
    case InitUSBBus__WaitForPrinter:
        currentState = state_____InitUSBBus__WaitForPrinter( &message );
        break;
    case InitUSBBus__WaitForStorage:
        currentState = state_____InitUSBBus__WaitForStorage( &message );
        break;
    case InitUSBBus__boundary:
        currentState = state_____InitUSBBus__boundary( &message );
        break;
    default:
        break;
    }
}

void Capsule_Computer::initialize( const UMLRTMessage & message )
{
    msg = &message;
    actionchain_____Initialise( &message );
    currentState = WaitForResourceManager;
}

const char * Capsule_Computer::getCurrentStateString() const
{
    return stateNames[currentState];
}





void Capsule_Computer::save_history( Capsule_Computer::State compositeState, Capsule_Computer::State subState )
{
    history[compositeState] = subState;
}

bool Capsule_Computer::check_history( Capsule_Computer::State compositeState, Capsule_Computer::State subState )
{
    return history[compositeState] == subState;
}

void Capsule_Computer::update_state( Capsule_Computer::State newState )
{
    currentState = newState;
}

void Capsule_Computer::entryaction_____InitUSBBus( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/ComputerSystem/ComputerSystem.uml ComputerSystem::Computer::Computer::InitUSBBus entry  */
    time_t currentTime;
    struct tm *localTime;
    time( &currentTime );
    localTime = localtime( &currentTime );
    std::cout << std::setfill('0') << "[" << std::setw(2) << localTime->tm_hour << ":" << std::setw(2) << localTime->tm_min << ":" << std::setw(2) << localTime->tm_sec << "] ";
    std::cout << "[" << this->getName() << "] is in state {InitUSBBus}, waiting for the USB bus to be initialized." << std::endl;
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_Computer::entryaction_____InitUSBBus__WaitForPrinter( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/ComputerSystem/ComputerSystem.uml ComputerSystem::Computer::Computer::InitUSBBus::WaitForPrinter entry  */
    time_t currentTime;
    struct tm *localTime;
    time( &currentTime );
    localTime = localtime( &currentTime );
    std::cout << std::setfill('0') << "[" << std::setw(2) << localTime->tm_hour << ":" << std::setw(2) << localTime->tm_min << ":" << std::setw(2) << localTime->tm_sec << "] ";
    std::cout << "[" << this->getName() << "] ";
    numSec = 10;
    timerId = timer.informIn( UMLRTTimespec( numSec, 0 ) );
    if ( timerId.isValid() ) {
    std::cout << " sets a " << numSec << " seconds timer" << std::endl;
    }
    else {
    std::cout << " FAILed to set a " << numSec << " seconds timer!" << std::endl;
    }
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_Computer::entryaction_____InitUSBBus__WaitForStorage( const UMLRTMessage * msg )
{
    #define resourceID ( *(const UMLRTCapsuleId * )msg->getParam( 0 ) )
    #define rtdata ( (const UMLRTCapsuleId * )msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/ComputerSystem/ComputerSystem.uml ComputerSystem::Computer::Computer::InitUSBBus::WaitForStorage entry  */
    time_t currentTime;
    struct tm *localTime;
    time( &currentTime );
    localTime = localtime( &currentTime );
    std::cout << std::setfill('0') << "[" << std::setw(2) << localTime->tm_hour << ":" << std::setw(2) << localTime->tm_min << ":" << std::setw(2) << localTime->tm_sec << "] ";
    std::cout << "[" << this->getName() << "] is in state {WaitForStorage} ";
    /**
    * Send storage request
    */
    if ( resMgr.requestStorageDriver().send() ) {
    std::cout << "and requested a storage driver";
    } else {
    std::cout << "FAILed to request a storage driver";
    }
    /**
    * Set timeout for storage request
    */
    numSec = 10;
    timerId = timer.informIn( UMLRTTimespec( numSec, 0 ) );
    if ( timerId.isValid() ) {
    std::cout << " set a " << numSec << " seconds timer waiting for a storageDriver." << std::endl;
    }
    else {
    std::cout << " FAILed to set a " << numSec << " seconds timer!" << std::endl;
    }
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
    #undef resourceID
}

void Capsule_Computer::entryaction_____Running( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/ComputerSystem/ComputerSystem.uml ComputerSystem::Computer::Computer::Running entry  */
    time_t currentTime;
    struct tm *localTime;
    time( &currentTime );
    localTime = localtime( &currentTime );
    std::cout << std::setfill('0') << "[" << std::setw(2) << localTime->tm_hour << ":" << std::setw(2) << localTime->tm_min << ":" << std::setw(2) << localTime->tm_sec << "] ";
    std::cout << "[" << this->getName() << "] is in state {Running}." << std::endl;
    //context()->debugOutputModel();
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_Computer::entryaction_____WaitForResourceManager( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/ComputerSystem/ComputerSystem.uml ComputerSystem::Computer::Computer::WaitForResourceManager entry  */
    time_t currentTime ;
    struct tm *localTime;
    time( &currentTime );
    localTime = localtime( &currentTime );
    std::cout << std::setfill('0') << "[" << std::setw(2) << localTime->tm_hour << ":" << std::setw(2) << localTime->tm_min << ":" << std::setw(2) << localTime->tm_sec << "] ";
    std::cout << "[" << this->getName() << "] is in state {WaitForResourceManager}, waiting for resource manager initalization" << std::endl;
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_Computer::transitionaction_____InitUSBBus__onPrintDriverTimeout( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/ComputerSystem/ComputerSystem.uml ComputerSystem::Computer::Computer::InitUSBBus transition InitUSBBus::WaitForPrinter,InitUSBBus::WaitForStorage,timeout:timer */
    time_t currentTime;
    struct tm *localTime;
    time( &currentTime );
    localTime = localtime( &currentTime );
    std::cout << std::setfill('0') << "[" << std::setw(2) << localTime->tm_hour << ":" << std::setw(2) << localTime->tm_min << ":" << std::setw(2) << localTime->tm_sec << "] ";
    std::cout << "[" << this->getName() << "] timed out while waiting for a printerId" << std::endl;
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_Computer::transitionaction_____InitUSBBus__onPrintResourceID( const UMLRTMessage * msg )
{
    #define resourceID ( *(const UMLRTCapsuleId * )msg->getParam( 0 ) )
    #define rtdata ( (const UMLRTCapsuleId * )msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/ComputerSystem/ComputerSystem.uml ComputerSystem::Computer::Computer::InitUSBBus transition InitUSBBus::WaitForPrinter,InitUSBBus::WaitForStorage,resourceID:resMgr */
    time_t currentTime;
    struct tm *localTime;
    time( &currentTime );
    localTime = localtime( &currentTime );
    std::cout << std::setfill('0') << "[" << std::setw(2) << localTime->tm_hour << ":" << std::setw(2) << localTime->tm_min << ":" << std::setw(2) << localTime->tm_sec << "] ";
    std::cout << "[" << this->getName() << "] ";
    if ( timer.cancelTimer( timerId ) ) {
    std::cout << "timer canceled";
    } else {
    std::cout << "ERROR canceling timer";
    }
    printerID = (UMLRTCapsuleId) *rtdata;
    if ( printerID.isValid() ) {
    if ( frame.import( printerID, usbBus,  0) ) {
    std::cout << " and successfully imported the printer driver" << std::endl;
    } else {
    std::cout << " and FAILed to import the printer driver" << std::endl;
    }
    } else {
    std::cout << "[" << this->getName() << "] and received an INVALID printerID!" << std::endl;
    }
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
    #undef resourceID
}

void Capsule_Computer::transitionaction_____InitUSBBus__onStorageResourceID( const UMLRTMessage * msg )
{
    #define resourceID ( *(const UMLRTCapsuleId * )msg->getParam( 0 ) )
    #define rtdata ( (const UMLRTCapsuleId * )msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/ComputerSystem/ComputerSystem.uml ComputerSystem::Computer::Computer::InitUSBBus transition InitUSBBus::WaitForStorage,InitUSBBus::EX_initUSB,resourceID:resMgr */
    time_t currentTime;
    struct tm *localTime;
    time( &currentTime );
    localTime = localtime( &currentTime );
    std::cout << std::setfill('0') << "[" << std::setw(2) << localTime->tm_hour << ":" << std::setw(2) << localTime->tm_min << ":" << std::setw(2) << localTime->tm_sec << "] ";
    std::cout << "[" << this->getName() << "] ";
    if ( timer.cancelTimer( timerId ) ) {
    std::cout << "timer canceled";
    } else {
    std::cout << "ERROR canceling timer";
    }
    storageID = (UMLRTCapsuleId) *rtdata;
    if ( storageID.isValid() ) {
    if ( frame.import( storageID, usbBus, 1)) {
    std::cout << " and storage is now available." << std::endl;
    } else {
    std::cout << " but FAILed to import storage driver!" << std::endl;
    }
    } else {
    std::cout << "[" << this->getName() << "but received INVALID storageID!" << std::endl;
    }
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
    #undef resourceID
}

void Capsule_Computer::transitionaction_____InitUSBBus__onStorageTimeout( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/ComputerSystem/ComputerSystem.uml ComputerSystem::Computer::Computer::InitUSBBus transition InitUSBBus::WaitForStorage,InitUSBBus::EX_initUSB */
    time_t currentTime;
    struct tm *localTime;
    time( &currentTime );
    localTime = localtime( &currentTime );
    std::cout << std::setfill('0') << std::setw(2) << localTime->tm_hour << ":" << std::setw(2) << localTime->tm_min << ":" << std::setw(2) << localTime->tm_sec << "] ";
    std::cout << "[" << this->getName() << " timed out waiting to receive a storageId" << std::endl;
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_Computer::transitionaction_____Initialise( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/ComputerSystem/ComputerSystem.uml ComputerSystem::Computer::Computer transition Initial1,WaitForResourceManager */
    time_t currentTime;
    struct tm *localTime;
    time( &currentTime );
    localTime = localtime( &currentTime );
    std::cout << std::setfill('0') << "[" << std::setw(2) << localTime->tm_hour << ":" << std::setw(2) << localTime->tm_min << ":" << std::setw(2) << localTime->tm_sec << "] ";
    std::cout << "[" << this->getName() << "] initializing" << std::endl;
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_Computer::transitionaction_____onResMgrRunning( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/ComputerSystem/ComputerSystem.uml ComputerSystem::Computer::Computer transition WaitForResourceManager,InitUSBBus::EN_initUSB,resMgrRunning:resMgr */
    time_t currentTime;
    struct tm *localTime;
    time( &currentTime );
    localTime = localtime( &currentTime );
    std::cout << std::setfill('0') << "[" << std::setw(2) << localTime->tm_hour << ":" << std::setw(2) << localTime->tm_min << ":" << std::setw(2) << localTime->tm_sec << "] ";
    std::cout << "[" << this->getName() << "::{resMgrRunning}] is now accepting requests ";
    if ( resMgr.requestPrinterDriver().send() ) {
    std::cout << " -- requesting for a printer driver." << std::endl;
    } else {
    std::cout << " -- FAILed to request a printer driver!" << std::endl;
    }
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_Computer::actionchain_____EX_InitUSB_Continuation( const UMLRTMessage * msg )
{
    update_state( SPECIAL_INTERNAL_STATE_TOP );
    update_state( Running );
    entryaction_____Running( msg );
}

void Capsule_Computer::actionchain_____InitUSBBus__EN_initUSB_Continuation( const UMLRTMessage * msg )
{
    update_state( InitUSBBus__WaitForPrinter );
    entryaction_____InitUSBBus__WaitForPrinter( msg );
}

void Capsule_Computer::actionchain_____InitUSBBus__new_transition_1( const UMLRTMessage * msg )
{
    update_state( InitUSBBus__WaitForPrinter );
    entryaction_____InitUSBBus__WaitForPrinter( msg );
}

void Capsule_Computer::actionchain_____InitUSBBus__new_transition_2( const UMLRTMessage * msg )
{
    update_state( InitUSBBus__WaitForStorage );
    entryaction_____InitUSBBus__WaitForStorage( msg );
}

void Capsule_Computer::actionchain_____InitUSBBus__new_transition_3_to_unvisited_boundary( const UMLRTMessage * msg )
{
    update_state( InitUSBBus__boundary );
}

void Capsule_Computer::actionchain_____InitUSBBus__new_transition_4_to_visited_boundary( const UMLRTMessage * msg )
{
    update_state( InitUSBBus__boundary );
}

void Capsule_Computer::actionchain_____InitUSBBus__onPrintDriverTimeout( const UMLRTMessage * msg )
{
    update_state( InitUSBBus );
    transitionaction_____InitUSBBus__onPrintDriverTimeout( msg );
    update_state( InitUSBBus__WaitForStorage );
    entryaction_____InitUSBBus__WaitForStorage( msg );
}

void Capsule_Computer::actionchain_____InitUSBBus__onPrintResourceID( const UMLRTMessage * msg )
{
    update_state( InitUSBBus );
    transitionaction_____InitUSBBus__onPrintResourceID( msg );
    update_state( InitUSBBus__WaitForStorage );
    entryaction_____InitUSBBus__WaitForStorage( msg );
}

void Capsule_Computer::actionchain_____InitUSBBus__onStorageResourceID( const UMLRTMessage * msg )
{
    update_state( InitUSBBus );
    transitionaction_____InitUSBBus__onStorageResourceID( msg );
    save_history( InitUSBBus, InitUSBBus__WaitForStorage );
}

void Capsule_Computer::actionchain_____InitUSBBus__onStorageTimeout( const UMLRTMessage * msg )
{
    update_state( InitUSBBus );
    transitionaction_____InitUSBBus__onStorageTimeout( msg );
    save_history( InitUSBBus, InitUSBBus__WaitForStorage );
}

void Capsule_Computer::actionchain_____Initialise( const UMLRTMessage * msg )
{
    transitionaction_____Initialise( msg );
    update_state( WaitForResourceManager );
    entryaction_____WaitForResourceManager( msg );
}

void Capsule_Computer::actionchain_____onResMgrRunning( const UMLRTMessage * msg )
{
    update_state( SPECIAL_INTERNAL_STATE_TOP );
    transitionaction_____onResMgrRunning( msg );
    update_state( InitUSBBus );
    entryaction_____InitUSBBus( msg );
}

Capsule_Computer::State Capsule_Computer::junction_____InitUSBBus__EN_initUSB( const UMLRTMessage * msg )
{
    actionchain_____InitUSBBus__EN_initUSB_Continuation( msg );
    return InitUSBBus__WaitForPrinter;
}

Capsule_Computer::State Capsule_Computer::junction_____InitUSBBus__EX_initUSB( const UMLRTMessage * msg )
{
    actionchain_____EX_InitUSB_Continuation( msg );
    return Running;
}

Capsule_Computer::State Capsule_Computer::choice_____InitUSBBus__deephistory( const UMLRTMessage * msg )
{
    if( check_history( InitUSBBus, InitUSBBus__WaitForPrinter ) )
    {
        actionchain_____InitUSBBus__new_transition_1( msg );
        return InitUSBBus__WaitForPrinter;
    }
    else if( check_history( InitUSBBus, InitUSBBus__WaitForStorage ) )
    {
        actionchain_____InitUSBBus__new_transition_2( msg );
        return InitUSBBus__WaitForStorage;
    }
    else if( check_history( InitUSBBus, SPECIAL_INTERNAL_STATE_UNVISITED ) )
    {
        actionchain_____InitUSBBus__new_transition_3_to_unvisited_boundary( msg );
        return InitUSBBus__boundary;
    }
    else if( check_history( InitUSBBus, InitUSBBus__boundary ) )
    {
        actionchain_____InitUSBBus__new_transition_4_to_visited_boundary( msg );
        return InitUSBBus__boundary;
    }
    return currentState;
}

Capsule_Computer::State Capsule_Computer::state_____InitUSBBus__WaitForPrinter( const UMLRTMessage * msg )
{
    switch( msg->destPort->role()->id )
    {
    case port_resMgr:
        switch( msg->getSignalId() )
        {
        case Resource::signal_resourceID:
            actionchain_____InitUSBBus__onPrintResourceID( msg );
            return InitUSBBus__WaitForStorage;
        default:
            this->unexpectedMessage();
            break;
        }
        return currentState;
    case port_timer:
        switch( msg->getSignalId() )
        {
        case UMLRTTimerProtocol::signal_timeout:
            actionchain_____InitUSBBus__onPrintDriverTimeout( msg );
            return InitUSBBus__WaitForStorage;
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

Capsule_Computer::State Capsule_Computer::state_____InitUSBBus__WaitForStorage( const UMLRTMessage * msg )
{
    switch( msg->destPort->role()->id )
    {
    case port_resMgr:
        switch( msg->getSignalId() )
        {
        case Resource::signal_resourceID:
            actionchain_____InitUSBBus__onStorageResourceID( msg );
            return junction_____InitUSBBus__EX_initUSB( msg );
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

Capsule_Computer::State Capsule_Computer::state_____InitUSBBus__boundary( const UMLRTMessage * msg )
{
    switch( msg->destPort->role()->id )
    {
    default:
        this->unexpectedMessage();
        break;
    }
    return currentState;
}

Capsule_Computer::State Capsule_Computer::state_____Running( const UMLRTMessage * msg )
{
    switch( msg->destPort->role()->id )
    {
    default:
        this->unexpectedMessage();
        break;
    }
    return currentState;
}

Capsule_Computer::State Capsule_Computer::state_____WaitForResourceManager( const UMLRTMessage * msg )
{
    switch( msg->destPort->role()->id )
    {
    case port_resMgr:
        switch( msg->getSignalId() )
        {
        case Resource::signal_resMgrRunning:
            actionchain_____onResMgrRunning( msg );
            return junction_____InitUSBBus__EN_initUSB( msg );
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
        "application",
        &WordProcessorApp,
        1,
        1,
        false,
        false
    },
    {
        "driverManager",
        &ResourceManager,
        1,
        1,
        false,
        false
    },
    {
        "usbBus",
        &USBDeviceDriver,
        0,
        2,
        false,
        true
    }
};

static const UMLRTCommsPortRole portroles_border[] = 
{
    {
        Capsule_Computer::port_frame,
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
        Capsule_Computer::port_timer,
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
    },
    {
        Capsule_Computer::port_usbPort,
        "USBProtocol",
        "usbPort",
        "",
        2,
        true,
        true,
        false,
        false,
        false,
        false,
        true
    },
    {
        Capsule_Computer::port_userPort,
        "AppControl",
        "userPort",
        "",
        1,
        true,
        false,
        false,
        true,
        false,
        false,
        true
    }
};

static const UMLRTCommsPortRole portroles_internal[] = 
{
    {
        Capsule_Computer::port_resMgr,
        "Resource",
        "resMgr",
        "",
        1,
        true,
        true,
        false,
        false,
        false,
        false,
        true
    }
};

static void instantiate_Computer( const UMLRTRtsInterface * rts, UMLRTSlot * slot, const UMLRTCommsPort * * borderPorts )
{
    const UMLRTCommsPort * * internalPorts = UMLRTFrameService::createInternalPorts( slot, &Computer );
    UMLRTFrameService::connectPorts( internalPorts[Capsule_Computer::internalport_resMgr], 0, &slot->parts[Capsule_Computer::part_driverManager].slots[0]->ports[Capsule_ResourceManager::borderport_resMgr], 0 );
    UMLRTFrameService::connectRelayPort( borderPorts[Capsule_Computer::borderport_usbPort], 0, &slot->parts[Capsule_Computer::part_usbBus].slots[0]->ports[Capsule_USBDeviceDriver::borderport_usbExtPort], 0 );
    UMLRTFrameService::connectRelayPort( borderPorts[Capsule_Computer::borderport_usbPort], 1, &slot->parts[Capsule_Computer::part_usbBus].slots[1]->ports[Capsule_USBDeviceDriver::borderport_usbExtPort], 0 );
    UMLRTFrameService::connectRelayPort( borderPorts[Capsule_Computer::borderport_userPort], 0, &slot->parts[Capsule_Computer::part_application].slots[0]->ports[Capsule_WordProcessorApp::borderport_UserControlePort], 0 );
    UMLRTFrameService::connectPorts( &slot->parts[Capsule_Computer::part_application].slots[0]->ports[Capsule_WordProcessorApp::borderport_resourcePort], 0, &slot->parts[Capsule_Computer::part_driverManager].slots[0]->ports[Capsule_ResourceManager::borderport_appPort], 0 );
    WordProcessorApp.instantiate( NULL, slot->parts[Capsule_Computer::part_application].slots[0], UMLRTFrameService::createBorderPorts( slot->parts[Capsule_Computer::part_application].slots[0], WordProcessorApp.numPortRolesBorder ) );
    ResourceManager.instantiate( NULL, slot->parts[Capsule_Computer::part_driverManager].slots[0], UMLRTFrameService::createBorderPorts( slot->parts[Capsule_Computer::part_driverManager].slots[0], ResourceManager.numPortRolesBorder ) );
    slot->capsule = new Capsule_Computer( &Computer, slot, borderPorts, internalPorts, false );
}

const UMLRTCapsuleClass Computer = 
{
    "Computer",
    NULL,
    instantiate_Computer,
    3,
    roles,
    4,
    portroles_border,
    1,
    portroles_internal
};

