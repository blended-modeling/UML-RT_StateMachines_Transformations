
#include "C.hh"

#include "umlrtobjectclass.hh"
#include "umlrtobjectclassgeneric.hh"
#include <cstddef>

const UMLRTObject_field C::fields[] = 
{
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
        0,
        C::fields
    },
    UMLRTOBJECTCLASS_DEFAULT_VERSION,
    UMLRTOBJECTCLASS_DEFAULT_BACKWARDS
};

