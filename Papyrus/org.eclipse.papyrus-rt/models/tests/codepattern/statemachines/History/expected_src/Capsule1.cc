
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
    stateNames[s3] = "s3";
    stateNames[s1] = "s1";
    stateNames[s2] = "s2";
    stateNames[s3__s31] = "s3__s31";
    stateNames[s3__s32] = "s3__s32";
    stateNames[SPECIAL_INTERNAL_STATE_TOP] = "<top>";
    stateNames[SPECIAL_INTERNAL_STATE_UNVISITED] = "<uninitialized>";
    int i = 0;
    while( i < 1 )
        history[i++] = SPECIAL_INTERNAL_STATE_UNVISITED;
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
    case s1:
        currentState = state_____s1( &message );
        break;
    case s2:
        currentState = state_____s2( &message );
        break;
    case s3__s31:
        currentState = state_____s3__s31( &message );
        break;
    case s3__s32:
        currentState = state_____s3__s32( &message );
        break;
    default:
        break;
    }
}

void Capsule_Capsule1::initialize( const UMLRTMessage & message )
{
    msg = &message;
    actionchain_____t1( &message );
    currentState = s1;
}

const char * Capsule_Capsule1::getCurrentStateString() const
{
    return stateNames[currentState];
}





void Capsule_Capsule1::save_history( Capsule_Capsule1::State compositeState, Capsule_Capsule1::State subState )
{
    history[compositeState] = subState;
}

bool Capsule_Capsule1::check_history( Capsule_Capsule1::State compositeState, Capsule_Capsule1::State subState )
{
    return history[compositeState] == subState;
}

void Capsule_Capsule1::update_state( Capsule_Capsule1::State newState )
{
    currentState = newState;
}

void Capsule_Capsule1::actionchain_____s3__new_transition_1( const UMLRTMessage * msg )
{
    update_state( s3__s31 );
}

void Capsule_Capsule1::actionchain_____s3__new_transition_2( const UMLRTMessage * msg )
{
    update_state( s3__s32 );
}

void Capsule_Capsule1::actionchain_____s3__new_transition_3( const UMLRTMessage * msg )
{
    update_state( s3__s31 );
}

void Capsule_Capsule1::actionchain_____s3__new_transition_4( const UMLRTMessage * msg )
{
    update_state( s3 );
    save_history( s3, s3__s31 );
}

void Capsule_Capsule1::actionchain_____s3__new_transition_5( const UMLRTMessage * msg )
{
    update_state( s3 );
    save_history( s3, s3__s32 );
}

void Capsule_Capsule1::actionchain_____s3__new_transition_6( const UMLRTMessage * msg )
{
    update_state( s3 );
    save_history( s3, s3__s31 );
}

void Capsule_Capsule1::actionchain_____s3__new_transition_7( const UMLRTMessage * msg )
{
    update_state( s3 );
    save_history( s3, s3__s32 );
}

void Capsule_Capsule1::actionchain_____s3__t32( const UMLRTMessage * msg )
{
    update_state( s3 );
    update_state( s3__s32 );
}

void Capsule_Capsule1::actionchain_____s3__t33( const UMLRTMessage * msg )
{
    update_state( s3 );
}

void Capsule_Capsule1::actionchain_____s3__t34( const UMLRTMessage * msg )
{
    update_state( s3 );
}

void Capsule_Capsule1::actionchain_____t1( const UMLRTMessage * msg )
{
    update_state( s1 );
}

void Capsule_Capsule1::actionchain_____t2( const UMLRTMessage * msg )
{
    update_state( SPECIAL_INTERNAL_STATE_TOP );
    update_state( s2 );
}

void Capsule_Capsule1::actionchain_____t3( const UMLRTMessage * msg )
{
    update_state( SPECIAL_INTERNAL_STATE_TOP );
    update_state( s3 );
}

void Capsule_Capsule1::actionchain_____t4( const UMLRTMessage * msg )
{
    update_state( SPECIAL_INTERNAL_STATE_TOP );
    update_state( s3 );
}

void Capsule_Capsule1::actionchain_____t5( const UMLRTMessage * msg )
{
    update_state( SPECIAL_INTERNAL_STATE_TOP );
    update_state( s1 );
}

void Capsule_Capsule1::actionchain_____t6( const UMLRTMessage * msg )
{
    update_state( SPECIAL_INTERNAL_STATE_TOP );
    update_state( s1 );
}

Capsule_Capsule1::State Capsule_Capsule1::junction_____s3__connectionPoint1( const UMLRTMessage * msg )
{
    actionchain_____t5( msg );
    return s1;
}

Capsule_Capsule1::State Capsule_Capsule1::junction_____s3__new_exitpoint_1( const UMLRTMessage * msg )
{
    actionchain_____t6( msg );
    return s1;
}

Capsule_Capsule1::State Capsule_Capsule1::choice_____s3__deephistory( const UMLRTMessage * msg )
{
    if( check_history( s3, s3__s31 ) )
    {
        actionchain_____s3__new_transition_1( msg );
        return s3__s31;
    }
    else if( check_history( s3, s3__s32 ) )
    {
        actionchain_____s3__new_transition_2( msg );
        return s3__s32;
    }
    else if( check_history( s3, SPECIAL_INTERNAL_STATE_UNVISITED ) )
    {
        actionchain_____s3__new_transition_3( msg );
        return s3__s31;
    }
    return currentState;
}

Capsule_Capsule1::State Capsule_Capsule1::state_____s1( const UMLRTMessage * msg )
{
    switch( msg->destPort->role()->id )
    {
    case port_port1:
        switch( msg->getSignalId() )
        {
        case Protocol1::signal_im2:
            actionchain_____t2( msg );
            return s2;
        case Protocol1::signal_im3:
            actionchain_____t3( msg );
            return choice_____s3__deephistory( msg );
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

Capsule_Capsule1::State Capsule_Capsule1::state_____s2( const UMLRTMessage * msg )
{
    switch( msg->destPort->role()->id )
    {
    case port_port1:
        switch( msg->getSignalId() )
        {
        case Protocol1::signal_im4:
            actionchain_____t4( msg );
            return choice_____s3__deephistory( msg );
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

Capsule_Capsule1::State Capsule_Capsule1::state_____s3__s31( const UMLRTMessage * msg )
{
    switch( msg->destPort->role()->id )
    {
    case port_port1:
        switch( msg->getSignalId() )
        {
        case Protocol1::signal_im2:
            actionchain_____s3__t32( msg );
            return s3__s32;
        case Protocol1::signal_im3:
            actionchain_____s3__t33( msg );
            return choice_____s3__deephistory( msg );
        case Protocol1::signal_im1:
            actionchain_____s3__new_transition_4( msg );
            return junction_____s3__connectionPoint1( msg );
        case Protocol1::signal_iom1:
            actionchain_____s3__new_transition_6( msg );
            return junction_____s3__new_exitpoint_1( msg );
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

Capsule_Capsule1::State Capsule_Capsule1::state_____s3__s32( const UMLRTMessage * msg )
{
    switch( msg->destPort->role()->id )
    {
    case port_port1:
        switch( msg->getSignalId() )
        {
        case Protocol1::signal_im4:
            actionchain_____s3__t34( msg );
            return choice_____s3__deephistory( msg );
        case Protocol1::signal_im1:
            actionchain_____s3__new_transition_5( msg );
            return junction_____s3__connectionPoint1( msg );
        case Protocol1::signal_iom1:
            actionchain_____s3__new_transition_7( msg );
            return junction_____s3__new_exitpoint_1( msg );
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

