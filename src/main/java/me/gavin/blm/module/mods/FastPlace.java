package me.gavin.blm.module.mods;

import me.gavin.blm.events.PlayerUpdateEvent;
import me.gavin.blm.module.Module;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Module.Info(
        name = "FastPlace",
        description = "Place fast",
        category = Module.Category.Player
)
public final class FastPlace extends Module {

    @SubscribeEvent
    public void onTick(PlayerUpdateEvent event) {
        mc.rightClickDelayTimer = 0;
    }
}
