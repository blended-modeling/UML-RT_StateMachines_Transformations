
#ifndef TOPCONTROLLERS_HH
#define TOPCONTROLLERS_HH

class UMLRTController;
struct UMLRTCommsPort;
struct UMLRTSlot;

enum CapsuleInstanceId
{
    InstId_Top,
    InstId_Top_ponger,
    InstId_Top_pinger
};
extern UMLRTController * TopController;
extern UMLRTCommsPort borderports_Top[];
extern UMLRTCommsPort borderports_Top_ponger[];
extern UMLRTController * PingerController;
extern UMLRTCommsPort borderports_Top_pinger[];
extern UMLRTSlot Top_slots[];

#endif

