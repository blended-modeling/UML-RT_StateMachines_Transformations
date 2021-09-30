
#include "Top.hh"

#include "P.hh"
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
, p( borderPorts[borderport_p] )
, currentState( SPECIAL_INTERNAL_STATE_UNVISITED )
{
    stateNames[State1] = "State1";
    stateNames[State2] = "State2";
    stateNames[State3] = "State3";
    stateNames[State4] = "State4";
    stateNames[SPECIAL_INTERNAL_STATE_TOP] = "<top>";
    stateNames[SPECIAL_INTERNAL_STATE_UNVISITED] = "<uninitialized>";
}





void Capsule_Top::bindPort( bool isBorder, int portId, int index )
{
    if( isBorder )
        switch( portId )
        {
        case borderport_p:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_p, index, true );
            break;
        }
}

void Capsule_Top::unbindPort( bool isBorder, int portId, int index )
{
    if( isBorder )
        switch( portId )
        {
        case borderport_p:
            UMLRTFrameService::sendBoundUnbound( borderPorts, borderport_p, index, false );
            UMLRTFrameService::disconnectPort( borderPorts[borderport_p], index );
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
    case State2:
        currentState = state_____State2( &message );
        break;
    case State3:
        currentState = state_____State3( &message );
        break;
    case State4:
        currentState = state_____State4( &message );
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




void Capsule_Top::update_state( Capsule_Top::State newState )
{
    currentState = newState;
}

void Capsule_Top::transitionaction_____t1( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/Guards/Guards.uml Guards::Top transition State1,State2,e1:p/e2:p */
    // Transition effect t1
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_Top::transitionaction_____t2( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/Guards/Guards.uml Guards::Top transition State1,State3,e1:p/e2:p */
    // Effect code t2
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_Top::transitionaction_____t3( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/Guards/Guards.uml Guards::Top transition State1,State4,e1:p/e2:p */
    // Effect code t3
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

bool Capsule_Top::guard_____t1( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/Guards/Guards.uml Guards::Top guard State1,State2,e1:p/e2:p */
    // Trigger guard e1 for transition t1
    return true;
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

bool Capsule_Top::guard_____t1__trigger0( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/Guards/Guards.uml Guards::Top trigger State1,State2,e1:p/e2:p>>e1:p */
    // Trigger guard e1 for transition t1
    return true;
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

bool Capsule_Top::guard_____t1__trigger1( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/Guards/Guards.uml Guards::Top trigger State1,State2,e1:p/e2:p>>e2:p */
    // Trigger guard e2 for transition t1
    return false;
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

bool Capsule_Top::guard_____t3__trigger0( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/Guards/Guards.uml Guards::Top trigger State1,State4,e1:p/e2:p>>e1:p */
    // Trigger guard e1 for transition t3
    return true;
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

bool Capsule_Top::guard_____t3__trigger1( const UMLRTMessage * msg )
{
    #define rtdata ( (void *)msg->getParam( 0 ) )
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/Guards/Guards.uml Guards::Top trigger State1,State4,e1:p/e2:p>>e2:p */
    // Trigger guard e2 for transition t3
    return false;
    /* UMLRTGEN-USERREGION-END */
    #undef rtdata
}

void Capsule_Top::actionchain_____Initial( const UMLRTMessage * msg )
{
    update_state( State1 );
}

void Capsule_Top::actionchain_____t1( const UMLRTMessage * msg )
{
    update_state( SPECIAL_INTERNAL_STATE_TOP );
    transitionaction_____t1( msg );
    update_state( State2 );
}

void Capsule_Top::actionchain_____t2( const UMLRTMessage * msg )
{
    update_state( SPECIAL_INTERNAL_STATE_TOP );
    transitionaction_____t2( msg );
    update_state( State3 );
}

void Capsule_Top::actionchain_____t3( const UMLRTMessage * msg )
{
    update_state( SPECIAL_INTERNAL_STATE_TOP );
    transitionaction_____t3( msg );
    update_state( State4 );
}

Capsule_Top::State Capsule_Top::state_____State1( const UMLRTMessage * msg )
{
    switch( msg->destPort->role()->id )
    {
    case port_p:
        switch( msg->getSignalId() )
        {
        case P::signal_e1:
            if( guard_____t1__trigger0( msg ) )
            {
                if( guard_____t1( msg ) )
                {
                    actionchain_____t1( msg );
                    return State2;
                }
            }
            if( guard_____t3__trigger0( msg ) )
            {
                actionchain_____t3( msg );
                return State4;
            }
            actionchain_____t2( msg );
            return State3;
        case P::signal_e2:
            if( guard_____t1__trigger1( msg ) )
            {
                if( guard_____t1( msg ) )
                {
                    actionchain_____t1( msg );
                    return State2;
                }
            }
            if( guard_____t3__trigger1( msg ) )
            {
                actionchain_____t3( msg );
                return State4;
            }
            actionchain_____t2( msg );
            return State3;
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
        Capsule_Top::port_p,
        "P",
        "p",
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

