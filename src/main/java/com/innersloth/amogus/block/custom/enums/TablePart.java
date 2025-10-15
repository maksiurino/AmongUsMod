package com.innersloth.amogus.block.custom.enums;

import net.minecraft.util.StringIdentifiable;

public enum TablePart implements StringIdentifiable {
    CENTER("center"),
    CENTER_EDGE("center_edge"),
    CENTER_EDGE_SIDE("center_edge_side"),
    CENTER_EDGE_SIDE_MIRRORED("center_edge_side_mirrored"),
    CORNER("corner");

    private final String name;

    private TablePart(final String name) {
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
