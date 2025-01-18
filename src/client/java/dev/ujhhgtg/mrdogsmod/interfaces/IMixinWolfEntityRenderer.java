package dev.ujhhgtg.mrdogsmod.interfaces;

import net.minecraft.client.render.entity.WolfEntityRenderer;

public interface IMixinWolfEntityRenderer {
    WolfEntityRenderer mrdogs_mod$getWolfEntityRenderer();
    void mrdogs_mod$setWolfEntityRenderer(WolfEntityRenderer renderer);
}
