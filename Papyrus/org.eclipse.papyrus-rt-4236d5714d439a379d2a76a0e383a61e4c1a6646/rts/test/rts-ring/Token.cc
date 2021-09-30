
#include "Token.hh"

#include "umlrtobjectclass.hh"
#include <cstddef>

unsigned int Token::Label( 0 );



void Token::write( char * id )
{
}

static const UMLRTObject_field fields[] = 
{
    {
        "data",
        UMLRTType_uint,
        offsetof( Token, data ),
        sizeof( unsigned int ),
        1,
        0
    },
    {
        "label",
        UMLRTType_uint,
        offsetof( Token, label ),
        sizeof( unsigned int ),
        1,
        0
    }
};
static const UMLRTObject_class desc = 
{
    "Token",
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
    sizeof( Token ),
    2,
    fields
};
const UMLRTObject_class * const UMLRTType_Token = &desc;
