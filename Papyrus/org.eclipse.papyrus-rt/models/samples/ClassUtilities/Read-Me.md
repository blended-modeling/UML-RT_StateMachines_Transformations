# Class Utility

This sample shows how a (staticc) class utility can be used to store global data, debug capability flags in this case.
A similar approach could be used to create various utilities common to a system (e.g. specialised math, pseudo-random number generator, etc.

WARNNG: This implementation is not thread-safe! If thread-safety is required, modifying the operations to be protected or using SAP/SPP pairs could be alternate solutions.

## Class Utility Status

**In Progress**

## Class Utility TBD

- Fix compilation error
	- TopControllers.cc:45:1: error: cannot convert 'bool' to 'const char*' in initialization
- Full testing
- Documentation (above what is shown above)

## Class Utility History

### 2015.12.23

- Updated Read-Me.md with TBD (To Be Done) section

### 2015.12.22

- Moved to git repository.
- Still TBD: thread safety.

### 2015.12.21

- Works to access public properties
- Replaced direct access to properties with accessor/setter class methods.
