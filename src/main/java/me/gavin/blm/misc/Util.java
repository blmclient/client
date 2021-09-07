package me.gavin.blm.misc;

import me.gavin.blm.BLMClient;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

import static com.mojang.realmsclient.gui.ChatFormatting.*;

public class Util implements MC {

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

    public static ITextComponent getComponent(String str) {
        return new TextComponentString(str);
    }
}
