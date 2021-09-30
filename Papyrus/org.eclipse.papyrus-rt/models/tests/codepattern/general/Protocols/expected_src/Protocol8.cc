
#include "Protocol8.hh"

#include "umlrtobjectclass.hh"
#include "umlrtinsignal.hh"
#include "umlrtoutsignal.hh"
struct UMLRTCommsPort;

Protocol8::Base::Base( const UMLRTCommsPort * & srcPort )
: UMLRTProtocol( srcPort )
{
}

UMLRTOutSignal Protocol8::Base::pr8_out_m1() const
{
    UMLRTOutSignal signal;
    signal.initialize( "pr8_out_m1", signal_pr8_out_m1 );
    return signal;
}

UMLRTOutSignal Protocol8::Base::pr8_out_m1( const UMLRTTypedValue & data ) const
{
    UMLRTOutSignal signal;
    signal.initialize( "pr8_out_m1", signal_pr8_out_m1, srcPort, data.type, data.data );
    return signal;
}

Protocol8::Conj::Conj( const UMLRTCommsPort * & srcPort )
: UMLRTProtocol( srcPort )
{
}

UMLRTInSignal Protocol8::Conj::pr8_out_m1() const
{
    UMLRTInSignal signal;
    signal.initialize( "pr8_out_m1", signal_pr8_out_m1 );
    return signal;
}


