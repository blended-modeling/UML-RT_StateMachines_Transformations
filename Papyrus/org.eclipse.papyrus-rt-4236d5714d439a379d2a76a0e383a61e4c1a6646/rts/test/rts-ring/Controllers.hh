
#ifndef CONTROLLERS_HH
#define CONTROLLERS_HH

#include "umlrtcontroller.hh"
struct UMLRTCommsPort;
struct UMLRTSlot;

enum CapsuleInstanceId
{
    InstId_Top_part2,
    InstId_Top_part4,
    InstId_Top_part1,
    InstId_Top_part3
};
extern UMLRTController Controller2;
extern UMLRTCommsPort borderports_Top_part2[];
extern UMLRTCommsPort borderports_Top_part4[];
extern UMLRTController Controller1;
extern UMLRTController Controller3;
extern UMLRTController Controller4;
extern UMLRTCommsPort borderports_Top_part1[];
extern UMLRTCommsPort borderports_Top_part3[];
extern UMLRTSlot Top_slots[];

#endif

