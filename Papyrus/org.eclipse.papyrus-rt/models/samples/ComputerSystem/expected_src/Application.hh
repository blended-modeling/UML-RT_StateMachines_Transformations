
#ifndef APPLICATION_HH
#define APPLICATION_HH

#include "AppType.hh"
#include "umlrtcapsule.hh"
#include "umlrtcapsuleclass.hh"
#include "umlrtmessage.hh"
struct UMLRTCommsPort;
struct UMLRTSlot;

#include "ResourceType.hh"

#include <iostream>
#include <iomanip>
#include <ctime>
#include "AppType.hh"

class Capsule_Application : public UMLRTCapsule
{
public:
    Capsule_Application( const UMLRTCapsuleClass * cd, UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat );
    enum PartId
    {
    };
    virtual void bindPort( bool isBorder, int portId, int index );
    virtual void unbindPort( bool isBorder, int portId, int index );
protected:
    AppType applicationType;
public:
    virtual void initialize( const UMLRTMessage & msg );
    virtual void inject( const UMLRTMessage & msg );
};
extern const UMLRTCapsuleClass Application;

#endif

