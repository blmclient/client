package me.gavin.blm.module.mods;

import me.gavin.blm.events.PacketEvent;
import me.gavin.blm.module.Module;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Module.Info(
        name = "Criticals",
        description = "Makes you hit more crits",
        category = Module.Category.Combat
)
public final class Criticals extends Module {

    @SubscribeEvent
    public void onPacketSend(PacketEvent.Send event) {
        if (event.getPacket() instanceof CPacketUseEntity) {
            final CPacketUseEntity packetUseEntity = (CPacketUseEntity) event.getPacket();
            if (packetUseEntity.getAction() == CPacketUseEntity.Action.ATTACK) {
                if (mc.thePlayer.onGround && !mc.thePlayer.isInLava() && !mc.thePlayer.isInWater()) {
                    mc.thePlayer.connection.sendPacket(new CPacketPlayer.Position(mc.thePlayer.posX, mc.thePlayer.posY + 0.02, mc.thePlayer.posZ, false));
                    mc.thePlayer.connection.sendPacket(new CPacketPlayer.Position(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, false));
                }
            }
        }
    }
}
