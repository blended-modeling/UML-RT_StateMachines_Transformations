
#ifndef P_HH
#define P_HH

#include "umlrtprotocol.hh"
struct UMLRTCommsPort;

namespace P
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

