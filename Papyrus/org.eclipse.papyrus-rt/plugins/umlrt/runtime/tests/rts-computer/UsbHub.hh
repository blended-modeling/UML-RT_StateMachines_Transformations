/*******************************************************************************
* Copyright (c) 2015 Zeligsoft (2009) Limited  and others.
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v10.html
*******************************************************************************/

#ifndef USBHUB_HH
#define USBHUB_HH

#include "UsbPortProtocol.hh"
#include "umlrtcapsule.hh"
#include "umlrtcapsuleclass.hh"
#include "umlrttimerid.hh"
#include "umlrttimerprotocol.hh"

#include <cstddef>
struct UMLRTCommsPort;
struct UMLRTSlot;
class UMLRTRtsInterface;
class UMLRTInMessage;

class Capsule_UsbHub : public UMLRTCapsule
{
public:
    Capsule_UsbHub( const UMLRTRtsInterface * rtsif, const UMLRTCapsuleClass * capsuleClass, UMLRTSlot * slot, const UMLRTCommsPort * *border, const UMLRTCommsPort * * internal, bool isStat );
    enum BorderPortId
    {
        borderport_usbPort,
        borderport_staticPort,
    };
protected:
    UsbPortProtocol::Conj usbPort;
    UsbPortProtocol::Conj staticPort;
public:

    enum PartId
    {
    };
    enum PortId
    {
        port_usbPort,
        port_staticPort,
    };
    virtual void inject( const UMLRTMessage & msg );
    virtual void initialize( const UMLRTMessage & msg );
    virtual void unbindPort( bool isBorder, int portIndex, int farEndIndex );
    virtual void bindPort( bool isBorder, int portIndex, int farEndIndex );
    int timeoutCount;
    UMLRTTimerId intervalTimerId;
};
extern const UMLRTCapsuleClass UsbHub;

#endif

