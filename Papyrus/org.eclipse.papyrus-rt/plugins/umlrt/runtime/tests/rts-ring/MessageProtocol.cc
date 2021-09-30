
#include "MessageProtocol.hh"

#include "Token.hh"
#include "umlrtobjectclass.hh"
#include "umlrtoutsignal.hh"
struct UMLRTCommsPort;

MessageProtocol::Base::Base( const UMLRTCommsPort * & srcPort )
: UMLRTProtocol( srcPort )
{
}

UMLRTOutSignal MessageProtocol::Base::msg( const Token & param ) const
{
    UMLRTOutSignal signal;
    signal.initialize( "signal_msg", signal_msg, srcPort, &UMLRTType_Token, &param);
    return signal;
}

MessageProtocol::Conj::Conj( const UMLRTCommsPort * & srcPort )
: UMLRTProtocol( srcPort )
{
}

UMLRTOutSignal MessageProtocol::Conj::msg( const Token & param ) const
{
    UMLRTOutSignal signal;
    signal.initialize( "signal_msg", signal_msg, srcPort, &UMLRTType_Token, &param);
    return signal;
}


