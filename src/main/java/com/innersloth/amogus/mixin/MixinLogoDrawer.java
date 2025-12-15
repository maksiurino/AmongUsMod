package com.innersloth.amogus.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.LogoDrawer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(LogoDrawer.class)
public class MixinLogoDrawer {
    @Unique
    private MinecraftClient minecraft = MinecraftClient.getInstance();

    @ModifyConstant(
            method = "draw(Lnet/minecraft/client/gui/DrawContext;IFI)V",
            constant = @Constant(intValue = 128, ordinal = 0)
    )
    private int changeLogoDrawerX(int defaultValue) {
        return 1;
    }

    @ModifyConstant(
            method = "draw(Lnet/minecraft/client/gui/DrawContext;IFI)V",
            constant = @Constant(intValue = 64, ordinal = 1)
    )
    private int changeLogoDrawerX2(int defaultValue) {
        return -63;
    }

    @ModifyConstant(
            method = "draw(Lnet/minecraft/client/gui/DrawContext;IFI)V",
            constant = @Constant(intValue = 2)
    )
    private int changeLogoDrawerWidthX(int defaultValue) {
        return minecraft.currentScreen.width;
    }
}
