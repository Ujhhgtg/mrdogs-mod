package dev.ujhhgtg.mrdogsmod;

import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import static dev.ujhhgtg.mrdogsmod.MrDogsModClient.*;

public class Utils {
    public static boolean isDisallowed(Item item) {
        return (item instanceof AxeItem)
                || (item instanceof PickaxeItem)
                || (item instanceof ShovelItem)
                || (item instanceof HoeItem)
                || (item instanceof SwordItem)
                || (item instanceof BowItem)
                || (item instanceof CrossbowItem)
                || (item instanceof FlintAndSteelItem); 
    }

    public static boolean isDisallowed(ItemStack stack) {
        return isDisallowed(stack.getItem());
    }

    public static boolean isFood(Item item) {
        return item.getComponents().contains(DataComponentTypes.FOOD) || Registries.ITEM.getEntry(item).getIdAsString().equals("minecraft:cake");
    }

    public static boolean isFood(ItemStack stack) {
        return isFood(stack.getItem());
    }

    public static boolean isPotion(Item item) {
        return item instanceof PotionItem;
    }

    public static boolean isPotion(ItemStack stack) {
        return isPotion(stack.getItem());
    }

    public static void randomizeWidgetLocation(Widget widget) {
        if (widget == null) {
            return;
        }

        if (!(widget instanceof ButtonWidget)) {
            return;
        }

        // sometimes the second argument of nextInt() is smaller than 0 but i'm not going to try to figure out why
        widget.setX(RANDOM.nextInt(0, Math.max(MC.getWindow().getScaledWidth() - widget.getWidth(), 0) + 1));
        widget.setY(RANDOM.nextInt(0, Math.max(MC.getWindow().getScaledHeight() - widget.getHeight(), 0) + 1));
    }

    public static void sendErrorOverlayToPlayer(PlayerEntity player, String message) {
        sendErrorOverlayToPlayer(player, Text.literal(message).formatted(Formatting.RED, Formatting.BOLD));
    }

    public static void sendErrorOverlayToPlayer(PlayerEntity player, MutableText message) {
        if (player == null) {
            return;
        }

        if (player != MC.player) {
            return;
        }

        player.sendMessage(message.formatted(Formatting.RED, Formatting.BOLD), true);
    }

    public static double getRectanglePointDistance(int rectTopLeftX, int rectTopLeftY, int rectBottomRightX, int rectBottomRightY, int pointX, int pointY) {
        int clampedX = Math.max(rectTopLeftX, Math.min(pointX, rectBottomRightX));
        int clampedY = Math.max(rectTopLeftY, Math.min(pointY, rectBottomRightY));

        return Math.sqrt(Math.pow(pointX - clampedX, 2) + Math.pow(pointY - clampedY, 2));
    }

    // why isn't there default parameter values
    public static int scale(int value) {
        return scale(value, MC.options.getGuiScale().getValue());
    }

    public static int scale(int value, int scaleFactor) {
        int i = (int)((double)value / scaleFactor);
        return (double)value / scaleFactor > (double)i ? i + 1 : i;
    }
}
