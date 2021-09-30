
#ifndef CAPSULE1_HH
#define CAPSULE1_HH

#include "Protocol1.hh"
#include "Protocol10.hh"
#include "Protocol2.hh"
#include "Protocol3.hh"
#include "Protocol4.hh"
#include "Protocol5.hh"
#include "Protocol6.hh"
#include "Protocol7.hh"
#include "Protocol8.hh"
#include "Protocol9.hh"
#include "umlrtcapsule.hh"
#include "umlrtcapsuleclass.hh"
#include "umlrtmessage.hh"
struct UMLRTCommsPort;
struct UMLRTSlot;

class Capsule_Capsule1 : public UMLRTCapsule
{
public:
    Capsule_Capsule1( const UMLRTCapsuleClass * cd, UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat );
    enum BorderPortId
    {
        borderport_p1a,
        borderport_p10a,
        borderport_p10b,
        borderport_p1b,
        borderport_p2a,
        borderport_p2b,
        borderport_p3a,
        borderport_p3b,
        borderport_p4a,
        borderport_p4b,
        borderport_p5a,
        borderport_p5b,
        borderport_p6a,
        borderport_p6b,
        borderport_p7a,
        borderport_p7b,
        borderport_p8a,
        borderport_p8b,
        borderport_p9a,
        borderport_p9b
    };
protected:
    Protocol10::Conj p10a;
    Protocol10::Base p10b;
    Protocol1::Conj p1a;
    Protocol1::Base p1b;
    Protocol2::Conj p2a;
    Protocol2::Base p2b;
    Protocol3::Conj p3a;
    Protocol3::Base p3b;
    Protocol4::Conj p4a;
    Protocol4::Base p4b;
    Protocol5::Conj p5a;
    Protocol5::Base p5b;
    Protocol6::Conj p6a;
    Protocol6::Base p6b;
    Protocol7::Conj p7a;
    Protocol7::Base p7b;
    Protocol8::Conj p8a;
    Protocol8::Base p8b;
    Protocol9::Conj p9a;
    Protocol9::Base p9b;
public:
    enum PartId
    {
    };
    enum PortId
    {
        port_p10a,
        port_p10b,
        port_p1a,
        port_p1b,
        port_p2a,
        port_p2b,
        port_p3a,
        port_p3b,
        port_p4a,
        port_p4b,
        port_p5a,
        port_p5b,
        port_p6a,
        port_p6b,
        port_p7a,
        port_p7b,
        port_p8a,
        port_p8b,
        port_p9a,
        port_p9b
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
        State4,
        State5,
        State6,
        State7,
        State8,
        State9,
        SPECIAL_INTERNAL_STATE_TOP,
        SPECIAL_INTERNAL_STATE_UNVISITED
    };
    const char * stateNames[11];
    State currentState;
    void update_state( State newState );
    void actionchain_____Initial( const UMLRTMessage * msg );
    void actionchain_____p1b_pr1_in_m1( const UMLRTMessage * msg );
    void actionchain_____p2a_pr2_out_m1( const UMLRTMessage * msg );
    void actionchain_____p2b_any( const UMLRTMessage * msg );
    void actionchain_____p3a_pr3_inout_m1( const UMLRTMessage * msg );
    void actionchain_____p3b_pr3_inout_m1( const UMLRTMessage * msg );
    void actionchain_____p4a_pr4_out_m1_inout_m1( const UMLRTMessage * msg );
    void actionchain_____p4b_pr4_in_m1_inout_m1( const UMLRTMessage * msg );
    void actionchain_____pa1_any( const UMLRTMessage * msg );
    State state_____State1( const UMLRTMessage * msg );
    State state_____State2( const UMLRTMessage * msg );
    State state_____State3( const UMLRTMessage * msg );
    State state_____State4( const UMLRTMessage * msg );
    State state_____State5( const UMLRTMessage * msg );
    State state_____State6( const UMLRTMessage * msg );
    State state_____State7( const UMLRTMessage * msg );
    State state_____State8( const UMLRTMessage * msg );
    State state_____State9( const UMLRTMessage * msg );
};
extern const UMLRTCapsuleClass Capsule1;

#endif

