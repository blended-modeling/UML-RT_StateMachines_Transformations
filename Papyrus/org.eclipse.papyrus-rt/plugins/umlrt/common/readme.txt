folder description:
--------------------

Common plugins, shared by all other sets of plugins.

To build Common locally, you should use specific maven profiles:

There are 2 maven profiles that could build the "common" component:

1) common-gerrit
Which was created to be executed by gerrit jobs in Hudson.
This is based on the last successfull build p2 of Papyrus RT. 

2) core
The core profile includes the build of all the core bundles. 
Including core, common, profile, cpp.


To build with maven with a specific profile, run the command :

"mvn clean verify -Pcommon-gerrit"

From the root of the project: org.eclipse.papyrus-rt