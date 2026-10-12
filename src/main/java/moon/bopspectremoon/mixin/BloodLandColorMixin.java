package moon.bopspectremoon.mixin;

import biomesoplenty.particle.DripParticleBOP;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = DripParticleBOP.BloodLandProvider.class, remap = false)
public abstract class BloodLandColorMixin {

    @Inject(
            method = "createParticle(Lnet/minecraft/core/particles/SimpleParticleType;Lnet/minecraft/client/multiplayer/ClientLevel;DDDDDD)Lnet/minecraft/client/particle/Particle;",
            at = @At("RETURN"),
            remap = false
    )
    private void bopSpectreMoon$recolour(
            SimpleParticleType type,
            ClientLevel level,
            double x, double y, double z,
            double dx, double dy, double dz,
            CallbackInfoReturnable<Particle> cir
    ) {
        if (level.getBiome(BlockPos.containing(x, y, z))
                .unwrapKey()
                .map(key -> key.location().toString()
                        .equals("biomesoplenty:visceral_heap"))
                .orElse(false)
                && cir.getReturnValue() != null) {

            cir.getReturnValue().setColor(
                    0x55 / 255.0F,
                    0xF2 / 255.0F,
                    0xE6 / 255.0F
            );
        }
    }
}