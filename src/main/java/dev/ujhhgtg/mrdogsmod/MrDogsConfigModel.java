package dev.ujhhgtg.mrdogsmod;

import io.wispforest.owo.config.annotation.Config;
import io.wispforest.owo.config.annotation.Modmenu;
import io.wispforest.owo.config.annotation.SectionHeader;

@Modmenu(modId = "mrdogs-mod")
@Config(name = "mrdogs-config", wrapperName = "MrDogsConfig")
public class MrDogsConfigModel {
    public short randomEffectActivationInterval = 10;
    public boolean randomEffectBlindnessEffect = true;
    public boolean randomEffectSound = true;
    public boolean morphToWolf = false;
    public String customWolfName = "[REDACTED]";
    public boolean randomizeWidgets = false;
    public boolean removeCrashReport = true;
    public boolean replaceChineseWithPinyin = false;
    public boolean escapingWidgets = false;
    public String trollUri = "https://vdse.bdstatic.com/192d9a98d782d9c74c96f09db9378d93.mp4";
    public MorphedWolfEntityDimensionType morphedWolfEntityDimensionType = MorphedWolfEntityDimensionType.ORIGINAL;
    @SectionHeader("debug")
    public boolean logScreenClassName = false;

    public enum MorphedWolfEntityDimensionType {
        ORIGINAL,
        WOLF
    }
}
