package me.gavin.blm.module.mods;

import me.gavin.blm.events.PortalGuiClosedEvent;
import me.gavin.blm.module.Module;
import me.gavin.blm.setting.BoolSetting;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Module.Info(
        name = "PortalGui",
        description = "Use guis in portals",
        category = Module.Category.Player
)
public final class Portals extends Module {

    public final BoolSetting guis = new BoolSetting("Gui", true);
    // coming soon ig
//    public final BoolSetting nohitbox = new BoolSetting("NoHitBox", true);

    @Override
    protected void init() {
        addSettings(guis);
    }

    @SubscribeEvent
    public void onGuiPortal(PortalGuiClosedEvent event) {
        event.setCanceled(true);
    }
}
