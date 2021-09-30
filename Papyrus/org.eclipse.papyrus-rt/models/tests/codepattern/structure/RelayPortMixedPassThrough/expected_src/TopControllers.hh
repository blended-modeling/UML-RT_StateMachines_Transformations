
#ifndef TOPCONTROLLERS_HH
#define TOPCONTROLLERS_HH

class UMLRTController;
struct UMLRTCommsPort;
struct UMLRTSlot;

enum CapsuleInstanceId
{
    InstId_Top,
    InstId_Top_medium,
    InstId_Top_medium_eavesdropper,
    InstId_Top_receiver,
    InstId_Top_sender
};
extern UMLRTController * DefaultController;
extern UMLRTCommsPort borderports_Top_medium[];
extern UMLRTCommsPort borderports_Top_medium_eavesdropper[];
extern UMLRTCommsPort borderports_Top_receiver[];
extern UMLRTCommsPort borderports_Top_sender[];
extern UMLRTSlot Top_slots[];

#endif

