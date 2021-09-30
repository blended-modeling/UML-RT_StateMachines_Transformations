#include "umlrtcontroller.hh"
#include "umlrtmain.hh"
#include "umlrtmessagepool.hh"
#include "umlrtsignalelementpool.hh"
#include "umlrttimerpool.hh"
#include "umlrtuserconfig.hh"
#include "umlrtcapsuletocontrollermap.hh"
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

    // Set the default slot list in the RTS (used by controllers constructed with no explicit slot list.)
    UMLRTCapsuleToControllerMap::setDefaultSlotList( Top_slots, 4 );

    if( UMLRTMain::targetStartup() )
    {
        // Spawn the controllers
        Controller4->spawn();
        Controller3->spawn();
        Controller2->spawn();
        Controller1->spawn();

        bool main_ok = UMLRTMain::mainLoop();
        if( main_ok )
        {
            // Wait for completion.
            Controller4->join();
            Controller3->join();
            Controller2->join();
            Controller1->join();
        }
        return UMLRTMain::targetShutdown( main_ok );
    }

    return EXIT_FAILURE;
}
