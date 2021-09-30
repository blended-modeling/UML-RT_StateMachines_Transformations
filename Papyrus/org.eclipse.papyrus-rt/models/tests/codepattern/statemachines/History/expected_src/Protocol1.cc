
#include "Protocol1.hh"

#include "umlrtinoutsignal.hh"
#include "umlrtinsignal.hh"
#include "umlrtobjectclass.hh"
#include "umlrtoutsignal.hh"
struct UMLRTCommsPort;

static UMLRTObject_field fields_im1[] = 
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

static UMLRTObject payload_im1 = 
{
    0,
    #ifdef NEED_NON_FLEXIBLE_ARRAY
    1
    #else
    0
    #endif
    ,
    fields_im1
};

static UMLRTObject_field fields_im2[] = 
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

static UMLRTObject payload_im2 = 
{
    0,
    #ifdef NEED_NON_FLEXIBLE_ARRAY
    1
    #else
    0
    #endif
    ,
    fields_im2
};

static UMLRTObject_field fields_im3[] = 
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

static UMLRTObject payload_im3 = 
{
    0,
    #ifdef NEED_NON_FLEXIBLE_ARRAY
    1
    #else
    0
    #endif
    ,
    fields_im3
};

static UMLRTObject_field fields_im4[] = 
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

static UMLRTObject payload_im4 = 
{
    0,
    #ifdef NEED_NON_FLEXIBLE_ARRAY
    1
    #else
    0
    #endif
    ,
    fields_im4
};

static UMLRTObject_field fields_iom1[] = 
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

static UMLRTObject payload_iom1 = 
{
    0,
    #ifdef NEED_NON_FLEXIBLE_ARRAY
    1
    #else
    0
    #endif
    ,
    fields_iom1
};

static UMLRTObject_field fields_iom2[] = 
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

static UMLRTObject payload_iom2 = 
{
    0,
    #ifdef NEED_NON_FLEXIBLE_ARRAY
    1
    #else
    0
    #endif
    ,
    fields_iom2
};

static UMLRTObject_field fields_iom3[] = 
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

static UMLRTObject payload_iom3 = 
{
    0,
    #ifdef NEED_NON_FLEXIBLE_ARRAY
    1
    #else
    0
    #endif
    ,
    fields_iom3
};

static UMLRTObject_field fields_iom4[] = 
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

static UMLRTObject payload_iom4 = 
{
    0,
    #ifdef NEED_NON_FLEXIBLE_ARRAY
    1
    #else
    0
    #endif
    ,
    fields_iom4
};

static UMLRTObject_field fields_om1[] = 
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

static UMLRTObject payload_om1 = 
{
    0,
    #ifdef NEED_NON_FLEXIBLE_ARRAY
    1
    #else
    0
    #endif
    ,
    fields_om1
};

static UMLRTObject_field fields_om2[] = 
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

static UMLRTObject payload_om2 = 
{
    0,
    #ifdef NEED_NON_FLEXIBLE_ARRAY
    1
    #else
    0
    #endif
    ,
    fields_om2
};

static UMLRTObject_field fields_om3[] = 
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

static UMLRTObject payload_om3 = 
{
    0,
    #ifdef NEED_NON_FLEXIBLE_ARRAY
    1
    #else
    0
    #endif
    ,
    fields_om3
};

static UMLRTObject_field fields_om4[] = 
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

static UMLRTObject payload_om4 = 
{
    0,
    #ifdef NEED_NON_FLEXIBLE_ARRAY
    1
    #else
    0
    #endif
    ,
    fields_om4
};

Protocol1::Base::Base( const UMLRTCommsPort * & srcPort )
: UMLRTProtocol( srcPort )
{
}

UMLRTInSignal Protocol1::Base::im1() const
{
    UMLRTInSignal signal;
    signal.initialize( "im1", signal_im1, srcPort, &payload_im1 );
    return signal;
}

UMLRTInSignal Protocol1::Base::im2() const
{
    UMLRTInSignal signal;
    signal.initialize( "im2", signal_im2, srcPort, &payload_im2 );
    return signal;
}

UMLRTInSignal Protocol1::Base::im3() const
{
    UMLRTInSignal signal;
    signal.initialize( "im3", signal_im3, srcPort, &payload_im3 );
    return signal;
}

UMLRTInSignal Protocol1::Base::im4() const
{
    UMLRTInSignal signal;
    signal.initialize( "im4", signal_im4, srcPort, &payload_im4 );
    return signal;
}

UMLRTInOutSignal Protocol1::Base::iom1() const
{
    UMLRTInOutSignal signal;
    signal.initialize( "iom1", signal_iom1, srcPort, &payload_iom1 );
    return signal;
}

UMLRTInOutSignal Protocol1::Base::iom2() const
{
    UMLRTInOutSignal signal;
    signal.initialize( "iom2", signal_iom2, srcPort, &payload_iom2 );
    return signal;
}

UMLRTInOutSignal Protocol1::Base::iom3() const
{
    UMLRTInOutSignal signal;
    signal.initialize( "iom3", signal_iom3, srcPort, &payload_iom3 );
    return signal;
}

UMLRTInOutSignal Protocol1::Base::iom4() const
{
    UMLRTInOutSignal signal;
    signal.initialize( "iom4", signal_iom4, srcPort, &payload_iom4 );
    return signal;
}

UMLRTOutSignal Protocol1::Base::om1() const
{
    UMLRTOutSignal signal;
    signal.initialize( "om1", signal_om1, srcPort, &payload_om1 );
    return signal;
}

UMLRTOutSignal Protocol1::Base::om2() const
{
    UMLRTOutSignal signal;
    signal.initialize( "om2", signal_om2, srcPort, &payload_om2 );
    return signal;
}

UMLRTOutSignal Protocol1::Base::om3() const
{
    UMLRTOutSignal signal;
    signal.initialize( "om3", signal_om3, srcPort, &payload_om3 );
    return signal;
}

UMLRTOutSignal Protocol1::Base::om4() const
{
    UMLRTOutSignal signal;
    signal.initialize( "om4", signal_om4, srcPort, &payload_om4 );
    return signal;
}

Protocol1::Conj::Conj( const UMLRTCommsPort * & srcPort )
: UMLRTProtocol( srcPort )
{
}

UMLRTOutSignal Protocol1::Conj::im1() const
{
    UMLRTOutSignal signal;
    signal.initialize( "im1", signal_im1, srcPort, &payload_im1 );
    return signal;
}

UMLRTOutSignal Protocol1::Conj::im2() const
{
    UMLRTOutSignal signal;
    signal.initialize( "im2", signal_im2, srcPort, &payload_im2 );
    return signal;
}

UMLRTOutSignal Protocol1::Conj::im3() const
{
    UMLRTOutSignal signal;
    signal.initialize( "im3", signal_im3, srcPort, &payload_im3 );
    return signal;
}

UMLRTOutSignal Protocol1::Conj::im4() const
{
    UMLRTOutSignal signal;
    signal.initialize( "im4", signal_im4, srcPort, &payload_im4 );
    return signal;
}

UMLRTInOutSignal Protocol1::Conj::iom1() const
{
    UMLRTInOutSignal signal;
    signal.initialize( "iom1", signal_iom1, srcPort, &payload_iom1 );
    return signal;
}

UMLRTInOutSignal Protocol1::Conj::iom2() const
{
    UMLRTInOutSignal signal;
    signal.initialize( "iom2", signal_iom2, srcPort, &payload_iom2 );
    return signal;
}

UMLRTInOutSignal Protocol1::Conj::iom3() const
{
    UMLRTInOutSignal signal;
    signal.initialize( "iom3", signal_iom3, srcPort, &payload_iom3 );
    return signal;
}

UMLRTInOutSignal Protocol1::Conj::iom4() const
{
    UMLRTInOutSignal signal;
    signal.initialize( "iom4", signal_iom4, srcPort, &payload_iom4 );
    return signal;
}

UMLRTInSignal Protocol1::Conj::om1() const
{
    UMLRTInSignal signal;
    signal.initialize( "om1", signal_om1, srcPort, &payload_om1 );
    return signal;
}

UMLRTInSignal Protocol1::Conj::om2() const
{
    UMLRTInSignal signal;
    signal.initialize( "om2", signal_om2, srcPort, &payload_om2 );
    return signal;
}

UMLRTInSignal Protocol1::Conj::om3() const
{
    UMLRTInSignal signal;
    signal.initialize( "om3", signal_om3, srcPort, &payload_om3 );
    return signal;
}

UMLRTInSignal Protocol1::Conj::om4() const
{
    UMLRTInSignal signal;
    signal.initialize( "om4", signal_om4, srcPort, &payload_om4 );
    return signal;
}


