
#ifndef PROTOCOL2_HH
#define PROTOCOL2_HH

#include "umlrtinsignal.hh"
#include "umlrtoutsignal.hh"
#include "umlrtprotocol.hh"
#include "umlrtsignal.hh"
struct UMLRTCommsPort;

namespace Protocol2
{
    class Conj : public UMLRTProtocol
    {
    public:
        Conj( const UMLRTCommsPort * & srcPort );
        UMLRTInSignal om1() const;
        UMLRTInSignal om2() const;
        UMLRTInSignal om3() const;
    };
    enum SignalId
    {
        signal_om1 = UMLRTSignal::FIRST_PROTOCOL_SIGNAL_ID,
        signal_om2,
        signal_om3
    };
    class Base : public UMLRTProtocol
    {
    public:
        Base( const UMLRTCommsPort * & srcPort );
        UMLRTOutSignal om1() const;
        UMLRTOutSignal om2() const;
        UMLRTOutSignal om3() const;
    };
};

#endif

