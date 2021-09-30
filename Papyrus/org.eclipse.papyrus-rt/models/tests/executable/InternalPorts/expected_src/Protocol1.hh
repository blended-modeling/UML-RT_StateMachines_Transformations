
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
        UMLRTInSignal done() const;
        UMLRTInSignal ready() const;
        UMLRTOutSignal start() const;
        UMLRTOutSignal stop() const;
    };
    class Conj : public UMLRTProtocol
    {
    public:
        Conj( const UMLRTCommsPort * & srcPort );
        UMLRTOutSignal done() const;
        UMLRTOutSignal ready() const;
        UMLRTInSignal start() const;
        UMLRTInSignal stop() const;
    };
    enum SignalId
    {
        signal_done = UMLRTSignal::FIRST_PROTOCOL_SIGNAL_ID,
        signal_ready,
        signal_start,
        signal_stop
    };
};

#endif

