
#include "Pinger.hh"

#include "PingPongProtocol.hh"
#include "umlrtcapsuleclass.hh"
#include "umlrtcommsport.hh"
#include "umlrtcommsportfarend.hh"
#include "umlrtcommsportrole.hh"
#include "umlrtframeservice.hh"
#include "umlrtmessage.hh"
#include "umlrtslot.hh"
#include "umlrttimerprotocol.hh"
#include "umlrttimespec.hh"
#include <cstddef>
class UMLRTRtsInterface;
struct UMLRTCommsPort;

#include <iostream>
#include <stdio.h>

Capsule_Pinger::Capsule_Pinger( const UMLRTCapsuleClass * cd, UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat )
: UMLRTCapsule( NULL, cd, st, border, internal, isStat )
, PingPort( borderPorts[borderport_PingPort] )
, timerPort( internalPorts[internalport_timerPort] )
, currentState( SPECIAL_INTERNAL_STATE_UNVISITED )
, timeoutCount(0)
{
    stateNames[top__Running] = "top__Running";
}

void Capsule_Pinger::bindPort( bool isBorder, int portId, int index )
{
    if( isBorder )
        switch( portId )
        {
        case borderport_PingPort:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_PingPort, index, true );
            break;
        }
}

void Capsule_Pinger::unbindPort( bool isBorder, int portId, int index )
{
    if( isBorder )
        switch( portId )
        {
        case borderport_PingPort:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_PingPort, index, false );
            UMLRTFrameService::disconnectPort( borderPorts[borderport_PingPort], index );
            break;
        }
}

void Capsule_Pinger::inject( const UMLRTMessage & msg )
{
    switch( currentState )
    {
    case top__Running:
        currentState = state_____top__Running( msg );
        break;
    }
}

void Capsule_Pinger::initialize( const UMLRTMessage & msg )
{
    actionchain_____top__initialise__ActionChain3( msg );
    currentState = top__Running;
}

const char * Capsule_Pinger::getCurrentStateString() const
{
    return stateNames[currentState];
}

void Capsule_Pinger::entryaction_____top__initialise__ActionChain3__onEntry( const UMLRTMessage & msg )
{
    void * rtdata = *(void * *)msg.signal.getPayload();
}

void Capsule_Pinger::transitionaction_____top__initialise__ActionChain3__onInit( const UMLRTMessage & msg )
{
    // UMLRT-CODEGEN: ...
    printf("%s (%s) onInit: received signal %s\n", name(), getName(), msg.getSignalName());

    std::cout << "Pinger initialised" << std::endl;
    timerPort.informEvery( UMLRTTimespec( 2, 0 ) );
}

void Capsule_Pinger::transitionaction_____top__onPong__ActionChain5__onPong( const UMLRTMessage & msg )
{
    // UMLRT-CODEGEN: ...
    printf("%s (%s) onPong: received signal %s state %s\n", name(), getName(), msg.getSignalName(), getCurrentStateString());

#define rtdata      ((void*)msg.getParam(0))
#define dta         ((DataType1 *)msg.getParam(0))
#define command     ((int *)msg.getParam(0))
#define label       ((char *)msg.getParam(1))
#define datatype    ((DataType1 *)msg.getParam(2))
#define timestamp   ((long long *)msg.getParam(3))
#define measurement ((float *)msg.getParam(4))

    if (msg.getSignalId() == PingPongProtocol::signal_pong1)
    {
        std::cout << getName() << ":received pong1 payload size " << msg.signal.getPayloadSize() << std::endl;
        UMLRTObject_fprintf(stdout, &RTType_DataType1, dta, 0/*nest*/, 2/*arraySize*/);
        std::cout << std::endl;
    }
    else
    {
        std::cout << getName() << ":received pong2 payload size " << msg.signal.getPayloadSize() << std::endl;
        printf("%s (%s): received pong2 command %d label %d timestamp %lld measurement %f\n",
                name(), getName(), *command, *label, *timestamp, *measurement);
    }

#undef rtdata
#undef dta
#undef command
#undef label
#undef datatype
#undef timestamp
#undef measurement
}

void Capsule_Pinger::transitionaction_____top__onTimeout__ActionChain7__onTimeout( const UMLRTMessage & msg )
{
    // UMLRT-CODEGEN: ...
    printf("%s (%s) onTimeout: received signal %s state %s\n", name(), getName(), msg.getSignalName(), getCurrentStateString());

    UMLRTOutSignal s;
    DataType1 dt[2] = {
                { {timeoutCount, timeoutCount*10 + 1}, (timeoutCount & 1) != 0, timeoutCount*10 + 3.,
                        {{"start", 'a', timeoutCount*10 + 4}, {"middle", 'b', timeoutCount*10 + 5}, {"end", 'c', timeoutCount*10 + 6}}},
                { {timeoutCount*10 + 7, timeoutCount*10 + 8}, (timeoutCount & 2) != 0, timeoutCount*10 + 9.,
                    {{"start2", 'd', timeoutCount*10 + 1}, {"middle2", 'e', timeoutCount*10 + 2}, {"end2", 'f', timeoutCount*10 + 3 }}},
        };
    if (timeoutCount & 1)
    {
        std::cout << getName() << ":Sending ping1 from timeout transition action - timeoutCount " << timeoutCount << " " << std::endl;
        UMLRTObject_fprintf(stdout, &RTType_DataType1, &dt, 0/*nest*/, 2/*arraySize*/);
        std::cout << std::endl;
        s = PingPort.ping1( dt );
        std::cout << "Encoded parameter:" << std::endl;
        UMLRTObject_fprintf(stdout, &RTType_DataType1, s.getPayload(), 0/*nest*/, 2/*arraySize*/);
        std::cout << std::endl;
    }
    else
    {
        std::cout << getName() << ":Sending ping2 from timeout transition action - timeoutCount " << timeoutCount << " " << std::endl;
        s = PingPort.ping2( timeoutCount, (timeoutCount + 1), dt[0], (timeoutCount + 1) * 2, (timeoutCount + 1) * 3.0 );
    }
    s.send();
    ++timeoutCount;
    if (timeoutCount == 4)
    {
        timerPort.timeAdjustStart();
        UMLRTTimespec ts(0, 500000000);
        timerPort.timeAdjustComplete(ts);
    }
    if (timeoutCount == 8)
    {
        timerPort.timeAdjustStart();
        UMLRTTimespec ts(0, -500000000);
        timerPort.timeAdjustComplete(ts);
    }
}

void Capsule_Pinger::actionchain_____top__initialise__ActionChain3( const UMLRTMessage & msg )
{
    transitionaction_____top__initialise__ActionChain3__onInit( msg );
    entryaction_____top__initialise__ActionChain3__onEntry( msg );
}

void Capsule_Pinger::actionchain_____top__onPong__ActionChain5( const UMLRTMessage & msg )
{
    transitionaction_____top__onPong__ActionChain5__onPong( msg );
}

void Capsule_Pinger::actionchain_____top__onTimeout__ActionChain7( const UMLRTMessage & msg )
{
    transitionaction_____top__onTimeout__ActionChain7__onTimeout( msg );
}

Capsule_Pinger::State Capsule_Pinger::state_____top__Running( const UMLRTMessage & msg )
{
    printf("%s (%s) Running: inject signal %s state %s\n", name(), getName(), msg.getSignalName(), getCurrentStateString());
    switch( msg.destPort->role()->id )
    {
    case port_timerPort:
        switch( msg.getSignalId() )
        {
        case UMLRTTimerProtocol::signal_timeout:
            actionchain_____top__onTimeout__ActionChain7( msg );
            return top__Running;
        default:
            this->unexpectedMessage();
            break;
        }
        return currentState;
    case port_PingPort:
        switch(  msg.getSignalId() )
        {
        case PingPongProtocol::signal_pong1:
            actionchain_____top__onPong__ActionChain5( msg );
            return top__Running;
        case PingPongProtocol::signal_pong2:
            actionchain_____top__onPong__ActionChain5( msg );
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
        Capsule_Pinger::port_PingPort,
        "PingPongProtocol",
        "PingPort",
        NULL,
        1,
        true,
        false,
        false,
        false,
        false,
        false,
        true
    }
};

static const UMLRTCommsPortRole portroles_internal[] = 
{
    {
        Capsule_Pinger::port_timerPort,
        "UMLRTTimerProtocol",
        "timerPort",
        NULL,
        1,
        true,
        false,
        false,
        false,
        false,
        false,
        false
    }
};

static void instantiate_Pinger( const UMLRTRtsInterface * rts, UMLRTSlot * slot, const UMLRTCommsPort * * borderPorts )
{
    const UMLRTCommsPort * * internalPorts = UMLRTFrameService::createInternalPorts( slot, &Pinger );
    slot->capsule = new Capsule_Pinger( &Pinger, slot, borderPorts, internalPorts, false );
}

const UMLRTCapsuleClass Pinger = 
{
    "Pinger",
    NULL,
    instantiate_Pinger,
    0,
    NULL,
    1,
    portroles_border,
    1,
    portroles_internal
};

