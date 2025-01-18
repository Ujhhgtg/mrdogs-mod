package dev.ujhhgtg.mrdogsmod.mixin.client;

import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static dev.ujhhgtg.mrdogsmod.MrDogsModClient.CONFIG;

@Mixin(ChatScreen.class)
public abstract class ChatScreenClientMixin extends Screen {
    protected ChatScreenClientMixin(Text title) {
        super(title);
    }

    @Inject(method = "insertText", at = @At("HEAD"))
    protected void insertText(String text, boolean override, CallbackInfo ci) {
        if (!CONFIG.morphToWolf()) {
            return;
        }

        // noinspection UnusedAssignment
        text = I18n.translate("text.mrdogs-mod.woof").repeat(text.length()).trim();
    }
}
