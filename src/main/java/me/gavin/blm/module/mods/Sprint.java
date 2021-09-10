package me.gavin.blm.module.mods;

import me.gavin.blm.events.PlayerUpdateEvent;
import me.gavin.blm.module.Module;
import me.gavin.blm.setting.ModeSetting;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Module.Info(
        name = "Sprint",
        description = "Sprint automatically",
        category = Module.Category.Movement
)
public final class Sprint extends Module {

    private final ModeSetting<SprintMode> sprintMode = new ModeSetting<>("Mode", SprintMode.OmniDirectional);

    @SubscribeEvent
    public void onPlayerUpdate(PlayerUpdateEvent event) {
        if (sprintMode.getValue() == SprintMode.Normal) {
            if (mc.thePlayer.moveForward > 0 && sprintCheck()) {
                if (!mc.thePlayer.isSprinting())
                    mc.thePlayer.setSprinting(true);
            }
        } else {
            if (mc.thePlayer.moveForward != 0 || mc.thePlayer.moveStrafing != 0 && sprintCheck()) {
                if (!mc.thePlayer.isSprinting())
                    mc.thePlayer.setSprinting(true);
            }
        }
    }

    private boolean sprintCheck() {
        return !mc.thePlayer.isSneaking() && !mc.thePlayer.isCollidedHorizontally && !mc.thePlayer.isElytraFlying() && mc.thePlayer.getFoodStats().getFoodLevel() > 6;
    }

    public enum SprintMode {
        Normal,
        OmniDirectional
    }
}
