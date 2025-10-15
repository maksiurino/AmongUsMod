package com.innersloth.amogus.item;

import com.innersloth.amogus.AmongUs;
import com.innersloth.amogus.block.ModBlocks;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups {
    public static final RegistryKey<ItemGroup> AMONG_US_SKELD_GROUP_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), Identifier.of(AmongUs.MOD_ID, "among_us_skeld_group"));
    public static final ItemGroup AMONG_US_SKELD_GROUP = FabricItemGroup.builder()
            .icon(() -> new ItemStack(ModBlocks.SKELD_CAFETERIA_TABLE.asItem()))
            .displayName(Text.translatable("itemGroup.among-us.skeld_group"))
            .build();

    public static void registerModItemGroups() {
        Registry.register(Registries.ITEM_GROUP, AMONG_US_SKELD_GROUP_KEY, AMONG_US_SKELD_GROUP);

        ItemGroupEvents.modifyEntriesEvent(AMONG_US_SKELD_GROUP_KEY).register(itemGroup -> {
            itemGroup.add(ModBlocks.SKELD_CAFETERIA_TILES.asItem());
            itemGroup.add(ModBlocks.SKELD_CAFETERIA_TABLE.asItem());
            itemGroup.add(ModBlocks.EMERGENCY_MEETING_BUTTON.asItem());
        });
    }
}
