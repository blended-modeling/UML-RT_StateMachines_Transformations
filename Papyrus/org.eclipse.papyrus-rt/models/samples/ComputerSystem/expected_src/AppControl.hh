
#ifndef APPCONTROL_HH
#define APPCONTROL_HH

#include "umlrtoutsignal.hh"
#include "umlrtprotocol.hh"
#include "umlrtsignal.hh"
struct UMLRTCommsPort;

namespace AppControl
{
    class Base : public UMLRTProtocol
    {
    public:
        Base( const UMLRTCommsPort * & srcPort );
        UMLRTOutSignal docID( int docID ) const;
        UMLRTOutSignal error() const;
    };
    enum SignalId
    {
        signal_addToDoc = UMLRTSignal::FIRST_PROTOCOL_SIGNAL_ID,
        signal_closeDocument,
        signal_createDocument,
        signal_openDocument,
        signal_printDocument,
        signal_saveDocument,
        signal_docID,
        signal_error
    };
    class Conj : public UMLRTProtocol
    {
    public:
        Conj( const UMLRTCommsPort * & srcPort );
        UMLRTOutSignal addToDoc() const;
        UMLRTOutSignal closeDocument() const;
        UMLRTOutSignal createDocument( char * fileName, void * data ) const;
        UMLRTOutSignal openDocument( char * fileName ) const;
        UMLRTOutSignal printDocument() const;
        UMLRTOutSignal saveDocument() const;
    };
};

#endif

