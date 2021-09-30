
#ifndef OSCOMMAND_HH
#define OSCOMMAND_HH

#include "AppType.hh"
#include "umlrtoutsignal.hh"
#include "umlrtprotocol.hh"
#include "umlrtsignal.hh"
struct UMLRTCommsPort;

namespace OSCommand
{
    class Base : public UMLRTProtocol
    {
    public:
        Base( const UMLRTCommsPort * & srcPort );
        UMLRTOutSignal AppLaunchFail() const;
        UMLRTOutSignal AppLaunched() const;
    };
    class Conj : public UMLRTProtocol
    {
    public:
        Conj( const UMLRTCommsPort * & srcPort );
        UMLRTOutSignal LaunchApp( const AppType & appType ) const;
    };
    enum SignalId
    {
        signal_LaunchApp = UMLRTSignal::FIRST_PROTOCOL_SIGNAL_ID,
        signal_AppLaunchFail,
        signal_AppLaunched
    };
};

#endif

