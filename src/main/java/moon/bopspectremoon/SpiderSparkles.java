package moon.bopspectremoon;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;

@EventBusSubscriber(
        modid = "bop_spectre_moon",
        value = Dist.CLIENT
)
public final class SpiderSparkles {
    private SpiderSparkles() {}

    @SubscribeEvent
    public static void onTick(ClientTickEvent.Post event) {
        Minecraft minecraft = Minecraft.getInstance();
        ClientLevel level = minecraft.level;
        if (level == null || minecraft.isPaused() || !AccessibilityConfig.friendlySpiders()) {
            return;
        }

        ParticleOptions particle = particle();
        if (particle == null) {
            return;
        }

        for (Entity entity : level.entitiesForRendering()) {
            if (entity instanceof LivingEntity mob && mob.isAlive() && FriendlyMobs.isSpider(mob)) {
                int count = Math.max(1, Math.round(mob.getBbWidth()));
                for (int i = 0; i < count; i++) {
                    level.addParticle(particle, mob.getRandomX(0.5), mob.getRandomY(), mob.getRandomZ(0.5),
                            (level.random.nextDouble() - 0.5) * 0.4, level.random.nextDouble() * 0.2, (level.random.nextDouble() - 0.5) * 0.4);
                }
            }
        }
    }

    private static ParticleOptions particle() {
        ResourceLocation id = ResourceLocation.tryParse(AccessibilityConfig.FRIENDLY_SPIDER_PARTICLE.get());
        return id == null ? null : BuiltInRegistries.PARTICLE_TYPE.getOptional(id)
                .filter(SimpleParticleType.class::isInstance)
                .map(SimpleParticleType.class::cast)
                .orElse(null);
    }
}