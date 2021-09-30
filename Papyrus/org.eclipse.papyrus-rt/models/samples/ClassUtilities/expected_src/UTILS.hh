
#ifndef UTILS_HH
#define UTILS_HH

#include "UtilsPrintKinds.hh"
#include "umlrtobjectclass.hh"

#include "UtilsPrintKinds.hh"

class UTILS
{
public:
    UTILS();
protected:
    static bool debugStatus;
    static UtilsPrintKinds printTimeKind;
public:
    bool isDebug();
    bool setDebug( bool dbg );
    bool setPrintType( UtilsPrintKinds printType );
    UtilsPrintKinds getPrintKind();
    static const UMLRTObject_field fields[];
};
extern const UMLRTObject_class * const UMLRTType_UTILS;

#endif

