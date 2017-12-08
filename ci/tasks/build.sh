#!/bin/bash

set -e -u -x

cd sources/

./gradlew build

cp build/libs/ohmypwd.jar build/
cp Dockerfile build/