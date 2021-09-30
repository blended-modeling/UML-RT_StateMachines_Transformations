
#ifndef TOPCONTROLLERS_HH
#define TOPCONTROLLERS_HH

class UMLRTController;
struct UMLRTCommsPort;
struct UMLRTSlot;

enum CapsuleInstanceId
{
    InstId_Top,
    InstId_Top_computerSystem,
    InstId_Top_computerSystem_computer,
    InstId_Top_computerSystem_computer_application,
    InstId_Top_computerSystem_computer_application_deviceInterface,
    InstId_Top_computerSystem_computer_driverManager,
    InstId_Top_computerSystem_computer_driverManager_usbPrinterDriver,
    InstId_Top_computerSystem_computer_driverManager_usbStorageDriver,
    InstId_Top_computerSystem_computer_usbBus_0,
    InstId_Top_computerSystem_computer_usbBus_1,
    InstId_Top_computerSystem_massStorage,
    InstId_Top_computerSystem_printer,
    InstId_Top_computerSystem_user
};
extern UMLRTController * DefaultController;
extern UMLRTCommsPort borderports_Top_computerSystem[];
extern UMLRTCommsPort borderports_Top_computerSystem_computer[];
extern UMLRTCommsPort internalports_Top_computerSystem_computer[];
extern UMLRTCommsPort borderports_Top_computerSystem_computer_application[];
extern UMLRTCommsPort borderports_Top_computerSystem_computer_application_deviceInterface[];
extern UMLRTCommsPort borderports_Top_computerSystem_computer_driverManager[];
extern UMLRTCommsPort borderports_Top_computerSystem_computer_driverManager_usbPrinterDriver[];
extern UMLRTCommsPort borderports_Top_computerSystem_computer_driverManager_usbStorageDriver[];
extern UMLRTCommsPort borderports_Top_computerSystem_computer_usbBus_0[];
extern UMLRTCommsPort borderports_Top_computerSystem_computer_usbBus_1[];
extern UMLRTCommsPort borderports_Top_computerSystem_massStorage[];
extern UMLRTCommsPort borderports_Top_computerSystem_printer[];
extern UMLRTCommsPort borderports_Top_computerSystem_user[];
extern UMLRTSlot Top_slots[];

#endif

