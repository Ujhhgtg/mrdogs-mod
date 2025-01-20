package dev.ujhhgtg.mrdogsmod.mixin.client;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
import net.minecraft.client.gui.screen.ConfirmLinkScreen;
import net.minecraft.client.gui.screen.ConfirmScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Util;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.net.URI;

import static dev.ujhhgtg.mrdogsmod.MrDogsModClient.TROLL_URI;

@Mixin(ConfirmLinkScreen.class)
public abstract class ConfirmLinkScreenClientMixin extends ConfirmScreen {
    public ConfirmLinkScreenClientMixin(BooleanConsumer callback, Text title, Text message) {
        super(callback, title, message);
    }

    public ConfirmLinkScreenClientMixin(BooleanConsumer callback, Text title, Text message, Text yesText, Text noText) {
        super(callback, title, message, yesText, noText);
    }

    @ModifyReturnValue(method = "opening(Lnet/minecraft/client/gui/screen/Screen;Ljava/net/URI;Z)Lnet/minecraft/client/gui/widget/ButtonWidget$PressAction;", at = @At("RETURN"))
    private static ButtonWidget.PressAction openingOpenURI(ButtonWidget.PressAction original, @Local(argsOnly = true) URI uri) {
        if (uri != TROLL_URI) {
            return original;
        }

        return (button) -> Util.getOperatingSystem().open(uri);
    }
}
