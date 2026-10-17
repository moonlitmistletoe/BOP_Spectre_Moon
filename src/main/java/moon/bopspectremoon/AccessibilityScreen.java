package moon.bopspectremoon;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

public final class AccessibilityScreen extends Screen {
    private final Screen parent;
    private final int resourceState = AccessibilityConfig.resourceState();

    public AccessibilityScreen(Screen parent) {
        super(Component.translatable("bop_spectre_moon.screen.title"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        int x = width / 2 - 100;
        int y = 44;
        for (AccessibilityConfig.Option option : AccessibilityConfig.OPTIONS) {
            String key = "bop_spectre_moon.option." + option.key();
            addRenderableWidget(CycleButton.onOffBuilder(option.value().get())
                    .withTooltip(value -> Tooltip.create(Component.translatable(key + ".tooltip")))
                    .create(x, y, 200, 20, Component.translatable(key), (button, value) -> option.value().set(value)));
            y += 24;
        }
        addRenderableWidget(Button.builder(CommonComponents.GUI_DONE, button -> onClose())
                .bounds(x, y + 12, 200, 20)
                .build());
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        super.render(graphics, mouseX, mouseY, partialTick);
        graphics.drawCenteredString(font, title, width / 2, 12, 0xFFFFFF);
        graphics.drawCenteredString(font, Component.translatable("bop_spectre_moon.screen.reload_note"), width / 2, 26, 0xA0A0A0);
    }

    @Override
    public void onClose() {
        AccessibilityConfig.SPEC.save();
        minecraft.setScreen(parent);
        if (resourceState != AccessibilityConfig.resourceState()) {
            AccessibilityPacks.reload();
        }
    }
}
