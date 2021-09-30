
#include "Class2.hh"

#include "umlrtobjectclass.hh"
#include "umlrtobjectclassgeneric.hh"
#include <cstddef>

const UMLRTObject_field Class2::fields[] = 
{
    {
        "Attribute1",
        &UMLRTType_int,
        offsetof( Class2, Attribute1 ),
        1,
        0
    },
    {
        "Attribute2",
        &UMLRTType_ptr,
        offsetof( Class2, Attribute2 ),
        1,
        0
    }
};




const UMLRTObject_class UMLRTType_Class2 = 
{
    UMLRTObjectInitialize<Class2>,
    UMLRTObjectCopy<Class2>,
    UMLRTObject_decode,
    UMLRTObject_encode,
    UMLRTObjectDestroy<Class2>,
    UMLRTObject_fprintf,
    "Class2",
    NULL,
    {
        sizeof( Class2 ),
        2,
        Class2::fields
    },
    UMLRTOBJECTCLASS_DEFAULT_VERSION,
    UMLRTOBJECTCLASS_DEFAULT_BACKWARDS
};

