
#include "DBG.hh"

#include "umlrtobjectclass.hh"
#include "umlrtobjectclassgeneric.hh"
#include <cstddef>


const UMLRTObject_field DBG::fields[] = 
{
    {
        "debugStatus",
        UMLRTType_bool,
        offsetof( DBG, debugStatus ),
        1,
        0
    },
    {
        "printTimeKind",
        UMLRTType_int,
        offsetof( DBG, printTimeKind ),
        1,
        0
    }
};
DBG::DBG()
{
}

DBG::~DBG()
{
}



bool DBG::isDebug()
{
    // the following code has been generated
    /* UMLRT-CODEGEN:platform:/resource/GetArguments/GetArguments.uml#_XQSjB6gWEeWGg7h3jLnwpQ */
    // generated code ends
    return( debugStatus );
}

void DBG::setDebug( bool dbg )
{
    // the following code has been generated
    /* UMLRT-CODEGEN:platform:/resource/GetArguments/GetArguments.uml#_XQSjCagWEeWGg7h3jLnwpQ */
    // generated code ends
    debugStatus = dbg;
}

void DBG::setPrintType( debugPrintKinds printType )
{
    // the following code has been generated
    /* UMLRT-CODEGEN:platform:/resource/GetArguments/GetArguments.uml#_XQSjC6gWEeWGg7h3jLnwpQ */
    // generated code ends
    printTimeKind = printType;
}

debugPrintKinds DBG::getPrintKind()
{
    // the following code has been generated
    /* UMLRT-CODEGEN:platform:/resource/GetArguments/GetArguments.uml#_XQSjDagWEeWGg7h3jLnwpQ */
    // generated code ends
    return( printTimeKind );
}



static const UMLRTObject_class desc = 
{
    UMLRTObjectInitialize<DBG>,
    UMLRTObjectCopy<DBG>,
    UMLRTObject_decode,
    UMLRTObject_encode,
    UMLRTObjectDestroy<DBG>,
    UMLRTObject_fprintf,
    "DBG",
    NULL,
    {
        sizeof( DBG ),
        2,
        DBG::fields
    },
    UMLRTOBJECTCLASS_DEFAULT_VERSION,
    UMLRTOBJECTCLASS_DEFAULT_BACKWARDS
};

const UMLRTObject_class * const UMLRTType_DBG = &desc;

DBG dbg;

