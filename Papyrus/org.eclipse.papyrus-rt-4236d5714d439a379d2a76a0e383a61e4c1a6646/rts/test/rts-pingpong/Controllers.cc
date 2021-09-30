
#include "Controllers.hh"

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

UMLRTCommsPort borderports_Top[] = 
{
    {
        &Top,
        Capsule_Top::borderport_FramePort,
        &DefaultController_slots[InstId_Top],
        0,
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
static const UMLRTCommsPort * borderports_Top_ptrs[] = 
{
    &borderports_Top[0]
};
static Capsule_Top top( &DefaultController_slots[InstId_Top], borderports_Top_ptrs, NULL, true );
static UMLRTCommsPortFarEnd borderfarEndList_Top_pinger[] = 
{
    {
        0,
        &borderports_Top_ponger[Capsule_Ponger::borderport_PongPort]
    }
};
UMLRTCommsPort borderports_Top_pinger[] = 
{
    {
        &Pinger,
        Capsule_Pinger::borderport_PingPort,
        &DefaultController_slots[InstId_Top_pinger],
        1,
        borderfarEndList_Top_pinger,
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
        &Pinger,
        Capsule_Pinger::borderport_timerPort,
        &DefaultController_slots[InstId_Top_pinger],
        0,
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
        false
    }
};
static const UMLRTCommsPort * borderports_Top_pinger_ptrs[] = 
{
    &borderports_Top_pinger[0],
    &borderports_Top_pinger[1]
};
static Capsule_Pinger top_pinger( &DefaultController_slots[InstId_Top_pinger], borderports_Top_pinger_ptrs, NULL, true );
UMLRTController DefaultController( "DefaultController", 3, DefaultController_slots );
static UMLRTSlot * slots_Top[] = 
{
    &DefaultController_slots[InstId_Top_pinger],
    &DefaultController_slots[InstId_Top_ponger]
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
static int slotToBorderMap_Top[] = 
{
    -1
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
        &DefaultController_slots[InstId_Top_ponger],
        1,
        borderfarEndList_Top_ponger,
        NULL,
        NULL,
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
    },
    {
        &Ponger,
        Capsule_Ponger::borderport__bind,
        &DefaultController_slots[InstId_Top_ponger],
        0,
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
static int slotToBorderMap_Top_ponger[] = 
{
    -1,
    -1
};
static int slotToBorderMap_Top_pinger[] = 
{
    -1,
    -1
};
UMLRTSlot DefaultController_slots[] = 
{
    {
        "Top",
        0,
        &Top,
        NULL,
        0,
        &top,
        &DefaultController,
        2,
        parts_Top,
        1,
        borderports_Top,
        slotToBorderMap_Top,
        true,
        false
    },
    {
        "Top.ponger",
        0,
        &Ponger,
        &Top,
        Capsule_Top::part_ponger,
        NULL,
        &DefaultController,
        0,
        NULL,
        2,
        borderports_Top_ponger,
        slotToBorderMap_Top_ponger,
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
        &DefaultController,
        0,
        NULL,
        2,
        borderports_Top_pinger,
        slotToBorderMap_Top_pinger,
        true,
        false
    }
};
