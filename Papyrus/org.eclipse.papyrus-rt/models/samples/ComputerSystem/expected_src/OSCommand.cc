
#include "OSCommand.hh"

#include "AppType.hh"
#include "umlrtobjectclass.hh"
#include "umlrtoutsignal.hh"
struct UMLRTCommsPort;

static UMLRTObject_field fields_LaunchApp[] = 
{
    {
        "appType",
        &UMLRTType_int,
        0,
        1,
        0
    }
};

static UMLRTObject payload_LaunchApp = 
{
    sizeof( AppType ),
    1,
    fields_LaunchApp
};

static UMLRTObject_field fields_AppLaunchFail[] = 
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

static UMLRTObject payload_AppLaunchFail = 
{
    0,
    #ifdef NEED_NON_FLEXIBLE_ARRAY
    1
    #else
    0
    #endif
    ,
    fields_AppLaunchFail
};

static UMLRTObject_field fields_AppLaunched[] = 
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

static UMLRTObject payload_AppLaunched = 
{
    0,
    #ifdef NEED_NON_FLEXIBLE_ARRAY
    1
    #else
    0
    #endif
    ,
    fields_AppLaunched
};

OSCommand::Base::Base( const UMLRTCommsPort * & srcPort )
: UMLRTProtocol( srcPort )
{
}

UMLRTOutSignal OSCommand::Base::AppLaunchFail() const
{
    UMLRTOutSignal signal;
    signal.initialize( "AppLaunchFail", signal_AppLaunchFail, srcPort, &payload_AppLaunchFail );
    return signal;
}

UMLRTOutSignal OSCommand::Base::AppLaunched() const
{
    UMLRTOutSignal signal;
    signal.initialize( "AppLaunched", signal_AppLaunched, srcPort, &payload_AppLaunched );
    return signal;
}

OSCommand::Conj::Conj( const UMLRTCommsPort * & srcPort )
: UMLRTProtocol( srcPort )
{
}

UMLRTOutSignal OSCommand::Conj::LaunchApp( const AppType & appType ) const
{
    UMLRTOutSignal signal;
    signal.initialize( "LaunchApp", signal_LaunchApp, srcPort, &payload_LaunchApp, &appType );
    return signal;
}


