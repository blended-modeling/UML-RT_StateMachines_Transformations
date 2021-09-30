
#ifndef CONTROL_HH
#define CONTROL_HH

#include "umlrtinsignal.hh"
#include "umlrtoutsignal.hh"
#include "umlrtprotocol.hh"
#include "umlrtsignal.hh"
struct UMLRTCommsPort;

namespace Control
{
    class Conj : public UMLRTProtocol
    {
    public:
        Conj( const UMLRTCommsPort * & srcPort );
        UMLRTInSignal begin() const;
    };
    enum SignalId
    {
        signal_begin = UMLRTSignal::FIRST_PROTOCOL_SIGNAL_ID
    };
    class Base : public UMLRTProtocol
    {
    public:
        Base( const UMLRTCommsPort * & srcPort );
        UMLRTOutSignal begin() const;
    };
};

#endif

