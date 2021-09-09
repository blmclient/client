package me.gavin.blm.module.mods;

import me.gavin.blm.events.EntityPushedEvent;
import me.gavin.blm.events.PacketEvent;
import me.gavin.blm.events.PlayerPushedByWaterEvent;
import me.gavin.blm.module.Module;
import me.gavin.blm.setting.NumberSetting;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraft.network.play.server.SPacketExplosion;
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
    public void onWaterPush(PlayerPushedByWaterEvent event) {
        if (event.getEntityId() == mc.thePlayer.entityId) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onPush(EntityPushedEvent event) {
        if (event.getEntity() == mc.thePlayer)
            event.setCanceled(true);
    }

    @SubscribeEvent
    public void onPacketReceive(PacketEvent.Receive event) {
        if (event.getPacket() instanceof SPacketEntityVelocity) {
            final SPacketEntityVelocity velocityPacket = (SPacketEntityVelocity) event.getPacket();
            if (velocityPacket.getEntityID() == mc.thePlayer.getEntityId()) {
                if (horizontal.getValue() == 0 && vertical.getValue() == 0) {
                    event.setCanceled(true);
                }

                velocityPacket.motionX *= horizontal.getValue();
                velocityPacket.motionY *= vertical.getValue();
                velocityPacket.motionZ *= horizontal.getValue();
            }
        } else if (event.getPacket() instanceof SPacketExplosion) {
            final SPacketExplosion explosionPacket = (SPacketExplosion) event.getPacket();
            explosionPacket.motionX *= horizontal.getValue();
            explosionPacket.motionY *= vertical.getValue();
            explosionPacket.motionZ *= horizontal.getValue();
        }
    }
}
