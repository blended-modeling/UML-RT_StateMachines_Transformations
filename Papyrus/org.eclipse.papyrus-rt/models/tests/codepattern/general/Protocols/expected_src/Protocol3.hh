
#ifndef PROTOCOL3_HH
#define PROTOCOL3_HH

#include "umlrtinoutsignal.hh"
#include "umlrtprotocol.hh"
#include "umlrtsignal.hh"
struct UMLRTCommsPort;

namespace Protocol3
{
    class Base : public UMLRTProtocol
    {
    public:
        Base( const UMLRTCommsPort * & srcPort );
        UMLRTInOutSignal pr3_inout_m1() const;
    };
    class Conj : public UMLRTProtocol
    {
    public:
        Conj( const UMLRTCommsPort * & srcPort );
        UMLRTInOutSignal pr3_inout_m1() const;
    };
    enum SignalId
    {
        signal_pr3_inout_m1 = UMLRTSignal::FIRST_PROTOCOL_SIGNAL_ID
    };
};

#endif

