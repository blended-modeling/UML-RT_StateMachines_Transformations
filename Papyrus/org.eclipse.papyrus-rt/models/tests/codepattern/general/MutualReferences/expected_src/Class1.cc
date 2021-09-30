
#include "Class1.hh"

#include "umlrtobjectclass.hh"
#include "umlrtobjectclassgeneric.hh"
#include <cstddef>

const UMLRTObject_field Class1::fields[] = 
{
    {
        "Attribute1",
        &UMLRTType_bool,
        offsetof( Class1, Attribute1 ),
        1,
        0
    },
    {
        "Attribute2",
        &UMLRTType_Class2,
        offsetof( Class1, Attribute2 ),
        1,
        0
    }
};




const UMLRTObject_class UMLRTType_Class1 = 
{
    UMLRTObjectInitialize<Class1>,
    UMLRTObjectCopy<Class1>,
    UMLRTObject_decode,
    UMLRTObject_encode,
    UMLRTObjectDestroy<Class1>,
    UMLRTObject_fprintf,
    "Class1",
    NULL,
    {
        sizeof( Class1 ),
        2,
        Class1::fields
    },
    UMLRTOBJECTCLASS_DEFAULT_VERSION,
    UMLRTOBJECTCLASS_DEFAULT_BACKWARDS
};

