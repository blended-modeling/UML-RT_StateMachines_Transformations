
#include "DataType1.hh"

#include "umlrtobjectclass.hh"
#include <cstddef>

static const UMLRTObject_field sst1fields[] =
{
    {
        "name",
        UMLRTType_char,
        offsetof( SubStructType1, name ),
        sizeof(((struct SubStructType1 *)0)->name[0]),
        8,
        0
    },
    {
        "character",
        UMLRTType_char,
        offsetof( SubStructType1, character),
        sizeof(((struct SubStructType1 *)0)->character),
        1,
        0
    },
    {
        "integer",
        UMLRTType_int,
        offsetof( SubStructType1, integer ),
        sizeof(((struct SubStructType1 *)0)->integer),
        1,
        0
    },
};

static const UMLRTObject_class sst1desc =
{
    "SubStructType1",
    UMLRTObject_initialize,
    UMLRTObject_copy,
    UMLRTObject_decode,
    UMLRTObject_encode,
    UMLRTObject_destroy,
    UMLRTObject_getSize,
    UMLRTObject_fprintf,
    NULL,
    UMLRTOBJECTCLASS_DEFAULT_VERSION,
    UMLRTOBJECTCLASS_DEFAULT_BACKWARDS,
    sizeof(SubStructType1),
    3,
    sst1fields
};

const UMLRTObject_class * const RTType_SubStruct1 = &sst1desc;

static const UMLRTObject_field dt1fields[] =
{
    {
        "field1_int",
        UMLRTType_int,
        offsetof( DataType1, field1_int ),
        sizeof(((struct DataType1 *)0)->field1_int[0]),
        2,
        0
    },
    {
        "field2_bool",
        UMLRTType_bool,
        offsetof( DataType1, field2_bool ),
        sizeof(((struct DataType1 *)0)->field2_bool),
        1,
        0
    },
    {
        "field3_double",
        UMLRTType_double,
        offsetof( DataType1, field3_double ),
        sizeof(((struct DataType1 *)0)->field3_double),
        1,
        0
    },
    {
        "field4_sst1",
        RTType_SubStruct1,
        offsetof( DataType1, field4_sst1 ),
        sizeof(((struct DataType1 *)0)->field4_sst1[0]),
        3,
        0
    },

};
static const UMLRTObject_class dt1desc =
{
    "DataType1",
    UMLRTObject_initialize,
    UMLRTObject_copy,
    UMLRTObject_decode,
    UMLRTObject_encode,
    UMLRTObject_destroy,
    UMLRTObject_getSize,
    UMLRTObject_fprintf,
    NULL,
    UMLRTOBJECTCLASS_DEFAULT_VERSION,
    UMLRTOBJECTCLASS_DEFAULT_BACKWARDS,
    sizeof(DataType1),
    4,
    dt1fields
};

const UMLRTObject_class * const RTType_DataType1 = &dt1desc;
