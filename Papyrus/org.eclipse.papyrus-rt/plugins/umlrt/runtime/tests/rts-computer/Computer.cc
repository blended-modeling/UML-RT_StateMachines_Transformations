/*******************************************************************************
* Copyright (c) 2015 Zeligsoft (2009) Limited  and others.
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v10.html
*******************************************************************************/

#include "Computer.hh"
#include "Controllers.hh"

#include "UsbDevice.hh"
#include "UsbHub.hh"
#include "umlrtcapsuleclass.hh"
#include "umlrtcapsulepart.hh"
#include "umlrtslot.hh"
#include <cstddef>
#include "umlrtcapsuleclass.hh"
#include "umlrtcapsulepart.hh"
#include "umlrtcapsulerole.hh"
#include "umlrtcommsport.hh"
#include "umlrtcommsportrole.hh"
#include "umlrtmessage.hh"
#include "umlrtslot.hh"
#include "umlrtrtsinterface.hh"
#include "umlrttimerprotocol.hh"
#include <cstddef>
#include "umlrtcontroller.hh"
#include "Printer.hh"
#include "Scanner.hh"
#include "DataType1.hh"

Capsule_Computer::Capsule_Computer( const UMLRTRtsInterface * rtsif, const UMLRTCapsuleClass * capsuleClass, UMLRTSlot * slot, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat )
: UMLRTCapsule( rtsif, capsuleClass, slot, border, internal, isStat ),
  frame(internalPorts[internalport_frame]),
  inkPresent(internalPorts[internalport_inkPresent]),
  timer(internalPorts[internalport_timer]),
  inkStatus(internalPorts[internalport_inkStatus]),
  tonerStatus(internalPorts[internalport_tonerStatus]),
  printerStatus(internalPorts[internalport_printerStatus]),
  computerStatus(internalPorts[internalport_computerStatus]),
  staticPort(internalPorts[internalport_staticPort]),
  timeoutCount(0),
  currentState(SPECIAL_INTERNAL_STATE_UNVISITED)
{
}

const UMLRTCapsulePart * Capsule_Computer::optionalUsb() const
{
    return &slot->parts[part_optionalUsb];
}

const UMLRTCapsulePart * Capsule_Computer::pluginUsb() const
{
    return &slot->parts[part_pluginUsb];
}

const UMLRTCapsulePart * Capsule_Computer::usbHub() const
{
    return &slot->parts[part_usbHub];
}

void Capsule_Computer::inject( const UMLRTMessage & msg )
{
    int rtdata = *(int *)msg.signal.getPayload();
    size_t size = msg.signal.getPayloadSize();

    printf("Capsule_Computer::inject: got msg port '%s' - signal(%s) data %d %s\n", msg.sap()->getName(), msg.getSignalName(),
            (size < sizeof(rtdata)) ? 0 : rtdata, (size < sizeof(rtdata)) ? "undef" : "");

    switch( msg.destPort->role()->id )
    {
    case port_inkPresent:
        switch( msg.signal.getId() )
        {
        case InkPresentProtocol::signal_cartridgePresent:
            printf("%s: inkPresent received %d\n", getName(), rtdata);
        }
        break;
    case port_timer:
        switch( msg.signal.getId() )
        {
        case UMLRTTimerProtocol::signal_timeout:
            printf("Got timeout priority %d\n", msg.getPriority());
            handleTimeout();
        }
        break;
    case port_tonerStatus:
        printf("%s: tonerStatus message received %d\n", getName(), rtdata);
    }
}

void Capsule_Computer::handleTimeout()
{

    if (inkPresent.cartridgePresentResponse(timeoutCount).send())
    {
        printf("%s: inkPresent send %d success\n", getName(), timeoutCount);
    }
    else
    {
        printf("%s: inkPresent send %d failed [%s]\n", getName(), timeoutCount, context()->strerror());
    }
    if (inkStatus.tonerReadyResponse(timeoutCount).send())
    {
        printf("%s: inkStatus send %d success\n", getName(), timeoutCount);
    }
    else
    {
        printf("%s: inkStatus send %d failed [%s]\n", getName(), timeoutCount, context()->strerror());
    }
    if (tonerStatus.tonerReadyResponse(timeoutCount).send())
    {
        printf("%s: tonerStatus send %d success\n", getName(), timeoutCount);
    }
    else
    {
        printf("%s: tonerStatus send %d failed [%s]\n", getName(), timeoutCount, context()->strerror());
    }
    if (printerStatus.tonerReadyResponse(timeoutCount).send())
    {
        printf("%s: printerStatus send %d success\n", getName(), timeoutCount);
    }
    else
    {
        printf("%s: printerStatus send %d failed [%s]\n", getName(), timeoutCount, context()->strerror());
    }
    if (computerStatus.tonerReady(timeoutCount).send())
    {
        printf("%s: computerStatus send %d success\n", getName(), timeoutCount);
    }
    else
    {
        printf("%s: computerStatus send %d failed [%s]\n", getName(), timeoutCount, context()->strerror());
    }


    if (timeoutCount == 0)
    {
        ; // Do something first time in?
    }
    else if (timeoutCount == 1)
    {
        printf("********************************************");
        printf("%s: incarnate Printer\n", getName());
        struct DataType1 dt =
                    { {timeoutCount, timeoutCount*10 + 1}, (timeoutCount & 1) != 0, timeoutCount*10 + 3.,
                            {{"start", 'a', timeoutCount*10 + 4}, {"middle", 'b', timeoutCount*10 + 5}, {"end", 'c', timeoutCount*10 + 6}}};

        printerId = frame.incarnate(optionalUsb(), Printer, UMLRTTypedValue(&dt, &RTType_DataType1));

        if (!printerId.isValid())
        {
            UMLRTController::Error error = context()->getError();
            if (error == UMLRTController::E_INC_NO_FREE_SLOT)
            {
                printf("%s: no slots available.\n", getName());
            }
            else
            {
                context()->perror("ERROR: Computer State1_entry could not incarnate Printer");
            }
        }
    }
    else if (timeoutCount == 2)
    {
        printf("********************************************");
        context()->debugOutputModel("model after Printer incarnate");

        printf("isType: printerId is Printer(%s) is Scanner(%s)\n",
                printerId.getCapsule()->isType("Printer") ? "YES" : "NO",
                printerId.getCapsule()->isType("Scanner") ? "YES" : "NO");
    }
    else if (timeoutCount == 3)
    {
        printf("********************************************");
        printf("%s: register computerStatus SPP\n", getName());

        if (!computerStatus.registerSPP("ComputerStatusOverride"))
        {
            context()->perror("ERROR: registerSPP failed.\n");
        }
    }
    else if (timeoutCount == 4)
    {
        printf("********************************************");
        printf("%s: register inkStatus SAP\n", getName());

        if (!inkStatus.registerSAP("InkStatusOverride"))
        {
            context()->perror("ERROR: registerSAP failed.\n");
        }
    }
    else if (timeoutCount == 5)
    {
        printf("********************************************");
        context()->debugOutputModel("model after computerStatus SPP and inkStatus SAP register");
    }
    else if (timeoutCount == 6)
    {
        printf("********************************************");
        printf("%s: deregister computerStatus SPP\n", getName());

        if (!computerStatus.deregisterSPP())
        {
            context()->perror("ERROR: deregisterSPP failed.\n");
        }
    }
    else if (timeoutCount == 7)
    {
        printf("********************************************");
        context()->debugOutputModel("model after computerStatus SPP deregister");
    }
    else if (timeoutCount == 8)
    {
        printf("********************************************");
        printf("%s: destroy Printer\n", getName());

        if (!frame.destroy(optionalUsb()))
        {
            context()->perror("ERROR: frame.destroy(optionalUsb()) failed.\n");
        }
    }
    else if (timeoutCount == 9)
    {
        printf("********************************************");
        context()->debugOutputModel("model after Printer destroy");
    }
    else if (timeoutCount == 10)
    {
        printf("********************************************");
        printf("%s: incarnate Scanner - end-spp-test\n", getName());
        scannerId = frame.incarnate(optionalUsb(), Scanner, NULL, &UMLRTObject_empty, Controller12);

        if (!scannerId.isValid())
        {
            UMLRTController::Error error = context()->getError();
            if (error == UMLRTController::E_INC_NO_FREE_SLOT)
            {
                printf("%s: no slots available.\n", getName());
            }
            else
            {
                context()->perror("ERROR: Computer State1_entry could not incarnate Scanner");
            }
        }
        else
        {
            printf("isType: scannerId is Printer(%s) is Scanner(%s)\n",
                    scannerId.getCapsule()->isType("Printer") ? "YES" : "NO",
                    scannerId.getCapsule()->isType("Scanner") ? "YES" : "NO");

        }
    }
    else if (timeoutCount == 11)
    {
        printf("********************************************");
        context()->debugOutputModel("model after Scanner incarnate");
    }
    else if (timeoutCount == 12)
    {
        printf("********************************************");
        printf("%s: incarnate Printer to 3nd optional slot\n", getName());

        printerId = frame.incarnate(optionalUsb(), Printer, UMLRTTypedValue(NULL,NULL), context(), 2);

        if (!printerId.isValid())
        {
            UMLRTController::Error error = context()->getError();
            if (error == UMLRTController::E_INC_NO_FREE_SLOT)
            {
                printf("%s: no slots available.\n", getName());
            }
            else
            {
                context()->perror("ERROR: Computer State1_entry could not incarnate Scanner");
            }
        }
    }
    else if (timeoutCount == 13)
    {
        for (size_t i = 0; i < optionalUsb()->numSlot; ++i)
        {
            UMLRTCapsuleId id = frame.incarnationAt(optionalUsb(), i);
            if (id.isValid())
            {
                printf("incarnationAt optionalUsb[%lu] valid : capsule %s\n", i, id.getCapsule()->name());
            }
        }
        printf("********************************************");
        context()->debugOutputModel("model after Printer incarnate into 3rd optional slot");
    }
    else if (timeoutCount == 14)
    {
        printf("********************************************");
        printf("%s: import Printer to 2nd plugin slot\n", getName());

        if (!frame.import(printerId, pluginUsb(), 1))
        {
            UMLRTController::Error error = context()->getError();
            if (error == UMLRTController::E_INC_NO_FREE_SLOT)
            {
                printf("%s: no slots available.\n", getName());
            }
            else
            {
                context()->perror("ERROR: Computer import of Printer into 2nd plugin slot failed");
            }
        }
    }
    else if (timeoutCount == 15)
    {
        printf("********************************************");
        context()->debugOutputModel("model after Printer import into 2nd plugin slot");
    }
    else if (timeoutCount == 16)
    {
        printf("********************************************");
        printf("%s: import Printer to 1st available plugin slot\n", getName());

        if (!frame.import(printerId, pluginUsb()))
        {
            UMLRTController::Error error = context()->getError();
            if (error == UMLRTController::E_INC_NO_FREE_SLOT)
            {
                printf("%s: no slots available.\n", getName());
            }
            else
            {
                context()->perror("ERROR: Computer import of Printer into 2nd plugin slot failed");
            }
        }
    }
    else if (timeoutCount == 17)
    {
        printf("********************************************");
        context()->debugOutputModel("model after Printer import into first available plugin slot");
    }
    else if (timeoutCount == 18)
    {
        printf("********************************************");
        printf("%s: attempt import Printer a 3rd time - should fail - occupied\n", getName());

        if (!frame.import(printerId, pluginUsb(), 1))
        {
            UMLRTController::Error error = context()->getError();
            if (error == UMLRTController::E_INC_NO_FREE_SLOT)
            {
                printf("%s: no slots available.\n", getName());
            }
            else
            {
                context()->perror("ERROR: Computer import of Printer into 2nd plugin slot failed");
            }
        }
    }
    else if (timeoutCount == 19)
    {
        printf("********************************************");
        context()->debugOutputModel("model after Printer 3rd import - occupied - that should have failed");
    }
    else if (timeoutCount == 20)
    {
        printf("********************************************");
        printf("%s: attempt import Printer a 4rd time - should fail - no inkPresent port available\n", getName());

        if (!frame.import(printerId, pluginUsb()))
        {
            UMLRTController::Error error = context()->getError();
            if (error == UMLRTController::E_INC_NO_FREE_SLOT)
            {
                printf("%s: no slots available.\n", getName());
            }
            else
            {
                context()->perror("ERROR: Computer import of Printer into 2nd plugin slot failed");
            }
        }
    }
    else if (timeoutCount == 21)
    {
        printf("********************************************");
        context()->debugOutputModel("model after Printer 4th import - port compatibility - that should have failed");
    }
    else if (timeoutCount == 22)
    {
        printf("********************************************");
        printf("%s: import Scanner - should go into free plugin slot\n", getName());

        if (!frame.import(scannerId, pluginUsb()))
        {
            UMLRTController::Error error = context()->getError();
            if (error == UMLRTController::E_INC_NO_FREE_SLOT)
            {
                printf("%s: no slots available.\n", getName());
            }
            else
            {
                context()->perror("ERROR: Computer import of Printer into 2nd plugin slot failed");
            }
        }
    }
    else if (timeoutCount == 23)
    {
        printf("********************************************");
        context()->debugOutputModel("model after Scanner import that should have succeeded");
    }
    else if (timeoutCount == 24)
    {
        printf("********************************************");
        printf("%s: deport Printer once\n", getName());

        if (!frame.deport(printerId, pluginUsb()))
        {
            UMLRTController::Error error = context()->getError();
            context()->perror("ERROR: Computer deport of Printer from plugin slot failed error(%d)", error);
        }
    }
    else if (timeoutCount == 25)
    {
        printf("********************************************");
        context()->debugOutputModel("model after Printer deport once");
    }
    else if (timeoutCount == 26)
    {
        printf("********************************************");
        printf("%s: deport Printer twice\n", getName());

        if (!frame.deport(printerId, pluginUsb()))
        {
            UMLRTController::Error error = context()->getError();
            context()->perror("ERROR: Computer deport of Printer from plugin slot failed error(%d)", error);
        }
    }
    else if (timeoutCount == 27)
    {
        printf("********************************************");
        context()->debugOutputModel("model after Printer deport twice");
    }
    else if (timeoutCount == 28)
    {
        printf("********************************************");
        printf("%s: deport Printer thrice - should fail\n", getName());

        if (!frame.deport(printerId, pluginUsb()))
        {
            UMLRTController::Error error = context()->getError();
            context()->perror("ERROR: Computer deport of Printer from plugin slot failed error(%d)", error);
        }
    }
    else if (timeoutCount == 29)
    {
        printf("********************************************");
        context()->debugOutputModel("model after Printer deport thrice");
    }
    else if (timeoutCount == 30)
    {
        printf("********************************************");
        printf("%s: deport Scanner - should succeed\n", getName());

        if (!frame.deport(scannerId, pluginUsb()))
        {
            UMLRTController::Error error = context()->getError();
            context()->perror("ERROR: Computer deport of Scanner from plugin slot failed error(%d)", error);
        }
    }
    else if (timeoutCount == 31)
    {
        printf("********************************************");
        printf("%s: destroy Scanner via id - should succeed\n", getName());

        if (!frame.destroy(scannerId))
        {
            UMLRTController::Error error = context()->getError();
            context()->perror("ERROR: Computer destroy scanner failed error(%d)", error);
        }
    }
    else if (timeoutCount == 32)
    {
        printf("********************************************");
        context()->debugOutputModel("model after destroying Scanner via id");
    }
    else if (timeoutCount == 33)
    {
        printf("********************************************");
        printf("%s: cancel intervalTimerId\n", getName());
        timer.cancelTimer(intervalTimerId);
        printf("%s: abort all controllers!\n", getName());
        context()->abortAllControllers();
        fflush(stdout);
    }
    ++timeoutCount;

    printf("%s: exit timeout(%d)\n", getName(), timeoutCount);
}

void Capsule_Computer::initialize( const UMLRTMessage & msg )
{
    actionchain_____StateMachine1__initialize__Chain1( msg );
    currentState = StateMachine1_State1;
    printf("%s: initialize - start the interval timer\n", getName());

    // Create a timer that is going to fire every 5 seconds.
    intervalTimerId = timer.informEvery(UMLRTTimespec(0,UMLRTTimespec::NANOSECONDS_PER_MILLISECOND*500), PRIORITY_HIGH);
    if (!intervalTimerId.isValid())
    {
        context()->perror("ERROR:%s: could not create interval timer", getName());
    }
    printf("%s size of optionalUsb(%lu)\n", getName(), optionalUsb()->size());
    printf("%s size of pluginUsb(%lu)\n", getName(), pluginUsb()->size());
    printf("%s size of usbHub(%lu)\n", getName(), usbHub()->size());

    inkPresent.bindingNotification(true); // Enable rtBound/rtUnbound notify.
    inkStatus.bindingNotification(true);
    tonerStatus.bindingNotification(true);
    printerStatus.bindingNotification(true);
    computerStatus.bindingNotification(true);
}

void Capsule_Computer::save_history( Capsule_Computer::State compositeState, Capsule_Computer::State subState )
{
    history[compositeState] = subState;
}

bool Capsule_Computer::check_history( Capsule_Computer::State compositeState, Capsule_Computer::State subState )
{
    return history[compositeState] == subState;
}

void Capsule_Computer::entryaction_____StateMachine1__State1__State1_entry( const UMLRTMessage & msg )
{
    //int rtdata = *(int *)msg.signal.getPayload();
}

void Capsule_Computer::transitionaction_____StateMachine1__Transition2__Chain3__timeoutRx( const UMLRTMessage & msg )
{
    //void * rtdata = *(void * *)msg.signal.getPayload();
    //inkPresent().cartridgePresentResponse(timeoutCount).send();
    ++timeoutCount;
    printf("%s: exit timeout(%d)\n", getName(), timeoutCount);

}

void Capsule_Computer::transitionaction_____StateMachine1__initialize__Chain1__computerInit( const UMLRTMessage & msg )
{
    //void * rtdata = *(void * *)msg.signal.getPayload();
        printf("%s: initialize - start the interval timer\n", getName());

//        // Create a timer that is going to fire every 1 seconds.
//        intervalTimerId = timer().informEvery(UMLRTTimespec(1,0));
//        if (!intervalTimerId.isValid())
//        {
//            context()->perror("ERROR:%s: could not create interval timer", getName());
//        }
}

void Capsule_Computer::transitionaction_____StateMachine1__tonerTypeRx__Chain5__tonerTypeOpaqueBehaviour( const UMLRTMessage & msg )
{
    int * rtdata = (int *)msg.getParam(0);
    printf("%s: tonerType %d priority %d\n", getName(), rtdata ? *rtdata : -1, msg.getPriority());
}

void Capsule_Computer::transitionaction_____StateMachine1__usbPortrx__Chain4__usbPortRx( const UMLRTMessage & msg )
{
    int * rtdata = (int *)msg.getParam(0);
    printf("%s: usbPort rx %d priority %d", getName(), rtdata ? *rtdata : -1, msg.getPriority());
}

void Capsule_Computer::actionchain_____StateMachine1__Transition2__Chain3( const UMLRTMessage & msg )
{
    transitionaction_____StateMachine1__Transition2__Chain3__timeoutRx( msg );
    entryaction_____StateMachine1__State1__State1_entry( msg );
}

void Capsule_Computer::actionchain_____StateMachine1__initialize__Chain1( const UMLRTMessage & msg )
{
}

void Capsule_Computer::unbindPort( bool isBorder, int portIndex, int farEndIndex )
{
    if (isBorder)
    {
        rtsif->unbindPort(borderPorts, portIndex, farEndIndex);
    }
    else
    {
        rtsif->unbindPort(internalPorts, portIndex, farEndIndex);
    }
}

void Capsule_Computer::bindPort( bool isBorder, int portIndex, int farEndIndex )
{
    if (isBorder)
    {
        rtsif->bindPort(borderPorts, portIndex, farEndIndex);
    }
    else
    {
        rtsif->bindPort(internalPorts, portIndex, farEndIndex);
    }
}

static const UMLRTCapsuleRole roles[] = 
{
    {
        "usbHub",
        &UsbHub,
        1,
        1,
        false,
        false
    },
    {
        "optionalUsb",
        &UsbDevice,
        0,
        3,
        true,
        false
    },
    {
        "pluginUsb",
        &UsbDevice,
        0,
        3,
        false,
        true
    }
};
static const UMLRTCommsPortRole portroles_internal[] = 
{
    {
        Capsule_Computer::port_frame,
        "UMLRTFrameProtocol",
        "frame",
        NULL, // registrationOverride
        0,
        false, // automatic
        false, // conjugated
        false, // locked
        false, // notification
        false, // sap
        false, // spp
        false, // wired
    },
    {
        Capsule_Computer::port_timer,
        "UMLRTTimerProtocol",
        "timer",
        NULL, // registrationOverride
        0,
        false, // automatic
        false, // conjugated
        false, // locked
        false, // notification
        false, // sap
        false, // spp
        false, // wired
    },
    {
        Capsule_Computer::port_inkPresent,
        "InkPresentProtocol",
        "inkPresent",
        NULL, // registrationOverride
        6,
        false, // automatic
        true, // conjugated
        false, // locked
        true, // notification
        false, // sap
        false, // spp
        true, // wired
    },
    {
        Capsule_Computer::port_inkStatus,
        "TonerStatusProtocol",
        "inkStatus",
        NULL, // registrationOverride
        1,
        false, // automatic
        true, // conjugated
        false, // locked
        true, // notification
        true, // sap
        false, // spp
        false, // wired
    },
    {
        Capsule_Computer::port_tonerStatus,
        "TonerStatusProtocol",
        "tonerStatus",
        (char*)"TonerStatusOverride", // registrationOverride
        1,
        true, // automatic
        true, // conjugated
        false, // locked
        true, // notification
        true, // sap
        false, // spp
        false, // wired
    },
    {
        Capsule_Computer::port_printerStatus,
        "TonerStatusProtocol",
        "printerStatus",
        (char*)"PrinterStatusOverride", // registrationOverride
        1,
        false, // automatic
        true, // conjugated
        true, // locked
        true, // notification
        true, // sap
        false, // spp
        false, // wired
    },
    {
        Capsule_Computer::port_computerStatus,
        "TonerStatusProtocol",
        "ComputerStatus",
        NULL, // registrationOverride
        4,
        false, // automatic
        false, // conjugated
        false, // locked
        true, // notification
        false, // sap
        true, // spp
        false, // wired
    },
    {
        Capsule_Computer::port_staticPort,
        "UsbPortProtocol",
        "staticPort",
        NULL, // registrationOverride
        1,
        false, // automatic
        false, // conjugated
        false, // locked
        true, // notification
        false, // sap
        false, // spp
        true, // wired
    }
};

static void instantiate_Computer( const UMLRTRtsInterface * rtsif, UMLRTSlot * slot, const UMLRTCommsPort * * borderPorts )
{
    const UMLRTCommsPort * * internalPorts = rtsif->createInternalPorts( slot, &Computer );
    rtsif->connectPorts( internalPorts[Capsule_Computer::internalport_inkPresent], 0, &slot->parts[Capsule_Computer::part_optionalUsb].slots[0]->ports[Capsule_UsbDevice::borderport_inkPresent], 0 );
    rtsif->connectPorts( internalPorts[Capsule_Computer::internalport_inkPresent], 1, &slot->parts[Capsule_Computer::part_optionalUsb].slots[1]->ports[Capsule_UsbDevice::borderport_inkPresent], 0 );
    rtsif->connectPorts( internalPorts[Capsule_Computer::internalport_inkPresent], 2, &slot->parts[Capsule_Computer::part_optionalUsb].slots[2]->ports[Capsule_UsbDevice::borderport_inkPresent], 0 );
    rtsif->connectPorts( internalPorts[Capsule_Computer::internalport_inkPresent], 3, &slot->parts[Capsule_Computer::part_pluginUsb].slots[0]->ports[Capsule_UsbDevice::borderport_inkPresent], 0 );
    rtsif->connectPorts( internalPorts[Capsule_Computer::internalport_inkPresent], 4, &slot->parts[Capsule_Computer::part_pluginUsb].slots[1]->ports[Capsule_UsbDevice::borderport_inkPresent], 0 );
    rtsif->connectPorts( internalPorts[Capsule_Computer::internalport_inkPresent], 5, &slot->parts[Capsule_Computer::part_pluginUsb].slots[2]->ports[Capsule_UsbDevice::borderport_inkPresent], 0 );
    rtsif->connectPorts( &slot->parts[Capsule_Computer::part_optionalUsb].slots[0]->ports[Capsule_UsbDevice::borderport_usbPort], 0, &slot->parts[Capsule_Computer::part_usbHub].slots[0]->ports[Capsule_UsbHub::borderport_usbPort], 0 );
    rtsif->connectPorts( &slot->parts[Capsule_Computer::part_optionalUsb].slots[1]->ports[Capsule_UsbDevice::borderport_usbPort], 0, &slot->parts[Capsule_Computer::part_usbHub].slots[0]->ports[Capsule_UsbHub::borderport_usbPort], 1 );
    rtsif->connectPorts( &slot->parts[Capsule_Computer::part_optionalUsb].slots[2]->ports[Capsule_UsbDevice::borderport_usbPort], 0, &slot->parts[Capsule_Computer::part_usbHub].slots[0]->ports[Capsule_UsbHub::borderport_usbPort], 2 );
    rtsif->connectPorts( &slot->parts[Capsule_Computer::part_pluginUsb].slots[0]->ports[Capsule_UsbDevice::borderport_usbPort], 0, &slot->parts[Capsule_Computer::part_usbHub].slots[0]->ports[Capsule_UsbHub::borderport_usbPort], 3 );
    rtsif->connectPorts( &slot->parts[Capsule_Computer::part_pluginUsb].slots[1]->ports[Capsule_UsbDevice::borderport_usbPort], 0, &slot->parts[Capsule_Computer::part_usbHub].slots[0]->ports[Capsule_UsbHub::borderport_usbPort], 4 );
    rtsif->connectPorts( &slot->parts[Capsule_Computer::part_pluginUsb].slots[2]->ports[Capsule_UsbDevice::borderport_usbPort], 0, &slot->parts[Capsule_Computer::part_usbHub].slots[0]->ports[Capsule_UsbHub::borderport_usbPort], 5 );
    UsbHub.instantiate( rtsif, slot->parts[Capsule_Computer::part_usbHub].slots[0], rtsif->createBorderPorts(slot->parts[Capsule_Computer::part_usbHub].slots[0], UsbHub.numPortRolesBorder) );
    slot->capsule = new Capsule_Computer( rtsif, &Computer, slot, borderPorts, internalPorts, false );
}

const UMLRTCapsuleClass Computer = 
{
    "Computer",
    NULL,
    instantiate_Computer,
    3,
    roles,
    0,
    NULL,
    8,
    portroles_internal
};
