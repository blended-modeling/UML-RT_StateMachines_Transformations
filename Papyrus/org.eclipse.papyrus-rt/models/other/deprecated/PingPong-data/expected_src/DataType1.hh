
#ifndef DATATYPE1_HH
#define DATATYPE1_HH

#include "Enumeration1.hh"
#include "umlrtobjectclass.hh"

struct DataType1
{
    Enumeration1 field0_enum;
    int field1_int;
    bool field2_bool;
    double field3_double;
    int * field4_ptr;
    float field5_freeform;
    char * field6_string;
    static const UMLRTObject_field fields[];
};
extern const UMLRTObject_class UMLRTType_DataType1;

#endif

