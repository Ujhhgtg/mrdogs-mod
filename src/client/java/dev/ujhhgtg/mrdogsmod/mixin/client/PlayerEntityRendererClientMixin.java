package dev.ujhhgtg.mrdogsmod.mixin.client;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static dev.ujhhgtg.mrdogsmod.MrDogsModClient.CONFIG;

@Mixin(PlayerEntityRenderer.class)
public abstract class PlayerEntityRendererClientMixin extends LivingEntityRenderer<AbstractClientPlayerEntity, PlayerEntityRenderState, PlayerEntityModel> {
    public PlayerEntityRendererClientMixin(EntityRendererFactory.Context ctx, PlayerEntityModel model, float shadowRadius) {
        super(ctx, model, shadowRadius);
    }

    @Inject(method = "renderLeftArm", at = @At("HEAD"), cancellable = true)
    public void renderLeftArm(CallbackInfo ci) {
        if (CONFIG.morphToWolf()) {
            ci.cancel();
        }
    }

    @Inject(method = "renderRightArm", at = @At("HEAD"), cancellable = true)
    public void renderRightArm(CallbackInfo ci) {
        if (CONFIG.morphToWolf()) {
            ci.cancel();
        }
    }

    @ModifyReturnValue(method = "getTexture(Lnet/minecraft/client/render/entity/state/PlayerEntityRenderState;)Lnet/minecraft/util/Identifier;", at = @At("RETURN"))
    public Identifier getTexture(Identifier original) {
        if (!CONFIG.morphToWolf()) {
            return original;
        }

        return Identifier.ofVanilla("textures/entity/wolf/wolf_tame.png");
    }
}
