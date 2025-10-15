package com.innersloth.amogus.util;

import com.innersloth.amogus.block.custom.enums.TablePart;
import com.innersloth.amogus.block.custom.enums.TableType;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties.*;

public class Properties {
    public static final BooleanProperty WATERLOGGED = net.minecraft.state.property.Properties.WATERLOGGED;

    public static final EnumProperty<TableType> TABLE_TYPE = EnumProperty.of("type", TableType.class);
    public static final EnumProperty<TablePart> TABLE_PART = EnumProperty.of("table_part", TablePart.class);
}
