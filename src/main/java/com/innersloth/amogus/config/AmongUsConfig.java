package com.innersloth.amogus.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "among-us")
public class AmongUsConfig implements ConfigData {
    @ConfigEntry.ColorPicker
    int player_color = 0xFFFFFF;
}
