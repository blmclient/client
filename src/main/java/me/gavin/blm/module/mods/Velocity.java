package me.gavin.blm.module.mods;

import me.gavin.blm.events.EntityPushedByWaterEvent;
import me.gavin.blm.module.Module;
import me.gavin.blm.setting.NumberSetting;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Module.Info(
        name = "Velocity",
        description = "Prevents velocity",
        category = Module.Category.Movement
)
public final class Velocity extends Module {

    private final NumberSetting vertical = new NumberSetting("Vertical", 0f, 0f, 1f);
    private final NumberSetting horizontal = new NumberSetting("Horizontal", 0f, 0f, 1f);

    @Override
    protected void init() {
        addSettings(horizontal, vertical);
    }

    @SubscribeEvent
    public void onWaterPush(EntityPushedByWaterEvent event) {
        if (event.getEntityId() == mc.thePlayer.entityId) {
            System.out.println("cancelling water push");
            event.setCanceled(true);
        }
    }
}
