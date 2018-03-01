#!/bin/bash

set -e

cd ohmypwd/

sudo ./gradlew build

cp ./build/libs/ohmypwd.jar ../build/
cp ./ci/tasks/Dockerfile ../build/