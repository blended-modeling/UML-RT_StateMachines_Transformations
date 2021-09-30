
#include "USBProtocol.hh"

#include "USBDeviceClasses.hh"
#include "USBErrorCodes.hh"
#include "umlrtobjectclass.hh"
#include "umlrtoutsignal.hh"
struct UMLRTCommsPort;

static UMLRTObject_field fields_Eject[] = 
{
    #ifdef NEED_NON_FLEXIBLE_ARRAY
    {
        0,
        0,
        0,
        0,
        0
    }
    #endif
};

static UMLRTObject payload_Eject = 
{
    0,
    #ifdef NEED_NON_FLEXIBLE_ARRAY
    1
    #else
    0
    #endif
    ,
    fields_Eject
};

static UMLRTObject_field fields_ack[] = 
{
    #ifdef NEED_NON_FLEXIBLE_ARRAY
    {
        0,
        0,
        0,
        0,
        0
    }
    #endif
};

static UMLRTObject payload_ack = 
{
    0,
    #ifdef NEED_NON_FLEXIBLE_ARRAY
    1
    #else
    0
    #endif
    ,
    fields_ack
};

static UMLRTObject_field fields_connect[] = 
{
    #ifdef NEED_NON_FLEXIBLE_ARRAY
    {
        0,
        0,
        0,
        0,
        0
    }
    #endif
};

static UMLRTObject payload_connect = 
{
    0,
    #ifdef NEED_NON_FLEXIBLE_ARRAY
    1
    #else
    0
    #endif
    ,
    fields_connect
};

static UMLRTObject_field fields_data[] = 
{
    {
        "data",
        &UMLRTType_ptr,
        0,
        1,
        0
    }
};

static UMLRTObject payload_data = 
{
    sizeof( void * ),
    1,
    fields_data
};

static UMLRTObject_field fields_deviceClass[] = 
{
    {
        "deviceClassID",
        &UMLRTType_int,
        0,
        1,
        0
    }
};

static UMLRTObject payload_deviceClass = 
{
    sizeof( USBDeviceClasses ),
    1,
    fields_deviceClass
};

static UMLRTObject_field fields_eod[] = 
{
    #ifdef NEED_NON_FLEXIBLE_ARRAY
    {
        0,
        0,
        0,
        0,
        0
    }
    #endif
};

static UMLRTObject payload_eod = 
{
    0,
    #ifdef NEED_NON_FLEXIBLE_ARRAY
    1
    #else
    0
    #endif
    ,
    fields_eod
};

static UMLRTObject_field fields_error[] = 
{
    {
        "errorCode",
        &UMLRTType_int,
        0,
        1,
        0
    }
};

static UMLRTObject payload_error = 
{
    sizeof( USBErrorCodes ),
    1,
    fields_error
};

static UMLRTObject_field fields_getDeviceClass[] = 
{
    #ifdef NEED_NON_FLEXIBLE_ARRAY
    {
        0,
        0,
        0,
        0,
        0
    }
    #endif
};

static UMLRTObject payload_getDeviceClass = 
{
    0,
    #ifdef NEED_NON_FLEXIBLE_ARRAY
    1
    #else
    0
    #endif
    ,
    fields_getDeviceClass
};

static UMLRTObject_field fields_resend[] = 
{
    #ifdef NEED_NON_FLEXIBLE_ARRAY
    {
        0,
        0,
        0,
        0,
        0
    }
    #endif
};

static UMLRTObject payload_resend = 
{
    0,
    #ifdef NEED_NON_FLEXIBLE_ARRAY
    1
    #else
    0
    #endif
    ,
    fields_resend
};

static UMLRTObject_field fields_setDeviceBusID[] = 
{
    {
        "busId",
        &UMLRTType_int,
        0,
        1,
        0
    }
};

static UMLRTObject payload_setDeviceBusID = 
{
    sizeof( int ),
    1,
    fields_setDeviceBusID
};

static UMLRTObject_field fields_status[] = 
{
    {
        "percent",
        &UMLRTType_int,
        0,
        1,
        0
    }
};

static UMLRTObject payload_status = 
{
    sizeof( int ),
    1,
    fields_status
};

USBProtocol::Conj::Conj( const UMLRTCommsPort * & srcPort )
: UMLRTProtocol( srcPort )
{
}

UMLRTOutSignal USBProtocol::Conj::Eject() const
{
    UMLRTOutSignal signal;
    signal.initialize( "Eject", signal_Eject, srcPort, &payload_Eject );
    return signal;
}

UMLRTOutSignal USBProtocol::Conj::ack() const
{
    UMLRTOutSignal signal;
    signal.initialize( "ack", signal_ack, srcPort, &payload_ack );
    return signal;
}

UMLRTOutSignal USBProtocol::Conj::connect() const
{
    UMLRTOutSignal signal;
    signal.initialize( "connect", signal_connect, srcPort, &payload_connect );
    return signal;
}

UMLRTOutSignal USBProtocol::Conj::data( void * data ) const
{
    UMLRTOutSignal signal;
    signal.initialize( "data", signal_data, srcPort, &payload_data, &data );
    return signal;
}

UMLRTOutSignal USBProtocol::Conj::deviceClass( const USBDeviceClasses & deviceClassID ) const
{
    UMLRTOutSignal signal;
    signal.initialize( "deviceClass", signal_deviceClass, srcPort, &payload_deviceClass, &deviceClassID );
    return signal;
}

UMLRTOutSignal USBProtocol::Conj::eod() const
{
    UMLRTOutSignal signal;
    signal.initialize( "eod", signal_eod, srcPort, &payload_eod );
    return signal;
}

UMLRTOutSignal USBProtocol::Conj::error( const USBErrorCodes & errorCode ) const
{
    UMLRTOutSignal signal;
    signal.initialize( "error", signal_error, srcPort, &payload_error, &errorCode );
    return signal;
}

UMLRTOutSignal USBProtocol::Conj::getDeviceClass() const
{
    UMLRTOutSignal signal;
    signal.initialize( "getDeviceClass", signal_getDeviceClass, srcPort, &payload_getDeviceClass );
    return signal;
}

UMLRTOutSignal USBProtocol::Conj::resend() const
{
    UMLRTOutSignal signal;
    signal.initialize( "resend", signal_resend, srcPort, &payload_resend );
    return signal;
}

UMLRTOutSignal USBProtocol::Conj::setDeviceBusID( int busId ) const
{
    UMLRTOutSignal signal;
    signal.initialize( "setDeviceBusID", signal_setDeviceBusID, srcPort, &payload_setDeviceBusID, &busId );
    return signal;
}

UMLRTOutSignal USBProtocol::Conj::status( int percent ) const
{
    UMLRTOutSignal signal;
    signal.initialize( "status", signal_status, srcPort, &payload_status, &percent );
    return signal;
}

USBProtocol::Base::Base( const UMLRTCommsPort * & srcPort )
: UMLRTProtocol( srcPort )
{
}

UMLRTOutSignal USBProtocol::Base::Eject() const
{
    UMLRTOutSignal signal;
    signal.initialize( "Eject", signal_Eject, srcPort, &payload_Eject );
    return signal;
}

UMLRTOutSignal USBProtocol::Base::ack() const
{
    UMLRTOutSignal signal;
    signal.initialize( "ack", signal_ack, srcPort, &payload_ack );
    return signal;
}

UMLRTOutSignal USBProtocol::Base::connect() const
{
    UMLRTOutSignal signal;
    signal.initialize( "connect", signal_connect, srcPort, &payload_connect );
    return signal;
}

UMLRTOutSignal USBProtocol::Base::data( void * data ) const
{
    UMLRTOutSignal signal;
    signal.initialize( "data", signal_data, srcPort, &payload_data, &data );
    return signal;
}

UMLRTOutSignal USBProtocol::Base::deviceClass( const USBDeviceClasses & deviceClassID ) const
{
    UMLRTOutSignal signal;
    signal.initialize( "deviceClass", signal_deviceClass, srcPort, &payload_deviceClass, &deviceClassID );
    return signal;
}

UMLRTOutSignal USBProtocol::Base::eod() const
{
    UMLRTOutSignal signal;
    signal.initialize( "eod", signal_eod, srcPort, &payload_eod );
    return signal;
}

UMLRTOutSignal USBProtocol::Base::error( const USBErrorCodes & errorCode ) const
{
    UMLRTOutSignal signal;
    signal.initialize( "error", signal_error, srcPort, &payload_error, &errorCode );
    return signal;
}

UMLRTOutSignal USBProtocol::Base::getDeviceClass() const
{
    UMLRTOutSignal signal;
    signal.initialize( "getDeviceClass", signal_getDeviceClass, srcPort, &payload_getDeviceClass );
    return signal;
}

UMLRTOutSignal USBProtocol::Base::resend() const
{
    UMLRTOutSignal signal;
    signal.initialize( "resend", signal_resend, srcPort, &payload_resend );
    return signal;
}

UMLRTOutSignal USBProtocol::Base::setDeviceBusID( int busId ) const
{
    UMLRTOutSignal signal;
    signal.initialize( "setDeviceBusID", signal_setDeviceBusID, srcPort, &payload_setDeviceBusID, &busId );
    return signal;
}

UMLRTOutSignal USBProtocol::Base::status( int percent ) const
{
    UMLRTOutSignal signal;
    signal.initialize( "status", signal_status, srcPort, &payload_status, &percent );
    return signal;
}


