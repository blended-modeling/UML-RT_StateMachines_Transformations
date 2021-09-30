/*******************************************************************************
* Copyright (c) 2015 Zeligsoft (2009) Limited  and others.
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v10.html
*******************************************************************************/

#ifndef INK_HH
#define INK_HH

#include "InkLevelProtocol.hh"
#include "InkPresentProtocol.hh"
#include "TonerStatusProtocol.hh"
#include "umlrtcapsule.hh"
#include "umlrtcapsuleclass.hh"
#include "umlrttimerid.hh"
#include "umlrttimerprotocol.hh"
#include <cstddef>

struct UMLRTCommsPort;
struct UMLRTSlot;
class UMLRTRtsInterface;
class UMLRTMessage;

class Capsule_Ink : public UMLRTCapsule
{
public:
    Capsule_Ink( const UMLRTRtsInterface * rtsif, const UMLRTCapsuleClass * capsuleClass, UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat );
    enum BorderPortId
    {
        borderport_inkPresent,
        borderport_inkLevel
    };
    enum InternalPortId
    {
        internalport_timer,
        internalport_inkStatus
    };
protected:
    InkLevelProtocol::Base inkLevel;
    InkPresentProtocol::Base inkPresent;
    TonerStatusProtocol::Base inkStatus;
    UMLRTTimerProtocol_baserole timer;
public:
    enum PartId
    {
    };
    enum PortId
    {
        port_inkLevel,
        port_inkPresent,
        port_timer,
        port_inkStatus,
    };
    int timeoutCount;
    UMLRTTimerId intervalTimerId;
    virtual void unbindPort( bool isBorder, int portIndex, int farEndIndex );
    virtual void bindPort( bool isBorder, int portIndex, int farEndIndex );
    virtual void inject( const UMLRTMessage & msg );
    virtual void initialize( const UMLRTMessage & msg );
private:
    enum State
    {
        InkStateMachine_State1,
        SPECIAL_INTERNAL_STATE_UNVISITED
    };
    State currentState;
    State history[1];
    void save_history( State compositeState, State subState );
    bool check_history( State compositeState, State subState );
    void entryaction_____InkStateMachine__State1__State1_entry( const UMLRTMessage & msg );
    void transitionaction_____InkStateMachine__Transition2__Chain3__inkTimeout( const UMLRTMessage & msg );
    void transitionaction_____InkStateMachine__initialize__Chain1__computerInit( const UMLRTMessage & msg );
    void actionchain_____InkStateMachine__Transition2__Chain3( const UMLRTMessage & msg );
    void actionchain_____InkStateMachine__initialize__Chain1( const UMLRTMessage & msg );
    State state_____InkStateMachine__State1( const UMLRTMessage & msg );
};
extern const UMLRTCapsuleClass Ink;

#endif

