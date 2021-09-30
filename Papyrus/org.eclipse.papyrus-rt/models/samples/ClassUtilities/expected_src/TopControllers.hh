
#ifndef TOPCONTROLLERS_HH
#define TOPCONTROLLERS_HH

#include "umlrtcontroller.hh"
struct UMLRTCommsPort;
struct UMLRTSlot;

enum CapsuleInstanceId
{
    InstId_Top
};
extern UMLRTController DefaultController;
extern UMLRTCommsPort internalports_Top[];
extern UMLRTSlot Top_slots[];

#endif

