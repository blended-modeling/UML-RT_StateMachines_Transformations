/*******************************************************************************
* Copyright (c) 2015 Zeligsoft (2009) Limited  and others.
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v10.html
*******************************************************************************/

#include "Scanner.hh"

#include "Printer.hh"
#include "UsbDevice.hh"
#include "umlrtcapsuleclass.hh"
#include "umlrtcapsulepart.hh"
#include "umlrtcapsuleclass.hh"
#include "umlrtcapsulepart.hh"
#include "umlrtcapsulerole.hh"
#include "umlrtcommsport.hh"
#include "umlrtcommsportrole.hh"
#include "umlrtrtsinterface.hh"
#include "umlrtslot.hh"
#include <cstddef>
#include "umlrtcontroller.hh"

Capsule_Scanner::Capsule_Scanner( const UMLRTRtsInterface * rtsif, const UMLRTCapsuleClass * capsuleClass, UMLRTSlot * slot, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat )
: Capsule_UsbDevice( rtsif, capsuleClass, slot, border, internal, isStat ),
  inkPresent(borderPorts[borderport_inkPresent]),
  usbPort(borderPorts[borderport_usbPort]),
  printerPort(internalPorts[internalport_printerPort]),
  timer(internalPorts[internalport_timer]),
  timeoutCount(0)
{
}

const UMLRTCapsulePart * Capsule_Scanner::printer() const
{
    return &slot->parts[part_printer];
}

void Capsule_Scanner::unbindPort( bool isBorder, int portIndex, int farEndIndex )
{
    if (isBorder)
    {
        switch(portIndex)
        {
        case borderport_inkPresent:
                 rtsif->unbindSubcapsulePort( true/*isBorder*/, slot->parts[part_printer].slots[0]->capsule, Capsule_Printer::borderport_inkPresent, farEndIndex);
            break;

        case borderport_usbPort:
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

void Capsule_Scanner::bindPort( bool isBorder, int portIndex, int farEndIndex )
{
    if (isBorder)
    {
        switch(portIndex)
        {
        case borderport_inkPresent:
            rtsif->connectRelayPort( borderPorts[borderport_inkPresent], farEndIndex, &slot->parts[part_printer].slots[0]->ports[Capsule_Printer::borderport_inkPresent], farEndIndex );
            rtsif->bindSubcapsulePort( true/*isBorder*/, slot->parts[part_printer].slots[0]->capsule, Capsule_Printer::borderport_inkPresent, farEndIndex);
            break;

        case borderport_usbPort:
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

void Capsule_Scanner::inject( const UMLRTMessage & msg )
{
    int rtdata = *(int *)msg.signal.getPayload();
    size_t size = msg.signal.getPayloadSize();

    printf("Capsule_Scanner::inject: got msg port '%s' - id(%d) data %d %s\n",
            msg.sap()->getName(), msg.signal.getId(),
            (size < sizeof(rtdata)) ? 0 : rtdata, (size < sizeof(rtdata)) ? "undef" : "");

    switch( msg.destPort->role()->id )
    {
    case port_timer:
        switch( msg.signal.getId() )
        {
        case UMLRTTimerProtocol::signal_timeout:
            printerPort.deviceIdResponse(timeoutCount);
            usbPort.deviceId(timeoutCount);
            timeoutCount++;
            break;
        }
    }

}

void Capsule_Scanner::initialize( const UMLRTMessage & msg )
{
    printf("%s: initialize - start the interval timer\n", name());

#define rtdata ( msg.getParam( 0 ) )

    if (rtdata == NULL)
    {
        printf("%s: no initialization parameter\n", name());
    }
    else
    {
        printf("%s: initialization parameter pointer %p\n", name(), rtdata);
    }
    // Create a timer that is going to fire every 1 seconds.
    intervalTimerId = timer.informEvery(UMLRTTimespec(1,0));
    if (!intervalTimerId.isValid())
    {
        context()->perror("ERROR:%s: could not create interval timer", getName());
    }
    inkPresent.bindingNotification(true); // Enable rtBound/rtUnbound notify.
}

static const UMLRTCapsuleRole roles[] = 
{
    {
        "printer",
        &Printer,
        1,
        1,
        false,
        false
    }
};
static const UMLRTCommsPortRole portroles_border[] = 
{
    {
        Capsule_Scanner::port_inkPresent,
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
    },
    {
        Capsule_Scanner::port_usbPort,
        "UsbPortProtocol",
        "usbPort",
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
        Capsule_Scanner::port_printerPort,
        "UsbPortProtocol",
        "printerPort",
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
        Capsule_Scanner::port_timer,
        "UMLRTTimerProtocol",
        "timer",
        NULL, // registeredName
        0,
        false, // automatic
        false, // conjugated
        false, // locked
        true, // notification
        false, // sap
        false, // spp
        false, // wired
    }
};

static void instantiate_Scanner( const UMLRTRtsInterface * rtsif, UMLRTSlot * slot, const UMLRTCommsPort * * borderPorts)
{
    const UMLRTCommsPort * * internalPorts = rtsif->createInternalPorts( slot, &Scanner );
    rtsif->connectPorts( internalPorts[Capsule_Scanner::internalport_printerPort], 0, &slot->parts[Capsule_Scanner::part_printer].slots[0]->ports[Capsule_Printer::borderport_usbPort], 0 );
    rtsif->connectRelayPort( borderPorts[Capsule_Scanner::borderport_inkPresent], 0, &slot->parts[Capsule_Scanner::part_printer].slots[0]->ports[Capsule_Printer::borderport_inkPresent], 0 );
    rtsif->connectRelayPort( borderPorts[Capsule_Scanner::borderport_inkPresent], 1, &slot->parts[Capsule_Scanner::part_printer].slots[0]->ports[Capsule_Printer::borderport_inkPresent], 1 );
    rtsif->connectRelayPort( borderPorts[Capsule_Scanner::borderport_inkPresent], 2, &slot->parts[Capsule_Scanner::part_printer].slots[0]->ports[Capsule_Printer::borderport_inkPresent], 2 );
    Printer.instantiate( rtsif, slot->parts[Capsule_Scanner::part_printer].slots[0], rtsif->createBorderPorts(slot->parts[Capsule_Scanner::part_printer].slots[0], Printer.numPortRolesBorder));
    slot->capsule = new Capsule_Scanner( rtsif, &Scanner, slot, borderPorts, internalPorts, false );
}

const UMLRTCapsuleClass Scanner = 
{
    "Scanner",
    &UsbDevice,
    instantiate_Scanner,
    1,
    roles,
    2,
    portroles_border,
    2,
    portroles_internal
};
