package com.innersloth.amogus.block.custom;

import com.innersloth.amogus.block.custom.enums.BenchPart;
import com.mojang.serialization.MapCodec;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import org.jetbrains.annotations.Nullable;

public class BenchBlock extends HorizontalFacingBlock {
    public static final MapCodec<BenchBlock> CODEC = createCodec(BenchBlock::new);
    public static final EnumProperty<BenchPart> BENCH_PART = EnumProperty.of("bench_part", BenchPart.class);

    public BenchBlock(AbstractBlock.Settings settings) {
        super(settings);
    }

    @Override
    protected MapCodec<? extends HorizontalFacingBlock> getCodec() {
        return CODEC;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, BENCH_PART);
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing().getOpposite()).with(BENCH_PART, BenchPart.EDGES);
    }
}
