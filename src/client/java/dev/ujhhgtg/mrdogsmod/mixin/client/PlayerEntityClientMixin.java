package dev.ujhhgtg.mrdogsmod.mixin.client;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import dev.ujhhgtg.mrdogsmod.MrDogsConfigModel;
import net.minecraft.entity.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Map;

import static dev.ujhhgtg.mrdogsmod.MrDogsModClient.CONFIG;
import static dev.ujhhgtg.mrdogsmod.MrDogsModClient.MC;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityClientMixin extends LivingEntity {
    protected PlayerEntityClientMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @ModifyReturnValue(method = "getBaseDimensions", at = @At("RETURN"))
    private EntityDimensions getBaseDimensions(EntityDimensions original) {
        if (!shouldApply(this)) {
            return original;
        }

        if (CONFIG.morphedWolfEntityDimensionType() == MrDogsConfigModel.MorphedWolfEntityDimensionType.ORIGINAL) {
            return original.withEyeHeight(EntityType.WOLF.getDimensions().eyeHeight());
        }
        else if (CONFIG.morphedWolfEntityDimensionType() == MrDogsConfigModel.MorphedWolfEntityDimensionType.WOLF) {
            return EntityType.WOLF.getDimensions();
        }

        throw new IndexOutOfBoundsException("invalid config option in morphedWolfEntityDimensionType");
    }

    @Unique
    private static boolean shouldApply(PlayerEntityClientMixin player) {
        if (!CONFIG.morphToWolf()) {
            return false;
        }

        // noinspection EqualsBetweenInconvertibleTypes
        if (!player.equals(MC.player)) {
            return false;
        }

        return true;
    }
}
