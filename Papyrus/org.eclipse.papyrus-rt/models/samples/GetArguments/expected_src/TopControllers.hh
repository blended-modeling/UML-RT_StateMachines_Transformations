
#ifndef TOPCONTROLLERS_HH
#define TOPCONTROLLERS_HH

#include "umlrtcontroller.hh"
struct UMLRTCommsPort;
struct UMLRTSlot;

enum CapsuleInstanceId
{
    InstId_Top,
    InstId_Top_below1
};
extern UMLRTController DefaultController;
extern UMLRTCommsPort internalports_Top[];
extern UMLRTCommsPort internalports_Top_below1[];
extern UMLRTSlot Top_slots[];

#endif

