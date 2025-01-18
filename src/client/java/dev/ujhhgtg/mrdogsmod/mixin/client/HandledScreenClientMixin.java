package dev.ujhhgtg.mrdogsmod.mixin.client;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import dev.ujhhgtg.mrdogsmod.Utils;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.ScreenHandlerProvider;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(HandledScreen.class)
public abstract class HandledScreenClientMixin<T extends ScreenHandler> extends Screen implements ScreenHandlerProvider<T> {
    protected HandledScreenClientMixin(Text title) {
        super(title);
    }

    @Shadow
    public abstract T getScreenHandler();

    @ModifyExpressionValue(
            method = "drawSlot",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/screen/slot/Slot;getStack()Lnet/minecraft/item/ItemStack;")
    )
    private ItemStack drawSlot(ItemStack original) {
        if (original != null) {
            if (Utils.isFood(original)) {
                original.set(DataComponentTypes.ITEM_MODEL, Identifier.of("minecraft:bone"));
            }
        }

        return original;
    }
}