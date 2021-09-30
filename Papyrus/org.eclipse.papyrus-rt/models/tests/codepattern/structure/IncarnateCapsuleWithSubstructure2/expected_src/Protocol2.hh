
#ifndef PROTOCOL2_HH
#define PROTOCOL2_HH

#include "umlrtprotocol.hh"
struct UMLRTCommsPort;

namespace Protocol2
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

