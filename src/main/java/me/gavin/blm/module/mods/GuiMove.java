package me.gavin.blm.module.mods;

import me.gavin.blm.events.PlayerUpdateEvent;
import me.gavin.blm.gui.ClickGuiDisplayScreen;
import me.gavin.blm.module.Module;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiScreenOptionsSounds;
import net.minecraft.client.gui.GuiVideoSettings;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;

@Module.Info(
        name = "GuiMove",
        description = "Move while in a gui",
        category = Module.Category.Movement
)
public final class GuiMove extends Module {

    private final KeyBinding[] keys = {
            mc.gameSettings.keyBindForward,
            mc.gameSettings.keyBindBack,
            mc.gameSettings.keyBindLeft,
            mc.gameSettings.keyBindRight,
            mc.gameSettings.keyBindJump,
            mc.gameSettings.keyBindSprint
    };

    @SubscribeEvent
    public void onPlayerTick(PlayerUpdateEvent event) {
        if (mc.currentScreen instanceof GuiOptions
                || mc.currentScreen instanceof GuiVideoSettings
                || mc.currentScreen instanceof GuiScreenOptionsSounds
                || mc.currentScreen instanceof GuiContainer
                || mc.currentScreen instanceof GuiIngameMenu
                || mc.currentScreen instanceof ClickGuiDisplayScreen) {
            for (KeyBinding bind : keys) {
                KeyBinding.setKeyBindState(bind.getKeyCode(), Keyboard.isKeyDown(bind.getKeyCode()));
            }
        } else if (mc.currentScreen == null) {
            for (KeyBinding bind : keys) {
                if (!Keyboard.isKeyDown(bind.getKeyCode())) {
                    KeyBinding.setKeyBindState(bind.getKeyCode(), false);
                }
            }
        }
    }
}
