package com.innersloth.amogus.util;

import com.innersloth.amogus.AmongUs;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;

public record ItemGroupBuilder(ItemGroup group, RegistryKey<ItemGroup> key) {
    public static ItemGroupBuilder createGroup(String id, ItemGroup itemGroup) {
        RegistryKey<ItemGroup> groupKey = RegistryKey.of(Registries.ITEM_GROUP.getKey(), Identifier.of(AmongUs.MOD_ID, id));

        return new ItemGroupBuilder(itemGroup, groupKey);
    }
}
