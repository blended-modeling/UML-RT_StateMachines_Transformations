
#ifndef TIMINGUTIL_HH
#define TIMINGUTIL_HH

#include "umlrtobjectclass.hh"

#include "umlrttimespec.hh"
#include "umlrtmain.hh"
#include <cstdlib>
#include <iostream>
#include <sstream>
#include <string>
using namespace std;

class TimingUtil
{
public:
    TimingUtil();
    TimingUtil( const TimingUtil & other );
    ~TimingUtil();
    static const long DEFAULT_DELAY = 1;
    static char * timeStr;
    static long getDelayFromCmdLine( int i, char * capName, char * stateName );
    static const char * currTimeStr();
    TimingUtil & operator=( const TimingUtil & other );
    static const UMLRTObject_field fields[];
};
extern const UMLRTObject_class UMLRTType_TimingUtil;

#endif

