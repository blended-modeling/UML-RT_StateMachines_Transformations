
#ifndef PROTOCOL1_HH
#define PROTOCOL1_HH

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
        UMLRTInSignal InProtocolMessage1() const;
        UMLRTInSignal InProtocolMessage2() const;
        UMLRTInSignal InProtocolMessage3() const;
    };
    class Conj : public UMLRTProtocol
    {
    public:
        Conj( const UMLRTCommsPort * & srcPort );
        UMLRTOutSignal InProtocolMessage1() const;
        UMLRTOutSignal InProtocolMessage2( int data ) const;
        UMLRTOutSignal InProtocolMessage3( char data ) const;
    };
    enum SignalId
    {
        signal_InProtocolMessage1 = UMLRTSignal::FIRST_PROTOCOL_SIGNAL_ID,
        signal_InProtocolMessage2,
        signal_InProtocolMessage3
    };
};

#endif

