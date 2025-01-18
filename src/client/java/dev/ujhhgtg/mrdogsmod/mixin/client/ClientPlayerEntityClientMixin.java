package dev.ujhhgtg.mrdogsmod.mixin.client;

import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.authlib.GameProfile;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

import static dev.ujhhgtg.mrdogsmod.MrDogsModClient.MC;

@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerEntityClientMixin extends AbstractClientPlayerEntity {
    public ClientPlayerEntityClientMixin(ClientWorld world, GameProfile profile) {
        super(world, profile);
    }

    @Inject(method = "playSound", at = @At("HEAD"))
    private void playSound(CallbackInfo ci, @Local(argsOnly = true) SoundEvent sound, @Local(ordinal = 0, argsOnly = true) float volume, @Local(ordinal = 1, argsOnly = true) float pitch) {
        if (this.isSilent()) {
            return;
        }

        if ((PlayerEntity) this != MC.player) {
            return;
        }

        String soundId = sound.id().toString();

        if (soundId.contains("player.player.breath")) {
            soundId = "player.wolf.ambient";
        }
        else if (soundId.contains("player.player.death")) {
            soundId = "player.wolf.death";
        }
        else if (soundId.contains("player.player.hurt")) {
            soundId = "player.wolf.hurt";
        }

        if (soundId != null) {
            this.getWorld().playSound(null, this.getX(), this.getY(), this.getZ(),
                    new SoundEvent(Identifier.of(soundId),
                            Optional.empty()), SoundCategory.AMBIENT, volume, pitch);
        }
    }
}
