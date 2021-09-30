
#include "TopControllers.hh"

#include "A.hh"
#include "B.hh"
#include "Top.hh"
#include "umlrtcapsuleclass.hh"
#include "umlrtcapsulepart.hh"
#include "umlrtcontroller.hh"
#include "umlrtslot.hh"
#include <cstddef>


static UMLRTController DefaultController_( "DefaultController" );

UMLRTController * DefaultController = &DefaultController_;

static Capsule_Top top( &Top, &Top_slots[InstId_Top], NULL, NULL, true );

static UMLRTSlot * slots_Top[] = 
{
    &Top_slots[InstId_Top_a],
    &Top_slots[InstId_Top_b]
};

static UMLRTCapsulePart parts_Top[] = 
{
    {
        &Top,
        Capsule_Top::part_a,
        1,
        &slots_Top[0]
    },
    {
        &Top,
        Capsule_Top::part_b,
        1,
        &slots_Top[1]
    }
};

static Capsule_A top_a( &A, &Top_slots[InstId_Top_a], NULL, NULL, true );

static Capsule_B top_b( &B, &Top_slots[InstId_Top_b], NULL, NULL, true );

UMLRTSlot Top_slots[] = 
{
    {
        "Top",
        0,
        &Top,
        NULL,
        0,
        &top,
        &DefaultController_,
        2,
        parts_Top,
        0,
        NULL,
        NULL,
        true,
        false
    },
    {
        "Top.a",
        0,
        &A,
        &Top,
        Capsule_Top::part_a,
        &top_a,
        &DefaultController_,
        0,
        NULL,
        0,
        NULL,
        NULL,
        true,
        false
    },
    {
        "Top.b",
        0,
        &B,
        &Top,
        Capsule_Top::part_b,
        &top_b,
        &DefaultController_,
        0,
        NULL,
        0,
        NULL,
        NULL,
        true,
        false
    }
};

