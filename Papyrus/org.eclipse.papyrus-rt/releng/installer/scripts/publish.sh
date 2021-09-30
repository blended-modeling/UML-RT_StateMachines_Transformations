#!/bin/bash
#
# Copyright (c) 2016 Christian W. Damus and others.
# All rights reserved. This program and the accompanying materials
# are made available under the terms of the Eclipse Public License v1.0
# which accompanies this distribution, and is available at
# http://www.eclipse.org/legal/epl-v10.html
#
# Contributors:
#     Christian W. Damus - initial API and implementation
#

set -e
set -u

#
# Global variables
#

REMOTE_DIR=/home/data/httpd/download.eclipse.org/papyrus-rt/installer/updates/

#
# Handle command-line options
#

function usage() {
	echo "
Usage: $0 [ -r <path> ]
where
    <path> is the path of the 'remote' directory to which to publish
"
	
	exit 2
}

args=`getopt r: $*`
if [ $? != 0 ]; then
	usage
fi
set -- $args

for i; do
	case "$i" in
		-r)
			REMOTE_DIR="$2"; shift;
			shift;;
		--)
			shift; break;;
	esac
done

#
# Functions
#

function bail() {
	local errresult=$?
	
	# Ensure that the stage is removed
	rm -rf "$STAGE"
	
	return errresult
}

#
# Main program.  Publish installer packages and update site to the remote directory.
#

case $(uname -s) in
	Linux) SEDOPTS=-r;;
	Darwin) SEDOPTS=-E;;
	*) echo "I don't know sed on your platform."; exit 1;;
esac

# The artifacts to be published are all in the build workspace
cd $WORKSPACE

# Compute remote directory
case "${BUILD_TYPE:=nightly}" in
	milestone)
		REMOTE_DIR="$REMOTE_DIR/milestones"
		;;
	release)
		# Remote directory is the release directory
		;;
	*)
		REMOTE_DIR="$REMOTE_DIR/${BUILD_TYPE}"
		;;
esac

# Create the staging directory
STAGE=$REMOTE_DIR.stage.$RANDOM
if [ -e "$STAGE" ]; then
	echo "Cannot create staging area for publish."
	exit 1
fi

REPOSITORY=$STAGE/repository
mkdir -p "$REPOSITORY"

# Put the repository
rsync -a updates/ "$REPOSITORY" || bail

# And the packages
for f in Papyrus-RT-Installer-*.{zip,tar.gz,exe}; do
	destname=$(echo "$f" | sed $SEDOPTS -e 's/[-0-9.]{2,}/-/')
	cp -a "$f" "$STAGE/$destname" || bail
done

# Cut over
if [[ -d "$REMOTE_DIR" || ! ( -e "$REMOTE_DIR" )  ]]; then
	rm -rf "$REMOTE_DIR/repository" || bail
	mkdir -p "$REMOTE_DIR" || bail
	
	# Point of no return
	
	mv -f "$REPOSITORY" "$REMOTE_DIR"
	mv -f "$STAGE"/Papyrus-RT-Installer-*.{zip,tar.gz,exe} "$REMOTE_DIR"
	
	rm -rf "$STAGE"
else
	echo "Remote exists but is not a directory."
	rm -rf "$STAGE"
	exit 1
fi
