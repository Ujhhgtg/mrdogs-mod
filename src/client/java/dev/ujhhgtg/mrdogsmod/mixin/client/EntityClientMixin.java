package dev.ujhhgtg.mrdogsmod.mixin.client;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import dev.ujhhgtg.mrdogsmod.MrDogsConfigModel;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.time.Duration;

import static dev.ujhhgtg.mrdogsmod.MrDogsModClient.*;

@Mixin(Entity.class)
public abstract class EntityClientMixin {
    @Shadow
    private float standingEyeHeight;

    @Shadow
    private EntityDimensions dimensions;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void ctor(CallbackInfo ci) {
        if (!CONFIG.morphToWolf()) {
            return;
        }

        // noinspection ConstantValue
        if (!((Object) this instanceof PlayerEntity player)) {
            return;
        }

        // FIXME: figure out a better way to accomplish this
        // player != MC.player because in this state MC.player has not been set (is null)
        Thread.startVirtualThread(() -> {
            while (MC.player == null) {
                try {
                    Thread.sleep(Duration.ofSeconds(1));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            if (!player.equals(MC.player)) {
                return;
            }

            if (CONFIG.morphedWolfEntityDimensionType() == MrDogsConfigModel.MorphedWolfEntityDimensionType.ORIGINAL) {
                this.dimensions = this.dimensions.withEyeHeight(EntityType.WOLF.getDimensions().eyeHeight());
            }
            else if (CONFIG.morphedWolfEntityDimensionType() == MrDogsConfigModel.MorphedWolfEntityDimensionType.WOLF) {
                this.dimensions = EntityType.WOLF.getDimensions();
            }

            this.standingEyeHeight = this.dimensions.eyeHeight();
        });
    }

    @ModifyReturnValue(method = "getCustomName", at = @At("RETURN"))
    public Text getCustomName(Text original) {
        if (!((Entity) (Object) this instanceof WolfEntity wolfEntity)) {
            return original;
        }

        if (!wolfEntity.isTamed())
            return Text.literal(I18n.translate("text.mrdogs-mod.lonely") + CONFIG.customWolfName()).formatted(Formatting.RED, Formatting.BOLD);
        else
            return Text.literal(CONFIG.customWolfName()).formatted(Formatting.GREEN, Formatting.BOLD);

    }

    @ModifyReturnValue(method = "hasCustomName", at = @At("RETURN"))
    public boolean hasCustomName(boolean original) {
        if (((Entity) (Object) this) instanceof WolfEntity) {
            return true;
        }

        return original;
    }

    // FIXME: these currently don't work because they're overridden by other classes
//    @Inject(method = "canUsePortals", at = @At("RETURN"), cancellable = true)
//    public void canUsePortals(CallbackInfoReturnable<Boolean> cir) {
//        if (!((Entity) (Object) this instanceof PlayerEntity player)) {
//            return;
//        }
//
//        if (!player.equals(MC.player)) {
//            return;
//        }
//
//        Utils.sendErrorOverlayToPlayer(player, Text.translatable("text.mrdogs-mod.no_using_portals"));
//        cir.setReturnValue(false);
//    }
//
//    @ModifyReturnValue(method = "getSafeFallDistance", at = @At("RETURN"))
//    public int getSafeFallDistance(int original) {
//        if (!((Entity) (Object) this instanceof PlayerEntity player)) {
//            return original;
//        }
//
//        if (!player.equals(MC.player)) {
//            return original;
//        }
//
//        return 1;
//    }
}
