
#ifndef USBPRINTERDRIVER_HH
#define USBPRINTERDRIVER_HH

#include "USBDeviceClasses.hh"
#include "USBDeviceDriver.hh"
#include "USBProtocol.hh"
#include "umlrtcapsuleclass.hh"
#include "umlrtmessage.hh"
struct UMLRTCommsPort;
struct UMLRTSlot;

#include <iostream>
#include <iomanip>
#include <ctime>
#include "USBDeviceClasses.hh"

class Capsule_USBPrinterDriver : public Capsule_USBDeviceDriver
{
public:
    Capsule_USBPrinterDriver( const UMLRTCapsuleClass * cd, UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat );
protected:
    USBProtocol::Conj usbExtPort;
public:
    enum BorderPortId
    {
        borderport_usbExtPort,
        borderport_usbInPort
    };
protected:
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
    static const USBDeviceClasses usbDevice = Printer;
public:
    virtual void initialize( const UMLRTMessage & msg );
    virtual void inject( const UMLRTMessage & msg );
};
extern const UMLRTCapsuleClass USBPrinterDriver;

#endif

