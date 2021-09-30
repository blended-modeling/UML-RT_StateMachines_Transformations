
#include "PingPongProtocol.hh"

#include "DataType1.hh"
#include "umlrtoutsignal.hh"
#include "umlrtobjectclass.hh"
#include "basedebug.hh"
#include "basedebugtype.hh"

#include <string.h>
struct UMLRTCommsPort;

PingPongProtocol::Conj::Conj( const UMLRTCommsPort * & srcPort )
: UMLRTProtocol( srcPort )
{
}

struct pong1_params
{
    DataType1 dta[2];
};

static const UMLRTObject_field pong1_fields[]
= {
        { "dta", &RTType_DataType1, offsetof( pong1_params, dta ), 2, 0 },
};

static const UMLRTObject pong1_payload = { sizeof(pong1_params), 1, pong1_fields };

UMLRTOutSignal PingPongProtocol::Conj::pong1 ( const DataType1 (& dta)[2] ) const
{
    UMLRTOutSignal signal;
    signal.initialize( "signal_pong1", signal_pong1, srcPort, &pong1_payload, &dta);
    return signal;
}

struct pong2_params
{
    int command_param;
    char label_param;
    DataType1 datatype_param;
    long long timestamp_param;
    float measurement_param;
};

static const UMLRTObject_field pong2_fields[]
= {
    { "command",     &UMLRTType_int,      offsetof( pong2_params, command_param ),     1, 0 },
    { "label",       &UMLRTType_char,     offsetof( pong2_params, label_param ),       1, 0 },
    { "datatype",    &RTType_DataType1,   offsetof( pong2_params, datatype_param ),    1, 0 },
    { "timestamp",   &UMLRTType_longlong, offsetof( pong2_params, timestamp_param ),   1, 0 },
    { "measurement", &UMLRTType_float,    offsetof( pong2_params, measurement_param ), 1, 0 }
};

static const UMLRTObject pong2_object = { sizeof( pong2_params ), 5, pong2_fields };

UMLRTOutSignal PingPongProtocol::Conj::pong2( int command, char label, const DataType1 & datatype, long long timestamp, float measurement ) const
{
    UMLRTOutSignal signal;
    signal.initialize( "signal_pong2", signal_pong2, srcPort, &pong2_object, &command, &label, &datatype, &timestamp, &measurement);
    return signal;
}

PingPongProtocol::Base::Base( const UMLRTCommsPort * & srcPort )
: UMLRTProtocol( srcPort )
{
}

struct ping1_params
{
    DataType1 dta[2];
};

static const UMLRTObject_field ping1_fields[]
= {
 { "dta", &RTType_DataType1,  offsetof( ping1_params, dta ), 2, 0 },
};

static const UMLRTObject ping1_payload = { sizeof(ping1_params), 1, ping1_fields };

UMLRTOutSignal PingPongProtocol::Base::ping1 ( const DataType1 (& dta)[2] ) const
{
    UMLRTOutSignal signal;
    signal.initialize( "signal_ping1", signal_ping1, srcPort, &ping1_payload, &dta);
    return signal;
}

struct ping2_params
{
    int command_param;
    char label_param;
    DataType1 datatype_param;
    long long timestamp_param;
    float measurement_param;
};

static const UMLRTObject_field ping2_fields[]
= {
    { "command",     &UMLRTType_int,      offsetof( ping2_params, command_param ),     1, 0 },
    { "label",       &UMLRTType_char,     offsetof( ping2_params, label_param ),       1, 0 },
    { "datatype",    &RTType_DataType1,   offsetof( ping2_params, datatype_param ),    1, 0 },
    { "timestamp",   &UMLRTType_longlong, offsetof( ping2_params, timestamp_param ),   1, 0 },
    { "measurement", &UMLRTType_float,    offsetof( ping2_params, measurement_param ), 1, 0 }
};

static const UMLRTObject ping2_object = { sizeof( ping2_params ), 5, ping2_fields };

UMLRTOutSignal PingPongProtocol::Base::ping2( int command, char label, const DataType1 & datatype, long long timestamp, float measurement ) const
{
    UMLRTOutSignal signal;
    BDEBUG(BD_SIGNAL, "PingPongProtocol::Base::ping2 &cmd %p &lbl %p &dt %p &ts %p &msr %p\n",
            &command, &label, & datatype, &timestamp, &measurement);
    signal.initialize( "signal_ping2", signal_ping2, srcPort, &ping2_object, &command, &label, &datatype, &timestamp, &measurement);
    return signal;
}


