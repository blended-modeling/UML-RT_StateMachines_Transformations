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
API for console logging with log levels and colors
"""


# Log levels
LEVELS = {
    "DEBUG": 0,
    "INFO": 0,
    "WARNING": 93,
    "ERROR": 91
}

# Color codes for the console colors
COLORS = {
    "ENDC": 0,  # RESET COLOR
    "GREY70": 97,
    "RED": 91,
    "YELLOW": 93,
    "BLUE": 94,
    "PURPLE": 95,
    "GREEN": 92,
}

# Activate/Deactivate the use of console colors
USE_COLOR = False
# Command line option to activate colors
CLI_COLOR = "--color"


def log(message, level="INFO"):
    """
    Logs the given message at the given level
    :param message: The message to log
    :param level: The log level
    :return: None
    """
    print(__get_formatted(message, level))


def __get_formatted(message, level):
    """
    Gets the formatted string for the given message and level
    :param message: The message to format
    :param level: The log level
    :return: The formatted message string
    """
    if USE_COLOR and LEVELS[level] > 0:
        return __termcode(LEVELS[level]) + "[" + level + "] " + message + __termcode(0)
    else:
        return "[" + level + "] " + message


def __termcode(num):
    """
    Gets the string for the given console color
    :param num: A console color
    :return: The terminal string for the specified color
    """
    return "\033[%sm" % num