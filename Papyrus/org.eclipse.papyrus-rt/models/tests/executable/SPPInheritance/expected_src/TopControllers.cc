
#include "TopControllers.hh"

#include "Client.hh"
#include "Server.hh"
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
        Capsule_Top::internalport_timing,
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
    &internalports_Top[1],
    &internalports_Top[2]
};

static Capsule_Top top( &Top, &Top_slots[InstId_Top], NULL, internalports_Top_ptrs, true );

static UMLRTSlot * slots_Top[] = 
{
    &Top_slots[InstId_Top_client],
    &Top_slots[InstId_Top_server]
};

static UMLRTCapsulePart parts_Top[] = 
{
    {
        &Top,
        Capsule_Top::part_client,
        1,
        &slots_Top[0]
    },
    {
        &Top,
        Capsule_Top::part_server,
        1,
        &slots_Top[1]
    }
};

static UMLRTCommsPortFarEnd internalfarEndList_Top_client[] = 
{
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

UMLRTCommsPort internalports_Top_client[] = 
{
    {
        &Client,
        Capsule_Client::internalport_service,
        &Top_slots[InstId_Top_client],
        1,
        &internalfarEndList_Top_client[1],
        NULL,
        NULL,
        "",
        true,
        false,
        true,
        false,
        true,
        false,
        false,
        true,
        false,
        false,
        false
    },
    {
        &Client,
        Capsule_Client::internalport_timing,
        &Top_slots[InstId_Top_client],
        1,
        &internalfarEndList_Top_client[2],
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
        &Client,
        Capsule_Client::internalport_log,
        &Top_slots[InstId_Top_client],
        1,
        internalfarEndList_Top_client,
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

static const UMLRTCommsPort * internalports_Top_client_ptrs[] = 
{
    &internalports_Top_client[0],
    &internalports_Top_client[1],
    &internalports_Top_client[2]
};

static Capsule_Client top_client( &Client, &Top_slots[InstId_Top_client], NULL, internalports_Top_client_ptrs, true );

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
        "Top.client",
        0,
        &Client,
        &Top,
        Capsule_Top::part_client,
        &top_client,
        &DefaultController_,
        0,
        NULL,
        0,
        NULL,
        NULL,
        true,
        false
    },
    {
        "Top.server",
        0,
        &Server,
        &Top,
        Capsule_Top::part_server,
        NULL,
        &DefaultController_,
        0,
        NULL,
        0,
        NULL,
        NULL,
        true,
        false
    }
};

