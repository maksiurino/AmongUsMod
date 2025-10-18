package com.innersloth.amogus.block.custom;

import com.innersloth.amogus.block.ModBlocks;
import com.innersloth.amogus.block.custom.enums.TablePart;
import com.innersloth.amogus.block.custom.enums.TableType;
import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.block.enums.BlockHalf;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import com.innersloth.amogus.util.Properties;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import net.minecraft.world.tick.ScheduledTickView;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class TableBlock extends Block implements Waterloggable {
    public static final MapCodec<TableBlock> CODEC = createCodec(TableBlock::new);
    public static final EnumProperty<Direction> FACING = HorizontalFacingBlock.FACING;
    public static final EnumProperty<TableType> TYPE = Properties.TABLE_TYPE;
    public static final EnumProperty<TablePart> TABLE_PART = Properties.TABLE_PART;
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
    private static final VoxelShape BOTTOM_SHAPE = Block.createColumnShape(16.0, 0.0, 8.0);
    private static final VoxelShape CORNER_SHAPE_FIRST = Block.createCuboidShape(0, 0, 0, 4, 8, 4);
    private static final VoxelShape CORNER_SHAPE_SECOND = Block.createCuboidShape(0,0,0,0,0,0);
    private static final Map<Direction, VoxelShape> CORNER_SHAPE = VoxelShapes.createFacingShapeMap(VoxelShapes.combineAndSimplify(CORNER_SHAPE_FIRST, CORNER_SHAPE_SECOND, BooleanBiFunction.OR));
    private static final Map<Direction, VoxelShape> CENTER_EDGE_SIDE_SHAPE = VoxelShapes.createFacingShapeMap(Block.createCuboidShape(0, 0, 0, 16, 8, 8));

    @Override
    public MapCodec<? extends TableBlock> getCodec() {
        return CODEC;
    }

    public TableBlock(AbstractBlock.Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(TYPE, TableType.BOTTOM).with(TABLE_PART, TablePart.CENTER).with(WATERLOGGED, false).with(FACING, Direction.NORTH));
    }

    @Override
    protected boolean hasSidedTransparency(BlockState state) {
        return state.get(TYPE) != TableType.DOUBLE;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(TYPE, WATERLOGGED, TABLE_PART, FACING);
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return switch ((TablePart)state.get(TABLE_PART)) {
            case CORNER -> (VoxelShape)CORNER_SHAPE.get(state.get(FACING));
            case CENTER_EDGE_SIDE, CENTER_EDGE_SIDE_MIRRORED -> (VoxelShape) CENTER_EDGE_SIDE_SHAPE.get(state.get(FACING));
            default -> BOTTOM_SHAPE;
        };
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing().getOpposite()).with(TABLE_PART, TablePart.CENTER).with(TYPE, TableType.BOTTOM);
    }

    @Override
    protected boolean canReplace(BlockState state, ItemPlacementContext context) {
        ItemStack itemStack = context.getStack();
        TableType slabType = state.get(TYPE);
        if (slabType == TableType.DOUBLE || !itemStack.isOf(this.asItem())) {
            return false;
        } else if (context.canReplaceExisting()) {
            boolean bl = context.getHitPos().y - context.getBlockPos().getY() > 0.5;
            Direction direction = context.getSide();
            return slabType == TableType.BOTTOM
                    ? direction == Direction.UP || bl && direction.getAxis().isHorizontal()
                    : direction == Direction.DOWN || !bl && direction.getAxis().isHorizontal();
        } else {
            return true;
        }
    }

    @Override
    protected FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    @Override
    public boolean tryFillWithFluid(WorldAccess world, BlockPos pos, BlockState state, FluidState fluidState) {
        return state.get(TYPE) != TableType.DOUBLE ? Waterloggable.super.tryFillWithFluid(world, pos, state, fluidState) : false;
    }

    @Override
    public boolean canFillWithFluid(@Nullable LivingEntity filler, BlockView world, BlockPos pos, BlockState state, Fluid fluid) {
        return state.get(TYPE) != TableType.DOUBLE ? Waterloggable.super.canFillWithFluid(filler, world, pos, state, fluid) : false;
    }

    @Override
    protected BlockState getStateForNeighborUpdate(
            BlockState state,
            WorldView world,
            ScheduledTickView tickView,
            BlockPos pos,
            Direction direction,
            BlockPos neighborPos,
            BlockState neighborState,
            Random random
    ) {
        if ((Boolean)state.get(WATERLOGGED)) {
            tickView.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }

        return super.getStateForNeighborUpdate(state, world, tickView, pos, direction, neighborPos, neighborState, random);
    }

    @Override
    protected boolean canPathfindThrough(BlockState state, NavigationType type) {
        switch (type) {
            case LAND:
                return false;
            case WATER:
                return state.getFluidState().isIn(FluidTags.WATER);
            case AIR:
                return false;
            default:
                return false;
        }
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        world.setBlockState(pos.north(3).east(), ModBlocks.SKELD_CAFETERIA_BENCH.getDefaultState());
        world.setBlockState(pos.north(), state);
        world.setBlockState(pos.south(), state);
        world.setBlockState(pos.east(), state);
        world.setBlockState(pos.west(), state);
        world.setBlockState(pos.north().east(), state);
        world.setBlockState(pos.north().west(), state);
        world.setBlockState(pos.south().east(), state);
        world.setBlockState(pos.south().west(), state);
        world.setBlockState(pos.north(2), state.with(TABLE_PART, TablePart.CENTER_EDGE).with(FACING, Direction.SOUTH));
        world.setBlockState(pos.south(2), state.with(TABLE_PART, TablePart.CENTER_EDGE).with(FACING, Direction.NORTH));
        world.setBlockState(pos.east(2), state.with(TABLE_PART, TablePart.CENTER_EDGE).with(FACING, Direction.WEST));
        world.setBlockState(pos.west(2), state.with(TABLE_PART, TablePart.CENTER_EDGE).with(FACING, Direction.EAST));
        world.setBlockState(pos.north(2).west(), state.with(TABLE_PART, TablePart.CENTER_EDGE_SIDE).with(FACING, Direction.SOUTH));
        world.setBlockState(pos.north(2).east(), state.with(TABLE_PART, TablePart.CENTER_EDGE_SIDE_MIRRORED).with(FACING, Direction.SOUTH));
        world.setBlockState(pos.south(2).east(), state.with(TABLE_PART, TablePart.CENTER_EDGE_SIDE).with(FACING, Direction.NORTH));
        world.setBlockState(pos.south(2).west(), state.with(TABLE_PART, TablePart.CENTER_EDGE_SIDE_MIRRORED).with(FACING, Direction.NORTH));
        world.setBlockState(pos.east(2).north(), state.with(TABLE_PART, TablePart.CENTER_EDGE_SIDE).with(FACING, Direction.WEST));
        world.setBlockState(pos.east(2).south(), state.with(TABLE_PART, TablePart.CENTER_EDGE_SIDE_MIRRORED).with(FACING, Direction.WEST));
        world.setBlockState(pos.west(2).south(), state.with(TABLE_PART, TablePart.CENTER_EDGE_SIDE).with(FACING, Direction.EAST));
        world.setBlockState(pos.west(2).north(), state.with(TABLE_PART, TablePart.CENTER_EDGE_SIDE_MIRRORED).with(FACING, Direction.EAST));
        world.setBlockState(pos.north(2).west(2), state.with(TABLE_PART, TablePart.CORNER).with(FACING, Direction.SOUTH));
        world.setBlockState(pos.north(2).east(2), state.with(TABLE_PART, TablePart.CORNER).with(FACING, Direction.WEST));
        world.setBlockState(pos.south(2).west(2), state.with(TABLE_PART, TablePart.CORNER).with(FACING, Direction.EAST));
        world.setBlockState(pos.south(2).east(2), state.with(TABLE_PART, TablePart.CORNER).with(FACING, Direction.NORTH));
    }
}
