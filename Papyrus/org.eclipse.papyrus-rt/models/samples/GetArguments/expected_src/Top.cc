
#include "Top.hh"

#include "Below1.hh"
#include "umlrtcapsuleclass.hh"
#include "umlrtcapsulepart.hh"
#include "umlrtcommsportrole.hh"
#include "umlrtmessage.hh"
#include "umlrtslot.hh"
#include <cstddef>
#include "umlrtcapsulerole.hh"
#include "umlrtframeservice.hh"
class UMLRTRtsInterface;
struct UMLRTCommsPort;


Capsule_Top::Capsule_Top( const UMLRTCapsuleClass * cd, UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat )
: UMLRTCapsule( NULL, cd, st, border, internal, isStat )
, below1( &slot->parts[part_below1] )
, currentState( SPECIAL_INTERNAL_STATE_UNVISITED )
{
    stateNames[top__Running] = "top__Running";
    stateNames[SPECIAL_INTERNAL_STATE_UNVISITED] = "<uninitialized>";
}






void Capsule_Top::bindPort( bool isBorder, int portId, int index )
{
}

void Capsule_Top::unbindPort( bool isBorder, int portId, int index )
{
}

void Capsule_Top::inject( const UMLRTMessage & message )
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

void Capsule_Top::initialize( const UMLRTMessage & message )
{
    msg = &message;
    actionchain_____top__TopInitial__ActionChain3( &message );
    currentState = top__Running;
}

const char * Capsule_Top::getCurrentStateString() const
{
    return stateNames[currentState];
}




void Capsule_Top::entryaction_____GetArguments__Top__StateMachine__Region__Running__topRunning( const UMLRTMessage * msg )
{
    // the following code has been generated
    /* UMLRT-CODEGEN:platform:/resource/GetArguments/GetArguments.uml#_YSG7oKXBEeWN9JTg-IMukA */
    #define rtdata ( (void *)msg->getParam( 0 ) )
    // generated code ends
    log.log( "Top is running. ");
    // the following code has been generated
    #undef rtdata
    // generated code ends
}

void Capsule_Top::transitionaction_____GetArguments__Top__StateMachine__Region__TopInitial__initializeTop( const UMLRTMessage * msg )
{
    // the following code has been generated
    /* UMLRT-CODEGEN:platform:/resource/GetArguments/GetArguments.uml#_-DasEKW-EeWN9JTg-IMukA */
    #define rtdata ( (void *)msg->getParam( 0 ) )
    // generated code ends
    log.log( "initializeTop" );

    int numArgs = 0;
    char argVal;

    dbg.setDebug( false );
    dbg.setPrintType( PrintActual );

    numArgs = UMLRTMain::getArgCount();

    if ( numArgs > 0 ) {
    	log.show ("Number of arguments: ");
    	log.log( numArgs );
    	for ( int i = 0; i < numArgs; i++ ) {
    		log.show( "Args(" );
    		log.show( i );
    		log.show( " ) = " );
    		(log.log( UMLRTMain::getArg( i ) ));
    	}
    } else {
    	log.log( "No user arguments" );
    }

    for ( int i = 0; i < numArgs; i++ ) {
    	if  ( UMLRTMain::getArg(i)[0] == '-' ) {
    		argVal = UMLRTMain::getArg(i)[1];
    		log.show( "argument: "); log.log( argVal );
    		switch ( argVal ) {
    			case 'D' :
    			case 'd' :
    				dbg.setDebug( true );
    				log.log( "debug output enabled" );
    				break;
    			case 'T' :
    			case 't' :
    				dbg.setPrintType( PrintActual );
    				if ( dbg.isDebug() ) {
    					log.log("Should print actual time!");
    				}
    				break;
    			case 'R' :
    			case 'r' :
    				dbg.setPrintType( PrintDelta );
    				if ( dbg.isDebug() ) {
    					log.log("Should print relative time!");
    				}
    				break;
    			default:
    				if ( dbg.isDebug() ) {
    					log.show( "Unrecognized argument: " );
    					log.log( argVal );
    				}
    				break;
    		}
    	} else {
    		if ( dbg.isDebug() ) {
    			log.show( "Argument value: " );
    			log.log( UMLRTMain::getArg(i) );
    		}
    	}
    }
    // the following code has been generated
    #undef rtdata
    // generated code ends
}

void Capsule_Top::actionchain_____top__TopInitial__ActionChain3( const UMLRTMessage * msg )
{
    transitionaction_____GetArguments__Top__StateMachine__Region__TopInitial__initializeTop( msg );
    entryaction_____GetArguments__Top__StateMachine__Region__Running__topRunning( msg );
}

Capsule_Top::State Capsule_Top::state_____top__Running( const UMLRTMessage * msg )
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
        "below1",
        &Below1,
        1,
        1,
        false,
        false
    }
};

static const UMLRTCommsPortRole portroles_internal[] = 
{
    {
        Capsule_Top::port_log,
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

static void instantiate_Top( const UMLRTRtsInterface * rts, UMLRTSlot * slot, const UMLRTCommsPort * * borderPorts )
{
    const UMLRTCommsPort * * internalPorts = UMLRTFrameService::createInternalPorts( slot, &Top );
    Below1.instantiate( NULL, slot->parts[Capsule_Top::part_below1].slots[0], UMLRTFrameService::createBorderPorts( slot->parts[Capsule_Top::part_below1].slots[0], Below1.numPortRolesBorder ) );
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
    1,
    portroles_internal
};

