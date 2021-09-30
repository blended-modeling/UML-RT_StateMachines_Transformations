
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

static UMLRTCommsPortFarEnd internalfarEndList_Top[] = 
{
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
    &internalports_Top[1]
};

static Capsule_Top top( &Top, &Top_slots[InstId_Top], NULL, internalports_Top_ptrs, true );

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
        Capsule_A::borderport_protocol1,
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
        true,
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

static Capsule_A top_a( &A, &Top_slots[InstId_Top_a], borderports_Top_a_ptrs, NULL, true );

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
        &borderports_Top_a_d[Capsule_D::borderport_protocol2]
    }
};

UMLRTCommsPort borderports_Top_a_c[] = 
{
    {
        &C,
        Capsule_C::borderport_protocol2,
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
    }
};

static const UMLRTCommsPort * borderports_Top_a_c_ptrs[] = 
{
    &borderports_Top_a_c[0]
};

static Capsule_C top_a_c( &C, &Top_slots[InstId_Top_a_c], borderports_Top_a_c_ptrs, NULL, true );

static UMLRTCommsPortFarEnd borderfarEndList_Top_a_d[] = 
{
    {
        0,
        &borderports_Top_b[Capsule_B::borderport_protocol1]
    },
    {
        0,
        &borderports_Top_a_c[Capsule_C::borderport_protocol2]
    }
};

UMLRTCommsPort borderports_Top_a_d[] = 
{
    {
        &D,
        Capsule_D::borderport_protocol1,
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
        Capsule_D::borderport_protocol2,
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

static UMLRTCommsPortFarEnd borderfarEndList_Top_b[] = 
{
    {
        0,
        &borderports_Top_a_d[Capsule_D::borderport_protocol1]
    }
};

UMLRTCommsPort borderports_Top_b[] = 
{
    {
        &B,
        Capsule_B::borderport_protocol1,
        &Top_slots[InstId_Top_b],
        1,
        borderfarEndList_Top_b,
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
        &top_a,
        &DefaultController_,
        2,
        parts_Top_a,
        1,
        borderports_Top_a,
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
        1,
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
        NULL,
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
        NULL,
        &DefaultController_,
        0,
        NULL,
        1,
        borderports_Top_b,
        NULL,
        true,
        false
    }
};

