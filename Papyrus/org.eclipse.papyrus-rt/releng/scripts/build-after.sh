#--------------------------------------------------------------------------------
# Copyright (c) 2012, 2017 CEA LIST, Christian W. Damus, and Others.
#    
# All rights reserved. This program and the accompanying materials
# are made available under the terms of the Eclipse Public License v1.0
# which accompanies this distribution, and is available at
# http://www.eclipse.org/legal/epl-v10.html
#
# Contributors:
#    Nicolas Bros (Mia-Software)
#    Camille Letavernier (CEA LIST)
#    Celine Janssens (ALL4TEC)
#    Christian W. Damus - avoid redundant artifacts in build workspace
#--------------------------------------------------------------------------------

########## Moving ##########
##
## Known variables:
## BUILD_ID=2014-10-01_05-16-17
##
## For stable builds:
## REPO_VERSION=M4
##

# The Target of the update Site : <jobs>/workspace/repository
updateSite=${WORKSPACE}/repository

# The source URL of the repo to be moved
if [ ${COMPONENT} == "all" ]; then
	
	p2Target=${WORKSPACE}/source/releng/org.eclipse.papyrusrt.p2/target
	p2UpdateSiteDir=$p2Target/repository
	
else
	p2Target=${WORKSPACE}/source/releng/org.eclipse.papyrusrt.${COMPONENT}.p2/target
	p2UpdateSiteDir=$p2Target/repository
	
fi


### Test For CodeGen

if [ ${COMPONENT} = "codegen" ] || [ ${COMPONENT} = "all" ]; then

	# Run the auto-test if a test model is provided
	if [ -z "$TEST_MODEL_DIR" ]; then
		TEST_MODEL_DIR=samples
	fi
	
	
	full_test_models_dir=${LOCAL_REPO_DIR}/models/$TEST_MODEL_DIR
	
	if [ -n "$TEST_MODEL" ]; then
		${LOCAL_REPO_DIR}/releng/scripts/codegen/runtest.sh $BUILD_ID $TEST_MODEL $full_test_models_dir Gerrit TopMain "-l ALL" $ECLIPSE_DOWNLOADS
		echo "* Test result for BUILD ID : ${BUILD_ID}"
		if [ $? -ne 0 ]; then
			echo "Test failed"
			exit 1
		else
			echo "Test succeeded"
		fi
	else
		echo "* No test performed"
	fi
fi

### Ends of Codegen Tests

# Move the Build p2 from the job into the new UpdateSite URL
rm -rf "$updateSite"
cp -r "$p2UpdateSiteDir" "$updateSite"
rm -rf "$p2UpdateSiteDir"

# Move p2 zip into the WS root
rm -f "${WORKSPACE}"/*.zip
cp "${p2Target}"/org.eclipse.papyrusrt*.p2*.zip "${WORKSPACE}"
rm -rf "${p2Target}"/org.eclipse.papyrusrt*.p2*.zip
