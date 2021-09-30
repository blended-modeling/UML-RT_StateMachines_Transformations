
#ifndef PROTOCOL4_HH
#define PROTOCOL4_HH

#include "umlrtinsignal.hh"
#include "umlrtoutsignal.hh"
#include "umlrtprotocol.hh"
#include "umlrtsignal.hh"
struct UMLRTCommsPort;

namespace Protocol4
{
    class Base : public UMLRTProtocol
    {
    public:
        Base( const UMLRTCommsPort * & srcPort );
        UMLRTInSignal p4_in_m1() const;
    };
    class Conj : public UMLRTProtocol
    {
    public:
        Conj( const UMLRTCommsPort * & srcPort );
        UMLRTOutSignal p4_in_m1( float data ) const;
    };
    enum SignalId
    {
        signal_p4_in_m1 = UMLRTSignal::FIRST_PROTOCOL_SIGNAL_ID
    };
};

#endif

