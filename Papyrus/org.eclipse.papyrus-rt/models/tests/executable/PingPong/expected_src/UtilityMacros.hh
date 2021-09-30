
#ifndef UTILITYMACROS_HH
#define UTILITYMACROS_HH

// To use with std::cout
#define CAPINST "[" << name() << ":" << getTypeName() << "]"
#define FCAPINST "[" << name() << ":" << getTypeName() << "::" << getName() << "[" << getIndex() << "]]"
#define CAPINST_STATE CAPINST << "(" << getCurrentStateString() << ")"
#define FCAPINST_STATE FCAPINST << "(" << getCurrentStateString() << ")"

// To use with log.log
#define LCAPINST "[%s:%s] %s", name(), getTypeName()


#endif

