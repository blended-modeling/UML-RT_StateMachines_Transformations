
#include "TopControllers.hh"

#include "A.hh"
#include "Top.hh"
#include "umlrtcapsuleclass.hh"
#include "umlrtcapsulepart.hh"
#include "umlrtcommsport.hh"
#include "umlrtcommsportfarend.hh"
#include "umlrtcontroller.hh"
#include "umlrtslot.hh"
#include <cstddef>


static UMLRTController DefaultController_( "DefaultController" );

UMLRTController * DefaultController = &DefaultController_;

static Capsule_Top top( &Top, &Top_slots[InstId_Top], NULL, NULL, true );

static UMLRTSlot * slots_Top[] = 
{
    &Top_slots[InstId_Top_a]
};

static UMLRTCapsulePart parts_Top[] = 
{
    {
        &Top,
        Capsule_Top::part_a,
        1,
        &slots_Top[0]
    }
};

static UMLRTCommsPortFarEnd borderfarEndList_Top_a[] = 
{
    {
        0,
        NULL
    }
};

UMLRTCommsPort borderports_Top_a[] = 
{
    {
        &A,
        Capsule_A::borderport_p,
        &Top_slots[InstId_Top_a],
        1,
        borderfarEndList_Top_a,
        NULL,
        NULL,
        "",
        true,
        true,
        true,
        false,
        false,
        false,
        false,
        false,
        false,
        false,
        true
    }
};

static const UMLRTCommsPort * borderports_Top_a_ptrs[] = 
{
    &borderports_Top_a[0]
};

static UMLRTCommsPortFarEnd internalfarEndList_Top_a[] = 
{
    {
        0,
        NULL
    }
};

UMLRTCommsPort internalports_Top_a[] = 
{
    {
        &A,
        Capsule_A::internalport_log,
        &Top_slots[InstId_Top_a],
        1,
        internalfarEndList_Top_a,
        NULL,
        NULL,
        "",
        true,
        false,
        true,
        false,
        false,
        false,
        false,
        true,
        false,
        false,
        false
    }
};

static const UMLRTCommsPort * internalports_Top_a_ptrs[] = 
{
    &internalports_Top_a[0]
};

static Capsule_A top_a( &A, &Top_slots[InstId_Top_a], borderports_Top_a_ptrs, internalports_Top_a_ptrs, true );

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
        "Top.a",
        0,
        &A,
        &Top,
        Capsule_Top::part_a,
        &top_a,
        &DefaultController_,
        0,
        NULL,
        1,
        borderports_Top_a,
        NULL,
        true,
        false
    }
};

