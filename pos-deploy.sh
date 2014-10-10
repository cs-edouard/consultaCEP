#!/bin/bash
# ----------------------------------------------------------------------------
# Uncomment the next line to enable debug
# set -x

export MR_KEY=2557d07a1476ecf5081cce15d592fcddcd4823f6
export MR_USER=admin
export MR_APPLICATION_ID=15
export MR_PLATFORM_ID=3
export MR_APPLICATION_SLUG=consultacep
export MR_PLATFORM_SLUG=android

# Uncomment the next two lines and the last line of this file to enable log
LOG="/tmp/after-build.log"
{

## CODE BEGIN  #############################################################

# Get the version number from buildfile
VERSION="$(cat ${WORKSPACE}/ConsultaCEP/app/build.gradle | 
egrep -o "versionName '.*'" | 
egrep -o \'.*\' | tr -d \')"


# If the ENV variable has been set to dev, then we append dev in version name:
echo "VERSION: $VERSION"


# Get the release note from git
RELEASE_NOTE="Release $(date)"


# Save the JSON as an Environment Variable (Set up App and Platform in Jenkins)
read -r -d '' JSON <<ENDVAR
{
  "application":    "/api/v1/application/$MR_APPLICATION_ID/",
  "platform":       "/api/v1/platform/$MR_PLATFORM_ID/",
  "version":        "$VERSION",
  "release_notes":  "$RELEASE_NOTE"
}
ENDVAR
echo "JSON: $JSON"


# Set the Mobrelease URL (Must export in Jenkins)
MR_API_RELEASE="http://mobrelease.concretecorp.com.br/api/v1/release/?format=json&username=$MR_USER&api_key=$MR_KEY"
MR_API_RELEASE_FILE="http://mobrelease.concretecorp.com.br/api/v1/releasefile/RELEASE_ID/?format=json&username=$MR_USER&api_key=$MR_KEY"
echo "MR_API_RELEASE: $MR_API_RELEASE"
echo "MR_API_RELEASE_FILE: $MR_API_RELEASE_FILE"


# Send the JSON via HTTP POST to server and get the release ID
RESP_JSON="$(curl -v -s -H "Content-Type: application/json" -X POST --data "$JSON" "$MR_API_RELEASE")"
RELEASE_ID="$(echo "$RESP_JSON" | egrep -o '\"id\":\ "[0-9a-zA-Z\-\.]*\"' | cut -d ':' -f 2 | tr -d '" ')"
echo "RELEASE_ID: $RELEASE_ID"


# Set the right Release ID in the Release file
MR_API_RELEASE_FILE=$(echo "$MR_API_RELEASE_FILE" | sed -e "s/RELEASE_ID/$RELEASE_ID/g")
echo "MR_API_RELEASE_FILE: $MR_API_RELEASE_FILE"


# Upload the APK file via HTTP POST
echo "Uploading APK..."
APKFILE="$WORKSPACE/ConsultaCEP/app/build/outputs/apk/app-debug.apk" 
curl -v -X POST -F file="@$APKFILE" "$MR_API_RELEASE_FILE"

## CODE END  ###############################################################


# Uncomment to enable log
} &> "$LOG"
echo "==========================================" >> "$LOG"
set >> "$LOG"


