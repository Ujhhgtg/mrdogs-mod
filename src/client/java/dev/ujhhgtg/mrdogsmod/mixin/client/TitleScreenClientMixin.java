package dev.ujhhgtg.mrdogsmod.mixin.client;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TitleScreen.class)
public abstract class TitleScreenClientMixin extends Screen {
    protected TitleScreenClientMixin(Text title) {
        super(title);
    }

    @Inject(method = "addNormalWidgets", at = @At("HEAD"))
    private void addNormalWidgets(CallbackInfoReturnable<Integer> cir) {
        if (this.client == null) {
            return;
        }

        this.client.options.skipMultiplayerWarning = false;
    }
}
