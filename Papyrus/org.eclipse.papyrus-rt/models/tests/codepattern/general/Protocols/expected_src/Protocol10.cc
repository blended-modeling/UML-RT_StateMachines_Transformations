
#include "Protocol10.hh"

#include "umlrtinoutsignal.hh"
#include "umlrtinsignal.hh"
#include "umlrtobjectclass.hh"
#include "umlrtoutsignal.hh"
struct UMLRTCommsPort;

static UMLRTObject_field fields_pr4_in_m1[] = 
{
    {
        "data",
        &UMLRTType_double,
        0,
        1,
        0
    }
};

static UMLRTObject payload_pr4_in_m1 = 
{
    sizeof( double ),
    1,
    fields_pr4_in_m1
};

static UMLRTObject_field fields_pr4_in_m2[] = 
{
    #ifdef NEED_NON_FLEXIBLE_ARRAY
    {
        0,
        0,
        0,
        0,
        0
    }
    #endif
};

static UMLRTObject payload_pr4_in_m2 = 
{
    0,
    #ifdef NEED_NON_FLEXIBLE_ARRAY
    1
    #else
    0
    #endif
    ,
    fields_pr4_in_m2
};

static UMLRTObject_field fields_pr4_inout_m1[] = 
{
    {
        "data",
        &UMLRTType_double,
        0,
        1,
        0
    }
};

static UMLRTObject payload_pr4_inout_m1 = 
{
    sizeof( double ),
    1,
    fields_pr4_inout_m1
};

static UMLRTObject_field fields_pr4_inout_m2[] = 
{
    #ifdef NEED_NON_FLEXIBLE_ARRAY
    {
        0,
        0,
        0,
        0,
        0
    }
    #endif
};

static UMLRTObject payload_pr4_inout_m2 = 
{
    0,
    #ifdef NEED_NON_FLEXIBLE_ARRAY
    1
    #else
    0
    #endif
    ,
    fields_pr4_inout_m2
};

static UMLRTObject_field fields_pr4_out_m1[] = 
{
    {
        "data",
        &UMLRTType_double,
        0,
        1,
        0
    }
};

static UMLRTObject payload_pr4_out_m1 = 
{
    sizeof( double ),
    1,
    fields_pr4_out_m1
};

static UMLRTObject_field fields_pr4_out_m2[] = 
{
    #ifdef NEED_NON_FLEXIBLE_ARRAY
    {
        0,
        0,
        0,
        0,
        0
    }
    #endif
};

static UMLRTObject payload_pr4_out_m2 = 
{
    0,
    #ifdef NEED_NON_FLEXIBLE_ARRAY
    1
    #else
    0
    #endif
    ,
    fields_pr4_out_m2
};

Protocol10::Base::Base( const UMLRTCommsPort * & srcPort )
: UMLRTProtocol( srcPort )
{
}

UMLRTInSignal Protocol10::Base::pr4_in_m1() const
{
    UMLRTInSignal signal;
    signal.initialize( "pr4_in_m1", signal_pr4_in_m1, srcPort, &payload_pr4_in_m1 );
    return signal;
}

UMLRTInSignal Protocol10::Base::pr4_in_m2() const
{
    UMLRTInSignal signal;
    signal.initialize( "pr4_in_m2", signal_pr4_in_m2, srcPort, &payload_pr4_in_m2 );
    return signal;
}

UMLRTInSignal Protocol10::Base::pr4_in_m3() const
{
    UMLRTInSignal signal;
    signal.initialize( "pr4_in_m3", signal_pr4_in_m3 );
    return signal;
}

UMLRTInSignal Protocol10::Base::pr4_inout_m1() const
{
    UMLRTInSignal signal;
    signal.initialize( "pr4_inout_m1", signal_pr4_inout_m1, srcPort, &payload_pr4_inout_m1 );
    return signal;
}

UMLRTOutSignal Protocol10::Base::pr4_inout_m1( double data ) const
{
    UMLRTOutSignal signal;
    signal.initialize( "pr4_inout_m1", signal_pr4_inout_m1, srcPort, &payload_pr4_inout_m1, &data );
    return signal;
}

UMLRTInOutSignal Protocol10::Base::pr4_inout_m2() const
{
    UMLRTInOutSignal signal;
    signal.initialize( "pr4_inout_m2", signal_pr4_inout_m2, srcPort, &payload_pr4_inout_m2 );
    return signal;
}

UMLRTInOutSignal Protocol10::Base::pr4_inout_m3() const
{
    UMLRTInOutSignal signal;
    signal.initialize( "pr4_inout_m3", signal_pr4_inout_m3 );
    return signal;
}

UMLRTOutSignal Protocol10::Base::pr4_inout_m3( const UMLRTTypedValue & data ) const
{
    UMLRTOutSignal signal;
    signal.initialize( "pr4_inout_m3", signal_pr4_inout_m3, srcPort, data.type, data.data );
    return signal;
}

UMLRTOutSignal Protocol10::Base::pr4_out_m1( double data ) const
{
    UMLRTOutSignal signal;
    signal.initialize( "pr4_out_m1", signal_pr4_out_m1, srcPort, &payload_pr4_out_m1, &data );
    return signal;
}

UMLRTOutSignal Protocol10::Base::pr4_out_m2() const
{
    UMLRTOutSignal signal;
    signal.initialize( "pr4_out_m2", signal_pr4_out_m2, srcPort, &payload_pr4_out_m2 );
    return signal;
}

UMLRTOutSignal Protocol10::Base::pr4_out_m3() const
{
    UMLRTOutSignal signal;
    signal.initialize( "pr4_out_m3", signal_pr4_out_m3 );
    return signal;
}

UMLRTOutSignal Protocol10::Base::pr4_out_m3( const UMLRTTypedValue & data ) const
{
    UMLRTOutSignal signal;
    signal.initialize( "pr4_out_m3", signal_pr4_out_m3, srcPort, data.type, data.data );
    return signal;
}

Protocol10::Conj::Conj( const UMLRTCommsPort * & srcPort )
: UMLRTProtocol( srcPort )
{
}

UMLRTOutSignal Protocol10::Conj::pr4_in_m1( double data ) const
{
    UMLRTOutSignal signal;
    signal.initialize( "pr4_in_m1", signal_pr4_in_m1, srcPort, &payload_pr4_in_m1, &data );
    return signal;
}

UMLRTOutSignal Protocol10::Conj::pr4_in_m2() const
{
    UMLRTOutSignal signal;
    signal.initialize( "pr4_in_m2", signal_pr4_in_m2, srcPort, &payload_pr4_in_m2 );
    return signal;
}

UMLRTOutSignal Protocol10::Conj::pr4_in_m3() const
{
    UMLRTOutSignal signal;
    signal.initialize( "pr4_in_m3", signal_pr4_in_m3 );
    return signal;
}

UMLRTOutSignal Protocol10::Conj::pr4_in_m3( const UMLRTTypedValue & data ) const
{
    UMLRTOutSignal signal;
    signal.initialize( "pr4_in_m3", signal_pr4_in_m3, srcPort, data.type, data.data );
    return signal;
}

UMLRTOutSignal Protocol10::Conj::pr4_inout_m1( double data ) const
{
    UMLRTOutSignal signal;
    signal.initialize( "pr4_inout_m1", signal_pr4_inout_m1, srcPort, &payload_pr4_inout_m1, &data );
    return signal;
}

UMLRTInSignal Protocol10::Conj::pr4_inout_m1() const
{
    UMLRTInSignal signal;
    signal.initialize( "pr4_inout_m1", signal_pr4_inout_m1, srcPort, &payload_pr4_inout_m1 );
    return signal;
}

UMLRTInOutSignal Protocol10::Conj::pr4_inout_m2() const
{
    UMLRTInOutSignal signal;
    signal.initialize( "pr4_inout_m2", signal_pr4_inout_m2, srcPort, &payload_pr4_inout_m2 );
    return signal;
}

UMLRTInOutSignal Protocol10::Conj::pr4_inout_m3() const
{
    UMLRTInOutSignal signal;
    signal.initialize( "pr4_inout_m3", signal_pr4_inout_m3 );
    return signal;
}

UMLRTOutSignal Protocol10::Conj::pr4_inout_m3( const UMLRTTypedValue & data ) const
{
    UMLRTOutSignal signal;
    signal.initialize( "pr4_inout_m3", signal_pr4_inout_m3, srcPort, data.type, data.data );
    return signal;
}

UMLRTInSignal Protocol10::Conj::pr4_out_m1() const
{
    UMLRTInSignal signal;
    signal.initialize( "pr4_out_m1", signal_pr4_out_m1, srcPort, &payload_pr4_out_m1 );
    return signal;
}

UMLRTInSignal Protocol10::Conj::pr4_out_m2() const
{
    UMLRTInSignal signal;
    signal.initialize( "pr4_out_m2", signal_pr4_out_m2, srcPort, &payload_pr4_out_m2 );
    return signal;
}

UMLRTInSignal Protocol10::Conj::pr4_out_m3() const
{
    UMLRTInSignal signal;
    signal.initialize( "pr4_out_m3", signal_pr4_out_m3 );
    return signal;
}


