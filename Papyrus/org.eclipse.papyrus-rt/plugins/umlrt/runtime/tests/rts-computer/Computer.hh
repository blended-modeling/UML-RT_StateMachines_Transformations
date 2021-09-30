/*******************************************************************************
* Copyright (c) 2015 Zeligsoft (2009) Limited  and others.
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v10.html
*******************************************************************************/

#ifndef COMPUTER_HH
#define COMPUTER_HH

#include "InkPresentProtocol.hh"
#include "TonerStatusProtocol.hh"
#include "UsbPortProtocol.hh"
#include "umlrtcapsule.hh"
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

class Capsule_Computer : public UMLRTCapsule
{
public:
    Capsule_Computer( const UMLRTRtsInterface * rtsif, const UMLRTCapsuleClass * capsuleClass, UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat );
    enum InternalPortId
    {
        internalport_frame,
        internalport_timer,
        internalport_inkPresent,
        internalport_inkStatus,
        internalport_tonerStatus,
        internalport_printerStatus,
        internalport_computerStatus,
        internalport_staticPort,
    };
    enum PartId
    {
        part_usbHub,
        part_optionalUsb,
        part_pluginUsb
    };
protected:
    const UMLRTCapsulePart * optionalUsb() const;
    const UMLRTCapsulePart * pluginUsb() const;
    const UMLRTCapsulePart * usbHub() const;
public:
    enum PortId
    {
        port_frame,
        port_timer,
        port_inkPresent,
        port_inkStatus,
        port_tonerStatus,
        port_printerStatus,
        port_computerStatus,
        port_staticPort,
    };
    virtual void unbindPort( bool isBorder, int portIndex, int farEndIndex );
    virtual void bindPort( bool isBorder, int portIndex, int farEndIndex );
    virtual void inject( const UMLRTMessage & msg );
    virtual void initialize( const UMLRTMessage & msg );

    UMLRTTimerId intervalTimerId;
    UMLRTCapsuleId printerId;
    UMLRTCapsuleId scannerId;

protected:
    UMLRTFrameProtocol_baserole frame;
    InkPresentProtocol::Conj inkPresent;
    UMLRTTimerProtocol_baserole timer;
    TonerStatusProtocol::Conj inkStatus;
    TonerStatusProtocol::Conj tonerStatus;
    TonerStatusProtocol::Conj printerStatus;
    TonerStatusProtocol::Base computerStatus;
    UsbPortProtocol::Base staticPort;
private:
    int timeoutCount;
    enum State
    {
        StateMachine1_State1,
        SPECIAL_INTERNAL_STATE_UNVISITED
    };
    State currentState;
    State history[1];
    void save_history( State compositeState, State subState );
    bool check_history( State compositeState, State subState );
    void entryaction_____StateMachine1__State1__State1_entry( const UMLRTMessage & msg );
    void transitionaction_____StateMachine1__Transition2__Chain3__timeoutRx( const UMLRTMessage & msg );
    void transitionaction_____StateMachine1__initialize__Chain1__computerInit( const UMLRTMessage & msg );
    void transitionaction_____StateMachine1__tonerTypeRx__Chain5__tonerTypeOpaqueBehaviour( const UMLRTMessage & msg );
    void transitionaction_____StateMachine1__usbPortrx__Chain4__usbPortRx( const UMLRTMessage & msg );
    void actionchain_____StateMachine1__Transition2__Chain3( const UMLRTMessage & msg );
    void actionchain_____StateMachine1__initialize__Chain1( const UMLRTMessage & msg );
    void actionchain_____StateMachine1__tonerTypeRx__Chain5( const UMLRTMessage & msg );
    void actionchain_____StateMachine1__usbPortrx__Chain4( const UMLRTMessage & msg );
    State state_____StateMachine1__State1( const UMLRTMessage & msg );

    void handleTimeout();

};
extern const UMLRTCapsuleClass Computer;

#endif

