#--------------------------------------------------------------------------------
# Copyright (c) 2012-2014 CEA LIST.
#
#    
# All rights reserved. This program and the accompanying materials
# are made available under the terms of the Eclipse Public License v1.0
# which accompanies this distribution, and is available at
# http://www.eclipse.org/legal/epl-v10.html
#
# Contributors:
#    Nicolas Bros (Mia-Software)
#	 Camille Letavernier (CEA LIST)
#--------------------------------------------------------------------------------

########## publishing ##########

##
## Known variables:
## BUILD_ID=2014-10-01_05-16-17
##
## For stable builds:
## BUILD_ALIAS=M4
##

p2UpdateSiteDir=${WORKSPACE}/releng/dev/site/target/repository
updateSite=${WORKSPACE}/repository

updateZipName=Papyrus-Developer-Update.zip
zipName=Papyrus-Developer.zip

rm -rf tmp
mkdir -p "tmp/dev"

rm -rf $updateSite
mv $p2UpdateSiteDir $updateSite

# create the update site zip
(cd $updateSite && zip -r $updateZipName *)
mv $updateSite/$updateZipName "tmp/dev"

(cd tmp && zip -r $zipName *)
mv tmp/$zipName .
