package dev.ujhhgtg.mrdogsmod.mixin.client;

import io.wispforest.owo.config.ui.ConfigScreen;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.screen.DeathScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Arrays;

import static dev.ujhhgtg.mrdogsmod.MrDogsModClient.CONFIG;
import static dev.ujhhgtg.mrdogsmod.MrDogsModClient.MC;

@Mixin(ChatScreen.class)
public abstract class ChatScreenClientMixin extends Screen {
    @Shadow
    protected abstract void insertText(String text, boolean override);

    protected ChatScreenClientMixin(Text title) {
        super(title);
    }

    @ModifyArg(method = "sendMessage", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/network/ClientPlayNetworkHandler;sendChatMessage(Ljava/lang/String;)V"))
    protected String sendMessageSendChatMessage(String original) {
        if (!CONFIG.morphToWolf()) {
            return original;
        }

        return I18n.translate("text.mrdogs-mod.woof").repeat(original.length()).trim();
    }

//    @Inject(method = "sendMessage", at = @At(
//            value = "INVOKE",
//            target = "Lnet/minecraft/client/gui/screen/ChatScreen;normalize(Ljava/lang/String;)Ljava/lang/String;",
//            shift = At.Shift.AFTER), cancellable = true)
//    protected void sendMessageHandleModCommand(String chatText, boolean addToHistory, CallbackInfo ci) {
//        if (!chatText.startsWith("/")) {
//            return;
//        }
//
//        String[] parts = chatText.split(" ");
//        String command = parts[0];
//
//        if (!command.equals("mrdogs-mod")) {
//            return;
//        }
//
//        String[] args = Arrays.copyOfRange(parts, 1, parts.length);
//
//        if (args[0].equals("config")) {
//            if (this.guardArgumentLength(args, 1)) {
//                MC.setScreen(ConfigScreen.create(CONFIG, MC.currentScreen));
//            }
//        }
//        else if (args[0].equals("fake_death")) {
//            if (this.guardArgumentLength(args, 1)) {
//                MC.setScreen(new DeathScreen(Text.literal("###FAKE_DEATH_SCREEN###"), false));
//            }
//        }
//        else {
//            this.logToChat("Invalid argument!");
//        }
//
//        ci.cancel();
//    }

//    @Unique
//    private void logToChat(String content) {
//        this.insertText("[Mr. Dog's Mod] " + content, false);
//    }
//
//    @Unique
//    private boolean guardArgumentLength(String[] args, int count) {
//        if (args.length == count) {
//            return true;
//        }
//
//        this.logToChat("Invalid argument!");
//        return false;
//    }
//
//    @Unique
//    private void clearChat() {
//        this.insertText("", true);
//    }
}
