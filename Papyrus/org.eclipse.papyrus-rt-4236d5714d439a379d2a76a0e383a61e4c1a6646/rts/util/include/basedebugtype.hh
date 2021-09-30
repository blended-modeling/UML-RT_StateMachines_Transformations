// basedebugtype.hh - Platform independent Base Debug (BD) bdtype definitions.

/*******************************************************************************
* Copyright (c) 2014 Zeligsoft (2009) Limited  and others.
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v10.html
*******************************************************************************/

#ifndef BASEDEBUGTYPE_HH
#define BASEDEBUGTYPE_HH

// BD - base debug message types.
// These will be moved elsewhere in a future installment.

// --- USERS --- new BD_xxx macros can be added here.
// BD_xxx (BD type) values can go up to 63.

// BD type 0 is reserved for future use.

// WARNING - changing BD_xxx values requires changing BD_PREFIX_STRING below.
#define BD_BIND          1
#define BD_BINDDEBUG     2
#define BD_BINDFAIL      3
#define BD_COMMAND       4
#define BD_CONNECT       5
#define BD_CONTROLLER    6
#define BD_DESTROY       7
#define BD_ERROR         8
#define BD_IMPORT        9
#define BD_INJECT        10
#define BD_INSTANTIATE   11
#define BD_LOCK          12
// WARNING - changing BD_xxx values requires changing BD_PREFIX_STRING below.
#define BD_MODEL         13
#define BD_MSG           14
#define BD_MSGALLOC      15
#define BD_SAP           16
#define BD_SEND          17
#define BD_SERIALIZE     18
#define BD_SIGNAL        19
#define BD_SIGNALDATA    20
#define BD_SIGNALREF     21
#define BD_SIGNALINIT    22
#define BD_SIGNALALLOC   23
#define BD_SWERR         24
#define BD_TIMER         25
#define BD_TIMERALLOC    26
#define BD_MAXPLUS1      27
// WARNING - changing BD_xxx values requires changing BD_PREFIX_STRING below.

// WARNING - these have to align to BD_xxx values above.
#define BD_PREFIX_STRING { \
            "?", \
            "BIND", \
            "BINDDEBUG", \
            "BINDFAIL", \
            "COMMAND", \
            "CONNECT", \
            "CONTROLLER", \
            "DESTROY", \
            "ERROR", \
            "IMPORT", \
            "INJECT", \
            "INSTANTIATE", \
 /* WARNING - these have to align to BD_xxx values above.*/ \
            "LOCK", \
            "MODEL", \
            "MSG", \
            "MSGALLOC", \
            "SAP", \
            "SEND", \
            "SERIALIZE", \
            "SIGNAL", \
            "SIGNALDATA", \
            "SIGNALREF", \
            "SIGNALINIT", \
            "SIGNALALLOC", \
            "SWERR", \
            "TIMER", \
            "TIMERALLOC", \
}
// WARNING - these have to align to BD_xxx values above.

#endif // BASEDEBUGTYPE_HH
