package me.gavin.blm.module.mods;

import me.gavin.blm.events.MovementInputUpdateEvent;
import me.gavin.blm.module.Module;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Module.Info(
        name = "NoSlow",
        description = "Prevent various things from slowing you down",
        category = Module.Category.Movement
)
public final class NoSlow extends Module {

    @SubscribeEvent
    public void onInputUpdate(MovementInputUpdateEvent event) {
        if (mc.thePlayer.isHandActive() && !mc.thePlayer.isRiding()) {
            event.getMovementInput().moveForward *= 5.0;
            event.getMovementInput().moveStrafe *= 5.0;
        }
    }
}
