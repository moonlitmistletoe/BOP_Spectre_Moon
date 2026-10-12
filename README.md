# BOP_Spectre_Moon

Client-side NeoForge mod for Minecraft 1.21.1 and Biomes O' Plenty 21.1.0.14.

## What it does
- Bundles the supplied Spectre resource-pack assets directly into the mod's `assets/` resources, so no separate resource-pack ZIP is required.
- Recolours Biomes O' Plenty's hanging, falling, and landing blood particles to ectoplasm cyan (`#55F2E6`) using a client-only Mixin.
- Declares a dependency on Biomes O' Plenty 21.1.0.14.

## Install
Place `BOP_Spectre_Moon.jar` in the client's `mods` folder alongside NeoForge 1.21.1 and Biomes O' Plenty 21.1.0.14. Do not install the resource pack ZIP separately; its assets are already inside the mod.

This package has been checked for ZIP/JAR integrity and JSON validity. It has not been launched in a Minecraft client in this build environment, so in-game verification remains necessary.

## Build from source
Use JDK 21 and run `gradle build` in this directory. The Gradle build downloads the NeoForge and Mixin build dependencies.
