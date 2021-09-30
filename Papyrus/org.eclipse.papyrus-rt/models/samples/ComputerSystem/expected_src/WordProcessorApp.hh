
#ifndef WORDPROCESSORAPP_HH
#define WORDPROCESSORAPP_HH

#include "AppControl.hh"
#include "AppType.hh"
#include "Application.hh"
#include "Resource.hh"
#include "USBProtocol.hh"
#include "umlrtcapsuleclass.hh"
#include "umlrtcapsuleid.hh"
#include "umlrtframeprotocol.hh"
#include "umlrtlogprotocol.hh"
#include "umlrtmessage.hh"
struct UMLRTCapsulePart;
struct UMLRTCommsPort;
struct UMLRTSlot;

#include <iostream>
#include <iomanip>
#include <ctime>
#include <string>
#include "ResourceType.hh"
#include "AppType.hh"

class Capsule_WordProcessorApp : public Capsule_Application
{
public:
    Capsule_WordProcessorApp( const UMLRTCapsuleClass * cd, UMLRTSlot * st, const UMLRTCommsPort * * border, const UMLRTCommsPort * * internal, bool isStat );
    enum BorderPortId
    {
        borderport_UserControlePort,
        borderport_resourcePort,
        borderport_frame,
        borderport_usbPort,
        borderport_log
    };
protected:
    AppControl::Base UserControlePort;
    UMLRTFrameProtocol_baserole frame;
    UMLRTLogProtocol_baserole log;
    Resource::Conj resourcePort;
    USBProtocol::Conj usbPort;
public:
    enum PartId
    {
        part_deviceInterface
    };
protected:
    const UMLRTCapsulePart * const deviceInterface;
public:
    enum PortId
    {
        port_UserControlePort,
        port_frame,
        port_log,
        port_resourcePort,
        port_usbPort
    };
    virtual void bindPort( bool isBorder, int portId, int index );
    virtual void unbindPort( bool isBorder, int portId, int index );
protected:
    char * document;
    UMLRTCapsuleId printerID;
    int progress;
    UMLRTCapsuleId storageID;
    bool importOK;
    bool status;
    AppType apptype;
public:
    virtual void inject( const UMLRTMessage & message );
    virtual void initialize( const UMLRTMessage & message );
    const char * getCurrentStateString() const;
private:
    enum State
    {
        Printing,
        Saving,
        Printing__Printing,
        Printing__boundary,
        Printing__waitForPrinter,
        Saving__Saving,
        Saving__WaitForStorage,
        Saving__boundary,
        WaitingForCommand,
        SPECIAL_INTERNAL_STATE_TOP,
        SPECIAL_INTERNAL_STATE_UNVISITED
    };
    const char * stateNames[11];
    State currentState;
    State history[2];
    void save_history( State compositeState, State subState );
    bool check_history( State compositeState, State subState );
    void update_state( State newState );
    void entryaction_____Printing( const UMLRTMessage * msg );
    void entryaction_____Printing__waitForPrinter( const UMLRTMessage * msg );
    void entryaction_____Saving( const UMLRTMessage * msg );
    void entryaction_____Saving__WaitForStorage( const UMLRTMessage * msg );
    void entryaction_____WaitingForCommand( const UMLRTMessage * msg );
    void transitionaction_____Initial( const UMLRTMessage * msg );
    void transitionaction_____Printing__getPrinter( const UMLRTMessage * msg );
    void transitionaction_____Printing__importNOK( const UMLRTMessage * msg );
    void transitionaction_____Printing__importOK( const UMLRTMessage * msg );
    void transitionaction_____Printing__onPrintResource( const UMLRTMessage * msg );
    void transitionaction_____Printing__onPrintStatus( const UMLRTMessage * msg );
    void transitionaction_____Printing__onResNotAvail( const UMLRTMessage * msg );
    void transitionaction_____Printing__printCompleted( const UMLRTMessage * msg );
    void transitionaction_____Printing__printPrintProgress( const UMLRTMessage * msg );
    void transitionaction_____Printing__transition8( const UMLRTMessage * msg );
    void transitionaction_____Saving__getStorage( const UMLRTMessage * msg );
    void transitionaction_____Saving__importNOK( const UMLRTMessage * msg );
    void transitionaction_____Saving__importOK( const UMLRTMessage * msg );
    void transitionaction_____Saving__isNOK( const UMLRTMessage * msg );
    void transitionaction_____Saving__onResNotAvail( const UMLRTMessage * msg );
    void transitionaction_____Saving__onSaveStatus( const UMLRTMessage * msg );
    void transitionaction_____Saving__onStorageResource( const UMLRTMessage * msg );
    void transitionaction_____Saving__printSaveProgress( const UMLRTMessage * msg );
    void transitionaction_____Saving__saveCompleted( const UMLRTMessage * msg );
    void transitionaction_____onAddToDoc( const UMLRTMessage * msg );
    void transitionaction_____onCreateDoc( const UMLRTMessage * msg );
    void transitionaction_____onPrintDocument( const UMLRTMessage * msg );
    void transitionaction_____onSaveDocument( const UMLRTMessage * msg );
    void transitionaction_____printComplete_cont( const UMLRTMessage * msg );
    void transitionaction_____saveComplete_cont( const UMLRTMessage * msg );
    bool guard_____Printing__importNOK( const UMLRTMessage * msg );
    bool guard_____Printing__importOK( const UMLRTMessage * msg );
    bool guard_____Printing__printCompleted( const UMLRTMessage * msg );
    bool guard_____Printing__printPrintProgress( const UMLRTMessage * msg );
    bool guard_____Printing__transition8( const UMLRTMessage * msg );
    bool guard_____Printing__transition9( const UMLRTMessage * msg );
    bool guard_____Saving__importNOK( const UMLRTMessage * msg );
    bool guard_____Saving__importOK( const UMLRTMessage * msg );
    bool guard_____Saving__isNOK( const UMLRTMessage * msg );
    bool guard_____Saving__isOK( const UMLRTMessage * msg );
    bool guard_____Saving__printSaveProgress( const UMLRTMessage * msg );
    bool guard_____Saving__saveCompleted( const UMLRTMessage * msg );
    void actionchain_____Initial( const UMLRTMessage * msg );
    void actionchain_____Printing__getPrinter( const UMLRTMessage * msg );
    void actionchain_____Printing__importNOK( const UMLRTMessage * msg );
    void actionchain_____Printing__importOK( const UMLRTMessage * msg );
    void actionchain_____Printing__new_transition_1( const UMLRTMessage * msg );
    void actionchain_____Printing__new_transition_2( const UMLRTMessage * msg );
    void actionchain_____Printing__new_transition_3_to_unvisited_boundary( const UMLRTMessage * msg );
    void actionchain_____Printing__new_transition_4_to_visited_boundary( const UMLRTMessage * msg );
    void actionchain_____Printing__onPrintResource( const UMLRTMessage * msg );
    void actionchain_____Printing__onPrintStatus( const UMLRTMessage * msg );
    void actionchain_____Printing__onResNotAvail( const UMLRTMessage * msg );
    void actionchain_____Printing__printCompleted( const UMLRTMessage * msg );
    void actionchain_____Printing__printPrintProgress( const UMLRTMessage * msg );
    void actionchain_____Printing__transition8( const UMLRTMessage * msg );
    void actionchain_____Printing__transition9( const UMLRTMessage * msg );
    void actionchain_____Saving__getStorage( const UMLRTMessage * msg );
    void actionchain_____Saving__importNOK( const UMLRTMessage * msg );
    void actionchain_____Saving__importOK( const UMLRTMessage * msg );
    void actionchain_____Saving__isNOK( const UMLRTMessage * msg );
    void actionchain_____Saving__isOK( const UMLRTMessage * msg );
    void actionchain_____Saving__new_transition_5( const UMLRTMessage * msg );
    void actionchain_____Saving__new_transition_6( const UMLRTMessage * msg );
    void actionchain_____Saving__new_transition_7_to_unvisited_boundary( const UMLRTMessage * msg );
    void actionchain_____Saving__new_transition_8_to_visited_boundary( const UMLRTMessage * msg );
    void actionchain_____Saving__onResNotAvail( const UMLRTMessage * msg );
    void actionchain_____Saving__onSaveStatus( const UMLRTMessage * msg );
    void actionchain_____Saving__onStorageResource( const UMLRTMessage * msg );
    void actionchain_____Saving__printSaveProgress( const UMLRTMessage * msg );
    void actionchain_____Saving__saveCompleted( const UMLRTMessage * msg );
    void actionchain_____StorageNotAvailable( const UMLRTMessage * msg );
    void actionchain_____onAddToDoc( const UMLRTMessage * msg );
    void actionchain_____onCreateDoc( const UMLRTMessage * msg );
    void actionchain_____onPrintDocument( const UMLRTMessage * msg );
    void actionchain_____onSaveDocument( const UMLRTMessage * msg );
    void actionchain_____printComplete_cont( const UMLRTMessage * msg );
    void actionchain_____printerUnavailable( const UMLRTMessage * msg );
    void actionchain_____saveComplete_cont( const UMLRTMessage * msg );
    State junction_____Printing__EntryPoint1( const UMLRTMessage * msg );
    State junction_____Printing__ExitPoint1( const UMLRTMessage * msg );
    State junction_____Printing__ExitPoint2( const UMLRTMessage * msg );
    State junction_____Saving__EntryPoint1( const UMLRTMessage * msg );
    State junction_____Saving__ExitPoint1( const UMLRTMessage * msg );
    State junction_____Saving__ExitPoint2( const UMLRTMessage * msg );
    State choice_____Printing__Choice1( const UMLRTMessage * msg );
    State choice_____Printing__deephistory( const UMLRTMessage * msg );
    State choice_____Printing__importOK( const UMLRTMessage * msg );
    State choice_____Printing__printOK( const UMLRTMessage * msg );
    State choice_____Saving__Choice1( const UMLRTMessage * msg );
    State choice_____Saving__deephistory( const UMLRTMessage * msg );
    State choice_____Saving__importOK( const UMLRTMessage * msg );
    State choice_____Saving__saveOK( const UMLRTMessage * msg );
    State state_____Printing__Printing( const UMLRTMessage * msg );
    State state_____Printing__boundary( const UMLRTMessage * msg );
    State state_____Printing__waitForPrinter( const UMLRTMessage * msg );
    State state_____Saving__Saving( const UMLRTMessage * msg );
    State state_____Saving__WaitForStorage( const UMLRTMessage * msg );
    State state_____Saving__boundary( const UMLRTMessage * msg );
    State state_____WaitingForCommand( const UMLRTMessage * msg );
};
extern const UMLRTCapsuleClass WordProcessorApp;

#endif

