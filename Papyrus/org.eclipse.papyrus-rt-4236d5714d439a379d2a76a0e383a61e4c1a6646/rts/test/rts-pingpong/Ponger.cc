
#include "Ponger.hh"

#include "DataType1.hh"
#include "PingPongProtocol.hh"
#include "umlrtcapsuleclass.hh"
#include "umlrtcommsport.hh"
#include "umlrtcommsportfarend.hh"
#include "umlrtcommsportrole.hh"
#include "umlrtframeservice.hh"
#include "umlrtinmessage.hh"
#include "umlrtsignal.hh"
#include "umlrtslot.hh"
#include <cstddef>
class UMLRTRtsInterface;

#include <iostream>
#include <stdio.h>

Capsule_Ponger::Capsule_Ponger( UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * internal, bool isStat )
: UMLRTCapsule( NULL, &Ponger, st, border, internal, isStat )
{
}


PingPongProtocol_conjrole Capsule_Ponger::PongPort() const
{
    return PingPongProtocol_conjrole( borderPorts[borderport_PongPort] );
}



void Capsule_Ponger::bindPort( bool isBorder, int portId, int index )
{
    if( isBorder )
        switch( portId )
        {
        case borderport_PongPort:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_PongPort, index, true );
            break;
        case borderport__bind:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport__bind, index, true );
            break;
        }
    else
    {
    }
}

void Capsule_Ponger::unbindPort( bool isBorder, int portId, int index )
{
    if( isBorder )
        switch( portId )
        {
        case borderport_PongPort:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_PongPort, index, true );
            UMLRTFrameService::disconnectPort( borderPorts[borderport_PongPort], index );
            break;
        case borderport__bind:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport__bind, index, true );
            UMLRTFrameService::disconnectPort( borderPorts[borderport__bind], index );
            break;
        }
    else
    {
    }
}

void Capsule_Ponger::inject( const UMLRTInMessage & msg )
{
    switch( currentState )
    {
    case top__Running:
        currentState = state_____top__Running( msg );
        break;
    }
}

void Capsule_Ponger::initialize( const UMLRTInMessage & msg )
{
    actionchain_____top__initial__ActionChain3( msg );
    currentState = top__Running;
}



void Capsule_Ponger::transitionaction_____top__initial__ActionChain3__onInit( const UMLRTInMessage & msg )
{
    void * rtdata = *(void * *)msg.signal.getPayload();
    std::cout << "Ponger initialised" << std::endl;
}

void Capsule_Ponger::transitionaction_____top__onBound__ActionChain6__onBound( const UMLRTInMessage & msg )
{
    void * rtdata = *(void * *)msg.signal.getPayload();
    printf( "%s onBound\n", getName() );
}

void Capsule_Ponger::transitionaction_____top__onPing__ActionChain5__onPing( const UMLRTInMessage & msg )
{
    std::cout << getName() << ":received ping" << std::endl;
    RTType_DataType1->fprintf(stdout, RTType_DataType1, msg.signal.getPayload(), 0/*nest*/, 2/*arraySize*/);

    DataType1 rtdata[2];
    msg.decode(&rtdata, 2);
    std::cout << getName() <<
            " rtdata[0].field1_int[1] " << rtdata[0].field1_int[1] <<
            " rtdata[1].field1_int[1] " << rtdata[1].field1_int[1] <<
            " rtdata[0].field3_double " << rtdata[0].field3_double <<
            " rtdata[1].field3_double " << rtdata[1].field3_double <<
            " rtdata[1].sst1.[2].name " << rtdata[1].field4_sst1[2].name <<
            " rtdata[1].sst1.[2].integer " << rtdata[1].field4_sst1[2].integer <<
            std::endl;
    rtdata[0].field1_int[0]++;
    rtdata[0].field1_int[1]++;
    rtdata[1].field1_int[0]++;
    rtdata[2].field1_int[1]++;
    rtdata[0].field3_double += 1.;
    rtdata[1].field3_double += 1.;
    rtdata[0].field4_sst1[0].integer += 1;
    rtdata[0].field4_sst1[1].integer += 1;
    rtdata[0].field4_sst1[2].integer += 1;
    rtdata[1].field4_sst1[0].integer += 1;
    rtdata[1].field4_sst1[1].integer += 1;
    rtdata[1].field4_sst1[2].integer += 1;
    std::cout << "Sending pong" << std::endl;
    PongPort().pong(rtdata).send();
}

void Capsule_Ponger::actionchain_____top__initial__ActionChain3( const UMLRTInMessage & msg )
{
    transitionaction_____top__initial__ActionChain3__onInit( msg );
}

void Capsule_Ponger::actionchain_____top__onBound__ActionChain6( const UMLRTInMessage & msg )
{
    transitionaction_____top__onBound__ActionChain6__onBound( msg );
}

void Capsule_Ponger::actionchain_____top__onPing__ActionChain5( const UMLRTInMessage & msg )
{
    transitionaction_____top__onPing__ActionChain5__onPing( msg );
}

Capsule_Ponger::State Capsule_Ponger::state_____top__Running( const UMLRTInMessage & msg )
{
    switch( msg.destPort->role()->id )
    {
    case port_PongPort:
        switch( msg.signal.getId() )
        {
        case UMLRTSignal::rtBound:
            actionchain_____top__onBound__ActionChain6( msg );
            return top__Running;
        case PingPongProtocol::signal_ping:
            msg.decodeInit(RTType_DataType1); // Assume this is the place where parameter types are known.
            actionchain_____top__onPing__ActionChain5( msg );
            return top__Running;
        }
        return currentState;
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
        true,
        false,
        false,
        true
    },
    {
        Capsule_Ponger::port__bind,
        "UMLRTBaseCommProtocol",
        "_bind",
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
static void instantiate_Ponger( const UMLRTRtsInterface * rts, UMLRTSlot * slot, const UMLRTCommsPort * * borderPorts )
{
    slot->capsule = new Capsule_Ponger( slot, borderPorts, NULL, false );
}
const UMLRTCapsuleClass Ponger = 
{
    "Ponger",
    NULL,
    instantiate_Ponger,
    0,
    NULL,
    2,
    portroles_border,
    0,
    NULL
};
