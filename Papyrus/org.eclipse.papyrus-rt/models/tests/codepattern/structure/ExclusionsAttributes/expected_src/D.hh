
#ifndef D_HH
#define D_HH

#include "C.hh"
#include "umlrtobjectclass.hh"

class D : public C
{
public:
    int a2;
    bool a1;
    static const UMLRTObject_field fields[];
};
extern const UMLRTObject_class UMLRTType_D;

#endif

