
#ifndef TOP_HH
#define TOP_HH

#include "umlrtcapsule.hh"
#include "umlrtcapsuleclass.hh"
#include "umlrtframeprotocol.hh"
#include "umlrtinmessage.hh"
struct UMLRTCapsulePart;
struct UMLRTCommsPort;
struct UMLRTSlot;

class Capsule_Top : public UMLRTCapsule
{
public:
    Capsule_Top( UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * internal, bool isStat );
    enum BorderPortId
    {
        borderport_FramePort
    };
protected:
    UMLRTFrameProtocol_baserole FramePort() const;
public:
    enum PartId
    {
        part_pinger,
        part_ponger
    };
protected:
    const UMLRTCapsulePart * pinger() const;
    const UMLRTCapsulePart * ponger() const;
public:
    enum PortId
    {
        port_FramePort
    };
    virtual void bindPort( bool isBorder, int portId, int index );
    virtual void unbindPort( bool isBorder, int portId, int index );
    virtual void inject( const UMLRTInMessage & msg );
    virtual void initialize( const UMLRTInMessage & msg );
private:
    enum State
    {
        top__State1,
        SPECIAL_INTERNAL_STATE_UNVISITED
    };
    State currentState;
    void transitionaction_____top__Transition2__ActionChain3__onInit( const UMLRTInMessage & msg );
    void actionchain_____top__Transition2__ActionChain3( const UMLRTInMessage & msg );
    State state_____top__State1( const UMLRTInMessage & msg );
};
extern const UMLRTCapsuleClass Top;

#endif

