/*******************************************************************************
* Copyright (c) 2015 Zeligsoft (2009) Limited  and others.
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v10.html
*******************************************************************************/

#ifndef PRINTER_HH
#define PRINTER_HH

#include "InkPresentProtocol.hh"
#include "TonerStatusProtocol.hh"
#include "TonerTypeProtocol.hh"
#include "UsbDevice.hh"
#include "UsbPortProtocol.hh"
#include "umlrtcapsuleclass.hh"
#include "umlrtframeprotocol.hh"
#include "umlrttimerid.hh"
#include "umlrttimerprotocol.hh"
#include <cstddef>

struct UMLRTCapsulePart;
struct UMLRTCommsPort;
struct UMLRTSlot;
class UMLRTRtsInterface;
class UMLRTMessage;

class Capsule_Printer : public Capsule_UsbDevice
{
public:
    Capsule_Printer( const UMLRTRtsInterface * rtsif, const UMLRTCapsuleClass * capsuleClass, UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat );
    enum InternalPortId
    {
        internalport_frame,
        internalport_timer,
        internalport_tonerType,
        internalport_printerStatus,
        internalport_computerStatus,
    };
protected:
    UMLRTFrameProtocol_baserole frame() const;
public:
    enum BorderPortId
    {
        borderport_inkPresent,
        borderport_usbPort,
        borderport_inkPresent2
    };
protected:
    UMLRTTimerProtocol_baserole timer;
    TonerTypeProtocol::Conj tonerType;
    TonerStatusProtocol::Base printerStatus;
    TonerStatusProtocol::Conj computerStatus;
    InkPresentProtocol::Base inkPresent;
    UsbPortProtocol::Base usbPort;
    InkPresentProtocol::Base inkPresent2;
public:
    enum PartId
    {
        part_toner
    };
protected:
    const UMLRTCapsulePart * toner() const;
public:
    enum PortId
    {
        port_frame,
        port_inkPresent,
        port_timer,
        port_printerStatus,
        port_tonerType,
        port_usbPort,
        port_inkPresent2,
        port_computerStatus,
    };
    virtual void inject( const UMLRTMessage & msg );
    virtual void initialize( const UMLRTMessage & msg );
    virtual void unbindPort( bool isBorder, int portIndex, int farEndIndex );
    virtual void bindPort( bool isBorder, int portIndex, int farEndIndex );
    int timeoutCount;
    UMLRTTimerId intervalTimerId;
};
extern const UMLRTCapsuleClass Printer;

#endif

