package dev.ujhhgtg.mrdogsmod.commands;

import com.mojang.brigadier.context.CommandContext;
import io.wispforest.owo.config.ui.ConfigScreen;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.ServerCommandSource;

import static dev.ujhhgtg.mrdogsmod.MrDogsModClient.CONFIG;
import static dev.ujhhgtg.mrdogsmod.MrDogsModClient.MC;
import static net.minecraft.server.command.CommandManager.literal;

public class MrDogsCommand {
    public static void init() {
        CommandRegistrationCallback.EVENT.register(((dispatcher, registryAccess, environment) -> {
            dispatcher.register(literal("mrdogs-mod")
                    .then(literal("config")
                            .executes(MrDogsCommand::commandConfig)
                    )
            );
        }));
    }

    private static int commandConfig(CommandContext<ServerCommandSource> context) {
        MC.execute(() -> MC.setScreen(ConfigScreen.create(CONFIG, MC.currentScreen)));
        return 0;
    }
}
