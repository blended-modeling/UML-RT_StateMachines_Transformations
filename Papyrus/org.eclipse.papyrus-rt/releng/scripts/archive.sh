#--------------------------------------------------------------------------------
# Copyright (c) 2012-2016 CEA LIST and Others.
#
#    
# All rights reserved. This program and the accompanying materials
# are made available under the terms of the Eclipse Public License v1.0
# which accompanies this distribution, and is available at
# http://www.eclipse.org/legal/epl-v10.html
#
# Contributors:
#	 Celine Janssens (All4Tec)
#--------------------------------------------------------------------------------

#!/bin/sh
# Archive Phase
# Only if required

echo "* Copy Archive ZIP"
echo "* Archive URL = ${ARCHIVE_URL}"

p2UpdateSiteDir=${WORKSPACE}/repository

if [ -n "$ARCHIVE_URL" ]; then
	downloadsArea=${ARCHIVE_URL}

	case $BUILD_TYPE in
	   "nightly") typeLetter="N";;
	   "milestones") typeLetter="M";;
	   "integration") typeLetter="I";;
	   "releases") typeLetter="R";;
	   *) echo "Sorry, The BUILD_TYPE variable is not correct !";;
	esac
	
	echo "* Build ID = $BUILD_ID"
	
	COMPACT_BUILD_ID="${BUILD_ID//[-_]}"
	COMPACT_BUILD_ID="${COMPACT_BUILD_ID:0:12}"
	FULL_BUILD_ID=${typeLetter}${COMPACT_BUILD_ID}
	
	
	
	# set zip file name
	if [ -n "${BUILD_ALIAS}" ]; then
		updateZipFile="Papyrus-RT-Update-${BUILD_ALIAS}.zip"
	else
		updateZipFile="Papyrus-RT-Update-${FULL_BUILD_ID}.zip"
	fi
	
	
	#Archive directory path (ie: download.eclipse.org/papyrus-rt/downloads/drops/0.8.0/N201605210600)
	archiveDir=$downloadsArea/${REPO_VERSION}
	
	mkdir -p $archiveDir/$FULL_BUILD_ID
	finalArchiveDir=$archiveDir/$FULL_BUILD_ID
	
	
	# duplicate the archive with the full name
	cp org.eclipse.papyrusrt.p2*.zip $updateZipFile
	
	# Move it to the Remote Location
	mv $updateZipFile $finalArchiveDir

fi
