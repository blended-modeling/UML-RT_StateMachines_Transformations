/*******************************************************************************
* Copyright (c) 2015 Zeligsoft (2009) Limited  and others.
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v10.html
*******************************************************************************/

#include "Toner.hh"

#include "Ink.hh"
#include "InkLevelProtocol.hh"
#include "TonerTypeProtocol.hh"
#include "umlrtcapsuleclass.hh"
#include "umlrtcapsuleid.hh"
#include "umlrtcapsulepart.hh"
#include "umlrtslot.hh"
#include "umlrtcapsuleclass.hh"
#include "umlrtcapsulepart.hh"
#include "umlrtcapsulerole.hh"
#include "umlrtcommsport.hh"
#include "umlrtcommsportrole.hh"
#include "umlrtframeprotocol.hh"
#include "umlrtmessage.hh"
#include "umlrtrtsinterface.hh"
#include "umlrtslot.hh"
#include "umlrttimerprotocol.hh"
#include "umlrttimespec.hh"
#include <cstddef>

Capsule_Toner::Capsule_Toner( const UMLRTRtsInterface * rtsif, const UMLRTCapsuleClass * capsuleClass, UMLRTSlot * slot, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat )
: UMLRTCapsule( rtsif, capsuleClass, slot, border, internal, isStat ),
  tonerType(borderPorts[borderport_tonerType]),
  inkPresent(borderPorts[borderport_inkPresent]),
  frame(internalPorts[internalport_frame]),
  inkLevel(internalPorts[internalport_inkLevel]),
  timer(internalPorts[internalport_timer]),
  tonerStatus(internalPorts[internalport_tonerStatus]),
  computerStatus(internalPorts[internalport_computerStatus]),
  timeoutCount(0),
  currentState(SPECIAL_INTERNAL_STATE_UNVISITED)
{
}

const UMLRTCapsulePart * Capsule_Toner::ink() const
{
    return &slot->parts[part_ink];
}

void Capsule_Toner::unbindPort( bool isBorder, int portIndex, int farEndIndex )
{
    if (isBorder)
    {
        switch(portIndex)
        {
        case borderport_inkPresent:
            // Note how farEndIndex is used as a slot index. Generated code may need an internal switch statement?
            rtsif->unbindSubcapsulePort( true/*isBorder*/, slot->parts[part_ink].slots[farEndIndex]->capsule, Capsule_Ink::borderport_inkPresent, 0);
            break;

        case borderport_tonerType:
            rtsif->unbindPort(borderPorts, portIndex, farEndIndex);
            break;
        }
    }
    else
    {
        rtsif->unbindPort( internalPorts, portIndex, farEndIndex );
    }
}

void Capsule_Toner::bindPort( bool isBorder, int portIndex, int farEndIndex )
{
    if (isBorder)
    {
        switch(portIndex)
        {
        case borderport_inkPresent:
            // Generated code may need a switch statement? Here, 'farEndIndex' is used for both the border port and slot index, but I know they are the same.
            rtsif->connectRelayPort( borderPorts[borderport_inkPresent], farEndIndex, &slot->parts[part_ink].slots[farEndIndex]->ports[Capsule_Ink::borderport_inkPresent], 0);
            rtsif->bindSubcapsulePort( true/*isBorder*/, slot->parts[part_ink].slots[farEndIndex]->capsule, Capsule_Ink::borderport_inkPresent, 0);
            break;
        case borderport_tonerType:
            rtsif->bindPort(borderPorts, portIndex, farEndIndex);
            break;
        }
    }
    else
    {
        rtsif->bindPort( internalPorts, portIndex, farEndIndex);
    }
}

void Capsule_Toner::inject( const UMLRTMessage & msg )
{
    printf("Capsule_Toner::inject: got msg port '%s' - id(%d)(%s) priority(%d)\n",
            msg.sap()->getName(), msg.getSignalId(), msg.getSignalName(), msg.getPriority());

    switch( currentState )
    {
    case TonerStateMachine_State1:
        currentState = state_____TonerStateMachine__State1( msg );
        break;
    case SPECIAL_INTERNAL_STATE_UNVISITED:
        break;
    }
}

void Capsule_Toner::initialize( const UMLRTMessage & msg )
{
    printf("%s: initialize - start the interval timer\n", getName());

    // Create a timer that is going to fire every 1 seconds.
    UMLRTTimespec tm;
    UMLRTTimespec::getclock(tm);

    intervalTimerId = timer.informEvery(UMLRTTimespec(1,0), &tm, &UMLRTType_UMLRTTimespec);
    if (!intervalTimerId.isValid())
    {
        context()->perror("ERROR:%s: could not create interval timer", getName());
    }
    actionchain_____TonerStateMachine__initialize__Chain1( msg );
    currentState = TonerStateMachine_State1;

    inkPresent.bindingNotification(true); // Enable rtBound/rtUnbound notify.
}




void Capsule_Toner::save_history( Capsule_Toner::State compositeState, Capsule_Toner::State subState )
{
    history[compositeState] = subState;
}

bool Capsule_Toner::check_history( Capsule_Toner::State compositeState, Capsule_Toner::State subState )
{
    return history[compositeState] == subState;
}

void Capsule_Toner::entryaction_____TonerStateMachine__State1__State1_entry( const UMLRTMessage & msg )
{
    //int rtdata = *(int *)msg.signal.getPayload();
}

void Capsule_Toner::transitionaction_____TonerStateMachine__Transition2__Chain4__tonerTypeResponse( const UMLRTMessage & msg )
{
#define tmspec ((UMLRTTimespec *)msg.getParam(0))
    char tmbuf[UMLRTTimespec::TIMESPEC_TOSTRING_SZ];
    fprintf(stdout, "%s: Received time (%s)\n", getName(), tmspec->toString(tmbuf, sizeof(tmbuf)));
}

void Capsule_Toner::transitionaction_____TonerStateMachine__Transition2__Chain3__tonerTimeout( const UMLRTMessage & msg )
{
    //void * rtdata = *(void * *)msg.signal.getPayload();
    UMLRTCapsuleId id = frame.me();
    UMLRTTimespec tm;
    UMLRTTimespec::getclock(&tm);
    printf("%s: Sending tonerType UMLRTCapsuleId(%s)\n", getName(), id.getCapsule()->getName());
    tonerType.tonerType(timeoutCount, id, tm).send(PRIORITY_HIGH);
    //inkLevel().inkLevelResponse(timeoutCount).send();
#define rtdata ((UMLRTTimespec *)msg.getParam(0))
    if (rtdata)
    {
        char tmbuf[UMLRTTimespec::TIMESPEC_TOSTRING_SZ];
        printf("%s (%s): Timeout contained data - time %s - priority %d\n", name(), getName(), rtdata->toString(tmbuf, sizeof(tmbuf)), msg.getPriority());
    }
    ++timeoutCount;
#undef rtdata
}

void Capsule_Toner::transitionaction_____TonerStateMachine__initialize__Chain1__computerInit( const UMLRTMessage & msg )
{
    //void * rtdata = *(void * *)msg.signal.getPayload();
        printf("%s: initialize - start the interval timer\n", getName());

        // Create a timer that is going to fire every 1 seconds.
        UMLRTTimespec tm;
        UMLRTTimespec::getclock(&tm);
        intervalTimerId = timer.informEvery(UMLRTTimespec(1,0), &tm, &UMLRTType_UMLRTTimespec, PRIORITY_LOW);
        if (!intervalTimerId.isValid())
        {
            context()->perror("ERROR:%s: could not create interval timer", getName());
        }
}

void Capsule_Toner::transitionaction_____TonerStateMachine__inkLevelReceived__Chain4__inkLevelOpaqueBehaviour( const UMLRTMessage & msg )
{
    int rtdata = *(int *)msg.signal.getPayload();
    printf("%s: inkLevel rx %d\n", getName(), rtdata);
}

void Capsule_Toner::actionchain_____TonerStateMachine__Transition2__Chain3( const UMLRTMessage & msg )
{
    transitionaction_____TonerStateMachine__Transition2__Chain3__tonerTimeout( msg );
    entryaction_____TonerStateMachine__State1__State1_entry( msg );
}

void Capsule_Toner::actionchain_____TonerStateMachine__Transition2__Chain4( const UMLRTMessage & msg )
{
    transitionaction_____TonerStateMachine__Transition2__Chain4__tonerTypeResponse( msg );
    entryaction_____TonerStateMachine__State1__State1_entry( msg );
}

void Capsule_Toner::actionchain_____TonerStateMachine__initialize__Chain1( const UMLRTMessage & msg )
{
    transitionaction_____TonerStateMachine__initialize__Chain1__computerInit( msg );
    entryaction_____TonerStateMachine__State1__State1_entry( msg );
}

void Capsule_Toner::actionchain_____TonerStateMachine__inkLevelReceived__Chain4( const UMLRTMessage & msg )
{
    transitionaction_____TonerStateMachine__inkLevelReceived__Chain4__inkLevelOpaqueBehaviour( msg );
    entryaction_____TonerStateMachine__State1__State1_entry( msg );
}

Capsule_Toner::State Capsule_Toner::state_____TonerStateMachine__State1( const UMLRTMessage & msg )
{
    switch( msg.destPort->role()->id )
    {
    case port_inkLevel:
        switch( msg.signal.getId() )
        {
        case InkLevelProtocol::signal_inkLevel:
            actionchain_____TonerStateMachine__inkLevelReceived__Chain4( msg );
            return TonerStateMachine_State1;
        }
        return currentState;
    case port_timer:
        switch( msg.signal.getId() )
        {
        case UMLRTTimerProtocol::signal_timeout:
            actionchain_____TonerStateMachine__Transition2__Chain3( msg );
            return TonerStateMachine_State1;
        }
        return currentState;
    case port_tonerType:
        switch( msg.signal.getId() )
        {
        case TonerTypeProtocol::signal_tonerTypeResponse:
            actionchain_____TonerStateMachine__Transition2__Chain4( msg );
            return TonerStateMachine_State1;
        }
        return currentState;
    }
    return currentState;
}

static const UMLRTCapsuleRole roles[] = 
{
    {
        "ink",
        &Ink,
        0,
        3,
        false,
        true
    }
};
static const UMLRTCommsPortRole portroles_border[] = 
{
    {
        Capsule_Toner::port_tonerType,
        "TonerTypeProtocol",
        "tonerType",
        NULL, // registrationOverride
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
        Capsule_Toner::port_inkPresent,
        "InkPresentProtocol",
        "inkPresent",
        NULL, // registrationOverride
        3,
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
        Capsule_Toner::port_frame,
        "UMLRTFrameProtocol",
        "frame",
        NULL, // registrationOverride
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
        Capsule_Toner::port_inkLevel,
        "InkLevelProtocol",
        "inkLevel",
        NULL, // registrationOverride
        3,
        false, // automatic
        false, // conjugated
        false, // locked
        true, // notification
        false, // sap
        false, // spp
        true, // wired
    },
    {
        Capsule_Toner::port_timer,
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
        Capsule_Toner::port_tonerStatus,
        "TonerStatusProtocol",
        "tonerStatus",
        (char*)"TonerStatusOverride", // registrationOverride
        1,
        true, // automatic
        false, // conjugated
        true, // locked
        true, // notification
        false, // sap
        true, // spp
        false, // wired
    },
    {
        Capsule_Toner::port_computerStatus,
        "TonerStatusProtocol",
        "computerStatus",
        (char*)"ComputerStatusOverride", // registrationOverride
        1,
        true, // automatic
        true, // conjugated
        true, // locked
        true, // notification
        true, // sap
        false, // spp
        false, // wired
    },

};

static void instantiate_Toner( const UMLRTRtsInterface * rtsif, UMLRTSlot * slot, const UMLRTCommsPort * * borderPorts )
{
    const UMLRTCommsPort * * internalPorts = rtsif->createInternalPorts( slot, &Toner );
    rtsif->connectPorts( internalPorts[Capsule_Toner::internalport_inkLevel], 0, &slot->parts[Capsule_Toner::part_ink].slots[0]->ports[Capsule_Ink::borderport_inkLevel], 0 );
    rtsif->connectPorts( internalPorts[Capsule_Toner::internalport_inkLevel], 1, &slot->parts[Capsule_Toner::part_ink].slots[1]->ports[Capsule_Ink::borderport_inkLevel], 0 );
    rtsif->connectPorts( internalPorts[Capsule_Toner::internalport_inkLevel], 2, &slot->parts[Capsule_Toner::part_ink].slots[2]->ports[Capsule_Ink::borderport_inkLevel], 0 );
    rtsif->connectRelayPort( borderPorts[Capsule_Toner::borderport_inkPresent], 0, &slot->parts[Capsule_Toner::part_ink].slots[0]->ports[Capsule_Ink::borderport_inkPresent], 0);
    rtsif->connectRelayPort( borderPorts[Capsule_Toner::borderport_inkPresent], 1, &slot->parts[Capsule_Toner::part_ink].slots[1]->ports[Capsule_Ink::borderport_inkPresent], 0);
    rtsif->connectRelayPort( borderPorts[Capsule_Toner::borderport_inkPresent], 2, &slot->parts[Capsule_Toner::part_ink].slots[2]->ports[Capsule_Ink::borderport_inkPresent], 0);
    slot->capsule = new Capsule_Toner( rtsif, &Toner, slot, borderPorts, internalPorts, false );
}

const UMLRTCapsuleClass Toner = 
{
    "Toner",
    NULL,
    instantiate_Toner,
    1,
    roles,
    2,
    portroles_border,
    5,
    portroles_internal
};
