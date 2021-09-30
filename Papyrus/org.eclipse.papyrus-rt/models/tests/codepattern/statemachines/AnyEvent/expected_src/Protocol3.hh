
#ifndef PROTOCOL3_HH
#define PROTOCOL3_HH

#include "umlrtinsignal.hh"
#include "umlrtoutsignal.hh"
#include "umlrtprotocol.hh"
#include "umlrtsignal.hh"
struct UMLRTCommsPort;

namespace Protocol3
{
    class Conj : public UMLRTProtocol
    {
    public:
        Conj( const UMLRTCommsPort * & srcPort );
        UMLRTOutSignal m5() const;
        UMLRTOutSignal m6() const;
    };
    class Base : public UMLRTProtocol
    {
    public:
        Base( const UMLRTCommsPort * & srcPort );
        UMLRTInSignal m5() const;
        UMLRTInSignal m6() const;
    };
    enum SignalId
    {
        signal_m5 = UMLRTSignal::FIRST_PROTOCOL_SIGNAL_ID,
        signal_m6
    };
};

#endif

