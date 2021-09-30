
#ifndef TOPCONTROLLERS_HH
#define TOPCONTROLLERS_HH

class UMLRTController;
struct UMLRTCommsPort;
struct UMLRTSlot;

enum CapsuleInstanceId
{
    InstId_Top
};
extern UMLRTController * DefaultController;
extern UMLRTCommsPort borderports_Top[];
extern UMLRTSlot Top_slots[];

#endif

