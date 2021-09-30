
#ifndef R_HH
#define R_HH

#include "umlrtprotocol.hh"
struct UMLRTCommsPort;

namespace R
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

