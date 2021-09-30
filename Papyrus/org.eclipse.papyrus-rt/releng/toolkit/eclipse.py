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
This script provides an API to load and manipulate
Eclipse plugins and features in a code repository
"""

import os  # File handling
import os.path  # File path handling
import re  # Regular expressions
import xml.dom.minidom  # Minimal XML


class Bundle:
    """
    Represents an Eclipse bundle
    """

    def __init__(self, location):
        """
        Initializes this bundle
        :param location: The bundle's location
        :return: The bundle
        """
        # The name as the last element of the path (folder name)
        self.name = os.path.basename(location)
        # The folder
        self.location = location


class Plugin(Bundle):
    """
    Represents an Eclipse plugin
    """

    def __init__(self, location):
        """
        Initializes this plugin
        :param location: The plugin's location
        :return: The plugin
        """
        Bundle.__init__(self, location)
        # Initializes the list of dependencies
        self.dependencies = []
        # Initializes the manifest data
        self.properties = {}
        # Load the data from manifest
        manifest = open(os.path.join(os.path.join(location, "META-INF"), "MANIFEST.MF"), "r")
        on_dependency = False
        for line in manifest:
            if line.startswith("Require-Bundle:") or on_dependency:
                m = re.search("[a-zA-Z_][a-zA-Z_0-9]+(\\.[a-zA-Z_][a-zA-Z_0-9]+)+", line)
                dep = m.group(0)
                self.dependencies.append(dep)
                on_dependency = line.endswith(",")
            elif line.startswith("Bundle-"):
                m = re.match("Bundle-(\\w+): (.*)", line)
                self.properties[m.group(1)] = m.group(2)
        manifest.close()


class Feature(Bundle):
    """
    Represents an Eclipse feature
    """

    def __init__(self, location):
        """
        Represents an Eclipse feature
        :param location:  The feature's location
        :return: The feature
        """
        Bundle.__init__(self, location)
        # Initializes the list of the included features
        self.included = []
        # Initializes the list of the plugins
        self.plugins = []
        # Load the content
        doc = xml.dom.minidom.parse(os.path.join(location, "feature.xml"))
        for node in doc.getElementsByTagName("plugin"):
            identifier = node.getAttribute("id")
            self.plugins.append(identifier)
        for node in doc.getElementsByTagName("includes"):
            identifier = node.getAttribute("id")
            self.included.append(identifier)


class Repository:
    """
    Represents a repository of Eclipse plugins and features
    """

    def __init__(self):
        """
        Initializes this repository
        :return: The repository
        """
        # Initializes a dictionary of plugins indexed by name
        self.plugins = {}
        # Initializes a dictionary of features indexed by name
        self.features = {}

    def load(self, directory):
        """
        Recursively load plugins and features in the given directory
        :param directory: The directory to load from
        :return: None
        """
        subs = os.listdir(directory)
        if "META-INF" in subs:
            # this is a plugin
            plugin = Plugin(directory)
            self.plugins[plugin.name] = plugin
            return
        if "feature.xml" in subs:
            # this is a feature
            feature = Feature(directory)
            self.features[feature.name] = feature
            return
        for name in subs:
            sub = os.path.join(directory, name)
            if os.path.isdir(sub):
                self.load(sub)

    def check(self, include_pattern, exclude_pattern):
        """
        Checks the consistency of the repository to check whether
        all required features and plugins are present
        The given pattern is used to determine whether a feature or
        plugin is required ; matching means required
        This method returns the list of the missing features and plugins
        :param include_pattern: A pattern matching the bundles to include
        :param exclude_pattern: A pattern matching the bundles to exclude
        :return: The missing bundles
        """
        result = []
        for name in self.features:
            if match(name, exclude_pattern):
                continue
            feature = self.features[name]
            for included in feature.included:
                if match(included, exclude_pattern):
                    continue
                if not included in self.features and match(included, include_pattern):
                    result.append(included)
            for plugin in feature.plugins:
                if match(plugin, exclude_pattern):
                    continue
                if not plugin in self.plugins and match(plugin, include_pattern):
                    result.append(plugin)
        for name in self.plugins:
            if match(name, exclude_pattern):
                continue
            plugin = self.plugins[name]
            for dep in plugin.dependencies:
                if match(dep, exclude_pattern):
                    continue
                if not dep in self.plugins and match(dep, include_pattern):
                    result.append(dep)
        return result


def match(value, pattern):
    """
    Determines whether the specified value matches the given pattern
    :param value: A value
    :param pattern: A pattern
    :return: True if the value matches the pattern
    """
    m = re.match(pattern, value)
    return m is not None