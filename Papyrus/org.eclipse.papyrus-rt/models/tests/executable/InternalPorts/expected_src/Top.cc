
#include "Top.hh"

#include "Capsule1.hh"
#include "Protocol1.hh"
#include "umlrtcapsuleclass.hh"
#include "umlrtcapsulepart.hh"
#include "umlrtcommsportrole.hh"
#include "umlrtmessage.hh"
#include "umlrtslot.hh"
#include <cstddef>
#include "umlrtcapsulerole.hh"
#include "umlrtcommsport.hh"
#include "umlrtframeservice.hh"
class UMLRTRtsInterface;

Capsule_Top::Capsule_Top( const UMLRTCapsuleClass * cd, UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat )
: UMLRTCapsule( NULL, cd, st, border, internal, isStat )
, protocol1( internalPorts[internalport_protocol1] )
, capsule1( &slot->parts[part_capsule1] )
, currentState( SPECIAL_INTERNAL_STATE_UNVISITED )
{
    stateNames[State1] = "State1";
    stateNames[State2] = "State2";
    stateNames[State3] = "State3";
    stateNames[SPECIAL_INTERNAL_STATE_TOP] = "<top>";
    stateNames[SPECIAL_INTERNAL_STATE_UNVISITED] = "<uninitialized>";
}







void Capsule_Top::bindPort( bool isBorder, int portId, int index )
{
    if( isBorder )
        switch( portId )
        {
        }
    else
        UMLRTFrameService::sendBoundUnbound( internalPorts, internalport_protocol1, index, true );
}

void Capsule_Top::unbindPort( bool isBorder, int portId, int index )
{
    if( isBorder )
        switch( portId )
        {
        }
    else
    {
        UMLRTFrameService::sendBoundUnbound( internalPorts, internalport_protocol1, index, false );
        UMLRTFrameService::disconnectPort( internalPorts[internalport_protocol1], index );
    }
}

void Capsule_Top::inject( const UMLRTMessage & message )
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

void Capsule_Top::initialize( const UMLRTMessage & message )
{
    msg = &message;
    actionchain_____Initial( &message );
    currentState = State1;
}

const char * Capsule_Top::getCurrentStateString() const
{
    return stateNames[currentState];
}




void Capsule_Top::update_state( Capsule_Top::State newState )
{
    currentState = newState;
}

void Capsule_Top::entryaction_____State1( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/InternalPorts/InternalPorts.uml RootElement::Top::State1 entry  */
    log.log("[Top](s:State1) waiting for capsule1 to be ready");
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_Top::entryaction_____State2( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/InternalPorts/InternalPorts.uml RootElement::Top::State2 entry  */
    log.log("[Top](s:State2) received 'ready' from capsule1; sending 'start' to it");
    protocol1.start().send();
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_Top::entryaction_____State3( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/InternalPorts/InternalPorts.uml RootElement::Top::State3 entry  */
    log.log("[Top](s:State3) received 'done' from capsule1; exiting");
    exit(0);
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_Top::actionchain_____Initial( const UMLRTMessage * msg )
{
    update_state( State1 );
    entryaction_____State1( msg );
}

void Capsule_Top::actionchain_____done( const UMLRTMessage * msg )
{
    update_state( SPECIAL_INTERNAL_STATE_TOP );
    update_state( State3 );
    entryaction_____State3( msg );
}

void Capsule_Top::actionchain_____ready( const UMLRTMessage * msg )
{
    update_state( SPECIAL_INTERNAL_STATE_TOP );
    update_state( State2 );
    entryaction_____State2( msg );
}

Capsule_Top::State Capsule_Top::state_____State1( const UMLRTMessage * msg )
{
    switch( msg->destPort->role()->id )
    {
    case port_protocol1:
        switch( msg->getSignalId() )
        {
        case Protocol1::signal_ready:
            actionchain_____ready( msg );
            return State2;
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

Capsule_Top::State Capsule_Top::state_____State2( const UMLRTMessage * msg )
{
    switch( msg->destPort->role()->id )
    {
    case port_protocol1:
        switch( msg->getSignalId() )
        {
        case Protocol1::signal_done:
            actionchain_____done( msg );
            return State3;
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

Capsule_Top::State Capsule_Top::state_____State3( const UMLRTMessage * msg )
{
    switch( msg->destPort->role()->id )
    {
    default:
        this->unexpectedMessage();
        break;
    }
    return currentState;
}


static const UMLRTCapsuleRole roles[] = 
{
    {
        "capsule1",
        &Capsule1,
        1,
        1,
        false,
        false
    }
};

static const UMLRTCommsPortRole portroles_internal[] = 
{
    {
        Capsule_Top::port_protocol1,
        "Protocol1",
        "protocol1",
        "",
        1,
        true,
        false,
        false,
        false,
        false,
        false,
        true
    },
    {
        Capsule_Top::port_log,
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

static void instantiate_Top( const UMLRTRtsInterface * rts, UMLRTSlot * slot, const UMLRTCommsPort * * borderPorts )
{
    const UMLRTCommsPort * * internalPorts = UMLRTFrameService::createInternalPorts( slot, &Top );
    UMLRTFrameService::connectPorts( internalPorts[Capsule_Top::internalport_protocol1], 0, &slot->parts[Capsule_Top::part_capsule1].slots[0]->ports[Capsule_Capsule1::borderport_protocol1], 0 );
    Capsule1.instantiate( NULL, slot->parts[Capsule_Top::part_capsule1].slots[0], UMLRTFrameService::createBorderPorts( slot->parts[Capsule_Top::part_capsule1].slots[0], Capsule1.numPortRolesBorder ) );
    slot->capsule = new Capsule_Top( &Top, slot, borderPorts, internalPorts, false );
}

const UMLRTCapsuleClass Top = 
{
    "Top",
    NULL,
    instantiate_Top,
    1,
    roles,
    0,
    NULL,
    2,
    portroles_internal
};

