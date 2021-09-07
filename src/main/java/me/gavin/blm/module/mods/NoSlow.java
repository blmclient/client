package me.gavin.blm.module.mods;

import me.gavin.blm.events.PlayerMovementInputUpdateEvent;
import me.gavin.blm.module.Module;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Module.Info(
        name = "NoSlow",
        description = "Prevent various things from slowing you down",
        category = Module.Category.Movement
)
public class NoSlow extends Module {

    @SubscribeEvent
    public void onInputUpdate(PlayerMovementInputUpdateEvent event) {
        if (mc.thePlayer.isHandActive()) {
            event.getMovementInput().moveForward *= 5.0;
            event.getMovementInput().moveStrafe *= 5.0;
        }
    }
}
