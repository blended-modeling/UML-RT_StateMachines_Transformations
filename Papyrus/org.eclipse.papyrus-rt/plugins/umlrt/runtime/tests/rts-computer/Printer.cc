/*******************************************************************************
* Copyright (c) 2015 Zeligsoft (2009) Limited  and others.
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v10.html
*******************************************************************************/

#include "Printer.hh"

#include "Toner.hh"
#include "UsbDevice.hh"
#include "umlrtcapsuleclass.hh"
#include "umlrtcapsulepart.hh"
#include "umlrtslot.hh"
#include "umlrtcapsuleclass.hh"
#include "umlrtcapsulepart.hh"
#include "umlrtcapsulerole.hh"
#include "umlrtcommsport.hh"
#include "umlrtcommsportrole.hh"
#include "umlrtrtsinterface.hh"
#include "umlrtslot.hh"
#include "umlrtcapsuleid.hh"
#include "umlrtframeprotocol.hh"
#include "umlrttimespec.hh"
#include "umlrtmessage.hh"
#include "umlrtobjectclass.hh"
#include "DataType1.hh"
#include <cstddef>

Capsule_Printer::Capsule_Printer( const UMLRTRtsInterface * rtsif, const UMLRTCapsuleClass * capsuleClass, UMLRTSlot * slot, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat )
: Capsule_UsbDevice( rtsif, capsuleClass, slot, border, internal, isStat ),
  timer(internalPorts[internalport_timer]),
  tonerType(internalPorts[internalport_tonerType]),
  printerStatus(internalPorts[internalport_printerStatus]),
  computerStatus(internalPorts[internalport_computerStatus]),
  inkPresent(borderPorts[borderport_inkPresent]),
  usbPort(borderPorts[borderport_usbPort]),
  inkPresent2(borderPorts[borderport_inkPresent2]),
  timeoutCount(0)
{
}


UMLRTFrameProtocol_baserole Capsule_Printer::frame() const
{
    return UMLRTFrameProtocol_baserole( internalPorts[internalport_frame] );
}

const UMLRTCapsulePart * Capsule_Printer::toner() const
{
    return &slot->parts[part_toner];
}

void Capsule_Printer::initialize( const UMLRTMessage & msg )
{
#define dt ((DataType1 *)msg.getParam(0))

    if (dt)
    {
        // Only gets initialize data from an incarnate from Computer - not from static capsule creation
        // as part of a Scanner.
        UMLRTObject_fprintf(stdout, &RTType_DataType1, dt);
        fprintf(stdout, "\n");
    }
    // Create a timer that is going to fire every 1 seconds.
    intervalTimerId = timer.informEvery(UMLRTTimespec(1,0));
    if (!intervalTimerId.isValid())
    {
        context()->perror("ERROR:%s: could not create interval timer", getName());
    }
    inkPresent.bindingNotification(true); // Enable rtBound/rtUnbound notify.
}


void Capsule_Printer::unbindPort( bool isBorder, int portIndex, int farEndIndex)
{
    if (isBorder)
    {
        switch(portIndex)
        {
        case borderport_inkPresent:
            rtsif->unbindSubcapsulePort( true/*isBorder*/, slot->parts[part_toner].slots[0]->capsule, Capsule_Toner::borderport_inkPresent, farEndIndex);
            break;

        case borderport_usbPort:
        case borderport_inkPresent2:
            rtsif->unbindPort( borderPorts, portIndex, farEndIndex);
            break;
        }
    }
    else
    {
        // Same for all internal ports.
        rtsif->unbindPort( internalPorts, portIndex, farEndIndex);
    }
}


void Capsule_Printer::bindPort( bool isBorder, int portIndex, int farEndIndex )
{
    if (isBorder)
    {
        switch(portIndex)
        {
        case borderport_inkPresent:
            rtsif->connectRelayPort( borderPorts[borderport_inkPresent], farEndIndex, &slot->parts[part_toner].slots[0]->ports[Capsule_Toner::borderport_inkPresent], farEndIndex);
            rtsif->bindSubcapsulePort( true/*isBorder*/, slot->parts[part_toner].slots[0]->capsule, Capsule_Toner::borderport_inkPresent, farEndIndex);
            break;

        case borderport_usbPort:
        case borderport_inkPresent2:
            rtsif->bindPort( borderPorts, portIndex, farEndIndex);
            break;
        }
    }
    else
    {
        // Same for all internal ports.
        rtsif->bindPort( internalPorts, portIndex, farEndIndex);
    }
}

void Capsule_Printer::inject( const UMLRTMessage & msg )
{
    size_t size = msg.signal.getPayloadSize();

    printf("Capsule_Printer::inject: got msg port '%s' port-id(%d) signal-id(%d)(%s) payloadSize(%lu)\n",
            msg.sap()->getName(), msg.destPort->role()->id, msg.getSignalId(), msg.getSignalName(), size);

    switch( msg.destPort->role()->id )
    {
    case port_timer:
        switch( msg.signal.getId() )
        {
        case UMLRTTimerProtocol::signal_timeout:
            printf("Capsule_Printer::inject: sending messages out all ports - including printerStatus port\n");
            UMLRTCapsuleId id = frame().me();
            UMLRTTimespec tm;
            UMLRTTimespec::getclock(tm);
            tonerType.tonerTypeResponse(timeoutCount, id, tm).send();
            usbPort.deviceId(timeoutCount).send();
            printerStatus.tonerReady(timeoutCount).send();
            inkPresent2.cartridgePresent(timeoutCount).send();
            timeoutCount++;
            break;
        }
        break;
    case port_tonerType:
        switch( msg.signal.getId() )
        {
        case TonerTypeProtocol::signal_tonerType:
#define id ((UMLRTCapsuleId *)msg.getParam(0))
            fprintf(stdout, "%s: tonerType: got UMLRTCapsuleId (%s)\n", getName(), id->getCapsule()->getName());
        }
    }
}


static const UMLRTCapsuleRole roles[] = 
{
    {
        "toner",
        &Toner,
        1,
        1,
        false,
        false
    }
};
static const UMLRTCommsPortRole portroles_border[] = 
{
    {
        Capsule_Printer::port_inkPresent,
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
    },
    {
        Capsule_Printer::port_usbPort,
        "UsbPortProtocol",
        "usbPort",
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
        Capsule_Printer::port_inkPresent2,
        "InkPresentProtocol",
        "inkPresent2",
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
};
static const UMLRTCommsPortRole portroles_internal[] = 
{
    {
        Capsule_Printer::port_frame,
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
        Capsule_Printer::port_timer,
        "UMLRTTimerProtocol",
        "timer",
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
        Capsule_Printer::port_tonerType,
        "TonerTypeProtocol",
        "tonerType",
        NULL, // registrationOverride
        1,
        false, // automatic
        true, // conjugated
        false, // locked
        true, // notification
        false, // sap
        false, // spp
        true, // wired
    },
    {
        Capsule_Printer::port_printerStatus,
        "TonerStatusProtocol",
        "printerStatus",
        "PrinterStatusOverride", // registrationOverride
        1,
        false, // automatic
        false, // conjugated
        true, // locked
        true, // notification
        false, // sap
        true, // spp
        false, // wired
   },
   {
       Capsule_Printer::port_computerStatus,
       "TonerStatusProtocol",
       "computerStatus",
       "ComputerStatusOverride", // registrationOverride
       1,
       true, // automatic
       true, // conjugated
       false, // locked
       true, // notification
       true, // sap
       false, // spp
       false, // wired
   },
};


static void instantiate_Printer( const UMLRTRtsInterface * rtsif, UMLRTSlot * slot, const UMLRTCommsPort * * borderPorts )
{
    const UMLRTCommsPort * * internalPorts = rtsif->createInternalPorts( slot, &Printer );
    rtsif->connectPorts( internalPorts[Capsule_Printer::internalport_tonerType], 0, &slot->parts[Capsule_Printer::part_toner].slots[0]->ports[Capsule_Toner::borderport_tonerType], 0 );
    rtsif->connectRelayPort( borderPorts[Capsule_Printer::borderport_inkPresent], 0, &slot->parts[Capsule_Printer::part_toner].slots[0]->ports[Capsule_Toner::borderport_inkPresent], 0);
    rtsif->connectRelayPort( borderPorts[Capsule_Printer::borderport_inkPresent], 1, &slot->parts[Capsule_Printer::part_toner].slots[0]->ports[Capsule_Toner::borderport_inkPresent], 1);
    rtsif->connectRelayPort( borderPorts[Capsule_Printer::borderport_inkPresent], 2, &slot->parts[Capsule_Printer::part_toner].slots[0]->ports[Capsule_Toner::borderport_inkPresent], 2);
    Toner.instantiate( rtsif, slot->parts[Capsule_Printer::part_toner].slots[0], rtsif->createBorderPorts(slot->parts[Capsule_Printer::part_toner].slots[0], Toner.numPortRolesBorder));
    slot->capsule = new Capsule_Printer( rtsif, &Printer, slot, borderPorts, internalPorts, false );
}

const UMLRTCapsuleClass Printer = 
{
    "Printer",
    &UsbDevice,
    instantiate_Printer,
    1,
    roles,
    3,
    portroles_border,
    5,
    portroles_internal
};
