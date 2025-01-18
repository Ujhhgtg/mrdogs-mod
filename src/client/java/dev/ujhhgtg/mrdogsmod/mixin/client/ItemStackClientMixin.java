package dev.ujhhgtg.mrdogsmod.mixin.client;

import dev.ujhhgtg.mrdogsmod.Utils;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ItemStack.class)
public abstract class ItemStackClientMixin {
    @Shadow
    public abstract Item getItem();

    // replaces item id for foods (except cake), potions and wolf-related items
    @Redirect(method = "getTooltip", at = @At(value = "INVOKE", target = "Lnet/minecraft/text/Text;literal(Ljava/lang/String;)Lnet/minecraft/text/MutableText;"))
    private MutableText getTooltip(String literal) {
        String namespace = literal.split(":")[0];
        String originalPath = literal.split(":")[1];

        originalPath = originalPath.replace("wolf", "mr_dog");

        if (!Utils.isFood(this.getItem()) && !Utils.isFood(this.getItem())) {
            return Text.literal(namespace + ":" + originalPath);
        }

        String bonePath = "bone";
        return Text.literal(namespace + ":" + originalPath + "_flavored_" + bonePath).formatted(Formatting.DARK_GRAY);
    }
}