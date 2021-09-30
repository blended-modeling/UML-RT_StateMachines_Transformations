
#ifndef SERVICE_HH
#define SERVICE_HH

#include "umlrtinsignal.hh"
#include "umlrtoutsignal.hh"
#include "umlrtprotocol.hh"
#include "umlrtsignal.hh"
struct UMLRTCommsPort;

namespace Service
{
    class Base : public UMLRTProtocol
    {
    public:
        Base( const UMLRTCommsPort * & srcPort );
        UMLRTInSignal request() const;
        UMLRTOutSignal response() const;
    };
    class Conj : public UMLRTProtocol
    {
    public:
        Conj( const UMLRTCommsPort * & srcPort );
        UMLRTOutSignal request() const;
        UMLRTInSignal response() const;
    };
    enum SignalId
    {
        signal_request = UMLRTSignal::FIRST_PROTOCOL_SIGNAL_ID,
        signal_response
    };
};

#endif

