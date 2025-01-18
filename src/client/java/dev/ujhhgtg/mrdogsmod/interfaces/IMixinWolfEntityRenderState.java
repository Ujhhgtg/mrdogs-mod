package dev.ujhhgtg.mrdogsmod.interfaces;

import net.minecraft.client.render.entity.state.WolfEntityRenderState;

public interface IMixinWolfEntityRenderState {
    WolfEntityRenderState mrdogs_mod$getWolfEntityRenderState();
    void mrdogs_mod$setWolfEntityRenderState(WolfEntityRenderState wolfEntityRenderState);
}
