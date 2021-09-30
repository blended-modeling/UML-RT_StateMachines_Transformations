
#include "Top.hh"

#include "Protocol1.hh"
#include "Protocol2.hh"
#include "umlrtcommsportrole.hh"
#include "umlrtmessage.hh"
#include "umlrtslot.hh"
#include <cstddef>
#include "umlrtcapsuleclass.hh"
#include "umlrtframeservice.hh"
class UMLRTRtsInterface;
struct UMLRTCommsPort;

Capsule_Top::Capsule_Top( const UMLRTCapsuleClass * cd, UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat )
: UMLRTCapsule( NULL, cd, st, border, internal, isStat )
, protocol1( borderPorts[borderport_protocol1] )
, protocol2( borderPorts[borderport_protocol2] )
, currentState( SPECIAL_INTERNAL_STATE_UNVISITED )
{
    stateNames[State1] = "State1";
    stateNames[State1__State1] = "State1__State1";
    stateNames[State1__State2] = "State1__State2";
    stateNames[State1__boundary] = "State1__boundary";
    stateNames[State2] = "State2";
    stateNames[State3] = "State3";
    stateNames[State4] = "State4";
    stateNames[SPECIAL_INTERNAL_STATE_TOP] = "<top>";
    stateNames[SPECIAL_INTERNAL_STATE_UNVISITED] = "<uninitialized>";
    int i = 0;
    while( i < 1 )
        history[i++] = SPECIAL_INTERNAL_STATE_UNVISITED;
}






void Capsule_Top::bindPort( bool isBorder, int portId, int index )
{
    if( isBorder )
        switch( portId )
        {
        case borderport_protocol1:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_protocol1, index, true );
            break;
        case borderport_protocol2:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_protocol2, index, true );
            break;
        }
}

void Capsule_Top::unbindPort( bool isBorder, int portId, int index )
{
    if( isBorder )
        switch( portId )
        {
        case borderport_protocol1:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_protocol1, index, false );
            UMLRTFrameService::disconnectPort( borderPorts[borderport_protocol1], index );
            break;
        case borderport_protocol2:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_protocol2, index, false );
            UMLRTFrameService::disconnectPort( borderPorts[borderport_protocol2], index );
            break;
        }
}

void Capsule_Top::inject( const UMLRTMessage & message )
{
    msg = &message;
    switch( currentState )
    {
    case State2:
        currentState = state_____State2( &message );
        break;
    case State3:
        currentState = state_____State3( &message );
        break;
    case State4:
        currentState = state_____State4( &message );
        break;
    case State1__State1:
        currentState = state_____State1__State1( &message );
        break;
    case State1__State2:
        currentState = state_____State1__State2( &message );
        break;
    case State1__boundary:
        currentState = state_____State1__boundary( &message );
        break;
    default:
        break;
    }
}

void Capsule_Top::initialize( const UMLRTMessage & message )
{
    msg = &message;
    actionchain_____Initial( &message );
    currentState = junction_____State1__connectionPoint0( &message );
}

const char * Capsule_Top::getCurrentStateString() const
{
    return stateNames[currentState];
}





void Capsule_Top::save_history( Capsule_Top::State compositeState, Capsule_Top::State subState )
{
    history[compositeState] = subState;
}

bool Capsule_Top::check_history( Capsule_Top::State compositeState, Capsule_Top::State subState )
{
    return history[compositeState] == subState;
}

void Capsule_Top::update_state( Capsule_Top::State newState )
{
    currentState = newState;
}

void Capsule_Top::actionchain_____Initial( const UMLRTMessage * msg )
{
    update_state( State1 );
}

void Capsule_Top::actionchain_____State1__new_transition_1( const UMLRTMessage * msg )
{
    update_state( State1__State1 );
}

void Capsule_Top::actionchain_____State1__new_transition_10( const UMLRTMessage * msg )
{
    update_state( State1 );
    save_history( State1, State1__boundary );
}

void Capsule_Top::actionchain_____State1__new_transition_2( const UMLRTMessage * msg )
{
    update_state( State1__State2 );
}

void Capsule_Top::actionchain_____State1__new_transition_3_to_unvisited_boundary( const UMLRTMessage * msg )
{
    update_state( State1__boundary );
}

void Capsule_Top::actionchain_____State1__new_transition_4_to_visited_boundary( const UMLRTMessage * msg )
{
    update_state( State1__boundary );
}

void Capsule_Top::actionchain_____State1__new_transition_5( const UMLRTMessage * msg )
{
    update_state( State1 );
    save_history( State1, State1__State1 );
}

void Capsule_Top::actionchain_____State1__new_transition_6( const UMLRTMessage * msg )
{
    update_state( State1 );
    save_history( State1, State1__State2 );
}

void Capsule_Top::actionchain_____State1__new_transition_7( const UMLRTMessage * msg )
{
    update_state( State1 );
    save_history( State1, State1__boundary );
}

void Capsule_Top::actionchain_____State1__new_transition_8( const UMLRTMessage * msg )
{
    update_state( State1 );
    save_history( State1, State1__State1 );
}

void Capsule_Top::actionchain_____State1__new_transition_9( const UMLRTMessage * msg )
{
    update_state( State1 );
    save_history( State1, State1__State2 );
}

void Capsule_Top::actionchain_____State1__transition0( const UMLRTMessage * msg )
{
    update_state( State1__State1 );
}

void Capsule_Top::actionchain_____State1__transition1( const UMLRTMessage * msg )
{
    update_state( State1 );
    update_state( State1__State2 );
}

void Capsule_Top::actionchain_____State1__transition2( const UMLRTMessage * msg )
{
    update_state( State1 );
    save_history( State1, State1__State1 );
}

void Capsule_Top::actionchain_____transition1( const UMLRTMessage * msg )
{
    update_state( SPECIAL_INTERNAL_STATE_TOP );
    update_state( State2 );
}

void Capsule_Top::actionchain_____transition2( const UMLRTMessage * msg )
{
    update_state( SPECIAL_INTERNAL_STATE_TOP );
    update_state( State3 );
}

void Capsule_Top::actionchain_____transition3( const UMLRTMessage * msg )
{
    update_state( SPECIAL_INTERNAL_STATE_TOP );
    update_state( State4 );
}

Capsule_Top::State Capsule_Top::junction_____State1__connectionPoint0( const UMLRTMessage * msg )
{
    actionchain_____State1__transition0( msg );
    return State1__State1;
}

Capsule_Top::State Capsule_Top::junction_____State1__exp1( const UMLRTMessage * msg )
{
    actionchain_____transition1( msg );
    return State2;
}

Capsule_Top::State Capsule_Top::junction_____State1__exp2( const UMLRTMessage * msg )
{
    actionchain_____transition2( msg );
    return State3;
}

Capsule_Top::State Capsule_Top::junction_____State1__exp3( const UMLRTMessage * msg )
{
    actionchain_____transition3( msg );
    return State4;
}

Capsule_Top::State Capsule_Top::choice_____State1__deephistory( const UMLRTMessage * msg )
{
    if( check_history( State1, State1__State1 ) )
    {
        actionchain_____State1__new_transition_1( msg );
        return State1__State1;
    }
    else if( check_history( State1, State1__State2 ) )
    {
        actionchain_____State1__new_transition_2( msg );
        return State1__State2;
    }
    else if( check_history( State1, SPECIAL_INTERNAL_STATE_UNVISITED ) )
    {
        actionchain_____State1__new_transition_3_to_unvisited_boundary( msg );
        return State1__boundary;
    }
    else if( check_history( State1, State1__boundary ) )
    {
        actionchain_____State1__new_transition_4_to_visited_boundary( msg );
        return State1__boundary;
    }
    return currentState;
}

Capsule_Top::State Capsule_Top::state_____State1__State1( const UMLRTMessage * msg )
{
    switch( msg->destPort->role()->id )
    {
    case port_protocol1:
        switch( msg->getSignalId() )
        {
        case Protocol1::signal_m1:
            actionchain_____State1__transition1( msg );
            return State1__State2;
        case Protocol1::signal_m2:
            actionchain_____State1__transition2( msg );
            return junction_____State1__exp2( msg );
        default:
            this->unexpectedMessage();
            break;
        }
        return currentState;
    case port_protocol2:
        switch( msg->getSignalId() )
        {
        case Protocol2::signal_m1:
            actionchain_____State1__new_transition_8( msg );
            return junction_____State1__exp3( msg );
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

Capsule_Top::State Capsule_Top::state_____State1__State2( const UMLRTMessage * msg )
{
    switch( msg->destPort->role()->id )
    {
    case port_protocol1:
        switch( msg->getSignalId() )
        {
        case Protocol1::signal_m1:
            actionchain_____State1__new_transition_6( msg );
            return junction_____State1__exp1( msg );
        default:
            this->unexpectedMessage();
            break;
        }
        return currentState;
    case port_protocol2:
        switch( msg->getSignalId() )
        {
        case Protocol2::signal_m1:
            actionchain_____State1__new_transition_9( msg );
            return junction_____State1__exp3( msg );
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

Capsule_Top::State Capsule_Top::state_____State1__boundary( const UMLRTMessage * msg )
{
    switch( msg->destPort->role()->id )
    {
    case port_protocol1:
        switch( msg->getSignalId() )
        {
        case Protocol1::signal_m1:
            actionchain_____State1__new_transition_7( msg );
            return junction_____State1__exp1( msg );
        default:
            this->unexpectedMessage();
            break;
        }
        return currentState;
    case port_protocol2:
        switch( msg->getSignalId() )
        {
        case Protocol2::signal_m1:
            actionchain_____State1__new_transition_10( msg );
            return junction_____State1__exp3( msg );
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

Capsule_Top::State Capsule_Top::state_____State4( const UMLRTMessage * msg )
{
    switch( msg->destPort->role()->id )
    {
    default:
        this->unexpectedMessage();
        break;
    }
    return currentState;
}


static const UMLRTCommsPortRole portroles_border[] = 
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
        Capsule_Top::port_protocol2,
        "Protocol2",
        "protocol2",
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

static void instantiate_Top( const UMLRTRtsInterface * rts, UMLRTSlot * slot, const UMLRTCommsPort * * borderPorts )
{
    slot->capsule = new Capsule_Top( &Top, slot, borderPorts, NULL, false );
}

const UMLRTCapsuleClass Top = 
{
    "Top",
    NULL,
    instantiate_Top,
    0,
    NULL,
    2,
    portroles_border,
    0,
    NULL
};

