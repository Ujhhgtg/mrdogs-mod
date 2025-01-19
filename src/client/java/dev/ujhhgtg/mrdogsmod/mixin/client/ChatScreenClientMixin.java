package dev.ujhhgtg.mrdogsmod.mixin.client;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import static dev.ujhhgtg.mrdogsmod.MrDogsModClient.CONFIG;

@Mixin(ChatScreen.class)
public abstract class ChatScreenClientMixin extends Screen {
    protected ChatScreenClientMixin(Text title) {
        super(title);
    }

    @ModifyArg(method = "sendMessage", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/network/ClientPlayNetworkHandler;sendChatMessage(Ljava/lang/String;)V"))
    protected String sendMessage(String content) {
        if (!CONFIG.morphToWolf()) {
            return content;
        }

        return I18n.translate("text.mrdogs-mod.woof").repeat(content.length()).trim();
    }
}
