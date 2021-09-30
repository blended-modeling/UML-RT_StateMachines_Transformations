
#include "Protocol9.hh"

#include "umlrtinoutsignal.hh"
#include "umlrtobjectclass.hh"
#include "umlrtoutsignal.hh"
struct UMLRTCommsPort;

Protocol9::Base::Base( const UMLRTCommsPort * & srcPort )
: UMLRTProtocol( srcPort )
{
}

UMLRTInOutSignal Protocol9::Base::pr9_inout_m1() const
{
    UMLRTInOutSignal signal;
    signal.initialize( "pr9_inout_m1", signal_pr9_inout_m1 );
    return signal;
}

UMLRTOutSignal Protocol9::Base::pr9_inout_m1( const UMLRTTypedValue & data ) const
{
    UMLRTOutSignal signal;
    signal.initialize( "pr9_inout_m1", signal_pr9_inout_m1, srcPort, data.type, data.data );
    return signal;
}

Protocol9::Conj::Conj( const UMLRTCommsPort * & srcPort )
: UMLRTProtocol( srcPort )
{
}

UMLRTInOutSignal Protocol9::Conj::pr9_inout_m1() const
{
    UMLRTInOutSignal signal;
    signal.initialize( "pr9_inout_m1", signal_pr9_inout_m1 );
    return signal;
}

UMLRTOutSignal Protocol9::Conj::pr9_inout_m1( const UMLRTTypedValue & data ) const
{
    UMLRTOutSignal signal;
    signal.initialize( "pr9_inout_m1", signal_pr9_inout_m1, srcPort, data.type, data.data );
    return signal;
}


