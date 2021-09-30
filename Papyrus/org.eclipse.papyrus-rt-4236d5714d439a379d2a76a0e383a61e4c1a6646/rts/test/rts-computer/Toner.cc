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
#include "umlrtinmessage.hh"
#include "umlrtrtsinterface.hh"
#include "umlrtslot.hh"
#include "umlrttimerprotocol.hh"
#include <cstddef>

Capsule_Toner::Capsule_Toner( const UMLRTRtsInterface * rtsif, const UMLRTCapsuleClass * capsuleClass, UMLRTSlot * slot, const UMLRTCommsPort * * border, const UMLRTCommsPort * internal, bool isStat )
: UMLRTCapsule( rtsif, capsuleClass, slot, border, internal, isStat ), timeoutCount(0)
{
}


UMLRTFrameProtocol_baserole Capsule_Toner::frame() const
{
    return UMLRTFrameProtocol_baserole( &internalPorts[internalport_frame] );
}

InkLevelProtocol_conjrole Capsule_Toner::inkLevel() const
{
    return InkLevelProtocol_conjrole( &internalPorts[internalport_inkLevel] );
}

InkPresentProtocol_baserole Capsule_Toner::inkPresent() const
{
    return InkPresentProtocol_baserole( borderPorts[borderport_inkPresent]);
}

UMLRTTimerProtocol_baserole Capsule_Toner::timer() const
{
    return UMLRTTimerProtocol_baserole( &internalPorts[internalport_timer] );
}

TonerTypeProtocol_baserole Capsule_Toner::tonerType() const
{
    return TonerTypeProtocol_baserole( borderPorts[borderport_tonerType]);
}

TonerStatusProtocol_baserole Capsule_Toner::tonerStatus() const
{
    return TonerStatusProtocol_baserole( &internalPorts[internalport_tonerStatus]);
}

TonerStatusProtocol_conjrole Capsule_Toner::computerStatus() const
{
    return TonerStatusProtocol_conjrole( &internalPorts[internalport_computerStatus]);
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

void Capsule_Toner::inject( const UMLRTInMessage & msg )
{
    int rtdata = *(int *)msg.signal.getPayload();
    size_t size = msg.signal.getPayloadSize();

    printf("Capsule_Toner::inject: got msg port '%s' - id(%d) data %d %s\n",
            msg.destPort->role()->name, msg.signal.getId(),
            (size < sizeof(rtdata)) ? 0 : rtdata, (size < sizeof(rtdata)) ? "undef" : "");

    if (msg.signal.getId() == TonerTypeProtocol::signal_tonerTypeResponse)
    {
        UMLRTCapsuleId id;
        UMLRTTimespec tm;
        int type;
        msg.decodeInit(UMLRTType_int);
        msg.decode(&type);
        //((UMLRTInSignal &)msg.signal).decode(&decodeInfo, &id, UMLRTType_UMLRTCapsuleId);
        //((UMLRTInSignal &)msg.signal).decode(&decodeInfo, &tm, UMLRTType_UMLRTTimespec);
        fprintf(stdout, "%s: tonerTypeResponse: type ", getName());
        UMLRTType_int->fprintf(stdout, UMLRTType_int, &type, 0/*nest*/, 1/*arraySize*/);
        //fprintf(stdout, " capsule ");
        //UMLRTType_UMLRTCapsuleId->fprintf(stdout, UMLRTType_UMLRTCapsuleId, &id);
        //fprintf(stdout, " time ");
        //UMLRTType_UMLRTTimespec->fprintf(stdout, UMLRTType_UMLRTTimespec, &tm);
        fprintf(stdout, "\n");
    }


    switch( currentState )
    {
    case TonerStateMachine_State1:
        currentState = state_____TonerStateMachine__State1( msg );
        break;
    case SPECIAL_INTERNAL_STATE_UNVISITED:
        break;
    }
}

void Capsule_Toner::initialize( const UMLRTInMessage & msg )
{
    printf("%s: initialize - start the interval timer\n", getName());

    // Create a timer that is going to fire every 1 seconds.
    intervalTimerId = timer().informEvery(UMLRTTimespec(1,0));
    if (!intervalTimerId.isValid())
    {
        context()->perror("ERROR:%s: could not create interval timer", getName());
    }
    actionchain_____TonerStateMachine__initialize__Chain1( msg );
    currentState = TonerStateMachine_State1;

    inkPresent().bindingNotification(true); // Enable rtBound/rtUnbound notify.
}




void Capsule_Toner::save_history( Capsule_Toner::State compositeState, Capsule_Toner::State subState )
{
    history[compositeState] = subState;
}

bool Capsule_Toner::check_history( Capsule_Toner::State compositeState, Capsule_Toner::State subState )
{
    return history[compositeState] == subState;
}

void Capsule_Toner::entryaction_____TonerStateMachine__State1__State1_entry( const UMLRTInMessage & msg )
{
    //int rtdata = *(int *)msg.signal.getPayload();
}

void Capsule_Toner::transitionaction_____TonerStateMachine__Transition2__Chain3__tonerTimeout( const UMLRTInMessage & msg )
{
    //void * rtdata = *(void * *)msg.signal.getPayload();
    UMLRTCapsuleId id = frame().me();
    UMLRTTimespec tm;
    UMLRTTimespec::getClock(&tm);
    tonerType().tonerType(timeoutCount, id, tm ).send();
    //inkLevel().inkLevelResponse(timeoutCount).send();
    ++timeoutCount;
}

void Capsule_Toner::transitionaction_____TonerStateMachine__initialize__Chain1__computerInit( const UMLRTInMessage & msg )
{
    //void * rtdata = *(void * *)msg.signal.getPayload();
//        printf("%s: initialize - start the interval timer\n", getName());
//
//        // Create a timer that is going to fire every 1 seconds.
//        intervalTimerId = timer().informEvery(UMLRTTimespec(1,0));
//        if (!intervalTimerId.isValid())
//        {
//            context()->perror("ERROR:%s: could not create interval timer", getName());
//        }
}

void Capsule_Toner::transitionaction_____TonerStateMachine__inkLevelReceived__Chain4__inkLevelOpaqueBehaviour( const UMLRTInMessage & msg )
{
    int rtdata = *(int *)msg.signal.getPayload();
    printf("%s: inkLevel rx %d\n", getName(), rtdata);
}

void Capsule_Toner::actionchain_____TonerStateMachine__Transition2__Chain3( const UMLRTInMessage & msg )
{
    transitionaction_____TonerStateMachine__Transition2__Chain3__tonerTimeout( msg );
    entryaction_____TonerStateMachine__State1__State1_entry( msg );
}

void Capsule_Toner::actionchain_____TonerStateMachine__initialize__Chain1( const UMLRTInMessage & msg )
{
    transitionaction_____TonerStateMachine__initialize__Chain1__computerInit( msg );
    entryaction_____TonerStateMachine__State1__State1_entry( msg );
}

void Capsule_Toner::actionchain_____TonerStateMachine__inkLevelReceived__Chain4( const UMLRTInMessage & msg )
{
    transitionaction_____TonerStateMachine__inkLevelReceived__Chain4__inkLevelOpaqueBehaviour( msg );
    entryaction_____TonerStateMachine__State1__State1_entry( msg );
}

Capsule_Toner::State Capsule_Toner::state_____TonerStateMachine__State1( const UMLRTInMessage & msg )
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
        Capsule_Toner::port_inkPresent,
        "InkPresentProtocol",
        "inkPresent",
        NULL, // registeredName
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
        Capsule_Toner::port_inkLevel,
        "InkLevelProtocol",
        "inkLevel",
        NULL, // registeredName
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
        (char*)"TonerStatus", // registeredName
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
        (char*)"ComputerStatus", // registeredName
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
    const UMLRTCommsPort * internalPorts = rtsif->createInternalPorts( slot, &Toner, Toner.numPortRolesInternal, Toner.portRolesInternal );
    rtsif->connectPorts( &internalPorts[Capsule_Toner::internalport_inkLevel], 0, &slot->parts[Capsule_Toner::part_ink].slots[0]->ports[Capsule_Ink::borderport_inkLevel], 0 );
    rtsif->connectPorts( &internalPorts[Capsule_Toner::internalport_inkLevel], 1, &slot->parts[Capsule_Toner::part_ink].slots[1]->ports[Capsule_Ink::borderport_inkLevel], 0 );
    rtsif->connectPorts( &internalPorts[Capsule_Toner::internalport_inkLevel], 2, &slot->parts[Capsule_Toner::part_ink].slots[2]->ports[Capsule_Ink::borderport_inkLevel], 0 );
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
