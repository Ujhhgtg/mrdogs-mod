package dev.ujhhgtg.mrdogsmod.mixin.client;

import net.minecraft.client.gui.LogoDrawer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LogoDrawer.class)
public abstract class LogoDrawerClientMixin {
    @SuppressWarnings("SpellCheckingInspection")
    @Mutable
    @Shadow
    @Final
    private boolean minceraft;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void init(CallbackInfo ci) {
        this.minceraft = true;
    }
}
