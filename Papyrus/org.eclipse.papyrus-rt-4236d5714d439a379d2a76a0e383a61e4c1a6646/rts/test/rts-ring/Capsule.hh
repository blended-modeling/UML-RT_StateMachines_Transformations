
#ifndef CAPSULE_HH
#define CAPSULE_HH

#include "MessageProtocol.hh"
#include "umlrtcapsule.hh"
#include "umlrtcapsuleclass.hh"
#include "umlrtinmessage.hh"
struct UMLRTCommsPort;
struct UMLRTSlot;

class Capsule_Capsule : public UMLRTCapsule
{
public:
    Capsule_Capsule( const UMLRTCapsuleClass * cd, UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * internal, bool isStat );
    enum BorderPortId
    {
        borderport_right,
        borderport_left
    };
protected:
    MessageProtocol_baserole left() const;
    MessageProtocol_baserole right() const;
public:
    enum PartId
    {
    };
    enum PortId
    {
        port_left,
        port_right
    };
    virtual void bindPort( bool isBorder, int portId, int index );
    virtual void unbindPort( bool isBorder, int portId, int index );
    unsigned int limit;
    virtual void inject( const UMLRTInMessage & message );
    virtual void initialize( const UMLRTInMessage & message );
    const char * getCurrentStateString() const;
private:
    enum State
    {
        top__Running,
        SPECIAL_INTERNAL_STATE_UNVISITED
    };
    const char * stateNames[1];
    State currentState;
    void transitionaction_____top__fromLeft__ActionChain4__toRight( const UMLRTInMessage & msg );
    void transitionaction_____top__fromRight__ActionChain6__toLeft( const UMLRTInMessage & msg );
    void transitionaction_____top__initial__ActionChain2__init( const UMLRTInMessage & msg );
    void actionchain_____top__fromLeft__ActionChain4( const UMLRTInMessage & msg );
    void actionchain_____top__fromRight__ActionChain6( const UMLRTInMessage & msg );
    void actionchain_____top__initial__ActionChain2( const UMLRTInMessage & msg );
    State state_____top__Running( const UMLRTInMessage & msg );
};
extern const UMLRTCapsuleClass Capsule;

#endif

