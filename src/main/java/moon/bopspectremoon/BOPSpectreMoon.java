package moon.bopspectremoon;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod(value = "bop_spectre_moon", dist = Dist.CLIENT)
public final class BOPSpectreMoon {
    public BOPSpectreMoon(ModContainer container) {
        container.registerConfig(ModConfig.Type.CLIENT, AccessibilityConfig.SPEC);
        container.registerExtensionPoint(IConfigScreenFactory.class, (mod, parent) -> new AccessibilityScreen(parent));
    }
}
