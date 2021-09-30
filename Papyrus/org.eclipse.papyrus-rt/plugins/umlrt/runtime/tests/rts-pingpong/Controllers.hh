
#ifndef CONTROLLERS_HH
#define CONTROLLERS_HH

class UMLRTController;
struct UMLRTCommsPort;
struct UMLRTSlot;

enum CapsuleInstanceId
{
    InstId_Top_pinger = 0,
    InstId_Top = 1,
    InstId_Top_ponger = 2,
};
extern UMLRTCommsPort borderports_Top[];
extern UMLRTCommsPort borderports_Top_pinger[];
extern UMLRTController * TopController;
extern UMLRTController * PingerController;
extern UMLRTCommsPort borderports_Top_ponger[];
extern UMLRTSlot Top_slots[];

#endif

