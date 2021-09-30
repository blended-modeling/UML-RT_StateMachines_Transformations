/*******************************************************************************
* Copyright (c) 2015 Zeligsoft (2009) Limited  and others.
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v10.html
*******************************************************************************/

#ifndef CONTROLLERS_HH
#define CONTROLLERS_HH

class UMLRTController;
struct UMLRTCommsPort;
struct UMLRTSlot;

enum CapsuleInstanceId
{
    InstId_Top = 0,
    InstId_Top_computer = 1,
    InstId_Top_computer_pluginUsb_0 = 2,
    InstId_Top_computer_pluginUsb_1 = 3,
    InstId_Top_computer_pluginUsb_2 = 4,
    InstId_Top_computer_optionalUsb_0 = 5,
    InstId_Top_computer_optionalUsb_1 = 6,
    InstId_Top_computer_optionalUsb_2 = 7,
    InstId_Top_computer_usbHub = 8
};
extern UMLRTCommsPort internalports_Top_computerArray[];
extern UMLRTController * DefaultController;
extern UMLRTController * Controller1;
extern UMLRTController * Controller2;
extern UMLRTController * Controller3;
extern UMLRTController * Controller4;
extern UMLRTController * Controller5;
extern UMLRTController * Controller6;
extern UMLRTController * Controller7;
extern UMLRTController * Controller8;
extern UMLRTController * Controller9;
extern UMLRTController * Controller10;
extern UMLRTController * Controller11;
extern UMLRTController * Controller12;
extern UMLRTCommsPort borderports_Top_computer_pluginUsb_0[];
extern UMLRTCommsPort borderports_Top_computer_pluginUsb_1[];
extern UMLRTCommsPort borderports_Top_computer_pluginUsb_2[];
extern UMLRTCommsPort borderports_Top_computer_optionalUsb_0[];
extern UMLRTCommsPort borderports_Top_computer_optionalUsb_1[];
extern UMLRTCommsPort borderports_Top_computer_optionalUsb_2[];
extern UMLRTCommsPort borderports_Top_computer_usbHub[];
extern UMLRTSlot DefaultController_slots[];

#endif

