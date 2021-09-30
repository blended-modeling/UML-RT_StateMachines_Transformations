
#include "Capsule.hh"

#include "MessageProtocol.hh"
#include "Token.hh"
#include "umlrtcommsportrole.hh"
#include "umlrtinmessage.hh"
#include "umlrtslot.hh"
#include <cstddef>
#include "Token.hh"
#include "umlrtcapsuleclass.hh"
#include "umlrtcommsportrole.hh"
#include "umlrtframeservice.hh"
#include "umlrtinmessage.hh"
#include <cstddef>
class UMLRTRtsInterface;
struct UMLRTCommsPort;
struct UMLRTObject_class;

#include "umlrtmain.hh"

Capsule_Capsule::Capsule_Capsule( const UMLRTCapsuleClass * cd, UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * internal, bool isStat )
: UMLRTCapsule( NULL, cd, st, border, internal, isStat )
{
    stateNames[top__Running] = "top__Running";
}


MessageProtocol_baserole Capsule_Capsule::left() const
{
    return MessageProtocol_baserole( borderPorts[borderport_left] );
}

MessageProtocol_baserole Capsule_Capsule::right() const
{
    return MessageProtocol_baserole( borderPorts[borderport_right] );
}



void Capsule_Capsule::bindPort( bool isBorder, int portId, int index )
{
    if( isBorder )
        switch( portId )
        {
        case borderport_right:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_right, index, true );
            break;
        case borderport_left:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_left, index, true );
            break;
        }
}

void Capsule_Capsule::unbindPort( bool isBorder, int portId, int index )
{
    if( isBorder )
        switch( portId )
        {
        case borderport_right:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_right, index, false );
            UMLRTFrameService::disconnectPort( borderPorts[borderport_right], index );
            break;
        case borderport_left:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_left, index, false );
            UMLRTFrameService::disconnectPort( borderPorts[borderport_left], index );
            break;
        }
}


void Capsule_Capsule::inject( const UMLRTInMessage & message )
{
    msg = &message;
    switch( currentState )
    {
    case top__Running:
        currentState = state_____top__Running( message );
        break;
    default:
        break;
    }
}

void Capsule_Capsule::initialize( const UMLRTInMessage & message )
{
    msg = &message;
    actionchain_____top__initial__ActionChain2( message );
    currentState = top__Running;
}

const char * Capsule_Capsule::getCurrentStateString() const
{
    return stateNames[currentState];
}




void Capsule_Capsule::transitionaction_____top__fromLeft__ActionChain4__toRight( const UMLRTInMessage & msg )
{
    Token rtdata;
    msg.decode( &rtdata );
    printf( "%s: %d going right:%d\n", getName(), rtdata.label, rtdata.data++ );
    right().msg( rtdata ).send();
}

void Capsule_Capsule::transitionaction_____top__fromRight__ActionChain6__toLeft( const UMLRTInMessage & msg )
{
    Token rtdata;
    msg.decode( &rtdata );
    printf( "%s: %d going left:%d\n", getName(), rtdata.label, rtdata.data++ );
    if( rtdata.data >= limit )
    {
        context()->debugOutputModel();
        exit( 0 );
    }
    left().msg( rtdata ).send();
}

void Capsule_Capsule::transitionaction_____top__initial__ActionChain2__init( const UMLRTInMessage & msg )
{
    void * rtdata = NULL;
    msg.decode( &rtdata );
    if( UMLRTMain::getArgCount() <= 0 )
        limit = 10;
    else
    {
    	printf( "parsing arg '%s'\n", UMLRTMain::getArg( 0 ) );
    	limit = atoi( UMLRTMain::getArg( 0 ) );
    }

    Token token = { 0, Token::Label++ };
    left().msg( token ).send();
}

void Capsule_Capsule::actionchain_____top__fromLeft__ActionChain4( const UMLRTInMessage & msg )
{
    transitionaction_____top__fromLeft__ActionChain4__toRight( msg );
}

void Capsule_Capsule::actionchain_____top__fromRight__ActionChain6( const UMLRTInMessage & msg )
{
    transitionaction_____top__fromRight__ActionChain6__toLeft( msg );
}

void Capsule_Capsule::actionchain_____top__initial__ActionChain2( const UMLRTInMessage & msg )
{
    transitionaction_____top__initial__ActionChain2__init( msg );
}

Capsule_Capsule::State Capsule_Capsule::state_____top__Running( const UMLRTInMessage & msg )
{
    switch( msg.destPort->role()->id )
    {
    case port_right:
        switch( msg.getSignalId() )
        {
        case MessageProtocol::signal_msg:
            msg.decodeInit( UMLRTType_Token );
            actionchain_____top__fromRight__ActionChain6( msg );
            return top__Running;
        default:
            this->unexpectedMessage();
            break;
        }
        return currentState;
    case port_left:
        switch( msg.getSignalId() )
        {
        case MessageProtocol::signal_msg:
            msg.decodeInit( UMLRTType_Token );
            actionchain_____top__fromLeft__ActionChain4( msg );
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
        Capsule_Capsule::port_right,
        "MessageProtocol",
        "right",
        NULL,
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
        Capsule_Capsule::port_left,
        "MessageProtocol",
        "left",
        NULL,
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
static void instantiate_Capsule( const UMLRTRtsInterface * rts, UMLRTSlot * slot, const UMLRTCommsPort * * borderPorts )
{
    slot->capsule = new Capsule_Capsule( &Capsule, slot, borderPorts, NULL, false );
}
const UMLRTCapsuleClass Capsule = 
{
    "Capsule",
    NULL,
    instantiate_Capsule,
    0,
    NULL,
    2,
    portroles_border,
    0,
    NULL
};
