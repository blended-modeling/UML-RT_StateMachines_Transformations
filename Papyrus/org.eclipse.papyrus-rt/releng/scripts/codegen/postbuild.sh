#!/bin/sh

p2UpdateSiteDir=${LOCAL_REPO_DIR}/releng/codegen/site/target/repository
downloadsArea=/home/data/httpd/download.eclipse.org/papyrus-rt
buildsUpdateSitesRoot=$downloadsArea/builds

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
	${LOCAL_REPO_DIR}/releng/scripts/codegen/runtest.sh $BUILD_ID $TEST_MODEL $full_test_models_dir Gerrit TopMain "-l WARNING" $ECLIPSE_DOWNLOADS
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
. ${LOCAL_REPO_DIR}/releng/scripts/codegen/archive.sh

