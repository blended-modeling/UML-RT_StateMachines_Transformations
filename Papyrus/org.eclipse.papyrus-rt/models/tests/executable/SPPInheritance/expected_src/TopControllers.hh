
#ifndef TOPCONTROLLERS_HH
#define TOPCONTROLLERS_HH

class UMLRTController;
struct UMLRTCommsPort;
struct UMLRTSlot;

enum CapsuleInstanceId
{
    InstId_Top,
    InstId_Top_client,
    InstId_Top_server
};
extern UMLRTController * DefaultController;
extern UMLRTCommsPort internalports_Top[];
extern UMLRTCommsPort internalports_Top_client[];
extern UMLRTSlot Top_slots[];

#endif

