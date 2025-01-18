package dev.ujhhgtg.mrdogsmod.mixin.client;

import dev.ujhhgtg.mrdogsmod.Utils;
import io.wispforest.owo.config.ui.ConfigScreen;
import net.minecraft.client.gui.AbstractParentElement;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.screen.DeathScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

import static dev.ujhhgtg.mrdogsmod.MrDogsModClient.*;

@Mixin(Screen.class)
public abstract class ScreenClientMixin extends AbstractParentElement implements Drawable {
    @Final
    @Shadow
    private List<Element> children;

    @Shadow
    protected abstract <T extends Element & Drawable & Selectable> T addDrawableChild(T drawableElement);

    @Shadow
    protected abstract void refreshWidgetPositions();

    @Inject(method = "addDrawableChild", at = @At("TAIL"))
    private <T extends Element & Drawable & Selectable> void onAddDrawableChild(T drawableElement, CallbackInfoReturnable<T> cir) {
        if (!CONFIG.randomizeWidgets()) {
            return;
        }

        if (!(drawableElement instanceof Widget widget)) {
            return;
        }

        Utils.randomizeWidgetLocation(widget);
    }

    @Inject(method = "addDrawable", at = @At("TAIL"))
    private <T extends Drawable> void addDrawable(T drawable, CallbackInfoReturnable<T> cir) {
        if (!CONFIG.randomizeWidgets()) {
            return;
        }

        if (!(drawable instanceof Widget widget)) {
            return;
        }

        Utils.randomizeWidgetLocation(widget);
    }

    @Inject(method = "addSelectableChild", at = @At("TAIL"))
    private <T extends Element & Selectable> void addSelectableChild(T child, CallbackInfoReturnable<T> cir) {
        if (!CONFIG.randomizeWidgets()) {
            return;
        }

        if (!(child instanceof Widget widget)) {
            return;
        }

        Utils.randomizeWidgetLocation(widget);
    }

    @Inject(method = "init*", at = @At("TAIL"))
    private void init(CallbackInfo ci) {
        if (CONFIG.escapingWidgets() && !SURRENDERED_FROM_ESCAPING_WIDGETS) {
            this.addDrawableChild(
                    new ButtonWidget.Builder(SURRENDER_TEXT,
                            button -> {
                                SURRENDERED_FROM_ESCAPING_WIDGETS = true;
                                this.refreshWidgetPositions();
                                button.remove();
                            })
                            .position(5, 5)
                            .size(40, 20)
                            .build()
            );
        }

        if (CONFIG.randomizeWidgets()) {
            for (Element child : children) {
                if (child instanceof Widget widget) {
                    Utils.randomizeWidgetLocation(widget);
                }
            }
        }

        // noinspection ConstantValue
        if (!((Object) this instanceof DeathScreen)) {
            this.addDrawableChild(new ButtonWidget.Builder(Text.literal("Show Fake Death"), button ->
                    MC.execute(() -> MC.setScreen(new DeathScreen(Text.literal("###FAKE_DEATH_SCREEN###"), false))))
                    .position(5, 35)
                    .size(120, 20)
                    .build());
        }

        // noinspection ConstantValue
        if (!((Object) this instanceof ConfigScreen)) {
            this.addDrawableChild(new ButtonWidget.Builder(Text.literal("Show Config Screen"), button ->
                    MC.execute(() -> MC.setScreen(ConfigScreen.create(CONFIG, MC.currentScreen))))
                    .position(5, 65)
                    .size(120, 20)
                    .build());
        }
    }
}
