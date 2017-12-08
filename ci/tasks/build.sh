#!/bin/bash

set -e -u -x

export ROOT_FOLDER=$(pwd)
export GRADLE_USER_HOME="${ROOT_FOLDER}/.gradle"

cd ohmypwd/

./gradlew build

cp ./build/libs/ohmypwd.jar ../build/
cp ./ci/tasks/Dockerfile ../build/