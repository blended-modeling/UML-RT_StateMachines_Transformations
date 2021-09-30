
#include "D.hh"

#include "umlrtobjectclass.hh"
#include "umlrtobjectclassgeneric.hh"
#include <cstddef>

const UMLRTObject_field D::fields[] = 
{
    {
        "a2",
        &UMLRTType_int,
        offsetof( D, a2 ),
        1,
        0
    },
    {
        "a1",
        &UMLRTType_bool,
        offsetof( D, a1 ),
        1,
        0
    }
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
        2,
        D::fields
    },
    UMLRTOBJECTCLASS_DEFAULT_VERSION,
    UMLRTOBJECTCLASS_DEFAULT_BACKWARDS
};

