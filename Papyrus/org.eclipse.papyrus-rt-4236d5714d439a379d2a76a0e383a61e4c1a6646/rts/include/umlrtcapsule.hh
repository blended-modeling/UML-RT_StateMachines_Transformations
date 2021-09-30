// umlrtcapsule.hh

/*******************************************************************************
* Copyright (c) 2014-2015 Zeligsoft (2009) Limited  and others.
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v10.html
*******************************************************************************/

#ifndef UMLRTCAPSULE_HH
#define UMLRTCAPSULE_HH

#include "umlrtcommsport.hh"
#include "umlrtcontroller.hh"
#include "umlrtcapsulerole.hh"
#include "umlrtcapsuletocontrollermap.hh"
#include "umlrtslot.hh"

// This is the base class for each generated capsule that is defined in the model.

// It will have the implementation of the state machine and will include data
// like the current state.

// Sub-classes define the model's capsules and contain user-defined and methods.

struct UMLRTSlot;
class UMLRTInMessage;
class UMLRTController;
struct UMLRTCapsuleClass;
class UMLRTRtsInterface;

class UMLRTCapsule
{
public:
    virtual ~UMLRTCapsule ( );
    virtual void initialize ( const UMLRTInMessage & msg ) = 0;
    virtual void inject ( const UMLRTInMessage & msg ) = 0;
    virtual void unbindPort ( bool isBorder, int portIndex, int farEndIndex );
    virtual void bindPort ( bool isBorder, int portIndex, int farEndIndex );
    virtual void unexpectedMessage ( ) const;
    virtual const char * getCurrentStateString ( ) const { return ""; }

    const UMLRTCommsPort * * getBorderPorts ( ) const { return borderPorts; }
    const UMLRTCommsPort * getInternalPorts ( ) const { return internalPorts; }
    UMLRTSlot * getSlot ( ) const { return slot; }
    const char * getName ( ) const { return slot->name; }
    size_t getIndex ( ) const { return slot->capsuleIndex; }
    UMLRTController::Error getError ( ) const { return context()->getError(); }
    const UMLRTCapsuleClass * getClass ( ) const { return capsuleClass; }
    const UMLRTMessage * getMsg ( ) const { return msg; }

    const UMLRTMessage * msg;

protected:

    // User can get the controller e.g. for incarnation or getting the last error.
    UMLRTController * context ( ) const { return slot->controller; }

    UMLRTCapsule ( const UMLRTRtsInterface * rtsif, const UMLRTCapsuleClass * capsuleClass, UMLRTSlot * slot, const UMLRTCommsPort * * borderPorts_, const UMLRTCommsPort * internalPorts_, bool isStatic_ );

    const UMLRTRtsInterface * rtsif;
    const UMLRTCapsuleClass * capsuleClass;
    UMLRTSlot * const slot;
    const UMLRTCommsPort * * borderPorts;
    const UMLRTCommsPort * internalPorts; // Capsule's internal ports. Each of these may be replicated.
    bool isStatic; // Keep this until we've soak-tested incarnate/destroy + import/deport.
};

#endif // UMLRTCAPSULE_HH
