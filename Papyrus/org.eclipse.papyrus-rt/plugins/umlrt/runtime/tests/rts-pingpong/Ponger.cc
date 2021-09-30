
#include "Ponger.hh"

#include "DataType1.hh"
#include "PingPongProtocol.hh"
#include "umlrtcapsuleclass.hh"
#include "umlrtcommsport.hh"
#include "umlrtcommsportfarend.hh"
#include "umlrtcommsportrole.hh"
#include "umlrtframeservice.hh"
#include "umlrtmessage.hh"
#include "umlrtmain.hh"
#include "umlrtsignal.hh"
#include "umlrtslot.hh"
#include <cstddef>
class UMLRTRtsInterface;
struct UMLRTCommsPort;

#include <iostream>
#include <stdio.h>

Capsule_Ponger::Capsule_Ponger( const UMLRTCapsuleClass * cd, UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat )
: UMLRTCapsule( NULL, cd, st, border, internal, isStat )
, PongPort( borderPorts[borderport_PongPort] )
, currentState( SPECIAL_INTERNAL_STATE_UNVISITED )
, messageLimit(0)
{
    stateNames[top__Running] = "top__Running";
}

void Capsule_Ponger::bindPort( bool isBorder, int portId, int index )
{
    if( isBorder )
        switch( portId )
        {
        case borderport_PongPort:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_PongPort, index, true );
            break;
        }
}

void Capsule_Ponger::unbindPort( bool isBorder, int portId, int index )
{
    if( isBorder )
        switch( portId )
        {
        case borderport_PongPort:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_PongPort, index, false );
            UMLRTFrameService::disconnectPort( borderPorts[borderport_PongPort], index );
            break;
        }
}

void Capsule_Ponger::inject( const UMLRTMessage & message )
{
    msg = &message;
    switch( currentState )
    {
    case top__Running:
        currentState = state_____top__Running( message );
        break;
    default:
        break;
    }
}

void Capsule_Ponger::initialize( const UMLRTMessage & message )
{
    msg = &message;
    actionchain_____top__initial__ActionChain3( message );
    currentState = top__Running;
}

const char * Capsule_Ponger::getCurrentStateString() const
{
    return stateNames[currentState];
}

void Capsule_Ponger::transitionaction_____top__initial__ActionChain3__onInit( const UMLRTMessage & msg )
{
    printf("%s (%s) onInit: received signal %s\n", name(), getName(), msg.getSignalName());

    // UMLRT-CODEGEN:platform:/resource/PingPong/PingPong.uml#__Obm0FYKEeSmi4Fyw18d0w
    int limit = 0;
    if( UMLRTMain::getArgCount() > 0 )
    {
        std::cout << "parsing arg '" << UMLRTMain::getArg( 0 ) << '\'' << std::endl;
        limit = atoi( UMLRTMain::getArg( 0 ) );
    }
    if( limit <= 0 )
        limit = 15;

    messageLimit = limit;

    std::cout << getName() << ": initialized with message limit " << messageLimit << std::endl;
}

void Capsule_Ponger::transitionaction_____top__onPing__ActionChain5__onPing( const UMLRTMessage & msg )
{
    printf("%s (%s) onPing: received signal %s state %s\n", name(), getName(), msg.getSignalName(), getCurrentStateString());

#define rtdata      ((void*)msg.getParam(0))
#define dta         ((DataType1 *)msg.getParam(0))
#define command     ((int *)msg.getParam(0))
#define label       ((char *)msg.getParam(1))
#define datatype    ((DataType1 *)msg.getParam(2))
#define timestamp   ((long long *)msg.getParam(3))
#define measurement ((float *)msg.getParam(4))


    if (msg.getSignalId() == PingPongProtocol::signal_ping1)
    {
        std::cout << getName() << ":received ping1 payload size " << msg.signal.getPayloadSize() << std::endl;
        UMLRTObject_fprintf(stdout, &RTType_DataType1, msg.signal.getPayload(), 0/*nest*/, 2/*arraySize*/);
        std::cout << std::endl;
        dta[0].field1_int[0]++;
        dta[0].field1_int[1]++;
        dta[1].field1_int[0]++;
        dta[0].field3_double += 1.;
        dta[1].field3_double += 1.;
        dta[0].field4_sst1[0].integer += 1;
        dta[0].field4_sst1[1].integer += 1;
        dta[0].field4_sst1[2].integer += 1;
        dta[1].field4_sst1[0].integer += 1;
        dta[1].field4_sst1[1].integer += 1;
        dta[1].field4_sst1[2].integer += 1;
        std::cout << "Sending pong1" << std::endl;
        PongPort.pong1((DataType1(&)[2])*dta).send();
        if (dta[0].field1_int[0] >= messageLimit)
        {
            // Abort all controllers after we've exceeded the limit.
#if (((__GNUC__ * 100) + __GNUC_MINOR__) >= 406)
#pragma GCC diagnostic push
#pragma GCC diagnostic ignored "-Wint-to-pointer-cast"
#endif
            void * exitValue = (void*)messageLimit;
#if (((__GNUC__ * 100) + __GNUC_MINOR__) >= 406)
#pragma GCC diagnostic pop
#endif
            context()->exit(exitValue);
            PongPort.pong1((DataType1(&)[2])*dta).send();
            PongPort.pong1((DataType1(&)[2])*dta).send();
            PongPort.pong1((DataType1(&)[2])*dta).send();
        }

    }
    else
    {
        std::cout << getName() << ":received ping2 payload size " << msg.signal.getPayloadSize() << std::endl;
        printf("%s (%s): received ping2 command %d label %d timestamp %lld measurement %f\n",
                name(), getName(), *command, *label, *timestamp, *measurement);
        PongPort.pong2(*command+1, *label+1, *datatype, *timestamp+1, *measurement+1.0).send();
    }

#undef rtdata
#undef dta
#undef command
#undef label
#undef datatype
#undef timestamp
#undef measurement
}

void Capsule_Ponger::actionchain_____top__initial__ActionChain3( const UMLRTMessage & msg )
{
    transitionaction_____top__initial__ActionChain3__onInit( msg );
}

void Capsule_Ponger::actionchain_____top__onPing__ActionChain5( const UMLRTMessage & msg )
{
    transitionaction_____top__onPing__ActionChain5__onPing( msg );
}

Capsule_Ponger::State Capsule_Ponger::state_____top__Running( const UMLRTMessage & msg )
{
    printf("%s (%s) Running: inject signal %s state %s\n", name(), getName(), msg.getSignalName(), getCurrentStateString());
    switch( msg.destPort->role()->id )
    {
    case port_PongPort:
        switch( msg.signal.getId() )
        {
        case PingPongProtocol::signal_ping1:
            actionchain_____top__onPing__ActionChain5( msg );
            return top__Running;
        case PingPongProtocol::signal_ping2:
            actionchain_____top__onPing__ActionChain5( msg );
            return top__Running;
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
        Capsule_Ponger::port_PongPort,
        "PingPongProtocol",
        "PongPort",
        NULL,
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

static void instantiate_Ponger( const UMLRTRtsInterface * rts, UMLRTSlot * slot, const UMLRTCommsPort * * borderPorts )
{
    slot->capsule = new Capsule_Ponger( &Ponger, slot, borderPorts, NULL, false );
}

const UMLRTCapsuleClass Ponger = 
{
    "Ponger",
    NULL,
    instantiate_Ponger,
    0,
    NULL,
    1,
    portroles_border,
    0,
    NULL
};

