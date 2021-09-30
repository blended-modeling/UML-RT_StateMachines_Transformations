# How to build #

## Introduction ##
Components in this project are built using Maven and its Tycho plugins for the build of Eclipse artifacts.
To build locally, simply execute the command line in the root directory:

```
mvn clean verify
```
By default the build will be done using the latest Papyrus and Eclipse Nightly target platform. 
By default it will build tooling, profile, core, migration, cpp, common and junit tests

Specific profiles have been created to be run from Hudson Jobs:

## Hudson Profiles ##

### core , tooling , codegen ###
Those profiles build sub repos and java modules for the 3 main parts . 
For example the profile "core" builds core, common, profile, cpp and the oeprt.core.p2

To activate those profile launch the following :

```
mvn clean verify -Pcore
```

```
mvn clean verify -Ptooling
```

(not yet in place)
```
mvn clean verify -Pcodegen
```

By default the Target platform for those profile will be the nightly Papyrus, and the required Papyrus RT sub P2. 
For example: Tooling depends on core, thus the TP for the "tooling" profile will include  "oeprt.core.p2"



### releng ###
This profile will build all the releng part of the Papyrus RT project: 
- oeprt.rcp
- oeprt.feature
- oeprt.p2
- oeprt.product
- oeprt.rcptt

This is run by the last job in charge of the industrialization, and itself triggered by the component jobs:
```
mvn clean verify -Preleng
```  

### core-gerrit, profile-gerrit, ... ###
The gerrit profiles are used by the gerrit jobs (one per component)

The TP for the gerrit jobs is common for all the components: and is based on the main p2 built during the industrialization phase: oeprt.p2


Some examples:

```
mvn clean verify -Pprofile-gerrit
```  

```
mvn clean verify -Pcpp-gerrit
```

```
mvn clean verify -Pmigration-gerrit
```

```
mvn clean verify -Ptooling-gerrit
```
# Codegen #
For the time being, Codegen is not symmetric compare to the tooling part. 
To build Codegen, you should run maven from the "org.eclipse.papyrus-rt/releng/codegen/" location and run the following command:

```
mvn clean verify -Prelease
```
```
mvn clean verify -Pmilestone

```
mvn clean verify -Pnightly
```

# Override Target platform #
If you want to build the different profiles based on another Target Platform, you have to override the property <eclipse.targetrelease> with the appropriate value:

*neon.core => TP with Papyrus nightly only
*neon.tooling => TP with Papyrus nightly + Last core p2
*neon.rt.included => TP with Papyrus Nightly + All Papyrus RT Nightly
*neon.releng => TP with Papyrus Nightly + Last Core P2 + Last Codegen P2 + Last Tooling P2
*neon.papyrusmilestones => TP with Papyrus Milestones
*neon.papyrusreleases => TP with Papyrus Release
*neon.papyrusnightly => TP with Papyrus Nightly

To override a property the following command line option is availble:

```
mvn clean verify -Declipse.targetrelease=neon.rt.included
```

# Remarks and known issues #

## Known Issues ##
When launching the Junit tests on a windows machine you could meet the following error:

[ERROR] Failed to execute goal org.eclipse.tycho:tycho-surefire-plugin:0.25.0:test (default-test) on project org.eclipse.papyrusrt.umlrt.profile.tests: Unable to parse configuration of mojo org.eclipse.tycho:tycho-surefire-plugin:0.25.0:test for parameter excludes: Cannot assign configuration entry 'excludes' with value '**/*AllTests.java' of type java.lang.String to property of type java.util.List -> [Help 1]

This is probably a Tycho regression: https://bugs.eclipse.org/bugs/show_bug.cgi?id=496411


 