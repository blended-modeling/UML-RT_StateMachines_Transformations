
#ifndef Q_HH
#define Q_HH

#include "umlrtprotocol.hh"
struct UMLRTCommsPort;

namespace Q
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

