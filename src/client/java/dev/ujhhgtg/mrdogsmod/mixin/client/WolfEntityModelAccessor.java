package dev.ujhhgtg.mrdogsmod.mixin.client;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.WolfEntityModel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(WolfEntityModel.class)
public interface WolfEntityModelAccessor {
    @Accessor
    ModelPart getHead();
    @Accessor
    ModelPart getRealHead();
    @Accessor
    ModelPart getTorso();
    @Accessor
    ModelPart getRightHindLeg();
    @Accessor
    ModelPart getLeftHindLeg();
    @Accessor
    ModelPart getRightFrontLeg();
    @Accessor
    ModelPart getLeftFrontLeg();
    @Accessor
    ModelPart getTail();
    @Accessor
    ModelPart getRealTail();
    @Accessor
    ModelPart getNeck();
}
