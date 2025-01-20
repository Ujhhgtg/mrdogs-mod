package dev.ujhhgtg.mrdogsmod.mixin.client;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.world.LevelLoadingScreen;
import net.minecraft.text.Text;
import org.apache.commons.lang3.RandomUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import static dev.ujhhgtg.mrdogsmod.MrDogsModClient.RANDOM;

@Mixin(LevelLoadingScreen.class)
public abstract class LevelLoadingScreenClientMixin extends Screen {
    protected LevelLoadingScreenClientMixin(Text title) {
        super(title);
    }

    @Redirect(method = "getPercentage", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/util/math/MathHelper;clamp(III)I",
            shift = At.Shift.AFTER))
    private Integer getPercentage(int intPercentage) {
        return RANDOM.nextInt(0, 10001);
    }
}
