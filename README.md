# BOP_Spectre_Moon

Client-side NeoForge mod for Minecraft 1.21.1, NeoForge 21.1.248 and Biomes O' Plenty 21.1.0.14.

## What it does
Adds accessibility options for QFS. Each one can be turned on or off in game.

- **Cyan Blood** (on by default): bundles the supplied Spectre resource assets and uses a client Mixin to recolour Biomes O' Plenty hanging, falling and landing blood particles to ectoplasm cyan (`#55F2E6`).
- **Vanilla Mob Skins**: shows the QFSExtra Cucurucho and Dark Cucurucho mobs with their vanilla skins.
- **Friendly Spiders**: shows every spider (vanilla, cave, QFSExtra dark, Born in Chaos and any other mod's spider) as a slime with purple sparkles, with cat meows and matching captions. The Born in Chaos tamed baby spider is included, and other mobs can be added in the config.
- **Friendly Bugs**: shows silverfish, endermites and the bugs from Alex's Mobs and Alex's Caves as small slimes, with softer sounds. The bug list and the replacement mob are set in the config.
- **Hole-Free Textures**: shows sponges, honeycomb, barnacles, porous blocks and similar textures without clusters of holes or bubbles.
- **Loud Sound Limiter**: turns mob and weather sounds down as soon as a loud sound starts (Wither spawn, Ender Dragon, Warden, thunder, roars, screams and so on), then brings them back up over about three seconds. The volume sliders in the sound settings are never changed.

The assets for each option are built-in resource packs inside the mod, so no separate resource-pack ZIP is required.

## Changing the options
Open Options and press QFS Accessibility, or open the mod's Config from the Mods menu. Press Done. Options that change textures or sounds reload resources (the same loading screen as toggling a resource pack, no restart needed). The Loud Sound Limiter applies straight away.

The choices are saved in `config/bop_spectre_moon-client.toml`. That file also holds the volume used by the sound limiter (`limiter_volume`), the mobs treated as bugs (`friendly_bug_entities`), the mob drawn instead of bugs (`friendly_bug_replacement`), the mob drawn instead of spiders (`friendly_spider_replacement`) and extra mobs treated as spiders (`friendly_spider_entities`).

## Languages
The accessibility screen is translated into Spanish (Spain and Mexico), French, German, Italian, Portuguese (Brazil and Portugal), Russian, Ukrainian, Polish, Dutch, Turkish, Japanese, Korean and Chinese (Simplified and Traditional). Other languages show English.

## Install
Put `BOP_Spectre_Moon.jar` in the client's `mods` folder alongside NeoForge 1.21.1 and Biomes O' Plenty 21.1.0.14.

## Build from source
Use JDK 21 and Gradle, then run `gradle build`. The Gradle build is configured for NeoForge 21.1.248 and Mixin 0.8.7.
