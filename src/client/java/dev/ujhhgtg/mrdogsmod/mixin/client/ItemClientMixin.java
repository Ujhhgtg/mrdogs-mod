package dev.ujhhgtg.mrdogsmod.mixin.client;

import dev.ujhhgtg.mrdogsmod.Utils;
import net.fabricmc.fabric.api.item.v1.FabricItem;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.resource.featuretoggle.ToggleableFeature;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public abstract class ItemClientMixin implements ToggleableFeature, ItemConvertible, FabricItem {
	// replaces item name for foods (except cake)
	@Inject(method = "getName(Lnet/minecraft/item/ItemStack;)Lnet/minecraft/text/Text;", at = @At("HEAD"), cancellable = true)
	public void getName(ItemStack stack, CallbackInfoReturnable<Text> cir) {
        if (!Utils.isFood(stack)) {
            return;
        }

		String boneName = I18n.translate("item.minecraft.bone");
        cir.setReturnValue(Text.of(getRealItemStackName(stack).getString() + I18n.translate("text.mrdogs-mod.flavored") + boneName));
    }

	@Unique
	private Text getRealItemStackName(ItemStack stack) {
		return stack.getComponents().getOrDefault(DataComponentTypes.ITEM_NAME, ScreenTexts.EMPTY);
	}
}