package com.innersloth.amogus.block.custom;

import com.innersloth.amogus.block.custom.enums.AmongUsMaps;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

public class EmergencyMeetingButtonBlock extends TransparentBlock {
    public static final MapCodec<EmergencyMeetingButtonBlock> CODEC = createCodec(EmergencyMeetingButtonBlock::new);
    public static final BooleanProperty PRESSED = BooleanProperty.of("pressed");
    public static final EnumProperty<AmongUsMaps> CURRENT_MAP = EnumProperty.of("current_map", AmongUsMaps.class);
    private static final VoxelShape BLOCK_SHAPE = Block.createColumnShape(16.0, 0.0, 16.0);

    private final int pressTicks;

    @Override
    public MapCodec<? extends EmergencyMeetingButtonBlock> getCodec() {
        return CODEC;
    }

    public EmergencyMeetingButtonBlock(AbstractBlock.Settings settings) {
        super(settings);
        this.pressTicks = 20;
        this.setDefaultState(this.getDefaultState().with(PRESSED, true).with(CURRENT_MAP, AmongUsMaps.SKELD));
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
        if ((Boolean) state.get(PRESSED)) {
            return ActionResult.CONSUME;
        } else {
            this.powerOn(state, world, pos, player);
            return ActionResult.SUCCESS;
        }
    }

    public void powerOn(BlockState state, World world, BlockPos pos, @Nullable PlayerEntity player) {
        world.setBlockState(pos, state.with(PRESSED, true), Block.NOTIFY_ALL);
        world.scheduleBlockTick(pos, this, this.pressTicks);
        this.playClickSound(player, world, pos, true);
        world.emitGameEvent(player, GameEvent.BLOCK_ACTIVATE, pos);
    }

    protected void playClickSound(@Nullable PlayerEntity player, WorldAccess world, BlockPos pos, boolean powered) {
        world.playSound(powered ? player : null, pos, this.getClickSound(powered), SoundCategory.BLOCKS);
    }

    protected SoundEvent getClickSound(boolean powered) {
        return powered ? BlockSetType.OAK.buttonClickOn() : BlockSetType.OAK.buttonClickOff();
    }
}
