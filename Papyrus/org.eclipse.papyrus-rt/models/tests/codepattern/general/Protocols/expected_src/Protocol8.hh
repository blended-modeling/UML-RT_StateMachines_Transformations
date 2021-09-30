
#ifndef PROTOCOL8_HH
#define PROTOCOL8_HH

#include "umlrtinsignal.hh"
#include "umlrtobjectclass.hh"
#include "umlrtoutsignal.hh"
#include "umlrtprotocol.hh"
#include "umlrtsignal.hh"
struct UMLRTCommsPort;

namespace Protocol8
{
    class Base : public UMLRTProtocol
    {
    public:
        Base( const UMLRTCommsPort * & srcPort );
        UMLRTOutSignal pr8_out_m1() const;
        UMLRTOutSignal pr8_out_m1( const UMLRTTypedValue & data ) const;
    };
    class Conj : public UMLRTProtocol
    {
    public:
        Conj( const UMLRTCommsPort * & srcPort );
        UMLRTInSignal pr8_out_m1() const;
    };
    enum SignalId
    {
        signal_pr8_out_m1 = UMLRTSignal::FIRST_PROTOCOL_SIGNAL_ID
    };
};

#endif

