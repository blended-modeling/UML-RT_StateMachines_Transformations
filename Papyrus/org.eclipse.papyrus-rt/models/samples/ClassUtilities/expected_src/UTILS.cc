
#include "UTILS.hh"

#include "umlrtobjectclass.hh"
#include "umlrtobjectclassgeneric.hh"
#include <cstddef>


bool UTILS::debugStatus;
UtilsPrintKinds UTILS::printTimeKind;
const UMLRTObject_field UTILS::fields[] = 
{
};
UTILS::UTILS()
{
}



bool UTILS::isDebug()
{
    // the following code has been generated
    /* UMLRT-CODEGEN:platform:/resource/ClassUtility/ClassUtility.uml#_okReAKfvEeWQ0-isBiruBg */
    // generated code ends
    return(debugStatus);
}

bool UTILS::setDebug( bool dbg )
{
    // the following code has been generated
    /* UMLRT-CODEGEN:platform:/resource/ClassUtility/ClassUtility.uml#_yt2goKfwEeWQ0-isBiruBg */
    // generated code ends
    debugStatus = dbg;

    /**
     * This function will always return true as it can not fail.
     */
    return true;
}

bool UTILS::setPrintType( UtilsPrintKinds printType )
{
    // the following code has been generated
    /* UMLRT-CODEGEN:platform:/resource/ClassUtility/ClassUtility.uml#_4Vx9AKfxEeWQ0-isBiruBg */
    // generated code ends
    printTimeKind = printType;

    /**
     * This function will always return true as it can not fail.
     */
    return true;
}

UtilsPrintKinds UTILS::getPrintKind()
{
    // the following code has been generated
    /* UMLRT-CODEGEN:platform:/resource/ClassUtility/ClassUtility.uml#_DiEPQKfyEeWQ0-isBiruBg */
    // generated code ends
    return( printTimeKind );
}



static const UMLRTObject_class desc = 
{
    UMLRTObjectInitialize<UTILS>,
    UMLRTObjectCopy<UTILS>,
    UMLRTObject_decode,
    UMLRTObject_encode,
    UMLRTObjectDestroy<UTILS>,
    UMLRTObject_fprintf,
    "UTILS",
    NULL,
    {
        sizeof( UTILS ),
        0,
        UTILS::fields
    },
    UMLRTOBJECTCLASS_DEFAULT_VERSION,
    UMLRTOBJECTCLASS_DEFAULT_BACKWARDS
};

const UMLRTObject_class * const UMLRTType_UTILS = &desc;

