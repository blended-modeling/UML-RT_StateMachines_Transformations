
#ifndef TOPCONTROLLERS_HH
#define TOPCONTROLLERS_HH

class UMLRTController;
struct UMLRTCommsPort;
struct UMLRTSlot;

enum CapsuleInstanceId
{
    InstId_Top,
    InstId_Top_capsule1,
    InstId_Top_capsule2_0,
    InstId_Top_capsule2_1,
    InstId_Top_capsule2_2
};
extern UMLRTController * DefaultController;
extern UMLRTCommsPort borderports_Top_capsule1[];
extern UMLRTCommsPort borderports_Top_capsule2_0[];
extern UMLRTCommsPort borderports_Top_capsule2_1[];
extern UMLRTCommsPort borderports_Top_capsule2_2[];
extern UMLRTSlot Top_slots[];

#endif

