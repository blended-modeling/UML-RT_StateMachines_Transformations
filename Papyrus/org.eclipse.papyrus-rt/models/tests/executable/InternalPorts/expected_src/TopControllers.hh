
#ifndef TOPCONTROLLERS_HH
#define TOPCONTROLLERS_HH

class UMLRTController;
struct UMLRTCommsPort;
struct UMLRTSlot;

enum CapsuleInstanceId
{
    InstId_Top,
    InstId_Top_capsule1
};
extern UMLRTController * DefaultController;
extern UMLRTCommsPort internalports_Top[];
extern UMLRTCommsPort borderports_Top_capsule1[];
extern UMLRTCommsPort internalports_Top_capsule1[];
extern UMLRTSlot Top_slots[];

#endif

