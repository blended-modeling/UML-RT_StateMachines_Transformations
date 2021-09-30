
#ifndef TOPCONTROLLERS_HH
#define TOPCONTROLLERS_HH

class UMLRTController;
struct UMLRTSlot;

enum CapsuleInstanceId
{
    InstId_Top,
    InstId_Top_a,
    InstId_Top_a_c1,
    InstId_Top_a_c2,
    InstId_Top_a_c3,
    InstId_Top_b,
    InstId_Top_b_c1,
    InstId_Top_b_c2
};
extern UMLRTController * DefaultController;
extern UMLRTSlot Top_slots[];

#endif

