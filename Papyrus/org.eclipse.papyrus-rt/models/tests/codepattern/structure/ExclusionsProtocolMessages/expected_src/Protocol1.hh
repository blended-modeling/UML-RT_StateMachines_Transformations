
#ifndef PROTOCOL1_HH
#define PROTOCOL1_HH

#include "D.hh"
#include "umlrtinoutsignal.hh"
#include "umlrtinsignal.hh"
#include "umlrtoutsignal.hh"
#include "umlrtprotocol.hh"
#include "umlrtsignal.hh"
struct UMLRTCommsPort;

namespace Protocol1
{
    class Base : public UMLRTProtocol
    {
    public:
        Base( const UMLRTCommsPort * & srcPort );
        UMLRTInSignal m2() const;
        UMLRTInOutSignal m3() const;
        UMLRTOutSignal m1() const;
    };
    class Conj : public UMLRTProtocol
    {
    public:
        Conj( const UMLRTCommsPort * & srcPort );
        UMLRTOutSignal m2( char * x, const D & y ) const;
        UMLRTInOutSignal m3() const;
        UMLRTInSignal m1() const;
    };
    enum SignalId
    {
        signal_m2 = UMLRTSignal::FIRST_PROTOCOL_SIGNAL_ID,
        signal_m3,
        signal_m1
    };
};

#endif

