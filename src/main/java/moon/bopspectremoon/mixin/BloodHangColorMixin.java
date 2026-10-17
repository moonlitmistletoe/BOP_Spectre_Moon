
package moon.bopspectremoon.mixin;

import moon.bopspectremoon.AccessibilityConfig;
import biomesoplenty.particle.DripParticleBOP;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.particles.SimpleParticleType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = DripParticleBOP.BloodHangProvider.class, remap = false)
public abstract class BloodHangColorMixin {

    @Inject(
            method = "createParticle(Lnet/minecraft/core/particles/SimpleParticleType;Lnet/minecraft/client/multiplayer/ClientLevel;DDDDDD)Lnet/minecraft/client/particle/TextureSheetParticle;",
            at = @At("RETURN"),
            remap = false
    )
    private void bopSpectreMoon$recolorBloodHang(
            SimpleParticleType type,
            ClientLevel level,
            double x, double y, double z,
            double xSpeed, double ySpeed, double zSpeed,
            CallbackInfoReturnable<TextureSheetParticle> cir
    ) {
        TextureSheetParticle particle = cir.getReturnValue();
        if (particle != null && AccessibilityConfig.ectoplasmBlood()) {
            particle.setColor(85.0F / 255.0F, 242.0F / 255.0F, 230.0F / 255.0F);
        }
    }
}
