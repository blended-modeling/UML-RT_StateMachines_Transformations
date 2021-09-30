
#ifndef RESOURCE_HH
#define RESOURCE_HH

#include "umlrtcapsuleid.hh"
#include "umlrtoutsignal.hh"
#include "umlrtprotocol.hh"
#include "umlrtsignal.hh"
struct UMLRTCommsPort;

namespace Resource
{
    class Base : public UMLRTProtocol
    {
    public:
        Base( const UMLRTCommsPort * & srcPort );
        UMLRTOutSignal resMgrRunning() const;
        UMLRTOutSignal resNotAvail() const;
        UMLRTOutSignal resourceID( const UMLRTCapsuleId & resourceID ) const;
    };
    class Conj : public UMLRTProtocol
    {
    public:
        Conj( const UMLRTCommsPort * & srcPort );
        UMLRTOutSignal requestPrinterDriver() const;
        UMLRTOutSignal requestStorageDriver() const;
    };
    enum SignalId
    {
        signal_requestPrinterDriver = UMLRTSignal::FIRST_PROTOCOL_SIGNAL_ID,
        signal_requestStorageDriver,
        signal_resMgrRunning,
        signal_resNotAvail,
        signal_resourceID
    };
};

#endif

