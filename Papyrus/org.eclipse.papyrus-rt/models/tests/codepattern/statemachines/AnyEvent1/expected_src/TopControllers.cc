
#include "TopControllers.hh"

#include "Capsule1.hh"
#include "Capsule2.hh"
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
    &Top_slots[InstId_Top_capsule1],
    &Top_slots[InstId_Top_capsule2]
};

static UMLRTCapsulePart parts_Top[] = 
{
    {
        &Top,
        Capsule_Top::part_capsule1,
        1,
        &slots_Top[0]
    },
    {
        &Top,
        Capsule_Top::part_capsule2,
        1,
        &slots_Top[1]
    }
};

static UMLRTCommsPortFarEnd borderfarEndList_Top_capsule1[] = 
{
    {
        0,
        &borderports_Top_capsule2[Capsule_Capsule2::borderport_port1]
    }
};

UMLRTCommsPort borderports_Top_capsule1[] = 
{
    {
        &Capsule1,
        Capsule_Capsule1::borderport_port1,
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

static const UMLRTCommsPort * borderports_Top_capsule1_ptrs[] = 
{
    &borderports_Top_capsule1[0]
};

static UMLRTCommsPortFarEnd internalfarEndList_Top_capsule1[] = 
{
    {
        0,
        NULL
    }
};

UMLRTCommsPort internalports_Top_capsule1[] = 
{
    {
        &Capsule1,
        Capsule_Capsule1::internalport_log,
        &Top_slots[InstId_Top_capsule1],
        1,
        internalfarEndList_Top_capsule1,
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

static const UMLRTCommsPort * internalports_Top_capsule1_ptrs[] = 
{
    &internalports_Top_capsule1[0]
};

static Capsule_Capsule1 top_capsule1( &Capsule1, &Top_slots[InstId_Top_capsule1], borderports_Top_capsule1_ptrs, internalports_Top_capsule1_ptrs, true );

static UMLRTCommsPortFarEnd borderfarEndList_Top_capsule2[] = 
{
    {
        0,
        &borderports_Top_capsule1[Capsule_Capsule1::borderport_port1]
    }
};

UMLRTCommsPort borderports_Top_capsule2[] = 
{
    {
        &Capsule2,
        Capsule_Capsule2::borderport_port1,
        &Top_slots[InstId_Top_capsule2],
        1,
        borderfarEndList_Top_capsule2,
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

static const UMLRTCommsPort * borderports_Top_capsule2_ptrs[] = 
{
    &borderports_Top_capsule2[0]
};

static UMLRTCommsPortFarEnd internalfarEndList_Top_capsule2[] = 
{
    {
        0,
        NULL
    }
};

UMLRTCommsPort internalports_Top_capsule2[] = 
{
    {
        &Capsule2,
        Capsule_Capsule2::internalport_log,
        &Top_slots[InstId_Top_capsule2],
        1,
        internalfarEndList_Top_capsule2,
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

static const UMLRTCommsPort * internalports_Top_capsule2_ptrs[] = 
{
    &internalports_Top_capsule2[0]
};

static Capsule_Capsule2 top_capsule2( &Capsule2, &Top_slots[InstId_Top_capsule2], borderports_Top_capsule2_ptrs, internalports_Top_capsule2_ptrs, true );

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
        "Top.capsule1",
        0,
        &Capsule1,
        &Top,
        Capsule_Top::part_capsule1,
        &top_capsule1,
        &DefaultController_,
        0,
        NULL,
        1,
        borderports_Top_capsule1,
        NULL,
        true,
        false
    },
    {
        "Top.capsule2",
        0,
        &Capsule2,
        &Top,
        Capsule_Top::part_capsule2,
        &top_capsule2,
        &DefaultController_,
        0,
        NULL,
        1,
        borderports_Top_capsule2,
        NULL,
        true,
        false
    }
};

