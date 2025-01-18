package dev.ujhhgtg.mrdogsmod;

import net.minecraft.client.MinecraftClient;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MrDogsMod implements ModInitializer {
    public static final String MOD_ID = "mrdogs-mod";

    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static boolean IS_IN_COBWEB = false;

    public static MinecraftClient MC = null;

    @Override
    public void onInitialize() {
        LOGGER.info("Initializing Mr. Dog's Mod");
        MC = MinecraftClient.getInstance();
    }
}