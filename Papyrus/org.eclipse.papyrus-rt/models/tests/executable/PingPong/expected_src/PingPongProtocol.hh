
#ifndef PINGPONGPROTOCOL_HH
#define PINGPONGPROTOCOL_HH

#include "umlrtinsignal.hh"
#include "umlrtoutsignal.hh"
#include "umlrtprotocol.hh"
#include "umlrtsignal.hh"
struct UMLRTCommsPort;

namespace PingPongProtocol
{
    class Base : public UMLRTProtocol
    {
    public:
        Base( const UMLRTCommsPort * & srcPort );
        UMLRTInSignal pong() const;
        UMLRTOutSignal ping() const;
    };
    class Conj : public UMLRTProtocol
    {
    public:
        Conj( const UMLRTCommsPort * & srcPort );
        UMLRTOutSignal pong() const;
        UMLRTInSignal ping() const;
    };
    enum SignalId
    {
        signal_pong = UMLRTSignal::FIRST_PROTOCOL_SIGNAL_ID,
        signal_ping
    };
};

#endif

