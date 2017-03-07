#!/bin/bash
find src -name *.java -print >javafiles
if [ ! -d bin ]; then
    mkdir bin
fi
javac -d bin @javafiles
cp -R src/textures bin
jar -cvfm flatcraft.jar manifest.mf -C bin/ .
