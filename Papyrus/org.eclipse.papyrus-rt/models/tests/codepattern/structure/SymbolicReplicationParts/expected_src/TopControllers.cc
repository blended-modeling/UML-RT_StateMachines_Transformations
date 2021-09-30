
#include "TopControllers.hh"

#include "Capsule1.hh"
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
    &Top_slots[InstId_Top_capsule1_0],
    &Top_slots[InstId_Top_capsule1_1],
    &Top_slots[InstId_Top_capsule1_2]
};

static UMLRTCapsulePart parts_Top[] = 
{
    {
        &Top,
        Capsule_Top::part_capsule1,
        3,
        &slots_Top[0]
    }
};

static Capsule_Capsule1 top_capsule1_0( &Capsule1, &Top_slots[InstId_Top_capsule1_0], NULL, NULL, true );

static Capsule_Capsule1 top_capsule1_1( &Capsule1, &Top_slots[InstId_Top_capsule1_1], NULL, NULL, true );

static Capsule_Capsule1 top_capsule1_2( &Capsule1, &Top_slots[InstId_Top_capsule1_2], NULL, NULL, true );

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
        1,
        parts_Top,
        0,
        NULL,
        NULL,
        true,
        false
    },
    {
        "Top.capsule1[0]",
        0,
        &Capsule1,
        &Top,
        Capsule_Top::part_capsule1,
        &top_capsule1_0,
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
        "Top.capsule1[1]",
        1,
        &Capsule1,
        &Top,
        Capsule_Top::part_capsule1,
        &top_capsule1_1,
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
        "Top.capsule1[2]",
        2,
        &Capsule1,
        &Top,
        Capsule_Top::part_capsule1,
        &top_capsule1_2,
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

