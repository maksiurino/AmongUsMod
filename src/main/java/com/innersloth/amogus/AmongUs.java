package com.innersloth.amogus;

import com.innersloth.amogus.block.ModBlocks;
import com.innersloth.amogus.item.ModItemGroups;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AmongUs implements ModInitializer {
	public static final String MOD_ID = "among-us";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("Hello Fabric world!");
		ModBlocks.initialize();
		ModItemGroups.registerModItemGroups();
	}
}