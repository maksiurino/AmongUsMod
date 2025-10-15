package com.innersloth.amogus.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public class TransparentHorizontalFacingBlock extends TranslucentHorizontalFacingBlock {
    public static final MapCodec<TransparentHorizontalFacingBlock> CODEC = createCodec(TransparentHorizontalFacingBlock::new);

    public TransparentHorizontalFacingBlock(AbstractBlock.Settings settings) {
        super(settings);
    }

    @Override
    protected MapCodec<? extends  TransparentHorizontalFacingBlock> getCodec() {
        return CODEC;
    }

    @Override
    protected VoxelShape getCameraCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VoxelShapes.empty();
    }

    @Override
    protected float getAmbientOcclusionLightLevel(BlockState state, BlockView world, BlockPos pos) {
        return 1.0F;
    }

    @Override
    protected boolean isTransparent(BlockState state) {
        return true;
    }
}
