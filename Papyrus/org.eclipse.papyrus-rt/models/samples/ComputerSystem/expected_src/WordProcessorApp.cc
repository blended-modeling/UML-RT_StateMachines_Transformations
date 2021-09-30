
#include "WordProcessorApp.hh"

#include "AppControl.hh"
#include "Application.hh"
#include "Resource.hh"
#include "USBDeviceDriver.hh"
#include "USBProtocol.hh"
#include "umlrtcapsuleclass.hh"
#include "umlrtcapsuleid.hh"
#include "umlrtcapsulepart.hh"
#include "umlrtcapsulerole.hh"
#include "umlrtcommsport.hh"
#include "umlrtcommsportrole.hh"
#include "umlrtframeservice.hh"
#include "umlrtmessage.hh"
#include "umlrtslot.hh"
#include <cstddef>
class UMLRTRtsInterface;

Capsule_WordProcessorApp::Capsule_WordProcessorApp( const UMLRTCapsuleClass * cd, UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat )
: Capsule_Application( cd, st, border, internal, isStat )
, UserControlePort( borderPorts[borderport_UserControlePort] )
, frame( borderPorts[borderport_frame] )
, resourcePort( borderPorts[borderport_resourcePort] )
, usbPort( borderPorts[borderport_usbPort] )
, deviceInterface( &slot->parts[part_deviceInterface] )
, apptype( WordProcessor )
, currentState( SPECIAL_INTERNAL_STATE_UNVISITED )
{
    stateNames[Printing] = "Printing";
    stateNames[Saving] = "Saving";
    stateNames[Printing__Printing] = "Printing__Printing";
    stateNames[Printing__boundary] = "Printing__boundary";
    stateNames[Printing__waitForPrinter] = "Printing__waitForPrinter";
    stateNames[Saving__Saving] = "Saving__Saving";
    stateNames[Saving__WaitForStorage] = "Saving__WaitForStorage";
    stateNames[Saving__boundary] = "Saving__boundary";
    stateNames[WaitingForCommand] = "WaitingForCommand";
    stateNames[SPECIAL_INTERNAL_STATE_TOP] = "<top>";
    stateNames[SPECIAL_INTERNAL_STATE_UNVISITED] = "<uninitialized>";
    int i = 0;
    while( i < 2 )
        history[i++] = SPECIAL_INTERNAL_STATE_UNVISITED;
}










void Capsule_WordProcessorApp::bindPort( bool isBorder, int portId, int index )
{
    if( isBorder )
        switch( portId )
        {
        case borderport_UserControlePort:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_UserControlePort, index, true );
            break;
        case borderport_resourcePort:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_resourcePort, index, true );
            break;
        case borderport_usbPort:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_usbPort, index, true );
            break;
        }
}

void Capsule_WordProcessorApp::unbindPort( bool isBorder, int portId, int index )
{
    if( isBorder )
        switch( portId )
        {
        case borderport_UserControlePort:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_UserControlePort, index, false );
            UMLRTFrameService::disconnectPort( borderPorts[borderport_UserControlePort], index );
            break;
        case borderport_resourcePort:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_resourcePort, index, false );
            UMLRTFrameService::disconnectPort( borderPorts[borderport_resourcePort], index );
            break;
        case borderport_usbPort:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_usbPort, index, false );
            UMLRTFrameService::disconnectPort( borderPorts[borderport_usbPort], index );
            break;
        }
}








void Capsule_WordProcessorApp::inject( const UMLRTMessage & message )
{
    msg = &message;
    switch( currentState )
    {
    case WaitingForCommand:
        currentState = state_____WaitingForCommand( &message );
        break;
    case Printing__waitForPrinter:
        currentState = state_____Printing__waitForPrinter( &message );
        break;
    case Printing__Printing:
        currentState = state_____Printing__Printing( &message );
        break;
    case Printing__boundary:
        currentState = state_____Printing__boundary( &message );
        break;
    case Saving__WaitForStorage:
        currentState = state_____Saving__WaitForStorage( &message );
        break;
    case Saving__Saving:
        currentState = state_____Saving__Saving( &message );
        break;
    case Saving__boundary:
        currentState = state_____Saving__boundary( &message );
        break;
    default:
        break;
    }
}

void Capsule_WordProcessorApp::initialize( const UMLRTMessage & message )
{
    msg = &message;
    actionchain_____Initial( &message );
    currentState = WaitingForCommand;
}

const char * Capsule_WordProcessorApp::getCurrentStateString() const
{
    return stateNames[currentState];
}





void Capsule_WordProcessorApp::save_history( Capsule_WordProcessorApp::State compositeState, Capsule_WordProcessorApp::State subState )
{
    history[compositeState] = subState;
}

bool Capsule_WordProcessorApp::check_history( Capsule_WordProcessorApp::State compositeState, Capsule_WordProcessorApp::State subState )
{
    return history[compositeState] == subState;
}

void Capsule_WordProcessorApp::update_state( Capsule_WordProcessorApp::State newState )
{
    currentState = newState;
}

void Capsule_WordProcessorApp::entryaction_____Printing( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/ComputerSystem/ComputerSystem.uml ComputerSystem::Software::WordProcessorApp::Printing entry  */
    time_t currentTime;
    struct tm *localTime;
    time( &currentTime );
    localTime = localtime( &currentTime );
    std::cout << std::setfill('0') << "[" << std::setw(2) << localTime->tm_hour << ":" << std::setw(2) << localTime->tm_min << ":" << std::setw(2) << localTime->tm_sec << "] ";
    std::cout << "[" << this->getName() << "] is {Printing}." << std::endl;
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_WordProcessorApp::entryaction_____Printing__waitForPrinter( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/ComputerSystem/ComputerSystem.uml ComputerSystem::Software::WordProcessorApp::Printing::waitForPrinter entry  */
    time_t currentTime;
    struct tm *localTime;
    time( &currentTime );
    localTime = localtime( &currentTime );
    std::cout << std::setfill('0') << "[" << std::setw(2) << localTime->tm_hour << ":" << std::setw(2) << localTime->tm_min << ":" << std::setw(2) << localTime->tm_sec << "] ";
    std::cout << "[" << this->getName() << "] waiting for printer driver" << std::endl;
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_WordProcessorApp::entryaction_____Saving( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/ComputerSystem/ComputerSystem.uml ComputerSystem::Software::WordProcessorApp::Saving entry  */
    time_t currentTime;
    struct tm *localTime;
    time( &currentTime );
    localTime = localtime( &currentTime );
    std::cout << std::setfill('0') << "[" << std::setw(2) << localTime->tm_hour << ":" << std::setw(2) << localTime->tm_min << ":" << std::setw(2) << localTime->tm_sec << "] ";
    std::cout << "[" << this->getName() << "] is {Saving}." << std::endl;
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_WordProcessorApp::entryaction_____Saving__WaitForStorage( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/ComputerSystem/ComputerSystem.uml ComputerSystem::Software::WordProcessorApp::Saving::WaitForStorage entry  */
    time_t currentTime;
    struct tm *localTime;
    time( &currentTime );
    localTime = localtime( &currentTime );
    std::cout << std::setfill('0') << "[" << std::setw(2) << localTime->tm_hour << ":" << std::setw(2) << localTime->tm_min << ":" << std::setw(2) << localTime->tm_sec << "] ";
    std::cout << "[" << this->getName() << "] waiting for storage driver" << std::endl;
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_WordProcessorApp::entryaction_____WaitingForCommand( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/ComputerSystem/ComputerSystem.uml ComputerSystem::Software::WordProcessorApp::WaitingForCommand entry  */
    time_t currentTime;
    struct tm *localTime;
    time( &currentTime );
    localTime = localtime( &currentTime );
    std::cout << std::setfill('0') << "[" << std::setw(2) << localTime->tm_hour << ":" << std::setw(2) << localTime->tm_min << ":" << std::setw(2) << localTime->tm_sec << "] ";
    std::cout << "[" << this->getName() << "] is {WaitingForCommand}." << std::endl;
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_WordProcessorApp::transitionaction_____Initial( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/ComputerSystem/ComputerSystem.uml ComputerSystem::Software::WordProcessorApp transition Initial1,WaitingForCommand */
    time_t currentTime;
    struct tm *localTime;
    time( &currentTime );
    localTime = localtime( &currentTime );
    document = (char *) malloc(101);
    strcpy(document, "Aenean lacinia bibendum nulla sed consectetur.\0\0");
    std::cout << std::setfill('0') << "[" << std::setw(2) << localTime->tm_hour << ":" << std::setw(2) << localTime->tm_min << ":" << std::setw(2) << localTime->tm_sec << "] ";
    std::cout << "[" << this->getName() << "] is initializing and document contains string \"" << document << "\"." << std::endl;
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_WordProcessorApp::transitionaction_____Printing__getPrinter( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/ComputerSystem/ComputerSystem.uml ComputerSystem::Software::WordProcessorApp::Printing transition Printing::EntryPoint1,Printing::waitForPrinter */
    time_t currentTime;
    struct tm *localTime;
    time( &currentTime );
    localTime = localtime( &currentTime );
    std::cout << std::setfill('0') << "[" << std::setw(2) << localTime->tm_hour << ":" << std::setw(2) << localTime->tm_min << ":" << std::setw(2) << localTime->tm_sec << "] ";
    std::cout << "[" << this->getName() << "]";
    if ( resourcePort.requestPrinterDriver().send() ) {
    std::cout << " requested a printer driver" << std::endl;
    } else {
    std::cout << " FAILed to  request a printer driver" << std::endl;
    }
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_WordProcessorApp::transitionaction_____Printing__importNOK( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/ComputerSystem/ComputerSystem.uml ComputerSystem::Software::WordProcessorApp::Printing transition Printing::importOK,Printing::ExitPoint1 */
    time_t currentTime;
    struct tm *localTime;
    time( &currentTime );
    localTime = localtime( &currentTime );
    std::cout << std::setfill('0') << "[" << std::setw(2) << localTime->tm_hour << ":" << std::setw(2) << localTime->tm_min << ":" << std::setw(2) << localTime->tm_sec << "] ";
    std::cout << "[" << this->getName() << "] could not import printer driver" << std::endl;
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_WordProcessorApp::transitionaction_____Printing__importOK( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/ComputerSystem/ComputerSystem.uml ComputerSystem::Software::WordProcessorApp::Printing transition Printing::importOK,Printing::printOK */
    time_t currentTime;
    struct tm *localTime;
    time( &currentTime );
    localTime = localtime( &currentTime );
    std::cout << std::setfill('0') << "[" << std::setw(2) << localTime->tm_hour << ":" << std::setw(2) << localTime->tm_min << ":" << std::setw(2) << localTime->tm_sec << "] ";
    status = usbPort.data(document).send();
    if ( status ) {
    std::cout << "[" << this->getName() << "] sent document to print" << std::endl;
    } else {
    std::cout << "[" << this->getName() << "] ERROR sending document to print" << std::endl;
    }
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_WordProcessorApp::transitionaction_____Printing__onPrintResource( const UMLRTMessage * msg )
{
    #define resourceID ( *(const UMLRTCapsuleId * )msg->getParam( 0 ) )
    #define rtdata ( (const UMLRTCapsuleId * )msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/ComputerSystem/ComputerSystem.uml ComputerSystem::Software::WordProcessorApp::Printing transition Printing::waitForPrinter,Printing::importOK,resourceID:resourcePort */
    time_t currentTime;
    struct tm *localTime;
    time( &currentTime );
    localTime = localtime( &currentTime );
    std::cout << std::setfill('0') << "[" << std::setw(2) << localTime->tm_hour << ":" << std::setw(2) << localTime->tm_min << ":" << std::setw(2) << localTime->tm_sec << "] ";
    std::cout << "[" << this->getName() << "]";
    printerID = (UMLRTCapsuleId) *rtdata;
    if ( printerID.isValid() ) {
    importOK = frame.import ( printerID, deviceInterface );
    std::cout << " importing a printer driver." << std::endl;
    } else {
    importOK = false;
    std::cout << " received an invalid Printer ID" << std::endl;
    }
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
    #undef resourceID
}

void Capsule_WordProcessorApp::transitionaction_____Printing__onPrintStatus( const UMLRTMessage * msg )
{
    #define percent ( *(int *)msg->getParam( 0 ) )
    #define rtdata ( (int *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/ComputerSystem/ComputerSystem.uml ComputerSystem::Software::WordProcessorApp::Printing transition Printing::Printing,Printing::Choice1,status:usbPort */
    progress = (int) *rtdata;
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
    #undef percent
}

void Capsule_WordProcessorApp::transitionaction_____Printing__onResNotAvail( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/ComputerSystem/ComputerSystem.uml ComputerSystem::Software::WordProcessorApp::Printing transition Printing::waitForPrinter,Printing::ExitPoint1,resNotAvail:resourcePort */
    time_t currentTime;
    struct tm *localTime;
    time( &currentTime );
    localTime = localtime( &currentTime );
    std::cout << std::setfill('0') << "[" << std::setw(2) << localTime->tm_hour << ":" << std::setw(2) << localTime->tm_min << ":" << std::setw(2) << localTime->tm_sec << "] ";
    std::cout << "[" << this->getName() << "] print driver not available" << std::endl;
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_WordProcessorApp::transitionaction_____Printing__printCompleted( const UMLRTMessage * msg )
{
    #define percent ( *(int *)msg->getParam( 0 ) )
    #define rtdata ( (int *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/ComputerSystem/ComputerSystem.uml ComputerSystem::Software::WordProcessorApp::Printing transition Printing::Choice1,Printing::ExitPoint2,status:usbPort */
    time_t currentTime;
    struct tm *localTime;
    time( &currentTime );
    localTime = localtime( &currentTime );
    std::cout << std::setfill('0') << "[" << std::setw(2) << localTime->tm_hour << ":" << std::setw(2) << localTime->tm_min << ":" << std::setw(2) << localTime->tm_sec << "] ";
    std::cout << "[" << this->getName() << "] has no more data";
    status = usbPort.eod().send();
    if ( status ) {
    std::cout << "[" << this->getName() << "] - sent EOD";
    } else {
    std::cout << "[" << this->getName() << "] - FAILed to send EOD";
    }
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
    #undef percent
}

void Capsule_WordProcessorApp::transitionaction_____Printing__printPrintProgress( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/ComputerSystem/ComputerSystem.uml ComputerSystem::Software::WordProcessorApp::Printing transition Printing::Choice1,Printing::Printing */
    time_t currentTime;
    struct tm *localTime;
    time( &currentTime );
    localTime = localtime( &currentTime );
    std::cout << std::setfill('0') << "[" << std::setw(2) << localTime->tm_hour << ":" << std::setw(2) << localTime->tm_min << ":" << std::setw(2) << localTime->tm_sec << "] ";
    std::cout << "[" << this->getName() << "] Printing... " << (int) progress << "%%" << std::endl;
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_WordProcessorApp::transitionaction_____Printing__transition8( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/ComputerSystem/ComputerSystem.uml ComputerSystem::Software::WordProcessorApp::Printing transition Printing::printOK,Printing::ExitPoint1 */
    time_t currentTime;
    struct tm *localTime;
    time( &currentTime );
    localTime = localtime( &currentTime );
    std::cout << std::setfill('0') << "[" << std::setw(2) << localTime->tm_hour << ":" << std::setw(2) << localTime->tm_min << ":" << std::setw(2) << localTime->tm_sec << "] ";
    std::cout << "[" << this->getName() << "] could not print document" << std::endl;
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_WordProcessorApp::transitionaction_____Saving__getStorage( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/ComputerSystem/ComputerSystem.uml ComputerSystem::Software::WordProcessorApp::Saving transition Saving::EntryPoint1,Saving::WaitForStorage */
    time_t currentTime;
    struct tm *localTime;
    time( &currentTime );
    localTime = localtime( &currentTime );
    std::cout << std::setfill('0') << "[" << std::setw(2) << localTime->tm_hour << ":" << std::setw(2) << localTime->tm_min << ":" << std::setw(2) << localTime->tm_sec << "] ";
    std::cout << "[" << this->getName() << "]";
    if ( resourcePort.requestStorageDriver().send() ) {
    std::cout << " requested a storage driver" << std::endl;
    } else {
    std::cout << "FAILed to request a storage driver" << std::endl;
    }
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_WordProcessorApp::transitionaction_____Saving__importNOK( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/ComputerSystem/ComputerSystem.uml ComputerSystem::Software::WordProcessorApp::Saving transition Saving::importOK,Saving::ExitPoint1 */
    time_t currentTime;
    struct tm *localTime;
    time( &currentTime );
    localTime = localtime( &currentTime );
    std::cout << std::setfill('0') << "[" << (localTime->tm_year + 1900) << "." << std::setw(2) << (localTime->tm_mon + 1) << "." << std::setw(2) << localTime->tm_mday << " " << std::setw(2) << localTime->tm_hour << ":" << std::setw(2) << localTime->tm_min << ":" << std::setw(2) << localTime->tm_sec << "] ";
    std::cout << "[APPLICATION] could not import storage driver" << std::endl;
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_WordProcessorApp::transitionaction_____Saving__importOK( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/ComputerSystem/ComputerSystem.uml ComputerSystem::Software::WordProcessorApp::Saving transition Saving::importOK,Saving::saveOK */
    time_t currentTime;
    struct tm *localTime;
    time( &currentTime );
    localTime = localtime( &currentTime );
    std::cout << std::setfill('0') << "[" << std::setw(2) << localTime->tm_hour << ":" << std::setw(2) << localTime->tm_min << ":" << std::setw(2) << localTime->tm_sec << "] ";
    status = usbPort.data(document).send();
    if ( status ) {
    std::cout << "[" << this->getName() << "] sends document to save" << std::endl;
    } else {
    std::cout << "[" << this->getName() << "] error sending document to save" << std::endl;
    }
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_WordProcessorApp::transitionaction_____Saving__isNOK( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/ComputerSystem/ComputerSystem.uml ComputerSystem::Software::WordProcessorApp::Saving transition Saving::saveOK,Saving::ExitPoint1 */
    time_t currentTime;
    struct tm *localTime;
    time( &currentTime );
    localTime = localtime( &currentTime );
    std::cout << std::setfill('0') << "[" << std::setw(2) << localTime->tm_hour << ":" << std::setw(2) << localTime->tm_min << ":" << std::setw(2) << localTime->tm_sec << "] ";
    std::cout << "[" << this->getName() << "] could not save document" << std::endl;
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_WordProcessorApp::transitionaction_____Saving__onResNotAvail( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/ComputerSystem/ComputerSystem.uml ComputerSystem::Software::WordProcessorApp::Saving transition Saving::WaitForStorage,Saving::ExitPoint1,resNotAvail:resourcePort */
    time_t currentTime;
    struct tm *localTime;
    time( &currentTime );
    localTime = localtime( &currentTime );
    std::cout << std::setfill('0') << "[" << std::setw(2) << localTime->tm_hour << ":" << std::setw(2) << localTime->tm_min << ":" << std::setw(2) << localTime->tm_sec << "] ";
    std::cout << "[APPLICATION] storage driver not available" << std::endl;
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_WordProcessorApp::transitionaction_____Saving__onSaveStatus( const UMLRTMessage * msg )
{
    #define percent ( *(int *)msg->getParam( 0 ) )
    #define rtdata ( (int *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/ComputerSystem/ComputerSystem.uml ComputerSystem::Software::WordProcessorApp::Saving transition Saving::Saving,Saving::Choice1,status:usbPort */
    progress = (int) *rtdata;
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
    #undef percent
}

void Capsule_WordProcessorApp::transitionaction_____Saving__onStorageResource( const UMLRTMessage * msg )
{
    #define resourceID ( *(const UMLRTCapsuleId * )msg->getParam( 0 ) )
    #define rtdata ( (const UMLRTCapsuleId * )msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/ComputerSystem/ComputerSystem.uml ComputerSystem::Software::WordProcessorApp::Saving transition Saving::WaitForStorage,Saving::importOK,resourceID:resourcePort */
    time_t currentTime;
    struct tm *localTime;
    time( &currentTime );
    localTime = localtime( &currentTime );
    std::cout << std::setfill('0') << "[" << std::setw(2) << localTime->tm_hour << ":" << std::setw(2) << localTime->tm_min << ":" << std::setw(2) << localTime->tm_sec << "] ";
    storageID = (UMLRTCapsuleId) *rtdata;
    if ( storageID.isValid() ) {
    importOK = frame.import(storageID,deviceInterface );
    std::cout << "[" << this->getName() << "] importing a storage driver." << std::endl;
    } else {
    importOK = false;
    std::cout << "[" << this->getName() << "] Invalid StorageDriver ID." << std::endl;
    }
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
    #undef resourceID
}

void Capsule_WordProcessorApp::transitionaction_____Saving__printSaveProgress( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/ComputerSystem/ComputerSystem.uml ComputerSystem::Software::WordProcessorApp::Saving transition Saving::Choice1,Saving::Saving */
    time_t currentTime;
    struct tm *localTime;
    time( &currentTime );
    localTime = localtime( &currentTime );
    std::cout << std::setfill('0') << "[" << std::setw(2) << localTime->tm_hour << ":" << std::setw(2) << localTime->tm_min << ":" << std::setw(2) << localTime->tm_sec << "] ";
    std::cout << "[" << this->getName() << "] Saving..." << (int) progress << "%%" << std::endl;
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_WordProcessorApp::transitionaction_____Saving__saveCompleted( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/ComputerSystem/ComputerSystem.uml ComputerSystem::Software::WordProcessorApp::Saving transition Saving::Choice1,Saving::ExitPoint2 */
    time_t currentTime;
    struct tm *localTime;
    time( &currentTime );
    localTime = localtime( &currentTime );
    std::cout << std::setfill('0') << "[" << std::setw(2) << localTime->tm_hour << ":" << std::setw(2) << localTime->tm_min << ":" << std::setw(2) << localTime->tm_sec << "] ";
    std::cout << "[" << this->getName() << "] has no more data";
    status = usbPort.eod().send();
    if ( status ) {
    std::cout << " - sent EOD" << std::endl;
    } else {
    std::cout << " - FAILed to send EOD" << std::endl;
    }
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_WordProcessorApp::transitionaction_____onAddToDoc( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/ComputerSystem/ComputerSystem.uml ComputerSystem::Software::WordProcessorApp transition WaitingForCommand,WaitingForCommand,addToDoc:UserControlePort */
    time_t currentTime;
    struct tm *localTime;
    time( &currentTime );
    localTime = localtime( &currentTime );
    std::cout << std::setfill('0') << "[" << (localTime->tm_year + 1900) << "." << std::setw(2) << (localTime->tm_mon + 1) << "." << std::setw(2) << localTime->tm_mday << " " << std::setw(2) << localTime->tm_hour << ":" << std::setw(2) << localTime->tm_min << ":" << std::setw(2) << localTime->tm_sec << "] ";
    std::cout << "[" << this->getName() << "] String added to document." << std::endl;
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_WordProcessorApp::transitionaction_____onCreateDoc( const UMLRTMessage * msg )
{
    #define fileName ( *(const char * *)msg->getParam( 0 ) )
    #define data ( *(const void * *)msg->getParam( 1 ) )
    #define rtdata ( (char *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/ComputerSystem/ComputerSystem.uml ComputerSystem::Software::WordProcessorApp transition WaitingForCommand,WaitingForCommand,createDocument:UserControlePort */
    time_t currentTime;
    struct tm *localTime;
    time( &currentTime );
    localTime = localtime( &currentTime );
    std::cout << std::setfill('0') << "[" << (localTime->tm_year + 1900) << "." << std::setw(2) << (localTime->tm_mon + 1) << "." << std::setw(2) << localTime->tm_mday << " " << std::setw(2) << localTime->tm_hour << ":" << std::setw(2) << localTime->tm_min << ":" << std::setw(2) << localTime->tm_sec << "] ";
    std::cout << "[" << this->getName() << "] document created with provided string." << std::endl;
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
    #undef data
    #undef fileName
}

void Capsule_WordProcessorApp::transitionaction_____onPrintDocument( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/ComputerSystem/ComputerSystem.uml ComputerSystem::Software::WordProcessorApp transition WaitingForCommand,Printing::EntryPoint1,printDocument:UserControlePort */
    time_t currentTime;
    struct tm *localTime;
    time( &currentTime );
    localTime = localtime( &currentTime );
    std::cout << std::setfill('0') << "[" << std::setw(2) << localTime->tm_hour << ":" << std::setw(2) << localTime->tm_min << ":" << std::setw(2) << localTime->tm_sec << "] ";
    std::cout << "[" << this->getName() << "] received request to print the document" << std::endl;
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_WordProcessorApp::transitionaction_____onSaveDocument( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/ComputerSystem/ComputerSystem.uml ComputerSystem::Software::WordProcessorApp transition WaitingForCommand,Saving::EntryPoint1,saveDocument:UserControlePort */
    time_t currentTime;
    struct tm *localTime;
    time( &currentTime );
    localTime = localtime( &currentTime );
    std::cout << std::setfill('0') << "[" << std::setw(2) << localTime->tm_hour << ":" << std::setw(2) << localTime->tm_min << ":" << std::setw(2) << localTime->tm_sec << "] ";
    std::cout << "[" << this->getName() << "] received request to save the document" << std::endl;
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_WordProcessorApp::transitionaction_____printComplete_cont( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/ComputerSystem/ComputerSystem.uml ComputerSystem::Software::WordProcessorApp transition Printing::ExitPoint2,WaitingForCommand */
    time_t currentTime;
    struct tm *localTime;
    time( &currentTime );
    localTime = localtime( &currentTime );
    std::cout << std::setfill('0') << "[" << std::setw(2) << localTime->tm_hour << ":" << std::setw(2) << localTime->tm_min << ":" << std::setw(2) << localTime->tm_sec << "] ";
    std::cout << "[" << this->getName() << "] is done printing";
    if ( frame.deport(printerID, deviceInterface) ) {
    std::cout << " - printer driver released." << std::endl;
    } else {
    std::cout << " - FAILed to release the printer driver!" << std::endl;
    }
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_WordProcessorApp::transitionaction_____saveComplete_cont( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/ComputerSystem/ComputerSystem.uml ComputerSystem::Software::WordProcessorApp transition Saving::ExitPoint2,WaitingForCommand */
    time_t currentTime;
    struct tm *localTime;
    time( &currentTime );
    localTime = localtime( &currentTime );
    std::cout << std::setfill('0') << "[" << std::setw(2) << localTime->tm_hour << ":" << std::setw(2) << localTime->tm_min << ":" << std::setw(2) << localTime->tm_sec << "] ";
    std::cout << "[" << this->getName() << "] is done saving";
    if ( frame.deport(storageID, deviceInterface) ) {
    std::cout << " - storage driver released." << std::endl;
    } else {
    std::cout << " - FAILed to release the storage driver!" << std::endl;
    }
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

bool Capsule_WordProcessorApp::guard_____Printing__importNOK( const UMLRTMessage * msg )
{
    #define resourceID ( *(const UMLRTCapsuleId * )msg->getParam( 0 ) )
    #define rtdata ( (const UMLRTCapsuleId * )msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/ComputerSystem/ComputerSystem.uml ComputerSystem::Software::WordProcessorApp::Printing guard Printing::importOK,Printing::ExitPoint1 */
    return( ! importOK );
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
    #undef resourceID
}

bool Capsule_WordProcessorApp::guard_____Printing__importOK( const UMLRTMessage * msg )
{
    #define resourceID ( *(const UMLRTCapsuleId * )msg->getParam( 0 ) )
    #define rtdata ( (const UMLRTCapsuleId * )msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/ComputerSystem/ComputerSystem.uml ComputerSystem::Software::WordProcessorApp::Printing guard Printing::importOK,Printing::printOK */
    return( importOK );
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
    #undef resourceID
}

bool Capsule_WordProcessorApp::guard_____Printing__printCompleted( const UMLRTMessage * msg )
{
    #define percent ( *(int *)msg->getParam( 0 ) )
    #define rtdata ( (int *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/ComputerSystem/ComputerSystem.uml ComputerSystem::Software::WordProcessorApp::Printing guard Printing::Choice1,Printing::ExitPoint2,status:usbPort */
    return( progress >= 100);
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
    #undef percent
}

bool Capsule_WordProcessorApp::guard_____Printing__printPrintProgress( const UMLRTMessage * msg )
{
    #define percent ( *(int *)msg->getParam( 0 ) )
    #define rtdata ( (int *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/ComputerSystem/ComputerSystem.uml ComputerSystem::Software::WordProcessorApp::Printing guard Printing::Choice1,Printing::Printing */
    return( progress < 100);
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
    #undef percent
}

bool Capsule_WordProcessorApp::guard_____Printing__transition8( const UMLRTMessage * msg )
{
    #define resourceID ( *(const UMLRTCapsuleId * )msg->getParam( 0 ) )
    #define rtdata ( (const UMLRTCapsuleId * )msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/ComputerSystem/ComputerSystem.uml ComputerSystem::Software::WordProcessorApp::Printing guard Printing::printOK,Printing::ExitPoint1 */
    return( ! status );
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
    #undef resourceID
}

bool Capsule_WordProcessorApp::guard_____Printing__transition9( const UMLRTMessage * msg )
{
    #define resourceID ( *(const UMLRTCapsuleId * )msg->getParam( 0 ) )
    #define rtdata ( (const UMLRTCapsuleId * )msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/ComputerSystem/ComputerSystem.uml ComputerSystem::Software::WordProcessorApp::Printing guard Printing::printOK,Printing::Printing */
    return( status );
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
    #undef resourceID
}

bool Capsule_WordProcessorApp::guard_____Saving__importNOK( const UMLRTMessage * msg )
{
    #define resourceID ( *(const UMLRTCapsuleId * )msg->getParam( 0 ) )
    #define rtdata ( (const UMLRTCapsuleId * )msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/ComputerSystem/ComputerSystem.uml ComputerSystem::Software::WordProcessorApp::Saving guard Saving::importOK,Saving::ExitPoint1 */
    return( ! importOK );
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
    #undef resourceID
}

bool Capsule_WordProcessorApp::guard_____Saving__importOK( const UMLRTMessage * msg )
{
    #define resourceID ( *(const UMLRTCapsuleId * )msg->getParam( 0 ) )
    #define rtdata ( (const UMLRTCapsuleId * )msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/ComputerSystem/ComputerSystem.uml ComputerSystem::Software::WordProcessorApp::Saving guard Saving::importOK,Saving::saveOK */
    return( importOK );
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
    #undef resourceID
}

bool Capsule_WordProcessorApp::guard_____Saving__isNOK( const UMLRTMessage * msg )
{
    #define resourceID ( *(const UMLRTCapsuleId * )msg->getParam( 0 ) )
    #define rtdata ( (const UMLRTCapsuleId * )msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/ComputerSystem/ComputerSystem.uml ComputerSystem::Software::WordProcessorApp::Saving guard Saving::saveOK,Saving::ExitPoint1 */
    return ( ! status );
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
    #undef resourceID
}

bool Capsule_WordProcessorApp::guard_____Saving__isOK( const UMLRTMessage * msg )
{
    #define resourceID ( *(const UMLRTCapsuleId * )msg->getParam( 0 ) )
    #define rtdata ( (const UMLRTCapsuleId * )msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/ComputerSystem/ComputerSystem.uml ComputerSystem::Software::WordProcessorApp::Saving guard Saving::saveOK,Saving::Saving */
    return( status );
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
    #undef resourceID
}

bool Capsule_WordProcessorApp::guard_____Saving__printSaveProgress( const UMLRTMessage * msg )
{
    #define percent ( *(int *)msg->getParam( 0 ) )
    #define rtdata ( (int *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/ComputerSystem/ComputerSystem.uml ComputerSystem::Software::WordProcessorApp::Saving guard Saving::Choice1,Saving::Saving */
    return( progress < 100 );
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
    #undef percent
}

bool Capsule_WordProcessorApp::guard_____Saving__saveCompleted( const UMLRTMessage * msg )
{
    #define percent ( *(int *)msg->getParam( 0 ) )
    #define rtdata ( (int *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/ComputerSystem/ComputerSystem.uml ComputerSystem::Software::WordProcessorApp::Saving guard Saving::Choice1,Saving::ExitPoint2 */
    return( progress >= 100 );
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
    #undef percent
}

void Capsule_WordProcessorApp::actionchain_____Initial( const UMLRTMessage * msg )
{
    transitionaction_____Initial( msg );
    update_state( WaitingForCommand );
    entryaction_____WaitingForCommand( msg );
}

void Capsule_WordProcessorApp::actionchain_____Printing__getPrinter( const UMLRTMessage * msg )
{
    transitionaction_____Printing__getPrinter( msg );
    update_state( Printing__waitForPrinter );
    entryaction_____Printing__waitForPrinter( msg );
}

void Capsule_WordProcessorApp::actionchain_____Printing__importNOK( const UMLRTMessage * msg )
{
    transitionaction_____Printing__importNOK( msg );
}

void Capsule_WordProcessorApp::actionchain_____Printing__importOK( const UMLRTMessage * msg )
{
    transitionaction_____Printing__importOK( msg );
}

void Capsule_WordProcessorApp::actionchain_____Printing__new_transition_1( const UMLRTMessage * msg )
{
    update_state( Printing__waitForPrinter );
    entryaction_____Printing__waitForPrinter( msg );
}

void Capsule_WordProcessorApp::actionchain_____Printing__new_transition_2( const UMLRTMessage * msg )
{
    update_state( Printing__Printing );
}

void Capsule_WordProcessorApp::actionchain_____Printing__new_transition_3_to_unvisited_boundary( const UMLRTMessage * msg )
{
    update_state( Printing__boundary );
}

void Capsule_WordProcessorApp::actionchain_____Printing__new_transition_4_to_visited_boundary( const UMLRTMessage * msg )
{
    update_state( Printing__boundary );
}

void Capsule_WordProcessorApp::actionchain_____Printing__onPrintResource( const UMLRTMessage * msg )
{
    update_state( Printing );
    transitionaction_____Printing__onPrintResource( msg );
    save_history( Printing, Printing__waitForPrinter );
}

void Capsule_WordProcessorApp::actionchain_____Printing__onPrintStatus( const UMLRTMessage * msg )
{
    update_state( Printing );
    transitionaction_____Printing__onPrintStatus( msg );
    save_history( Printing, Printing__Printing );
}

void Capsule_WordProcessorApp::actionchain_____Printing__onResNotAvail( const UMLRTMessage * msg )
{
    update_state( Printing );
    transitionaction_____Printing__onResNotAvail( msg );
    save_history( Printing, Printing__waitForPrinter );
}

void Capsule_WordProcessorApp::actionchain_____Printing__printCompleted( const UMLRTMessage * msg )
{
    transitionaction_____Printing__printCompleted( msg );
}

void Capsule_WordProcessorApp::actionchain_____Printing__printPrintProgress( const UMLRTMessage * msg )
{
    transitionaction_____Printing__printPrintProgress( msg );
    update_state( Printing__Printing );
}

void Capsule_WordProcessorApp::actionchain_____Printing__transition8( const UMLRTMessage * msg )
{
    transitionaction_____Printing__transition8( msg );
}

void Capsule_WordProcessorApp::actionchain_____Printing__transition9( const UMLRTMessage * msg )
{
    update_state( Printing__Printing );
}

void Capsule_WordProcessorApp::actionchain_____Saving__getStorage( const UMLRTMessage * msg )
{
    transitionaction_____Saving__getStorage( msg );
    update_state( Saving__WaitForStorage );
    entryaction_____Saving__WaitForStorage( msg );
}

void Capsule_WordProcessorApp::actionchain_____Saving__importNOK( const UMLRTMessage * msg )
{
    transitionaction_____Saving__importNOK( msg );
}

void Capsule_WordProcessorApp::actionchain_____Saving__importOK( const UMLRTMessage * msg )
{
    transitionaction_____Saving__importOK( msg );
}

void Capsule_WordProcessorApp::actionchain_____Saving__isNOK( const UMLRTMessage * msg )
{
    transitionaction_____Saving__isNOK( msg );
}

void Capsule_WordProcessorApp::actionchain_____Saving__isOK( const UMLRTMessage * msg )
{
    update_state( Saving__Saving );
}

void Capsule_WordProcessorApp::actionchain_____Saving__new_transition_5( const UMLRTMessage * msg )
{
    update_state( Saving__WaitForStorage );
    entryaction_____Saving__WaitForStorage( msg );
}

void Capsule_WordProcessorApp::actionchain_____Saving__new_transition_6( const UMLRTMessage * msg )
{
    update_state( Saving__Saving );
}

void Capsule_WordProcessorApp::actionchain_____Saving__new_transition_7_to_unvisited_boundary( const UMLRTMessage * msg )
{
    update_state( Saving__boundary );
}

void Capsule_WordProcessorApp::actionchain_____Saving__new_transition_8_to_visited_boundary( const UMLRTMessage * msg )
{
    update_state( Saving__boundary );
}

void Capsule_WordProcessorApp::actionchain_____Saving__onResNotAvail( const UMLRTMessage * msg )
{
    update_state( Saving );
    transitionaction_____Saving__onResNotAvail( msg );
    save_history( Saving, Saving__WaitForStorage );
}

void Capsule_WordProcessorApp::actionchain_____Saving__onSaveStatus( const UMLRTMessage * msg )
{
    update_state( Saving );
    transitionaction_____Saving__onSaveStatus( msg );
    save_history( Saving, Saving__Saving );
}

void Capsule_WordProcessorApp::actionchain_____Saving__onStorageResource( const UMLRTMessage * msg )
{
    update_state( Saving );
    transitionaction_____Saving__onStorageResource( msg );
    save_history( Saving, Saving__WaitForStorage );
}

void Capsule_WordProcessorApp::actionchain_____Saving__printSaveProgress( const UMLRTMessage * msg )
{
    transitionaction_____Saving__printSaveProgress( msg );
    update_state( Saving__Saving );
}

void Capsule_WordProcessorApp::actionchain_____Saving__saveCompleted( const UMLRTMessage * msg )
{
    transitionaction_____Saving__saveCompleted( msg );
}

void Capsule_WordProcessorApp::actionchain_____StorageNotAvailable( const UMLRTMessage * msg )
{
    update_state( SPECIAL_INTERNAL_STATE_TOP );
    update_state( WaitingForCommand );
    entryaction_____WaitingForCommand( msg );
}

void Capsule_WordProcessorApp::actionchain_____onAddToDoc( const UMLRTMessage * msg )
{
    update_state( SPECIAL_INTERNAL_STATE_TOP );
    transitionaction_____onAddToDoc( msg );
    update_state( WaitingForCommand );
    entryaction_____WaitingForCommand( msg );
}

void Capsule_WordProcessorApp::actionchain_____onCreateDoc( const UMLRTMessage * msg )
{
    update_state( SPECIAL_INTERNAL_STATE_TOP );
    transitionaction_____onCreateDoc( msg );
    update_state( WaitingForCommand );
    entryaction_____WaitingForCommand( msg );
}

void Capsule_WordProcessorApp::actionchain_____onPrintDocument( const UMLRTMessage * msg )
{
    update_state( SPECIAL_INTERNAL_STATE_TOP );
    transitionaction_____onPrintDocument( msg );
    update_state( Printing );
    entryaction_____Printing( msg );
}

void Capsule_WordProcessorApp::actionchain_____onSaveDocument( const UMLRTMessage * msg )
{
    update_state( SPECIAL_INTERNAL_STATE_TOP );
    transitionaction_____onSaveDocument( msg );
    update_state( Saving );
    entryaction_____Saving( msg );
}

void Capsule_WordProcessorApp::actionchain_____printComplete_cont( const UMLRTMessage * msg )
{
    update_state( SPECIAL_INTERNAL_STATE_TOP );
    transitionaction_____printComplete_cont( msg );
    update_state( WaitingForCommand );
    entryaction_____WaitingForCommand( msg );
}

void Capsule_WordProcessorApp::actionchain_____printerUnavailable( const UMLRTMessage * msg )
{
    update_state( SPECIAL_INTERNAL_STATE_TOP );
    update_state( WaitingForCommand );
    entryaction_____WaitingForCommand( msg );
}

void Capsule_WordProcessorApp::actionchain_____saveComplete_cont( const UMLRTMessage * msg )
{
    update_state( SPECIAL_INTERNAL_STATE_TOP );
    transitionaction_____saveComplete_cont( msg );
    update_state( WaitingForCommand );
    entryaction_____WaitingForCommand( msg );
}

Capsule_WordProcessorApp::State Capsule_WordProcessorApp::junction_____Printing__EntryPoint1( const UMLRTMessage * msg )
{
    actionchain_____Printing__getPrinter( msg );
    return Printing__waitForPrinter;
}

Capsule_WordProcessorApp::State Capsule_WordProcessorApp::junction_____Printing__ExitPoint1( const UMLRTMessage * msg )
{
    actionchain_____printerUnavailable( msg );
    return WaitingForCommand;
}

Capsule_WordProcessorApp::State Capsule_WordProcessorApp::junction_____Printing__ExitPoint2( const UMLRTMessage * msg )
{
    actionchain_____printComplete_cont( msg );
    return WaitingForCommand;
}

Capsule_WordProcessorApp::State Capsule_WordProcessorApp::junction_____Saving__EntryPoint1( const UMLRTMessage * msg )
{
    actionchain_____Saving__getStorage( msg );
    return Saving__WaitForStorage;
}

Capsule_WordProcessorApp::State Capsule_WordProcessorApp::junction_____Saving__ExitPoint1( const UMLRTMessage * msg )
{
    actionchain_____StorageNotAvailable( msg );
    return WaitingForCommand;
}

Capsule_WordProcessorApp::State Capsule_WordProcessorApp::junction_____Saving__ExitPoint2( const UMLRTMessage * msg )
{
    actionchain_____saveComplete_cont( msg );
    return WaitingForCommand;
}

Capsule_WordProcessorApp::State Capsule_WordProcessorApp::choice_____Printing__Choice1( const UMLRTMessage * msg )
{
    if( guard_____Printing__printCompleted( msg ) )
    {
        actionchain_____Printing__printCompleted( msg );
        return junction_____Printing__ExitPoint2( msg );
    }
    else if( guard_____Printing__printPrintProgress( msg ) )
    {
        actionchain_____Printing__printPrintProgress( msg );
        return Printing__Printing;
    }
    return currentState;
}

Capsule_WordProcessorApp::State Capsule_WordProcessorApp::choice_____Printing__deephistory( const UMLRTMessage * msg )
{
    if( check_history( Printing, Printing__waitForPrinter ) )
    {
        actionchain_____Printing__new_transition_1( msg );
        return Printing__waitForPrinter;
    }
    else if( check_history( Printing, Printing__Printing ) )
    {
        actionchain_____Printing__new_transition_2( msg );
        return Printing__Printing;
    }
    else if( check_history( Printing, SPECIAL_INTERNAL_STATE_UNVISITED ) )
    {
        actionchain_____Printing__new_transition_3_to_unvisited_boundary( msg );
        return Printing__boundary;
    }
    else if( check_history( Printing, Printing__boundary ) )
    {
        actionchain_____Printing__new_transition_4_to_visited_boundary( msg );
        return Printing__boundary;
    }
    return currentState;
}

Capsule_WordProcessorApp::State Capsule_WordProcessorApp::choice_____Printing__importOK( const UMLRTMessage * msg )
{
    if( guard_____Printing__importNOK( msg ) )
    {
        actionchain_____Printing__importNOK( msg );
        return junction_____Printing__ExitPoint1( msg );
    }
    else if( guard_____Printing__importOK( msg ) )
    {
        actionchain_____Printing__importOK( msg );
        return choice_____Printing__printOK( msg );
    }
    return currentState;
}

Capsule_WordProcessorApp::State Capsule_WordProcessorApp::choice_____Printing__printOK( const UMLRTMessage * msg )
{
    if( guard_____Printing__transition8( msg ) )
    {
        actionchain_____Printing__transition8( msg );
        return junction_____Printing__ExitPoint1( msg );
    }
    else if( guard_____Printing__transition9( msg ) )
    {
        actionchain_____Printing__transition9( msg );
        return Printing__Printing;
    }
    return currentState;
}

Capsule_WordProcessorApp::State Capsule_WordProcessorApp::choice_____Saving__Choice1( const UMLRTMessage * msg )
{
    if( guard_____Saving__saveCompleted( msg ) )
    {
        actionchain_____Saving__saveCompleted( msg );
        return junction_____Saving__ExitPoint2( msg );
    }
    else if( guard_____Saving__printSaveProgress( msg ) )
    {
        actionchain_____Saving__printSaveProgress( msg );
        return Saving__Saving;
    }
    return currentState;
}

Capsule_WordProcessorApp::State Capsule_WordProcessorApp::choice_____Saving__deephistory( const UMLRTMessage * msg )
{
    if( check_history( Saving, Saving__WaitForStorage ) )
    {
        actionchain_____Saving__new_transition_5( msg );
        return Saving__WaitForStorage;
    }
    else if( check_history( Saving, Saving__Saving ) )
    {
        actionchain_____Saving__new_transition_6( msg );
        return Saving__Saving;
    }
    else if( check_history( Saving, SPECIAL_INTERNAL_STATE_UNVISITED ) )
    {
        actionchain_____Saving__new_transition_7_to_unvisited_boundary( msg );
        return Saving__boundary;
    }
    else if( check_history( Saving, Saving__boundary ) )
    {
        actionchain_____Saving__new_transition_8_to_visited_boundary( msg );
        return Saving__boundary;
    }
    return currentState;
}

Capsule_WordProcessorApp::State Capsule_WordProcessorApp::choice_____Saving__importOK( const UMLRTMessage * msg )
{
    if( guard_____Saving__importNOK( msg ) )
    {
        actionchain_____Saving__importNOK( msg );
        return junction_____Saving__ExitPoint1( msg );
    }
    else if( guard_____Saving__importOK( msg ) )
    {
        actionchain_____Saving__importOK( msg );
        return choice_____Saving__saveOK( msg );
    }
    return currentState;
}

Capsule_WordProcessorApp::State Capsule_WordProcessorApp::choice_____Saving__saveOK( const UMLRTMessage * msg )
{
    if( guard_____Saving__isOK( msg ) )
    {
        actionchain_____Saving__isOK( msg );
        return Saving__Saving;
    }
    else if( guard_____Saving__isNOK( msg ) )
    {
        actionchain_____Saving__isNOK( msg );
        return junction_____Saving__ExitPoint1( msg );
    }
    return currentState;
}

Capsule_WordProcessorApp::State Capsule_WordProcessorApp::state_____Printing__Printing( const UMLRTMessage * msg )
{
    switch( msg->destPort->role()->id )
    {
    case port_usbPort:
        switch( msg->getSignalId() )
        {
        case USBProtocol::signal_status:
            actionchain_____Printing__onPrintStatus( msg );
            return choice_____Printing__Choice1( msg );
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

Capsule_WordProcessorApp::State Capsule_WordProcessorApp::state_____Printing__boundary( const UMLRTMessage * msg )
{
    switch( msg->destPort->role()->id )
    {
    default:
        this->unexpectedMessage();
        break;
    }
    return currentState;
}

Capsule_WordProcessorApp::State Capsule_WordProcessorApp::state_____Printing__waitForPrinter( const UMLRTMessage * msg )
{
    switch( msg->destPort->role()->id )
    {
    case port_resourcePort:
        switch( msg->getSignalId() )
        {
        case Resource::signal_resourceID:
            actionchain_____Printing__onPrintResource( msg );
            return choice_____Printing__importOK( msg );
        case Resource::signal_resNotAvail:
            actionchain_____Printing__onResNotAvail( msg );
            return junction_____Printing__ExitPoint1( msg );
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

Capsule_WordProcessorApp::State Capsule_WordProcessorApp::state_____Saving__Saving( const UMLRTMessage * msg )
{
    switch( msg->destPort->role()->id )
    {
    case port_usbPort:
        switch( msg->getSignalId() )
        {
        case USBProtocol::signal_status:
            actionchain_____Saving__onSaveStatus( msg );
            return choice_____Saving__Choice1( msg );
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

Capsule_WordProcessorApp::State Capsule_WordProcessorApp::state_____Saving__WaitForStorage( const UMLRTMessage * msg )
{
    switch( msg->destPort->role()->id )
    {
    case port_resourcePort:
        switch( msg->getSignalId() )
        {
        case Resource::signal_resNotAvail:
            actionchain_____Saving__onResNotAvail( msg );
            return junction_____Saving__ExitPoint1( msg );
        case Resource::signal_resourceID:
            actionchain_____Saving__onStorageResource( msg );
            return choice_____Saving__importOK( msg );
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

Capsule_WordProcessorApp::State Capsule_WordProcessorApp::state_____Saving__boundary( const UMLRTMessage * msg )
{
    switch( msg->destPort->role()->id )
    {
    default:
        this->unexpectedMessage();
        break;
    }
    return currentState;
}

Capsule_WordProcessorApp::State Capsule_WordProcessorApp::state_____WaitingForCommand( const UMLRTMessage * msg )
{
    switch( msg->destPort->role()->id )
    {
    case port_UserControlePort:
        switch( msg->getSignalId() )
        {
        case AppControl::signal_createDocument:
            actionchain_____onCreateDoc( msg );
            return WaitingForCommand;
        case AppControl::signal_addToDoc:
            actionchain_____onAddToDoc( msg );
            return WaitingForCommand;
        case AppControl::signal_printDocument:
            actionchain_____onPrintDocument( msg );
            return junction_____Printing__EntryPoint1( msg );
        case AppControl::signal_saveDocument:
            actionchain_____onSaveDocument( msg );
            return junction_____Saving__EntryPoint1( msg );
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
        "deviceInterface",
        &USBDeviceDriver,
        0,
        1,
        false,
        true
    }
};

static const UMLRTCommsPortRole portroles_border[] = 
{
    {
        Capsule_WordProcessorApp::port_UserControlePort,
        "AppControl",
        "UserControlePort",
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
        Capsule_WordProcessorApp::port_resourcePort,
        "Resource",
        "resourcePort",
        "",
        1,
        true,
        true,
        false,
        false,
        false,
        false,
        true
    },
    {
        Capsule_WordProcessorApp::port_frame,
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
        Capsule_WordProcessorApp::port_usbPort,
        "USBProtocol",
        "usbPort",
        "",
        1,
        true,
        true,
        false,
        false,
        false,
        false,
        true
    },
    {
        Capsule_WordProcessorApp::port_log,
        "Log",
        "log",
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

static void instantiate_WordProcessorApp( const UMLRTRtsInterface * rts, UMLRTSlot * slot, const UMLRTCommsPort * * borderPorts )
{
    UMLRTFrameService::connectPorts( borderPorts[Capsule_WordProcessorApp::borderport_usbPort], 0, &slot->parts[Capsule_WordProcessorApp::part_deviceInterface].slots[0]->ports[Capsule_USBDeviceDriver::borderport_usbInPort], 0 );
    slot->capsule = new Capsule_WordProcessorApp( &WordProcessorApp, slot, borderPorts, NULL, false );
}

const UMLRTCapsuleClass WordProcessorApp = 
{
    "WordProcessorApp",
    &Application,
    instantiate_WordProcessorApp,
    1,
    roles,
    5,
    portroles_border,
    0,
    NULL
};

