package com.innersloth.amogus.block.custom;

import com.innersloth.amogus.block.custom.enums.AmongUsMaps;
import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class EmergencyMeetingButtonBlock extends TransparentHorizontalFacingBlock {
    public static final MapCodec<EmergencyMeetingButtonBlock> CODEC = createCodec(EmergencyMeetingButtonBlock::new);
    public static final BooleanProperty PRESSED = BooleanProperty.of("pressed");
    public static final EnumProperty<AmongUsMaps> CURRENT_MAP = EnumProperty.of("current_map", AmongUsMaps.class);
    private static final VoxelShape COLUMN_SHAPE = Block.createColumnShape(16.0, 0.0, 10.0);
    private static final VoxelShape BLOCK_SHAPE = VoxelShapes.combineAndSimplify(COLUMN_SHAPE, Block.createColumnShape(12.0, 10.0, 16.0), BooleanBiFunction.OR);

    @Override
    public MapCodec<? extends EmergencyMeetingButtonBlock> getCodec() {
        return CODEC;
    }

    public EmergencyMeetingButtonBlock(AbstractBlock.Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(PRESSED, false).with(CURRENT_MAP, AmongUsMaps.SKELD));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(PRESSED, CURRENT_MAP);
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return BLOCK_SHAPE;
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (!world.isClient) {
            world.setBlockState(pos, state.cycle(PRESSED));
        }
        return ActionResult.SUCCESS;
    }
}
