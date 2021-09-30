#!/bin/sh

BUILD_ID=$1
BUILDTYPE=$4
TOPNAME=$5
EXTRAOPTS=$6
ECLIPSE_DOWNLOADS=$7

if [ -z "$BUILD_ID" ]; then
	echo "Usage $0 <build-id> <modelname> <modeldirname> $BUILDTYPE [<top>]"
	exit 1
fi

if [ -z ${build_dir+x} ]; then
	build_dir=/opt/public/download-staging.priv/papyrus-rt/
fi

logs_dir={$build_dir}/logs

if [ -z "$TOPNAME" ]; then
	$TOPNAME="TopMain"	
fi

downloadsArea=/home/data/httpd/download.eclipse.org/papyrus-rt
script_dir=`dirname $0`
modelname=$2
test_models_dir=$3 

if [ -z "${ECLIPSE_DOWNLOADS}" ]; then
	ECLIPSE_DOWNLOADS="http://download.eclipse.org"
fi

if [ -z "${ECLIPSE_RELEASE_NAME}" ]; then
	ECLIPSE_RELEASE_NAME="neon"
fi

if [ -z "${PAPYRUS_RT_TOOLING_UPDATE_SITE}" ]; then
	PAPYRUS_RT_TOOLING_UPDATE_SITE="https://hudson.eclipse.org/papyrus-rt/job/Papyrus-RT-Product/lastSuccessfulBuild/artifact/repository"
fi

echo "* Test parameters:"
echo "  - Build dir is:           '$build_dir'"
echo "  - Logs dir is:            '$logs_dir'"
echo "  - Model to test is:       '$modelname'"
echo "  - Model dir is:           '$test_models_dir'"
echo "  - Build type is:          '$BUILDTYPE'"
echo "  - Topname is:             '$TOPNAME'"
echo "  - Eclipse stream:         '$ECLIPSE_RELEASE_NAME'"
echo "  - Eclipse downloads:      '$ECLIPSE_DOWNLOADS'"
echo "  - PapyrusRT Tooling repo: '$PAPYRUS_RT_TOOLING_UPDATE_SITE'"

base_package=$downloadsArea/$ECLIPSE_RELEASE_NAME.tar.gz
install_dir=$build_dir/autotest.$BUILD_ID.install
ws_dir=$build_dir/autotest.$BUILD_ID.ws

rm -rf $install_dir $src_dir $ws_dir

echo "* Installing $BUILD_ID to '$install_dir'"

#trap "echo removing $install_dir $src_dir $ws_dir; rm -rf $install_dir $src_dir $ws_dir" EXIT

resource=`readlink -f $base_package`
echo "* Expanding base installation for `basename ${resource%.*}`"
mkdir $install_dir
tar zxf $base_package -C $install_dir
echo "* Untar done"

echo "* Installing $BUILD_ID"

$install_dir/eclipse/eclipse \
	-application org.eclipse.equinox.p2.director \
	-noSplash \
	-repository \
        $ECLIPSE_DOWNLOADS/papyrus-rt/builds/repository,$ECLIPSE_DOWNLOADS/releases/$ECLIPSE_RELEASE_NAME,$ECLIPSE_DOWNLOADS/modeling/mdt/papyrus/updates/releases/$ECLIPSE_RELEASE_NAME,$ECLIPSE_DOWNLOADS/modeling/tmf/xtext/updates/composite/releases/,${PAPYRUS_RT_TOOLING_UPDATE_SITE},$ECLIPSE_DOWNLOADS/modeling/mdt/papyrus/components/designer/ \
	-installIUs \
	org.eclipse.uml2.sdk.feature.group,org.eclipse.papyrusrt.codegen-feature.feature.group,org.eclipse.papyrusrt.rts-feature.feature.group,org.eclipse.papyrusrt.umlrt.profile.feature.feature.group

if [ $? -ne 0 ]; then
	echo "* Unable to install $BUILD_ID"
	exit 1
else
	echo "* Installed $BUILD_ID"
fi


standalone_dir=$install_dir/eclipse/plugins/org.eclipse.papyrusrt.codegen.standalone_*

# The generation script needs a folder for the development plugins.  We don't
# want any here, so create a dummy folder.
mkdir -p /tmp/empty

mkdir -p $ws_dir

echo "* Generating code for test model"

chmod +x $standalone_dir/umlrtgen.sh
$standalone_dir/umlrtgen.sh \
	$install_dir/eclipse/plugins \
	/tmp/empty \
	java \
	$EXTRAOPTS -s -o $ws_dir/$modelname \
	$test_models_dir/$modelname/$modelname.uml


rc=$?
if [ $rc -ne 0 ]; then
	echo "* Unable to generate code for $modelname, generator completed with $rc"
	exit 1
fi

echo "* Code generated successfully"

cd $ws_dir/$modelname/src

echo "* Running 'make' on generated source"

export UMLRTS_ROOT=`echo $install_dir/eclipse/plugins/org.eclipse.papyrusrt.rts_*/umlrts`
make TARGETOS=linux BUILDTOOLS=x86-gcc-4.6.3

if [ $? -ne 0 ]; then
	echo "* Unable to build executable for $modelname"
	mail -s sredding@zeligsoft.com "$modelname $BUILDTYPE build failed" < /dev/null
	exit 1
fi

echo "* Compiled generated sources successfully"

echo "* Executing model"

time ./$TOPNAME
rc=$?
if [ $rc -ne 0 ]; then
	echo "* Unable to execute $modelname, exited with $rc"
	mail sredding@zeligsoft.com -s "$modelname $BUILDTYPE build failed" < /dev/null
	exit 1
fi

echo "* Removing temporary work dirs" 
cd $build_dir
mkdir -p logs/logs.$BUILD_ID
mv autotest.$BUILD_ID.install/eclipse/configuration/*.log logs/logs.$BUILD_ID
rm -rf autotest.$BUILD_ID.install
rm -rf autotest.$BUILD_ID.src
rm -rf autotest.$BUILD_ID.ws
echo "* Successfully built and ran $modelname"
echo "* Send sucess email"
mail sredding@zeligsoft.com -s "$modelname $BUILDTYPE build passed" < /dev/null
exit 0

