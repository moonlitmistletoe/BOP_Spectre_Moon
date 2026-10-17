 package moon.bopspectremoon;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ViewportEvent;

@EventBusSubscriber(
        modid = "bop_spectre_moon",
        value = Dist.CLIENT,
        bus = EventBusSubscriber.Bus.GAME
)
public final class VisceralHeapFog {
    private static final ResourceKey<Biome> VISCERAL_HEAP =
            ResourceKey.create(
                    Registries.BIOME,
                    ResourceLocation.fromNamespaceAndPath(
                            "biomesoplenty", "visceral_heap"
                    )
            );

    private VisceralHeapFog() {}

    @SubscribeEvent
    public static void onFogColor(ViewportEvent.ComputeFogColor event) {
        Minecraft minecraft = Minecraft.getInstance();

        if (minecraft.level == null || !AccessibilityConfig.ectoplasmBlood()) {
            return;
        }

        BlockPos pos = BlockPos.containing(event.getCamera().getPosition());

        if (minecraft.level.getBiome(pos).is(VISCERAL_HEAP)) {
            event.setRed(0x55 / 255.0F);
            event.setGreen(0xF2 / 255.0F);
            event.setBlue(0xE6 / 255.0F);
        }
    }
}
