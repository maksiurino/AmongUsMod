package com.innersloth.amogus.entity;

import com.innersloth.amogus.AmongUs;
import com.innersloth.amogus.entity.custom.ChairEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class ModEntities {
    public static final EntityType<ChairEntity> CHAIR = Registry.register(Registries.ENTITY_TYPE,
            Identifier.of(AmongUs.MOD_ID, "chair_entity"),
            EntityType.Builder.create(ChairEntity::new, SpawnGroup.MISC)
                    .dimensions(0.5f, 0.5f).build(RegistryKey.of(RegistryKeys.ENTITY_TYPE, Identifier.of(AmongUs.MOD_ID, "chair_entity"))));

    public static void registerModEntities() {
        AmongUs.LOGGER.info("Registering Mod Entities for " + AmongUs.MOD_ID);
    }
}
