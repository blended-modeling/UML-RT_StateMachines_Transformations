
#ifndef LOCALPRINTER_HH
#define LOCALPRINTER_HH

#include "USBDeviceClasses.hh"
#include "USBProtocol.hh"
#include "umlrtcapsule.hh"
#include "umlrtcapsuleclass.hh"
#include "umlrtlogprotocol.hh"
#include "umlrtmessage.hh"
struct UMLRTCommsPort;
struct UMLRTSlot;

#include <iostream>
#include <iomanip>
#include <ctime>

class Capsule_LocalPrinter : public UMLRTCapsule
{
public:
    Capsule_LocalPrinter( const UMLRTCapsuleClass * cd, UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat );
protected:
    UMLRTLogProtocol_baserole log;
    USBProtocol::Base usbPort;
public:
    enum BorderPortId
    {
        borderport_usbPort,
        borderport_log
    };
    enum PartId
    {
    };
    enum PortId
    {
        port_log,
        port_usbPort
    };
    virtual void bindPort( bool isBorder, int portId, int index );
    virtual void unbindPort( bool isBorder, int portId, int index );
protected:
    USBDeviceClasses usbClass;
    bool connectionStatus;
    bool printingData;
public:
    virtual void inject( const UMLRTMessage & message );
    virtual void initialize( const UMLRTMessage & message );
    const char * getCurrentStateString() const;
private:
    enum State
    {
        Connected,
        Disconnected,
        SPECIAL_INTERNAL_STATE_TOP,
        SPECIAL_INTERNAL_STATE_UNVISITED
    };
    const char * stateNames[4];
    State currentState;
    void update_state( State newState );
    void entryaction_____Disconnected( const UMLRTMessage * msg );
    void transitionaction_____MessageSent( const UMLRTMessage * msg );
    void transitionaction_____MsgSendFail( const UMLRTMessage * msg );
    void transitionaction_____initialize( const UMLRTMessage * msg );
    void transitionaction_____onConnect( const UMLRTMessage * msg );
    void transitionaction_____onData( const UMLRTMessage * msg );
    void transitionaction_____onEOD( const UMLRTMessage * msg );
    void transitionaction_____onEject( const UMLRTMessage * msg );
    bool guard_____MessageSent( const UMLRTMessage * msg );
    bool guard_____MsgSendFail( const UMLRTMessage * msg );
    void actionchain_____MessageSent( const UMLRTMessage * msg );
    void actionchain_____MsgSendFail( const UMLRTMessage * msg );
    void actionchain_____initialize( const UMLRTMessage * msg );
    void actionchain_____onConnect( const UMLRTMessage * msg );
    void actionchain_____onData( const UMLRTMessage * msg );
    void actionchain_____onEOD( const UMLRTMessage * msg );
    void actionchain_____onEject( const UMLRTMessage * msg );
    State choice_____Connected_( const UMLRTMessage * msg );
    State state_____Connected( const UMLRTMessage * msg );
    State state_____Disconnected( const UMLRTMessage * msg );
};
extern const UMLRTCapsuleClass LocalPrinter;

#endif

