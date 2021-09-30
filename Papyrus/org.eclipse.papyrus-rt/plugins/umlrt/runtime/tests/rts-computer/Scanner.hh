/*******************************************************************************
* Copyright (c) 2015 Zeligsoft (2009) Limited  and others.
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v10.html
*******************************************************************************/

#ifndef SCANNER_HH
#define SCANNER_HH

#include "InkPresentProtocol.hh"
#include "UsbDevice.hh"
#include "UsbPortProtocol.hh"
#include "umlrttimerprotocol.hh"
#include "umlrtcapsuleclass.hh"
#include "umlrttimerid.hh"

#include <cstddef>

struct UMLRTCapsulePart;
struct UMLRTCommsPort;
struct UMLRTSlot;
class UMLRTRtsInterface;
class UMLRTInMessage;

class Capsule_Scanner : public Capsule_UsbDevice
{
public:
    Capsule_Scanner( const UMLRTRtsInterface * rtsif, const UMLRTCapsuleClass * capsuleClass, UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat );
    enum BorderPortId
    {
        borderport_inkPresent,
        borderport_usbPort
    };
public:
    enum InternalPortId
    {
        internalport_printerPort,
        internalport_timer
    };
protected:
    InkPresentProtocol::Base inkPresent;
    UsbPortProtocol::Base usbPort;
    UsbPortProtocol::Conj printerPort;
    UMLRTTimerProtocol_baserole timer;

public:
    enum PartId
    {
        part_printer
    };
protected:
    const UMLRTCapsulePart * printer() const;
public:
    enum PortId
    {
        port_inkPresent,
        port_printerPort,
        port_usbPort,
        port_timer
    };
    virtual void inject( const UMLRTMessage & msg );
    virtual void initialize( const UMLRTMessage & msg );
    virtual void unbindPort( bool isBorder, int portIndex, int farEndIndex );
    virtual void bindPort( bool isBorder, int portIndex, int farEndIndex );
    int timeoutCount;
    UMLRTTimerId intervalTimerId;
};
extern const UMLRTCapsuleClass Scanner;

#endif

