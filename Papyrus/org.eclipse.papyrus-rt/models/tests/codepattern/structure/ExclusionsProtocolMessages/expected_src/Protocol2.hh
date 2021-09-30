
#ifndef PROTOCOL2_HH
#define PROTOCOL2_HH

#include "C.hh"
#include "Protocol1.hh"
#include "umlrtinsignal.hh"
#include "umlrtoutsignal.hh"
#include "umlrtsignal.hh"
struct UMLRTCommsPort;

namespace Protocol2
{
    class Base : public Protocol1::Base
    {
    public:
        Base( const UMLRTCommsPort * & srcPort );
        UMLRTInSignal m2() const;
    };
    class Conj : public Protocol1::Conj
    {
    public:
        Conj( const UMLRTCommsPort * & srcPort );
        UMLRTOutSignal m2( char * x, const C & y ) const;
    };
    enum SignalId
    {
        signal_m2 = UMLRTSignal::FIRST_PROTOCOL_SIGNAL_ID,
        signal_m3,
        signal_m1
    };
};

#endif

