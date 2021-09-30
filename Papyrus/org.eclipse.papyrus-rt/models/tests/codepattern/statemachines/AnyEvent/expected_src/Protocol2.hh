
#ifndef PROTOCOL2_HH
#define PROTOCOL2_HH

#include "umlrtinsignal.hh"
#include "umlrtoutsignal.hh"
#include "umlrtprotocol.hh"
#include "umlrtsignal.hh"
struct UMLRTCommsPort;

namespace Protocol2
{
    class Base : public UMLRTProtocol
    {
    public:
        Base( const UMLRTCommsPort * & srcPort );
        UMLRTOutSignal m3() const;
        UMLRTOutSignal m4() const;
    };
    class Conj : public UMLRTProtocol
    {
    public:
        Conj( const UMLRTCommsPort * & srcPort );
        UMLRTInSignal m3() const;
        UMLRTInSignal m4() const;
    };
    enum SignalId
    {
        signal_m3 = UMLRTSignal::FIRST_PROTOCOL_SIGNAL_ID,
        signal_m4
    };
};

#endif

