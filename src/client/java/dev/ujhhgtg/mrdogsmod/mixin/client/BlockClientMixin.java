package dev.ujhhgtg.mrdogsmod.mixin.client;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.item.ItemConvertible;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Block.class)
public abstract class BlockClientMixin extends AbstractBlock implements ItemConvertible {
    public BlockClientMixin(Settings settings) {
        super(settings);
    }

    // modifies blocks' slipperiness
//    @ModifyReturnValue(method = "getSlipperiness", at = @At("RETURN"))
//    public float getSlipperiness(float ignoredOriginal) {
//        return 1.1F;
//    }
}