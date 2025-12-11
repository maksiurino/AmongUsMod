package com.innersloth.amogus.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.resource.language.I18n;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(TitleScreen.class)
public class MixinTitleScreen {
    @Unique
    private MinecraftClient minecraft = MinecraftClient.getInstance();

    @Inject(method = "init", at = @At("TAIL"))
    private void initVoid(CallbackInfo ci) {
        List<Drawable> d = ((ScreenAccessor) this).getDrawables();
        d.removeIf(w ->
                w instanceof ButtonWidget btn &&
                        ((ButtonWidgetAccessor)btn).onPress() instanceof ButtonWidget.PressAction press &&
                        (btn.getMessage().getString().equals(I18n.translate("menu.online")))
        );

        List<Object> c = ((ScreenAccessor) this).getChildren();
        c.removeIf(w ->
                w instanceof ButtonWidget btn &&
                        ((ButtonWidgetAccessor)btn).onPress() instanceof ButtonWidget.PressAction press &&
                        (btn.getMessage().getString().equals(I18n.translate("menu.online")))
        );

        List<Object> s = ((ScreenAccessor) this).getSelectables();
        s.removeIf(w ->
                w instanceof ButtonWidget btn &&
                        ((ButtonWidgetAccessor)btn).onPress() instanceof ButtonWidget.PressAction press &&
                        (btn.getMessage().getString().equals(I18n.translate("menu.online")))
        );
    }
}
