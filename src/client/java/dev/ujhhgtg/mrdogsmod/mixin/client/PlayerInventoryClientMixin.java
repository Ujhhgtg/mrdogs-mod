package dev.ujhhgtg.mrdogsmod.mixin.client;

import dev.ujhhgtg.mrdogsmod.Utils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerInventory.class)
public abstract class PlayerInventoryClientMixin implements Inventory {
//    @Shadow
//    public int selectedSlot;

    @Final
    @Shadow
    public PlayerEntity player;

    @Inject(method = "swapStackWithHotbar", at = @At("HEAD"), cancellable = true)
    private void swapStackWithHotbar(ItemStack newStack, CallbackInfo ci) {
        if (Utils.isDisallowed(newStack)) {
            sendErrorMessage(player);
            ci.cancel();
        }
    }

//    @Inject(method = "swapSlotWithHotbar", at = @At("HEAD"), cancellable = true)
//    private void swapSlotWithHotbar(int newStackId, CallbackInfo ci) {
//        ItemStack newStack = getStack(newStackId);
//        if (isStackDisallowed(newStack)) {
//            sendErrorMessage(player);
//            ci.cancel();
//        }
//    }

    @Inject(method = "setSelectedSlot", at = @At("HEAD"), cancellable = true)
    public void setSelectedSlot(int newStackId, CallbackInfo ci) {
        ItemStack newStack = getStack(newStackId);
        if (Utils.isDisallowed(newStack)) {
            sendErrorMessage(player);
            ci.cancel();
        }
    }

    @Unique
    private void sendErrorMessage(@Nullable PlayerEntity player) {
        Utils.sendErrorOverlayToPlayer(player, "[Mr. Dog's Mod] 非高级物种没有使用工具的能力！");
    }
}
