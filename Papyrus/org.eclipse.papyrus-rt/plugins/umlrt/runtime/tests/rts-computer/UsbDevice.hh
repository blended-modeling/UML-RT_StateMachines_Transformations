/*******************************************************************************
* Copyright (c) 2015 Zeligsoft (2009) Limited  and others.
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v10.html
*******************************************************************************/

#ifndef USBDEVICE_HH
#define USBDEVICE_HH

#include "InkPresentProtocol.hh"
#include "UsbPortProtocol.hh"
#include "umlrtcapsule.hh"
#include "umlrtcapsuleclass.hh"
#include "umlrtmessage.hh"
#include <cstddef>

struct UMLRTCommsPort;
struct UMLRTSlot;
class UMLRTRtsInterface;
class UMLRTInMessage;

class Capsule_UsbDevice : public UMLRTCapsule
{
public:
    Capsule_UsbDevice( const UMLRTRtsInterface * rtsif, const UMLRTCapsuleClass * capsuleClass, UMLRTSlot * slot, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat );
    enum BorderPortId
    {
        borderport_inkPresent,
        borderport_usbPort
    };
protected:
    InkPresentProtocol::Base inkPresent;
    UsbPortProtocol::Base usbPort;
public:
    enum PartId
    {
    };
    enum PortId
    {
        port_inkPresent,
        port_usbPort
    };
    virtual void unbindPort(  bool isBorder, int portIndex, int farEndIndex );
    virtual void bindPort( bool isBorder, int portIndex, int farEndIndex );
    virtual void initialize( const UMLRTMessage & msg );
    virtual void inject( const UMLRTMessage & msg );
};
extern const UMLRTCapsuleClass UsbDevice;

#endif

