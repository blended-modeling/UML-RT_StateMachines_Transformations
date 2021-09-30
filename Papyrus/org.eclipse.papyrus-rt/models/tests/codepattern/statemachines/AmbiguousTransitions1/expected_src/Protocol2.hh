
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
        UMLRTInSignal m1() const;
    };
    enum SignalId
    {
        signal_m1 = UMLRTSignal::FIRST_PROTOCOL_SIGNAL_ID
    };
    class Conj : public UMLRTProtocol
    {
    public:
        Conj( const UMLRTCommsPort * & srcPort );
        UMLRTOutSignal m1() const;
    };
};

#endif

