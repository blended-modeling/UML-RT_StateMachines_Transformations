
#include "LocalPrinter.hh"

#include "USBProtocol.hh"
#include "umlrtcommsportrole.hh"
#include "umlrtmessage.hh"
#include "umlrtslot.hh"
#include <cstddef>
#include "umlrtcapsuleclass.hh"
#include "umlrtframeservice.hh"
class UMLRTRtsInterface;
struct UMLRTCommsPort;

Capsule_LocalPrinter::Capsule_LocalPrinter( const UMLRTCapsuleClass * cd, UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat )
: UMLRTCapsule( NULL, cd, st, border, internal, isStat )
, usbPort( borderPorts[borderport_usbPort] )
, usbClass( Printer )
, printingData( false )
, currentState( SPECIAL_INTERNAL_STATE_UNVISITED )
{
    stateNames[Connected] = "Connected";
    stateNames[Disconnected] = "Disconnected";
    stateNames[SPECIAL_INTERNAL_STATE_TOP] = "<top>";
    stateNames[SPECIAL_INTERNAL_STATE_UNVISITED] = "<uninitialized>";
}






void Capsule_LocalPrinter::bindPort( bool isBorder, int portId, int index )
{
    if( isBorder )
        switch( portId )
        {
        case borderport_usbPort:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_usbPort, index, true );
            break;
        }
}

void Capsule_LocalPrinter::unbindPort( bool isBorder, int portId, int index )
{
    if( isBorder )
        switch( portId )
        {
        case borderport_usbPort:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_usbPort, index, false );
            UMLRTFrameService::disconnectPort( borderPorts[borderport_usbPort], index );
            break;
        }
}




void Capsule_LocalPrinter::inject( const UMLRTMessage & message )
{
    msg = &message;
    switch( currentState )
    {
    case Connected:
        currentState = state_____Connected( &message );
        break;
    case Disconnected:
        currentState = state_____Disconnected( &message );
        break;
    default:
        break;
    }
}

void Capsule_LocalPrinter::initialize( const UMLRTMessage & message )
{
    msg = &message;
    actionchain_____initialize( &message );
    currentState = choice_____Connected_( &message );
}

const char * Capsule_LocalPrinter::getCurrentStateString() const
{
    return stateNames[currentState];
}




void Capsule_LocalPrinter::update_state( Capsule_LocalPrinter::State newState )
{
    currentState = newState;
}

void Capsule_LocalPrinter::entryaction_____Disconnected( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/ComputerSystem/ComputerSystem.uml ComputerSystem::Hardware::ExternalDevices::LocalPrinter::Disconnected entry  */
    time_t currentTime;
    struct tm *localTime;
    time( &currentTime );
    localTime = localtime( &currentTime );
    std::cout << std::setfill('0') << "[" << (localTime->tm_year + 1900) << "." << std::setw(2) << (localTime->tm_mon + 1) << "." << std::setw(2) << localTime->tm_mday << " " << std::setw(2) << localTime->tm_hour << ":" << std::setw(2) << localTime->tm_min << ":" << std::setw(2) << localTime->tm_sec << "] ";
    std::cout << "[" << this->getName() << "] is in {Disconnected} state." << std::endl;
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_LocalPrinter::transitionaction_____MessageSent( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/ComputerSystem/ComputerSystem.uml ComputerSystem::Hardware::ExternalDevices::LocalPrinter transition Connected?,Connected */
    time_t currentTime;
    struct tm *localTime;
    time( &currentTime );
    localTime = localtime( &currentTime );
    std::cout << std::setfill('0') << "[" << std::setw(2) << localTime->tm_hour << ":" << std::setw(2) << localTime->tm_min << ":" << std::setw(2) << localTime->tm_sec << "] ";
    std::cout << "[" << this->getName() << "] sent its device class to the hub and is now {Connected}." << std::endl;
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_LocalPrinter::transitionaction_____MsgSendFail( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/ComputerSystem/ComputerSystem.uml ComputerSystem::Hardware::ExternalDevices::LocalPrinter transition Connected?,Disconnected */
    time_t currentTime;
    struct tm *localTime;
    time( &currentTime );
    localTime = localtime( &currentTime );
    std::cout << std::setfill('0') << "[" << std::setw(2) << localTime->tm_hour << ":" << std::setw(2) << localTime->tm_min << ":" << std::setw(2) << localTime->tm_sec << "] ";
    std::cout << "[" << this->getName() << "] Error sending device class to hub : connection failed {" << connectionStatus << "]" << std::endl;
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_LocalPrinter::transitionaction_____initialize( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/ComputerSystem/ComputerSystem.uml ComputerSystem::Hardware::ExternalDevices::LocalPrinter transition Initial1,Connected? */
    time_t currentTime;
    struct tm *localTime;
    time( &currentTime );
    localTime = localtime( &currentTime );
    std::cout << std::setfill('0') << "[" << std::setw(2) << localTime->tm_hour << ":" << std::setw(2) << localTime->tm_min << ":" << std::setw(2) << localTime->tm_sec << "] ";
    std::cout << "[" << this->getName() << "] initializing." << std::endl;
    connectionStatus = usbPort.deviceClass(usbClass).send();
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_LocalPrinter::transitionaction_____onConnect( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/ComputerSystem/ComputerSystem.uml ComputerSystem::Hardware::ExternalDevices::LocalPrinter transition Disconnected,Connected,connect:usbPort */
    time_t currentTime;
    struct tm *localTime;
    time( &currentTime );
    localTime = localtime( &currentTime );
    std::cout << std::setfill('0') << "[" << std::setw(2) << localTime->tm_hour << ":" << std::setw(2) << localTime->tm_min << ":" << std::setw(2) << localTime->tm_sec << "] ";
    std::cout << "[" << this->getName() << "] received a connection request and is now in {Connected} state." << std::endl;
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_LocalPrinter::transitionaction_____onData( const UMLRTMessage * msg )
{
    #define data ( *(const void * *)msg->getParam( 0 ) )
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/ComputerSystem/ComputerSystem.uml ComputerSystem::Hardware::ExternalDevices::LocalPrinter transition Connected,Connected,data:usbPort */
    /**
    * Only show logging start on 1st dataset received
    */
    if ( ! printingData ) {
    time_t currentTime;
    struct tm *localTime;
    time( &currentTime );
    localTime = localtime( &currentTime );
    std::cout << std::setfill('0') << "[" << std::setw(2) << localTime->tm_hour << ":" << std::setw(2) << localTime->tm_min << ":" << std::setw(2) << localTime->tm_sec << "] ";
    std::cout << "[" << this->getName() << "] Printing Start ==========" << std::endl;
    printingData = true;
    }
    std::cout << (char *) data;
    usbPort.status(100).send();
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
    #undef data
}

void Capsule_LocalPrinter::transitionaction_____onEOD( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/ComputerSystem/ComputerSystem.uml ComputerSystem::Hardware::ExternalDevices::LocalPrinter transition Connected,Connected,eod:usbPort */
    std::cout << std::endl;
    time_t currentTime;
    struct tm *localTime;
    time( &currentTime );
    localTime = localtime( &currentTime );
    std::cout << std::setfill('0') << "[" << std::setw(2) << localTime->tm_hour << ":" << std::setw(2) << localTime->tm_min << ":" << std::setw(2) << localTime->tm_sec << "] ";
    std::cout << "[" << this->getName() << "] Printing Ended ==========" << std::endl;
    printingData = false;
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_LocalPrinter::transitionaction_____onEject( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/ComputerSystem/ComputerSystem.uml ComputerSystem::Hardware::ExternalDevices::LocalPrinter transition Connected,Disconnected,Eject:usbPort */
    time_t currentTime;
    struct tm *localTime;
    time( &currentTime );
    localTime = localtime( &currentTime );
    std::cout << std::setfill('0') << "[" << std::setw(2) << localTime->tm_hour << ":" << std::setw(2) << localTime->tm_min << ":" << std::setw(2) << localTime->tm_sec << "] ";
    std::cout << "[" << this->getName() << "] ejected!" << std::endl;
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

bool Capsule_LocalPrinter::guard_____MessageSent( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/ComputerSystem/ComputerSystem.uml ComputerSystem::Hardware::ExternalDevices::LocalPrinter guard Connected?,Connected */
    return(connectionStatus);
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

bool Capsule_LocalPrinter::guard_____MsgSendFail( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/ComputerSystem/ComputerSystem.uml ComputerSystem::Hardware::ExternalDevices::LocalPrinter guard Connected?,Disconnected */
    return(! connectionStatus);
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_LocalPrinter::actionchain_____MessageSent( const UMLRTMessage * msg )
{
    transitionaction_____MessageSent( msg );
    update_state( Connected );
}

void Capsule_LocalPrinter::actionchain_____MsgSendFail( const UMLRTMessage * msg )
{
    transitionaction_____MsgSendFail( msg );
    update_state( Disconnected );
    entryaction_____Disconnected( msg );
}

void Capsule_LocalPrinter::actionchain_____initialize( const UMLRTMessage * msg )
{
    transitionaction_____initialize( msg );
}

void Capsule_LocalPrinter::actionchain_____onConnect( const UMLRTMessage * msg )
{
    update_state( SPECIAL_INTERNAL_STATE_TOP );
    transitionaction_____onConnect( msg );
    update_state( Connected );
}

void Capsule_LocalPrinter::actionchain_____onData( const UMLRTMessage * msg )
{
    update_state( SPECIAL_INTERNAL_STATE_TOP );
    transitionaction_____onData( msg );
    update_state( Connected );
}

void Capsule_LocalPrinter::actionchain_____onEOD( const UMLRTMessage * msg )
{
    update_state( SPECIAL_INTERNAL_STATE_TOP );
    transitionaction_____onEOD( msg );
    update_state( Connected );
}

void Capsule_LocalPrinter::actionchain_____onEject( const UMLRTMessage * msg )
{
    update_state( SPECIAL_INTERNAL_STATE_TOP );
    transitionaction_____onEject( msg );
    update_state( Disconnected );
    entryaction_____Disconnected( msg );
}

Capsule_LocalPrinter::State Capsule_LocalPrinter::choice_____Connected_( const UMLRTMessage * msg )
{
    if( guard_____MsgSendFail( msg ) )
    {
        actionchain_____MsgSendFail( msg );
        return Disconnected;
    }
    else if( guard_____MessageSent( msg ) )
    {
        actionchain_____MessageSent( msg );
        return Connected;
    }
    return currentState;
}

Capsule_LocalPrinter::State Capsule_LocalPrinter::state_____Connected( const UMLRTMessage * msg )
{
    switch( msg->destPort->role()->id )
    {
    case port_usbPort:
        switch( msg->getSignalId() )
        {
        case USBProtocol::signal_data:
            actionchain_____onData( msg );
            return Connected;
        case USBProtocol::signal_eod:
            actionchain_____onEOD( msg );
            return Connected;
        case USBProtocol::signal_Eject:
            actionchain_____onEject( msg );
            return Disconnected;
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

Capsule_LocalPrinter::State Capsule_LocalPrinter::state_____Disconnected( const UMLRTMessage * msg )
{
    switch( msg->destPort->role()->id )
    {
    case port_usbPort:
        switch( msg->getSignalId() )
        {
        case USBProtocol::signal_connect:
            actionchain_____onConnect( msg );
            return Connected;
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


static const UMLRTCommsPortRole portroles_border[] = 
{
    {
        Capsule_LocalPrinter::port_usbPort,
        "USBProtocol",
        "usbPort",
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
        Capsule_LocalPrinter::port_log,
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

static void instantiate_LocalPrinter( const UMLRTRtsInterface * rts, UMLRTSlot * slot, const UMLRTCommsPort * * borderPorts )
{
    slot->capsule = new Capsule_LocalPrinter( &LocalPrinter, slot, borderPorts, NULL, false );
}

const UMLRTCapsuleClass LocalPrinter = 
{
    "LocalPrinter",
    NULL,
    instantiate_LocalPrinter,
    0,
    NULL,
    2,
    portroles_border,
    0,
    NULL
};

