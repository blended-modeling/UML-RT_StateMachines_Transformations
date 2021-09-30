/*******************************************************************************
* Copyright (c) 2015 Zeligsoft (2009) Limited  and others.
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v10.html
*******************************************************************************/

#include "UsbDevice.hh"

#include "umlrtcapsuleclass.hh"
#include "umlrtcommsport.hh"
#include "umlrtcommsportrole.hh"
#include "umlrtrtsinterface.hh"
#include "umlrtslot.hh"
#include "umlrtmessage.hh"
#include <cstddef>
#include <stdio.h>

Capsule_UsbDevice::Capsule_UsbDevice( const UMLRTRtsInterface * rtsif, const UMLRTCapsuleClass * capsuleClass, UMLRTSlot * slot, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat )
: UMLRTCapsule( rtsif, capsuleClass, slot, border, internal, isStat ),
inkPresent(borderPorts[borderport_inkPresent]),
usbPort(borderPorts[borderport_usbPort])
{
}

void Capsule_UsbDevice::bindPort( bool isBorder, int portIndex, int farEndIndex )
{
    // Should have code here, but not used in this test app yet.
}

void Capsule_UsbDevice::unbindPort( bool isBorder, int portIndex, int farEndIndex )
{
    // Should have code here, but not used in this test app yet.
}

void Capsule_UsbDevice::initialize( const UMLRTMessage & msg )
{
}

void Capsule_UsbDevice::inject( const UMLRTMessage & msg )
{
    //int rtdata = *(int *)msg.signal.getPayload();
}

static const UMLRTCommsPortRole portroles_border[] = 
{
    {
        Capsule_UsbDevice::port_inkPresent,
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
        Capsule_UsbDevice::port_usbPort,
        "UsbPortProtocol",
        "usbPort",
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

static void instantiate_UsbDevice( const UMLRTRtsInterface * rtsif, UMLRTSlot * slot, const UMLRTCommsPort * * borderPorts )
{
    slot->capsule = new Capsule_UsbDevice( rtsif, &UsbDevice, slot, borderPorts, NULL, false );
}
const UMLRTCapsuleClass UsbDevice = 
{
    "UsbDevice",
    NULL,
    instantiate_UsbDevice,
    0,
    NULL,
    2,
    portroles_border,
    0,
    NULL
};
