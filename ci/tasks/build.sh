#!/bin/bash

set -e -u -x

cd sources/

./gradle build

cp build/libs/ohmypwd.jar build/
cp Dockerfile build/