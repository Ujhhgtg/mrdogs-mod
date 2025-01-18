package dev.ujhhgtg.mrdogsmod.mixin.client;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import static dev.ujhhgtg.mrdogsmod.MrDogsModClient.CONFIG;

@Mixin(EntityRenderer.class)
public abstract class EntityRendererClientMixin<T extends Entity, S extends EntityRenderState> {
    @ModifyReturnValue(method = "getDisplayName", at = @At("RETURN"))
    private Text getDisplayName(Text original, @Local(argsOnly = true) Entity entity) {
        if (entity instanceof WolfEntity wolfEntity) {
            if (!wolfEntity.isTamed())
                return Text.literal(I18n.translate("text.mrdogs-mod.lonely") + CONFIG.customWolfName()).formatted(Formatting.RED, Formatting.BOLD);
            else
                return Text.literal(CONFIG.customWolfName()).formatted(Formatting.GREEN, Formatting.BOLD);
        }

        return original;
    }
}