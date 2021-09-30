/*******************************************************************************
* Copyright (c) 2015 Zeligsoft (2009) Limited  and others.
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v10.html
*******************************************************************************/

#include "umlrtcontroller.hh"
#include "umlrtcapsuletocontrollermap.hh"
#include "umlrtmain.hh"
#include "umlrtmessagepool.hh"
#include "umlrtsignalelementpool.hh"
#include "umlrttimerpool.hh"
#include "umlrtuserconfig.hh"
#include "Controllers.hh"
#include <stdio.h>

// main.cc

// Application-wide signal pool.
static UMLRTSignalElement signalElementBuffer[USER_CONFIG_SIGNAL_ELEMENT_POOL_SIZE];
static UMLRTSignalElementPool signalElementPool(signalElementBuffer, USER_CONFIG_SIGNAL_ELEMENT_POOL_SIZE);

// Application-wide message pool.
static UMLRTMessage messageBuffer[USER_CONFIG_MESSAGE_POOL_SIZE];
static UMLRTMessagePool messagePool(messageBuffer, USER_CONFIG_MESSAGE_POOL_SIZE);

// Application-wide timer pool.
static UMLRTTimer timers[USER_CONFIG_TIMER_POOL_SIZE];
static UMLRTTimerPool timerPool(timers, USER_CONFIG_TIMER_POOL_SIZE);

// Example testing invocation for running on a single controller (terminates.) Omit -c option to run on all controllers (does not terminate).
// ./main -s -M -Tswerr -Tmodel -Tbind -Tbindfail -Tbinddebug -Tinstantiate -Tdestroy -Timport -Tinject -Terror -Tmsg -Tcontroller -Tcommand -Tconnect -Tsap -Tcontrollermap -Thashmap -N1 -n1 -C0 -S0 -c top.controllers-single

int main( int argc, char * argv[] )
{

    // Initialize application-wide signal- and message-pools.
    UMLRTController::initializePools(&signalElementPool, &messagePool, &timerPool);

    // Will only output arguments found on command-line after '--userargs' argument.
    UMLRTMain::setArgs(argc, argv);

    UMLRTCapsuleToControllerMap::setDefaultSlotList( DefaultController_slots, 9 );

    bool startup_ok = UMLRTMain::targetStartup();

    if (startup_ok)
    {
        // Spawn the controllers
        DefaultController->spawn();
        Controller1->spawn();
        Controller2->spawn();
        Controller3->spawn();
        Controller4->spawn();
        Controller5->spawn();
        Controller6->spawn();
        Controller7->spawn();
        Controller8->spawn();
        Controller9->spawn();
        Controller10->spawn();
        Controller11->spawn();
        Controller12->spawn();

        bool main_ok = UMLRTMain::mainLoop();

        if (main_ok)
        {
            // Wait for completion.
            DefaultController->join();
        }
        return UMLRTMain::targetShutdown(main_ok);
    }

    return EXIT_FAILURE;
}
