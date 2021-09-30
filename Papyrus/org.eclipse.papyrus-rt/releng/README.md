# How to build #

Components in this project are built using Maven and its Tycho plugins for the build of Eclipse artifacts.
To build locally, simply execute the command line in the releng directory:

```
mvn clean install
```
By default the build will be done using the latest Papyrus and Eclipse release target platform

# Other Options #

## Nightly ##

Use the following command to run the build on the nightly target platform : 

```
mvn clean install -Pneon-papyrusnightly
```

## Milestone ##
Use the following command to run the build on the Latest Milestone target platform:

```
mvn clean install -Pneon-papyrusmilestone
```

## CodeGen ##
To run codegen go into releng/codegen and launch

For the release:
```
mvn clean install -PusePapyrusReleases
```

For the Nightlies:
```
mvn clean install  -PusePapyrusNightlies
```

For the Milestones:
```
mvn clean install -PusePapyrusMilestones
```
 