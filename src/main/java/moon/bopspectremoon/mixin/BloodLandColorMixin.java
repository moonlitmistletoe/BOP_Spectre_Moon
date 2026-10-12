
package moon.bopspectremoon.mixin;

import biomesoplenty.particle.DripParticleBOP;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = DripParticleBOP.BloodLandProvider.class, remap = false)
public abstract class BloodLandColorMixin {
    private static final ResourceKey<Biome> TARGET_BIOME =
            ResourceKey.create(Registries.BIOME,
                    ResourceLocation.fromNamespaceAndPath("biomesoplenty", "visceral_heap"));

    @Inject(
            method = "createParticle(Lnet/minecraft/core/particles/SimpleParticleType;Lnet/minecraft/client/multiplayer/ClientLevel;DDDDDD)Lnet/minecraft/client/particle/Particle;",
            at = @At("RETURN"),
            remap = false
    )
    private void bopSpectreMoon$recolour(
            SimpleParticleType type, ClientLevel level,
            double x, double y, double z,
            double dx, double dy, double dz,
            CallbackInfoReturnable<Particle> cir
    ) {
        Particle result = cir.getReturnValue();
        if (result instanceof TextureSheetParticle particle &&
                level.getBiome(BlockPos.containing(x, y, z)).is(TARGET_BIOME)) {
            ((TextureSheetParticleAccessor) particle).bopSpectreMoon$invokeSetSprite(
                    Minecraft.getInstance().getTextureAtlas(TextureAtlas.LOCATION_PARTICLES)
                            .apply(ResourceLocation.fromNamespaceAndPath("bop_spectre_moon", "particle/ectoplasm_drip_land"))
            );
            particle.setColor(0x55 / 255.0F, 0xF2 / 255.0F, 0xE6 / 255.0F);
        }
    }
}
