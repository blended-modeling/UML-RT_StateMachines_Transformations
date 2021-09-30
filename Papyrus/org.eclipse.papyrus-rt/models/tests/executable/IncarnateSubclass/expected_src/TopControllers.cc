
#include "TopControllers.hh"

#include "A.hh"
#include "C.hh"
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
        &borderports_Top_a[Capsule_A::borderport_control]
    },
    {
        0,
        &borderports_Top_c[Capsule_C::borderport_control]
    },
    {
        0,
        NULL
    },
    {
        0,
        NULL
    }
};

UMLRTCommsPort internalports_Top[] = 
{
    {
        &Top,
        Capsule_Top::internalport_control,
        &Top_slots[InstId_Top],
        2,
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
        false,
        false,
        false,
        true
    },
    {
        &Top,
        Capsule_Top::internalport_frame,
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
        true,
        false,
        false,
        false
    },
    {
        &Top,
        Capsule_Top::internalport_log,
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
    &internalports_Top[2]
};

static Capsule_Top top( &Top, &Top_slots[InstId_Top], NULL, internalports_Top_ptrs, true );

static UMLRTSlot * slots_Top[] = 
{
    &Top_slots[InstId_Top_a],
    &Top_slots[InstId_Top_c]
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
        Capsule_Top::part_c,
        1,
        &slots_Top[1]
    }
};

static UMLRTCommsPortFarEnd borderfarEndList_Top_a[] = 
{
    {
        0,
        &internalports_Top[Capsule_Top::internalport_control]
    },
    {
        0,
        &borderports_Top_c[Capsule_C::borderport_p1]
    },
    {
        0,
        &borderports_Top_c[Capsule_C::borderport_p2]
    }
};

UMLRTCommsPort borderports_Top_a[] = 
{
    {
        &A,
        Capsule_A::borderport_control,
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
        Capsule_A::borderport_p1,
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
        Capsule_A::borderport_p2,
        &Top_slots[InstId_Top_a],
        1,
        &borderfarEndList_Top_a[2],
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

static UMLRTCommsPortFarEnd borderfarEndList_Top_c[] = 
{
    {
        1,
        &internalports_Top[Capsule_Top::internalport_control]
    },
    {
        0,
        &borderports_Top_a[Capsule_A::borderport_p1]
    },
    {
        0,
        &borderports_Top_a[Capsule_A::borderport_p2]
    }
};

UMLRTCommsPort borderports_Top_c[] = 
{
    {
        &C,
        Capsule_C::borderport_control,
        &Top_slots[InstId_Top_c],
        1,
        borderfarEndList_Top_c,
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
        &C,
        Capsule_C::borderport_p1,
        &Top_slots[InstId_Top_c],
        1,
        &borderfarEndList_Top_c[1],
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
        &C,
        Capsule_C::borderport_p2,
        &Top_slots[InstId_Top_c],
        1,
        &borderfarEndList_Top_c[2],
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
        NULL,
        &DefaultController_,
        0,
        NULL,
        3,
        borderports_Top_a,
        NULL,
        true,
        false
    },
    {
        "Top.c",
        0,
        &C,
        &Top,
        Capsule_Top::part_c,
        NULL,
        &DefaultController_,
        0,
        NULL,
        3,
        borderports_Top_c,
        NULL,
        true,
        false
    }
};

