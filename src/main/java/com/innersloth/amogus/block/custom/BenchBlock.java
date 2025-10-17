package com.innersloth.amogus.block.custom;

import com.innersloth.amogus.block.custom.enums.BenchPart;
import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class BenchBlock extends HorizontalFacingBlock {
    public static final MapCodec<BenchBlock> CODEC = createCodec(BenchBlock::new);
    public static final EnumProperty<BenchPart> BENCH_PART = EnumProperty.of("bench_part", BenchPart.class);
    private static final Map<Direction, VoxelShape> EDGES_SHAPE = VoxelShapes.createFacingShapeMap(Block.createCuboidShape(0, 0, 0, 16, 4, 8));
    private static final Map<Direction, VoxelShape> NORMAL_SHAPE = VoxelShapes.createFacingShapeMap(Block.createColumnShape(16, 0, 4));

    public BenchBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(BENCH_PART, BenchPart.EDGES));
    }

    @Override
    protected MapCodec<? extends HorizontalFacingBlock> getCodec() {
        return CODEC;
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return switch (state.get(BENCH_PART)) {
            case EDGES, EDGE_CORNERS, EDGE_CORNERS_MIRRORED -> (VoxelShape)EDGES_SHAPE.get(state.get(FACING).getOpposite());
            default -> (VoxelShape)NORMAL_SHAPE.get(state.get(FACING).getOpposite());
        };
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
