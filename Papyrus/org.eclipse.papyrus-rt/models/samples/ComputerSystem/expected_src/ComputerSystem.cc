
#include "ComputerSystem.hh"

#include "Computer.hh"
#include "ExtMassStorage.hh"
#include "LocalPrinter.hh"
#include "User.hh"
#include "umlrtcapsuleclass.hh"
#include "umlrtcapsulepart.hh"
#include "umlrtcommsportrole.hh"
#include "umlrtmessage.hh"
#include "umlrtslot.hh"
#include "umlrttimerprotocol.hh"
#include <cstddef>
#include "umlrtcapsulerole.hh"
#include "umlrtcommsport.hh"
#include "umlrtframeservice.hh"
class UMLRTRtsInterface;

Capsule_ComputerSystem::Capsule_ComputerSystem( const UMLRTCapsuleClass * cd, UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat )
: UMLRTCapsule( NULL, cd, st, border, internal, isStat )
, currentState( SPECIAL_INTERNAL_STATE_UNVISITED )
, frame( borderPorts[borderport_frame] )
, timer( borderPorts[borderport_timer] )
, computer( &slot->parts[part_computer] )
, massStorage( &slot->parts[part_massStorage] )
, printer( &slot->parts[part_printer] )
, user( &slot->parts[part_user] )
{
    stateNames[Failure] = "Failure";
    stateNames[Running] = "Running";
    stateNames[WaitingForComputerInit] = "WaitingForComputerInit";
    stateNames[WaitingForDeviceInit] = "WaitingForDeviceInit";
    stateNames[SPECIAL_INTERNAL_STATE_TOP] = "<top>";
    stateNames[SPECIAL_INTERNAL_STATE_UNVISITED] = "<uninitialized>";
}


void Capsule_ComputerSystem::inject( const UMLRTMessage & message )
{
    msg = &message;
    switch( currentState )
    {
    case Running:
        currentState = state_____Running( &message );
        break;
    case Failure:
        currentState = state_____Failure( &message );
        break;
    case WaitingForComputerInit:
        currentState = state_____WaitingForComputerInit( &message );
        break;
    case WaitingForDeviceInit:
        currentState = state_____WaitingForDeviceInit( &message );
        break;
    default:
        break;
    }
}

void Capsule_ComputerSystem::initialize( const UMLRTMessage & message )
{
    msg = &message;
    actionchain_____setComputerInitTimer( &message );
    currentState = choice_____timerOK_( &message );
}

const char * Capsule_ComputerSystem::getCurrentStateString() const
{
    return stateNames[currentState];
}




void Capsule_ComputerSystem::update_state( Capsule_ComputerSystem::State newState )
{
    currentState = newState;
}

void Capsule_ComputerSystem::entryaction_____Failure( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/ComputerSystem/ComputerSystem.uml ComputerSystem::System::ComputerSystem::Failure entry  */
    time_t currentTime;
    struct tm *localTime;
    time( &currentTime );
    localTime = localtime( &currentTime );
    std::cout << std::setfill('0') << "[" << (localTime->tm_year + 1900) << "." << std::setw(2) << (localTime->tm_mon + 1) << "." << std::setw(2) << localTime->tm_mday << " " << std::setw(2) << localTime->tm_hour << ":" << std::setw(2) << localTime->tm_min << ":" << std::setw(2) << localTime->tm_sec << "] ";
    std::cout << "[" << this->getName() << "] terminated in failure!" << std::endl;
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_ComputerSystem::entryaction_____Running( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/ComputerSystem/ComputerSystem.uml ComputerSystem::System::ComputerSystem::Running entry  */
    time_t currentTime;
    struct tm *localTime;
    time( &currentTime );
    localTime = localtime( &currentTime );
    std::cout << std::setfill('0') << "[" << std::setw(2) << localTime->tm_hour << ":" << std::setw(2) << localTime->tm_min << ":" << std::setw(2) << localTime->tm_sec << "] ";
    std::cout << "[" << this->getName() << "] is {Running}." << std::endl;
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_ComputerSystem::entryaction_____WaitingForComputerInit( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/ComputerSystem/ComputerSystem.uml ComputerSystem::System::ComputerSystem::WaitingForComputerInit entry  */
    time_t currentTime;
    struct tm *localTime;
    time( &currentTime );
    localTime = localtime( &currentTime );
    std::cout << std::setfill('0') << "[" << std::setw(2) << localTime->tm_hour << ":" << std::setw(2) << localTime->tm_min << ":" << std::setw(2) << localTime->tm_sec << "] ";
    std::cout << "[" << this->getName() << "] is waiting for computer initialisation {WaitingForComputerInit}." << std::endl;
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_ComputerSystem::entryaction_____WaitingForDeviceInit( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/ComputerSystem/ComputerSystem.uml ComputerSystem::System::ComputerSystem::WaitingForDeviceInit entry  */
    time_t currentTime;
    struct tm *localTime;
    time( &currentTime );
    localTime = localtime( &currentTime );
    std::cout << std::setfill('0') << "[" << std::setw(2) << localTime->tm_hour << ":" << std::setw(2) << localTime->tm_min << ":" << std::setw(2) << localTime->tm_sec << "] ";
    std::cout << "[" << this->getName() << "] is waiting for external device initialization." << std::endl;
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_ComputerSystem::transitionaction_____Failed( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/ComputerSystem/ComputerSystem.uml ComputerSystem::System::ComputerSystem transition initOK?,Failure */
    time_t currentTime;
    struct tm *localTime;
    time( &currentTime );
    localTime = localtime( &currentTime );
    std::cout << std::setfill('0') << "[" << std::setw(2) << localTime->tm_hour << ":" << std::setw(2) << localTime->tm_min << ":" << std::setw(2) << localTime->tm_sec << "] ";
    std::cout << "[" << this->getName() << "] did not incarnate all devices!" << std::endl;
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_ComputerSystem::transitionaction_____Success( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/ComputerSystem/ComputerSystem.uml ComputerSystem::System::ComputerSystem transition initOK?,WaitingForDeviceInit */
    time_t currentTime;
    struct tm *localTime;
    time( &currentTime );
    localTime = localtime( &currentTime );
    std::cout << std::setfill('0') << "[" << std::setw(2) << localTime->tm_hour << ":" << std::setw(2) << localTime->tm_min << ":" << std::setw(2) << localTime->tm_sec << "] ";
    std::cout << "[" << this->getName() << "] incarnated all external devices!" << std::endl;
    // context()->debugOutputModel();
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_ComputerSystem::transitionaction_____compTimerFail( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/ComputerSystem/ComputerSystem.uml ComputerSystem::System::ComputerSystem transition timerOK?,Failure */
    std::cout << " but FAILed to set a " << numSec << " seconds timer!" << std::endl;
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_ComputerSystem::transitionaction_____incarnateDevices( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/ComputerSystem/ComputerSystem.uml ComputerSystem::System::ComputerSystem transition WaitingForComputerInit,initOK?,timeout:timer */
    /**
    * Initialise the devices
    */
    /**
    * Incarnate the local printer
    */
    time_t currentTime;
    struct tm *localTime;
    time( &currentTime );
    localTime = localtime( &currentTime );
    std::cout << std::setfill('0') << "[" << std::setw(2) << localTime->tm_hour << ":" << std::setw(2) << localTime->tm_min << ":" << std::setw(2) << localTime->tm_sec << "] ";
    std::cout << "[" << this->getName() << "] local printer incarnation";
    UMLRTCapsuleId printerId = frame.incarnate(printer, LocalPrinter);
    bool printerStatus = printerId.isValid();
    if ( printerStatus ) {
    std::cout << " successful." << std::endl;
    } else {
    std::cout << " FAILED!" << std::endl;
    }
    /**
    * Incarnate the mass storage device
    */
    time( &currentTime );
    localTime = localtime( &currentTime );
    std::cout << std::setfill('0') << "[" << std::setw(2) << localTime->tm_hour << ":" << std::setw(2) << localTime->tm_min << ":" << std::setw(2) << localTime->tm_sec << "] ";
    std::cout << "[" << this->getName() << "] mass storage incarnation";
    UMLRTCapsuleId storageId = frame.incarnate(massStorage, ExtMassStorage);
    bool storageStatus = storageId.isValid();
    if ( storageStatus ) {
    std::cout << " successful." << std::endl;
    } else {
    std::cout << " FAILED!" << std::endl;
    }
    bool timerStatus = false;
    if ( printerStatus && storageStatus ) {
    /**
    * Set timer to wait for device initialisation
    */
    numSec = 15;
    timerID = timer.informIn(UMLRTTimespec(numSec,0));
    timerStatus = timerID.isValid();
    time( &currentTime );
    localTime = localtime( &currentTime );
    std::cout << std::setfill('0') << "[" << std::setw(2) << localTime->tm_hour << ":" << std::setw(2) << localTime->tm_min << ":" << std::setw(2) << localTime->tm_sec << "] ";
    std::cout << "[" << this->getName() << "] ";
    if ( ! timerStatus ) {
    std::cout << "FAILED to ";
    }
    std::cout << "set " << numSec << " seconds timout for device initialization wait." << std::endl;
    }
    initStatus = ( printerStatus && storageStatus && timerStatus);
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_ComputerSystem::transitionaction_____incarnateUser( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/ComputerSystem/ComputerSystem.uml ComputerSystem::System::ComputerSystem transition WaitingForDeviceInit,userOK?,timeout:timer */
    /**
    * Incarnate the user
    */
    time_t currentTime;
    struct tm *localTime;
    time( &currentTime );
    localTime = localtime( &currentTime );
    std::cout << std::setfill('0') << "[" << std::setw(2) << localTime->tm_hour << ":" << std::setw(2) << localTime->tm_min << ":" << std::setw(2) << localTime->tm_sec << "] ";
    std::cout << "[" << this->getName() << "] is incarnating a user." << std::endl;
    UMLRTCapsuleId userId = frame.incarnate(user, User);
    initStatus = userId.isValid();
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_ComputerSystem::transitionaction_____setComputerInitTimer( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/ComputerSystem/ComputerSystem.uml ComputerSystem::System::ComputerSystem transition Initial1,timerOK? */
    time_t currentTime;
    struct tm *localTime;
    time( &currentTime );
    localTime = localtime( &currentTime );
    std::cout << std::setfill('0') << "[" << std::setw(2) << localTime->tm_hour << ":" << std::setw(2) << localTime->tm_min << ":" << std::setw(2) << localTime->tm_sec << "] ";
    std::cout << "[" << this->getName() << "] is initializing ";
    numSec = 20;
    timerID = timer.informIn(UMLRTTimespec(numSec,0));
    initStatus = timerID.isValid();
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_ComputerSystem::transitionaction_____timerOK( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/ComputerSystem/ComputerSystem.uml ComputerSystem::System::ComputerSystem transition timerOK?,WaitingForComputerInit */
    std::cout << " and set a " << numSec << " seconds timer." << std::endl;
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_ComputerSystem::transitionaction_____userNOK( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/ComputerSystem/ComputerSystem.uml ComputerSystem::System::ComputerSystem transition userOK?,Failure */
    time_t currentTime;
    struct tm *localTime;
    time( &currentTime );
    localTime = localtime( &currentTime );
    std::cout << std::setfill('0') << "[" << std::setw(2) << localTime->tm_hour << ":" << std::setw(2) << localTime->tm_min << ":" << std::setw(2) << localTime->tm_sec << "] ";
    std::cout << "[" << this->getName() << "] FAILed to incarnate the user!" << std::endl;
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_ComputerSystem::transitionaction_____userOK( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/ComputerSystem/ComputerSystem.uml ComputerSystem::System::ComputerSystem transition userOK?,Running */
    time_t currentTime;
    struct tm *localTime;
    time( &currentTime );
    localTime = localtime( &currentTime );
    std::cout << std::setfill('0') << "[" << std::setw(2) << localTime->tm_hour << ":" << std::setw(2) << localTime->tm_min << ":" << std::setw(2) << localTime->tm_sec << "] ";
    std::cout << "[" << this->getName() << "] incarnated the user." << std::endl;
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

bool Capsule_ComputerSystem::guard_____Failed( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/ComputerSystem/ComputerSystem.uml ComputerSystem::System::ComputerSystem guard initOK?,Failure */
    return(!initStatus);
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

bool Capsule_ComputerSystem::guard_____Success( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/ComputerSystem/ComputerSystem.uml ComputerSystem::System::ComputerSystem guard initOK?,WaitingForDeviceInit */
    return(initStatus);
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

bool Capsule_ComputerSystem::guard_____compTimerFail( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/ComputerSystem/ComputerSystem.uml ComputerSystem::System::ComputerSystem guard timerOK?,Failure */
    return(!initStatus);
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

bool Capsule_ComputerSystem::guard_____timerOK( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/ComputerSystem/ComputerSystem.uml ComputerSystem::System::ComputerSystem guard timerOK?,WaitingForComputerInit */
    return(initStatus);
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

bool Capsule_ComputerSystem::guard_____userNOK( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/ComputerSystem/ComputerSystem.uml ComputerSystem::System::ComputerSystem guard userOK?,Failure */
    return(!initStatus);
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

bool Capsule_ComputerSystem::guard_____userOK( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/ComputerSystem/ComputerSystem.uml ComputerSystem::System::ComputerSystem guard userOK?,Running */
    return(initStatus);
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_ComputerSystem::actionchain_____Failed( const UMLRTMessage * msg )
{
    transitionaction_____Failed( msg );
    update_state( Failure );
    entryaction_____Failure( msg );
}

void Capsule_ComputerSystem::actionchain_____Success( const UMLRTMessage * msg )
{
    transitionaction_____Success( msg );
    update_state( WaitingForDeviceInit );
    entryaction_____WaitingForDeviceInit( msg );
}

void Capsule_ComputerSystem::actionchain_____compTimerFail( const UMLRTMessage * msg )
{
    transitionaction_____compTimerFail( msg );
    update_state( Failure );
    entryaction_____Failure( msg );
}

void Capsule_ComputerSystem::actionchain_____incarnateDevices( const UMLRTMessage * msg )
{
    update_state( SPECIAL_INTERNAL_STATE_TOP );
    transitionaction_____incarnateDevices( msg );
}

void Capsule_ComputerSystem::actionchain_____incarnateUser( const UMLRTMessage * msg )
{
    update_state( SPECIAL_INTERNAL_STATE_TOP );
    transitionaction_____incarnateUser( msg );
}

void Capsule_ComputerSystem::actionchain_____setComputerInitTimer( const UMLRTMessage * msg )
{
    transitionaction_____setComputerInitTimer( msg );
}

void Capsule_ComputerSystem::actionchain_____timerOK( const UMLRTMessage * msg )
{
    transitionaction_____timerOK( msg );
    update_state( WaitingForComputerInit );
    entryaction_____WaitingForComputerInit( msg );
}

void Capsule_ComputerSystem::actionchain_____userNOK( const UMLRTMessage * msg )
{
    transitionaction_____userNOK( msg );
    update_state( Failure );
    entryaction_____Failure( msg );
}

void Capsule_ComputerSystem::actionchain_____userOK( const UMLRTMessage * msg )
{
    transitionaction_____userOK( msg );
    update_state( Running );
    entryaction_____Running( msg );
}

Capsule_ComputerSystem::State Capsule_ComputerSystem::choice_____initOK_( const UMLRTMessage * msg )
{
    if( guard_____Failed( msg ) )
    {
        actionchain_____Failed( msg );
        return Failure;
    }
    else if( guard_____Success( msg ) )
    {
        actionchain_____Success( msg );
        return WaitingForDeviceInit;
    }
    return currentState;
}

Capsule_ComputerSystem::State Capsule_ComputerSystem::choice_____timerOK_( const UMLRTMessage * msg )
{
    if( guard_____timerOK( msg ) )
    {
        actionchain_____timerOK( msg );
        return WaitingForComputerInit;
    }
    else if( guard_____compTimerFail( msg ) )
    {
        actionchain_____compTimerFail( msg );
        return Failure;
    }
    return currentState;
}

Capsule_ComputerSystem::State Capsule_ComputerSystem::choice_____userOK_( const UMLRTMessage * msg )
{
    if( guard_____userOK( msg ) )
    {
        actionchain_____userOK( msg );
        return Running;
    }
    else if( guard_____userNOK( msg ) )
    {
        actionchain_____userNOK( msg );
        return Failure;
    }
    return currentState;
}

Capsule_ComputerSystem::State Capsule_ComputerSystem::state_____Failure( const UMLRTMessage * msg )
{
    switch( msg->destPort->role()->id )
    {
    default:
        this->unexpectedMessage();
        break;
    }
    return currentState;
}

Capsule_ComputerSystem::State Capsule_ComputerSystem::state_____Running( const UMLRTMessage * msg )
{
    switch( msg->destPort->role()->id )
    {
    default:
        this->unexpectedMessage();
        break;
    }
    return currentState;
}

Capsule_ComputerSystem::State Capsule_ComputerSystem::state_____WaitingForComputerInit( const UMLRTMessage * msg )
{
    switch( msg->destPort->role()->id )
    {
    case port_timer:
        switch( msg->getSignalId() )
        {
        case UMLRTTimerProtocol::signal_timeout:
            actionchain_____incarnateDevices( msg );
            return choice_____initOK_( msg );
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

Capsule_ComputerSystem::State Capsule_ComputerSystem::state_____WaitingForDeviceInit( const UMLRTMessage * msg )
{
    switch( msg->destPort->role()->id )
    {
    case port_timer:
        switch( msg->getSignalId() )
        {
        case UMLRTTimerProtocol::signal_timeout:
            actionchain_____incarnateUser( msg );
            return choice_____userOK_( msg );
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









void Capsule_ComputerSystem::bindPort( bool isBorder, int portId, int index )
{
}

void Capsule_ComputerSystem::unbindPort( bool isBorder, int portId, int index )
{
}






static const UMLRTCapsuleRole roles[] = 
{
    {
        "computer",
        &Computer,
        1,
        1,
        false,
        false
    },
    {
        "massStorage",
        &ExtMassStorage,
        0,
        1,
        true,
        false
    },
    {
        "printer",
        &LocalPrinter,
        0,
        1,
        true,
        false
    },
    {
        "user",
        &User,
        0,
        1,
        true,
        false
    }
};

static const UMLRTCommsPortRole portroles_border[] = 
{
    {
        Capsule_ComputerSystem::port_frame,
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
        Capsule_ComputerSystem::port_timer,
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

static void instantiate_ComputerSystem( const UMLRTRtsInterface * rts, UMLRTSlot * slot, const UMLRTCommsPort * * borderPorts )
{
    UMLRTFrameService::connectPorts( &slot->parts[Capsule_ComputerSystem::part_computer].slots[0]->ports[Capsule_Computer::borderport_usbPort], 0, &slot->parts[Capsule_ComputerSystem::part_printer].slots[0]->ports[Capsule_LocalPrinter::borderport_usbPort], 0 );
    UMLRTFrameService::connectPorts( &slot->parts[Capsule_ComputerSystem::part_computer].slots[0]->ports[Capsule_Computer::borderport_usbPort], 1, &slot->parts[Capsule_ComputerSystem::part_massStorage].slots[0]->ports[Capsule_ExtMassStorage::borderport_usbPort], 0 );
    UMLRTFrameService::connectPorts( &slot->parts[Capsule_ComputerSystem::part_computer].slots[0]->ports[Capsule_Computer::borderport_userPort], 0, &slot->parts[Capsule_ComputerSystem::part_user].slots[0]->ports[Capsule_User::borderport_computerPort], 0 );
    Computer.instantiate( NULL, slot->parts[Capsule_ComputerSystem::part_computer].slots[0], UMLRTFrameService::createBorderPorts( slot->parts[Capsule_ComputerSystem::part_computer].slots[0], Computer.numPortRolesBorder ) );
    slot->capsule = new Capsule_ComputerSystem( &ComputerSystem, slot, borderPorts, NULL, false );
}

const UMLRTCapsuleClass ComputerSystem = 
{
    "ComputerSystem",
    NULL,
    instantiate_ComputerSystem,
    4,
    roles,
    2,
    portroles_border,
    0,
    NULL
};

