folder description:
--------------------

Common plugins, shared by all other sets of plugins.

To build profile plugins locally, you should use specific maven profiles:

There are 2 maven profiles that could build the "profile" component:

1) profile-gerrit
It is about the core subcomponent ( to not confuse with core as main component including subcomponents)
Which was created to be executed by gerrit jobs in Hudson.
This is based on the last successfull build p2 of Papyrus RT. 

2) core
The core profile includes the build of all the core bundles. 
Including core, common, profile, cpp.


To build with maven with a specific maven profile, run the command :

"mvn clean verify -Pprofile-gerrit"

From the root of the project: org.eclipse.papyrus-rt