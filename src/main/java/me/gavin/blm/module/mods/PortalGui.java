package me.gavin.blm.module.mods;

import me.gavin.blm.module.Module;

@Module.Info(
        name = "PortalGui",
        description = "Open gui's inside of nether portals",
        category = Module.Category.Player
)
public class PortalGui extends Module {

    // See ASMHooks.onLivingUpdateHook(Minecraft, GuiScreen)
}
