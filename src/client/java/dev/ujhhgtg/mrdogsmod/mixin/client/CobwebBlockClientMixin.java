package dev.ujhhgtg.mrdogsmod.mixin.client;

import dev.ujhhgtg.mrdogsmod.Utils;
import net.minecraft.block.BlockState;
import net.minecraft.block.CobwebBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static dev.ujhhgtg.mrdogsmod.MrDogsModClient.IS_IN_COBWEB;

@Mixin(CobwebBlock.class)
public abstract class CobwebBlockClientMixin {
    @Inject(method = "onEntityCollision", at = @At("HEAD"))
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity, CallbackInfo ci) {
        if (!(entity instanceof PlayerEntity player)) {
            return;
        }

        IS_IN_COBWEB = true;
        entity.setVelocityClient(0, 0, 0);
        player.setMovementSpeed(0);
        Utils.sendErrorOverlayToPlayer(player, Text.translatable("text.mrdogs-mod.stuck_in_cobweb"));
    }
}
