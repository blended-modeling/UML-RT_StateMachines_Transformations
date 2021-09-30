
#include "USBPrinterDriver.hh"

#include "USBDeviceDriver.hh"
#include "umlrtslot.hh"
#include <cstddef>
#include "umlrtcapsuleclass.hh"
#include "umlrtcommsportrole.hh"
class UMLRTRtsInterface;
struct UMLRTCommsPort;

const USBDeviceClasses Capsule_USBPrinterDriver::usbDevice;
Capsule_USBPrinterDriver::Capsule_USBPrinterDriver( const UMLRTCapsuleClass * cd, UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat )
: Capsule_USBDeviceDriver( cd, st, border, internal, isStat )
, usbExtPort( borderPorts[borderport_usbExtPort] )
, usbInPort( borderPorts[borderport_usbInPort] )
{
}






void Capsule_USBPrinterDriver::bindPort( bool isBorder, int portId, int index )
{
}

void Capsule_USBPrinterDriver::unbindPort( bool isBorder, int portId, int index )
{
}


void Capsule_USBPrinterDriver::initialize( const UMLRTMessage & msg )
{
}

void Capsule_USBPrinterDriver::inject( const UMLRTMessage & msg )
{
}


static const UMLRTCommsPortRole portroles_border[] = 
{
    {
        Capsule_USBPrinterDriver::port_usbExtPort,
        "USBProtocol",
        "usbExtPort",
        "",
        1,
        true,
        true,
        false,
        false,
        false,
        false,
        true
    },
    {
        Capsule_USBPrinterDriver::port_usbInPort,
        "USBProtocol",
        "usbInPort",
        "",
        1,
        true,
        true,
        false,
        false,
        false,
        false,
        true
    }
};

static void instantiate_USBPrinterDriver( const UMLRTRtsInterface * rts, UMLRTSlot * slot, const UMLRTCommsPort * * borderPorts )
{
    slot->capsule = new Capsule_USBPrinterDriver( &USBPrinterDriver, slot, borderPorts, NULL, false );
}

const UMLRTCapsuleClass USBPrinterDriver = 
{
    "USBPrinterDriver",
    &USBDeviceDriver,
    instantiate_USBPrinterDriver,
    0,
    NULL,
    2,
    portroles_border,
    0,
    NULL
};

