
#include "TopControllers.hh"

#include "A.hh"
#include "B.hh"
#include "C.hh"
#include "D.hh"
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

static UMLRTSlot * slots_Top_a[] = 
{
    &Top_slots[InstId_Top_a_c1],
    &Top_slots[InstId_Top_a_c2],
    &Top_slots[InstId_Top_a_c3]
};

static UMLRTCapsulePart parts_Top_a[] = 
{
    {
        &A,
        Capsule_A::part_c1,
        1,
        &slots_Top_a[0]
    },
    {
        &A,
        Capsule_A::part_c2,
        1,
        &slots_Top_a[1]
    },
    {
        &A,
        Capsule_A::part_c3,
        1,
        &slots_Top_a[2]
    }
};

static Capsule_C top_a_c1( &C, &Top_slots[InstId_Top_a_c1], NULL, NULL, true );

static Capsule_C top_a_c2( &C, &Top_slots[InstId_Top_a_c2], NULL, NULL, true );

static Capsule_C top_a_c3( &C, &Top_slots[InstId_Top_a_c3], NULL, NULL, true );

static Capsule_B top_b( &B, &Top_slots[InstId_Top_b], NULL, NULL, true );

static UMLRTSlot * slots_Top_b[] = 
{
    &Top_slots[InstId_Top_b_c1],
    &Top_slots[InstId_Top_b_c2]
};

static UMLRTCapsulePart parts_Top_b[] = 
{
    {
        &B,
        Capsule_B::part_c1,
        1,
        &slots_Top_b[0]
    },
    {
        &B,
        Capsule_B::part_c2,
        1,
        &slots_Top_b[1]
    }
};

static Capsule_C top_b_c1( &C, &Top_slots[InstId_Top_b_c1], NULL, NULL, true );

static Capsule_D top_b_c2( &D, &Top_slots[InstId_Top_b_c2], NULL, NULL, true );

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
        3,
        parts_Top_a,
        0,
        NULL,
        NULL,
        true,
        false
    },
    {
        "Top.a.c1",
        0,
        &C,
        &A,
        Capsule_A::part_c1,
        &top_a_c1,
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
        "Top.a.c2",
        0,
        &C,
        &A,
        Capsule_A::part_c2,
        &top_a_c2,
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
        "Top.a.c3",
        0,
        &C,
        &A,
        Capsule_A::part_c3,
        &top_a_c3,
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
        2,
        parts_Top_b,
        0,
        NULL,
        NULL,
        true,
        false
    },
    {
        "Top.b.c1",
        0,
        &C,
        &B,
        Capsule_B::part_c1,
        &top_b_c1,
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
        "Top.b.c2",
        0,
        &D,
        &B,
        Capsule_B::part_c2,
        &top_b_c2,
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

