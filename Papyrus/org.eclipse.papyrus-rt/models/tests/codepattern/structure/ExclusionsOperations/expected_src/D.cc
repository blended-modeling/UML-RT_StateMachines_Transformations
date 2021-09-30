
#include "D.hh"

#include "umlrtobjectclass.hh"
#include "umlrtobjectclassgeneric.hh"
#include <cstddef>

const UMLRTObject_field D::fields[] = 
{
};


const UMLRTObject_class UMLRTType_D = 
{
    UMLRTObjectInitialize<D>,
    UMLRTObjectCopy<D>,
    UMLRTObject_decode,
    UMLRTObject_encode,
    UMLRTObjectDestroy<D>,
    UMLRTObject_fprintf,
    "D",
    NULL,
    {
        sizeof( D ),
        0,
        D::fields
    },
    UMLRTOBJECTCLASS_DEFAULT_VERSION,
    UMLRTOBJECTCLASS_DEFAULT_BACKWARDS
};

