package com.innersloth.amogus.block.custom.enums;

import net.minecraft.util.StringIdentifiable;

public enum AmongUsMaps implements StringIdentifiable {
    SKELD("skeld"),
    MIRA_HQ("mira_hq"),
    POLUS("polus"),
    THE_AIRSHIP("the_airship"),
    THE_FUNGLE("the_fungle");

    private final String name;

    private AmongUsMaps(final String name) {
        this.name = name;
    }

    public String toString() {
        return this.name;
    }

    @Override
    public String asString() {
        return this.name;
    }
}
