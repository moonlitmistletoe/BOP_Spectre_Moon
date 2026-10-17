package moon.bopspectremoon;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackLocationInfo;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackSelectionConfig;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.PathPackResources;
import net.minecraft.server.packs.metadata.MetadataSectionSerializer;
import net.minecraft.server.packs.repository.BuiltInPackSource;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.server.packs.resources.IoSupplier;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.AddPackFindersEvent;

@EventBusSubscriber(
        modid = "bop_spectre_moon",
        value = Dist.CLIENT
)
public final class AccessibilityPacks {
    private static final String ID = "bop_spectre_moon/accessibility";

    private AccessibilityPacks() {}

    @SubscribeEvent
    public static void onAddPackFinders(AddPackFindersEvent event) {
        if (event.getPackType() != PackType.CLIENT_RESOURCES) {
            return;
        }

        event.addRepositorySource(accept -> {
            List<String> folders = enabledFolders();
            if (folders.isEmpty()) {
                return;
            }

            PackLocationInfo info = new PackLocationInfo(ID, Component.literal("QFS Accessibility"), PackSource.BUILT_IN, Optional.empty());
            Path root = ModList.get().getModFileById("bop_spectre_moon").getFile().findResource("resourcepacks");
            accept.accept(Pack.readMetaAndCreate(
                    info,
                    BuiltInPackSource.fromName(location -> new Layered(location, folders.stream()
                            .<PackResources>map(folder -> new PathPackResources(location, root.resolve(folder)))
                            .toList())),
                    PackType.CLIENT_RESOURCES,
                    new PackSelectionConfig(true, Pack.Position.TOP, false)));
        });
    }

    public static void reload() {
        Minecraft minecraft = Minecraft.getInstance();
        minecraft.getResourcePackRepository().reload();
        minecraft.reloadResourcePacks();
    }

    private static List<String> enabledFolders() {
        boolean blood = AccessibilityConfig.ectoplasmBlood();
        boolean holes = AccessibilityConfig.smoothHoles();
        List<String> folders = new ArrayList<>();
        if (blood) {
            folders.add("ectoplasm");
        }
        if (AccessibilityConfig.vanillaMobSkins()) {
            folders.add("vanilla_mob_skins");
        }
        if (holes) {
            folders.add("smooth_holes");
            if (blood) {
                folders.add("smooth_holes_ectoplasm");
            }
        }
        if (AccessibilityConfig.friendlySpiders()) {
            folders.add("friendly_spiders");
        }
        if (AccessibilityConfig.friendlyBugs()) {
            folders.add("friendly_bugs");
        }
        return folders;
    }

    private record Layered(PackLocationInfo location, List<PackResources> layers) implements PackResources {
        @Override
        public IoSupplier<InputStream> getRootResource(String... elements) {
            for (int i = layers.size() - 1; i >= 0; i--) {
                IoSupplier<InputStream> resource = layers.get(i).getRootResource(elements);
                if (resource != null) {
                    return resource;
                }
            }
            return null;
        }

        @Override
        public IoSupplier<InputStream> getResource(PackType type, ResourceLocation location) {
            for (int i = layers.size() - 1; i >= 0; i--) {
                IoSupplier<InputStream> resource = layers.get(i).getResource(type, location);
                if (resource != null) {
                    return resource;
                }
            }
            return null;
        }

        @Override
        public void listResources(PackType type, String namespace, String path, ResourceOutput output) {
            for (PackResources layer : layers) {
                layer.listResources(type, namespace, path, output);
            }
        }

        @Override
        public Set<String> getNamespaces(PackType type) {
            Set<String> namespaces = new LinkedHashSet<>();
            for (PackResources layer : layers) {
                namespaces.addAll(layer.getNamespaces(type));
            }
            return namespaces;
        }

        @Override
        public <T> T getMetadataSection(MetadataSectionSerializer<T> serializer) throws IOException {
            return layers.get(0).getMetadataSection(serializer);
        }

        @Override
        public void close() {
            layers.forEach(PackResources::close);
        }
    }
}