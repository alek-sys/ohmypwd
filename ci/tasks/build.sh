#!/usr/bin/env bash

set -e

cd sources/

./gradle build

cp build/libs/ohmypwd.jar build/
cp Dockerfile build/