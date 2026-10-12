package moon.bopspectremoon.mixin;

import net.minecraft.client.particle.Particle;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/** Recolours Biomes O' Plenty's three blood-drip particle providers. */
@Mixin(targets = {
        "biomesoplenty.particle.DripParticleBOP$BloodHangProvider",
        "biomesoplenty.particle.DripParticleBOP$BloodFallProvider",
        "biomesoplenty.particle.DripParticleBOP$BloodLandProvider"
})
public abstract class BloodParticleColorMixin {
    // Ectoplasm cyan: #55F2E6
    private static final float ECTOPLASM_RED = 0x55 / 255.0F;
    private static final float ECTOPLASM_GREEN = 0xF2 / 255.0F;
    private static final float ECTOPLASM_BLUE = 0xE6 / 255.0F;

    @Inject(method = "createParticle", at = @At("RETURN"), remap = false)
    private void bopSpectreMoon$recolourBloodParticle(CallbackInfoReturnable<Particle> cir) {
        Particle particle = cir.getReturnValue();
        if (particle != null) {
            particle.setColor(ECTOPLASM_RED, ECTOPLASM_GREEN, ECTOPLASM_BLUE);
        }
    }
}
