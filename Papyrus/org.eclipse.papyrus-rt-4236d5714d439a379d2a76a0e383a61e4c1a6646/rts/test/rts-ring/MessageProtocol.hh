
#ifndef MESSAGEPROTOCOL_HH
#define MESSAGEPROTOCOL_HH

#include "Token.hh"
#include "umlrtoutsignal.hh"
#include "umlrtprotocol.hh"
#include "umlrtsignal.hh"
struct UMLRTCommsPort;

class MessageProtocol : public UMLRTProtocol
{
public:
    enum SignalId
    {
        signal_msg = UMLRTSignal::FIRST_PROTOCOL_SIGNAL_ID
    };
    class OutSignals
    {
    public:
        UMLRTOutSignal msg( const UMLRTCommsPort * sourcePort, const Token & param ) const;
    };
    class InSignals
    {
    public:
        UMLRTOutSignal msg( const UMLRTCommsPort * sourcePort, const Token & param ) const;
    };
    typedef OutSignals Base;
    typedef InSignals Conjugate;
};
class MessageProtocol_baserole : protected UMLRTProtocol, private MessageProtocol::Base
{
public:
    MessageProtocol_baserole( const UMLRTCommsPort * srcPort );
    UMLRTOutSignal msg( const Token & param ) const;
};
class MessageProtocol_conjrole : protected UMLRTProtocol, private MessageProtocol::Conjugate
{
public:
    MessageProtocol_conjrole( const UMLRTCommsPort * srcPort );
    UMLRTOutSignal msg( const Token & param ) const;
};

#endif

