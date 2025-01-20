package dev.ujhhgtg.mrdogsmod;

import com.github.promeg.pinyinhelper.Pinyin;
import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.client.MinecraftClient;
import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.util.Random;

public class MrDogsModClient implements ClientModInitializer {
    public static final String MOD_ID = "mrdogs-mod";

    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID + "-client");

    public static boolean IS_IN_COBWEB = false;

    public static boolean IS_SNEAKING = false;

    public static boolean SURRENDERED_FROM_ESCAPING_WIDGETS = false;

    public static final Text SURRENDER_TEXT = Text.translatable("text.mrdogs-mod.surrender");

    // TODO: TESTING - REMOVE!
//    public static float Y_ROTATION = 40f;
//
//    public static float X_ROTATION = 0.0f;
//
//    public static float Z_ROTATION = 0.0f;
//
//    public static float X_MOVE = -0.03f;
//
//    public static float Y_MOVE = 0.23f;
//
//    public static float Z_MOVE = -0.15f;

    public static MinecraftClient MC;

    public static final dev.ujhhgtg.mrdogsmod.MrDogsConfig CONFIG = dev.ujhhgtg.mrdogsmod.MrDogsConfig.createAndLoad();

    public static final URI TROLL_URI = URI.create(CONFIG.trollUri());

    public static final Random RANDOM = new Random();

    @Override
    public void onInitializeClient() {
        LOGGER.info("Initializing Mr. Dog's Mod client mod");
        MC = MinecraftClient.getInstance();

        LOGGER.info("Initializing TinyPinyin");
        Pinyin.init(Pinyin.newConfig());

        LOGGER.info("1 - Prevent attacking & taming dogs");
        AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
            if (entity instanceof WolfEntity) {
                if (!player.equals(MC.player)) {
                    return ActionResult.PASS;
                }

                Utils.sendErrorOverlayToPlayer(player, Text.translatable("text.mrdogs-mod.no_attacking_wolves"));
                return ActionResult.FAIL;
            }

            if (entity instanceof SheepEntity) {
                if (!player.equals(MC.player)) {
                    return ActionResult.PASS;
                }

                if (MC.world == null) {
                    return ActionResult.PASS;
                }

                MC.world.addParticle(ParticleTypes.HEART, player.getX(), player.getY() + player.getHeight(), player.getZ(), 0D, 0D, 0D);
                Utils.sendErrorOverlayToPlayer(player, Text.translatable("text.mrdogs-mod.no_attacking_sheep"));
                return ActionResult.FAIL;
            }

            return ActionResult.PASS;
        });
        UseEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
            if (!(entity instanceof WolfEntity)) {
                return ActionResult.PASS;
            }

            if (!player.equals(MC.player)) {
                return ActionResult.PASS;
            }

            if (!player.getMainHandStack().getItem().toString().equals("minecraft:bone")) {
                return ActionResult.PASS;
            }

            Utils.sendErrorOverlayToPlayer(player, Text.translatable("text.mrdogs-mod.no_taming_wolves"));
            return ActionResult.FAIL;
        });

        LOGGER.info("2 - Modify foods' names");
        // done in ItemClientMixin, ItemStackClientMixin, TranslationStorageClientMixin

        LOGGER.info("3 - Modify blocks' slipperiness");
        // done in BlockClientMixin

        LOGGER.info("4 - Hide crash report");
        // done in CrashReportClientMixin

        LOGGER.info("5 - Improve cobweb");
        AttackBlockCallback.EVENT.register((player, world, hand, blockPos, direction) -> {
            if (!player.equals(MC.player)) {
                return ActionResult.PASS;
            }

            if (!IS_IN_COBWEB) {
                return ActionResult.PASS;
            }

            return ActionResult.FAIL;
        });
        AttackEntityCallback.EVENT.register((player, world, hand, entity, entityHitResult) -> {
            if (!player.equals(MC.player)) {
                return ActionResult.PASS;
            }

            if (!IS_IN_COBWEB) {
                return ActionResult.PASS;
            }

            return ActionResult.FAIL;
        });
        UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
            if (!player.equals(MC.player)) {
                return ActionResult.PASS;
            }

            if (!IS_IN_COBWEB) {
                return ActionResult.PASS;
            }

            return ActionResult.FAIL;
        });
        UseEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
            if (!player.equals(MC.player)) {
                return ActionResult.PASS;
            }

            if (!IS_IN_COBWEB) {
                return ActionResult.PASS;
            }

            return ActionResult.FAIL;
        });
        // others done in CobwebBlockClientMixin

        LOGGER.info("6 - Random blindness effect");
        // done in LivingEntityClientMixin

        LOGGER.info("7 - Register config screen command");
//        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
//            dispatcher.register(
//                    ClientCommandManager.literal("mrdogsmod_config")
//                            .executes(context -> {
////                                        MinecraftClient client = context.getSource().getClient();
////                                    client.execute(() -> client.setScreen(ConfigScreen.create(CONFIG, client.currentScreen)));
//                                        context.getSource().sendFeedback(Text.literal("invoked mrdogsmod_config"));
//                                        return 1;
//                                    }
//                            ));
//
//            dispatcher.register(
//                    ClientCommandManager.literal("mrdogsmod_fakedeath")
//                            .executes(context -> {
//                                MinecraftClient client = context.getSource().getClient();
//                                client.execute(() -> client.setScreen(new DeathScreen(Text.literal("###FAKE_DEATH_SCREEN###"), false)));
//                                context.getSource().sendFeedback(Text.literal("invoked mrdogsmod_fakedeath"));
//                                return 1;
//                            })
//            );
//        });
    }
}