
#include "C.hh"

#include "umlrtobjectclass.hh"
#include "umlrtobjectclassgeneric.hh"
#include <cstddef>

const UMLRTObject_field C::fields[] = 
{
    {
        "a1",
        &UMLRTType_bool,
        offsetof( C, a1 ),
        1,
        0
    },
    {
        "a2",
        &UMLRTType_int,
        offsetof( C, a2 ),
        1,
        0
    },
    {
        "a3",
        &UMLRTType_double,
        offsetof( C, a3 ),
        1,
        0
    }
};





const UMLRTObject_class UMLRTType_C = 
{
    UMLRTObjectInitialize<C>,
    UMLRTObjectCopy<C>,
    UMLRTObject_decode,
    UMLRTObject_encode,
    UMLRTObjectDestroy<C>,
    UMLRTObject_fprintf,
    "C",
    NULL,
    {
        sizeof( C ),
        3,
        C::fields
    },
    UMLRTOBJECTCLASS_DEFAULT_VERSION,
    UMLRTOBJECTCLASS_DEFAULT_BACKWARDS
};

