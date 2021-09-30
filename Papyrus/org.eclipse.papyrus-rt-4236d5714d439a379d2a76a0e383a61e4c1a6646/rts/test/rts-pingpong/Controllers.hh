
#ifndef CONTROLLERS_HH
#define CONTROLLERS_HH

#include "umlrtcontroller.hh"
struct UMLRTCommsPort;
struct UMLRTSlot;

enum CapsuleInstanceId
{
    InstId_Top = 0,
    InstId_Top_ponger = 1,
    InstId_Top_pinger = 2
};
extern UMLRTCommsPort borderports_Top[];
extern UMLRTCommsPort borderports_Top_pinger[];
extern UMLRTController DefaultController;
extern UMLRTCommsPort borderports_Top_ponger[];
extern UMLRTSlot DefaultController_slots[];

#endif

