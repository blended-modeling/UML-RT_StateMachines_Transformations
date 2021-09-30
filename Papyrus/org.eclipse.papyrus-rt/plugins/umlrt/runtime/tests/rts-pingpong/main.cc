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
static UMLRTSignalElementPool signalElementPool( signalElementBuffer, USER_CONFIG_SIGNAL_ELEMENT_POOL_SIZE );

// Application-wide message pool.
static UMLRTMessage messageBuffer[USER_CONFIG_MESSAGE_POOL_SIZE];
static UMLRTMessagePool messagePool( messageBuffer, USER_CONFIG_MESSAGE_POOL_SIZE );

// Application-wide timer pool.
static UMLRTTimer timers[USER_CONFIG_TIMER_POOL_SIZE];
static UMLRTTimerPool timerPool( timers, USER_CONFIG_TIMER_POOL_SIZE );

// Generated application main.
int main( int argc, char * argv[] )
{
    // Initialize application-wide signal- and message-pools.
    UMLRTController::initializePools( &signalElementPool, &messagePool, &timerPool );

    // Will only output arguments found on command-line after '--userargs' argument.
    UMLRTMain::setArgs( argc, argv );

    printf("main: argCount %d\n", UMLRTMain::argCount());
    for (int i = 0; i < UMLRTMain::argCount(); ++i)
    {
        printf("main: arg[%d] (%s)\n", i, UMLRTMain::argStrings()[i]);
    }
    UMLRTCapsuleToControllerMap::setDefaultSlotList( Top_slots, 3 );

    if( UMLRTMain::targetStartup() )
    {
        // Spawn the controllers
        TopController->spawn();
        PingerController->spawn();

        bool main_ok = UMLRTMain::mainLoop();
        if( main_ok )
        {
            // Wait for completion.
            PingerController->join();
            TopController->join();
        }
        return UMLRTMain::targetShutdown( main_ok );
    }

    return EXIT_FAILURE;
}
