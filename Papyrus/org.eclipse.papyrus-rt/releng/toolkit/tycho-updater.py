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
This script provides an API to update the URI
of P2 update sites in Tycho pom.xml using the
Eclipse SIMREL repository
"""

import os.path  # File path handling
import re  # Regular expressions
import shutil  # Shell utilities
import subprocess  # OS Process management
import sys  # System
import xml.dom.minidom  # Minimal XML

import console  # Console pretty printing
import xmlutils  # XML utilities


# URI of the git SIMREL repository
SIMREL_GIT = "http://git.eclipse.org/gitroot/simrel/org.eclipse.simrel.build.git"

# Path of the local SIMREL repository
SIMREL_PATH = "simrel"


def print_usage():
    """
    Print how to use this script
    :return: None
    """
    print("Usage:")
    print("  python tycho-updater.py [-h | --help] [--color] [--simrel <local>] <targets>")
    print("  <targets> is the list of file to update")
    print("Options:")
    print("    -h, --help: print this screen")
    print("    --color: activate console color")
    print("    --simrel <local>: use the SIMREL repo at the given <local> path")


def __get_url_for(simrel, identifier):
    """
    Get the update site URL for the given identifier
    An identifier is a simple name with an optional '[index]' suffix
    where index is the integer index of the update site to use
    :param simrel: Path to the local simrel repository
    :param identifier: Identifier of the dependency
    :return: The URL for the dependency
    """
    m = re.match("(?P<id>(\\w|-)+)(\\[(?P<index>\\d+)\\])?", identifier)
    if m is None:
        return None
    file = simrel + "/" + m.group("id") + ".b3aggrcon"
    index = m.group("index")
    if index is None:
        index = 0
    else:
        index = int(index)
    if os.path.isfile(file):
        content = xml.dom.minidom.parse(simrel + "/" + m.group("id") + ".b3aggrcon")
        location = content.getElementsByTagName("repositories")[index].getAttribute("location")
        return location
    return None


def update(simrel, target):
    """
    Update the given Tycho pom.xml file with new update sites
    :param simrel: Path to the local simrel repository
    :param target: Path to the target pom.xml to update
    :return: None
    """
    console.log("Reading " + target)
    pom = xml.dom.minidom.parse(target)

    console.log("Updating " + target)
    for node in pom.getElementsByTagName("repository"):
        identifier = node.getElementsByTagName("id")[0].childNodes[0].data
        url = node.getElementsByTagName("url")[0].childNodes[0].data
        data = __get_url_for(simrel, identifier)
        if data is None:
            console.log(identifier + " => no matching repository found", "WARNING")
        else:
            if data == url:
                console.log(identifier + " => no change")
            else:
                node.getElementsByTagName("url")[0].childNodes[0].data = data
                console.log(identifier + " => updated to " + data)

    console.log("Writing back " + target)
    xmlutils.output(pom, target)


def execute(arguments):
    """
    Executes the update process
    :return: None
    """
    # Checks the arguments
    nb = len(arguments)
    if nb <= 1:
        print_usage()
        sys.exit(1)

    # Initializes the local data
    local = False
    simrel = SIMREL_PATH
    targets = []

    # Parse the arguments
    expect_local = False
    for arg in arguments[1:]:
        if arg == "-h" or arg == "--help":
            print_usage()
            sys.exit(0)
        elif arg == console.CLI_COLOR:
            console.USE_COLOR = True
        elif arg == "--simrel":
            expect_local = True
        elif expect_local:
            local = True
            simrel = arg
            expect_local = False
        else:
            targets.append(arg)

    # Checks the data
    if expect_local:
        console.log("Expected path the local SIMREL repo", "ERROR")
        print_usage()
        sys.exit(1)
    if len(targets) == 0:
        console.log("No target given", "ERROR")
        print_usage()
        sys.exit(1)

    # Clone the SIMREL repo if needed
    if local:
        console.log("Using local simrel at " + simrel)
    else:
        console.log("Cloning the simrel repository from Eclipse")
        subprocess.call(["git", "clone", SIMREL_GIT, simrel])
    # Do the updates
    for target in targets:
        update(simrel, target)
    # Cleanup if required
    if not local:
        console.log("Cleaning up ...")
        shutil.rmtree(simrel)


if __name__ == "__main__":
    execute(sys.argv)