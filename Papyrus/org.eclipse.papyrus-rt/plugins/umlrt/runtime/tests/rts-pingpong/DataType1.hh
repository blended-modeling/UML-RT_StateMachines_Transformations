
#ifndef DATATYPE1_HH
#define DATATYPE1_HH

struct UMLRTObject_class;

struct SubStructType1
{
    char name[8];
    char character; // Force alignment to insert padding.
    int integer;
    char character2; // Force alignment to insert padding.
};

struct DataType1
{
    int field1_int[2];
    bool field2_bool;
    double field3_double;
    SubStructType1 field4_sst1[3];
};
extern const UMLRTObject_class RTType_SubStructType1;
extern const UMLRTObject_class RTType_DataType1;

#endif

