package dev.ujhhgtg.mrdogsmod.mixin.client;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.entry.RegistryEntry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.time.Instant;

import static dev.ujhhgtg.mrdogsmod.MrDogsModClient.CONFIG;
import static dev.ujhhgtg.mrdogsmod.MrDogsModClient.MC;

@Mixin(LivingEntity.class)
public abstract class LivingEntityClientMixin {
    @Inject(method = "hasStatusEffect", at = @At("HEAD"), cancellable = true)
    public void hasStatusEffect(RegistryEntry<StatusEffect> effect, CallbackInfoReturnable<Boolean> cir) {
        if (shouldSkip(effect)) {
            return;
        }

        // happen every <value> seconds
        cir.setReturnValue(true);
    }

    @Inject(method = "getStatusEffect", at = @At("HEAD"), cancellable = true)
    public void getStatusEffect(RegistryEntry<StatusEffect> effect, CallbackInfoReturnable<StatusEffectInstance> cir) {
        if (shouldSkip(effect)) {
            return;
        }

        // happen every <value> seconds
        // -1 is infinite in duration
        cir.setReturnValue(new StatusEffectInstance(StatusEffects.BLINDNESS, -1, 1, false, false, false));
    }

    @Unique
    private boolean shouldSkip(RegistryEntry<StatusEffect> effect) {
        if (!CONFIG.randomEffectBlindnessEffect()) {
            return true;
        }

        if (CONFIG.randomEffectActivationInterval() < 0) {
            return true;
        }
        else if (CONFIG.randomEffectActivationInterval() == 0) {
            return false;
        }

        if (!((Entity) (Object) this instanceof PlayerEntity player)) {
            return true;
        }

        if (!player.equals(MC.player)) {
            return true;
        }

        if (effect != StatusEffects.BLINDNESS) {
            return true;
        }

        if (Instant.now().getEpochSecond() % CONFIG.randomEffectActivationInterval() != 0) {
            return true;
        }

        return false;
    }

    // FIXME: this is server-sided
//    @Inject(method = "tryUseDeathProtector", at = @At("HEAD"), cancellable = true)
//    private void tryUseDeathProtector(CallbackInfoReturnable<Boolean> cir) {
//        cir.setReturnValue(false);
//    }
}
