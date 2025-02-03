package dev.ujhhgtg.mrdogsmod.mixin.client;

import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.MutableWorldProperties;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.time.Instant;
import java.util.function.BooleanSupplier;

import static dev.ujhhgtg.mrdogsmod.MrDogsModClient.CONFIG;
import static dev.ujhhgtg.mrdogsmod.MrDogsModClient.MC;

@Mixin(ClientWorld.class)
public abstract class ClientWorldClientMixin extends World {
    @Shadow
    public abstract void playSound(@Nullable PlayerEntity source, double x, double y, double z, RegistryEntry<SoundEvent> sound, SoundCategory category, float volume, float pitch, long seed);

    protected ClientWorldClientMixin(MutableWorldProperties properties, RegistryKey<World> registryRef, DynamicRegistryManager registryManager, RegistryEntry<DimensionType> dimensionEntry, boolean isClient, boolean debugWorld, long seed, int maxChainedNeighborUpdates) {
        super(properties, registryRef, registryManager, dimensionEntry, isClient, debugWorld, seed, maxChainedNeighborUpdates);
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void tick(CallbackInfo ci) {
        if (!shouldSkip()) {
            return;
        }

        assert MC.player != null;
        this.playSound(MC.player, MC.player.getBlockPos(), SoundEvents.AMBIENT_CAVE.value(), SoundCategory.AMBIENT);
    }

    @Unique
    private static boolean shouldSkip() {
        if (!CONFIG.randomEffectSound()) {
            return true;
        }

        if (CONFIG.randomEffectActivationInterval() < 0) {
            return true;
        }

        if (MC.player == null) {
            return true;
        }

        if (CONFIG.randomEffectActivationInterval() == 0) {
            return false;
        }

        if (Instant.now().getEpochSecond() % CONFIG.randomEffectActivationInterval() != 0) {
            return true;
        }

        return false;
    }
}
