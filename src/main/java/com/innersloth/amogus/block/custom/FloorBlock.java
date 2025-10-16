package com.innersloth.amogus.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class FloorBlock extends Block {
    public static final MapCodec<FloorBlock> CODEC = createCodec(FloorBlock::new);

    @Override
    public MapCodec<? extends FloorBlock> getCodec() {
        return CODEC;
    }

    public FloorBlock(AbstractBlock.Settings settings) {
        super(settings);
    }

    @Override
    public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
        if (entity instanceof PlayerEntity player) {
            if (player.isSprinting()) {
                player.setSprinting(false);
            }

            if (player.isSneaking()) {
                player.setSneaking(false);
            }

            super.onSteppedOn(world, pos, state, entity);
        }
    }
}
