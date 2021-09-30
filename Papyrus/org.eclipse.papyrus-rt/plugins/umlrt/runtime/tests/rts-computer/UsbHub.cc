/*******************************************************************************
* Copyright (c) 2015 Zeligsoft (2009) Limited  and others.
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v10.html
*******************************************************************************/

#include "UsbHub.hh"

#include "umlrtcapsule.hh"
#include "umlrtcapsuleclass.hh"
#include "umlrtcommsport.hh"
#include "umlrtcommsportrole.hh"
#include "umlrtrtsinterface.hh"
#include "umlrtslot.hh"
#include <cstddef>
#include "umlrtmessage.hh"
#include <stdio.h>

Capsule_UsbHub::Capsule_UsbHub( const UMLRTRtsInterface * rtsif, const UMLRTCapsuleClass * capsuleClass, UMLRTSlot * slot, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat )
: UMLRTCapsule( rtsif, capsuleClass, slot, border, internal, isStat ),
  usbPort(borderPorts[borderport_usbPort]),
  staticPort(borderPorts[borderport_staticPort]),
  timeoutCount(0)
{
}

void Capsule_UsbHub::unbindPort( bool isBorder, int portIndex, int farEndIndex )
{
    if (isBorder)
    {
        rtsif->unbindPort(borderPorts, portIndex, farEndIndex);
    }
    else
    {
        rtsif->unbindPort( internalPorts, portIndex, farEndIndex);
    }
}

void Capsule_UsbHub::bindPort( bool isBorder, int portIndex, int farEndIndex )
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

void Capsule_UsbHub::initialize( const UMLRTMessage & msg )
{
    usbPort.bindingNotification(true);
}

void Capsule_UsbHub::inject( const UMLRTMessage & msg )
{
    static int msg_count = 0;

    int rtdata = *(int *)msg.signal.getPayload();
    size_t size = msg.signal.getPayloadSize();

    ++msg_count;

    printf("%s: inject received msg(%d) data=%d %s\n", getName(), msg_count,
            (size < sizeof(rtdata)) ? 0 : rtdata, (size < sizeof(rtdata)) ? "undef" : "");

    if (!(msg_count%2))
    {
        if (msg.defer())
        {
            printf("%s: msg(%d) data=%d deferred ok\n", getName(), msg_count, rtdata);
        }
        else
        {
            printf("%s: msg(%d), #%d defer failed %s\n", getName(), msg_count, rtdata, context()->strerror());
        }
    }
    if (!(msg_count%7))
    {
        printf("%s: msg(%d) data=%d recall 1 to tail of queue\n", getName(), msg_count, rtdata);
        int n = usbPort.recall();
        printf("%s: recalled %d message(s)\n", getName(), n);
    }
    if (!(msg_count%17))
    {
        printf("%s: msg(%d) data=%d recall all to tail of queue\n", getName(), msg_count, rtdata);
        int n = usbPort.recallAll();
        printf("%s: recalled %d message(s)\n", getName(), n);
    }
    if (!(msg_count%29))
    {
        printf("%s: msg(%d) data=%d recall all to front of queue\n", getName(), msg_count, rtdata);
        int n = usbPort.recallAllFront();
        printf("%s: recalled %d message(s)\n", getName(), n);
    }
    if (!(msg_count%37))
    {
        printf("%s: msg(%d) data=%d purge\n", getName(), msg_count, rtdata);
        int n = usbPort.purge();
        printf("%s: purged %d message(s)\n", getName(), n);
    }
}

static const UMLRTCommsPortRole portroles_border[] = 
{
        {
            Capsule_UsbHub::port_usbPort,
            "UsbPortProtocol",
            "usbPort",
            NULL, // registeredName
            6,
            false, // automatic
            true, // conjugated
            false, // locked
            true, // notification
            false, // sap
            false, // spp
            true, // wired
        },
        {
            Capsule_UsbHub::port_staticPort,
            "UsbPortProtocol",
            "staticPort",
            NULL, // registeredName
            1,
            false, // automatic
            true, // conjugated
            false, // locked
            true, // notification
            false, // sap
            false, // spp
            true, // wired
        },
};

static void instantiate_UsbHub( const UMLRTRtsInterface * rtsif, UMLRTSlot * slot, const UMLRTCommsPort * * borderPorts )
{
    slot->capsule = new Capsule_UsbHub( rtsif, &UsbHub, slot, borderPorts, NULL, false );
}

const UMLRTCapsuleClass UsbHub = 
{
    "UsbHub",
    NULL,
    instantiate_UsbHub,
    0,
    NULL,
    2,
    portroles_border,
    0,
    NULL
};
