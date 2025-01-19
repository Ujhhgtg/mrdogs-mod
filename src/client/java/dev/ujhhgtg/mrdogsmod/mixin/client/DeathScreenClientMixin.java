package dev.ujhhgtg.mrdogsmod.mixin.client;

import com.llamalad7.mixinextras.sugar.Local;
import dev.ujhhgtg.mrdogsmod.Utils;
import net.minecraft.client.gui.screen.DeathScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

import static dev.ujhhgtg.mrdogsmod.MrDogsModClient.MC;

@Mixin(DeathScreen.class)
public abstract class DeathScreenClientMixin extends Screen {
    @Shadow
    @Final
    private List<ButtonWidget> buttons;

    @Shadow
    @Nullable
    private ButtonWidget titleScreenButton;

    @Shadow
    protected abstract void setButtonsActive(boolean active);

    @Shadow
    private Text scoreText;

    @Mutable
    @Shadow
    @Final
    private Text message;

    @Mutable
    @Shadow
    @Final
    private boolean isHardcore;

    @Shadow private int ticksSinceDeath;
    @Unique
    private boolean isFakeDeathScreen;

    protected DeathScreenClientMixin(Text title) {
        super(title);
    }

    @Inject(method = "<init>", at = @At("CTOR_HEAD"))
    private void ctorHead(CallbackInfo ci, @Local(argsOnly = true) Text message, @Local(argsOnly = true) boolean isHardcore) {
        if (message.getString().equals("###FAKE_DEATH_SCREEN###")) {
            this.isFakeDeathScreen = true;
        }
    }

    @Inject(method = "init", at = @At("HEAD"), cancellable = true)
    private void init(CallbackInfo ci) {
        if (!this.isFakeDeathScreen) {
            return;
        }

        this.ticksSinceDeath = 0;
        this.buttons.clear();
        this.isHardcore = false;
        this.message = Text.translatable("death.attack.badRespawnPoint.link");
        Text text = Text.translatable("deathScreen.respawn");
        this.buttons.add(this.addDrawableChild(ButtonWidget.builder(text, this::displayTrolledMessage).dimensions(this.width / 2 - 100, this.height / 4 + 72, 200, 20).build()));
        this.titleScreenButton = this.addDrawableChild(ButtonWidget.builder(Text.translatable("deathScreen.titleScreen"), this::displayTrolledMessage).dimensions(this.width / 2 - 100, this.height / 4 + 96, 200, 20).build());
        this.buttons.add(this.titleScreenButton);
        this.setButtonsActive(true);
        this.scoreText = Text.translatable("deathScreen.score.value", Text.literal("25555").formatted(Formatting.YELLOW));
        ci.cancel();
    }

    @Unique
    private void displayTrolledMessage(ButtonWidget button) {
        MC.setScreen(null);
        Utils.sendErrorOverlayToPlayer(MC.player, Text.translatable("text.mrdogs-mod.fake_death"));
        button.active = false;
    }
}
