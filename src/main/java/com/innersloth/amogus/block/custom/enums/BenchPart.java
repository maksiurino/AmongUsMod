package com.innersloth.amogus.block.custom.enums;

import net.minecraft.util.StringIdentifiable;

public enum BenchPart implements StringIdentifiable {
    EDGES("edges"),
    EDGE_SIDES("edge_sides"),
    EDGE_SIDES_MIRRORED("edge_sides_mirrored"),
    EDGE_CORNERS("edge_corners"),
    EDGE_CORNERS_MIRRORED("edge_corners_mirrored"),
    EDGE_TURNABLE("edge_turnable");

    private final String name;

    private BenchPart(final String name) {
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
