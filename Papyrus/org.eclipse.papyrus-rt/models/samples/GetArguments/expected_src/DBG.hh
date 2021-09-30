
#ifndef DBG_HH
#define DBG_HH

#include "debugPrintKinds.hh"
#include "umlrtobjectclass.hh"

#include "debugPrintKinds.hh"

class DBG
{
public:
    DBG();
    ~DBG();
protected:
    bool debugStatus;
    debugPrintKinds printTimeKind;
public:
    bool isDebug();
    void setDebug( bool dbg );
    void setPrintType( debugPrintKinds printType );
    debugPrintKinds getPrintKind();
    static const UMLRTObject_field fields[];
};
extern const UMLRTObject_class * const UMLRTType_DBG;
extern DBG dbg;


#endif

