
#ifndef PROTOCOL6_HH
#define PROTOCOL6_HH

#include "umlrtinsignal.hh"
#include "umlrtoutsignal.hh"
#include "umlrtprotocol.hh"
#include "umlrtsignal.hh"
struct UMLRTCommsPort;

namespace Protocol6
{
    class Base : public UMLRTProtocol
    {
    public:
        Base( const UMLRTCommsPort * & srcPort );
        UMLRTInSignal pr6_inout_m1() const;
        UMLRTOutSignal pr6_inout_m1( float data ) const;
    };
    class Conj : public UMLRTProtocol
    {
    public:
        Conj( const UMLRTCommsPort * & srcPort );
        UMLRTOutSignal pr6_inout_m1( float data ) const;
        UMLRTInSignal pr6_inout_m1() const;
    };
    enum SignalId
    {
        signal_pr6_inout_m1 = UMLRTSignal::FIRST_PROTOCOL_SIGNAL_ID
    };
};

#endif

