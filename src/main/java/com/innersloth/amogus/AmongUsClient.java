package com.innersloth.amogus;

import com.innersloth.amogus.block.ModBlocks;
import com.innersloth.amogus.entity.ModEntities;
import com.innersloth.amogus.entity.client.ChairRenderer;
import com.innersloth.amogus.util.IconsHelpers;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.SharedConstants;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.resource.Resource;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.resource.ResourceType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.Icons;
import java.util.Map.Entry;

import java.io.IOException;

public class AmongUsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.EMERGENCY_MEETING_BUTTON, RenderLayer.getTranslucent());

        EntityRendererRegistry.register(ModEntities.CHAIR, ChairRenderer::new);

        ResourceManagerHelper.registerBuiltinResourcePack(AmongUs.identifier("alternate_icons"),
                FabricLoader.getInstance().getModContainer(AmongUs.MOD_ID).get(),
                ResourcePackActivationType.NORMAL);

        ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES)
                .registerReloadListener(new SimpleSynchronousResourceReloadListener() {
                    @Override
                    public Identifier getFabricId() {
                        return AmongUs.identifier("icons");
                    }

                    @Override
                    public void reload(ResourceManager manager) {
                        try {
                            IconsHelpers.window().setIcon(MinecraftClient.getInstance().getDefaultResourcePack(),
                                    SharedConstants.getGameVersion().isStable() ? Icons.RELEASE : Icons.SNAPSHOT);
                        } catch (IOException exception) {
                            AmongUs.LOGGER.error("failed to reset icon", exception);
                        }

                        boolean found = false;

                        for (Entry<Identifier, Resource> entry : manager
                                .findResources(IconsHelpers.ICONS, path -> true).entrySet()) {
                            if (found) {
                                return;
                            }

                            Identifier identifier = Identifier.of(entry.getKey().getNamespace(),
                                    IconsHelpers.ICONS
                                            + (SharedConstants.getGameVersion().isStable() ? IconsHelpers.STABLE
                                            : IconsHelpers.SNAPSHOT));
                            Resource resource = entry.getValue();

                            try {
                                IconsHelpers.setIcon(identifier, resource.getPack());
                                AmongUs.LOGGER.info("successfully used icons from resource pack {}",
                                        resource.getPackId());
                                found = true;
                            } catch (IOException exception) {
                                AmongUs.LOGGER.warn("failed to get icons from resource pack {}", resource.getPackId(),
                                        exception);
                            }
                        }
                    }
                });
    }
}
