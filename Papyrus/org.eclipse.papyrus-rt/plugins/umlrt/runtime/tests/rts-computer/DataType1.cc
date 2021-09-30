
#include "DataType1.hh"

#include "umlrtobjectclass.hh"
#include "umlrtobjectclassgeneric.hh"
#include <cstddef>

static const UMLRTObject_field sst1fields[] =
{
    {
        "name",
        &UMLRTType_char,
        offsetof( SubStructType1, name ),
        8,
        0
    },
    {
        "character",
        &UMLRTType_char,
        offsetof( SubStructType1, character),
        1,
        0
    },
    {
        "integer",
        &UMLRTType_int,
        offsetof( SubStructType1, integer ),
        1,
        0
    },
    {
        "character2",
        &UMLRTType_char,
        offsetof( SubStructType1, character2),
        1,
        0
    },
};

const UMLRTObject_class RTType_SubStruct1 =
{
    UMLRTObjectInitialize<SubStructType1>,
    UMLRTObjectCopy<SubStructType1>,
    UMLRTObject_decode,
    UMLRTObject_encode,
    UMLRTObjectDestroy<SubStructType1>,
    UMLRTObject_fprintf,
    "SubStructType1",
    NULL,
    {sizeof(SubStructType1), 4, sst1fields},
    UMLRTOBJECTCLASS_DEFAULT_VERSION,
    UMLRTOBJECTCLASS_DEFAULT_BACKWARDS,
};

static const UMLRTObject_field dt1fields[] =
{
    {
        "field1_int",
        &UMLRTType_int,
        offsetof( DataType1, field1_int ),
        2,
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
        "field4_sst1",
        &RTType_SubStruct1,
        offsetof( DataType1, field4_sst1 ),
        3,
        0
    },

};
const UMLRTObject_class RTType_DataType1 =
{
    UMLRTObjectInitialize<DataType1>,
    UMLRTObjectCopy<DataType1>,
    UMLRTObject_decode,
    UMLRTObject_encode,
    UMLRTObjectDestroy<DataType1>,
    UMLRTObject_fprintf,
    "DataType1",
    NULL,
    {sizeof(DataType1), 4, dt1fields},
    UMLRTOBJECTCLASS_DEFAULT_VERSION,
    UMLRTOBJECTCLASS_DEFAULT_BACKWARDS,
};
