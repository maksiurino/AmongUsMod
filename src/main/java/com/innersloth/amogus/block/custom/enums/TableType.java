package com.innersloth.amogus.block.custom.enums;

import net.minecraft.util.StringIdentifiable;

public enum TableType implements StringIdentifiable {
    TOP("top"),
    BOTTOM("bottom"),
    DOUBLE("double");

    private final String name;

    private TableType(final String name) {
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
