folder description:
--------------------

Core plugins, shared by all other sets of plugins. They are dedicated for headless use, with no user interface launched.


To build Core locally, you should use specific maven profiles:

There are 2 maven profiles that could build the "common" component:

1) core-gerrit
It is about the core subcomponent ( to not confuse with core as main component including subcomponents)
Which was created to be executed by gerrit jobs in Hudson.
This is based on the last successfull build p2 of Papyrus RT. 

2) core
The core profile includes the build of all the core bundles. 
Including core, common, profile, cpp.


To build with maven with a specific profile, run the command :

"mvn clean verify -Pcore-gerrit"

From the root of the project: org.eclipse.papyrus-rt