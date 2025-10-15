package com.innersloth.amogus.block;

import com.innersloth.amogus.AmongUs;
import com.innersloth.amogus.block.custom.EmergencyMeetingButtonBlock;
import com.innersloth.amogus.block.custom.TableBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.Blocks;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

import java.util.function.Function;

public class ModBlocks {
    public static final Block SKELD_CAFETERIA_TILES = register(
            "skeld_cafeteria_tiles",
            Block::new,
            AbstractBlock.Settings.create().sounds(BlockSoundGroup.STONE),
            true
    );
    public static final Block SKELD_CAFETERIA_TABLE = register(
            "skeld_cafeteria_table",
            TableBlock::new,
            AbstractBlock.Settings.create().sounds(BlockSoundGroup.STONE).nonOpaque(),
            true
    );
    public static final Block EMERGENCY_MEETING_BUTTON = register(
            "emergency_meeting_button",
            EmergencyMeetingButtonBlock::new,
            AbstractBlock.Settings.create().sounds(BlockSoundGroup.IRON).nonOpaque(),
            true
    );

    public static void initialize() {}

    private static Block register(String name, Function<AbstractBlock.Settings, Block> blockFactory, AbstractBlock.Settings settings, boolean shouldRegisterItem) {
        // Create a registry key for the block
        RegistryKey<Block> blockKey = keyOfBlock(name);
        // Create the block instance
        Block block = blockFactory.apply(settings.registryKey(blockKey));

        // Sometimes, you may not want to register an item for the block.
        // Eg: if it's a technical block like `minecraft:moving_piston` or `minecraft:end_gateway`
        if (shouldRegisterItem) {
            // Items need to be registered with a different type of registry key, but the ID
            // can be the same.
            RegistryKey<Item> itemKey = keyOfItem(name);

            BlockItem blockItem = new BlockItem(block, new Item.Settings().registryKey(itemKey).useBlockPrefixedTranslationKey());
            Registry.register(Registries.ITEM, itemKey, blockItem);
        }

        return Registry.register(Registries.BLOCK, blockKey, block);
    }

    private static RegistryKey<Block> keyOfBlock(String name) {
        return RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(AmongUs.MOD_ID, name));
    }

    private static RegistryKey<Item> keyOfItem(String name) {
        return RegistryKey.of(RegistryKeys.ITEM, Identifier.of(AmongUs.MOD_ID, name));
    }
}
