folder description:
--------------------

plugins for code generation from model, targeting the various runtimes

Codegen plugins, shared by all other sets of plugins. They are dedicated for headless use, with no user interface launched.


To build codegen locally, you should use specific maven profiles:

There are 2 maven profiles that could build the "codegen" component:

1) codegen-gerrit
It is about the codegen subcomponent ( to not confuse with codegen as main component including subcomponents)
Which was created to be executed by gerrit jobs in Hudson.
This is based on the last successfull build p2 of Papyrus RT. 

2) core
The codegen profile includes the build of all the codegen bundles. 
Including codegen, runtime, xtext.


To build with maven with a specific profile, run the command :

"mvn clean verify -Pcodegen-gerrit"

From the root of the project: org.eclipse.papyrus-rt