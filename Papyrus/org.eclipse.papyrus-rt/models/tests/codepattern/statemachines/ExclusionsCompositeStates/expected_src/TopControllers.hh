
#ifndef TOPCONTROLLERS_HH
#define TOPCONTROLLERS_HH

class UMLRTController;
struct UMLRTCommsPort;
struct UMLRTSlot;

enum CapsuleInstanceId
{
    InstId_Top,
    InstId_Top_a,
    InstId_Top_b,
    InstId_Top_c
};
extern UMLRTController * DefaultController;
extern UMLRTCommsPort borderports_Top_a[];
extern UMLRTCommsPort internalports_Top_a[];
extern UMLRTCommsPort borderports_Top_b[];
extern UMLRTCommsPort internalports_Top_b[];
extern UMLRTCommsPort borderports_Top_c[];
extern UMLRTCommsPort internalports_Top_c[];
extern UMLRTSlot Top_slots[];

#endif
