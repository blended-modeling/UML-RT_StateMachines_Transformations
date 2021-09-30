
#include "TopControllers.hh"

#include "Medium.hh"
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
        &borderports_Top_sender[Capsule_Sender::borderport_control]
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

static Capsule_Top top( &Top, &Top_slots[InstId_Top], borderports_Top_ptrs, internalports_Top_ptrs, true );

static UMLRTSlot * slots_Top[] = 
{
    &Top_slots[InstId_Top_medium],
    &Top_slots[InstId_Top_receiver],
    &Top_slots[InstId_Top_sender]
};

static UMLRTCapsulePart parts_Top[] = 
{
    {
        &Top,
        Capsule_Top::part_medium,
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

static UMLRTCommsPortFarEnd borderfarEndList_Top_medium[] = 
{
    {
        0,
        &borderports_Top_sender[Capsule_Sender::borderport_out]
    },
    {
        1,
        &borderports_Top_sender[Capsule_Sender::borderport_out]
    },
    {
        0,
        &borderports_Top_receiver[Capsule_Receiver::borderport_inp]
    }
};

UMLRTCommsPort borderports_Top_medium[] = 
{
    {
        &Medium,
        Capsule_Medium::borderport_m_inp,
        &Top_slots[InstId_Top_medium],
        2,
        borderfarEndList_Top_medium,
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
        &Medium,
        Capsule_Medium::borderport_m_out,
        &Top_slots[InstId_Top_medium],
        1,
        &borderfarEndList_Top_medium[2],
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
        &borderports_Top_medium[Capsule_Medium::borderport_m_out]
    }
};

UMLRTCommsPort borderports_Top_receiver[] = 
{
    {
        &Receiver,
        Capsule_Receiver::borderport_inp,
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

static UMLRTCommsPortFarEnd internalfarEndList_Top_receiver[] = 
{
    {
        0,
        NULL
    }
};

UMLRTCommsPort internalports_Top_receiver[] = 
{
    {
        &Receiver,
        Capsule_Receiver::internalport_log,
        &Top_slots[InstId_Top_receiver],
        1,
        internalfarEndList_Top_receiver,
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

static const UMLRTCommsPort * internalports_Top_receiver_ptrs[] = 
{
    &internalports_Top_receiver[0]
};

static Capsule_Receiver top_receiver( &Receiver, &Top_slots[InstId_Top_receiver], borderports_Top_receiver_ptrs, internalports_Top_receiver_ptrs, true );

static UMLRTCommsPortFarEnd borderfarEndList_Top_sender[] = 
{
    {
        0,
        &internalports_Top[Capsule_Top::internalport_control]
    },
    {
        0,
        &borderports_Top_medium[Capsule_Medium::borderport_m_inp]
    },
    {
        1,
        &borderports_Top_medium[Capsule_Medium::borderport_m_inp]
    }
};

UMLRTCommsPort borderports_Top_sender[] = 
{
    {
        &Sender,
        Capsule_Sender::borderport_control,
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
        Capsule_Sender::borderport_out,
        &Top_slots[InstId_Top_sender],
        2,
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

static UMLRTCommsPortFarEnd internalfarEndList_Top_sender[] = 
{
    {
        0,
        NULL
    }
};

UMLRTCommsPort internalports_Top_sender[] = 
{
    {
        &Sender,
        Capsule_Sender::internalport_log,
        &Top_slots[InstId_Top_sender],
        1,
        internalfarEndList_Top_sender,
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

static const UMLRTCommsPort * internalports_Top_sender_ptrs[] = 
{
    &internalports_Top_sender[0]
};

static Capsule_Sender top_sender( &Sender, &Top_slots[InstId_Top_sender], borderports_Top_sender_ptrs, internalports_Top_sender_ptrs, true );

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
        "Top.medium",
        0,
        &Medium,
        &Top,
        Capsule_Top::part_medium,
        NULL,
        &DefaultController_,
        0,
        NULL,
        2,
        borderports_Top_medium,
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

