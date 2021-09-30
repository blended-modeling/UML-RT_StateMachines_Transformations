
#ifndef P_HH
#define P_HH

#include "umlrtinsignal.hh"
#include "umlrtoutsignal.hh"
#include "umlrtprotocol.hh"
#include "umlrtsignal.hh"
struct UMLRTCommsPort;

namespace P
{
    class Base : public UMLRTProtocol
    {
    public:
        Base( const UMLRTCommsPort * & srcPort );
        UMLRTInSignal e1() const;
        UMLRTInSignal e2() const;
    };
    class Conj : public UMLRTProtocol
    {
    public:
        Conj( const UMLRTCommsPort * & srcPort );
        UMLRTOutSignal e1() const;
        UMLRTOutSignal e2() const;
    };
    enum SignalId
    {
        signal_e1 = UMLRTSignal::FIRST_PROTOCOL_SIGNAL_ID,
        signal_e2
    };
};

#endif

