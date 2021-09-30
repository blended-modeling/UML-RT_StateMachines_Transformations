/*******************************************************************************
* Copyright (c) 2015 Zeligsoft (2009) Limited  and others.
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v10.html
*******************************************************************************/

#ifndef TONER_HH
#define TONER_HH

#include "InkLevelProtocol.hh"
#include "InkPresentProtocol.hh"
#include "TonerTypeProtocol.hh"
#include "TonerStatusProtocol.hh"
#include "umlrtcapsule.hh"
#include "umlrttimerid.hh"
#include "umlrttimerprotocol.hh"
#include "umlrtframeprotocol.hh"
#include <cstddef>
#include "umlrtcontroller.hh"

struct UMLRTCapsulePart;
struct UMLRTCommsPort;
struct UMLRTSlot;
struct UMLRTCapsuleClass;
class UMLRTRtsInterface;
class UMLRTInMessage;

class Capsule_Toner : public UMLRTCapsule
{
public:
    Capsule_Toner( const UMLRTRtsInterface * rtsif, const UMLRTCapsuleClass * capsuleClass, UMLRTSlot * slot, const UMLRTCommsPort * * border, const UMLRTCommsPort * internal, bool isStat );
    enum BorderPortId
    {
        borderport_tonerType,
        borderport_inkPresent
    };
    enum InternalPortId
    {
        internalport_frame,
        internalport_inkLevel,
        internalport_timer,
        internalport_tonerStatus,
        internalport_computerStatus,
    };
protected:
    UMLRTFrameProtocol_baserole frame() const;
    InkLevelProtocol_conjrole inkLevel() const;
    InkPresentProtocol_baserole inkPresent() const;
    UMLRTTimerProtocol_baserole timer() const;
    TonerTypeProtocol_baserole tonerType() const;
    TonerStatusProtocol_baserole tonerStatus() const;
    TonerStatusProtocol_conjrole computerStatus() const;
public:
    enum PartId
    {
        part_ink
    };
protected:
    const UMLRTCapsulePart * ink() const;
public:
    enum PortId
    {
        port_frame,
        port_inkLevel,
        port_inkPresent,
        port_timer,
        port_tonerType,
        port_tonerStatus,
        port_computerStatus,
    };
    int timeoutCount;
    UMLRTTimerId intervalTimerId;
    virtual void unbindPort( bool isBorder, int portIndex, int farEndIndex );
    virtual void bindPort( bool isBorder, int portIndex, int farEndIndex );
    virtual void inject( const UMLRTInMessage & msg );
    virtual void initialize( const UMLRTInMessage & msg );
private:
    enum State
    {
        TonerStateMachine_State1,
        SPECIAL_INTERNAL_STATE_UNVISITED
    };
    State currentState;
    State history[1];
    void save_history( State compositeState, State subState );
    bool check_history( State compositeState, State subState );
    void entryaction_____TonerStateMachine__State1__State1_entry( const UMLRTInMessage & msg );
    void transitionaction_____TonerStateMachine__Transition2__Chain3__tonerTimeout( const UMLRTInMessage & msg );
    void transitionaction_____TonerStateMachine__initialize__Chain1__computerInit( const UMLRTInMessage & msg );
    void transitionaction_____TonerStateMachine__inkLevelReceived__Chain4__inkLevelOpaqueBehaviour( const UMLRTInMessage & msg );
    void actionchain_____TonerStateMachine__Transition2__Chain3( const UMLRTInMessage & msg );
    void actionchain_____TonerStateMachine__initialize__Chain1( const UMLRTInMessage & msg );
    void actionchain_____TonerStateMachine__inkLevelReceived__Chain4( const UMLRTInMessage & msg );
    State state_____TonerStateMachine__State1( const UMLRTInMessage & msg );
};
extern const UMLRTCapsuleClass Toner;

#endif

