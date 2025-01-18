package dev.ujhhgtg.mrdogsmod.mixin.client;

import com.llamalad7.mixinextras.sugar.Local;
import dev.ujhhgtg.mrdogsmod.interfaces.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.*;
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

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import static dev.ujhhgtg.mrdogsmod.MrDogsModClient.CONFIG;

@Mixin(LivingEntityRenderer.class)
public abstract class LivingEntityRendererClientMixin<T extends LivingEntity, S extends LivingEntityRenderState, M extends EntityModel<? super S>> extends EntityRenderer<T, S> implements FeatureRendererContext<S, M>, IMixinEntityRendererFactoryContext, IMixinWolfEntityModel, IMixinWolfEntityRenderState, IMixinWolfEntityRenderer {
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
        if (!CONFIG.morphToWolf()) {
            return;
        }

        if ((Object) this instanceof WolfEntityRenderer) {
            return;
        }

        if (!((Object) this instanceof PlayerEntityRenderer)) {
            return;
        }

        this.mrdogs_mod$setEntityRendererFactoryContext(context);
        this.mrdogs_mod$setWolfEntityModel(new WolfEntityModel(this.mrdogs_mod$getEntityRendererFactoryContext().getPart(EntityModelLayers.WOLF)));
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
        this.mrdogs_mod$setWolfEntityRenderState(state);
        this.mrdogs_mod$setWolfEntityRenderer(new WolfEntityRenderer(this.mrdogs_mod$getEntityRendererFactoryContext()));
        this.wolfArmorFeatureRenderer = new WolfArmorFeatureRenderer(this.mrdogs_mod$getWolfEntityRenderer(), this.mrdogs_mod$getEntityRendererFactoryContext().getEntityModels(), this.mrdogs_mod$getEntityRendererFactoryContext().getEquipmentRenderer());
        this.wolfCollarFeatureRenderer = new WolfCollarFeatureRenderer(this.mrdogs_mod$getWolfEntityRenderer());
    }

    @Redirect(method = "render(Lnet/minecraft/client/render/entity/state/LivingEntityRenderState;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/render/entity/model/EntityModel;render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumer;III)V"))
    private void renderEntityModel(M instance, MatrixStack matrixStack, VertexConsumer vertexConsumer, int i, int j, int l, @Local(argsOnly = true) S livingEntityRenderState, @Local(argsOnly = true) VertexConsumerProvider vertexConsumerProvider) {
        if (!CONFIG.morphToWolf()) {
            instance.render(matrixStack, vertexConsumer, i, j, l);
            return;
        }

        if (!(instance instanceof PlayerEntityModel)) {
            instance.render(matrixStack, vertexConsumer, i, j, l);
            return;
        }

        this.mrdogs_mod$getWolfEntityModel().setAngles(this.mrdogs_mod$getWolfEntityRenderState());

        ((WolfEntityModelAccessor) this.mrdogs_mod$getWolfEntityModel()).getLeftFrontLeg().pitch = ((PlayerEntityModel) instance).leftLeg.pitch;
        ((WolfEntityModelAccessor) this.mrdogs_mod$getWolfEntityModel()).getLeftHindLeg().pitch = ((PlayerEntityModel) instance).leftLeg.pitch;
        ((WolfEntityModelAccessor) this.mrdogs_mod$getWolfEntityModel()).getRightFrontLeg().pitch = ((PlayerEntityModel) instance).rightLeg.pitch;
        ((WolfEntityModelAccessor) this.mrdogs_mod$getWolfEntityModel()).getRightHindLeg().pitch = ((PlayerEntityModel) instance).rightLeg.pitch;
        ((WolfEntityModelAccessor) this.mrdogs_mod$getWolfEntityModel()).getLeftFrontLeg().yaw = ((PlayerEntityModel) instance).leftLeg.yaw;
        ((WolfEntityModelAccessor) this.mrdogs_mod$getWolfEntityModel()).getLeftHindLeg().yaw = ((PlayerEntityModel) instance).leftLeg.yaw;
        ((WolfEntityModelAccessor) this.mrdogs_mod$getWolfEntityModel()).getRightFrontLeg().yaw = ((PlayerEntityModel) instance).rightLeg.yaw;
        ((WolfEntityModelAccessor) this.mrdogs_mod$getWolfEntityModel()).getRightHindLeg().yaw = ((PlayerEntityModel) instance).rightLeg.yaw;
        // i don't know how minecraft generates this value so i'm just taking a similar one
        ((WolfEntityModelAccessor) this.mrdogs_mod$getWolfEntityModel()).getTail().yaw = ((PlayerEntityModel) instance).leftLeg.pitch * 0.7F;
        ((WolfEntityModelAccessor) this.mrdogs_mod$getWolfEntityModel()).getHead().pitch = livingEntityRenderState.pitch * ((float)Math.PI / 180F);
        ((WolfEntityModelAccessor) this.mrdogs_mod$getWolfEntityModel()).getHead().yaw = livingEntityRenderState.yawDegrees * ((float)Math.PI / 180F);

        this.mrdogs_mod$getWolfEntityModel().render(matrixStack, vertexConsumer, i, j, l);
        this.wolfArmorFeatureRenderer.render(matrixStack, vertexConsumerProvider, i, this.mrdogs_mod$getWolfEntityRenderState(), this.mrdogs_mod$getWolfEntityRenderState().yawDegrees, this.mrdogs_mod$getWolfEntityRenderState().pitch);
        this.wolfCollarFeatureRenderer.render(matrixStack, vertexConsumerProvider, i, this.mrdogs_mod$getWolfEntityRenderState(), this.mrdogs_mod$getWolfEntityRenderState().yawDegrees, this.mrdogs_mod$getWolfEntityRenderState().pitch);
    }

    @Redirect(method = "render(Lnet/minecraft/client/render/entity/state/LivingEntityRenderState;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/render/entity/feature/FeatureRenderer;render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/client/render/entity/state/EntityRenderState;FF)V"))
    private void renderFeature(FeatureRenderer<S, M> instance, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, EntityRenderState state, float limbAngle, float limbDistance) {
        boolean isPlayerEntity = false;
        Type superclass = this.getClass().getGenericSuperclass();
        if (superclass instanceof ParameterizedType parameterized) {
            String typeName = parameterized.getActualTypeArguments()[0].getTypeName();
            if (typeName.equals("PlayerEntity")) {
                isPlayerEntity = true;
            }
        }

        if (!isPlayerEntity) {
            instance.render(matrices, vertexConsumers, light, (S) state, limbAngle, limbDistance);
        }
    }

    @Override
    public EntityRendererFactory.Context mrdogs_mod$getEntityRendererFactoryContext() {
        return entityRendererFactoryContext;
    }

    @Override
    public void mrdogs_mod$setEntityRendererFactoryContext(EntityRendererFactory.Context entityRendererFactoryContext) {
        this.entityRendererFactoryContext = entityRendererFactoryContext;
    }

    @Override
    public WolfEntityModel mrdogs_mod$getWolfEntityModel() {
        return wolfEntityModel;
    }

    @Override
    public void mrdogs_mod$setWolfEntityModel(WolfEntityModel wolfEntityModel) {
        this.wolfEntityModel = wolfEntityModel;
    }
    @Override
    public WolfEntityRenderState mrdogs_mod$getWolfEntityRenderState() {
        return this.wolfEntityRenderState;
    }

    @Override
    public void mrdogs_mod$setWolfEntityRenderState(WolfEntityRenderState wolfEntityRenderState) {
        this.wolfEntityRenderState = wolfEntityRenderState;
    }
    @Override
    public WolfEntityRenderer mrdogs_mod$getWolfEntityRenderer() {
        return this.wolfEntityRenderer;
    }

    @Override
    public void mrdogs_mod$setWolfEntityRenderer(WolfEntityRenderer renderer) {
        this.wolfEntityRenderer = renderer;
    }
}
