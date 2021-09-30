# Target Platforms Read-Me Primer

This document presents an overview of the target definitions project in
the Papyrus-RT release engineering (build) system.

## How to Generate This Documentation

First, it is important to know how to generate the HTML presentation of this
documentation if you prefer not to read the markdown source.

The `build.xml` Ant build script is configured, via a custom project builder,
to generate HTML automatically from all `*.md` files in the project (including
this one).  But, this requires an installation on the local system of the
Mylyn WikiText Stand-alone distribution, which includes the Ant tasks required.

To enable documentation generation, simply download the latest WikiText Stand-alone
distribution from the [Mylyn Project Downloads](https://www.eclipse.org/mylyn/downloads/)
and unzip it somewhere on your filesystem.  Then, in the Ant preferences of your
Eclipse workbench, define this property:

	    wikitext.standalone

with the absolute filesystem path of the unzipped Wikitext Standalone distribution
as its value.  From this point forward, any changes that you make in the markdown
sources in this project will be generated to HTML automatically.

Generated HTML files are ignored in git.  And if you do not have the Wikitext Standalone
tools and therefore do not define the required Ant property in your preferences, then
the HTML files simply won't be generated.  This set-up is not required for the project
to build correctly. 

## Target Platform Files Organization

A variety of target platform files are specified in this project for the Hudson
build, with variability in a few different dimensions:

* the development stream of Eclipse, Papyrus, and other dependencies on which to build
* the kind (stability level) of Papyrus build to use
* whether the target platform is optimized for local filesystem access to dependencies on the Eclipse download server. [More on that specifically, below](#eclipsetps)

The basic directory structure has the Papyrus build kind at the top level with Maven
modules for each of the target platforms tailored to any given component or gerrit build
(some targets are used by several builds, others are specific to a Papyrus-RT component).

So, at the top level we have

* `papyrusnightly` — for building on the latest Papyrus nightly build from the current stream
* `papyrusmilestone` — for building on the latest Papyrus milestone (stable) build from the current stream
* `papyrusrelease` — for building on the latest Papyrus official release build from the current stream

And at the next level, for example using Papyrus nightly builds

* `papyrusnightly`
    * `org.eclipse.papyrusrt.targetplatform.core` — intended for the Core component build
    * similarly for other components (Codegen, Tooling)
    * `org.eclipse.papyrusrt.targetplatform.papyrus` — not used on its own as a component, but providing common Papyrus content in the `*.tpd` which is then included in other target platforms
    * similarly for other common content not from the Papyrus project
    * `org.eclipse.papyrusrt.targetplatform.rt.included` — use for the Gerrit builds, which build an individual component on top of the latest successful Papyrus-RT nightly build
    * `org.eclipse.papyrusrt.targetplatform.releng` — used for the Papyrus-RT product and packaging builds

According to Maven properties described in the top POM and configured in each Hudson build,
the POM in this project selects out of all of these target-platform modules the one as its
child module that it should build and make available to all of the other maven modules.

### Target Platform Generation

All of these target platforms (`*.target` files) are generated from abstract target
platform definitions (`*.tpd` files) using the tooling provided by Obeo at
[Github](http://mbarbero.github.io/fr.obeo.releng.targetplatform/).  Furthermore, the
Papyrus Developer Tools feature provides a context-menu action **Generate all Target files**
that finds all `*.tpd` files in the current project and generates the corresponding
`*.target` files.

## <a name="eclipsetps"></a>Eclipse-specific Target Platform Files

Within the directory structure of alternative target-platform definitions for the
Tycho build within this project, there is an entire parallel module structure
weaved into it that provides for target-platforms optimized for use on the HIPP
instance on the Eclipse.org Hudson build server.

These alternative target platforms are located in `eclipse/` sub-directories within
each primary portable target-platform module.  In the HIPP environment on the
Eclipse build server, a profile in the top POM for the target-platform project
selects this `eclipse` variant.

So, for example, the Core component target platform build on the latest Papyrus nightly
build has this structure:

* `papyrusnightly` — the latest Papyrus nightly
    * `org.eclipse.papyrusrt.targetplatform.core` — the Core component's target platform
        * `eclipse` — the variant for Eclipse build-server local downloads access
    * the "portable" target platform definition and generated target file with HTTP access

### Maintenance

The 'eclipse' variants of the target-platform files are maintained automatically
by the project build configuration.  If your workspace is configured for automatic
build, then the 'eclipse' variants will be regenerated automatically for any
changes in the generated portable (HTTP-based) *.target files.  Otherwise, just
do a manual build of the project.  Clean build will delete all contents of the
'eclipse' variant directories.
