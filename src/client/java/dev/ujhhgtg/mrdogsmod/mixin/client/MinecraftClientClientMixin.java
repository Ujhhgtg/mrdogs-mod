package dev.ujhhgtg.mrdogsmod.mixin.client;

import dev.ujhhgtg.mrdogsmod.Utils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.DeathScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static dev.ujhhgtg.mrdogsmod.MrDogsModClient.IS_IN_COBWEB;
import static dev.ujhhgtg.mrdogsmod.MrDogsModClient.MC;

@Mixin(MinecraftClient.class)
public abstract class MinecraftClientClientMixin {
    // (i know i only need one reset method but i just want to make sure)

    @Inject(method = "joinWorld", at = @At("HEAD"))
    private void joinWorld(CallbackInfo ignoredCi) {
        MC.execute(() -> MC.setScreen(new DeathScreen(Text.literal("###FAKE_DEATH_SCREEN###"), false)));

        // resets cobweb status when joining world
        IS_IN_COBWEB = false;
    }

    @Inject(method = "onDisconnected", at = @At("HEAD"))
    private void onDisconnected(CallbackInfo ignoredCi) {
        // resets cobweb status when disconnecting server
        IS_IN_COBWEB = false;
    }

    @Redirect(method = "handleInputEvents", at = @At(
            value = "FIELD",
            target = "Lnet/minecraft/entity/player/PlayerInventory;selectedSlot:I"))
    private void handleInputEventsPlayerInventorySelectedSlot(PlayerInventory inventory, int slot) {
        ItemStack itemStack = inventory.getStack(slot);

        if (!Utils.isTool(itemStack)) {
            inventory.selectedSlot = slot;
            return;
        }

        sendErrorMessage(inventory.player);
    }

    @Redirect(method = "handleInputEvents", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/gui/screen/Screen;hasControlDown()Z"))
    private boolean handleInputEventsScreenHasControlDown() {
        return !Screen.hasControlDown();
    }

    @Unique
    private static void sendErrorMessage(@Nullable PlayerEntity player) {
        Utils.sendErrorOverlayToPlayer(player, "[Mr. Dog's Mod] 非高级物种没有使用工具的能力！");
    }
}
