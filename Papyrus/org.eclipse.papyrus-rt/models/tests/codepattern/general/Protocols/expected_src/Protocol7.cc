
#include "Protocol7.hh"

#include "umlrtinsignal.hh"
#include "umlrtobjectclass.hh"
#include "umlrtoutsignal.hh"
struct UMLRTCommsPort;

Protocol7::Base::Base( const UMLRTCommsPort * & srcPort )
: UMLRTProtocol( srcPort )
{
}

UMLRTInSignal Protocol7::Base::pr7_in_m1() const
{
    UMLRTInSignal signal;
    signal.initialize( "pr7_in_m1", signal_pr7_in_m1 );
    return signal;
}

Protocol7::Conj::Conj( const UMLRTCommsPort * & srcPort )
: UMLRTProtocol( srcPort )
{
}

UMLRTOutSignal Protocol7::Conj::pr7_in_m1() const
{
    UMLRTOutSignal signal;
    signal.initialize( "pr7_in_m1", signal_pr7_in_m1 );
    return signal;
}

UMLRTOutSignal Protocol7::Conj::pr7_in_m1( const UMLRTTypedValue & data ) const
{
    UMLRTOutSignal signal;
    signal.initialize( "pr7_in_m1", signal_pr7_in_m1, srcPort, data.type, data.data );
    return signal;
}


