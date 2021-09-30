
#include "USBStorageDriver.hh"

#include "USBDeviceDriver.hh"
#include "umlrtslot.hh"
#include <cstddef>
#include "umlrtcapsuleclass.hh"
#include "umlrtcommsportrole.hh"
class UMLRTRtsInterface;
struct UMLRTCommsPort;

const USBDeviceClasses Capsule_USBStorageDriver::deviceClass;
Capsule_USBStorageDriver::Capsule_USBStorageDriver( const UMLRTCapsuleClass * cd, UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat )
: Capsule_USBDeviceDriver( cd, st, border, internal, isStat )
, usbExtPort( borderPorts[borderport_usbExtPort] )
, usbInPort( borderPorts[borderport_usbInPort] )
{
}






void Capsule_USBStorageDriver::bindPort( bool isBorder, int portId, int index )
{
}

void Capsule_USBStorageDriver::unbindPort( bool isBorder, int portId, int index )
{
}


void Capsule_USBStorageDriver::initialize( const UMLRTMessage & msg )
{
}

void Capsule_USBStorageDriver::inject( const UMLRTMessage & msg )
{
}


static const UMLRTCommsPortRole portroles_border[] = 
{
    {
        Capsule_USBStorageDriver::port_usbExtPort,
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
        Capsule_USBStorageDriver::port_usbInPort,
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

static void instantiate_USBStorageDriver( const UMLRTRtsInterface * rts, UMLRTSlot * slot, const UMLRTCommsPort * * borderPorts )
{
    slot->capsule = new Capsule_USBStorageDriver( &USBStorageDriver, slot, borderPorts, NULL, false );
}

const UMLRTCapsuleClass USBStorageDriver = 
{
    "USBStorageDriver",
    &USBDeviceDriver,
    instantiate_USBStorageDriver,
    0,
    NULL,
    2,
    portroles_border,
    0,
    NULL
};

