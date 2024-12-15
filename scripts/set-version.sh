#!/bin/bash
set -e

# shellcheck disable=SC1091
. ./release.env

mvn versions:set -DnewVersion="$RELEASE_VERSION"

sed -i 'src/deb/DEBIAN/control' -e 's@Version: .*@Version: '"$RELEASE_VERSION"'@g'
sed -i 'src/deb/usr/bin/semvery' -e 's@readonly VERSION=.*@readonly VERSION='"$RELEASE_VERSION"'@g'
sed -i 'src/main/java/sing/app/semvery/Version.java' -e 's@APPLICATION_VERSION = ".*"@APPLICATION_VERSION = "'"$RELEASE_VERSION"'"@g'
# - Test files
sed -i 'src/test/java/sing/app/semvery/VersionTest.java' -e 's@CURRENT_VERSION = ".*"@CURRENT_VERSION = "'"$RELEASE_VERSION"'"@g'
sed -i 'src/test/java/sing/app/semvery/__snapshots__/HelpTest.snap' -e '2s@semvery .*@semvery '"$RELEASE_VERSION"'@g'
sed -i 'src/test/java/sing/app/semvery/__snapshots__/SemveryTest.snap' \
  -e '2s@semvery .*@semvery '"$RELEASE_VERSION"'@g' \
  -e '72s@semvery .*@semvery '"$RELEASE_VERSION"'@g' \
  -e '142s@semvery .*@semvery '"$RELEASE_VERSION"'@g' \
  -e '212s@.*@'"$RELEASE_VERSION"'@g'

sed -i 'src/test/java/sing/app/semvery/__snapshots__/VersionTest.snap' \
  -e '2s@semvery .*@semvery '"$RELEASE_VERSION"'@g' \
  -e '8s@.*@'"$RELEASE_VERSION"'@g'
