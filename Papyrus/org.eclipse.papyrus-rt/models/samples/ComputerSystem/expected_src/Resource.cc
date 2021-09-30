
#include "Resource.hh"

#include "umlrtcapsuleid.hh"
#include "umlrtobjectclass.hh"
#include "umlrtoutsignal.hh"
struct UMLRTCommsPort;

static UMLRTObject_field fields_requestPrinterDriver[] = 
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

static UMLRTObject payload_requestPrinterDriver = 
{
    0,
    #ifdef NEED_NON_FLEXIBLE_ARRAY
    1
    #else
    0
    #endif
    ,
    fields_requestPrinterDriver
};

static UMLRTObject_field fields_requestStorageDriver[] = 
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

static UMLRTObject payload_requestStorageDriver = 
{
    0,
    #ifdef NEED_NON_FLEXIBLE_ARRAY
    1
    #else
    0
    #endif
    ,
    fields_requestStorageDriver
};

static UMLRTObject_field fields_resMgrRunning[] = 
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

static UMLRTObject payload_resMgrRunning = 
{
    0,
    #ifdef NEED_NON_FLEXIBLE_ARRAY
    1
    #else
    0
    #endif
    ,
    fields_resMgrRunning
};

static UMLRTObject_field fields_resNotAvail[] = 
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

static UMLRTObject payload_resNotAvail = 
{
    0,
    #ifdef NEED_NON_FLEXIBLE_ARRAY
    1
    #else
    0
    #endif
    ,
    fields_resNotAvail
};

static UMLRTObject_field fields_resourceID[] = 
{
    {
        "resourceID",
        &UMLRTType_UMLRTCapsuleId,
        0,
        1,
        0
    }
};

static UMLRTObject payload_resourceID = 
{
    sizeof( UMLRTCapsuleId ),
    1,
    fields_resourceID
};

Resource::Base::Base( const UMLRTCommsPort * & srcPort )
: UMLRTProtocol( srcPort )
{
}

UMLRTOutSignal Resource::Base::resMgrRunning() const
{
    UMLRTOutSignal signal;
    signal.initialize( "resMgrRunning", signal_resMgrRunning, srcPort, &payload_resMgrRunning );
    return signal;
}

UMLRTOutSignal Resource::Base::resNotAvail() const
{
    UMLRTOutSignal signal;
    signal.initialize( "resNotAvail", signal_resNotAvail, srcPort, &payload_resNotAvail );
    return signal;
}

UMLRTOutSignal Resource::Base::resourceID( const UMLRTCapsuleId & resourceID ) const
{
    UMLRTOutSignal signal;
    signal.initialize( "resourceID", signal_resourceID, srcPort, &payload_resourceID, &resourceID );
    return signal;
}

Resource::Conj::Conj( const UMLRTCommsPort * & srcPort )
: UMLRTProtocol( srcPort )
{
}

UMLRTOutSignal Resource::Conj::requestPrinterDriver() const
{
    UMLRTOutSignal signal;
    signal.initialize( "requestPrinterDriver", signal_requestPrinterDriver, srcPort, &payload_requestPrinterDriver );
    return signal;
}

UMLRTOutSignal Resource::Conj::requestStorageDriver() const
{
    UMLRTOutSignal signal;
    signal.initialize( "requestStorageDriver", signal_requestStorageDriver, srcPort, &payload_requestStorageDriver );
    return signal;
}


