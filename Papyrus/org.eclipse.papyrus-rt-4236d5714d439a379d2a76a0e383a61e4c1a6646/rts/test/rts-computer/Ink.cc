/*******************************************************************************
* Copyright (c) 2015 Zeligsoft (2009) Limited  and others.
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v10.html
*******************************************************************************/

#include "Ink.hh"

#include "umlrtcapsuleclass.hh"
#include "umlrtcapsuleclass.hh"
#include "umlrtcommsport.hh"
#include "umlrtcommsportrole.hh"
#include "umlrtinmessage.hh"
#include "umlrtrtsinterface.hh"
#include "umlrtslot.hh"
#include "umlrttimerprotocol.hh"
#include <cstddef>
#include <cstddef>
#include "umlrtcontroller.hh"

Capsule_Ink::Capsule_Ink( const UMLRTRtsInterface * rtsif, const UMLRTCapsuleClass * capsuleClass, UMLRTSlot * slot, const UMLRTCommsPort * * border, const UMLRTCommsPort * internal, bool isStat )
: UMLRTCapsule( rtsif, capsuleClass, slot, border, internal, isStat )
{
}


InkLevelProtocol_baserole Capsule_Ink::inkLevel() const
{
    return InkLevelProtocol_baserole( borderPorts[borderport_inkLevel] );
}

InkPresentProtocol_baserole Capsule_Ink::inkPresent() const
{
    return InkPresentProtocol_baserole( borderPorts[borderport_inkPresent]);
}


UMLRTTimerProtocol_baserole Capsule_Ink::timer() const
{
    return UMLRTTimerProtocol_baserole( &internalPorts[internalport_timer] );
}

TonerStatusProtocol_baserole Capsule_Ink::inkStatus() const
{
    return TonerStatusProtocol_baserole( &internalPorts[internalport_inkStatus] );
}




void Capsule_Ink::inject( const UMLRTInMessage & msg )
{
    //int rtdata = *(int *)msg.signal.getPayload();

    switch( currentState )
    {
    case InkStateMachine_State1:
        currentState = state_____InkStateMachine__State1( msg );
        break;
    case SPECIAL_INTERNAL_STATE_UNVISITED:
        break;
    }
}

void Capsule_Ink::initialize( const UMLRTInMessage & msg )
{
    actionchain_____InkStateMachine__initialize__Chain1( msg );
    currentState = InkStateMachine_State1;
}

void Capsule_Ink::unbindPort( bool isBorder, int portIndex, int farEndIndex )
{
    if (isBorder)
    {
        rtsif->unbindPort(borderPorts, portIndex, farEndIndex);
    }
    else
    {
        rtsif->unbindPort(internalPorts, portIndex, farEndIndex);
    }
}

void Capsule_Ink::bindPort( bool isBorder, int portIndex, int farEndIndex )
{
    if (isBorder)
    {
        rtsif->bindPort(borderPorts, portIndex, farEndIndex);
    }
    else
    {
        rtsif->bindPort( internalPorts, portIndex, farEndIndex);
    }
}

void Capsule_Ink::save_history( Capsule_Ink::State compositeState, Capsule_Ink::State subState )
{
    history[compositeState] = subState;
}

bool Capsule_Ink::check_history( Capsule_Ink::State compositeState, Capsule_Ink::State subState )
{
    return history[compositeState] == subState;
}

void Capsule_Ink::entryaction_____InkStateMachine__State1__State1_entry( const UMLRTInMessage & msg )
{
    //void * rtdata = *(void * *)msg.signal.getPayload();
}

void Capsule_Ink::transitionaction_____InkStateMachine__Transition2__Chain3__inkTimeout( const UMLRTInMessage & msg )
{
    //void * rtdata = *(void * *)msg.signal.getPayload();
    inkPresent().cartridgePresent(timeoutCount);
    inkLevel().inkLevel(timeoutCount).send();
    ++timeoutCount;
}

void Capsule_Ink::transitionaction_____InkStateMachine__initialize__Chain1__computerInit( const UMLRTInMessage & msg )
{
    //void * rtdata = *(void * *)msg.signal.getPayload();
        printf("%s: initialize - start the interval timer\n", getName());

        // Create a timer that is going to fire every 1 seconds.
        intervalTimerId = timer().informEvery(UMLRTTimespec(1,0));
        if (!intervalTimerId.isValid())
        {
            context()->perror("ERROR:%s: could not create interval timer", getName());
        }
}

void Capsule_Ink::actionchain_____InkStateMachine__Transition2__Chain3( const UMLRTInMessage & msg )
{
    transitionaction_____InkStateMachine__Transition2__Chain3__inkTimeout( msg );
    entryaction_____InkStateMachine__State1__State1_entry( msg );
}

void Capsule_Ink::actionchain_____InkStateMachine__initialize__Chain1( const UMLRTInMessage & msg )
{
    transitionaction_____InkStateMachine__initialize__Chain1__computerInit( msg );
    entryaction_____InkStateMachine__State1__State1_entry( msg );
}

Capsule_Ink::State Capsule_Ink::state_____InkStateMachine__State1( const UMLRTInMessage & msg )
{
    switch( msg.destPort->role()->id )
    {
    case port_timer:
        switch( msg.signal.getId() )
        {
        case UMLRTTimerProtocol::signal_timeout:
            actionchain_____InkStateMachine__Transition2__Chain3( msg );
            return InkStateMachine_State1;
        }
        return currentState;
    }
    return currentState;
}

static const UMLRTCommsPortRole portroles_border[] = 
{
    {
        Capsule_Ink::port_inkPresent,
        "InkPresentProtocol",
        "inkPresent",
        NULL, // registeredName
        1,
        false, // automatic
        false, // conjugated
        false, // locked
        true, // notification
        false, // sap
        false, // spp
        true, // wired
    },
    {
        Capsule_Ink::port_inkLevel,
        "InkLevelProtocol",
        "inkLevel",
        NULL, // registeredName
        1,
        false, // automatic
        false, // conjugated
        false, // locked
        true, // notification
        false, // sap
        false, // spp
        true, // wired
    }
};
static const UMLRTCommsPortRole portroles_internal[] = 
{
        {
            Capsule_Ink::port_timer,
            "UMLRTTimerProtocol",
            "timer",
            NULL, // registeredName
            0,
            false, // automatic
            false, // conjugated
            false, // locked
            false, // notification
            false, // sap
            false, // spp
            false, // wired
       },
        {
            Capsule_Ink::port_inkStatus,
            "TonerStatusProtocol",
            "inkStatus",
            "InkStatus", // registeredName
            0,
            true, // automatic
            false, // conjugated
            false, // locked
            true, // notification
            false, // sap
            true, // spp
            false, // wired
       }
};

static void instantiate_Ink( const UMLRTRtsInterface * rtsif, UMLRTSlot * slot, const UMLRTCommsPort * * borderPorts)
{
    const UMLRTCommsPort * internalPorts = rtsif->createInternalPorts( slot, &Ink, Ink.numPortRolesInternal, Ink.portRolesInternal);
    slot->capsule = new Capsule_Ink( rtsif, &Ink, slot, borderPorts, internalPorts, false );
}

const UMLRTCapsuleClass Ink = 
{
    "Ink",
    NULL,
    instantiate_Ink,
    0,
    NULL,
    2,
    portroles_border,
    1,
    portroles_internal
};
