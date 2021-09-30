/*******************************************************************************
* Copyright (c) 2015 Zeligsoft (2009) Limited  and others.
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v10.html
*******************************************************************************/

#include "Top.hh"

#include "Computer.hh"
#include "umlrtcapsulepart.hh"
#include "umlrtslot.hh"
#include <cstddef>
#include "umlrtcapsuleclass.hh"
#include "umlrtcapsulepart.hh"
#include "umlrtcapsulerole.hh"
#include "umlrtmessage.hh"
#include <cstddef>

struct UMLRTCommsPort;

Capsule_Top::Capsule_Top( const UMLRTRtsInterface * rtsif, const UMLRTCapsuleClass * capsuleClass, UMLRTSlot * slot, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat )
: UMLRTCapsule( rtsif, capsuleClass, slot, border, internal, isStat )
{
}


const UMLRTCapsulePart * Capsule_Top::computer() const
{
    return &slot->parts[part_computer];
}

void Capsule_Top::initialize( const UMLRTMessage & msg )
{
}

void Capsule_Top::inject( const UMLRTMessage & msg )
{
}

static const UMLRTCapsuleRole roles[] = 
{
    {
        "computer",
        &Computer,
        1,
        1,
        false,
        false
    }
};

static void instantiate_Top( const UMLRTRtsInterface * rtsif, UMLRTSlot * slot, const UMLRTCommsPort * * border )
{
    Computer.instantiate( rtsif, slot->parts[Capsule_Top::part_computer].slots[0], NULL );
    slot->capsule = new Capsule_Top( rtsif, &Top, slot, border, NULL, false );
}

const UMLRTCapsuleClass Top = 
{
    "Top",
    NULL,
    instantiate_Top,
    1,
    roles,
    0,
    NULL,
    0,
    NULL
};
