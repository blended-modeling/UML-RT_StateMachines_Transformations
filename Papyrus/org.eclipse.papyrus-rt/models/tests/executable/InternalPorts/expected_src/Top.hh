
#ifndef TOP_HH
#define TOP_HH

#include "Protocol1.hh"
#include "umlrtcapsule.hh"
#include "umlrtcapsuleclass.hh"
#include "umlrtlogprotocol.hh"
#include "umlrtmessage.hh"
struct UMLRTCapsulePart;
struct UMLRTCommsPort;
struct UMLRTSlot;

class Capsule_Top : public UMLRTCapsule
{
public:
    Capsule_Top( const UMLRTCapsuleClass * cd, UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat );
protected:
    UMLRTLogProtocol_baserole log;
    Protocol1::Base protocol1;
public:
    enum InternalPortId
    {
        internalport_protocol1,
        internalport_log
    };
    enum PartId
    {
        part_capsule1
    };
protected:
    const UMLRTCapsulePart * const capsule1;
public:
    enum PortId
    {
        port_log,
        port_protocol1
    };
    virtual void bindPort( bool isBorder, int portId, int index );
    virtual void unbindPort( bool isBorder, int portId, int index );
    virtual void inject( const UMLRTMessage & message );
    virtual void initialize( const UMLRTMessage & message );
    const char * getCurrentStateString() const;
private:
    enum State
    {
        State1,
        State2,
        State3,
        SPECIAL_INTERNAL_STATE_TOP,
        SPECIAL_INTERNAL_STATE_UNVISITED
    };
    const char * stateNames[5];
    State currentState;
    void update_state( State newState );
    void entryaction_____State1( const UMLRTMessage * msg );
    void entryaction_____State2( const UMLRTMessage * msg );
    void entryaction_____State3( const UMLRTMessage * msg );
    void actionchain_____Initial( const UMLRTMessage * msg );
    void actionchain_____done( const UMLRTMessage * msg );
    void actionchain_____ready( const UMLRTMessage * msg );
    State state_____State1( const UMLRTMessage * msg );
    State state_____State2( const UMLRTMessage * msg );
    State state_____State3( const UMLRTMessage * msg );
};
extern const UMLRTCapsuleClass Top;

#endif

