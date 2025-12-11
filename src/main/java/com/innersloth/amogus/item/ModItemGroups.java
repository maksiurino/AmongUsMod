package com.innersloth.amogus.item;

import com.innersloth.amogus.block.ModBlocks;
import com.innersloth.amogus.util.ItemGroupBuilder;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

public class ModItemGroups {
    private static final List<ItemGroupBuilder> itemGroupBuilders = new ArrayList<>();

    public static void registerModItemGroups() {
        final ItemGroupBuilder AMONG_US_SKELD_BLOCKS = ItemGroupBuilder.createGroup("skeld_blocks_group", FabricItemGroup.builder()
                .icon(() -> new ItemStack(ModBlocks.SKELD_CAFETERIA_TILES.asItem()))
                .displayName(Text.translatable("itemGroup.among-us.skeld_blocks_group"))
                .build());
        itemGroupBuilders.add(AMONG_US_SKELD_BLOCKS);

        final ItemGroupBuilder AMONG_US_SKELD_UTILS = ItemGroupBuilder.createGroup("skeld_utils_group", FabricItemGroup.builder()
                .icon(() -> new ItemStack(ModBlocks.EMERGENCY_MEETING_BUTTON))
                .displayName(Text.translatable("itemGroup.among-us.skeld_utils_group"))
                .build());
        itemGroupBuilders.add(AMONG_US_SKELD_UTILS);

        for (ItemGroupBuilder builder : itemGroupBuilders)
            Registry.register(Registries.ITEM_GROUP, builder.key(), builder.group());

        ItemGroupEvents.modifyEntriesEvent(AMONG_US_SKELD_BLOCKS.key()).register(itemGroup -> {
            itemGroup.add(ModBlocks.SKELD_CAFETERIA_TILES.asItem());
        });

        ItemGroupEvents.modifyEntriesEvent(AMONG_US_SKELD_UTILS.key()).register(entries -> {
            entries.add(ModBlocks.EMERGENCY_MEETING_BUTTON.asItem());
            entries.add(ModBlocks.SKELD_CAFETERIA_TABLE.asItem());
            entries.add(ModBlocks.SKELD_CAFETERIA_BENCH.asItem());
        });
    }
}
