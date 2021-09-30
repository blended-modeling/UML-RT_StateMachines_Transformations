#--------------------------------------------------------------------------------
# Copyright (c) 2012, 2017 CEA LIST, Christian W. Damus, and others.
#    
# All rights reserved. This program and the accompanying materials
# are made available under the terms of the Eclipse Public License v1.0
# which accompanies this distribution, and is available at
# http://www.eclipse.org/legal/epl-v10.html
#
# Contributors:
#    Celine Janssens (All4Tec)
#    Christian W. Damus - fix directory permissions in publish
#--------------------------------------------------------------------------------

#!/bin/sh

echo "* Starting of postbuild-publish.sh"

# Source path Directory 
p2UpdateSiteDir=${WORKSPACE}/repository

# Target Update site download Area
if [ -n "${TARGET_URL}" ]; then
	repoArea=$TARGET_URL
else
	repoArea=/home/data/httpd/download.eclipse.org/papyrus-rt/updates
fi


# BUILD_TYPE can be "nightly", "releases" , "integration" or "milestones"
if [ -n "${BUILD_TYPE}" ]; then
	updateSite=$BUILD_TYPE
else
	updateSite="nightly"
fi

#Eclipse Release can be neon or oxygen
if [ -n "${ECLIPSE_RELEASE}" ]; then
	release=${ECLIPSE_RELEASE}
else
	release="neon"
fi


# Publish Phase
case ${BUILD_TYPE} in
   "nightly") typeLetter="N";;
   "milestones") typeLetter="M";;
   "integration") typeLetter="I";;
   "releases") typeLetter="R";;
   *) echo "Sorry, The BUILD_TYPE variable is not correct !";;
esac

COMPACT_BUILD_ID="${BUILD_ID//[-_]}"
COMPACT_BUILD_ID="${COMPACT_BUILD_ID:0:12}"
stampDir=${typeLetter}${COMPACT_BUILD_ID}

if [ -n "$BUILD_ALIAS" ]; then
	stampDir=$BUILD_ALIAS 
fi

if [ $updateSite = "releases" ] || [ $updateSite = "milestones" ]; then
	# Set the Version only for release and Milestones
	if [ -n "${REPO_VERSION}" ]; then
		version=${REPO_VERSION}
	else
		version="0.9.0"
	fi
	
	updateSiteDeploymentDir=$repoArea/$updateSite/$release/$version

else 
	updateSiteDeploymentDir=$repoArea/$updateSite/$release
	
fi

if [ $updateSite != "nightly" ]; then
# For release, Milestones and Integration create a Stamp Directory (/I201605120600/)
	echo "* Create Directory before publishing "
	finalTargetDir=$updateSiteDeploymentDir/$stampDir
	mkdir -p -m g=rwxs "${finalTargetDir}"
	echo "* Publishing"
	#Copy artifact repository Content <Jobs Workspace>/repository into Update site area 'download.eclipse.org/papyrus-rt/updates/nightly/neon'. 
	cp -r "${p2UpdateSiteDir}"/. "${finalTargetDir}"
fi

echo "* END OF PUBLISH * "
