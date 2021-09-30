
#ifndef PINGPONGPROTOCOL_HH
#define PINGPONGPROTOCOL_HH

#include "DataType1.hh"
#include "umlrtinsignal.hh"
#include "umlrtoutsignal.hh"
#include "umlrtprotocol.hh"
#include "umlrtsignal.hh"
struct UMLRTCommsPort;

namespace PingPongProtocol
{
    class Conj : protected UMLRTProtocol
    {
    public:
        Conj( const UMLRTCommsPort * & srcPort );
        UMLRTOutSignal pong1( const DataType1 (& dta)[2] ) const;
        UMLRTOutSignal pong2( int command, char label, const DataType1 & datatype, long long timestamp, float measurement ) const;
    };
    enum SignalId
    {
        signal_pong1 = UMLRTSignal::FIRST_PROTOCOL_SIGNAL_ID,
        signal_pong2,
        signal_ping1,
        signal_ping2
    };
    class Base : protected UMLRTProtocol
    {
    public:
        Base( const UMLRTCommsPort * & srcPort );
        UMLRTOutSignal ping1( const DataType1 (& dta_param)[2] ) const;
        UMLRTOutSignal ping2( int command, char label, const DataType1 & datatype, long long timestamp, float measurement ) const;
    };
};

#endif
