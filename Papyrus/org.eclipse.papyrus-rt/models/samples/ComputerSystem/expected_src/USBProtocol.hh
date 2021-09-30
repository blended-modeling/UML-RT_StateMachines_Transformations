
#ifndef USBPROTOCOL_HH
#define USBPROTOCOL_HH

#include "USBDeviceClasses.hh"
#include "USBErrorCodes.hh"
#include "umlrtoutsignal.hh"
#include "umlrtprotocol.hh"
#include "umlrtsignal.hh"
struct UMLRTCommsPort;

namespace USBProtocol
{
    class Conj : public UMLRTProtocol
    {
    public:
        Conj( const UMLRTCommsPort * & srcPort );
        UMLRTOutSignal Eject() const;
        UMLRTOutSignal ack() const;
        UMLRTOutSignal connect() const;
        UMLRTOutSignal data( void * data ) const;
        UMLRTOutSignal deviceClass( const USBDeviceClasses & deviceClassID ) const;
        UMLRTOutSignal eod() const;
        UMLRTOutSignal error( const USBErrorCodes & errorCode ) const;
        UMLRTOutSignal getDeviceClass() const;
        UMLRTOutSignal resend() const;
        UMLRTOutSignal setDeviceBusID( int busId ) const;
        UMLRTOutSignal status( int percent ) const;
    };
    enum SignalId
    {
        signal_Eject = UMLRTSignal::FIRST_PROTOCOL_SIGNAL_ID,
        signal_ack,
        signal_connect,
        signal_data,
        signal_deviceClass,
        signal_eod,
        signal_error,
        signal_getDeviceClass,
        signal_resend,
        signal_setDeviceBusID,
        signal_status
    };
    class Base : public UMLRTProtocol
    {
    public:
        Base( const UMLRTCommsPort * & srcPort );
        UMLRTOutSignal Eject() const;
        UMLRTOutSignal ack() const;
        UMLRTOutSignal connect() const;
        UMLRTOutSignal data( void * data ) const;
        UMLRTOutSignal deviceClass( const USBDeviceClasses & deviceClassID ) const;
        UMLRTOutSignal eod() const;
        UMLRTOutSignal error( const USBErrorCodes & errorCode ) const;
        UMLRTOutSignal getDeviceClass() const;
        UMLRTOutSignal resend() const;
        UMLRTOutSignal setDeviceBusID( int busId ) const;
        UMLRTOutSignal status( int percent ) const;
    };
};

#endif

