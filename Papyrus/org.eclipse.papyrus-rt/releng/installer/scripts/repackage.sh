#!/bin/sh
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

HOMEBASE=`pwd`
PRODUCTS=$HOMEBASE/target/products
WORKDIR=$HOMEBASE/target/papyrus-rt-installer
EXTRACTDIR=$HOMEBASE/target/extractor
OUTPUT=${WORKSPACE:=$HOMEBASE/target}
BUILD_VERSION=SNAPSHOT
ECLIPSE_SIGN=${ECLIPSE_SIGN:-false}

#
# Handle command-line options
#

function usage() {
	echo "
Usage: $0 [ -b <build> ] [ -e <installer> ]
where
    <build> is the build version number
    <installer> is the full path of the installer executable to extract
"
	
	exit 2
}

args=`getopt b:e: $*`
if [ $? != 0 ]; then
	usage
fi
set -- $args

for i; do
	case "$i" in
		-b)
			BUILD_VERSION="$2"; shift;
			shift;;
		-e)
			EXTRACT_INSTALLER="$2"; shift;
			shift;;
		--)
			shift; break;;
	esac
done

#
# Functions
#
      
function sign() {
	local name platform signee
	
	platform=$1
	signee=$2
	name=$3
	
	if [[ "$ECLIPSE_SIGN" == true ]]; then
		if [ -n "$platform" ]; then
			echo "Signing $name"
			
			curl -o "${signee}.signed" \
				-F "file=@$signee" \
				http://build.eclipse.org:31338/${platform}sign.php
			
			mv -f "${signee}.signed" "$signee"
		fi
	fi
}

function chkoutput() {
	local filename expectedsize
	
	filename=$1
	expectedsize=${2:-20000} # Binaries that we sign are all bigger than this
	
	size=$(wc -c <"$filename")
	
	if [[ "$size" -lt "$expectedsize" ]]; then
		echo "Something is wrong:  output file is too small."
		echo "  $size bytes: $filename"
		return 1
	fi
}

#
# Main program.  Repackage each archive as appropriate for the platform, with signing.
#

if [[ "$BUILD_VERSION" == SNAPSHOT && -f $HOMEBASE/target/buildver.txt ]]; then
	BUILD_VERSION=`cat $HOMEBASE/target/buildver.txt`
fi

cd $PRODUCTS
for archive in *.zip; do
	if [[ "$archive" == *x86_64* ]]; then
		bitness=64
	else
		bitness=32
	fi
	
	rm -rf $WORKDIR
	mkdir $WORKDIR
	cd $WORKDIR

	echo "Re-packaging $archive"
	
	archive="$PRODUCTS/$archive"

	# Explode the archive, here
	unzip -qq "$archive"

	# Find the platform-specific INI file
	ini=papyrus-rt-inst.ini
	if [[ "$archive" == *macosx* ]]; then
		ini="Papyrus-RT Installer.app/Contents/Eclipse/$ini"
	fi
		
	# Add the update URL appropriate to the build kind
	case "${BUILD_TYPE:=nightly}" in
		milestone)
			repo=milestones/repository
			;;
		release)
			repo=repository
			;;
		*)
			repo=${BUILD_TYPE}/repository
			;;
	esac
	echo "-Doomph.installer.update.url=http://download.eclipse.org/papyrus-rt/installer/updates/${repo}/" >> "$ini"
	
	if [[ "$archive" == *macosx* ]]; then
		# Rewrite the bundle name in the Info.plist
		INFO_PLIST="Papyrus-RT Installer.app/Contents/Info.plist"
		sed -e 's/Papyrus-rt-inst/Papyrus-RT Installer/g' "$INFO_PLIST" > .infoplist.tmp
		mv -f .infoplist.tmp "$INFO_PLIST"
		
		if [[ "$ECLIPSE_SIGN" == true ]]; then
			# Zip it up for signing
			zip -r -q signing-mac.zip "Papyrus-RT Installer.app"
			
			# Sign it
			if sign mac signing-mac.zip "Mac Installer"; then
				# Need to extract it again
				rm -rf "Papyrus-RT Installer.app"
				unzip -qq signing-mac.zip
			fi
			
			# Clean up
			rm -f signing-mac.zip
		fi
		
		chmod a+x "Papyrus-RT Installer.app/Contents/MacOS/papyrus-rt-inst"
		macoutput="$OUTPUT/Papyrus-RT-Installer-${BUILD_VERSION}-mac${bitness}.zip"
		zip -r -q "$macoutput" "Papyrus-RT Installer.app"
		chkoutput "$macoutput" || exit $?
	elif [[ "$archive" == *win32* ]]; then
		# We don't need the console executable
		rm -f eclipsec.exe
		
		sign win papyrus-rt-inst.exe "Windows launcher binary"
		
		echo "Making Windows self-extractor"
		
		zip -r -9 -qq --symlinks "$OUTPUT/product.zip" *

		marker=$EXTRACTDIR/marker.txt
		extractor=$EXTRACTDIR/extractor.exe
		javalib=$EXTRACTDIR/org.eclipse.oomph.extractor.lib.jar
		descriptor=$HOMEBASE/product-descriptor-${bitness}.txt
		extractee=${EXTRACT_INSTALLER:=/home/data/httpd/download.eclipse.org/oomph/products/latest/eclipse-inst-win${bitness}.exe}
		mkdir "$EXTRACTDIR"
		
		java -classpath $HOMEBASE/target/repository/plugins/org.eclipse.oomph.extractor.lib_*.jar \
			org.eclipse.oomph.extractor.lib.BINExtractor \
			"$extractee" \
			"$EXTRACTDIR/product.zip" \
			-export \
				"$marker" \
				"$extractor" \
				"$javalib" \
				"$EXTRACTDIR/product-descriptor"
     
		# Assemble the self-extracting archive
		winoutput="$OUTPUT/Papyrus-RT-Installer-${BUILD_VERSION}-win${bitness}.exe"
		if [[ -f /opt/public/tools/oomph/extractor-${bitness}.exe ]]; then
			# Prefer this version, as used by the Eclipse Installer build
			extractor=/opt/public/tools/oomph/extractor-${bitness}.exe
		fi
  		cat "$extractor" \
  			"$marker" \
  			"$javalib" \
  			"$marker" \
  			"$descriptor" \
  			"$marker" \
  			"$OUTPUT/product.zip" \
  			"$marker" > "$winoutput"
		
		# Sign the self-extractor
		sign win "$winoutput" "Windows Installer"
		chkoutput "$winoutput" || exit $?
	
		# Clean up
		rm -rf "$EXTRACTDIR"
		rm -f "$OUTPUT/product.zip"
	elif [[ "$archive" == *linux* ]]; then
		# Just tar it up
		linoutput="$OUTPUT/Papyrus-RT-Installer-${BUILD_VERSION}-linux${bitness}.tar.gz"
		tar -czf "$linoutput" papyrus-rt-installer
		chkoutput "$linoutput" || exit $?
	fi
	
	cd $PRODUCTS
done

cd $HOMEBASE

rm -rf $WORKDIR
