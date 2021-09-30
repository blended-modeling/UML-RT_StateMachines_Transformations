
#include "Ponger.hh"

#include "PingPongProtocol.hh"
#include "umlrtcommsportrole.hh"
#include "umlrtmessage.hh"
#include "umlrtsignal.hh"
#include "umlrtslot.hh"
#include <cstddef>
#include "DataType1.hh"
#include "umlrtcapsuleclass.hh"
#include "umlrtframeservice.hh"
class UMLRTRtsInterface;
struct UMLRTCommsPort;

Capsule_Ponger::Capsule_Ponger( const UMLRTCapsuleClass * cd, UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat )
: UMLRTCapsule( NULL, cd, st, border, internal, isStat )
, pong( borderPorts[borderport_pong] )
, currentState( SPECIAL_INTERNAL_STATE_UNVISITED )
{
    stateNames[top__Running] = "top__Running";
    stateNames[SPECIAL_INTERNAL_STATE_UNVISITED] = "<uninitialized>";
}





void Capsule_Ponger::bindPort( bool isBorder, int portId, int index )
{
    if( isBorder )
        switch( portId )
        {
        case borderport_pong:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_pong, index, true );
            break;
        }
}

void Capsule_Ponger::unbindPort( bool isBorder, int portId, int index )
{
    if( isBorder )
        switch( portId )
        {
        case borderport_pong:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_pong, index, false );
            UMLRTFrameService::disconnectPort( borderPorts[borderport_pong], index );
            break;
        }
}

void Capsule_Ponger::inject( const UMLRTMessage & message )
{
    msg = &message;
    switch( currentState )
    {
    case top__Running:
        currentState = state_____top__Running( &message );
        break;
    default:
        break;
    }
}

void Capsule_Ponger::initialize( const UMLRTMessage & message )
{
    msg = &message;
    actionchain_____action_____PingPong_data__Ponger__Ponger_SM__Region1__initial( &message );
    currentState = top__Running;
}

const char * Capsule_Ponger::getCurrentStateString() const
{
    return stateNames[currentState];
}




void Capsule_Ponger::transitionaction_____PingPong_data__Ponger__Ponger_SM__Region1__initial__onInit( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/PingPong-data/PingPong-data.uml PingPong-data::Ponger transition Initial,Running */
    std::cout << "Ponger initialised" << std::endl;
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_Ponger::transitionaction_____PingPong_data__Ponger__Ponger_SM__Region1__onBound__onBound( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/PingPong-data/PingPong-data.uml PingPong-data::Ponger transition Running,Running,rtBound:pong */
    std::cout << getName() << " onBound" << std::endl;
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_Ponger::transitionaction_____PingPong_data__Ponger__Ponger_SM__Region1__onPing__onPing( const UMLRTMessage * msg )
{
    #define umlrtparam_param ( *(const DataType1 * )msg->getParam( 0 ) )
    #define rtdata ( (const DataType1 * )msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/PingPong-data/PingPong-data.uml PingPong-data::Ponger transition Running,Running,ping:pong */
    std::cout << "Ping received"
    << " field0:" << rtdata->field0_enum
    << " field1:" << rtdata->field1_int
    << " field2:" << rtdata->field2_bool
    << " field3:" << rtdata->field3_double
    << " field4:" << *rtdata->field4_ptr
    << " field5:" << rtdata->field5_freeform
    << " field6:" << rtdata->field6_string
    << std::endl;
    std::cout << "Sending Pong" << std::endl;
    pong.pong().send();
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
    #undef umlrtparam_param
}

void Capsule_Ponger::actionchain_____action_____PingPong_data__Ponger__Ponger_SM__Region1__initial( const UMLRTMessage * msg )
{
    transitionaction_____PingPong_data__Ponger__Ponger_SM__Region1__initial__onInit( msg );
}

void Capsule_Ponger::actionchain_____action_____PingPong_data__Ponger__Ponger_SM__Region1__onBound( const UMLRTMessage * msg )
{
    transitionaction_____PingPong_data__Ponger__Ponger_SM__Region1__onBound__onBound( msg );
}

void Capsule_Ponger::actionchain_____action_____PingPong_data__Ponger__Ponger_SM__Region1__onPing( const UMLRTMessage * msg )
{
    transitionaction_____PingPong_data__Ponger__Ponger_SM__Region1__onPing__onPing( msg );
}

Capsule_Ponger::State Capsule_Ponger::state_____top__Running( const UMLRTMessage * msg )
{
    switch( msg->destPort->role()->id )
    {
    case port_pong:
        switch( msg->getSignalId() )
        {
        case PingPongProtocol::signal_ping:
            actionchain_____action_____PingPong_data__Ponger__Ponger_SM__Region1__onPing( msg );
            return top__Running;
        case UMLRTSignal::rtBound:
            actionchain_____action_____PingPong_data__Ponger__Ponger_SM__Region1__onBound( msg );
            return top__Running;
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
        Capsule_Ponger::port_pong,
        "PingPongProtocol",
        "pong",
        "",
        1,
        true,
        true,
        false,
        true,
        false,
        false,
        true
    }
};

static void instantiate_Ponger( const UMLRTRtsInterface * rts, UMLRTSlot * slot, const UMLRTCommsPort * * borderPorts )
{
    slot->capsule = new Capsule_Ponger( &Ponger, slot, borderPorts, NULL, false );
}

const UMLRTCapsuleClass Ponger = 
{
    "Ponger",
    NULL,
    instantiate_Ponger,
    0,
    NULL,
    1,
    portroles_border,
    0,
    NULL
};

