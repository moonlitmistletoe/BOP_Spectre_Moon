package moon.bopspectremoon;

import com.mojang.blaze3d.vertex.PoseStack;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.level.Level;

public final class FriendlyMobs {
    private static final Swap SPIDERS = new Swap(AccessibilityConfig.FRIENDLY_SPIDER_REPLACEMENT::get);
    private static final Swap BUGS = new Swap(AccessibilityConfig.FRIENDLY_BUG_REPLACEMENT::get);

    private static boolean drawing;

    private FriendlyMobs() {}

    public static boolean draw(EntityRenderDispatcher dispatcher, Entity entity, double x, double y, double z, float yaw,
                               float partialTick, PoseStack pose, MultiBufferSource buffers, int light) {
        if (drawing || !(entity instanceof LivingEntity mob)) {
            return false;
        }

        Swap swap = null;
        if (AccessibilityConfig.friendlyBugs() && listed(mob, AccessibilityConfig.FRIENDLY_BUG_ENTITIES.get())) {
            swap = BUGS;
        } else if (AccessibilityConfig.friendlySpiders() && isSpider(mob)) {
            swap = SPIDERS;
        }
        if (swap == null) {
            return false;
        }

        LivingEntity copy = swap.standIn(mob.level());
        if (copy == null) {
            return false;
        }

        copy.setPos(mob.getX(), mob.getY(), mob.getZ());
        copy.xo = mob.xo;
        copy.yo = mob.yo;
        copy.zo = mob.zo;
        copy.xOld = mob.xOld;
        copy.yOld = mob.yOld;
        copy.zOld = mob.zOld;
        copy.setYRot(mob.getYRot());
        copy.yRotO = mob.yRotO;
        copy.setXRot(mob.getXRot());
        copy.xRotO = mob.xRotO;
        copy.yBodyRot = mob.yBodyRot;
        copy.yBodyRotO = mob.yBodyRotO;
        copy.yHeadRot = mob.yHeadRot;
        copy.yHeadRotO = mob.yHeadRotO;
        copy.tickCount = mob.tickCount;
        copy.hurtTime = mob.hurtTime;
        copy.deathTime = mob.deathTime;
        copy.setCustomName(mob.getCustomName());
        copy.setCustomNameVisible(mob.isCustomNameVisible());
        copy.walkAnimation.update(mob.walkAnimation.speed(partialTick), 1.0F);

        float scale = Math.max(0.6F, Math.min(8.0F, mob.getBbWidth() / copy.getBbWidth()));
        drawing = true;
        try {
            pose.pushPose();
            pose.translate(x, y, z);
            pose.scale(scale, scale, scale);
            dispatcher.render(copy, 0.0, 0.0, 0.0, yaw, partialTick, pose, buffers, light);
            pose.popPose();
        } finally {
            drawing = false;
        }
        return true;
    }

    public static boolean isSpider(LivingEntity entity) {
        return entity instanceof Spider || listed(entity, AccessibilityConfig.FRIENDLY_SPIDER_ENTITIES.get());
    }

    private static boolean listed(LivingEntity entity, List<? extends String> ids) {
        return ids.contains(BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType()).toString());
    }

    private static final class Swap {
        private final Supplier<String> replacement;
        private LivingEntity standIn;
        private Level level;
        private String id;

        private Swap(Supplier<String> replacement) {
            this.replacement = replacement;
        }

        private LivingEntity standIn(Level current) {
            String wanted = replacement.get();
            if (level != current || !wanted.equals(id)) {
                standIn = null;
                level = current;
                id = wanted;
                EntityType.byString(wanted)
                        .map(type -> type.create(current))
                        .filter(LivingEntity.class::isInstance)
                        .map(LivingEntity.class::cast)
                        .ifPresent(created -> {
                            if (created instanceof Slime slime) {
                                slime.setSize(1, false);
                            }
                            if (created instanceof AgeableMob ageable) {
                                ageable.setBaby(true);
                            }
                            standIn = created;
                        });
            }
            return standIn;
        }
    }
}