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
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static dev.ujhhgtg.mrdogsmod.MrDogsModClient.CONFIG;
import static dev.ujhhgtg.mrdogsmod.MrDogsModClient.MC;

@Mixin(PlayerEntityRenderer.class)
public abstract class PlayerEntityRendererClientMixin extends LivingEntityRenderer<AbstractClientPlayerEntity, PlayerEntityRenderState, PlayerEntityModel> implements EntityRendererAccessor<AbstractClientPlayerEntity, PlayerEntityRenderState> {
    public PlayerEntityRendererClientMixin(EntityRendererFactory.Context ctx, PlayerEntityModel model, float shadowRadius) {
        super(ctx, model, shadowRadius);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void init(CallbackInfo info) {
        this.features.clear();
    }

    @Inject(method = "renderArm", at = @At("HEAD"), cancellable = true)
    public void renderArm(CallbackInfo ci) {
        if (!shouldMorphToWolf()) {
            return;
        }

        ci.cancel();
    }

    @ModifyReturnValue(method = "getTexture(Lnet/minecraft/client/render/entity/state/PlayerEntityRenderState;)Lnet/minecraft/util/Identifier;", at = @At("RETURN"))
    public Identifier getTexture(Identifier original) {
        if (!shouldMorphToWolf()) {
            return original;
        }

        return Identifier.ofVanilla("textures/entity/wolf/wolf_tame.png");
    }

    @Unique
    private boolean shouldMorphToWolf() {
        if (MC.player == null) {
            return false;
        }

        return CONFIG.morphToWolf() && this.getState().name.equals(MC.player.getGameProfile().getName());
    }
}
