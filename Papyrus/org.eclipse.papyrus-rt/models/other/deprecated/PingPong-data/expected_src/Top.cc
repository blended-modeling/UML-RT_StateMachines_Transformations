
#include "Top.hh"

#include "Pinger.hh"
#include "Ponger.hh"
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
, currentState( SPECIAL_INTERNAL_STATE_UNVISITED )
, frame( borderPorts[borderport_frame] )
, pinger( &slot->parts[part_pinger] )
, ponger( &slot->parts[part_ponger] )
{
    stateNames[top__State1] = "top__State1";
    stateNames[SPECIAL_INTERNAL_STATE_UNVISITED] = "<uninitialized>";
}

void Capsule_Top::inject( const UMLRTMessage & message )
{
    msg = &message;
    switch( currentState )
    {
    case top__State1:
        currentState = state_____top__State1( &message );
        break;
    default:
        break;
    }
}

void Capsule_Top::initialize( const UMLRTMessage & message )
{
    msg = &message;
    actionchain_____action_____PingPong_data__Top__StateMachine1__Region1__initial( &message );
    currentState = top__State1;
}

const char * Capsule_Top::getCurrentStateString() const
{
    return stateNames[currentState];
}




void Capsule_Top::transitionaction_____PingPong_data__Top__StateMachine1__Region1__initial__onInit( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/PingPong-data/PingPong-data.uml PingPong-data::Top transition Initial1,State1 */
    std::cout << "Top initialised!\n";
    UMLRTCapsuleId pongerId = frame.incarnate( ponger, Ponger );
    if( ! pongerId.isValid() )
    context()->perror("ponger incarnate failed.");
    printf( "incarnated ponger valid:%d @%p\n",
    pongerId.isValid(), pongerId.getCapsule() );
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_Top::actionchain_____action_____PingPong_data__Top__StateMachine1__Region1__initial( const UMLRTMessage * msg )
{
    transitionaction_____PingPong_data__Top__StateMachine1__Region1__initial__onInit( msg );
}

Capsule_Top::State Capsule_Top::state_____top__State1( const UMLRTMessage * msg )
{
    switch( msg->destPort->role()->id )
    {
    default:
        this->unexpectedMessage();
        break;
    }
    return currentState;
}







void Capsule_Top::bindPort( bool isBorder, int portId, int index )
{
    if( isBorder )
        switch( portId )
        {
        case borderport_frame:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_frame, index, true );
            break;
        }
}

void Capsule_Top::unbindPort( bool isBorder, int portId, int index )
{
    if( isBorder )
        switch( portId )
        {
        case borderport_frame:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_frame, index, false );
            UMLRTFrameService::disconnectPort( borderPorts[borderport_frame], index );
            break;
        }
}


static const UMLRTCapsuleRole roles[] = 
{
    {
        "pinger",
        &Pinger,
        1,
        1,
        false,
        false
    },
    {
        "ponger",
        &Ponger,
        0,
        1,
        true,
        false
    }
};

static const UMLRTCommsPortRole portroles_border[] = 
{
    {
        Capsule_Top::port_frame,
        "Frame",
        "frame",
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
    UMLRTFrameService::connectPorts( &slot->parts[Capsule_Top::part_pinger].slots[0]->ports[Capsule_Pinger::borderport_ping], 0, &slot->parts[Capsule_Top::part_ponger].slots[0]->ports[Capsule_Ponger::borderport_pong], 0 );
    Pinger.instantiate( NULL, slot->parts[Capsule_Top::part_pinger].slots[0], UMLRTFrameService::createBorderPorts( slot->parts[Capsule_Top::part_pinger].slots[0], Pinger.numPortRolesBorder ) );
    slot->capsule = new Capsule_Top( &Top, slot, borderPorts, NULL, false );
}

const UMLRTCapsuleClass Top = 
{
    "Top",
    NULL,
    instantiate_Top,
    2,
    roles,
    1,
    portroles_border,
    0,
    NULL
};

