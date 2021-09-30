#!/bin/sh

##
## Known variables:
## BUILD_ID=2014-10-01_05-16-17
##
## For stable builds:
## BUILD_ALIAS=M4
##
## LOCAL_REPO, usually ${WORKSPACE}/source
## COMPONENT is either 'tooling' or 'codegen'
## UPDATE_SITE is either 'releases', 'nightly', or 'repo'


p2UpdateSiteDir=${LOCAL_REPO_DIR}/releng/codegen/site/target/repository
downloadsArea=/home/data/httpd/download.eclipse.org/papyrus-rt
buildsUpdateSitesRoot=$downloadsArea/builds
archivesRoot=$downloadsArea/archives

echo "* Archiving and publishing"

if [ -n "${UPDATE_SITE}" ]; then
	updateSite=$UPDATE_SITE
else
	updateSite="repo"
fi

updateSiteDeploymentDir=$buildsUpdateSitesRoot/$updateSite
archiveDir=$archivesRoot/$updateSite

if [ -n "$BUILD_ALIAS" ]; then
	buildType=S
else
	buildType=N
fi

COMPACT_BUILD_ID="${BUILD_ID//[-_]}"
COMPACT_BUILD_ID="${COMPACT_BUILD_ID:0:12}"
FULL_BUILD_ID=${buildType}${COMPACT_BUILD_ID}

if [ -n "$COMPONENT" ]; then
	componentName="codegen"
else
	componentName="tooling"
fi

if [ -n "$BUILD_ALIAS" ]; then
	updateZipFile="Papyrus-RT-${componentName}-Update-${BUILD_ALIAS}.zip"
else
	updateZipFile="Papyrus-RT-${componentName}-Update-${FULL_BUILD_ID}.zip"
fi

rm -rf $updateSiteDeploymentDir
cp -r $p2UpdateSiteDir $updateSiteDeploymentDir

# create the update site zip
zip -r $updateZipFile $p2UpdateSiteDir
mkdir -p $archiveDir
mv $updateZipFile $archiveDir/
