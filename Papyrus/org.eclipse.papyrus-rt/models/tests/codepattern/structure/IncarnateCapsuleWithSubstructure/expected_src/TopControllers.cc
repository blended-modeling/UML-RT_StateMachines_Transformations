
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

static UMLRTCommsPortFarEnd internalfarEndList_Top[] = 
{
    {
        0,
        NULL
    },
    {
        0,
        NULL
    },
    {
        0,
        &borderports_Top_a[Capsule_A::borderport_p]
    },
    {
        0,
        &borderports_Top_a[Capsule_A::borderport_r]
    }
};

UMLRTCommsPort internalports_Top[] = 
{
    {
        &Top,
        Capsule_Top::internalport_frame,
        &Top_slots[InstId_Top],
        1,
        internalfarEndList_Top,
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
    },
    {
        &Top,
        Capsule_Top::internalport_p,
        &Top_slots[InstId_Top],
        1,
        &internalfarEndList_Top[2],
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
        false,
        false,
        false,
        true
    },
    {
        &Top,
        Capsule_Top::internalport_r,
        &Top_slots[InstId_Top],
        1,
        &internalfarEndList_Top[3],
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
        false,
        false,
        false,
        true
    },
    {
        &Top,
        Capsule_Top::internalport_log,
        &Top_slots[InstId_Top],
        1,
        &internalfarEndList_Top[1],
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

static const UMLRTCommsPort * internalports_Top_ptrs[] = 
{
    &internalports_Top[0],
    &internalports_Top[1],
    &internalports_Top[2],
    &internalports_Top[3]
};

static Capsule_Top top( &Top, &Top_slots[InstId_Top], NULL, internalports_Top_ptrs, true );

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
        &internalports_Top[Capsule_Top::internalport_p]
    },
    {
        0,
        &internalports_Top[Capsule_Top::internalport_r]
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
    },
    {
        &A,
        Capsule_A::borderport_r,
        &Top_slots[InstId_Top_a],
        1,
        &borderfarEndList_Top_a[1],
        NULL,
        NULL,
        "",
        true,
        true,
        true,
        false,
        true,
        false,
        false,
        false,
        false,
        false,
        true
    }
};

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
        NULL,
        &DefaultController_,
        0,
        NULL,
        2,
        borderports_Top_a,
        NULL,
        true,
        false
    }
};

