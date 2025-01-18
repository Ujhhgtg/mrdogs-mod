package dev.ujhhgtg.mrdogsmod.mixin.client;

import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityClientMixin extends LivingEntity {
    protected PlayerEntityClientMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Mutable
    @Final
    @Shadow
    public static EntityDimensions STANDING_DIMENSIONS;

    @Mutable
    @Final
    @Shadow
    public static float DEFAULT_EYE_HEIGHT;

    @Shadow @Final private static Logger LOGGER;

    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void clinit(CallbackInfo ci) {
        DEFAULT_EYE_HEIGHT = EntityType.WOLF.getDimensions().eyeHeight();
        STANDING_DIMENSIONS = EntityType.WOLF.getDimensions();
    }
}
