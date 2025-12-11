package com.innersloth.amogus;

import com.innersloth.amogus.block.ModBlocks;
import com.innersloth.amogus.config.AmongUsConfig;
import com.innersloth.amogus.entity.ModEntities;
import com.innersloth.amogus.item.ModItemGroups;
import com.innersloth.amogus.util.ModGameRules;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import net.fabricmc.api.ModInitializer;

import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AmongUs implements ModInitializer {
	public static final String MOD_ID = "among-us";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		AutoConfig.register(AmongUsConfig.class, JanksonConfigSerializer::new);
		ModGameRules.initialize();
		ModBlocks.initialize();
		ModEntities.registerModEntities();
		ModItemGroups.registerModItemGroups();
	}

	public static Identifier identifier(String name) {
		return Identifier.of(MOD_ID, name);
	}
}