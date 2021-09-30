
#ifndef CLASS1_HH
#define CLASS1_HH

#include "Class2.hh"
#include "umlrtobjectclass.hh"

class Class1
{
public:
    bool Attribute1;
    Class2 Attribute2;
    static const UMLRTObject_field fields[];
};
extern const UMLRTObject_class UMLRTType_Class1;

#endif

