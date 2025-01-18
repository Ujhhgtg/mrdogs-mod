package dev.ujhhgtg.mrdogsmod.mixin.client;

import net.minecraft.client.gui.screen.SplashTextRenderer;
import net.minecraft.client.resource.SplashTextResourceSupplier;
import net.minecraft.client.resource.language.I18n;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SplashTextResourceSupplier.class)
public class SplashTextResourceSupplierClientMixin {
    @Inject(method = "get", at = @At("HEAD"), cancellable = true)
    public void get(CallbackInfoReturnable<SplashTextRenderer> cir) {
        // FIXME: splash text is not re-translated on language change
        cir.setReturnValue(new SplashTextRenderer(I18n.translate("text.mrdogs-mod.splash_text")));
    }
}
