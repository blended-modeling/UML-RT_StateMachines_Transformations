
#ifndef P1_HH
#define P1_HH

#include "umlrtprotocol.hh"
struct UMLRTCommsPort;

namespace P1
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

