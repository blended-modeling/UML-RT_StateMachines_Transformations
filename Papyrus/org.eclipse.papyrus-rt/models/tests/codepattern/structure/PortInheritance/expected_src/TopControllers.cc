
#include "TopControllers.hh"

#include "B.hh"
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
    &Top_slots[InstId_Top_b]
};

static UMLRTCapsulePart parts_Top[] = 
{
    {
        &Top,
        Capsule_Top::part_b,
        1,
        &slots_Top[0]
    }
};

static UMLRTCommsPortFarEnd borderfarEndList_Top_b[] = 
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

UMLRTCommsPort borderports_Top_b[] = 
{
    {
        &B,
        Capsule_B::borderport_p,
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
    },
    {
        &B,
        Capsule_B::borderport_q,
        &Top_slots[InstId_Top_b],
        1,
        &borderfarEndList_Top_b[1],
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

static const UMLRTCommsPort * borderports_Top_b_ptrs[] = 
{
    &borderports_Top_b[0],
    &borderports_Top_b[1]
};

static UMLRTCommsPortFarEnd internalfarEndList_Top_b[] = 
{
    {
        0,
        NULL
    }
};

UMLRTCommsPort internalports_Top_b[] = 
{
    {
        &B,
        Capsule_B::internalport_log,
        &Top_slots[InstId_Top_b],
        1,
        internalfarEndList_Top_b,
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

static const UMLRTCommsPort * internalports_Top_b_ptrs[] = 
{
    &internalports_Top_b[0]
};

static Capsule_B top_b( &B, &Top_slots[InstId_Top_b], borderports_Top_b_ptrs, internalports_Top_b_ptrs, true );

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
        "Top.b",
        0,
        &B,
        &Top,
        Capsule_Top::part_b,
        &top_b,
        &DefaultController_,
        0,
        NULL,
        2,
        borderports_Top_b,
        NULL,
        true,
        false
    }
};

