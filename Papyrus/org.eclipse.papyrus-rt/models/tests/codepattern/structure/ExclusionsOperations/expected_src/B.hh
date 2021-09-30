
#ifndef B_HH
#define B_HH

#include "A.hh"
#include "C.hh"
#include "D.hh"
#include "umlrtcapsuleclass.hh"
#include "umlrtmessage.hh"
struct UMLRTCommsPort;
struct UMLRTSlot;

class Capsule_B : public Capsule_A
{
public:
    Capsule_B( const UMLRTCapsuleClass * cd, UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat );
    enum PartId
    {
    };
    virtual void bindPort( bool isBorder, int portId, int index );
    virtual void unbindPort( bool isBorder, int portId, int index );
    D op2( char * x, C y );
    C op3( char * x, D y );
    C op4( char * x, D y );
    virtual void initialize( const UMLRTMessage & msg );
    virtual void inject( const UMLRTMessage & msg );
};
extern const UMLRTCapsuleClass B;

#endif

