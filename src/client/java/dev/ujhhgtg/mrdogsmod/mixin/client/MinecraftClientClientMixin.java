package dev.ujhhgtg.mrdogsmod.mixin.client;

import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static dev.ujhhgtg.mrdogsmod.MrDogsModClient.IS_IN_COBWEB;

@Mixin(MinecraftClient.class)
public abstract class MinecraftClientClientMixin {
    // (i know i only need one reset method but i just want to make sure)
    @Inject(method = "joinWorld", at = @At("HEAD"))
    private void joinWorld(CallbackInfo ignoredCi) {
        // resets cobweb status when joining world
        IS_IN_COBWEB = false;
    }

    @Inject(method = "onDisconnected", at = @At("HEAD"))
    private void onDisconnected(CallbackInfo ignoredCi) {
        // resets cobweb status when disconnecting server
        IS_IN_COBWEB = false;
    }
}
