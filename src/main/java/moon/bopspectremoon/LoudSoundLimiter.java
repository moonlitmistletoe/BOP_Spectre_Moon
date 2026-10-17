package moon.bopspectremoon;

import java.util.EnumSet;
import java.util.Set;
import java.util.regex.Pattern;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.sounds.SoundSource;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.sound.PlaySoundEvent;

@EventBusSubscriber(
        modid = "bop_spectre_moon",
        value = Dist.CLIENT
)
public final class LoudSoundLimiter {
    private static final Set<SoundSource> LIMITED = EnumSet.of(SoundSource.HOSTILE, SoundSource.NEUTRAL, SoundSource.WEATHER);
    private static final Pattern LOUD_NAME = Pattern.compile(
            "(^|[._/])(roar|scream|shriek|screech|bellow|boom|thunder|horn|siren|alarm)"
                    + "|wither\\.(spawn|death)|ender_dragon\\.(growl|death)|elder_guardian\\.curse");
    private static final float LOUD_VOLUME = 2.0F;
    private static final int HOLD_TICKS = 20;
    private static final int RELEASE_TICKS = 60;

    private static float factor = 1.0F;
    private static int hold;

    private LoudSoundLimiter() {}

    public static float factor(SoundSource source) {
        return LIMITED.contains(source) ? factor : 1.0F;
    }

    @SubscribeEvent
    public static void onPlaySound(PlaySoundEvent event) {
        SoundInstance sound = event.getSound();
        if (sound != null && AccessibilityConfig.soundLimiter() && isLoud(sound, Minecraft.getInstance().getSoundManager())) {
            factor = (float) AccessibilityConfig.LIMITER_VOLUME.get().doubleValue();
            hold = HOLD_TICKS;
        }
    }

    @SubscribeEvent
    public static void onTick(ClientTickEvent.Post event) {
        if (factor >= 1.0F) {
            return;
        }

        if (!AccessibilityConfig.soundLimiter()) {
            factor = 1.0F;
        } else if (hold > 0) {
            hold--;
        } else {
            float floor = (float) AccessibilityConfig.LIMITER_VOLUME.get().doubleValue();
            factor = Math.min(1.0F, factor + (1.0F - floor) / RELEASE_TICKS);
        }
        refreshPlayingSounds();
    }

    private static boolean isLoud(SoundInstance sound, SoundManager manager) {
        if (LOUD_NAME.matcher(sound.getLocation().getPath()).find()) {
            return true;
        }
        sound.resolve(manager);
        return sound.getVolume() >= LOUD_VOLUME;
    }

    private static void refreshPlayingSounds() {
        Minecraft minecraft = Minecraft.getInstance();
        for (SoundSource source : LIMITED) {
            minecraft.getSoundManager().updateSourceVolume(source, minecraft.options.getSoundSourceVolume(source));
        }
    }
}