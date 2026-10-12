# BOP_Spectre_Moon

Client-side NeoForge mod for Minecraft 1.21.1, NeoForge 21.1.248 and Biomes O' Plenty 21.1.0.14.

## What it does
- Bundles the supplied Spectre resource assets inside the mod; no separate resource-pack ZIP is required.
- Uses a client Mixin to recolour Biomes O' Plenty hanging, falling and landing blood particles to ectoplasm cyan (`#55F2E6`).

## Install
Put `BOP_Spectre_Moon.jar` in the client's `mods` folder alongside NeoForge 1.21.1 and Biomes O' Plenty 21.1.0.14.

## Build from source
Use JDK 21 and Gradle, then run `gradle build`. The Gradle build is configured for NeoForge 21.1.248 and Mixin 0.8.7.

The included JAR has been checked for archive integrity and the compiled mixin class has been checked for a runtime-visible `@Mixin` annotation. An in-game launch test was not possible in this build environment.
