package dev.ujhhgtg.mrdogsmod.mixin.client;

import net.minecraft.client.input.Input;
import net.minecraft.client.input.KeyboardInput;
import net.minecraft.util.PlayerInput;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static dev.ujhhgtg.mrdogsmod.MrDogsModClient.*;

@Mixin(KeyboardInput.class)
public abstract class KeyboardInputClientMixin extends Input {
    @Inject(method = "tick", at = @At("TAIL"))
    private void tick(CallbackInfo ignoredCi) {
        if (IS_IN_COBWEB) {
            this.playerInput = new PlayerInput(false, false, false, false, false, false, false);
            this.movementForward = 0;
            this.movementSideways = 0;
            return;
        }

        // stop moving if sitting
        if (IS_SNEAKING) {
//            this.playerInput = new PlayerInput(false, false, false, false, false, this.playerInput.sneak(), false);
            this.movementForward = 0;
            this.movementSideways = 0;
        }

        if (MC.player != null && !MC.player.getAbilities().flying && !MC.player.isRiding()) {
            IS_SNEAKING = this.playerInput.sneak();
            this.playerInput = new PlayerInput(this.playerInput.forward(), this.playerInput.backward(), this.playerInput.left(), this.playerInput.right(), this.playerInput.jump(), false, this.playerInput.sprint());
        }
    }
}
