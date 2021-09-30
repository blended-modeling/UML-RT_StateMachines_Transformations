
#ifndef CAPSULE_HH
#define CAPSULE_HH

#include "MessageProtocol.hh"
#include "umlrtcapsule.hh"
#include "umlrtcapsuleclass.hh"
#include "umlrtmessage.hh"
struct UMLRTCommsPort;
struct UMLRTSlot;

class Capsule_Capsule : public UMLRTCapsule
{
public:
    Capsule_Capsule( const UMLRTCapsuleClass * cd, UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat );
protected:
    MessageProtocol::Base left;
public:
    enum BorderPortId
    {
        borderport_left,
        borderport_right
    };
protected:
    MessageProtocol::Base right;
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
private:
    unsigned int limit;
public:
    virtual void inject( const UMLRTMessage & message );
    virtual void initialize( const UMLRTMessage & message );
    const char * getCurrentStateString() const;
private:
    enum State
    {
        top__Running,
        SPECIAL_INTERNAL_STATE_UNVISITED
    };
    const char * stateNames[1];
    State currentState;
    void transitionaction_____top__fromLeft__ActionChain4__toRight( const UMLRTMessage & msg );
    void transitionaction_____top__fromRight__ActionChain6__toLeft( const UMLRTMessage & msg );
    void transitionaction_____top__initial__ActionChain2__init( const UMLRTMessage & msg );
    void actionchain_____top__fromLeft__ActionChain4( const UMLRTMessage & msg );
    void actionchain_____top__fromRight__ActionChain6( const UMLRTMessage & msg );
    void actionchain_____top__initial__ActionChain2( const UMLRTMessage & msg );
    State state_____top__Running( const UMLRTMessage & msg );
};
extern const UMLRTCapsuleClass Capsule;

#endif

