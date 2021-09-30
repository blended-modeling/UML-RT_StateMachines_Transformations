
#include "AppControl.hh"

#include "umlrtobjectclass.hh"
#include "umlrtoutsignal.hh"
#include <cstddef>
struct UMLRTCommsPort;

static UMLRTObject_field fields_addToDoc[] = 
{
    #ifdef NEED_NON_FLEXIBLE_ARRAY
    {
        0,
        0,
        0,
        0,
        0
    }
    #endif
};

static UMLRTObject payload_addToDoc = 
{
    0,
    #ifdef NEED_NON_FLEXIBLE_ARRAY
    1
    #else
    0
    #endif
    ,
    fields_addToDoc
};

static UMLRTObject_field fields_closeDocument[] = 
{
    #ifdef NEED_NON_FLEXIBLE_ARRAY
    {
        0,
        0,
        0,
        0,
        0
    }
    #endif
};

static UMLRTObject payload_closeDocument = 
{
    0,
    #ifdef NEED_NON_FLEXIBLE_ARRAY
    1
    #else
    0
    #endif
    ,
    fields_closeDocument
};

struct params_createDocument
{
    char * fileName;
    void * data;
};

static UMLRTObject_field fields_createDocument[] = 
{
    {
        "fileName",
        &UMLRTType_charptr,
        offsetof( params_createDocument, fileName ),
        1,
        0
    },
    {
        "data",
        &UMLRTType_ptr,
        offsetof( params_createDocument, data ),
        1,
        0
    }
};

static UMLRTObject payload_createDocument = 
{
    sizeof( params_createDocument ),
    2,
    fields_createDocument
};

static UMLRTObject_field fields_openDocument[] = 
{
    {
        "fileName",
        &UMLRTType_charptr,
        0,
        1,
        0
    }
};

static UMLRTObject payload_openDocument = 
{
    sizeof( char * ),
    1,
    fields_openDocument
};

static UMLRTObject_field fields_printDocument[] = 
{
    #ifdef NEED_NON_FLEXIBLE_ARRAY
    {
        0,
        0,
        0,
        0,
        0
    }
    #endif
};

static UMLRTObject payload_printDocument = 
{
    0,
    #ifdef NEED_NON_FLEXIBLE_ARRAY
    1
    #else
    0
    #endif
    ,
    fields_printDocument
};

static UMLRTObject_field fields_saveDocument[] = 
{
    #ifdef NEED_NON_FLEXIBLE_ARRAY
    {
        0,
        0,
        0,
        0,
        0
    }
    #endif
};

static UMLRTObject payload_saveDocument = 
{
    0,
    #ifdef NEED_NON_FLEXIBLE_ARRAY
    1
    #else
    0
    #endif
    ,
    fields_saveDocument
};

static UMLRTObject_field fields_docID[] = 
{
    {
        "docID",
        &UMLRTType_int,
        0,
        1,
        0
    }
};

static UMLRTObject payload_docID = 
{
    sizeof( int ),
    1,
    fields_docID
};

static UMLRTObject_field fields_error[] = 
{
    #ifdef NEED_NON_FLEXIBLE_ARRAY
    {
        0,
        0,
        0,
        0,
        0
    }
    #endif
};

static UMLRTObject payload_error = 
{
    0,
    #ifdef NEED_NON_FLEXIBLE_ARRAY
    1
    #else
    0
    #endif
    ,
    fields_error
};

AppControl::Base::Base( const UMLRTCommsPort * & srcPort )
: UMLRTProtocol( srcPort )
{
}

UMLRTOutSignal AppControl::Base::docID( int docID ) const
{
    UMLRTOutSignal signal;
    signal.initialize( "docID", signal_docID, srcPort, &payload_docID, &docID );
    return signal;
}

UMLRTOutSignal AppControl::Base::error() const
{
    UMLRTOutSignal signal;
    signal.initialize( "error", signal_error, srcPort, &payload_error );
    return signal;
}

AppControl::Conj::Conj( const UMLRTCommsPort * & srcPort )
: UMLRTProtocol( srcPort )
{
}

UMLRTOutSignal AppControl::Conj::addToDoc() const
{
    UMLRTOutSignal signal;
    signal.initialize( "addToDoc", signal_addToDoc, srcPort, &payload_addToDoc );
    return signal;
}

UMLRTOutSignal AppControl::Conj::closeDocument() const
{
    UMLRTOutSignal signal;
    signal.initialize( "closeDocument", signal_closeDocument, srcPort, &payload_closeDocument );
    return signal;
}

UMLRTOutSignal AppControl::Conj::createDocument( char * fileName, void * data ) const
{
    UMLRTOutSignal signal;
    signal.initialize( "createDocument", signal_createDocument, srcPort, &payload_createDocument, &fileName, &data );
    return signal;
}

UMLRTOutSignal AppControl::Conj::openDocument( char * fileName ) const
{
    UMLRTOutSignal signal;
    signal.initialize( "openDocument", signal_openDocument, srcPort, &payload_openDocument, &fileName );
    return signal;
}

UMLRTOutSignal AppControl::Conj::printDocument() const
{
    UMLRTOutSignal signal;
    signal.initialize( "printDocument", signal_printDocument, srcPort, &payload_printDocument );
    return signal;
}

UMLRTOutSignal AppControl::Conj::saveDocument() const
{
    UMLRTOutSignal signal;
    signal.initialize( "saveDocument", signal_saveDocument, srcPort, &payload_saveDocument );
    return signal;
}


