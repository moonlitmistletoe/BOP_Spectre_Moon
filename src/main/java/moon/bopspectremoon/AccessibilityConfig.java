package moon.bopspectremoon;

import java.util.List;
import net.neoforged.neoforge.common.ModConfigSpec;

public final class AccessibilityConfig {
    public record Option(String key, ModConfigSpec.BooleanValue value, boolean reloadsResources) {}

    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.BooleanValue ECTOPLASM_BLOOD = BUILDER
            .comment("Show blood as ectoplasm cyan.")
            .define("ectoplasm_blood", true);
    public static final ModConfigSpec.BooleanValue VANILLA_MOB_SKINS = BUILDER
            .comment("Show the Cucurucho and Dark Cucurucho mobs with vanilla skins.")
            .define("vanilla_mob_skins", false);
    public static final ModConfigSpec.BooleanValue FRIENDLY_SPIDERS = BUILDER
            .comment("Show spiders as slimes that sound like cats. Every spider mob counts, and friendly_spider_entities adds others.")
            .define("friendly_spiders", false);
    public static final ModConfigSpec.BooleanValue FRIENDLY_BUGS = BUILDER
            .comment("Show bugs as small slimes with softer sounds. The bugs are listed in friendly_bug_entities and the stand-in mob is friendly_bug_replacement.")
            .define("friendly_bugs", false);
    public static final ModConfigSpec.BooleanValue SMOOTH_HOLES = BUILDER
            .comment("Show clusters of holes and bubbles (sponges, honeycomb, porous blocks) as smooth surfaces.")
            .define("smooth_holes", false);
    public static final ModConfigSpec.BooleanValue SOUND_LIMITER = BUILDER
            .comment("Turn mob and weather sounds down for a few seconds when a loud sound plays.")
            .define("sound_limiter", false);

    public static final ModConfigSpec.DoubleValue LIMITER_VOLUME = BUILDER
            .comment("Volume of mob and weather sounds while the sound limiter is active. 1.0 means no change.")
            .defineInRange("limiter_volume", 0.35, 0.05, 1.0);
    public static final ModConfigSpec.ConfigValue<String> FRIENDLY_SPIDER_REPLACEMENT = BUILDER
            .comment("Mob drawn in place of spiders when friendly_spiders is on. Must be a living mob.")
            .define("friendly_spider_replacement", "minecraft:slime");
    public static final ModConfigSpec.ConfigValue<List<? extends String>> FRIENDLY_SPIDER_ENTITIES = BUILDER
            .comment("Spider-like mobs that are not spiders in the game code, shown as the replacement mob when friendly_spiders is on.")
            .defineListAllowEmpty("friendly_spider_entities", List.of("born_in_chaos_v1:baby_spider_controlled"), () -> "minecraft:spider", entry -> entry instanceof String);
    public static final ModConfigSpec.ConfigValue<String> FRIENDLY_SPIDER_PARTICLE = BUILDER
            .comment("Particle shown around spiders when friendly_spiders is on, so they stand out from bugs. Leave empty for none.")
            .define("friendly_spider_particle", "minecraft:witch");
    public static final ModConfigSpec.ConfigValue<String> FRIENDLY_BUG_REPLACEMENT = BUILDER
            .comment("Mob drawn in place of the listed bugs when friendly_bugs is on. Must be a living mob.")
            .define("friendly_bug_replacement", "minecraft:slime");
    public static final ModConfigSpec.ConfigValue<List<? extends String>> FRIENDLY_BUG_ENTITIES = BUILDER
            .comment("Bugs shown as the replacement mob when friendly_bugs is on.")
            .defineListAllowEmpty("friendly_bug_entities", List.of(
                    "minecraft:silverfish",
                    "minecraft:endermite",
                    "alexsmobs:centipede_head",
                    "alexsmobs:centipede_body",
                    "alexsmobs:centipede_tail",
                    "alexsmobs:cockroach",
                    "alexsmobs:crimson_mosquito",
                    "alexsmobs:fly",
                    "alexsmobs:leafcutter_ant",
                    "alexsmobs:tarantula_hawk",
                    "alexscaves:gammaroach"
            ), () -> "minecraft:silverfish", entry -> entry instanceof String);

    public static final ModConfigSpec SPEC = BUILDER.build();

    public static final List<Option> OPTIONS = List.of(
            new Option("ectoplasm_blood", ECTOPLASM_BLOOD, true),
            new Option("vanilla_mob_skins", VANILLA_MOB_SKINS, true),
            new Option("friendly_spiders", FRIENDLY_SPIDERS, true),
            new Option("friendly_bugs", FRIENDLY_BUGS, true),
            new Option("smooth_holes", SMOOTH_HOLES, true),
            new Option("sound_limiter", SOUND_LIMITER, false)
    );

    private AccessibilityConfig() {}

    public static boolean ectoplasmBlood() {
        return enabled(ECTOPLASM_BLOOD);
    }

    public static boolean vanillaMobSkins() {
        return enabled(VANILLA_MOB_SKINS);
    }

    public static boolean friendlySpiders() {
        return enabled(FRIENDLY_SPIDERS);
    }

    public static boolean friendlyBugs() {
        return enabled(FRIENDLY_BUGS);
    }

    public static boolean smoothHoles() {
        return enabled(SMOOTH_HOLES);
    }

    public static boolean soundLimiter() {
        return enabled(SOUND_LIMITER);
    }

    public static int resourceState() {
        int state = 0;
        for (Option option : OPTIONS) {
            state = (state << 1) | (option.reloadsResources() && enabled(option.value()) ? 1 : 0);
        }
        return state;
    }

    private static boolean enabled(ModConfigSpec.BooleanValue option) {
        return SPEC.isLoaded() ? option.get() : option.getDefault();
    }
}