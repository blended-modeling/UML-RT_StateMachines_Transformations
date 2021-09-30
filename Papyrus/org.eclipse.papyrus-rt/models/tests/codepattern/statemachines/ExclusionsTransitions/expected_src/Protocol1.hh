
#ifndef PROTOCOL1_HH
#define PROTOCOL1_HH

#include "umlrtinsignal.hh"
#include "umlrtoutsignal.hh"
#include "umlrtprotocol.hh"
#include "umlrtsignal.hh"
#include <stdint.h>
struct UMLRTCommsPort;

namespace Protocol1
{
    class Base : public UMLRTProtocol
    {
    public:
        Base( const UMLRTCommsPort * & srcPort );
        UMLRTInSignal m1() const;
        UMLRTInSignal m2() const;
        UMLRTInSignal m3() const;
    };
    class Conj : public UMLRTProtocol
    {
    public:
        Conj( const UMLRTCommsPort * & srcPort );
        UMLRTOutSignal m1( const int8_t & data ) const;
        UMLRTOutSignal m2( const int16_t & data ) const;
        UMLRTOutSignal m3( const int32_t & data ) const;
    };
    enum SignalId
    {
        signal_m1 = UMLRTSignal::FIRST_PROTOCOL_SIGNAL_ID,
        signal_m2,
        signal_m3
    };
};

#endif

