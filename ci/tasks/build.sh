#!/bin/bash

set -e -u -x

cd ohmypwd/

./gradlew build

cp ./build/libs/ohmypwd.jar ../build/
cp ./ci/tasks/Dockerfile ../build/