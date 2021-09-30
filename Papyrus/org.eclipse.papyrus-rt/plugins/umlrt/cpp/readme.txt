folder description:
--------------------

Cpp plugins, to handle specifics for C++ management in UML-RT models

To build CPP locally, you should use specific maven profiles:

There are 2 maven profiles that could build the "cpp" component:

1) cpp-gerrit
It is about the core subcomponent ( to not confuse with core as main component including subcomponents)
Which was created to be executed by gerrit jobs in Hudson.
This is based on the last successfull build p2 of Papyrus RT. 

2) core
The core profile includes the build of all the core bundles. 
Including core, common, profile, cpp.


To build with maven with a specific profile, run the command :

"mvn clean verify -Pcpp-gerrit"

From the root of the project: org.eclipse.papyrus-rt