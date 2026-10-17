package moon.bopspectremoon;

import java.util.Comparator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.options.OptionsScreen;
import net.minecraft.network.chat.Component;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ScreenEvent;

@EventBusSubscriber(
        modid = "bop_spectre_moon",
        value = Dist.CLIENT
)
public final class AccessibilityButton {
    private AccessibilityButton() {}

    @SubscribeEvent
    public static void onScreenInit(ScreenEvent.Init.Post event) {
        if (!(event.getScreen() instanceof OptionsScreen options)) {
            return;
        }

        List<AbstractWidget> widgets = event.getListenersList().stream()
                .filter(AbstractWidget.class::isInstance)
                .map(AbstractWidget.class::cast)
                .filter(widget -> widget.getHeight() == Button.DEFAULT_HEIGHT)
                .toList();
        int topRow = widgets.stream().mapToInt(AbstractWidget::getY).min().orElse(0);
        int secondRow = widgets.stream().mapToInt(AbstractWidget::getY).filter(y -> y > topRow).min().orElse(topRow);
        List<AbstractWidget> first = widgets.stream().filter(w -> w.getY() == topRow).toList();
        AbstractWidget leftColumn = first.stream().min(Comparator.comparingInt(AbstractWidget::getX)).orElse(null);
        int rightX = first.stream().mapToInt(AbstractWidget::getX).filter(x -> leftColumn != null && x > leftColumn.getX()).min().orElse(0);
        boolean known = leftColumn != null && rightX != 0;
        event.addListener(Button.builder(
                        Component.translatable("bop_spectre_moon.screen.button"),
                        button -> Minecraft.getInstance().setScreen(new AccessibilityScreen(options)))
                .bounds(known ? rightX : 5, known ? (topRow + secondRow) / 2 : 5, known ? leftColumn.getWidth() : 150, 20)
                .build());
    }
}
