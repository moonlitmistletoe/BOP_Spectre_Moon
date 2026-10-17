package moon.bopspectremoon.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import moon.bopspectremoon.FriendlyMobs;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = EntityRenderDispatcher.class, remap = false)
public abstract class EntityRenderDispatcherMixin {
    @Inject(method = "render", at = @At("HEAD"), cancellable = true, remap = false)
    private <E extends Entity> void bopSpectreMoon$drawStandIn(E entity, double x, double y, double z, float yaw, float partialTick,
                                                               PoseStack pose, MultiBufferSource buffers, int light, CallbackInfo ci) {
        if (FriendlyMobs.draw((EntityRenderDispatcher) (Object) this, entity, x, y, z, yaw, partialTick, pose, buffers, light)) {
            ci.cancel();
        }
    }
}