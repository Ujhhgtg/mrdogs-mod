package dev.ujhhgtg.mrdogsmod.mixin.client;

import net.minecraft.client.render.WorldRenderer;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(WorldRenderer.class)
public abstract class WorldRendererClientMixin {
//    @Inject(method = "hasBlindnessOrDarkness(Lnet/minecraft/client/render/Camera;)Z", at = @At("HEAD"), cancellable = true)
//    private void hasBlindnessOrDarkness(Camera camera, CallbackInfoReturnable<Boolean> info) {
//
//    }
}
