package dev.ujhhgtg.mrdogsmod.interfaces;

import net.minecraft.client.render.entity.model.WolfEntityModel;

public interface IMixinWolfEntityModel {
    WolfEntityModel mrdogs_mod$getWolfEntityModel();
    void mrdogs_mod$setWolfEntityModel(WolfEntityModel wolfEntityModel);
}
