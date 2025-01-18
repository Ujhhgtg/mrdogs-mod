package dev.ujhhgtg.mrdogsmod.mixin.client;

import com.github.promeg.pinyinhelper.Pinyin;
import net.minecraft.client.resource.language.TranslationStorage;
import net.minecraft.util.Language;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.WordUtils;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;

import static dev.ujhhgtg.mrdogsmod.MrDogsModClient.CONFIG;

@Mixin(TranslationStorage.class)
public abstract class TranslationStorageClientMixin extends Language {
    @Shadow
    @Final
    private Map<String, String> translations;

    // replaces item name for cake and spawn egg
    @Inject(method = "get", at = @At("RETURN"), cancellable = true)
    public void get(String key, String fallback, CallbackInfoReturnable<String> cir) {
        String boneName = this.translations.get("item.minecraft.bone");
        String cakeName = this.translations.get("block.minecraft.cake");

        if (key.equals("block.minecraft.cake")) {
            cir.setReturnValue(boneName + this.translations.get("text.mrdogs-mod.flavored") + cakeName);
        }

        String translated = cir.getReturnValue();

        if (!key.contains("mrdogs-")) {
            String wolfName = this.translations.get("entity.minecraft.wolf");
            translated = translated.replace(wolfName, CONFIG.customWolfName());
        }

        if (CONFIG.replaceChineseWithPinyin()) {
            translated = StringUtils.swapCase(WordUtils.capitalizeFully(Pinyin.toPinyin(translated, " ").toLowerCase())).replace("% ", "%");
        }

        cir.setReturnValue(translated);
    }
}
