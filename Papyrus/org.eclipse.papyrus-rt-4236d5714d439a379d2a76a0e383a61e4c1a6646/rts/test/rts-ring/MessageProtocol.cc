
#include "MessageProtocol.hh"

#include "Token.hh"
#include "Token.hh"
#include "umlrtoutsignal.hh"
struct UMLRTCommsPort;
struct UMLRTObject_class;


UMLRTOutSignal MessageProtocol::OutSignals::msg( const UMLRTCommsPort * sourcePort, const Token & param ) const
{
    UMLRTOutSignal signal;
    signal.initialize( signal_msg, sourcePort, UMLRTType_Token->getSize( UMLRTType_Token ) );
    signal.encode( UMLRTType_Token, &param );
    return signal;
}


UMLRTOutSignal MessageProtocol::InSignals::msg( const UMLRTCommsPort * sourcePort, const Token & param ) const
{
    UMLRTOutSignal signal;
    signal.initialize( signal_msg, sourcePort, UMLRTType_Token->getSize( UMLRTType_Token ) );
    signal.encode( UMLRTType_Token, &param );
    return signal;
}




MessageProtocol_baserole::MessageProtocol_baserole( const UMLRTCommsPort * srcPort )
: UMLRTProtocol( srcPort )
{
}

UMLRTOutSignal MessageProtocol_baserole::msg( const Token & param ) const
{
    return MessageProtocol::Base::msg( srcPort, param );
}

MessageProtocol_conjrole::MessageProtocol_conjrole( const UMLRTCommsPort * srcPort )
: UMLRTProtocol( srcPort )
{
}

UMLRTOutSignal MessageProtocol_conjrole::msg( const Token & param ) const
{
    return MessageProtocol::Conjugate::msg( srcPort, param );
}

