
#include "TimingUtil.hh"

#include "umlrtobjectclass.hh"
#include "umlrtobjectclassgeneric.hh"
#include <cstddef>

const long TimingUtil::DEFAULT_DELAY;
char * TimingUtil::timeStr = "null";
const UMLRTObject_field TimingUtil::fields[] = 
{
};
TimingUtil::TimingUtil()
{
}

TimingUtil::TimingUtil( const TimingUtil & other )
{
}

TimingUtil::~TimingUtil()
{
}



long TimingUtil::getDelayFromCmdLine( int i, char * capName, char * stateName )
{
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/SPPInheritance/SPPInheritance.uml SPPInheritance::TimingUtil operation getDelayFromCmdLine */
    int argc = UMLRTMain::getArgCount();
    long delay = DEFAULT_DELAY;
    cout << "[" << capName << "](" << stateName << ") " << argc << " user arguments provided" << endl;
    if (argc > i)
    {
    const char * arg = UMLRTMain::getArg(i);
    cout << "[" << capName << "](" << stateName << ") user-provided delay = " << arg << endl;
    delay = atol(arg);
    }
    else
    {
    cout << "[" << capName << "](" << stateName << ") no user-provided delay; using default" << endl;
    }
    cout << "[" << capName << "](" << stateName << ") delay used = " << delay << " sec" << endl;
    return delay;
    /* UMLRTGEN-USERREGION-END */
}

const char * TimingUtil::currTimeStr()
{
    /* UMLRTGEN-USERREGION-BEGIN platform:/resource/SPPInheritance/SPPInheritance.uml SPPInheritance::TimingUtil operation currTimeStr */
    UMLRTTimespec timespec;
    UMLRTTimespec::getclock(&timespec);
    long int seconds = timespec.tv_sec;
    long int days = seconds / UMLRTTimespec::SECONDS_PER_DAY;
    seconds -= (days * UMLRTTimespec::SECONDS_PER_DAY);
    long int hours = seconds / UMLRTTimespec::SECONDS_PER_HOUR;
    seconds -= (hours * UMLRTTimespec::SECONDS_PER_HOUR);
    long int minutes = seconds / UMLRTTimespec::SECONDS_PER_MINUTE;
    seconds -= (minutes * UMLRTTimespec::SECONDS_PER_MINUTE);
    long int msec = timespec.tv_nsec / UMLRTTimespec::NANOSECONDS_PER_MILLISECOND;
    stringstream ss;
    ss << days << " d";
    ss << "; " << hours << " h";
    ss << "; " << minutes << " m";
    ss << "; " << seconds << " s";
    ss << "; " << msec << " ms";
    return ss.str().c_str();
    /* UMLRTGEN-USERREGION-END */
}

TimingUtil & TimingUtil::operator=( const TimingUtil & other )
{
    if( &other == this )
        return *this;
    timeStr = other.timeStr;
    return *this;
}



const UMLRTObject_class UMLRTType_TimingUtil = 
{
    UMLRTObjectInitialize<TimingUtil>,
    UMLRTObjectCopy<TimingUtil>,
    UMLRTObject_decode,
    UMLRTObject_encode,
    UMLRTObjectDestroy<TimingUtil>,
    UMLRTObject_fprintf,
    "TimingUtil",
    NULL,
    {
        sizeof( TimingUtil ),
        0,
        TimingUtil::fields
    },
    UMLRTOBJECTCLASS_DEFAULT_VERSION,
    UMLRTOBJECTCLASS_DEFAULT_BACKWARDS
};

