# ###############################################################################
# Copyright (c) 2014 CEA LIST.
#
# All rights reserved. This program and the accompanying materials
# are made available under the terms of the Eclipse Public License v1.0
# which accompanies this distribution, and is available at
# http://www.eclipse.org/legal/epl-v10.html
#
# Contributors:
# Laurent Wouters laurent.wouters@cea.fr - Initial API and implementation
#
# ###############################################################################

"""
This script provides an API to generate the Tycho
configuration files (pom.xml) for an Eclipse code repository
"""

import os  # File handling
import os.path  # File path handling
import sys  # System
import xml.dom.minidom  # Minimal XML

import console  # Console pretty printing
import eclipse  # Eclipse API
import xmlutils  # XML utilities


class Target:
    """
    Represents a build target
    """
    def __init__(self, name, pom, site, feature):
        """
        Initializes this build target
        :param name: The target's name
        :param pom: Path to the target top pom.xml
        :param site: Path to the target's site
        :param feature: Identifier of the target's top Eclipse feature
        :return: The build target
        """
        self.name = name
        self.pom = pom
        self.site = site
        self.feature = feature


# General constants
MAVEN_MODEL_VERSION = "4.0.0"

# Product constants
PRODUCT_VERSION = "1.0.0"
PRODUCT_CATEGORY_ID = "org.eclipse.papyrus.category"
PRODUCT_CATEGORY_LABEL = "Papyrus Category"
PRODUCT_CATEGORY_DESC = PRODUCT_CATEGORY_LABEL
PRODUCT_GROUP = "org.eclipse.papyrus"

# Generator targets configuration
TARGETS = [Target("main",
                  "releng/top-pom-main.xml",
                  "releng/main",
                  "org.eclipse.papyrus.sdk.feature"),
           Target("extras",
                  "releng/top-pom-extras.xml",
                  "releng/extras",
                  "org.eclipse.papyrus.extra.feature"),
           Target("dev",
                  "releng/top-pom-dev.xml",
                  "releng/dev",
                  "org.eclipse.papyrus.dev.feature")]

# Generator inputs configuration
INPUTS = [
    "plugins",
    "extraplugins",
    "features/papyrus-main-features",
    "features/papyrus-extra-features",
    "features/papyrus-dev-features"
]

# Pattern to recognize required plugin to include in the build
PATTERN_INCLUDE = "org\\.eclipse\\.papyrus\\..*"
# Pattern to recognize required plugin to exclude from the build
PATTERN_EXCLUDE = "(.*\\.source.feature)|(.*\\.tests)"


def print_usage():
    """
    Print how to use this script
    :return: None
    """
    print("Usage:")
    print("  python tycho-generator.py [-h | --help] [--color]")
    print("Options:")
    print("    -h, --help: print this screen")
    print("    --color: activate console color")


def generate(inputs, targets, include_pattern, exclude_pattern):
    """
    Generate the Tycho data and files
    :param inputs: Array of input directories to load Eclipse plugins and features from
    :param targets: Array of build targets
    :param include_pattern: Pattern matching Eclipse bundle to include into a build target
    :param exclude_pattern: Pattern matching Eclipse bundle to exclude from any build target
    :return: The error code, or 0 if all went well
    """
    # Build repo
    console.log("== Preparing the repository ...")
    repo = __build_repository(inputs, include_pattern, exclude_pattern)
    if repo is None:
        return 1
    # Setup the bundles' target data
    for target in targets:
        __add_target(repo, target.feature, target)
    # Generate all bundles POM
    console.log("== Generating POM for features ...")
    for name in iter(sorted(repo.features)):
        if not __generate_bundle_pom(repo.features[name], "eclipse-feature"):
            return 2
    console.log("== Generating POM for plugins ...")
    for name in iter(sorted(repo.plugins)):
        if not __generate_bundle_pom(repo.plugins[name], "eclipse-plugin"):
            return 2
    # Update the targets' top pom.xml
    console.log("== Updating the module references in top POMs ...")
    for target in targets:
        __update_modules(repo, target)
    return 0


def __build_repository(inputs, include_pattern, exclude_pattern):
    """
    Gets an initialized repository of features and plugins
    :param inputs: Array of input directories to load Eclipse plugins and features from
    :param include_pattern: Pattern matching Eclipse bundle to include into a build target
    :param exclude_pattern: Pattern matching Eclipse bundle to exclude from any build target
    :return: The corresponding repository of Eclipse plugins and features
    """
    # Build the repo
    repository = eclipse.Repository()
    for directory_input in inputs:
        repository.load(directory_input)
    # Check for missing bundles
    missing = repository.check(include_pattern, exclude_pattern)
    for m in missing:
        console.log("Missing bundle " + m, "ERROR")
    if len(missing) > 0:
        return None
    # Initializes the targets
    for name in repository.plugins:
        repository.plugins[name].targets = []
    for name in repository.features:
        repository.features[name].targets = []
    return repository


def __add_target(repository, feature_identifier, target):
    """
    Recursively add a build target to a feature, its included features and its plugins
    :param repository: The Eclipse repository to work on
    :param feature_identifier: The identifier of the Eclipse feature to add a build target for
    :param target: The build target to add
    :return: None
    """
    # If the feature is missing
    if not feature_identifier in repository.features:
        return
    feature = repository.features[feature_identifier]
    # Add the target is required
    if not target in feature.targets:
        feature.targets.append(target)
    # Traverse all sub-features
    for included in feature.included:
        __add_target(repository, included, target)
    # Traverse all plugins
    for name in feature.plugins:
        if name in repository.plugins:
            plugin = repository.plugins[name]
            if not target in plugin.targets:
                plugin.targets.append(target)


def __generate_bundle_pom(bundle, packaging):
    """
    Generate the pom.xml file for the given bundle and given packaging
    :param bundle: The bundle to generate the pom for
    :param packaging: The type of packaging (feature or plugin)
    :return: True if the operation succeeded, False otherwise
    """
    if len(bundle.targets) == 0:
        console.log("Bundle " + bundle.name + " has no target => skipped", "WARNING")
        return True
    if len(bundle.targets) >= 2:
        console.log("Bundle " + bundle.name + " has more than one target:", "ERROR")
        for target in bundle.targets:
            console.log("\t" + target, "ERROR")
        return False
    if os.path.isfile(os.path.join(bundle.location, "pom.xml")):
        console.log("Bundle " + bundle.name + " already has pom.xml => skipped")
        return True
    relative = os.path.relpath(".", bundle.location)
    relative = os.path.join(relative, bundle.targets[0].pom)
    impl = xml.dom.minidom.getDOMImplementation()
    doc = impl.createDocument(None, "project", None)
    project = doc.documentElement
    __xml_append_text(doc, project, "modelVersion", MAVEN_MODEL_VERSION)
    parent = doc.createElement("parent")
    project.appendChild(parent)
    __xml_append_tycho_ref(doc, parent, PRODUCT_GROUP)
    __xml_append_text(doc, parent, "relativePath", relative)
    __xml_append_tycho_ref(doc, project, bundle.name)
    __xml_append_text(doc, project, "packaging", packaging)
    xmlutils.output(doc, os.path.join(bundle.location, "pom.xml"))
    console.log("Bundle " + bundle.name + " POM generated for target " + bundle.targets[0].name)
    return True


def __xml_append_text(doc, parent, tag, content):
    """
    Append an element node with the given tag and content
    :param doc: The parent document
    :param parent: The parent XML element node
    :param tag: The element tag to create
    :param content: The content of the element to create
    :return: None
    """
    child = doc.createElement(tag)
    parent.appendChild(child)
    child.appendChild(doc.createTextNode(content))


def __xml_append_tycho_ref(doc, parent, tycho_identifier):
    """
    Append a reference to a Tycho module
    :param doc: The parent document
    :param parent: The parent XML element node
    :param tycho_identifier: The Tycho module identifier
    :return:
    """
    __xml_append_text(doc, parent, "artifactId", tycho_identifier)
    __xml_append_text(doc, parent, "groupId", PRODUCT_GROUP)
    __xml_append_text(doc, parent, "version", PRODUCT_VERSION + "-SNAPSHOT")


def __update_modules(repository, target):
    """
    Updates the modules for the given target
    :param repository: The Eclipse repository to work on
    :param target: The build target to update
    :return: None
    """
    doc = xml.dom.minidom.parse(target.pom)
    modules = doc.getElementsByTagName("modules")[0]
    for module in modules.getElementsByTagName("module"):
        modules.removeChild(module)
    for name in iter(sorted(repository.features)):
        feature = repository.features[name]
        if target in feature.targets:
            modules.appendChild(__get_module_node(feature, doc))
    for name in iter(sorted(repository.plugins)):
        plugin = repository.plugins[name]
        if target in plugin.targets:
            modules.appendChild(__get_module_node(plugin, doc))
    repo_node = doc.createElement("module")
    repo_node.appendChild(doc.createTextNode(target.name))
    modules.appendChild(repo_node)
    xmlutils.output(doc, target.pom)
    console.log("Updated top POM for target " + target.name)


def __get_module_node(bundle, doc):
    """
    Get the path to the specified bundle relatively to the given target's top POM
    :param bundle: An Eclipse bundle
    :param doc: The parent XML document
    :return: The XML node containing the relative path
    """
    child = doc.createElement("module")
    child.appendChild(doc.createTextNode(os.path.join("..", bundle.location)))
    return child


# Main script
if __name__ == "__main__":
    # Checks the arguments
    for arg in sys.argv[1:]:
        if arg == "-h" or arg == "--help":
            print_usage()
            sys.exit(0)
        elif arg == console.CLI_COLOR:
            console.USE_COLOR = True
    # Execute the generation
    code = generate(INPUTS, TARGETS, PATTERN_INCLUDE, PATTERN_EXCLUDE)
    sys.exit(code)