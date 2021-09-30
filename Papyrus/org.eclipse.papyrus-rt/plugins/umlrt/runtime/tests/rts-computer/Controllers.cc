/*******************************************************************************
* Copyright (c) 2015 Zeligsoft (2009) Limited  and others.
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v10.html
*******************************************************************************/

#include "Controllers.hh"

#include "Computer.hh"
#include "Top.hh"
#include "UsbDevice.hh"
#include "UsbHub.hh"
#include "umlrtcapsuleclass.hh"
#include "umlrtcapsulepart.hh"
#include "umlrtcommsport.hh"
#include "umlrtcommsportfarend.hh"
#include "umlrtcontroller.hh"
#include "umlrtframeservice.hh"
#include "umlrtmessagequeue.hh"
#include "umlrtslot.hh"
#include <cstddef>

static Capsule_Top top( UMLRTFrameService::getRtsInterface(), &Top, &DefaultController_slots[InstId_Top], NULL, NULL, true );
static UMLRTCommsPortFarEnd internalfarEndList_Top_computer[] = 
{
        {
            0,
            &borderports_Top_computer_optionalUsb_0[Capsule_UsbDevice::borderport_inkPresent]
        },
        {
            0,
            &borderports_Top_computer_optionalUsb_1[Capsule_UsbDevice::borderport_inkPresent]
        },
        {
            0,
            &borderports_Top_computer_optionalUsb_2[Capsule_UsbDevice::borderport_inkPresent]
        },
        {
            0,
            &borderports_Top_computer_pluginUsb_0[Capsule_UsbDevice::borderport_inkPresent]
        },
        {
            0,
            &borderports_Top_computer_pluginUsb_1[Capsule_UsbDevice::borderport_inkPresent]
        },
        {
            0,
            &borderports_Top_computer_pluginUsb_2[Capsule_UsbDevice::borderport_inkPresent]
        },
        {
            0, // computer SAP 'inkStatus' has 1 far-end
            NULL
        },
        {
            0, // computer SAP 'tonerStatus' has 1 far-end
            NULL
        },
        {
            0, // computer SAP 'printerStatus' has 1 far-end
            NULL
        },
        {
            0, // computer SPP 'computerStatus' has 4 far-end
            NULL
        },
        {
            0, // computer SPP 'computerStatus' has 4 far-end
            NULL
        },
        {
            0, // computer SPP 'computerStatus' has 4 far-end
            NULL
        },
        {
            0, // computer SPP 'computerStatus' has 4 far-end
            NULL
        },
        {
            0, // computer wired 'staticPort' has 1 far-end
            &borderports_Top_computer_usbHub[Capsule_UsbHub::borderport_staticPort]
        },
};
UMLRTCommsPort internalports_Top_computerArray[] =
{
    {
        &Computer,
        Capsule_Computer::internalport_frame,
        &DefaultController_slots[InstId_Top_computer],
        0,
        NULL,
        NULL, // deferQueue
        NULL, // registeredName
        NULL, // registrationOverride
        false, // automatic
        false, // border
        true, // generated
        false, // locked
        false, // notification
        false, // proxy
        false, // relay
        false, // sap
        false, // spp
        false, // unbound
        false, // wired
    },
    {
        &Computer,
        Capsule_Computer::internalport_timer,
        &DefaultController_slots[InstId_Top_computer],
        0,
        NULL,
        NULL, // deferQueue
        NULL, // registeredName
        NULL, // registrationOverride
        false, // automatic
        false, // border
        true, // generated
        false, // locked
        false, // notification
        false, // proxy
        false, // relay
        false, // sap
        false, // spp
        false, // unbound
        false, // wired
    },
    {
        &Computer,
        Capsule_Computer::internalport_inkPresent,
        &DefaultController_slots[InstId_Top_computer],
        6,
        internalfarEndList_Top_computer,
        NULL, // deferQueue
        NULL, // registeredName
        NULL, // registrationOverride
        false, // automatic
        false, // border
        true, // generated
        false, // locked
        true, // notification
        false, // proxy
        false, // relay
        false, // sap
        false, // spp
        false, // unbound
        true, // wired
    },
    {
        &Computer,
        Capsule_Computer::internalport_inkStatus,
        &DefaultController_slots[InstId_Top_computer],
        1,
        &internalfarEndList_Top_computer[6],
        NULL, // deferQueue
        NULL, // registeredName
        (char*)"InkStatusOverride", // registrationOverride
        false, // automatic
        false, // border
        true, // generated
        false, // locked
        true, // notification
        false, // proxy
        false, // relay
        true, // sap
        false, // spp
        false, // unbound
        false, // wired
    },
    {
        &Computer,
        Capsule_Computer::internalport_tonerStatus,
        &DefaultController_slots[InstId_Top_computer],
        1,
        &internalfarEndList_Top_computer[7],
        NULL, // deferQueue
        NULL, // registeredName
        (char*)"TonerStatusOverride", // registrationOverride
        true, // automatic
        false, // border
        true, // generated
        false, // locked
        true, // notification
        false, // proxy
        false, // relay
        true, // sap
        false, // spp
        false, // unbound
        false, // wired
    },
    {
        &Computer,
        Capsule_Computer::internalport_printerStatus,
        &DefaultController_slots[InstId_Top_computer],
        1,
        &internalfarEndList_Top_computer[8],
        NULL, // deferQueue
        NULL,
        (char*)"PrinterStatusOverride", // registrationOverride
        true, // automatic
        false, // border
        true, // generated
        true, // locked
        true, // notification
        false, // proxy
        false, // relay
        true, // sap
        false, // spp
        false, // unbound
        false, // wired
    },
    {
        &Computer,
        Capsule_Computer::internalport_computerStatus,
        &DefaultController_slots[InstId_Top_computer],
        4,
        &internalfarEndList_Top_computer[9],
        NULL, // deferQueue
        NULL, // registeredName
        NULL, // registrationOverride
        false, // automatic
        false, // border
        true, // generated
        false, // locked
        true, // notification
        false, // proxy
        false, // relay
        false, // sap
        true, // spp
        false, // unbound
        false, // wired
    },
    {
        &Computer,
        Capsule_Computer::internalport_staticPort,
        &DefaultController_slots[InstId_Top_computer],
        1,
        &internalfarEndList_Top_computer[13],
        NULL, // deferQueue
        NULL, // registeredName
        NULL, // registrationOverride
        false, // automatic
        false, // border
        true, // generated
        false, // locked
        true, // notification
        false, // proxy
        false, // relay
        false, // sap
        true, // spp
        false, // unbound
        true, // wired
    },
};

const UMLRTCommsPort * internalports_Top_computer[] =
{
        &internalports_Top_computerArray[0],
        &internalports_Top_computerArray[1],
        &internalports_Top_computerArray[2],
        &internalports_Top_computerArray[3],
        &internalports_Top_computerArray[4],
        &internalports_Top_computerArray[5],
        &internalports_Top_computerArray[6],
        &internalports_Top_computerArray[7],
};

UMLRTController DefaultController_( "DefaultController", 9, DefaultController_slots );
UMLRTController Controller1_( "Controller1", 9, DefaultController_slots );
UMLRTController Controller2_( "Controller2", 9, DefaultController_slots );
UMLRTController Controller3_( "Controller3", 9, DefaultController_slots );
UMLRTController Controller4_( "Controller4", 9, DefaultController_slots );
UMLRTController Controller5_( "Controller5", 9, DefaultController_slots );
UMLRTController Controller6_( "Controller6", 9, DefaultController_slots );
UMLRTController Controller7_( "Controller7", 9, DefaultController_slots );
UMLRTController Controller8_( "Controller8", 9, DefaultController_slots );
UMLRTController Controller9_( "Controller9", 9, DefaultController_slots );
UMLRTController Controller10_( "Controller10", 9, DefaultController_slots );
UMLRTController Controller11_( "Controller11", 9, DefaultController_slots );
UMLRTController Controller12_( "Controller12", 9, DefaultController_slots );

UMLRTController * DefaultController = &DefaultController_;
UMLRTController * Controller1 = &Controller1_;
UMLRTController * Controller2 = &Controller2_;
UMLRTController * Controller3 = &Controller3_;
UMLRTController * Controller4 = &Controller4_;
UMLRTController * Controller5 = &Controller5_;
UMLRTController * Controller6 = &Controller6_;
UMLRTController * Controller7 = &Controller7_;
UMLRTController * Controller8 = &Controller8_;
UMLRTController * Controller9 = &Controller9_;
UMLRTController * Controller10 = &Controller10_;
UMLRTController * Controller11 = &Controller11_;
UMLRTController * Controller12 = &Controller12_;

static UMLRTSlot * slots_Top[] = 
{
    &DefaultController_slots[InstId_Top_computer]
};
static UMLRTCapsulePart parts_Top[] = 
{
    {
        &Top,
        Capsule_Top::part_computer,
        1,
        &slots_Top[0]
    }
};
static UMLRTSlot * slots_Top_computer[] = 
{
    &DefaultController_slots[InstId_Top_computer_usbHub],
    &DefaultController_slots[InstId_Top_computer_optionalUsb_0],
    &DefaultController_slots[InstId_Top_computer_optionalUsb_1],
    &DefaultController_slots[InstId_Top_computer_optionalUsb_2],
    &DefaultController_slots[InstId_Top_computer_pluginUsb_0],
    &DefaultController_slots[InstId_Top_computer_pluginUsb_1],
    &DefaultController_slots[InstId_Top_computer_pluginUsb_2]
};
static UMLRTCapsulePart parts_Top_computer[] = 
{
    {
        &Computer,
        Capsule_Computer::part_usbHub,
        1,
        &slots_Top_computer[0]
    },
    {
        &Computer,
        Capsule_Computer::part_optionalUsb,
        3,
        &slots_Top_computer[1]
    },
    {
        &Computer,
        Capsule_Computer::part_pluginUsb,
        3,
        &slots_Top_computer[4]
    }
};
static UMLRTCommsPortFarEnd borderfarEndList_Top_computer_pluginUsb_0[] =
{
    {
        3,
        &internalports_Top_computerArray[Capsule_Computer::internalport_inkPresent]
    },
    {
        3,
        &borderports_Top_computer_usbHub[Capsule_UsbHub::borderport_usbPort]
    }
};
static UMLRTCommsPortFarEnd borderfarEndList_Top_computer_pluginUsb_1[] =
{
    {
        4,
        &internalports_Top_computerArray[Capsule_Computer::internalport_inkPresent]
    },
    {
        4,
        &borderports_Top_computer_usbHub[Capsule_UsbHub::borderport_usbPort]
    }
};
static UMLRTCommsPortFarEnd borderfarEndList_Top_computer_pluginUsb_2[] =
{
    {
        5,
        &internalports_Top_computerArray[Capsule_Computer::internalport_inkPresent]
    },
    {
        5,
        &borderports_Top_computer_usbHub[Capsule_UsbHub::borderport_usbPort]
    }
};
UMLRTCommsPort borderports_Top_computer_pluginUsb_0[] = 
{
    {
        &UsbDevice,
        Capsule_UsbDevice::port_inkPresent,
        &DefaultController_slots[InstId_Top_computer_pluginUsb_0],
        1,
        borderfarEndList_Top_computer_pluginUsb_0,
        NULL, // deferQueue
        NULL, // registeredName
        NULL, // registrationOverride
        false, // automatic
        true, // border
        true, // generated
        false, // locked
        true, // notification
        false, // proxy
        false, // relay
        false, // sap
        false, // spp
        false, // unbound
        true, // wired
    },
    {
        &UsbDevice,
        Capsule_UsbDevice::port_usbPort,
        &DefaultController_slots[InstId_Top_computer_pluginUsb_0],
        1,
        &borderfarEndList_Top_computer_pluginUsb_0[1],
        NULL, // deferQueue
        NULL, // registeredName
        NULL, // registrationOverride
        false, // automatic
        true, // border
        true, // generated
        false, // locked
        true, // notification
        false, // proxy
        false, // relay
        false, // sap
        false, // spp
        false, // unbound
        true, // wired
    }
};
UMLRTCommsPort borderports_Top_computer_pluginUsb_1[] = 
{
    {
        &UsbDevice,
        Capsule_UsbDevice::port_inkPresent,
        &DefaultController_slots[InstId_Top_computer_pluginUsb_1],
        1,
        borderfarEndList_Top_computer_pluginUsb_1,
        NULL, // deferQueue
        NULL, // registeredName
        NULL, // registrationOverride
        false, // automatic
        true, // border
        true, // generated
        false, // locked
        true, // notification
        false, // proxy
        false, // relay
        false, // sap
        false, // spp
        false, // unbound
        true, // wired
    },
    {
        &UsbDevice,
        Capsule_UsbDevice::port_usbPort,
        &DefaultController_slots[InstId_Top_computer_pluginUsb_1],
        1,
        &borderfarEndList_Top_computer_pluginUsb_1[1],
        NULL, // deferQueue
        NULL, // registeredName
        NULL, // registrationOverride
        false, // automatic
        true, // border
        true, // generated
        false, // locked
        true, // notification
        false, // proxy
        false, // relay
        false, // sap
        false, // spp
        false, // unbound
        true, // wired
    }
};
UMLRTCommsPort borderports_Top_computer_pluginUsb_2[] = 
{
    {
        &UsbDevice,
        Capsule_UsbDevice::port_inkPresent,
        &DefaultController_slots[InstId_Top_computer_pluginUsb_2],
        1,
        borderfarEndList_Top_computer_pluginUsb_2,
        NULL, // deferQueue
        NULL, // registeredName
        NULL, // registrationOverride
        false, // automatic
        true, // border
        true, // generated
        false, // locked
        true, // notification
        false, // proxy
        false, // relay
        false, // sap
        false, // spp
        false, // unbound
        true, // wired
    },
    {
        &UsbDevice,
        Capsule_UsbDevice::port_usbPort,
        &DefaultController_slots[InstId_Top_computer_pluginUsb_2],
        1,
        &borderfarEndList_Top_computer_pluginUsb_2[1],
        NULL, // deferQueue
        NULL, // registeredName
        NULL, // registrationOverride
        false, // automatic
        true, // border
        true, // generated
        false, // locked
        true, // notification
        false, // proxy
        false, // relay
        false, // sap
        false, // spp
        false, // unbound
        true, // wired
    }
};
static UMLRTCommsPortFarEnd borderfarEndList_Top_computer_optionalUsb_0[] = 
{
    {
        0,
        &internalports_Top_computerArray[Capsule_Computer::internalport_inkPresent]
    },
    {
        0,
        &borderports_Top_computer_usbHub[Capsule_UsbHub::borderport_usbPort]
    }
};
static UMLRTCommsPortFarEnd borderfarEndList_Top_computer_optionalUsb_1[] =
{
    {
        1,
        &internalports_Top_computerArray[Capsule_Computer::internalport_inkPresent]
    },
    {
        1,
        &borderports_Top_computer_usbHub[Capsule_UsbHub::borderport_usbPort]
    }
};
static UMLRTCommsPortFarEnd borderfarEndList_Top_computer_optionalUsb_2[] =
{
    {
        2,
        &internalports_Top_computerArray[Capsule_Computer::internalport_inkPresent]
    },
    {
        2,
        &borderports_Top_computer_usbHub[Capsule_UsbHub::borderport_usbPort]
    }
};
UMLRTCommsPort borderports_Top_computer_optionalUsb_0[] = 
{
    {
        &UsbDevice,
        Capsule_UsbDevice::port_inkPresent,
        &DefaultController_slots[InstId_Top_computer_optionalUsb_0],
        1,
        borderfarEndList_Top_computer_optionalUsb_0,
        NULL, // deferQueue
        NULL, // registeredName
        NULL, // registrationOverride
        false, // automatic
        true, // border
        true, // generated
        false, // locked
        true, // notification
        false, // proxy
        false, // relay
        false, // sap
        false, // spp
        false, // unbound
        true, // wired
    },
    {
        &UsbDevice,
        Capsule_UsbDevice::port_usbPort,
        &DefaultController_slots[InstId_Top_computer_optionalUsb_0],
        1,
        &borderfarEndList_Top_computer_optionalUsb_0[1],
        NULL, // deferQueue
        NULL, // registeredName
        NULL, // registrationOverride
        false, // automatic
        true, // border
        true, // generated
        false, // locked
        true, // notification
        false, // proxy
        false, // relay
        false, // sap
        false, // spp
        false, // unbound
        true, // wired
    }
};
UMLRTCommsPort borderports_Top_computer_optionalUsb_1[] = 
{
    {
        &UsbDevice,
        Capsule_UsbDevice::port_inkPresent,
        &DefaultController_slots[InstId_Top_computer_optionalUsb_1],
        1,
        borderfarEndList_Top_computer_optionalUsb_1,
        NULL, // deferQueue
        NULL, // registeredName
        NULL, // registrationOverride
        false, // automatic
        true, // border
        true, // generated
        false, // locked
        true, // notification
        false, // proxy
        false, // relay
        false, // sap
        false, // spp
        false, // unbound
        true, // wired
    },
    {
        &UsbDevice,
        Capsule_UsbDevice::port_usbPort,
        &DefaultController_slots[InstId_Top_computer_optionalUsb_1],
        1,
        &borderfarEndList_Top_computer_optionalUsb_1[1],
        NULL, // deferQueue
        NULL, // registeredName
        NULL, // registrationOverride
        false, // automatic
        true, // border
        true, // generated
        false, // locked
        true, // notification
        false, // proxy
        false, // relay
        false, // sap
        false, // spp
        false, // unbound
        true, // wired
    }
};
UMLRTCommsPort borderports_Top_computer_optionalUsb_2[] = 
{
    {
        &UsbDevice,
        Capsule_UsbDevice::port_inkPresent,
        &DefaultController_slots[InstId_Top_computer_optionalUsb_2],
        1,
        borderfarEndList_Top_computer_optionalUsb_2,
        NULL, // deferQueue
        NULL, // registeredName
        NULL, // registrationOverride
        false, // automatic
        true, // border
        true, // generated
        false, // locked
        true, // notification
        false, // proxy
        false, // relay
        false, // sap
        false, // spp
        false, // unbound
        true, // wired
    },
    {
        &UsbDevice,
        Capsule_UsbDevice::port_usbPort,
        &DefaultController_slots[InstId_Top_computer_optionalUsb_2],
        1,
        &borderfarEndList_Top_computer_optionalUsb_2[1],
        NULL, // deferQueue
        NULL, // registeredName
        NULL, // registrationOverride
        false, // automatic
        true, // border
        true, // generated
        false, // locked
        true, // notification
        false, // proxy
        false, // relay
        false, // sap
        false, // spp
        false, // unbound
        true, // wired
    }
};
static UMLRTCommsPortFarEnd borderfarEndList_Top_computer_usbHub[] = 
{
        {
            0,
            &borderports_Top_computer_optionalUsb_0[Capsule_UsbDevice::borderport_usbPort]
        },
        {
            0,
            &borderports_Top_computer_optionalUsb_1[Capsule_UsbDevice::borderport_usbPort]
        },
        {
            0,
            &borderports_Top_computer_optionalUsb_2[Capsule_UsbDevice::borderport_usbPort]
        },
        {
            0,
            &borderports_Top_computer_pluginUsb_0[Capsule_UsbDevice::borderport_usbPort]
        },
        {
            0,
            &borderports_Top_computer_pluginUsb_1[Capsule_UsbDevice::borderport_usbPort]
        },
        {
            0,
            &borderports_Top_computer_pluginUsb_2[Capsule_UsbDevice::borderport_usbPort]
        },
        {
            0,
            &internalports_Top_computerArray[Capsule_Computer::internalport_staticPort]
        },
};
UMLRTCommsPort borderports_Top_computer_usbHub[] = 
{
    {
        &UsbHub,
        Capsule_UsbHub::port_usbPort,
        &DefaultController_slots[InstId_Top_computer_usbHub],
        6,
        borderfarEndList_Top_computer_usbHub,
        NULL, // deferQueue
        NULL, // registeredName
        NULL, // registrationOverride
        false, // automatic
        true, // border
        true, // generated
        false, // locked
        true, // notification
        false, // proxy
        false, // relay
        false, // sap
        false, // spp
        false, // unbound
        true, // wired
    },
    {
        &UsbHub,
        Capsule_UsbHub::port_staticPort,
        &DefaultController_slots[InstId_Top_computer_usbHub],
        1,
        &borderfarEndList_Top_computer_usbHub[6],
        NULL, // deferQueue
        NULL, // registeredName
        NULL, // registrationOverride
        false, // automatic
        true, // border
        true, // generated
        false, // locked
        true, // notification
        false, // proxy
        false, // relay
        false, // sap
        false, // spp
        false, // unbound
        true, // wired
    },
};

static const UMLRTCommsPort * borderportlist_Top_computer_usbHub[] =
{
        &borderports_Top_computer_usbHub[0],
        &borderports_Top_computer_usbHub[1],
};

static Capsule_Computer top_computer( UMLRTFrameService::getRtsInterface(), &Computer, &DefaultController_slots[InstId_Top_computer], NULL, internalports_Top_computer, true );
static Capsule_UsbHub top_computer_usbHub( UMLRTFrameService::getRtsInterface(), &UsbHub, &DefaultController_slots[InstId_Top_computer_usbHub], borderportlist_Top_computer_usbHub, NULL, true );

UMLRTSlot DefaultController_slots[] = 
{
    {
        "Top",
        0,
        &Top,
        NULL,
        0,
        &top,
        &DefaultController_,
        1,
        parts_Top,
        0,
        NULL,
        NULL,
        true,
        false
    },
    {
        "Top.computer",
        0,
        &Computer,
        &Top,
        Capsule_Top::part_computer,
        &top_computer,
        &Controller1_,
        3,
        parts_Top_computer,
        0,
        NULL,
        NULL,
        true,
        false
    },
    {
        "Top.computer.pluginUsb[0]",
        0,
        &UsbDevice,
        &Computer,
        Capsule_Computer::part_pluginUsb,
        NULL,
        &Controller9_,
        0,
        NULL,
        2,
        borderports_Top_computer_pluginUsb_0,
        NULL,
        true,
        false
    },
    {
        "Top.computer.pluginUsb[1]",
        1,
        &UsbDevice,
        &Computer,
        Capsule_Computer::part_pluginUsb,
        NULL,
        &Controller10_,
        0,
        NULL,
        2,
        borderports_Top_computer_pluginUsb_1,
        NULL,
        true,
        false
    },
    {
        "Top.computer.pluginUsb[2]",
        2,
        &UsbDevice,
        &Computer,
        Capsule_Computer::part_pluginUsb,
        NULL,
        &Controller11_,
        0,
        NULL,
        2,
        borderports_Top_computer_pluginUsb_2,
        NULL,
        true,
        false
    },
    {
        "Top.computer.optionalUsb[0]",
        0,
        &UsbDevice,
        &Computer,
        Capsule_Computer::part_optionalUsb,
        NULL,
        &Controller2_,
        0,
        NULL,
        2,
        borderports_Top_computer_optionalUsb_0,
        NULL,
        true,
        false
    },
    {
        "Top.computer.optionalUsb[1]",
        1,
        &UsbDevice,
        &Computer,
        Capsule_Computer::part_optionalUsb,
        NULL,
        &Controller6_,
        0,
        NULL,
        2,
        borderports_Top_computer_optionalUsb_1,
        NULL,
        true,
        false
    },
    {
        "Top.computer.optionalUsb[2]",
        2,
        &UsbDevice,
        &Computer,
        Capsule_Computer::part_optionalUsb,
        NULL,
        &Controller7_,
        0,
        NULL,
        2,
        borderports_Top_computer_optionalUsb_2,
        NULL,
        true,
        false
    },
    {
        "Top.computer.usbHub",
        0,
        &UsbHub,
        &Computer,
        Capsule_Computer::part_usbHub,
        &top_computer_usbHub,
        &Controller12_,
        0,
        NULL,
        2,
        borderports_Top_computer_usbHub,
        NULL,
        true,
        false
    }
};
