
#include "TopControllers.hh"

#include "Pinger.hh"
#include "Ponger.hh"
#include "Top.hh"
#include "umlrtcapsuleclass.hh"
#include "umlrtcapsulepart.hh"
#include "umlrtcommsport.hh"
#include "umlrtcommsportfarend.hh"
#include "umlrtcontroller.hh"
#include "umlrtslot.hh"
#include <cstddef>


static UMLRTController TopController_( "TopController" );

UMLRTController * TopController = &TopController_;

static Capsule_Top top( &Top, &Top_slots[InstId_Top], NULL, NULL, true );

static UMLRTSlot * slots_Top[] = 
{
    &Top_slots[InstId_Top_pinger],
    &Top_slots[InstId_Top_ponger]
};

static UMLRTCapsulePart parts_Top[] = 
{
    {
        &Top,
        Capsule_Top::part_pinger,
        1,
        &slots_Top[0]
    },
    {
        &Top,
        Capsule_Top::part_ponger,
        1,
        &slots_Top[1]
    }
};

static UMLRTCommsPortFarEnd borderfarEndList_Top_ponger[] = 
{
    {
        0,
        &borderports_Top_pinger[Capsule_Pinger::borderport_PingPort]
    }
};

UMLRTCommsPort borderports_Top_ponger[] = 
{
    {
        &Ponger,
        Capsule_Ponger::borderport_PongPort,
        &Top_slots[InstId_Top_ponger],
        1,
        borderfarEndList_Top_ponger,
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

static const UMLRTCommsPort * borderports_Top_ponger_ptrs[] = 
{
    &borderports_Top_ponger[0]
};

static Capsule_Ponger top_ponger( &Ponger, &Top_slots[InstId_Top_ponger], borderports_Top_ponger_ptrs, NULL, true );

static UMLRTController PingerController_( "PingerController" );

UMLRTController * PingerController = &PingerController_;

static UMLRTCommsPortFarEnd borderfarEndList_Top_pinger[] = 
{
    {
        0,
        &borderports_Top_ponger[Capsule_Ponger::borderport_PongPort]
    },
    {
        0,
        NULL
    }
};

UMLRTCommsPort borderports_Top_pinger[] = 
{
    {
        &Pinger,
        Capsule_Pinger::borderport_PingPort,
        &Top_slots[InstId_Top_pinger],
        1,
        borderfarEndList_Top_pinger,
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
        &Pinger,
        Capsule_Pinger::borderport_timerPort,
        &Top_slots[InstId_Top_pinger],
        1,
        &borderfarEndList_Top_pinger[1],
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

static const UMLRTCommsPort * borderports_Top_pinger_ptrs[] = 
{
    &borderports_Top_pinger[0],
    &borderports_Top_pinger[1]
};

static Capsule_Pinger top_pinger( &Pinger, &Top_slots[InstId_Top_pinger], borderports_Top_pinger_ptrs, NULL, true );

UMLRTSlot Top_slots[] = 
{
    {
        "Top",
        0,
        &Top,
        NULL,
        0,
        &top,
        &TopController_,
        2,
        parts_Top,
        0,
        NULL,
        NULL,
        true,
        false
    },
    {
        "Top.ponger",
        0,
        &Ponger,
        &Top,
        Capsule_Top::part_ponger,
        &top_ponger,
        &TopController_,
        0,
        NULL,
        1,
        borderports_Top_ponger,
        NULL,
        true,
        false
    },
    {
        "Top.pinger",
        0,
        &Pinger,
        &Top,
        Capsule_Top::part_pinger,
        &top_pinger,
        &PingerController_,
        0,
        NULL,
        2,
        borderports_Top_pinger,
        NULL,
        true,
        false
    }
};

