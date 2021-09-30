
#ifndef PINGPONGPROTOCOL_HH
#define PINGPONGPROTOCOL_HH

#include "DataType1.hh"
#include "umlrtinsignal.hh"
#include "umlrtoutsignal.hh"
#include "umlrtprotocol.hh"
#include "umlrtsignal.hh"
struct UMLRTCommsPort;

class PingPongProtocol : public UMLRTProtocol
{
public:
    enum SignalId
    {
        signal_pong = UMLRTSignal::FIRST_PROTOCOL_SIGNAL_ID,
        signal_ping
    };
    class OutSignals
    {
    public:
        UMLRTOutSignal ping ( const UMLRTCommsPort * sourcePort, const DataType1 (& param)[2] ) const;
        UMLRTInSignal pong ( const UMLRTCommsPort * sourcePort ) const;
    };
    class InSignals
    {
    public:
        UMLRTInSignal ping ( const UMLRTCommsPort * sourcePort ) const;
        UMLRTOutSignal pong ( const UMLRTCommsPort * sourcePort, const DataType1 (& param)[2] ) const;
    };
    typedef OutSignals Base;
    typedef InSignals Conjugate;
};
class PingPongProtocol_baserole : protected UMLRTProtocol, private PingPongProtocol::Base
{
public:
    PingPongProtocol_baserole ( const UMLRTCommsPort * srcPort );
    UMLRTOutSignal ping ( const DataType1 (& param)[2] ) const;
    UMLRTInSignal pong ( ) const;
};
class PingPongProtocol_conjrole : protected UMLRTProtocol, private PingPongProtocol::Conjugate
{
public:
    PingPongProtocol_conjrole ( const UMLRTCommsPort * srcPort );
    UMLRTOutSignal pong ( const DataType1 (& param)[2] ) const;
    UMLRTInSignal ping ( ) const;
};

#endif

