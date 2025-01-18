package dev.ujhhgtg.mrdogsmod.interfaces;

import net.minecraft.client.render.entity.EntityRendererFactory;

public interface IMixinEntityRendererFactoryContext {
    EntityRendererFactory.Context mrdogs_mod$getEntityRendererFactoryContext();
    void mrdogs_mod$setEntityRendererFactoryContext(EntityRendererFactory.Context entityRendererFactoryContext);
}
