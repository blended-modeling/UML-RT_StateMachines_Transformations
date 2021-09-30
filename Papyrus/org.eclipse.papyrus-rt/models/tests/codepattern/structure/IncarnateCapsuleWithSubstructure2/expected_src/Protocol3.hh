
#ifndef PROTOCOL3_HH
#define PROTOCOL3_HH

#include "umlrtprotocol.hh"
struct UMLRTCommsPort;

namespace Protocol3
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

