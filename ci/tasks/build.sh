#!/bin/bash

set -e

cd ohmypwd/

sudo TERM=dumb ./gradlew build

cp ./build/libs/ohmypwd.jar ../build/
cp ./ci/tasks/Dockerfile ../build/