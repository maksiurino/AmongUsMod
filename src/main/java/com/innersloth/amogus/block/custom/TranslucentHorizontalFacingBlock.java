package com.innersloth.amogus.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.util.math.Direction;

public class TranslucentHorizontalFacingBlock extends HorizontalFacingBlock {
    public static final MapCodec<TranslucentHorizontalFacingBlock> CODEC = createCodec(TranslucentHorizontalFacingBlock::new);

    @Override
    protected MapCodec<? extends TranslucentHorizontalFacingBlock> getCodec() {
        return CODEC;
    }

    public TranslucentHorizontalFacingBlock(AbstractBlock.Settings settings) {
        super(settings);
    }

    @Override
    protected boolean isSideInvisible(BlockState state, BlockState stateFrom, Direction direction) {
        return stateFrom.isOf(this) ? true : super.isSideInvisible(state, stateFrom, direction);
    }
}
