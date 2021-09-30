################################################
# Makefile
################################################

ifeq ($(TARGETOS),)
TARGETOS=linux
endif

ifeq ($(BUILDTOOLS),)
BUILDTOOLS=x86-gcc-4.6.3-debug
endif

# Location of RTS root.
UMLRTS_ROOT ?= ../../../rts

# Compiler
# This can be overridden while invoking make with CC=xxx.
CC=g++

INCLUDES+=\
	$(UMLRTS_ROOT)/include

CPPFLAGS+= $(foreach d, $(DEFINES), -D$d) $(foreach i, $(INCLUDES), -I$i)

# This can be overridden while invoking make with DEBUG=1
DEBUG=0

# Add debugging compile flags
ifeq ($(DEBUG),1)
CXXFLAGS+= -g -O0
endif

CONFIG=$(TARGETOS).$(BUILDTOOLS)

# Destination directory for the RTS services library.
LIBDEST=$(UMLRTS_ROOT)/lib/$(CONFIG)

SRCS = $(wildcard *.cc)
OBJS = $(SRCS:.cc=.o)

MAIN = main

LDPATH+=-L$(LIBDEST)

LIBS+=-lrts -lpthread -lrt

all:	$(MAIN)

$(MAIN): $(OBJS) $(LIBDEST)/librts.a
	$(CC) $(CXXFLAGS) $(CPPFLAGS) -o $(MAIN) $(OBJS) $(LDPATH) $(LIBS)

clean :
	$(RM) *.o *~ $(MAIN)

info:
	@echo SRCS = $(SRCS)
	@echo OBJS = $(OBJS)
	@echo CMD = $(CC) $(CXXFLAGS) $(CPPFLAGS) -o $(MAIN) $(OBJS) $(LDPATH) $(LIBS)
