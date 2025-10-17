package com.innersloth.amogus.util;

import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.minecraft.world.GameRules;

public class ModGameRules {
    public static final GameRules.Key<GameRules.BooleanRule> SHOW_DISABLED_RUNNING_DIALOGUE =
            GameRuleRegistry.register("showDisabledRunningDialogue", GameRules.Category.CHAT, GameRuleFactory.createBooleanRule(false));

    public static void initialize() {}
}
