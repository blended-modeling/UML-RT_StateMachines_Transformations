
#ifndef MESSAGEPROTOCOL_HH
#define MESSAGEPROTOCOL_HH

#include "Token.hh"
#include "umlrtoutsignal.hh"
#include "umlrtprotocol.hh"
#include "umlrtsignal.hh"
struct UMLRTCommsPort;

namespace MessageProtocol
{
    class Base : protected UMLRTProtocol
    {
    public:
        Base( const UMLRTCommsPort * & srcPort );
        UMLRTOutSignal msg( const Token & param ) const;
    };
    class Conj : protected UMLRTProtocol
    {
    public:
        Conj( const UMLRTCommsPort * & srcPort );
        UMLRTOutSignal msg( const Token & param ) const;
    };
    enum SignalId
    {
        signal_msg = UMLRTSignal::FIRST_PROTOCOL_SIGNAL_ID
    };
};

#endif

