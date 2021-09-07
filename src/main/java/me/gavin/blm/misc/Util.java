package me.gavin.blm.misc;

import me.gavin.blm.BLMClient;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

import java.awt.*;

import static com.mojang.realmsclient.gui.ChatFormatting.*;

public final class Util implements MC {

    public static final String CHAT_PREFIX = DARK_RED + "{" + RED + BLMClient.MODID + DARK_RED + "} " + RESET;

    public static void sendClientMessage(String message) {
        if (mc.ingameGUI != null) {
            mc.ingameGUI.getChatGUI().printChatMessage(getComponent(CHAT_PREFIX + message));
        }
    }

    public static void sendClientMessage(String message, int id) {
        if (mc.ingameGUI != null) {
            mc.ingameGUI.getChatGUI().printChatMessageWithOptionalDeletion(getComponent(CHAT_PREFIX + message), id);
        }
    }

    public static Color normalizedRedGreen(float value) {
        float green = 1.0f - value;
        return new Color(value, green, 0f);
    }

    public static double interpolate(double now, double then, float delta) {
        return then + (now - then) * delta;
    }

    public static float normalize(float value, float min, float max) {
        return 1.0f - ((value - min) / (max - min));
    }

    public static double normalize(double value, double min, double max) {
        return 1.0d - ((value - min) / (max - min));
    }

    public static ITextComponent getComponent(String str) {
        return new TextComponentString(str);
    }
}
