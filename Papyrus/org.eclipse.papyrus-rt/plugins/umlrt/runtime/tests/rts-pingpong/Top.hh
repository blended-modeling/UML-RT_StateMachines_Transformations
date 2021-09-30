
#ifndef TOP_HH
#define TOP_HH

#include "umlrtcapsule.hh"
#include "umlrtcapsuleclass.hh"
struct UMLRTCapsulePart;
struct UMLRTCommsPort;
struct UMLRTSlot;
class UMLRTMessage;

class Capsule_Top : public UMLRTCapsule
{
public:
    Capsule_Top( const UMLRTCapsuleClass * cd, UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat );
    enum PartId
    {
        part_pinger,
        part_ponger
    };
protected:
    const UMLRTCapsulePart * pinger() const;
    const UMLRTCapsulePart * ponger() const;
public:
    virtual void bindPort( bool isBorder, int portId, int index );
    virtual void unbindPort( bool isBorder, int portId, int index );
    virtual void inject( const UMLRTMessage & message );
    virtual void initialize( const UMLRTMessage & message );
    const char * getCurrentStateString() const;
private:
    enum State
    {
        top__State1,
        SPECIAL_INTERNAL_STATE_UNVISITED
    };
    const char * stateNames[1];
    State currentState;
    void transitionaction_____top__Transition2__ActionChain3__onInit( const UMLRTMessage & msg );
    void actionchain_____top__Transition2__ActionChain3( const UMLRTMessage & msg );
    State state_____top__State1( const UMLRTMessage & msg );
};
extern const UMLRTCapsuleClass Top;

#endif

