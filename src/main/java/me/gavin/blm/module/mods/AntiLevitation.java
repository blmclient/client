package me.gavin.blm.module.mods;

import me.gavin.blm.events.PlayerUpdateEvent;
import me.gavin.blm.module.Module;
import me.gavin.blm.setting.ModeSetting;
import net.minecraft.init.MobEffects;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Module.Info(
        name = "AntiLevitation",
        description = "Negates the levitation effect",
        category = Module.Category.Movement
)
public final class AntiLevitation extends Module {

    public final ModeSetting<Mode> mode = new ModeSetting<>("Mode", Mode.Remove);

    @Override
    protected void init() {
        addSettings(mode);
    }

    @SubscribeEvent
    public void onTick(PlayerUpdateEvent event) {
        if (mc.thePlayer.isPotionActive(MobEffects.LEVITATION)) {
            if (mode.getValue() == Mode.Remove) {
                mc.thePlayer.removePotionEffect(MobEffects.LEVITATION);
            } else {
                if (!mc.thePlayer.isInWater() && !mc.thePlayer.isInLava() && !mc.thePlayer.isElytraFlying()) {
                    final double amount = mc.thePlayer.getActivePotionEffect(MobEffects.LEVITATION).getAmplifier() + 1.0;
                    mc.thePlayer.addVelocity(0, -(amount / 20.0), 0);
                }
            }
        }
    }

    public enum Mode {
        Remove,
        Velocity
    }
}
