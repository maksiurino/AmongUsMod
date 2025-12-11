package com.innersloth.amogus.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextIconButtonWidget;
import net.minecraft.client.resource.language.I18n;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;
import java.util.List;

@Mixin(TitleScreen.class)
public class MixinTitleScreen {
    @Unique
    private MinecraftClient minecraft = MinecraftClient.getInstance();

    @Inject(method = "init", at = @At("TAIL"))
    private void onInit(CallbackInfo ci) {
        List<Drawable> d = ((ScreenAccessor) this).getDrawables();
        d.removeIf(w ->
                w instanceof TextIconButtonWidget btn &&
                        ((ButtonWidgetAccessor) btn).onPress() instanceof ButtonWidget.PressAction press
        );

        List<Object> c = ((ScreenAccessor) this).getChildren();
        c.removeIf(w ->
                w instanceof TextIconButtonWidget btn &&
                        ((ButtonWidgetAccessor) btn).onPress() instanceof ButtonWidget.PressAction press
        );

        List<Object> s = ((ScreenAccessor) this).getSelectables();
        s.removeIf(w ->
                w instanceof TextIconButtonWidget btn &&
                        ((ButtonWidgetAccessor) btn).onPress() instanceof ButtonWidget.PressAction press
        );
    }

    @ModifyConstant(
            method = "init",
            constant = @Constant(intValue = 20, ordinal = 1)
    )
    private int modifyOptionsHeight(int original) {
        return 15;
    }

    @ModifyConstant(
            method = "init",
            constant = @Constant(intValue = 20, ordinal = 2)
    )
    private int modifyQuitButtonHeight(int original) {
        return 12;
    }



    @ModifyConstant(
            method = "addNormalWidgets",
            constant = @Constant(intValue = 200, ordinal = 0)
    )
    private int modifySingleplayerWidth(int original) {
        return 150;
    }

    @ModifyConstant(
            method = "addNormalWidgets",
            constant = @Constant(intValue = 100, ordinal = 0)
    )
    private int modifySingleplayerXValue(int original) {
        return 1;
    }

    @ModifyConstant(
            method = "addNormalWidgets",
            constant = @Constant(intValue = 2, ordinal = 0)
    )
    private int modifySingleplayerXWidthValue(int original) {
        return minecraft.currentScreen.width;
    }



    @ModifyConstant(
            method = "addNormalWidgets",
            constant = @Constant(intValue = 200, ordinal = 1)
    )
    private int modifyMultiplayerWidth(int original) {
        return 150;
    }

    @ModifyConstant(
            method = "addNormalWidgets",
            constant = @Constant(intValue = 100, ordinal = 1)
    )
    private int modifyMultiplayerXValue(int original) {
        return 1;
    }

    @ModifyConstant(
            method = "addNormalWidgets",
            constant = @Constant(intValue = 2, ordinal = 1)
    )
    private int modifyMultiplayerXWidthValue(int original) {
        return minecraft.currentScreen.width;
    }



    @ModifyConstant(
            method = "addNormalWidgets",
            constant = @Constant(intValue = 2, ordinal = 2)
    )
    private int modifyRealmsXWidthValue(int original) {
        return minecraft.currentScreen.width;
    }

    @ModifyConstant(
            method = "addNormalWidgets",
            constant = @Constant(intValue = 200, ordinal = 2)
    )
    private int modifyRealmsWidth(int original) {
        return 150;
    }

    @ModifyConstant(
            method = "addNormalWidgets",
            constant = @Constant(intValue = 100, ordinal = 2)
    )
    private int modifyRealmsXValue(int original) {
        return 1;
    }
}
