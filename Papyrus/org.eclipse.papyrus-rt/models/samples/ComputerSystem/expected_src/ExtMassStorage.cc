
#include "ExtMassStorage.hh"

#include "USBProtocol.hh"
#include "umlrtcommsportrole.hh"
#include "umlrtmessage.hh"
#include "umlrtslot.hh"
#include <cstddef>
#include "umlrtcapsuleclass.hh"
#include "umlrtframeservice.hh"
class UMLRTRtsInterface;
struct UMLRTCommsPort;

Capsule_ExtMassStorage::Capsule_ExtMassStorage( const UMLRTCapsuleClass * cd, UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat )
: UMLRTCapsule( NULL, cd, st, border, internal, isStat )
, usbPort( borderPorts[borderport_usbPort] )
, usbClass( MassStorage )
, savingData( false )
, currentState( SPECIAL_INTERNAL_STATE_UNVISITED )
{
    stateNames[Connected] = "Connected";
    stateNames[Disconnected] = "Disconnected";
    stateNames[SPECIAL_INTERNAL_STATE_TOP] = "<top>";
    stateNames[SPECIAL_INTERNAL_STATE_UNVISITED] = "<uninitialized>";
}






void Capsule_ExtMassStorage::bindPort( bool isBorder, int portId, int index )
{
    if( isBorder )
        switch( portId )
        {
        case borderport_usbPort:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_usbPort, index, true );
            break;
        }
}

void Capsule_ExtMassStorage::unbindPort( bool isBorder, int portId, int index )
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





void Capsule_ExtMassStorage::inject( const UMLRTMessage & message )
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

void Capsule_ExtMassStorage::initialize( const UMLRTMessage & message )
{
    msg = &message;
    actionchain_____initialize( &message );
    currentState = choice_____connected_( &message );
}

const char * Capsule_ExtMassStorage::getCurrentStateString() const
{
    return stateNames[currentState];
}




void Capsule_ExtMassStorage::update_state( Capsule_ExtMassStorage::State newState )
{
    currentState = newState;
}

void Capsule_ExtMassStorage::entryaction_____Disconnected( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/ComputerSystem/ComputerSystem.uml ComputerSystem::Hardware::ExternalDevices::ExtMassStorage::Disconnected entry  */
    std::cout << "[" << this->getName() << "] is in {Disconnected} state." << std::endl;
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_ExtMassStorage::transitionaction_____Connect_Failed( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/ComputerSystem/ComputerSystem.uml ComputerSystem::Hardware::ExternalDevices::ExtMassStorage transition connected?,Disconnected */
    time_t currentTime;
    struct tm *localTime;
    time( &currentTime );
    localTime = localtime( &currentTime );
    std::cout << std::setfill('0') << "[" << std::setw(2) << localTime->tm_hour << ":" << std::setw(2) << localTime->tm_min << ":" << std::setw(2) << localTime->tm_sec << "] ";
    std::cout << "[" << this->getName() << "] ERROR sending device class : Connection failed! {" <<  connectionStatus <<  "}.";
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_ExtMassStorage::transitionaction_____Connected( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/ComputerSystem/ComputerSystem.uml ComputerSystem::Hardware::ExternalDevices::ExtMassStorage transition connected?,Connected */
    time_t currentTime;
    struct tm *localTime;
    time( &currentTime );
    localTime = localtime( &currentTime );
    std::cout << std::setfill('0') << "[" << std::setw(2) << localTime->tm_hour << ":" << std::setw(2) << localTime->tm_min << ":" << std::setw(2) << localTime->tm_sec << "] ";
    std::cout << "[" << this->getName() << "] sent its device class to the hub and is now in {Connected} state." << std::endl;
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_ExtMassStorage::transitionaction_____Eject( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/ComputerSystem/ComputerSystem.uml ComputerSystem::Hardware::ExternalDevices::ExtMassStorage transition Connected,Disconnected,Eject:usbPort */
    time_t currentTime;
    struct tm *localTime;
    time( &currentTime );
    localTime = localtime( &currentTime );
    std::cout << std::setfill('0') << "[" << std::setw(2) << localTime->tm_hour << ":" << std::setw(2) << localTime->tm_min << ":" << std::setw(2) << localTime->tm_sec << "] ";
    std::cout << "[" << this->getName() << "] ejected" << std::endl;
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_ExtMassStorage::transitionaction_____initialize( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/ComputerSystem/ComputerSystem.uml ComputerSystem::Hardware::ExternalDevices::ExtMassStorage transition Initial1,connected? */
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

void Capsule_ExtMassStorage::transitionaction_____onConnect( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/ComputerSystem/ComputerSystem.uml ComputerSystem::Hardware::ExternalDevices::ExtMassStorage transition Disconnected,Connected,connect:usbPort */
    time_t currentTime;
    struct tm *localTime;
    time( &currentTime );
    localTime = localtime( &currentTime );
    std::cout << std::setfill('0') << "[" << (localTime->tm_year + 1900) << "." << std::setw(2) << (localTime->tm_mon + 1) << "." << std::setw(2) << localTime->tm_mday << " " << std::setw(2) << localTime->tm_hour << ":" << std::setw(2) << localTime->tm_min << ":" << std::setw(2) << localTime->tm_sec << "] ";
    std::cout << "[" << this->getName() << "] received connection request and is now in {Connected} state." << std::endl;
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_ExtMassStorage::transitionaction_____onData( const UMLRTMessage * msg )
{
    #define data ( *(const void * *)msg->getParam( 0 ) )
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/ComputerSystem/ComputerSystem.uml ComputerSystem::Hardware::ExternalDevices::ExtMassStorage transition Connected,Connected,data:usbPort */
    /**
    * Only show logging start on 1st dataset received
    */
    time_t currentTime;
    struct tm *localTime;
    time( &currentTime );
    localTime = localtime( &currentTime );
    if ( ! savingData ) {
    std::cout << std::setfill('0') << "[" << std::setw(2) << localTime->tm_hour << ":" << std::setw(2) << localTime->tm_min << ":" << std::setw(2) << localTime->tm_sec << "] ";
    std::cout << "[" << this->getName() << "] starts saving ==========" << std::endl;
    savingData = true;
    lineCount = 0;
    }
    /**
    * Now print the data
    */
    //std::cout << (char *) *rtdata; // Still need debugging of data retrieval
    std::cout << std::setfill('0') << "[ Output #" << std::setw(4) << ++lineCount << "] " << "Aenean lacinia bibendum nulla sed consectetur." << std::endl;
    std::cout << std::setfill('0') << "[" << std::setw(2) << localTime->tm_hour << ":" << std::setw(2) << localTime->tm_min << ":" << std::setw(2) << localTime->tm_sec << "] ";
    std::cout << "[" << this->getName() << "]";
    if ( usbPort.status(100).send() ) {
    std::cout << "sent status = " << 100 << "%" << std::endl;
    } else {
    std::cout << "ERROR sending status!" << std::endl;
    }
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
    #undef data
}

void Capsule_ExtMassStorage::transitionaction_____onEOD( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/ComputerSystem/ComputerSystem.uml ComputerSystem::Hardware::ExternalDevices::ExtMassStorage transition Connected,Connected,eod:usbPort */
    std::cout << std::endl;
    time_t currentTime;
    struct tm *localTime;
    time( &currentTime );
    localTime = localtime( &currentTime );
    std::cout << std::setfill('0') << "[" << std::setw(2) << localTime->tm_hour << ":" << std::setw(2) << localTime->tm_min << ":" << std::setw(2) << localTime->tm_sec << "] ";
    std::cout << "[" << this->getName() << "] Saving Ended =====" << std::endl;
    savingData = false;
    exit(0); 
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

bool Capsule_ExtMassStorage::guard_____Connect_Failed( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/ComputerSystem/ComputerSystem.uml ComputerSystem::Hardware::ExternalDevices::ExtMassStorage guard connected?,Disconnected */
    return( ! connectionStatus );
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

bool Capsule_ExtMassStorage::guard_____Connected( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/ComputerSystem/ComputerSystem.uml ComputerSystem::Hardware::ExternalDevices::ExtMassStorage guard connected?,Connected */
    return(connectionStatus);
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_ExtMassStorage::actionchain_____Connect_Failed( const UMLRTMessage * msg )
{
    transitionaction_____Connect_Failed( msg );
    update_state( Disconnected );
    entryaction_____Disconnected( msg );
}

void Capsule_ExtMassStorage::actionchain_____Connected( const UMLRTMessage * msg )
{
    transitionaction_____Connected( msg );
    update_state( Connected );
}

void Capsule_ExtMassStorage::actionchain_____Eject( const UMLRTMessage * msg )
{
    update_state( SPECIAL_INTERNAL_STATE_TOP );
    transitionaction_____Eject( msg );
    update_state( Disconnected );
    entryaction_____Disconnected( msg );
}

void Capsule_ExtMassStorage::actionchain_____initialize( const UMLRTMessage * msg )
{
    transitionaction_____initialize( msg );
}

void Capsule_ExtMassStorage::actionchain_____onConnect( const UMLRTMessage * msg )
{
    update_state( SPECIAL_INTERNAL_STATE_TOP );
    transitionaction_____onConnect( msg );
    update_state( Connected );
}

void Capsule_ExtMassStorage::actionchain_____onData( const UMLRTMessage * msg )
{
    update_state( SPECIAL_INTERNAL_STATE_TOP );
    transitionaction_____onData( msg );
    update_state( Connected );
}

void Capsule_ExtMassStorage::actionchain_____onEOD( const UMLRTMessage * msg )
{
    update_state( SPECIAL_INTERNAL_STATE_TOP );
    transitionaction_____onEOD( msg );
    update_state( Connected );
}

Capsule_ExtMassStorage::State Capsule_ExtMassStorage::choice_____connected_( const UMLRTMessage * msg )
{
    if( guard_____Connected( msg ) )
    {
        actionchain_____Connected( msg );
        return Connected;
    }
    else if( guard_____Connect_Failed( msg ) )
    {
        actionchain_____Connect_Failed( msg );
        return Disconnected;
    }
    return currentState;
}

Capsule_ExtMassStorage::State Capsule_ExtMassStorage::state_____Connected( const UMLRTMessage * msg )
{
    switch( msg->destPort->role()->id )
    {
    case port_usbPort:
        switch( msg->getSignalId() )
        {
        case USBProtocol::signal_Eject:
            actionchain_____Eject( msg );
            return Disconnected;
        case USBProtocol::signal_data:
            actionchain_____onData( msg );
            return Connected;
        case USBProtocol::signal_eod:
            actionchain_____onEOD( msg );
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

Capsule_ExtMassStorage::State Capsule_ExtMassStorage::state_____Disconnected( const UMLRTMessage * msg )
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
        Capsule_ExtMassStorage::port_usbPort,
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
        Capsule_ExtMassStorage::port_log,
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

static void instantiate_ExtMassStorage( const UMLRTRtsInterface * rts, UMLRTSlot * slot, const UMLRTCommsPort * * borderPorts )
{
    slot->capsule = new Capsule_ExtMassStorage( &ExtMassStorage, slot, borderPorts, NULL, false );
}

const UMLRTCapsuleClass ExtMassStorage = 
{
    "ExtMassStorage",
    NULL,
    instantiate_ExtMassStorage,
    0,
    NULL,
    2,
    portroles_border,
    0,
    NULL
};

