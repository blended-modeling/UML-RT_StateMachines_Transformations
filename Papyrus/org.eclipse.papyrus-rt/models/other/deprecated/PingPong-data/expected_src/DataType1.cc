
#include "DataType1.hh"

#include "umlrtobjectclass.hh"
#include "umlrtobjectclassgeneric.hh"
#include <cstddef>

const UMLRTObject_field DataType1::fields[] = 
{
    {
        "field0_enum",
        &UMLRTType_int,
        offsetof( DataType1, field0_enum ),
        1,
        0
    },
    {
        "field1_int",
        &UMLRTType_int,
        offsetof( DataType1, field1_int ),
        1,
        0
    },
    {
        "field2_bool",
        &UMLRTType_bool,
        offsetof( DataType1, field2_bool ),
        1,
        0
    },
    {
        "field3_double",
        &UMLRTType_double,
        offsetof( DataType1, field3_double ),
        1,
        0
    },
    {
        "field4_ptr",
        &UMLRTType_ptr,
        offsetof( DataType1, field4_ptr ),
        1,
        0
    },
    {
        "field6_string",
        &UMLRTType_charptr,
        offsetof( DataType1, field6_string ),
        1,
        0
    }
};









const UMLRTObject_class UMLRTType_DataType1 = 
{
    UMLRTObjectInitialize<DataType1>,
    UMLRTObjectCopy<DataType1>,
    UMLRTObject_decode,
    UMLRTObject_encode,
    UMLRTObjectDestroy<DataType1>,
    UMLRTObject_fprintf,
    "DataType1",
    NULL,
    {
        sizeof( DataType1 ),
        6,
        DataType1::fields
    },
    UMLRTOBJECTCLASS_DEFAULT_VERSION,
    UMLRTOBJECTCLASS_DEFAULT_BACKWARDS
};

