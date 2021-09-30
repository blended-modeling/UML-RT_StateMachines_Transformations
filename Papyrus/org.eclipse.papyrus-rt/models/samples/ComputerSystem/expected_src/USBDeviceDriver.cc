
#include "USBDeviceDriver.hh"

#include "umlrtslot.hh"
#include <cstddef>
#include "umlrtcapsuleclass.hh"
#include "umlrtcommsportrole.hh"
class UMLRTRtsInterface;
struct UMLRTCommsPort;

const USBDeviceClasses Capsule_USBDeviceDriver::deviceClass;
Capsule_USBDeviceDriver::Capsule_USBDeviceDriver( const UMLRTCapsuleClass * cd, UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat )
: UMLRTCapsule( NULL, cd, st, border, internal, isStat )
, usbExtPort( borderPorts[borderport_usbExtPort] )
, usbInPort( borderPorts[borderport_usbInPort] )
, busID( 0 )
{
}






void Capsule_USBDeviceDriver::bindPort( bool isBorder, int portId, int index )
{
}

void Capsule_USBDeviceDriver::unbindPort( bool isBorder, int portId, int index )
{
}



void Capsule_USBDeviceDriver::initialize( const UMLRTMessage & msg )
{
}

void Capsule_USBDeviceDriver::inject( const UMLRTMessage & msg )
{
}


static const UMLRTCommsPortRole portroles_border[] = 
{
    {
        Capsule_USBDeviceDriver::port_usbExtPort,
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
        Capsule_USBDeviceDriver::port_usbInPort,
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

static void instantiate_USBDeviceDriver( const UMLRTRtsInterface * rts, UMLRTSlot * slot, const UMLRTCommsPort * * borderPorts )
{
    slot->capsule = new Capsule_USBDeviceDriver( &USBDeviceDriver, slot, borderPorts, NULL, false );
}

const UMLRTCapsuleClass USBDeviceDriver = 
{
    "USBDeviceDriver",
    NULL,
    instantiate_USBDeviceDriver,
    0,
    NULL,
    2,
    portroles_border,
    0,
    NULL
};

