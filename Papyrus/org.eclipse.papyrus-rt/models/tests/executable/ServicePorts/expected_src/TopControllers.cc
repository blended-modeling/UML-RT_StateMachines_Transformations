
#include "TopControllers.hh"

#include "Capsule1.hh"
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
        &borderports_Top_capsule1[Capsule_Capsule1::borderport_control]
    },
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
        NULL
    }
};

UMLRTCommsPort internalports_Top[] = 
{
    {
        &Top,
        Capsule_Top::internalport_control,
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
    },
    {
        &Top,
        Capsule_Top::internalport_timing,
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
    },
    {
        &Top,
        Capsule_Top::internalport_log,
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
    &Top_slots[InstId_Top_capsule1]
};

static UMLRTCapsulePart parts_Top[] = 
{
    {
        &Top,
        Capsule_Top::part_capsule1,
        1,
        &slots_Top[0]
    }
};

static UMLRTCommsPortFarEnd borderfarEndList_Top_capsule1[] = 
{
    {
        0,
        &internalports_Top[Capsule_Top::internalport_control]
    }
};

UMLRTCommsPort borderports_Top_capsule1[] = 
{
    {
        &Capsule1,
        Capsule_Capsule1::borderport_control,
        &Top_slots[InstId_Top_capsule1],
        1,
        borderfarEndList_Top_capsule1,
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
        1,
        parts_Top,
        0,
        NULL,
        NULL,
        true,
        false
    },
    {
        "Top.capsule1",
        0,
        &Capsule1,
        &Top,
        Capsule_Top::part_capsule1,
        NULL,
        &DefaultController_,
        0,
        NULL,
        1,
        borderports_Top_capsule1,
        NULL,
        true,
        false
    }
};

