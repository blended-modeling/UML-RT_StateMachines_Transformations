
#include "Top.hh"

#include "Protocol1.hh"
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
, currentState( SPECIAL_INTERNAL_STATE_UNVISITED )
{
    stateNames[State2] = "State2";
    stateNames[State1] = "State1";
    stateNames[State2__State1] = "State2__State1";
    stateNames[State2__State2] = "State2__State2";
    stateNames[State2__State3] = "State2__State3";
    stateNames[State2__State4] = "State2__State4";
    stateNames[State2__boundary] = "State2__boundary";
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
    case State3:
        currentState = state_____State3( &message );
        break;
    case State4:
        currentState = state_____State4( &message );
        break;
    case State2__State1:
        currentState = state_____State2__State1( &message );
        break;
    case State2__State2:
        currentState = state_____State2__State2( &message );
        break;
    case State2__State3:
        currentState = state_____State2__State3( &message );
        break;
    case State2__State4:
        currentState = state_____State2__State4( &message );
        break;
    case State2__boundary:
        currentState = state_____State2__boundary( &message );
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

bool Capsule_Top::guard_____State2__transition4( const UMLRTMessage * msg )
{
    #define data ( *(char *)msg->getParam( 0 ) )
    #define rtdata ( (char *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/EffectiveNames/EffectiveNames.uml EffectiveNames::Top::State2 guard State2::subvertex4,State2::State3 */
    return *rtdata == 'q';
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
    #undef data
}

bool Capsule_Top::guard_____State2__tt( const UMLRTMessage * msg )
{
    #define data ( *(int *)msg->getParam( 0 ) )
    #define rtdata ( (int *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/EffectiveNames/EffectiveNames.uml EffectiveNames::Top::State2 guard State2::c1,State2::State2 */
    return *rtdata == 1;
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
    #undef data
}

void Capsule_Top::actionchain_____Initial( const UMLRTMessage * msg )
{
    update_state( State1 );
}

void Capsule_Top::actionchain_____State2__new_transition_1( const UMLRTMessage * msg )
{
    update_state( State2__State1 );
}

void Capsule_Top::actionchain_____State2__new_transition_2( const UMLRTMessage * msg )
{
    update_state( State2__State2 );
}

void Capsule_Top::actionchain_____State2__new_transition_3( const UMLRTMessage * msg )
{
    update_state( State2__State3 );
}

void Capsule_Top::actionchain_____State2__new_transition_4( const UMLRTMessage * msg )
{
    update_state( State2__State4 );
}

void Capsule_Top::actionchain_____State2__new_transition_5_to_unvisited_boundary( const UMLRTMessage * msg )
{
    update_state( State2__boundary );
}

void Capsule_Top::actionchain_____State2__new_transition_6_to_visited_boundary( const UMLRTMessage * msg )
{
    update_state( State2__boundary );
}

void Capsule_Top::actionchain_____State2__transition0( const UMLRTMessage * msg )
{
    update_state( State2 );
}

void Capsule_Top::actionchain_____State2__transition1( const UMLRTMessage * msg )
{
    update_state( State2 );
}

void Capsule_Top::actionchain_____State2__transition3( const UMLRTMessage * msg )
{
    update_state( State2__State3 );
}

void Capsule_Top::actionchain_____State2__transition4( const UMLRTMessage * msg )
{
    update_state( State2__State3 );
}

void Capsule_Top::actionchain_____State2__transition5( const UMLRTMessage * msg )
{
    update_state( State2__State4 );
}

void Capsule_Top::actionchain_____State2__transition6( const UMLRTMessage * msg )
{
    update_state( State2__State1 );
}

void Capsule_Top::actionchain_____State2__transition7( const UMLRTMessage * msg )
{
    update_state( State2 );
    save_history( State2, State2__State2 );
}

void Capsule_Top::actionchain_____State2__transition8( const UMLRTMessage * msg )
{
    update_state( State2 );
    save_history( State2, State2__State4 );
}

void Capsule_Top::actionchain_____State2__tt( const UMLRTMessage * msg )
{
    update_state( State2__State2 );
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

void Capsule_Top::actionchain_____transition4( const UMLRTMessage * msg )
{
    update_state( SPECIAL_INTERNAL_STATE_TOP );
    update_state( State2 );
}

Capsule_Top::State Capsule_Top::junction_____State2__connectionPoint0( const UMLRTMessage * msg )
{
    actionchain_____State2__transition6( msg );
    return State2__State1;
}

Capsule_Top::State Capsule_Top::junction_____State2__connectionPoint1( const UMLRTMessage * msg )
{
    actionchain_____transition2( msg );
    return State3;
}

Capsule_Top::State Capsule_Top::junction_____State2__connectionPoint2( const UMLRTMessage * msg )
{
    return choice_____State2__subvertex4( msg );
}

Capsule_Top::State Capsule_Top::junction_____State2__connectionPoint3( const UMLRTMessage * msg )
{
    actionchain_____transition3( msg );
    return State4;
}

Capsule_Top::State Capsule_Top::choice_____State2__c1( const UMLRTMessage * msg )
{
    if( guard_____State2__tt( msg ) )
    {
        actionchain_____State2__tt( msg );
        return State2__State2;
    }
    else
    {
        actionchain_____State2__transition3( msg );
        return State2__State3;
    }
    return currentState;
}

Capsule_Top::State Capsule_Top::choice_____State2__deephistory( const UMLRTMessage * msg )
{
    if( check_history( State2, State2__State1 ) )
    {
        actionchain_____State2__new_transition_1( msg );
        return State2__State1;
    }
    else if( check_history( State2, State2__State2 ) )
    {
        actionchain_____State2__new_transition_2( msg );
        return State2__State2;
    }
    else if( check_history( State2, State2__State3 ) )
    {
        actionchain_____State2__new_transition_3( msg );
        return State2__State3;
    }
    else if( check_history( State2, State2__State4 ) )
    {
        actionchain_____State2__new_transition_4( msg );
        return State2__State4;
    }
    else if( check_history( State2, SPECIAL_INTERNAL_STATE_UNVISITED ) )
    {
        actionchain_____State2__new_transition_5_to_unvisited_boundary( msg );
        return State2__boundary;
    }
    else if( check_history( State2, State2__boundary ) )
    {
        actionchain_____State2__new_transition_6_to_visited_boundary( msg );
        return State2__boundary;
    }
    return currentState;
}

Capsule_Top::State Capsule_Top::choice_____State2__subvertex4( const UMLRTMessage * msg )
{
    if( guard_____State2__transition4( msg ) )
    {
        actionchain_____State2__transition4( msg );
        return State2__State3;
    }
    else
    {
        actionchain_____State2__transition5( msg );
        return State2__State4;
    }
    return currentState;
}

Capsule_Top::State Capsule_Top::state_____State1( const UMLRTMessage * msg )
{
    switch( msg->destPort->role()->id )
    {
    case port_protocol1:
        switch( msg->getSignalId() )
        {
        case Protocol1::signal_InProtocolMessage1:
            actionchain_____transition1( msg );
            return junction_____State2__connectionPoint0( msg );
        case Protocol1::signal_InProtocolMessage2:
            actionchain_____transition4( msg );
            return junction_____State2__connectionPoint2( msg );
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

Capsule_Top::State Capsule_Top::state_____State2__State1( const UMLRTMessage * msg )
{
    switch( msg->destPort->role()->id )
    {
    case port_protocol1:
        switch( msg->getSignalId() )
        {
        case Protocol1::signal_InProtocolMessage2:
            actionchain_____State2__transition0( msg );
            return choice_____State2__c1( msg );
        case Protocol1::signal_InProtocolMessage3:
            actionchain_____State2__transition1( msg );
            return choice_____State2__subvertex4( msg );
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

Capsule_Top::State Capsule_Top::state_____State2__State2( const UMLRTMessage * msg )
{
    switch( msg->destPort->role()->id )
    {
    case port_protocol1:
        switch( msg->getSignalId() )
        {
        case Protocol1::signal_InProtocolMessage1:
            actionchain_____State2__transition7( msg );
            return junction_____State2__connectionPoint1( msg );
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

Capsule_Top::State Capsule_Top::state_____State2__State3( const UMLRTMessage * msg )
{
    switch( msg->destPort->role()->id )
    {
    default:
        this->unexpectedMessage();
        break;
    }
    return currentState;
}

Capsule_Top::State Capsule_Top::state_____State2__State4( const UMLRTMessage * msg )
{
    switch( msg->destPort->role()->id )
    {
    case port_protocol1:
        switch( msg->getSignalId() )
        {
        case Protocol1::signal_InProtocolMessage3:
            actionchain_____State2__transition8( msg );
            return junction_____State2__connectionPoint3( msg );
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

Capsule_Top::State Capsule_Top::state_____State2__boundary( const UMLRTMessage * msg )
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
    1,
    portroles_border,
    0,
    NULL
};

