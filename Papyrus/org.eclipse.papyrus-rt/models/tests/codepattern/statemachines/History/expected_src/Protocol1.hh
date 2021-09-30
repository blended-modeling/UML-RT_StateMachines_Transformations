
#ifndef PROTOCOL1_HH
#define PROTOCOL1_HH

#include "umlrtinoutsignal.hh"
#include "umlrtinsignal.hh"
#include "umlrtoutsignal.hh"
#include "umlrtprotocol.hh"
#include "umlrtsignal.hh"
struct UMLRTCommsPort;

namespace Protocol1
{
    class Base : public UMLRTProtocol
    {
    public:
        Base( const UMLRTCommsPort * & srcPort );
        UMLRTInSignal im1() const;
        UMLRTInSignal im2() const;
        UMLRTInSignal im3() const;
        UMLRTInSignal im4() const;
        UMLRTInOutSignal iom1() const;
        UMLRTInOutSignal iom2() const;
        UMLRTInOutSignal iom3() const;
        UMLRTInOutSignal iom4() const;
        UMLRTOutSignal om1() const;
        UMLRTOutSignal om2() const;
        UMLRTOutSignal om3() const;
        UMLRTOutSignal om4() const;
    };
    class Conj : public UMLRTProtocol
    {
    public:
        Conj( const UMLRTCommsPort * & srcPort );
        UMLRTOutSignal im1() const;
        UMLRTOutSignal im2() const;
        UMLRTOutSignal im3() const;
        UMLRTOutSignal im4() const;
        UMLRTInOutSignal iom1() const;
        UMLRTInOutSignal iom2() const;
        UMLRTInOutSignal iom3() const;
        UMLRTInOutSignal iom4() const;
        UMLRTInSignal om1() const;
        UMLRTInSignal om2() const;
        UMLRTInSignal om3() const;
        UMLRTInSignal om4() const;
    };
    enum SignalId
    {
        signal_im1 = UMLRTSignal::FIRST_PROTOCOL_SIGNAL_ID,
        signal_im2,
        signal_im3,
        signal_im4,
        signal_iom1,
        signal_iom2,
        signal_iom3,
        signal_iom4,
        signal_om1,
        signal_om2,
        signal_om3,
        signal_om4
    };
};

#endif

