
#ifndef P2_HH
#define P2_HH

#include "umlrtprotocol.hh"
struct UMLRTCommsPort;

namespace P2
{
    class Base : public UMLRTProtocol
    {
    public:
        Base( const UMLRTCommsPort * & srcPort );
    };
    class Conj : public UMLRTProtocol
    {
    public:
        Conj( const UMLRTCommsPort * & srcPort );
    };
};

#endif

