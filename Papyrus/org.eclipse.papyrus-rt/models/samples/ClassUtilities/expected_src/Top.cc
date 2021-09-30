
#include "Top.hh"

#include "umlrtcommsportrole.hh"
#include "umlrtmessage.hh"
#include "umlrtslot.hh"
#include <cstddef>
#include "umlrtcapsuleclass.hh"
#include "umlrtframeservice.hh"
class UMLRTRtsInterface;
struct UMLRTCommsPort;

#include "UtilsPrintKinds.hh"

Capsule_Top::Capsule_Top( const UMLRTCapsuleClass * cd, UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat )
: UMLRTCapsule( NULL, cd, st, border, internal, isStat )
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
    actionchain_____top__Transition3__ActionChain4( &message );
    currentState = top__Running;
}

const char * Capsule_Top::getCurrentStateString() const
{
    return stateNames[currentState];
}




void Capsule_Top::entryaction_____ClassUtility__Top__StateMachine__Region__Running__topRunning( const UMLRTMessage * msg )
{
    // the following code has been generated
    /* UMLRT-CODEGEN:platform:/resource/ClassUtility/ClassUtility.uml#_mDLQEKXHEeWN9JTg-IMukA */
    #define rtdata ( (void *)msg->getParam( 0 ) )
    // generated code ends
    log.log("Top Running.");

    log.show( "Debug is: ");
    if ( utils.isDebug() ) {
    	log.log( "enabled." );
    }else{
    	log.log( "disabled." );
    }

    log.show( "Timestamp kind is: " );
    switch ( utils.getPrintKind() ) {
    	case PrintNone :
    		log.log( "no timestamp." );
    		break;
    	case PrintActual :
    		log.log( "actual timestamp" );
    		break;
    	case PrintDelta :
    		log.log( "delta timestamp" );
    		break;
    	default:
    		log.log( "UNKONWN" );
    		break;
    }

    /**
    log.show("Utils::debugStatus = ");
    if (Utils::debugStatus) {
    log.show( " Debug Status : ");

    log.show("Utils:: printActualTime = ");
    if (Utils::printActualTime) {
    	log.log("true.");
    }else{
    	log.log("false.");
    }

    log.show("Utils:: printDeltaTime = ");
    if (Utils::printDeltaTime) {
    	log.log("true.");
    }else{
    	log.log("false.");
    }
     */
    // the following code has been generated
    #undef rtdata
    // generated code ends
}

void Capsule_Top::transitionaction_____ClassUtility__Top__StateMachine__Region__Transition0__topInItializing( const UMLRTMessage * msg )
{
    // the following code has been generated
    /* UMLRT-CODEGEN:platform:/resource/ClassUtility/ClassUtility.uml#_idwBgKXHEeWN9JTg-IMukA */
    #define rtdata ( (void *)msg->getParam( 0 ) )
    // generated code ends
    log.log("Top initializing.");

    utils.setDebug(true);
    utils.setPrintType(PrintNone);
    // UTILS::setPrintType(PrintActual);
    // UTILS::setPrintType(PrintDelta);

    /**
    Utils::debugStatus = true;
    Utils::printActualTime = true;
    Utils::printDeltaTime = false;
     */
    // the following code has been generated
    #undef rtdata
    // generated code ends
}

void Capsule_Top::actionchain_____top__Transition3__ActionChain4( const UMLRTMessage * msg )
{
    transitionaction_____ClassUtility__Top__StateMachine__Region__Transition0__topInItializing( msg );
    entryaction_____ClassUtility__Top__StateMachine__Region__Running__topRunning( msg );
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
    slot->capsule = new Capsule_Top( &Top, slot, borderPorts, internalPorts, false );
}

const UMLRTCapsuleClass Top = 
{
    "Top",
    NULL,
    instantiate_Top,
    0,
    NULL,
    0,
    NULL,
    1,
    portroles_internal
};

