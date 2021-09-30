
#include "TopControllers.hh"

#include "Below1.hh"
#include "Top.hh"
#include "umlrtcapsuleclass.hh"
#include "umlrtcapsulepart.hh"
#include "umlrtcommsport.hh"
#include "umlrtcommsportfarend.hh"
#include "umlrtcontroller.hh"
#include "umlrtslot.hh"
#include <cstddef>


UMLRTController DefaultController( "DefaultController" );

static UMLRTCommsPortFarEnd internalfarEndList_Top[] = 
{
    {
        0,
        NULL
    }
};

UMLRTCommsPort internalports_Top[] = 
{
    {
        &Top,
        Capsule_Top::internalport_log,
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
    }
};

static const UMLRTCommsPort * internalports_Top_ptrs[] = 
{
    &internalports_Top[0]
};

static Capsule_Top top( &Top, &Top_slots[InstId_Top], NULL, internalports_Top_ptrs, true );

static UMLRTSlot * slots_Top[] = 
{
    &Top_slots[InstId_Top_below1]
};

static UMLRTCapsulePart parts_Top[] = 
{
    {
        &Top,
        Capsule_Top::part_below1,
        1,
        &slots_Top[0]
    }
};

static UMLRTCommsPortFarEnd internalfarEndList_Top_below1[] = 
{
    {
        0,
        NULL
    }
};

UMLRTCommsPort internalports_Top_below1[] = 
{
    {
        &Below1,
        Capsule_Below1::internalport_log,
        &Top_slots[InstId_Top_below1],
        1,
        internalfarEndList_Top_below1,
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

static const UMLRTCommsPort * internalports_Top_below1_ptrs[] = 
{
    &internalports_Top_below1[0]
};

static Capsule_Below1 top_below1( &Below1, &Top_slots[InstId_Top_below1], NULL, internalports_Top_below1_ptrs, true );

UMLRTSlot Top_slots[] = 
{
    {
        "Top",
        0,
        &Top,
        NULL,
        0,
        &top,
        &DefaultController,
        1,
        parts_Top,
        0,
        NULL,
        NULL,
        true,
        false
    },
    {
        "Top.below1",
        0,
        &Below1,
        &Top,
        Capsule_Top::part_below1,
        &top_below1,
        &DefaultController,
        0,
        NULL,
        0,
        NULL,
        NULL,
        true,
        false
    }
};

