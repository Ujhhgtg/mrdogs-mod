package dev.ujhhgtg.mrdogsmod.mixin.client;

import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.feature.HeldItemFeatureRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.ModelWithArms;
import net.minecraft.client.render.entity.state.ArmedEntityRenderState;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(HeldItemFeatureRenderer.class)
public abstract class HeldItemFeatureRendererClientMixin<S extends ArmedEntityRenderState, M extends EntityModel<S> & ModelWithArms> extends FeatureRenderer<S, M> implements HeldItemFeatureRendererInvoker<S, M> {
    public HeldItemFeatureRendererClientMixin(FeatureRendererContext<S, M> context) {
        super(context);
    }
}
