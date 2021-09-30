
#include "User.hh"

#include "umlrtcommsportrole.hh"
#include "umlrtmessage.hh"
#include "umlrtslot.hh"
#include "umlrttimerprotocol.hh"
#include <cstddef>
#include "umlrtcapsuleclass.hh"
#include "umlrtframeservice.hh"
class UMLRTRtsInterface;
struct UMLRTCommsPort;

Capsule_User::Capsule_User( const UMLRTCapsuleClass * cd, UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat )
: UMLRTCapsule( NULL, cd, st, border, internal, isStat )
, computerPort( borderPorts[borderport_computerPort] )
, timer( borderPorts[borderport_timer] )
, currentState( SPECIAL_INTERNAL_STATE_UNVISITED )
{
    stateNames[Alive] = "Alive";
    stateNames[FAILED] = "FAILED";
    stateNames[TestDone] = "TestDone";
    stateNames[Waiting4DocPrint] = "Waiting4DocPrint";
    stateNames[Waiting4DocSave] = "Waiting4DocSave";
    stateNames[SPECIAL_INTERNAL_STATE_TOP] = "<top>";
    stateNames[SPECIAL_INTERNAL_STATE_UNVISITED] = "<uninitialized>";
}






void Capsule_User::bindPort( bool isBorder, int portId, int index )
{
    if( isBorder )
        switch( portId )
        {
        case borderport_computerPort:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_computerPort, index, true );
            break;
        }
}

void Capsule_User::unbindPort( bool isBorder, int portId, int index )
{
    if( isBorder )
        switch( portId )
        {
        case borderport_computerPort:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_computerPort, index, false );
            UMLRTFrameService::disconnectPort( borderPorts[borderport_computerPort], index );
            break;
        }
}




void Capsule_User::inject( const UMLRTMessage & message )
{
    msg = &message;
    switch( currentState )
    {
    case Alive:
        currentState = state_____Alive( &message );
        break;
    case Waiting4DocSave:
        currentState = state_____Waiting4DocSave( &message );
        break;
    case Waiting4DocPrint:
        currentState = state_____Waiting4DocPrint( &message );
        break;
    case TestDone:
        currentState = state_____TestDone( &message );
        break;
    case FAILED:
        currentState = state_____FAILED( &message );
        break;
    default:
        break;
    }
}

void Capsule_User::initialize( const UMLRTMessage & message )
{
    msg = &message;
    actionchain_____initialize( &message );
    currentState = choice_____tid1_( &message );
}

const char * Capsule_User::getCurrentStateString() const
{
    return stateNames[currentState];
}




void Capsule_User::update_state( Capsule_User::State newState )
{
    currentState = newState;
}

void Capsule_User::entryaction_____Alive( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/ComputerSystem/ComputerSystem.uml ComputerSystem::System::User::Alive entry  */
    time_t currentTime;
    struct tm *localTime;
    time( &currentTime );
    localTime = localtime( &currentTime );
    std::cout << std::setfill('0') << "[" << std::setw(2) << localTime->tm_hour << ":" << std::setw(2) << localTime->tm_min << ":" << std::setw(2) << localTime->tm_sec << "] ";
    std::cout << "[" << this->getName() << "(" << this->getClass()->name << ")] is alive!" << std::endl;
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_User::entryaction_____FAILED( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/ComputerSystem/ComputerSystem.uml ComputerSystem::System::User::FAILED entry  */
    time_t currentTime;
    struct tm *localTime;
    time( &currentTime );
    localTime = localtime( &currentTime );
    std::cout << std::setfill('0') << "[" << std::setw(2) << localTime->tm_hour << ":" << std::setw(2) << localTime->tm_min << ":" << std::setw(2) << localTime->tm_sec << "] ";
    std::cout << "[" << this->getName() << "(" << this->getClass()->name << ")] {FAILED}!";
    std::exit(EXIT_FAILURE); // unsuccessful execution
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_User::entryaction_____TestDone( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/ComputerSystem/ComputerSystem.uml ComputerSystem::System::User::TestDone entry  */
    time_t currentTime;
    struct tm *localTime;
    time( &currentTime );
    localTime = localtime( &currentTime );
    std::cout << std::setfill('0') << "[" << std::setw(2) << localTime->tm_hour << ":" << std::setw(2) << localTime->tm_min << ":" << std::setw(2) << localTime->tm_sec << "] ";
    std::cout << "[" << this->getName() << "(" << this->getClass()->name << ")] is finished testing." << std::endl;
    std::exit(EXIT_SUCCESS); // successful execution
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_User::entryaction_____Waiting4DocPrint( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/ComputerSystem/ComputerSystem.uml ComputerSystem::System::User::Waiting4DocPrint entry  */
    std::cout << "[" << this->getName() << "(" << this->getClass()->name << ")] user is {Waiting4DocPrint}" << std::endl;
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_User::entryaction_____Waiting4DocSave( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/ComputerSystem/ComputerSystem.uml ComputerSystem::System::User::Waiting4DocSave entry  */
    time_t currentTime;
    struct tm *localTime;
    time( &currentTime );
    localTime = localtime( &currentTime );
    std::cout << std::setfill('0') << "[" << std::setw(2) << localTime->tm_hour << ":" << std::setw(2) << localTime->tm_min << ":" << std::setw(2) << localTime->tm_sec << "] ";
    std::cout << "[" << this->getName() << "(" << this->getClass()->name << ")] user is {Waiting4DocSave}" << std::endl;
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_User::transitionaction_____initTimerFail( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/ComputerSystem/ComputerSystem.uml ComputerSystem::System::User transition tid1?,FAILED */
    std::cout << " but FAILed to set a " << numSec << " seconds timer." << std::endl;
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_User::transitionaction_____initTimerSet( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/ComputerSystem/ComputerSystem.uml ComputerSystem::System::User transition tid1?,Alive */
    std::cout << " and set a " << numSec << " seconds timer." << std::endl;
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_User::transitionaction_____initialize( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/ComputerSystem/ComputerSystem.uml ComputerSystem::System::User transition Initial,tid1? */
    time_t currentTime;
    struct tm *localTime;
    time( &currentTime );
    localTime = localtime( &currentTime );
    std::cout << std::setfill('0') << "[" << std::setw(2) << localTime->tm_hour << ":" << std::setw(2) << localTime->tm_min << ":" << std::setw(2) << localTime->tm_sec << "] ";
    std::cout << "[" << this->getName() << "(" << this->getClass()->name << ")] initializing";
    numSec = 15;
    timerID = timer.informIn(UMLRTTimespec(numSec,0));
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_User::transitionaction_____isFalsePrintDoc( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/ComputerSystem/ComputerSystem.uml ComputerSystem::System::User transition TesterChoice,tid3? */
    time_t currentTime;
    struct tm *localTime;
    time( &currentTime );
    localTime = localtime( &currentTime );
    std::cout << std::setfill('0') << "[" << std::setw(2) << localTime->tm_hour << ":" << std::setw(2) << localTime->tm_min << ":" << std::setw(2) << localTime->tm_sec << "] ";
    std::cout << "[" << this->getName() << "]";
    bool sendStatus = computerPort.printDocument().send();
    //numSec = 10;
    timerID = timer.informIn(UMLRTTimespec(numSec,0));
    status = sendStatus && timerID.isValid();
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_User::transitionaction_____isTrueDone( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/ComputerSystem/ComputerSystem.uml ComputerSystem::System::User transition TesterChoice,TestDone */
    time_t currentTime;
    struct tm *localTime;
    time( &currentTime );
    localTime = localtime( &currentTime );
    std::cout << std::setfill('0') << "["<< std::setw(2) << localTime->tm_hour << ":" << std::setw(2) << localTime->tm_min << ":" << std::setw(2) << localTime->tm_sec << "] ";
    std::cout << "[" << this->getName() << "(" << this->getClass()->name << ")] Successful Run!" << std::endl;
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_User::transitionaction_____onTimeout1( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/ComputerSystem/ComputerSystem.uml ComputerSystem::System::User transition Alive,tid2?,timeout:timer */
    time_t currentTime;
    struct tm *localTime;
    time( &currentTime );
    localTime = localtime( &currentTime );
    std::cout << std::setfill('0') << "[" << std::setw(2) << localTime->tm_hour << ":" << std::setw(2) << localTime->tm_min << ":" << std::setw(2) << localTime->tm_sec << "] ";
    std::cout << "[" << this->getName() << "(" << this->getClass()->name << ")]";
    bool sendStatus = computerPort.saveDocument().send();
    //numSec = 10;
    timerID = timer.informIn(UMLRTTimespec(numSec,0));
    status = sendStatus && timerID.isValid();
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_User::transitionaction_____onTimeout2( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/ComputerSystem/ComputerSystem.uml ComputerSystem::System::User transition Waiting4DocSave,TesterChoice,timeout:timer */
    time_t currentTime;
    struct tm *localTime;
    time( &currentTime );
    localTime = localtime( &currentTime );
    std::cout << std::setfill('0') << "[" << std::setw(2) << localTime->tm_hour << ":" << std::setw(2) << localTime->tm_min << ":" << std::setw(2) << localTime->tm_sec << "] ";
    std::cout << "[" << this->getName() << "(" << this->getClass()->name << ")] Save timeout received" << std::endl;
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_User::transitionaction_____transition6( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/ComputerSystem/ComputerSystem.uml ComputerSystem::System::User transition tid2?,Waiting4DocSave */
    std::cout << " asked for the document to be saved." << std::endl;
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_User::transitionaction_____transition7( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/ComputerSystem/ComputerSystem.uml ComputerSystem::System::User transition tid3?,Waiting4DocPrint */
    std::cout << " asked for the document to be printed." << std::endl;
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_User::transitionaction_____transition8( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/ComputerSystem/ComputerSystem.uml ComputerSystem::System::User transition tid2?,FAILED */
    std::cout << " FAILed to ask for the document to be saved!";
    if ( timerID.isValid() ) {
    std::cout << " - FAILed to send message!" << std::endl;
    } else {
    std::cout << " FAILed to set timer!" << std::endl;
    }
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_User::transitionaction_____transition9( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/ComputerSystem/ComputerSystem.uml ComputerSystem::System::User transition tid3?,FAILED */
    std::cout << " FAILed to ask for the document to be printed!";
    if ( timerID.isValid() ) {
    std::cout << " - FAILed to send message!" << std::endl;
    } else {
    std::cout << " FAILed to set timer!" << std::endl;
    }
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

bool Capsule_User::guard_____initTimerFail( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/ComputerSystem/ComputerSystem.uml ComputerSystem::System::User guard tid1?,FAILED */
    return(!timerID.isValid());
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

bool Capsule_User::guard_____initTimerSet( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/ComputerSystem/ComputerSystem.uml ComputerSystem::System::User guard tid1?,Alive */
    return(timerID.isValid());
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

bool Capsule_User::guard_____isFalsePrintDoc( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/ComputerSystem/ComputerSystem.uml ComputerSystem::System::User guard TesterChoice,tid3? */
    return(false);
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

bool Capsule_User::guard_____isTrueDone( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/ComputerSystem/ComputerSystem.uml ComputerSystem::System::User guard TesterChoice,TestDone */
    return(true);
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

bool Capsule_User::guard_____transition6( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/ComputerSystem/ComputerSystem.uml ComputerSystem::System::User guard tid2?,Waiting4DocSave */
    return(status);
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

bool Capsule_User::guard_____transition7( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/ComputerSystem/ComputerSystem.uml ComputerSystem::System::User guard tid3?,Waiting4DocPrint */
    return(status);
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

bool Capsule_User::guard_____transition8( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/ComputerSystem/ComputerSystem.uml ComputerSystem::System::User guard tid2?,FAILED */
    return(!status);
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

bool Capsule_User::guard_____transition9( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/ComputerSystem/ComputerSystem.uml ComputerSystem::System::User guard tid3?,FAILED */
    return(!status);
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_User::actionchain_____initTimerFail( const UMLRTMessage * msg )
{
    transitionaction_____initTimerFail( msg );
    update_state( FAILED );
    entryaction_____FAILED( msg );
}

void Capsule_User::actionchain_____initTimerSet( const UMLRTMessage * msg )
{
    transitionaction_____initTimerSet( msg );
    update_state( Alive );
    entryaction_____Alive( msg );
}

void Capsule_User::actionchain_____initialize( const UMLRTMessage * msg )
{
    transitionaction_____initialize( msg );
}

void Capsule_User::actionchain_____isFalsePrintDoc( const UMLRTMessage * msg )
{
    transitionaction_____isFalsePrintDoc( msg );
}

void Capsule_User::actionchain_____isTrueDone( const UMLRTMessage * msg )
{
    transitionaction_____isTrueDone( msg );
    update_state( TestDone );
    entryaction_____TestDone( msg );
}

void Capsule_User::actionchain_____onTimeout1( const UMLRTMessage * msg )
{
    update_state( SPECIAL_INTERNAL_STATE_TOP );
    transitionaction_____onTimeout1( msg );
}

void Capsule_User::actionchain_____onTimeout2( const UMLRTMessage * msg )
{
    update_state( SPECIAL_INTERNAL_STATE_TOP );
    transitionaction_____onTimeout2( msg );
}

void Capsule_User::actionchain_____onTimeout3( const UMLRTMessage * msg )
{
    update_state( SPECIAL_INTERNAL_STATE_TOP );
    update_state( FAILED );
    entryaction_____FAILED( msg );
}

void Capsule_User::actionchain_____transition10( const UMLRTMessage * msg )
{
    update_state( SPECIAL_INTERNAL_STATE_TOP );
    update_state( TestDone );
    entryaction_____TestDone( msg );
}

void Capsule_User::actionchain_____transition6( const UMLRTMessage * msg )
{
    transitionaction_____transition6( msg );
    update_state( Waiting4DocSave );
    entryaction_____Waiting4DocSave( msg );
}

void Capsule_User::actionchain_____transition7( const UMLRTMessage * msg )
{
    transitionaction_____transition7( msg );
    update_state( Waiting4DocPrint );
    entryaction_____Waiting4DocPrint( msg );
}

void Capsule_User::actionchain_____transition8( const UMLRTMessage * msg )
{
    transitionaction_____transition8( msg );
    update_state( FAILED );
    entryaction_____FAILED( msg );
}

void Capsule_User::actionchain_____transition9( const UMLRTMessage * msg )
{
    transitionaction_____transition9( msg );
    update_state( FAILED );
    entryaction_____FAILED( msg );
}

Capsule_User::State Capsule_User::choice_____TesterChoice( const UMLRTMessage * msg )
{
    if( guard_____isFalsePrintDoc( msg ) )
    {
        actionchain_____isFalsePrintDoc( msg );
        return choice_____tid3_( msg );
    }
    else if( guard_____isTrueDone( msg ) )
    {
        actionchain_____isTrueDone( msg );
        return TestDone;
    }
    return currentState;
}

Capsule_User::State Capsule_User::choice_____tid1_( const UMLRTMessage * msg )
{
    if( guard_____initTimerSet( msg ) )
    {
        actionchain_____initTimerSet( msg );
        return Alive;
    }
    else if( guard_____initTimerFail( msg ) )
    {
        actionchain_____initTimerFail( msg );
        return FAILED;
    }
    return currentState;
}

Capsule_User::State Capsule_User::choice_____tid2_( const UMLRTMessage * msg )
{
    if( guard_____transition6( msg ) )
    {
        actionchain_____transition6( msg );
        return Waiting4DocSave;
    }
    else if( guard_____transition8( msg ) )
    {
        actionchain_____transition8( msg );
        return FAILED;
    }
    return currentState;
}

Capsule_User::State Capsule_User::choice_____tid3_( const UMLRTMessage * msg )
{
    if( guard_____transition7( msg ) )
    {
        actionchain_____transition7( msg );
        return Waiting4DocPrint;
    }
    else if( guard_____transition9( msg ) )
    {
        actionchain_____transition9( msg );
        return FAILED;
    }
    return currentState;
}

Capsule_User::State Capsule_User::state_____Alive( const UMLRTMessage * msg )
{
    switch( msg->destPort->role()->id )
    {
    case port_timer:
        switch( msg->getSignalId() )
        {
        case UMLRTTimerProtocol::signal_timeout:
            actionchain_____onTimeout1( msg );
            return choice_____tid2_( msg );
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

Capsule_User::State Capsule_User::state_____FAILED( const UMLRTMessage * msg )
{
    switch( msg->destPort->role()->id )
    {
    default:
        this->unexpectedMessage();
        break;
    }
    return currentState;
}

Capsule_User::State Capsule_User::state_____TestDone( const UMLRTMessage * msg )
{
    switch( msg->destPort->role()->id )
    {
    default:
        this->unexpectedMessage();
        break;
    }
    return currentState;
}

Capsule_User::State Capsule_User::state_____Waiting4DocPrint( const UMLRTMessage * msg )
{
    switch( msg->destPort->role()->id )
    {
    case port_timer:
        switch( msg->getSignalId() )
        {
        case UMLRTTimerProtocol::signal_timeout:
            actionchain_____onTimeout3( msg );
            return FAILED;
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

Capsule_User::State Capsule_User::state_____Waiting4DocSave( const UMLRTMessage * msg )
{
    switch( msg->destPort->role()->id )
    {
    case port_timer:
        switch( msg->getSignalId() )
        {
        case UMLRTTimerProtocol::signal_timeout:
            actionchain_____onTimeout2( msg );
            return choice_____TesterChoice( msg );
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
        Capsule_User::port_computerPort,
        "AppControl",
        "computerPort",
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
        Capsule_User::port_timer,
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

static void instantiate_User( const UMLRTRtsInterface * rts, UMLRTSlot * slot, const UMLRTCommsPort * * borderPorts )
{
    slot->capsule = new Capsule_User( &User, slot, borderPorts, NULL, false );
}

const UMLRTCapsuleClass User = 
{
    "User",
    NULL,
    instantiate_User,
    0,
    NULL,
    2,
    portroles_border,
    0,
    NULL
};

