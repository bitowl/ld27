#!/bin/bash
rm -r ../ld27-android/assets/maps/*
CP="/home/bitowl/libgdx/extensions/gdx-tools/gdx-tools.jar:/home/bitowl/libgdx/gdx.jar:/home/bitowl/libgdx/extensions/gdx-tiled-preprocessor/gdx-tiled-preprocessor.jar:/home/bitowl/libgdx/gdx-backend-lwjgl.jar:/home/bitowl/libgdx/gdx-backend-lwjgl-natives.jar:/home/bitowl/libgdx/gdx-natives.jar"
java -cp $CP com.badlogic.gdx.tiledmappacker.TiledMapPacker maps ../ld27-android/assets/maps --strip-unused 
