
#include "TopControllers.hh"

#include "A.hh"
#include "B.hh"
#include "C.hh"
#include "D.hh"
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
    &Top_slots[InstId_Top_a_c],
    &Top_slots[InstId_Top_a_d]
};

static UMLRTCapsulePart parts_Top_a[] = 
{
    {
        &A,
        Capsule_A::part_c,
        1,
        &slots_Top_a[0]
    },
    {
        &A,
        Capsule_A::part_d,
        1,
        &slots_Top_a[1]
    }
};

static UMLRTCommsPortFarEnd borderfarEndList_Top_a_c[] = 
{
    {
        0,
        &borderports_Top_a_d[Capsule_D::borderport_p]
    },
    {
        0,
        &borderports_Top_a_d[Capsule_D::borderport_q]
    }
};

UMLRTCommsPort borderports_Top_a_c[] = 
{
    {
        &C,
        Capsule_C::borderport_p,
        &Top_slots[InstId_Top_a_c],
        1,
        borderfarEndList_Top_a_c,
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
        Capsule_C::borderport_q,
        &Top_slots[InstId_Top_a_c],
        1,
        &borderfarEndList_Top_a_c[1],
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

static const UMLRTCommsPort * borderports_Top_a_c_ptrs[] = 
{
    &borderports_Top_a_c[0],
    &borderports_Top_a_c[1]
};

static Capsule_C top_a_c( &C, &Top_slots[InstId_Top_a_c], borderports_Top_a_c_ptrs, NULL, true );

static UMLRTCommsPortFarEnd borderfarEndList_Top_a_d[] = 
{
    {
        0,
        &borderports_Top_a_c[Capsule_C::borderport_p]
    },
    {
        0,
        &borderports_Top_a_c[Capsule_C::borderport_q]
    }
};

UMLRTCommsPort borderports_Top_a_d[] = 
{
    {
        &D,
        Capsule_D::borderport_p,
        &Top_slots[InstId_Top_a_d],
        1,
        borderfarEndList_Top_a_d,
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
        &D,
        Capsule_D::borderport_q,
        &Top_slots[InstId_Top_a_d],
        1,
        &borderfarEndList_Top_a_d[1],
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

static const UMLRTCommsPort * borderports_Top_a_d_ptrs[] = 
{
    &borderports_Top_a_d[0],
    &borderports_Top_a_d[1]
};

static Capsule_D top_a_d( &D, &Top_slots[InstId_Top_a_d], borderports_Top_a_d_ptrs, NULL, true );

static Capsule_B top_b( &B, &Top_slots[InstId_Top_b], NULL, NULL, true );

static UMLRTSlot * slots_Top_b[] = 
{
    &Top_slots[InstId_Top_b_c],
    &Top_slots[InstId_Top_b_d]
};

static UMLRTCapsulePart parts_Top_b[] = 
{
    {
        &B,
        Capsule_B::part_c,
        1,
        &slots_Top_b[0]
    },
    {
        &B,
        Capsule_B::part_d,
        1,
        &slots_Top_b[1]
    }
};

static UMLRTCommsPortFarEnd borderfarEndList_Top_b_c[] = 
{
    {
        0,
        &borderports_Top_b_d[Capsule_D::borderport_p]
    },
    {
        0,
        NULL
    }
};

UMLRTCommsPort borderports_Top_b_c[] = 
{
    {
        &C,
        Capsule_C::borderport_p,
        &Top_slots[InstId_Top_b_c],
        1,
        borderfarEndList_Top_b_c,
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
        Capsule_C::borderport_q,
        &Top_slots[InstId_Top_b_c],
        1,
        &borderfarEndList_Top_b_c[1],
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

static const UMLRTCommsPort * borderports_Top_b_c_ptrs[] = 
{
    &borderports_Top_b_c[0],
    &borderports_Top_b_c[1]
};

static Capsule_C top_b_c( &C, &Top_slots[InstId_Top_b_c], borderports_Top_b_c_ptrs, NULL, true );

static UMLRTCommsPortFarEnd borderfarEndList_Top_b_d[] = 
{
    {
        0,
        &borderports_Top_b_c[Capsule_C::borderport_p]
    },
    {
        0,
        NULL
    }
};

UMLRTCommsPort borderports_Top_b_d[] = 
{
    {
        &D,
        Capsule_D::borderport_p,
        &Top_slots[InstId_Top_b_d],
        1,
        borderfarEndList_Top_b_d,
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
        &D,
        Capsule_D::borderport_q,
        &Top_slots[InstId_Top_b_d],
        1,
        &borderfarEndList_Top_b_d[1],
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

static const UMLRTCommsPort * borderports_Top_b_d_ptrs[] = 
{
    &borderports_Top_b_d[0],
    &borderports_Top_b_d[1]
};

static Capsule_D top_b_d( &D, &Top_slots[InstId_Top_b_d], borderports_Top_b_d_ptrs, NULL, true );

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
        2,
        parts_Top_a,
        0,
        NULL,
        NULL,
        true,
        false
    },
    {
        "Top.a.c",
        0,
        &C,
        &A,
        Capsule_A::part_c,
        &top_a_c,
        &DefaultController_,
        0,
        NULL,
        2,
        borderports_Top_a_c,
        NULL,
        true,
        false
    },
    {
        "Top.a.d",
        0,
        &D,
        &A,
        Capsule_A::part_d,
        &top_a_d,
        &DefaultController_,
        0,
        NULL,
        2,
        borderports_Top_a_d,
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
        "Top.b.c",
        0,
        &C,
        &B,
        Capsule_B::part_c,
        &top_b_c,
        &DefaultController_,
        0,
        NULL,
        2,
        borderports_Top_b_c,
        NULL,
        true,
        false
    },
    {
        "Top.b.d",
        0,
        &D,
        &B,
        Capsule_B::part_d,
        &top_b_d,
        &DefaultController_,
        0,
        NULL,
        2,
        borderports_Top_b_d,
        NULL,
        true,
        false
    }
};

