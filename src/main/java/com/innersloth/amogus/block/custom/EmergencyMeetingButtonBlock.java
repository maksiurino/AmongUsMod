package com.innersloth.amogus.block.custom;

import com.innersloth.amogus.block.custom.enums.AmongUsMaps;
import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
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
import org.jetbrains.annotations.Nullable;

public class EmergencyMeetingButtonBlock extends TransparentHorizontalFacingBlock {
    public static final MapCodec<EmergencyMeetingButtonBlock> CODEC = createCodec(EmergencyMeetingButtonBlock::new);
    public static final BooleanProperty PRESSED = BooleanProperty.of("pressed");
    public static final EnumProperty<AmongUsMaps> CURRENT_MAP = EnumProperty.of("current_map", AmongUsMaps.class);
    private static final VoxelShape COLUMN_SHAPE = Block.createColumnShape(16.0, 0.0, 10.0);
    private static final VoxelShape BLOCK_SHAPE_UNPOWERED = VoxelShapes.combineAndSimplify(COLUMN_SHAPE, Block.createColumnShape(12.0, 10.0, 16.0), BooleanBiFunction.OR);
    private static final VoxelShape BLOCK_SHAPE_POWERED = VoxelShapes.combineAndSimplify(COLUMN_SHAPE, Block.createCuboidShape(2, 10, 14, 14, 22, 20), BooleanBiFunction.OR);

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
        builder.add(PRESSED, CURRENT_MAP, FACING);
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return state.get(PRESSED) ? BLOCK_SHAPE_POWERED : BLOCK_SHAPE_UNPOWERED;
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (!world.isClient) {
            world.setBlockState(pos, state.cycle(PRESSED));
        }
        world.playSound(player, pos, state.get(PRESSED) ? SoundEvents.BLOCK_STONE_BUTTON_CLICK_OFF : SoundEvents.BLOCK_STONE_BUTTON_CLICK_ON, SoundCategory.BLOCKS);
        return ActionResult.SUCCESS;
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing().getOpposite());
    }
}
