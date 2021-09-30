
#include "Pinger.hh"

#include "PingPongProtocol.hh"
#include "umlrtcapsuleclass.hh"
#include "umlrtcommsport.hh"
#include "umlrtcommsportfarend.hh"
#include "umlrtcommsportrole.hh"
#include "umlrtframeservice.hh"
#include "umlrtinmessage.hh"
#include "umlrtslot.hh"
#include "umlrttimerprotocol.hh"
#include <cstddef>
class UMLRTRtsInterface;

#include <iostream>
#include <stdio.h>

Capsule_Pinger::Capsule_Pinger( UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * internal, bool isStat )
: UMLRTCapsule( NULL, &Pinger, st, border, internal, isStat ), timeoutCount(0)
{
}


PingPongProtocol_baserole Capsule_Pinger::PingPort() const
{
    return PingPongProtocol_baserole( borderPorts[borderport_PingPort] );
}

UMLRTTimerProtocol_baserole Capsule_Pinger::timerPort() const
{
    return UMLRTTimerProtocol_baserole( borderPorts[borderport_timerPort] );
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
    else
    {
    }
}

void Capsule_Pinger::unbindPort( bool isBorder, int portId, int index )
{
    if( isBorder )
        switch( portId )
        {
        case borderport_PingPort:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_PingPort, index, true );
            UMLRTFrameService::disconnectPort( borderPorts[borderport_PingPort], index );
            break;
        }
    else
    {
    }
}

void Capsule_Pinger::inject( const UMLRTInMessage & msg )
{
    switch( currentState )
    {
    case top__Running:
        currentState = state_____top__Running( msg );
        break;
    }
}

void Capsule_Pinger::initialize( const UMLRTInMessage & msg )
{
    actionchain_____top__initialise__ActionChain3( msg );
    currentState = top__Running;
}



void Capsule_Pinger::entryaction_____top__initialise__ActionChain3__onEntry( const UMLRTInMessage & msg )
{
    void * rtdata = *(void * *)msg.signal.getPayload();
}

void Capsule_Pinger::transitionaction_____top__initialise__ActionChain3__onInit( const UMLRTInMessage & msg )
{
    void * rtdata = *(void * *)msg.signal.getPayload();
    std::cout << "Pinger initialised" << std::endl;
    timerPort().informEvery( UMLRTTimespec( 2, 0 ) );
}

void Capsule_Pinger::transitionaction_____top__onPong__ActionChain5__onPong( const UMLRTInMessage & msg )
{
    std::cout << getName() << ":received pong payload size " << msg.signal.getPayloadSize() << std::endl;
    RTType_DataType1->fprintf(stdout, RTType_DataType1, msg.signal.getPayload(), 0/*nest*/, 2/*arraySize*/);
    std::cout << std::endl;

    DataType1 rtdata[2];
    msg.decode(&rtdata, 2);
    std::cout << getName() <<
            " rtdata[2] size " << sizeof(rtdata) <<
            " rtdata[0].field1_int[1] " << rtdata[0].field1_int[1] <<
            " rtdata[1].sst1.[2].name " << rtdata[1].field4_sst1[2].name <<
            " rtdata[1].sst1.[2].integer " << rtdata[1].field4_sst1[2].integer <<
            std::endl;
}

void Capsule_Pinger::transitionaction_____top__onTimeout__ActionChain7__onTimeout( const UMLRTInMessage & msg )
{
    void * rtdata = *(void * *)msg.signal.getPayload();
    DataType1 dt[2] = {
            { {timeoutCount*10, timeoutCount*10 + 1}, (timeoutCount & 1) != 0, timeoutCount*10 + 3.,
                    {{"start", 'a', timeoutCount*10 + 4}, {"middle", 'b', timeoutCount*10 + 5}, {"end", 'c', timeoutCount*10 + 6}}},
            { {timeoutCount*10 + 7, timeoutCount*10 + 8}, (timeoutCount & 2) != 0, timeoutCount*10 + 9.,
                {{"start2", 'd', timeoutCount*10 + 1}, {"middle2", 'e', timeoutCount*10 + 2}, {"end2", 'f', timeoutCount*10 + 3 }}},
    };
    std::cout << getName() << ":Sending ping from timeout transition action - timeoutCount " << timeoutCount << " " << std::endl;
    UMLRTOutSignal s = PingPort().ping( dt );
    std::cout << "Encoded parameter:" << std::endl;
    RTType_DataType1->fprintf(stdout, RTType_DataType1, s.getPayload(), 0/*nest*/, 2/*arraySize*/);
    s.send();
    ++timeoutCount;
}

void Capsule_Pinger::actionchain_____top__initialise__ActionChain3( const UMLRTInMessage & msg )
{
    transitionaction_____top__initialise__ActionChain3__onInit( msg );
    entryaction_____top__initialise__ActionChain3__onEntry( msg );
}

void Capsule_Pinger::actionchain_____top__onPong__ActionChain5( const UMLRTInMessage & msg )
{
    transitionaction_____top__onPong__ActionChain5__onPong( msg );
}

void Capsule_Pinger::actionchain_____top__onTimeout__ActionChain7( const UMLRTInMessage & msg )
{
    transitionaction_____top__onTimeout__ActionChain7__onTimeout( msg );
}

Capsule_Pinger::State Capsule_Pinger::state_____top__Running( const UMLRTInMessage & msg )
{
    switch( msg.destPort->role()->id )
    {
    case port_timerPort:
        switch( msg.getSignalId() )
        {
        case UMLRTTimerProtocol::signal_timeout:
            actionchain_____top__onTimeout__ActionChain7( msg );
            return top__Running;
        }
        return currentState;
    case port_PingPort:
        switch(  msg.getSignalId() )
        {
        case PingPongProtocol::signal_pong:
            msg.decodeInit(RTType_DataType1); // Assume this is the place where parameter types are known.
            actionchain_____top__onPong__ActionChain5( msg );
            return top__Running;
        }
        return currentState;
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
    },
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
    slot->capsule = new Capsule_Pinger( slot, borderPorts, NULL, false );
}
const UMLRTCapsuleClass Pinger = 
{
    "Pinger",
    NULL,
    instantiate_Pinger,
    0,
    NULL,
    2,
    portroles_border,
    0,
    NULL
};
