package dev.ujhhgtg.mrdogsmod.mixin.client;

import net.minecraft.client.render.item.ItemRenderer;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ItemRenderer.class)
public abstract class ItemRendererClientMixin {
//    @Inject(method = "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/item/ModelTransformationMode;II" +
//            "Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;Lnet/minecraft/world/World;I)V", at = @At("HEAD"))
//    private void renderItem1(CallbackInfo ci, @Local ItemStack stack) {
//        LOGGER.info("called renderItem1()");
//
//        if (Utils.isFood(stack) || Utils.isPotion(stack)) {
//            stack.set(DataComponentTypes.ITEM_MODEL, Identifier.ofVanilla("item/bone"));
//        }
//    }
//
//    @Inject(method = "(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/item/ItemStack;Lnet/minecraft/item/ModelTransformationMode;" +
//            "ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;Lnet/minecraft/world/World;III)V", at = @At("HEAD"))
//    private void renderItem2(CallbackInfo ci, @Local ItemStack stack) {
//        LOGGER.info("called renderItem2()");
//
//        if (Utils.isFood(stack) || Utils.isPotion(stack)) {
//            stack.set(DataComponentTypes.ITEM_MODEL, Identifier.ofVanilla("item/bone"));
//        }
//    }
}
