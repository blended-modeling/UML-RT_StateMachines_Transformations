
#ifndef USBDEVICEDRIVER_HH
#define USBDEVICEDRIVER_HH

#include "USBDeviceClasses.hh"
#include "USBProtocol.hh"
#include "umlrtcapsule.hh"
#include "umlrtcapsuleclass.hh"
#include "umlrtmessage.hh"
struct UMLRTCommsPort;
struct UMLRTSlot;

#include "USBDeviceClasses.hh"

#include <iostream>
#include <iomanip>
#include <ctime>
#include "USBDeviceClasses.hh"

class Capsule_USBDeviceDriver : public UMLRTCapsule
{
public:
    Capsule_USBDeviceDriver( const UMLRTCapsuleClass * cd, UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat );
    enum BorderPortId
    {
        borderport_usbExtPort,
        borderport_usbInPort
    };
protected:
    USBProtocol::Conj usbExtPort;
    USBProtocol::Conj usbInPort;
public:
    enum PartId
    {
    };
    enum PortId
    {
        port_usbExtPort,
        port_usbInPort
    };
    virtual void bindPort( bool isBorder, int portId, int index );
    virtual void unbindPort( bool isBorder, int portId, int index );
protected:
    static const USBDeviceClasses deviceClass = Unspecified;
    int busID;
public:
    virtual void initialize( const UMLRTMessage & msg );
    virtual void inject( const UMLRTMessage & msg );
};
extern const UMLRTCapsuleClass USBDeviceDriver;

#endif

