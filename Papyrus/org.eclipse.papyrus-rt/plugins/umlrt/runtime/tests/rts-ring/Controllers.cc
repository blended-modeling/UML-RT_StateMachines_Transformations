
#include "Controllers.hh"

#include "Capsule.hh"
#include "Top.hh"
#include "umlrtcapsuleclass.hh"
#include "umlrtcommsport.hh"
#include "umlrtcommsportfarend.hh"
#include "umlrtcontroller.hh"
#include "umlrtslot.hh"
#include <cstddef>


static UMLRTController Controller1_( "Controller1" );

UMLRTController * Controller1 = &Controller1_;

static UMLRTCommsPortFarEnd borderfarEndList_Top_part3[] = 
{
    {
        0,
        &borderports_Top_part4[Capsule_Capsule::borderport_right]
    },
    {
        0,
        &borderports_Top_part2[Capsule_Capsule::borderport_left]
    }
};

UMLRTCommsPort borderports_Top_part3[] = 
{
    {
        &Capsule,
        Capsule_Capsule::borderport_left,
        &Top_slots[InstId_Top_part3],
        1,
        borderfarEndList_Top_part3,
        NULL,
        NULL,
        NULL,
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
        &Capsule,
        Capsule_Capsule::borderport_right,
        &Top_slots[InstId_Top_part3],
        1,
        &borderfarEndList_Top_part3[1],
        NULL,
        NULL,
        NULL,
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

static const UMLRTCommsPort * borderports_Top_part3_ptrs[] = 
{
    &borderports_Top_part3[0],
    &borderports_Top_part3[1]
};

static Capsule_Capsule top_part3( &Capsule, &Top_slots[InstId_Top_part3], borderports_Top_part3_ptrs, NULL, true );

static UMLRTCommsPortFarEnd borderfarEndList_Top_part1[] = 
{
    {
        0,
        &borderports_Top_part2[Capsule_Capsule::borderport_right]
    },
    {
        0,
        &borderports_Top_part4[Capsule_Capsule::borderport_left]
    }
};

UMLRTCommsPort borderports_Top_part1[] = 
{
    {
        &Capsule,
        Capsule_Capsule::borderport_left,
        &Top_slots[InstId_Top_part1],
        1,
        borderfarEndList_Top_part1,
        NULL,
        NULL,
        NULL,
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
        &Capsule,
        Capsule_Capsule::borderport_right,
        &Top_slots[InstId_Top_part1],
        1,
        &borderfarEndList_Top_part1[1],
        NULL,
        NULL,
        NULL,
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

static const UMLRTCommsPort * borderports_Top_part1_ptrs[] = 
{
    &borderports_Top_part1[0],
    &borderports_Top_part1[1]
};

static Capsule_Capsule top_part1( &Capsule, &Top_slots[InstId_Top_part1], borderports_Top_part1_ptrs, NULL, true );

static UMLRTController Controller2_( "Controller2" );
UMLRTController * Controller2 = &Controller2_;
static UMLRTController Controller3_( "Controller3" );
UMLRTController * Controller3 = &Controller3_;
static UMLRTController Controller4_( "Controller4" );
UMLRTController * Controller4 = &Controller4_;

static UMLRTCommsPortFarEnd borderfarEndList_Top_part4[] = 
{
    {
        0,
        &borderports_Top_part1[Capsule_Capsule::borderport_right]
    },
    {
        0,
        &borderports_Top_part3[Capsule_Capsule::borderport_left]
    }
};

UMLRTCommsPort borderports_Top_part4[] = 
{
    {
        &Capsule,
        Capsule_Capsule::borderport_left,
        &Top_slots[InstId_Top_part4],
        1,
        borderfarEndList_Top_part4,
        NULL,
        NULL,
        NULL,
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
        &Capsule,
        Capsule_Capsule::borderport_right,
        &Top_slots[InstId_Top_part4],
        1,
        &borderfarEndList_Top_part4[1],
        NULL,
        NULL,
        NULL,
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

static const UMLRTCommsPort * borderports_Top_part4_ptrs[] = 
{
    &borderports_Top_part4[0],
    &borderports_Top_part4[1]
};

static Capsule_Capsule top_part4( &Capsule, &Top_slots[InstId_Top_part4], borderports_Top_part4_ptrs, NULL, true );

static UMLRTCommsPortFarEnd borderfarEndList_Top_part2[] = 
{
    {
        0,
        &borderports_Top_part3[Capsule_Capsule::borderport_right]
    },
    {
        0,
        &borderports_Top_part1[Capsule_Capsule::borderport_left]
    }
};

UMLRTCommsPort borderports_Top_part2[] = 
{
    {
        &Capsule,
        Capsule_Capsule::borderport_left,
        &Top_slots[InstId_Top_part2],
        1,
        borderfarEndList_Top_part2,
        NULL,
        NULL,
        NULL,
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
        &Capsule,
        Capsule_Capsule::borderport_right,
        &Top_slots[InstId_Top_part2],
        1,
        &borderfarEndList_Top_part2[1],
        NULL,
        NULL,
        NULL,
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

static const UMLRTCommsPort * borderports_Top_part2_ptrs[] = 
{
    &borderports_Top_part2[0],
    &borderports_Top_part2[1]
};

static Capsule_Capsule top_part2( &Capsule, &Top_slots[InstId_Top_part2], borderports_Top_part2_ptrs, NULL, true );

UMLRTSlot Top_slots[] = 
{
    {
        "Top.part3",
        0,
        &Capsule,
        &Top,
        Capsule_Top::part_part3,
        &top_part3,
        &Controller3_,
        0,
        NULL,
        2,
        borderports_Top_part3,
        NULL,
        true,
        false
    },
    {
        "Top.part1",
        0,
        &Capsule,
        &Top,
        Capsule_Top::part_part1,
        &top_part1,
        &Controller1_,
        0,
        NULL,
        2,
        borderports_Top_part1,
        NULL,
        true,
        false
    },
    {
        "Top.part4",
        0,
        &Capsule,
        &Top,
        Capsule_Top::part_part4,
        &top_part4,
        &Controller4_,
        0,
        NULL,
        2,
        borderports_Top_part4,
        NULL,
        true,
        false
    },
    {
        "Top.part2",
        0,
        &Capsule,
        &Top,
        Capsule_Top::part_part2,
        &top_part2,
        &Controller2_,
        0,
        NULL,
        2,
        borderports_Top_part2,
        NULL,
        true,
        false
    }
};

