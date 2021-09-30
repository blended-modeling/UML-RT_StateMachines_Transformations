
#ifndef TOPCONTROLLERS_HH
#define TOPCONTROLLERS_HH

class UMLRTController;
struct UMLRTCommsPort;
struct UMLRTSlot;

enum CapsuleInstanceId
{
    InstId_Top,
    InstId_Top_a
};
extern UMLRTController * DefaultController;
extern UMLRTCommsPort borderports_Top_a[];
extern UMLRTCommsPort internalports_Top_a[];
extern UMLRTSlot Top_slots[];

#endif

