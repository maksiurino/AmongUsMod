package com.innersloth.amogus;

import com.innersloth.amogus.block.ModBlocks;
import com.innersloth.amogus.entity.ModEntities;
import com.innersloth.amogus.entity.client.ChairRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.util.Window;

public class AmongUsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.EMERGENCY_MEETING_BUTTON, RenderLayer.getTranslucent());

        EntityRendererRegistry.register(ModEntities.CHAIR, ChairRenderer::new);
    }
}
