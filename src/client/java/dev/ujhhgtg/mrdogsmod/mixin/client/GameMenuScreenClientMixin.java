package dev.ujhhgtg.mrdogsmod.mixin.client;

import com.mojang.datafixers.util.Either;
import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.server.ServerLinks;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.net.URI;
import java.util.Arrays;

import static dev.ujhhgtg.mrdogsmod.MrDogsModClient.CONFIG;
import static dev.ujhhgtg.mrdogsmod.MrDogsModClient.TROLL_URI;

@Mixin(GameMenuScreen.class)
public class GameMenuScreenClientMixin extends Screen {
    protected GameMenuScreenClientMixin(Text title) {
        super(title);
    }

    @Redirect(method = "initWidgets", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/network/ClientPlayNetworkHandler;getServerLinks()Lnet/minecraft/server/ServerLinks;"))
    private ServerLinks initWidgetsGetServerLinks(ClientPlayNetworkHandler instance) {
        ServerLinks.Known[] sites = new ServerLinks.Known[] { ServerLinks.Known.WEBSITE, ServerLinks.Known.ANNOUNCEMENTS, ServerLinks.Known
                .FEEDBACK, ServerLinks.Known.NEWS, ServerLinks.Known.FORUMS };
        return new ServerLinks(Arrays.stream(sites).map(site -> new ServerLinks.Entry(Either.left(site), TROLL_URI)).toList());
    }
}
