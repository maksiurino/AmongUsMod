package com.innersloth.amogus.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;

public class FloorBlock extends Block {
    public static final MapCodec<FloorBlock> CODEC = createCodec(FloorBlock::new);

    @Override
    public MapCodec<? extends FloorBlock> getCodec() {
        return CODEC;
    }

    public FloorBlock(AbstractBlock.Settings settings) {
        super(settings);
    }
}
