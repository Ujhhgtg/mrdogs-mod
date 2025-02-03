package dev.ujhhgtg.mrdogsmod.mixin.client;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.WolfEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.feature.WolfArmorFeatureRenderer;
import net.minecraft.client.render.entity.feature.WolfCollarFeatureRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.render.entity.model.WolfEntityModel;
import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import net.minecraft.client.render.entity.state.WolfEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static dev.ujhhgtg.mrdogsmod.MrDogsModClient.CONFIG;
import static dev.ujhhgtg.mrdogsmod.MrDogsModClient.IS_SNEAKING;
import static dev.ujhhgtg.mrdogsmod.MrDogsModClient.MC;

@Mixin(LivingEntityRenderer.class)
public abstract class LivingEntityRendererClientMixin<T extends LivingEntity, S extends LivingEntityRenderState, M extends EntityModel<? super S>> extends EntityRenderer<T, S> implements FeatureRendererContext<S, M>, EntityRendererAccessor<T, S> {
    @Unique
    private EntityRendererFactory.Context entityRendererFactoryContext;

    @Unique
    private WolfEntityModel wolfEntityModel;

    @Unique
    private WolfEntityRenderState wolfEntityRenderState;

    @Unique
    private WolfEntityRenderer wolfEntityRenderer;

    @Unique
    private WolfArmorFeatureRenderer wolfArmorFeatureRenderer;

    @Unique
    private WolfCollarFeatureRenderer wolfCollarFeatureRenderer;

    protected LivingEntityRendererClientMixin(EntityRendererFactory.Context context) {
        super(context);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void init(CallbackInfo info, @Local(argsOnly = true) EntityRendererFactory.Context context) {
        // we have to initialize these fields even if shouldMorphToWolf() == false
        // since this class may exist even if player isn't in world
//        if (!shouldMorphToWolf()) {
//            return;
//        }
        if ((Object) this instanceof WolfEntityRenderer) {
            return;
        }

        this.entityRendererFactoryContext = context;
        this.wolfEntityModel = new WolfEntityModel(this.entityRendererFactoryContext.getPart(EntityModelLayers.WOLF));
        WolfEntityRenderState state = new WolfEntityRenderState();
        state.angerTime = false;
        state.inSittingPose = false;
        state.tailAngle = ((float)Math.PI / 5F);
        state.begAnimationProgress = 0;
        state.shakeProgress = 0;
        state.texture = Identifier.ofVanilla("textures/entity/wolf/wolf_tame.png");
        state.furWetBrightnessMultiplier = 1.0F;
        state.collarColor = DyeColor.RED;
        state.bodyArmor = ItemStack.EMPTY;
        this.wolfEntityRenderState = state;
        this.wolfEntityRenderer = new WolfEntityRenderer(this.entityRendererFactoryContext);
        this.wolfArmorFeatureRenderer = new WolfArmorFeatureRenderer(this.wolfEntityRenderer, this.entityRendererFactoryContext.getEntityModels(), this.entityRendererFactoryContext.getEquipmentRenderer());
        this.wolfCollarFeatureRenderer = new WolfCollarFeatureRenderer(this.wolfEntityRenderer);
    }

    @Redirect(method = "render(Lnet/minecraft/client/render/entity/state/LivingEntityRenderState;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/render/entity/model/EntityModel;render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumer;III)V"))
    private void renderEntityModel(M instance, MatrixStack matrixStack, VertexConsumer vertexConsumer, int light, int overlay, int color, @Local(argsOnly = true) S livingEntityRenderState, @Local(argsOnly = true) VertexConsumerProvider vertexConsumerProvider) {
        if (!this.shouldMorphToWolf()) {
            instance.render(matrixStack, vertexConsumer, light, overlay, color);
            return;
        }

        this.wolfEntityRenderState.inSittingPose = IS_SNEAKING;
        this.wolfEntityModel.setAngles(this.wolfEntityRenderState);

        ((WolfEntityModelAccessor) this.wolfEntityModel).getLeftFrontLeg().pitch = ((PlayerEntityModel) instance).leftLeg.pitch;
        ((WolfEntityModelAccessor) this.wolfEntityModel).getLeftHindLeg().pitch = ((PlayerEntityModel) instance).leftLeg.pitch;
        ((WolfEntityModelAccessor) this.wolfEntityModel).getRightFrontLeg().pitch = ((PlayerEntityModel) instance).rightLeg.pitch;
        ((WolfEntityModelAccessor) this.wolfEntityModel).getRightHindLeg().pitch = ((PlayerEntityModel) instance).rightLeg.pitch;
        ((WolfEntityModelAccessor) this.wolfEntityModel).getLeftFrontLeg().yaw = ((PlayerEntityModel) instance).leftLeg.yaw;
        ((WolfEntityModelAccessor) this.wolfEntityModel).getLeftHindLeg().yaw = ((PlayerEntityModel) instance).leftLeg.yaw;
        ((WolfEntityModelAccessor) this.wolfEntityModel).getRightFrontLeg().yaw = ((PlayerEntityModel) instance).rightLeg.yaw;
        ((WolfEntityModelAccessor) this.wolfEntityModel).getRightHindLeg().yaw = ((PlayerEntityModel) instance).rightLeg.yaw;
        // i don't know how minecraft generates this value so i'm just taking a similar one
        ((WolfEntityModelAccessor) this.wolfEntityModel).getTail().yaw = ((PlayerEntityModel) instance).leftLeg.pitch * 0.7F;
        ((WolfEntityModelAccessor) this.wolfEntityModel).getHead().pitch = livingEntityRenderState.pitch * ((float)Math.PI / 180F);
        ((WolfEntityModelAccessor) this.wolfEntityModel).getHead().yaw = livingEntityRenderState.yawDegrees * ((float)Math.PI / 180F);
        ((WolfEntityModelAccessor) this.wolfEntityModel).getHead().roll = ((PlayerEntityModel) instance).head.roll;

        this.wolfEntityModel.render(matrixStack, vertexConsumer, light, overlay, color);
        this.wolfArmorFeatureRenderer.render(matrixStack, vertexConsumerProvider, light, this.wolfEntityRenderState, this.wolfEntityRenderState.yawDegrees, this.wolfEntityRenderState.pitch);
        this.wolfCollarFeatureRenderer.render(matrixStack, vertexConsumerProvider, light, this.wolfEntityRenderState, this.wolfEntityRenderState.yawDegrees, this.wolfEntityRenderState.pitch);
        /*
            data:  x_move = -0.03
            y_move = 0.23
            z_move = -0.15
            y_rot = 40

            -1.57 0.022
            0.45 0.04
            pitch
        */
        //        matrixStack.push();
//        matrixStack.translate(X_MOVE, Y_MOVE, Z_MOVE);
//        matrixStack.push();
//        matrixStack.multiply(RotationAxis.POSITIVE_X.rotation(((WolfEntityModelAccessor) this.wolfEntityModel).getHead().pitch));
//        LOGGER.info("X_MOVE = {}, Y_MOVE = {}, Z_MOVE = {}", X_MOVE, Y_MOVE, Z_MOVE);
//        LOGGER.info("X_ROT = {}, Y_ROT = {}, Z_ROT = {}", 0, Y_ROTATION, Z_ROTATION);
//        LOGGER.info("head.pitch = {}, head.yaw = {}, head.roll = {}", ((WolfEntityModelAccessor) this.wolfEntityModel).getHead().pitch, ((WolfEntityModelAccessor) this.wolfEntityModel).getHead().yaw, ((WolfEntityModelAccessor) this.wolfEntityModel).getHead().roll);
//        Utils.renderFoxHoldItem(matrixStack, vertexConsumerProvider, light, Items.BONE.getDefaultStack(), Z_ROTATION, 0, Y_ROTATION, ((WolfEntityModelAccessor) this.wolfEntityModel).getHead());
//        matrixStack.pop();
//        matrixStack.pop();
    }

    @Redirect(method = "render(Lnet/minecraft/client/render/entity/state/LivingEntityRenderState;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/render/entity/feature/FeatureRenderer;render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/client/render/entity/state/EntityRenderState;FF)V"))
    private void renderFeature(FeatureRenderer<S, M> instance, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, EntityRenderState state, float limbAngle, float limbDistance) {
        // we render the features ourselves (in renderEntityModel()) because some arguments need to be modified

        if (!shouldMorphToWolf()) {
            instance.render(matrices, vertexConsumers, light, (S) state, limbAngle, limbDistance);
        }
    }

    @Unique
    private boolean shouldMorphToWolf() {
        if (!CONFIG.morphToWolf()) {
            return false;
        }

        if (((Object) this) instanceof WolfEntityRenderer) {
            return false;
        }

        if (!(this.getState() instanceof PlayerEntityRenderState playerEntityRenderState)) {
            return false;
        }

        if (MC.player == null) {
            return false;
        }

        return playerEntityRenderState.name.equals(MC.player.getGameProfile().getName());
    }
}
