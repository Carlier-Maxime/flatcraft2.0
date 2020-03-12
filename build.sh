#!/bin/bash
set -e
export LC_ALL=en_US.UTF-8
export LANG=en_US.UTF-8
export LANGUAGE=en_US.UTF-8

find src -name "*.java" -print >javafiles
if [ ! -d bin ]; then
    mkdir bin
fi
javac -d bin -cp lib/dpprocessor.jar -source 8 -target 8 @javafiles
cp -R src/textures bin
cp src/*.txt bin
jar -cfm flatcraft.jar manifest.mf -C bin/ .
