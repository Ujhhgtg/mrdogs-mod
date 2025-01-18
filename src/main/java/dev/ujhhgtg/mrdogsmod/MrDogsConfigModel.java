package dev.ujhhgtg.mrdogsmod;

import io.wispforest.owo.config.annotation.Config;
import io.wispforest.owo.config.annotation.Modmenu;

//import com.teamresourceful.resourcefulconfig.api.annotations.Config;
//import com.teamresourceful.resourcefulconfig.api.annotations.ConfigEntry;
//import com.teamresourceful.resourcefulconfig.api.annotations.ConfigOption;

@Modmenu(modId = "mrdogs-mod")
@Config(name = "mrdogs-config", wrapperName = "MrDogsConfig")
//@Config(value = "mrdogs-config")
public class MrDogsConfigModel {
//    @ConfigEntry(id = "blindnessActivationFrequency", translation = "text.config.mrdogs-config.option.blindnessActivationFrequency")
//    @ConfigOption.Range(min = 1, max = 60)
//    @ConfigOption.Slider
    public short blindnessActivationFrequency = 10;

//    @ConfigEntry(id = "morphToWolf", translation = "text.config.mrdogs-config.option.morphToWolf")
    public boolean morphToWolf = false;

//    @ConfigEntry(id = "customWolfName", translation = "text.config.mrdogs-config.option.customWolfName")
    public String customWolfName = "[REDACTED]";

//    @ConfigEntry(id = "randomizeWidgets", translation = "text.config.mrdogs-config.option.randomizeWidgets")
    public boolean randomizeWidgets = false;

//    @ConfigEntry(id = "removeCrashReport", translation = "text.config.mrdogs-config.option.removeCrashReport")
    public boolean removeCrashReport = true;

//    @ConfigEntry(id = "replaceChineseWithPinyin", translation = "text.config.mrdogs-config.option.replaceChineseWithPinyin")
    public boolean replaceChineseWithPinyin = false;

//    @ConfigEntry(id = "escapingWidgets", translation = "text.config.mrdogs-config.option.escapingWidgets")
    public boolean escapingWidgets = false;
}
