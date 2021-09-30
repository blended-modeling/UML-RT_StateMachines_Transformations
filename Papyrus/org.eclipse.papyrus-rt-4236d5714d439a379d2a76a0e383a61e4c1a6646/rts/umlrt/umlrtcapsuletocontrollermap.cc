// umlrtcapsuletocontrollermap.cc

/*******************************************************************************
* Copyright (c) 2014-2015 Zeligsoft (2009) Limited  and others.
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v10.html
*******************************************************************************/

#include "umlrtcapsule.hh"
#include "umlrtcapsuletocontrollermap.hh"
#include "umlrtcontroller.hh"
#include "umlrthashmap.hh"
#include "basefatal.hh"
#include "basedebug.hh"
#include "basedebugtype.hh"
#include <string.h>
#include <stdlib.h>
#include <stdio.h>
#include "osutil.hh"

UMLRTHashMap * UMLRTCapsuleToControllerMap::capsuleToControllerListMap = NULL;
UMLRTHashMap * UMLRTCapsuleToControllerMap::controllerNameMap = NULL;
UMLRTHashMap * UMLRTCapsuleToControllerMap::capsuleNameMap = NULL;
UMLRTSlot * UMLRTCapsuleToControllerMap::defaultSlotList = NULL;
int UMLRTCapsuleToControllerMap::numDefaultSlotList = 0;

/*static*/ UMLRTHashMap * UMLRTCapsuleToControllerMap::getControllerNameMap()
{
    if (controllerNameMap == NULL)
    {
        controllerNameMap = new UMLRTHashMap("controllerNameMap", UMLRTHashMap::compareString);
    }
    return controllerNameMap;
}

/*static*/ UMLRTHashMap * UMLRTCapsuleToControllerMap::getCapsuleToControllerListMap()
{
    if (capsuleToControllerListMap == NULL)
    {
        capsuleToControllerListMap = new UMLRTHashMap("capsuleToControllerMap", UMLRTHashMap::compareString);
    }
    return capsuleToControllerListMap;
}

/*static*/ UMLRTHashMap * UMLRTCapsuleToControllerMap::getCapsuleNameMap()
{
    if (capsuleNameMap == NULL)
    {
        capsuleNameMap = new UMLRTHashMap("capsuleNameMap", UMLRTHashMap::compareString);
    }
    return capsuleNameMap;
}

// Add a controller by name.
/*static*/ bool UMLRTCapsuleToControllerMap::addController( const char * controllername, UMLRTController * controller )
{
    if (getControllerFromName(controllername) != NULL)
    {
        FATAL("ERROR: Controller %s has already registered.", controllername);
    }
    BDEBUG(BD_CONTROLLER, "add controller(%s) p(%p)\n", controllername, controller );

    getControllerNameMap()->insert( controllername, (void *)controller);

    return true;
}

// Add a capsule by name.
/*static*/ void UMLRTCapsuleToControllerMap::addCapsule( const char * capsulename, UMLRTCapsule * capsule )
{
    getCapsuleNameMap()->insert( capsulename, (void *)capsule);
}

/*static*/ const UMLRTCapsule * UMLRTCapsuleToControllerMap::getCapsuleFromName ( const char * capsulename )
{
    return (const UMLRTCapsule *)getCapsuleNameMap()->getObject(capsulename);
}

// Remove a capsule instance from the list.
/*static*/ void UMLRTCapsuleToControllerMap::removeCapsule ( const char * capsuleName, UMLRTCapsule * capsule )
{
    getCapsuleNameMap()->remove(capsuleName);
}

/*static*/ UMLRTController * UMLRTCapsuleToControllerMap::getControllerFromName( const char * controllerName )
{
    return (UMLRTController *)getControllerNameMap()->getObject(controllerName);
}

/*static*/ int UMLRTCapsuleToControllerMap::getDefaultSlotList ( UMLRTSlot * * slot_p )
{
    *slot_p = defaultSlotList;

    return numDefaultSlotList;
}

/*static*/ UMLRTController * UMLRTCapsuleToControllerMap::getFirstController()
{
    return (UMLRTController *)getControllerNameMap()->getFirstObject();
}

/*static*/ const char * UMLRTCapsuleToControllerMap::getControllerNameForCapsule ( const char * capsuleName, const UMLRTCapsuleClass * capsuleClass )
{
    const char * controllerName = NULL;
    UMLRTHashMap * controllerList = (UMLRTHashMap *)getCapsuleToControllerListMap()->getObject(capsuleName);
    if (controllerList)
    {
        // Attempt to find the class-specific controller.
        controllerName = (const char *) controllerList->getObject(capsuleClass->name);
        if (controllerName == NULL)
        {
            // Class-specific controller not assigned - look for the default.
            controllerName = (const char *)controllerList->getObject((void*)NULL);
        }
    }
    return controllerName;
}

// Return the controller assigned to this capsule. Returns NULL if no controller is assigned or doesn't exist.
/*static*/ UMLRTController * UMLRTCapsuleToControllerMap::getControllerForCapsule ( const char * capsuleName, const UMLRTCapsuleClass * capsuleClass )
{
    const char * controllerName = getControllerNameForCapsule( capsuleName, capsuleClass );

    return (UMLRTController *)getControllerFromName(controllerName);
}

/*static*/ void UMLRTCapsuleToControllerMap::addCapsuleToControllerSpec ( char * capsuleName, char * controllerName, char * className )
{
    // className can be NULL, indicating the default controller for all capsule classes incarnated into the slot.

    UMLRTHashMap * controllerList = (UMLRTHashMap *)getCapsuleToControllerListMap()->getObject(capsuleName);

    if (controllerList == NULL)
    {
        // This capsule has no previous controller assignments - no controller list - add one.
        const char * capsuleKey = strdup(capsuleName);
        controllerList = new UMLRTHashMap(capsuleKey, UMLRTHashMap::compareString);
        getCapsuleToControllerListMap()->insert(capsuleKey, (void *)controllerList);
    }
    if ((controllerList->getObject(className)) != NULL)
    {
        FATAL("-C option: capsule-to-controller-map already had an entry for capsule '%s' class '%s'",
                capsuleName, (className == NULL) ? "(default)" : className);
    }
    // Assign this controller to the capsule/class combination.
    const char *classKey = className;
    if (classKey != NULL)
    {
        classKey = strdup(className);
    }
    controllerList->insert(classKey, (void *)strdup(controllerName));
}

// Create a map of capsule to controller
/*static*/ bool UMLRTCapsuleToControllerMap::parseCapsuleControllerLine ( char * line )
{
    bool ok = false;
    char * capsuleName;
    char * saveptr;
    capsuleName = strtok_r( line, " =\n\r\t", &saveptr);
    if (capsuleName != NULL)
    {
        char * controllerName = strtok_r(NULL, " =\n\r\t", &saveptr);
        if (controllerName != NULL)
        {
            char * className = strtok_r(NULL, " = \n\r\t", &saveptr);
            // If className is NULL, then this is the default controller assignment for this capsule - no capsule class specified.
            addCapsuleToControllerSpec( capsuleName, controllerName, className );
            ok = true;
        }
    }
    return ok;
}

// Create a map of capsule to controller
/*static*/ bool UMLRTCapsuleToControllerMap::readCapsuleControllerMap( const char * controllerfile )
{
    bool ok = true;
    FILE *fd;

    if ((fd = fopen(controllerfile, "r")) == NULL)
    {
        printf("ERROR: failed to open controllers file %s\n", controllerfile);
        ok = false;
    }
    else
    {
        char * line;
        char buf[1024];

        while (((line = fgets(buf, sizeof(buf), fd)) != NULL) && ok)
        {
            ok = UMLRTCapsuleToControllerMap::parseCapsuleControllerLine(buf);
        }
    }
    return ok;
}

// Assign static capsule controllers based on the run-time map.
/*static*/ void UMLRTCapsuleToControllerMap::setDefaultSlotList( UMLRTSlot * slots, size_t size )
{
    for (size_t i = 0; i < size; ++i)
    {
        UMLRTController * controller = getControllerForCapsule(slots[i].name, slots[i].capsuleClass);

        // Only reassign static capsules (i.e. controller already assigned) whose controllers were specified in the run-time map.
        if ((slots[i].controller != NULL) && (controller != NULL))
        {
            // Reassign the capsule according to the run-time map collected.
            slots[i].controller = controller;
        }
    }
    defaultSlotList = slots;
    numDefaultSlotList = size;
}


// Debug output the the capsule, controller and capsule-to-controller maps.
/*static*/ void UMLRTCapsuleToControllerMap::debugOutputControllerList()
{
    BDEBUG(BD_MODEL, "Controller list: { <controller name>, <instance address> }\n");

    UMLRTHashMap::Iterator iter = getControllerNameMap()->getIterator();

    if (iter == iter.end())
    {
        BDEBUG(BD_MODEL, "    No controllers.\n");
    }
    else
    {
        while (iter != iter.end())
        {
            BDEBUG(BD_MODEL, "    { %s, %p }\n", ((UMLRTController *)((char *)iter.getObject()))->getName(), (UMLRTController *)((char *)iter.getObject()));
            iter = iter.next();
        }
    }
}

/*static*/ void UMLRTCapsuleToControllerMap::debugOutputCapsuleList()
{
    BDEBUG(BD_MODEL, "Capsule list: { <capsule name>, <instance address>, <capsule class>, <assigned controller> }\n");

    UMLRTHashMap::Iterator iter = getCapsuleNameMap()->getIterator();

    if (iter == iter.end())
    {
        BDEBUG(BD_MODEL, "    No capsules.\n");
    }
    else
    {
        while (iter != iter.end())
        {
            UMLRTCapsule * capsule = (UMLRTCapsule *)iter.getObject();
            BDEBUG(BD_MODEL, "    { %s, %p, %s, %s }\n",
                    capsule->getName(), capsule, capsule->getClass()->name, capsule->getSlot()->controller->getName());
            iter = iter.next();
        }
    }
}

// Debug output of capsule-to-controller map.
/*static*/ void UMLRTCapsuleToControllerMap::debugOutputCaspuleToControllerMap()
{
    BDEBUG(BD_MODEL, "Capsule to controller map: { <slot>, <controller>, <capsule class> }\n");

    UMLRTHashMap::Iterator ctclIter = getCapsuleToControllerListMap()->getIterator();

    if (ctclIter == ctclIter.end())
    {
        BDEBUG(BD_MODEL, "    No capsule to controller assignments.\n");
    }
    else
    {
        while (ctclIter != ctclIter.end())
        {
            const char * capsuleName = (const char *)ctclIter.getKey();
            UMLRTHashMap * controllerList = (UMLRTHashMap *)ctclIter.getObject();
            if (controllerList)
            {
                UMLRTHashMap::Iterator clIter = controllerList->getIterator();
                while (clIter != clIter.end())
                {
                    BDEBUG(BD_MODEL, "    { %s, %s, %s }\n",
                                capsuleName,
                                (clIter.getObject() == NULL) ? "?no controller?" : clIter.getObject(),
                                (clIter.getKey() == NULL) ? "(default)" : clIter.getKey());
                    clIter = clIter.next();
                }
            }
            ctclIter = ctclIter.next();
        }
    }
}


