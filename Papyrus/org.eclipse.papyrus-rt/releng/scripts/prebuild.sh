#--------------------------------------------------------------------------------
# Copyright (c) 2012, 2017 CEA LIST, Christian W. Damus, and others.
#    
# All rights reserved. This program and the accompanying materials
# are made available under the terms of the Eclipse Public License v1.0
# which accompanies this distribution, and is available at
# http://www.eclipse.org/legal/epl-v10.html
#
# Contributors:
#	 Celine Janssens (All4Tec) - Initial API and implementation
#    Christian W. Damus - bug 518265
#--------------------------------------------------------------------------------

#!/bin/sh
set -eux

PRIVATE_MAVEN="$WORKSPACE/.maven"
PRIVATE_REPO="$PRIVATE_MAVEN/repo"
LAST_CLEANED_RECORD="$PRIVATE_MAVEN/.lastCleaned"
TODAY=`date "+%Y-%m-%d"`

#
# Function determining whether the private Maven repository should be cleaned.
#
need_clean_repo() {
	if [[ ! -f "$LAST_CLEANED_RECORD" ]]; then
		return 0; # Initial clean to be recorded
	fi
	
	# The first word of the record is the build ID that was cleaned
	# The rest of the record is the date that it was cleaned
	read lastCleanedBuild lastCleanedDate < "$LAST_CLEANED_RECORD"
	
	# Clean if the last cleaned build was ten builds ago
	if (( "$BUILD_NUMBER" - "$lastCleanedBuild" >= 10 )); then
		# But only if that was not today
		if [[ "$lastCleanedDate" == "$TODAY" ]]; then
			return 1
		fi
		
		return 0
	fi
	
	# No need to clean
	return 1
}

#
# Function to clean the private maven repository.
#
clean_repo() {
	rm -rf "$PRIVATE_REPO"
	mkdir -p "$PRIVATE_REPO"
	echo "$BUILD_NUMBER $TODAY" > "$LAST_CLEANED_RECORD"
}

# Clean the Maven repository if it's time
if need_clean_repo; then
	clean_repo
fi

if [[ -z "$LOCAL_REPO_DIR" ]]; then
	LOCAL_REPO_DIR = "$WORKSPACE/source"
fi

cd $LOCAL_REPO_DIR
git clean -fdx

