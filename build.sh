#!/bin/bash
set -e

find src -name "*.java" -print >javafiles
if [ ! -d bin ]; then
    mkdir bin
fi
javac -d bin @javafiles
cp -R src/textures bin
cp src/*.txt bin
jar -cfm flatcraft.jar manifest.mf -C bin/ .
