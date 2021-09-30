folder description:
--------------------

Migration to RSA plugins.

To build Migration locally, you should use specific maven profiles:

There are 2 maven profiles that could build the "migration" component:

1) migration-gerrit
It is about the migration subcomponent 
Which was created to be executed by gerrit jobs in Hudson.
This is based on the last successfull build p2 of Papyrus RT. 

2) tooling
The tooling profile includes the build of all the tooling bundles. 
Including tooling, migration.


To build with maven with a specific profile, run the command :

"mvn clean verify -Pmigration-gerrit"

From the root of the project: org.eclipse.papyrus-rt