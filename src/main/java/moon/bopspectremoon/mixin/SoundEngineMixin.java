package moon.bopspectremoon.mixin;

import moon.bopspectremoon.LoudSoundLimiter;
import net.minecraft.client.sounds.SoundEngine;
import net.minecraft.sounds.SoundSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = SoundEngine.class, remap = false)
public abstract class SoundEngineMixin {
    @Inject(method = "getVolume", at = @At("RETURN"), cancellable = true, remap = false)
    private void bopSpectreMoon$limitLoudSounds(SoundSource source, CallbackInfoReturnable<Float> cir) {
        if (source != null) {
            cir.setReturnValue(cir.getReturnValueF() * LoudSoundLimiter.factor(source));
        }
    }
}
