#!/bin/bash

set -e

cd ohmypwd/

sudo TERM=dumb ./gradlew build

sudo cp ./build/libs/ohmypwd.jar ../build/
sudo cp ./ci/tasks/Dockerfile ../build/