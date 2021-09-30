
#ifndef PROTOCOL9_HH
#define PROTOCOL9_HH

#include "umlrtinoutsignal.hh"
#include "umlrtobjectclass.hh"
#include "umlrtoutsignal.hh"
#include "umlrtprotocol.hh"
#include "umlrtsignal.hh"
struct UMLRTCommsPort;

namespace Protocol9
{
    class Base : public UMLRTProtocol
    {
    public:
        Base( const UMLRTCommsPort * & srcPort );
        UMLRTInOutSignal pr9_inout_m1() const;
        UMLRTOutSignal pr9_inout_m1( const UMLRTTypedValue & data ) const;
    };
    class Conj : public UMLRTProtocol
    {
    public:
        Conj( const UMLRTCommsPort * & srcPort );
        UMLRTInOutSignal pr9_inout_m1() const;
        UMLRTOutSignal pr9_inout_m1( const UMLRTTypedValue & data ) const;
    };
    enum SignalId
    {
        signal_pr9_inout_m1 = UMLRTSignal::FIRST_PROTOCOL_SIGNAL_ID
    };
};

#endif

