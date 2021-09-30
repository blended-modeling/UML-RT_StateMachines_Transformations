
#include "TopControllers.hh"

#include "Mediator.hh"
#include "Receiver.hh"
#include "Sender.hh"
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

static UMLRTCommsPortFarEnd borderfarEndList_Top[] = 
{
    {
        0,
        NULL
    }
};

UMLRTCommsPort borderports_Top[] = 
{
    {
        &Top,
        Capsule_Top::borderport_frame,
        &Top_slots[InstId_Top],
        1,
        borderfarEndList_Top,
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
        false
    }
};

static const UMLRTCommsPort * borderports_Top_ptrs[] = 
{
    &borderports_Top[0]
};

static UMLRTCommsPortFarEnd internalfarEndList_Top[] = 
{
    {
        0,
        &borderports_Top_sender[Capsule_Sender::borderport_start]
    }
};

UMLRTCommsPort internalports_Top[] = 
{
    {
        &Top,
        Capsule_Top::internalport_start,
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
    }
};

static const UMLRTCommsPort * internalports_Top_ptrs[] = 
{
    &internalports_Top[0]
};

static Capsule_Top top( &Top, &Top_slots[InstId_Top], borderports_Top_ptrs, internalports_Top_ptrs, true );

static UMLRTSlot * slots_Top[] = 
{
    &Top_slots[InstId_Top_mediator],
    &Top_slots[InstId_Top_receiver],
    &Top_slots[InstId_Top_sender]
};

static UMLRTCapsulePart parts_Top[] = 
{
    {
        &Top,
        Capsule_Top::part_mediator,
        1,
        &slots_Top[0]
    },
    {
        &Top,
        Capsule_Top::part_receiver,
        1,
        &slots_Top[1]
    },
    {
        &Top,
        Capsule_Top::part_sender,
        1,
        &slots_Top[2]
    }
};

static UMLRTCommsPortFarEnd borderfarEndList_Top_mediator[] = 
{
    {
        0,
        &borderports_Top_sender[Capsule_Sender::borderport_s_out]
    },
    {
        0,
        &borderports_Top_receiver[Capsule_Receiver::borderport_r_inp]
    }
};

UMLRTCommsPort borderports_Top_mediator[] = 
{
    {
        &Mediator,
        Capsule_Mediator::borderport_m_inp,
        &Top_slots[InstId_Top_mediator],
        1,
        borderfarEndList_Top_mediator,
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
        &Mediator,
        Capsule_Mediator::borderport_m_out,
        &Top_slots[InstId_Top_mediator],
        1,
        &borderfarEndList_Top_mediator[1],
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

static UMLRTCommsPortFarEnd borderfarEndList_Top_receiver[] = 
{
    {
        0,
        &borderports_Top_mediator[Capsule_Mediator::borderport_m_out]
    }
};

UMLRTCommsPort borderports_Top_receiver[] = 
{
    {
        &Receiver,
        Capsule_Receiver::borderport_r_inp,
        &Top_slots[InstId_Top_receiver],
        1,
        borderfarEndList_Top_receiver,
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

static const UMLRTCommsPort * borderports_Top_receiver_ptrs[] = 
{
    &borderports_Top_receiver[0]
};

static Capsule_Receiver top_receiver( &Receiver, &Top_slots[InstId_Top_receiver], borderports_Top_receiver_ptrs, NULL, true );

static UMLRTCommsPortFarEnd borderfarEndList_Top_sender[] = 
{
    {
        0,
        &borderports_Top_mediator[Capsule_Mediator::borderport_m_inp]
    },
    {
        0,
        &internalports_Top[Capsule_Top::internalport_start]
    }
};

UMLRTCommsPort borderports_Top_sender[] = 
{
    {
        &Sender,
        Capsule_Sender::borderport_s_out,
        &Top_slots[InstId_Top_sender],
        1,
        borderfarEndList_Top_sender,
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
        &Sender,
        Capsule_Sender::borderport_start,
        &Top_slots[InstId_Top_sender],
        1,
        &borderfarEndList_Top_sender[1],
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

static const UMLRTCommsPort * borderports_Top_sender_ptrs[] = 
{
    &borderports_Top_sender[0],
    &borderports_Top_sender[1]
};

static Capsule_Sender top_sender( &Sender, &Top_slots[InstId_Top_sender], borderports_Top_sender_ptrs, NULL, true );

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
        3,
        parts_Top,
        1,
        borderports_Top,
        NULL,
        true,
        false
    },
    {
        "Top.mediator",
        0,
        &Mediator,
        &Top,
        Capsule_Top::part_mediator,
        NULL,
        &DefaultController_,
        0,
        NULL,
        2,
        borderports_Top_mediator,
        NULL,
        true,
        false
    },
    {
        "Top.receiver",
        0,
        &Receiver,
        &Top,
        Capsule_Top::part_receiver,
        &top_receiver,
        &DefaultController_,
        0,
        NULL,
        1,
        borderports_Top_receiver,
        NULL,
        true,
        false
    },
    {
        "Top.sender",
        0,
        &Sender,
        &Top,
        Capsule_Top::part_sender,
        &top_sender,
        &DefaultController_,
        0,
        NULL,
        2,
        borderports_Top_sender,
        NULL,
        true,
        false
    }
};

