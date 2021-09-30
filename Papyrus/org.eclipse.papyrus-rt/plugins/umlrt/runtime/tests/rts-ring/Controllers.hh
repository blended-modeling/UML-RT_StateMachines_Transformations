
#ifndef TOPCONTROLLERS_HH
#define TOPCONTROLLERS_HH

class UMLRTController;
struct UMLRTCommsPort;
struct UMLRTSlot;

enum CapsuleInstanceId
{
    InstId_Top_part3,
    InstId_Top_part1,
    InstId_Top_part4,
    InstId_Top_part2
};
extern UMLRTController * Controller1;
extern UMLRTCommsPort borderports_Top_part3[];
extern UMLRTCommsPort borderports_Top_part1[];
extern UMLRTController * Controller2;
extern UMLRTController * Controller3;
extern UMLRTController * Controller4;
extern UMLRTCommsPort borderports_Top_part4[];
extern UMLRTCommsPort borderports_Top_part2[];
extern UMLRTSlot Top_slots[];

#endif

