// umlrtcapsuleid.c

/*******************************************************************************
* Copyright (c) 2014-2015 Zeligsoft (2009) Limited  and others.
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v10.html
*******************************************************************************/

#include "umlrtcapsule.hh"
#include "umlrtcapsuleid.hh"
#include "basefatal.hh"
#include <stdlib.h>
#include <new>

// Must have verified #isValid before calling this.
UMLRTCapsule * UMLRTCapsuleId::getCapsule() const
{
    if (!capsule)
    {
        FATAL("no capsule defined");
    }
    return capsule;
}

static int UMLRTObject_fprintf_UMLRTCapsuleId( FILE *ostream, const UMLRTObject_class * desc, const void * data, int nest, int arraySize )
{
    return fprintf(ostream, "{UMLRTCapsuleId %s}", ((const UMLRTCapsuleId *)data)->getCapsule()->getName());
}

static void * UMLRTObject_initialize_UMLRTCapsuleId( const UMLRTObject_class * desc, void * data )
{
    (void)new(data) UMLRTCapsuleId;

    return (void *)(((uint8_t *)data) + desc->size);
}

void * UMLRTObject_copy_UMLRTCapsuleId( const UMLRTObject_class * desc, const void * src, void * dst )
{
    (void)new(dst) UMLRTCapsuleId(*(UMLRTCapsuleId *)src);

    return (void *)(((uint8_t *)dst) + desc->size);
}

static void * UMLRTObject_destroy_UMLRTCapsuleId(  const UMLRTObject_class * desc, void * data )
{
    ((UMLRTCapsuleId *)data)->~UMLRTCapsuleId();

    return (void *)(((uint8_t *)data) + desc->size);
}

static const UMLRTObject_class UMLRTType_UMLRTCapsuleId_
= {
        "UMRTCapsuleId",
        UMLRTObject_initialize_UMLRTCapsuleId,
        UMLRTObject_copy_UMLRTCapsuleId,
        UMLRTObject_decode,
        UMLRTObject_encode,
        UMLRTObject_destroy_UMLRTCapsuleId,
        UMLRTObject_getSize,
        UMLRTObject_fprintf_UMLRTCapsuleId,
        NULL, // super
        UMLRTOBJECTCLASS_DEFAULT_VERSION, // version
        UMLRTOBJECTCLASS_DEFAULT_BACKWARDS, // backwards
        sizeof(UMLRTCapsuleId),
        sizeof(UMLRTCapsuleId),
        NULL, // fields
};

const UMLRTObject_class * const UMLRTType_UMLRTCapsuleId = &UMLRTType_UMLRTCapsuleId_;

