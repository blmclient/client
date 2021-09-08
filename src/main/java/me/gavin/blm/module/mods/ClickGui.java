package me.gavin.blm.module.mods;

import me.gavin.blm.module.Module;
import org.lwjgl.input.Keyboard;

@Module.Info(
        name = "ClickGui",
        description = "Opens the click gui",
        category = Module.Category.Other,
        keybind = Keyboard.KEY_RSHIFT
)
public final class ClickGui extends Module {

    @Override
    protected void onEnable() {
        mc.displayGuiScreen(blm.getClickGui());
        disable();
    }
}
