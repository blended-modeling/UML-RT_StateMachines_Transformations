# GetArguments sample model

The GetArguments sample model shows how to read arguments passed to the UML-RT application through the command line on application startup.

The GetArguments sample model uses a refined version of the approach shown in the ClassUtility sample model to store the information from the command line. Note that the limitations expresses in the documentation for the ClassUtility sample model also apply in this case.

## GetArguments Status

**In Progress**

## GetArguments TBD

- Full testing
- Documentation (above what is shown above)

## GetArguments History

### 2015.12.23
- Resolved the compilation errors from yesterday:
	- _TopControllers.cc:47:1: error: cannot convert 'bool' to 'const char*' in initialization_
 	- _TopControllers.cc:101:1: error: cannot convert 'bool' to 'const char*' in initialization_
- Completed global access to debugging flags 
- Updated Read-Me.md with TBD (To Be Done) section

### 2015.12.22
-  Pushed to Git repository
- compilation errors present at end of day:
	- TopControllers.cc:47:1: error: cannot convert 'bool' to 'const char*' in initialization
	- TopControllers.cc:101:1: error: cannot convert 'bool' to 'const char*' in initialization

### 2015.12.21
- Still need to find a way to have a global, static repository for debug status.
	- Resolved by including the "ClassUtility" sample model functionality to implement the storage and query of the debug status.

