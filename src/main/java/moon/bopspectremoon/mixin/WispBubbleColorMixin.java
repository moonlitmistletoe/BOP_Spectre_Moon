
package moon.bopspectremoon.mixin;

import biomesoplenty.particle.WispBubbleParticle;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.client.particle.Particle;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = WispBubbleParticle.Provider.class, remap = false)
public abstract class WispBubbleColorMixin {

    @Inject(
            method = "createParticle(Lnet/minecraft/core/particles/SimpleParticleType;Lnet/minecraft/client/multiplayer/ClientLevel;DDDDDD)Lnet/minecraft/client/particle/Particle;",
            at = @At("RETURN"),
            remap = false
    )
    private void recolorWispBubble(
            SimpleParticleType type,
            ClientLevel level,
            double x, double y, double z,
            double xd, double yd, double zd,
            CallbackInfoReturnable<Particle> cir
    ) {
        cir.getReturnValue().setColor(
                0x55 / 255.0F,
                0xF2 / 255.0F,
                0xE6 / 255.0F
        );
    }
}
