package me.gavin.blm.module.mods;

import me.gavin.blm.events.HurtCameraEffectEvent;
import me.gavin.blm.module.Module;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Module.Info(
        name = "NoHurtCam",
        description = "Removed the hurt cam effect",
        category = Module.Category.Render
)
public final class NoHurtCam extends Module {

    @SubscribeEvent
    public void onHurtCam(HurtCameraEffectEvent event) {
        event.setCanceled(true);
    }
}