package dev.ujhhgtg.mrdogsmod.mixin.client;

import com.llamalad7.mixinextras.sugar.Local;
import dev.ujhhgtg.mrdogsmod.Utils;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ClickableWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

import static dev.ujhhgtg.mrdogsmod.MrDogsModClient.*;

@Mixin(ClickableWidget.class)
public abstract class ClickableWidgetClientMixin {
    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/widget/ClickableWidget;renderWidget(Lnet/minecraft/client/gui/DrawContext;IIF)V", shift = At.Shift.BEFORE))
    private void render(CallbackInfo ci, @Local(ordinal = 0, argsOnly = true) int mouseX, @Local(ordinal = 1, argsOnly = true) int mouseY) {
        if (SURRENDERED_FROM_ESCAPING_WIDGETS) {
            return;
        }

        if (!CONFIG.escapingWidgets()) {
            return;
        }

        // noinspection ConstantValue
        if (!((Object) this instanceof ButtonWidget widget)) {
            return;
        }

        if (widget.getMessage() == SURRENDER_TEXT) {
            return;
        }

        // powered by ChatGPT (TM)
        int ESCAPE_SPEED = 1;
        double THRESHOLD = 50.0;

        int windowWidth = MC.getWindow().getScaledWidth();
        int windowHeight = MC.getWindow().getScaledHeight();

        int buttonWidth = widget.getWidth();
        int buttonHeight = widget.getHeight();
        int buttonX = widget.getX();
        int buttonY = widget.getY();

        // Calculate the distance between mouse and widget center
        int buttonCenterX = buttonX + buttonWidth / 2;
        int buttonCenterY = buttonY + buttonHeight / 2;
        int deltaX = buttonCenterX - mouseX;
        int deltaY = buttonCenterY - mouseY;

        // Distance between the mouse and the widget's center
        double distance = Utils.getRectanglePointDistance(buttonX, buttonY, buttonX + buttonWidth, buttonY + buttonHeight, mouseX, mouseY);

        if (distance < THRESHOLD) {
            // Normalize the direction vector (deltaX, deltaY)
            double directionX = deltaX / distance;
            double directionY = deltaY / distance;

            // Calculate the new position of the widget
            int newButtonX = (int) (buttonX + directionX * ESCAPE_SPEED);
            int newButtonY = (int) (buttonY + directionY * ESCAPE_SPEED);

            // Handle teleportation when the widget reaches the border
            if (newButtonX > windowWidth) {
                newButtonX = 0; // Teleport to the left
            } else if (newButtonX + buttonWidth < 0) {
                newButtonX = windowWidth - buttonWidth; // Teleport to the right
            }

            if (newButtonY > windowHeight) {
                newButtonY = 0; // Teleport to the top
            } else if (newButtonY + buttonHeight < 0) {
                newButtonY = windowHeight - buttonHeight; // Teleport to the bottom
            }

            // Update the widget's position
            widget.setX(newButtonX);
            widget.setY(newButtonY);
        }
    }
}
