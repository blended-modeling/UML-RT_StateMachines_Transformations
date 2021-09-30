
#include "Below1.hh"

#include "umlrtcommsportrole.hh"
#include "umlrtmessage.hh"
#include "umlrtslot.hh"
#include <cstddef>
#include "umlrtcapsuleclass.hh"
#include "umlrtframeservice.hh"
class UMLRTRtsInterface;
struct UMLRTCommsPort;


Capsule_Below1::Capsule_Below1( const UMLRTCapsuleClass * cd, UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat )
: UMLRTCapsule( NULL, cd, st, border, internal, isStat )
, currentState( SPECIAL_INTERNAL_STATE_UNVISITED )
{
    stateNames[top__Running] = "top__Running";
    stateNames[SPECIAL_INTERNAL_STATE_UNVISITED] = "<uninitialized>";
}





void Capsule_Below1::bindPort( bool isBorder, int portId, int index )
{
}

void Capsule_Below1::unbindPort( bool isBorder, int portId, int index )
{
}

void Capsule_Below1::inject( const UMLRTMessage & message )
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

void Capsule_Below1::initialize( const UMLRTMessage & message )
{
    msg = &message;
    actionchain_____top__belowOneInitial__ActionChain3( &message );
    currentState = top__Running;
}

const char * Capsule_Below1::getCurrentStateString() const
{
    return stateNames[currentState];
}




void Capsule_Below1::entryaction_____GetArguments__Below1__StateMachine__Region__Running__Running_EN( const UMLRTMessage * msg )
{
    // the following code has been generated
    /* UMLRT-CODEGEN:platform:/resource/GetArguments/GetArguments.uml#_gGwlaJhoEeW6kuh42ZvZkQ */
    #define rtdata ( (void *)msg->getParam( 0 ) )
    // generated code ends
    log.log( "Below1 Running");

    if ( dbg.isDebug() ) {
    	log.log("Debug is ON!");
    	switch ( dbg.getPrintKind() ) {
    		case PrintActual:
    			log.log( "Printing actual time." );
    			break;
    		case PrintDelta:
    			log.log( "Printing relative (delta) time." );
    			break;
    		case PrintNone:
    			log.log( "Time is not printed." );
    			break;
    		default:
    			log.log( "Could not decipher printing type!" );
    			break;
    	}
    } else {
    	log.log("Debug is OFF!");
    }
    // the following code has been generated
    #undef rtdata
    // generated code ends
}

void Capsule_Below1::transitionaction_____GetArguments__Below1__StateMachine__Region__belowOneInitial__below1Init( const UMLRTMessage * msg )
{
    // the following code has been generated
    /* UMLRT-CODEGEN:platform:/resource/GetArguments/GetArguments.uml#_qF3HcKmuEeWEXOiRnj2ETQ */
    #define rtdata ( (void *)msg->getParam( 0 ) )
    // generated code ends
    log.log( "below1Init" );
    // the following code has been generated
    #undef rtdata
    // generated code ends
}

void Capsule_Below1::actionchain_____top__belowOneInitial__ActionChain3( const UMLRTMessage * msg )
{
    transitionaction_____GetArguments__Below1__StateMachine__Region__belowOneInitial__below1Init( msg );
    entryaction_____GetArguments__Below1__StateMachine__Region__Running__Running_EN( msg );
}

Capsule_Below1::State Capsule_Below1::state_____top__Running( const UMLRTMessage * msg )
{
    switch( msg->destPort->role()->id )
    {
    default:
        this->unexpectedMessage();
        break;
    }
    return currentState;
}


static const UMLRTCommsPortRole portroles_internal[] = 
{
    {
        Capsule_Below1::port_log,
        "Log",
        "log",
        "",
        1,
        true,
        false,
        false,
        false,
        true,
        false,
        false
    }
};

static void instantiate_Below1( const UMLRTRtsInterface * rts, UMLRTSlot * slot, const UMLRTCommsPort * * borderPorts )
{
    const UMLRTCommsPort * * internalPorts = UMLRTFrameService::createInternalPorts( slot, &Below1 );
    slot->capsule = new Capsule_Below1( &Below1, slot, borderPorts, internalPorts, false );
}

const UMLRTCapsuleClass Below1 = 
{
    "Below1",
    NULL,
    instantiate_Below1,
    0,
    NULL,
    0,
    NULL,
    1,
    portroles_internal
};

