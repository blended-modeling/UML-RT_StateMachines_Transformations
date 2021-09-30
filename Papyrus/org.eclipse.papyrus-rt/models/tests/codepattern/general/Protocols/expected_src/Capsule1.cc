
#include "Capsule1.hh"

#include "Protocol1.hh"
#include "Protocol10.hh"
#include "Protocol2.hh"
#include "Protocol3.hh"
#include "umlrtcapsuleclass.hh"
#include "umlrtcommsportrole.hh"
#include "umlrtmessage.hh"
#include "umlrtslot.hh"
#include <cstddef>
#include "umlrtframeservice.hh"
class UMLRTRtsInterface;
struct UMLRTCommsPort;

Capsule_Capsule1::Capsule_Capsule1( const UMLRTCapsuleClass * cd, UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat )
: UMLRTCapsule( NULL, cd, st, border, internal, isStat )
, p10a( borderPorts[borderport_p10a] )
, p10b( borderPorts[borderport_p10b] )
, p1a( borderPorts[borderport_p1a] )
, p1b( borderPorts[borderport_p1b] )
, p2a( borderPorts[borderport_p2a] )
, p2b( borderPorts[borderport_p2b] )
, p3a( borderPorts[borderport_p3a] )
, p3b( borderPorts[borderport_p3b] )
, p4a( borderPorts[borderport_p4a] )
, p4b( borderPorts[borderport_p4b] )
, p5a( borderPorts[borderport_p5a] )
, p5b( borderPorts[borderport_p5b] )
, p6a( borderPorts[borderport_p6a] )
, p6b( borderPorts[borderport_p6b] )
, p7a( borderPorts[borderport_p7a] )
, p7b( borderPorts[borderport_p7b] )
, p8a( borderPorts[borderport_p8a] )
, p8b( borderPorts[borderport_p8b] )
, p9a( borderPorts[borderport_p9a] )
, p9b( borderPorts[borderport_p9b] )
, currentState( SPECIAL_INTERNAL_STATE_UNVISITED )
{
    stateNames[State1] = "State1";
    stateNames[State2] = "State2";
    stateNames[State3] = "State3";
    stateNames[State4] = "State4";
    stateNames[State5] = "State5";
    stateNames[State6] = "State6";
    stateNames[State7] = "State7";
    stateNames[State8] = "State8";
    stateNames[State9] = "State9";
    stateNames[SPECIAL_INTERNAL_STATE_TOP] = "<top>";
    stateNames[SPECIAL_INTERNAL_STATE_UNVISITED] = "<uninitialized>";
}
























void Capsule_Capsule1::bindPort( bool isBorder, int portId, int index )
{
    if( isBorder )
        switch( portId )
        {
        case borderport_p10a:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_p10a, index, true );
            break;
        case borderport_p10b:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_p10b, index, true );
            break;
        case borderport_p1a:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_p1a, index, true );
            break;
        case borderport_p1b:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_p1b, index, true );
            break;
        case borderport_p2a:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_p2a, index, true );
            break;
        case borderport_p2b:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_p2b, index, true );
            break;
        case borderport_p3a:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_p3a, index, true );
            break;
        case borderport_p3b:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_p3b, index, true );
            break;
        case borderport_p4a:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_p4a, index, true );
            break;
        case borderport_p4b:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_p4b, index, true );
            break;
        case borderport_p5a:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_p5a, index, true );
            break;
        case borderport_p5b:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_p5b, index, true );
            break;
        case borderport_p6a:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_p6a, index, true );
            break;
        case borderport_p6b:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_p6b, index, true );
            break;
        case borderport_p7a:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_p7a, index, true );
            break;
        case borderport_p7b:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_p7b, index, true );
            break;
        case borderport_p8a:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_p8a, index, true );
            break;
        case borderport_p8b:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_p8b, index, true );
            break;
        case borderport_p9a:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_p9a, index, true );
            break;
        case borderport_p9b:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_p9b, index, true );
            break;
        }
}

void Capsule_Capsule1::unbindPort( bool isBorder, int portId, int index )
{
    if( isBorder )
        switch( portId )
        {
        case borderport_p10a:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_p10a, index, false );
            UMLRTFrameService::disconnectPort( borderPorts[borderport_p10a], index );
            break;
        case borderport_p10b:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_p10b, index, false );
            UMLRTFrameService::disconnectPort( borderPorts[borderport_p10b], index );
            break;
        case borderport_p1a:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_p1a, index, false );
            UMLRTFrameService::disconnectPort( borderPorts[borderport_p1a], index );
            break;
        case borderport_p1b:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_p1b, index, false );
            UMLRTFrameService::disconnectPort( borderPorts[borderport_p1b], index );
            break;
        case borderport_p2a:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_p2a, index, false );
            UMLRTFrameService::disconnectPort( borderPorts[borderport_p2a], index );
            break;
        case borderport_p2b:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_p2b, index, false );
            UMLRTFrameService::disconnectPort( borderPorts[borderport_p2b], index );
            break;
        case borderport_p3a:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_p3a, index, false );
            UMLRTFrameService::disconnectPort( borderPorts[borderport_p3a], index );
            break;
        case borderport_p3b:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_p3b, index, false );
            UMLRTFrameService::disconnectPort( borderPorts[borderport_p3b], index );
            break;
        case borderport_p4a:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_p4a, index, false );
            UMLRTFrameService::disconnectPort( borderPorts[borderport_p4a], index );
            break;
        case borderport_p4b:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_p4b, index, false );
            UMLRTFrameService::disconnectPort( borderPorts[borderport_p4b], index );
            break;
        case borderport_p5a:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_p5a, index, false );
            UMLRTFrameService::disconnectPort( borderPorts[borderport_p5a], index );
            break;
        case borderport_p5b:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_p5b, index, false );
            UMLRTFrameService::disconnectPort( borderPorts[borderport_p5b], index );
            break;
        case borderport_p6a:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_p6a, index, false );
            UMLRTFrameService::disconnectPort( borderPorts[borderport_p6a], index );
            break;
        case borderport_p6b:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_p6b, index, false );
            UMLRTFrameService::disconnectPort( borderPorts[borderport_p6b], index );
            break;
        case borderport_p7a:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_p7a, index, false );
            UMLRTFrameService::disconnectPort( borderPorts[borderport_p7a], index );
            break;
        case borderport_p7b:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_p7b, index, false );
            UMLRTFrameService::disconnectPort( borderPorts[borderport_p7b], index );
            break;
        case borderport_p8a:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_p8a, index, false );
            UMLRTFrameService::disconnectPort( borderPorts[borderport_p8a], index );
            break;
        case borderport_p8b:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_p8b, index, false );
            UMLRTFrameService::disconnectPort( borderPorts[borderport_p8b], index );
            break;
        case borderport_p9a:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_p9a, index, false );
            UMLRTFrameService::disconnectPort( borderPorts[borderport_p9a], index );
            break;
        case borderport_p9b:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_p9b, index, false );
            UMLRTFrameService::disconnectPort( borderPorts[borderport_p9b], index );
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
    case State4:
        currentState = state_____State4( &message );
        break;
    case State5:
        currentState = state_____State5( &message );
        break;
    case State6:
        currentState = state_____State6( &message );
        break;
    case State7:
        currentState = state_____State7( &message );
        break;
    case State8:
        currentState = state_____State8( &message );
        break;
    case State9:
        currentState = state_____State9( &message );
        break;
    default:
        break;
    }
}

void Capsule_Capsule1::initialize( const UMLRTMessage & message )
{
    msg = &message;
    actionchain_____Initial( &message );
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

void Capsule_Capsule1::actionchain_____Initial( const UMLRTMessage * msg )
{
    update_state( State1 );
}

void Capsule_Capsule1::actionchain_____p1b_pr1_in_m1( const UMLRTMessage * msg )
{
    update_state( SPECIAL_INTERNAL_STATE_TOP );
    update_state( State3 );
}

void Capsule_Capsule1::actionchain_____p2a_pr2_out_m1( const UMLRTMessage * msg )
{
    update_state( SPECIAL_INTERNAL_STATE_TOP );
    update_state( State4 );
}

void Capsule_Capsule1::actionchain_____p2b_any( const UMLRTMessage * msg )
{
    update_state( SPECIAL_INTERNAL_STATE_TOP );
    update_state( State5 );
}

void Capsule_Capsule1::actionchain_____p3a_pr3_inout_m1( const UMLRTMessage * msg )
{
    update_state( SPECIAL_INTERNAL_STATE_TOP );
    update_state( State6 );
}

void Capsule_Capsule1::actionchain_____p3b_pr3_inout_m1( const UMLRTMessage * msg )
{
    update_state( SPECIAL_INTERNAL_STATE_TOP );
    update_state( State7 );
}

void Capsule_Capsule1::actionchain_____p4a_pr4_out_m1_inout_m1( const UMLRTMessage * msg )
{
    update_state( SPECIAL_INTERNAL_STATE_TOP );
    update_state( State8 );
}

void Capsule_Capsule1::actionchain_____p4b_pr4_in_m1_inout_m1( const UMLRTMessage * msg )
{
    update_state( SPECIAL_INTERNAL_STATE_TOP );
    update_state( State9 );
}

void Capsule_Capsule1::actionchain_____pa1_any( const UMLRTMessage * msg )
{
    update_state( SPECIAL_INTERNAL_STATE_TOP );
    update_state( State2 );
}

Capsule_Capsule1::State Capsule_Capsule1::state_____State1( const UMLRTMessage * msg )
{
    switch( msg->destPort->role()->id )
    {
    case port_p1a:
        switch( msg->getSignalId() )
        {
        default:
            actionchain_____pa1_any( msg );
            return State2;
        }
        return currentState;
    case port_p1b:
        switch( msg->getSignalId() )
        {
        case Protocol1::signal_pr1_in_m1:
            actionchain_____p1b_pr1_in_m1( msg );
            return State3;
        default:
            this->unexpectedMessage();
            break;
        }
        return currentState;
    case port_p2a:
        switch( msg->getSignalId() )
        {
        case Protocol2::signal_pr2_out_m1:
            actionchain_____p2a_pr2_out_m1( msg );
            return State4;
        default:
            this->unexpectedMessage();
            break;
        }
        return currentState;
    case port_p2b:
        switch( msg->getSignalId() )
        {
        default:
            actionchain_____p2b_any( msg );
            return State5;
        }
        return currentState;
    case port_p3a:
        switch( msg->getSignalId() )
        {
        case Protocol3::signal_pr3_inout_m1:
            actionchain_____p3a_pr3_inout_m1( msg );
            return State6;
        default:
            this->unexpectedMessage();
            break;
        }
        return currentState;
    case port_p3b:
        switch( msg->getSignalId() )
        {
        case Protocol3::signal_pr3_inout_m1:
            actionchain_____p3b_pr3_inout_m1( msg );
            return State7;
        default:
            this->unexpectedMessage();
            break;
        }
        return currentState;
    case port_p4a:
        switch( msg->getSignalId() )
        {
        case Protocol10::signal_pr4_inout_m1:
            actionchain_____p4a_pr4_out_m1_inout_m1( msg );
            return State8;
        case Protocol10::signal_pr4_out_m1:
            actionchain_____p4a_pr4_out_m1_inout_m1( msg );
            return State8;
        default:
            this->unexpectedMessage();
            break;
        }
        return currentState;
    case port_p4b:
        switch( msg->getSignalId() )
        {
        case Protocol10::signal_pr4_inout_m1:
            actionchain_____p4b_pr4_in_m1_inout_m1( msg );
            return State9;
        case Protocol10::signal_pr4_in_m1:
            actionchain_____p4b_pr4_in_m1_inout_m1( msg );
            return State9;
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

Capsule_Capsule1::State Capsule_Capsule1::state_____State2( const UMLRTMessage * msg )
{
    switch( msg->destPort->role()->id )
    {
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
    default:
        this->unexpectedMessage();
        break;
    }
    return currentState;
}

Capsule_Capsule1::State Capsule_Capsule1::state_____State4( const UMLRTMessage * msg )
{
    switch( msg->destPort->role()->id )
    {
    default:
        this->unexpectedMessage();
        break;
    }
    return currentState;
}

Capsule_Capsule1::State Capsule_Capsule1::state_____State5( const UMLRTMessage * msg )
{
    switch( msg->destPort->role()->id )
    {
    default:
        this->unexpectedMessage();
        break;
    }
    return currentState;
}

Capsule_Capsule1::State Capsule_Capsule1::state_____State6( const UMLRTMessage * msg )
{
    switch( msg->destPort->role()->id )
    {
    default:
        this->unexpectedMessage();
        break;
    }
    return currentState;
}

Capsule_Capsule1::State Capsule_Capsule1::state_____State7( const UMLRTMessage * msg )
{
    switch( msg->destPort->role()->id )
    {
    default:
        this->unexpectedMessage();
        break;
    }
    return currentState;
}

Capsule_Capsule1::State Capsule_Capsule1::state_____State8( const UMLRTMessage * msg )
{
    switch( msg->destPort->role()->id )
    {
    default:
        this->unexpectedMessage();
        break;
    }
    return currentState;
}

Capsule_Capsule1::State Capsule_Capsule1::state_____State9( const UMLRTMessage * msg )
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
        Capsule_Capsule1::port_p1a,
        "Protocol1",
        "p1a",
        "",
        1,
        true,
        true,
        false,
        false,
        false,
        false,
        true
    },
    {
        Capsule_Capsule1::port_p10a,
        "Protocol10",
        "p10a",
        "",
        1,
        true,
        true,
        false,
        false,
        false,
        false,
        true
    },
    {
        Capsule_Capsule1::port_p10b,
        "Protocol10",
        "p10b",
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
        Capsule_Capsule1::port_p1b,
        "Protocol1",
        "p1b",
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
        Capsule_Capsule1::port_p2a,
        "Protocol2",
        "p2a",
        "",
        1,
        true,
        true,
        false,
        false,
        false,
        false,
        true
    },
    {
        Capsule_Capsule1::port_p2b,
        "Protocol2",
        "p2b",
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
        Capsule_Capsule1::port_p3a,
        "Protocol3",
        "p3a",
        "",
        1,
        true,
        true,
        false,
        false,
        false,
        false,
        true
    },
    {
        Capsule_Capsule1::port_p3b,
        "Protocol3",
        "p3b",
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
        Capsule_Capsule1::port_p4a,
        "Protocol4",
        "p4a",
        "",
        1,
        true,
        true,
        false,
        false,
        false,
        false,
        true
    },
    {
        Capsule_Capsule1::port_p4b,
        "Protocol4",
        "p4b",
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
        Capsule_Capsule1::port_p5a,
        "Protocol5",
        "p5a",
        "",
        1,
        true,
        true,
        false,
        false,
        false,
        false,
        true
    },
    {
        Capsule_Capsule1::port_p5b,
        "Protocol5",
        "p5b",
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
        Capsule_Capsule1::port_p6a,
        "Protocol6",
        "p6a",
        "",
        1,
        true,
        true,
        false,
        false,
        false,
        false,
        true
    },
    {
        Capsule_Capsule1::port_p6b,
        "Protocol6",
        "p6b",
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
        Capsule_Capsule1::port_p7a,
        "Protocol7",
        "p7a",
        "",
        1,
        true,
        true,
        false,
        false,
        false,
        false,
        true
    },
    {
        Capsule_Capsule1::port_p7b,
        "Protocol7",
        "p7b",
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
        Capsule_Capsule1::port_p8a,
        "Protocol8",
        "p8a",
        "",
        1,
        true,
        true,
        false,
        false,
        false,
        false,
        true
    },
    {
        Capsule_Capsule1::port_p8b,
        "Protocol8",
        "p8b",
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
        Capsule_Capsule1::port_p9a,
        "Protocol9",
        "p9a",
        "",
        1,
        true,
        true,
        false,
        false,
        false,
        false,
        true
    },
    {
        Capsule_Capsule1::port_p9b,
        "Protocol9",
        "p9b",
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

static void instantiate_Capsule1( const UMLRTRtsInterface * rts, UMLRTSlot * slot, const UMLRTCommsPort * * borderPorts )
{
    slot->capsule = new Capsule_Capsule1( &Capsule1, slot, borderPorts, NULL, false );
}

const UMLRTCapsuleClass Capsule1 = 
{
    "Capsule1",
    NULL,
    instantiate_Capsule1,
    0,
    NULL,
    20,
    portroles_border,
    0,
    NULL
};

