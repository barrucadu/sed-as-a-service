#!/usr/bin/env bash

BUILD_DIR=`mktemp -d`
trap "rm -rf $BUILD_DIR" EXIT

for file in *.java; do
  javac -d "$BUILD_DIR" $file
done

jar -cvf UnixUtilityAsAService.jar -C "$BUILD_DIR" .
