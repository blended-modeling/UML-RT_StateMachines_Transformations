
#ifndef TOPCONTROLLERS_HH
#define TOPCONTROLLERS_HH

class UMLRTController;
struct UMLRTCommsPort;
struct UMLRTSlot;

enum CapsuleInstanceId
{
    InstId_Top,
    InstId_Top_capsule1,
    InstId_Top_capsule2
};
extern UMLRTController * DefaultController;
extern UMLRTCommsPort borderports_Top_capsule1[];
extern UMLRTCommsPort internalports_Top_capsule1[];
extern UMLRTCommsPort borderports_Top_capsule2[];
extern UMLRTCommsPort internalports_Top_capsule2[];
extern UMLRTSlot Top_slots[];

#endif

