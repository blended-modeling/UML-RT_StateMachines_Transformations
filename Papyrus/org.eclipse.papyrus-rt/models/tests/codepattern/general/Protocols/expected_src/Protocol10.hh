
#ifndef PROTOCOL10_HH
#define PROTOCOL10_HH

#include "umlrtinoutsignal.hh"
#include "umlrtinsignal.hh"
#include "umlrtobjectclass.hh"
#include "umlrtoutsignal.hh"
#include "umlrtprotocol.hh"
#include "umlrtsignal.hh"
struct UMLRTCommsPort;

namespace Protocol10
{
    class Base : public UMLRTProtocol
    {
    public:
        Base( const UMLRTCommsPort * & srcPort );
        UMLRTInSignal pr4_in_m1() const;
        UMLRTInSignal pr4_in_m2() const;
        UMLRTInSignal pr4_in_m3() const;
        UMLRTInSignal pr4_inout_m1() const;
        UMLRTOutSignal pr4_inout_m1( double data ) const;
        UMLRTInOutSignal pr4_inout_m2() const;
        UMLRTInOutSignal pr4_inout_m3() const;
        UMLRTOutSignal pr4_inout_m3( const UMLRTTypedValue & data ) const;
        UMLRTOutSignal pr4_out_m1( double data ) const;
        UMLRTOutSignal pr4_out_m2() const;
        UMLRTOutSignal pr4_out_m3() const;
        UMLRTOutSignal pr4_out_m3( const UMLRTTypedValue & data ) const;
    };
    class Conj : public UMLRTProtocol
    {
    public:
        Conj( const UMLRTCommsPort * & srcPort );
        UMLRTOutSignal pr4_in_m1( double data ) const;
        UMLRTOutSignal pr4_in_m2() const;
        UMLRTOutSignal pr4_in_m3() const;
        UMLRTOutSignal pr4_in_m3( const UMLRTTypedValue & data ) const;
        UMLRTOutSignal pr4_inout_m1( double data ) const;
        UMLRTInSignal pr4_inout_m1() const;
        UMLRTInOutSignal pr4_inout_m2() const;
        UMLRTInOutSignal pr4_inout_m3() const;
        UMLRTOutSignal pr4_inout_m3( const UMLRTTypedValue & data ) const;
        UMLRTInSignal pr4_out_m1() const;
        UMLRTInSignal pr4_out_m2() const;
        UMLRTInSignal pr4_out_m3() const;
    };
    enum SignalId
    {
        signal_pr4_in_m1 = UMLRTSignal::FIRST_PROTOCOL_SIGNAL_ID,
        signal_pr4_in_m2,
        signal_pr4_in_m3,
        signal_pr4_inout_m1,
        signal_pr4_inout_m2,
        signal_pr4_inout_m3,
        signal_pr4_out_m1,
        signal_pr4_out_m2,
        signal_pr4_out_m3
    };
};

#endif

