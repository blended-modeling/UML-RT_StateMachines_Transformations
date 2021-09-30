# ###############################################################################
# Copyright (c) 2013 CEA LIST.
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
This script cleans up a repository of Eclipse features and plugins
in order to improve their Tycho compatibility
"""

import os  # File handling
import os.path  # File path handling
import shutil  # File handling
import sys  # System
import subprocess  # Process handling
import xml.dom.minidom  # Minimal XML

import console  # Console pretty printing
import eclipse  # Eclipse API
import xmlutils  # XML utilities


def print_usage():
    """
    Print how to use this script
    :return: None
    """
    print("Usage:")
    print("  python cleaner.py [-h | --help] [--color] <targets>")
    print("  <targets> is the list of files and directories to clean")
    print("Options:")
    print("    -h, --help: print this screen")
    print("    --color: activate console color")


def cleanup_bundle(bundle):
    """
    Cleanup the specified Eclipse bundle
    :param bundle: An Eclipse bundle
    :return: None
    """
    __cleanup_classpath(bundle)
    __cleanup_build_properties(bundle)


def __cleanup_classpath(bundle):
    """
    Cleanup the classpath of the specified Eclipse bundle
    :param bundle: An Eclipse bundle
    :return: None
    """
    subs = os.listdir(bundle.location)
    if not ".classpath" in subs:
        return
    doc = xml.dom.minidom.parse(os.path.join(bundle.location, ".classpath"))
    dirty = False
    for entry in doc.getElementsByTagName("classpathentry"):
        data = entry.getAttribute("excluding")
        folder = entry.getAttribute("path")
        if data is not None and len(data) > 0:
            files = data.split("|")
            for file in files:
                if file is not None and len(file) > 0:
                    dirty = True
                    full = os.path.join(bundle.location, os.path.join(folder, os.path.join(file)))
                    print("Found " + full)
                    if full.endswith("/"):
                        subprocess.call(["git", "rm", "-r", full])
                    else:
                        subprocess.call(["git", "rm", full])
            entry.parentNode.removeChild(entry)
    if dirty:
        xmlutils.output(doc, os.path.join(bundle.location, ".classpath"))
        console.log("Bundle " + bundle.name + " => Fixed .classpath to remove excluded sources")


def __cleanup_build_properties(bundle):
    """
    Cleanup the build properties of the specified Eclipse bunle
    :param bundle: An Eclipse bundle
    :return: None
    """
    subs = os.listdir(bundle.location)
    if not "build.properties" in subs:
        return
    properties = open(os.path.join(bundle.location, "build.properties"), "r")
    found = False
    for line in properties:
        if line.find("src.includes") != -1:
            found = True
            break
    properties.close()
    if not found:
        if not "about.html" in subs:
            shutil.copy("about.html", os.path.join(bundle.location, "about.html"))
        properties = open(os.path.join(bundle.location, "build.properties"), "a")
        properties.write("src.includes = about.html\n")
        properties.close()
        console.log("Bundle " + bundle.name + " => Fixed build.properties to add src.includes")


def cleanup(directories):
    """
    Cleanup the Eclipse bundles in the specified directories
    :param directories: A collection of directories
    :return: None
    """
    # Build the repo
    repo = eclipse.Repository()
    for directory in directories:
        repo.load(directory)
    # Do the cleanup
    for name in repo.plugins:
        cleanup_bundle(repo.plugins[name])
    for name in repo.features:
        cleanup_bundle(repo.features[name])


# Main script
if __name__ == "__main__":
    # Checks the arguments
    nb = len(sys.argv)
    if nb <= 1:
        print_usage()
        sys.exit(1)

    targets = []
    # Parse the arguments
    for arg in sys.argv[1:]:
        if arg == "-h" or arg == "--help":
            print_usage()
            sys.exit(0)
        elif arg == console.CLI_COLOR:
            console.USE_COLOR = True
        else:
            targets.append(arg)
    # Execute
    cleanup(targets)
    sys.exit(0)