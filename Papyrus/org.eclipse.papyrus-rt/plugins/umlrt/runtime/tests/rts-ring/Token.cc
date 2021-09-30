
#include "Token.hh"

#include "umlrtobjectclass.hh"
#include "umlrtobjectclassgeneric.hh"
#include <cstddef>

unsigned int Token::Label( 0 );



void Token::write( char id )
{
}


static const UMLRTObject_field fields[] = 
{
    {
        "data",
        &UMLRTType_uint,
        offsetof( Token, data ),
        1,
        0
    },
    {
        "label",
        &UMLRTType_uint,
        offsetof( Token, label ),
        1,
        0
    }
};

const UMLRTObject_class UMLRTType_Token =
{
    UMLRTObjectInitialize<Token>,
    UMLRTObjectCopy<Token>,
    UMLRTObject_decode,
    UMLRTObject_encode,
    UMLRTObjectDestroy<Token>,
    UMLRTObject_fprintf,
    "Token",
    NULL,
    { sizeof(Token), 2, fields },
    UMLRTOBJECTCLASS_DEFAULT_VERSION,
    UMLRTOBJECTCLASS_DEFAULT_BACKWARDS,
};

