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
#	 Celine Janssens (ALL4TEC)
#--------------------------------------------------------------------------------
#!/bin/sh

p2UpdateSiteDir=${LOCAL_REPO_DIR}/releng/org.eclipse.papyrusrt.${COMPONENT}.p2/target/repository
downloadsArea=/home/data/httpd/download.eclipse.org/papyrus-rt
buildsUpdateSitesRoot=$downloadsArea/builds/${COMPONENT}

if [ -z "${ECLIPSE_DOWNLOADS}"]; then
	ECLIPSE_DOWNLOADS="http://download.eclipse.org"
fi

echo "* Post-build"

# Copy update site to 'repository' folder in the builds download area
cp -r $p2UpdateSiteDir $buildsUpdateSitesRoot

# Run the auto-test if a test model is provided
if [ -z "$TEST_MODEL_DIR" ]; then
	TEST_MODEL_DIR=samples
fi

full_test_models_dir=${LOCAL_REPO_DIR}/models/$TEST_MODEL_DIR

if [ -n "$TEST_MODEL" ]; then
	${LOCAL_REPO_DIR}/releng/scripts/codegen/runtest.sh $BUILD_ID $TEST_MODEL $full_test_models_dir Gerrit TopMain "-l ALL" $ECLIPSE_DOWNLOADS
	echo "* Test result for BUILD ID " $BUILD_ID
	if [ $? -ne 0 ]; then
		echo "Test failed"
		exit 1
	else
		echo "Test succeeded"
	fi
else
	echo "* No test performed"
fi

# Archive and put update site repository in the appropriate update site folder
. ${LOCAL_REPO_DIR}/releng/scripts/archive-sub-repo.sh

