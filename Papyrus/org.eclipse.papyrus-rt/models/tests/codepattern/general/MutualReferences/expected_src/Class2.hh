
#ifndef CLASS2_HH
#define CLASS2_HH

#include "umlrtobjectclass.hh"
class Class1;

class Class2
{
public:
    int Attribute1;
    const Class1 * Attribute2;
    static const UMLRTObject_field fields[];
};
extern const UMLRTObject_class UMLRTType_Class2;

#endif

