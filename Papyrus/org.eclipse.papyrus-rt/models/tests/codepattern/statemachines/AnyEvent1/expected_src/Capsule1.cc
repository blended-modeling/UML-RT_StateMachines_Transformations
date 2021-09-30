
#include "Capsule1.hh"

#include "Protocol1.hh"
#include "umlrtcommsportrole.hh"
#include "umlrtmessage.hh"
#include "umlrtslot.hh"
#include <cstddef>
#include "umlrtcapsuleclass.hh"
#include "umlrtframeservice.hh"
class UMLRTRtsInterface;
struct UMLRTCommsPort;

Capsule_Capsule1::Capsule_Capsule1( const UMLRTCapsuleClass * cd, UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat )
: UMLRTCapsule( NULL, cd, st, border, internal, isStat )
, port1( borderPorts[borderport_port1] )
, currentState( SPECIAL_INTERNAL_STATE_UNVISITED )
{
    stateNames[State1] = "State1";
    stateNames[State2] = "State2";
    stateNames[State3] = "State3";
    stateNames[SPECIAL_INTERNAL_STATE_TOP] = "<top>";
    stateNames[SPECIAL_INTERNAL_STATE_UNVISITED] = "<uninitialized>";
}







void Capsule_Capsule1::bindPort( bool isBorder, int portId, int index )
{
    if( isBorder )
        switch( portId )
        {
        case borderport_port1:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_port1, index, true );
            break;
        }
}

void Capsule_Capsule1::unbindPort( bool isBorder, int portId, int index )
{
    if( isBorder )
        switch( portId )
        {
        case borderport_port1:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_port1, index, false );
            UMLRTFrameService::disconnectPort( borderPorts[borderport_port1], index );
            break;
        }
}

void Capsule_Capsule1::inject( const UMLRTMessage & message )
{
    msg = &message;
    switch( currentState )
    {
    case State1:
        currentState = state_____State1( &message );
        break;
    case State2:
        currentState = state_____State2( &message );
        break;
    case State3:
        currentState = state_____State3( &message );
        break;
    default:
        break;
    }
}

void Capsule_Capsule1::initialize( const UMLRTMessage & message )
{
    msg = &message;
    actionchain_____t0( &message );
    currentState = State1;
}

const char * Capsule_Capsule1::getCurrentStateString() const
{
    return stateNames[currentState];
}




void Capsule_Capsule1::update_state( Capsule_Capsule1::State newState )
{
    currentState = newState;
}

void Capsule_Capsule1::entryaction_____State1( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/AnyEvent1/AnyEvent1.uml AnyEvent1::Capsule1::State1 entry  */
    log.log(LFCAPINST_STATE, "entry");
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_Capsule1::entryaction_____State2( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/AnyEvent1/AnyEvent1.uml AnyEvent1::Capsule1::State2 entry  */
    log.log(LFCAPINST_STATE, "entry");
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_Capsule1::entryaction_____State3( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/AnyEvent1/AnyEvent1.uml AnyEvent1::Capsule1::State3 entry  */
    log.log(LFCAPINST_STATE, "entry");
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_Capsule1::transitionaction_____t1( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/AnyEvent1/AnyEvent1.uml AnyEvent1::Capsule1 transition State1,State2,msg1:port1 */
    log.log(LFCAPINST_STATE, "t1");
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_Capsule1::transitionaction_____t2( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/AnyEvent1/AnyEvent1.uml AnyEvent1::Capsule1 transition State1,State3,*:port1 */
    log.log(LFCAPINST_STATE, "t2");
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_Capsule1::transitionaction_____t3( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/AnyEvent1/AnyEvent1.uml AnyEvent1::Capsule1 transition State2,State1,step:port1 */
    log.log(LFCAPINST_STATE, "t3");
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_Capsule1::transitionaction_____t4( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/AnyEvent1/AnyEvent1.uml AnyEvent1::Capsule1 transition State3,State1,step:port1 */
    log.log(LFCAPINST_STATE, "t4");
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_Capsule1::actionchain_____t0( const UMLRTMessage * msg )
{
    update_state( State1 );
    entryaction_____State1( msg );
}

void Capsule_Capsule1::actionchain_____t1( const UMLRTMessage * msg )
{
    update_state( SPECIAL_INTERNAL_STATE_TOP );
    transitionaction_____t1( msg );
    update_state( State2 );
    entryaction_____State2( msg );
}

void Capsule_Capsule1::actionchain_____t2( const UMLRTMessage * msg )
{
    update_state( SPECIAL_INTERNAL_STATE_TOP );
    transitionaction_____t2( msg );
    update_state( State3 );
    entryaction_____State3( msg );
}

void Capsule_Capsule1::actionchain_____t3( const UMLRTMessage * msg )
{
    update_state( SPECIAL_INTERNAL_STATE_TOP );
    transitionaction_____t3( msg );
    update_state( State1 );
    entryaction_____State1( msg );
}

void Capsule_Capsule1::actionchain_____t4( const UMLRTMessage * msg )
{
    update_state( SPECIAL_INTERNAL_STATE_TOP );
    transitionaction_____t4( msg );
    update_state( State1 );
    entryaction_____State1( msg );
}

Capsule_Capsule1::State Capsule_Capsule1::state_____State1( const UMLRTMessage * msg )
{
    switch( msg->destPort->role()->id )
    {
    case port_port1:
        switch( msg->getSignalId() )
        {
        case Protocol1::signal_msg1:
            actionchain_____t1( msg );
            return State2;
        default:
            actionchain_____t2( msg );
            return State3;
        }
        return currentState;
    default:
        this->unexpectedMessage();
        break;
    }
    return currentState;
}

Capsule_Capsule1::State Capsule_Capsule1::state_____State2( const UMLRTMessage * msg )
{
    switch( msg->destPort->role()->id )
    {
    case port_port1:
        switch( msg->getSignalId() )
        {
        case Protocol1::signal_step:
            actionchain_____t3( msg );
            return State1;
        default:
            this->unexpectedMessage();
            break;
        }
        return currentState;
    default:
        this->unexpectedMessage();
        break;
    }
    return currentState;
}

Capsule_Capsule1::State Capsule_Capsule1::state_____State3( const UMLRTMessage * msg )
{
    switch( msg->destPort->role()->id )
    {
    case port_port1:
        switch( msg->getSignalId() )
        {
        case Protocol1::signal_step:
            actionchain_____t4( msg );
            return State1;
        default:
            this->unexpectedMessage();
            break;
        }
        return currentState;
    default:
        this->unexpectedMessage();
        break;
    }
    return currentState;
}


static const UMLRTCommsPortRole portroles_border[] = 
{
    {
        Capsule_Capsule1::port_port1,
        "Protocol1",
        "port1",
        "",
        1,
        true,
        false,
        false,
        false,
        false,
        false,
        true
    }
};

static const UMLRTCommsPortRole portroles_internal[] = 
{
    {
        Capsule_Capsule1::port_log,
        "Log",
        "log",
        "",
        0,
        false,
        false,
        false,
        false,
        true,
        false,
        false
    }
};

static void instantiate_Capsule1( const UMLRTRtsInterface * rts, UMLRTSlot * slot, const UMLRTCommsPort * * borderPorts )
{
    const UMLRTCommsPort * * internalPorts = UMLRTFrameService::createInternalPorts( slot, &Capsule1 );
    slot->capsule = new Capsule_Capsule1( &Capsule1, slot, borderPorts, internalPorts, false );
}

const UMLRTCapsuleClass Capsule1 = 
{
    "Capsule1",
    NULL,
    instantiate_Capsule1,
    0,
    NULL,
    1,
    portroles_border,
    1,
    portroles_internal
};

