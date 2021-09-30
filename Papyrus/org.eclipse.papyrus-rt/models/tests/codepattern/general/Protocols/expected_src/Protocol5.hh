
#ifndef PROTOCOL5_HH
#define PROTOCOL5_HH

#include "umlrtinsignal.hh"
#include "umlrtoutsignal.hh"
#include "umlrtprotocol.hh"
#include "umlrtsignal.hh"
struct UMLRTCommsPort;

namespace Protocol5
{
    class Base : public UMLRTProtocol
    {
    public:
        Base( const UMLRTCommsPort * & srcPort );
        UMLRTOutSignal pr5_out_m1( float data ) const;
    };
    class Conj : public UMLRTProtocol
    {
    public:
        Conj( const UMLRTCommsPort * & srcPort );
        UMLRTInSignal pr5_out_m1() const;
    };
    enum SignalId
    {
        signal_pr5_out_m1 = UMLRTSignal::FIRST_PROTOCOL_SIGNAL_ID
    };
};

#endif

