
#ifndef PROTOCOL1_HH
#define PROTOCOL1_HH

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
    };
    class Conj : public UMLRTProtocol
    {
    public:
        Conj( const UMLRTCommsPort * & srcPort );
        UMLRTOutSignal im1() const;
        UMLRTOutSignal im2() const;
    };
    enum SignalId
    {
        signal_im1 = UMLRTSignal::FIRST_PROTOCOL_SIGNAL_ID,
        signal_im2
    };
};

#endif

